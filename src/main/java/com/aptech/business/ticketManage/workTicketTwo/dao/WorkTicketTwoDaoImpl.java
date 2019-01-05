package com.aptech.business.ticketManage.workTicketTwo.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.workTicketTwo.domain.WorkTicketTwoEntity;
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
@Repository("workTicketTwoDao")
public class WorkTicketTwoDaoImpl extends AncestorDao<WorkTicketTwoEntity> implements WorkTicketTwoDao{
	
	@Override
	public String getNameSpace() {
		return "com.aptech.business.workTicketTwo";
	}
}
