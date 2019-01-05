package com.aptech.business.ticketManage.workticketRepair.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.workticketRepair.domain.WorkticketRepairEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 紧急抢修单应用数据类
 *
 * @author 
 * @created 2017-08-14 15:41:38
 * @lastModified 
 * @history
 *
 */
@Repository("workticketRepairDao")
public class WorkticketRepairDaoImpl extends AncestorDao<WorkticketRepairEntity> implements WorkticketRepairDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.com.aptech.business.workticketRepair";
	}
}
