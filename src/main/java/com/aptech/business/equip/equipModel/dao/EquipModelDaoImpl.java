package com.aptech.business.equip.equipModel.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.equip.equipModel.domain.EquipModelEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 *  设备模版应用数据类
 *
 * @author 
 * @created 2017-06-12 14:04:41
 * @lastModified 
 * @history
 *
 */
@Repository("equipModelDao")
public class EquipModelDaoImpl extends AncestorDao<EquipModelEntity> implements EquipModelDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.equipModel";
	}
}
