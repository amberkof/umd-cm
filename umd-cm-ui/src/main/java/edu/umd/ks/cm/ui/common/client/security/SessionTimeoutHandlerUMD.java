package edu.umd.ks.cm.ui.common.client.security;

import org.kuali.student.common.ui.client.security.SessionTimeoutHandler;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.StatusCodeException;

/**
 * This implements the SessionTimeoutHandler.  
 * 
 * @author Will
 *
 */
public class SessionTimeoutHandlerUMD implements SessionTimeoutHandler {

	public boolean isSessionTimeout(Throwable error) {
		if (error instanceof StatusCodeException){
			StatusCodeException sce = (StatusCodeException)error;
			Window.alert("RPC Exception: " + sce.getStatusCode());
			return true;
		} else {
			return false;
		}
	}

	public void handleSessionTimeout() {
		Window.alert("Your session has timed out and are being redirected to the login page.");
		//BrowserUtils.reload();
	}
 
}
