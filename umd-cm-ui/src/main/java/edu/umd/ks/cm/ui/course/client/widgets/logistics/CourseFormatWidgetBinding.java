package edu.umd.ks.cm.ui.course.client.widgets.logistics;

import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LabelBase;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

import edu.umd.ks.cm.ui.course.client.configuration.UMDConstants;

public class CourseFormatWidgetBinding extends ModelWidgetBindingSupport<CourseFormatWidget> {

    public static CourseFormatWidgetBinding INSTANCE = new CourseFormatWidgetBinding();
    
    private boolean init = false;

    @Override
    public void setModelValue(CourseFormatWidget widget, DataModel model, String path) {
        if (init) {
            for (FieldDescriptor fd : widget.getFields()) {
                Widget w = fd.getFieldWidget();
                QueryPath keyPath = QueryPath.parse(fd.getFieldKey());
                String textValue = null;
                if (w instanceof TextBoxBase) {
                    textValue = ((TextBoxBase) w).getText();
                    model.set(keyPath, isEmptyValue(textValue) ? null : textValue);
                } else if (w instanceof LabelBase) {
                    textValue = ((Label) w).getText();
                    model.set(keyPath, isEmptyValue(textValue) ? null : textValue);
                } else if (w instanceof KSSelectItemWidgetAbstract) {
                    model.set(keyPath, ((KSSelectItemWidgetAbstract) w).getSelectedItem());
                }
                //This don't do much since the metadata is .../*/...
                setDirtyFlag(model, keyPath);
            }
            adjustSemesterDependableModelValues(widget, model);
        }
    
    }

    private void adjustSemesterDependableModelValues(CourseFormatWidget widget, DataModel model) {
        String semesterType = widget.getSemesterType();
        if (CourseFormatWidget.SEMESTER_TYPE_STANDARD.equals(semesterType)) {
            model.set(QueryPath.parse(widget.getParentPath() + "/" + UMDConstants.COURSE_ACTIVITY_TYPE_EXPLANATION), "<null>");
            model.set(QueryPath.parse(widget.getParentPath() + "/" + UMDConstants.COURSE_SEMESTER_TYPE),
                    CourseFormatWidget.SEMESTER_TYPE_STANDARD);
        }
    }

    @Override
    public void setWidgetValue(CourseFormatWidget widget, DataModel model, String path) {        

        if(!widget.tableInitialized) {
            widget.setModel(model);
        } else {

            for (FieldDescriptor fd : widget.getFields()) {
                Widget w = fd.getFieldWidget();
                QueryPath keyPath = QueryPath.parse(fd.getFieldKey());
                Object modelValue = model.get(keyPath);
                if (modelValue != null && !isEmptyValue(modelValue.toString())) {
                    if (w instanceof TextBoxBase) {
                        ((TextBoxBase) w).setValue(model.get(keyPath).toString());
                    } else if (w instanceof LabelBase) {
                        ((Label) w).setText(model.get(keyPath).toString());
                    }
                }
            }
            
            init = true;
            widget.calculateTotals();
        }        
    }
    
    private boolean isEmptyValue(String textValue) {
        if (textValue == null) {
            return true;
        }
        if (textValue.trim().isEmpty()) {
            return true;
        }
        if (textValue.trim().equals("0")) {
            return true;
        }
        if (textValue.trim().equals("<null>")) {
            return true;
        }
        return false;
    }
}
