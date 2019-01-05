package com.aptech.business.cargo.stockStatictisPrint.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.cargo.stockStatictisPrint.domain.StockStatictisPrintEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 库存统计打印表应用数据类
 *
 * @author 
 * @created 2018-09-08 16:00:12
 * @lastModified 
 * @history
 *
 */
@Repository("stockStatictisPrintDao")
public class StockStatictisPrintDaoImpl extends AncestorDao<StockStatictisPrintEntity> implements StockStatictisPrintDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.stockStatictisPrint";
	}
}
