package edu.umd.ks.cm.course.service.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.core.search.infc.SearchParam;

/**
 * Utility methods used in conversion.
 */
public class CM20 {

    /**
     * This method converts the attributes to a map.
     * 
     * @param attributes
     * @return
     */
    public static Map<String, String> attributeInfoToMap(List<AttributeInfo> attributes) {

        Map<String, String> m = new HashMap<String, String>();
        for (AttributeInfo a : attributes) {
            m.put(a.getKey(), a.getValue());
        }
        return m;
    }

    public static List<AttributeInfo> mapToAttributeInfo(Map<String, String> attributesMap) {
        List<AttributeInfo> attributesList = new ArrayList<AttributeInfo>();
        for (String key : attributesMap.keySet()) {
            attributesList.add(new AttributeInfo(key, attributesMap.get(key)));
        }
        return attributesList;
    }
    
    public static Object getFirstValueFromSearchParam(SearchParam searchParam){
        Object returnVal = null;
        List<String> vals = searchParam.getValues();
        for (String val : vals) {
            returnVal = val;
            break;
        }
        return returnVal;
    }

}
