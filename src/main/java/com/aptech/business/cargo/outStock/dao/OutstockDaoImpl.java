package com.aptech.business.cargo.outStock.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.cargo.outStock.domain.OutstockEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 出库管理应用数据类
 *
 * @author 
 * @created 2017-07-21 09:26:05
 * @lastModified 
 * @history
 *
 */
@Repository("outstockDao")
public class OutstockDaoImpl extends AncestorDao<OutstockEntity> implements OutstockDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.cargo.outStock";
	}
}
