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

package edu.umd.ks.cm.util.siscm.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.kuali.student.common.messagebuilder.booleanmessage.ast.BooleanFunction;
import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.r1.common.dao.impl.AbstractCrudDaoImpl;
import org.kuali.student.r1.core.statement.dao.StatementDao;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r1.core.statement.entity.Statement;
import org.kuali.student.r1.core.statement.naturallanguage.translators.ReqComponentTranslator;
import org.kuali.student.r1.core.statement.naturallanguage.translators.StatementParser;
import org.kuali.student.r1.core.statement.naturallanguage.util.ReqComponentReference;
import org.kuali.student.r1.core.statement.service.StatementService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.infc.SearchResult;
import org.kuali.student.r2.core.search.infc.SearchResultCell;
import org.kuali.student.r2.core.search.infc.SearchResultRow;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.CourseJointInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.springframework.transaction.annotation.Transactional;

import edu.umd.ks.cm.course.service.utils.CM20;
import edu.umd.ks.cm.util.siscm.dao.SisCmDao;
import edu.umd.ks.cm.util.siscm.entity.CmToSisExportCourse;
import edu.umd.ks.cm.util.siscm.entity.SisToCmImportCourse;
import edu.umd.ks.cm.util.siscm.entity.SiscmDiff;
import edu.umd.ks.cm.util.siscm.entity.SiscmDiffPK;

/**
 * @Author VG 10/20/11
 * @See https://issues.umd.edu/browse/KSCM-616
 * Used by SisCourseInfoAdvice to update SISCM.UMDCM_CRS table for MF
 */

public class SisCmDaoImpl extends AbstractCrudDaoImpl implements SisCmDao {

	private CourseService courseService;
	private StatementService statementService;
	private CluService luService;
	private StatementDao statementDao;
	private ReqComponentTranslator reqComponentTranslator;

	final static Logger LOG = Logger.getLogger(SisCmDaoImpl.class);
	
	@Override
	public void setEm(EntityManager em) {
		super.setEm(em);
	}

   public List<SisToCmImportCourse> getImportQueueCoursesOrderedByCrsAndStartTerm( ) {
        Query query = em.createNamedQuery("SisToCmImportCourse.findAllOrderedByCrsAndStartTrm");
        @SuppressWarnings("unchecked")
        List<SisToCmImportCourse> resultList = query.getResultList();
        return resultList;
    }
	
	public List<CmToSisExportCourse> getSisCourseByCrsTrmStat(CourseInfo course, String statusInd) {
		Query query;
		if(statusInd!=null){
			query = em.createNamedQuery("CmToSisExportCourse.findByCrsTrmStat");
			query.setParameter("statusInd", statusInd);
		}else{
			query = em.createNamedQuery("CmToSisExportCourse.findByCrsTrm");
		}
		query.setParameter("course", course.getCode());
		query.setParameter("term", getTerm(course.getStartTerm()));

		@SuppressWarnings("unchecked")
		List<CmToSisExportCourse> resultList = query.getResultList();
		return resultList;
	}

	

	
	//@Override
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public void updateSisCourseInfo(CourseInfo courseInfo, CmToSisExportCourse course, String statusInd,  ContextInfo contextInfo) {
		try {
			//Create a new object if it does not yet exist
			if(course == null){
				course = new CmToSisExportCourse();
			}
			
			// reqs
			List<StatementTreeViewInfo> statements = courseService.getCourseStatements(courseInfo.getId(), null, null,contextInfo);
			String courseStatements = "";
			if (!statements.isEmpty())
				courseStatements = getStatements(statements,contextInfo);
			String courseOfferedAs = getCoursesOfferedAs(courseInfo.getJoints(), courseInfo.getCrossListings(),contextInfo);
			String courseAttributes = getCourseAttributes(courseInfo.getAttributes(), courseOfferedAs);
			String courseAdditionalInformation = getAdditionalInformation(CM20.attributeInfoToMap(courseInfo.getAttributes()));

			String courseReqs = "";
			if (courseStatements.length() > 0) 
				courseReqs = courseStatements;
			
			if (courseAttributes.length() > 0) 
				if (courseReqs.length() > 0)
					courseReqs = courseReqs + ".  " + courseAttributes;
				else
					courseReqs = courseAttributes;
				
			if (courseAdditionalInformation.length() > 0) 
				if (courseReqs.length() > 0)
					courseReqs = courseReqs + ".  " + courseAdditionalInformation;
				else
					courseReqs = courseAdditionalInformation;
				
			if ((courseReqs.length() > 0) && (!courseReqs.endsWith(".")))
				courseReqs = courseReqs + ".";	
			
			course.setCourseReqs(courseReqs);
			
			//Copy fields
			course.setCourse(courseInfo.getCode());
			course.setTerm(getTerm(courseInfo.getStartTerm()));
			if ("Retired".equals(courseInfo.getState()) || "Superseded".equals(courseInfo.getState()))
				course.setTermEnd(getTerm(courseInfo.getEndTerm()));
			else
				course.setTermEnd(null);
			
			course.setCourseTitle(courseInfo.getCourseTitle());
			course.setTranscrTitle(courseInfo.getTranscriptTitle());
			course.setCourseDesc(courseInfo.getDescr()==null?null:courseInfo.getDescr().getPlain());
			
			// credits
			String[] credits = getCredits(courseInfo.getCreditOptions());
			float minCredit = 0;
			if (credits[0] != null && credits[0].length() > 0)
				minCredit =  Float.parseFloat(credits[0]);
			course.setMinCredit(minCredit);
			float maxCredit = 0;
			if (credits[1] != null && credits[1].length() > 0)
				maxCredit = Float.parseFloat(credits[1]);
			course.setMaxCredit(maxCredit);
		
			// grading methods
			course.setGradeMethod(getGradeMethods(courseInfo.getGradingOptions(), courseInfo));

			// userId
			course.setUserId(SecurityUtils.getCurrentPrincipalId());		
		
			// params for the conditions
			course.setCourseId(courseInfo.getId());
			course.setStatusInd(statusInd);
			
			// pilot (aka admin_Stat)
			course.setAdminStat((courseInfo.isPilotCourse() == true) ? "P":"");

			// params for genEd, core, diversity
			String genEd = "", core = "", diversity = "";
			List<String> cluSetNameList = getCluSetName(courseInfo.getId(), contextInfo);			
			if (!cluSetNameList.isEmpty()) {
				for (String name : cluSetNameList) {
					if (name.equalsIgnoreCase("CORE"))
						core = "Yes";
					else if (name.equalsIgnoreCase("Diversity"))
						diversity = "Yes";
					else
						genEd = genEd + name + ",";
				}
				if (genEd.length() > 0)
					genEd = genEd.substring(0, genEd.length() - 2);
			}
			
			course.setGenEd(genEd);
			// Want to exclude "AK" course set for now.
			if (!core.equals("AK")){
			   course.setCore(core);
			}
			course.setDiversity(diversity);

			// Get activities from first format only, 
			// then loop through the activities to 
			// obtain lecture, lab, discussion and experimental hours.

			course.setLectureHours(0);
			course.setLabHours(0);	
			course.setExperimentalHours(0);
			course.setDiscussionHours(0);
			
			List<FormatInfo> formats = courseInfo.getFormats();
			List <ActivityInfo> activities = formats.get(0).getActivities();
			for (ActivityInfo ai:activities){
				String aType = ai.getTypeKey();
				if (ai.getContactHours()!=null && ai.getContactHours().getUnitQuantity()!=null) {
					int hours = 0;
					try{
						hours = Integer.parseInt(ai.getContactHours().getUnitQuantity());
					}catch(NumberFormatException e){
						hours = 0;
					}
					if (aType.equalsIgnoreCase("kuali.lu.type.activity.Lecture")){
						course.setLectureHours(hours);
					} else if (aType.equalsIgnoreCase("kuali.lu.type.activity.Lab")){						   
						course.setLabHours(hours);
					} else if (aType.equalsIgnoreCase("kuali.lu.type.activity.Experimental")){						   
						course.setExperimentalHours(hours);
					} else if (aType.equalsIgnoreCase("kuali.lu.type.activity.Discussion")){						   
						course.setDiscussionHours(hours);
					}
				}
			}
			


			//Update or create based on if there is an id
			if(course.getId()==null){
				create(course);
			}else{
				update(course);
			}
			
		} catch (Exception e) {
			throw new RuntimeException("Error updateSisCourseInfo with courseId: " + courseInfo.getId(), e);
		}
	}

	private String getTerm(String atpId) {
		if(null==atpId){
			return null;
		}
		String month = "";
		if(atpId.endsWith("Spring")){
			month="01";
		}else if(atpId.endsWith("Summer1")){
			month="05";
		}else if(atpId.endsWith("Summer2")){
			month="07";
		}else if(atpId.endsWith("Fall")){
			month="08";
		}else if(atpId.endsWith("Winter")){
			month="12";
		}
		String year = atpId.substring(10, 14);	
		return year+month;		
	}
	
	private String[] getCredits(List<ResultValuesGroupInfo> creditOptions) {
		String minCredits = "";
		String maxCredits = "";
		for (ResultValuesGroupInfo creditOption:creditOptions) {
		 
		    //Depending on the type, set the id, type and result values differently
            if(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_FIXED.equals(creditOption.getTypeKey())){
      
			//2.0 upgrade: This is return a float.  Float.parseFloat.  
				minCredits =  creditOption.getResultValueRange().getMinValue();
				maxCredits = minCredits;
			} else {
			    // 2.0 upgrade: now there is both variable and multiple credit values in the CourseAssembler
			    // I think we only use min max an UMD?
				minCredits = creditOption.getResultValueRange().getMinValue();
				maxCredits = creditOption.getResultValueRange().getMinValue();
			}
		}
		String[] credits = {minCredits, maxCredits};
		return credits;
	}
	
	private String getGradeMethods(List<String> gradMeths, CourseInfo courseInfo) {
	    
	    Map courseAttributes = CM20.attributeInfoToMap(courseInfo.getAttributes());
	    
		String gradeMethod = "";
		if (!gradMeths.isEmpty() && gradMeths.contains("kuali.resultComponent.grade.letter"))
			gradeMethod += "R";
		if (!gradMeths.isEmpty() && gradMeths.contains("kuali.resultComponent.grade.passFail")){
			gradeMethod += "P";
		}else if (courseAttributes.containsKey(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_PASSFAIL) &&
				"true".equals(courseAttributes.get(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_PASSFAIL))){
			gradeMethod += "P";
		}
		if (!gradMeths.isEmpty() && gradMeths.contains("kuali.resultComponent.grade.audit")){
			gradeMethod += "A";
		}else if (courseAttributes.containsKey(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_AUDIT) &&
				"true".equals(courseAttributes.get(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_AUDIT))){
			gradeMethod += "A";
		}
		if (!gradMeths.isEmpty() && gradMeths.contains("kuali.resultComponent.grade.satisfactory"))
			gradeMethod += "S";
		if (!gradMeths.isEmpty() && gradMeths.contains("kuali.resultComponent.grade.completedNotation"))
			gradeMethod += "N";
		if("AS".equals(gradeMethod))
			gradeMethod = "SA"; //This one is reversed in SIS
		
		return gradeMethod;
	}

	private String getStatements(List<StatementTreeViewInfo> statements, ContextInfo contextInfo) throws Exception  {
		// Requirements
		String courseReqs = "";
		HashMap nlComponents = new HashMap();
		
		for(StatementTreeViewInfo statement : statements){					
			String nl = statementService.getNaturalLanguageForStatement(statement.getId(), "KUALI.RULE.CATALOG", "en");
							
			Statement stmt = this.statementDao.fetch(Statement.class, statement.getId());
			
			StatementParser statementParser = new StatementParser("*", "+");
			
			String booleanExpression = statementParser.getBooleanExpressionAsReqComponents(stmt);
			List<ReqComponentReference> reqComponentList = statementParser.getLeafReqComponents(stmt);
					
			if (booleanExpression != null && booleanExpression.length() > 0) {
				nl = getNaturalLanguageForStatement(booleanExpression, reqComponentList);
				if (statement.getType().equals("kuali.statement.type.course.academicReadiness.studentEligibility")) {
					nl = "Restriction: " + nl;
					nlComponents.put("kuali.statement.type.course.academicReadiness.studentEligibility", nl);
				} else if (statement.getType().equals("kuali.statement.type.course.academicReadiness.prereq")) {
					nl = "Prerequisite: " + nl;
					nlComponents.put("kuali.statement.type.course.academicReadiness.prereq", nl);
				} else if (statement.getType().equals("kuali.statement.type.course.academicReadiness.coreq")) {
					nl = "Corequisite: " + nl;
					nlComponents.put("kuali.statement.type.course.academicReadiness.coreq", nl);
				} else if (statement.getType().equals("kuali.statement.type.course.recommendedPreparation")) {
					nl = "Recommended: " + nl;
					nlComponents.put("kuali.statement.type.course.recommendedPreparation", nl);
				}				
			} 
		}		

		if (nlComponents.containsKey("kuali.statement.type.course.academicReadiness.prereq"))
			courseReqs = courseReqs + nlComponents.get("kuali.statement.type.course.academicReadiness.prereq");
		
		if (nlComponents.containsKey("kuali.statement.type.course.academicReadiness.coreq")) {
			if (courseReqs.length() > 0)
				courseReqs = courseReqs + ".  " + nlComponents.get("kuali.statement.type.course.academicReadiness.coreq");
			else
				courseReqs = courseReqs + nlComponents.get("kuali.statement.type.course.academicReadiness.coreq");
		}

		if (nlComponents.containsKey("kuali.statement.type.course.recommendedPreparation")) {
			if (courseReqs.length() > 0)
				courseReqs = courseReqs + ".  " + nlComponents.get("kuali.statement.type.course.recommendedPreparation");
			else
				courseReqs = courseReqs + nlComponents.get("kuali.statement.type.course.recommendedPreparation");
		}

		if (nlComponents.containsKey("kuali.statement.type.course.academicReadiness.studentEligibility")) {
			if (courseReqs.length() > 0)
				courseReqs = courseReqs + ".  " + nlComponents.get("kuali.statement.type.course.academicReadiness.studentEligibility");
			else
				courseReqs = courseReqs + nlComponents.get("kuali.statement.type.course.academicReadiness.studentEligibility");
		}
		
		return courseReqs;
	}	
		
	private String getCourseAttributes(List<AttributeInfo> attributesListObjs, String courseOfferedAs) {
		String attributeNL = "";
 
		Map<String,String> attributes = CM20.attributeInfoToMap(attributesListObjs);
		
		if (attributes != null && attributes.containsKey("repeatableNumCredits") &&  attributes.get("repeatableNumCredits") != null && attributes.get("repeatableNumCredits").length() > 0) {
			attributeNL = "Repeatable to " + attributes.get("repeatableNumCredits") + " credits";
			
			if (attributes.containsKey("repeatableIfContentDiffers") &&  attributes.get("repeatableIfContentDiffers") != null && attributes.get("repeatableIfContentDiffers").equals("true"))
				attributeNL = attributeNL + " if content differs";
		}

		if (courseOfferedAs != null && courseOfferedAs.length() > 0) 
			if (attributeNL.length() > 0)
				attributeNL = attributeNL + ".  " + courseOfferedAs;
			else
				attributeNL = courseOfferedAs;
  
		if (attributes != null && attributes.containsKey("creditGrantedFor") &&  attributes.get("creditGrantedFor") != null && attributes.get("creditGrantedFor").length() > 0) 
			if (attributeNL.length() > 0)
				attributeNL = attributeNL + ".  Credit only granted for: " + attributes.get("creditGrantedFor");
			else
				attributeNL = "Credit only granted for: " + attributes.get("creditGrantedFor");

		if (attributes != null  && attributes.containsKey("formerly") &&  attributes.get("formerly") != null  && attributes.get("formerly").length() > 0)
			if (attributeNL.length() > 0)
				attributeNL = attributeNL + ".  Formerly: " + attributes.get("formerly");
			else
				attributeNL = "Formerly: " + attributes.get("formerly");
		
		return attributeNL;		
		
	}

	// KSCM1430 - Append Additional Course Information at the end of the field.
	private String getAdditionalInformation(Map<String, String> attributes){
		String attributeNL = "";
		if (attributes != null && attributes.containsKey("additionalCourseInformation") &&  attributes.get("additionalCourseInformation") != null   && attributes.get("additionalCourseInformation").length() > 0) 
			attributeNL = "Additional information: " + attributes.get("additionalCourseInformation");
	
		return attributeNL;	
	}
	
	private String getCoursesOfferedAs(List<CourseJointInfo> joints, List<CourseCrossListingInfo> crossListings, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		String coursesOfferedAsNL = "";
		
		if (joints != null && joints.size() > 0) {			
			String courseJointNL = "";
			for (CourseJointInfo joint : joints) {
				if(joint!=null){
					//Check the joint's state so that only active ones are displayed
					CluInfo jointClu = luService.getClu(joint.getCourseId(), contextInfo);
					if("Active".equals(jointClu.getState())){
						courseJointNL = courseJointNL + joint.getSubjectArea() + joint.getCourseNumberSuffix() + ", ";
					}
				}
			}
			if (courseJointNL.length() > 0) 
				coursesOfferedAsNL = "Also offered as: " + courseJointNL.trim().substring(0, courseJointNL.trim().length() - 1);
		}

		if (crossListings != null && crossListings.size() > 0) {
			String courseCrossListingNL = "";
			for (CourseCrossListingInfo crossListing : crossListings){
				//Check the crosslist's state so that only active ones are displayed
				CluInfo crosslistClu = luService.getClu(CM20.attributeInfoToMap(crossListing.getAttributes()).get("courseId"), contextInfo);
				if("Active".equals(crosslistClu.getState())){
					courseCrossListingNL = courseCrossListingNL + crossListing.getCode() + ", ";
				}
			}
			if (courseCrossListingNL.length() > 0) {
				if (coursesOfferedAsNL.length() == 0){
					coursesOfferedAsNL = "Also offered as: " + courseCrossListingNL.trim().substring(0, courseCrossListingNL.trim().length() - 1);
				} else { 
					coursesOfferedAsNL = coursesOfferedAsNL + ", " + courseCrossListingNL.trim().substring(0, courseCrossListingNL.trim().length() - 1);
				}
			}
		}

		return coursesOfferedAsNL;
	}
	
	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}
	
	public void setStatementService(StatementService statementService) {
		this.statementService = statementService;
	}

	public void setLuService(CluService luService) {
		this.luService = luService;
	}

    public void setStatementDao(final StatementDao statementDao) {
		this.statementDao = statementDao;
	}
   
	public void setReqComponentTranslator(final ReqComponentTranslator reqComponentTranslator) {
		this.reqComponentTranslator = reqComponentTranslator;
	}		
	
	private String getNaturalLanguageForStatement(String booleanExpression, List<ReqComponentReference> reqComponentList) throws Exception {
		HashMap reqComponentMap = new HashMap();
		LinkedHashMap<Integer, Integer> parPositionMap = new LinkedHashMap<Integer, Integer>();
		ArrayList<Integer> parLeftList = new ArrayList<Integer>();
		
		for(ReqComponentReference reqComponent:reqComponentList) {
			String translation = this.reqComponentTranslator.translate(reqComponent.getReqComponent(), "KUALI.RULE.CATALOG", "en");
			if (translation != null && translation.length() > 0 && translation.substring(translation.length()-1).equals("."))
				translation = translation.substring(0, translation.length()-1);
			reqComponentMap.put(reqComponent.getBooleanId(), translation);
		}

        BooleanFunction booleanFunction = new BooleanFunction(booleanExpression);
        List<String> funcSymbs = booleanFunction.getSymbols();

		for (int i = 0; i < funcSymbs.size(); i++) { 
			if (funcSymbs.get(i).equals("(")) { 
				parLeftList.add(i);
			}
			int parLeftLast = parLeftList.size() - 1;
			if (funcSymbs.get(i).equals(")")) {
				parPositionMap.put(parLeftList.get(parLeftLast), i);
				parLeftList.remove(parLeftLast);
			}	
		}		

		// For the expression (A + B + (C * D)) want to remove outer ()
		if (parPositionMap.containsKey(0) && parPositionMap.get(0) == funcSymbs.size()-1) {
			parPositionMap.remove(0);
			funcSymbs.set(0, "null");
			funcSymbs.set(funcSymbs.size()-1, "null");
		}			
		
		if (!parPositionMap.isEmpty()) {
			for(Integer key : parPositionMap.keySet()) {
				StringBuffer funcSymb = new StringBuffer("");
				int pos = 0;
				String expr = "";
				for (int i = key + 1; i < parPositionMap.get(key); i++) {
					String funcSymbAdd = funcSymbs.get(i);
					if (!funcSymbAdd.equals("+") && !funcSymbAdd.equals("*") && !funcSymbAdd.equals("null")) {
						expr = (String) reqComponentMap.get(funcSymbAdd);
						if (pos == 0 && !funcSymbAdd.substring(0, 1).equals("V") && expr.length() > 2 &&
								expr.substring(0, 1).equals("(") && expr.substring(expr.length()-1).equals(")")) {
							expr = expr.substring(1, expr.length()-1);
						}
						pos = 1;
						
						//convert the first character of 'expr' to lower case, if necessary
						if (expr.length() > 0) {
							char ch0 = expr.charAt(0);
							if (ch0 <= 'Z' && ch0 >= 'A') {
								if (expr.length() > 1) {
									char ch1 = expr.charAt(1);
									if (ch1 >= 'a' && ch1 <= 'z') {
										expr = expr.substring(0, 1).toLowerCase() + expr.substring(1);
									}
								} else {
									expr = expr.toLowerCase();
								}
							}
						}

						funcSymb.append(expr);									
					} else if (funcSymbAdd.equals("+")) {
						funcSymb.append("; or ");
					} else if (funcSymbAdd.equals("*")) {
						funcSymb.append( "; and ");
					}	
				} // for int i					
				String id = "V" + Integer.toString(key);
				funcSymb.insert(0, "("); funcSymb.append(")");
				reqComponentMap.put(id, funcSymb.toString());
					
				funcSymbs.set(key, id);
				for (int i = key + 1; i < parPositionMap.get(key) + 1; i++) 
					funcSymbs.set(i, "null");
			}
		} 		
		
        List<String> funcSymbsNew = new ArrayList<String>();
		for (int i = 0; i < funcSymbs.size(); i++) { 
			if (!funcSymbs.get(i).equals("null")) 
				funcSymbsNew.add(funcSymbs.get(i));
		}

		String nl = "";
		if (funcSymbsNew.size() == 1) {
			nl = (String) reqComponentMap.get(funcSymbsNew.get(0));
			if (nl.substring(0, 1).equals("(") && nl.substring(nl.length()-1).equals(")"))
				nl = nl.substring(1, nl.length()-1);				
		} else {
			int pos = 0;
			String expr = "";
			for (int i = 0; i < funcSymbsNew.size(); i++) {
				if (!funcSymbsNew.get(i).equals("*") && !funcSymbsNew.get(i).equals("+")) {
					expr = (String) reqComponentMap.get(funcSymbsNew.get(i));
					if (pos == 0) { 
						if (expr.length() > 2 && expr.substring(0, 1).equals("(") && expr.substring(expr.length()-1).equals(")")) 
							expr = expr.substring(1, expr.length()-1);
						pos = 1;
					} else {
						if (funcSymbsNew.get(i).substring(0,1).equals("V") && expr.length() > 2 && 
								expr.substring(0, 1).equals("(") && expr.substring(expr.length()-1).equals(")"))
							expr = expr.substring(1, expr.length()-1);
					}
					nl = nl + expr;
				} else if (funcSymbsNew.get(i).equals("+")) {
					if ((i > 0 && funcSymbsNew.get(i-1).substring(0,1).equals("V")) || (i < (funcSymbsNew.size() - 1) && funcSymbsNew.get(i+1).substring(0,1).equals("V")))
						nl = nl + ". Or ";
					else	
						nl = nl + "; or ";
				} else if (funcSymbsNew.get(i).equals("*")) {
					if ((i > 0 && funcSymbsNew.get(i-1).substring(0,1).equals("V")) || (i < (funcSymbsNew.size() - 1) && funcSymbsNew.get(i+1).substring(0,1).equals("V")))
						nl = nl + ". And ";
					else	
						nl = nl + "; and ";
				}	
			}
		}	
		
		//TODO: Fix Capitalization
		nl = nl.substring(0, 1).toUpperCase() + nl.substring(1);
		return nl.trim();
	}
	
	//@Override
	public SiscmDiff findSiscmDiff(String courseCd, String startTerm) {
		SiscmDiffPK key = new SiscmDiffPK();
		key.setCrs(courseCd);
		key.setStartTrm(startTerm);
		return em.find(SiscmDiff.class, key);
	}
	
	//@Override
	@Transactional(readOnly = false, noRollbackFor = { DoesNotExistException.class }, rollbackFor = { Throwable.class })
	public SisToCmImportCourse updateSisToCmImportCourseInTransaction(SisToCmImportCourse course){
		return update(course);
	}
	
	private List<String> getCluSetName(String cluId, ContextInfo contextInfo) throws MissingParameterException, PermissionDeniedException, OperationFailedException, InvalidParameterException {
	    SearchRequestInfo request = new SearchRequestInfo("lu.search.cluSetByCluId");
		request.addParam("lu.queryParam.cluVersionIndId", cluId);
		SearchResult result = luService.search(request,contextInfo);
		List<String> cluSetNameList = new ArrayList<String>(); 

		for(SearchResultRow row:result.getRows()){
			for(SearchResultCell cell:row.getCells())
				if("lu.resultColumn.cluSetName".equals(cell.getKey()))
					cluSetNameList.add(cell.getValue());
		}
		
		return cluSetNameList;
	}
}