package com.aptech.business.safeManage.threeBusiness.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.safeManage.threeBusiness.domain.ThreeBusinessEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 三项业务应用数据类
 *
 * @author 
 * @created 2018-04-03 13:17:33
 * @lastModified 
 * @history
 *
 */
@Repository("threeBusinessDao")
public class ThreeBusinessDaoImpl extends AncestorDao<ThreeBusinessEntity> implements ThreeBusinessDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.threeBusiness";
	}
}
