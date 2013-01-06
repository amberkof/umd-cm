package edu.umd.ks.cm.ui.tools.client.configuration;

import java.util.ArrayList;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.security.AuthorizationCallback;
import org.kuali.student.common.ui.client.security.RequiresAuthorization;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.lum.common.client.lu.LUUIPermissions;
import org.kuali.student.lum.lu.ui.tools.client.configuration.CluSetsManagementController;
import org.kuali.student.lum.lu.ui.tools.client.configuration.ClusetView;
import org.kuali.student.r1.common.assembly.data.Data;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.umd.ks.cm.ui.common.client.lu.LUUIPermissionsUMD;
public class CluSetsManagementControllerUMD extends CluSetsManagementController {
  
	  @Override
	    public <V extends Enum<?>> void showView(final V viewType) {
          
		   // Cache permission for checking ala CurriculumHomeConfigurer 		  
	        ArrayList<String> permissionList=new ArrayList<String>();
	        permissionList.add(LUUIPermissionsUMD.EDIT_COURSE_SETS);
	        permissionList.add(LUUIPermissions.USE_VIEW_COURSE_SET_MANAGEMENT_SCREENS);        
	    	Application.getApplicationContext().getSecurityContext().loadScreenPermissions(permissionList, new Callback<Boolean>(){  		

			@Override
			public void exec(Boolean result) {	
			// if has permission AND can edit, load edit view
				if (viewType == ClusetView.CluSetsManagementViews.EDIT) {
	            	  if (!Application.getApplicationContext().getSecurityContext().
	            			   checkCachedScreenPermission(LUUIPermissionsUMD.EDIT_COURSE_SETS))
	            	  {
							// popup error window
							giveCluSetEditCreatePermissionError();							
	            	  }
	            	  else {  // permission granted
	            final String cluSetId = mainView.getSelectedCluSetId();
	            editClusetView.setSelectedCluSetId(cluSetId);
	            viewClusetView.setSelectedCluSetId(cluSetId);
	            if (cluSetId != null) {
	                KSBlockingProgressIndicator.addTask(retrievingTask);
	                cluSetManagementRpcServiceAsync.getData(cluSetId,  new KSAsyncCallback<Data>() {
	                    @Override
	                    public void handleFailure(Throwable caught) {
	                        KSBlockingProgressIndicator.removeTask(retrievingTask);
	                        Window.alert("Failed to retrieve cluset with id" + cluSetId);
	                    }
	                    @Override
	                    public void onSuccess(Data result) {
	                        try {
	                            cluSetModel.setRoot(result);
	                            editClusetView.updateView(cluSetModel);
	                            CluSetsManagementControllerUMD.super.showView(viewType);
	                        } finally {
	                            KSBlockingProgressIndicator.removeTask(retrievingTask);
	                        }
	                    }
	                });
	            }
	          }
		     }  // if we are trying to view, and have rights then allow showView 
						else if ((Application.getApplicationContext()
								.getSecurityContext()
								.checkCachedScreenPermission(LUUIPermissions.USE_VIEW_COURSE_SET_MANAGEMENT_SCREENS))
								&& (viewType == ClusetView.CluSetsManagementViews.VIEW)) {
							final String cluSetId = mainView
									.getSelectedCluSetId();
							editClusetView.setSelectedCluSetId(cluSetId);
							viewClusetView.setSelectedCluSetId(cluSetId);
							if (cluSetId != null) {
								KSBlockingProgressIndicator
										.addTask(retrievingTask);
								cluSetManagementRpcServiceAsync.getData(
										cluSetId, new KSAsyncCallback<Data>() {
											@Override
											public void handleFailure(
													Throwable caught) {
												KSBlockingProgressIndicator
														.removeTask(retrievingTask);
												Window.alert("Failed to retrieve cluset with id"
														+ cluSetId);
											}

											@Override
											public void onSuccess(Data result) {
												try {
													cluSetModel.setRoot(result);
													viewClusetView
															.updateView(cluSetModel);
													afterModelLoaded();
													CluSetsManagementControllerUMD.super
															.showView(viewType);
												} finally {
													KSBlockingProgressIndicator
															.removeTask(retrievingTask);
												}
											}
										});
							}
						}
	            // if user is trying to Create a Cluset, check permissions for Edit cluset 
	            else if (viewType == ClusetView.CluSetsManagementViews.CREATE) {
	            	  if (!Application.getApplicationContext().getSecurityContext().
	            			   checkCachedScreenPermission(LUUIPermissionsUMD.EDIT_COURSE_SETS))
	            	  {
							// popup error window
							giveCluSetEditCreatePermissionError();							
	            	  }
	            	  else   // permission granted
		             {
	           				   cluSetModel.setRoot(new Data());
				          	   CluSetsManagementControllerUMD.super
							   .showView(viewType);
					}
	            }
								// Else we are loading Main view
								else {
								

				cluSetModel.setRoot(new Data());
				CluSetsManagementControllerUMD.super
						.showView(viewType);
							}
	        
						} // end exec block
					});
		}
	
	  /**
	   * beginShowView
	   * overriding Controller level method in order to perform custom 
	   * security on edit/create views.
	   * 
	   * The standard Kuali API doesn't work because
	   * cluset/view is a view, and can only have one access check by default.
	   * We need to treat cluset/view, cluset/edit and cluset/create differently.
	   * 
	   */
	  @Override
	    protected <V extends Enum<?>> void beginShowView(final View view, final V viewType, final Callback<Boolean> onReadyCallback){

		// Cache permission for checking ala CurriculumHomeConfigurer
		ArrayList<String> permissionList = new ArrayList<String>();
		permissionList.add(LUUIPermissionsUMD.EDIT_COURSE_SETS);
		Application.getApplicationContext().getSecurityContext()
				.loadScreenPermissions(permissionList, new Callback<Boolean>() {
					@Override
					public void exec(Boolean result) {
						beforeViewChange(viewType, new Callback<Boolean>() {
							@Override
							public void exec(Boolean result) {
								if (result) {

									// If we are trying to show cluset EDIT or
									// CREATE view
									// make sure we have permission first.
									// if not, do not finalize view.
									// This is strictly to prevent direct
									// hacking of the URL from bypassing
									// other security measures.

									if (((viewType == ClusetView.CluSetsManagementViews.EDIT) || (viewType == ClusetView.CluSetsManagementViews.CREATE))
											&& !Application
													.getApplicationContext()
													.getSecurityContext()
													.checkCachedScreenPermission(
															LUUIPermissionsUMD.EDIT_COURSE_SETS)) {
										// popup error window
										giveCluSetEditCreatePermissionError();
										
										onReadyCallback.exec(false);
									} else {    
										boolean requiresAuthz = (view instanceof RequiresAuthorization)
												&& ((RequiresAuthorization) view)
														.isAuthorizationRequired();

										if (requiresAuthz) {

											// A callback is required if async rpc call is required for authz check
											((RequiresAuthorization) view)
													.checkAuthorization(new AuthorizationCallback() {
														public void isAuthorized() {
															CluSetsManagementControllerUMD.super
																	.finalizeShowView(
																			view,
																			viewType,
																			onReadyCallback);
														}

														public void isNotAuthorized(
																String msg) {
															Window.alert(msg);
															onReadyCallback
																	.exec(false);
														}
													});
										} else {
											GWT.log("Not Requiring Auth.", null);
											CluSetsManagementControllerUMD.super
													.finalizeShowView(view,
															viewType,
															onReadyCallback);
										}
									}
								} // end if (result) block
								else {
									onReadyCallback.exec(false);
								}

							}
						}); // beginshowView
					} // exec block
				}); // loadscreenpermission wrapper
	}
	  
	public void giveCluSetEditCreatePermissionError(){
		GWT.log("Drawing Edit or Create CluSet Screen without Permission, failing!!!");
		// Window.alert("You do not have persmission to Create or Edit CluSets");
		
            KSLightBox errorWindow = new KSLightBox("Permission Denied ", KSLightBox.Size.SMALL);
            // errorWindow.setStylePrimaryName("MAIN_PANEL");
            errorWindow.setModal(true);
            errorWindow.setGlassEnabled(false);
            
            // Create Label 
            Label lineOneLbl = new Label("Sorry, you do not have permission to create or edit course sets.");

            // Add to the panel.
            VerticalPanel panel = new VerticalPanel();          
           
            panel.add(lineOneLbl);
            errorWindow.add(panel);   	
 
            errorWindow.setVisible(true);
            errorWindow.show();
    }
	

	}

