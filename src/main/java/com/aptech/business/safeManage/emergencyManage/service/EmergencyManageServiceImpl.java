package com.aptech.business.safeManage.emergencyManage.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.safeManage.emergencyManage.dao.EmergencyManageDao;
import com.aptech.business.safeManage.emergencyManage.domain.EmergencyManageEntity;
import com.aptech.business.safeManage.safeReport.domain.SafeReportEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 应急管理应用管理服务实现类
 *
 * @author 
 * @created 2018-03-28 16:27:06
 * @lastModified 
 * @history
 *
 */
@Service("emergencyManageService")
@Transactional
public class EmergencyManageServiceImpl extends AbstractBaseEntityOperation<EmergencyManageEntity> implements EmergencyManageService {
	
	@Autowired
	private EmergencyManageDao emergencyManageDao;
	
	/**
	 * 操作日志
	 */
	@Autowired
	private OperateLogService operateLogService;
	
	@Override
	public IBaseEntityOperation<EmergencyManageEntity> getDao() {
		return emergencyManageDao;
	}
	
	
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if (page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("id"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		return findByCondition(conditions, page);
	}
	@Override
	  public void addEntity(EmergencyManageEntity t) {
	    JSONArray jSONArray=JSONArray.fromObject(t.getFileId());
	    for (int i = 0; i < jSONArray.size(); i++) {
	      JSONArray array=new JSONArray();
	      JSONObject josnObject= (JSONObject) jSONArray.get(i);
	      array.add(jSONArray.get(i));
	      t.setInformation(String.valueOf(josnObject.get("name")));
	      t.setFileId(array.toString());
	      emergencyManageDao.addEntity(t);
	    }
	    SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.EMERGENCIESMANAGE.getName(), OperateUserEnum.ADD.getName(), "", OperateUserEnum.ADD.getId());
	  }
	@Override
	public ResultObj update(EmergencyManageEntity t) {
		// TODO Auto-generated method stub
		EmergencyManageEntity emergencyManageEntity = this.findById(t.getId());
		emergencyManageEntity.setName(t.getName());
		emergencyManageEntity.setYearNum(t.getYearNum());
		emergencyManageEntity.setTime(t.getTime());
		emergencyManageEntity.setType(t.getType());
		emergencyManageEntity.setUnitId(t.getUnitId());
		emergencyManageEntity.setUserId(t.getUserId());
		JSONArray jSONArray=JSONArray.fromObject(t.getFileId());
		for (int i = 0; i < jSONArray.size(); i++) {
			JSONArray array=new JSONArray();
			JSONObject josnObject= (JSONObject) jSONArray.get(i);
			array.add(jSONArray.get(i));
			emergencyManageEntity.setInformation(String.valueOf(josnObject.get("name")));
			emergencyManageEntity.setFileId(array.toString());
			
		}
		emergencyManageDao.updateEntity(emergencyManageEntity);
		ResultObj resultObj = new ResultObj();
		resultObj.setData(emergencyManageEntity);
 		return resultObj;
	}
}