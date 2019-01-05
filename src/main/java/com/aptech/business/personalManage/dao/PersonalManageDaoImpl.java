package com.aptech.business.personalManage.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.personalManage.domain.PersonalManageEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 人员管理应用数据类
 *
 * @author 
 * @created 2017-10-19 17:34:22
 * @lastModified 
 * @history
 *
 */
@Repository("personalManageDao")
public class PersonalManageDaoImpl extends AncestorDao<PersonalManageEntity> implements PersonalManageDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.personalManage";
	}
}
