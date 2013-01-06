package edu.umd.ks.cm.ui.theme.client;

import org.kuali.student.lum.lu.ui.main.client.theme.LumCss;

import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ResourcePrototype;

public class LumCssImplUMD implements LumCss {

	public String getCssString() {
       StringBuffer injectString = new StringBuffer("");
        for(ResourcePrototype r: LumClientBundleUMD.INSTANCE.getResources()){
            if(r instanceof CssResource){
                if(((CssResource)r).getText() != null){
                    injectString.append("\n" + (((CssResource)r).getText()));
                }
            }
        }
        return injectString.toString();
	}

}
