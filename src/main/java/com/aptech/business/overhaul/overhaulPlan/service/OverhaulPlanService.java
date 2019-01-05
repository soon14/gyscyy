package com.aptech.business.overhaul.overhaulPlan.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.aptech.business.overhaul.overhaulPlan.domain.OverhaulPlanEntity;
import com.aptech.business.overhaul.overhaulPlanMember.domain.OverhaulPlanMemberEntity;
import com.aptech.business.overhaul.overhaulProject.domain.OverhaulProjectEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 检修计划应用管理服务接口
 *
 * @author 
 * @created 2017-06-09 10:42:58
 * @lastModified 
 * @history
 *
 */
public interface OverhaulPlanService  extends IBaseEntityOperation<OverhaulPlanEntity> {

	/**
	 * 提交检修计划
	 * @param id
	 * @param params
	 * @return
	 */
	ResultObj submit(Serializable id, Map<String, Object> params);
	
	/**
	 * 审核
	 * @param t
	 * @param params
	 * @return
	 */
	ResultObj approve(OverhaulPlanEntity t,Map<String,Object> params);
	
	public void  insertMemberAuto(OverhaulPlanMemberEntity t, OverhaulPlanEntity planEntity);
}