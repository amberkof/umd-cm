package edu.umd.ks.cm.ui.course.client.configuration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.assembly.data.Data.Property;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptorReadOnly;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.MenuSectionController;
import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.WarnContainer;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.widgets.KSCharCount;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldBlock;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableSection;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.common.validation.dto.ValidationResultInfo.ErrorLevel;
import org.kuali.student.core.document.ui.client.widgets.documenttool.DocumentList;
import org.kuali.student.core.document.ui.client.widgets.documenttool.DocumentListBinding;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.core.workflow.ui.client.views.CollaboratorSectionView;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowEnhancedNavController;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseRetireSummaryConfigurer;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseSummaryConfigurer;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseProposalConfigurer.CourseSections;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Widget;

public class CourseRetireSummaryConfigurerUMD extends CourseRetireSummaryConfigurer {

	@Override
    @SuppressWarnings("unchecked")
    public SummaryTableFieldBlock generateRetirementInfoSection() {
        SummaryTableFieldBlock block = new SummaryTableFieldBlock();
        block.addEditingHandler(new EditHandler(CourseSections.COURSE_INFO));
        block.setTitle(getLabel(LUUIConstants.PROPOSED_RETIRE_INFORMATION_LABEL_KEY));
        block.addSummaryTableFieldRow(getFieldRow(PROPOSAL_TITLE_PATH,
                generateMessageInfo(LUUIConstants.PROPOSED_PROPOSAL_TITLE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(PROPOSAL + "/" + PROPOSED_RATIONALE,
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