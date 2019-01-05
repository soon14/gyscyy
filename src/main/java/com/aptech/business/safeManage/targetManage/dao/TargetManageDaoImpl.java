package com.aptech.business.safeManage.targetManage.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.safeManage.targetManage.domain.TargetManageEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 目标管理应用数据类
 *
 * @author 
 * @created 2018-03-23 14:32:07
 * @lastModified 
 * @history
 *
 */
@Repository("targetManageDao")
public class TargetManageDaoImpl extends AncestorDao<TargetManageEntity> implements TargetManageDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.targetManage";
	}
}
