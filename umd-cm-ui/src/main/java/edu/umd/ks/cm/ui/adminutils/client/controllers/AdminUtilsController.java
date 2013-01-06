package edu.umd.ks.cm.ui.adminutils.client.controllers;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.BasicLayout;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.security.AuthorizationCallback;
import org.kuali.student.common.ui.client.security.RequiresAuthorization;

import edu.umd.ks.cm.ui.adminutils.client.views.AdminUtilsView;

public class AdminUtilsController extends BasicLayout implements RequiresAuthorization {

	public enum AdminUtilsViews {
		MAIN
	}

	protected static final String USE_ADMIN_UTILS_SCREEN = "useAdminUtilsScreens";

	public AdminUtilsController(String controllerId) {
        super(controllerId);
        addView(new AdminUtilsView(this));
        setDefaultView(AdminUtilsViews.MAIN);
	}

    @Override
	public boolean isAuthorizationRequired() {
		return true;
	}

	@Override
	public void setAuthorizationRequired(boolean required) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void checkAuthorization(final AuthorizationCallback authCallback) {
		Application.getApplicationContext().getSecurityContext().checkScreenPermission(USE_ADMIN_UTILS_SCREEN, new Callback<Boolean>() {
			@Override
			public void exec(Boolean result) {

				final boolean isAuthorized = result;
	        
				if(isAuthorized){
					authCallback.isAuthorized();
				}
				else
					authCallback.isNotAuthorized("User is not authorized: " + USE_ADMIN_UTILS_SCREEN);
			}	
		});
	}
}
