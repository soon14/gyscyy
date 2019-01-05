package com.aptech.business.run.safeMeeting.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.overhaul.overhaulLog.domain.OverhaulLogEntity;
import com.aptech.business.run.runLog.dao.RunLogDao;
import com.aptech.business.run.runLog.domain.RunLogEntity;
import com.aptech.business.run.runLog.service.RunLogService;
import com.aptech.business.run.safeMeeting.dao.SafeMeetingDao;
import com.aptech.business.run.safeMeeting.domain.SafeMeetingEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.validate.ValidateUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 安全例会应用管理服务实现类
 *
 * @author 
 * @created 2017-06-07 16:00:28
 * @lastModified 
 * @history
 *
 */
@Service("safeMeetingService")
@Transactional
public class SafeMeetingServiceImpl extends AbstractBaseEntityOperation<SafeMeetingEntity> implements SafeMeetingService {
	
	@Autowired
	private SafeMeetingDao safeMeetingDao;
	@Autowired
	private RunLogDao runLogDao;
	@Autowired
	private RunLogService runLogService;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<SafeMeetingEntity> getDao() {
		return safeMeetingDao;
	}
	/**
	 * @Description:   自己写的修改方法
	 * @author         zhangzq 
	 * @Date           2017年6月20日 下午3:34:59 
	 * @throws         Exception
	 */
	@Override
	public ResultObj update(SafeMeetingEntity t, Long id,boolean flag) {
		SafeMeetingEntity  safeMeetingEntity = null;
		if(flag){
			safeMeetingDao.setFlag(true);
			safeMeetingEntity=this.findById(id);
			safeMeetingEntity.setWorkTime(t.getWorkTime());
			safeMeetingEntity.setfZR(t.getfZR());
			safeMeetingEntity.setCheckState(t.getCheckState());
			safeMeetingEntity.setMeetingContent(t.getMeetingContent());
			super.updateEntity(safeMeetingEntity);
		}else{
			safeMeetingEntity=this.findByIdForWind(id);
			safeMeetingEntity.setCheckState(t.getCheckState());
			safeMeetingEntity.setMeetingContent(t.getMeetingContent());
			safeMeetingEntity.setWorkTime(t.getWorkTime());
			safeMeetingEntity.setfZR(t.getfZR());
			safeMeetingDao.setFlag(false);
			super.updateEntity(safeMeetingEntity);
			
		}
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.WORK.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		return new ResultObj();
	}
	@Override
	public ResultListObj searchDate(HttpServletRequest request,
			Map<String, Object> params) {
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		ResultListObj resultObj = new ResultListObj();
		Page<SafeMeetingEntity> page = PageUtil.getPage(params);
		page.setOrders(OrmUtil.changeMapToOrders(params));
		safeMeetingDao.setFlag(true);
		List<SafeMeetingEntity> overhaulLogEntities =  (List<SafeMeetingEntity>) safeMeetingDao.findByCondition(conditions, page);
		resultObj.setDraw((Integer)params.get("draw"));
		if (overhaulLogEntities != null) {
			resultObj.setData(overhaulLogEntities);
			resultObj.setRecordsTotal(page.getTotal());
		}
		return resultObj;
	}
	
	@Override
	public ResultListObj searchDataForWind(HttpServletRequest request,
			Map<String, Object> params) {
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		ResultListObj resultObj = new ResultListObj();
		Page<SafeMeetingEntity> page = PageUtil.getPage(params);
		page.setOrders(OrmUtil.changeMapToOrders(params));
		safeMeetingDao.setFlag(false);
		List<SafeMeetingEntity> overhaulLogEntities =  (List<SafeMeetingEntity>) safeMeetingDao.findByCondition(conditions, page);
		safeMeetingDao.setFlag(true);
		resultObj.setDraw((Integer)params.get("draw"));
		if (overhaulLogEntities != null) {
			resultObj.setData(overhaulLogEntities);
			resultObj.setRecordsTotal(page.getTotal());
		}
		return resultObj;
	}
	
	@Override
	public ResultObj addForWind(SafeMeetingEntity meetingEntity,
			HttpServletRequest request) {
		ResultObj resultObj = new ResultObj();
		safeMeetingDao.setFlag(false);
		safeMeetingDao.addEntity(meetingEntity);
		safeMeetingDao.setFlag(true);
		resultObj.setData(meetingEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.WORK.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		return resultObj;
	}
	@Override
	public void deleteEntityForWind(Long id) {
		safeMeetingDao.setFlag(false);
		safeMeetingDao.deleteEntity(id);
		safeMeetingDao.setFlag(true);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.WORK.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
	@Override
	public List<SafeMeetingEntity> findByConditionForWind(Long rlId) {
		List<Condition> conditions=new ArrayList<Condition>();
        conditions.add(new Condition("a.C_RL_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,rlId));
        conditions.add(new Condition("a.C_MEETING_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"6"));
        safeMeetingDao.setFlag(false);
        List<SafeMeetingEntity> dataList=safeMeetingDao.findByCondition(conditions, null);
        safeMeetingDao.setFlag(true);
		return dataList;
	}
	@Override
	public ResultObj updateForWind(SafeMeetingEntity t, Long id,boolean flag) {
		safeMeetingDao.setFlag(false);
		SafeMeetingEntity  safeMeetingEntity=this.findById(id);
		if(flag){
			safeMeetingEntity.setWorkTime(t.getWorkTime());
			safeMeetingEntity.setfZR(t.getfZR());
		}
		safeMeetingEntity.setCheckState(t.getCheckState());
		safeMeetingEntity.setMeetingContent(t.getMeetingContent());
		safeMeetingDao.updateEntity(safeMeetingEntity);
		safeMeetingDao.setFlag(true);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.WORK.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		return new ResultObj();
	}
	@Override
	public ResultObj deleteForWind(Long id) {
		safeMeetingDao.setFlag(false);
		safeMeetingDao.deleteEntity(id);
		safeMeetingDao.setFlag(true);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.WORK.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		return new ResultObj();
	}
	@Override
	public SafeMeetingEntity findByIdForWind(Long id) {
		safeMeetingDao.setFlag(false);
		SafeMeetingEntity meetingEntity = safeMeetingDao.findById(id);
		safeMeetingDao.setFlag(true);
		return meetingEntity;
	}
	@Override
	public void addEntity(SafeMeetingEntity t){
		RunLogEntity runLogEntity = runLogService.findById(Long.valueOf(t.getRlId()));
		t.setReceptDate(runLogEntity.getDate());
		safeMeetingDao.addEntity(t);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.WORK.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());

	}
}