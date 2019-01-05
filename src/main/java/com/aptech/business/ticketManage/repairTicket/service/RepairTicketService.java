package com.aptech.business.ticketManage.repairTicket.service;

import java.util.List;
import java.util.Map;

import com.aptech.business.ticketManage.repairTicket.domain.RepairTicketEntity;
import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.Page;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 工作票应用应用管理服务接口
 *
 * @author 
 * @created 2017-06-29 18:50:39
 * @lastModified 
 * @history
 *
 */
public interface RepairTicketService  extends IBaseEntityOperation<RepairTicketEntity> {
	/**
	 * @Description:   自己写的修改方法
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:20:11 
	 * @throws         Exception
	 */
	ResultObj update(RepairTicketEntity t, Long id);
	/**
	 * @Description:   批量删除
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:20:18 
	 * @throws         Exception
	 */
	ResultObj deleteBulk(List<Integer> ids);
	/**
	 * @Description:   鉴定
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:20:23 
	 * @throws         Exception
	 */
	ResultObj saveInvalid(RepairTicketEntity workTicketEntity);
	/**
	 * @Description:   验证是否执行了安全措施 
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:20:29 
	 * @throws         Exception
	 */
	ResultObj isExecute(Long id);
	/**
	 * @Description:   修改时候的验证
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:20:41 
	 * @throws         Exception
	 */
	ResultObj updateValidate(Long id);
	/**
	 * @Description:    单个删除时候的验证
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:20:47 
	 * @throws         Exception
	 */
	ResultObj deleteValidate(Long id);
	/**
	 * @Description:   提交时候的验证
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:20:52 
	 * @throws         Exception
	 */
	ResultObj tijiaoValidate(Long id);
	/**
	 * @Description:   鉴定的时候的验证
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:21:00 
	 * @throws         Exception
	 */
	ResultObj invalidValidate(Long id);
	/**
	 * @Description:   设置标准票时候的验证
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:21:05 
	 * @throws         Exception
	 */
	ResultObj setValidate(Long id);
	List<RepairTicketEntity> searchJan(String yearStart,String yearEnd ,Map<String, Object> params, Page<RepairTicketEntity> page,String identify,String qualifiedCount,String unQualifiedCount)throws Exception;

}