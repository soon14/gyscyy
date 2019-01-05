package com.aptech.business.run.protect.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.run.protect.domain.ProtectEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 保护投退应用数据类
 *
 * @author 
 * @created 2017-06-02 14:38:25
 * @lastModified 
 * @history
 *
 */
@Repository("protectDao")
public class ProtectDaoImpl extends AncestorDao<ProtectEntity> implements ProtectDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.protect";
	}
}
