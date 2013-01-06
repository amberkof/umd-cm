package edu.umd.ks.cm.ui.course.client.widgets.logistics;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.assembly.data.LookupMetadata;
import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.assembly.data.MetadataInterrogator;
import org.kuali.student.common.assembly.data.ModelDefinition;
import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.search.dto.SearchResult;
import org.kuali.student.common.search.dto.SearchResultRow;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.configurable.mvc.CanProcessValidationResults;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.event.ValidateRequestEvent;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.service.CachingSearchService;
import org.kuali.student.common.ui.client.util.SearchUtils;
import org.kuali.student.common.ui.client.util.SearchUtils.SearchRequestWrapper;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.field.layout.element.FieldElement;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.common.ui.client.widgets.list.KSLabelList;
import org.kuali.student.common.ui.client.widgets.list.KSRadioButtonList;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.SearchResultListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.common.validation.dto.ValidationResultInfo.ErrorLevel;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseActivityConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseFormatConstants;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LabelBase;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

import edu.umd.ks.cm.ui.common.client.widgets.CreditOptionGuidanceLabelUMD;
import edu.umd.ks.cm.ui.course.client.configuration.UMDConstants;

public class CourseFormatWidget extends VerticalSection implements HasValueChangeHandlers<String>,
        CreditCourseConstants,
        CreditCourseFormatConstants, CreditCourseActivityConstants, UMDConstants, CanProcessValidationResults {

	
    protected CachingSearchService cachingSearchService = CachingSearchService.getSearchService();
    protected SearchRequestWrapper searchRequestWrapper = new SearchRequestWrapper();
    protected FlexTable activityTable = new FlexTable();
    protected DecimalFormat format = new DecimalFormat("#.##");
    protected ModelDefinition modelDefinition;
    protected List<String> header = Arrays.asList("Activity", "Hours/Week", "Credit Ratio", "Contact Hours", "Credits");
    protected ErrorLevel errorLevel = ErrorLevel.OK;
    protected double creditRatioConstant = COURSE_SEMESTER_DURATION;
    protected boolean onTheFlyValidation = true;
    private String semesterType = "";
    public static String SEMESTER_TYPE_STANDARD = "semesterType.standard";
    public static String SEMESTER_TYPE_NON_STANDARD = "semesterType.nonStandard";
    public static String UNIT_TYPE = "kuali.atp.duration.week";
    protected boolean activityTypesInitialized = false;
    protected boolean ratiosInitialized = false;
    protected Map<String, String> activityTypeMap = new HashMap<String, String>();
    protected Map<String, String> ratioMap = new HashMap<String, String>();
    protected Map<String, String> sortTemplate = new HashMap<String, String>();
    protected Map<String, String> typeRatioMap = new HashMap<String, String>();
    protected VerticalSection creditInfoSection;
    protected VerticalSection container = new VerticalSection();
    protected VerticalSection activitySection = new VerticalSection();
    protected VerticalSection explanationSection = new VerticalSection();
    protected VerticalSection labelSection = new VerticalSection();
    public boolean widgetInitialized = false;
    public boolean tableInitialized = false;
    protected Callback<DataModel> widgetReadyCallback;
    protected DataModel model;
    protected String currentSemesterType = "";
    protected String previousSemesterType = "";
    private String parentPath = "";
    public static String state = "";
    public static String type = "";
    public static String groupName = "";
    
    public boolean hasFixedHandlers = false;
    public boolean hasRangeHandlers = false;
    
    String path = parentPath + QueryPath.getPathSeparator() + CREDIT_OPTIONS;
    QueryPath creditOptionsTypePath = QueryPath.concat(path, "0",
                CreditCourseConstants.TYPE);
    QueryPath creditOptionFixedFullPath = QueryPath.concat(path, "0",
                CREDIT_OPTION_FIXED_CREDITS);
    QueryPath creditOptionMinFullPath = QueryPath.concat(path, "0",
                CREDIT_OPTION_MIN_CREDITS);
    QueryPath creditOptionMaxFullPath = QueryPath.concat(path, "0",
            CREDIT_OPTION_MAX_CREDITS);
    
    protected FieldElement fieldElement = null;

    public CourseFormatWidget(ModelDefinition modelDefinition, String parentPath, String state, String type,
            String groupName) {
        super();

        this.add(labelSection);
        this.modelDefinition = modelDefinition;
        this.parentPath = parentPath;
        CourseFormatWidget.state = state;
        CourseFormatWidget.type = type;
        CourseFormatWidget.groupName = groupName;

        registerEventsAndHandlers();

        activitySection.add(activityTable);
        this.add(activitySection);
        this.add(explanationSection);

    }

    public void setWidgetLabel() {

        if (semesterType.equalsIgnoreCase(SEMESTER_TYPE_STANDARD)) {
            fieldElement = new FieldElement("", new MessageKeyInfo(groupName, type, state,
                    UMDConstants.COMMITMENT_PER_WEEK));
        } else if (semesterType.equalsIgnoreCase(SEMESTER_TYPE_NON_STANDARD)) {
            fieldElement = new FieldElement("", new MessageKeyInfo(groupName, type, state,
                    UMDConstants.CREDITS_PER_ACTIVITY_TYPE));
        }

        if (fieldElement != null) {
            setupRequiredField(fieldElement);
            for (int i = 0; i < labelSection.getWidgetCount(); i++) {
                labelSection.remove(i);
            }
            labelSection.add(fieldElement);
            fieldElement.addStyleName("activity-section-title");
        }

    }

    protected void setupRequiredField(FieldElement fieldElement) {
        Metadata metadata = this.modelDefinition.getMetadata(parentPath + "/" + UMDConstants.COURSE_ACTIVITY_TOTAL_CONTACT_HOURS);

        if(metadata != null){
            if(MetadataInterrogator.isRequired(metadata)){
                fieldElement.setRequiredString("requiredMarker", "ks-form-module-elements-required");
            }
            else if(MetadataInterrogator.isRequiredForNextState(metadata)){
                String nextState = MetadataInterrogator.getNextState(metadata);
                if(nextState != null){
                    if(nextState.equalsIgnoreCase("SUBMITTED")){
                        fieldElement.setRequiredString("requiredOnSubmit", "ks-form-required-for-submit");
                    }
                    else if(nextState.equalsIgnoreCase("APPROVED")){
                        fieldElement.setRequiredString("reqApproval", "ks-form-required-for-submit");
                    }
                    else if(nextState.equalsIgnoreCase("ACTIVE")){
                        fieldElement.setRequiredString("reqActivate", "ks-form-required-for-submit");
                    }
                    else if(nextState.equalsIgnoreCase("SUSPENDED") ||
                            nextState.equalsIgnoreCase("RETIRED")){
                        fieldElement.setRequiredString("reqDeactivate", "ks-form-required-for-submit");
                    }
                    else {
                        fieldElement.setRequiredString("requiredOnSubmit", "ks-form-required-for-submit");
                    }

                }
            } else{
                fieldElement.clearRequiredText();
            }
        }
    }

    public void init() {
        if (!semesterType.isEmpty()) {
            //Initialise Fields then initialise Table
            initFields(new Callback<Boolean>() {

                @Override
                public void exec(Boolean result) {
                    if (activityTypesInitialized && ratiosInitialized) {
                        doActivityToRatioMapping();
                        widgetInitialized = true;
                        initTable();
                    }
                }

            });
        }
    }

    public void setModel(DataModel model) {
        this.model = model;
    }

    public void setWidgetReadyCallback(Callback<DataModel> widgetReadyCallback) {
        this.widgetReadyCallback = widgetReadyCallback;
    }

    public Callback<DataModel> getWidgetReadyCallback() {
        return widgetReadyCallback;
    }

    public void registerEventsAndHandlers() {

        FieldDescriptor semesterType = getFieldDescriptor(null, QueryPath.parse(UMDConstants.COURSE_SEMESTER_TYPE));

        
        KSPicker picker = (KSPicker) semesterType.getFieldWidget();
        final KSDropDown dropdown = (KSDropDown) picker.getInputWidget();

        picker.addSelectionChangeHandler(new SelectionChangeHandler() {

            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                if (dropdown.getSelectedItem() != null) {
                    previousSemesterType = CourseFormatWidget.this.semesterType;
                    currentSemesterType = dropdown.getSelectedItem();
                    CourseFormatWidget.this.semesterType = dropdown.getSelectedItem();

                    if (!previousSemesterType.equals(currentSemesterType)) {
                        redraw(dropdown.getSelectedItem());
                        setWidgetLabel();
                    }
                }
            }

        });

        //Modelwidgetbinding calls setValue before widgets fields are initialized
        this.setWidgetReadyCallback(new Callback<DataModel>() {

            @Override
            public void exec(DataModel model) {
                if (model != null) {
                    FieldDescriptor fd = getFieldDescriptor(null,
                            QueryPath.parse(parentPath + "/" + FORMATS + "/*/" + ACTIVITIES));
                    ((CourseFormatWidgetBinding) fd.getModelWidgetBinding()).setWidgetValue(CourseFormatWidget.this,
                            model,
                            QueryPath.parse(parentPath + "/" + FORMATS + "/*/" + ACTIVITIES).toString());

                    initEmptyFields();
                }
            }

        });

        KSPicker creditOptionsPicker = (KSPicker) getFieldDescriptor(null, creditOptionsTypePath).getFieldWidget();
        final KSRadioButtonList radio = (KSRadioButtonList) creditOptionsPicker.getInputWidget();

        radio.addSelectionChangeHandler(new SelectionChangeHandler() {

            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                if (radio.getSelectedItem() != null) {
                	setIsDirty(false);
                    ValueChangeEvent.fire(CourseFormatWidget.this, "");
                    if (radio.getSelectedItem().equals("kuali.resultComponentType.credit.degree.fixed") && !hasFixedHandlers) {
                        FieldDescriptor creditOptionFixed = getFieldDescriptor(null, creditOptionFixedFullPath);
                        TextBoxBase fixedTB = (TextBoxBase) ((CreditOptionGuidanceLabelUMD) creditOptionFixed.getFieldWidget()).getInputWidget();
                        fixedTB.addValueChangeHandler(new ValueChangeHandler<String>() {

                            @Override
                            public void onValueChange(ValueChangeEvent<String> event) {
                                ValueChangeEvent.fire(CourseFormatWidget.this, event.getValue());
                            }
                        });
                        hasFixedHandlers = true;

                    } else if (radio.getSelectedItem().equals("kuali.resultComponentType.credit.degree.range") && !hasRangeHandlers) {
                        FieldDescriptor creditOptionMin = getFieldDescriptor(null, creditOptionMinFullPath);
                        TextBoxBase minTB =   (TextBoxBase) ((CreditOptionGuidanceLabelUMD) creditOptionMin.getFieldWidget()).getInputWidget();
                        minTB.addValueChangeHandler(new ValueChangeHandler<String>() {

                            @Override
                            public void onValueChange(ValueChangeEvent<String> event) {
                                ValueChangeEvent.fire(CourseFormatWidget.this, event.getValue());
                            }
                        });

                        FieldDescriptor creditOptionMax = getFieldDescriptor(null, creditOptionMaxFullPath);
                        KSTextBox maxTB =  (KSTextBox) creditOptionMax.getFieldWidget();
                        maxTB.addValueChangeHandler(new ValueChangeHandler<String>() {

                            @Override
                            public void onValueChange(ValueChangeEvent<String> event) {
                                ValueChangeEvent.fire(CourseFormatWidget.this, event.getValue());

                            }

                        });
                        hasRangeHandlers = true;
                    }
                }
            }
        });
    }

    public void doActivityToRatioMapping() {

        for (String ratioKey : ratioMap.keySet()) {
            typeRatioMap.put(ratioKey.substring(0, ratioKey.indexOf(".ratio")), ratioMap.get(ratioKey));
        }
    }
    
    public FieldElement addTitleWidget(String title) {
        title = "courseFormat" + title.replaceAll(" ", "");
        FieldElement element = new FieldElement("", new MessageKeyInfo(groupName, type, state,
                title));
        element.removeStyleName("ks-form-module-single-line-margin");
        return element;
    }

    public void redraw(String semesterType) {

        if (activityTable.getRowCount() == 0) {
            if (!widgetInitialized) {
                init();
            } else {
                initTable();
            }
        } else {
            if (semesterType.equals(SEMESTER_TYPE_NON_STANDARD)) {

                activityTable.getCellFormatter().addStyleName(0, header.indexOf("Hours/Week"),
                        "activity-table-cell-header-hidden");

                for (int index = 0; index < activityTypeMap.size(); index++) {
                    FieldDescriptor fd = container.getField(buildPath(parentPath, "contactHours/unitQuantity", index));

                    if (fd != null) {
                        activityTable.remove(container.getField(
                                buildPath(parentPath, "contactHours/unitQuantity", index))
                                .getFieldWidget());

                        container.removeField(buildPath(parentPath, "contactHours/unitQuantity", index));
                        container.removeField(buildPath(parentPath, "contactHours/unitType", index));
                        container.removeField(buildPath(parentPath, "credits", index));
                    }
                    addField(parentPath, buildPath(parentPath, "credits", index),
                                new KSTextBox(), index + 1, header.indexOf("Credits"));

                    //                        calculate(index + 1, false);

                }

                addExplanationField(parentPath, UMDConstants.COURSE_ACTIVITY_TYPE_EXPLANATION, new MessageKeyInfo("course", "course", "",
                        UMDConstants.COURSE_ACTIVITY_TYPE_EXPLANATION_LABEL_KEY));


                tableInitialized = true;

                if(getLayoutController()!=null){
	               	this.getLayoutController().requestModel(new ModelRequestCallback<DataModel>(){
						public void onModelReady(DataModel model) {
							widgetReadyCallback.exec(model);
						}
						public void onRequestFail(Throwable cause) {
							widgetReadyCallback.exec(model);
						}});
                }else{
            		widgetReadyCallback.exec(model);
                }

            } else if (semesterType.equals(SEMESTER_TYPE_STANDARD)) {

                activityTable.getCellFormatter().removeStyleName(0, header.indexOf("Hours/Week"),
                        "activity-table-cell-header-hidden");

                for (int index = 0; index < activityTypeMap.size(); index++) {
                    addField(parentPath, buildPath(parentPath, "contactHours/unitQuantity", index),
                            new KSTextBox(), index + 1, header.indexOf("Hours/Week"));
                    addField(parentPath, buildPath(parentPath, "contactHours/unitType", index),
                            new KSLabel(), index + 1, -1);
                    container.removeField(buildPath(parentPath, "credits", index));
                    addField(parentPath, buildPath(parentPath, "credits", index),
                            new KSLabel(), index + 1, header.indexOf("Credits"));

                }

                container.removeField(UMDConstants.COURSE_ACTIVITY_TYPE_EXPLANATION);
                explanationSection.removeField(UMDConstants.COURSE_ACTIVITY_TYPE_EXPLANATION);

                tableInitialized = true;

                if(getLayoutController()!=null){
	               	this.getLayoutController().requestModel(new ModelRequestCallback<DataModel>(){
						public void onModelReady(DataModel model) {
							widgetReadyCallback.exec(model);
						}
						public void onRequestFail(Throwable cause) {
							widgetReadyCallback.exec(model);
						}});
                }else{
            		widgetReadyCallback.exec(model);
                }

            }

        }

    }

    public void setHeader() {

        for (int i = 0; i < header.size(); i++) {
            if ((semesterType.equalsIgnoreCase(SEMESTER_TYPE_STANDARD)) && (header.get(i).equalsIgnoreCase("Credit Ratio"))){
                activityTable.setWidget(0, i, addTitleWidget(header.get(i)));
                activityTable.getCellFormatter().addStyleName(0, i, "activity-table-cell-help");
            } else if ((semesterType.equalsIgnoreCase(SEMESTER_TYPE_NON_STANDARD)) && (header.get(i).equalsIgnoreCase("Contact Hours"))){
                activityTable.setWidget(0, i, addTitleWidget(header.get(i)));
                activityTable.getCellFormatter().addStyleName(0, i, "activity-table-cell-help");
            } else {
                setText(0, i, header.get(i));
            }
        }

        setHeaderFormat();

    }

    public void setHeaderFormat() {

        activityTable.getCellFormatter().addStyleName(0, 0, "activity-table-cell-first-header");

        for (int i = 0; i < header.size(); i++) {
            activityTable.getCellFormatter().addStyleName(0, i, "activity-table-cell-header");
            activityTable.getCellFormatter().addStyleName(1, i, "activity-table-cell-header-underline");
        }

    }

    public void setFooter() {

        int row = activityTable.getRowCount() - 1;
        int cellCount = activityTable.getCellCount(0);

        for (int cell = 0; cell < cellCount; cell++) {
            activityTable.getCellFormatter().addStyleName(row, cell, "activity-table-cell-footer");
        }

        setText(activityTable.getRowCount(), header.indexOf("Credit Ratio"), "Total");
        activityTable.getCellFormatter().addStyleName(activityTable.getRowCount() - 1, header.indexOf("Credit Ratio"),
                "activity-table-cell-total");

        for (int cell = cellCount - 3; cell < cellCount; cell++) {
            activityTable.getCellFormatter().addStyleName(row + 1, cell, "activity-table-cell-total-background");
        }
        
        //Add total hours contact field
        addFooterTotalField(parentPath, COURSE_ACTIVITY_TOTAL_CONTACT_HOURS,
                    new KSLabel(), row + 1, header.indexOf("Contact Hours"));
        //Add total credit field
        addFooterTotalField(parentPath, COURSE_ACTIVITY_TOTAL_CREDITS,
                    new KSLabel(), row + 1, header.indexOf("Credits"));
    }

    public boolean isEmpty(int row, int column) {
        boolean isEmpty = true;

        if (!getText(row, column).isEmpty()) {
            isEmpty = false;
        }

        return isEmpty;
    }

    //Initialise activityTypes and ratios
    public void initFields(final Callback<Boolean> initFieldsCompleteCallback) {

        Metadata activityTypeMetadata = this.modelDefinition.getMetadata(parentPath + "/" + FORMATS + "/*/"
                + ACTIVITIES
                + "/*/" + ACTIVITY_TYPE);
        Metadata ratioMetadata = this.modelDefinition.getMetadata(parentPath + "/" + FORMATS + "/*/" + ACTIVITIES
                + "/*/"
                + "creditRatio");

        LookupMetadata activityTypeLookupMetadata = activityTypeMetadata.getInitialLookup();
        LookupMetadata ratioLookupMetadata = ratioMetadata.getInitialLookup();

        //Initialise activityTypes
        search(activityTypeLookupMetadata, new Callback<Map<String, String>>() {

            @Override
            public void exec(Map<String, String> result) {
                activityTypeMap = result;
                activityTypesInitialized = true;
                initFieldsCompleteCallback.exec(true);

            }

        }, false);

        //Initialise ratios
        search(ratioLookupMetadata, new Callback<Map<String, String>>() {

            @Override
            public void exec(Map<String, String> result) {
                ratioMap = result;
                ratiosInitialized = true;
                initFieldsCompleteCallback.exec(true);

            }

        }, true);

    }

    public void search(final LookupMetadata lookupMetadata, final Callback<Map<String, String>> searchCompleteCallback, final boolean isSortColumn) {

        final Map<String, String> resultMap = new HashMap<String, String>();

        SearchUtils.initializeSearchRequest(lookupMetadata, searchRequestWrapper);

        cachingSearchService.search(searchRequestWrapper.getSearchRequest(), new KSAsyncCallback<SearchResult>() {

            @Override
            public void onSuccess(SearchResult result) {
                if (result != null) {
                    SearchResultListItems srList = new SearchResultListItems(result.getRows(), lookupMetadata);
                    int displayKey = srList.getItemTextAttrNdx();
                    int returnKey = srList.getKeyAttrNdx();
                    int intSortKey = srList.getSortAttrNdx()   ;                 
                    
                    for (SearchResultRow sRow : result.getRows()) {
                        resultMap.put(sRow.getCells().get(returnKey).getValue(), sRow.getCells().get(displayKey)
                                .getValue());
                        
                        if (isSortColumn) {
                            sortTemplate.put(sRow.getCells().get(intSortKey).getValue(), sRow.getCells().get(returnKey).getValue());
                        }
                    }
                }

                searchCompleteCallback.exec(resultMap);
            }

        });
    }

    public void initTable() {
        String key = "";
        setHeader();

        for (int index = 0; index < activityTypeMap.size(); index++) {
            
            key = sortTemplate.get(Integer.toString(index));
            key = key.substring(0, key.lastIndexOf(".ratio"));
            
            addField(parentPath, buildPath(parentPath, "activityType", index),
                    getSimpleListItemsWidget(key, activityTypeMap.get(key)), index + 1, header.indexOf("Activity"));
            addField(parentPath, buildPath(parentPath, "creditRatio", index),
                    new KSLabel(typeRatioMap.get(key)), index + 1, header.indexOf("Credit Ratio"));
            addField(parentPath, buildPath(parentPath, "calculatedContactHours", index),
                    new KSLabel(), index + 1, header.indexOf("Contact Hours"));

            //            calculate(index, false);

        }

        setFooter();

        redraw(semesterType);
    }

    public String buildPath(String parentPath, String fieldKey, int index) {
        return QueryPath
                .concat(parentPath, FORMATS, Integer.toString(0), ACTIVITIES, Integer.toString(index), fieldKey)
                .toString();
    }

    public void addField(String pPath, String fieldKey, Widget widget, int row, int col) {
        QueryPath path = QueryPath.concat(pPath, fieldKey);

        FieldDescriptor cFd = container.getField(path.toString());

        if (cFd == null) {

            Metadata meta = modelDefinition.getMetadata(path);

            FieldDescriptor fd = new FieldDescriptor(path.toString(), null, meta);
            if (widget != null) {
                fd.setFieldWidget(widget);
                fd.setWidgetBinding(null);
                fd.setHasHadFocus(true);
                fd.setDirty(true);

                addHandlers(widget, row);

                if (col != -1) {
                    activityTable.setWidget(row, col, widget);
                    activityTable.getCellFormatter().addStyleName(row, col, "activity-table-cell-center");

                    if (widget instanceof KSTextBox) {
                        widget.addStyleName("activity-textBox");
                    }

                } else if ((fieldKey.contains("contactHours/unitType")) && (widget instanceof KSLabel)) {
                    ((KSLabel) widget).setText(UNIT_TYPE);
                }
            }

            container.addField(fd);
        }
    }
    
    private void addFooterTotalField(String parentPath, String fieldKey, Widget widget, int row, int col) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
        FieldDescriptor cFd = container.getField(path.toString());
        if (cFd == null) {
            Metadata meta = modelDefinition.getMetadata(path);
            FieldDescriptor fd = new FieldDescriptor(path.toString(), null, meta);            
            if (widget != null) {
            	fd.setFieldWidget(widget);
                fd.setWidgetBinding(null);
                fd.setHasHadFocus(true);
                fd.setDirty(true);
                if (col != -1) {
                    activityTable.setWidget(row, col, widget);
                    //activityTable.getCellFormatter().addStyleName(row, col, "activity-table-cell-calculated");
                }
                container.addField(fd);
            }
        }
    }

    public KSLabelList getSimpleListItemsWidget(String returnValue, String displayValue) {

        KSLabelList activityTypeLabelList = new KSLabelList();
        SimpleListItems activityType = new SimpleListItems();

        activityType.addItem(returnValue, displayValue);
        activityTypeLabelList.setListItems(activityType);
        activityTypeLabelList.selectItem(returnValue.toString());

        return activityTypeLabelList;
    }

    public void addExplanationField(String parentPath, String fieldKey, MessageKeyInfo messageKey) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);

        if (explanationSection.getField(path.toString()) == null) {

            Metadata meta = modelDefinition.getMetadata(path);

            FieldDescriptor fd = new FieldDescriptor(path.toString(), messageKey, meta);

            explanationSection.addField(fd);

        }

    }

    public void addHandlers(final Widget widget, final int selectedRow) {
        if (widget instanceof KSTextBox) {
            ((KSTextBox) widget).addKeyUpHandler(new KeyUpHandler() {

                @Override
                public void onKeyUp(KeyUpEvent event) {
					setIsDirty(true);
                    calculate(selectedRow, true);

                }

            });

            ((KSTextBox) widget).addValueChangeHandler(new ValueChangeHandler<String>() {

                @Override
                public void onValueChange(ValueChangeEvent<String> event) {
                    ValueChangeEvent.fire(CourseFormatWidget.this, event.getValue());

                }

            });
            ((KSTextBox) widget).addBlurHandler(new BlurHandler() {

                @Override
                public void onBlur(BlurEvent event) {
                    if (((KSTextBox) widget).getValue().isEmpty()) {
                        ((KSTextBox) widget).setValue("0");
                        setIsDirty(true);
                        calculate(selectedRow, true);
                    }
                }
            });

        }
    };

    public void calculateColumn(String title) {
        int column = 0;
        double total = 0;

        int row = activityTable.getRowCount() - 1;

        if ((header.indexOf(title) != -1) && (row > 0)) {

            column = header.indexOf(title);

            for (int i = 1; i < row; i++) {
                if ((!isEmpty(i, column)) && isInputValid(i)) {
                    total += parseDouble(getText(i, column));
                }
            }

            setText(row, header.indexOf(title), format.format(total));
            activityTable.getCellFormatter().addStyleName(row, column,
                    "activity-table-cell-calculated");
        }

    }

    public boolean isAllNull(String title) {
        int column = 0;
        int row = activityTable.getRowCount() - 1;
        if ((header.indexOf(title) != -1) && (row > 0)) {
            column = header.indexOf(title);
            for (int i = 1; i < row; i++) {
                if ((!isEmpty(i, column)) && isInputValid(i) && getText(i, column) != null){
                	return false;
                }
            }
        }
        return true;
    }
    
    public void calculate(int row, boolean calculateTotals) {

        if (isInputValid(row)) {
            calculateContactHours(row);
            if (semesterType.equals(SEMESTER_TYPE_STANDARD)) {
                calculateCredits(row);
            }
            if (calculateTotals) {
                calculateTotals();
            }
            ValueChangeEvent.fire(CourseFormatWidget.this, "fire");
        }
    }

    public boolean isInputValid(int row) {
        boolean isValid = false;

        if ((semesterType.equals(SEMESTER_TYPE_STANDARD)) && (isDigits(getText(row, header.indexOf("Hours/Week"))))) {
            isValid = true;
        }

        if ((semesterType.equals(SEMESTER_TYPE_NON_STANDARD)) && (isDigits(getText(row, header.indexOf("Credits"))))) {
            isValid = true;
        }

        return isValid;
    }

    public double parseDouble(String doubleStr) {
        double val = 0;

        if (isDigits(doubleStr)) {
            val = Double.parseDouble(doubleStr);
        }

        return val;
    }

    public void calculateTotals() {
        calculateColumn("Contact Hours");
        calculateColumn("Credits");

    }

    public void calculateContactHours(int row) {
        double val = 0;
        int column = header.indexOf("Contact Hours");
        double[] ratio = getRatios(row);

        if (semesterType.equals(SEMESTER_TYPE_STANDARD) && (!isEmpty(row, header.indexOf("Hours/Week")))) {
            double hoursWeek = parseDouble(getText(row, header.indexOf("Hours/Week")));
            val = hoursWeek * creditRatioConstant;

        } else if (semesterType.equals(SEMESTER_TYPE_NON_STANDARD) && (!isEmpty(row, header.indexOf("Credits")))) {
            double credits = parseDouble(getText(row, header.indexOf("Credits")));
            val = credits * (creditRatioConstant * ratio[1]);
        }

        setText(row, column, format.format(val));
    }

    public void calculateCredits(int row) {
        double val = 0;
        int column = header.indexOf("Credits");
        double[] ratio = getRatios(row);

        if (!isEmpty(row, header.indexOf("Hours/Week"))) {
            double hWeek = parseDouble(getText(row, header.indexOf("Hours/Week")));
            val = hWeek * (ratio[0] / ratio[1]);
        }

        setText(row, column, format.format(val));
    }

    public double[] getRatios(int row) {
        //A:B
        double[] ratio = new double[2];
        int column = header.indexOf("Credit Ratio");
        //A
        double antecedent = 0;
        //B
        double consequent = 0;

        String val = getText(row, column);

        int delim = val.indexOf(':');

        antecedent = parseDouble(val.substring(0, delim));
        consequent = parseDouble(val.substring(delim + 1, val.length()));

        ratio[0] = antecedent;
        ratio[1] = consequent;

        return ratio;
    }

    public void setText(int row, int column, String text) {

        if (activityTable.isCellPresent(row, column)) {
            if (activityTable.getWidget(row, column) != null) {
                Widget w = activityTable.getWidget(row, column);

                if (w instanceof TextBoxBase) {
                    ((TextBoxBase) w).setText(text);
                } else if (w instanceof LabelBase) {
                    ((Label) w).setText(text);
                }
            } else {
                activityTable.setText(row, column, text);
            }
        } else {
            activityTable.setText(row, column, text);
        }
    }

    public String getText(int row, int column) {
        String text = "";

        if (activityTable.getWidget(row, column) != null) {
            Widget w = activityTable.getWidget(row, column);

            if (w != null) {
                if (w instanceof TextBoxBase) {
                    text = ((TextBoxBase) w).getText();
                } else if (w instanceof LabelBase) {
                    text = ((Label) w).getText();
                } else if (w instanceof KSSelectItemWidgetAbstract) {
                    text = ((KSSelectItemWidgetAbstract) w).getSelectedItem();
                }
            }

        } else {
            text = activityTable.getText(row, column);
        }

        return text;
    }

    public void initEmptyFields() {

        if (activityTable.getRowCount() > 0) {
            Widget w;
            for (int index = 0; index <= activityTypeMap.size(); index++) {
                if (semesterType.equals(SEMESTER_TYPE_NON_STANDARD)) {
                    w = activityTable.getWidget(index, header.indexOf("Credits"));

                    if ((w != null) && (w instanceof KSTextBox)
                                    && (getText(index, header.indexOf("Credits")).isEmpty())) {
                        setText(index, header.indexOf("Credits"), "0");
                    }

                } else if (semesterType.equals(SEMESTER_TYPE_STANDARD)) {
                    w = activityTable.getWidget(index, header.indexOf("Hours/Week"));

                    if ((w != null) && (w instanceof KSTextBox)
                                    && (getText(index, header.indexOf("Hours/Week")).isEmpty())) {
                        setText(index, header.indexOf("Hours/Week"), "0");
                    }
                }

                calculate(index, true);
            }

            calculateTotals();
        }else{
        	//Save calculated values to model
        	ValueChangeEvent.fire(CourseFormatWidget.this, "");
        }
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

    @Override
    public ErrorLevel processValidationResults(FieldDescriptor fd, List<ValidationResultInfo> results) {
        return processValidationResults(fd, results, false);
    }

    @Override
    public ErrorLevel processValidationResults(FieldDescriptor fd, List<ValidationResultInfo> results,
            boolean clearErrors) {

    	Validate(fd,results);
    	
    	ErrorLevel status = this.errorLevel;
        
        FieldElement element = fd.getFieldElement();

        if (errorLevel == ErrorLevel.OK) {
            element.addStyleName("activity-section");
        }

        return status;

    }

    @Override
    public boolean doesOnTheFlyValidation() {
        return onTheFlyValidation;
    }

    // Validate option pulls all the cases from the event and if the element that triggered the event is set by the field descriptor
    // then add process the error relative to that element
    @Override
    public void Validate(ValidateRequestEvent event, List<ValidationResultInfo> result) {
        if (widgetInitialized && this.isDirty) {
        	Validate(event.getFieldDescriptor(), result);
        }        
    }

    public void Validate(FieldDescriptor fieldDescriptor, List<ValidationResultInfo> result) {
        if (widgetInitialized) {
            errorLevel = ErrorLevel.OK;
            
            result.addAll(validateCredits());

            FieldElement element = fieldDescriptor.getFieldElement();

            element.clearValidationErrors();
            element.clearValidationWarnings();

            for (int i = 0; i < result.size(); i++) {
                ValidationResultInfo vr = result.get(i);

                for (FieldDescriptor fd : container.getFields()) {
                    QueryPath path = QueryPath.parse(fd.getFieldKey());

                    if (vr.getElement().equals(path.toString()) && fd.hasHadFocus()) {
                        errorLevel = processValidationResult(element, vr);
                    }
                }

                if (vr.getElement().contains("activity.total.") || (vr.getElement().contains("activity.credits"))) {
                    errorLevel = processValidationResult(element, vr);
                }
            }

            if (errorLevel == ErrorLevel.OK) {
                addElementStyle(element);
                removeAllTotalValidationStyle();
                addTotalStyle();
            }
        }

    }

    public List<ValidationResultInfo> validateCredits() {
        List<ValidationResultInfo> results = new ArrayList<ValidationResultInfo>();

        KSPicker picker = (KSPicker) getFieldDescriptor(null, creditOptionsTypePath).getFieldWidget();
        KSRadioButtonList radio = (KSRadioButtonList) picker.getInputWidget();

        String creditOptionType = radio.getSelectedItem();
        
        double creditOptionFixed = 0;
        double totalCreditContactHours = 0;
        double creditOptionMin = 0;
        double creditOptionMax = 0;
        double minCreditContactHours = 0;

        //Stop validation if nothing was ever entered
        if(isAllNull("Credits") && isAllNull("Contact Hours")){
        	return results;
        }
        
        double totalActivityBasedCredits = getTotal("Credits");
        double totalActivityBasedContactHours = getTotal("Contact Hours");

        if ("kuali.resultComponentType.credit.degree.fixed".equals(creditOptionType)) {
            creditOptionFixed = parseDouble(((CreditOptionGuidanceLabelUMD) getFieldDescriptor(null,
                    creditOptionFixedFullPath)
                        .getFieldWidget()).getText());
            totalCreditContactHours = creditOptionFixed * creditRatioConstant;
            
        } else if ("kuali.resultComponentType.credit.degree.range".equals(creditOptionType)) {
            creditOptionMin = parseDouble(((CreditOptionGuidanceLabelUMD) getFieldDescriptor(null,
                    creditOptionMinFullPath)
                        .getFieldWidget()).getText());
            creditOptionMax = parseDouble(((KSTextBox) getFieldDescriptor(null,
                    creditOptionMaxFullPath)
                        .getFieldWidget()).getText());
           minCreditContactHours = creditOptionMin * creditRatioConstant;
        }

        if ((radio.getSelectedItem() != null)) {

            if (creditOptionType.isEmpty()) {
                ValidationResultInfo vr = new ValidationResultInfo();
                vr.setElement("activity.total");
                vr.setError("Credit Type not selected");
                results.add(vr);
            } else

            //#Fixed Credit Courses
            if (creditOptionType.equals("kuali.resultComponentType.credit.degree.fixed")) {
                //Validation Messages : https://confluence.umd.edu/display/KSCM/Prototypes+Supporting+JIRAs#
                //#3
                if (creditOptionFixed > totalActivityBasedCredits) {
                    ValidationResultInfo vr = new ValidationResultInfo();
                    vr.setElement("activity.total.contactHours");
                    vr.setError("Not enough contact hours to merit specified total credits. \n Either reduce course credit or add additional contact hours.");
                    results.add(vr);
                }
                //#4 - Commented out as part of KSCM-1613 - Will be reinserted with later milestone
/*                if (creditOptionFixed < totalActivityBasedCredits) {
                    ValidationResultInfo vr = new ValidationResultInfo();
                    vr.setElement("activity.total.contactHours");
                    vr.setWarning("Expected contact hours exceeded. \n Ensure the credit value and contact hours for this course are appropriate.");
                    results.add(vr);
                }*/
                //#Variable Credit Courses 
            } else if (creditOptionType.equals("kuali.resultComponentType.credit.degree.range")) {
                //#5
                if (creditOptionMin >= creditOptionMax) {
                    ValidationResultInfo vr = new ValidationResultInfo();
                    vr.setElement("activity.total.credits");
                    vr.setError("Minimum Credits must be less than the Maximum Credits.  If the two values should be the same, use the Fixed Credit option above." );
                    results.add(vr);
                    return results;
                }
                //#8
                if (creditOptionMin > totalActivityBasedCredits) {
                    ValidationResultInfo vr = new ValidationResultInfo();
                    vr.setElement("activity.total.contactHours");
                    vr.setError("Not enough contact hours to merit specified minimum credits. \n Either reduce course minimum credit or add additional contact hours");
                    results.add(vr);
                }
                //#9 - Commented out as part of KSCM-1613 - Will be reinserted with later milestone
                /*if (creditOptionMax < totalActivityBasedCredits) {
                    ValidationResultInfo vr = new ValidationResultInfo();
                    vr.setElement("activity.total.contactHours");
                    vr.setWarning("Specified activities equate to a larger credit value than the course maximum credit value.  Ensure credit values and contact hours for this course are appropriate.");
                    results.add(vr);
                }*/
            }
        }

        return results;
    }

    public void addElementStyle(FieldElement element) {
        element.addStyleName("activity-section");

        int cellCount = activityTable.getCellCount(0);
        int row = activityTable.getRowCount() - 1;

        for (int cell = cellCount - 3; cell < cellCount; cell++) {
            activityTable.getCellFormatter().addStyleName(row, cell, "activity-table-cell-total-background");
        }
    }

    public void addTotalValidationStyle(String fieldName, String style) {
        int cellCount = activityTable.getCellCount(0);
        int row = activityTable.getRowCount() - 1;

        removeAllTotalValidationStyle();

        for (int cell = cellCount - 3; cell < cellCount; cell++) {
            activityTable.getCellFormatter().addStyleName(row, cell, style);
        }

        if (style == "waring") {
            style = "warn";
        }

        if (fieldName.contains("contactHours")) {
            activityTable.getCellFormatter().addStyleName(row, header.indexOf("Contact Hours"),
                    "ks-form-" + style + "-label");
        }

        if (fieldName.contains("credits")) {
            activityTable.getCellFormatter()
                    .addStyleName(row, header.indexOf("Credits"), "ks-form-" + style + "-label");
        }

    }

    public void addTotalStyle() {
        int cellCount = activityTable.getCellCount(0);
        int row = activityTable.getRowCount() - 1;

        for (int cell = cellCount - 3; cell < cellCount; cell++) {
            activityTable.getCellFormatter().addStyleName(row, cell, "activity-table-cell-total-background");
        }

    }

    public void removeAllTotalValidationStyle() {
        removeTotalValidationStyle("error");
        removeTotalValidationStyle("warning");
        removeTotalValidationStyle("warn");
    }

    public void removeTotalValidationStyle(String style) {
        int cellCount = activityTable.getCellCount(0);
        int row = activityTable.getRowCount() - 1;

        for (int cell = cellCount - 3; cell < cellCount; cell++) {
            activityTable.getCellFormatter().removeStyleName(row, cell, style);

            activityTable.getCellFormatter().removeStyleName(row, header.indexOf("Credits"),
                    "ks-form-" + style + "-label");
            activityTable.getCellFormatter().removeStyleName(row, header.indexOf("Contact Hours"),
                    "ks-form-" + style + "-label");
            activityTable.getCellFormatter().removeStyleName(row, cell, "activity-table-cell-total-background");
        }
    }

    public void removeElementStyle(FieldElement element) {
        element.removeStyleName("activity-section");

        int cellCount = activityTable.getCellCount(0);
        int row = activityTable.getRowCount() - 1;

        for (int cell = cellCount - 3; cell < cellCount; cell++) {
            activityTable.getCellFormatter().removeStyleName(row, cell, "activity-table-cell-total-background");
        }
    }

    public String getTranslation(String fieldKey) {
        String friendlyKey = fieldKey;

        if (fieldKey.equals("activity.total.contactHours")) {
            friendlyKey = "Total Contact Hours";
        }

        if (fieldKey.contains("/contactHours/unitQuantity")) {
            friendlyKey = fieldKey.replace("contactHours/unitQuantity", "Hours/Week");
        }

        if (fieldKey.equals("activity.total.credits")) {
            friendlyKey = "Total Credits";
        }

        return friendlyKey;
    }

    public ErrorLevel processValidationResult(FieldElement element, ValidationResultInfo vr) {

        if (vr.getLevel() == ErrorLevel.ERROR) {
            String message = Application.getApplicationContext().getUILabel("validation", vr.getMessage());
            this.addValidationErrorMessage(element, message, vr.getElement());

            if (errorLevel.getLevel() < ErrorLevel.ERROR.getLevel()) {
                errorLevel = vr.getLevel();
            }

        } else if (vr.getLevel() == ErrorLevel.WARN) {
            String message = Application.getApplicationContext().getUILabel("validation", vr.getMessage());
            this.addValidationWarningMessage(element, message, vr.getElement());

            if (errorLevel.getLevel() < ErrorLevel.WARN.getLevel()) {
                errorLevel = vr.getLevel();

            }
        }
        return errorLevel;
    }

    public KSLabel addValidationErrorMessage(FieldElement element, String text, String fieldName) {

        KSLabel message;
        if (fieldName != null && !fieldName.trim().equals("")) {
            message = new KSLabel(getTranslation(fieldName) + " - " + text);
        } else {
            message = new KSLabel(text);
        }
        element.setErrorState(true);

        if (!fieldName.contains("activity.total.")) {
            removeElementStyle(element);
            element.addStyleName("activity-validation-layout");
        } else {
            addTotalValidationStyle(fieldName, "error");
        }

        message.setStyleName("ks-form-error-label");
        element.getValidationPanel().addErrorMessage(message);
        return message;
    }

    public Widget addValidationWarningMessage(FieldElement element, String text, String fieldName) {

        SpanPanel message = new SpanPanel();
        if (fieldName != null && !fieldName.trim().equals("")) {
            message.setHTML("<b> Warning </b> " + getTranslation(fieldName) + " - " + text);
        } else {
            message.setHTML("<b> Warning </b> " + text);
        }

        message.setStyleName("ks-form-warn-label");
        element.setWarnState((errorLevel != ErrorLevel.ERROR));

        if (!fieldName.contains("activity.total.")) {
            element.addStyleName("activity-validation-layout");
        } else {
            if (errorLevel != ErrorLevel.ERROR) {
                addTotalValidationStyle(fieldName, "warning");
            }
        }
        element.getValidationPanel().addWarnMessage(message);
        return message;
    }

    public double getTotal(String key) {
        return parseDouble(getText(activityTable.getRowCount() - 1, header.indexOf(key)));
    }

    public boolean isDigits(String val) {
        boolean valid = false;
        try {
            Double.parseDouble(val);
            valid = true;
        } catch (Exception excep) {
            valid = false;
        }

        return valid;
    }

    public List<FieldDescriptor> getFields() {
    	List<FieldDescriptor> fields = container.getFields();
    	fields.addAll(explanationSection.getFields());
        return fields;
    }

    public FieldDescriptor getFieldDescriptor(String namespace, QueryPath fieldKey) {
        return Application.getApplicationContext().getPathToFieldMapping(namespace, fieldKey.toString());
    }

    public FieldElement updateFieldElement(String fieldKey, MessageKeyInfo messageKeyInfo) {
        FieldElement fieldElement = new FieldElement("FOO", new MessageKeyInfo(""));

        return fieldElement;
    }
    
    public String getSemesterType() {
        return semesterType;
    }

    public String getParentPath() {
        return parentPath;
    }
}
