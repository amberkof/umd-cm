package edu.umd.ks.cm.util.audit;

import org.apache.log4j.Logger;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;

import org.kuali.rice.kew.api.*;
import org.kuali.student.common.util.security.SecurityUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class CourseModifyAuditInfoController extends AbstractController {

	final Logger LOG = Logger.getLogger(getClass());

	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
	    String docId = request.getParameter("docId");
	
		
		String principalId = SecurityUtils.getCurrentPrincipalId();

		WorkflowDocument document = new WorkflowDocumentImpl(principalId, Long.decode(docId));		
		String docContent = document.getApplicationContent();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(docContent))); 
		DOMSource src = new DOMSource(doc);

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("courseModifyAuditInfo", src);

		LOG.info("CourseModifyAuditInfoController - In the Controller");
		
		return new ModelAndView("courseModifyAuditInfo", map);			
    */
	    throw new RuntimeException("Commented above out during cm20 upgrade.  I don't think this audit code is being used or is not finished. The call to create a workflow doc should" +
	    		"be passed a doc type, not doc Id.  If I'm wrong, restore class from 1.2.2 and debug.");
	    
	}

}
