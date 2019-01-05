package com.aptech.business.ticketManage.workTicketLine.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.workTicketLine.domain.WorkTicketLineEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 工作票应用应用数据类
 *
 * @author 
 * @created 2017-10-11 11:50:39
 * @lastModified 
 * @history
 *
 */
@Repository("workTicketLineDao")
public class WorkTicketLineDaoImpl extends AncestorDao<WorkTicketLineEntity> implements WorkTicketLineDao{
	
	@Override
	public String getNameSpace() {
		return "com.aptech.business.workTicketLine";
	}
}
