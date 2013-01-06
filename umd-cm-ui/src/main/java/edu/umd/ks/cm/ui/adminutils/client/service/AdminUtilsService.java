package edu.umd.ks.cm.ui.adminutils.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * This service is intended to allow administrators to perform administrative tasks on UMDCM
 */
@RemoteServiceRelativePath("rpcservices/AdminUtilsRpcService")
public interface AdminUtilsService extends RemoteService{
	public boolean exportAllCourses();
	public boolean exportCourses(List<String> courseIds);
	public String updateCourseOrgsForPrefix(String prefixes);
}
