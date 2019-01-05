package com.aptech.business.cargo.stockStatistics.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.cargo.stockStatistics.domain.StockStatisticsEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 运行检查应用数据类
 *
 * @author 
 * @created 2017-09-08 15:00:42
 * @lastModified 
 * @history
 *
 */
@Repository("stockStatisticsDao")
public class StockStatisticsDaoImpl extends AncestorDao<StockStatisticsEntity> implements StockStatisticsDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.stockStatistics";
	}
}
