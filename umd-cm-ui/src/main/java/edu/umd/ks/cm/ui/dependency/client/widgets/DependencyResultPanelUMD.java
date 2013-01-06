package edu.umd.ks.cm.ui.dependency.client.widgets;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.lum.lu.ui.dependency.client.widgets.DependencyResultPanel;

public class DependencyResultPanelUMD extends DependencyResultPanel {

	// KSCM-2125 Insert UMD Specific Instructions into Dep.Res.Panel
	@Override
    public void init() {
        this.initWidget(dependencySectionContainer);
        dependencySectionContainer.addWidget(headerLabel);
        dependencySectionContainer.setInstructions(getMessage("dependencyAnalysisInstructions"));
        dependencySectionContainer.addStyleName("ks-dependency-results");
    }

	// KSCM-2125 populate Instructions in MEssage table under key dependencyAnalysisInstructions
	// so in the future it can be changed via SQL
    public String getMessage(String courseMessageKey) {
    	String msg = Application.getApplicationContext().getMessage("course", courseMessageKey);
    	if (msg == null) {
    		msg = courseMessageKey;
    	}
    	return msg;
    }

	
}
