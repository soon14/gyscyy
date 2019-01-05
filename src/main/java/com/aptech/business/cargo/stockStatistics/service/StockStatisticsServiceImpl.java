package com.aptech.business.cargo.stockStatistics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.cargo.stockStatistics.dao.StockStatisticsDao;
import com.aptech.business.cargo.stockStatistics.domain.StockStatisticsEntity;
import com.aptech.business.run.runLog.dao.RunLogDao;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 运行检查应用管理服务实现类
 *
 * @author 
 * @param <O>
 * @created 2017-09-08 15:00:42
 * @lastModified 
 * @history
 *
 */
@Service("stockStatisticsService")
@Transactional
public class StockStatisticsServiceImpl extends AbstractBaseEntityOperation<StockStatisticsEntity> implements StockStatisticsService {
	
	@Autowired
	private StockStatisticsDao stockStatisticsDao;
	@Autowired
	private RunLogDao runLogDao;
	
	@Override
	public IBaseEntityOperation<StockStatisticsEntity> getDao() {
		return stockStatisticsDao;
	}
	
	public int merge = 0;
}