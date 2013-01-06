/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package edu.umd.ks.cm.util.siscm.dao;

import java.util.List;

import org.kuali.student.r1.common.dao.CrudDao;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

import edu.umd.ks.cm.util.siscm.entity.CmToSisExportCourse;
import edu.umd.ks.cm.util.siscm.entity.SisToCmImportCourse;
import edu.umd.ks.cm.util.siscm.entity.SiscmDiff;

/**
 * @Author VG 10/20/11
 * @See https://issues.umd.edu/browse/KSCM-616
 * Used by SisCourseInfoAdvice to update SISCM.UMDCM_CRS table for MF
 */

public interface SisCmDao extends CrudDao  {
	public List<CmToSisExportCourse> getSisCourseByCrsTrmStat(CourseInfo course, String statusInd);
	public void updateSisCourseInfo(CourseInfo courseInfo, CmToSisExportCourse course, String statusInd,  ContextInfo contextInfo);
	public SiscmDiff findSiscmDiff(String courseCd, String startTerm);
	public List<SisToCmImportCourse> getImportQueueCoursesOrderedByCrsAndStartTerm();
	public SisToCmImportCourse updateSisToCmImportCourseInTransaction(
			SisToCmImportCourse course);
}
