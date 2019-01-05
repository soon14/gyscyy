package com.aptech.business.technical.eventAnalysis.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.technical.eventAnalysis.domain.EventAnalysisEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 事件分析应用数据类
 *
 * @author 
 * @created 2018-09-07 17:36:53
 * @lastModified 
 * @history
 *
 */
@Repository("eventAnalysisDao")
public class EventAnalysisDaoImpl extends AncestorDao<EventAnalysisEntity> implements EventAnalysisDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.eventAnalysis";
	}
}
