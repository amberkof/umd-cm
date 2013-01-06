package edu.umd.ks.cm.ui.common.client.security;

import org.kuali.student.common.ui.client.security.AsyncCallbackFailureHandler;
import org.kuali.student.common.ui.client.service.exceptions.VersionMismatchClientException;

import com.google.gwt.user.client.Window;

public class AsyncCallbackFailureHandlerUMD extends AsyncCallbackFailureHandler {
 
    public void onFailure(Throwable caught) {
        // Get message returned in the exception
        // this is usually the raw HTML wrapped in
        // an invocationException
        String message = caught.getMessage();
 
        // On timeout the application returns the spring
        // login screen in the exception message
        boolean timedout = message.contains("j_username");
        
        // Display custom text on timeout
        if (timedout) {
            Window.alert("An error has occurred connecting to the server.  This may be the result of a lost internet connection or the session timing out because of inactivity.  Please close this box and try to save your work to resume the session.   If the system is not letting you resume, please exit the system and log in again.  Debug info: "
                    + message);
        }
        else if (caught instanceof VersionMismatchClientException) {
            super.handleVersionMismatch(caught);
        }
        else {
            super.handleFailure(caught);
        }
  
    }
}
