package com.aptech.business.cargo.securityApparatus.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.cargo.securityApparatus.dao.SecurityApparatusDao;
import com.aptech.business.cargo.securityApparatus.domain.SecurityApparatusEntity;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 安全工器具管理应用管理服务实现类
 *
 * @author 
 * @created 2018-03-15 13:41:51
 * @lastModified 
 * @history
 *
 */
@Service("securityApparatusService")
@Transactional
public class SecurityApparatusServiceImpl extends AbstractBaseEntityOperation<SecurityApparatusEntity> implements SecurityApparatusService {
	
	@Autowired
	private SecurityApparatusDao securityApparatusDao;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<SecurityApparatusEntity> getDao() {
		return securityApparatusDao;
	}
	@Override
	public void deleteEntity(Serializable id) {
		getDao().deleteEntity(id);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SECURITYAPPARATUS.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}

	@Override
	public void bulkDelete(Serializable[] ids) {
		getDao().bulkDelete(ids);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SECURITYAPPARATUS.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
}