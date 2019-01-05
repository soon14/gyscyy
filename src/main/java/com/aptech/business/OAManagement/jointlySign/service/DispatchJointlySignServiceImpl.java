package com.aptech.business.OAManagement.jointlySign.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.OAManagement.jointlySign.dao.DispatchJointlySignDao;
import com.aptech.business.OAManagement.jointlySign.domain.DispatchJointlySignEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation; 

@Service("dispatchJointlySignService")
@Transactional
public class DispatchJointlySignServiceImpl extends AbstractBaseEntityOperation<DispatchJointlySignEntity> implements DispatchJointlySignService {

    @Autowired
    private DispatchJointlySignDao dispatchJointlySignDao;

   	@Override
	public IBaseEntityOperation<DispatchJointlySignEntity> getDao() {
		return dispatchJointlySignDao;
	}
}