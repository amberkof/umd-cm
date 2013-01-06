package edu.umd.ks.cm.ui.course.client.configuration;

import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.RequiredContainer;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.widgets.KSCharCount;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.base.RichTextInfoConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseProposalConfigurer;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseAdminController;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseAdminWithoutVersionController;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseProposalController;
import org.kuali.student.lum.lu.ui.course.client.requirements.CourseRequirementsViewController;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import edu.umd.ks.cm.ui.common.client.lu.LUUIConstantsUMD;

public class CourseAdminWithoutVersionConfigurerUMD extends CourseProposalConfigurerUMD {
	protected CourseRequirementsViewController requisitesSection;
	
	private RequiredContainer requiredContainer = new RequiredContainer();
	
    /**
     * Sets up all the views, sections, and views of the CourseAdminController.  This should be called
     * once for initialization and setup per CourseAdminController instance.
     * 
     * @param layout
     */
    @Override
	public void configure(final CourseProposalController layout) {
    	type = "course";
        state = DtoConstants.STATE_APPROVED;
        nextState = DtoConstants.STATE_ACTIVE;
        
    	groupName = LUUIConstants.COURSE_GROUP_NAME;

        KSLabel courseStatusLabel = new KSLabel("");
        if (layout.getCourseState() != null)
        	courseStatusLabel.setText("Status: " + layout.getCourseState());
        else
        	courseStatusLabel.setText("Status: Unknown");
        layout.addContentWidget(courseStatusLabel); 
        		
        layout.addInfoWidget(requiredContainer);
    	layout.addView(generateCourseAdminView((CourseAdminWithoutVersionController)layout));
    }

	/**
	 * Configuration for the course admin screens
	 * 
	 * @return view 
	 */
    protected View generateCourseAdminView(final CourseAdminWithoutVersionController layout) {
        VerticalSectionView view = 
        	new VerticalSectionView(CourseSections.COURSE_INFO, getLabel(LUUIConstants.INFORMATION_LABEL_KEY), COURSE_PROPOSAL_MODEL, false);
        view.addStyleName(LUUIConstants.STYLE_SECTION);

        // Create course admin sections
        Section courseSection = generateCourseInfoSection(initSection(LUUIConstants.INFORMATION_LABEL_KEY)); 
        Section logisticsSection = generateCourseLogisticsSection(initSection(LUUIConstants.LOGISTICS_LABEL_KEY));
        Section loSection = initSection(LUUIConstants.LEARNING_OBJECTIVES_LABEL_KEY);
        loSection.addSection(generateLearningObjectivesNestedSection());
        Section activeDatesSection = generateActiveDatesSection(initSection(LUUIConstants.ACTIVE_DATES_LABEL_KEY));
//        Section financialSection = generateFinancialsSection(initSection(LUUIConstants.FINANCIALS_LABEL_KEY));
        Section retireSection = generateRetirementSection(initSection(LUUIConstants.RETIREMENT_LABEL_KEY));
        
        //Create requisite sections.
        requisitesSection = new CourseRequirementsViewController(layout, getLabel(LUUIConstants.REQUISITES_LABEL_KEY), CourseSections.COURSE_REQUISITES, false, false);
        
        //Add course admin sections to view
        view.addSection(courseSection);
        view.addSection(logisticsSection);
        view.addSection(loSection);   
        view.addView(requisitesSection);
        view.addSection(this.createHiddenRequisitesSection());
        view.addSection(activeDatesSection);
//        view.addSection(financialSection);
        view.addSection(retireSection);
        
        //Add menu items for sections
        String sections = getLabel(LUUIConstantsUMD.PROPOSAL_SECTIONS);
        layout.addMenu(sections);
        layout.addMenuItemSection(sections, getLabel(LUUIConstants.INFORMATION_LABEL_KEY), LUUIConstants.INFORMATION_LABEL_KEY, courseSection.getLayout());
        layout.addMenuItemSection(sections, getLabel(LUUIConstants.LOGISTICS_LABEL_KEY), LUUIConstants.LOGISTICS_LABEL_KEY, logisticsSection.getLayout());
        layout.addMenuItemSection(sections, getLabel(LUUIConstants.LEARNING_OBJECTIVE_LABEL_KEY), LUUIConstants.LEARNING_OBJECTIVE_LABEL_KEY, loSection.getLayout());
        layout.addMenuItemSection(sections, getLabel(LUUIConstants.REQUISITES_LABEL_KEY), LUUIConstants.REQUISITES_LABEL_KEY, requisitesSection);
        layout.addMenuItemSection(sections, getLabel(LUUIConstants.ACTIVE_DATES_LABEL_KEY), LUUIConstants.ACTIVE_DATES_LABEL_KEY, activeDatesSection.getLayout());
//        layout.addMenuItemSection(sections, getLabel(LUUIConstants.FINANCIALS_LABEL_KEY), LUUIConstants.FINANCIALS_LABEL_KEY, financialSection.getLayout());
        layout.addMenuItemSection(sections, getLabel(LUUIConstants.RETIREMENT_LABEL_KEY), LUUIConstants.RETIREMENT_LABEL_KEY, retireSection.getLayout());        
        
        //Add buttons to top and bottom of view
        layout.addButtonForView(CourseSections.COURSE_INFO, layout.getSaveButton());
        layout.addButtonForView(CourseSections.COURSE_INFO, layout.getCancelButton());
        layout.addTopButtonForView(CourseSections.COURSE_INFO, layout.getSaveButton());        
        layout.addTopButtonForView(CourseSections.COURSE_INFO, layout.getCancelButton());    

        requiredContainer.setMainSection(view);
        
        return view;
	}
    
    /**
     * Override {@link CourseProposalConfigurer#generateCourseInfoSection(Section)} to remove configuration
     * of proposal fields.
     *  
     */
    @Override
    public Section generateCourseInfoSection(Section section) {
        section.addSection(generateCourseNumberSection());
        addField(section, PROPOSAL_TITLE_PATH, generateMessageInfo(LUUIConstants.PROPOSAL_TITLE_LABEL_KEY));
        addField(section, COURSE + "/" + COURSE_TITLE, generateMessageInfo(LUUIConstants.COURSE_TITLE_LABEL_KEY), new KSCharCount(getMetaData(COURSE + "/" + COURSE_TITLE)));
        addField(section, COURSE + "/" + TRANSCRIPT_TITLE, generateMessageInfo(LUUIConstants.SHORT_TITLE_LABEL_KEY),
                new KSCharCount(getMetaData(COURSE + "/" + TRANSCRIPT_TITLE)));

        final KSCharCount transcriptInput = (KSCharCount) section.getField(QueryPath.parse(COURSE + "/" + TRANSCRIPT_TITLE).toString()).getFieldWidget();
        final int maxLength = section.getField(QueryPath.parse(COURSE + "/" + TRANSCRIPT_TITLE).toString()).getMetadata().getConstraints().get(0).getMaxLength();
        
        final KSCharCount courseInput = (KSCharCount) section.getField(QueryPath.parse(COURSE + "/" + COURSE_TITLE).toString()).getFieldWidget();
        courseInput.addBlurHandler(new BlurHandler () {

            public void onBlur(BlurEvent event) {
                if (courseInput.getText().length() <= maxLength) {
                    transcriptInput.setText(courseInput.getText().trim().toUpperCase());
                } else {
                    transcriptInput.setText(courseInput.getText().substring(0, maxLength).trim().toUpperCase());
                }
            }
            
        });
        section.addSection(generateCrossListed_Ver_Joint_Section());
        section.addSection(generateDescriptionRationaleSection());
        section.addSection(generateGovernanceSection());

        return section;
    }
    
    /**
     * Override  {@link CourseProposalConfigurer#generateDescriptionRationaleSection()} to remove configuration of
     * proposal fields.
     * 
     */
    @Override
    protected VerticalSection generateDescriptionRationaleSection() {
        SectionTitle title = getH4Title(LUUIConstants.PROPOSAL_TITLE_SECTION_LABEL_KEY);
        VerticalSection description = initSection(title, !WITH_DIVIDER);
        title.setStyleName("cluProposalTitleSection");
        String courseDescPlainKey = COURSE + "/" + DESCRIPTION + "/" + RichTextInfoConstants.PLAIN;
        addField(description, courseDescPlainKey, generateMessageInfo(LUUIConstants.DESCRIPTION_LABEL_KEY),
                new KSCharCount(getMetaData(courseDescPlainKey)));
        addField(description, COURSE + "/" + UMDConstants.ADDITIONAL_COURSE_INFORMATION, generateMessageInfo(UMDConstants.ADDITIONAL_COURSE_INFORMATION));
        return description;
    }
    
    public CourseRequirementsViewController getRequisitesSection(CourseAdminController layout) {
		if(requisitesSection == null){
			requisitesSection = new CourseRequirementsViewController(layout, getLabel(LUUIConstants.REQUISITES_LABEL_KEY), CourseSections.COURSE_REQUISITES, false, false);
		}
    	return requisitesSection;
	}

    protected Section generateRetirementSection(Section section) {
        addField(section, COURSE + "/" + RETIREMENT_RATIONALE, generateMessageInfo(LUUIConstants.RETIREMENT_RATIONALE_LABEL_KEY));
        addField(section, COURSE + "/" + LAST_TERM_OFFERED, generateMessageInfo(LUUIConstants.LAST_TERM_OFFERED_LABEL_KEY));
        addField(section, COURSE + "/" + LAST_PUBLICATION_YEAR, generateMessageInfo(LUUIConstants.LAST_PUBLICATION_YEAR_LABEL_KEY));
        addField(section, COURSE + "/" + SPECIAL_CIRCUMSTANCES, generateMessageInfo(LUUIConstants.SPECIAL_CIRCUMSTANCES_LABEL_KEY));
        
        return section;
    }    
    
    protected VerticalSection initSection(String labelKey) {
        final VerticalSection section = initSection(SectionTitle.generateH2Title(getLabel(labelKey)), NO_DIVIDER);
        // Add Show All Link on the sections.
        section.addShowAllLink(requiredContainer.createShowAllLink(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                requiredContainer.processInnerSection(section, true);
                section.getShowAllLink().setVisible(false);
            }
        }));

        return section;
    }
    
    private VerticalSection createHiddenRequisitesSection() {
        final VerticalSection section = initSection(SectionTitle.generateH2Title(getLabel(LUUIConstants.REQUISITES_LABEL_KEY)), NO_DIVIDER);
        // Add Show All Link on the sections.
        section.addShowAllLink(requiredContainer.createShowAllLink(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                requisitesSection.asWidget().setVisible(true);
                section.getLayout().setVisible(false);
            }
        }));
        
        // Setup show/hide non-required fields configuration.
        requiredContainer.addCallback(new Callback<Boolean>() {

            @Override
            public void exec(Boolean result) {
                requisitesSection.asWidget().setVisible(result);
                section.getLayout().setVisible(!result);
            }
        });
        return section;
    }

	public CourseRequirementsViewController getRequisitesSection() {
		return requisitesSection;
	}
    
}
