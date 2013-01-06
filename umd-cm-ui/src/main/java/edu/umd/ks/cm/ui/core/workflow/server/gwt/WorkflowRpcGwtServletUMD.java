package edu.umd.ks.cm.ui.core.workflow.server.gwt;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.core.workflow.ui.server.gwt.WorkflowRpcGwtServlet;

public class WorkflowRpcGwtServletUMD extends WorkflowRpcGwtServlet{

	private static final long serialVersionUID = 1L;
	final Logger LOG = Logger.getLogger(WorkflowRpcGwtServletUMD.class);
	
	@Override
	public List<String> getPreviousRouteNodeNames(String workflowId) throws OperationFailedException {
        try {
            String[] nodeNames = getWorkflowUtilityService().getPreviousRouteNodeNames(Long.parseLong(workflowId));
            if("preRoute".equalsIgnoreCase(nodeNames[0]))
              nodeNames[0] = "Pre-Submission";
            return Arrays.asList(nodeNames);
        } catch (Exception e) {
            LOG.error("Error approving document",e);
            throw new OperationFailedException("Error getting previous node names");
        }
    }
}
