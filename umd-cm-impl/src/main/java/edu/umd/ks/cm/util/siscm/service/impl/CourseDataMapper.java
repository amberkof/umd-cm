package edu.umd.ks.cm.util.siscm.service.impl;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.r1.core.subjectcode.service.SubjectCodeService;
import org.kuali.student.r2.common.dto.AmountInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.infc.SearchResult;
import org.kuali.student.r2.core.search.infc.SearchResultCell;
import org.kuali.student.r2.core.search.infc.SearchResultRow;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.r2.lum.lo.dto.LoInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;

import edu.umd.ks.cm.course.service.utils.CM20;
import edu.umd.ks.cm.util.siscm.dto.SisToCmImportCourseInfo;

public class CourseDataMapper {
	
	private final static int HOURS_PER_WEEK=15;
	
	private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyyMM");
		}
	};

	public static Date convert(String source) throws ParseException {
		Date d = df.get().parse(source);
		return d;
	}
	
	public static String removeNonUtf8CompliantCharacters(final String inString) {
	    if (null == inString ) return null;
	    byte[] byteArr = inString.getBytes();
	    for ( int i=0; i < byteArr.length; i++ ) {
	        byte ch= byteArr[i]; 
	        // remove any characters outside the valid UTF-8 range as well as all control characters
	        // except tabs and new lines
	        if ( !( (ch > 31 && ch < 253 ) || ch == '\t' || ch == '\n' || ch == '\r') ) {
	            byteArr[i]=' ';
	        }
	    }
	    return new String( byteArr );
	}
	
	public static List<LoDisplayInfo> mapToLoDisplayList(String lo){
		// The only field we are actually taking from VPAC is learning_outcomes		
	
		// Create a list of the right type (LoDisplayInfo) to add to courseInfo
		List<LoDisplayInfo> loList = new ArrayList<LoDisplayInfo>();
	
		if (lo!=null) {
			lo = removeNonUtf8CompliantCharacters(lo);
			if (!lo.isEmpty()){
				int i = 0;
		        // Split line by line
				for(String line:lo.split("\\r?\\n")){
					if(!line.isEmpty()){
						// Split if the line is too long
						for(String truncatedLine:truncateLines(line,1500)){
							if(!truncatedLine.isEmpty() && !truncatedLine.matches("\\s+")){
								RichTextInfo rt = new RichTextInfo(); 
								rt.setPlain(truncatedLine);
					
								LoDisplayInfo loDispInfo = new LoDisplayInfo();
								LoInfo loInfo = new LoInfo();
					
								loInfo.setDescr(rt);  // set Description
								loInfo.setType("kuali.lo.type.singleUse");  // Type required
								loInfo.getAttributes().add(new AttributeInfo("sequence",Integer.toString(i))); // make sure order comes out right
								
								loDispInfo.setLoInfo(loInfo);
								loList.add(loDispInfo);
								i++;
							}
						}
					}
				}
			}
		}
		return loList;
	}
		
	private static String[] truncateLines(String line, int maxLength) {
		List<String> list = new ArrayList<String>();
		while(line.length()>maxLength){
			list.add(line.substring(0, maxLength));
			line = line.substring(maxLength);
		}
		list.add(line);
		return list.toArray(new String[list.size()]);
	}

	public static ProposalInfo mapToProposalInfo(String[] rs, CourseInfo courseInfo, int versionCount) throws SQLException, ParseException {
			ProposalInfo proposalInfo = new ProposalInfo();
			
			// Populate generic VPAC shell
			
			// String action  = rs[SchField.ACTION.ordinal()];
	        proposalInfo.getAttributes().add(new AttributeInfo("vpacAction","Add" ));
			proposalInfo.getAttributes().add(new AttributeInfo("vpacLogNo","00000" ));
	   		proposalInfo.setType("kuali.proposal.type.course.create");		
	   		proposalInfo.setName("Add: "+courseInfo.getCode()+" ver. "+versionCount);
	   		proposalInfo.setState("final");
	   		proposalInfo.setProposalReferenceType("kuali.proposal.referenceType.clu");
	   		proposalInfo.getProposalReference().add(courseInfo.getId());
            //Set the node attribute on the proposal to preroute as an initial value
            proposalInfo.getAttributes().add(new AttributeInfo( "workflowNode", "PreRoute" ));
            
			return proposalInfo;
	}
 
	
	public static CourseInfo mapToCourseInfo(CourseInfo courseInfo, SisToCmImportCourseInfo importCourse, List<String> courseSetCodes, SubjectCodeService subjectCodeService, ContextInfo contextInfo) throws Exception {

		//Get fields from result set
		String crs_cd      = importCourse.getApCrs();
		String startTerm   = importCourse.getStartTrm();
		String endTerm     = importCourse.getEndTrm();
		String txt40       = importCourse.getTxt40();
		String transTitle  = importCourse.getTransTitle();
		String formalTitle = importCourse.getFormalTitle();
		String maxCredits  = importCourse.getMaxCr();
		String minCredits  = importCourse.getMinCr();
		String gradeMethod = importCourse.getGrdMeth();
		String adminStat   = importCourse.getAdminStat();
		String core 	   = importCourse.getCore();
		String diversity   = importCourse.getDiversity();
		String genEdCodes  = importCourse.getGenEdCodes();
		
		String learningOutcomes = importCourse.getLearningOutcomes();
		
//		String crsAttrType   = rs[SchField.CRS_ATTR_TYPE.ordinal()];  
//		String crsAttrCode   = rs[SchField.CRS_ATTR_CD.ordinal()];


		if(courseInfo == null){
			courseInfo = new CourseInfo();
		}
		
		if(courseInfo.getId()==null){
			//Add the start/end terms to the id (use blanks if the end term is blank)
			String id=crs_cd;
			if(id.length()==7){
				id+="-";
			}
			id += (startTerm==null||startTerm.isEmpty())?"000000":startTerm;
			id += (endTerm==null||endTerm.isEmpty())?"000000":endTerm;
			
			courseInfo.setId("CLUID-"+id);
		}
		
		courseInfo.setCode(crs_cd);		
		courseInfo.setSubjectArea(crs_cd.substring(0,4));
		courseInfo.setCourseNumberSuffix(crs_cd.substring(4));
		courseInfo.setTranscriptTitle(transTitle.replaceAll("\\p{Cntrl}", ""));
		courseInfo.setCourseTitle(formalTitle.replaceAll("\\p{Cntrl}", ""));
		courseInfo.setLevel(crs_cd.charAt(4)+"00");
	 	
		//Credits
		if(minCredits!=null && maxCredits!=null){
			courseInfo.setCreditOptions(new ArrayList<ResultValuesGroupInfo>());
			ResultValuesGroupInfo creditInfo = new ResultValuesGroupInfo();
			if(minCredits.equals(maxCredits)){
				creditInfo.setTypeKey(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_FIXED);
				creditInfo.getResultValueRange().setMaxValue(maxCredits);
 			}else{
				creditInfo.setTypeKey(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_VARIABLE);
				creditInfo.getResultValueRange().setMaxValue(maxCredits);
				creditInfo.getResultValueRange().setMinValue(minCredits); 
			}
			courseInfo.getCreditOptions().add(creditInfo);	
		}
		
		//Grading Method
		courseInfo.setGradingOptions(new ArrayList<String>());
		//dynamic attributes are never deleted(see KSLAB-1733) so set these to false 
		courseInfo.getAttributes().add(new AttributeInfo(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_PASSFAIL, "false"   ));
		courseInfo.getAttributes().add(new AttributeInfo( CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_AUDIT, "false"  ));
		if(gradeMethod!=null){
			if(gradeMethod.contains("R")){
				courseInfo.getGradingOptions().add("kuali.resultComponent.grade.letter");
			}
			if(gradeMethod.contains("S")){
				courseInfo.getGradingOptions().add("kuali.resultComponent.grade.satisfactory");
			}
			if(gradeMethod.contains("N")){
				courseInfo.getGradingOptions().add("kuali.resultComponent.grade.completedNotation");
			}
			
			//These go into a separate field on the screen.. but we might be able to split it up just in the ui 
			if(gradeMethod.contains("P")){ 
				courseInfo.getAttributes().add(new AttributeInfo( CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_PASSFAIL,"true" ));
				courseInfo.getGradingOptions().add("kuali.resultComponent.grade.passFail");
			}
			if(gradeMethod.contains("A")){
				courseInfo.getAttributes().add(new AttributeInfo( CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_AUDIT,"true" ));
				courseInfo.getGradingOptions().add("kuali.resultComponent.grade.audit");
			}

		}
		
		//Descriptions
		courseInfo.setDescr(new RichTextInfo());
		if(txt40!=null){
			String description = txt40.replaceAll("\\p{Cntrl}", "");
			description = description.replaceAll("\\*", ""); 
			description = description.replaceAll("\\}", "");
			description = description.replaceAll("\\{", "");
			courseInfo.getDescr().setPlain(description);
						
		}
		
		//Set Dates
		courseInfo.setEffectiveDate(convert(startTerm));
		if(endTerm!=null&&!endTerm.isEmpty()){
			courseInfo.setExpirationDate(convert(endTerm));
		}
		
		courseInfo.setStartTerm(deriveTerm(startTerm));
		
		courseInfo.setEndTerm(deriveTerm(endTerm));
		
		
		//Check if this is a Pilot Course
		if(adminStat!=null && adminStat.toUpperCase().contains("P")){
			courseInfo.setPilotCourse(true);
		}else{
			courseInfo.setPilotCourse(false);
		}
		
		courseInfo.setType("kuali.lu.type.CreditCourse");

		//Set Learning outcomes
		if(courseInfo.getCourseSpecificLOs().isEmpty()){
			courseInfo.setCourseSpecificLOs(mapToLoDisplayList(learningOutcomes));
		}
		
		//Set default values for required fields that we should map later
		if(courseInfo.getCampusLocations().isEmpty()){
			courseInfo.getCampusLocations().add("NO");
		}
		
		if(null == CM20.attributeInfoToMap(courseInfo.getAttributes()).get("finalExamStatus")){
		    courseInfo.getAttributes().add(new AttributeInfo( "finalExamStatus", "STD" ));
		}
		
		//Default credit hours to min credit for variable
		if(courseInfo.getFormats().isEmpty()){
			FormatInfo format = new FormatInfo();
			format.setType("kuali.lu.type.CreditCourseFormatShell");
			format.setState("Approved");
			String lectureHours = "0";
			if (minCredits!=null){
				lectureHours = minCredits;
			}
			format.getActivities().add(makeActivity("kuali.lu.type.activity.LectureORSeminar",lectureHours));
			format.getActivities().add(makeActivity("kuali.lu.type.activity.Lab","0"));
			format.getActivities().add(makeActivity("kuali.lu.type.activity.Discussion","0"));
			format.getActivities().add(makeActivity("kuali.lu.type.activity.ExperientialLearningOROther","0"));

			courseInfo.getFormats().add(format);
			
			//Set some required fields 
			courseInfo.getAttributes().add(new AttributeInfo( "semesterType", "semesterType.standard"  ));
			//Calculate contact hours total
			DecimalFormat deciamlFormat = new DecimalFormat("#.##");
			courseInfo.getAttributes().add(new AttributeInfo( "activityTotalContactHours", deciamlFormat.format(Float.parseFloat(lectureHours)*HOURS_PER_WEEK)  ));
			courseInfo.getAttributes().add(new AttributeInfo(  "activityTotalCredits", lectureHours ));
			courseInfo.getAttributes().add(new AttributeInfo( "activityTypeExplanation", "."  ));
		}
		
		if(courseInfo.getUnitsContentOwner().isEmpty()){
			courseInfo.getUnitsContentOwner().addAll(getOrgsFromPrefix(courseInfo.getSubjectArea(), subjectCodeService, contextInfo));
		}
		
		mapToCourseInfoGenedCoreDiversityOnly(courseInfo,importCourse,courseSetCodes);
		
		 
		return courseInfo;
	}
 
	
	/**
	 * 
	 * This method will only work when the course already
	 * exists in CM.  All courses should exist in CM since
	 * it is now the system of record.
	 * <p>
	 * 
	 * 
	 * @param courseInfo
	 * @param importCourse
	 * @param courseSetCodes
	 * @param subjectCodeService
	 * @return
	 * @throws Exception
	 */
	public static CourseInfo mapToCourseInfoGenedCoreDiversityOnly(  CourseInfo courseInfo, SisToCmImportCourseInfo importCourse, List<String> courseSetCodes) throws Exception {
 
	    
	        // The only thing we need to do is add/remove from coursesets
	        // based on the diversity, core, and gen ed attributes.
	    
	        String core        = importCourse.getCore();
	        String genEdCodes  = importCourse.getGenEdCodes();
	        String diversity   = importCourse.getDiversity();  // NOTE: diversity is part of core
       
	        //  Create CluSet and Add, or just Add to Clu Set 
	        //  If a Core, Div, or Gen Ed code exist for this course
	        //  crsAttrType:  GG = GenEd, GC = CAA Core (ignoring for now, using CAM core)
	        
	        // Map diversity
	        if ("D".equals(diversity)) {
	            // Fix this one  up to match top level Set name.
	            // Since this one does not have sub sets.
	            courseSetCodes.add("Diversity");
	        }
	        // Map core
	        if(core!=null&&!core.isEmpty()){
	            courseSetCodes.add(core);
	        }
	        // Map Gen ed
	        if(genEdCodes!=null&&!genEdCodes.isEmpty()){
	            for(String genEdCode:genEdCodes.split(",")){
	                courseSetCodes.add(genEdCode);
	            }
	        }
	        
	        return courseInfo;
	    }
	
	private static ActivityInfo makeActivity(String activityType, String hours) {
		ActivityInfo activity = new ActivityInfo();
		activity.setTypeKey(activityType);
		activity.setState("Active");
		AmountInfo amount = new AmountInfo();
		amount.setUnitTypeKey("kuali.atp.duration.week");
		amount.setUnitQuantity(hours);
		activity.setContactHours(amount);
		DecimalFormat deciamlFormat = new DecimalFormat("#.##");
		activity.getAttributes().add(new AttributeInfo( "calculatedContactHours",deciamlFormat.format(HOURS_PER_WEEK*Float.parseFloat(hours))  ));
		activity.getAttributes().add(new AttributeInfo( "credits",hours  ));
		return activity;
	}

	public static List<String> getOrgsFromPrefix(String prefix, SubjectCodeService subjectCodeService, ContextInfo contextInfo) throws MissingParameterException{
		SearchRequestInfo searchRequest = new SearchRequestInfo("subjectCode.search.orgsForSubjectCode");
		searchRequest.addParam("subjectCode.queryParam.code", prefix);
		searchRequest.addParam("subjectCode.queryParam.optionalRestrictToValidRelations", "true");
		SearchResult results = null;
		List<String> orgIds = null;
        try {
            results = subjectCodeService.search(searchRequest, contextInfo);
            orgIds = new ArrayList<String>();

            // If there are no results, try again with invalid relations
            if (results.getRows().isEmpty()) {
                searchRequest.getParams().clear();
                searchRequest.addParam("subjectCode.queryParam.code", prefix);
                results = subjectCodeService.search(searchRequest, contextInfo);
            }

        } catch (InvalidParameterException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (OperationFailedException e2) {
            e2.printStackTrace();
            throw new RuntimeException(e2);
        } catch (PermissionDeniedException e3) {
            e3.printStackTrace();
            throw new RuntimeException(e3);
        } catch (MissingParameterException e4) {
            e4.printStackTrace();
            throw new RuntimeException(e4);
        }
		for(SearchResultRow row:results.getRows()){
			for(SearchResultCell cell:row.getCells()){
				if("subjectCode.resultColumn.orgId".equals(cell.getKey())){
					orgIds.add(cell.getValue());
					break;
				}
			}
		}
		return orgIds;
	}
	
	public static Map<String,String> getOrgsAndNamesFromPrefix(String prefix, SubjectCodeService subjectCodeService, ContextInfo contextInfo) throws MissingParameterException{
		SearchRequestInfo searchRequest = new SearchRequestInfo("subjectCode.search.orgsForSubjectCode");
		searchRequest.addParam("subjectCode.queryParam.code", prefix);
		searchRequest.addParam("subjectCode.queryParam.optionalRestrictToValidRelations", "true");

        SearchResult results = null;
        Map<String, String> orgs = null;
        try {
            results = subjectCodeService.search(searchRequest, contextInfo);
            orgs = new HashMap<String, String>();

            // If there are no results, try again with invalid relations
            if (results.getRows().isEmpty()) {
                searchRequest.getParams().clear();
                searchRequest.addParam("subjectCode.queryParam.code", prefix);
                results = subjectCodeService.search(searchRequest, contextInfo);
            }
        } catch (InvalidParameterException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (OperationFailedException e2) {
            e2.printStackTrace();
            throw new RuntimeException(e2);
        } catch (PermissionDeniedException e3) {
            e3.printStackTrace();
            throw new RuntimeException(e3);
        } catch (MissingParameterException e4) {
            e4.printStackTrace();
            throw new RuntimeException(e4);
        }
		for(SearchResultRow row:results.getRows()){
			String orgId=null;
			String orgName=null;
			for(SearchResultCell cell:row.getCells()){
				if("subjectCode.resultColumn.orgId".equals(cell.getKey())){
					orgId=cell.getValue();
				}else if("subjectCode.resultColumn.orgLongName".equals(cell.getKey())){
					orgName=cell.getValue();
				}
			}
			orgs.put(orgId, orgName);
		}
		return orgs;
	}
	
	public static String deriveTerm(String trm) {
		String semester = "";
		if(trm !=null && trm.length()==6){
			
			switch (Integer.parseInt(trm.substring(4))) {
			case 1:
				semester="Spring";
				break;
			case 5:
				semester="Summer1";
				break;
			case 7:
				semester="Summer2";
				break;
			case 8:
				semester="Fall";
				break;
			case 12:
				semester="Winter";
				break;
			default:
				break;
			}
			
			return "kuali.atp."+trm.substring(0,4)+semester;
		}
		return null;
	}
}
