package com.aptech.business.safeCheckUnit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.safeCheckUnit.dao.SafeCheckUnitDao;
import com.aptech.business.safeCheckUnit.domain.SafeCheckUnitEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 安全检查组织机构表应用管理服务实现类
 *
 * @author 
 * @created 2018-09-03 10:56:03
 * @lastModified 
 * @history
 *
 */
@Service("safeCheckUnitService")
@Transactional
public class SafeCheckUnitServiceImpl extends AbstractBaseEntityOperation<SafeCheckUnitEntity> implements SafeCheckUnitService {
	
	@Autowired
	private SafeCheckUnitDao safeCheckUnitDao;
	
	@Override
	public IBaseEntityOperation<SafeCheckUnitEntity> getDao() {
		return safeCheckUnitDao;
	}
}