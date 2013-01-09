package edu.umd.ks.cm.ui.course.client.controllers;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.event.ActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.mvc.ActionCompleteCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseAdminWithoutVersionController;
import org.kuali.student.r2.common.dto.DtoConstants;

import edu.umd.ks.cm.ui.course.client.configuration.CourseAdminWithoutVersionConfigurerUMD;

public class CourseAdminWithoutVersionControllerUMD extends CourseAdminWithoutVersionController {

	@Override
	protected void handleButtonClick(final String state){
		final SaveActionEvent saveActionEvent = new SaveActionEvent(false);

    	saveActionEvent.setActionCompleteCallback(new ActionCompleteCallback(){
			@Override
			public void onActionComplete(ActionEvent actionEvent) {
				if (saveActionEvent.isSaveSuccessful()){
	                final ViewContext viewContext = new ViewContext();
	                viewContext.setId((String)cluProposalModel.get(CreditCourseConstants.ID));
	                viewContext.setIdType(IdType.OBJECT_ID);											
					if (DtoConstants.STATE_ACTIVE.equalsIgnoreCase(state) || DtoConstants.STATE_APPROVED.equalsIgnoreCase(state)){
						KSNotifier.show("Course saved.");
						Application.navigate(AppLocations.Locations.VIEW_COURSE.getLocation(), viewContext);						
					}
				}      
			}
    	});
    	
    	//Store the rules if save was called
    	if((String)cluProposalModel.get(CreditCourseConstants.ID)!=null && getCourseProposalConfigurer() instanceof CourseAdminWithoutVersionConfigurerUMD){
    		((CourseAdminWithoutVersionConfigurerUMD)getCourseProposalConfigurer()).getRequisitesSection().storeRules(new Callback<Boolean>(){
    			public void exec(Boolean result) {
					if(result){
						CourseAdminWithoutVersionControllerUMD.this.fireApplicationEvent(saveActionEvent); 
					}else{
						KSNotifier.show("Error saving rules.");
					}
				}
    		});
    	}else{
    		CourseAdminWithoutVersionControllerUMD.this.fireApplicationEvent(saveActionEvent);    		
    	}
	}

}
