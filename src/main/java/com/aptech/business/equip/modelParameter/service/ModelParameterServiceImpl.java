package com.aptech.business.equip.modelParameter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.equip.modelParameter.dao.ModelParameterDao;
import com.aptech.business.equip.modelParameter.domain.ModelParameterEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 模版参数应用管理服务实现类
 *
 * @author 
 * @created 2017-06-20 15:59:01
 * @lastModified 
 * @history
 *
 */
@Service("modelParameterService")
@Transactional
public class ModelParameterServiceImpl extends AbstractBaseEntityOperation<ModelParameterEntity> implements ModelParameterService {
	
	@Autowired
	private ModelParameterDao modelParameterDao;
	
	@Override
	public IBaseEntityOperation<ModelParameterEntity> getDao() {
		return modelParameterDao;
	}
}