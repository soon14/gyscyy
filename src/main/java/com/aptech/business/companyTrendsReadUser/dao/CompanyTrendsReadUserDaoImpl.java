package com.aptech.business.companyTrendsReadUser.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.companyTrendsReadUser.domain.CompanyTrendsReadUserEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 公司动态已读用户应用数据类
 *
 * @author 
 * @created 2018-07-27 11:24:27
 * @lastModified 
 * @history
 *
 */
@Repository("companyTrendsReadUserDao")
public class CompanyTrendsReadUserDaoImpl extends AncestorDao<CompanyTrendsReadUserEntity> implements CompanyTrendsReadUserDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.companyTrendsReadUser";
	}
}
