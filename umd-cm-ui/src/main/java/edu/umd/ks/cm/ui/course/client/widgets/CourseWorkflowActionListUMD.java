package edu.umd.ks.cm.ui.course.client.widgets;

import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.notification.KSNotification;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.lum.lu.ui.course.client.widgets.CourseWorkflowActionList;

import com.google.gwt.user.client.rpc.AsyncCallback;
public class CourseWorkflowActionListUMD extends CourseWorkflowActionList {

	public CourseWorkflowActionListUMD() {
		super();
	}

	/**
	 * We can only have one retire proposal in workflow at a time.  This method
	 * will call the proposal webservice and run a custom search that will look
	 * for any retire proposals that are in the saved or enroute state.  A 
	 * count is returned and, if the count is > 0, we display an error message
	 * and prevent the user from proposing to retire this course until the outstanding 
	 * proposal is approved or canceled.
	 * 
	 * @param viewContext
	 * @param modifyPath
	 * @param model
	 * @param reviewOption
	 */
	@Override
	protected void checkOnlyOneRetireProposalInWorkflow(final ViewContext viewContext, final String retirePath, final DataModel model){

		// Get the course clu ID from the model
		String courseId = getCourseCluIdFromModel(model);

		// Call server to check how many other retire proposals for this course are in
		// workflow
		courseServiceAsync.isAnyOtherRetireProposalsInWorkflow(courseId, new AsyncCallback<Boolean>(){

			public void onFailure(Throwable caught) {
				KSNotifier.add(new KSNotification("Error checking number of proposals in workflow", false, 5000));
			}

			public void onSuccess(Boolean result) {

				// If there are no other retire proposals in workflow, display
				// the dialog and let the user propose to retire the course
				if (result != null && result.booleanValue() == false){
					doRetireActionItem(viewContext, retirePath, model);
				}
				else{
					// Otherwise, show this error message
					KSLightBox errorWindow = new KSLightBox(true);
					errorWindow.setModal(true);
					errorWindow.setGlassEnabled(true);
					errorWindow.setText("Cannot Create Multiple Proposals");
					errorWindow.setWidget(new KSLabel("\n" + getMessage("courseProposeRetireSingleProposal"), true));
					errorWindow.setSize(400, 250);
					errorWindow.setVisible(true);
					errorWindow.show();
				}
			}
		});
	}

}