package com.aptech.business.equip.equipAbnormalEquipRel.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.equip.equipAbnormalEquipRel.domain.EquipAbnormalEquipRelEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 设备异动设备关联表应用数据类
 *
 * @author 
 * @created 2018-09-10 17:38:22
 * @lastModified 
 * @history
 *
 */
@Repository("equipAbnormalEquipRelDao")
public class EquipAbnormalEquipRelDaoImpl extends AncestorDao<EquipAbnormalEquipRelEntity> implements EquipAbnormalEquipRelDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.equip.equipAbnormalEquipRel";
	}
}
