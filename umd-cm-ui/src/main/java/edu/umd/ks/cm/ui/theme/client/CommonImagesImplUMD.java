package edu.umd.ks.cm.ui.theme.client;

import org.kuali.student.common.ui.theme.standard.client.CommonImagesImpl;

import com.google.gwt.user.client.ui.Image;


public class CommonImagesImplUMD extends CommonImagesImpl {

    @Override
    public Image getDisclosureClosedIcon() {
        return new Image(LumClientBundleUMD.INSTANCE.disclosureClosedIcon());
    }

    @Override
    public Image getDisclosureOpenedIcon() {
        return new Image(LumClientBundleUMD.INSTANCE.disclosureOpenedIcon());
    }

    @Override
    public Image getDropDownIconWhite() {
        return new Image(LumClientBundleUMD.INSTANCE.dropdownWhite());
    }
    
    public Image getExpandablePanelIconOpened() {
        return new Image(LumClientBundleUMD.INSTANCE.expandablePanelIconOpen());
    }
    
    public Image getExpandablePanelIconClosed() {
        return new Image(LumClientBundleUMD.INSTANCE.expandablePanelIconClose());
    }
    
}
