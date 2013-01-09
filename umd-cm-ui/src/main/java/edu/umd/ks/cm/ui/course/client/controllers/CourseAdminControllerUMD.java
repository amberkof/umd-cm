package edu.umd.ks.cm.ui.course.client.controllers;

import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.event.ActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.mvc.ActionCompleteCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.notification.KSNotification;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseAdminController;
import org.kuali.student.r1.common.assembly.data.QueryPath;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

import edu.umd.ks.cm.ui.course.client.configuration.CourseAdminConfigurerUMD;

public class CourseAdminControllerUMD extends CourseAdminController {

	/**
	 * Processes the save, approve, or approve and activate button clicks. The action is determined
	 * by the value of the state parameter.
	 * 
	 * @param state The state to set on the course when saving course data. DRAFT=Save, APPROVED=Approve, and
	 * ACTIVE=Approve & Activate
	 */
	@Override
	protected void handleButtonClick(final String state){
		
    	//Set state on course before performing save action
		cluProposalModel.set(QueryPath.parse(CreditCourseConstants.STATE), state);
    	
    	final SaveActionEvent saveActionEvent = getSaveActionEvent(state);
    	
    	//Store the rules if save was called
    	if((String)cluProposalModel.get(CreditCourseConstants.ID)!=null && getCourseProposalConfigurer() instanceof CourseAdminConfigurerUMD){
    		((CourseAdminConfigurerUMD )getCourseProposalConfigurer()).getRequisitesSection().storeRules(new Callback<Boolean>(){
    			public void exec(Boolean result) {
					if(result){
						doAdminSaveAction(saveActionEvent, state);
					}else{
						KSNotifier.show("Error saving rules.");
					}
				}
    		});
    	}else{
            doAdminSaveAction(saveActionEvent, state);    		
    	}
	}
	
	private void doAdminSaveAction(final SaveActionEvent saveActionEvent, String state){
		if (DtoConstants.STATE_APPROVED.equalsIgnoreCase(state) || DtoConstants.STATE_ACTIVE.equalsIgnoreCase(state)){
			//For Approved action, validate required fields for next (i.e.Approved) state before firing the save action
			cluProposalModel.validateNextState((new Callback<List<ValidationResultInfo>>() {
	            @Override
	            public void exec(List<ValidationResultInfo> result) {
	
	            	boolean isSectionValid = isValid(result, true);
	
	            	if(isSectionValid){
	            		CourseAdminControllerUMD.this.fireApplicationEvent(saveActionEvent);            	}
	            	else{
	            		KSNotifier.add(new KSNotification("Unable to save, please check fields for errors.", false, true, 5000));
	            	}
	
	            }
	        }));
		} else {
			CourseAdminControllerUMD.this.fireApplicationEvent(saveActionEvent);			
		} 		
	}

	private SaveActionEvent getSaveActionEvent(final String state){
    	final SaveActionEvent saveActionEvent = new SaveActionEvent(false);
		if (DtoConstants.STATE_ACTIVE.equalsIgnoreCase(state)){
    		saveActionEvent.setActionCompleteCallback(new ActionCompleteCallback(){
				@Override
				public void onActionComplete(ActionEvent actionEvent) {
					if (saveActionEvent.isSaveSuccessful()){
						workflowUtil.blanketApprove(new Callback<Boolean>(){
							@Override
							public void exec(Boolean result) {
								
								final ViewContext viewContext = new ViewContext();
				                viewContext.setId((String)cluProposalModel.get(CreditCourseConstants.ID));
				                viewContext.setIdType(IdType.OBJECT_ID);															
								if (DtoConstants.STATE_ACTIVE.equalsIgnoreCase(state)){
									KSNotifier.show("Course approved and activated. It may take a minute or two for course status to be updated. Refresh to see latest status.");
									Application.navigate(AppLocations.Locations.VIEW_COURSE.getLocation(), viewContext);
								}
								
							}
						});
					}      
				}
	    	});
    	} else {
    		//User clicked Save button. When user clicks save, both document upload and cancel should be enabled
    		saveActionEvent.setActionCompleteCallback(new ActionCompleteCallback(){
				@Override
				public void onActionComplete(ActionEvent action) {
					for (KSButton cancelButton:cancelButtons){
						cancelButton.setEnabled(true);
						((CourseAdminConfigurerUMD )cfg).getDocumentTool().beforeShow(NO_OP_CALLBACK);
					}					
				}    			
    		});
    	}
		
		return saveActionEvent;
	}

}
