package edu.umd.ks.cm.course.service.assembler;

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.service.assembler.ActivityAssembler;

import edu.umd.ks.cm.course.service.utils.CM20;

public class ActivityAssemblerUMD extends ActivityAssembler {

    @Override
    public ActivityInfo assemble(CluInfo baseDTO, ActivityInfo businessDTO, boolean shallowBuild,
            ContextInfo contextInfo) {
        if (baseDTO == null) {
            return null;
        }

        ActivityInfo activityInfo = (null != businessDTO) ? businessDTO : new ActivityInfo();

        activityInfo.setId(baseDTO.getId());
        activityInfo.setTypeKey(baseDTO.getTypeKey());
        activityInfo.setStateKey(baseDTO.getStateKey());
        activityInfo.setDefaultEnrollmentEstimate(baseDTO.getDefaultEnrollmentEstimate());
        activityInfo.setDuration(baseDTO.getStdDuration());
        activityInfo.setContactHours(baseDTO.getIntensity());
        activityInfo.setMeta(baseDTO.getMeta());
        activityInfo.setAttributes(baseDTO.getAttributes());

        if (!CM20.attributeInfoToMap(activityInfo.getAttributes()).containsKey("calculatedContactHours")) {
            activityInfo.getAttributes().add(new AttributeInfo("calculatedContactHours", "0"));

        }

        return activityInfo;
    }

}
