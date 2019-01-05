package com.aptech.business.mobile.userMobile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.mobile.userMobile.dao.UserMobileDao;
import com.aptech.business.mobile.userMobile.domain.UserMobileEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

@Service("userMobileService")
@Transactional
public class UserMobileServiceImpl  extends AbstractBaseEntityOperation<UserMobileEntity> implements  UserMobileService{
	@Autowired
	private UserMobileDao userMobileDao;
	@Override
	public IBaseEntityOperation<UserMobileEntity> getDao() {
		// TODO Auto-generated method stub
		return userMobileDao;
	}

}
