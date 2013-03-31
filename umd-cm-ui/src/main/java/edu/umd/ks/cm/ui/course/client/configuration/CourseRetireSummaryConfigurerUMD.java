package edu.umd.ks.cm.ui.course.client.configuration;

import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldBlock;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.base.RichTextInfoConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseProposalConfigurer.CourseSections;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseRetireSummaryConfigurer;

public class CourseRetireSummaryConfigurerUMD extends CourseRetireSummaryConfigurer {

	@Override
    @SuppressWarnings("unchecked")
    public SummaryTableFieldBlock generateRetirementInfoSection() {
        SummaryTableFieldBlock block = new SummaryTableFieldBlock();
        block.addEditingHandler(new EditHandler(CourseSections.COURSE_INFO));
        block.setTitle(getLabel(LUUIConstants.PROPOSED_RETIRE_INFORMATION_LABEL_KEY));
        block.addSummaryTableFieldRow(getFieldRow(PROPOSAL_TITLE_PATH,
                generateMessageInfo(LUUIConstants.PROPOSED_PROPOSAL_TITLE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(PROPOSAL + "/" + PROPOSED_RATIONALE + RichTextInfoConstants.PLAIN,
        		generateMessageInfo(LUUIConstants.RETIREMENT_RATIONALE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + START_TERM, 
        		generateMessageInfo(LUUIConstants.START_TERM_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(PROPOSAL + "/" + PROPOSED_END_TERM,
                generateMessageInfo(LUUIConstants.PROPOSED_END_TERM_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(PROPOSAL + "/" + OTHER_COMMENTS,
                generateMessageInfo(LUUIConstants.OTHER_COMMENTS_LABEL_KEY)));

        return block;
    }
       
}