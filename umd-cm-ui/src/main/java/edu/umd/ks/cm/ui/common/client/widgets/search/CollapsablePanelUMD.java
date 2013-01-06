package edu.umd.ks.cm.ui.common.client.widgets.search;

import org.kuali.student.common.ui.client.widgets.search.CollapsablePanel;

import com.google.gwt.user.client.ui.Widget;

public class CollapsablePanelUMD extends CollapsablePanel {
    
    public CollapsablePanelUMD () {}
    
    @Override
    public void initialise(String label, Widget content, boolean isOpen) {
        init(getButtonLabel(label), content, isOpen, true, ImagePosition.ALIGN_LEFT);
    }

    @Override
    public void initialise(String label, Widget content, boolean isOpen, boolean withImages) {
        init(getButtonLabel(label), content, isOpen, withImages, ImagePosition.ALIGN_LEFT);
    }   
    
    @Override
    protected void init(Widget label, Widget content, boolean isOpen, boolean withImages, ImagePosition imagePosition) {
        this.isOpen = isOpen;
        this.withImages = withImages;
        this.imagePosition = imagePosition;
        this.content.setWidget(content);

        if (this.imagePosition == ImagePosition.ALIGN_RIGHT) {
            linkPanel.add(label);
        }

        if (this.withImages) {
            linkPanel.add(closedImage);
            linkPanel.add(openedImage);
            setImageState();
        }

        if (this.imagePosition == ImagePosition.ALIGN_LEFT) {
            linkPanel.add(label);
        }

        if (!isOpen) {
            this.content.setVisible(false);
        }

        closedImage.addClickHandler(openCloseClickHandler);
        openedImage.addClickHandler(openCloseClickHandler);

        layout.add(linkPanel);
        layout.add(this.content);
        closedImage.addStyleName("ks-image-middle-alignment");
        openedImage.addStyleName("ks-image-middle-alignment");        
        closedImage.addStyleName("expandable-Arrow-Closed");
        openedImage.addStyleName("expandable-Arrow-Opened");
        content.addStyleName("top-padding");
        this.initWidget(layout);
    }
    
}
