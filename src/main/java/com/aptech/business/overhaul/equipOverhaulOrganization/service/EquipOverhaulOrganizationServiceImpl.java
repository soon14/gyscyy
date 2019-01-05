package com.aptech.business.overhaul.equipOverhaulOrganization.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.overhaul.equipOverhaulOrganization.dao.EquipOverhaulOrganizationDao;
import com.aptech.business.overhaul.equipOverhaulOrganization.domain.EquipOverhaulOrganizationEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 设备检修纪录组织机构应用管理服务实现类
 *
 * @author 
 * @created 2018-08-21 11:18:12
 * @lastModified 
 * @history
 *
 */
@Service("equipOverhaulOrganizationService")
@Transactional
public class EquipOverhaulOrganizationServiceImpl extends AbstractBaseEntityOperation<EquipOverhaulOrganizationEntity> implements EquipOverhaulOrganizationService {
	
	@Autowired
	private EquipOverhaulOrganizationDao equipOverhaulOrganizationDao;
	@Autowired
	private OperateLogService operateLogService;
	
	@Override
	public IBaseEntityOperation<EquipOverhaulOrganizationEntity> getDao() {
		return equipOverhaulOrganizationDao;
	}

	

	@Override
	public void addEntity(EquipOverhaulOrganizationEntity overhaulOrganizationEntity) {
		//用户信息
		SysUserEntity userEntity = RequestContext.get().getUser();
		overhaulOrganizationEntity.setCreateDate(new Date());
		equipOverhaulOrganizationDao.addEntity(overhaulOrganizationEntity);
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPROVERHAULORGANIZATION.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
	}
	
	@Override
	public void updateEntity(EquipOverhaulOrganizationEntity overhaulOrganizationEntity) {
		//用户信息
		SysUserEntity userEntity = RequestContext.get().getUser();
		long id = overhaulOrganizationEntity.getId();
		EquipOverhaulOrganizationEntity entity = equipOverhaulOrganizationDao.findById(id);
		entity.setName(overhaulOrganizationEntity.getName());
		entity.setCode(overhaulOrganizationEntity.getCode());
		equipOverhaulOrganizationDao.updateEntity(entity);
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPROVERHAULORGANIZATION.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
	}



	
	
	
//	@Override
//	public ResultObj deleteOnlyOne(Long id) {
//		ResultObj resultObj = new ResultObj();
//		return resultObj;
//	}
	
	
}