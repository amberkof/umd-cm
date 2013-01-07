package edu.umd.ks.cm.ui.course.client.configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.Data.Property;
import org.kuali.student.common.assembly.data.LookupMetadata;
import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.search.dto.SearchResult;
import org.kuali.student.common.search.dto.SearchResultRow;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptorReadOnly;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ListToTextBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityConfiguration;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.service.CachingSearchService;
import org.kuali.student.common.ui.client.util.SearchUtils;
import org.kuali.student.common.ui.client.util.SearchUtils.SearchRequestInfoWrapper;
import org.kuali.student.common.ui.client.widgets.list.KSSelectedList;
import org.kuali.student.common.ui.client.widgets.list.SearchResultListItems;
import org.kuali.student.common.ui.client.widgets.table.summary.ShowRowConditionCallback;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldBlock;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldRow;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableSection;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.base.RichTextInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseJointsConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseProposalConfigurer.CourseSections;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseSummaryConfigurer;
import org.kuali.student.lum.lu.ui.course.client.configuration.ViewCourseConfigurer.ViewCourseSections;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseProposalController;
import org.kuali.student.lum.lu.ui.course.client.controllers.VersionsController;

import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import edu.umd.ks.cm.ui.course.client.widgets.logistics.CourseFormatWidget;

public class CourseSummaryConfigurerUMD extends CourseSummaryConfigurer {

    private final SearchRequestInfoWrapper activitySearchRequestInfoWrapper = new SearchRequestInfoWrapper();
    private final Map<String, String> activitySortTemplate = new HashMap<String, String>();
    private Map<String, String> activityTypeMap = new HashMap<String, String>();
    private Map<String, String> ratioMap = new HashMap<String, String>();
    private String lectureHoursKey;
    private String lectureCreditsKey;
    private String discussionHoursKey;
    private String discussionCreditsKey;
    private String labHoursKey;
    private String labCreditsKey;
    private String experientialHoursKey;
    private String experientialCreditsKey;

    private enum ActivitySummaryType {
        HOURS, CREDITS
    }

    public CourseSummaryConfigurerUMD(String type, String state, String groupName, DataModelDefinition modelDefinition,
            List<StatementTypeInfo> stmtTypes, Controller controller, String modelId) {
        super(type, state, groupName, modelDefinition, stmtTypes, controller, modelId);
    }

    public CourseSummaryConfigurerUMD() {

    }

    @Override
    protected void addSummaryTableFieldBlocks()
    {
        tableSection.addSummaryTableFieldBlock(generateCourseInformationForProposal());
        tableSection.addSummaryTableFieldBlock(generateGovernanceSection());
        tableSection.addSummaryTableFieldBlock(generateCourseLogisticsSection());
        tableSection.addSummaryTableFieldBlock(generateLearningObjectivesSection());
        tableSection.addSummaryTableFieldBlock(generateRequirementsSection());
        tableSection.addSummaryTableFieldBlock(generateActiveDatesSection());
        tableSection.addSummaryTableFieldBlock(generateFeesSection());
    }

    @Override
    public SummaryTableFieldBlock generateCourseInformationForProposal() {
        SummaryTableFieldBlock block = new SummaryTableFieldBlock();
        block.addEditingHandler(new EditHandler(CourseSections.COURSE_INFO));
        block.setTitle(getLabel(LUUIConstants.INFORMATION_LABEL_KEY));
        block.addSummaryTableFieldRow(getFieldRow(PROPOSAL_TITLE_PATH, generateMessageInfo(LUUIConstants.PROPOSAL_TITLE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + COURSE_TITLE, generateMessageInfo(LUUIConstants.COURSE_TITLE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + TRANSCRIPT_TITLE, generateMessageInfo(LUUIConstants.SHORT_TITLE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + SUBJECT_AREA, generateMessageInfo(LUUIConstants.SUBJECT_CODE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + COURSE_NUMBER_SUFFIX, generateMessageInfo(LUUIConstants.COURSE_NUMBER_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + PROPOSAL_DESCRIPTION + "/" + RichTextInfoConstants.PLAIN, generateMessageInfo(LUUIConstants.DESCRIPTION_LABEL_KEY)));

        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + UMDConstants.ADDITIONAL_COURSE_INFORMATION, generateMessageInfo(UMDConstants.ADDITIONAL_COURSE_INFORMATION)));

        block.addSummaryTableFieldRow(getFieldRow("proposal/rationale", generateMessageInfo(LUUIConstants.PROPOSAL_RATIONALE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + CURRICULUM_OVERSIGHT_ORGS_, generateMessageInfo(LUUIConstants.ACADEMIC_SUBJECT_ORGS_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + UMDConstants.COURSE_PCC_INFORMATION, generateMessageInfo(UMDConstants.COURSE_PCC_INFORMATION_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + UMDConstants.COURSE_AP_AND_IB, generateMessageInfo(UMDConstants.COURSE_AP_AND_IB_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + UMDConstants.COURSE_CORE_AND_GENERAL_EDCUCATION, generateMessageInfo(UMDConstants.COURSE_CORE_AND_GENERAL_EDCUCATION_LABEL_KEY)));
        block.addEditingHandler(new EditHandler(CourseSections.COURSE_INFO));

        block.addSummaryMultiplicity(getMultiplicityConfig(
                COURSE + QueryPath.getPathSeparator() + CROSS_LISTINGS,
                LUUIConstants.CROSS_LISTED_ITEM_LABEL_KEY,
                Arrays.asList(Arrays.asList(CreditCourseJointsConstants.COURSE_ID, LUUIConstants.COURSE_NUMBER_OR_TITLE_LABEL_KEY)))
                );

        block.addSummaryMultiplicity(getMultiplicityConfig(
                COURSE + QueryPath.getPathSeparator() + JOINTS,
                LUUIConstants.JOINT_OFFER_ITEM_LABEL_KEY,
                Arrays.asList(Arrays.asList(CreditCourseJointsConstants.COURSE_ID, LUUIConstants.COURSE_NUMBER_OR_TITLE_LABEL_KEY)))
                );

        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + UMDConstants.COURSE_FORMERLY, generateMessageInfo(UMDConstants.COURSE_FORMERLY_LABEL)));

        // KSCM-950
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + UMDConstants.COURSE_CREDIT_GRANTED_FOR, generateMessageInfo(UMDConstants.COURSE_CREDIT_GRANTED_FOR)));


        return block;
    }

    @Override
    public SummaryTableFieldBlock generateGovernanceSection() {
        return new SummaryTableFieldBlock();
    }

    @Override
    public SummaryTableFieldBlock generateCourseLogisticsSection() {
        final SummaryTableFieldBlock block = new SummaryTableFieldBlock();

        block.addEditingHandler(new EditHandler(CourseSections.COURSE_LOGISTICS));
        block.setTitle(getLabel(LUUIConstants.LOGISTICS_LABEL_KEY));

        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + GRADING_OPTIONS, generateMessageInfo(LUUIConstants.LEARNING_RESULTS_GRADES_ASSESSMENTS_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + PASS_FAIL, generateMessageInfo(LUUIConstants.LEARNING_RESULT_PASS_FAIL_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + AUDIT, generateMessageInfo(LUUIConstants.LEARNING_RESULT_AUDIT_LABEL_KEY)));

        // Credit Information
        final String path = COURSE + QueryPath.getPathSeparator() + CREDIT_OPTIONS;
        final QueryPath creditOptionsPath = QueryPath.concat(path, "0", CreditCourseConstants.TYPE);
        final QueryPath creditOptionFixedFullPath = QueryPath.concat(path, "0", CREDIT_OPTION_FIXED_CREDITS);
        final QueryPath creditOptionMinFullPath = QueryPath.concat(path, "0", CREDIT_OPTION_MIN_CREDITS);
        final QueryPath creditOptionMaxFullPath = QueryPath.concat(path, "0", CREDIT_OPTION_MAX_CREDITS);

        block.addSummaryTableFieldRow(getFieldRow(creditOptionsPath.toString(), generateMessageInfo(LUUIConstants.LEARNING_RESULT_OUTCOME_TYPE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(creditOptionFixedFullPath.toString(), generateMessageInfo(LUUIConstants.CREDIT_OPTION_FIXED_CREDITS_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(creditOptionMinFullPath.toString(), generateMessageInfo(LUUIConstants.CREDIT_OPTION_MIN_CREDITS_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(creditOptionMaxFullPath.toString(), generateMessageInfo(LUUIConstants.CREDIT_OPTION_MAX_CREDITS_LABEL_KEY)));

        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + UMDConstants.COURSE_SEMESTER_TYPE, generateMessageInfo(UMDConstants.COURSE_SEMESTER_TYPE_LABEL_KEY)));

        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + UMDConstants.COURSE_REPEATABLE_NUM_CREDITS, generateMessageInfo(UMDConstants.COURSE_REPEATABLE_NUM_CREDITS_LABEL)));

        // KSCM-1646
        lectureHoursKey = buildActivityKey(0, ActivitySummaryType.HOURS);
        block.addSummaryTableFieldRow(getFieldRow(lectureHoursKey, generateMessageInfo(UMDConstants.COURSE_LECTURE_HOURS_PER_WEEK_LABEL_KEY)));
        lectureCreditsKey = buildActivityKey(0, ActivitySummaryType.CREDITS);
        block.addSummaryTableFieldRow(getFieldRow(lectureCreditsKey, generateMessageInfo(UMDConstants.COURSE_LECTURE_CREDITS_LABEL_KEY)));

        discussionHoursKey = buildActivityKey(1, ActivitySummaryType.HOURS);
        block.addSummaryTableFieldRow(getFieldRow(discussionHoursKey, generateMessageInfo(UMDConstants.COURSE_DISCUSSION_HOURS_PER_WEEK_LABEL_KEY)));
        discussionCreditsKey = buildActivityKey(1, ActivitySummaryType.CREDITS);
        block.addSummaryTableFieldRow(getFieldRow(discussionCreditsKey, generateMessageInfo(UMDConstants.COURSE_DISCUSSION_CREDITS_LABEL_KEY)));

        labHoursKey = buildActivityKey(2, ActivitySummaryType.HOURS);
        block.addSummaryTableFieldRow(getFieldRow(labHoursKey, generateMessageInfo(UMDConstants.COURSE_LAB_HOURS_PER_WEEK_LABEL_KEY)));
        labCreditsKey = buildActivityKey(2, ActivitySummaryType.CREDITS);
        block.addSummaryTableFieldRow(getFieldRow(labCreditsKey, generateMessageInfo(UMDConstants.COURSE_LAB_CREDITS_LABEL_KEY)));

        experientialHoursKey = buildActivityKey(3, ActivitySummaryType.HOURS);
        block.addSummaryTableFieldRow(getFieldRow(experientialHoursKey, generateMessageInfo(UMDConstants.COURSE_EXPERIENTIAL_HOURS_PER_WEEK_LABEL_KEY)));
        experientialCreditsKey = buildActivityKey(3, ActivitySummaryType.CREDITS);
        block.addSummaryTableFieldRow(getFieldRow(experientialCreditsKey, generateMessageInfo(UMDConstants.COURSE_EXPERIENTIAL_CREDITS_LABEL_KEY)));

        //Activity Totals
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + UMDConstants.COURSE_ACTIVITY_TOTAL_CONTACT_HOURS,
                generateMessageInfo(UMDConstants.COURSE_ACTIVITY_TOTAL_CONTACT_HOURS_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + UMDConstants.COURSE_ACTIVITY_TOTAL_CREDITS,
                generateMessageInfo(UMDConstants.COURSE_ACTIVITY_ALLOWABLE_CREDITS_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + UMDConstants.COURSE_ACTIVITY_TYPE_EXPLANATION,
                generateMessageInfo(UMDConstants.COURSE_ACTIVITY_TYPE_EXPLANATION_LABEL_KEY)));

        // KS-1539 putting a tableSection.addShowRowCallback here to ensure
        // values for the keys above are actually generated before running logic on them.
        // previously checks were being doing against keys which had no chance to build

        tableSection.addShowRowCallback(new ShowRowConditionCallback() {
            @Override
            public void processShowConditions(SummaryTableFieldRow row, DataModel column1, DataModel column2) {
                //Show only the fixed credits / Hide the variable range credits
                if (row.getFieldDescriptor1() != null && 
                        (row.getFieldDescriptor1().getFieldKey().contains(QueryPath.parse(lectureHoursKey != null ? lectureHoursKey : "").toString())
                                || row.getFieldDescriptor1().getFieldKey().contains(QueryPath.parse(discussionHoursKey != null ? discussionHoursKey : "").toString())
                                || row.getFieldDescriptor1().getFieldKey().contains(labHoursKey != null ? labHoursKey : "") 
                                || row.getFieldDescriptor1().getFieldKey().contains(experientialHoursKey != null ? experientialHoursKey : ""))) {
                    String semesterTypeValue = getSemesterTypeFromModel(column1);
                    if (!CourseFormatWidget.SEMESTER_TYPE_STANDARD.equals(semesterTypeValue)) 
                        row.setShown(false);
                } else if (row.getFieldDescriptor1() != null && 
                        (row.getFieldDescriptor1().getFieldKey().contains(lectureCreditsKey != null ? lectureCreditsKey : "")
                                || row.getFieldDescriptor1().getFieldKey().contains(discussionCreditsKey != null ? discussionCreditsKey : "")
                                || row.getFieldDescriptor1().getFieldKey().contains(labCreditsKey != null ? labCreditsKey : "") 
                                || row.getFieldDescriptor1().getFieldKey().contains(experientialCreditsKey != null ? experientialCreditsKey : ""))) {
                    String semesterTypeValue = getSemesterTypeFromModel(column1);
                    if (!CourseFormatWidget.SEMESTER_TYPE_NON_STANDARD.equals(semesterTypeValue)) 
                        row.setShown(false);
                } 
            }
        }); 
        // End KSCM-1646        

        tableSection.addShowRowCallback(new ShowRowConditionCallback() {

            @Override
            public void processShowConditions(SummaryTableFieldRow row, DataModel column1, DataModel column2) {
                //Show only the fixed credits / Hide the variable range credits
                if (row.getFieldDescriptor1() != null) {
                    if (row.getFieldDescriptor1().getFieldKey().contains(creditOptionFixedFullPath.toString())) {
                        //KSCM-1886 somehow, this row object is losing it's "required" attribute, forcing the issue here
                        row.setRequired(true);
                        Object data1 = null;
                        Object data2 = null;
                        if (column1 != null) {
                            data1 = column1.get(creditOptionsPath.toString());
                        }
                        if (column2 != null) {
                            data2 = column2.get(creditOptionsPath.toString());
                        }

                        if (data1 != null && data1 instanceof String) {
                            if (!((String) data1).equals("kuali.resultComponentType.credit.degree.fixed")) {
                                row.setShown(false);
                            }
                        } else if (data2 != null && data2 instanceof String) {
                            if (!((String) data2).equals("kuali.resultComponentType.credit.degree.fixed")) {
                                row.setShown(false);
                            }
                        }
                        //Show only the variable range credits / Hide the fixed credits
                    } else if (row.getFieldDescriptor1().getFieldKey().contains(creditOptionMinFullPath.toString()) || 
                            row.getFieldDescriptor1().getFieldKey().contains(creditOptionMaxFullPath.toString())) {
                        //KSCM-1886 somehow, this row object is losing it's "required" attribute, forcing the issue here
                        row.setRequired(true);
                        Object data1 = null;
                        Object data2 = null;
                        if (column1 != null) {
                            data1 = column1.get(creditOptionsPath.toString());
                        }
                        if (column2 != null) {
                            data2 = column2.get(creditOptionsPath.toString());
                        }

                        if (data1 != null && data1 instanceof String) {
                            if (!((String) data1).equals("kuali.resultComponentType.credit.degree.range")) {
                                row.setShown(false);
                            }
                        } else if (data2 != null && data2 instanceof String) {
                            if (!((String) data2).equals("kuali.resultComponentType.credit.degree.range")) {
                                row.setShown(false);
                            }
                        }
                        // Changing the value for 'repeatableNumCredit' from a number value to more descriptive text.
                    } else if (row.getFieldDescriptor1().getFieldKey().contains(UMDConstants.COURSE_REPEATABLE_NUM_CREDITS)) {
                        String repeatableNumCredits = column1.get(QueryPath.concat(COURSE, "/", UMDConstants.COURSE_REPEATABLE_NUM_CREDITS));
                        Boolean repeatableIfContentDiffers = column1.get(QueryPath.concat(COURSE, "/", UMDConstants.COURSE_REPEATABLE_IF_CONTENT_DIFFERS));

                        if (null != repeatableNumCredits && row.getCell1() instanceof HasText) {
                            String newValue = repeatableIfContentDiffers ? "Up to " + repeatableNumCredits
                                    + " credits if content differs" : "Up to " + repeatableNumCredits + " credits";
                            ((HasText) row.getCell1()).setText(newValue);
                        }
                    } else if (row.getFieldDescriptor1().getFieldKey().contains(UMDConstants.COURSE_ACTIVITY_TYPE_EXPLANATION)) {
                        String semesterTypeValue = getSemesterTypeFromModel(column1);
                        if (CourseFormatWidget.SEMESTER_TYPE_STANDARD.equals(semesterTypeValue)) {
                            row.setShown(false);
                        }
                    } else if (row.getFieldDescriptor1().getFieldKey().contains(UMDConstants.COURSE_ACTIVITY_TOTAL_CREDITS)) {
                        String semesterTypeValue = getSemesterTypeFromModel(column1);
                        if (CourseFormatWidget.SEMESTER_TYPE_NON_STANDARD.equals(semesterTypeValue)) {
                            String totalActivityCreditsLabel = Application.getApplicationContext().getMessage(
                                    UMDConstants.COURSE_ACTIVITY_TOTAL_CREDITS_LABEL_KEY);
                            if (totalActivityCreditsLabel != null) {
                                row.setTitle(totalActivityCreditsLabel);
                            }
                        }
                    }
                }
            }
        });

        return block;
    }

    private String buildActivityKey(int index, ActivitySummaryType type) {
        StringBuilder sb = new StringBuilder();
        sb.append(COURSE);
        sb.append("/");
        sb.append(FORMATS);
        sb.append("/");
        sb.append("0");
        sb.append("/");
        sb.append(ACTIVITIES);
        sb.append("/");
        sb.append(index);
        sb.append("/");
        switch (type) {
            case HOURS:
                sb.append(CONTACT_HOURS);
                sb.append("/");
                sb.append(UMDConstants.COURSE_ACTIVITY_HOURS_UNIT_QUANTITY);
                break;
            case CREDITS:
                sb.append(UMDConstants.COURSE_ACTIVITY_HOURS_CREDITS);
                break;
        }
        QueryPath queryPath = QueryPath.parse(sb.toString());
        return queryPath.toString();
    }

    private String getSemesterTypeFromModel(DataModel dataModel) {
        if (dataModel != null) {
            return dataModel.get(QueryPath.concat(COURSE, "/", UMDConstants.COURSE_SEMESTER_TYPE));
        }
        return null;
    }

    @Override
    public SummaryTableFieldBlock generateFeesSection() {
        SummaryTableFieldBlock block = new SummaryTableFieldBlock();
        return block;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public VerticalSectionView generateCourseBriefSection() {
        SummaryTableSection courseBriefSection = new SummaryTableSection(controller);
        SummaryTableFieldBlock block = new SummaryTableFieldBlock();

        courseBriefSection.setEditable(false);
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + COURSE_TITLE,
                generateMessageInfo(LUUIConstants.COURSE_TITLE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + "code",
                generateMessageInfo(UMDConstants.COURSE_PREFIX_NUMBER_LABEL)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + PROPOSAL_DESCRIPTION + "/"
                + RichTextInfoConstants.PLAIN, generateMessageInfo(LUUIConstants.DESCRIPTION_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + CURRICULUM_OVERSIGHT_ORGS_,
                generateMessageInfo(LUUIConstants.ACADEMIC_SUBJECT_ORGS_KEY)));

        Map<String, ModelWidgetBinding> customBindings = new HashMap<String, ModelWidgetBinding>();
        ListToTextBinding resultValuesBinding = new ListToTextBinding();
        customBindings.put("resultValues", resultValuesBinding);
        String outcomesKey = COURSE + QueryPath.getPathSeparator() + CREDIT_OPTIONS;
        MultiplicityConfiguration outcomesConfig = getMultiplicityConfig(
                outcomesKey,
                null,
                Arrays.asList(
                        Arrays.asList(CreditCourseConstants.TYPE, LUUIConstants.LEARNING_RESULT_OUTCOME_TYPE_LABEL_KEY),
                        Arrays.asList(CREDIT_OPTION_FIXED_CREDITS, LUUIConstants.CREDITS_LABEL_KEY, OPTIONAL),
                        Arrays.asList(CREDIT_OPTION_MIN_CREDITS, LUUIConstants.CREDIT_OPTION_MIN_CREDITS_LABEL_KEY,
                                OPTIONAL),
                                Arrays.asList(CREDIT_OPTION_MAX_CREDITS, LUUIConstants.CREDIT_OPTION_MAX_CREDITS_LABEL_KEY,
                                        OPTIONAL),
                                        Arrays.asList("resultValues", LUUIConstants.CREDIT_OPTION_FIXED_CREDITS_LABEL_KEY, OPTIONAL)),
                                        customBindings);

        // Massive workaround for result values problem where we dont want to show them on certain selections,
        // in most cases you want to just use the optional flag and have it be based on empty/null data
        // but since this data is sometimes not empty/null when we dont want to show it, it requires a show
        // condition callback
        courseBriefSection.addShowRowCallback(new ShowRowConditionCallback() {
            @Override
            public void processShowConditions(SummaryTableFieldRow row, DataModel column1, DataModel column2) {
                if (row.getFieldDescriptor1() != null
                        && row.getFieldDescriptor1().getFieldKey().contains(CREDIT_OPTIONS)
                        && row.getFieldDescriptor1().getFieldKey().contains("resultValues")) {
                    String type = row.getFieldDescriptor1().getFieldKey()
                            .replace("resultValues", CreditCourseConstants.TYPE);
                    Object data1 = null;
                    Object data2 = null;
                    if (column1 != null) {
                        data1 = column1.get(type);
                    }
                    if (column2 != null) {
                        data2 = column2.get(type);
                    }

                    if (data1 != null && data1 instanceof String) {
                        if (!((String) data1).equals("kuali.resultComponentType.credit.degree.multiple")) {
                            row.setShown(false);
                        }
                    } else if (data2 != null && data2 instanceof String) {
                        if (!((String) data2).equals("kuali.resultComponentType.credit.degree.multiple")) {
                            row.setShown(false);
                        }
                    }
                    // Removing some styling so the text is aligned with the rest of the information in the tabel.
                } else if (null != row.getFieldDescriptor1()
                        && row.getFieldDescriptor1().getFieldKey().contains(CURRICULUM_OVERSIGHT_ORGS_)
                        && row.getCell1() instanceof KSSelectedList) {
                    ComplexPanel mainPanel = ((KSSelectedList) row.getCell1()).getMainPanel();
                    Iterator<Widget> mainPanelIterator = mainPanel.iterator();
                    while (mainPanelIterator.hasNext()) {
                        ComplexPanel listPanel = (ComplexPanel) mainPanelIterator.next();
                        Iterator<Widget> listPanelIterator = listPanel.iterator();
                        while (listPanelIterator.hasNext()) {
                            listPanelIterator.next().removeStyleName("ks-selected-list-value");
                        }
                    }
                }

            }
        });
        block.addSummaryMultiplicity(outcomesConfig);

        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + GRADING_OPTIONS,
                generateMessageInfo(LUUIConstants.LEARNING_RESULTS_GRADES_ASSESSMENTS_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + PASS_FAIL,
                generateMessageInfo(LUUIConstants.LEARNING_RESULT_PASS_FAIL_LABEL_KEY), true));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + AUDIT,
                generateMessageInfo(LUUIConstants.LEARNING_RESULT_AUDIT_LABEL_KEY), true));
        courseBriefSection.addSummaryTableFieldBlock(block);

        VerticalSectionView verticalSection = new VerticalSectionView(ViewCourseSections.BRIEF, "At a Glance", modelId,
                false);
        verticalSection.addSection(courseBriefSection);

        return verticalSection;
    }

    @SuppressWarnings("unchecked")
    @Override
    public SummaryTableFieldBlock generateCourseInformation() {
        SummaryTableFieldBlock block = new SummaryTableFieldBlock();

        block.addEditingHandler(new EditHandler(CourseSections.COURSE_INFO));
        block.setTitle(getLabel(LUUIConstants.INFORMATION_LABEL_KEY));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + COURSE_TITLE,
                generateMessageInfo(LUUIConstants.COURSE_TITLE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + TRANSCRIPT_TITLE,
                generateMessageInfo(LUUIConstants.SHORT_TITLE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + SUBJECT_AREA,
                generateMessageInfo(LUUIConstants.SUBJECT_CODE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + COURSE_NUMBER_SUFFIX,
                generateMessageInfo(LUUIConstants.COURSE_NUMBER_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + CURRICULUM_OVERSIGHT_ORGS_,
                generateMessageInfo(LUUIConstants.ACADEMIC_SUBJECT_ORGS_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + PROPOSAL_DESCRIPTION + "/"
                + RichTextInfoConstants.PLAIN, generateMessageInfo(LUUIConstants.DESCRIPTION_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + UMDConstants.ADDITIONAL_COURSE_INFORMATION,
                generateMessageInfo(UMDConstants.ADDITIONAL_COURSE_INFORMATION)));

        tableSection.addShowRowCallback(new ShowRowConditionCallback() {
            // Removing some styling so the text is aligned with the rest of the information in the tabel.
            public void processShowConditions(SummaryTableFieldRow row, DataModel column1, DataModel column2) {
                if (null != row.getFieldDescriptor1()
                        && row.getFieldDescriptor1().getFieldKey().contains(CURRICULUM_OVERSIGHT_ORGS_)
                        && row.getCell1() instanceof KSSelectedList) {
                    ComplexPanel mainPanel = ((KSSelectedList) row.getCell1()).getMainPanel();
                    Iterator<Widget> mainPanelIterator = mainPanel.iterator();
                    while (mainPanelIterator.hasNext()) {
                        ComplexPanel listPanel = (ComplexPanel) mainPanelIterator.next();
                        Iterator<Widget> listPanelIterator = listPanel.iterator();
                        while (listPanelIterator.hasNext()) {
                            listPanelIterator.next().removeStyleName("ks-selected-list-value");
                        }
                    }
                }
            }
        });
        
//KSCM-2063 Merging Cross Listings section with Course Information
        block.addSummaryMultiplicity(getMultiplicityConfig(
                COURSE + QueryPath.getPathSeparator() + CROSS_LISTINGS,
                "Cross Listing",
                Arrays.asList(Arrays.asList(SUBJECT_AREA, LUUIConstants.SUBJECT_CODE_LABEL_KEY),
                        Arrays.asList(COURSE_NUMBER_SUFFIX, LUUIConstants.COURSE_NUMBER_LABEL_KEY))));
        block.addSummaryMultiplicity(getMultiplicityConfig(COURSE + QueryPath.getPathSeparator() + JOINTS,
                LUUIConstants.JOINT_OFFER_ITEM_LABEL_KEY, Arrays.asList(Arrays.asList(
                        CreditCourseJointsConstants.COURSE_ID, LUUIConstants.COURSE_NUMBER_OR_TITLE_LABEL_KEY))));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + UMDConstants.COURSE_FORMERLY,
                generateMessageInfo(UMDConstants.COURSE_FORMERLY_LABEL)));
        // KSCM-950
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + UMDConstants.COURSE_CREDIT_GRANTED_FOR,
                generateMessageInfo(UMDConstants.COURSE_CREDIT_GRANTED_FOR)));

        return block;
    }

    @Override
    public VerticalSectionView generateCourseSummarySection() {
        tableSection.setEditable(false);
        tableSection.addSummaryTableFieldBlock(generateCourseInformation());
//        tableSection.addSummaryTableFieldBlock(generateCourseInformationCrossListing());
        tableSection.addSummaryTableFieldBlock(generateCourseLogisticsSection());
        tableSection.addSummaryTableFieldBlock(generateLearningObjectivesSection());
        tableSection.addSummaryTableFieldBlock(generateRequirementsSection());
        tableSection.addSummaryTableFieldBlock(generateActiveDatesSection());
        tableSection.addSummaryTableFieldBlock(generateFeesSection());

        VerticalSectionView verticalSection = new VerticalSectionView(
                ViewCourseSections.DETAILED,
                getLabel(LUUIConstants.SUMMARY_LABEL_KEY), modelId, false);
        verticalSection.addSection(tableSection);

        return verticalSection;
    }
/*
    @SuppressWarnings("unchecked")
    @Override
    public SummaryTableFieldBlock generateCourseInformationCrossListing() {
        SummaryTableFieldBlock block = new SummaryTableFieldBlock();

        block.addEditingHandler(new EditHandler(CourseSections.COURSE_INFO));
        block.setTitle("Cross Listings");

        block.addSummaryMultiplicity(getMultiplicityConfig(
                COURSE + QueryPath.getPathSeparator() + CROSS_LISTINGS,
                "Cross Listing",
                Arrays.asList(Arrays.asList(SUBJECT_AREA, LUUIConstants.SUBJECT_CODE_LABEL_KEY),
                        Arrays.asList(COURSE_NUMBER_SUFFIX, LUUIConstants.COURSE_NUMBER_LABEL_KEY))));
        block.addSummaryMultiplicity(getMultiplicityConfig(COURSE + QueryPath.getPathSeparator() + JOINTS,
                LUUIConstants.JOINT_OFFER_ITEM_LABEL_KEY, Arrays.asList(Arrays.asList(
                        CreditCourseJointsConstants.COURSE_ID, LUUIConstants.COURSE_NUMBER_OR_TITLE_LABEL_KEY))));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + UMDConstants.COURSE_FORMERLY,
                generateMessageInfo(UMDConstants.COURSE_FORMERLY_LABEL)));
        // KSCM-950
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + UMDConstants.COURSE_CREDIT_GRANTED_FOR,
                generateMessageInfo(UMDConstants.COURSE_CREDIT_GRANTED_FOR)));

        return block;
    }
*/
    @Override
    public SummaryTableFieldBlock generateRequirementsSection() {
        final SummaryTableFieldBlock block = new SummaryTableFieldBlock();

        block.addEditingHandler(new EditHandler(CourseSections.COURSE_REQUISITES));
        block.setTitle(getLabel(LUUIConstants.REQUISITES_LABEL_KEY));

        // one row per requirement type
        for (StatementTypeInfo stmtType : stmtTypes) {
            SummaryTableFieldRow arow;

            if ("kuali.statement.type.course.credit.restriction".equalsIgnoreCase(stmtType.getId())
                    || "kuali.statement.type.course.credit.repeatable".equalsIgnoreCase(stmtType.getId())) {
                continue;
            }
            if (controller instanceof VersionsController || controller instanceof CourseProposalController) {
                arow = new SummaryTableFieldRow(addRequisiteField(new FlowPanel(), stmtType), addRequisiteFieldComp(
                        new FlowPanel(), stmtType));
            } else {
                arow = new SummaryTableFieldRow(addRequisiteField(new FlowPanel(), stmtType), addRequisiteField(
                        new FlowPanel(), stmtType));
            }

            block.addSummaryTableFieldRow(arow);
        }

        return block;
    }

    @Override
    public VerticalSectionView generateCourseCatalogSection() {
        VerticalSectionView verticalSection = new VerticalSectionView(
                ViewCourseSections.CATALOG, "Catalog View", modelId, false);
        FieldDescriptorReadOnly catalogField = new FieldDescriptorReadOnly("",
                null, null, new HTML());
        catalogField.hideLabel();
        catalogField.setWidgetBinding(new ModelWidgetBinding<HTML>() {

            @Override
            public void setModelValue(HTML widget, DataModel model, String path) { //Don't place a breakpoint here:  It will stall debugging for some unknown reason!
            }

            @Override
            public void setWidgetValue(HTML widget, DataModel model, String path) { //Don't place a breakpoint here:  It will stall debugging for some unknown reason!
                String code = model.get("code");
                String title = model.get(COURSE + "/" + COURSE_TITLE);
                String credits = "";
                String outcomesKey = COURSE + QueryPath.getPathSeparator()
                        + CREDIT_OPTIONS;
                Data outcomes = model.get(outcomesKey);
                if (outcomes != null) {
                    Iterator<Property> iter = outcomes.realPropertyIterator();
                    String list = "";
                    ListToTextBinding binding = new ListToTextBinding();
                    while (iter.hasNext()) {
                        Property prop = iter.next();
                        if (prop.getKey() instanceof Integer) {
                            Integer number = (Integer) prop.getKey();
                            Object value = outcomes.get(number);
                            if (value instanceof Data) {
                                list = list
                                        + binding.getStringList(model,
                                                outcomesKey + "/" + number
                                                + "/" + "resultValues")
                                                + ", ";
                            }
                        }
                    }

                    if (!list.isEmpty()) {
                        List<String> creditList = Arrays.asList(list.trim().split(","));
                        if (creditList.size() > 1) {
                            credits = "(" + creditList.get(0) + " - " + creditList.get(creditList.size() - 1) + ")";
                        } else {
                            credits = "(" + creditList.get(0) + ")";
                        }
                    }
                }

                String description = model.get(COURSE + "/"
                        + PROPOSAL_DESCRIPTION + "/"
                        + RichTextInfoConstants.PLAIN);
                String catalogText = "<b> " + code + " " + title + " "
                        + credits + "</b> " + description + " ";
                catalogText.replace(" null ", "");
                catalogText.trim();
                widget.setHTML(catalogText);
            }
        });

        verticalSection.addField(catalogField);

        return verticalSection;
    }
}
