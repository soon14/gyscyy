package com.aptech.business.equip.equipAppraise.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.equip.equipAbnormalEquipRel.domain.EquipAbnormalEquipRelConstantEnum;
import com.aptech.business.equip.equipAbnormalEquipRel.domain.EquipAbnormalEquipRelEntity;
import com.aptech.business.equip.equipAbnormalEquipRel.service.EquipAbnormalEquipRelService;
import com.aptech.business.equip.equipAbnormalReport.domain.EquipAbnormalReportEntity;
import com.aptech.business.equip.equipAppraise.dao.EquipAppraiseDao;
import com.aptech.business.equip.equipAppraise.domain.EquipAppraiseConstantEnum;
import com.aptech.business.equip.equipAppraise.domain.EquipAppraiseEntity;
import com.aptech.business.equip.equipTree.service.EquipTreeService;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 设备评价应用管理服务实现类
 *
 * @author 
 * @created 2017-09-18 16:41:54
 * @lastModified 
 * @history
 *
 */
@Service("equipAppraiseService")
@Transactional
public class EquipAppraiseServiceImpl extends AbstractBaseEntityOperation<EquipAppraiseEntity> implements EquipAppraiseService {
	
	@Autowired
	private EquipAppraiseDao equipAppraiseDao;
	@Autowired
	private EquipTreeService equipTreeService;
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private EquipAbnormalEquipRelService equipAbnormalEquipRelService;
	@Override
	public IBaseEntityOperation<EquipAppraiseEntity> getDao() {
		return equipAppraiseDao;
	}
	@Override
	  public void addEntity(EquipAppraiseEntity t) {
		equipAppraiseDao.addEntity(t);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPAPPRAISE.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
	  }
	@Override
	public void updateEntity(EquipAppraiseEntity t) {
		getDao().updateEntity(t);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPAPPRAISE.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
	}
	@Override
	public void deleteEntity(Serializable id) {
		getDao().deleteEntity(id);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPAPPRAISE.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
	@Override
	public void bulkDelete(Serializable[] ids) {
		getDao().bulkDelete(ids);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPAPPRAISE.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
	@Override
	public ResultObj addEquipAppraise(EquipAppraiseEntity equipAppraiseEntity,
			HttpServletRequest request) {
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<EquipAbnormalEquipRelEntity> list = new ArrayList<EquipAbnormalEquipRelEntity>();
		if(equipAppraiseEntity.getEquipName()!=null && equipAppraiseEntity.getEquipCode()!=null){
			String tempCode = equipAppraiseEntity.getEquipCode();
			String tempName = equipAppraiseEntity.getEquipName();
			String[] arrayCode = null;
			String[] arrayName = null;
			if(tempCode.contains(",")){
				arrayCode = tempCode.split(",");
			}
			if(tempName.contains(",")){
				arrayName = tempName.split(",");
			}
			if(arrayCode!=null && arrayName!=null){
				for(int i = 0;i<arrayName.length;i++){
					EquipAbnormalEquipRelEntity entity = new EquipAbnormalEquipRelEntity();
					entity.setEquipCode(arrayCode[i]);
					entity.setEquipName(arrayName[i]);
					list.add(entity);
				}
			}
		}
		equipAppraiseEntity.setStatus(EquipAppraiseConstantEnum.NORMAL.getId());
		equipAppraiseDao.addEntity(equipAppraiseEntity);
		Long equipAppraiseId = equipAppraiseEntity.getId();
		for(EquipAbnormalEquipRelEntity appraiseEntity:list){
			EquipAbnormalEquipRelEntity abnormalEquipRelEntity = new EquipAbnormalEquipRelEntity();
			abnormalEquipRelEntity.setEquipCode(appraiseEntity.getEquipCode());
			abnormalEquipRelEntity.setEquipName(appraiseEntity.getEquipName());
			abnormalEquipRelEntity.setCreateUserId(userEntity.getId().toString());
			abnormalEquipRelEntity.setCreateDate(new Date());
			abnormalEquipRelEntity.setEquipAppraiseId(equipAppraiseId);
			abnormalEquipRelEntity.setStatus(EquipAbnormalEquipRelConstantEnum.NORMAL.getId());
			equipAbnormalEquipRelService.addEntity(abnormalEquipRelEntity);
		}
		return new ResultObj();
	}
	@Override
	public ResultObj updateEquipAppraise(
			EquipAppraiseEntity equipAppraiseEntity, HttpServletRequest request) {
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<EquipAbnormalReportEntity> list = new ArrayList<EquipAbnormalReportEntity>();
		if(equipAppraiseEntity.getEquipName()!=null && equipAppraiseEntity.getEquipCode()!=null){
			String tempCode = equipAppraiseEntity.getEquipCode();
			String tempName = equipAppraiseEntity.getEquipName();
			String[] arrayCode = null;
			String[] arrayName = null;
			if(tempCode.trim().contains(",")){
				arrayCode = tempCode.split(",");
			}
			if(tempName.trim().contains(",")){
				arrayName = tempName.split(",");
			}
			if(arrayCode!=null && arrayName!=null){
				for(int i = 0;i<arrayCode.length;i++){
					EquipAbnormalReportEntity entity = new EquipAbnormalReportEntity();
					entity.setEquipCode(arrayCode[i]);
					entity.setEquipName(arrayName[i]);
					list.add(entity);
				}
			}
		}
		equipAppraiseEntity.setStatus(EquipAppraiseConstantEnum.NORMAL.getId());
		//保存设备异动报告
		equipAppraiseDao.updateEntity(equipAppraiseEntity);
		//添加新的设备数据
		Long equipAppraiseId = equipAppraiseEntity.getId();
		//删除旧的设备数据
		equipAbnormalEquipRelService.deleteByCondition("delByEquipAppraiseId", equipAppraiseEntity.getId());
		for(EquipAbnormalReportEntity abnormalReportEntity:list){
			EquipAbnormalEquipRelEntity abnormalEquipRelEntity = new EquipAbnormalEquipRelEntity();
			abnormalEquipRelEntity.setEquipCode(abnormalReportEntity.getEquipCode());
			abnormalEquipRelEntity.setEquipName(abnormalReportEntity.getEquipName());
			abnormalEquipRelEntity.setCreateUserId(userEntity.getId().toString());
			abnormalEquipRelEntity.setCreateDate(new Date());
			abnormalEquipRelEntity.setUpdateUserId(userEntity.getId().toString());
			abnormalEquipRelEntity.setUpdateDate(new Date());
			abnormalEquipRelEntity.setStatus(EquipAbnormalEquipRelConstantEnum.NORMAL.getId());
			abnormalEquipRelEntity.setEquipAppraiseId(equipAppraiseId);
			equipAbnormalEquipRelService.addEntity(abnormalEquipRelEntity);
		}
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPAPPRAISE.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		return new ResultObj();
	}
	@Override
	public ResultObj delSelectInfo(List<Long> ids) {
		SysUserEntity userEntity = RequestContext.get().getUser();
		for (Long id : ids) {
			List<Condition> condition = new ArrayList<Condition>();
			condition.add(new Condition("C_EQUIP_APPRAISE_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,id));
			List<EquipAbnormalEquipRelEntity> list = equipAbnormalEquipRelService.findByCondition(condition, null);
			if(!list.isEmpty()){
				for(EquipAbnormalEquipRelEntity abnormalEquipRelEntity:list){
					abnormalEquipRelEntity.setStatus(EquipAppraiseConstantEnum.DELETE.getId());
					abnormalEquipRelEntity.setUpdateUserId(userEntity.getId().toString());
					abnormalEquipRelEntity.setUpdateDate(new Date());
					equipAbnormalEquipRelService.updateEntity(abnormalEquipRelEntity);
				}
			}
			EquipAppraiseEntity equipAppraiseEntity = equipAppraiseDao.findById(id);
			equipAppraiseEntity.setStatus(EquipAppraiseConstantEnum.DELETE.getId());
			equipAppraiseDao.updateEntity(equipAppraiseEntity);
		}
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPABNORMALREPORT.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		return new ResultObj();
	}
	
	@Override
	public ResultObj deleteOnlyOne(Long id) {
		SysUserEntity userEntity = RequestContext.get().getUser();
		List<Condition> condition = new ArrayList<Condition>();
		condition.add(new Condition("C_EQUIP_APPRAISE_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,id));
		List<EquipAbnormalEquipRelEntity> list = equipAbnormalEquipRelService.findByCondition(condition, null);
		if(!list.isEmpty()){
			for(EquipAbnormalEquipRelEntity equipAbnormalEquipRel:list){
				equipAbnormalEquipRel.setStatus(EquipAppraiseConstantEnum.DELETE.getId());
				equipAbnormalEquipRel.setUpdateUserId(userEntity.getId().toString());
				equipAbnormalEquipRel.setUpdateDate(new Date());
				equipAbnormalEquipRelService.updateEntity(equipAbnormalEquipRel);
			}
		}
		EquipAppraiseEntity equipAppraiseEntity = equipAppraiseDao.findById(id);
		equipAppraiseEntity.setStatus(EquipAppraiseConstantEnum.DELETE.getId());
		equipAppraiseDao.updateEntity(equipAppraiseEntity);
		return new ResultObj();
	}
}