package com.aptech.business.ticketManage.workTicketEquip.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.workTicketEquip.domain.WorkTicketEquipEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 两票设备关系表应用数据类
 *
 * @author 
 * @created 2018-06-20 14:24:31
 * @lastModified 
 * @history
 *
 */
@Repository("workTicketEquipDao")
public class WorkTicketEquipDaoImpl extends AncestorDao<WorkTicketEquipEntity> implements WorkTicketEquipDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.workTicketEquip";
	}
}
