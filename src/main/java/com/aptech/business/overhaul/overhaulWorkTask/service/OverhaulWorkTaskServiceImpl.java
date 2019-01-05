package com.aptech.business.overhaul.overhaulWorkTask.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.overhaul.overhaulArrange.domain.OverhaulWorkTaskStatusEnum;
import com.aptech.business.overhaul.overhaulWorkTask.dao.OverhaulWorkTaskDao;
import com.aptech.business.overhaul.overhaulWorkTask.domain.OverhaulWorkTaskEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 检修工作任务应用管理服务实现类
 *
 * @author 
 * @created 2017-12-22 10:15:21
 * @lastModified 
 * @history
 *
 */
@Service("overhaulWorkTaskService")
@Transactional
public class OverhaulWorkTaskServiceImpl extends AbstractBaseEntityOperation<OverhaulWorkTaskEntity> implements OverhaulWorkTaskService {
	
	@Autowired
	private OverhaulWorkTaskDao overhaulWorkTaskDao;
	@Override
	public IBaseEntityOperation<OverhaulWorkTaskEntity> getDao() {
		return overhaulWorkTaskDao;
	}
	@Override
	public <T> ResultObj overhaulWorkTaskAdd(OverhaulWorkTaskEntity overhaulWorkTaskEntity) {
		ResultObj resultObj = new ResultObj();
		//用户信息
		SysUserEntity userEntity = RequestContext.get().getUser();
		overhaulWorkTaskEntity.setCreateDate(new Date());
		overhaulWorkTaskEntity.setCreateUserId(userEntity.getId());
		overhaulWorkTaskEntity.setStatus(OverhaulWorkTaskStatusEnum.NORMAL.getCode());
		overhaulWorkTaskDao.addEntity(overhaulWorkTaskEntity);
		Page<T> page = new Page<T>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("C_ID"));
		List<Condition> Conditions = new ArrayList<Condition>();
		Conditions.add(new Condition("E.C_ID",FieldTypeEnum.INT,MatchTypeEnum.NE,"-1"));
		List<OverhaulWorkTaskEntity> overhaulWorkTaskEntities =  (List<OverhaulWorkTaskEntity>) overhaulWorkTaskDao.findByCondition(Conditions, page);
		if(!overhaulWorkTaskEntities.isEmpty()){
			resultObj.setData(overhaulWorkTaskEntities.get(0));
		}
		return resultObj;
	}

	@Override
	public <T> ResultObj delete(HttpServletRequest request, Long id) {
		ResultObj resultObj = new ResultObj();
		OverhaulWorkTaskEntity overhaulWorkTaskEntity = overhaulWorkTaskDao.findById(id);
		if(overhaulWorkTaskEntity!=null){
//			overhaulWorkTaskEntity.setStatus(OverhaulWorkTaskStatusEnum.DELETE.getCode());
//			overhaulWorkTaskDao.updateEntity(overhaulWorkTaskEntity);
			overhaulWorkTaskDao.deleteEntity(id);
		}
		return resultObj;
	}

	@Override
	public <T> ResultObj addEquipInfo(HttpServletRequest request,Map<String, Object> params, Long overhaulRecordId) {
		ResultObj resultObj = new ResultObj();
		//登录人信息
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<Condition> conditions = new ArrayList<Condition>();
		//设备信息
		List<Map<String, Object>> baseParameters = (List<Map<String, Object>>)params.get("equipInfo");
		for(Map<String, Object> map:baseParameters){
			Long  equipid = Long.parseLong(map.get("equipid").toString());
			List<Condition> Conditions = new ArrayList<Condition>();
			Conditions.add(new Condition("O.C_EQUIP_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,equipid));
			Conditions.add(new Condition("O.C_OVERHAUL_RECORD_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,overhaulRecordId));
			List<OverhaulWorkTaskEntity> workTaskEntities =  overhaulWorkTaskDao.findByCondition(Conditions, null);
			//如果没有该设备
			if(workTaskEntities.isEmpty()){
				OverhaulWorkTaskEntity  workTaskEntity = new OverhaulWorkTaskEntity();
				workTaskEntity.setOverhaulRecordId(overhaulRecordId);
				workTaskEntity.setCreateDate(new Date());
				workTaskEntity.setCreateUserId(userEntity.getId());
				workTaskEntity.setEquipId(equipid);
				workTaskEntity.setEquipName(map.get("equipName").toString());
				workTaskEntity.setStatus(OverhaulWorkTaskStatusEnum.NORMAL.getCode());
				overhaulWorkTaskDao.addEntity(workTaskEntity);
			}
		}
		conditions.add(new Condition("O.C_ID",FieldTypeEnum.STRING,MatchTypeEnum.NE,overhaulRecordId));
		List<OverhaulWorkTaskEntity> workTaskEntities =  overhaulWorkTaskDao.findByCondition(conditions, null);
		if(workTaskEntities.isEmpty()){
			resultObj.setData(workTaskEntities);
		}
		return resultObj;
	}
	@Override
	public <T> ResultObj deleteUnCheckDate(HttpServletRequest request,Map<String, Object> params) {
		ResultObj resultObj = new ResultObj();
		List<Map<String, Object>> deleteArray = (List<Map<String, Object>>)params.get("deleteArray");
		int overhaulRecordId = (int)params.get("overhaulRecordId");
		for(Map<String, Object> map:deleteArray){
			List<Condition> conditions = new ArrayList<Condition>();
			int equipId = (int)((Map<String, Object>)map.get("data")).get("id");
			conditions.add(new Condition("O.C_EQUIP_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,equipId));
			conditions.add(new Condition("O.C_OVERHAUL_RECORD_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,overhaulRecordId));
			List<OverhaulWorkTaskEntity> list = overhaulWorkTaskDao.findByCondition(conditions, null);
			if(!list.isEmpty()){
				Long overhaulWorkTaskId = list.get(0).getId();
				overhaulWorkTaskDao.deleteEntity(overhaulWorkTaskId);
			}
		}
		return resultObj;
	}
}