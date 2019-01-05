package com.aptech.business.ticketManage.workSituation.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.workSituation.domain.WorkSituationEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 开工和收工情况应用数据类
 *
 * @author 
 * @created 2017-06-05 17:12:39
 * @lastModified 
 * @history
 *
 */
@Repository("workSituationDao")
public class WorkSituationDaoImpl extends AncestorDao<WorkSituationEntity> implements WorkSituationDao{
	
	@Override
	public String getNameSpace() {
		return "com.aptech.business.workSituation";
	}
}
