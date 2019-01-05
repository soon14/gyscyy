package com.aptech.business.cargo.inStock.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.cargo.inStock.domain.InstockEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 入库管理应用数据类
 *
 * @author 
 * @created 2017-07-19 11:32:25
 * @lastModified 
 * @history
 *
 */
@Repository("instockDao")
public class InstockDaoImpl extends AncestorDao<InstockEntity> implements InstockDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.cargo.inStock";
	}
}
