package edu.umd.ks.cm.core.organization.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jws.WebParam;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r1.core.subjectcode.service.SubjectCodeService;
import org.kuali.student.r2.common.class1.search.SearchManagerImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.r2.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.r2.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.r2.core.organization.dto.OrgTreeInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.dto.SortDirection;
import org.kuali.student.r2.core.search.infc.SearchParam;
import org.springframework.transaction.annotation.Transactional;

import edu.umd.ks.cm.core.organization.dao.OrganizationAndPrefixDaoUMD;
import edu.umd.ks.cm.core.organization.entity.Unit;
import edu.umd.ks.cm.course.service.utils.CM20;

public class OrganizationAndPrefixServiceImplUMD implements SubjectCodeService, OrganizationService {

    final Logger logger = Logger.getLogger(OrganizationAndPrefixServiceImplUMD.class);

    private OrganizationAndPrefixDaoUMD dao;
    SearchManagerImpl searchDispatcher;
    TypeService typeService;

    @Override
    public List<TypeInfo> getSearchTypes(ContextInfo contextInfo) throws OperationFailedException,
            InvalidParameterException, MissingParameterException {
        return searchDispatcher.getSearchTypes(contextInfo);
    }

    @Override
    public TypeInfo getSearchType(String searchTypeKey, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return searchDispatcher.getSearchType(searchTypeKey, contextInfo);
    }

    /**
     * This method creates a search Result object from a searchRequest object, a
     * unit's Id and a unit's Short name
     * 
     * @return a constructed search result
     */
    protected SearchResultInfo createSearchResultFromIdAndShortName(SearchRequestInfo searchRequest, String unitId,
            String unitShortName) {

        // Sort column and Direction are probably moot since we are creating a
        // result with one row.
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
     * This method is meant to replace a search for a unit's parent college in
     * the case that the unit is both a dept and college. For e.g. INFO-College
     * of Information Studies, unit 2376331455 is both college and dept. In
     * these cases, we want to return information about this unit, not it's
     * parent (since it's department has no college parent, as they are the same
     * node)
     * 
     * @param searchRequest
     *            - request that contains Child orgId Param
     * @return null if child Unit is not both a Academic College and a
     *         Department. This will cause search method to perform a search for
     *         it's parent. Constructed SearchResult if child unit is a Academic
     *         College and a Department because in these cases,
     */
    protected SearchResultInfo createResultIfCollegeIsDept(SearchRequestInfo searchRequest) {
        SearchResultInfo searchResults = null;
        String orgId = "";

        // Pull out Org ID param
        List<SearchParamInfo> queryParamValues = new ArrayList<SearchParamInfo>(3);
        queryParamValues = searchRequest.getParams();

        for (SearchParamInfo searchParam : queryParamValues) {
            if (searchParam.getKey().equals("org.queryParam.orgId")) {
                orgId = (String) CM20.getFirstValueFromSearchParam(searchParam);
                continue;
            }
        }

        // Grab child Unit via dao fetch
        Unit childUnit = new Unit();
        try {
            childUnit = (Unit) dao.fetch(Unit.class, orgId);

        } catch (DoesNotExistException e) {
            throw new RuntimeException("Error performing search");
        }

        // If child unit is both an Academic College and a Department,
        // construct search Result with it and return that,
        // which will skip an actual search being performed
        if ((childUnit != null) && (childUnit.getUmType().equals("Academic College and Department"))) {
            // create Search Result with child Unit's Id and Short Name
            searchResults = createSearchResultFromIdAndShortName(searchRequest, String.valueOf(childUnit.getId()),
                    childUnit.getShortName());
        }

        return searchResults;
    }

    @Override
    @Transactional(readOnly = true)
    public SearchResultInfo search(SearchRequestInfo searchRequest, ContextInfo contextInfo)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {

        SearchResultInfo searchResults = null;

        if ("org.search.orgQuickViewByRelationTypeOrgTypeRelatedOrgIds".equals(searchRequest.getSearchKey())) {
            // Do a special hierarchical search for this search type
            searchResults = doOrgHierarchySearch(searchRequest);
        } else if ("org.search.orgQuickViewByRelationTypeRelatedOrgTypeOrgId".equals(searchRequest.getSearchKey())
                || "org.search.orgQuickViewByRelationTypeOrgTypeRelatedOrgId".equals(searchRequest.getSearchKey())) {

            if ("org.search.orgQuickViewByRelationTypeRelatedOrgTypeOrgId".equals(searchRequest.getSearchKey())) {
                // creates a search result if college is also the department
                // when looking for a parent college to a dept
                // (the other search
                // org.search.orgQuickViewByRelationTypeOrgTypeRelatedOrgId goes
                // the other way)
                // this bypasses the actual search later
                searchResults = createResultIfCollegeIsDept(searchRequest);
            }

            // Strip out relation type since there is none in UMD
            // Translate org types
            for (Iterator<SearchParamInfo> iter = searchRequest.getParams().iterator(); iter.hasNext();) {
                SearchParamInfo param = iter.next();
                if ("org.queryParam.relationType".equals(param.getKey())) {
                    iter.remove();
                }
            }
        } else if ("org.search.generic".equals(searchRequest.getSearchKey())) {
            // Translate org types
            for (SearchParamInfo param : searchRequest.getParams()) {
                if ("org.queryParam.orgOptionalType".equals(param.getKey())) {
                    
                    List<String> vals = param.getValues();
                    if (vals.contains("kuali.org.Department") && vals.contains("kuali.org.Department")){
                       List<String> newVals = new ArrayList<String>();
                       newVals.add("Both");
                       param.setValues(newVals);
                     }
                    else if (param.getValues().size() == 1){
                        List<String> newVals = new ArrayList<String>();
                        newVals.add(param.getValues().get(0));
                        param.setValues(newVals);
                    }else{
                        List<String> newVals = new ArrayList<String>();
                        newVals.add("Both");
                        param.setValues(newVals);
                    }
                    //TODO: verify this fix works
                    
                    /*
                    if (param.getValue() instanceof List) {
                        if (((List<String>) param.getValue()).contains("kuali.org.Department")
                                && ((List<String>) param.getValue()).contains("kuali.org.College")) {
                            param.setValue("Both");
                        } else if (((List<String>) param.getValue()).size() == 1) {
                            param.setValue(((List<String>) param.getValue()).get(0));
                        } else {
                            param.setValue("Both");
                        }
                    }
                    */
                }
            }
        }

        if (searchResults == null) {
            //TODO: we should be able to just drop the ref to dao as was done in LO service
            searchResults = searchDispatcher.search(searchRequest,  contextInfo);
        }
        return searchResults;
    }

    private SearchResultInfo doOrgHierarchySearch(SearchRequestInfo searchRequest) {
        // get params
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
                // Translate some org types
                orgTypes = (List<String>) param.getValues();
                if (orgTypes == null) {
                    orgTypes = new ArrayList<String>();
                }
                continue;
            } else if ("org.queryParam.relOrgOptionalId".equals(param.getKey())) {
                orgOptionalId = null;
                for (String v : param.getValues()) {
                    orgOptionalId = v; // expect single value
                }
                continue;
            }
        }
        try {
            List<Unit> units = null;
            if (orgOptionalId != null) {
                units = new ArrayList<Unit>();
                units.add((Unit) dao.fetch(Unit.class,  orgOptionalId));
            } else {
                List<Long> idListLong = new ArrayList<Long>();
                if (relatedOrgIds != null) {
                    for (String id : relatedOrgIds) {
                        if (id != null && !id.isEmpty()) {
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

    @Override
    @Transactional(readOnly = true)
    public OrgInfo getOrg(String orgId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        Unit unit = (Unit) dao.fetch(Unit.class,  orgId );
        return toOrgInfo(unit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrgInfo> getOrgsByIds(List<String> orgIdList, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<Long> idListLong = new ArrayList<Long>();
        if (orgIdList != null) {
            for (String id : orgIdList) {
                idListLong.add(Long.parseLong(id));
            }
        }
        return toOrgInfoList(dao.getOrganizationsByIdList(idListLong));
    }

    private List<OrgInfo> toOrgInfoList(List<Unit> units) {
        List<OrgInfo> orgInfos = new ArrayList<OrgInfo>();
        if (units != null) {
            for (Unit unit : units) {
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
        orgInfo.setAttributes(CM20.mapToAttributeInfo(attributes));

        return orgInfo;
    }
 
    @Override
    @Transactional(readOnly = true)
    public List<String> getAllAncestors(String orgId, String orgHierarchyId, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<Long> results = dao.getAllAncestors(Long.parseLong(orgId), orgHierarchyId);
        List<String> ancestors = new ArrayList<String>();
        if (results != null) {
            for (Long result : results) {
                ancestors.add(result.toString());
            }
        }

        return ancestors;
    }

    public OrgHierarchyInfo getOrgHierarchy(@WebParam(name = "orgHierarchyId") String orgHierarchyId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<OrgHierarchyInfo> getOrgHierarchiesByIds(
            @WebParam(name = "orgHierarchyIds") List<String> orgHierarchyIds,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<String> getOrgHierarchyIdsByType(@WebParam(name = "orgHierarchyTypeKey") String orgHierarchyTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<OrgHierarchyInfo> getOrgHierarchies(@WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<String> searchForOrgHierarchyIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<OrgHierarchyInfo> searchForOrgHierarchies(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<TypeInfo> getOrgTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

  
    public List<String> getOrgIdsByType(@WebParam(name = "orgTypeKey") String orgTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<String> searchForOrgIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<OrgInfo> searchForOrgs(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<ValidationResultInfo> validateOrg(@WebParam(name = "validationTypeKey") String validationTypeKey,
            @WebParam(name = "orgTypeKey") String orgTypeKey, @WebParam(name = "orgInfo") OrgInfo orgInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public OrgInfo createOrg(@WebParam(name = "orgTypeKey") String orgTypeKey,
            @WebParam(name = "orgInfo") OrgInfo orgInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public OrgInfo updateOrg(@WebParam(name = "orgId") String orgId, @WebParam(name = "orgInfo") OrgInfo orgInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public StatusInfo deleteOrg(@WebParam(name = "orgId") String orgId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<TypeInfo> getOrgOrgRelationTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<TypeInfo> getOrgOrgRelationTypesForOrgType(@WebParam(name = "orgTypeKey") String orgTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    @Deprecated
    public TypeInfo getOrgOrgRelationTypeForOrgType(@WebParam(name = "orgTypeKey") String orgTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<TypeInfo> getOrgOrgRelationTypesForOrgHierarchy(
            @WebParam(name = "orgHierarchyId") String orgHierarchyId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public Boolean hasOrgOrgRelation(@WebParam(name = "orgId") String orgId,
            @WebParam(name = "comparisonOrgId") String comparisonOrgId,
            @WebParam(name = "orgOrgRelationTypeKey") String orgOrgRelationTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public OrgOrgRelationInfo getOrgOrgRelation(@WebParam(name = "orgOrgRelationId") String orgOrgRelationId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<OrgOrgRelationInfo> getOrgOrgRelationsByIds(
            @WebParam(name = "orgOrgRelationIds") List<String> orgOrgRelationIds,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<String> getOrgOrgRelationIdsByType(
            @WebParam(name = "orgOrgRelationTypeKey") String orgOrgRelationTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<OrgOrgRelationInfo> getOrgOrgRelationsByOrg(@WebParam(name = "orgId") String orgId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<OrgOrgRelationInfo> getOrgOrgRelationsByOrgs(@WebParam(name = "orgId") String orgId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<OrgOrgRelationInfo> getOrgOrgRelationsByTypeAndOrg(@WebParam(name = "orgId") String orgId,
            @WebParam(name = "orgOrgRelationTypeKey") String orgOrgRelationTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<String> searchForOrgOrgRelationIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<OrgOrgRelationInfo> searchForOrgOrgRelations(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<ValidationResultInfo> validateOrgOrgRelation(
            @WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "orgId") String orgId,
            @WebParam(name = "orgPeerId") String orgPeerId,
            @WebParam(name = "orgOrgrelationTypeKey") String orgOrgRelationTypeKey,
            @WebParam(name = "orgOrgRelationInfo") OrgOrgRelationInfo orgOrgRelationInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public OrgOrgRelationInfo createOrgOrgRelation(@WebParam(name = "orgId") String orgId,
            @WebParam(name = "orgPeerId") String orgPeerId,
            @WebParam(name = "orgOrgRelationTypeKey") String orgOrgRelationTypeKey,
            @WebParam(name = "orgOrgRelationInfo") OrgOrgRelationInfo orgOrgRelationInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public OrgOrgRelationInfo updateOrgOrgRelation(@WebParam(name = "orgOrgRelationId") String orgOrgRelationId,
            @WebParam(name = "orgOrgRelationInfo") OrgOrgRelationInfo orgOrgRelationInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public StatusInfo deleteOrgOrgRelation(@WebParam(name = "orgOrgRelationId") String orgOrgRelationId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<TypeInfo> getOrgPersonRelationTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<TypeInfo> getOrgPersonRelationTypesForOrgType(@WebParam(name = "orgTypeKey") String orgTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public Boolean hasOrgPersonRelation(@WebParam(name = "orgId") String orgId,
            @WebParam(name = "personId") String personId,
            @WebParam(name = "orgPersonRelationTypeKey") String orgPersonRelationTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public OrgPersonRelationInfo getOrgPersonRelation(
            @WebParam(name = "orgPersonRelationId") String orgPersonRelationId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<OrgPersonRelationInfo> getOrgPersonRelationsByIds(
            @WebParam(name = "orgPersonRelationIds") List<String> orgPersonRelationIds,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<String> getOrgPersonRelationIdsByType(
            @WebParam(name = "orgPersonRelationTypeKey") String orgPersonRelationTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<OrgPersonRelationInfo> getOrgPersonRelationsByOrg(@WebParam(name = "orgId") String orgId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<OrgPersonRelationInfo> getOrgPersonRelationsByTypeAndOrg(
            @WebParam(name = "orgPersonRelationTypeKey") String orgPersonRelationTypeKey,
            @WebParam(name = "orgId") String orgId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    @Deprecated
    public OrgPersonRelationInfo getOrgPersonRelationByTypeAndOrg(
            @WebParam(name = "orgPersonRelationTypeKey") String orgPersonRelationTypeKey,
            @WebParam(name = "orgId") String orgId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<OrgPersonRelationInfo> getOrgPersonRelationsByPerson(@WebParam(name = "personId") String personId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<OrgPersonRelationInfo> getOrgPersonRelationsByTypeAndPerson(
            @WebParam(name = "orgPersonRelationTypeKey") String orgPersonRelationTypeKey,
            @WebParam(name = "personId") String personId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<OrgPersonRelationInfo> getOrgPersonRelationsByOrgAndPerson(@WebParam(name = "orgId") String orgId,
            @WebParam(name = "personId") String personId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<OrgPersonRelationInfo> getOrgPersonRelationsByTypeAndOrgAndPerson(
            @WebParam(name = "orgPersonRelationTypeKey") String orgPersonRelationTypeKey,
            @WebParam(name = "orgId") String orgId, @WebParam(name = "personId") String personId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<String> searchForOrgPersonRelationIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<OrgPersonRelationInfo> searchForOrgPersonRelations(
            @WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<ValidationResultInfo> validateOrgPersonRelation(
            @WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "orgId") String orgId,
            @WebParam(name = "personId") String personId,
            @WebParam(name = "orgPersonrelationTypeKey") String orgPersonRelationTypeKey,
            @WebParam(name = "orgPersonRelationInfo") OrgPersonRelationInfo orgPersonRelationInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public OrgPersonRelationInfo createOrgPersonRelation(@WebParam(name = "orgId") String orgId,
            @WebParam(name = "personId") String personId,
            @WebParam(name = "orgPersonRelationTypeKey") String orgPersonRelationTypeKey,
            @WebParam(name = "orgPersonRelationInfo") OrgPersonRelationInfo orgPersonRelationInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public OrgPersonRelationInfo updateOrgPersonRelation(
            @WebParam(name = "orgPersonRelationId") String orgPersonRelationId,
            @WebParam(name = "orgPersonRelationInfo") OrgPersonRelationInfo orgPersonRelationInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public StatusInfo deleteOrgPersonRelation(@WebParam(name = "orgPersonRelationId") String orgPersonRelationId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public OrgPositionRestrictionInfo getOrgPositionRestriction(
            @WebParam(name = "orgPositionRestrictionId") String orgPositionRestrictionId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<OrgPositionRestrictionInfo> getOrgPositionRestrictionsByIds(
            @WebParam(name = "orgPositionRestrictionIds") List<String> orgPositionRestrictionIds,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<String> getOrgPositionRestrictionIdsByType(
            @WebParam(name = "orgPersonRelationTypeKey") String orgPersonRelationTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<String> getOrgPositionRestrictionIdsByOrg(@WebParam(name = "orgId") String orgId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<String> searchForOrgPositionRestrictionIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<OrgPositionRestrictionInfo> searchForOrgPositionRestrictions(
            @WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<ValidationResultInfo> validateOrgPositionRestriction(
            @WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "orgId") String orgId,
            @WebParam(name = "orgPersonRelationTypeKey") String orgPersonRelationTypeKey,
            @WebParam(name = "orgPositionRestrictionInfo") OrgPositionRestrictionInfo orgPositionRestrictionInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public OrgPositionRestrictionInfo createOrgPositionRestriction(@WebParam(name = "orgId") String orgId,
            @WebParam(name = "orgPersonRelationTypeKey") String orgPersonRelationTypeKey,
            @WebParam(name = "orgPositionRestrictionInfo") OrgPositionRestrictionInfo orgPositionRestrictionInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public OrgPositionRestrictionInfo updateOrgPositionRestriction(
            @WebParam(name = "orgPositionRestrictionId") String orgPositionRestrictionId,
            @WebParam(name = "orgPositionRestrictionInfo") OrgPositionRestrictionInfo orgPositionRestrictionInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public StatusInfo deleteOrgPositionRestriction(
            @WebParam(name = "orgPositionRestrictionId") String orgPositionRestrictionId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public Boolean isDescendant(@WebParam(name = "orgId") String orgId,
            @WebParam(name = "descendantOrgId") String descendantOrgId,
            @WebParam(name = "orgHierarchyId") String orgHierarchyId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public List<String> getAllDescendants(@WebParam(name = "orgId") String orgId,
            @WebParam(name = "orgHierarchyId") String orgHierarchyId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    } 

    public List<OrgTreeInfo> getOrgTree(@WebParam(name = "rootOrgId") String rootOrgId,
            @WebParam(name = "orgHierarchyId") String orgHierarchyId, @WebParam(name = "maxLevels") int maxLevels,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO cmann - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException("This implementation does not support this operation");
    }

    public void setDao(OrganizationAndPrefixDaoUMD dao) {
        this.dao = dao;
    }

    public void setSearchManager(SearchManagerImpl searchDispatcher) {
        this.searchDispatcher = searchDispatcher;
    }

}
