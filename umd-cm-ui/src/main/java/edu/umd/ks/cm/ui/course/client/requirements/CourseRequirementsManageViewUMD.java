package edu.umd.ks.cm.ui.course.client.requirements;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.widgets.KSProgressIndicator;
import org.kuali.student.lum.lu.ui.course.client.requirements.CourseRequirementsManageView;

import edu.umd.ks.cm.ui.course.client.widgets.rules.RuleInfoHeader;

public class CourseRequirementsManageViewUMD extends CourseRequirementsManageView {

    private RuleInfoHeader requisiteInfo = new RuleInfoHeader();
    private boolean isNewSubRule;

    @Override
    protected void draw() {
        remove(layout);
        layout.clear();

        layout.add(requisiteInfo);

        //STEP 1
        SectionTitle title = SectionTitle.generateH3Title("Step 1: Build and Add Rules");
        title.setStyleName("KS-Course-Requisites-Manage-Step-header1");
        layout.add(title);

        layout.add(editReqCompWidget);

        //STEP 2
        title = SectionTitle.generateH3Title("Step 2: Combine Rules with Logic");
        title.setStyleName("KS-Course-Requisites-Manage-Step-header2");
        layout.add(title);

        layout.add(ruleManageWidget);

        //add progressive indicator when rules are being simplified
        KSProgressIndicator twiddler = new KSProgressIndicator();
        twiddler.setVisible(false);
        twiddlerPanel.setWidget(twiddler);
        layout.add(twiddlerPanel);

        addWidget(layout);

        displaySaveButton();
    }

    public RuleInfoHeader getRequisiteInfo() {
        return this.requisiteInfo;
    }

    public void setNewRule(boolean isNewRule) {
        super.isNewRule = isNewRule;
    }

    public boolean isNewSubRule() {
        return isNewSubRule;
    }

    public void setNewSubRule(boolean isNewSubRule) {
        this.isNewSubRule = isNewSubRule;
    }

}
