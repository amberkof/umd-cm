/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package edu.umd.ks.cm.util.spring;

import java.io.StringWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.aopalliance.aop.Advice;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.kuali.rice.kew.service.WorkflowDocument;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.common.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.course.service.CourseServiceConstants;
import org.kuali.student.lum.lu.dto.CluSetInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.springframework.transaction.annotation.Transactional;

import edu.umd.ks.cm.util.audit.CourseModifyAuditInfo;
import edu.umd.ks.cm.util.siscm.dao.SisCmDao;
import edu.umd.ks.cm.util.siscm.entity.CmToSisExportCourse;
import edu.umd.ks.cm.util.siscm.service.impl.CoreGenEdClusetMapper;

/**
 * @Author VG 10/20/11
 * @See https://issues.umd.edu/browse/KSCM-616
 * Runs after CourseService.updateCourse/createCourse/updateCourseStatement/createCourseStatement is called 
 * configured in ks-embedded-admin-context.xml 
 */

public class CmToSisExportAdvice implements Advice {
	public static final String DO_NOT_OUTPUT_TO_SIS = "_DO_NOT_OUTPUT_TO_SIS_";
	
	final Logger LOG = Logger.getLogger(getClass());

	private SisCmDao sisCmDao;
	private LuService luService;
	private CourseService courseService;
	private StatementService statementService;

	/**
	 * Inject by Spring
	 * true if we should push data data to SIS. We need this so
	 * we can turn off the data push for the public instance, since
	 * it will not have UMD course data in it.
	 */
	private boolean enablePushToSis;
	private boolean enableWFDoc;	
	
	private CoreGenEdClusetMapper coreGenedClusetMapper;
	
	public CmToSisExportAdvice() {		
		super();
	}

	//  after CourseService.updateCourse/createCourse
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public CourseInfo updateSisCourseInfo(ProceedingJoinPoint pjp) throws Throwable {

	    //Check the inbound course for a special attribute flag
		//which says not to use this advice
		CourseInfo inboundCourse = (CourseInfo) pjp.getArgs()[0];
		
		//Remove the flag if it's there and save state
		boolean outputToSis = true;
		if(inboundCourse.getAttributes().containsKey(DO_NOT_OUTPUT_TO_SIS)){
			inboundCourse.getAttributes().remove(DO_NOT_OUTPUT_TO_SIS);
			outputToSis = false;
		}
		
		//Proceed with the call
		CourseInfo courseInfo = (CourseInfo) pjp.proceed();
		 
		// If the enablePushToSis environment variable is false, do not write course to SIS
		// (allows us to turn off push for public environment)
        if (!enablePushToSis)
             outputToSis = false;
  		
		//Only output if the course did not have that attribute
		if(outputToSis)
			doUpdateSisCourseInfo(courseInfo);

		// Only do the Audit if parameter passed is true KSCM-1016
//		boolean outputToWF = true;
//        if (!enableWFDoc)
//        	outputToWF = false;
//      
//		if(outputToWF)
//			doWorkflowDocument(courseInfo); 
		// End KSCM-1016
	
		return courseInfo;
	}
	
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public void doUpdateSisCourseInfo(CourseInfo courseInfo){
		String state = courseInfo.getState();
		// Write courses with these states to SIS export table
		// Superseded courses must be written to ensure end term is updated in SIS
		if("Active".equals(state) || "Retired".equals(state) || "Suspended".equals(state) || "Superseded".equals(state)){
			String statusInd = "P"; // Used to be P for pending pending for sis retrival
			List<CmToSisExportCourse> coursePending = sisCmDao.getSisCourseByCrsTrmStat(courseInfo, null);
			if(coursePending.size() > 0)
				sisCmDao.updateSisCourseInfo(courseInfo, coursePending.get(0), statusInd);
			else
				sisCmDao.updateSisCourseInfo(courseInfo, null, statusInd);
		}
	}
	
	// after CourseService.updateCourseStatement/createCourseStatement
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public void updateSisCourseInfoStatement(JoinPoint jp, Object retVal) throws Throwable {
	    // If the enablePushToSis environment variable is false, do not write course to SIS
        // (allows us to turn off push for public environment)
        if (!enablePushToSis){
            return;
        }
	    
	    StatementTreeViewInfo statement = (StatementTreeViewInfo) retVal;
		String courseId = (String) jp.getArgs()[0];		
		CourseInfo courseInfo = courseService.getCourse(courseId);
		
		//Only update approved courses
		if(courseInfo!=null && ("Active".equals(courseInfo.getState()) || 
				               "Retired".equals(courseInfo.getState()) || 
				             "Suspended".equals(courseInfo.getState()) ||
				            "Superseded".equals(courseInfo.getState()))){
			String statusInd = "P"; // pending for sis retrival
			List<CmToSisExportCourse> coursePending = sisCmDao.getSisCourseByCrsTrmStat(courseInfo, null);
			if(coursePending.size() > 0)
				sisCmDao.updateSisCourseInfo(courseInfo, coursePending.get(0), statusInd);
			else
				sisCmDao.updateSisCourseInfo(courseInfo, null, statusInd);			
		}
		
		// Only do the Audit if parameter passed is true KSCM-1016
		boolean outputToWF = true;
        if (!enableWFDoc)
        	outputToWF = false;
      
//		if(outputToWF)
//			doWorkflowDocument(courseInfo); 
		// End KSCM-1016
	}
		
	// before LuService.updateCluSet - what UMDCM uses on manual changes
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public void updateSisCourseInfoCluSetUpdate(ProceedingJoinPoint pjp) throws Throwable{
	    if(true){
	    	return;
	    }
		// If the enablePushToSis environment variable is false, do not write course to SIS
        // (allows us to turn off push for public environment)
        if (!enablePushToSis){
             return;
        }
 	    
	    Object[] args = pjp.getArgs();
		String newCluSetId = (String)args[0];  // Modified cluSetId
		CluSetInfo newCluSetInfo = (CluSetInfo)args[1]; // Modified cluSetInfo
		
		// Make sure it's a CluSet we care about (Hardcoded)
		String cluSetName = newCluSetInfo.getName();
		
		// "cluSetName" will now be a long description name (was just the code before)
		// So, get and check the new map which contains hardcoded set description names.
		
		Map<String, String> CoreGenCluSetCodeToDescriptionMap = coreGenedClusetMapper.getCodeToDescriptionMap();
	 
		Boolean weCare = CoreGenCluSetCodeToDescriptionMap.containsValue(cluSetName);
		if (weCare){
	
			// Obtain new Ids
			Set<String> newCluIds = new HashSet<String>(newCluSetInfo.getCluIds());
			List<String> listNewCluIds = newCluSetInfo.getCluIds();
            
			// Obtain old ("current") Ids via luService call			
			List<String> listOldCluIds = luService.getAllCluIdsInCluSet(newCluSetId);		
			Set<String> oldCluIds = new HashSet<String>(listOldCluIds);
			
			// Removed Courses (old - new)
            Set<String> removedCluIds = new HashSet<String>(oldCluIds);
            removedCluIds.removeAll(newCluIds);
            System.out.println("Removed these clu IDs: " + removedCluIds );
            for(String cluId :removedCluIds){
            	// Translate from VerIndId to current Ver Id to get current courseInfo obj
            	VersionDisplayInfo vdi = courseService.getCurrentVersion(CourseServiceConstants.COURSE_NAMESPACE_URI, cluId); 	
            	CourseInfo courseInfo = courseService.getCourse(vdi.getId());               
            	//sisCmDao.updateSisCourseInfo(courseInfo, "P");//FIXME we should test to see if there is a pushed record before we update vs create
            }
            
            // Added Courses (new - old)
            Set<String> addedCluIds = new HashSet<String>(newCluIds);
            addedCluIds.removeAll(oldCluIds);            
            System.out.println("Added these clu IDs: " + addedCluIds );
            for(String cluId :addedCluIds){
              	// Translate from VerIndId to current Ver Id to get current courseInfo obj
            	VersionDisplayInfo vdi = courseService.getCurrentVersion(CourseServiceConstants.COURSE_NAMESPACE_URI, cluId); 	
            	CourseInfo courseInfo = courseService.getCourse(vdi.getId());
            	//sisCmDao.updateSisCourseInfo(courseInfo, "P");//FIXME we should test to see if there is a pushed record before we update vs create            	         
            }        
		} // end if weCare
	}

	// KSCM-1016
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	private void doWorkflowDocument(CourseInfo courseInfo){
		try {
			String principalId = SecurityUtils.getCurrentPrincipalId();

			// Creating xml element with all info
			CourseModifyAuditInfo course = getCourseModifyAuditInfo (courseInfo, principalId);
			JAXBContext context = JAXBContext.newInstance(CourseModifyAuditInfo.class);
			Marshaller marshaller = context.createMarshaller();
			StringWriter writer = new StringWriter();
			marshaller.marshal(course, writer);
			String appContent = writer.toString();

			// Creating a document and sending to Workflow to "Final" state
	        WorkflowDocument document = new WorkflowDocument(principalId, "kuali.admin.type.course.modify");
	        document.setApplicationContent(appContent);
	        document.saveDocument("");
	        document.approve("Audit course change to approve");
		} catch (Exception e) {
			LOG.error("Error doWorkflowDocument with courseId: " + courseInfo.getId(), e);
		}
	}

	// KSCM-1016
	private CourseModifyAuditInfo getCourseModifyAuditInfo (CourseInfo courseInfo, String principalId) {
		CourseModifyAuditInfo course = new CourseModifyAuditInfo();
		try {
			course.setUserId(principalId);
			course.setCluId(courseInfo.getId());
			course.setCourseInfo(courseInfo);
			
			String repeatable = getRepeatableCredits(courseInfo.getAttributes());
			course.setRepeatableNL(repeatable);
			
			List<StatementTreeViewInfo> statements;
			statements = courseService.getCourseStatements(courseInfo.getId(), null, null);

			for(StatementTreeViewInfo statement : statements){					
				String nl = statementService.getNaturalLanguageForStatement(statement.getId(), "KUALI.RULE", "en");
				
				if (statement.getType().equals("kuali.statement.type.course.academicReadiness.studentEligibility"))
					course.setStudentEligibilityNL(nl);
				else if (statement.getType().equals("kuali.statement.type.course.academicReadiness.prereq")) 
					course.setPrereqNL(nl);
				else if (statement.getType().equals("kuali.statement.type.course.academicReadiness.coreq")) 
					course.setCoreqNL(nl);
				else if (statement.getType().equals("kuali.statement.type.course.recommendedPreparation"))
					course.setRecommendedPreparationNL(nl);
			}	
//			course.setStatementsList(statements);
			
		} catch (Exception e) {
			LOG.error("Error doWorkflowDocument with courseId: " + courseInfo.getId(), e);
		}
	
		return course;
	}
	
	private String getRepeatableCredits( Map<String, String> attributes) {
		String attributeNL = "";

		if (attributes.containsKey("repeatableNumCredits") && attributes.get("repeatableNumCredits").length() > 0) {
			attributeNL = "Up to " + attributes.get("repeatableNumCredits") + " credits";
			
			if (attributes.containsKey("repeatableIfContentDiffers") && attributes.get("repeatableIfContentDiffers").equals("true"))
				attributeNL = attributeNL + " if content differs";
		}

		return attributeNL;
	}
	
	public SisCmDao getSisCmDao() {
		return sisCmDao;
	}

	public void setSisCmDao(SisCmDao sisCmDao) {
		this.sisCmDao = sisCmDao;
	}

	public LuService getLuService() {
		return luService;
	}

	public void setLuService(LuService luService) {
		this.luService = luService;
	}
	
	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	public void setStatementService(StatementService statementService) {
		this.statementService = statementService;
	}

    public boolean isEnablePushToSis() {
        return enablePushToSis;
    }

    public void setEnablePushToSis(boolean enablePushToSis) {
        this.enablePushToSis = enablePushToSis;
    }
	
    public boolean isEnableWFDoc() {
        return enableWFDoc;
    }

    public void setEnableWFDoc(boolean enableWFDoc) {
        this.enableWFDoc = enableWFDoc;
    }

	public void setCoreGenedClusetMapper(CoreGenEdClusetMapper coreGenedClusetMapper) {
		this.coreGenedClusetMapper = coreGenedClusetMapper;
	}
}
