package edu.umd.ks.cm.ui.common.client.widgets.panels.collapsable;



import org.kuali.student.common.ui.client.widgets.panels.collapsable.VerticalCollapsableDrawer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;

public class VerticalCollapsableDrawerUMD extends VerticalCollapsableDrawer {
    
    private static VerticalCollapsableDrawerBinderUMD uiBinder = GWT
    .create(VerticalCollapsableDrawerBinderUMD.class);
    
    interface VerticalCollapsableDrawerBinderUMD extends
        UiBinder<Widget, VerticalCollapsableDrawerUMD> {
    }
    
    public VerticalCollapsableDrawerUMD() {
        super();
    }
    
    @Override
    public void initialise(){
        initWidget(uiBinder.createAndBindUi(this));
        drawerHandle.addStyleName("drawerHandle-close");
        drawerHandle.addClickHandler(new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
                if(isOpen){
                    close();
                    drawerHandle.addStyleName("drawerHandle-open");
                    drawerHandle.removeStyleName("drawerHandle-close");
                }
                else{
                    open();
                    drawerHandle.addStyleName("drawerHandle-close");
                    drawerHandle.removeStyleName("drawerHandle-open");
                }
                
            }
        });
    }
    
}
