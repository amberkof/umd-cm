package edu.umd.ks.cm.ui.main.client.controllers;

import java.util.Map;

import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.lum.common.client.configuration.LUMViews;
import org.kuali.student.lum.lu.ui.course.client.views.CategoryManagementView;
import org.kuali.student.lum.lu.ui.course.client.views.CurriculumHomeView;
import org.kuali.student.lum.lu.ui.dependency.client.controllers.DependencyAnalysisController;
import org.kuali.student.lum.lu.ui.main.client.controllers.CurriculumHomeController;

import edu.umd.ks.cm.ui.adminutils.client.controllers.AdminUtilsController;
import edu.umd.ks.cm.ui.adminutils.client.controllers.AdminUtilsController.AdminUtilsViews;
import edu.umd.ks.cm.ui.vpac.agenda.client.configuration.VPACViews;
import edu.umd.ks.cm.ui.vpac.agenda.client.controllers.VpacAgendaController;

/**
 * Curriculum home controller UMD extension of 
 * Curriculum home controller which controls the main LayoutController views of the lum application.  The
 * default view of this controller is the Curriculum Home Landing page.  The following views are views within
 * this controller's scope:<br>
 * 		COURSE_PROPOSAL<br>
        VIEW_COURSE<br>
        PROGRAM_VIEW<br>
        PROGRAM_EDIT<br>
        PROGRAM_CREATE<br>
        PROGRAM_VERSIONS<br>
        CLU_SETS<br>
        VARIATION_VIEW<br>
        VARIATION_EDIT<br>
        COURSE_CATALOG<br>
        LO_CATEGORIES<br>
        BACC_PROGRAM_VIEW<br>
        BACC_PROGRAM_EDIT<br>
        BACC_PROGRAM_VERSIONS<br>
        CORE_PROGRAM_VIEW<br>
        CORE_PROGRAM_EDIT<br>
        CORE_PROGRAM_VERSIONS<br>
 * These views can be accessed through links and searches provided by the CurriculumHomeView (the default view).
 * 
 * @author Kuali Student Team
 * @see CurriculumHomeView
 */
public class CurriculumHomeControllerUMD extends CurriculumHomeController {

	private AdminUtilsController adminUtilsController;
	private VpacAgendaController vpacAgendaController;

	@Override
	protected View getCategoryManagementController() {
        return new CategoryManagementView(this, "Learning Outcome Categories", LUMViews.LO_CATEGORIES);
    }

	private LayoutController getVpacAgendaController() {
		vpacAgendaController = new VpacAgendaController("VpacAgenda");
	    return vpacAgendaController;
	}
	
	private LayoutController getAdminUtilsController() {
		adminUtilsController = new AdminUtilsController("AdminUtils");
	    return adminUtilsController;
	}


	/* 
	 * Overridden to allow for new UMD views
	 * (non-Javadoc)
	 * @see org.kuali.student.lum.lu.ui.main.client.controllers.CurriculumHomeController#getView(java.lang.Enum, org.kuali.student.common.ui.client.mvc.Callback, java.util.Map)
	 */
	@Override
	public <V extends Enum<?>> void getView(V viewType,	Callback<View> callback, Map<String, String> tokenMap) {
		
		if(AdminUtilsViews.MAIN.equals(viewType)){
			callback.exec(getAdminUtilsController());
			}
		else{
		if(VPACViews.VPAC_AGENDA.equals(viewType)){
			callback.exec(getVpacAgendaController());
			}
		else{
			super.getView(viewType, callback, tokenMap);
		} 
	  }

    }
	// Overriding this method to allow for non-LUM views.
    @Override
    public Enum<?> getViewEnumValue(String enumValue) {
    	// First check to see if this is a UMD-only view type.
    	// If it is, return that enum type. Otherwise return LUMViews (default behavoir).
   	if (enumValue.equals("VPAC_AGENDA")){
    		return VPACViews.valueOf(enumValue);
    	}
    	else 
    		return LUMViews.valueOf(enumValue);
    }
	
}
