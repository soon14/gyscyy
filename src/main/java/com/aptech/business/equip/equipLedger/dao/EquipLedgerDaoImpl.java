package com.aptech.business.equip.equipLedger.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 设备管理应用数据类
 *
 * @author 
 * @created 2017-06-08 10:50:56
 * @lastModified 
 * @history
 *
 */
@Repository("equipLedgerDao")
public class EquipLedgerDaoImpl extends AncestorDao<EquipLedgerEntity> implements EquipLedgerDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.equipLedger";
	}
}
