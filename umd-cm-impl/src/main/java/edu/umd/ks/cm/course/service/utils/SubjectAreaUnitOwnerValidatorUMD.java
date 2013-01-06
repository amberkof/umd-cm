package edu.umd.ks.cm.course.service.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.kuali.student.r1.common.dictionary.dto.FieldDefinition;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.validator.DefaultValidatorImpl;
import org.kuali.student.r2.core.search.infc.SearchRequest;
import org.kuali.student.r2.core.search.infc.SearchResult;
import org.kuali.student.r2.core.search.infc.SearchResultCell;
import org.kuali.student.r2.core.search.infc.SearchResultRow;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

public class SubjectAreaUnitOwnerValidatorUMD extends DefaultValidatorImpl {

	private SearchDispatcher searchDispatcher;
	
	@Override
	public List<ValidationResultInfo> validateObject(FieldDefinition field,
			Object o, ObjectStructureDefinition objStructure,
			Stack<String> elementStack) {
		
		List<ValidationResultInfo> validationResults = new ArrayList<ValidationResultInfo>();

		if (o instanceof CourseInfo) {
			CourseInfo course = (CourseInfo) o;
			if(course.getSubjectArea()!=null && !course.getUnitsContentOwner().isEmpty()){
				//Do a search for the orgs allowed under this subject code
				SearchRequest searchRequest = new SearchRequest("subjectCode.search.orgsForSubjectCode");
				searchRequest.addParam("subjectCode.queryParam.code", course.getSubjectArea());
				searchRequest.addParam("subjectCode.queryParam.optionalRestrictToValidRelations", "true");
				
				SearchResult result = searchDispatcher.dispatchSearch(searchRequest);
				
				Set<String> orgIds = new HashSet<String>();
				boolean useageAllOf = true;

				if(result!=null){
					//Parse the search results and get a list of all org ids, and if any of the subject code types was 
					//useage one of
					for(SearchResultRow row:result.getRows()){
						for(SearchResultCell cell:row.getCells()){
							if("subjectCode.resultColumn.orgId".equals(cell.getKey())){
								orgIds.add(cell.getValue());
							}else if("subjectCode.resultColumn.type".equals(cell.getKey())&&"ks.core.subjectcode.usage.one".equals(cell.getValue())){
								useageAllOf = false;
							}
						}
					}
				}

				List<String> units = new ArrayList<String>(course.getUnitsContentOwner());
				if(useageAllOf){
					//Make sure that the course has all the org ids in the found set of org ids
					if(!units.containsAll(orgIds)){
						ValidationResultInfo validationResult = new ValidationResultInfo(getElementXpath(elementStack) + "/" + field.getName());
						validationResult.setWarning(getMessage("validation.course.subjectAreaUsage.all"));
						validationResults.add(validationResult);
					}
				}else{
					//Make sure that the course has only one of the org ids in the set by finding the intersection
					units.retainAll(orgIds);
					if(units.size()!=1){
						ValidationResultInfo validationResult = new ValidationResultInfo(getElementXpath(elementStack) + "/" + field.getName());
						validationResult.setWarning(getMessage("validation.course.subjectAreaUsage.one"));
						validationResults.add(validationResult);
					}
				}
			}
		}
		
		return validationResults;
	}

	public void setSearchDispatcher(SearchDispatcher searchDispatcher) {
		this.searchDispatcher = searchDispatcher;
	}

}
