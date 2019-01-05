package com.aptech.business.ticketManage.workTicketEarth.service;

import java.util.List;

import com.aptech.business.ticketManage.workTicketEarth.domain.WorkTicketEarthEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 工作票应用应用管理服务接口
 *
 * @author 
 * @created 2017-06-02 11:50:39
 * @lastModified 
 * @history
 *
 */
public interface WorkTicketEarthService  extends IBaseEntityOperation<WorkTicketEarthEntity> {
	/**
	 * @Description:   自己写的修改方法
	 * @author         sunliang 
	 * @Date           2017年6月20日 下午3:34:59 
	 * @throws         Exception
	 */
	ResultObj update(WorkTicketEarthEntity t, Long id);
	/** 批量删除
	 * @author         sunliang 
	 * @Date           2017年6月20日 下午3:34:59 
	 * @throws         Exception
	 */
	ResultObj deleteBulk(List<Integer> ids);
	/**
	 * @Description:   作废
	 * @author         sunliang 
	 * @Date           2017年6月21日 下午5:01:36 
	 * @throws         Exception
	 */
	ResultObj saveInvalid(WorkTicketEarthEntity workTicketEntity);
	/**
	 * @Description:   验证是否执行了安全措施
	 * @author         sunliang 
	 * @Date           2017年6月28日 下午3:58:01 
	 * @throws         Exception
	 */
	ResultObj isExecute(Long id);
	/**
	 * @Description:   申请试运
	 * @author         sunliang 
	 * @Date           2017年6月28日 下午5:17:16 
	 * @throws         Exception
	 */
	ResultObj isExecuteSqsy(Long id);
	/**
	 * @Description:   试运恢复
	 * @author         sunliang 
	 * @Date           2017年6月28日 下午5:17:16 
	 * @throws         Exception
	 */
	ResultObj isExecuteSyhf(Long id);
	/**
	 * @Description:   修改时候的验证
	 * @author         sunliang 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	ResultObj updateValidate(Long id);
	/**
	 * @Description:   单个删除时候的验证
	 * @author         sunliang 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	ResultObj deleteValidate(Long id);
	/**
	 * @Description:   提交时候的验证
	 * @author         sunliang 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	ResultObj tijiaoValidate(Long id);
	/**
	 * @Description:   鉴定的时候的验证
	 * @author         sunliang 
	 * @Date           2017年6月29日 下午2:00:37 
	 * @throws         Exception
	 */
	ResultObj invalidValidate(Long id);
	/**
	 * @Description:   设置标准票时候的验证
	 * @author         sunliang 
	 * @Date           2017年6月29日 下午2:00:37 
	 * @throws         Exception
	 */
	ResultObj setValidate(Long id);
	
}