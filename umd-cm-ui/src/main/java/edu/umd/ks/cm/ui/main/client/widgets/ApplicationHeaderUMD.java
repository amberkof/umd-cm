package edu.umd.ks.cm.ui.main.client.widgets;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.common.ui.client.widgets.menus.KSMenu.MenuImageLocation;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.lu.ui.main.client.widgets.ApplicationHeader;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class ApplicationHeaderUMD extends ApplicationHeader {

    public ApplicationHeaderUMD() {
        super();
    }
    
    @Override
    protected void createNavDropDown() {
        navDropDown.setImageLocation(MenuImageLocation.LEFT);

        List<KSMenuItemData> items = new ArrayList<KSMenuItemData>();

        items.add(new KSMenuItemData(getMessage("wrapperPanelTitleHome"),Theme.INSTANCE.getCommonImages().getApplicationIcon(),
                new ClickHandler(){

                    @Override
                    public void onClick(ClickEvent event) {
                        HistoryManager.navigate(AppLocations.Locations.HOME.getLocation()+"&showView=ActionList");
                    }}));
        items.add(new KSMenuItemData(getMessage("wrapperPanelTitleCurriculumManagement"),Theme.INSTANCE.getCommonImages().getBookIcon(),
                new ClickHandler(){
                    @Override
                    public void onClick(ClickEvent event) {
                        HistoryManager.navigate(AppLocations.Locations.CURRICULUM_MANAGEMENT.getLocation());
                    }}));
        items.add(new KSMenuItemData(getMessage("wrapperPanelTitleWorkflowDocSearch"), Theme.INSTANCE.getCommonImages().getNodeIcon(),
                new ClickHandler(){

                    @Override
                    public void onClick(ClickEvent event) {
                        buildDocSearchPanel();
                        docSearchDialog.show();
                    }})
        );
        items.add(new KSMenuItemData(getMessage("wrapperPanelTitleRice"), Theme.INSTANCE.getCommonImages().getRiceIcon(),
                new WrapperNavigationHandler(
                        riceURL+"/portal.do?selectedTab=main"))
        );

        navDropDown.setItems(items);
        navDropDown.ensureDebugId("Application-Header");
    }
    
}
