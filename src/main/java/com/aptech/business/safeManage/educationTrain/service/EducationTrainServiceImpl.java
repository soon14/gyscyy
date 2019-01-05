package com.aptech.business.safeManage.educationTrain.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.EducationTrainTypeEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.safeManage.educationTrain.dao.EducationTrainDao;
import com.aptech.business.safeManage.educationTrain.domain.EducationTrainEntity;
import com.aptech.business.safeManage.safeCheck.domain.SafeCheckEntity;
import com.aptech.business.safeManage.targetManage.domain.TargetManageEntity;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.IdUtil;

/**
 * 
 * 教育培训应用管理服务实现类
 *
 * @author 
 * @created 2018-03-31 12:43:38
 * @lastModified 
 * @history
 *
 */
@Service("educationTrainService")
@Transactional
public class EducationTrainServiceImpl extends AbstractBaseEntityOperation<EducationTrainEntity> implements EducationTrainService {
	
	@Autowired
	private EducationTrainDao educationTrainDao;
	@Autowired
	private SysUnitService sysUnitService;
	
	/**
	 * 操作日志
	 */
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<EducationTrainEntity> getDao() {
		return educationTrainDao;
	}
	
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if(page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("C_ID"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		List<O> resultList = super.findByCondition(conditions, page);
		return resultList;
	}
	
	@Override
	public void addEntity(EducationTrainEntity t) {
		t.setCreateDate(new Date());
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setCreateUserId(userEntity.getId().toString());
		SysUnitEntity sysUnitEntity = sysUnitService.findById(t.getUnitId());
		t.setUnitName(sysUnitEntity.getName());
//		JSONArray jSONArray=JSONArray.fromObject(t.getFileId());
//			
//			for (int i = 0; i < jSONArray.size(); i++) {
//				JSONArray array=new JSONArray();
//				JSONObject josnObject= (JSONObject) jSONArray.get(i);
//				array.add(jSONArray.get(i));
//				t.setFileName(String.valueOf(josnObject.get("name")));
////				t.setFileId(array.toString());
//			}
		educationTrainDao.addEntity(t);
		
		if(EducationTrainTypeEnum.TYPE1.getCode().equals(t.getType().toString())) {
			operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.EDUCATIONTRAINHEADQUARTERS.getName(), OperateUserEnum.ADD.getName(), "", OperateUserEnum.ADD.getId());
		} else if(EducationTrainTypeEnum.TYPE2.getCode().equals(t.getType().toString())) {
			operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.EDUCATIONTRAINRODUCTIONUNIT.getName(), OperateUserEnum.ADD.getName(), "", OperateUserEnum.ADD.getId());
			
		}
	}
	
	@Override
	public void updateEntity(EducationTrainEntity t) {
		SysUnitEntity sysUnitEntity = sysUnitService.findById(t.getUnitId());
		t.setUnitName(sysUnitEntity.getName());
		educationTrainDao.updateEntity(t);
		
//		JSONArray jSONArray=JSONArray.fromObject(t.getFileId());
//		for (int i = 0; i < jSONArray.size(); i++) {
//			JSONArray array=new JSONArray();
//			JSONObject josnObject= (JSONObject) jSONArray.get(i);
//			array.add(jSONArray.get(i));
//			t.setFileName(String.valueOf(josnObject.get("name")));
//			educationTrainDao.updateEntity(t);
//		}
	}
}