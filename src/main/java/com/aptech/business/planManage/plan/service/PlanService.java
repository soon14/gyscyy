package com.aptech.business.planManage.plan.service;

import java.io.Serializable;
import java.util.Map;

import com.aptech.business.planManage.plan.domain.PlanEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 计划管理应用管理服务接口
 *
 * @author 
 * @created 2017-11-13 15:10:16
 * @lastModified 
 * @history
 *
 */
public interface PlanService  extends IBaseEntityOperation<PlanEntity> {
	
	public ResultObj add(PlanEntity planEntity);
	/**
	 * 提交实体
	 * 
	 * @param  id
	 */
	ResultObj submit(Serializable id, Map<String, Object> params);
	/**
	 * 审批：流程
	 * 
	 * @param  id
	 */
	ResultObj approve(PlanEntity t,Map<String, Object> params);
	/**
	 * 审批：流程
	 * 
	 * @param  id
	 */
	ResultObj approveFinish(PlanEntity t,Map<String, Object> params);
	/**
	 * @Description:   验证
	 * @author         changl 
	 * @Date           2017年11月20日 下午2:01:55 
	 * @throws         Exception
	 */
	public boolean validate(PlanEntity t);
	/**
	 * @Description:   验证(提交/删除)
	 * @author         changl 
	 * @Date           2017年11月20日 下午2:01:55 
	 * @throws         Exception
	 */
	public boolean  validateSubmit(PlanEntity t);
}