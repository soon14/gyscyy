package com.aptech.business.train.trainStatistics.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.aptech.business.train.trainStatistics.domain.TrainStatisticsEntity;
import com.aptech.business.train.trainStatistics.domain.TrainStatisticsVO;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 培训统计应用管理服务接口
 *
 * @author 
 * @created 2018-03-20 13:39:12
 * @lastModified 
 * @history
 *
 */
public interface TrainStatisticsService  extends IBaseEntityOperation<TrainStatisticsEntity> {

	List<TrainStatisticsVO> findByCondition1(Map<String, Object> params) throws ParseException;
	
}