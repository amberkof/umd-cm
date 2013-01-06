package edu.umd.ks.cm.util.siscm.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.infc.SearchResult;
import org.kuali.student.r2.core.search.infc.SearchResultCell;
import org.kuali.student.r2.core.search.infc.SearchResultRow;
import org.kuali.student.r2.lum.clu.service.CluService;
 

public class CoreGenEdClusetMapper {
	
	private static final String CLUSET_ENGLISH_TO_GUID_MAP = "A";
	private static final String CLUSET_ENGLISH_TO_DESCRIPTION_MAP = "B";

	protected int cacheMaxSize = 100;
	protected int cacheMaxAgeSeconds = 300;
	
	private CluService luservice;
	
	protected Map<String,MaxAgeSoftReference<Map<String,String>>> cache = Collections.synchronizedMap(new MaxSizeMap<String,MaxAgeSoftReference<Map<String,String>>>( cacheMaxSize ));
	
	protected Map<String,String> getCache(String key) {
		MaxAgeSoftReference<Map<String,String>> ref = cache.get(key);
		if ( ref != null ) {
			return ref.get();
		}
		return null;
	}
	protected Map<String,String> getCluSetEnglishToGuidMap(ContextInfo contextInfo){
		Map<String,String> map = getCache(CLUSET_ENGLISH_TO_GUID_MAP);
		if ( map == null ) {
			map = doSearchAndAddToCaches(CLUSET_ENGLISH_TO_GUID_MAP,contextInfo);
		}
		return map;
	}
	
	protected Map<String,String> getCluSetEnglishToDescriptionMap(ContextInfo contextInfo){
		Map<String,String> map = getCache(CLUSET_ENGLISH_TO_DESCRIPTION_MAP);
		if ( map == null ) {
			map = doSearchAndAddToCaches(CLUSET_ENGLISH_TO_DESCRIPTION_MAP,contextInfo);
		}
		return map;
	}

	private Map<String, String> doSearchAndAddToCaches(String key, ContextInfo contextInfo) {
		try{
			SearchRequestInfo sr = new SearchRequestInfo("lu.search.cluSetsWithSisCodes");
			SearchResult searchResult = luservice.search(sr,contextInfo);

			Map<String,String> guidMap = new HashMap<String,String>();
			Map<String,String> descrMap = new HashMap<String,String>();

			for(SearchResultRow row:searchResult.getRows()){
				
				String id = null;
				String name = null;
				String sisCode = null;
				
				for(SearchResultCell cell:row.getCells()){
				
					if("lu.resultColumn.cluSetId".equals(cell.getKey())){
						id = cell.getValue();
					}else if("lu.resultColumn.cluSetName".equals(cell.getKey())){
						name = cell.getValue();
					}else if("lu.resultColumn.cluSetAttributeValue".equals(cell.getKey())){
						sisCode = cell.getValue();
					}
				}
				
				guidMap.put(sisCode, id);
				descrMap.put(sisCode, name);
			}
			
			cache.put(CLUSET_ENGLISH_TO_GUID_MAP, new MaxAgeSoftReference<Map<String,String>>(cacheMaxAgeSeconds, guidMap));
			cache.put(CLUSET_ENGLISH_TO_DESCRIPTION_MAP, new MaxAgeSoftReference<Map<String,String>>(cacheMaxAgeSeconds, descrMap));
			
			if(CLUSET_ENGLISH_TO_GUID_MAP.equals(key)){
				return guidMap;
			}
			
			if(CLUSET_ENGLISH_TO_DESCRIPTION_MAP.equals(key)){
				return descrMap;
			}
			
		}catch(Exception e){
			throw new RuntimeException("Error searching for CORE/GenEd clusets for mapping",e);
		}
		
		return null;
	}

	public String getCluSetId(String coreGenEdClusetName, ContextInfo contextInfo) {
		return getCluSetEnglishToGuidMap(contextInfo).get(coreGenEdClusetName);
	}

	public List<String> getCluSetIds(ContextInfo contextInfo) {
		return new ArrayList<String>(getCluSetEnglishToGuidMap(contextInfo).values());
	}
	
	public Map<String, String> getMap(ContextInfo contextInfo){
		return getCluSetEnglishToGuidMap(contextInfo);
	}
	
	public Map<String, String> getCodeToDescriptionMap(ContextInfo contextInfo){
		return getCluSetEnglishToDescriptionMap(contextInfo);
	}
	public void setLuservice(CluService luservice) {
		this.luservice = luservice;
	}
}
