package com.aptech.business.ticketManage.controlCardRisk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.ticketManage.controlCardRisk.dao.ControlCardRiskDao;
import com.aptech.business.ticketManage.controlCardRisk.domain.ControlCardRiskEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 工作票控制卡风险与措施应用管理服务实现类
 *
 * @author 
 * @created 2017-06-05 17:12:23
 * @lastModified 
 * @history
 *
 */
@Service("controlCardRiskService")
@Transactional
public class ControlCardRiskServiceImpl extends AbstractBaseEntityOperation<ControlCardRiskEntity> implements ControlCardRiskService {
	
	@Autowired
	private ControlCardRiskDao controlCardRiskDao;
	
	@Override
	public IBaseEntityOperation<ControlCardRiskEntity> getDao() {
		return controlCardRiskDao;
	}
}