package com.aptech.business.run.dispaCom.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.terracotta.DisconnectedClusterNode;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.dutyOrder.domain.DutyOrderEntity;
import com.aptech.business.dutyOrder.service.DutyOrderService;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.run.dispaCom.dao.DispaComDao;
import com.aptech.business.run.dispaCom.domain.DispaComEntity;
import com.aptech.business.run.runLog.domain.RunLogEntity;
import com.aptech.business.run.runLog.service.RunLogService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 调度命令应用管理服务实现类
 *
 * @author 
 * @created 2017-06-07 11:31:01
 * @lastModified 
 * @history
 *
 */
@Service("dispaComService")
@Transactional
public class DispaComServiceImpl extends AbstractBaseEntityOperation<DispaComEntity> implements DispaComService {
	
	@Autowired
	private DispaComDao dispaComDao;
	@Autowired
	private RunLogService runLogService;
	@Autowired
	private DutyOrderService dutyOrderService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<DispaComEntity> getDao() {
		return dispaComDao;
	}
	@Override
	public  List<DispaComEntity> searchDis(Map<String, Object> params) {
		Page<DispaComEntity> page = new Page<DispaComEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("id"));
		List<Condition> conditions = new ArrayList<Condition>();
		List<DispaComEntity> dispaComList = dispaComDao.findByCondition(conditions, page);
		for(DispaComEntity dispaComEntity : dispaComList){
			SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(dispaComEntity.getDutyChiefPerson()));
			if(sysUserEntity!=null){
				dispaComEntity.setDutyChiefPersonString(sysUserEntity.getName());
				dispaComDao.updateEntity(dispaComEntity);
			}
		}
		return dispaComList;
	}
    public List<DispaComEntity> finddispaComMapByCondition(String startTime,String endTime){
        List<Condition> conditions = new ArrayList<Condition>();
        conditions.add(new Condition("C_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, startTime));
        conditions.add(new Condition("C_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LT, endTime));
        Page<DispaComEntity> page = new Page<DispaComEntity>();
        page.setPageSize(Integer.MAX_VALUE);
        List<DispaComEntity> dispaComEntity = this.findByCondition("findByCondition",conditions, page);
        
        return dispaComEntity;
    }
    
	@Override
	public ResultObj add(DispaComEntity t) {
		DispaComEntity dispaComEntity = new DispaComEntity();
		dispaComEntity.setUnitId(t.getUnitId());
		dispaComEntity.setTime(t.getTime());
		dispaComEntity.setDispath(t.getDispath());
		dispaComEntity.setDispatchPerson(t.getDispatchPerson());
		dispaComEntity.setDutyChiefPerson(t.getDutyChiefPerson());
		dispaComEntity.setContactContent(t.getContactContent());
		dispaComEntity.setDutyChiefPersonString(t.getDutyChiefPerson());
		dispaComEntity.setCreateDate(new Date());
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		dispaComEntity.setCreateUserId(sysUserEntity.getId().intValue());
		dispaComEntity.setCreateUserName(sysUserEntity.getName());
		dispaComDao.addEntity(dispaComEntity);
		ResultObj resultObj = new ResultObj();
		resultObj.setData(dispaComEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.DISPACOM.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		return resultObj;
	}
	@Override
	public Object isAdd() {
		//当前登陆用户
		SysUserEntity userEntity = RequestContext.get().getUser();
		String useridString = userEntity.getId().toString();
		Page<RunLogEntity> page = new Page<RunLogEntity>();
		page.setPageSize(1);
		page.addOrder(Sort.desc("C_ID"));
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("a.C_ID", FieldTypeEnum.INT, MatchTypeEnum.NE, "-1"));
	    conditions.add(new Condition("a.C_UNIT", FieldTypeEnum.INT, MatchTypeEnum.EQ, userEntity.getUnitId()));
	    List<RunLogEntity> logEntities =  runLogService.findByCondition(conditions, page);
		//获取当前日志的班组
		String dutyid = logEntities.get(0).getDutyId().toString();
		//获取值次
	    DutyOrderEntity dutyOrderEntity =  dutyOrderService.findById(Long.parseLong(dutyid));
	    String teamMember = "";
	    String[] teamMemberArray = null;
	    //返回结果
	    boolean resultString = false;
	    if(dutyOrderEntity!=null){
	    	teamMember  = dutyOrderEntity.getTeamMember();
	    	if(!StringUtils.equals(teamMember, "")){
	    		teamMemberArray = teamMember.split(",");
	    	}
	    }
	    for(String teamMemberString:teamMemberArray){
	    	if(StringUtils.equals(teamMemberString, useridString)){
	    		resultString = true;
	    	}
	    }
	    Map<String, Boolean> map = new HashMap<String, Boolean>();
	    map.put("result", resultString);
		return JsonUtil.toJson(map);
	}
}