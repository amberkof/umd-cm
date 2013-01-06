package edu.umd.ks.cm.course.service.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.kuali.student.common.dictionary.dto.FieldDefinition;
import org.kuali.student.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.common.validator.DefaultValidatorImpl;
import org.kuali.student.lum.lrc.dto.ResultComponentInfo;

public class MinCreditValidator extends DefaultValidatorImpl {

    private static final String MIN_CREDIT_VALUE = "minCreditValue";
    private static final Object MAX_CREDIT_VALUE = "maxCreditValue";

    @Override
    public List<ValidationResultInfo> validateObject(FieldDefinition field, Object o,
            ObjectStructureDefinition objStructure, Stack<String> elementStack) {
        List<ValidationResultInfo> results = new ArrayList<ValidationResultInfo>();
        if (!MIN_CREDIT_VALUE.equalsIgnoreCase(field.getName())) {
            throw new RuntimeException("Custom Validator " + this.getClass().getName()
                    + " was not called on the right field: " + MIN_CREDIT_VALUE);
        }
        if (o instanceof ResultComponentInfo) {
            ResultComponentInfo data = (ResultComponentInfo) o;
            Map<String, String> dataAttributes = data.getAttributes();
            if (dataAttributes != null) {
                String minCreditValue = dataAttributes.get(MIN_CREDIT_VALUE);
                String maxCreditValue = dataAttributes.get(MAX_CREDIT_VALUE);
                if (null != minCreditValue && null != maxCreditValue) {
                    try {
                        double minCreditNumValue = Double.parseDouble(minCreditValue);
                        double maxCreditNumValue = Double.parseDouble(maxCreditValue);
                        if (minCreditNumValue >= maxCreditNumValue) {
                            ValidationResultInfo result = new ValidationResultInfo(getElementXpath(elementStack) + "/"
                                    + field.getName());
                            result.setError(getMessage("validation.minCredits"));
                            results.add(result);
                        }
                    } catch (NumberFormatException nfe) {
                        //can't compare values, because they're not numeric
                    }
                }

            }
        }
        return results;
    }

}
