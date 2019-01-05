package com.aptech.business.ticketManage.typicalTicket.service;

import java.io.Serializable;
import java.util.Map;

import com.aptech.business.ticketManage.typicalTicket.domain.TypicalTicketEntity;
import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 典型票应用管理服务接口
 *
 * @author 
 * @created 2017-07-20 15:55:55
 * @lastModified 
 * @history
 *
 */
public interface TypicalTicketService  extends IBaseEntityOperation<TypicalTicketEntity> {
	/**
	 * 提交实体
	 * 
	 * @param  id
	 */
	ResultObj submit(Serializable id, Map<String, Object> params);
	/**
	 * 修改提交实体
	 * 
	 * @param  id
	 */
	ResultObj editSubmit(Serializable id, Map<String, Object> params);
	/**
	 * 审批：流程
	 * 
	 * @param  id
	 */
	ResultObj approve(TypicalTicketEntity t,Map<String, Object> params);
	
	//鉴定
	ResultObj saveInvalid(TypicalTicketEntity t);
}