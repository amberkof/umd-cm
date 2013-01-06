package edu.umd.ks.cm.course.service.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bouncycastle.asn1.cmp.CMPCertificate;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultComponentInfo;
 
import edu.umd.ks.cm.util.siscm.dto.SisToCmImportCourseInfo;
import edu.umd.ks.cm.util.siscm.entity.DiffCourse;
import edu.umd.ks.cm.util.siscm.entity.DiffField;
import edu.umd.ks.cm.util.siscm.service.impl.CoreGenEdClusetMapper;
import edu.umd.ks.cm.util.siscm.service.impl.CourseDataMapper;

/**
 * This class calculates differences between a CM and SIS course.
 * <p>
 */
public class DiffCourseUtil {
    /**
     * The CM course data
     */
    protected CourseInfo cmCourse;

    /**
     * The SIS course data
     */
    protected SisToCmImportCourseInfo sisCourse;

    /**
     * Differences in course
     */
    protected DiffCourse diffCourse = new DiffCourse();
    
 
    /**
     * LU services is needed to check clu sets for gen ed, core, etc
     */
    protected CluService luService;

    /**
     * We reuse the mapper code to figure out what clu sets the core, gen ed,
     * diversity map to
     */
    protected CoreGenEdClusetMapper coreGenedClusetMapper;

    /**
     * This constructs a class used to find differences between a SIS and CM
     * course
     * 
     * @param cmCourse
     *            The CM course
     * @param sisCourse
     *            The SIS course
     * @param luService
     * @param coreGenedClusetMapper
     */
    public DiffCourseUtil(CourseInfo cmCourse, SisToCmImportCourseInfo sisCourse, CluService luService,
            CoreGenEdClusetMapper coreGenedClusetMapper) {
        this.cmCourse = cmCourse;
        this.sisCourse = sisCourse;
        this.luService = luService;
        this.coreGenedClusetMapper = coreGenedClusetMapper;
        
        // We need to pass this info back to the diff report since we exclude 
        // courses that are not active from the report
        diffCourse.setStateInCm(cmCourse.getState());
    }

    /**
     * This method generates the diff between CM and SIS for this course.
     * 
     * @return
     */
    public DiffCourse diff() {

        List<String> diffs = new ArrayList<String>();

        diffField("AP_CRS", sisCourse.getApCrs(), cmCourse.getCode(), diffs);

        diffField("START_TRM", sisCourse.getStartTrm() != null ? CourseDataMapper.deriveTerm(sisCourse.getStartTrm())
                : null, cmCourse.getStartTerm(), diffs);

        diffEndTerm("END_TRM", sisCourse, cmCourse, diffs);

        diffField("FORMAL_TITLE", sisCourse.getFormalTitle().replaceAll("\\p{Cntrl}", ""), cmCourse.getCourseTitle(),
                diffs);

        diffGrdMethod();

        diffCredits(sisCourse.getMinCr(), sisCourse.getMaxCr(), cmCourse.getCreditOptions(), diffs);

        diffAdminStat("ADMIN_STAT", sisCourse, cmCourse, diffs);

        diffField("TXT40", sisCourse.getTxt40(), cmCourse.getDescr().getPlain(), diffs);

        diffField("TRANS_TITLE", sisCourse.getTransTitle().replaceAll("\\p{Cntrl}", ""), cmCourse.getTranscriptTitle(),
                diffs);

        //  diffLearningOutcomes("LEARNING_OUTCOMES", sisCourse, cmCourse, diffs);
        // Remove LO as per Mike C request
        
        diffCoreGenedDiversity();

        return diffCourse;
    }

    /**
     * Add a diff for return by the WS.
     * <p>
     * This is a special format that separates the data using three colons. If
     * the data happens to have three colons it will escape it.
     * 
     * @param equal
     *            The string EQUAL or NOTEQUAL says if CM and SIS data have a
     *            diff
     * @param key
     *            Used to identify the data.
     * @param sisVal
     *            The value in SIS
     * @param cmVal
     *            The value in CM
     */
    protected void addDiff(boolean equal, String key, String sisVal, String cmVal) {
        diffCourse.getDiffList().add(new DiffField(key, sisVal, cmVal, equal));
    }

    /**
     * This method determines if the end terms are different.
     * <p>
     * Note that we ignore cases where SIS end term is null.
     * 
     * @param key
     * @param sisCourse
     * @param courseInfo
     * @param diffs
     */
    protected void diffEndTerm(String key, SisToCmImportCourseInfo sisCourse, CourseInfo courseInfo, List<String> diffs) {

        String sisEndTrm = sisCourse.getEndTrm();

        // Convert SIS term to CM term
        if (sisEndTrm != null) {
            sisEndTrm = CourseDataMapper.deriveTerm(sisEndTrm);
        }

        String cmEndTrm = cmCourse.getEndTerm();

        boolean diffFound = false;

        if (courseInfo.getState().equals("Active") || courseInfo.getState().equals("Draft")){
       
            // Ignore end terms for draft and active courses since they will have a null end term
        }
        else if (cmEndTrm == null) {
            // This should not happen. CM should always have end term set 
            // if the course is not active or draft
            diffFound = true;
            cmEndTrm = "CM END TERM SHOULD NOT BE NULL WHEN COURSE STATE IS " + courseInfo.getState();
        } else if (sisEndTrm == null && cmEndTrm != null) {
            // This is OK.  In some cases SIS leaves end term null
        } else if (!sisEndTrm.equals(cmEndTrm)) {
            // If end term is set, but differs, record a difference
            diffFound = true;
        } 

        addDiff(diffFound ? false : true, key, sisEndTrm, cmEndTrm);

    }

    /**
     * Find any differences in the admin stat flag between CM and SIS.
     * <p>
     * The only check here is if the course in CM is a pilot course, since
     * that's all we were using admin stat = A for.
     * 
     * @param key
     * @param sisCourse
     * @param courseInfo
     * @param diffs
     */
    protected void diffAdminStat(String key, SisToCmImportCourseInfo sisCourse, CourseInfo courseInfo,
            List<String> diffs) {

        // Is course a pilot in CM?
        boolean cmIsPilot = courseInfo.isPilotCourse();

        String sisAdminStat = sisCourse.getAdminStat() == null ? "" : sisCourse.getAdminStat();

        // If it's a pilot in CM, but is NOT admin stat=A in SIS, there is a
        // difference
        boolean cmDiffFound = cmIsPilot && (!sisAdminStat.equals("P"));

        // So record the difference
        addDiff(cmDiffFound ? false : true, key, sisAdminStat, cmDiffFound ? "Pilot in CM but not in SIS" : "");

        // If it's not a pilot in CM, but admin stat=A in SIS, there's also is a
        // difference
        boolean sisDiffFound = !cmIsPilot && (sisAdminStat.equals("P"));

        // So record that difference
        addDiff(sisDiffFound ? false : true, key, sisAdminStat, sisDiffFound ? "Not pilot in CM but is pilot in SIS"
                : "");
    }

    /**
     * This method finds differences in learning outcomes between CM and SIS
     * 
     * @param key
     * @param sisCourse
     * @param courseInfo
     * @param diffs
     */
    
    /*
    protected void diffLearningOutcomes(String key, SisToCmImportCourseInfo sisCourse, CourseInfo courseInfo,
            List<String> diffs) {

        List<LoDisplayInfo> cmLos = courseInfo.getCourseSpecificLOs();

        String sisLoStr = sisCourse.getLearningOutcomes();

        String cmOut = "";

        String sisOut = "";

        // Convert the LO from SIS (which are in string format) into the CM
        // format
        // that uses LoDisplayInfo objects
        List<LoDisplayInfo> sisLos = CourseDataMapper.mapToLoDisplayList(sisLoStr);

        String cmPlain = null;

        String sisPlain = null;

        boolean diff = false;

        // Check if any lo's that are in SIS are missing from CM
        for (LoDisplayInfo sisLo : sisLos) {
            boolean found = false;

            // Loop over all the LOs
            for (LoDisplayInfo cmLo : cmLos) {

                // Get text for CM and SIS Los
                LoInfo cmInfo = cmLo.getLoInfo();
                LoInfo sisInfo = sisLo.getLoInfo();
                cmPlain = cmInfo.getDesc().getPlain();
                sisPlain = sisInfo.getDesc().getPlain();

                // If strings are the same we do not have a difference
                if (cmPlain.equals(sisPlain)) {
                    found = true; // found, so exit loop
                    break;
                }
            }
            if (!found) {
                // not found, we have a difference, append to string for output
                // to report
                cmOut += sisPlain + "<br>";
                diff = true;
            } else {

                // found, no difference, but send to report for display
                cmOut += sisPlain + "<br>";
            }
        }

        // Check if any lo's that are in CM are missing from SIS
        for (LoDisplayInfo cmLo : cmLos) {
            boolean found = false;
            // Loop over the LOs in CM
            for (LoDisplayInfo sisLo : sisLos) {

                // Get text for CM and SIS Los
                LoInfo cmInfo = cmLo.getLoInfo();
                LoInfo sisInfo = sisLo.getLoInfo();
                cmPlain = cmInfo.getDesc().getPlain();
                sisPlain = sisInfo.getDesc().getPlain();

                // If strings are the same we do not have a difference
                if (cmPlain.equals(sisPlain)) {
                    found = true; // found, so exit loop
                    break;
                }
            }
            if (!found) {

                // not found, we have a difference, append to string for output
                // to report
                sisOut += cmPlain + "<br>";
                diff = true;
            } else {

                // found, no difference, but send to report for display
                sisOut += cmPlain + "<br>";
            }
        }
        addDiff(diff ? false : true, key, sisOut, cmOut);
    }
    */
    
    /**
     * This method will do a simple diff of two strings.
     * 
     * @param key
     * @param sisField
     * @param cmField
     * @param diffs
     */
    protected void diffField(String key, String sisField, String cmField, List<String> diffs) {

        if (sisField == null) {
            sisField = "";
        }
        if (cmField == null) {
            cmField = "";
        }

        addDiff(sisField.equals(cmField) ? true : false, key, sisField, cmField);

    }

    /**
     * This method finds differences in grading method between SIS and CM.
     * 
     * @param key
     * @param sisField
     * @param cmField
     * @param diffs
     */
    protected void diffGrdMethod() {

        String key = "GRD_METH";

        String sisField = sisCourse.getGrdMeth();

        List<String> cmField = cmCourse.getGradingOptions();

        Map<String, String> attributes = cmCourse.getAttributes();

        // Create a map of all types of grading options
        Map<String, String> m = new HashMap<String, String>();
        m.put("kuali.resultComponent.grade.letter", "R");
        m.put("kuali.resultComponent.grade.satisfactory", "S");
        m.put("kuali.resultComponent.grade.completedNotation", "N");
        m.put("kuali.resultComponent.grade.passFail", "P");
        m.put("kuali.resultComponent.grade.audit", "A");

        String sisOut = "";
        String cmOut = "";
        boolean diffFound = false;

        // Loop over all the options and see if the option is in CM and SIS
        for (String cmKey : m.keySet()) {
            String sisKey = m.get(cmKey);
            boolean inCm = cmField.contains(cmKey);
            boolean inSis = sisField.contains(sisKey);

            // Special check for Audit. Audit is parsed out when it is sent
            // into CM and stored as a dynamic attribute
            if (cmKey.equals("kuali.resultComponent.grade.audit")) {
                String audit = attributes.get("audit");
                inCm = audit != null && audit.equals("true");
            }

            // If in CM, just append it for output
            if (inCm == true) {
                cmOut += cmKey + "<br>";
            }
            // If in SIS, just append it for output
            if (inSis == true) {
                sisOut += sisKey + "<br>";
            }
            // If in SIS but not in CM, or not in SIS but in CM, mark as there
            // being a difference
            if (inCm != inSis) {
                diffFound = true;
            }
        }

        // Mark as difference for return to report
        addDiff(diffFound ? false : true, key, sisOut, cmOut);

    }

    /**
     * This method finds difference between credits in SIS and CM.
     * 
     * @param sisMinCredits
     * @param sisMaxCredits
     * @param cmField
     * @param diffs
     */
    protected void diffCredits(String sisMinCredits, String sisMaxCredits, List<ResultComponentInfo> cmField,
            List<String> diffs) {

        // Loop over all CM field credit fields
        for (ResultComponentInfo r : cmField) {
            if (sisMinCredits != null && sisMaxCredits != null) {

                // strip the .0 since credits are stored inconsistently in the
                // system (sometimes with 0 and sometimes not)
                sisMinCredits = StringUtils.strip(sisMinCredits, ".0");
                sisMaxCredits = StringUtils.strip(sisMaxCredits, ".0");

                // If min and max credits are the same, CM stores as fixed
                // credits
                if (sisMinCredits.equals(sisMaxCredits)) {
                    String cmMinMax = r.getAttributes().get(
                            CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_FIXED_CREDIT_VALUE);

                    // strip the .0 since credits are stored inconsistently in
                    // the system (sometimes with 0 and sometimes not)
                    cmMinMax = StringUtils.strip(cmMinMax, ".0");

                    // Mark as difference for return to report
                    addDiff(cmMinMax.equals(sisMinCredits) ? true : false, "MIN_CR", sisMinCredits, cmMinMax);
                    addDiff(cmMinMax.equals(sisMaxCredits) ? true : false, "MAX_CR", sisMinCredits, cmMinMax);

                } else {
                    // variable credits because min and max differ
                    String cmMin = r.getAttributes().get(
                            CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_MIN_CREDIT_VALUE);
                    String cmMax = r.getAttributes().get(
                            CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_MAX_CREDIT_VALUE);

                    // strip the .0 since credits are stored inconsistently in
                    // the system (sometimes with 0 and sometimes not)
                    cmMin = StringUtils.strip(cmMin, ".0");
                    cmMax = StringUtils.strip(cmMax, ".0");

                    // Mark as difference for return to report
                    addDiff(cmMin.equals(sisMinCredits) ? true : false, "MIN_CR", sisMinCredits, cmMin);
                    addDiff(cmMax.equals(sisMaxCredits) ? true : false, "MAX_CR", sisMaxCredits, cmMax);

                }

            }
        }

    }

    /**
     * This method finds differences between core, gen ed, and diversity fields
     * in CM and SIS.
     * <p>
     * These are stored as course sets in CM. So we
     */
    protected void diffCoreGenedDiversity() {

     
           
        
        List<String> courseSetCodes = new ArrayList<String>();

        try {
            CourseDataMapper.mapToCourseInfoGenedCoreDiversityOnly(cmCourse, sisCourse, courseSetCodes);

        } catch (Exception e) {
            e.printStackTrace();
        }

        String cmOut = "";

        String sisOut = "<b>Core: </b> " + (sisCourse.getCore() == null ? "" : sisCourse.getCore())
                + " <br><b>Diversity: </b>" + (sisCourse.getDiversity() == null ? "" : sisCourse.getDiversity())
                + " <br><b>GenEd: </b>" + (sisCourse.getGenEdCodes() == null ? "" : sisCourse.getGenEdCodes());

        boolean diff = false;

        for (String courseSetCode : courseSetCodes) {
            String cluSetId = coreGenedClusetMapper.getCluSetId(courseSetCode);
            try {

                // Info on cluset
                CluSetInfo info = luService.getCluSetInfo(cluSetId);

                // Get the version independent IDs of clus in the cluset
                // NOTE: these are not clu ids!
                List<String> verIndIdsInCluSet = luService.getCluIdsFromCluSet(cluSetId);

                // Grab the version independent ID for this course
                String verIndId = cmCourse.getVersionInfo().getVersionIndId();

                // Check if version independent ID is in this cluset
                if (!verIndIdsInCluSet.contains(verIndId)) {
                    diff = true;

                    // Get the name of the cluset that it is missing from so we
                    // can output to report
                    cmOut += "<b>MISSING from cluset:</b><br><i>" + info.getName() + "</i><br><br>";

                } else {
                    cmOut += "<b>Found in cluset:</b><br><i> " + info.getName() + "</i><br><br>";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        
        // If course is NOT active, there is no difference since
        // we should only compare gened,core,diversity on the active
        // course as per Jen's comment at data team meeting.
        // So return true since there are no changes
        if (!cmCourse.getState().equals("Active")){
            addDiff(true, "CORE_DIVERSITY_GENED", sisOut , cmOut + " -- NOT COMPARED SINCE COURSE STATE != ACTIVE");
            return;
        }else{
            addDiff(diff ? false : true, "CORE_DIVERSITY_GENED", sisOut, cmOut);  
        }
  
    }

}
