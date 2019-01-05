package com.aptech.business.ticketManage.workSafe.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.workSafe.domain.WorkSafeEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 安全措施应用数据类
 *
 * @author 
 * @created 2017-06-05 17:12:33
 * @lastModified 
 * @history
 *
 */
@Repository("workSafeDao")
public class WorkSafeDaoImpl extends AncestorDao<WorkSafeEntity> implements WorkSafeDao{
	
	@Override
	public String getNameSpace() {
		return "com.aptech.business.workSafe";
	}
}
