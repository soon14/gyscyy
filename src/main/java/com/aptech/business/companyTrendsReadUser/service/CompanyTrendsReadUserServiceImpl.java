package com.aptech.business.companyTrendsReadUser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.companyTrendsReadUser.dao.CompanyTrendsReadUserDao;
import com.aptech.business.companyTrendsReadUser.domain.CompanyTrendsReadUserEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 公司动态已读用户应用管理服务实现类
 *
 * @author 
 * @created 2018-07-27 11:24:27
 * @lastModified 
 * @history
 *
 */
@Service("companyTrendsReadUserService")
@Transactional
public class CompanyTrendsReadUserServiceImpl extends AbstractBaseEntityOperation<CompanyTrendsReadUserEntity> implements CompanyTrendsReadUserService {
	
	@Autowired
	private CompanyTrendsReadUserDao companyTrendsReadUserDao;
	
	@Override
	public IBaseEntityOperation<CompanyTrendsReadUserEntity> getDao() {
		return companyTrendsReadUserDao;
	}
}