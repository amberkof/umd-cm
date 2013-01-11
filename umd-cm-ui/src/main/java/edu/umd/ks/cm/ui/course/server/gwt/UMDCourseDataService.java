package edu.umd.ks.cm.ui.course.server.gwt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.rice.kew.api.document.DocumentDetail;
import org.kuali.student.lum.lu.ui.course.server.gwt.CourseDataService;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.infc.SearchResult;
import org.kuali.student.r2.core.search.infc.SearchResultCell;
import org.kuali.student.r2.core.search.infc.SearchResultRow;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
/**
 * Overridden COruse data service
 *
 */
public class UMDCourseDataService extends CourseDataService {
	
	private OrganizationService orgService;
	
	/* 
	 * This is overridden so that the department and college are looked up from the clu and passed in as the department and college role qualifiers
	 * (non-Javadoc)
	 * @see org.kuali.student.common.ui.server.gwt.AbstractDataService#addAdditionalAttributes(java.util.Map, org.kuali.student.core.proposal.dto.ProposalInfo, org.kuali.rice.kew.dto.DocumentDetailDTO)
	 */
	@Override
	protected void addAdditionalAttributes(Map<String, String> attributes,
			ProposalInfo proposalInfo, DocumentDetail docDetail) {
		try {
		   //2.0 upgrade - can we use ContextUtils to get context info here?
			CluInfo clu = cluService.getClu(proposalInfo.getProposalReference().get(0),ContextUtils.createDefaultContextInfo());
			if(clu.getAdminOrgs()!=null&&!clu.getAdminOrgs().isEmpty()){
				String deptId = clu.getAdminOrgs().get(0).getOrgId();
				attributes.put("department", deptId);
				attributes.put("college", getCollegeOrgIdFromDeptId(deptId));
			}
			
		} catch (Exception e){
			throw new RuntimeException("Error getting Clu",e);
		}
	}

	//Use Search to find the college from the department
	private String getCollegeOrgIdFromDeptId(String deptId) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {
        if (null != deptId) {
            List<SearchParamInfo> queryParamValues = new ArrayList<SearchParamInfo>(3);
            SearchParamInfo qpRelType = new SearchParamInfo();
            qpRelType.setKey("org.queryParam.relationType");
            qpRelType.getValues().add("kuali.org.CurriculumParent");
            queryParamValues.add(qpRelType);

            SearchParamInfo qpOrgId = new SearchParamInfo();
            qpOrgId.setKey("org.queryParam.orgId");
            qpOrgId.getValues().add(deptId);
            queryParamValues.add(qpOrgId);

            SearchParamInfo qpRelOrgType = new SearchParamInfo();
            qpRelOrgType.setKey("org.queryParam.relatedOrgType");
            qpRelOrgType.getValues().add("kuali.org.College");
            queryParamValues.add(qpRelOrgType);

            SearchRequestInfo searchRequest = new SearchRequestInfo();
            searchRequest.setSearchKey("org.search.orgQuickViewByRelationTypeRelatedOrgTypeOrgId");
            searchRequest.setParams(queryParamValues);
            
            //Perform Search
            SearchResult results = orgService.search(searchRequest,ContextUtils.createDefaultContextInfo());
            
            //Find the org ID in the results
            for (SearchResultRow result : results.getRows()) {
                for (SearchResultCell resultCell : result.getCells()) {
                    if ("org.resultColumn.orgId".equals(resultCell.getKey())) {
                        return resultCell.getValue();
                    }
                }
            }
                
        }
		return null;
	}

	public void setOrgService(OrganizationService orgService) {
		this.orgService = orgService;
	}
}
