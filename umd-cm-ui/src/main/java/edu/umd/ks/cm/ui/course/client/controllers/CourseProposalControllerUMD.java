package edu.umd.ks.cm.ui.course.client.controllers;

import java.util.HashMap;

import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.rice.StudentIdentityConstants;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.shared.IdAttributes;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseProposalController;

public class CourseProposalControllerUMD extends CourseProposalController{

	public void beforeShow(Callback onReadyCallback) {
		//Window.alert("Hello World - Custom UMD Controller!");
		super.beforeShow(onReadyCallback);
	}

    @Override
    public void getMetadataForFinalState(final KSAsyncCallback<Metadata> callback){
		//Setup View Context
		String idType = null;
		String viewContextId = "";
		if(getViewContext().getIdType() != null){
            idType = getViewContext().getIdType().toString();
            viewContextId = getViewContext().getId();
            if(getViewContext().getIdType()==IdAttributes.IdType.COPY_OF_OBJECT_ID){
            	viewContextId = null;
            }
		}
		HashMap<String, String> idAttributes = new HashMap<String, String>();
		if(idType != null){
			idAttributes.put(IdAttributes.ID_TYPE, idType);
		}

		idAttributes.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME, currentDocType);
		idAttributes.put(DtoConstants.DTO_STATE, cfg.getState());		    		
		idAttributes.put(DtoConstants.DTO_NEXT_STATE, cfg.getNextState());
		idAttributes.put(DtoConstants.DTO_WORKFLOW_NODE, "Scheduling Office Processing");
		
		//Get metadata and complete initializing the screen
		getCourseProposalRpcService().getMetadata(viewContextId, idAttributes, new KSAsyncCallback<Metadata>(){
			@Override
			public void onSuccess(Metadata result) {
				callback.onSuccess(result);
			}
		});
    }
    
    public String getCurrentDocType(){
    	return currentDocType;
    }

}
