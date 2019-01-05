package com.aptech.business.ticketManage.workTicketLine.service;

import java.util.List;
import java.util.Map;

import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.business.ticketManage.workTicketLine.domain.WorkTicketLineEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.Page;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 工作票应用应用管理服务接口
 *
 * @author 
 * @created 2017-10-11 11:50:39
 * @lastModified 
 * @history
 *
 */
public interface WorkTicketLineService  extends IBaseEntityOperation<WorkTicketLineEntity> {
	/**
	 * @Description:   自己写的修改方法
	 * @author         zhangzq 
	 * @Date           2017年6月20日 下午3:34:59 
	 * @throws         Exception
	 */
	ResultObj update(WorkTicketLineEntity t, Long id);
	/** 批量删除
	 * @author         zhangzq 
	 * @Date           2017年6月20日 下午3:34:59 
	 * @throws         Exception
	 */
	ResultObj deleteBulk(List<Integer> ids);
	/**
	 * @Description:   作废
	 * @author         zhangzq 
	 * @Date           2017年6月21日 下午5:01:36 
	 * @throws         Exception
	 */
	ResultObj saveInvalid(WorkTicketLineEntity workTicketLineEntity);
	/**
	 * @Description:   验证是否执行了安全措施
	 * @author         zhangzq 
	 * @Date           2017年6月28日 下午3:58:01 
	 * @throws         Exception
	 */
	ResultObj isExecute(Long id);
	/**
	 * @Description:   申请试运
	 * @author         zhangzq 
	 * @Date           2017年6月28日 下午5:17:16 
	 * @throws         Exception
	 */
	ResultObj isExecuteSqsy(Long id);
	/**
	 * @Description:   试运恢复
	 * @author         zhangzq 
	 * @Date           2017年6月28日 下午5:17:16 
	 * @throws         Exception
	 */
	ResultObj isExecuteSyhf(Long id);
	/**
	 * @Description:   修改时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	ResultObj updateValidate(Long id);
	/**
	 * @Description:   单个删除时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	ResultObj deleteValidate(Long id);
	/**
	 * @Description:   提交时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	ResultObj tijiaoValidate(Long id);
	/**
	 * @Description:   鉴定的时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午2:00:37 
	 * @throws         Exception
	 */
	ResultObj invalidValidate(Long id);
	/**
	 * @Description:   设置标准票时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午2:00:37 
	 * @throws         Exception
	 */
	ResultObj setValidate(Long id);
	/**
	 * @Description:   签发人是否写了安全措施
	 * @author         zhangzq 
	 * @Date           2017年8月8日 上午8:21:34 
	 * @throws         Exception
	 */
	ResultObj isQfAqcs(Long id);
	/**
	 * @Description:   签发人是否写了安全措施
	 * @author         zhangzq 
	 * @Date           2017年8月8日 上午8:21:34 
	 * @throws         Exception
	 */
	ResultObj isQfAqcsAdd(Long id);
	/**
	 * @Description:   许可人是否写了安全措施
	 * @author         zhangzq 
	 * @Date           2017年8月8日 上午8:20:39 
	 * @throws         Exception
	 */
	ResultObj isXkAqcs(Long id);
	/**
	 * @Description:   许可人是否写了安全措施  新增按钮
	 * @author         zhangzq 
	 * @Date           2017年8月8日 上午8:20:39 
	 * @throws         Exception
	 */
	ResultObj isXkAqcsAdd(Long id);
	List<WorkTicketLineEntity> findAll(Map<String, Object> params, Page<WorkTicketLineEntity> page);
	
	ResultObj addValidate(String uuid);
	
	ResultObj editValidate(Long uuid);
	List<WorkTicketLineEntity> searchJan(String yearStart,String yearEnd ,Map<String, Object> params, Page<WorkTicketLineEntity> page,String identify,String qualifiedCount,String unQualifiedCount)throws Exception;

}