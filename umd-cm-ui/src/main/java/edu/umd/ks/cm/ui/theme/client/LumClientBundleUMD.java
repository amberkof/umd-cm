package edu.umd.ks.cm.ui.theme.client;

import org.kuali.student.lum.ui.theme.standard.client.LumClientBundle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface LumClientBundleUMD extends LumClientBundle {

	public static final LumClientBundleUMD INSTANCE =  GWT.create(LumClientBundleUMD.class);
	
	@Source("edu/umd/ks/cm/ui/theme/public/css/UMDGeneral.css")
	@CssResource.NotStrict
	public CssResource overrideCss();
	
    @Source("edu/umd/ks/cm/ui/theme/public/images/umd_disclosure_opened.png")
    public ImageResource disclosureOpenedIcon();

    @Source("edu/umd/ks/cm/ui/theme/public/images/umd_disclosure_closed.png")
    public ImageResource disclosureClosedIcon();

    @Source("edu/umd/ks/cm/ui/theme/public/images/umd_cm_dropdown_red.png")
    public ImageResource dropdownWhite();
    
    @Source("edu/umd/ks/cm/ui/theme/public/images/umd_expander_down.png")
    public ImageResource expandablePanelIconOpen();
    
    @Source("edu/umd/ks/cm/ui/theme/public/images/umd_expander_right.png")
    public ImageResource expandablePanelIconClose();
    
    @Source("edu/umd/ks/cm/ui/theme/public/images/umd_vertical_collapsable_drawer_close.png")
    public ImageResource verticalCollapsableDrawerIconClose();
    
    @Source("edu/umd/ks/cm/ui/theme/public/images/umd_vertical_collapsable_drawer_open.png")
    public ImageResource verticalCollapsableDrawerIconOpen();
    
}
