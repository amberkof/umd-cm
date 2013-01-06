package edu.umd.ks.cm.ui.course.client.configuration;

import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.core.proposal.ProposalConstants;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseAdminRetireConfigurer;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseAdminRetireController;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseProposalController;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import edu.umd.ks.cm.ui.common.client.lu.LUUIConstantsUMD;
import edu.umd.ks.cm.ui.common.client.widgets.commenttool.CommentToolUMD;

public class CourseAdminRetireConfigurerUMD extends CourseAdminRetireConfigurer implements UMDConstants{
    
    @Override
    public void configure(final CourseProposalController layout) {
        type = "course";
        state = DtoConstants.STATE_RETIRED;
        nextState = DtoConstants.STATE_RETIRED;

        groupName = LUUIConstants.COURSE_GROUP_NAME;

        KSLabel courseStatusLabel = new KSLabel("");
        if (layout.getCourseState() != null)
            courseStatusLabel.setText(getLabel("courseStatusLabel") + ": " + layout.getCourseState());
        else
            courseStatusLabel.setText(getLabel("courseStatusLabel") + ": Unknown");
        layout.addContentWidget(courseStatusLabel);

        final CommentToolUMD commentTool = new CommentToolUMD(CourseSections.COMMENTS,
                getLabel(LUUIConstants.TOOL_COMMENTS_LABEL_KEY), "kuali.comment.type.generalRemarks",
                "Proposal Comments");
        commentTool.setController(layout);
        
        layout.addContentWidget(new KSButton("Comments", ButtonStyle.DEFAULT_ANCHOR, new ClickHandler() {
            
            public void onClick(ClickEvent event) {
                String proposalState = layout.getExportDataModel().get(PROPOSAL_PATH + "/state");

                if (ProposalConstants.PROPOSAL_STATE_REJECTED.equalsIgnoreCase(proposalState)) {
                    commentTool.setEditModeViewComment(true);
                }
                commentTool.show();
            }
        }));
  
        layout.addView(generateCourseAdminView((CourseAdminRetireController) layout));
    }
    
    @Override
    protected View generateCourseAdminView(final CourseAdminRetireController layout) {
        VerticalSectionView view =
                new VerticalSectionView(CourseSections.COURSE_INFO, getLabel(LUUIConstants.RETIREMENT_LABEL_KEY),
                        COURSE_PROPOSAL_MODEL, false);
        view.addStyleName(LUUIConstants.STYLE_SECTION);

        // Create course admin sections
        Section activeDatesSection = generateActiveDatesSection(initSection(LUUIConstants.ACTIVE_DATES_LABEL_KEY));
        Section retirementSection = generateRetirementSection(initSection(LUUIConstants.RETIREMENT_LABEL_KEY));

        //Add course admin sections to view
        view.addSection(activeDatesSection);
        view.addSection(retirementSection);

        //Add menu items for sections
        String sections = getLabel(LUUIConstantsUMD.PROPOSAL_SECTIONS);
        layout.addMenu(sections);
        layout.addMenuItemSection(sections, getLabel(LUUIConstants.ACTIVE_DATES_LABEL_KEY),
                LUUIConstants.ACTIVE_DATES_LABEL_KEY, activeDatesSection.getLayout());
        layout.addMenuItemSection(sections, getLabel(LUUIConstants.RETIREMENT_LABEL_KEY),
                LUUIConstants.RETIREMENT_LABEL_KEY, retirementSection.getLayout());

        //Add buttons to top and bottom of view
        layout.addButtonForView(CourseSections.COURSE_INFO, layout.getSaveButton());
        layout.addButtonForView(CourseSections.COURSE_INFO, layout.getCancelButton());
        layout.addTopButtonForView(CourseSections.COURSE_INFO, layout.getSaveButton());
        layout.addTopButtonForView(CourseSections.COURSE_INFO, layout.getCancelButton());

        return view;
    }
    
    @Override
    protected Section generateRetirementSection(Section section) {
        addField(section, COURSE + "/" + RETIREMENT_RATIONALE,
                generateMessageInfo(LUUIConstants.RETIREMENT_RATIONALE_LABEL_KEY));   
        addField(section, COURSE + "/" + RETIRE_COMMENT,
                generateMessageInfo(RETIRE_COMMENT_LABEL_KEY));

        return section;
    }

}
