package com.aptech.business.equip.equipAppraise.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.equip.equipAppraise.domain.EquipAppraiseEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 设备评价应用数据类
 *
 * @author 
 * @created 2017-09-18 16:41:54
 * @lastModified 
 * @history
 *
 */
@Repository("equipAppraiseDao")
public class EquipAppraiseDaoImpl extends AncestorDao<EquipAppraiseEntity> implements EquipAppraiseDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.equipAppraise";
	}
}
