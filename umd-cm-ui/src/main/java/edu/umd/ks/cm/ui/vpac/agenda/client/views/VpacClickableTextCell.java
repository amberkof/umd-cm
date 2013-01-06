package edu.umd.ks.cm.ui.vpac.agenda.client.views;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Hyperlink;

public class VpacClickableTextCell extends ClickableTextCell {

    String style;   
    public VpacClickableTextCell()
    {
        super();
        style = "myClickableCellTestStyle";
    }


     @Override
      protected void render(Context context, SafeHtml value, SafeHtmlBuilder sb) {
        if (value != null) {
        	String s = value.asString();
        	if (s.startsWith("http://") || s.startsWith("https://")){
        		s = "<a href='" + s + "' target='newWin'> View </a>";
        	}
        	
        	sb.appendHtmlConstant(s);
              
        }
        
      }

     public void addStyleName(String style)
     {
         this.style = style; 
     }


}