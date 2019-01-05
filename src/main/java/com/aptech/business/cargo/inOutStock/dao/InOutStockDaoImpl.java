package com.aptech.business.cargo.inOutStock.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.cargo.inOutStock.domain.InOutStockEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 出入库明细应用数据类
 *
 * @author 
 * @created 2017-07-15 16:14:14
 * @lastModified 
 * @history
 *
 */
@Repository("inOutStockDao")
public class InOutStockDaoImpl extends AncestorDao<InOutStockEntity> implements InOutStockDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.cargo.inOutStock";
	}
}
