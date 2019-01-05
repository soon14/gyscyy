package com.aptech.business.ticketManage.workTicketEquip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




import com.aptech.business.ticketManage.workTicketEquip.dao.WorkTicketEquipDao;
import com.aptech.business.ticketManage.workTicketEquip.domain.WorkTicketEquipEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 两票设备关系表应用管理服务实现类
 *
 * @author 
 * @created 2018-06-20 14:24:31
 * @lastModified 
 * @history
 *
 */
@Service("workTicketEquipService")
@Transactional
public class WorkTicketEquipServiceImpl extends AbstractBaseEntityOperation<WorkTicketEquipEntity> implements WorkTicketEquipService {
	
	@Autowired
	private WorkTicketEquipDao workTicketEquipDao;
	
	@Override
	public IBaseEntityOperation<WorkTicketEquipEntity> getDao() {
		return workTicketEquipDao;
	}
}