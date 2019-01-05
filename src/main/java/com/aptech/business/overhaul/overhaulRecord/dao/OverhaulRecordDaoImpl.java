package com.aptech.business.overhaul.overhaulRecord.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.overhaul.overhaulRecord.domain.OverhaulRecordEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 设备检修记录应用数据类
 *
 * @author 
 * @created 2018-06-05 11:23:18
 * @lastModified 
 * @history
 *
 */
@Repository("overhaulRecordDao")
public class OverhaulRecordDaoImpl extends AncestorDao<OverhaulRecordEntity> implements OverhaulRecordDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.overhaulRecord";
	}
}
