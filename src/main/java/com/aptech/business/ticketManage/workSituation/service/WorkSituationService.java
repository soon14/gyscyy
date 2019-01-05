package com.aptech.business.ticketManage.workSituation.service;

import com.aptech.business.ticketManage.workSituation.domain.WorkSituationEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 开工和收工情况应用管理服务接口
 *
 * @author 
 * @created 2017-06-05 17:12:39
 * @lastModified 
 * @history
 *
 */
public interface WorkSituationService  extends IBaseEntityOperation<WorkSituationEntity> {
	/**
	 * @Description:  自己写的修改方法 
	 * @author         zhangzq 
	 * @Date           2017年6月21日 下午4:50:53 
	 * @throws         Exception
	 */
	ResultObj update(WorkSituationEntity t, Long id);
	
}