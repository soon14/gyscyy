package com.aptech.business.managePlanContract.accidentMeasuresPlan.service;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aptech.business.managePlanContract.accidentMeasuresPlan.domain.AccidentMeasuresPlanEntity;
import com.aptech.business.managePlanContract.annualSupervisionPlan.domain.AnnualSupervisionPlanEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 年度反事故措施计划应用管理服务接口
 *
 * @author 
 * @created 2018-04-16 15:12:09
 * @lastModified 
 * @history
 *
 */
public interface AccidentMeasuresPlanService  extends IBaseEntityOperation<AccidentMeasuresPlanEntity> {
	ResultObj update(AccidentMeasuresPlanEntity t ,Long id);
	/**
	 * 批量导入
	 * @param request
	 * @param file
	 * @param filepath
	 * @return
	 * @throws Exception
	 */
	ResultObj importAccidentMeasuresPlan(HttpServletRequest request,File file,String OriginalFilename) throws Exception;
	/**	提交年度反措施计划
	 * @Description:   
	 * @author         zhangzq 
	 * @Date           2017年6月12日 下午6:10:01 
	 * @throws         Exception
	 */
	void submit(String id, String selectUser);
	//同意流程
		void approveTicketAgree(String taskId,String procInstId,Map<String, Object> variables, AccidentMeasuresPlanEntity accidentMeasuresPlanEntity,SysUserEntity userEntity);
		/**
		 * @Description:  更新年度反措施计划信息,同意的时候
		 * @author         zhangzq 
		 * @Date           2017年6月8日 下午4:48:14 
		 * @throws         Exception
		 */
		void updateSpnrAgree(AccidentMeasuresPlanEntity accidentMeasuresPlanEntity,SysUserEntity userEntity);
		//不同意流程
		void approveTicketDisagree(String taskId,String procInstId,Map<String, Object> variables, AccidentMeasuresPlanEntity accidentMeasuresPlanEntity,SysUserEntity userEntity);
		/**
		 * @Description:  更新年度反措施计划,驳回的时候
		 * @author         zhangzq 
		 * @Date           2017年6月8日 下午4:48:14 
		 * @throws         Exception
		 */
		void updateSpnrDisagree(AccidentMeasuresPlanEntity accidentMeasuresPlanEntity,SysUserEntity userEntity);
}