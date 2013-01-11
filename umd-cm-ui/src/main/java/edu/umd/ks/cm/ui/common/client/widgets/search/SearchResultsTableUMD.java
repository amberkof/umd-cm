package edu.umd.ks.cm.ui.common.client.widgets.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.util.DebugIdUtils;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.FieldLayoutComponent;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.common.ui.client.widgets.search.SearchResultsTable;
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.common.ui.client.widgets.searchtable.ResultRow;
import org.kuali.student.r1.common.assembly.data.LookupResultMetadata;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SortDirection;
import org.kuali.student.r2.core.search.infc.SearchResultCell;
import org.kuali.student.r2.core.search.infc.SearchResultRow;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.AsyncHandler;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.cellview.client.ColumnSortList.ColumnSortInfo;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.HasRows;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

public class SearchResultsTableUMD extends SearchResultsTable {

    private CellTable<Map<String, String>> table;
    @SuppressWarnings("rawtypes")
    private AsyncDataProvider dataProvider;
    private HorizontalPanel pagerPanel = new HorizontalPanel();
    private SelectionModel<Map<String, String>> selectionModel;
    private VerticalFlowPanel noResultsPanel;
    private SimplePager pager;
    private ListBox pageSelection = new ListBox();
    private KSLabel pageSelectionLabel = new KSLabel("Skip to page");

    /**
     * 
     * Define our own style for the celltable.
     * 
     * @author Kuali Rice Team (rice.collab@kuali.org)
     */
    public interface CellTableResource extends com.google.gwt.user.cellview.client.CellTable.Resources {
        public CellTable.Resources INSTANCE =
                GWT.create(CellTableResource.class);

        /**
         * The styles used in this widget.
         */
        @Source("edu/umd/ks/cm/ui/theme/public/css/CellTable.css")
        CellTable.Style cellTableStyle();
        
        /**
         * Setting a spinner image for the loading indicator.
         */
        @Source("edu/umd/ks/cm/ui/theme/public/images/celltable/umd_cellTableLoading.gif")
        ImageResource cellTableLoading();
    }

    /**
     * 
     * Define our own style for the pager at the bottom of the table.
     * 
     * @author Kuali Rice Team (rice.collab@kuali.org)
     */
    public interface SimplePagerResource extends com.google.gwt.user.cellview.client.SimplePager.Resources {
        public SimplePager.Resources INSTANCE = GWT.create(SimplePagerResource.class);

        /**
         * The image used to go to the first page.
         */
        @Source("edu/umd/ks/cm/ui/theme/public/images/celltable/umd_simplePagerFirstPage.png")
        ImageResource simplePagerFirstPage();

        /**
         * The disabled first page image.
         */
        @Source("edu/umd/ks/cm/ui/theme/public/images/celltable/umd_simplePagerFirstPageDisabled.png")
        ImageResource simplePagerFirstPageDisabled();

        /**
         * The image used to go to the last page.
         */
        @Source("edu/umd/ks/cm/ui/theme/public/images/celltable/umd_simplePagerLastPage.png")
        ImageResource simplePagerLastPage();

        /**
         * The disabled last page image.
         */
        @Source("edu/umd/ks/cm/ui/theme/public/images/celltable/umd_simplePagerLastPageDisabled.png")
        ImageResource simplePagerLastPageDisabled();

        /**
         * The image used to go to the next page.
         */
        @Source("edu/umd/ks/cm/ui/theme/public/images/celltable/umd_simplePagerNextPage.png")
        ImageResource simplePagerNextPage();

        /**
         * The disabled next page image.
         */
        @Source("edu/umd/ks/cm/ui/theme/public/images/celltable/umd_simplePagerNextPageDisabled.png")
        ImageResource simplePagerNextPageDisabled();

        /**
         * The image used to go to the previous page.
         */
        @Source("edu/umd/ks/cm/ui/theme/public/images/celltable/umd_simplePagerPreviousPage.png")
        ImageResource simplePagerPreviousPage();

        /**
         * The disabled previous page image.
         */
        @Source("edu/umd/ks/cm/ui/theme/public/images/celltable/umd_simplePagerPreviousPageDisabled.png")
        ImageResource simplePagerPreviousPageDisabled();
    }

    public SearchResultsTableUMD() {
        super();
    }

    @Override
    public void initializeTable(String searchId, List<LookupResultMetadata> listResultMetadata, String resultIdKey, String resultDisplayKey) {
        table = new CellTable<Map<String, String>>(PAGE_SIZE, CellTableResource.INSTANCE);
        //        table = new CellTable<Map<String, String>>(PAGE_SIZE);

        this.resultIdColumnKey = resultIdKey;
        this.resultDisplayKey = resultDisplayKey;
                
        createTableColumns(searchId, listResultMetadata);
        configureSelectionModel();
        configurePager();
        configureDataProvider();

        // Add a ColumnSortEvent.AsyncHandler to connect sorting to the AsyncDataProvider.
        AsyncHandler columnSortHandler = new AsyncHandler(table);
        table.addColumnSortHandler(columnSortHandler);

        // Configure a no-matches-found message for the table.
        noResultsPanel = new VerticalFlowPanel();
        noResultsPanel.add(new KSLabel("No matches found"));
        if (withMslable)
            noResultsPanel.add(mslabel);
        noResultsPanel.addStyleName("ks-no-results-message");
        noResultsPanel.setVisible(false);

        redraw();
        layout.add(table);
        layout.add(noResultsPanel);
        layout.add(pagerPanel);

        // Go to a selected page.
        pageSelection.addChangeHandler(new ChangeHandler() {

            @Override
            public void onChange(ChangeEvent event) {
                Integer n = new Integer(pageSelection.getSelectedIndex());
                pager.setPage(n);
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public void removeContent() {
        if (dataProvider != null) {
            dataProvider.updateRowData(0, Collections.emptyList());
        }
    }

    @Override
    public List<String> getSelectedIds() {
        List<String> ids = new ArrayList<String>();

        for (ResultRow row : getSelectedRows()) {
            ids.add(row.getId());
        }

        return ids;
    }

    @Override
    public List<ResultRow> getSelectedRows() {
        List<ResultRow> rows = new ArrayList<ResultRow>();

        if (table.getSelectionModel() instanceof SingleSelectionModel) {
            rows = getSingleSelectedItem();
        } else if (table.getSelectionModel() instanceof MultiSelectionModel) {
            rows = getMultipleSelectedItems();
        }

        return rows;
    }

    /**
     * 
     * This overridden method does not do the actual data search, because this function is
     * provided by the AsyncDataProvider which is configured for the celltable.
     * 
     * @see edu.umd.ks.cm.ui.common.client.widgets.search.SearchResultsTableUMD#configureDataProvider()
     * 
     * @see org.kuali.student.common.ui.client.widgets.search.SearchResultsTable#performOnDemandSearch(int,
     * int)
     */
    @Override
    protected void performOnDemandSearch(final int startAt, int size) {
        // The method is intentially empty because the data read/search is done by the AsyncDataProvider 
        // which is configured for the celltable - see the method configureDataProvider().
    }

    /**
     * 
     * This method returns the value for a single selected row on the table.
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<ResultRow> getSingleSelectedItem() {
        ResultRow resultRow = new ResultRow();
        List<ResultRow> rows = new ArrayList<ResultRow>();
        Map<String, String> item = ((SingleSelectionModel<Map<String, String>>) table.getSelectionModel())
                .getSelectedObject();
        if (item != null) {
            for (Map.Entry<String, String> entry : item.entrySet()) {
                if (entry.getKey().equals(resultIdColumnKey)) {
                    resultRow.setId(entry.getValue());
                }
                resultRow.setValue(entry.getKey(), entry.getValue());
            }

            rows.add(resultRow);
        }
        return rows;
    }

    /**
     * 
     * This method configures a data provider for the celltable. The function of the
     * <code>AsyncDataProvider</code> is to execute a service call every time the table
     * needs to be updated, i.e. when data changed.
     * 
     * The results from the service provides the data records to the table.
     * 
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void configureDataProvider() {
        dataProvider = new AsyncDataProvider() {
            private ColumnSortList sortList;

            @Override
            protected void onRangeChanged(final HasData display) {
                final int start = display.getVisibleRange().getStart();
                searchRequest.setStartAt(start);
                searchRequest.setMaxResults(display.getVisibleRange().getLength());
                searchRequest.setNeededTotalResults(true);

                // Get the column sort info from the table.
                sortList = table.getColumnSortList();
                if (sortList != null && sortList.size() > 0) {
                    ColumnSortInfo columnSortInfo = sortList.get(0);
                    IndexedTextCellColumn col = (IndexedTextCellColumn) columnSortInfo.getColumn();
                    searchRequest.setSortColumn(col.getKeyId());
                    if (columnSortInfo.isAscending()) {
                        searchRequest.setSortDirection(SortDirection.ASC);
                    } else {
                        searchRequest.setSortDirection(SortDirection.DESC);
                    }
                }

                searchRpcServiceAsync.search(searchRequest, new KSAsyncCallback<SearchResultInfo>() {

                    @Override
                    public void handleFailure(Throwable cause) {
                        GWT.log("Failed to perform search", cause);
                        Window.alert("Failed to perform search");
                    }

                    public void onSuccess(SearchResultInfo results) {
                        List<Map<String, String>> newDataList = new ArrayList<Map<String, String>>();
                        if (results != null && results.getRows() != null && results.getRows().size() != 0) {
                            for (SearchResultRow r : results.getRows()) {
                                Map<String, String> row = new TreeMap<String, String>();
                                for (SearchResultCell c : r.getCells()) {
                                    row.put(c.getKey(), c.getValue());
                                }
                                newDataList.add(row);
                            }

                            // Configure the page selection listbox.
                            populatePageListBox(results.getTotalResults());

                        } else {
                            // Enable the no-matches-found message if no search results.
                            noResultsPanel.setVisible(true);
                            pageSelection.setEnabled(false);

                        }
                        // Push the data back into the table.
                        updateRowData(start, newDataList);
                        // Set the total record count for pagination
                        if (results != null && results.getTotalResults() != null) {
                            updateRowCount(results.getTotalResults(), true);
                        }
                    }
                });
            }
        };

        // Connect the list to the data provider.
        dataProvider.addDataDisplay(table);
    }

    /**
     * 
     * This method populates the page selection listbox.
     * 
     * @param totalResults
     */
    private void populatePageListBox(int totalResults) {
        pageSelection.clear();
        int pageIntervals = totalResults / PAGE_SIZE;
        if (totalResults % PAGE_SIZE > 0) {
            pageIntervals++;
        }

        for (int i = 0; i < pageIntervals; i++) {
            pageSelection.addItem(Integer.toString(i + 1));
        }

        // Keep the page selection listbox index insynch with the pager.
        pageSelection.setSelectedIndex(pager.getPage());
    }

    /**
     * 
     * This method returns the values of all the rows seelcted on the table.
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<ResultRow> getMultipleSelectedItems() {
        List<ResultRow> rows = new ArrayList<ResultRow>();
        Set<Map<String, String>> selected = ((MultiSelectionModel<Map<String, String>>) table.getSelectionModel())
                .getSelectedSet();

        Iterator<Map<String, String>> it = selected.iterator();
        while (it.hasNext()) {
            Map<String, String> item = it.next();
            ResultRow resultRow = new ResultRow();

            for (Map.Entry<String, String> entry : item.entrySet()) {
                if (entry.getKey().equals(resultIdColumnKey)) {
                    resultRow.setId(entry.getValue());
                }
                resultRow.setValue(entry.getKey(), entry.getValue());
            }

            rows.add(resultRow);
        }

        return rows;
    }

    /**
     * 
     * This method add the columns to the table.
     * 
     * @param listResultMetadata
     */
    private void createTableColumns(String searchId, List<LookupResultMetadata> listResultMetadata) {
        // Set the width of the table and put the table in fixed width mode.
        table.setWidth("100%", true);

        // Create a checkbox in the table header for multiple selection.
        if (isMultiSelect) {
            CheckboxCell selectCell = new CheckboxCell(false, false) {
                @Override
                public void render(Context context, Boolean value, SafeHtmlBuilder sb) {
                    //This method is only overridden to properly set the debugId.
                    //TODO: If we can get a handle to the actual checkbox element we could use UIOjbect.ensureDebugId to set the id instead.
                    
                    // Get the view data.
                    Object key = context.getKey();
                    Boolean viewData = getViewData(key);
                    if (viewData != null && viewData.equals(value)) {
                      clearViewData(key);
                      viewData = null;
                    }
                    
                    String debugId = null;
                    if (key instanceof TreeMap) {//same type as set in AsyncDataProvider
                        @SuppressWarnings("unchecked")
                        TreeMap<String,String> tableRowMap = (TreeMap<String, String>) key;
                        StringBuilder columnValues = new StringBuilder();
                        for (String columnvalue : tableRowMap.values()) {
                            columnValues.append(columnvalue);
                            columnValues.append("-");
                            
                        }
                        columnValues.deleteCharAt(columnValues.length() - 1);//remove the last '-' character
                        debugId = DebugIdUtils.createWebDriverSafeDebugId(columnValues.toString());
                    }
                                        
                    SafeHtml inputChecked = null;
                    SafeHtml inputUnchecked = null;
                    if (debugId != null) {
                        inputChecked = SafeHtmlUtils.fromSafeConstant("<input id=\"" + debugId + "\" type=\"checkbox\" tabindex=\"-1\" checked/>");
                        inputUnchecked = SafeHtmlUtils.fromSafeConstant("<input id=\"" + debugId + "\" type=\"checkbox\" tabindex=\"-1\"/>");
                    } else {
                        inputChecked = SafeHtmlUtils.fromSafeConstant("<input type=\"checkbox\" tabindex=\"-1\" checked/>");
                        inputUnchecked = SafeHtmlUtils.fromSafeConstant("<input type=\"checkbox\" tabindex=\"-1\"/>");
                    }
                    
                    if (value != null && ((viewData != null) ? viewData : value)) {
                      sb.append(inputChecked);
                    } else {
                      sb.append(inputUnchecked);
                    }
                }
            };
            
            Column<Map<String, String>, Boolean> selectColumn = new Column<Map<String, String>, Boolean>(
                    selectCell) {
                @Override
                public Boolean getValue(Map<String, String> object) {
                    // Get the value from the selection model.
                    return selectionModel.isSelected(object);
                }
            };

            selectColumn.setFieldUpdater(new FieldUpdater<Map<String, String>, Boolean>() {
                public void update(int index, Map<String, String> object, Boolean value) {
                    // Called when the user clicks on a checkbox. 
                    selectionModel.setSelected(object, value);
                }
            });
            
            CheckboxCell selectAllCell = new CheckboxCell(false, false) {
                @Override
                public void render(Context context, Boolean value, SafeHtmlBuilder sb) {
                    //This method is only overridden to properly set the debugId.
                    //TODO: If we can get a handle to the actual checkbox element we could use UIOjbect.ensureDebugId to set the id instead.
                    
                    // Get the view data.
                    Object key = context.getKey();
                    Boolean viewData = getViewData(key);
                    if (viewData != null && viewData.equals(value)) {
                      clearViewData(key);
                      viewData = null;
                    }
                    
                    SafeHtml inputChecked = SafeHtmlUtils.fromSafeConstant("<input id=\"selectAll\" type=\"checkbox\" tabindex=\"-1\" checked/>");
                    SafeHtml inputUnchecked = SafeHtmlUtils.fromSafeConstant("<input id=\"selectAll\" type=\"checkbox\" tabindex=\"-1\"/>");

                    if (value != null && ((viewData != null) ? viewData : value)) {
                      sb.append(inputChecked);
                    } else {
                      sb.append(inputUnchecked);
                    }
                }
            };
            
            final Header<Boolean> selectAllHeader = new Header<Boolean>(selectAllCell) {
                @Override
                public Boolean getValue() {
                    return ((MultiSelectionModel<Map<String, String>>) selectionModel).getSelectedSet().size() == table
                            .getRowCount();
                }
            };

            selectAllHeader.setUpdater(new ValueUpdater<Boolean>() {
                public void update(Boolean value) {
                    List<Map<String, String>> displayedItems = table.getVisibleItems();
                    for (Map<String, String> item : displayedItems) {
                        selectionModel.setSelected(item, value);
                    }
                }
            });

            table.addColumn(selectColumn, selectAllHeader);
            // Set the width of the column.
            table.setColumnWidth(selectColumn, 50.0, Unit.PX);
        }

        int totalColumns = listResultMetadata.size() - 1;
        for (LookupResultMetadata lookupResultMetadata : listResultMetadata) {
            if (lookupResultMetadata.isHidden()) {
                totalColumns--;
            } else {
                Column<Map<String, String>, String> cellTableColumn = new IndexedTextCellColumn(
                        lookupResultMetadata.getKey());
                cellTableColumn.setSortable(true);
                String header = "";                
                 
                // KSLAB2571 KSCM1326 - 
                // Overriding column Headers for Advanced Search *Results*.
                String testIt=Application.getApplicationContext().getMessage(searchId + ":"+ lookupResultMetadata.getKey() + FieldLayoutComponent.NAME_MESSAGE_KEY);
                if (Application.getApplicationContext().getMessage(searchId + ":"+ lookupResultMetadata.getKey() + FieldLayoutComponent.NAME_MESSAGE_KEY) != null) {
                    header = Application.getApplicationContext().getMessage(searchId + ":"+ lookupResultMetadata.getKey() + FieldLayoutComponent.NAME_MESSAGE_KEY);
                } else if (Application.getApplicationContext().getMessage(
                        lookupResultMetadata.getKey() + FieldLayoutComponent.NAME_MESSAGE_KEY) != null) {
                    header = Application.getApplicationContext().getMessage(
                            lookupResultMetadata.getKey() + FieldLayoutComponent.NAME_MESSAGE_KEY);
                } else {
                    header = Application.getApplicationContext().getUILabel("", null, null,
                            lookupResultMetadata.getName());
                }
                table.addColumn(cellTableColumn, header);
                // Set the width of the columns.
                if (totalColumns-- > 0) {
                    table.setColumnWidth(cellTableColumn, 100.0, Unit.PX);
                } else {
                    // Expand the last column for the remainder of the table width.
                    table.setColumnWidth(cellTableColumn, 100.0, Unit.PCT);
                }
            }
        }
    }

    /**
     * 
     * This method creates the selection model to be used on selecting a row (rows) on the
     * table.
     * 
     */
    private void configureSelectionModel() {
        // Add a selection model to handle user selection.
        if (isMultiSelect) {
            selectionModel = new MultiSelectionModel<Map<String, String>>();
            table.setSelectionModel(selectionModel,
                    DefaultSelectionEventManager.<Map<String, String>> createCheckboxManager());
        } else {
            selectionModel = new SingleSelectionModel<Map<String, String>>();
            table.setSelectionModel(selectionModel);
        }
    }

    /**
     * 
     * This method create a simple pager for the table.
     * 
     */
    private void configurePager() {
        // Ensure only one instance of pager is shown on the tabel.
        if (null != pager) {
            pagerPanel.remove(pager);
        }
        pager = new SimplePager(TextLocation.CENTER, SimplePagerResource.INSTANCE, false, 1000, true) {
            /**
             * 
             * This overridden method ensure that the last page will show only the remain
             * records, and not the total records set as the pagesize.
             * 
             * @see com.google.gwt.user.cellview.client.SimplePager#setPageStart(int)
             */
            @Override
            public void setPageStart(int index) {
                HasRows display = getDisplay();
                boolean isRangeLimited = !isRangeLimited();
                if (display != null) {
                    Range range = display.getVisibleRange();
                    int pageSize = range.getLength();
                    if (isRangeLimited && display.isRowCountExact()) {
                        index = Math.min(index, display.getRowCount() - pageSize);
                    }
                    index = Math.max(0, index);
                    if (index != range.getStart()) {
                        display.setVisibleRange(index, pageSize);
                    }
                }
            }
        };

        pager.setDisplay(table);
        pager.setPageSize(PAGE_SIZE);
        pagerPanel.add(pager);
        pagerPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        pageSelectionLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        pagerPanel.add(pageSelectionLabel);
        pagerPanel.add(pageSelection);
        pagerPanel.setCellWidth(pagerPanel.getWidget(1), "30%");
        pagerPanel.setCellHorizontalAlignment(pagerPanel.getWidget(1), HasHorizontalAlignment.ALIGN_RIGHT);
    }
    
    class IndexedTextCellColumn extends Column<Map<String, String>, String> {
        private final String keyId;

        public IndexedTextCellColumn(String keyId) {
            super(new DoubleClickTextCell());
            this.keyId = keyId;
        }

        @Override
        public String getValue(Map<String, String> object) {
            return object.get(this.keyId);
        }

        public String getKeyId() {
            return keyId;
        }
    }
    
    class DoubleClickTextCell extends TextCell {

        @Override
        public Set<String> getConsumedEvents() {
             Set<String> consumedEvents = super.getConsumedEvents();
             if (consumedEvents == null) {
                 consumedEvents = new HashSet<String>();
             } 
             consumedEvents.add("dblclick");
             return consumedEvents;
        }

        @Override
        public void onBrowserEvent(Context context, Element parent, String value, NativeEvent event,
                ValueUpdater<String> valueUpdater) {
            super.onBrowserEvent(context, parent, value, event, valueUpdater);
            String eventType = event.getType();
            if ("dblclick".equals(eventType) && !isMultiSelect) {
                onDoubleClick(context, parent, value, event, valueUpdater);
            }
        }
        
        @SuppressWarnings("unchecked")
        private void onDoubleClick(Context context, Element parent, String value, NativeEvent event,
                ValueUpdater<String> valueUpdater) {
            selectionModel.setSelected((Map<String, String>)context.getKey(), true);
            for(Callback<List<SelectedResults>> callback: selectedCompleteCallbacks){
                List<SelectedResults> selectedResultList = new ArrayList<SelectedResults>();
                for (ResultRow row : getSingleSelectedItem()) {
                    String displayKey = row.getValue(resultDisplayKey);
                    String returnKey = row.getValue(resultIdColumnKey);
                    selectedResultList.add(new SelectedResults(displayKey, returnKey, row));
                }
                callback.exec(selectedResultList);
            }  
            return;
        }
    }
}
