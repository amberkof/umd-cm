package edu.umd.ks.cm.ui.common.client.widgets.header;

import org.kuali.student.common.ui.client.widgets.headers.KSHeader;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;

public class KSHeaderUMD extends KSHeader {

    private static KSHeaderUiBinderUMD uiBinder = GWT
            .create(KSHeaderUiBinderUMD.class);

    interface KSHeaderUiBinderUMD extends UiBinder<Widget, KSHeaderUMD> {}

    public KSHeaderUMD() {
        super();
    }

    @Override
    protected void initialize() {
        initWidget(uiBinder.createAndBindUi(this));
        hiPanel.add(hiLabel);
        userNamePanel.add(userNameLabel);
        applicationTitleLabel.setText("");
    }
}
