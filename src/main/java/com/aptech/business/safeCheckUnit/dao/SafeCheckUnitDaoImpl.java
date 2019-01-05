package com.aptech.business.safeCheckUnit.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.safeCheckUnit.domain.SafeCheckUnitEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 安全检查组织机构表应用数据类
 *
 * @author 
 * @created 2018-09-03 10:56:03
 * @lastModified 
 * @history
 *
 */
@Repository("safeCheckUnitDao")
public class SafeCheckUnitDaoImpl extends AncestorDao<SafeCheckUnitEntity> implements SafeCheckUnitDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.safeCheckUnit";
	}
}
