package edu.umd.ks.cm.ui.dependency.client.widgets;

import org.kuali.student.common.ui.client.widgets.search.CollapsablePanel;
import org.kuali.student.lum.lu.ui.dependency.client.widgets.DependencyTypeSection;

/**
 * Overriding this class and subclass to change the text in the number of items in the dependency list. 
 * KSCM-2055
 * @author mike 
 */
public class DependencyTypeSectionUMD extends DependencyTypeSection {

    @Override
    public void finishLoad() {
        int numItems = dependencyItems.size();
        countLabel.setText("(Total:" + numItems + "):");
        if (numItems <= 5) {
            super.content.setVisible(true);
            super.isOpen = true;
            super.setImageState();
          // Force Sections to be open on initial load            
            DependencyTypeSectionUMD.this.open();  
        }
    }
}