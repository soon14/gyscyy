package com.aptech.business.managePlanContract.annualMaintenancePlan.service;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aptech.business.managePlanContract.annualMaintenancePlan.domain.AnnualMaintenancePlanEntity;
import com.aptech.business.managePlanContract.annualSupervisionPlan.domain.AnnualSupervisionPlanEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 年度检修维护计划应用管理服务接口
 *
 * @author 
 * @created 2018-04-18 15:31:10
 * @lastModified 
 * @history
 *
 */
public interface AnnualMaintenancePlanService  extends IBaseEntityOperation<AnnualMaintenancePlanEntity> {
	ResultObj update(AnnualMaintenancePlanEntity t,Long id);
	/**
	 * 批量导入
	 * @param request
	 * @param file
	 * @param filepath
	 * @return
	 * @throws Exception
	 */
	ResultObj importAnnualMaintenancePlan(HttpServletRequest request,File file,String OriginalFilename) throws Exception;
	/**	提交年度检修维护计划
	 * @Description:   
	 * @author         zhangzq 
	 * @Date           2017年6月12日 下午6:10:01 
	 * @throws         Exception
	 */
	void submit(String id, String selectUser);
	//同意流程
	void approveTicketAgree(String taskId,String procInstId,Map<String, Object> variables, AnnualMaintenancePlanEntity annualMaintenancePlanEntity,SysUserEntity userEntity);
	/**
	 * @Description:  更新年度检修维护计划信息,同意的时候
	 * @author         zhangzq 
	 * @Date           2017年6月8日 下午4:48:14 
	 * @throws         Exception
	 */
	void updateSpnrAgree(AnnualMaintenancePlanEntity annualMaintenancePlanEntity,SysUserEntity userEntity);
	//不同意流程
	void approveTicketDisagree(String taskId,String procInstId,Map<String, Object> variables, AnnualMaintenancePlanEntity annualMaintenancePlanEntity,SysUserEntity userEntity);
	/**
	 * @Description:  更新年度检修维护计划,驳回的时候
	 * @author         zhangzq 
	 * @Date           2017年6月8日 下午4:48:14 
	 * @throws         Exception
	 */
	void updateSpnrDisagree(AnnualMaintenancePlanEntity annualMaintenancePlanEntity,SysUserEntity userEntity);
}