package edu.umd.ks.cm.ui.tools.client.configuration;

import java.util.ArrayList;

import org.kuali.student.common.rice.authorization.PermissionType;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.lum.lu.ui.tools.client.configuration.ClusetView;
import org.kuali.student.lum.lu.ui.tools.client.configuration.ToolsConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import edu.umd.ks.cm.ui.common.client.lu.LUUIPermissionsUMD;

public class ClusetViewUMD extends ClusetView {
    private static final String CLU_SET_SIS_CODE_FIELD = "sisCode";
	private static final String CLU_SET_SIS_CODE = "clusetSisCode";
	protected Boolean allowCluSetEditing=false;

    /* 
     * Adds extra SIS code field for entering the code that is used to map GenEd/CORE/etc. codes to UMDCM course sets
     * (non-Javadoc)
     * @see org.kuali.student.lum.lu.ui.tools.client.configuration.ClusetView#addFields(org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection, java.lang.String)
     */
    protected void addFields(VerticalSection defineCluSet, String contextName) {
    	super.addFields(defineCluSet, contextName);
    	addField(defineCluSet, CLU_SET_SIS_CODE_FIELD, generateMessageInfo(CLU_SET_SIS_CODE), null, null);
    }

    /**
     * Helper function to make setupViewClusetView read more clearly, hopefully
     * @param generalClusInfoSection
     * @param cluSetTitle
     */
	protected void setupNonAdminPortionofCluSetView(Section generalClusInfoSection, KSLabel cluSetTitle ){
        addField(generalClusInfoSection,
                ToolsConstants.CLU_SET_NAME_FIELD,
                null,
                cluSetTitle,
                null);
        ClusetViewUMD.this.addSection(setupGeneralClusInfoSection());
        ClusetViewUMD.this.addWidget(new KSLabel("Items in this Course Set"));
        ClusetViewUMD.this.addWidget(cluSetDisplay);
        ClusetViewUMD.this.setStyleName("standard-content-padding");
	}

    
    /**
     * KSCM-2059 Add permission check to Edit CluSet Link
     * So normal users won't see it.
     */
    @Override
    protected void setupViewClusetView() {
    	
        // View Config
        final Section generalClusInfoSection = new VerticalSection();
        final KSLabel cluSetTitle = new KSLabel();
        cluSetTitle.getElement().getStyle().setProperty("fontWeight", "bold");
        cluSetTitle.getElement().getStyle().setProperty("fontSize", "16px");
        cluSetTitle.getElement().getStyle().setProperty("borderBotton", "1px solid #D8D8D8");
        addWidget(titleLabel);
    	
    	// Create perm list
    	ArrayList<String> permissionList=new ArrayList<String>();
    	
    	// Config permission list
        Application.getApplicationContext().getSecurityContext().loadPermissionsByPermissionType(PermissionType.INITIATE);
    	   permissionList.add(LUUIPermissionsUMD.EDIT_COURSE_SETS);
    	   
    	// Cache permission list for checking if we should make the link  
        Application.getApplicationContext().getSecurityContext().loadScreenPermissions(permissionList, new Callback<Boolean>(){

        @Override
  			public void exec(Boolean result) {   
        	
        	// check here, if has edit perms, create link
    	    if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissionsUMD.EDIT_COURSE_SETS)){
    	    	
    	    	// If we are admin and have editCluSet permission, add edit link we just created.
    	     	Anchor editCluSet = new Anchor("Edit Course Set");
		        editCluSet.addClickHandler(new ClickHandler() {
		            @Override
		            public void onClick(ClickEvent event) {
		                Controller parentController = getController();
		                parentController.showView(CluSetsManagementViews.EDIT);
		            }
		        });		   
		        
                //  If we are admin and have editCluSet permission, add edit link we just created.
		        ClusetViewUMD.this.addWidget(editCluSet);
		        
		        // Also add the non-admin portion to the view (do it here to maintain screen order)
		        setupNonAdminPortionofCluSetView(generalClusInfoSection, cluSetTitle);
    }
    	    else  // if no permission to edit, add only nonAdminPortion
    	    {
    	    	setupNonAdminPortionofCluSetView(generalClusInfoSection, cluSetTitle);
    	    }
        }}); // end of perm check/link create
        
    }



	}

