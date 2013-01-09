package edu.umd.ks.cm.ui.core.workflow.client.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.service.SecurityRpcService;
import org.kuali.student.common.ui.client.service.SecurityRpcServiceAsync;
import org.kuali.student.core.workflow.ui.client.views.CollaboratorSectionView;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.r1.common.rice.StudentWorkflowConstants.ActionRequestType;
import org.kuali.student.r1.common.rice.authorization.PermissionType;

import com.google.gwt.core.client.GWT;

import edu.umd.ks.cm.ui.course.client.controllers.CourseProposalControllerUMD;

public class CollaboratorSectionViewUMD extends CollaboratorSectionView{
    
    protected SecurityRpcServiceAsync securityRpcService = GWT.create(SecurityRpcService.class);
	private ArrayList<String> documentActionPermissions;
	public static final String ADD_COLLABORATOR_WITH_FYI = "Add Collaborator with FYI";
	public static final String ADD_COLLABORATOR_WITH_ACKNOWLEDGE = "Add Collaborator with Acknowledge";
	public static final String ADD_COLLABORATOR_WITH_APPROVE = "Add Collaborator with Approve";

	public CollaboratorSectionViewUMD(){
    	
    } 
	public CollaboratorSectionViewUMD(Enum<?> viewEnum, String name,
			String modelId) {
		super(viewEnum, name, modelId);
	}
   
	public void beforeShow(final Callback<Boolean> onReadyCallback) {
		super.beforeShow(onReadyCallback);
	}
	
	public void init(){
		super.init();
		authorNotation.getFieldWidget().setVisible(false);
		authorNotation.hideLabel();
	}
	@Override
    protected void refreshDocumentStatus(final Callback<Boolean> onReadyCallback) {
		
        workflowRpcServiceAsync.getDocumentStatus(workflowId, new KSAsyncCallback<String>() {
            @Override
            public void handleFailure(Throwable caught) {
                documentStatus = null;
                checkAuthorization(onReadyCallback);
            }

            @Override
            public void onSuccess(final String result) {
            	if(documentActionPermissions==null){
            		HashMap<String,String> attributes = new HashMap<String,String>();
            		addPermissionAttributes(attributes);
	            	securityRpcService.getPermissionsByType(PermissionType.ADD_COLLABORATOR_ACTION, attributes, new KSAsyncCallback<ArrayList<String>>() {
						@Override
						public void onSuccess(ArrayList<String> permissions) {
			                documentStatus = result;
							documentActionPermissions = permissions;
			                refreshActionRequestListItems();
			                checkAuthorization(onReadyCallback);
						}
					});
            	}else{
                    documentStatus = result;
                    refreshActionRequestListItems();
                    checkAuthorization(onReadyCallback);
            	}
            }
        });
    }
	
	/**
	 * This method adds any permission attributes required for checking proposal-creation/opening permissions.
	 */
	public void addPermissionAttributes(Map<String, String> attributes){
		ViewContext viewContext = getLayoutController().getViewContext();

		//Get the id to use to check permissions, this could either be the proposal id or the workflow document id,
		//will pass the id & id type as attributes to permission service.
		if ( (viewContext.getId() != null) && (!"".equals(viewContext.getId())) ) {
			attributes.put(viewContext.getIdType().toString(), viewContext.getId());
		}
		
		// Determine DocType to Determine the permission type being checked
		// Load Attributes with this information.
        String currentDocType = ((CourseProposalControllerUMD)layoutController).getCurrentDocType();
		
      
    	if(viewContext.getId() != null && !viewContext.getId().isEmpty()){
    		if (currentDocType.equals(LUConstants.PROPOSAL_TYPE_COURSE_CREATE))   
    	        {
    			attributes.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME, LUConstants.PROPOSAL_TYPE_COURSE_CREATE);
    		} else if (currentDocType.equals(LUConstants.PROPOSAL_TYPE_COURSE_MODIFY)){
    			//Copy id provided, so creating a proposal for modification
    			attributes.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME, LUConstants.PROPOSAL_TYPE_COURSE_MODIFY);
    		}
    	} else{
    		//No id in view context, so creating new empty proposal
			attributes.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME, LUConstants.PROPOSAL_TYPE_COURSE_CREATE);    		
    	}    	
	}
	
	@Override
    protected void refreshActionRequestListItems() {
        actionRequestListItems.clear();
        
        if(documentActionPermissions!=null){
	        if(documentActionPermissions.contains(ADD_COLLABORATOR_WITH_FYI)){
	        	actionRequestListItems.addItem(ActionRequestType.FYI.getActionRequestCode(), ActionRequestType.FYI.getActionRequestLabel());
	        }
	        if(!isDocumentPreRoute()){
		        if(documentActionPermissions.contains(ADD_COLLABORATOR_WITH_APPROVE)){
		        	actionRequestListItems.addItem(ActionRequestType.APPROVE.getActionRequestCode(), ActionRequestType.APPROVE.getActionRequestLabel());
		        }
		        if(documentActionPermissions.contains(ADD_COLLABORATOR_WITH_ACKNOWLEDGE)){
		        	actionRequestListItems.addItem(ActionRequestType.ACKNOWLEDGE.getActionRequestCode(), ActionRequestType.ACKNOWLEDGE.getActionRequestLabel());
		        }
	        }
        }
        
        actionRequestList.setListItems(actionRequestListItems);
        refreshPermissionList(ActionRequestType.FYI.getActionRequestCode());
    }

	@Override
	protected void refreshPermissionList(String selectedAction) {
        permissionListItems.clear();
        
        permissionListItems.addItem(PermissionType.EDIT.getCode(), "Edit, Comment, View");
        permissionListItems.addItem(PermissionType.ADD_COMMENT.getCode(), "Comment, View");
        permissionListItems.addItem(PermissionType.OPEN.getCode(), "View");

        permissionList.setListItems(permissionListItems);
    }
}
