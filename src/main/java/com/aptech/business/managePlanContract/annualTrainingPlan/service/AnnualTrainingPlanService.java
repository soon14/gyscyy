package com.aptech.business.managePlanContract.annualTrainingPlan.service;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aptech.business.managePlanContract.annualTrainingPlan.domain.AnnualTrainingPlanEntity;
import com.aptech.business.managePlanContract.scienceTechnologyPlan.domain.ScienceTechnologyPlanEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 年度培训计划应用管理服务接口
 *
 * @author 
 * @created 2018-04-13 15:24:06
 * @lastModified 
 * @history
 *
 */
public interface AnnualTrainingPlanService  extends IBaseEntityOperation<AnnualTrainingPlanEntity> {
	ResultObj update(AnnualTrainingPlanEntity t,Long id);
	/**
	 * 批量导入
	 * @param request
	 * @param file
	 * @param filepath
	 * @return
	 * @throws Exception
	 */
	ResultObj importAnnualTrainingPlan(HttpServletRequest request,File file,String OriginalFilename) throws Exception;
	/**提交年度科技计划
	 * @Description:   
	 * @author         zhangzq 
	 * @Date           2017年6月12日 下午6:10:01 
	 * @throws         Exception
	 */
	void submit(String id, String selectUser);
	//同意流程
	void approveTicketAgree(String taskId,String procInstId,Map<String, Object> variables, AnnualTrainingPlanEntity annualTrainingPlanEntity,SysUserEntity userEntity);
	/**
	 * @Description:  更新年度培训计划信息,同意的时候
	 * @author         zhangzq 
	 * @Date           2017年6月8日 下午4:48:14 
	 * @throws         Exception
	 */
	void updateSpnrAgree(AnnualTrainingPlanEntity annualTrainingPlanEntity,SysUserEntity userEntity);
	//不同意流程
	void approveTicketDisagree(String taskId,String procInstId,Map<String, Object> variables, AnnualTrainingPlanEntity annualTrainingPlanEntity,SysUserEntity userEntity);
	/**
	 * @Description:  更新年度培训计划,驳回的时候
	 * @author         zhangzq 
	 * @Date           2017年6月8日 下午4:48:14 
	 * @throws         Exception
	 */
	void updateSpnrDisagree(AnnualTrainingPlanEntity annualTrainingPlanEntity,SysUserEntity userEntity);
}