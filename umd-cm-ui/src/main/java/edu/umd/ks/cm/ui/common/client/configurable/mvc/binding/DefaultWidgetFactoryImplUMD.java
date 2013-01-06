package edu.umd.ks.cm.ui.common.client.configurable.mvc.binding;

import org.kuali.student.common.assembly.data.Data.DataType;
import org.kuali.student.common.assembly.data.LookupMetadata;
import org.kuali.student.common.assembly.data.LookupParamMetadata;
import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.assembly.data.MetadataInterrogator;
import org.kuali.student.common.ui.client.configurable.mvc.WidgetConfigInfo;
import org.kuali.student.common.ui.client.configurable.mvc.impl.DefaultWidgetFactoryImpl;
import org.kuali.student.common.ui.client.widgets.BooleanDisplayLabel;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSPlaceholder;
import org.kuali.student.common.ui.client.widgets.KSRichEditor;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.list.KSSelectedList;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;

import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

public class DefaultWidgetFactoryImplUMD extends DefaultWidgetFactoryImpl {

    @Override
    public Widget getWidget(LookupParamMetadata meta) {
        WidgetConfigInfo config = new WidgetConfigInfo();
        if (meta != null) {
            config.access = meta.getWriteAccess();
            config.type = meta.getDataType();
            config.lookupMeta = meta.getChildLookup();
            if (meta.getWriteAccess().equals(Metadata.WriteAccess.WHEN_NULL)) {
                config.canEdit = false;
            }
        }
        return _getWidget(config);
    }

    @Override
    protected Widget _getWidget(WidgetConfigInfo config) {
        Widget result = null;
        if (!config.canView) {
            result = new KSPlaceholder();
            result.setVisible(config.canView);
        } else if (!config.canEdit && (config.lookupMeta == null || config.lookupMeta.getWidget() == null)) {
            if (config.type == DataType.BOOLEAN) {
                result = new BooleanDisplayLabel();
            } else {
                result = new KSLabel();
            }
        } else {
            if (config.lookupMeta != null && config.lookupMeta.getWidget() != null) {
                //All repeating fields should use the KSSelectedList for multiplicities (Except checkboxes)
                if (config.metadata != null && MetadataInterrogator.isRepeating(config.metadata)
                        && !LookupMetadata.Widget.CHECKBOX_LIST.equals(config.lookupMeta.getWidget())) {
                    result = new KSSelectedList(config);
                } else {
                    KSPicker picker = new KSPicker(config);
                    Widget pickerInputWidget = picker.getInputWidget();
                    if (null != pickerInputWidget && pickerInputWidget instanceof KSSuggestBox) {
                        TextBoxBase pickerTextBox = ((KSSuggestBox) pickerInputWidget).getTextBox();
                        if (pickerTextBox instanceof KSTextBox) {
                            setMaxLengthKSTextBoxSettings(config, (KSTextBox) pickerTextBox);
                        }
                    }
                    result = picker;

                }
            } else {
                switch (config.type) {
                    case BOOLEAN:
                        result = new KSCheckBox();
                        break;

                    case DATE:
                        // fall through

                    case TRUNCATED_DATE:
                        result = new KSDatePicker();
                        break;

                    case DATA:
                        if (config.isRichText) {
                            result = new KSRichEditor();
                            break;
                        }
                    default:
                        if (config.isMultiLine) {
                            result = new KSTextArea();
                            result.addStyleName("ks-textarea-width");
                            if (config.maxLength != null) {
                                ((KSTextArea) (result)).setMaxLength(config.maxLength);
                                if (config.maxLength < 250) {
                                    result.addStyleName("ks-textarea-small-height");
                                } else if (config.maxLength < 500) {
                                    result.addStyleName("ks-textarea-medium-height");
                                } else {
                                    result.addStyleName("ks-textarea-large-height");
                                }
                            } else {
                                result.addStyleName("ks-textarea-medium-height");
                            }
                        } else {
                            KSTextBox text = new KSTextBox();
                            //text.removeStyleName("KS-Textbox");
                            setMaxLengthKSTextBoxSettings(config, text);
                            result = text;
                        }
                }
            }
        }
        return result;
    }

    private void setMaxLengthKSTextBoxSettings(WidgetConfigInfo config, KSTextBox text) {
        if (config.maxLength != null) {
            text.setMaxLength(config.maxLength);
            if (config.maxLength < 5) {
                switch (config.maxLength) {
                    case 1:
                        text.addStyleName("ks-one-width");
                        break;
                    case 2:
                        text.addStyleName("ks-two-width");
                        break;
                    case 3:
                        text.addStyleName("ks-three-width");
                        break;
                    case 4:
                        text.addStyleName("ks-four-width");
                        break;
                }
            } else if (config.maxLength < 23) {
                text.addStyleName("ks-small-width");
            } else if (config.maxLength < 35) {
                text.addStyleName("ks-medium-width");
            } else if (config.maxLength < 60) {
                text.addStyleName("ks-large-width");
            } else {
                text.addStyleName("ks-extra-large-width");
            }
        } else {
            text.addStyleName("ks-medium-width");
        }
    }
}
