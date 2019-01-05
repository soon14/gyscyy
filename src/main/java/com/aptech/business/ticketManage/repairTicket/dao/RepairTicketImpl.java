package com.aptech.business.ticketManage.repairTicket.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.repairTicket.domain.RepairTicketEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 工作票应用应用数据类
 *
 * @author 
 * @created 2017-06-29 18:50:39
 * @lastModified 
 * @history
 *
 */
@Repository("repairTickeDao")
public class RepairTicketImpl extends AncestorDao<RepairTicketEntity> implements RepairTicketDao{
	
	@Override
	public String getNameSpace() {
		return "com.aptech.business.repairTicket";
	}
}
