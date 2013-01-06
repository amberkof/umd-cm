package edu.umd.ks.cm.util.siscm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.kuali.student.r1.core.subjectcode.service.SubjectCodeService;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularReferenceException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.search.dto.SortDirection;
import org.kuali.student.r2.core.search.infc.SearchRequest;
import org.kuali.student.r2.core.search.infc.SearchResult;
import org.kuali.student.r2.core.search.infc.SearchResultCell;
import org.kuali.student.r2.core.search.infc.SearchResultRow;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.springframework.transaction.annotation.Transactional;

import com.thoughtworks.xstream.XStream;

import edu.umd.ks.cm.course.service.utils.DiffCourseUtil;
import edu.umd.ks.cm.util.siscm.dao.SisCmDao;
import edu.umd.ks.cm.util.siscm.dto.SisToCmImportCourseInfo;
import edu.umd.ks.cm.util.siscm.entity.CmToSisExportCourse;
import edu.umd.ks.cm.util.siscm.entity.DiffCourse;
import edu.umd.ks.cm.util.siscm.entity.SiscmDiff;
import edu.umd.ks.cm.util.siscm.service.SiscmService;
import edu.umd.ks.cm.util.spring.CmToSisExportAdvice;

@WebService(endpointInterface = "edu.umd.ks.cm.util.siscm.service.SiscmService", serviceName = "SiscmService", portName = "SiscmService", targetNamespace = "http://cm.ks.umd.edu/wsdl/siscm")
public class SiscmServiceImpl implements SiscmService {
	
	final Logger logger = Logger.getLogger(SiscmServiceImpl.class);
			
	private CourseService courseService;
	private AtpService atpService;
	private CluService luService;
	private SubjectCodeService subjectCodeService;
	private CmToSisExportAdvice cmToSisExportAdvice;
	
	private SisCmDao dao;	

	private CoreGenEdClusetMapper coreGenedClusetMapper;

	 
	
	   
    @Override
    @Transactional(readOnly=false, rollbackFor={Throwable.class})
   public String diffCourse(SisToCmImportCourseInfo sisCourse, String courseCd, String startTerm) throws DataValidationErrorException, Exception {
        
        // Convert the SIS term to the CM term so we can compare it
        String inputCourseStartTermAtpId = CourseDataMapper.deriveTerm(sisCourse.getStartTrm());
        
        // Get the version info for the course with this start term in CM
        CourseVersionInfo existingVersion = getExistingVersion(sisCourse.getApCrs(), inputCourseStartTermAtpId);
        
        if (existingVersion == null){
            return null; // return null if course does not exist in CM
        }
      
        // Get the data in the CM course
        CourseInfo courseInfo = courseService.getCourse(existingVersion.getCourseId());
        
        // Find all differences in the course
        DiffCourseUtil diff = new DiffCourseUtil(courseInfo, sisCourse, luService, coreGenedClusetMapper);
 
        // Create list of diffs
        DiffCourse diffCourse = diff.diff();
        
        // Convert to XML
        String xml = new XStream().toXML(diffCourse);
        
        // Send XML back
        return xml; 
        
   }
    

    /**
     * 	
     * We are making CM the course of record.  This method will send only the core, gen ed, and diversity
     * info to CM from SIS.  All other information is excluded.
     * 
     * @see edu.umd.ks.cm.util.siscm.service.SiscmService#importCourseGenedCoreDiversityOnly(edu.umd.ks.cm.util.siscm.dto.SisToCmImportCourseInfo, long, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional(readOnly=false, rollbackFor={Throwable.class})
   public String importCourseGenedCoreDiversityOnly(SisToCmImportCourseInfo course, long batchJobId, String courseCd, String startTerm) throws DataValidationErrorException, Exception {
        
        // We'll place course set codes in here
        List<String> courseSetCodes = new ArrayList<String>();
        
        // Convert the SIS term to a term in CM format
        String inputCourseStartTermAtpId = CourseDataMapper.deriveTerm(course.getStartTrm());
        
        // Get the course from CM
        CourseVersionInfo existingVersion = getExistingVersion(course.getApCrs(), inputCourseStartTermAtpId);
        
        // Hold a reference for future use
        //CourseInfo savedCourse;
        
        // Fail if we do not find a version of this course in CM
        if (existingVersion == null){
            // The course must exist in CM in order to set core, gen ed, diversity.    
            return "COURSE_MISSING_FROM_CM";
        } 
          
        // Get the course from CM 
        CourseInfo courseInfo = courseService.getCourse(existingVersion.getCourseId());
        
        
        // Skip courses that are NOT in active state.  We need to return this to the course loader
        // so it knows to exclude them from the report.
        if (!"Active".equals(courseInfo.getState())){
            return "SKIPPED_NOT_ACTIVE_STATE";
        }
 
        // Use our custom mapper to map only gen ed, core, diversity
        courseInfo = CourseDataMapper.mapToCourseInfoGenedCoreDiversityOnly(courseInfo, course, courseSetCodes);
       
        // Update the course
        // savedCourse = courseService.updateCourse(courseInfo);
        
     
        //Only do Gened/CORE/Diversity for Active courses
        //addCourseToCourseSets(savedCourse.getVersionInfo().getVersionIndId(), courseSetCodes, savedCourse.getState());
        
        String out = "Importing course " + courseCd + " start term " + startTerm + " state is " + courseInfo.getState() +" . Placing in course sets: ";
        
        addCourseToCourseSets(courseInfo.getVersionInfo().getVersionIndId(), courseSetCodes, courseInfo.getState());
        
        for (String s : courseSetCodes) {
            out +=s + ", ";
        }
        
        logger.info(out);
        
        // This is always a modify operation since we never add a course
        // using this method.  Here we update the hash value in the DB
        // so we can determine if a course changed.
        SiscmDiff diff = dao.findSiscmDiff(courseCd,startTerm);
        if (diff == null){
            diff = new SiscmDiff();
            diff.setCrs(courseCd);
            diff.setStartTrm(startTerm);
            diff.setModifyDate(new Date());
            diff.setHash(course.getHash());
            dao.update(diff);
        }else {
            diff.setHash(course.getHash());
            dao.update(diff);
        }
        
        return null;
          
    }
    
    
	/**
	 * @returns true if there are conflicts false if there are not
	 */
	@Override
	@Transactional(readOnly=false, rollbackFor={Throwable.class})
	@Deprecated
	public boolean importCourse(SisToCmImportCourseInfo course, long batchJobId, String courseCd, String startTerm) throws DataValidationErrorException, Exception {
		List<String> courseSetCodes = new ArrayList<String>();
		
		String inputCourseStartTermAtpId = CourseDataMapper.deriveTerm(course.getStartTrm());
		
		CourseVersionInfo existingVersion = getExistingVersion(course.getApCrs(), inputCourseStartTermAtpId);
		
		CourseInfo savedCourse;
		
		boolean isConflict = false;
		
		//3 scenarios: new course, update existing, new version 
		//Uncomment this line if you want to force end dates to create new subsequent versions 
		if(existingVersion==null/*||"Retired".equals(existingVersion.getState())*/){
			//New Course
			CourseInfo courseInfo = CourseDataMapper.mapToCourseInfo(null, course, courseSetCodes, subjectCodeService);
			courseInfo.setState("Active");
			//Check if this course is to be retired
			if(course.getEndTrm()!=null){
				courseInfo.setState("Retired");
				courseInfo.getAttributes().put("retirementRationale", "Retired by CourseLoader");
				courseInfo.getAttributes().put("lastTermOffered", courseInfo.getEndTerm());
			}
			if(!"A".equals(course.getDiffOperation())||!course.isLast()){
				//Only push adds back to sis(exclude anything that is not an add)
				courseInfo.getAttributes().put(CmToSisExportAdvice.DO_NOT_OUTPUT_TO_SIS, Boolean.TRUE.toString());
			}
			savedCourse = courseService.createCourse(courseInfo);
		}else{
			if(inputCourseStartTermAtpId.equals(existingVersion.getStartTerm())){
				//This is an existing course so update. (get the existing course and merge in changes)
				CourseInfo courseInfo = courseService.getCourse(existingVersion.getCourseId());
				courseInfo = CourseDataMapper.mapToCourseInfo(courseInfo, course, courseSetCodes, subjectCodeService);
				
				//Check if this course is to be retired and set required fields if so
				if("Active".equals(courseInfo.getState())&&course.getEndTrm()!=null){
					courseInfo.setState("Retired");
					courseInfo.getAttributes().put("retirementRationale", "Retired by CourseLoader");
					courseInfo.getAttributes().put("lastTermOffered", courseInfo.getEndTerm());
				}
				
				//If the change from sis is bringing this course out of retirement, then make it active
				if("Retired".equals(courseInfo.getState()) && course.getEndTrm()==null && !courseInfo.isPilotCourse()){
					courseInfo.setState("Active");
				}
				
				//Update the course
				isConflict = detectConflictsAndMarkForSisPush(courseInfo);
				savedCourse = courseService.updateCourse(courseInfo);
			}else{
				//Create a new version from the previous version
				CourseInfo courseInfo = courseService.createNewCourseVersion(existingVersion.getCourseVersionIndId(), "Versioned by Course Load");
				courseInfo.getFormats().clear();
				courseInfo = CourseDataMapper.mapToCourseInfo(courseInfo, course, courseSetCodes, subjectCodeService);

				//Check if this course is to be retired
				if(course.getEndTrm()!=null){
					courseInfo.setState("Retired");
					courseInfo.getAttributes().put("retirementRationale", "Retired by CourseLoader");
					courseInfo.getAttributes().put("lastTermOffered", courseInfo.getEndTerm());
				}else{
					courseInfo.setState("Active");
					courseInfo.setEndTerm(null);
					courseInfo.getAttributes().put("retirementRationale","");
					courseInfo.getAttributes().put("lastTermOffered","");
				}
				
				//Get the previous version and supersede it
				CourseInfo previousCourse = courseService.getCourse(existingVersion.getCourseId());
				previousCourse.setState("Superseded");
				previousCourse.getAttributes().put("lastTermOffered","");
				if(previousCourse.getEndTerm()==null){
					previousCourse.setEndTerm(findPreviousTermTo(courseInfo.getStartTerm()));	
 				}
				previousCourse.getAttributes().put(CmToSisExportAdvice.DO_NOT_OUTPUT_TO_SIS, Boolean.TRUE.toString());
				courseService.updateCourse(previousCourse);
				
				//Set the updated as the current version if this is not an add
				if(!"A".equals(course.getDiffOperation())||!course.isLast()){
					courseInfo.getAttributes().put(CmToSisExportAdvice.DO_NOT_OUTPUT_TO_SIS, Boolean.TRUE.toString());
				}
				savedCourse = courseService.updateCourse(courseInfo);
				courseService.setCurrentCourseVersion(savedCourse.getId(), null);
			}
		}
		
		//Only do Gened/CORE/Diversity for Active courses
		addCourseToCourseSets(savedCourse.getVersionInfo().getVersionIndId(), courseSetCodes, savedCourse.getState());
		
		//Mark this record as processed by updating the hash code
		//Based on the operation value do an insert or an update
		
		// The new load is using the DIFF table and the MD5 hashes are different
		// This section is commented out to prevent corruption of the diff table
		// in case the old load is run
		/*
		if("A".equals(course.getDiffOperation())){
			SiscmDiff diff = new SiscmDiff();
			diff.setCrs(courseCd);
			diff.setStartTrm(startTerm);
			diff.setHash(course.getHash());
	 		dao.create(diff);
		}else if("M".equals(course.getDiffOperation())){
			SiscmDiff diff = dao.findSiscmDiff(courseCd,startTerm);
			diff.setHash(course.getHash());
			dao.update(diff);
		}
		*/
		return isConflict;
	}
	
	private boolean detectConflictsAndMarkForSisPush(CourseInfo courseInfo) {
		//if there is a row in the siscm push table, there is a change in UMDCM and an incoming conflicting change from SIS
		//In this case, we will overwrite the changes in UMDCM with SIS data and log to the user that a conflict occurred
		
		//Check if there is a pending output record
		List<CmToSisExportCourse> coursePending = dao.getSisCourseByCrsTrmStat(courseInfo, "P");

		//If there is no pending there is no conflict so don't push in change.
		if(coursePending==null||coursePending.isEmpty()){
			courseInfo.getAttributes().put(CmToSisExportAdvice.DO_NOT_OUTPUT_TO_SIS, Boolean.TRUE.toString());
			return false;
		}
		return true;
	}

	/**
	 * Attempts to add a clu to the appropriate cluSet
	 * When information is available.
	 * @param versionIndId
	 * @param courseSetCodes
	 * @param courseState 
	 * @return
	 * @throws OperationFailedException 
	 * @throws MissingParameterException 
	 * @throws UnsupportedActionException 
	 * @throws PermissionDeniedException 
	 * @throws InvalidParameterException 
	 * @throws DoesNotExistException 
	 */
	private Boolean addCourseToCourseSets(String versionIndId, List<String> courseSetCodes, String courseState) throws OperationFailedException, MissingParameterException, DoesNotExistException, InvalidParameterException, PermissionDeniedException, UnsupportedActionException {
		//find which gen ed/ core course sets relate to this course already
		Set<String> courseSetIdsToDelete = new HashSet<String>();
		SearchRequest searchRequest = new SearchRequest("cluset.search.generic");
		searchRequest.addParam("cluset.queryParam.optionalIds", coreGenedClusetMapper.getCluSetIds());
		List<String> courseIds = new ArrayList<String>(1);
		courseIds.add(versionIndId);
		searchRequest.addParam("cluset.queryParam.optionalVerIndId", courseIds);
		SearchResult searchResult = luService.search(searchRequest);
		if (searchResult!=null && searchResult.getRows()!=null){
			for(SearchResultRow row:searchResult.getRows()){
				for(SearchResultCell cell:row.getCells()){
					if("cluset.resultColumn.cluSetId".equals(cell.getKey())){
						courseSetIdsToDelete.add(cell.getValue());
						break;
					}
				}
			}
		}
		
		//Add this course to the course sets only if it is active
		if("Active".equals(courseState)){
			for(String courseSetCode:courseSetCodes){
		 		String cluSetId = coreGenedClusetMapper.getCluSetId(courseSetCode);
		 		if ((cluSetId != null) && (!cluSetId.equals(""))){		 			
				    try {   
				    	logger.info("Inserting: Clu VersionIndId: " + versionIndId + " into CluSet with guid:  " + cluSetId);
				    	// exclude AK set, for now.
				    	// Need to do this because it's still in the CoreGenEdClusetMapper data
				    	if (!(cluSetId.equals("45058a59-e153-4219-be6d-3235e5c324d8"))){
				    		luService.addCluToCluSet(versionIndId, cluSetId);
				    		courseSetIdsToDelete.remove(cluSetId);
				    	}
				    	   		
					} catch (DoesNotExistException e) {
						logger.error("<Error> DoesNotExistException - adding clu with VerIndId: " + versionIndId + " to cluSet with Id" + cluSetId,e );
					} catch (InvalidParameterException e) {
						logger.error("<Error> InvalidParameterException - adding clu with VerIndId: " + versionIndId + " to cluSet with Id" + cluSetId,e );
					} catch (MissingParameterException e) {
						logger.error("<Error> MissingParameterException - adding clu with VerIndId: " + versionIndId + " to cluSet with Id" + cluSetId,e );
					} catch (OperationFailedException e) {
						if (e.getMessage().startsWith("CluSet already contains Clu")) {
							// Eat already exists error	
							logger.error("<Error> OperationFailedException - adding clu with VerIndId: " + versionIndId + " to cluSet with Id" + cluSetId,e );
						} else {
							throw e;
						}
					} catch (PermissionDeniedException e) {
						logger.error("<Error> PermissionDeniedException - adding clu with VerIndId: " + versionIndId + " to cluSet with Id" + cluSetId,e );
					} catch (UnsupportedActionException e) {
						logger.error("<Error> UnsupportedActionException - adding clu with VerIndId: " + versionIndId + " to cluSet with Id" + cluSetId,e );
					}
		 		}
			}	
		}
		
		//Now remove this course from any old coursesets
		for(String courseSetId:courseSetIdsToDelete){
			luService.removeCluFromCluSet(versionIndId, courseSetId);
		}
		
		return true;
	}
	
	private String findPreviousTermTo(String startTerm) throws Exception {
		if(startTerm!=null){
			//Look up the most recent Atp before the current one.
			SearchRequest request = new SearchRequest("atp.search.advancedAtpSearch");
			request.addParam("atp.advancedAtpSearchParam.atpEndDateAtpConstraintIdExclusive", startTerm);
			request.setSortDirection(SortDirection.DESC);
			request.setSortColumn("atp.resultColumn.atpStartDate");
			
			SearchResult result = atpService.search(request);
			if (result!=null && result.getRows()!=null){
				for(SearchResultCell cell:result.getRows().get(0).getCells()){
					if("atp.resultColumn.atpId".equals(cell.getKey())){
						return cell.getValue();
					}
				}
			}
		}
		return null;
	}
	
	private CourseVersionInfo getExistingVersion(String apCrs, String startTrm) throws MissingParameterException {
		SearchRequest request = new SearchRequest("lu.search.getCluFromCluCode");
		request.addParam("lu.queryParam.cluCode", apCrs);
		request.addParam("lu.queryParam.luAtpStartTerm", startTrm);
		SearchResult result = luService.search(request);
		CourseVersionInfo courseVersionInfo = null; 

		for(SearchResultRow row:result.getRows()){
			courseVersionInfo = new CourseVersionInfo();
			for(SearchResultCell cell:row.getCells()){
				if("lu.resultColumn.cluId".equals(cell.getKey())){
					courseVersionInfo.setCourseId(cell.getValue());
				}else if("lu.resultColumn.luOptionalVersionIndId".equals(cell.getKey())){
					courseVersionInfo.setCourseVersionIndId(cell.getValue());
				}else if("lu.resultColumn.luOptionalStartTermAtpId".equals(cell.getKey())){
					courseVersionInfo.setStartTerm(cell.getValue());
				}else if("lu.resultColumn.luOptionalEndTermAtpId".equals(cell.getKey())){
					courseVersionInfo.setEndTerm(cell.getValue());
				}else if("lu.resultColumn.luOptionalState".equals(cell.getKey())){
					courseVersionInfo.setState(cell.getValue());
				}
			}
		}
		return courseVersionInfo;
	}

	@Override
	@Transactional(readOnly=false, rollbackFor={Throwable.class})
	public boolean exportCourses(List<String> courseIds) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		if(courseIds!=null){
			for(String courseId:courseIds){
				CourseInfo courseInfo = courseService.getCourse(courseId);
				cmToSisExportAdvice.doUpdateSisCourseInfo(courseInfo);
			}
		}
		return true;
	}
	
	@Override
	public boolean exportAllCourses() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		
		SearchRequest request = new SearchRequest("lu.search.mostCurrent.union");
		request.addParam("lu.queryParam.luOptionalType", "kuali.lu.type.CreditCourse");
		SearchResult result = luService.search(request);
		
		for(SearchResultRow row:result.getRows()){
			for(SearchResultCell cell:row.getCells()){
				if("lu.resultColumn.cluId".equals(cell.getKey())){
					try{
						CourseInfo courseInfo = courseService.getCourse(cell.getValue());
						cmToSisExportAdvice.doUpdateSisCourseInfo(courseInfo);
					}catch (Exception ex){
						logger.error("Could not export course with id:"+cell.getValue(),ex);
					}
				}
			}
		}
		
		return true;
	}
	
	@Override
	@Transactional(readOnly=false, rollbackFor={Throwable.class})
	public String updateCourseOrgsForPrefix(String prefixes) throws MissingParameterException, DoesNotExistException, InvalidParameterException, OperationFailedException, PermissionDeniedException, UnsupportedOperationException, DataValidationErrorException, VersionMismatchException, AlreadyExistsException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException, CircularReferenceException {
		if(prefixes==null){
			throw new MissingParameterException("Must enter a prefix");
		}
		
		StringBuilder reportSb = new StringBuilder("");
		
		//split up the list of prefixes we want to update and loop through them
		for(String currentPrefix:prefixes.trim().toUpperCase().split("[^A-Z0-9]")){
			reportSb.append("\nProcessed Prefix:"+currentPrefix+"\nUsing Orgs:");
			
			//Find all valid org relations for a given prefix 
			Map<String,String> orgs = CourseDataMapper.getOrgsAndNamesFromPrefix(currentPrefix, subjectCodeService);
			for(String orgName:orgs.values()){
				reportSb.append("\n"+orgName);
			}
			
			//Search for all courses that start with the prefix and are of a certain set of States
			SearchRequest searchRequest = new SearchRequest("lu.search.generic");
			searchRequest.addParam("lu.queryParam.luOptionalType", "kuali.lu.type.CreditCourse");
			searchRequest.addParam("lu.queryParam.luOptionalCode", currentPrefix);
			List<String> states = new ArrayList<String>();
			states.add("Active"); //What other states?
			states.add("Draft"); 
			searchRequest.addParam("lu.queryParam.luOptionalState", states);
			SearchResult searchResults = luService.search(searchRequest);
			
			//For each course, update the curriculum oversight orgs
			for (SearchResultRow row:searchResults.getRows()){
				String courseId = null;
				for(SearchResultCell cell:row.getCells()){
					if("lu.resultColumn.cluId".equals(cell.getKey())){
						courseId = cell.getValue();
						break;
					}
				}
				CourseInfo course = courseService.getCourse(courseId);
				reportSb.append("\nUpdated Course:"+course.getCode());
				course.setUnitsContentOwner(new ArrayList<String>(orgs.keySet()));
				courseService.updateCourse(course);
			}
			
		}
		return reportSb.toString();
	}
	
	public void setDao(SisCmDao dao) {
		this.dao = dao;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	public void setAtpService(AtpService atpService) {
		this.atpService = atpService;
	}

	public void setLuService(LuService luService) {
		this.luService = luService;
	}

	public void setCmToSisExportAdvice(CmToSisExportAdvice cmToSisExportAdvice) {
		this.cmToSisExportAdvice = cmToSisExportAdvice;
	}

	public void setSubjectCodeService(SubjectCodeService subjectCodeService) {
		this.subjectCodeService = subjectCodeService;
	}

	public void setCoreGenedClusetMapper(CoreGenEdClusetMapper coreGenedClusetMapper) {
		this.coreGenedClusetMapper = coreGenedClusetMapper;
	}



}
