package edu.umd.ks.cm.ui.dependency.client.widgets;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.lum.lu.ui.dependency.client.widgets.DependencySection;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class DependencySectionUMD extends DependencySection{


        @Override
		public void init(String title) {
            SectionTitle sectionTitle = SectionTitle.generateH4Title(title);
            sectionTitle.addStyleName("ks-dependency-section-title");
            header.add(sectionTitle);
            header.addStyleName("header-underline");
            header.addStyleName("ks-dependency-section-header");
            this.setTitleWidget(header);
            this.addStyleName("ks-dependency-section");

            KSButton expandLabel = new KSButton("Expand All", ButtonStyle.DEFAULT_ANCHOR);
            KSButton collapseLabel = new KSButton("Collapse All", ButtonStyle.DEFAULT_ANCHOR);

            expandLabel.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    DependencySectionUMD.this.expandAll();
                }
            });

            collapseLabel.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    DependencySectionUMD.this.collapseAll();
                }
            });

            // header.add(new KSLabel("Use the red arrows to expand/collapse the results below. Arrows pointing to the right indicate that additional information is available. Expanded views show additional information about relationships to the target course. <b>Please note:</b> the expanded view shows <u>only</u> the rule statement that contains the target course. Click on the ''View Course'' link for a complete listing of the rule statements for that course."));
            header.add(expandLabel);
            SpanPanel spanPanel = new SpanPanel(" | ");
            spanPanel.setExportElement(false);
            header.add(spanPanel);
            header.add(collapseLabel);

        }
        
    

    }
        
       
