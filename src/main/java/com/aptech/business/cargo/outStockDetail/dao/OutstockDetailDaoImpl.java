package com.aptech.business.cargo.outStockDetail.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.cargo.outStockDetail.domain.OutstockDetailEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 出库物资明细应用数据类
 *
 * @author wangyue
 * @created 2017年7月25日 下午4:29:07 
 * @lastModified 
 * @history
 *
 */
@Repository("OutstockDetailDao")
public class OutstockDetailDaoImpl extends AncestorDao<OutstockDetailEntity> implements OutstockDetailDao{

	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.cargo.outStockDetail";
	}

}
