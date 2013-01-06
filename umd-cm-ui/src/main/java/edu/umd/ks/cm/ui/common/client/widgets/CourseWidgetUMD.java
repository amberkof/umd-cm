package edu.umd.ks.cm.ui.common.client.widgets;

import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.impl.KSDropDownImpl;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.lum.common.client.widgets.CommonWidgetConstants;
import org.kuali.student.lum.common.client.widgets.CourseWidget;

public class CourseWidgetUMD extends CourseWidget {
    
    @Override
    protected void createAndAddCourseTypesDropdown() {
        courseTypeWidget = new KSDropDown();
        SimpleListItems courseTypes = new SimpleListItems();
        courseTypes.addItem(CommonWidgetConstants.CLU_SET_APPROVED_CLUS_FIELD, "Approved Courses");
        courseTypes.addItem(CommonWidgetConstants.CLU_SET_PROPOSED_CLUS_FIELD, "Proposed Courses");
        courseTypeWidget.setListItems(courseTypes);
        courseTypeWidget.selectItem(CommonWidgetConstants.CLU_SET_PROPOSED_CLUS_FIELD);
        courseTypeWidget.addSelectionChangeHandler(new SelectionChangeHandler() {

            public void onSelectionChange(SelectionChangeEvent event) {
                String courseTypeSelected = ((KSDropDownImpl)event.getWidget()).getSelectedItem();
                if (courseTypeSelected == null) {
                    if (courseWidget != null) {
                        layout.remove(courseWidget);
                    }                    
                    return;
                }
                addCourseListWidget(true, courseTypeSelected);
            }
        });        
        layout.add(courseTypeWidget);
    }
}
