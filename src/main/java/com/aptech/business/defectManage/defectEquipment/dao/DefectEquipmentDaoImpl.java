package com.aptech.business.defectManage.defectEquipment.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.defectManage.defectEquipment.domain.DefectEquipmentEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 缺陷设备关系表应用数据类
 *
 * @author 
 * @created 2018-06-15 15:44:31
 * @lastModified 
 * @history
 *
 */
@Repository("defectEquipmentDao")
public class DefectEquipmentDaoImpl extends AncestorDao<DefectEquipmentEntity> implements DefectEquipmentDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.defectEquipment";
	}
}
