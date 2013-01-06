package edu.umd.ks.cm.ui.adminutils.client.views;

import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.lum.lu.ui.dependency.client.controllers.DependencyAnalysisController.DependencyViews;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.umd.ks.cm.ui.adminutils.client.service.AdminUtilsService;
import edu.umd.ks.cm.ui.adminutils.client.service.AdminUtilsServiceAsync;

public class AdminUtilsView extends ViewComposite {
	
	private VerticalPanel rootPanel;
	
	private AdminUtilsServiceAsync adminUtilsService = GWT.create(AdminUtilsService.class);
	
	public AdminUtilsView(Controller controller) {
		super(controller, "Admin Utils", DependencyViews.MAIN);
        this.initWidget(rootPanel);
        this.addStyleName("blockLayout");
        this.addStyleName("ks-dependency-container");
	}
	
	
	@Override
	public void beforeShow(final Callback<Boolean> onReadyCallback) {
		TabPanel tp = new TabPanel(); 
		tp.add(createUpdateNLWidget(),"Update Natural Language");
		tp.add(createUpdateUpdateOrgsWidget(),"Update Course Curriculum Oversight Orgs");
		rootPanel.add(tp);
		onReadyCallback.exec(true);
	}


	private Widget createUpdateUpdateOrgsWidget() {
		VerticalPanel holder = new VerticalPanel();
		KSLabel instructions = new KSLabel("Enter a list of prefixes whose oversight orgs should be updated for. (Comma or space delimited)");
		holder.add(instructions);
		
		final KSTextArea prefixInput = new KSTextArea(); 
		holder.add(prefixInput);
		
		KSButton updateButton = new KSButton("Update");
		holder.add(updateButton);

		final KSLabel info = new KSLabel();
		holder.add(info);
		
		updateButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				adminUtilsService.updateCourseOrgsForPrefix(prefixInput.getValue(), new KSAsyncCallback<String>() {
					public void onSuccess(String result) {
						info.setText(result);
					}
				});
			}
		});
		
		return holder;
	}


	private Widget createUpdateNLWidget() {
		return new KSLabel("createUpdateUpdateOrgsWidget");
	}
}
