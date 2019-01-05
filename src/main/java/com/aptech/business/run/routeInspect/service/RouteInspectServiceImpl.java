package com.aptech.business.run.routeInspect.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.run.routeInspect.dao.RouteInspectDao;
import com.aptech.business.run.routeInspect.domain.RouteInspectEntity;
import com.aptech.business.run.runRecord.domain.RunRecordEntity;
import com.aptech.business.run.safeMeeting.domain.SafeMeetingEntity;
import com.aptech.business.safeManage.safeReport.domain.SafeReportEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 巡检记录应用管理服务实现类
 *
 * @author 
 * @created 2018-03-13 17:34:53
 * @lastModified 
 * @history
 *
 */
@Service("routeInspectService")
@Transactional
public class RouteInspectServiceImpl extends AbstractBaseEntityOperation<RouteInspectEntity> implements RouteInspectService {
	
	@Autowired
	private RouteInspectDao routeInspectDao;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<RouteInspectEntity> getDao() {
		return routeInspectDao;
	}
	@Override
	public void addEntity(RouteInspectEntity t) {
		System.out.println(t.getRecordTime());
		super.addEntity(t);
	}

	public List<RouteInspectEntity> findByConditionDescId(List<Condition> conditions, Page<RouteInspectEntity> page) {
		return routeInspectDao.findByCondition(conditions, page);
	}
	
	@Override
	public ResultListObj searchData(HttpServletRequest request,
			Map<String, Object> params) {
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		ResultListObj resultObj = new ResultListObj();
//		Page<SafeMeetingEntity> page = PageUtil.getPage(params);
		Page<RouteInspectEntity> page = PageUtil.getPage(params);
		page.setOrders(OrmUtil.changeMapToOrders(params));
		routeInspectDao.setFlag(false);
//		List<SafeMeetingEntity> overhaulLogEntities =  (List<SafeMeetingEntity>) routeInspectDao.findByCondition(conditions, page);
		List<RouteInspectEntity> overhaulLogEntities =  (List<RouteInspectEntity>) routeInspectDao.findByCondition(conditions, page);
		routeInspectDao.setFlag(true);
		resultObj.setDraw((Integer)params.get("draw"));
		if (overhaulLogEntities != null) {
			resultObj.setData(overhaulLogEntities);
			resultObj.setRecordsTotal(page.getTotal());
		}
		return resultObj;
	}
	@Override
	public ResultObj addForWind(RouteInspectEntity routeInspectEntity,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		SysUserEntity userEntity = RequestContext.get().getUser();
		ResultObj resultObj = new ResultObj();
		routeInspectDao.setFlag(false);
		routeInspectDao.addEntity(routeInspectEntity);
		routeInspectDao.setFlag(true);
		resultObj.setData(routeInspectEntity);
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.ROUTEINSPECT.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		return resultObj;
	}
	@Override
	public void deleteEntityForWind(Long id) {
		// TODO Auto-generated method stub
		routeInspectDao.setFlag(false);
		routeInspectDao.deleteEntity(id);
		routeInspectDao.setFlag(true);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.ROUTEINSPECT.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
	@Override
	public List<RouteInspectEntity> findByConditionForWind(Long rlId) {
		// TODO Auto-generated method stub
		List<Condition> conditions=new ArrayList<Condition>();
        conditions.add(new Condition("a.C_RL_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,rlId));
        routeInspectDao.setFlag(false);
        List<RouteInspectEntity> dataList=routeInspectDao.findByCondition(conditions, null);
        routeInspectDao.setFlag(true);
		return dataList;
	}
	@Override
	public void updateEntityForWind(RouteInspectEntity routeInspectEntity) {
		// TODO Auto-generated method stub
		routeInspectDao.setFlag(false);
		routeInspectDao.updateEntity(routeInspectEntity);
		routeInspectDao.setFlag(true);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.ROUTEINSPECT.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
	}
	@Override
	public RouteInspectEntity findByIdForWind(Long id) {
		// TODO Auto-generated method stub
		routeInspectDao.setFlag(false);
		RouteInspectEntity routeInspectEntity = routeInspectDao.findById(id);
		routeInspectDao.setFlag(true);
		return routeInspectEntity;
	}
	@Override
	public ResultObj deleteForWind(Long id) {
		// TODO Auto-generated method stub
		routeInspectDao.setFlag(false);
		routeInspectDao.deleteEntity(id);
		routeInspectDao.setFlag(true);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.ROUTEINSPECT.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		return new ResultObj();
	}
}