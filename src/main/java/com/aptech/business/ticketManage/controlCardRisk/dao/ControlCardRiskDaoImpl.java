package com.aptech.business.ticketManage.controlCardRisk.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.controlCardRisk.domain.ControlCardRiskEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 工作票控制卡风险与措施应用数据类
 *
 * @author 
 * @created 2017-06-05 17:12:23
 * @lastModified 
 * @history
 *
 */
@Repository("controlCardRiskDao")
public class ControlCardRiskDaoImpl extends AncestorDao<ControlCardRiskEntity> implements ControlCardRiskDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.controlCardRisk";
	}
}
