package com.aptech.business.safeManage.emergencyManage.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.safeManage.emergencyManage.domain.EmergencyManageEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 应急管理应用数据类
 *
 * @author 
 * @created 2018-03-28 16:27:06
 * @lastModified 
 * @history
 *
 */
@Repository("emergencyManageDao")
public class EmergencyManageDaoImpl extends AncestorDao<EmergencyManageEntity> implements EmergencyManageDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.emergencyManage";
	}
}
