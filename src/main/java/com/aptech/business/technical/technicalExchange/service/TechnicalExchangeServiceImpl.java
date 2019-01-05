package com.aptech.business.technical.technicalExchange.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.EventNotificationStatusEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.technical.eventAnalysis.domain.EventAnalysisEntity;
import com.aptech.business.technical.technicalExchange.dao.TechnicalExchangeDao;
import com.aptech.business.technical.technicalExchange.domain.TechnicalExchangeEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 技术交流应用管理服务实现类
 *
 * @author 
 * @created 2018-09-17 10:37:44
 * @lastModified 
 * @history
 *
 */
@Service("technicalExchangeService")
@Transactional
public class TechnicalExchangeServiceImpl extends AbstractBaseEntityOperation<TechnicalExchangeEntity> implements TechnicalExchangeService {
	
	@Autowired
	private TechnicalExchangeDao technicalExchangeDao;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<TechnicalExchangeEntity> getDao() {
		return technicalExchangeDao;
	}
	@Override
	public void addEntity(TechnicalExchangeEntity t) {
		getDao().addEntity(t);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.TECHNICAL_EXCHANGE.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
	}
	@Override
	public void updateEntity(TechnicalExchangeEntity t) {
		getDao().updateEntity(t);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.TECHNICAL_EXCHANGE.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
	}

	@Override
	public void deleteEntity(Serializable id) {
		getDao().deleteEntity(id);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.TECHNICAL_EXCHANGE.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}

	@Override
	public void bulkDelete(Serializable[] ids) {
		getDao().bulkDelete(ids);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.TECHNICAL_EXCHANGE.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
}