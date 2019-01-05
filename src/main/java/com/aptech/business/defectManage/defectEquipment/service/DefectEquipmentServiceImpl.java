package com.aptech.business.defectManage.defectEquipment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.defectManage.defectEquipment.dao.DefectEquipmentDao;
import com.aptech.business.defectManage.defectEquipment.domain.DefectEquipmentEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 缺陷设备关系表应用管理服务实现类
 *
 * @author 
 * @created 2018-06-15 15:44:31
 * @lastModified 
 * @history
 *
 */
@Service("defectEquipmentService")
@Transactional
public class DefectEquipmentServiceImpl extends AbstractBaseEntityOperation<DefectEquipmentEntity> implements DefectEquipmentService {
	
	@Autowired
	private DefectEquipmentDao defectEquipmentDao;
	
	@Override
	public IBaseEntityOperation<DefectEquipmentEntity> getDao() {
		return defectEquipmentDao;
	}
}