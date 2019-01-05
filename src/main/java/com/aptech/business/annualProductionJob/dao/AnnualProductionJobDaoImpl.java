package com.aptech.business.annualProductionJob.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.annualProductionJob.domain.AnnualProductionJobEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 年度生产工作计划应用数据类
 *
 * @author 
 * @created 2018-09-20 13:26:33
 * @lastModified 
 * @history
 *
 */
@Repository("annualProductionJobDao")
public class AnnualProductionJobDaoImpl extends AncestorDao<AnnualProductionJobEntity> implements AnnualProductionJobDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.annualProductionJob";
	}
}
