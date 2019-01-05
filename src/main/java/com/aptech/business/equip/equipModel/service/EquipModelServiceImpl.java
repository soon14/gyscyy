package com.aptech.business.equip.equipModel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.equip.equipModel.dao.EquipModelDao;
import com.aptech.business.equip.equipModel.domain.EquipModelEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 *  设备模版应用管理服务实现类
 *
 * @author 
 * @created 2017-06-12 14:04:41
 * @lastModified 
 * @history
 *
 */
@Service("equipModelService")
@Transactional
public class EquipModelServiceImpl extends AbstractBaseEntityOperation<EquipModelEntity> implements EquipModelService {
	
	@Autowired
	private EquipModelDao equipModelDao;
	
	@Override
	public IBaseEntityOperation<EquipModelEntity> getDao() {
		return equipModelDao;
	}
}