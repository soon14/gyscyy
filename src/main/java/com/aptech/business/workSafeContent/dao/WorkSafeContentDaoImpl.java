package com.aptech.business.workSafeContent.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.workSafeContent.domain.WorkSafeContentEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 安全措施应用数据类
 *
 * @author 
 * @created 2018-11-16 13:17:33
 * @lastModified 
 * @history
 *
 */
@Repository("workSafeContentDao")
public class WorkSafeContentDaoImpl extends AncestorDao<WorkSafeContentEntity> implements WorkSafeContentDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.workSafeContent";
	}
}
