package edu.umd.ks.cm.ui.course.client.requirements;

import java.util.List;

import org.kuali.student.common.dto.RichTextInfo;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract;
import org.kuali.student.common.ui.client.widgets.dialog.ConfirmationDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrButton;
import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrButton.AbbrButtonType;
import org.kuali.student.common.ui.client.widgets.field.layout.element.LabelPanel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.core.statement.ui.client.widgets.rules.SubrulePreviewWidget;
import org.kuali.student.lum.lu.ui.course.client.configuration.AbstractCourseConfigurer;
import org.kuali.student.lum.lu.ui.course.client.requirements.CourseRequirementsManageView;
import org.kuali.student.lum.lu.ui.course.client.requirements.CourseRequirementsSummaryView;
import org.kuali.student.lum.lu.ui.course.client.requirements.CourseRequirementsViewController;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.umd.ks.cm.ui.course.client.widgets.rules.RuleInfoHeader;

public class CourseRequirementsSummaryViewUMD extends CourseRequirementsSummaryView {
	boolean displayInitialized = false;
    @Override
    public void displayRules() {
    	remove(layout);
        layout.clear();

        //display 'Course Requirements' page title (don't add if read only because the section itself will display the title)
        if (!isReadOnly) {
            SectionTitle pageTitle = SectionTitle.generateH2Title("Course Requisites");
            pageTitle.addStyleName("ks-layout-header"); //make the header orange
            layout.add(pageTitle);
        }

        //iterate and display rules for each Course Requirement type e.g. Pre-Requisites, Co-Requisites, Anti-Requisites
        boolean firstSubHeader = true;
        for (StatementTypeInfo stmtTypeInfo : rules.getStmtTypes()) {
            if ("kuali.statement.type.course.creditConstraints".equalsIgnoreCase(stmtTypeInfo.getId())) {
                continue;
            }

            //Show only headers for top statement types
            if (isTopStatement(stmtTypeInfo)) {
                SectionTitle title = SectionTitle.generateH3Title(stmtTypeInfo.getName());
                title.addStyleName("KS-Course-Requisites-Top-Stmt-Header");
                layout.add(title);
                firstSubHeader = true;
                continue;
            }

            //create and display one type of Course Requisites section
            SpanPanel requirementsPanel = new SpanPanel();
            perCourseRequisiteTypePanel.put(stmtTypeInfo.getId(), requirementsPanel);
            displayRequirementSectionForGivenType(requirementsPanel, stmtTypeInfo, firstSubHeader);
            firstSubHeader = false;

            //now display each requirement for this Course Requisites type; should be only one for courses
            for (StatementTreeViewInfo ruleInfo : rules.getCourseReqInfo(stmtTypeInfo.getId())) {
            	if (!ruleInfo.getStatements().isEmpty() || !ruleInfo.getReqComponents().isEmpty()) {
            		SubrulePreviewWidget subrulePreviewWidget = addCourseRequisite(requirementsPanel, ruleInfo);
            		requirementsPanel.add(subrulePreviewWidget);
            	}	
            }
        }

        //save and cancel buttons
        if (!isReadOnly && showSaveButtons) {
            layout.add(actionCancelButtons);
        }

        addWidget(layout);
        
        displayInitialized = true;
    }

    @Override
    protected void displayRequirementSectionForGivenType(final SpanPanel requirementsPanel,
            final StatementTypeInfo stmtTypeInfo, boolean firstSubHeader) {

        if ("kuali.statement.type.course.credit.repeatable".equalsIgnoreCase(stmtTypeInfo.getId()) ||
                "kuali.statement.type.course.credit.restriction".equalsIgnoreCase(stmtTypeInfo.getId())) {
            return;
        }

        //display header for this Course Requisites type e.g. Enrollment Eligibility
        SectionTitle title = SectionTitle.generateH3Title(stmtTypeInfo.getName());
        title.setStyleName((firstSubHeader ? "KS-Course-Requisites-Preview-Rule-Type-First-Header"
                : "KS-Course-Requisites-Preview-Rule-Type-Header"));
        layout.add(title);

        LabelPanel labelExamples = new LabelPanel(stmtTypeInfo.getDescr());
        labelExamples.getElement().setAttribute("style", "font-weight: normal; width: 80%;");

        String examplesHtml = Application.getApplicationContext().getMessage(stmtTypeInfo.getId());

        HorizontalPanel spacer0 = new HorizontalPanel();
        spacer0.addStyleName("KS-Course-Requisites-Button-Spacer");
        labelExamples.add(spacer0);

        layout.add(labelExamples);

        //display "Add Rule" button if user is in 'edit' mode OR a rule is already defined
        final String stmtId = stmtTypeInfo.getId();
        if (!isReadOnly) { // && rules.getCourseReqInfo(stmtId).isEmpty()) {
            KSButton addCourseReqButton = new KSButton("Add " + stmtTypeInfo.getName(),
                    KSButtonAbstract.ButtonStyle.FORM_SMALL);
            addCourseReqButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {

                    storeRules(false, new Callback<Boolean>() {
                        @Override
                        public void exec(Boolean courseExists) {
                            if (courseExists) {
                                final StatementTreeViewInfo newRule = new StatementTreeViewInfo();
                                newRule.setId(generateStatementTreeId());
                                newRule.setType(stmtId);
                                RichTextInfo text = new RichTextInfo();
                                text.setPlain("");
                                newRule.setDesc(text);
                                parentController.getView(
                                        CourseRequirementsViewController.CourseRequirementsViews.MANAGE,
                                        new Callback<View>() {
                                            @Override
                                            public void exec(View result) {
                                                RuleInfoHeader ruleHeading = ((CourseRequirementsManageViewUMD) result)
                                                        .getRequisiteInfo();
                                                ruleHeading.updateRuleHeading(stmtTypeInfo);
                                                rules.addRule(newRule);
                                                ((CourseRequirementsManageView) result).setRuleTree(newRule, true,
                                                        rules.getInternalCourseReqID(newRule));
                                                parentController
                                                        .showView(CourseRequirementsViewController.CourseRequirementsViews.MANAGE);
                                            }
                                        });
                            }
                        }
                    });
                };
            });
            if (!rules.getCourseReqInfo(stmtId).isEmpty()) {
            	boolean checkReqs = true;
            	for(StatementTreeViewInfo ruleInfo : rules.getCourseReqInfo(stmtId)) {
            		if (!ruleInfo.getStatements().isEmpty() || !ruleInfo.getReqComponents().isEmpty()) {
            			checkReqs = false;
            			break;
            		}
            	}    
            	addCourseReqButton.setVisible(checkReqs);
            } else
            	addCourseReqButton.setVisible(rules.getCourseReqInfo(stmtId).isEmpty());
            addButtonsList.put(stmtId, addCourseReqButton);
            layout.add(addCourseReqButton);
            VerticalPanel spacer = new VerticalPanel();
            spacer.addStyleName("KS-Course-Requisites-Button-Spacer");
            layout.add(spacer);
        }

        layout.add(requirementsPanel);
    }

    @Override
    protected void addRulePreviewWidgetHandlers(final SpanPanel requirementsPanel,
            final SubrulePreviewWidget subRuleWidget, final String stmtTypeId, final Integer internalProgReqID) {

        subRuleWidget.addEditButtonClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                parentController.getView(CourseRequirementsViewController.CourseRequirementsViews.MANAGE,
                        new Callback<View>() {
                            @Override
                            public void exec(View result) {
                                ((CourseRequirementsManageViewUMD) result).setNewSubRule(true);
                                ((CourseRequirementsManageView) result).setRuleTree(rules.getRule(internalProgReqID),
                                        false, internalProgReqID);
                                parentController
                                        .showView(CourseRequirementsViewController.CourseRequirementsViews.MANAGE);
                            }
                        });
            }
        });

        subRuleWidget.addDeleteButtonClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //deleting subrule does not delete the requirement (rule) itself
                rules.markRuleAsEdited(internalProgReqID);
                //remove rule from display and storage
                rules.deleteRule(internalProgReqID);
                requirementsPanel.remove(subRuleWidget);
                addButtonsList.get(stmtTypeId).setVisible(true);
            }
        });
    }

    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {
        //only when user wants to see rules then load requirements from database if they haven't been loaded yet
        if (!rules.isInitialized()) {
            rules.retrieveCourseRequirements(AbstractCourseConfigurer.COURSE_PROPOSAL_MODEL, new Callback<Boolean>() {
                @Override
                public void exec(Boolean result) {
                    if (result) {
                        displayRules();
                    }
                    onReadyCallback.exec(result);
                }
            });

            return;
        }

        //for read-only view, we don't need to worry about rules being added or modified
        if (isReadOnly) {
            displayRules();
            onReadyCallback.exec(true);

            return;
        }
        
        //Check if the display was ever drawn (it's being set to false in ()
        if(!displayInitialized){
        	displayRules();
        }
        
        //see if we need to update a rule if user is returning from rule manage screen
        parentController.getView(CourseRequirementsViewController.CourseRequirementsViews.MANAGE, new Callback<View>() {
            @Override
            public void exec(View result) {
                CourseRequirementsManageViewUMD manageView = (CourseRequirementsManageViewUMD) result;

                //return if user did not added or updated a rule
                if (!manageView.isDirty() || !manageView.isUserClickedSaveButton()) {
                    rules.removeEmptyRules();
                    onReadyCallback.exec(true);

                    return;
                }

                //update the rule because user added or edited the rule
                ((SectionView) parentController.getCurrentView()).setIsDirty(false);
                manageView.setUserClickedSaveButton(false);

                //if rule storage updated successfully, update the display as well
                StatementTreeViewInfo affectedRule = rules.updateRules(manageView.getRuleTree(),
                        manageView.getInternalCourseReqID(), manageView.isNewRule());
                updateRequirementWidgets(affectedRule);
                manageView.setNewRule(false);
                manageView.setNewSubRule(false);

                onReadyCallback.exec(true);
            }
        });
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void storeRules(final boolean storeRules, final Callback<Boolean> callback) {
        parentController.requestModel(CourseRequirementsViewController.COURSE_PROPOSAL_MODEL,
                new ModelRequestCallback() {
                    @Override
                    public void onRequestFail(Throwable cause) {
                        Window.alert(cause.getMessage());
                        GWT.log("Unable to retrieve model for course requirements view", cause);
                        callback.exec(false);
                    }

                    @Override
                    public void onModelReady(Model model) {
                        final String courseId = ((DataModel) model).getRoot().get("id");
                        final String courseState = ((DataModel) model).getRoot().get("state");
                        if (courseId == null) {
                            final ConfirmationDialog dialog = new ConfirmationDialog("Submit Course Title",
                                    "Before saving rules please submit course proposal title");

                            dialog.getConfirmButton().addClickHandler(new ClickHandler() {
                                @Override
                                public void onClick(ClickEvent event) {
                                    dialog.hide();
                                }
                            });

                            dialog.show();
                            callback.exec(false);
                        } else {
                            if (storeRules) {
                                //see if we need to update a rule if user is returning from rule manage screen
                                parentController.getView(
                                        CourseRequirementsViewController.CourseRequirementsViews.MANAGE,
                                        new Callback<View>() {
                                            @Override
                                            public void exec(View result) {
                                                CourseRequirementsManageViewUMD manageView = (CourseRequirementsManageViewUMD) result;

                                                //if rule storage updated successfully, update the display as well
                                                if (null != manageView.getRuleManageWidget()
                                                        && null != manageView.getRuleTree()
                                                        && (manageView.isNewRule() || manageView.isNewSubRule())) {
                                                    StatementTreeViewInfo affectedRule = rules.updateRules(
                                                            manageView.getRuleTree(),
                                                            manageView.getInternalCourseReqID(),
                                                            manageView.isNewRule());
                                                    updateRequirementWidgets(affectedRule);
                                                }

                                                rules.updateCourseRequisites(courseId, courseState,
                                                        new Callback<List<StatementTreeViewInfo>>() {
                                                            @Override
                                                            public void exec(List<StatementTreeViewInfo> rules) {
                                                                for (StatementTreeViewInfo rule : rules) {
                                                                    updateRequirementWidgets(rule);
                                                                }
                                                                callback.exec(true);
                                                            }
                                                        });
                                            }
                                        });
                            } else {
                                callback.exec(true);
                            }
                        }
                    }
                });
    }

}
