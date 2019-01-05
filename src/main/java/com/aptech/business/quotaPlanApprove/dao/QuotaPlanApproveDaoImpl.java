package com.aptech.business.quotaPlanApprove.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.quotaPlanApprove.domain.QuotaPlanApproveEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 指标计划流程表应用数据类
 *
 * @author 
 * @created 2018-09-21 10:10:36
 * @lastModified 
 * @history
 *
 */
@Repository("quotaPlanApproveDao")
public class QuotaPlanApproveDaoImpl extends AncestorDao<QuotaPlanApproveEntity> implements QuotaPlanApproveDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.quotaPlanApprove";
	}
}
