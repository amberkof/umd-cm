package edu.umd.ks.cm.ui.vpac.agenda.client.controllers;

import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.BasicLayout;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.security.AuthorizationCallback;
import org.kuali.student.common.ui.client.security.RequiresAuthorization;
import org.kuali.student.common.ui.client.util.ExportElement;

import edu.umd.ks.cm.ui.common.client.lu.LUUIPermissionsUMD;
import edu.umd.ks.cm.ui.vpac.agenda.client.configuration.VPACViews;
import edu.umd.ks.cm.ui.vpac.agenda.client.views.VpacAgendaView;
/**
 * Controller for Vpac Agenda Screen.
 * There is no configurer for this screen as it was not necessary, just a view (VpacAgendaView).
 * This screen was based code from the Dependency Analysis Screen, and parts of 
 * SearchResultsPanel for the search and results mechanisms.
 * 
 * @author mike
 *
 */
public class VpacAgendaController extends BasicLayout implements RequiresAuthorization {

	// Name of the permission defined in KIM/Rice that a user needs to have to use the screen.
	// CM Admin role has it, VPAC members and Managers will also need it.
	protected static final String USE_VPAC_AGENDA_SCREEN = "useVpacAgenda";

	
	public VpacAgendaController(String controllerId) {
        super(controllerId);
        addView(new VpacAgendaView(this));
        setDefaultView(VPACViews.VPAC_AGENDA);
	}

    @Override
	public boolean isAuthorizationRequired() {
		return true;
	}

	@Override
	public void setAuthorizationRequired(boolean required) {
		throw new UnsupportedOperationException();
	}
	
    /**
     * This overridden method return the view name when the controller name is null.
     * 
     * @see org.kuali.student.common.ui.client.configurable.mvc.LayoutController#getName()
     */
    @Override
    public String getName() {
        String name = super.getName();
        if ((name == null) && (this.getCurrentView() != null)) {
            name = this.getCurrentView().getName();
        }
        return name;
    }
	
    /**
     * Check to make sure user is allowed to use this screen.
     *  
     */
	@Override
	public void checkAuthorization(final AuthorizationCallback authCallback) {
		Application.getApplicationContext().getSecurityContext().checkScreenPermission(LUUIPermissionsUMD.USE_VPAC_AGENDA_SCREEN, new Callback<Boolean>() {
			@Override
			public void exec(Boolean result) {

				final boolean isAuthorized = result;
	        
				if(isAuthorized){
					authCallback.isAuthorized();
				}
				else
					authCallback.isNotAuthorized("User is not authorized: " + LUUIPermissionsUMD.USE_VPAC_AGENDA_SCREEN);
			}	
		});
	}
	
	
    public void showExport(boolean show) {
        VpacAgendaView vpacView = (VpacAgendaView) viewMap.get(VPACViews.VPAC_AGENDA);
        if (vpacView.getHeader() != null) {
        	vpacView.getHeader().showExport(isExportButtonActive());

        }
    }

    @Override
    public boolean isExportButtonActive() {
        return true;
    }

    @Override
    public DataModel getExportDataModel() {
        // TODO Auto-generated method stub
        return super.getExportDataModel();
    }

    @Override
    public List<ExportElement> getExportElementsFromView() {
        VpacAgendaView view = (VpacAgendaView) this.getCurrentView();
        //populate VpactableModel in view and call toJasperModel to get export Elements
        return view.getVpacTableModel().toJasperModel();
        
    }

    /**
     * @see org.kuali.student.common.ui.client.reporting.ReportExport#getExportTemplateName()
     */
    @Override
    public String getExportTemplateName() {
    	 return "vpac.template";
    }

	
}
