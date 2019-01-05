package com.aptech.business.cargo.inStockDetail.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.cargo.inStockDetail.domain.InstockDetailEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 入库物资明细应用数据类
 *
 * @author 
 * @created 2017-07-24 16:20:23
 * @lastModified 
 * @history
 *
 */
@Repository("instockDetailDao")
public class InstockDetailDaoImpl extends AncestorDao<InstockDetailEntity> implements InstockDetailDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.cargo.inStockDetail";
	}
}
