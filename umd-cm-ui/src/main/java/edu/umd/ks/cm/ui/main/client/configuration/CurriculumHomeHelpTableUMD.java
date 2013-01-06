package edu.umd.ks.cm.ui.main.client.configuration;

import org.kuali.student.lum.lu.ui.main.client.configuration.CurriculumHomeHelpTable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;

public class CurriculumHomeHelpTableUMD extends CurriculumHomeHelpTable {

    private static HelpTableUiBinderUMD uiBinder = GWT.create(HelpTableUiBinderUMD.class);

    interface HelpTableUiBinderUMD extends UiBinder<Widget, CurriculumHomeHelpTableUMD> {}

    public CurriculumHomeHelpTableUMD() {
        super();
    }

    @Override
    protected void initialize() {
        initWidget(uiBinder.createAndBindUi(this));
    }
    
}
