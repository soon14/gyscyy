package com.aptech.business.equip.equiplParameter.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.equip.equiplParameter.domain.EquipParameterEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 设备参数应用数据类
 *
 * @author 
 * @created 2017-06-12 14:04:46
 * @lastModified 
 * @history
 *
 */
@Repository("equipParameterDao")
public class EquipParameterDaoImpl extends AncestorDao<EquipParameterEntity> implements EquipParameterDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.equipParameter";
	}
}
