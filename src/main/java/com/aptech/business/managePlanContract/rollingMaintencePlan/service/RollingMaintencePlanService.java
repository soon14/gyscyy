package com.aptech.business.managePlanContract.rollingMaintencePlan.service;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aptech.business.managePlanContract.annualMaintenancePlan.domain.AnnualMaintenancePlanEntity;
import com.aptech.business.managePlanContract.rollingMaintencePlan.domain.RollingMaintencePlanEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 三年滚动检修计划应用管理服务接口
 *
 * @author 
 * @created 2018-04-23 18:55:02
 * @lastModified 
 * @history
 *
 */
public interface RollingMaintencePlanService  extends IBaseEntityOperation<RollingMaintencePlanEntity> {
	ResultObj update(RollingMaintencePlanEntity t,Long id);
	/**
	 * 批量导入
	 * @param request
	 * @param file
	 * @param filepath
	 * @return
	 * @throws Exception
	 */
	ResultObj importRollingMaintencePlan(HttpServletRequest request,File file,String OriginalFilename) throws Exception;
	/**	三年滚动检修计划
	 * @Description:   
	 * @author         zhangzq 
	 * @Date           2017年6月12日 下午6:10:01 
	 * @throws         Exception
	 */
	void submit(String id, String selectUser);
	//同意流程
	void approveTicketAgree(String taskId,String procInstId,Map<String, Object> variables, RollingMaintencePlanEntity rollingMaintencePlanEntity,SysUserEntity userEntity);
	/**
	 * @Description:  更新三年滚动检修计划信息,同意的时候
	 * @author         zhangzq 
	 * @Date           2017年6月8日 下午4:48:14 
	 * @throws         Exception
	 */
	void updateSpnrAgree(RollingMaintencePlanEntity rollingMaintencePlanEntity,SysUserEntity userEntity);
	//不同意流程
	void approveTicketDisagree(String taskId,String procInstId,Map<String, Object> variables, RollingMaintencePlanEntity rollingMaintencePlanEntity,SysUserEntity userEntity);
	/**
	 * @Description:  更新三年滚动检修计划,驳回的时候
	 * @author         zhangzq 
	 * @Date           2017年6月8日 下午4:48:14 
	 * @throws         Exception
	 */
	void updateSpnrDisagree(RollingMaintencePlanEntity rollingMaintencePlanEntity,SysUserEntity userEntity);
}