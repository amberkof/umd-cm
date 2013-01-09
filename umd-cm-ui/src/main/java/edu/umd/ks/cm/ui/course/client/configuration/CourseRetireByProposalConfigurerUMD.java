package edu.umd.ks.cm.ui.course.client.configuration;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.KSCharCount;
import org.kuali.student.core.comments.ui.client.widgets.decisiontool.DecisionPanel;
import org.kuali.student.core.document.ui.client.widgets.documenttool.DocumentTool;
import org.kuali.student.core.workflow.ui.client.views.CollaboratorSectionView;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseProposalConfigurer;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseAdminRetireController;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseProposalController;
import org.kuali.student.r1.common.assembly.data.QueryPath;
import org.kuali.student.r1.core.proposal.ProposalConstants;
import org.kuali.student.r2.common.dto.DtoConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import edu.umd.ks.cm.ui.common.client.lu.LUUIConstantsUMD;
import edu.umd.ks.cm.ui.common.client.widgets.commenttool.CommentToolUMD;

/**
 * Shell of Configurer for Retire by Proposal
 * 
 * @author mike
 *
 */
public class CourseRetireByProposalConfigurerUMD extends CourseProposalConfigurer {

    /**
     * Sets up all the views and sections of {@link CourseAdminRetireController}. This
     * should be called once for initialization and setup per
     * {@link CourseAdminRetireController} instance.
     * 
     * @param layout
     */
    @Override
    public void configure(final CourseProposalController layout) {
        type = "course";
        state = DtoConstants.STATE_DRAFT;
        nextState = DtoConstants.STATE_RETIRED;

        groupName = LUUIConstants.COURSE_GROUP_NAME;

    	if (modelDefinition.getMetadata().isCanEdit()) {
    		addCluStartSection(layout);
    		String sections = getLabel(LUUIConstantsUMD.PROPOSAL_SECTIONS);

    		layout.addMenu(sections);
                
    		//Retirement Info
    		layout.addMenuItem(sections, (SectionView)generateRetirementInfoSection(initSectionViewRetire(CourseSections.COURSE_INFO, getLabel(LUUIConstants.RETIREMENT_LABEL_KEY))));

    		//Collaborators
	        layout.addMenuItem(sections, new CollaboratorSectionView(CourseSections.PEOPLE_PERMISSONS, getLabel(LUUIConstants.SECTION_AUTHORS_AND_COLLABORATORS), COURSE_PROPOSAL_MODEL));
	
	        //Documents
	        documentTool = new DocumentTool(LUUIConstants.REF_DOC_RELATION_PROPOSAL_TYPE,CourseSections.DOCUMENTS, getLabel(LUUIConstants.TOOL_DOCUMENTS_LABEL_KEY));
	        documentTool.setModelDefinition((DataModelDefinition)modelDefinition);
	        layout.addMenuItem(sections, documentTool);
	        
	        //Add common buttons to sections except for sections with specific button behavior
	        List<Enum<?>> excludedViews = new ArrayList<Enum<?>>();
	        excludedViews.add(CourseSections.DOCUMENTS);
//	        excludedViews.add(CourseSections.PEOPLE_PERMISSONS);
	        //^^^ people and permissions requires a save action, so just treat it like COURSE_INFO
	        layout.addCommonButton(sections, layout.getSaveButton(), excludedViews);
	        layout.addCommonButton(sections, layout.getCancelButton(CourseSections.SUMMARY), excludedViews);
	        
	        //Summary
	        summaryConfigurer = GWT.create(CourseRetireSummaryConfigurerUMD.class);
	        summaryConfigurer.init(type, state, groupName,(DataModelDefinition)modelDefinition, stmtTypes, (Controller)layout, COURSE_PROPOSAL_MODEL);
	        layout.addSpecialMenuItem(summaryConfigurer.generateProposalSummarySection(true), "Review and Submit");
        
	        //Specific buttons for certain views
	        //TODO people and permissions will use a different button than continue
	        //^^^ people and permissions requires a save action, so just treat it like COURSE_INFO
	        layout.addButtonForView(CourseSections.DOCUMENTS, getContinueButton(layout));
    	} else {
    		summaryConfigurer = GWT.create(CourseRetireSummaryConfigurerUMD.class);
    		summaryConfigurer.init(type, state, groupName,(DataModelDefinition)modelDefinition, stmtTypes, (Controller)layout, COURSE_PROPOSAL_MODEL);
    		layout.removeMenuNavigation();
    		layout.addView(summaryConfigurer.generateProposalSummarySection(false));
    	}    	
        
        // Proposal Status
        layout.addContentWidget(layout.getWfUtilities().getProposalStatusLabel());
        
        // Comment Tool
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
        
        // Decision Tool
        final DecisionPanel decisionPanel = new DecisionPanel(CourseSections.DECISIONS, getLabel(LUUIConstants.TOOL_DECISION_LABEL_KEY), "kuali.comment.type.generalRemarks");
        layout.addView(decisionPanel);
        layout.addContentWidget(new KSButton("Decisions", ButtonStyle.DEFAULT_ANCHOR, new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
            	decisionPanel.show();
            }
        }));

    }
    
    protected Section generateRetirementInfoSection(Section section) {
    	FieldDescriptor fd = addField(section, PROPOSAL_PATH + "/" + PREV_START_TERM, generateMessageInfo(LUUIConstants.PROPOSAL_PREV_START_TERM));
        fd.getFieldWidget().setVisible(false);
        fd.hideLabel();
    	
        addField(section, PROPOSAL_TITLE_PATH, generateMessageInfo(LUUIConstants.PROPOSED_PROPOSAL_TITLE_LABEL_KEY));
        addField(section, PROPOSAL_PATH + "/" + PROPOSED_RATIONALE, generateMessageInfo(LUUIConstants.RETIREMENT_RATIONALE_LABEL_KEY),
                new KSCharCount(modelDefinition.getMetadata(QueryPath.parse(PROPOSAL_PATH + "/" + PROPOSED_RATIONALE))));
        addReadOnlyField(section, COURSE + "/" + START_TERM, generateMessageInfo(LUUIConstants.START_TERM_LABEL_KEY));
        addField(section, PROPOSAL_PATH + "/" + PROPOSED_END_TERM, generateMessageInfo(LUUIConstants.PROPOSED_END_TERM_LABEL_KEY));
        addField(section, PROPOSAL_PATH + "/" + OTHER_COMMENTS, generateMessageInfo(LUUIConstants.OTHER_COMMENTS_LABEL_KEY));  
        
        return section;
    }
    
    protected VerticalSectionView initSectionViewRetire(Enum<?> viewEnum, String labelKey) {
        VerticalSectionView section = new VerticalSectionView(viewEnum, getLabel(labelKey), this.getModelId());
        section.setInstructions("Provide information related to the retirement of the current version of this course.");
        section.addStyleName(LUUIConstants.STYLE_SECTION);
        return section;
    }
    
}
