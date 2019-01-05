package com.aptech.business.cargo.stock.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.cargo.stock.domain.StockEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 库存管理应用数据类
 *
 * @author 
 * @created 2017-07-17 16:40:59
 * @lastModified 
 * @history
 *
 */
@Repository("stockDao")
public class StockDaoImpl extends AncestorDao<StockEntity> implements StockDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.cargo.stock";
	}
}
