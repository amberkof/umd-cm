/********************************************************************************* 
* WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING
* WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING
* WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING
* WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING
* WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING
* WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING
* WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING
* WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING
* WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING
*
* READ BELOW BEFORE MAKING ANY CHANGES TO THIS CLASS
*
* ReqComponentTypeInfo is a KS class that uses Entity to store KSST_REQ_COM_TYPE table rows
* as objects. These objects are used to populate the Course requisites drop down on
* the Course Proposal page. ReqComponentTypeInfo inherits the Serializable interface
* from its parent class TypeInfo. GWT uses Serializable  to populate the drop down. 
* The population process is completely dependent on a GWT Serializable "white list" 
* (generated at RUNTIME) that specifies which types have permission to be used in the
* population process. This white list is generated at RUNTIME by adding all the types 
* in the RPC interface (in this case the KS class StatementRPCService) and storing them in 
* in a file in the target folder. However, UMD does not want to override the RPC interface
* as there are no changes to it. Because ReqComponentTypeInfoUMD is not in the RPC interface,
* it is not automatically added to the whitelist. The Devs have tried multiple methods to 
* manually add it to white list (listed below) before deciding to change the UMD package name
* to the one used in KS. This is completely incorrect but will be used unless problems whitelisting 
* become a recurrent problem in UMDCM. If this becomes the case, the Devs will do further research
* into the problem and find a better solution 
* 
* Methods attempted:
* 
* Adding a default constructor to ReqComponentTypeInfoUMD and ReqComponentTypeInfo
* 
* Having ReqComponentTypeInfoUMD implement isSerializable interface
* 
* Manually typing the class into the whitelist file (will not work b.c file is made at runtime)
* 
* Numbers 4. and 5. listed here:
* http://isolasoftware.it/2011/03/22/gwt-serialization-policy-error/
* 4. Adding the UMD package path to CommonUMD using <source path>. If this method is included in
*    the fix the tag <source path="client" /> MUST be included as it is loaded by default if no 
*    other <source path> is loaded.
* 5. Copying the Statement.gwt.xml file in KS and making an identical StatementUMD.gwt.xml file
*    in UMDCM and inheriting StatementUMD in UMDCMMain.gwt.xml
* 
* ReqComponentTypeInfo is a KS class that uses Entity to store KSST_REQ_COM_TYPE table rows
* as objects. These objects are used to populate the Course requisites drop down on
* the Course Proposal page. ReqComponentTypeInfo also uses an "attributes" map that 
* stores the owner's attributes (specified in KSST_REQ_COM_TYPE_ATTR table). UMD
* is overriding ReqComponentTypeInfo to allow use of attributes that do not exist in KS.
*********************************************************************************/


package org.kuali.student.core.statement.dto;

import org.kuali.student.r1.core.statement.dto.ReqComponentTypeInfo;


public class ReqComponentTypeInfoUMD extends ReqComponentTypeInfo implements Comparable<ReqComponentTypeInfoUMD>{
	private static final long serialVersionUID = 1L;
	
	//compareTo calls the SORT_ORDER attribute and uses it to sort ReqComponentTypeInfoUMD elements in a list
	public int compareTo(ReqComponentTypeInfoUMD sortElement)
	{
		if(this.getAttributes()!=null && this.getAttributes().get("SORT_ORDER")!=null && sortElement.getAttributes()!=null && sortElement.getAttributes().get("SORT_ORDER")!=null)
			return Integer.parseInt(this.getAttributes().get("SORT_ORDER"))-Integer.parseInt(sortElement.getAttributes().get("SORT_ORDER"));
		return 0;
	}
	
	//Clone method allows down casting of ReqComponentTypeInfo objects
	public void clone(ReqComponentTypeInfo copy)
	{
		this.setId(copy.getId());
		this.setDescr(copy.getDescr());
		this.setName(copy.getName());
		this.setAttributes(copy.getAttributes());
		this.setEffectiveDate(copy.getEffectiveDate());
		this.setExpirationDate(copy.getExpirationDate());
		this.setReqCompFieldTypeInfos(copy.getReqCompFieldTypeInfos());
	}
}
