package com.aptech.business.ticketManage.workEarth.service;

import com.aptech.business.ticketManage.workEarth.domain.WorkEarthEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 电气工作票应用管理服务接口
 *
 * @author 
 * @created 2017-06-05 17:12:46
 * @lastModified 
 * @history
 *
 */
public interface WorkEarthService  extends IBaseEntityOperation<WorkEarthEntity> {
	/**
	 * @Description:  更新电气第一种票的 信息，更新的是工作票的从表属性,同意的时候
	 * @author         sunliang 
	 * @Date           2017年6月8日 下午4:48:14 
	 * @throws         Exception
	 */
	void updateSpnrAgree(WorkEarthEntity workEarthEntity,SysUserEntity userEntity);
	/**
	 * @Description:  更新电气第一种票的 信息，更新的是工作票的从表属性,驳回的时候
	 * @author         sunliang 
	 * @Date           2017年6月8日 下午4:48:14 
	 * @throws         Exception
	 */
	void updateSpnrDisagree(WorkEarthEntity workEarthEntity,SysUserEntity userEntity);
	/**提交电气第一种工作票
	 * @Description:   
	 * @author         sunliang 
	 * @Date           2017年6月12日 下午6:10:01 
	 * @throws         Exception
	 */
	void submit(String id, String selectUser);
	/**
	 * @Description:   更新备注
	 * @author         sunliang 
	 * @Date           2017年7月4日 上午10:20:44 
	 * @throws         Exception
	 */
	ResultObj update(WorkEarthEntity t, Long id);
	
}