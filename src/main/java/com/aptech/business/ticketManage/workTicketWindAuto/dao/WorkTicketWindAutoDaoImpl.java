package com.aptech.business.ticketManage.workTicketWindAuto.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.workTicketWindAuto.domain.WorkTicketWindAutoEntity;
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
@Repository("workTicketWindAutoDao")
public class WorkTicketWindAutoDaoImpl extends AncestorDao<WorkTicketWindAutoEntity> implements WorkTicketWindAutoDao{
	@Override
	public String getNameSpace() {
		return "com.aptech.business.workTicketWindAuto";
	}
	

}
