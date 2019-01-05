package com.aptech.business.overhaul.overhaulSpareconsume.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.overhaul.overhaulArrange.domain.OverhaulWorkTaskStatusEnum;
import com.aptech.business.overhaul.overhaulSpareconsume.dao.OverhaulSpareconsumeDao;
import com.aptech.business.overhaul.overhaulSpareconsume.domain.OverhaulSpareconsumeEntity;
import com.aptech.business.overhaul.overhaulWorkTask.domain.OverhaulWorkTaskEntity;
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
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 备件消耗应用管理服务实现类
 *
 * @author 
 * @created 2018-03-15 10:23:12
 * @lastModified 
 * @history
 *
 */
@Service("overhaulSpareconsumeService")
@Transactional
public class OverhaulSpareconsumeServiceImpl extends AbstractBaseEntityOperation<OverhaulSpareconsumeEntity> implements OverhaulSpareconsumeService {
	
	@Autowired
	private OverhaulSpareconsumeDao overhaulSpareconsumeDao;
	
	@Override
	public IBaseEntityOperation<OverhaulSpareconsumeEntity> getDao() {
		return overhaulSpareconsumeDao;
	}

	@Override
	public <T> ResultObj addEquipInfo(HttpServletRequest request,Map<String, Object> params, Long overhaulRecordId) {
		ResultObj resultObj = new ResultObj();
		//登录人信息
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<Condition> conditions = new ArrayList<Condition>();
		//物资信息
		List<Map<String, Object>> baseParameters = (List<Map<String, Object>>)params.get("materialInfo");
		for(Map<String, Object> map:baseParameters){
			String  code = map.get("materialCode").toString();
			List<Condition> Conditions = new ArrayList<Condition>();
			Conditions.add(new Condition("O.C_CODE",FieldTypeEnum.STRING,MatchTypeEnum.EQ,code));
			Conditions.add(new Condition("O.C_OVERHAUL_RECORD_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,overhaulRecordId));
			List<OverhaulSpareconsumeEntity> list =  overhaulSpareconsumeDao.findByCondition(Conditions, null);
			//如果没有该设备
			if(list.isEmpty()){
				OverhaulSpareconsumeEntity  overhaulSpareconsumeEntity = new OverhaulSpareconsumeEntity();
				overhaulSpareconsumeEntity.setOverhaulRecordId(overhaulRecordId);
				overhaulSpareconsumeEntity.setCreateDate(new Date());
				overhaulSpareconsumeEntity.setCreateUserId(userEntity.getId());
				overhaulSpareconsumeEntity.setCode(map.get("materialCode").toString());
				overhaulSpareconsumeEntity.setName(map.get("materialName").toString());
				overhaulSpareconsumeEntity.setManufacturer(map.get("manufacturerName").toString());
				overhaulSpareconsumeEntity.setModel(map.get("model").toString());
//				workTaskEntity.setNumber(map.get("number").toString());
				overhaulSpareconsumeEntity.setUnit(map.get("unitName").toString());
				overhaulSpareconsumeEntity.setStatus(OverhaulWorkTaskStatusEnum.NORMAL.getCode());
				overhaulSpareconsumeDao.addEntity(overhaulSpareconsumeEntity);
			}
		}
		conditions.add(new Condition("O.C_ID",FieldTypeEnum.STRING,MatchTypeEnum.NE,overhaulRecordId));
		List<OverhaulSpareconsumeEntity> workTaskEntities =  overhaulSpareconsumeDao.findByCondition(conditions, null);
		if(workTaskEntities.isEmpty()){
			resultObj.setData(workTaskEntities);
		}
		return resultObj;
	}

	//删除
	@Override
	public <T> ResultObj delete(HttpServletRequest request, Long id) {
		ResultObj resultObj = new ResultObj();
		OverhaulSpareconsumeEntity overhaulWorkTaskEntity = overhaulSpareconsumeDao.findById(id);
		if(overhaulWorkTaskEntity!=null){

			overhaulSpareconsumeDao.deleteEntity(id);
		}
		return resultObj;
	}
	
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if(page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		page.setOrders(OrmUtil.changeMapToOrders(params));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("O.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		List<O> resultList = super.findByCondition(conditions, page);
		return resultList;
	}

	@Override
	public void update(OverhaulSpareconsumeEntity overhaulSpareconsumeEntity) {
		super.updateEntity(overhaulSpareconsumeEntity);
		
	}

}