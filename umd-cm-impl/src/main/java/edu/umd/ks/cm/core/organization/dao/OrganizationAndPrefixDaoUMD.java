package edu.umd.ks.cm.core.organization.dao;

import java.util.List;

import org.kuali.student.common.dao.CrudDao;
import org.kuali.student.common.dao.SearchableDao;
import org.kuali.student.common.exceptions.DoesNotExistException;

import edu.umd.ks.cm.core.organization.entity.Unit;

public interface OrganizationAndPrefixDaoUMD extends CrudDao, SearchableDao {
	public <T> T fetch(Class<T> clazz, Long key) throws DoesNotExistException;
	
	public List<Long> getAllAncestors(Long orgId, String orgHierarchy);

	public List<Unit> getOrganizationsByIdList(List<Long> orgIdList);

	public List<Unit> getAllAncestorsOfType(List<Long> unitIds, List<String> type);
}
