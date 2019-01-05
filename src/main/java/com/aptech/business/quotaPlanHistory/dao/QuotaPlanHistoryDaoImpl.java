package com.aptech.business.quotaPlanHistory.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.quotaPlanHistory.domain.QuotaPlanHistoryEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 指标类计划历史数据应用数据类
 *
 * @author 
 * @created 2018-09-19 18:20:50
 * @lastModified 
 * @history
 *
 */
@Repository("quotaPlanHistoryDao")
public class QuotaPlanHistoryDaoImpl extends AncestorDao<QuotaPlanHistoryEntity> implements QuotaPlanHistoryDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.quotaPlanHistory";
	}
}
