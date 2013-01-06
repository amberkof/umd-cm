package edu.umd.ks.cm.util.siscm.entity;

/**
 * 
 * Used to store difference between SIS and CM fields.
 *
 */
public class DiffField {
    
    protected String key;
    
    protected String sisVal;
    
    protected String cmVal;
    
    protected boolean equal = false;;

    public DiffField(String key, String sisVal, String cmVal, boolean equal) {
        super();
        this.key = key;
        this.sisVal = sisVal;
        this.cmVal = cmVal;
        this.equal = equal;
    }

    /**
     * 
     * Key used to identify field in a map
     * 
     * @return
     */
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 
     * The value in SIS
     * 
     * @return
     */
    public String getSisVal() {
        return sisVal;
    }

    public void setSisVal(String sisVal) {
        this.sisVal = sisVal;
    }

    /**
     * The value in CM
     * 
     * @return
     */
    public String getCmVal() {
        return cmVal;
    }

    public void setCmVal(String cmVal) {
        this.cmVal = cmVal;
    }

    /**
     * 
     * True if the values are equal
     * 
     * @return
     */
    public boolean isEqual() {
        return equal;
    }

    public void setEqual(boolean equal) {
        this.equal = equal;
    }
    
   

    
}
