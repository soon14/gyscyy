package com.aptech.business.companyTrends.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.companyTrends.domain.CompanyTrendsEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 公司动态应用数据类
 *
 * @author 
 * @created 2018-04-03 11:20:52
 * @lastModified 
 * @history
 *
 */
@Repository("companyTrendsDao")
public class CompanyTrendsDaoImpl extends AncestorDao<CompanyTrendsEntity> implements CompanyTrendsDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.OAManagement.companyTrends";
	}
}
