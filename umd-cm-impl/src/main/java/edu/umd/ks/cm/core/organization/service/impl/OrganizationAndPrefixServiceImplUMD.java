package edu.umd.ks.cm.core.organization.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jws.WebParam;

import org.apache.log4j.Logger;
import org.kuali.student.r1.common.dictionary.old.dto.ObjectStructure;
import org.kuali.student.r1.core.subjectcode.service.SubjectCodeService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.r2.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.r2.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.r2.core.organization.dto.OrgTreeInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.r2.core.search.dto.SortDirection;
import org.kuali.student.r2.core.search.infc.SearchParam;
import org.kuali.student.r2.core.search.infc.SearchResult;
import org.kuali.student.r2.core.search.infc.SearchResultRow;
import org.kuali.student.r2.core.search.service.SearchManager;
import org.kuali.student.r2.core.search.service.SearchService;
import org.springframework.transaction.annotation.Transactional;

import edu.umd.ks.cm.core.organization.dao.OrganizationAndPrefixDaoUMD;
import edu.umd.ks.cm.core.organization.entity.Unit;

public class OrganizationAndPrefixServiceImplUMD implements SubjectCodeService, OrganizationService{

	final Logger logger = Logger.getLogger(OrganizationAndPrefixServiceImplUMD.class);

	private OrganizationAndPrefixDaoUMD dao;
	SearchService searchDispatcher;
	
	////@Override
	public List<TypeInfo> getSearchTypes(ContextInfo contextInfo)
			throws OperationFailedException, InvalidParameterException, MissingParameterException {
		return searchDispatcher.getSearchTypes(contextInfo);
	}

	////@Override
	public TypeInfo getSearchType(String searchTypeKey, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return searchDispatcher.getSearchType(searchTypeKey, contextInfo);
	}
 

	//@Override
	public List<TypeInfo> getSearchTypesByResult(
			String searchResultTypeKey,ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return searchDispatcher.getSearchTypesByResult(searchResultTypeKey,contextInfo);
	}

	//@Override
	public List<TypeInfo> getSearchTypesByCriteria(
			String searchCriteriaTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return searchDispatcher.getSearchTypesByCriteria(searchCriteriaTypeKey);
	}

	//@Override
	public List<SearchResultTypeInfo> getSearchResultTypes()
			throws OperationFailedException {
		return searchDispatcher.getSearchResultTypes();
	}

	//@Override
	public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return searchDispatcher.getSearchResultType(searchResultTypeKey);
	}

	//@Override
	public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes()
			throws OperationFailedException {
		return searchDispatcher.getSearchCriteriaTypes();
	}

	//@Override
	public SearchCriteriaTypeInfo getSearchCriteriaType(
			String searchCriteriaTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return searchDispatcher.getSearchCriteriaType(searchCriteriaTypeKey);
	}
	
	/**
	 * This method creates a search Result object from a searchRequest object, 
	 * a unit's Id and a unit's Short name
	 * @return a constructed search result
	 */
	protected SearchResult createSearchResultFromIdAndShortName(SearchRequestInfo searchRequest, 
			String unitId, String unitShortName){
		
        // Sort column and Direction are probably moot since we are creating a result with one row.
		// but, setting them anyway.
        String sortColumn = searchRequest.getSortColumn();
        SortDirection sortDirection = searchRequest.getSortDirection();
		
        // Create a search result for the return value
        SearchResultInfo searchResult = new SearchResultInfo();
        searchResult.setSortColumn(sortColumn);
        searchResult.setSortDirection(sortDirection);
        
        // Create result Row
        SearchResultRowInfo resultRow = new SearchResultRowInfo();

        // Map the result cells
        resultRow.addCell("org.resultColumn.orgId", unitId);
        resultRow.addCell("org.resultColumn.orgShortName", unitShortName);
        
        // Add row to results
        searchResult.getRows().add(resultRow);
        searchResult.sortRows();
        
        return searchResult;
	}
	
	
	/**
	 * This method is meant to replace a search for a unit's parent college
	 * in the case that the unit is both a dept and college.
	 * For e.g. INFO-College of Information Studies, unit 2376331455 is both college and dept.
	 * In these cases, we want to return information about this unit, not it's parent
	 * (since it's department has no college parent, as they are the same node)
	 * 	 
	 * @param searchRequest - request that contains Child orgId Param
	 * @return null if child Unit is not both a Academic College and a Department.  This will cause
	 *         search method to perform a search for it's parent.
	 *         Constructed SearchResult if child unit is a Academic College and a Department because 
	 *         in these cases, 
	 */
	protected SearchResult createResultIfCollegeIsDept(SearchRequestInfo searchRequest){
		SearchResult searchResults = null;
		String orgId = "";
		
		// Pull out Org ID param
        List<SearchParam> queryParamValues = new ArrayList<SearchParam>(3);            		
        queryParamValues =	searchRequest.getParams();
               
        for (SearchParam searchParam : queryParamValues){
        	if (searchParam.getKey().equals("org.queryParam.orgId")){
        		orgId = (String)searchParam.getValue();
        		continue;
        	}
        }
        
        // Grab child Unit via dao fetch
		Unit childUnit = new Unit();
		try {
			childUnit = dao.fetch(Unit.class, Long.parseLong(orgId));

		} catch (DoesNotExistException e) {
			throw new RuntimeException("Error performing search");
		}  
		
		// If child unit is both an Academic College and a Department, 
		// construct search Result with it and return that, 
		// which will skip an actual search being performed 
		if ((childUnit!=null) && (childUnit.getUmType().equals("Academic College and Department"))){
			   // create Search Result with child Unit's Id and Short Name
		     	searchResults = createSearchResultFromIdAndShortName(searchRequest, String.valueOf(childUnit.getId()), childUnit.getShortName());
		}
		
		return searchResults;
	}

	//@Override
	@Transactional(readOnly=true)
	public SearchResult search(SearchRequestInfo searchRequest, ContextInfo contextInfo)
			throws MissingParameterException {
		
		SearchResult searchResults = null;
		
		if("org.search.orgQuickViewByRelationTypeOrgTypeRelatedOrgIds".equals(searchRequest.getSearchKey())){
			//Do a special hierarchical search for this search type
			searchResults = doOrgHierarchySearch(searchRequest);			
		}else if("org.search.orgQuickViewByRelationTypeRelatedOrgTypeOrgId".equals(searchRequest.getSearchKey())||
				 "org.search.orgQuickViewByRelationTypeOrgTypeRelatedOrgId".equals(searchRequest.getSearchKey())){

			if("org.search.orgQuickViewByRelationTypeRelatedOrgTypeOrgId".equals(searchRequest.getSearchKey())){
			// creates a search result if college is also the department
	        // when looking for a parent college to a dept 
			// (the other search org.search.orgQuickViewByRelationTypeOrgTypeRelatedOrgId goes the other way)
			// this bypasses the actual search later
			searchResults = createResultIfCollegeIsDept(searchRequest);
			}
			
			//Strip out relation type since there is none in UMD
			//Translate org types 
			for (Iterator<SearchParam> iter=searchRequest.getParams().iterator();iter.hasNext();) {
				SearchParam param = iter.next();
				if("org.queryParam.relationType".equals(param.getKey())){
					iter.remove();
				}
			}
		}else if("org.search.generic".equals(searchRequest.getSearchKey())){
			//Translate org types 
			for (SearchParam param:searchRequest.getParams()) {
				if("org.queryParam.orgOptionalType".equals(param.getKey())){
					if(param.getValue() instanceof List){
						if(((List<String>)param.getValue()).contains("kuali.org.Department")
								&&((List<String>)param.getValue()).contains("kuali.org.College")){
							param.setValue("Both");
						}else if(((List<String>)param.getValue()).size()==1){
							param.setValue(((List<String>)param.getValue()).get(0));
						}else{
							param.setValue("Both");
						}
					}
				}
			}
		}
		
		if (searchResults==null){
			searchResults = searchDispatcher.search(searchRequest, dao, contextInfo); 
		}
		return searchResults;
	}

	private SearchResult doOrgHierarchySearch(SearchRequestInfo searchRequest) {
		//get params
        List<String> relatedOrgIds = null;
        List<String> orgTypes = null;
        String orgOptionalId = null;
        String sortColumn = searchRequest.getSortColumn();
        SortDirection sortDirection = searchRequest.getSortDirection();
        
        for (SearchParam param : searchRequest.getParams()) {
            if ("org.queryParam.relatedOrgIds".equals(param.getKey())) {
                relatedOrgIds = (List<String>) param.getValues();
                continue;
            } else if ("org.queryParam.optionalOrgTypeList".equals(param.getKey())) {
            	//Translate some org types
                orgTypes = (List<String>) param.getValues();
                if(orgTypes==null){
                	orgTypes = new ArrayList<String>();
                }
                continue;
            } else if ("org.queryParam.relOrgOptionalId".equals(param.getKey())) {
                orgOptionalId = null;
                for (String v : param.getValues()) {
                    orgOptionalId = v;  // expect single value
                }
                 continue;
            }
        }
        try {
            List<Unit> units = null;
            if (orgOptionalId != null) {
                units = new ArrayList<Unit>();
                units.add((Unit)dao.fetch(Unit.class, Long.parseLong(orgOptionalId)));
            } else {
        		List<Long> idListLong = new ArrayList<Long>();
        		if(relatedOrgIds!=null){
        			for(String id:relatedOrgIds){
        				if(id!=null && !id.isEmpty()){
        					idListLong.add(Long.parseLong(id));
        				}
        			}
        		}
            	units = dao.getAllAncestorsOfType(idListLong, orgTypes);
            }

            // Create a search result for the return value
            SearchResultInfo searchResult = new SearchResultInfo();
            searchResult.setSortColumn(sortColumn);
            searchResult.setSortDirection(sortDirection);
            for (Unit unit : units) {
                SearchResultRowInfo resultRow = new SearchResultRowInfo();

                // Map the result cells
                resultRow.addCell("org.resultColumn.orgId", unit.getId().toString());
                resultRow.addCell("org.resultColumn.orgShortName", unit.getShortName());
                resultRow.addCell("org.resultColumn.orgOptionalLongName", unit.getLongName());
                resultRow.addCell("org.resultColumn.orgType", unit.getType());

                searchResult.getRows().add(resultRow);
            }
            
            searchResult.sortRows();
            
            return searchResult;
        } catch (DoesNotExistException e) {
            throw new RuntimeException("Error performing search");
        }

	}

	//@Override
	public List<String> getObjectTypes() {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public ObjectStructure getObjectStructure(String objectTypeKey) {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public List<OrgHierarchyInfo> getOrgHierarchies()
			throws OperationFailedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public OrgHierarchyInfo getOrgHierarchy(String orgHierarchyKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public List<TypeInfo> getOrgTypes() throws OperationFailedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public TypeInfo getOrgType(String orgTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public List<OrgOrgRelationTypeInfo> getOrgOrgRelationTypes()
			throws OperationFailedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public OrgOrgRelationTypeInfo getOrgOrgRelationType(
			String orgOrgRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public List<OrgOrgRelationTypeInfo> getOrgOrgRelationTypesForOrgType(
			String orgTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public List<OrgOrgRelationTypeInfo> getOrgOrgRelationTypesForOrgHierarchy(
			String orgHierarchyKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public List<OrgPersonRelationTypeInfo> getOrgPersonRelationTypes()
			throws OperationFailedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public OrgPersonRelationTypeInfo getOrgPersonRelationType(
			String orgPersonRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public List<OrgPersonRelationTypeInfo> getOrgPersonRelationTypesForOrgType(
			String orgTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public List<ValidationResultInfo> validateOrg(String validationType,
			OrgInfo orgInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public List<ValidationResultInfo> validateOrgOrgRelation(
			String validationType, OrgOrgRelationInfo orgOrgRelationInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public List<ValidationResultInfo> validateOrgPersonRelation(
			String validationType, OrgPersonRelationInfo orgPersonRelationInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public List<ValidationResultInfo> validateOrgPositionRestriction(
			String validationType,
			OrgPositionRestrictionInfo orgPositionRestrictionInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	@Transactional(readOnly=true)
	public OrgInfo getOrganization(String orgId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		Unit unit = dao.fetch(Unit.class,Long.parseLong(orgId));
		return toOrgInfo(unit);
	}

	//@Override
	@Transactional(readOnly=true)
	public List<OrgInfo> getOrganizationsByIdList(List<String> orgIdList)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<Long> idListLong = new ArrayList<Long>();
		if(orgIdList!=null){
			for(String id:orgIdList){
				idListLong.add(Long.parseLong(id));
			}
		}
		return toOrgInfoList(dao.getOrganizationsByIdList(idListLong));
	}

	private List<OrgInfo> toOrgInfoList(List<Unit> units) {
		List<OrgInfo> orgInfos = new ArrayList<OrgInfo>();
		if(units!=null){
			for(Unit unit:units){
				orgInfos.add(toOrgInfo(unit));
			}
		}
		return orgInfos;
	}

	private OrgInfo toOrgInfo(Unit unit) {
		OrgInfo orgInfo = new OrgInfo();
		orgInfo.setId(unit.getId().toString());
		orgInfo.setLongName(unit.getLongName());
		orgInfo.setSortName(unit.getShortName());
		orgInfo.setType(unit.getType());
		orgInfo.setEffectiveDate(unit.getStartDate());
		orgInfo.setExpirationDate(unit.getEndDate());
		
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("umType", unit.getUmType());
		orgInfo.setAttributes(attributes);
		
		return orgInfo;
	}

	//@Override
	public OrgOrgRelationInfo getOrgOrgRelation(String orgOrgRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public List<OrgOrgRelationInfo> getOrgOrgRelationsByIdList(
			List<String> orgOrgRelationIdList) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public List<OrgOrgRelationInfo> getOrgOrgRelationsByOrg(String orgId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public List<OrgOrgRelationInfo> getOrgOrgRelationsByRelatedOrg(
			String relatedOrgId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public Boolean hasOrgOrgRelation(String orgId, String comparisonOrgId,
			String orgOrgRelationTypeKey) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public Boolean isDescendant(String orgId, String descendantOrgId,
			String orgHierarchy) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public List<String> getAllDescendants(String orgId, String orgHierarchy)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	@Transactional(readOnly=true)
	public List<String> getAllAncestors(String orgId, String orgHierarchy)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		List<Long> results = dao.getAllAncestors(Long.parseLong(orgId), orgHierarchy);
		List<String> ancestors = new ArrayList<String>();
		if(results!=null){
			for(Long result:results){
				ancestors.add(result.toString());
			}
		}
				
		return ancestors;
	}

	//@Override
	public OrgPersonRelationInfo getOrgPersonRelation(String orgPersonRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public List<OrgPersonRelationInfo> getOrgPersonRelationsByIdList(
			List<String> orgPersonRelationIdList) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public List<String> getPersonIdsForOrgByRelationType(String orgId,
			String orgPersonRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public List<OrgPersonRelationInfo> getOrgPersonRelationsByOrg(String orgId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public List<OrgPersonRelationInfo> getOrgPersonRelationsByPerson(
			String personId, String orgId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public List<OrgPersonRelationInfo> getAllOrgPersonRelationsByPerson(
			String personId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public List<OrgPersonRelationInfo> getAllOrgPersonRelationsByOrg(
			String orgId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public Boolean hasOrgPersonRelation(String orgId, String personId,
			String orgPersonRelationTypeKey) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public List<OrgPositionRestrictionInfo> getPositionRestrictionsByOrg(
			String orgId) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, PermissionDeniedException,
			OperationFailedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public OrgInfo createOrganization(String orgTypeKey, OrgInfo orgInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public OrgInfo updateOrganization(String orgId, OrgInfo orgInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public StatusInfo deleteOrganization(String orgId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public OrgOrgRelationInfo createOrgOrgRelation(String orgId,
			String relatedOrgId, String orgOrgRelationTypeKey,
			OrgOrgRelationInfo orgOrgRelationInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, PermissionDeniedException,
			OperationFailedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public OrgOrgRelationInfo updateOrgOrgRelation(String orgOrgRelationId,
			OrgOrgRelationInfo orgOrgRelationInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public StatusInfo removeOrgOrgRelation(String orgOrgRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public OrgPersonRelationInfo createOrgPersonRelation(String orgId,
			String personId, String orgPersonRelationTypeKey,
			OrgPersonRelationInfo orgPersonRelationInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, PermissionDeniedException,
			OperationFailedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public OrgPersonRelationInfo updateOrgPersonRelation(
			String orgPersonRelationId,
			OrgPersonRelationInfo orgPersonRelationInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public StatusInfo removeOrgPersonRelation(String orgPersonRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public OrgPositionRestrictionInfo addPositionRestrictionToOrg(String orgId,
			String orgPersonRelationTypeKey,
			OrgPositionRestrictionInfo orgPositionRestrictionInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public OrgPositionRestrictionInfo updatePositionRestrictionForOrg(
			String orgId, String orgPersonRelationTypeKey,
			OrgPositionRestrictionInfo orgPositionRestrictionInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public StatusInfo removePositionRestrictionFromOrg(String orgId,
			String orgPersonRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	//@Override
	public List<OrgTreeInfo> getOrgTree(String rootOrgId,
			String orgHierarchyId, int maxLevels) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		throw new UnsupportedOperationException("This implementation does not support this operation");
	}

	public void setDao(OrganizationAndPrefixDaoUMD dao) {
		this.dao = dao;
	}

	public void setSearchManager(SearchService searchDispatcher) {
		this.searchDispatcher = searchDispatcher;
	}

}
