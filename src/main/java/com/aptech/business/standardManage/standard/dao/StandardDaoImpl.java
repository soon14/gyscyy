package com.aptech.business.standardManage.standard.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.standardManage.standard.domain.StandardEntity;

import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 标准管理应用数据类
 *
 * @author 
 * @created 2017-12-07 10:17:00
 * @lastModified 
 * @history
 *
 */
@Repository("standardDao")
public class StandardDaoImpl extends AncestorDao<StandardEntity> implements StandardDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.standard";
	}
}
