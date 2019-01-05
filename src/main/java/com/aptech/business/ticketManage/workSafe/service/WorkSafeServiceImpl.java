package com.aptech.business.ticketManage.workSafe.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.ticketManage.workSafe.dao.WorkSafeDao;
import com.aptech.business.ticketManage.workSafe.domain.WorkSafeEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 安全措施应用管理服务实现类
 *
 * @author 
 * @created 2017-06-05 17:12:33
 * @lastModified 
 * @history
 *
 */
@Service("workSafeService")
@Transactional
public class WorkSafeServiceImpl extends AbstractBaseEntityOperation<WorkSafeEntity> implements WorkSafeService {
	
	@Autowired
	private WorkSafeDao workSafeDao;
	
	@Override
	public IBaseEntityOperation<WorkSafeEntity> getDao() {
		return workSafeDao;
	}
	
	/**
	 * 查询列表 zzq 20170606
	 */
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		page.setPageSize(Integer.MAX_VALUE);
   		page.setOrders(OrmUtil.changeMapToOrders(params));
   		page.addOrder(Sort.asc("C_ID"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		//conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		List<O> resultList = super.findByCondition(conditions, page);
		return resultList;
	}
	@Override
	public void addEntity(WorkSafeEntity t) {
		super.addEntity(t);
	}
	
	
	@Override
	public <T> ResultObj addSafeInfo(HttpServletRequest request,Map<String, Object> params, String uuid, Long type) {
		ResultObj resultObj = new ResultObj();
		//登录人信息
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<Condition> conditions = new ArrayList<Condition>();
		//设备信息
		List<Map<String, Object>> baseParameters = (List<Map<String, Object>>)params.get("safeInfo");
		for(Map<String, Object> map:baseParameters){
			Long  safeid = Long.parseLong(map.get("id").toString());
			List<Condition> Conditions = new ArrayList<Condition>();
//			Conditions.add(new Condition("C_SAFE_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,safeid));
//			Conditions.add(new Condition("C_SAFE_TYPE",FieldTypeEnum.LONG,MatchTypeEnum.EQ,type));
//			List<WorkSafeEntity> workSafeEntities =  workSafeDao.findByCondition(Conditions, null);
//			//如果没有该设备
//			if(workSafeEntities.isEmpty()){
				WorkSafeEntity  workSafeEntity = new WorkSafeEntity();
				workSafeEntity.setSafeId(safeid);
				workSafeEntity.setUuidCode(uuid);
				workSafeEntity.setCreateDate(new Date());
				workSafeEntity.setCreateUserId(userEntity.getId().toString());
				workSafeEntity.setSignerContent(map.get("content").toString());
				workSafeEntity.setSafeType(type);
				workSafeDao.addEntity(workSafeEntity);
//			}
		}
		conditions.add(new Condition("C_UUID_CODE",FieldTypeEnum.STRING,MatchTypeEnum.NE,uuid));
		conditions.add(new Condition("C_SAFE_TYPE",FieldTypeEnum.LONG,MatchTypeEnum.NE,type));
		List<WorkSafeEntity> workTaskEntities =  workSafeDao.findByCondition(conditions, null);
		if(workTaskEntities.isEmpty()){
			resultObj.setData(workTaskEntities);
		}
		return resultObj;
	}
	
	
	@Override
	public <T> ResultObj editSafeInfo(HttpServletRequest request,Map<String, Object> params, Long workid, Long type) {
		ResultObj resultObj = new ResultObj();
		//登录人信息
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<Condition> conditions = new ArrayList<Condition>();
		//设备信息
		List<Map<String, Object>> baseParameters = (List<Map<String, Object>>)params.get("safeInfo");
		for(Map<String, Object> map:baseParameters){
			Long  safeid = Long.parseLong(map.get("id").toString());
			List<Condition> Conditions = new ArrayList<Condition>();
			Conditions.add(new Condition("C_WORKTICKET_ID",FieldTypeEnum.STRING,MatchTypeEnum.EQ,workid));
			Conditions.add(new Condition("C_SAFE_TYPE",FieldTypeEnum.LONG,MatchTypeEnum.EQ,type));
			Conditions.add(new Condition("C_SAFE_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,safeid));
			List<WorkSafeEntity> workSafeEntities =  workSafeDao.findByCondition(Conditions, null);
			//如果没有该设备
			if(workSafeEntities.isEmpty()){
				WorkSafeEntity  workSafeEntity = new WorkSafeEntity();
				workSafeEntity.setWorkticketId(workid);
				workSafeEntity.setSafeId(safeid);
				workSafeEntity.setCreateDate(new Date());
				workSafeEntity.setCreateUserId(userEntity.getId().toString());
				workSafeEntity.setSignerContent(map.get("content").toString());
				workSafeEntity.setSafeType(type);
				super.addEntity(workSafeEntity);
			}
		}
		conditions.add(new Condition("C_WORKTICKET_ID",FieldTypeEnum.LONG,MatchTypeEnum.NE,workid));
		conditions.add(new Condition("C_SAFE_TYPE",FieldTypeEnum.LONG,MatchTypeEnum.NE,type));
		List<WorkSafeEntity> workTaskEntities =  workSafeDao.findByCondition(conditions, null);
		if(workTaskEntities.isEmpty()){
			resultObj.setData(workTaskEntities);
		}
		return resultObj;
	}
}