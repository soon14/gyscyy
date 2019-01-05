package com.aptech.business.ticketManage.workticketIntervention.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.workticketIntervention.domain.WorkticketInterventionEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 介入工作票应用数据类
 *
 * @author 
 * @created 2017-08-02 11:40:17
 * @lastModified 
 * @history
 *
 */
@Repository("workticketInterventionDao")
public class WorkticketInterventionDaoImpl extends AncestorDao<WorkticketInterventionEntity> implements WorkticketInterventionDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.ticketManage.workticketIntervention";
	}
}
