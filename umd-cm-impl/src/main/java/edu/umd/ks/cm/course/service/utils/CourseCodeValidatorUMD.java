package edu.umd.ks.cm.course.service.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.kuali.student.common.dictionary.dto.FieldDefinition;
import org.kuali.student.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.common.search.dto.SearchResult;
import org.kuali.student.common.search.dto.SearchResultCell;
import org.kuali.student.common.search.dto.SearchResultRow;
import org.kuali.student.common.search.service.SearchDispatcher;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.common.validator.DefaultValidatorImpl;
import org.kuali.student.lum.course.dto.CourseInfo;

/**
 * This class provides custom validation on three fields:
 * courseInfo.subjectArea
 * courseInfo.courseNumberSuffix
 * courseInfo.startTerm
 * 
 * It uses two different searches. One is for just subject Area and Course Number
 * the other searches using all 3 of the above fields as params (if we send a null into the JPQL it breaks it).
 * There are 3 cases which can fail validation which all require different user messages.
 * These are defined in this design spec:
 * https://confluence.umd.edu/display/KSCM/DS+-+Prevent+Course+Number+Re-Use
 * 
 * 1 - Prevent re-use when approved course exists
 * 2 - Prevent re-use when proposed course exists. 
 * 3 - Prevent re-use when retired course exists, and timing of re-use would overlap between courses 
 *   
 * @author mike
 *
 */
public class CourseCodeValidatorUMD extends DefaultValidatorImpl {

	private SearchDispatcher searchDispatcher;
	
	/**    This helper method searches for existing Draft, Active or Suspended courses with the course code
	 *     which is currently being proposed this method ignores Retired State Courses (the other search will handle that).
	 * 	   Uses lu.search.countNumberOfConflictingClusBasedOnCourseCodeVindId which is defined in
	 *     umd-cm-lu-search-config.xml.  The lu-search takes course code and verIndId as params and returns
	 *     c.version.versionIndId and c.state 
	 * 
	 * @param course - The course which is being proposed. 
	 * @param elementStack - elementStack
	 * @param field - The field which is being validated (all fields on form are validated upon save).
	 * @return This method will return the results of the search, in a List of ValidationResultInfo objects.
	 * Or an empty list, if no conflicts are found.
	 */	
	private List<ValidationResultInfo> searchForDraftActiveSuspendedConflicts(CourseInfo course, Stack<String> elementStack, FieldDefinition field){
		
		List<ValidationResultInfo> validationResults = new ArrayList<ValidationResultInfo>();
		
		// Setup the search
		SearchResult result = null;
		SearchRequest searchRequest = null;
		searchRequest = new SearchRequest("lu.search.countNumberOfConflictingClusBasedOnCourseCodeVindId");					
		searchRequest.addParam("lu.queryParam.cluCode", course.getCode());
		searchRequest.addParam("lu.queryParam.cluVersionIndId", course.getVersionInfo().getVersionIndId());
		
		// Perform the search and obtain results where the course code already exists.
		result = searchDispatcher.dispatchSearch(searchRequest);							
		
		// if there is a result, and there is at least one row we have a conflict, so parse it
		if ((result!=null) && (!result.getRows().isEmpty()))
		{
			// Parse the search results, set the user message
			for (SearchResultRow row:result.getRows()) {
		       for (SearchResultCell cell:row.getCells()) {  
		    	    // If the state is Draft - give one user message.
					if (("lu.resultColumn.cluState".equals(cell.getKey()) && 
							(cell.getValue().equals("Draft"))))
					  {
						ValidationResultInfo validationResult = new ValidationResultInfo(getElementXpath(elementStack) + "/" + field.getName());
						validationResult.setWarning(getMessage("validation.course.preventCourseCodeReuse.draft"));	
						validationResults.add(validationResult);
						return validationResults;
					  }  
					else // If the state is Active or Suspended - give a different user message. 
					if (("lu.resultColumn.cluState".equals(cell.getKey()) && 
							  ((cell.getValue().equals("Active") || (cell.getValue().equals("Suspended"))))))
					  {
						ValidationResultInfo validationResult = new ValidationResultInfo(getElementXpath(elementStack) + "/" + field.getName());
						validationResult.setWarning(getMessage("validation.course.preventCourseCodeReuse.activeSuspended"));
						validationResults.add(validationResult);
						return validationResults;							
					  } 
				}
			}
		}					
		// empty list indicates the case of no results 
		return validationResults;
	}
	
	/**    This helper method searches for existing Retired courses with a course code and End date which 
	 *     conflict with the course code and start date currently being proposed.  
	 *     This method ignores Active, Draft or Suspended State Courses (the other search will handle that first).
	 * 	   Uses lu.search.countNumberOfConflictingClusBasedOnCourseCodeVindIdStartTerm which is defined in
	 *     umd-cm-lu-search-config.xml.  The lu-search takes courseCodem verIndId and StartTerm as params and returns
	 *     c.version.versionIndId and c.state and c.expirationDate
	 * 
	 * @param course - The course which is being proposed. 
	 * @param elementStack - elementStack
	 * @param field - The field which is being validated (all fields on form are validated upon save).
	 * @return This method will return the results of the search, in a List of ValidationResultInfo objects.
	 * Or an empty list, if no conflicts are found.
	 */	
	private List<ValidationResultInfo> searchForRetiredConflicts(CourseInfo course, Stack<String> elementStack, FieldDefinition field){
		
		List<ValidationResultInfo> validationResults = new ArrayList<ValidationResultInfo>();
		
		// Setup the search
		SearchResult result = null;
		SearchRequest searchRequest = null;
		
		// lu.search.countNumberOfConflictingClusBasedOnCourseCodeVindIdStartTerm
		searchRequest = new SearchRequest("lu.search.countNumberOfConflictingClusBasedOnCourseCodeVindIdStartTerm");
		searchRequest.addParam("lu.queryParam.cluCode", course.getCode());				
		searchRequest.addParam("lu.queryParam.cluVersionIndId", course.getVersionInfo().getVersionIndId());	
		searchRequest.addParam("lu.queryParam.luAtpStartTerm", course.getStartTerm());

		// Perform the search and obtain results where the course code already exists.
		// that is being used by a retired course, with an end date greater than the 
		// proposed start date.
		
		result = searchDispatcher.dispatchSearch(searchRequest);
		
		// if there is a result, take the first result.  In this case there should only be one result maximum
		// and if there are more results, someone is trying to create a course in the past 
		if ((result!=null) && (!result.getRows().isEmpty()))
		{
			ValidationResultInfo validationResult = new ValidationResultInfo(getElementXpath(elementStack) + "/" + field.getName());
			// Parse the search results, set the user message
			for (SearchResultRow row:result.getRows()) {
		       for (SearchResultCell cell:row.getCells()) {
		      		// Extract first End Date we find to build user message. 
						if ("lu.resultColumn.luVersionEndTerm".equals(cell.getKey())){
							// get end term for message, trim off kuali.atp.
							String endTerm = cell.getValue().substring(10);
							validationResult.setWarning("Another course is using this course number until " +
							        endTerm + ".  " +
									"Consult <a href=\"http://www.testudo.umd.edu/ks/documents.html\" target=\"_blank\">User Guide</a> for information and " +
									"instructions regarding changes to retired and Pilot courses.");							 
							validationResults.add(validationResult);
							return validationResults;
						}												
		       }
			}
		 }
		
		// empty list indicates the case of no results 
		return validationResults;
	}
	
	/**
	 * This method is the main method for this class.  It is overridden to provide custom validation 
	 * of certain UMD fields for the class (see above for further details on what this class does).
	 * 
	 * @param o - The course which is being proposed.
	 * @param objectStructure - objectStructure 
	 * @param elementStack - elementStack
	 * @param field - The field which is being validated (all fields on form are validated upon save).
	 * @return This method will return the results of two searches, in a List of ValidationResultInfo 
	 * objects. Or an empty list, if no conflicts are found.
	 * The priority order of conflicts (which user error will be displayed in the case of multiple 
	 * conflicts) will be:	 * 
	 * Draft
	 * Active, Suspended
	 * Retired
	 * 
	 * Really, in a system with good clean data, we should not be getting multiple results anyway.
	 * 
	 */
	@Override
	public List<ValidationResultInfo> validateObject(FieldDefinition field,
			Object o, ObjectStructureDefinition objStructure,
			Stack<String> elementStack) {
		
		List<ValidationResultInfo> validationResults = new ArrayList<ValidationResultInfo>();
	
		if (o instanceof CourseInfo) {
			CourseInfo course = (CourseInfo) o;
			
			// Only Search If the code has been filled out, and a Ver Ind ID exists			
			if(course.getCode()!=null && course.getVersionInfo()!=null){		
				
				// Draft, Active, or Suspended Search - do this first. Ignore Retired State Courses.
				// lu.search.countNumberOfConflictingClusActiveSuspended
				validationResults = searchForDraftActiveSuspendedConflicts(course, elementStack, field);
				if (!validationResults.isEmpty()) {
					return validationResults;
				}

				// Retired Check - 2nd Search, if Start term is filled out, include Start Term
				// and make sure we don't have a retired course which overlaps the new course.
				if (course.getStartTerm()!=null){
					validationResults = searchForRetiredConflicts(course, elementStack, field);
					if (!validationResults.isEmpty()) {
						return validationResults;
					}				
				}
			}	
		}
		
	  // Empty list indicates the case of no results (passes validation)
	  return validationResults;
	}

	public void setSearchDispatcher(SearchDispatcher searchDispatcher) {
		this.searchDispatcher = searchDispatcher;
	}

}

