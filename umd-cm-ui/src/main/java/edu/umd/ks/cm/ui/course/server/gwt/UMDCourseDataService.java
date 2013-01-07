package edu.umd.ks.cm.ui.course.server.gwt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.rice.kew.dto.DocumentDetailDTO;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.search.dto.SearchParam;
import org.kuali.student.common.search.dto.SearchRequestInfo;
import org.kuali.student.common.search.dto.SearchResult;
import org.kuali.student.common.search.dto.SearchResultCell;
import org.kuali.student.common.search.dto.SearchResultRow;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.ui.course.server.gwt.CourseDataService;
import org.apache.log4j.Logger;
import org.kuali.student.common.ui.server.gwt.AbstractDataService;
import org.kuali.student.core.assembly.transform.ProposalWorkflowFilter;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.clu.CLUConstants;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
			ProposalInfo proposalInfo, DocumentDetailDTO docDetail) {
		try {
			CluInfo clu = luService.getClu(proposalInfo.getProposalReference().get(0));
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
	private String getCollegeOrgIdFromDeptId(String deptId) throws MissingParameterException {
        if (null != deptId) {
            List<SearchParam> queryParamValues = new ArrayList<SearchParam>(3);
            SearchParam qpRelType = new SearchParam();
            qpRelType.setKey("org.queryParam.relationType");
            qpRelType.setValue("kuali.org.CurriculumParent");
            queryParamValues.add(qpRelType);

            SearchParam qpOrgId = new SearchParam();
            qpOrgId.setKey("org.queryParam.orgId");
            qpOrgId.setValue(deptId);
            queryParamValues.add(qpOrgId);

            SearchParam qpRelOrgType = new SearchParam();
            qpRelOrgType.setKey("org.queryParam.relatedOrgType");
            qpRelOrgType.setValue("kuali.org.College");
            queryParamValues.add(qpRelOrgType);

            SearchRequestInfo searchRequest = new SearchRequestInfo();
            searchRequest.setSearchKey("org.search.orgQuickViewByRelationTypeRelatedOrgTypeOrgId");
            searchRequest.setParams(queryParamValues);
            
            //Perform Search
            SearchResult results = orgService.search(searchRequest);
            
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
