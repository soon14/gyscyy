package com.aptech.business.safeManage.safeCheck.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.safeManage.safeCheck.domain.SafeCheckEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 安全检查应用数据类
 *
 * @author 
 * @created 2018-03-28 10:22:17
 * @lastModified 
 * @history
 *
 */
@Repository("safeCheckDao")
public class SafeCheckDaoImpl extends AncestorDao<SafeCheckEntity> implements SafeCheckDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.safeCheck";
	}
}
