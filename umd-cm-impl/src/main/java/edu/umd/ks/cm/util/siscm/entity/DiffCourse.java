package edu.umd.ks.cm.util.siscm.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;

/**
 * 
 * Holds all differences in a course.
 * 
 */
public class DiffCourse {
 
    /**
     * The state of the course in CM.  Used to determine if it should be excluded from the report. 
     */
    protected String stateInCm;
    
    protected Map<String, String> headerMap = null;
    
    /**
     * The list of differences
     */
    protected List<DiffField> diffList = new ArrayList<DiffField>();

    
    
    
    public DiffCourse() {
        super();
       
    }

    private void initHeaders(){
        headerMap = new ListOrderedMap();
        headerMap.put("AP_CRS", "Course");
        headerMap.put("START_TRM", "Start Term");
        headerMap.put("END_TRM", "End Term");
        headerMap.put("TRANS_TITLE", "Trans Title");
        headerMap.put("FORMAL_TITLE", "Formal Title");
        headerMap.put("GRD_METH", "Grade Method");
        headerMap.put("MIN_CR", "Min Credits");
        headerMap.put("MAX_CR", "Max Credits");
        headerMap.put("ADMIN_STAT", "Admin Stat");
        headerMap.put("CORE_DIVERSITY_GENED", "Core / Diversity / Gened");
        // headerMap.put("LEARNING_OUTCOMES", "Learning Outcomes");
        // Remove LO as per Mike C request
        headerMap.put("TXT40", "Text40");
    }
    
    /**
     * Convert to a map for use by UI
     */
    public Map<String, DiffField> getDiffMap(){
        Map<String, DiffField> m = new  HashMap<String, DiffField>();
        
        for (DiffField d : diffList) {
            m.put(d.getKey(), d);
        }
 
        return m;
    }
    
    /**
     * 
     * This method returns a list of headers that are
     * displayed in the UI.
     * 
     * @return
     */
    public Map<String, String> getHeaderMap() {
       if (headerMap == null)
           initHeaders();
       
        return headerMap;
    }

    
    public List<DiffField> getDiffList() {
        return diffList;
    }

    public void setDiffList(List<DiffField> diffList) {
        this.diffList = diffList;
    }
    
    /**
     * 
     * Check if any of the fields have a difference.
     * 
     * @return
     */
    public boolean isDiff(){
        boolean diff = false;
        for (DiffField d: diffList) {
            if (!d.equal){
                diff = true;
                break;
            }
        }
        return diff;
    }

    public String getStateInCm() {
        return stateInCm;
    }

    public void setStateInCm(String stateInCm) {
        this.stateInCm = stateInCm;
    }
    
}
