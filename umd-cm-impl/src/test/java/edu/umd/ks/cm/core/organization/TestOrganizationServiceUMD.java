package edu.umd.ks.cm.core.organization;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
 
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.infc.SearchResult;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestOrganizationServiceUMD {

    
    
	@Test
	//@Ignore
	public void test1() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, NotSupportedException, SystemException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
		        "test-umd-cm-organization-context.xml");
		OrganizationService os = (OrganizationService) ctx.getBean("orgServiceImpl");
		TransactionManager tx  = (TransactionManager) ctx.getBean("jotm");
		ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
		tx.begin();
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
		
	}
	
}
