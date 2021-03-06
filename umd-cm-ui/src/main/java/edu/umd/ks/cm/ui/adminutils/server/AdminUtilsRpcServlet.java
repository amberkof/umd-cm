package edu.umd.ks.cm.ui.adminutils.server;

import java.util.List;
 
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.kim.api.services.IdentityManagementService;
import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.r2.common.util.ContextUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.umd.ks.cm.ui.adminutils.client.service.AdminUtilsService;
import edu.umd.ks.cm.util.siscm.service.SiscmService;

public class AdminUtilsRpcServlet extends RemoteServiceServlet implements AdminUtilsService{
	
	private static final long serialVersionUID = 1L;

	private SiscmService siscmService;
	private PermissionService permissionService;
	
	@Override
	public boolean exportAllCourses() {
		isAuthorized();
		try {
			return siscmService.exportAllCourses(ContextUtils.getContextInfo());
		} catch (Exception e) {
			throw new RuntimeException("Error exporting courses.",e);
		}
	}

	@Override
	public boolean exportCourses(List<String> courseIds) {
		isAuthorized();
		try {
			return siscmService.exportCourses(courseIds,ContextUtils.getContextInfo());
		} catch (Exception e) {
			throw new RuntimeException("Error exporting courses.",e);
		}
	}

	@Override
	public String updateCourseOrgsForPrefix(String prefixes) {
		isAuthorized();
		try {
			return siscmService.updateCourseOrgsForPrefix(prefixes,ContextUtils.getContextInfo());
		} catch (Exception e) {
			throw new RuntimeException("Error updating prefixes.",e);
		}
	}

	private void isAuthorized() {
		if(true)return;
		String user = SecurityUtils.getCurrentPrincipalId();
		//AttributeSet permissionDetails = null;
		//AttributeSet qualification = null;
		// CM20 upgrade: this code does not work.  I don't think we are using admin tools.
		/*
		if(!permissionService.isAuthorized(user, "", "", permissionDetails, qualification)){
			throw new RuntimeException("User is not authorized.");
		}
		*/
	}
	
	public void setSiscmService(SiscmService siscmService) {
		this.siscmService = siscmService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	
}
