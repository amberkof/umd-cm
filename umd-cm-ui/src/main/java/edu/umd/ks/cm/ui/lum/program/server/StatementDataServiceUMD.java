/*********************************************************************************
*This class is loaded at runtime and overrides the KS class StatementDataService
*(overriding process happens in umd-cm-lum-gwt-context.xml)
*********************************************************************************/
package edu.umd.ks.cm.ui.lum.program.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.core.statement.dto.ReqComponentTypeInfoUMD;
import org.kuali.student.lum.program.server.StatementDataService;
import org.kuali.student.r1.core.statement.dto.ReqComponentTypeInfo;



public class StatementDataServiceUMD extends StatementDataService{
	
	final static Logger LOG = Logger.getLogger(StatementDataServiceUMD.class);
	
	//KSCM-765 This method gets the Course Requisites that are used in the rules Dropdown on the Course proposal screen 
	//Method is being overridden so that the Requisites in the drop down can be sorted
	@Override
    public List<ReqComponentTypeInfo> getReqComponentTypesForStatementType(String luStatementTypeKey) throws Exception {
        List<ReqComponentTypeInfo> reqComponentTypeInfoList;
        try { 
            reqComponentTypeInfoList = statementService.getReqComponentTypesForStatementType(luStatementTypeKey);
            
            //Use this code to cast reqComponentTypeInfoList to reqComponentTypeInfoListUMD so that the dropdown
            //can be sorted using Collecions.sort, which is only applicapble to type reqComponentTypeInfoListUMD
            List<ReqComponentTypeInfoUMD> reqComponentTypeInfoListUMD =new ArrayList<ReqComponentTypeInfoUMD>();
            for(ReqComponentTypeInfo elem:reqComponentTypeInfoList)
            {
            	ReqComponentTypeInfoUMD temp=new ReqComponentTypeInfoUMD();
            	temp.clone(elem);
            	reqComponentTypeInfoListUMD.add(temp);
            }
            
            //sort the list
            Collections.sort(reqComponentTypeInfoListUMD);
            
            //copy all the elements back into reqComponentTypeInfoList to prevent return type conflicts
            reqComponentTypeInfoList.clear();
            for(ReqComponentTypeInfoUMD elem:reqComponentTypeInfoListUMD)
            {
            	reqComponentTypeInfoList.add(((ReqComponentTypeInfo)elem));
            }
            
        } catch (Exception ex) {
            LOG.error(ex);
            throw new Exception("Unable to find Requirement Component Types based on LU Statement Type Key:" + luStatementTypeKey, ex);
        }
        
        return reqComponentTypeInfoList;
    }
}
