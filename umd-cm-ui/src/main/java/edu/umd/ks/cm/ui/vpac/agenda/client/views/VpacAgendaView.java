package edu.umd.ks.cm.ui.vpac.agenda.client.views;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.search.dto.SearchRequestInfo;
import org.kuali.student.common.search.dto.SearchResult;
import org.kuali.student.common.search.dto.SearchResultRow;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.service.MetadataRpcService;
import org.kuali.student.common.ui.client.service.MetadataRpcServiceAsync;
import org.kuali.student.common.ui.client.service.SearchRpcService;
import org.kuali.student.common.ui.client.service.SearchRpcServiceAsync;
import org.kuali.student.common.ui.client.util.ExportUtils;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.VerticalFieldLayout;
import org.kuali.student.common.ui.client.widgets.headers.KSDocumentHeader;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.lum.lu.LUConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import edu.umd.ks.cm.ui.common.client.widgets.AppLocationsUMD.Locations;
import edu.umd.ks.cm.ui.common.client.widgets.search.SearchResultsTableUMD.CellTableResource;
import edu.umd.ks.cm.ui.course.client.configuration.UMDConstants;
import edu.umd.ks.cm.ui.vpac.agenda.client.configuration.VPACViews;
import edu.umd.ks.cm.ui.vpac.agenda.client.controllers.VpacAgendaController;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptorReadOnly;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.headers.KSDocumentHeader;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldBlock;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldRow;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableSection;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.common.client.widgets.CluSetDetailsWidget;
import org.kuali.student.lum.common.client.widgets.CluSetEditorWidget;
import org.kuali.student.lum.common.client.widgets.CluSetManagementRpcService;
import org.kuali.student.lum.common.client.widgets.CluSetManagementRpcServiceAsync;
import org.kuali.student.lum.common.client.widgets.CluSetRetriever;
import org.kuali.student.lum.common.client.widgets.CluSetRetrieverImpl;
import org.kuali.student.r1.common.assembly.data.LookupMetadata;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.data.QueryPath;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/** The view for the Vpac agenda screen
 *  Uses metadata and search services to hit lu.search.VPACProposals defined in
 *  umd-cm-lu-search-config.xml.  The Vpac Agenda Screen does not require it's own service.
 *  
 *   We ended up using GWT's CellTable, which we created custom Rows and Cells for,
 *   mostly because we needed to put a hyper-link in the results table.
 *   
 * @author mike
 *
 */
public class VpacAgendaView extends ViewComposite {

	// Length of one page of results. - not using pagination at user's request.
	// But this does affect the max size of the CellTable.
	protected final int PAGE_SIZE = 400;

	// Loads search definitions for the VPAC agenda Search
    protected MetadataRpcServiceAsync metadataServiceAsync = GWT.create(MetadataRpcService.class);
    
    // Performs the VPAC search
    protected SearchRpcServiceAsync searchServiceAsync = GWT.create(SearchRpcService.class);
	
	// Contains searchId, etc.
    protected SearchRequestInfo searchRequest = new SearchRequestInfo();
    
    /* Not currently used. May come back later and use it.
	   protected Metadata searchMetadata; */
	
    protected String resultIdColumnKey;
    
    protected KSButton selectButton = new KSButton("View Proposal"); 
    
    // Main GWT container for the Vpac Screen.
	private VerticalFieldLayout layout = new VerticalFieldLayout();
	
    // GWT CellTable - using our custom Row (VpacTableRow) and Cell (VpacClickableTextCell) types.
	// we also overrode the renderer in our custom Cell to allow for hyperlinks.
    private CellTable<VpacTableRow> vpacCellTable = new CellTable<VpacTableRow>(PAGE_SIZE, CellTableResource.INSTANCE);
    
    // vpacTableModel - includes export method for Jasper reports.
    VpacTableModel vpacTableModel = new VpacTableModel();
    
    protected boolean initialized = false;
    
    // This is just used to pop up the nice "loading" icon and take it away.
	protected final BlockingTask initializingTask = new BlockingTask("Loading");
    
    // Container Header -> "VPAC Agenda"
	private KSDocumentHeader header;
	
	protected int numberOfProposals=0;
	
	/**
	 * Constructor. 
	 * @param controller
	 */
	public VpacAgendaView(Controller controller) {
		
		// VPACViews is the enumerated type class used to label this view.
		// This has to be defined separately, because this is a UMD only
		// view.  Most KS view enum types are defined by LUMViews.java
		
		super(controller, getMessage(UMDConstants.VPAC_SCREEN_NAME), VPACViews.VPAC_AGENDA);
        this.initWidget(layout);
     //   this.addStyleName("blockLayout");
       // this.addStyleName("ks-dependency-container");
	}
	
	
	/** Before Show handles the process indicator
	 *  and calls configureVpacScreen to setup the screen if needed.
	 *  
	 *  We were originally going to use KS framework to load the lookups,
	 *  in which case you would want to use the Metadata Service here to 
	 *  load them. However, we couldn't get it working easily and didn't 
	 *  really need it.
	 * 
	 */
	@Override
	public void beforeShow(final Callback<Boolean> onReadyCallback) {
        if (!initialized) {
        	
        	// Pops up "loading" message.
    		KSBlockingProgressIndicator.addTask(initializingTask);
            
    		/* If !initialized, configure the Screen, including configuring 
          	   and populating the results table. Adds cellTable to the layout on return. */
           	layout.add(configureVpacScreen(null));
           	
            onReadyCallback.exec(true);
            
            initialized = true;
                    
            // Removes "loading"
            KSBlockingProgressIndicator.removeTask(initializingTask);
        } else {
        	onReadyCallback.exec(true);
        }
	}
	
	/** Configure the Vpac screen, including header and results panel
	 * also calls performVpacAgendaSearch to populate the results.
	 * Returns widget to beforeShow.
	 * 
	 */
	public Widget configureVpacScreen(Metadata searchMeta){
		
        // Setup header, including export/print icons.
		List<String> exportFiles=new ArrayList<String>();
		exportFiles.add(ExportUtils.DOC);
		exportFiles.add(ExportUtils.XLS);
		exportFiles.add(ExportUtils.PDF);
		this.header = new KSDocumentHeader(exportFiles);
		header.setTitle(getMessage(UMDConstants.VPAC_SCREEN_NAME));
		 ((VpacAgendaController) VpacAgendaView.this.getController()).showExport(isExportButtonActive());
        header.showPrint(true);
        
    	// Perform the search, populate the table, set up the columns.     	
    	initializeTable(); 		
    	
    	vpacCellTable.setVisible(true);

    	/*  Set Print content after widget is populated. 
    	 *  We only want to print the table.
    	 */
        header.setPrintContent(layout); 
        
        // Add the header to the layout.
        layout.setTitleWidget(header);
        
        return vpacCellTable;
	}
		
	public KSDocumentHeader getHeader() {
		return header;
	}
	
	/**
	 * Calls VPAC search, which populates Model with results.
	 * Creates Columns for table and hardcodes their titles.
	 * Everything's hardcoded right here.  
	 * 
	 */
	protected void initializeTable(){

		// Run search and populate Model with results.	

		performVpacAgendaSearch(0,0);		
		
		// Create the columns for the Cell Table.
		   
		Column<VpacTableRow, String> oversightColumn = new Column<VpacTableRow, String>(new  VpacClickableTextCell()){
		    @Override
		    public String getValue(VpacTableRow vpacRow){
		        return vpacRow.getCurriculumOversightUnit();
		    }

		};
		
		Column<VpacTableRow, String> courseCodeColumn = new Column<VpacTableRow, String>(new  VpacClickableTextCell()){
		    @Override
		    public String getValue(VpacTableRow vpacRow){
		        return vpacRow.getCourseCode();
		    }

		};
		
		Column<VpacTableRow, String>  courseTitleColumn = new Column<VpacTableRow, String>(new  VpacClickableTextCell()){
		    @Override
		    public String getValue(VpacTableRow vpacRow){
		        return vpacRow.getCourseTitle();
		    }

		};
		
		Column<VpacTableRow, String>  proposalTypeColumn = new Column<VpacTableRow, String>(new  VpacClickableTextCell()){
		    @Override
		    public String getValue(VpacTableRow vpacRow){
		        return vpacRow.getProposalType();
		    }

		};
		
		Column<VpacTableRow, String> htmlLinkColumn = new Column<VpacTableRow, String>(new  VpacClickableTextCell()){
		    @Override
		    public String getValue(VpacTableRow vpacRow){
		        return vpacRow.getHtmlLink();
		    }

		};
		 
    	 // Add the columns to the vpacCellTable.
		vpacCellTable.addColumn(oversightColumn, "Curriculum Oversight Unit");
        vpacCellTable.addColumn(courseCodeColumn, "Course Code");
        vpacCellTable.addColumn(courseTitleColumn, "Course Title");
        vpacCellTable.addColumn(proposalTypeColumn, "Proposal Type");       
        vpacCellTable.addColumn(htmlLinkColumn, ""); // no title on this column
	}
	
	/**
	 * Takes document Id (KSPR.proposal.proposal_id)
	 * and document Type, and generates a direct HTML link to put in VPAC table.
	 */
	protected String buildLink(String docId, String docType){
 
		// Obtain Base URL
		String baseUrl = GWT.getHostPageBaseURL();     
		String partBaseAppURL = baseUrl +"LUMMain.jsp#";
			
		// Build simple parts.
		String partDocId = "&docId=" + docId;
		String partIdType="&idType=kualiStudentObjectWorkflowId";
		String partDocType = "&documentTypeName="+ docType;
		
		// Obtain screen path based on docType
		String partScreenPath="";
        if ((docType.equals(LUConstants.PROPOSAL_TYPE_COURSE_CREATE)) ||
            (docType.equals(LUConstants.PROPOSAL_TYPE_COURSE_MODIFY))) {
        	partScreenPath = Locations.COURSE_PROPOSAL.getLocation();
        } else
            if (docType.equals(LUConstants.PROPOSAL_TYPE_COURSE_RETIRE)) {
            	partScreenPath = Locations.COURSE_RETIRE_BY_PROPOSAL.getLocation();
            }        
        	
        // Return full URL
		return partBaseAppURL + partScreenPath + partDocId + partIdType + partDocType;
				
				
	}
	
/**
 * Adds a row to results array.
 * Checks for existing multi-curriculum oversight unit courses first, 
 * in which case we just the new oversight unit to the oversight column
 * in the existing row, and sort them.
 * 
 * @param vpacResultRows result list
 * @param docId - proposal id used for navigation
 * @param oversightUnit Used for viewing in the table 
 * @param courseCode Used for viewing in the table
 * @param courseTitle Used for viewing in the table
 * @param proposalType Used for viewing in the table
 * @param htmlLink Used for viewing in the table
 */
	protected void addVpacRow(List<VpacTableRow> vpacResultRows, String docId, String oversightUnit, 
			String courseCode, String courseTitle, String proposalType, String htmlLink){

		boolean foundMultiOversightUnitcourse = false;
		
		//Loop through, checking for existing course code, and the same proposalId
		//Append oversightUnit if found.
		for (VpacTableRow row:vpacResultRows){
			if ((courseCode!=null) && (courseCode.equals(row.getCourseCode())) 
					&& (docId!=null) && (docId.equals(row.getCluId()))){	

				// Add new unit
				row.setCurriculumOversightUnit(row.getCurriculumOversightUnit() + "; " + oversightUnit);
				// Split up oversight units
				String[] splitOUs = row.getCurriculumOversightUnit().split("; ");
				// Sort 'em
				java.util.Arrays.sort(splitOUs);
				// Put 'em back together.
				String mergedOUs="";
				for (String s:splitOUs){
					if (mergedOUs.isEmpty()){
						mergedOUs = s;
					} else {
						mergedOUs = mergedOUs + "; " + s;	
					}
				}				
			
				// Set sorted list.
				row.setCurriculumOversightUnit(mergedOUs);
				
				foundMultiOversightUnitcourse = true;
			}
		}
		
		// If it's just one oversight unit, just add the row to the results. 
		if (!foundMultiOversightUnitcourse)
        vpacResultRows.add(new VpacTableRow(docId, oversightUnit, courseCode, courseTitle,
        		proposalType, htmlLink));        
		
        return;
     	
	}
	
	/**
	 * Performs the Vpac search. Populates tableModel with results.
	 * 
	 * @param startAt - presume this changes what result is first, not sure why you'd
	 * ever want to start at anything but 0.
	 * @param size - size of a page of results. currently set to 10.
	 */	
	 protected void performVpacAgendaSearch(int startAt, int size) {
		 
			// Setup Search Request object.
			searchRequest.setSearchKey("lu.search.VPACProposals");			 			
		
			// Set start value and # results needed, if pagination is being used.
	        searchRequest.setStartAt(startAt);
	        if (size != 0) {
	        	searchRequest.setNeededTotalResults(false);
	        	searchRequest.setMaxResults(size);
	        } else {
	        	searchRequest.setNeededTotalResults(true);
	        }

	        // Perform the search and process results.
	        searchServiceAsync.search(searchRequest, new KSAsyncCallback<SearchResult>(){

	            @Override
	            public void handleFailure(Throwable cause) {
	                GWT.log("Failed to perform search", cause);
	                Window.alert("Failed to perform search");
	            }

	            @Override
	            public void onSuccess(SearchResult results) {
	       		 
	    			// List of Row Results for GWT Cell Table.
	    			List<VpacTableRow> vpacResultRows = new ArrayList<VpacTableRow>();
	    			
	            	// Populate model with results, parse one row at a time.
	                if(results != null && results.getRows() != null && results.getRows().size() != 0){
	                    for (SearchResultRow r: results.getRows()){
	                        
	                        // Construct a row of Values for our Cell table.
	                        // The last column is the HTML link which needs to be constructed.                        	            
	                        	                        
	                        // Get values from search row result.
	                    	
	                        // Doc Id/Type are For navigation
	                    	String docId = r.getCells().get(0).getValue();	                        
	                        String docType = r.getCells().get(1).getValue();	                        
	                        String oversightUnit = r.getCells().get(2).getValue();
	                        String courseCode = r.getCells().get(3).getValue();
	                        String courseTitle = r.getCells().get(4).getValue();
	                        String proposalType = r.getCells().get(5).getValue();
	                                                
	                        String htmlLink = buildLink(docId, docType);
	                        
	                        // Add one row at a time to results.
	           
	                        addVpacRow(vpacResultRows, docId, oversightUnit, courseCode, courseTitle,
	                        		proposalType, htmlLink);
	                    
	                        // Increment count as each row is added.
	                        numberOfProposals++;
	                    
	                    
	                    } 
	                    
	                    // Put rows into Table model all at once.
	                    vpacTableModel.setRows(vpacResultRows);	                    
	                } 
	                
	                // Add Table to the layout *after* search runs and populates it.
	                addVpacTable();
	           }
	        });
	    }
	 
	 /**
	  * Adds VPAC table to the scroll panel.
	  * Create and add count label.
	  * Add VPAC table to layout.
	  * 
	  */
	 protected void addVpacTable(){
		 
	        // Set the total row count. This isn't strictly necessary, but it affects
	        // paging calculations, so its good habit to keep the row count up to date.
	       vpacCellTable.setRowCount(vpacTableModel.getRows().size(), true);

	        // Push the data into the view (widget).
	       vpacCellTable.setRowData(0,vpacTableModel.getRows());	       
	       vpacCellTable.setWidth("100%");
	       
	       // Generate config style count label on top.
	       String plural = "s";
	       if (numberOfProposals==1){
	    	   plural="";
	       }
	       Label count = new Label(String.valueOf(numberOfProposals) + " proposal"+plural+" on the agenda");
	       count.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
	       // This style is set in UMDGeneral.css
	       count.setStylePrimaryName("vpacAgendaCount");
	       
	       // Add Count label on top.
	       layout.add(count);
	       
	       // Add VPAC Table to the layout panel.
	       layout.add(vpacCellTable);

	       layout.setWidth("100%");
   
	 }
    
	@Override
	public boolean isExportButtonActive() {
		return true;
	}

	protected static String getMessage(String key) {
		return Application.getApplicationContext().getMessage(key);
	}

	  
    public VpacTableModel getVpacTableModel() {
        return vpacTableModel;
    }

}
