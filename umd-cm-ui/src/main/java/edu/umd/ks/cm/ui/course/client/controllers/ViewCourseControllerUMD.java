package edu.umd.ks.cm.ui.course.client.controllers;

import org.kuali.student.common.ui.client.util.WindowTitleUtils;
import org.kuali.student.lum.lu.ui.course.client.controllers.ViewCourseController;

public class ViewCourseControllerUMD extends ViewCourseController {
 
     /**
      * 
      * This overridden method will allow use to customize the header for UMD.
      * <p>
      * Note: To customize controllers you need to (1) edit the CourseUMD.gwt.xml file and
      * override the class (2) make sure the class is being created with GWT.create in the
      * KS code base.  
      * 
      * @see org.kuali.student.lum.lu.ui.course.client.controllers.ViewCourseController#setHeaderTitle()
      */
    protected void setHeaderTitle() {
      
        String title;
        if (cluModel.get("transcriptTitle") != null) {
            title = getCourseTitle();

            // If we have a course code (e.g. ENGL101) then prefix it
            // to the title
            if (cluModel.get("code") != null) {
                title = cluModel.get("code") + " - " + title;
            }
        } else {
            // We are viewing a course without a transcript title
            title = "Course";
        }

        updateStatus();

        this.setContentTitle(title);
        this.setName(title);
        WindowTitleUtils.setContextTitle(title);
    }

}
