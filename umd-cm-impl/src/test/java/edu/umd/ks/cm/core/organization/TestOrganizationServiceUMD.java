package edu.umd.ks.cm.core.organization;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.common.search.dto.SearchResult;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.service.OrganizationService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestOrganizationServiceUMD {

	@Test
	@Ignore
	public void test1() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, NotSupportedException, SystemException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
		        "test-umd-cm-organization-context.xml");
		OrganizationService os = (OrganizationService) ctx.getBean("orgServiceImpl");
		TransactionManager tx  = (TransactionManager) ctx.getBean("jotm");
		tx.begin();
		OrgInfo org = os.getOrganization("2216143240");
		assertEquals("UGST-Educational Talent Search",org.getLongName());
		List<String> ancestors = os.getAllAncestors("2216143240", null);
		assertEquals(3,ancestors.size());
		
		SearchRequest sr = new SearchRequest("subjectCode.search.subjectCodeGeneric");
		SearchResult result = os.search(sr);
		assertEquals(283,result.getRows().size());
		
		sr.addParam("subjectCode.queryParam.code", "HESP");
		result = os.search(sr);
		assertEquals(1,result.getRows().size());
		assertEquals("HESP",result.getRows().get(0).getCells().get(0).getValue());
		 
		sr = new SearchRequest("subjectCode.search.orgsForSubjectCode");
		sr.addParam("subjectCode.queryParam.code", "HHUM");
		
		result = os.search(sr);
		assertEquals(2,result.getRows().size());
		
		sr = new SearchRequest("org.search.generic");
		result = os.search(sr);
		assertEquals(908,result.getRows().size());

		sr = new SearchRequest("org.search.generic");
		sr.addParam("org.queryParam.orgOptionalLongName", "SVPAAP");
		result = os.search(sr);
		assertEquals(44,result.getRows().size());
		
		sr = new SearchRequest("org.search.generic");
		sr.addParam("org.queryParam.orgOptionalId", "2859809809");
		result = os.search(sr);
		assertEquals(1,result.getRows().size());

		sr = new SearchRequest("org.search.generic");
		List<String> ids = new ArrayList<String>();
		ids.add("1964680419");
		ids.add("1988286422");
		ids.add("2073518245");
		sr.addParam("org.queryParam.orgOptionalIds", ids);
		result = os.search(sr);
		assertEquals(3,result.getRows().size());

		sr = new SearchRequest("org.search.generic");
		sr.addParam("org.queryParam.orgOptionalShortName", "SVPAAP");
		result = os.search(sr);
		assertEquals(2,result.getRows().size());
		
		sr = new SearchRequest("org.search.generic");
		sr.addParam("org.queryParam.startswith.orgOptionalShortName", "SVPAAP");
		result = os.search(sr);
		assertEquals(2,result.getRows().size());
		
		sr = new SearchRequest("org.search.generic");
		sr.addParam("org.queryParam.orgOptionalType", "kuali.org.Department");
		result = os.search(sr);
		assertEquals(420,result.getRows().size());

		sr = new SearchRequest("org.search.orgQuickViewByRelationTypeRelatedOrgTypeOrgId");
		sr.addParam("org.queryParam.orgId", "95634162");
		sr.addParam("org.queryParam.relationType", "foo");
		sr.addParam("org.queryParam.relatedOrgType", "kuali.org.College");
		result = os.search(sr);
		assertEquals(1,result.getRows().size());
		
		sr = new SearchRequest("org.search.orgQuickViewByRelationTypeOrgTypeRelatedOrgIds");
		ids = new ArrayList<String>();
		ids.add("9875092");
		sr.addParam("org.queryParam.relatedOrgIds", ids);
		List<String> types = new ArrayList<String>();
		types.add("org.kuali.College");
		sr.addParam("org.queryParam.optionalOrgTypeList", types);
		result = os.search(sr);
		assertEquals(1,result.getRows().size());
		
	}
	
}
