package com.aptech.business.mobile.userMobile.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.mobile.userMobile.domain.UserMobileEntity;
import com.aptech.framework.orm.AncestorDao;
@Repository("userMobileDao")
public class UserMobileDaoImpl  extends  AncestorDao<UserMobileEntity> implements UserMobileDao{

	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.mobile.userMobile";
	}


}
