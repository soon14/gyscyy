package com.aptech.business.equip.equipAbnormalEquipRel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.equip.equipAbnormalEquipRel.dao.EquipAbnormalEquipRelDao;
import com.aptech.business.equip.equipAbnormalEquipRel.domain.EquipAbnormalEquipRelEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 设备异动设备关联表应用管理服务实现类
 *
 * @author 
 * @created 2018-09-10 17:38:22
 * @lastModified 
 * @history
 *
 */
@Service("equipAbnormalEquipRelService")
@Transactional
public class EquipAbnormalEquipRelServiceImpl extends AbstractBaseEntityOperation<EquipAbnormalEquipRelEntity> implements EquipAbnormalEquipRelService {
	
	@Autowired
	private EquipAbnormalEquipRelDao equipAbnormalEquipRelDao;
	
	@Override
	public IBaseEntityOperation<EquipAbnormalEquipRelEntity> getDao() {
		return equipAbnormalEquipRelDao;
	}
}