package edu.umd.ks.cm.course.service.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.r2.common.dto.AttributeInfo;

/**
 * 
 * Utility methods used in conversion.
 *
 */
public class CM20 {

    /**
     * 
     * This method converts the attributes to a map.
     * 
     * @param attributes
     * @return
     */
    public static Map<String,String> attributeInfoToMap(List<AttributeInfo> attributes){
        
        Map<String,String> m = new HashMap<String,String>();
        for (AttributeInfo a : attributes) {
            m.put(a.getKey(), a.getValue());
        }
        return m;
    }
}
