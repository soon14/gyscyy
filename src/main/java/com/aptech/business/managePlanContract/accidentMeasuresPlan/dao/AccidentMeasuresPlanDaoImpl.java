package com.aptech.business.managePlanContract.accidentMeasuresPlan.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.managePlanContract.accidentMeasuresPlan.domain.AccidentMeasuresPlanEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 年度反事故措施计划应用数据类
 *
 * @author 
 * @created 2018-04-16 15:12:09
 * @lastModified 
 * @history
 *
 */
@Repository("accidentMeasuresPlanDao")
public class AccidentMeasuresPlanDaoImpl extends AncestorDao<AccidentMeasuresPlanEntity> implements AccidentMeasuresPlanDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.accidentMeasuresPlan";
	}
}
