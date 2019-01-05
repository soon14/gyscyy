package com.aptech.business.equip.equipAbnormalReport.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.equip.equipAbnormalReport.domain.EquipAbnormalReportEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 设备异动报告应用数据类
 *
 * @author 
 * @created 2018-09-14 13:48:29
 * @lastModified 
 * @history
 *
 */
@Repository("equipAbnormalReportDao")
public class EquipAbnormalReportDaoImpl extends AncestorDao<EquipAbnormalReportEntity> implements EquipAbnormalReportDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.equip.equipAbnormalReport";
	}
}
