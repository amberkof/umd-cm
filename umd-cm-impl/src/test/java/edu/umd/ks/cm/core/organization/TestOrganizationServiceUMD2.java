package edu.umd.ks.cm.core.organization;



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

 
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.organization.dto.OrgCodeInfo;
import org.kuali.student.r2.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.r2.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.r2.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.r2.core.organization.dto.OrgTreeInfo;
import org.kuali.student.r2.core.organization.infc.OrgPositionRestriction;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.infc.SearchResult;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:org-test-context.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
public class TestOrganizationServiceUMD2 {
    
    @Resource(name="orgServiceImpl")
    public OrganizationService os;
    
    public static String principalId = "123";
    
    public ContextInfo contextInfo = null;
    
    @Before
    public void setUp() {
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
    }
    
    @Test
    public void testSearchForOrgs() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("id", "1"));
        QueryByCriteria qbc = qbcBuilder.build();
        try {
            
            OrgInfo org = os.getOrg("2216143240",contextInfo);
            assertEquals("UGST-Educational Talent Search",org.getLongName());
            List<String> ancestors = os.getAllAncestors("2216143240", null,contextInfo);
            assertEquals(3,ancestors.size());
            
            SearchRequestInfo sr = new SearchRequestInfo("subjectCode.search.subjectCodeGeneric");
            SearchResult result = os.search(sr,contextInfo);
            assertEquals(283,result.getRows().size());
            
            sr.addParam("subjectCode.queryParam.code", "HESP");
            result = os.search(sr,contextInfo);
            assertEquals(1,result.getRows().size());
            assertEquals("HESP",result.getRows().get(0).getCells().get(0).getValue());
             
            sr = new SearchRequestInfo("subjectCode.search.orgsForSubjectCode");
            sr.addParam("subjectCode.queryParam.code", "HHUM");
            
            result = os.search(sr,contextInfo);
            assertEquals(2,result.getRows().size());
            
            sr = new SearchRequestInfo("org.search.generic");
            result = os.search(sr,contextInfo);
            assertEquals(908,result.getRows().size());

            sr = new SearchRequestInfo("org.search.generic");
            sr.addParam("org.queryParam.orgOptionalLongName", "SVPAAP");
            result = os.search(sr,contextInfo);
            assertEquals(44,result.getRows().size());
            
            sr = new SearchRequestInfo("org.search.generic");
            sr.addParam("org.queryParam.orgOptionalId", "2859809809");
            result = os.search(sr,contextInfo);
            assertEquals(1,result.getRows().size());

            sr = new SearchRequestInfo("org.search.generic");
            List<String> ids = new ArrayList<String>();
            ids.add("1964680419");
            ids.add("1988286422");
            ids.add("2073518245");
            sr.addParam("org.queryParam.orgOptionalIds", ids);
            result = os.search(sr,contextInfo);
            assertEquals(3,result.getRows().size());

            sr = new SearchRequestInfo("org.search.generic");
            sr.addParam("org.queryParam.orgOptionalShortName", "SVPAAP");
            result = os.search(sr,contextInfo);
            assertEquals(2,result.getRows().size());
            
            sr = new SearchRequestInfo("org.search.generic");
            sr.addParam("org.queryParam.startswith.orgOptionalShortName", "SVPAAP");
            result = os.search(sr,contextInfo);
            assertEquals(2,result.getRows().size());
            
            sr = new SearchRequestInfo("org.search.generic");
            sr.addParam("org.queryParam.orgOptionalType", "kuali.org.Department");
            result = os.search(sr,contextInfo);
            assertEquals(420,result.getRows().size());

            sr = new SearchRequestInfo("org.search.orgQuickViewByRelationTypeRelatedOrgTypeOrgId");
            sr.addParam("org.queryParam.orgId", "95634162");
            sr.addParam("org.queryParam.relationType", "foo");
            sr.addParam("org.queryParam.relatedOrgType", "kuali.org.College");
            result = os.search(sr,contextInfo);
            assertEquals(1,result.getRows().size());
            
            sr = new SearchRequestInfo("org.search.orgQuickViewByRelationTypeOrgTypeRelatedOrgIds");
            ids = new ArrayList<String>();
            ids.add("9875092");
            sr.addParam("org.queryParam.relatedOrgIds", ids);
            List<String> types = new ArrayList<String>();
            types.add("org.kuali.College");
            sr.addParam("org.queryParam.optionalOrgTypeList", types);
            result = os.search(sr,contextInfo);
            assertEquals(1,result.getRows().size());
            
             
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }        
    }
    
}
