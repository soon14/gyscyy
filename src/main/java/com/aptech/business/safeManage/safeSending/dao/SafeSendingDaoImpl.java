package com.aptech.business.safeManage.safeSending.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.safeManage.safeSending.domain.SafeSendingEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 安全发文应用数据类
 *
 * @author 
 * @created 2018-04-02 09:52:16
 * @lastModified 
 * @history
 *
 */
@Repository("safeSendingDao")
public class SafeSendingDaoImpl extends AncestorDao<SafeSendingEntity> implements SafeSendingDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.safeManage.safeSending";
	}
}
