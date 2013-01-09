package edu.umd.ks.cm.ui.course.client.configuration;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.layout.ContentBlockLayout;
import org.kuali.student.common.ui.client.widgets.layout.LinkContentBlock;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.search.SearchPanel;
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.lum.common.client.lu.LUUIPermissions;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.lu.ui.course.client.widgets.RecentlyViewedBlock;
import org.kuali.student.lum.lu.ui.main.client.configuration.CurriculumHomeConfigurer;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramRegistry;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.rice.authorization.PermissionType;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import edu.umd.ks.cm.ui.common.client.lu.LUUIPermissionsUMD;
import edu.umd.ks.cm.ui.common.client.widgets.AppLocationsUMD;

/**
 * 
 * CurriculumHomeController UMD is an extension of
 * CurriculumHomecontroller for the LUM Application, reads the hyperlink to override the targetHistoryToken.
 * 
 * @author Kuali Student Team
 */
public class CurriculumHomeConfigurerUMD extends CurriculumHomeConfigurer {
	
	public CurriculumHomeConfigurerUMD() {

	}


	@Override
	public Widget configure(Metadata searchMeta) {
	    this.searchMetadata = searchMeta;
        final ContentBlockLayout layout = new ContentBlockLayout(getMessage(CURRICULUM_MANAGEMENT));
        ArrayList<String> permissionList=new ArrayList<String>();
        
        layout.addContentTitleWidget(getHowToWidget());
        layout.addContentTitleWidget(getActionListLink());

        //TODO: Fix to improve performance, so permissions don't have to be loaded every time
        Application.getApplicationContext().getSecurityContext().loadPermissionsByPermissionType(PermissionType.INITIATE);
        permissionList.add(LUUIPermissions.USE_CREATE_COURSE_BY_PROPOSAL);
        permissionList.add(LUUIPermissions.USE_CREATE_COURSE_BY_ADMIN_PROPOSAL);
        permissionList.add(LUUIPermissions.USE_CREATE_PROGRAM_BY_PROPOSAL);       
        permissionList.add(LUUIPermissions.USE_BROWSE_CATALOG_SCREEN);
        permissionList.add(LUUIPermissions.USE_FIND_COURSE_SCREEN);
        permissionList.add(LUUIPermissions.USE_FIND_COURSE_PROPOSAL_SCREEN);
        permissionList.add(LUUIPermissions.USE_BROWSE_PROGRAM_SCREEN);
        permissionList.add(LUUIPermissions.USE_FIND_PROGRAM_SCREEN);
        permissionList.add(LUUIPermissions.USE_FIND_PROGRAM_PROPOSAL_SCREEN);
        permissionList.add(LUUIPermissions.USE_VIEW_CORE_PROGRAMS_SCREEN);
        permissionList.add(LUUIPermissions.USE_VIEW_CREDENTIAL_PROGRAMS_SCREEN);
        // KSCM-2059 UMD only permission.
        // We want only people with edit clu set rights to see the "manage clu sets" link
        // note that within Dependency Analysis normal users can still get to the "manage clu set" screen
        // but they actually will not have edit rights.
        permissionList.add(LUUIPermissionsUMD.EDIT_COURSE_SETS); 
        permissionList.add(LUUIPermissions.USE_LO_CATEGORY_SCREEN);
        permissionList.add(LUUIPermissions.USE_DEPENDENCY_ANALYSIS_SCREEN);
        
        // UMD only permission.
        permissionList.add(LUUIPermissionsUMD.USE_VPAC_AGENDA_SCREEN);
        
        Application.getApplicationContext().getSecurityContext().loadScreenPermissions(permissionList, new Callback<Boolean>(){

			@Override
			public void exec(Boolean result) {
				//Create Block
		        final LinkContentBlock create = new LinkContentBlock(getMessage(CREATE), getMessage(CREATE_DESC));
		        
				if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissions.USE_CREATE_COURSE_BY_PROPOSAL)||
						Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissions.USE_CREATE_COURSE_BY_ADMIN_PROPOSAL)){
						
				    create.addNavLinkWidget(getMessage(CREATE_COURSE), getCreateCourseClickHandler());
				}
				
				if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissions.USE_CREATE_PROGRAM_BY_PROPOSAL)){
                 
				    create.addNavLinkWidget(getMessage(CREATE_PROGRAM), AppLocationsUMD.Locations.EDIT_PROGRAM.getLocation());
				}
				
				 //Tools
		        final LinkContentBlock tools = new LinkContentBlock(getMessage(TOOLS), getMessage(TOOLS_DESC));
		        tools.addStyleName("management-tools");
		        
		        // KSCM-2059 Changed to only let people with edit rights see cluset link on landing page.
		        if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissionsUMD.EDIT_COURSE_SETS)){
                    
		        	 tools.addNavLinkWidget(getMessage(COURSE_SETS), AppLocationsUMD.Locations.MANAGE_CLU_SETS.getLocation());
		        }
		        
		        if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissions.USE_LO_CATEGORY_SCREEN)){
                    
		        	tools.addNavLinkWidget(getMessage(LO_CATEGORIES), AppLocationsUMD.Locations.MANAGE_LO_CATEGORIES.getLocation());
		        }
		        
		        if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissions.USE_DEPENDENCY_ANALYSIS_SCREEN)){
                    
		        	tools.addNavLinkWidget(getMessage(DEP_ANALYSIS), AppLocationsUMD.Locations.DEPENDENCY_ANALYSIS.getLocation());
		        }
				
		        //View + Modify
		        final LinkContentBlock viewModify = new LinkContentBlock(getMessage(VIEW_MODIFY), getMessage(VIEW_MODIFY_DESC));
		        SectionTitle courses = SectionTitle.generateH4Title(getMessage("courses"));
		        courses.addStyleName("bold");
		        viewModify.add(courses);
				
				
				if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissions.USE_BROWSE_CATALOG_SCREEN)){
					
				    viewModify.addNavLinkWidget(getMessage(BROWSE_CATALOG), AppLocationsUMD.Locations.BROWSE_CATALOG.getLocation());
			    } 
			
				if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissions.USE_FIND_COURSE_SCREEN)){
                                 
					viewModify.add(getFindCoursesWidget());
				}
				
				if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissions.USE_FIND_COURSE_PROPOSAL_SCREEN)){
                    
					viewModify.add(getFindCourseProposalsWidget());	
				}
				
				SectionTitle programs = SectionTitle.generateH4Title(getMessage("programs"));
			    programs.addStyleName("bold");
			    viewModify.add(programs);
			     					
				if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissions.USE_BROWSE_PROGRAM_SCREEN)){
                    
					 viewModify.addNavLinkWidget(getMessage(BROWSE_PROGRAM), AppLocationsUMD.Locations.BROWSE_PROGRAM.getLocation());
				}
				
				if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissions.USE_FIND_PROGRAM_SCREEN)){
                    
                    viewModify.add(getFindMajorsWidget());
				}
				
				if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissions.USE_FIND_PROGRAM_PROPOSAL_SCREEN)){
                    
                    viewModify.add(getFindProgramProposalsWidget());        
				}
				
				if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissions.USE_VIEW_CORE_PROGRAMS_SCREEN)){
                    
                    viewModify.add(getViewCoreProgramWidget());
				}
				
				if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissions.USE_VIEW_CREDENTIAL_PROGRAMS_SCREEN)){
                    
                    viewModify.add(getViewCredentialProgramWidget());
				}
				
				//RecentlyViewed
		        RecentlyViewedBlock recent = new RecentlyViewedBlock(getMessage(RECENTLY_VIEWED), getMessage(RV_DESC));
		        recent.addStyleName("recentlyViewed-block");
    
		        
				// VPAC Block- No perms required for external links which all roles are permitted to see.
		        final LinkContentBlock vpac = new LinkContentBlock(getMessage(UMDConstants.VPAC_BLOCK_TITLE), getMessage(UMDConstants.VPAC_BLOCK_DESCRIPTION));
                
		        // Open externalLinks in a new window.
		        boolean openNewWin = true;
                
		        vpac.addExternalLink(getMessage(UMDConstants.VPAC_ABOUT_LINK_TEXT), getMessage(UMDConstants.VPAC_ABOUT_LINK_URL), openNewWin);
                    
		        vpac.addExternalLink(getMessage(UMDConstants.VPAC_UNIVERSITY_POLICIES_LINK_TEXT), getMessage(UMDConstants.VPAC_UNIVERSITY_POLICIES_LINK_URL), openNewWin);
                    
		        vpac.addExternalLink(getMessage(UMDConstants.VPAC_REPRESENTATIVES_LINK_TEXT), getMessage(UMDConstants.VPAC_REPRESENTATIVES_LINK_URL), openNewWin);

		        vpac.addExternalLink(getMessage(UMDConstants.VPAC_MEETING_SCHEDULE_LINK_TEXT), getMessage(UMDConstants.VPAC_MEETING_SCHEDULE_LINK_URL), openNewWin);
		        
		        // Only the View Agenda Screen requires Permission in this block
				if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissionsUMD.USE_VPAC_AGENDA_SCREEN)){
				    vpac.addNavLinkWidget(getMessage(UMDConstants.VPAC_AGENDA_LINK_TEXT), AppLocationsUMD.Locations.VPAC_AGENDA.getLocation());
			    }
				
		        //Add all blocks to Curriculum Home Screen
				create.addBlock(tools);
		        layout.addContentBlock(create);
		        layout.addContentBlock(viewModify);
		        recent.addBlock(vpac);
		        layout.addContentBlock(recent);
			}}
        );

		if (layout.getWidget(0) instanceof FlowPanel) {
			FlowPanel panel = (FlowPanel) layout.getWidget(0);
			
			for (int i = 0; i < panel.getWidgetCount(); i++) {
				Widget childElement = panel.getWidget(i);
				String actionList = Application.getApplicationContext().getMessage(ACTIONLIST);
				if (childElement instanceof Hyperlink && ((Hyperlink)childElement).getText().equalsIgnoreCase(actionList)) {
					((Hyperlink) childElement).setTargetHistoryToken(AppLocationsUMD.Locations.HOME.getLocation()
									              + "&showView=ActionList");
				}
			}
		}
		return layout;
	}
	
    protected String getMessage(String key) {
        return Application.getApplicationContext().getMessage(key);
    }

    private Widget getViewCoreProgramWidget() {
        final Widget searchWidget;
        if (searchMetadata != null) {
            Metadata metadata = searchMetadata.getProperties().get("findCoreProgram");
            searchWidget = new KSPicker(metadata.getInitialLookup(), metadata.getAdditionalLookups());
            SearchPanel panel = ((KSPicker) searchWidget).getSearchPanel();
            if (panel != null) {
                panel.setMutipleSelect(false);
            }
            ((KSPicker) searchWidget).setAdvancedSearchCallback(new Callback<List<SelectedResults>>() {

                @Override
                public void exec(List<SelectedResults> result) {
                    SelectedResults value = result.get(0);
                    ViewContext viewContext = new ViewContext();
                    viewContext.setId(value.getResultRow().getId());
                    String cluType = value.getResultRow().getValue("lu.resultColumn.luOptionalType");
                    if (cluType != null) {
                        viewContext.setAttribute(ProgramConstants.TYPE, cluType);
                    }
                    viewContext.setIdType(IdType.OBJECT_ID);
                    ProgramRegistry.setCreateNew(true);
                    Application.navigate(AppLocations.Locations.VIEW_CORE_PROGRAM.getLocation(), viewContext);
                    ((KSPicker) searchWidget).getSearchWindow().hide();
                }
            });

        } else {
            searchWidget = new Label(getMessage(FIND_CORES));
            searchWidget.setStyleName("contentBlock-navLink-disabled");
        }
        searchWidget.setStyleName("contentBlock-navLink");
        return searchWidget;
    }

    private Widget getViewCredentialProgramWidget() {
        final Widget searchWidget;
        if (searchMetadata != null) {
            Metadata metadata = searchMetadata.getProperties().get("findCredentialProgram");
            searchWidget = new KSPicker(metadata.getInitialLookup(), metadata.getAdditionalLookups());
            SearchPanel panel = ((KSPicker) searchWidget).getSearchPanel();
            if (panel != null) {
                panel.setMutipleSelect(false);
            }
            ((KSPicker) searchWidget).setAdvancedSearchCallback(new Callback<List<SelectedResults>>() {

                @Override
                public void exec(List<SelectedResults> result) {
                    SelectedResults value = result.get(0);
                    ViewContext viewContext = new ViewContext();
                    viewContext.setId(value.getResultRow().getId());
                    String cluType = value.getResultRow().getValue("lu.resultColumn.luOptionalType");
                    if (cluType != null) {
                        viewContext.setAttribute(ProgramConstants.TYPE, cluType);
                    }
                    viewContext.setIdType(IdType.OBJECT_ID);
                    ProgramRegistry.setCreateNew(true);
                    Application.navigate(AppLocations.Locations.VIEW_BACC_PROGRAM.getLocation(), viewContext);
                    ((KSPicker) searchWidget).getSearchWindow().hide();
                }
            });

        } else {
            searchWidget = new Label(getMessage(FIND_CREDENTIALS));
            searchWidget.setStyleName("contentBlock-navLink-disabled");
        }
        searchWidget.setStyleName("contentBlock-navLink");
        return searchWidget;
	}
    
}
