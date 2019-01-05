package com.aptech.business.equip.equipAbnormal.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.equip.equipAbnormal.domain.EquipAbnormalEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 设备异动应用数据类
 *
 * @author 
 * @created 2017-06-22 14:52:35
 * @lastModified 
 * @history
 *
 */
@Repository("equipAbnormalDao")
public class EquipAbnormalDaoImpl extends AncestorDao<EquipAbnormalEntity> implements EquipAbnormalDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.equipAbnormal";
	}
}
