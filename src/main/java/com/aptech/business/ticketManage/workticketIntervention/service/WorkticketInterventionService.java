package com.aptech.business.ticketManage.workticketIntervention.service;

import com.aptech.business.ticketManage.workticketIntervention.domain.WorkticketInterventionEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 电气工作票应用管理服务接口
 *
 * @author 
 * @created 2017-06-29 17:12:46
 * @lastModified 
 * @history
 *
 */
public interface WorkticketInterventionService  extends IBaseEntityOperation<WorkticketInterventionEntity> {
	/**
	 * @Description:   更新的是工作票的从表属性,同意的时候
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:32:23 
	 * @throws         Exception
	 */
	void updateSpnrAgree(WorkticketInterventionEntity workticketInterventionEntity,SysUserEntity userEntity);
	/**
	 * @Description:   更新的是工作票的从表属性,驳回的时候
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:32:30 
	 * @throws         Exception
	 */
	void updateSpnrDisagree(WorkticketInterventionEntity workticketInterventionEntity,SysUserEntity userEntity);
	/**
	 * @Description:   提交
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:32:39 
	 * @throws         Exception
	 */
	void submit(String id, String selectUser);
	/**
	 * @Description:   更新备注
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:32:45 
	 * @throws         Exception
	 */
	ResultObj update(WorkticketInterventionEntity t, Long id);
	
}