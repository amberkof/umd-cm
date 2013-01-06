package edu.umd.ks.cm.ui.common.client.widgets;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.StylishDropDown;
import org.kuali.student.common.ui.client.widgets.menus.KSMenu.MenuImageLocation;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;

import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.PopupPanel;

import edu.umd.ks.cm.ui.theme.client.LumClientBundleUMD;

public class StylishDropDownUMD extends StylishDropDown {
    
    protected Image defaultArrowOpened = new Image(LumClientBundleUMD.INSTANCE.expandablePanelIconOpen());
    protected Image defaultArrowClosed = new Image(LumClientBundleUMD.INSTANCE.expandablePanelIconClose());
    
    protected boolean isOpen = false;

    @Override
    protected void init(){
        
        menuPanel.addCloseHandler(new CloseHandler<PopupPanel>() {

            @Override
            public void onClose(CloseEvent<PopupPanel> event) {
                isOpen = false;
                setImageState();
                
            }
            
        });       
        
        layout.clear();
        layout.setWidth("100%");
        layout.add(defaultArrowOpened);
        layout.add(defaultArrowClosed); 
        defaultArrowOpened.addStyleName("stylish-Arrow-Opened");
        defaultArrowClosed.addStyleName("stylish-Arrow-Closed");
        setImageState();
        layout.add(titleLayout);
        layout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

        namePanel.setWidget(layout);
        menu.addGlobalMenuItemSelectCallback(new Callback<KSMenuItemData>(){

            @Override
            public void exec(KSMenuItemData item) {
                if(item.getClickHandler() != null){
                    StylishDropDownUMD.this.hideMenu();
                    if(showSelectedItem){
                        titleLabel.setText(item.getLabel());
                        if(item.getShownIcon() != null && showTitleIcon){
                            titleLayout.remove(titleImage);
                            Image image = item.getShownIcon();
                            titleImage = new Image(image.getUrl(), image.getOriginLeft(), 
                                    image.getOriginTop(), image.getWidth(), image.getHeight());
                            if(imgLoc == MenuImageLocation.RIGHT){
                                titleLayout.add(titleImage);
                            }
                            else{
                                titleLayout.insert(titleImage, 0);
                            }
                            
                        }
                    }
                }
            }
        });
        menuPanel.setWidget(menu);
        namePanel.addClickHandler(panelHandler);
        namePanel.addKeyDownHandler(downHandler);
        namePanel.addFocusHandler(focusHandler);
        namePanel.addMouseOverHandler(mouseOverHandler);
        namePanel.addMouseOutHandler(mouseOutHandler);
        namePanel.setTabIndex(1);
        menuPanel.setAutoHideEnabled(true);
        menuPanel.addAutoHidePartner(namePanel.getElement());
        namePanel.getElement().setAttribute("id", HTMLPanel.createUniqueId());
        parentPanel.add(namePanel);
        this.initWidget(parentPanel);
//        titleLabel.addStyleName("KS-CustomDropDown-TitleLabel");
        layout.addStyleName("KS-CustomDropDown-TitlePanel");
//        defaultArrow.addStyleName("KS-CustomDropDown-Arrow");
    }
    
    @Override
    public void showMenu(){
        isOpen = true;
        menuPanel.setPopupPosition(layout.getAbsoluteLeft(), layout.getAbsoluteTop() + layout.getOffsetHeight());
        menuPanel.show();
        setImageState();
    }
    
    @Override
    public void hideMenu(){
        isOpen = false;
        menuPanel.hide();
        setImageState();
    }
  
    
    private void setImageState() {
        defaultArrowClosed.setVisible(!isOpen);
        defaultArrowOpened.setVisible(isOpen);
    }
    
}
