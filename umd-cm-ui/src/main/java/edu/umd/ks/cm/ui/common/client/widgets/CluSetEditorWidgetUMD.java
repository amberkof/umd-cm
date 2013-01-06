package edu.umd.ks.cm.ui.common.client.widgets;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.lum.common.client.widgets.CluSetEditorWidget;
import org.kuali.student.lum.common.client.widgets.CluSetRetriever;
import org.kuali.student.lum.common.client.widgets.CommonWidgetConstants;

public class CluSetEditorWidgetUMD extends CluSetEditorWidget {

    public CluSetEditorWidgetUMD() {
        super();
    }
    
    public CluSetEditorWidgetUMD(CluSetRetriever cluSetRetriever, Enum<?> viewEnum, String name, String modelId,
            boolean showTitle, Callback<Boolean> onReady, String cluSetType, boolean singularCluOnly) {
        super(cluSetRetriever, viewEnum, name, modelId, showTitle, onReady, cluSetType, singularCluOnly);

    }

    public static class CluSetEditOptionDropdownUMD extends CluSetEditOptionDropdown {
        
        public CluSetEditOptionDropdownUMD(){
            SimpleListItems editOptions = new SimpleListItems();

            if (cluSetType != null && cluSetType.equals("kuali.cluSet.type.Program")) {
                editOptions.addItem(CommonWidgetConstants.CLU_SET_SWAP_APPROVED_CLUS, "Approved Programs");
                editOptions.addItem(CommonWidgetConstants.CLU_SET_SWAP_PROPOSED_CLUS, "Proposed Programs");
                if (!singularCluOnly) {
                    editOptions.addItem(CommonWidgetConstants.CLU_SET_SWAP_CLU_SETS, "Program Sets");
                }
                
            } else {
                editOptions.addItem(CommonWidgetConstants.CLU_SET_SWAP_APPROVED_CLUS, "Approved Courses");
                editOptions.addItem(CommonWidgetConstants.CLU_SET_SWAP_PROPOSED_CLUS, "Proposed Courses");
                if (!singularCluOnly) {
                    editOptions.addItem(CommonWidgetConstants.CLU_SET_SWAP_CLU_SETS, "Course Sets");
                    editOptions.addItem(CommonWidgetConstants.CLU_SET_SWAP_CLU_SET_RANGE, "Course Ranges");
                }
            }
          super.setListItems(editOptions);
          super.selectItem(CommonWidgetConstants.CLU_SET_SWAP_APPROVED_CLUS);
        }
    }
}
