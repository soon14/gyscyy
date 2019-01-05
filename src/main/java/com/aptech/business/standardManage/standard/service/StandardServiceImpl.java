package com.aptech.business.standardManage.standard.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.defectManage.defect.exception.DefectException;
import com.aptech.business.defectManage.defect.exception.DefectExceptionType;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.standardManage.standard.dao.StandardDao;
import com.aptech.business.standardManage.standard.domain.StandardEntity;
import com.aptech.common.fourcode.service.FourCodeService;
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

/**
 * 
 * 标准管理应用管理服务实现类
 *
 * @author 
 * @created 2017-12-07 10:17:00
 * @lastModified 
 * @history
 *
 */
@Service("standardService")
@Transactional
public class StandardServiceImpl extends AbstractBaseEntityOperation<StandardEntity> implements StandardService {
	
	@Autowired
	private StandardDao standardDao;
	@Autowired
	private FourCodeService fourCodeService;
	
	@Autowired
	private OperateLogService operateLogService;
	
	@Override
	public IBaseEntityOperation<StandardEntity> getDao() {
		return standardDao;
	}
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if (page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("uploadTime"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		return findByCondition(conditions, page);
	}
	@Override
	public void addEntity(StandardEntity standardEntity){
		SysUserEntity userEntity= RequestContext.get().getUser();
		standardEntity.setUploadUser(userEntity.getId());
		standardEntity.setUploadTime(new Date());
		String edition=standardEntity.getEdition();
//		JSONArray jSONArray=JSONArray.fromObject(standardEntity.getFileId());
//		for (int i = 0; i < jSONArray.size(); i++) {
//			JSONArray array=new JSONArray();
//			JSONObject josnObject= (JSONObject) jSONArray.get(i);
//			array.add(jSONArray.get(i));
//			standardEntity.setName(String.valueOf(josnObject.get("name")));
//			standardEntity.setCode(IdUtil.creatUUID());
//			standardEntity.setFileId(array.toString());
//			standardEntity.setEdition(edition);
//			List<Condition> conditions=new ArrayList<Condition>();
//			conditions.add(new Condition("T.C_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ,standardEntity.getName()));
//			conditions.add(new Condition("T.C_EDITION", FieldTypeEnum.STRING, MatchTypeEnum.EQ,standardEntity.getEdition()));
//			conditions.add(new Condition("T.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,standardEntity.getType()));
//			List<StandardEntity>list=standardDao.findByCondition(conditions, null);
//			if (!list.isEmpty()) {
//				throw new DefectException(DefectExceptionType.EDITION_STATUS);
//			}
//			
//		}
		
	        	List<Condition> conditions=new ArrayList<Condition>();
//	    		conditions.add(new Condition("T.C_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ,fileMap.get("name")));
	    		conditions.add(new Condition("T.C_EDITION", FieldTypeEnum.STRING, MatchTypeEnum.EQ,standardEntity.getEdition()));
//	    		conditions.add(new Condition("T.C_TYPE = "+standardEntity.getType()+" OR T.C_EDITION = "+standardEntity.getEdition()));
	    		conditions.add(new Condition("T.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, standardEntity.getType()));
//	    		conditions.add(new Condition("OR T.C_EDITION = "+standardEntity.getEdition()));
	    		List<StandardEntity>list=standardDao.findByCondition(conditions, null);
	    		if (!list.isEmpty()) {
	    			throw new DefectException(DefectExceptionType.EDITION_STATUS);
	    		}
//	      standardEntity.setCode(IdUtil.creatUUID());
	    //标准管理编号开始
	        Map<String, Object> codeparams=new HashMap<String, Object> ();
			codeparams.put("TIME", new Date());
			String code=fourCodeService.getBusinessCode("标准编码", codeparams);
			standardEntity.setCode(code);
			//标准管理编号结束
		standardDao.addEntity(standardEntity);

		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.STANDARD.getName(), OperateUserEnum.ADD.getName(), "", OperateUserEnum.ADD.getId());
	}
	@Override
	public void updateEntity(StandardEntity standardEntity){
		StandardEntity entity = standardDao.findById(standardEntity.getId());
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("T.C_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ,standardEntity.getName()));
		conditions.add(new Condition("T.C_EDITION", FieldTypeEnum.STRING, MatchTypeEnum.EQ,standardEntity.getEdition()));
//		conditions.add(new Condition("T.C_TYPE = "+standardEntity.getType()+" OR T.C_EDITION = "+standardEntity.getEdition()));
		conditions.add(new Condition("T.C_TYPE",FieldTypeEnum.INT, MatchTypeEnum.EQ,standardEntity.getType()));
//		conditions.add(new Condition("T.C_EDITION", FieldTypeEnum.STRING, MatchTypeEnum.EQ,standardEntity.getEdition()));
		
		conditions.add(new Condition("T.C_TYPE = "+standardEntity.getType()+" OR T.C_EDITION = '"+standardEntity.getEdition() + "'"));
		conditions.add(new Condition("T.C_ID", FieldTypeEnum.STRING, MatchTypeEnum.NE,standardEntity.getId()));
		List<StandardEntity>list=standardDao.findByCondition(conditions, null);
		if (!list.isEmpty()) {
			throw new DefectException(DefectExceptionType.EDITION_STATUS);
		}
//		JSONArray jSONArray=JSONArray.fromObject(standardEntity.getFileId());
//		for (int i = 0; i < jSONArray.size(); i++) {
//			JSONArray array=new JSONArray();
//			JSONObject josnObject= (JSONObject) jSONArray.get(i);
//			array.add(jSONArray.get(i));
//			t.setName(String.valueOf(josnObject.get("name")));
//			t.setEdition(standardEntity.getEdition());
//			t.setRemark(standardEntity.getRemark());
//			t.setFileId(array.toString());
//		}
		entity.setName(standardEntity.getName());
		entity.setEdition(standardEntity.getEdition());
		entity.setRemark(standardEntity.getRemark());
		entity.setFileId(standardEntity.getFileId());
		entity.setUploadTime(new Date());
		entity.setUnitId(standardEntity.getUnitId());
		entity.setUnitName(standardEntity.getUnitName());
		
		standardDao.updateEntity(entity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.STANDARD.getName(), OperateUserEnum.EDIT.getName(), "", OperateUserEnum.EDIT.getId());
	}
}