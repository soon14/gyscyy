package com.aptech.business.annualProductionCapacity.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.annualProductionCapacity.domain.AnnualProductionCapacityEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 年度生产量计划应用数据类
 *
 * @author 
 * @created 2018-09-17 18:30:24
 * @lastModified 
 * @history
 *
 */
@Repository("annualProductionCapacityDao")
public class AnnualProductionCapacityDaoImpl extends AncestorDao<AnnualProductionCapacityEntity> implements AnnualProductionCapacityDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.annualProductionCapacity";
	}

	@Override
	public void deleteByType(AnnualProductionCapacityEntity entity) {
		this.updateEntity("deleteALLByType", entity);
		
	}
}
