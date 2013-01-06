package edu.umd.ks.cm.ui.adminutils.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AdminUtilsServiceAsync {
	public void exportAllCourses(AsyncCallback<Boolean> callback);
	public void exportCourses(List<String> courseIds, AsyncCallback<Boolean> callback);
	public void updateCourseOrgsForPrefix(String prefixes, AsyncCallback<String> callback);
}
