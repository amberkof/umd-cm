package edu.umd.ks.cm.ui.course.client.widgets.rules;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.widgets.field.layout.element.LabelPanel;
import org.kuali.student.r1.core.statement.dto.StatementTypeInfo;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
public class RuleInfoHeader extends FlowPanel {

    public RuleInfoHeader() {
        super();
    }

    public void updateRuleHeading(StatementTypeInfo statementTypeInfo) {
        this.clear();

        SectionTitle title = SectionTitle.generateH3Title(statementTypeInfo.getName());
        title.setStyleName("KS-Course-Requisites-Preview-Rule-Type-Header");
        this.add(title);

        LabelPanel labelExamples = new LabelPanel(statementTypeInfo.getDescr());
        labelExamples.getElement().setAttribute("style", "font-weight: normal; width: 70%;");

        HorizontalPanel horizontalSpacer = new HorizontalPanel();
        horizontalSpacer.addStyleName("KS-Course-Requisites-Button-Spacer");
        labelExamples.add(horizontalSpacer);

        this.add(labelExamples);
    }

}
