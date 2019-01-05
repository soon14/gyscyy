package com.aptech.business.overhaul.overhaulFile.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.overhaul.overhaulFile.dao.OverhaulFileDao;
import com.aptech.business.overhaul.overhaulFile.domain.OverhaulFileEntity;
import com.aptech.common.system.duties.domain.SysDutiesEntity;
import com.aptech.common.system.duties.service.SysDutiesService;
import com.aptech.common.system.dutiesdetail.domain.SysDutiesDetailEntity;
import com.aptech.common.system.dutiesdetail.service.SysDutiesDetailService;
import com.aptech.common.system.role.service.SysRoleService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.userUnit.domain.UserUnitRelEntity;
import com.aptech.common.system.userUnit.service.UserUnitRelService;
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
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 检修文件包应用管理服务实现类
 *
 * @author 
 * @created 2017-08-04 14:04:07
 * @lastModified 
 * @history
 *
 */
@Service("overhaulFileService")
@Transactional
public class OverhaulFileServiceImpl extends AbstractBaseEntityOperation<OverhaulFileEntity> implements OverhaulFileService {
	
	@Autowired
	private OverhaulFileDao overhaulFileDao;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysDutiesService sysDutiesService;
	@Autowired
	private SysDutiesDetailService sysDutiesDetailService;
	@Autowired
	private UserUnitRelService userUnitRelService;
	@Override
	public IBaseEntityOperation<OverhaulFileEntity> getDao() {
		return overhaulFileDao;
	}
	
	/**
	 * @Description: 查询
	 * @author 
	 * @Date 2017年7月31日 下午2:25
	 * @throws Exception
	 */
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if(page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		page.setOrders(OrmUtil.changeMapToOrders(params));
   		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		page.addOrder(Sort.desc("uploadDate"));
		SysUserEntity userEntity = RequestContext.get().getUser();
		
		List<Condition> conditioncode = new ArrayList<Condition>();
		conditioncode.add(new Condition("userId",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userEntity.getId()));
		List<UserUnitRelEntity> unitList = userUnitRelService.findByCondition(conditioncode, null);
		if(unitList!=null && unitList.size()>0){
			Long[] unitArray = new Long[unitList.size()];
			for(int i=0;i<unitList.size();i++){
				unitArray[i]=unitList.get(i).getUnitId();
			}
			conditions.add(new Condition("unitId", FieldTypeEnum.INT, MatchTypeEnum.IN, unitArray));
		}else{
			conditions.add(new Condition("unitId", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getUnitId()));
		}
		conditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));

		List<O> list = super.findByCondition(conditions, page);
		return list;
	}

	@Override
	public ResultObj add(OverhaulFileEntity t) {
		// TODO Auto-generated method stub
		String attchmentId = t.getAttchmentId();
		List<Map<String,Object>> list = JsonUtil.jsonToObj(attchmentId,List.class);

		SysUserEntity userEntity = RequestContext.get().getUser();
		
		for(Map<String,Object> map :list){
			OverhaulFileEntity entity = new OverhaulFileEntity();
			entity.setFileName((String)map.get("name"));
			entity.setAttchmentId((String)map.get("url"));
			entity.setStatus(String.valueOf(DataStatusEnum.NORMAL.ordinal()));
			entity.setUploadDate(new Date());
			entity.setUploadUserId(userEntity.getId().intValue());
			entity.setUploadUserName(userEntity.getName());
			entity.setUnitId(userEntity.getUnitId());
			this.addEntity(entity);
		}
		ResultObj resultObj = new ResultObj();
		resultObj.setData(t);
		return resultObj;
	}

	@Override
	public ResultObj deleteBulk(List<Integer> ids) {
		for(Integer id:ids){
			OverhaulFileEntity fileEntity  =this.findById((long)id);
			fileEntity.setStatus(String.valueOf(DataStatusEnum.DELETE.ordinal()));
			super.updateEntity(fileEntity);
		}
		ResultObj resultObj = new ResultObj();
		return resultObj;
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void deleteEntity(Serializable id){
		OverhaulFileEntity fileEntity  =this.findById(id);
		fileEntity.setStatus(String.valueOf(DataStatusEnum.DELETE.ordinal()));
		super.updateEntity(fileEntity);
	}

	@Override
	public boolean checkUpload() {
		SysUserEntity userEntity = RequestContext.get().getUser();
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("userId",FieldTypeEnum.STRING,MatchTypeEnum.EQ,userEntity.getId()));
		List<SysDutiesDetailEntity> list = sysDutiesDetailService.findByCondition(conditions, null);
		
		if(list!=null){
			String[] duties = new String[list.size()];
			for(int i =0;i<list.size();i++){
				duties[i] = list.get(i).getDutiesId();
			}
			conditions.clear();
			conditions.add(new Condition("id",FieldTypeEnum.INT,MatchTypeEnum.IN,duties));
			List<SysDutiesEntity> dutieslist = sysDutiesService.findByCondition(conditions, null);
			if(dutieslist!=null){
				for(SysDutiesEntity dutie : dutieslist){
					if("1013".equals(dutie.getCode())){
						return true;
					}
					if("检修专工".equals(dutie.getName())){
						return true;
					}
				}
			}
		}
		return false;
	}
	
}