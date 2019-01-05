package com.aptech.business.train.trainStatistics.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.train.trainStatistics.domain.TrainStatisticsEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 培训统计应用数据类
 *
 * @author 
 * @created 2018-03-20 13:39:12
 * @lastModified 
 * @history
 *
 */
@Repository("trainStatisticsDao")
public class TrainStatisticsDaoImpl extends AncestorDao<TrainStatisticsEntity> implements TrainStatisticsDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.trainStatistics";
	}
}
