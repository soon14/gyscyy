package com.aptech.business.overhaul.equipOverhaulOrganization.service;

import com.aptech.business.overhaul.equipOverhaulOrganization.domain.EquipOverhaulOrganizationEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 设备检修纪录组织机构应用管理服务接口
 *
 * @author 
 * @created 2018-08-21 11:18:12
 * @lastModified 
 * @history
 *
 */
public interface EquipOverhaulOrganizationService  extends IBaseEntityOperation<EquipOverhaulOrganizationEntity> {

	void addEntity(EquipOverhaulOrganizationEntity overhaulLogEntity);
	
	void updateEntity(EquipOverhaulOrganizationEntity overhaulOrganizationEntity);
}