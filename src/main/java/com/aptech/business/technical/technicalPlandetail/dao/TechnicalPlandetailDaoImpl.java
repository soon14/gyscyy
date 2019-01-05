package com.aptech.business.technical.technicalPlandetail.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.technical.technicalPlandetail.domain.TechnicalPlandetailEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 技术监督详细计划应用数据类
 *
 * @author 
 * @created 2017-11-13 16:16:12
 * @lastModified 
 * @history
 *
 */
@Repository("technicalPlandetailDao")
public class TechnicalPlandetailDaoImpl extends AncestorDao<TechnicalPlandetailEntity> implements TechnicalPlandetailDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.technical.technicalPlandetail";
	}
}
