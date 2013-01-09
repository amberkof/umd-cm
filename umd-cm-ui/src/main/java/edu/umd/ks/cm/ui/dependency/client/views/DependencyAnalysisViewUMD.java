package edu.umd.ks.cm.ui.dependency.client.views;


import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.util.UtilConstants;
import org.kuali.student.common.ui.client.widgets.HasWatermark;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.VerticalFieldLayout;
import org.kuali.student.common.ui.client.widgets.filter.FilterEvent;
import org.kuali.student.common.ui.client.widgets.filter.FilterEventHandler;
import org.kuali.student.common.ui.client.widgets.filter.FilterResetEventHandler;
import org.kuali.student.common.ui.client.widgets.filter.KSFilterOptions;
import org.kuali.student.common.ui.client.widgets.headers.KSDocumentHeader;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.suggestbox.IdableSuggestOracle.IdableSuggestion;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.lu.ui.dependency.client.controllers.DependencyAnalysisController;
import org.kuali.student.lum.lu.ui.dependency.client.views.DependencyAnalysisView;
import org.kuali.student.lum.lu.ui.dependency.client.widgets.DependencyResultPanel.DependencyTypeSection;
import org.kuali.student.lum.lu.ui.tools.client.configuration.ClusetView.Picker;
import org.kuali.student.r1.common.assembly.data.LookupMetadata;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.infc.SearchParam;
import org.kuali.student.r2.core.search.infc.SearchResult;
import org.kuali.student.r2.core.search.infc.SearchResultCell;
import org.kuali.student.r2.core.search.infc.SearchResultRow;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;

import edu.umd.ks.cm.ui.dependency.client.widgets.DependencyResultPanelUMD;
public class DependencyAnalysisViewUMD extends DependencyAnalysisView {

    public DependencyAnalysisViewUMD() {
        super();
    }
    
    public void initialize(Controller controller) {
        super.initialize(controller);
    }
    
    @Override
    protected void init() {		
        this.header = new KSDocumentHeader();

        header.setTitle("Dependency Analysis");        

        //Get search definition for dependency analysis trigger search and create trigger picker          
        Metadata metaData = searchDefinition.getMetadata("courseId");
        final KSPicker triggerPicker = new Picker(metaData.getInitialLookup(), metaData.getAdditionalLookups());
        ((HasWatermark)triggerPicker.getInputWidget()).setWatermarkText("Enter course code");	
        triggerPicker.getInputWidget().ensureDebugId("Dependency-Analysis-Course-Code");

        //Setup the "go" button for trigger picker
        KSButton goButton = new KSButton("Go", ButtonStyle.PRIMARY_SMALL);
        goButton.addClickHandler(new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {				
                selectedCourseId = triggerPicker.getValue().toString();
                KSSuggestBox suggestBox = (KSSuggestBox)triggerPicker.getInputWidget();
                IdableSuggestion selectedSuggestion = suggestBox.getCurrentSuggestion();

                // We need to make sure we only search for courses that have been selected from the suggest box
                if(selectedSuggestion != null && selectedSuggestion.getReplacementString().equalsIgnoreCase(suggestBox.getText())){
                    selectedCourseName = selectedSuggestion.getAttrMap().get("lu.resultColumn.luOptionalLongName");
                    if (!selectedCourseId.equals(UtilConstants.IMPOSSIBLE_CHARACTERS)){
                        KSBlockingProgressIndicator.addTask(loadDataTask);
                        selectedCourseCd = triggerPicker.getDisplayValue();                
                        if (depResultPanel != null){
                            resultContainer.remove(depResultPanel);
                        }
                        
		                // GWT create instead of new to enable gwt replace
		                depResultPanel =  GWT.create(DependencyResultPanelUMD.class);
		                depResultPanel.init();

                        depResultPanel.setHeaderTitle(selectedCourseCd + " - " + selectedCourseName);		
                        resultContainer.add(depResultPanel);
                        resultContainer.setVisible(true);
                        updateDependencyResults();

                        ((DependencyAnalysisController) DependencyAnalysisViewUMD.this.getController()).showExport(isExportButtonActive());
                        header.showPrint(true);
                        header.setPrintContent(depResultPanel); // we only want to print the results panel
                    }

                }
            }

        });


        //Create search section with the trigger picker and go button
        HorizontalPanel pickerPanel = new HorizontalPanel();
        pickerPanel.add(triggerPicker);
        pickerPanel.add(goButton);

//        FlowPanel searchPanel = new FlowPanel();
//        SimplePanel searchPanel = new SimplePanel();
        HorizontalPanel searchPanel = new HorizontalPanel();
        searchPanel.addStyleName("ks-dependency-search");
        searchPanel.add(pickerPanel);       
//        searchPanel.add(new KSLabel("Search for item to view its dependencies"));
        KSLabel ksLabel = new KSLabel("Search for a course to see if it is related to " +
        		"another course as a prerequisite, corequisite, recommended course, " +
        		"restriction, or cross-listed or jointly offered course.");
        ksLabel.addStyleName("ks-dependency-search-label");
        searchPanel.add(ksLabel);

        //Add widgets to view
        container.setTitleWidget(header);
        container.addStyleName("blockLayout");
        container.addWidget(searchPanel);

        List<LookupMetadata> lookups = new ArrayList<LookupMetadata>();
        metaData = searchDefinition.getMetadata("filter");
        lookups.add(metaData.getInitialLookup());

        dependencyFilter = new KSFilterOptions(metaData.getAdditionalLookups());
        dependencyFilter.addFilterEventHandler(new FilterEventHandler(){

            @Override
            public void onDeselect(FilterEvent e) {
                depResultPanel.hide(e.getFilterKey(), e.getFilterValue());
            }

            @Override
            public void onSelect(FilterEvent e) {
                if (e.isInitialSelect()){
                    depResultPanel.hideAll();
                }
                depResultPanel.show(e.getFilterKey(), e.getFilterValue());			
            }

        });

        dependencyFilter.addFilterResetEventHandler(new FilterResetEventHandler(){

            @Override
            public void onReset() {
                depResultPanel.showAll();				
            }        	
        });

        resultContainer.add(dependencyFilter);
        resultContainer.setVisible(false);

        container.addWidget(resultContainer);
    }
    
    
	//This generates the label for the dependency type section (eg. Pre-req requirement, course set inclusion, etc).
    // KSCM-2055 Overridden to add a space to the end For UMD
    @Override
	protected SpanPanel getDependencyTypeLabel(String dependencySectionKey, String dependencyType, String dependencyTypeName) {
		SpanPanel header = new SpanPanel();
		// KSCM-2129 Remove plurals from Dependency Type Name
		// Doesn't affect cluset because name is "Course Set"
		if ((dependencyTypeName!=null) && (dependencyTypeName.endsWith("s"))){
			dependencyTypeName = dependencyTypeName.substring(0, dependencyTypeName.length()-1);
		}
		// lower case all
		dependencyTypeName = dependencyTypeName.toLowerCase();
		
		if (dependencyType.equals("joint") || dependencyType.equals("crossListed")){
			header.setHTML("<b>" + selectedCourseCd + "</b> is <b>" + dependencyTypeName + "</b> with the following ");
		} // KSCM-2129 remove "a" from "a recommended"
		else if (dependencyType.equals("kuali.statement.type.course.recommendedPreparation")){
			header.setHTML("<b>" + selectedCourseCd + "</b> is <b>" + dependencyTypeName + "</b> for the following " + dependencySectionKey + "&nbsp;");
		} else if (dependencySectionKey.equals("courseSet")){
			header.setHTML("<b>" + selectedCourseCd + "</b> belongs to the following <b>course sets</b>&nbsp;");
		} else {
			header.setHTML("<b>" + selectedCourseCd + "</b> is a <b>" + dependencyTypeName + "</b> for the following " + dependencySectionKey + "&nbsp;");			
		}
    	header.setStyleName("KS-DependencyType-Label");
		return header;
	}

    /*
     * This method calls the dependency analysis search with the selected trigger from the picker,
     * and updates the DependencyResultPanel with the results from the search. 
     */
    protected void updateDependencyResults(){
        //Setup up sections for DependencyResultPanel       
        depResultPanel.addSection(LUUIConstants.DEP_SECTION_COURSE,"Course Dependencies");      
        depResultPanel.addSection(LUUIConstants.DEP_SECTION_PROGRAM,"Program Dependencies");
        depResultPanel.addSection(LUUIConstants.DEP_SECTION_COURSE_SET, "Course Set Inclusions");       
            
        dependencyFilter.reset();
        
        //Setup and invoke the dependency analysis search and process the results
        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("lu.search.dependencyAnalysis");
        
        SearchParam searchParam = new SearchParam();
        searchParam.setKey("lu.queryParam.luOptionalCluId");
        searchParam.setValue(selectedCourseId);             
        searchRequest.getParams().add(searchParam);
                
        searchServiceAsync.search(searchRequest, new KSAsyncCallback<SearchResult>(){

            @Override
            public void onSuccess(SearchResult searchResults) {             
                for (SearchResultRow searchResultRow : searchResults.getRows ()) {
                    
                    //TODO: This should not use hard-coded result columns 
                    String cluCode = "";
                    String cluName = "";
                    String cluType = "";
                    String dependencyType = "";
                    String dependencyTypeName = "";
                    String reqComponentIds = "";
                    String reqRootId = "";
                    String cluId = "";
                    String curriculumOversightNames = "";
                    String dependencySectionKey = "";
                    Boolean diffAdminOrg = false;
                    String parentCluId = "";
                    
                    for (SearchResultCell searchResultCell : searchResultRow.getCells ()){
                        if (searchResultCell.getKey().equals ("lu.resultColumn.luOptionalCode")) {
                            cluCode = searchResultCell.getValue();  
                        } if (searchResultCell.getKey().equals ("lu.resultColumn.cluId")) {
                            cluId = searchResultCell.getValue();
                        } else if (searchResultCell.getKey().equals("lu.resultColumn.luOptionalLongName")){
                            cluName = searchResultCell.getValue();
                        } else if (searchResultCell.getKey().equals("lu.resultColumn.cluType")){
                            cluType = searchResultCell.getValue();
                            if (cluType.equals(LUUIConstants.CLU_TYPE_CREDIT_COURSE)){
                                dependencySectionKey = LUUIConstants.DEP_SECTION_COURSE;
                            } else if (cluType != null){
                                dependencySectionKey = LUUIConstants.DEP_SECTION_PROGRAM;
                            }
                        } else if (searchResultCell.getKey().equals("lu.resultColumn.luOptionalDependencyType")){
                            dependencyType = searchResultCell.getValue();
                            if (dependencyType.equals("cluSet")){
                                dependencySectionKey = LUUIConstants.DEP_SECTION_COURSE_SET;
                            } else if (LUUIConstants.DEP_TYPE_JOINT.equals(dependencyType) || 
                                        LUUIConstants.DEP_TYPE_CROSS_LISTED.equals(dependencyType)){
                                dependencySectionKey = LUUIConstants.DEP_SECTION_COURSE;                                
                            }
                        } else if (searchResultCell.getKey().equals("lu.resultColumn.luOptionalDependencyTypeName")){
                            dependencyTypeName = searchResultCell.getValue();
                        } else if (searchResultCell.getKey().equals("lu.resultColumn.luOptionalDependencyRequirementComponentIds")){
                            reqComponentIds = searchResultCell.getValue();
                        } else if (searchResultCell.getKey().equals("lu.resultColumn.luOptionalDependencyRootId")){
                            reqRootId = searchResultCell.getValue();
                        } else if (searchResultCell.getKey().equals("lu.resultColumn.luOptionalOversightCommitteeNames")){
                            curriculumOversightNames = searchResultCell.getValue();
                        } /* else if (searchResultCell.getKey().equals("lu.resultColumn.luOptionalDependencyRequirementDifferentAdminOrg")){
                            diffAdminOrg = ("true").equals(searchResultCell.getValue());
                        } */else if (searchResultCell.getKey().equals("lu.resultColumn.parentCluId")){
                            parentCluId = searchResultCell.getValue();
                        }
                        
                            
                    }
                    
                    //Get the dependency type section to add the dependency to, create new one if this is the first dependency for the t
                    DependencyTypeSection typeSection = depResultPanel.getDependencyTypeSection(dependencySectionKey, dependencyType);
                    if (typeSection == null){
                    	// TODO: change "the following course" to "the following courses"
                    	// only when plural, may require KS change.
                    	SpanPanel dependencyTypeLabel = getDependencyTypeLabel(dependencySectionKey, dependencyType, dependencyTypeName);
                       //  dependencyTypeLabel = fixPluralRecommended(dependencySectionKey, dependencyType, dependencyTypeName,                     	typeSection.getNumberOfDependencies(););                    			
                        typeSection = depResultPanel.addDependencyTypeSection(dependencySectionKey, dependencyType, dependencyTypeLabel);
                    }
                    
                    //If dependency has details, create the details widget
                    VerticalFieldLayout depDetails = null;
                    if (hasDependencyDetails(dependencyType)){
                        depDetails = getDependencyDetails(dependencySectionKey, dependencyType, cluCode, reqRootId, reqComponentIds);
                        KSLabel curricOversightLabel = new KSLabel("Curriculum Oversight: " + curriculumOversightNames);
                        curricOversightLabel.addStyleName("ks-dependency-oversight");
                        depDetails.addWidget(curricOversightLabel);
                    }

                    //Add the dependency to the dependency section
                    typeSection.addDependencyItem(getDependencyLabel(dependencySectionKey, dependencyType, cluId, cluCode, cluName, cluType, diffAdminOrg, parentCluId), depDetails);
                }
                
                depResultPanel.finishLoad();
                KSBlockingProgressIndicator.removeTask(loadDataTask);
            }
            
        });
    }
    

}
