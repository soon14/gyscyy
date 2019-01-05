package com.aptech.business.ticketManage.workTicketWindFlow.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.workTicketWindFlow.domain.WorkTicketWindEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 风工机械工作票应用数据类
 *
 * @author 
 * @created 2017-06-05 17:12:46
 * @lastModified 
 * @history
 *
 */
@Repository("workTicketWindDao")
public class WorkTicketWindDaoImpl extends AncestorDao<WorkTicketWindEntity> implements WorkTicketWindDao{

	@Override
	public String getNameSpace() {
		return "com.aptech.business.workTicketWind";
	}
	

}
