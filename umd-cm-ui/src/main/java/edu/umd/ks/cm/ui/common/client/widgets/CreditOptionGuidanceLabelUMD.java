package edu.umd.ks.cm.ui.common.client.widgets;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.DefaultWidgetFactory;
import org.kuali.student.common.ui.client.widgets.HasInputWidget;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.umd.ks.cm.ui.course.client.configuration.UMDConstants;

public class CreditOptionGuidanceLabelUMD extends Composite implements HasText, HasInputWidget, HasBlurHandlers {

    private final HorizontalPanel wrapperPanel = new HorizontalPanel();

    private final KSLabel guidanceLabel = new KSLabel();

    private Widget inputWidget;
    
    private static final String MSG_GROUP = "course";

    public CreditOptionGuidanceLabelUMD(Metadata metadata) {
        super.initWidget(wrapperPanel);
        Widget generatedWidget = DefaultWidgetFactory.getInstance().getWidget(metadata);
        if (generatedWidget instanceof TextBoxBase) {
            this.inputWidget = generatedWidget;
        } else {
            this.inputWidget = new KSTextBox();
        }
        setupHandlers();
        guidanceLabel.setStyleName("ks-form-module-elements-help-text");
        guidanceLabel.setText(getGuidanceText());
        wrapperPanel.add(inputWidget);
        wrapperPanel.add(guidanceLabel);
    }

    private void setupHandlers() {
        if (null != inputWidget && inputWidget instanceof TextBoxBase) {
            ((TextBoxBase) (this.inputWidget)).addKeyUpHandler(new KeyUpHandler() {

                @Override
                public void onKeyUp(KeyUpEvent event) {
                    guidanceLabel.setText(getGuidanceText());

                }
            });

            ((TextBoxBase) (this.inputWidget)).addValueChangeHandler(new ValueChangeHandler<String>() {

                @Override
                public void onValueChange(ValueChangeEvent<String> event) {
                    guidanceLabel.setText(getGuidanceText());

                }

            });
        }
    }

    private String getGuidanceText() {
        String message = "";
        String inputText = getText();
        double numericValue = 0;
        if (null != inputText && !inputText.trim().equals("")) {
            try {
                numericValue = Double.parseDouble(inputText);
                numericValue *= UMDConstants.COURSE_SEMESTER_DURATION;
            } catch (NumberFormatException nfe) {
                numericValue = 0;
            }
            Map<String, Object> msgParams = new HashMap<String, Object>();
            msgParams.put("0", numericValue);
            message = Application.getApplicationContext().getUILabel(MSG_GROUP, UMDConstants.COURSE_CREDIT_MIN_CONTACT_HOURS_GUIDANCE_LABEL_KEY, msgParams);
        }
        return message;
    }

    @Override
    public HandlerRegistration addBlurHandler(BlurHandler handler) {
        return ((TextBoxBase) inputWidget).addBlurHandler(handler);
    }

    @Override
    public Widget getInputWidget() {
        return inputWidget;
    }

    @Override
    public String getText() {
        return ((TextBoxBase) inputWidget).getText();
    }

    @Override
    public void setText(String text) {
        ((TextBoxBase) inputWidget).setText(text);

    }

}
