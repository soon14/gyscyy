package com.aptech.business.equip.equiplParameter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.equip.equiplParameter.dao.EquipParameterDao;
import com.aptech.business.equip.equiplParameter.domain.EquipParameterEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 设备参数应用管理服务实现类
 *
 * @author 
 * @created 2017-06-12 14:04:46
 * @lastModified 
 * @history
 *
 */
@Service("equipParameterService")
@Transactional
public class EquipParameterServiceImpl extends AbstractBaseEntityOperation<EquipParameterEntity> implements EquipParameterService {
	
	@Autowired
	private EquipParameterDao equipParameterDao;
	
	@Override
	public IBaseEntityOperation<EquipParameterEntity> getDao() {
		return equipParameterDao;
	}
}