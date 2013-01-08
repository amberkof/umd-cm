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
            /*
            orgService.getOrg("2216143240",contextInfo);
            
            List<OrgInfo> orgInfos = orgService.searchForOrgs(qbc, callContext);
            assertNotNull(orgInfos);
            assertEquals(1, orgInfos.size());
            OrgInfo orgInfo = orgInfos.get(0);
            assertEquals("1", orgInfo.getId());
            assertEquals("KUSystem", orgInfo.getShortName());
            assertEquals("Kuali University System", orgInfo.getLongName());
            assertEquals("", orgInfo.getShortDescr().getPlain());
            assertEquals("", orgInfo.getLongDescr().getPlain());
            assertEquals("kuali.org.CorporateEntity", orgInfo.getTypeKey());

            List<String> orgIds = orgService.searchForOrgIds(qbc, callContext);
            assertNotNull(orgIds);
            assertEquals(1, orgIds.size());
            */
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }        
    }
    
}
