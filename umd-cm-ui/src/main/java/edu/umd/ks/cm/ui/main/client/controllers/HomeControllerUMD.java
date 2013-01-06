package edu.umd.ks.cm.ui.main.client.controllers;

import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.lum.lu.ui.main.client.controllers.HomeController;

/**
 * 
 * Home Controller UMD is an extension of
 * Home controller for the LUM Application, controls its default view, the curriculum home view, and the
 * Acknowledgments view page.
 * 
 * @author Kuali Student Team
 * @see HomeView
 */
public class HomeControllerUMD extends HomeController {
	
	
	public HomeControllerUMD(){
		super();
	}
	
	public HomeControllerUMD(Controller controller, String name,
			Enum<?> viewType) {
		super(controller, name, viewType);
	}

	@Override
    public void showDefaultView(Callback<Boolean> onReadyCallback) {
		ViewContext context = getViewContext();
    	if(getCurrentViewEnum() == null || (context.getAttribute("showView")==null))
		 super.showView(HomeViews.CURRICULUM_HOME);
    	else
    	 super.showView(HomeViews.DEFAULT);
		if(this.getCurrentView() instanceof Controller){
			((Controller) this.getCurrentView()).showDefaultView(onReadyCallback);
		}
		else{
			onReadyCallback.exec(true);
		}
		
	}

}
