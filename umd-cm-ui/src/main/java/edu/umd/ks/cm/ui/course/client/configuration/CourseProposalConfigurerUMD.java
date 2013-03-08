package edu.umd.ks.cm.ui.course.client.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.CollapsableSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.GroupSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.SwapEventHandler;
import org.kuali.student.common.ui.client.configurable.mvc.sections.SwapSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.KSCharCount;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.field.layout.element.LabelPanel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.FieldLayoutComponent;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.core.comments.ui.client.widgets.decisiontool.DecisionPanel;
import org.kuali.student.core.document.ui.client.widgets.documenttool.DocumentTool;
import org.kuali.student.core.workflow.ui.client.views.CollaboratorSectionView;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.base.RichTextInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseJointsConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseProposalConfigurer;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseSummaryConfigurer;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseProposalController;
import org.kuali.student.r1.common.assembly.data.QueryPath;
import org.kuali.student.r1.core.proposal.ProposalConstants;
import org.kuali.student.r2.common.dto.DtoConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.TextBoxBase;

import edu.umd.ks.cm.ui.common.client.lu.LUUIConstantsUMD;
import edu.umd.ks.cm.ui.common.client.widgets.CreditOptionGuidanceLabelUMD;
import edu.umd.ks.cm.ui.common.client.widgets.commenttool.CommentToolUMD;
import edu.umd.ks.cm.ui.course.client.widgets.logistics.CourseFormatWidget;
import edu.umd.ks.cm.ui.course.client.widgets.logistics.CourseFormatWidgetBinding;

public class CourseProposalConfigurerUMD extends CourseProposalConfigurer {
    
    @Override
    public void configure(final CourseProposalController layout) {
        type = "course";
        state = DtoConstants.STATE_DRAFT;
        groupName = LUUIConstants.COURSE_GROUP_NAME;

        if (modelDefinition.getMetadata().isCanEdit()) {
            addCluStartSection(layout);
            String sections = getLabel(LUUIConstantsUMD.PROPOSAL_SECTIONS);

            layout.addMenu(sections);
            
            //Course Content
            layout.addMenuItem(sections, (SectionView)generateCourseInfoSection(initSectionView(CourseSections.COURSE_INFO, LUUIConstants.INFORMATION_LABEL_KEY)));
            layout.addMenuItem(sections, (SectionView)generateCourseLogisticsSection(initSectionView(CourseSections.COURSE_LOGISTICS, LUUIConstants.LOGISTICS_LABEL_KEY)));
            layout.addMenuItem(sections, generateLearningObjectivesSection());

            //Student Eligibility
            layout.addMenuItem(sections, generateCourseRequisitesSection(layout,true));

            //Administrative
            layout.addMenuItem(sections, (SectionView)generateActiveDatesSection(initSectionView(CourseSections.ACTIVE_DATES, LUUIConstants.ACTIVE_DATES_LABEL_KEY)));
            
            //Authors & Collaborators
            CollaboratorSectionView view = GWT.create(CollaboratorSectionView.class);
            view.init(CourseSections.PEOPLE_PERMISSONS, getLabel(LUUIConstants.SECTION_AUTHORS_AND_COLLABORATORS), COURSE_PROPOSAL_MODEL);
            layout.addMenuItem(sections,view);
            
            //Documents
            documentTool = new DocumentTool(LUUIConstants.REF_DOC_RELATION_PROPOSAL_TYPE,CourseSections.DOCUMENTS, getLabel(LUUIConstants.TOOL_DOCUMENTS_LABEL_KEY));
            documentTool.setModelDefinition((DataModelDefinition)modelDefinition);
            documentTool.setTitle(getLabel(LUUIConstants.TOOL_DOCUMENTS_LABEL_KEY));
            layout.addMenuItem(sections, documentTool);
            
            //Summary
            summaryConfigurer = GWT.create(CourseSummaryConfigurer.class);
            summaryConfigurer.init(type, state, groupName,(DataModelDefinition)modelDefinition, stmtTypes, (Controller)layout, COURSE_PROPOSAL_MODEL);
            layout.addSpecialMenuItem(summaryConfigurer.generateProposalSummarySection(true), "Review and Submit");
            
            //Add common buttons to sections except for sections with specific button behavior
            List<Enum<?>> excludedViews = new ArrayList<Enum<?>>();
            excludedViews.add(CourseSections.DOCUMENTS);
            excludedViews.add(CourseSections.COURSE_REQUISITES);
            layout.addCommonButton(LUUIConstantsUMD.PROPOSAL_SECTIONS, layout.getSaveButton(), excludedViews);
            layout.addCommonButton(LUUIConstantsUMD.PROPOSAL_SECTIONS, layout.getCancelButton(CourseSections.SUMMARY), excludedViews);

            //Specific buttons for certain views
            //TODO people and permissions will use a different button than continue
            layout.addButtonForView(CourseSections.DOCUMENTS, getContinueButton(layout));
        }
        else{
             summaryConfigurer = GWT.create(CourseSummaryConfigurer.class);
             summaryConfigurer.init(type, state, groupName, (DataModelDefinition)modelDefinition, stmtTypes, (Controller)layout, COURSE_PROPOSAL_MODEL);
             layout.removeMenuNavigation();
             layout.addView(summaryConfigurer.generateProposalSummarySection(false));
        }
        layout.showPrint(true);
        layout.setDefaultView(CourseSections.SUMMARY);
        layout.addContentWidget(layout.getWfUtilities().getProposalStatusLabel());
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

        
        final DecisionPanel decisionPanel = new DecisionPanel(CourseSections.DECISIONS, getLabel(LUUIConstants.TOOL_DECISION_LABEL_KEY), "kuali.comment.type.generalRemarks");
        layout.addView(decisionPanel);
        layout.addContentWidget(new KSButton("Decisions", ButtonStyle.DEFAULT_ANCHOR, new ClickHandler() {

            public void onClick(ClickEvent event) {
                decisionPanel.show();
            }
        }));
        
    }

    @Override
    public Section generateCourseInfoSection(Section section) {
        section.addSection(generateCourseNumberSection());
        addField(section, PROPOSAL_TITLE_PATH, generateMessageInfo(LUUIConstants.PROPOSAL_TITLE_LABEL_KEY));
        addField(section, COURSE + "/" + COURSE_TITLE, generateMessageInfo(LUUIConstants.COURSE_TITLE_LABEL_KEY), new KSCharCount(getMetaData(COURSE + "/" + COURSE_TITLE)));
        final FieldDescriptor transcriptTitle = addField(section, COURSE + "/" + TRANSCRIPT_TITLE,
                generateMessageInfo(LUUIConstants.SHORT_TITLE_LABEL_KEY),
                new KSCharCount(getMetaData(COURSE + "/" + TRANSCRIPT_TITLE)));     
        
        final KSCharCount transcriptInput = (KSCharCount) section.getField(QueryPath.parse(COURSE + "/" + TRANSCRIPT_TITLE).toString()).getFieldWidget();
        final int maxLength = section.getField(QueryPath.parse(COURSE + "/" + TRANSCRIPT_TITLE).toString()).getMetadata().getConstraints().get(0).getMaxLength();
        
        final KSCharCount courseInput = (KSCharCount) section.getField(QueryPath.parse(COURSE + "/" + COURSE_TITLE).toString()).getFieldWidget();
        ((TextBoxBase) courseInput.getInputWidget()).addValueChangeHandler(new ValueChangeHandler<String>() {

            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                if (courseInput.getText().length() <= maxLength) {
                    transcriptInput.setText(courseInput.getText().trim().toUpperCase());
                } else {
                    transcriptInput.setText(courseInput.getText().substring(0, maxLength).trim().toUpperCase());
                }
                transcriptTitle.getValidationRequestCallback().exec(true);
            }
            
        });
        
        ((TextBoxBase)(transcriptInput.getInputWidget())).addKeyUpHandler(new KeyUpHandler () {

            @Override
            public void onKeyUp(KeyUpEvent event) {
                transcriptInput.setText(transcriptInput.getText().toUpperCase());
            }
            
        });
        section.addSection(generateCrossListed_Ver_Joint_Section());
        
        section.addSection(generateDescriptionRationaleSection());
        section.addSection(generateGovernanceSection());
        section.addSection(generateAdditionalInformationSection());

        return section;
    }
    
    @Override
    protected GroupSection generateCourseNumberSection() {

        //COURSE NUMBER
        GroupSection courseNumber = new GroupSection(getH4Title(""));
        courseNumber.addStyleName(LUUIConstants.STYLE_SECTION);
        courseNumber.addStyleName(LUUIConstants.STYLE_SECTION_DIVIDER);
        addField(courseNumber, COURSE + "/" + SUBJECT_AREA, generateMessageInfo(LUUIConstants.SUBJECT_CODE_LABEL_KEY));
        addField(courseNumber, COURSE + "/" + COURSE_NUMBER_SUFFIX, generateMessageInfo(LUUIConstants.COURSE_NUMBER_LABEL_KEY));
        
        return courseNumber;
    }
    
    private Section generateAdditionalInformationSection() {
        SectionTitle title = getH4Title(UMDConstants.ADDITION_INFORMATION_SECTION_LABEL_KEY);
        VerticalSection additionlInfoSection = initSection(title, !WITH_DIVIDER);

        title.setStyleName("cluProposalTitleSection");
        addField(additionlInfoSection, COURSE + "/" + UMDConstants.COURSE_PCC_INFORMATION,
                generateMessageInfo(UMDConstants.COURSE_PCC_INFORMATION_LABEL_KEY));
        addField(additionlInfoSection, COURSE + "/" + UMDConstants.COURSE_AP_AND_IB,
                generateMessageInfo(UMDConstants.COURSE_AP_AND_IB_LABEL_KEY), new KSCheckBox(
                        getLabel(UMDConstants.COURSE_AP_AND_IB_TEXT)));
        addField(additionlInfoSection, COURSE + "/" + UMDConstants.COURSE_CORE_AND_GENERAL_EDCUCATION,
                generateMessageInfo(UMDConstants.COURSE_CORE_AND_GENERAL_EDCUCATION_LABEL_KEY));

        return additionlInfoSection;
    }

    @Override
    protected VerticalSection generateDescriptionRationaleSection() {
        SectionTitle title = getH4Title(LUUIConstants.PROPOSAL_TITLE_SECTION_LABEL_KEY);
        VerticalSection description = initSection(title, !WITH_DIVIDER);
        title.setStyleName("cluProposalTitleSection");
        String courseDescPlainKey = COURSE + "/" + DESCRIPTION + "/" + RichTextInfoConstants.PLAIN;
        addField(description, courseDescPlainKey, generateMessageInfo(LUUIConstants.DESCRIPTION_LABEL_KEY));        
        addField(description, COURSE + "/" + UMDConstants.ADDITIONAL_COURSE_INFORMATION, generateMessageInfo(UMDConstants.ADDITIONAL_COURSE_INFORMATION));
        addField(description, "proposal/rationale", generateMessageInfo(LUUIConstants.PROPOSAL_RATIONALE_LABEL_KEY),
                new KSCharCount(modelDefinition.getMetadata(QueryPath.parse("proposal/rationale"))));
        return description;
    }

    @Override
    public Section generateCourseLogisticsSection(Section section) {
        section.addSection(generateLearningResultsSection());
        section.addSection(generateCreditInformationSection());
        return section;
    }
    
    protected Section generateCreditInformationSection() {
        final VerticalSection creditInfoSection = initSection(getH3Title(UMDConstants.CREDIT_INFO_LABEL_KEY), WITH_DIVIDER);
        creditInfoSection.addSection(generateCreditOptionsSection());
        
        //Semester Type
        FieldDescriptor semesterTypeFD = addField(creditInfoSection, COURSE + "/" + UMDConstants.COURSE_SEMESTER_TYPE, generateMessageInfo(UMDConstants.COURSE_SEMESTER_TYPE_LABEL_KEY));

        // patch by Dyak of 3/7/2013 Jira KSCM-2187
        // this was trying to use a wrong type of widget
        if (semesterTypeFD.getFieldWidget() instanceof KSPicker) {
         KSPicker picker = (KSPicker) semesterTypeFD.getFieldWidget();
         final KSDropDown dropdown = (KSDropDown) picker.getInputWidget();
         dropdown.setBlankFirstItem(false);
        }
        
        //Hour Commitment per Week
        final CourseFormatWidget courseFormatWidget = new CourseFormatWidget(modelDefinition, COURSE, state, type, groupName);
        FieldDescriptor fd = addField(creditInfoSection, COURSE + "/" + FORMATS + "/*/" + ACTIVITIES,
                generateMessageInfo(UMDConstants.COMMITMENT_PER_WEEK), courseFormatWidget, COURSE);
       
        courseFormatWidget.addValueChangeHandler(new ValueChangeHandler<String>(){
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				creditInfoSection.setIsDirty(true);
			}        	
        });
        
        fd.setWidgetBinding(CourseFormatWidgetBinding.INSTANCE);  
        fd.getFieldElement().hideLabel();
        fd.getFieldElement().removeStyleName("ks-form-module-single-line-margin");
        fd.getFieldElement().setStyleName("activity-section");
        
        // Repeat Credits
        creditInfoSection.addSection(generateRepeatableCreditsSection());
        
        return creditInfoSection;
    }

    protected Section generateCreditOptionsSection() {
        Section creditOptionsSection = new VerticalSection();
        String path = COURSE + QueryPath.getPathSeparator() + CREDIT_OPTIONS;
        QueryPath creditOptionsTypePath = QueryPath.concat(path, "0",
                CreditCourseConstants.TYPE);
        QueryPath creditOptionFixedFullPath = QueryPath.concat(path, "0",
                CREDIT_OPTION_FIXED_CREDITS);
        QueryPath creditOptionMinFullPath = QueryPath.concat(path, "0",
                CREDIT_OPTION_MIN_CREDITS);
        QueryPath creditOptionMaxFullPath = QueryPath.concat(path, "0",
                CREDIT_OPTION_MAX_CREDITS);
        FieldDescriptor creditOptionsDescriptor = addField(creditOptionsSection, creditOptionsTypePath.toString(),
                generateMessageInfo(LUUIConstants.LEARNING_RESULT_OUTCOME_TYPE_LABEL_KEY));
        KSSelectItemWidgetAbstract picker = (KSSelectItemWidgetAbstract) (((KSPicker) creditOptionsDescriptor
                .getFieldWidget()).getInputWidget());
        VerticalSection fixedSection = new VerticalSection();
        VerticalSection variableSection = new VerticalSection();
        SwapSection swapSection = new SwapSection(picker);
        FieldDescriptor fixedCreditDescriptor = addField(fixedSection, creditOptionFixedFullPath.toString(),
                generateMessageInfo(LUUIConstants.CREDIT_OPTION_FIXED_CREDITS_LABEL_KEY), new CreditOptionGuidanceLabelUMD(getMetaData(creditOptionFixedFullPath.toString())));
        fixedCreditDescriptor.setIgnoreShowRequired(true);
        FieldDescriptor minCreditDescriptor = addField(variableSection, creditOptionMinFullPath.toString(),
                generateMessageInfo(LUUIConstants.CREDIT_OPTION_MIN_CREDITS_LABEL_KEY), new CreditOptionGuidanceLabelUMD(getMetaData(creditOptionMinFullPath.toString())));
        minCreditDescriptor.setIgnoreShowRequired(true);
        FieldDescriptor maxCreditDescriptor = addField(variableSection, creditOptionMaxFullPath.toString(),
                generateMessageInfo(LUUIConstants.CREDIT_OPTION_MAX_CREDITS_LABEL_KEY));
        maxCreditDescriptor.setIgnoreShowRequired(true);
        swapSection.addSection(fixedSection, "kuali.resultComponentType.credit.degree.fixed");
        swapSection.addSection(variableSection, "kuali.resultComponentType.credit.degree.range");
        creditOptionsSection.addSection(swapSection);
        swapSection.setSwapEventHandler(new SwapEventHandler() {

            @Override
            public void onShowSwappableSection(String key, Section section) {
                progressiveEnableAndRequireSection(true, section);
            }

            @Override
            public void onRemoveSwappableSection(String key, Section section) {
                progressiveEnableAndRequireSection(false, section);
            }

        });
        
        return creditOptionsSection;
    }
    
    private VerticalSection generateRepeatableCreditsSection() {
        VerticalSection repeatCreditsSection = new VerticalSection();
        HorizontalSection repeatableNumCreditsSection = new HorizontalSection();

        KSLabel repeatCreditsLabel = new KSLabel(getLabel(UMDConstants.COURSE_REPEAT_CREDITS));
        repeatCreditsLabel.setStyleName("ks-form-module-elements-instruction");
        repeatCreditsLabel.addStyleName("bold");
        repeatCreditsSection.addWidget(repeatCreditsLabel);
        KSLabel repeatCreditsInstructionsLabel = new KSLabel(getLabel(UMDConstants.COURSE_REPEAT_CREDITS
                + FieldLayoutComponent.INSTRUCT_MESSAGE_KEY));
        repeatCreditsInstructionsLabel.setStyleName("ks-form-module-elements-instruction");
        repeatCreditsSection.addWidget(repeatCreditsInstructionsLabel);

        final KSCheckBox repeatCredits = new KSCheckBox();
        repeatableNumCreditsSection.addWidget(repeatCredits);
        KSLabel repeatCreditsText = new KSLabel(getLabel(UMDConstants.COURSE_REPEAT_CREDITS_TEXT));
        repeatableNumCreditsSection.addWidget(repeatCreditsText);
        
        final FieldDescriptor repeatableNumCredits = addField(repeatCreditsSection, COURSE + "/"
                + UMDConstants.COURSE_REPEATABLE_NUM_CREDITS, null);
        KSTextBox repeatableNumCreditsTextBox = ((KSTextBox) repeatableNumCredits.getFieldWidget());
        repeatableNumCredits.getFieldElement().removeStyleName("ks-form-module-single-line-margin");
        KSLabel repeatableNumCreditsLabel = new KSLabel(getLabel(UMDConstants.COURSE_REPEATABLE_NUM_CREDITS_TEXT));
        repeatableNumCreditsSection.addWidget(repeatableNumCreditsLabel);
        repeatableNumCreditsSection.addWidget(repeatableNumCreditsTextBox);
        ((KSTextBox) repeatableNumCredits.getFieldWidget()).setReadOnly(true);
        repeatCreditsSection.addSection(repeatableNumCreditsSection);

        final FieldDescriptor repeatableIfContentDiffers = addField(repeatCreditsSection, COURSE + "/"
                + UMDConstants.COURSE_REPEATABLE_IF_CONTENT_DIFFERS, null, new KSCheckBox(
                getLabel(UMDConstants.COURSE_REPEATABLE_IF_CONTENT_DIFFERS_TEXT)));
        repeatableIfContentDiffers.getFieldElement().removeStyleName("ks-form-module-single-line-margin");
        repeatableIfContentDiffers.getFieldElement().addStyleName("KS-indented");
        ((KSCheckBox) repeatableIfContentDiffers.getFieldWidget()).setEnabled(false);

        repeatCredits.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                KSCheckBox checkbox = (KSCheckBox) event.getSource();
                if (checkbox.getValue() == true) {
                    ((KSTextBox) repeatableNumCredits.getFieldWidget()).setReadOnly(false);
                    repeatableNumCredits.getMetadata().getConstraints().get(0).setMinOccurs(1);
                    ((KSCheckBox) repeatableIfContentDiffers.getFieldWidget()).setEnabled(true);
                } else {
                    ((KSTextBox) repeatableNumCredits.getFieldWidget()).setReadOnly(true);
                    ((KSTextBox) repeatableNumCredits.getFieldWidget()).setText("");
                    repeatableNumCredits.getMetadata().getConstraints().get(0).setMinOccurs(0);
                    repeatableNumCredits.getFieldElement().clearValidationErrors();
                    ((KSCheckBox) repeatableIfContentDiffers.getFieldWidget()).setEnabled(false);
                    ((KSCheckBox) repeatableIfContentDiffers.getFieldWidget()).setValue(false);
                }
            }
        });
        
        ((KSTextBox) repeatableNumCredits.getFieldWidget()).addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                KSTextBox textBox = (KSTextBox) event.getSource();
                if (textBox.getText().length() > 0 && !repeatCredits.getValue()) {
                    repeatCredits.setValue(true, true);
                }
            }
        });

        return repeatCreditsSection;
    }

    @Override
    protected Section generateLearningResultsSection() {
        VerticalSection learningResults = initSection(null, false);
        learningResults.addSection(generateGradesAssessmentsSection());
        learningResults.addSection(generateStudentRegistrationOptionsSection());
        return learningResults;
    }

    @Override
    protected Section generateGradesAssessmentsSection() {
        VerticalSection gradesAssessments = initSection(
                getH3Title(LUUIConstants.LEARNING_RESULTS_GRADES_ASSESSMENTS_LABEL_KEY), WITH_DIVIDER);
        gradesAssessments.setInstructions(getLabel(LUUIConstants.LEARNING_RESULTS_GRADES_ASSESSMENTS_LABEL_KEY
                + "-instruct")
                + "<br><br>");
        FieldDescriptor assessmentScaleField = addField(gradesAssessments, COURSE + "/" + GRADING_OPTIONS, null);
        gradesAssessments.setRequired(assessmentScaleField.getFieldElement().getRequiredPanel());
        //Move the assessment options up, since we're not displaying a label.
        assessmentScaleField.getFieldElement().removeStyleName("ks-form-module-single-line-margin");
        return gradesAssessments;
    }

    @Override
    protected Section generateGovernanceSection(Section section) {
        return section;
    }

    @Override
    protected CollapsableSection generateCrossListed_Ver_Joint_Section() {
        CollapsableSection result = new CollapsableSection(getLabel(LUUIConstants.CL_V_J_LABEL_KEY));
        result.setStyleName("collapsible-link");
        
        SpanPanel crslabelpan = new SpanPanel();
        crslabelpan.setStyleName("ks-multiplicity-section-label");
        crslabelpan.getElement().setAttribute("style", "line-height: 1.9em;");
        crslabelpan.setHTML("Cross Listed Courses");
        crslabelpan.setVisible(true);
        result.addWidget(crslabelpan);
        LabelPanel crsDescription = new LabelPanel(getLabel(UMDConstants.COURSE_CROSS_LISTED_DESCRIPTION));
        crsDescription.getElement().setAttribute("style", "font-weight: normal;");
        result.addWidget(crsDescription);
        addMultiplicityFields(result, COURSE + QueryPath.getPathSeparator() + CROSS_LISTINGS,
                LUUIConstants.ADD_CROSS_LISTED_LABEL_KEY,
                LUUIConstants.CROSS_LISTED_ITEM_LABEL_KEY,
                Arrays.asList(
                        new MultiplicityFieldConfig(
                                CreditCourseJointsConstants.COURSE_ID,
                                LUUIConstants.COURSE_NUMBER_OR_TITLE_LABEL_KEY, null, null, true)),
                        null,
                        null, 0);
        SpanPanel jntlabelpan = new SpanPanel();
        jntlabelpan.setStyleName("ks-multiplicity-section-label");
        jntlabelpan.getElement().setAttribute("style", "line-height: 1.9em;");
        jntlabelpan.setHTML("Jointly Offered Courses");
        jntlabelpan.setVisible(true);
        result.addWidget(jntlabelpan);
        LabelPanel jntDescription = new LabelPanel(getLabel(UMDConstants.COURSE_JOINT_OFFERINGS_DESCRIPTION));
        jntDescription.getElement().setAttribute("style", "font-weight: normal;");
        result.addWidget(jntDescription);
        addMultiplicityFields(result, COURSE + QueryPath.getPathSeparator() + JOINTS,
                LUUIConstants.ADD_EXISTING_LABEL_KEY,
                LUUIConstants.JOINT_OFFER_ITEM_LABEL_KEY,
                Arrays.asList(
                        new MultiplicityFieldConfig(
                                CreditCourseJointsConstants.COURSE_ID,
                                LUUIConstants.COURSE_NUMBER_OR_TITLE_LABEL_KEY, null, null, true)),
                                null,
                                null, 0);

        addField(result, COURSE + "/" + UMDConstants.COURSE_FORMERLY,
                generateMessageInfo(UMDConstants.COURSE_FORMERLY_LABEL));

        // KSCM-950
        addField(result, COURSE + "/" + UMDConstants.COURSE_CREDIT_GRANTED_FOR,
                generateMessageInfo(UMDConstants.COURSE_CREDIT_GRANTED_FOR));

        return result;
    }

    @Override
    protected Section generateStudentRegistrationOptionsSection() {
        VerticalSection studentRegistrationOptionsSection = initSection(
                getH3Title(LUUIConstants.LEARNING_RESULTS_STUDENT_REGISTRATION_LABEL_KEY), WITH_DIVIDER);
        studentRegistrationOptionsSection
                .setHelp(getLabel(LUUIConstants.LEARNING_RESULTS_STUDENT_REGISTRATION_LABEL_KEY
                        + FieldLayoutComponent.HELP_MESSAGE_KEY));

        SpanPanel studentRegistrationOptionsInstruction = new SpanPanel();
        studentRegistrationOptionsInstruction.setStyleName("ks-form-module-elements-instruction");
        studentRegistrationOptionsInstruction
                .setHTML(getLabel(LUUIConstants.LEARNING_RESULTS_STUDENT_REGISTRATION_LABEL_KEY
                        + FieldLayoutComponent.INSTRUCT_MESSAGE_KEY));
        studentRegistrationOptionsInstruction.setVisible(true);
        studentRegistrationOptionsSection.addWidget(studentRegistrationOptionsInstruction);

        addField(studentRegistrationOptionsSection, COURSE + "/" + AUDIT,
                generateMessageInfo(LUUIConstants.LEARNING_RESULT_AUDIT_LABEL_KEY), new KSCheckBox(
                        getLabel(LUUIConstants.LEARNING_RESULT_AUDIT_TEXT_LABEL_KEY)));
        addField(studentRegistrationOptionsSection, COURSE + "/" + PASS_FAIL,
                generateMessageInfo(LUUIConstants.LEARNING_RESULT_PASS_FAIL_LABEL_KEY), new KSCheckBox(
                        getLabel(LUUIConstants.LEARNING_RESULT_PASS_FAIL_TEXT_LABEL_KEY)));

        return studentRegistrationOptionsSection;
    }

    protected VerticalSection generateGovernanceSection() {
        SectionTitle title = getH4Title(UMDConstants.COURSE_GOVERANCE);
        VerticalSection goveranceSection = initSection(title, !WITH_DIVIDER);

        title.setStyleName("cluProposalTitleSection");
        addField(goveranceSection, COURSE + "/" + CURRICULUM_OVERSIGHT_ORGS_,
                generateMessageInfo(LUUIConstants.ACADEMIC_SUBJECT_ORGS_KEY));

        return goveranceSection;
    }

    @Override
    protected Section generateActiveDatesSection(Section section) {
        section = super.generateActiveDatesSection(section);

        final FieldDescriptor startTerm = Application.getApplicationContext().getPathToFieldMapping(null,
                CreditCourseConstants.START_TERM);
        final FieldDescriptor pilotCourse = Application.getApplicationContext().getPathToFieldMapping(null,
                CreditCourseConstants.PILOT_COURSE);
        final FieldDescriptor endTerm = Application.getApplicationContext().getPathToFieldMapping(null,
                CreditCourseConstants.END_TERM);

        KSCheckBox pilotCheckbox = ((KSCheckBox) pilotCourse.getFieldWidget());
        pilotCheckbox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                if (event.getValue()) {
                    String startTermValue = ((KSDropDown) ((KSPicker) startTerm.getFieldWidget()).getInputWidget())
                            .getSelectedItem();
                    ((KSPicker) endTerm.getFieldWidget()).setValue(startTermValue);
                    endTerm.getValidationRequestCallback().exec(true);
                }
            }
        });

        return section;
    }

}
