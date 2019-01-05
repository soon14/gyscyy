package com.aptech.business.safeManage.safeActivity.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.safeManage.safeActivity.domain.SafeActivityEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 安全活动应用数据类
 *
 * @author 
 * @created 2018-03-28 16:39:53
 * @lastModified 
 * @history
 *
 */
@Repository("safeActivityDao")
public class SafeActivityDaoImpl extends AncestorDao<SafeActivityEntity> implements SafeActivityDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.safeManage.safeActivity";
	}
}
