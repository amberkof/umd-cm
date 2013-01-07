package edu.umd.ks.cm.core.organization.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;

import org.kuali.student.r1.common.dao.impl.AbstractCrudDaoImpl;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;

import edu.umd.ks.cm.core.organization.entity.Unit;

public class OrganizationAndPrefixDaoImplUMD extends AbstractCrudDaoImpl implements OrganizationAndPrefixDaoUMD{
	@Override
	public List<Long> getAllAncestors(Long unitId, String orgHierarchy) {
		Query query = em.createNamedQuery("UnitRelation.getAncestors");
		return getAllLevels(query, "unitId", unitId);
	}
	
	private List<Long> getAllLevels(Query query, String paramName, Long paramValue) {
		// Eliminate dup's by using a set, but maintain order elements were inserted in
		Set<Long> valSet = new LinkedHashSet<Long>();
		query.setParameter(paramName, paramValue);
		@SuppressWarnings("unchecked")
		List<Long> nextLevelList = query.getResultList();
		valSet.addAll(nextLevelList);
		for (Long resultStr : nextLevelList) {
			valSet.addAll(getAllLevels(query, paramName, resultStr));
		}
		return new ArrayList<Long>(valSet);
	}
	
	@Override
	public List<Unit> getOrganizationsByIdList(List<Long> unitIdList) {
		Query query = em.createNamedQuery("Unit.getUnitsByIdList");
        query.setParameter("unitIdList", unitIdList);
        @SuppressWarnings("unchecked")
		List<Unit> units = query.getResultList();
        return units;
	}
	
	 
	@Override
	public List<Unit> getAllAncestorsOfType(List<Long> unitIdList,
			List<String> typeList) {
		Query query;
		if(typeList==null||typeList.isEmpty()||(typeList.contains("Department") && typeList.contains("College"))){
			query = em.createNamedQuery("UnitRelation.getAncestorsByIdListAndTypeList");
		}else if(typeList.contains("Department")){
			query = em.createNamedQuery("UnitRelation.getAncestorsByIdListAndTypeListDepartment");
		}else if(typeList.contains("College")){
			query = em.createNamedQuery("UnitRelation.getAncestorsByIdListAndTypeListCollege");
		}else{
			query = em.createNamedQuery("UnitRelation.getAncestorsByIdListAndTypeList");
		}
        return getAllLevelsOfType(query, unitIdList);
	}

	private List<Unit> getAllLevelsOfType(Query query, 
			List<Long> unitIdList) {
		
		query.setParameter("unitIdList", unitIdList);
		
		if(unitIdList==null||unitIdList.isEmpty()){
			return new ArrayList<Unit> ();
		}
	
		@SuppressWarnings("unchecked")
		List<Unit> nextLevelList = query.getResultList();

		Map<Long,Unit> nextLevelUnits = new HashMap<Long,Unit>();
		for(Unit unit:nextLevelList){
			nextLevelUnits.put(unit.getId(),unit);
		}
		if(!nextLevelUnits.isEmpty()){
			for(Unit unit:getAllLevelsOfType(query, new ArrayList<Long>(nextLevelUnits.keySet()))){
				nextLevelUnits.put(unit.getId(),unit);
			}
		}
		return new ArrayList<Unit>(nextLevelUnits.values());
	}

 
	@Override
	public <T> T fetch(Class<T> clazz, String key) throws DoesNotExistException {
		T entity = em.find(clazz, key);
		if (entity == null) {
			throw new DoesNotExistException("No entity for key '" + key + "' found for " + clazz);
		}
		return entity;
	}
}
