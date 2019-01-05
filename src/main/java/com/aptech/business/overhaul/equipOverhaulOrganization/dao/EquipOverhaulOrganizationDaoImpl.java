package com.aptech.business.overhaul.equipOverhaulOrganization.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.overhaul.equipOverhaulOrganization.domain.EquipOverhaulOrganizationEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 设备检修纪录组织机构应用数据类
 *
 * @author 
 * @created 2018-08-21 11:18:12
 * @lastModified 
 * @history
 *
 */
@Repository("equipOverhaulOrganizationDao")
public class EquipOverhaulOrganizationDaoImpl extends AncestorDao<EquipOverhaulOrganizationEntity> implements EquipOverhaulOrganizationDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.equipOverhaulOrganization";
	}
}
