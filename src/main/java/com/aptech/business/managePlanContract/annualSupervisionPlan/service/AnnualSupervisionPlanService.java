package com.aptech.business.managePlanContract.annualSupervisionPlan.service;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aptech.business.managePlanContract.annualSupervisionPlan.domain.AnnualSupervisionPlanEntity;
import com.aptech.business.managePlanContract.annualTechnicalPlan.domain.AnnualTechnicalPlanEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 年度技术监督计划应用管理服务接口
 *
 * @author 
 * @created 2018-04-12 15:36:28
 * @lastModified 
 * @history
 *
 */
public interface AnnualSupervisionPlanService  extends IBaseEntityOperation<AnnualSupervisionPlanEntity> {
	/**
	 * 批量导入
	 * @param request
	 * @param file
	 * @param filepath
	 * @return
	 * @throws Exception
	 */
	ResultObj importAnnualSupervisionPlan(HttpServletRequest request,File file,String OriginalFilename) throws Exception;
	ResultObj update(AnnualSupervisionPlanEntity t ,Long id);
	/**	提交年度技术监督计划
	 * @Description:   
	 * @author         zhangzq 
	 * @Date           2017年6月12日 下午6:10:01 
	 * @throws         Exception
	 */
	void submit(String id, String selectUser);
	//同意流程
	void approveTicketAgree(String taskId,String procInstId,Map<String, Object> variables, AnnualSupervisionPlanEntity annualSupervisionPlanEntity,SysUserEntity userEntity);
	/**
	 * @Description:  更新年度技术监督计划信息,同意的时候
	 * @author         zhangzq 
	 * @Date           2017年6月8日 下午4:48:14 
	 * @throws         Exception
	 */
	void updateSpnrAgree(AnnualSupervisionPlanEntity annualSupervisionPlanEntity,SysUserEntity userEntity);
	//不同意流程
	void approveTicketDisagree(String taskId,String procInstId,Map<String, Object> variables, AnnualSupervisionPlanEntity annualSupervisionPlanEntity,SysUserEntity userEntity);
	/**
	 * @Description:  更新年度技术监督计划,驳回的时候
	 * @author         zhangzq 
	 * @Date           2017年6月8日 下午4:48:14 
	 * @throws         Exception
	 */
	void updateSpnrDisagree(AnnualSupervisionPlanEntity annualSupervisionPlanEntity,SysUserEntity userEntity);
}