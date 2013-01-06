package edu.umd.ks.cm.ui.common.client.lo;

import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.lum.common.client.lo.LOBuilder.LearningObjectiveList;

public class LearningObjectiveListUMD extends LearningObjectiveList {

    public LearningObjectiveListUMD() {
        super();
        addNew.setStyleName(ButtonStyle.FORM_SMALL.getStyle());
    }

}
