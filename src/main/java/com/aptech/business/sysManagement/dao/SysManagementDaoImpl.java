package com.aptech.business.sysManagement.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.sysManagement.domain.SysManagementEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 制度管理应用数据类
 *
 * @author 
 * @created 2018-03-14 16:26:37
 * @lastModified 
 * @history
 *
 */
@Repository("sysManagementDao")
public class SysManagementDaoImpl extends AncestorDao<SysManagementEntity> implements SysManagementDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.sysManagement";
	}
}
