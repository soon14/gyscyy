package com.aptech.business.ticketManage.operationTicket.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.aptech.business.ticketManage.operationTicket.domain.OperationTicketEntity;
import com.aptech.business.ticketManage.typicalTicket.domain.TypicalTicketEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.Page;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 操作票应用管理服务接口
 *
 * @author 
 * @created 2017-07-12 15:53:44
 * @lastModified 
 * @history
 *
 */
public interface OperationTicketService  extends IBaseEntityOperation<OperationTicketEntity> {
	/**
	 * 提交实体
	 * 
	 * @param  id
	 */
	ResultObj submit(Serializable id, Map<String, Object> params);
	/**
	 * 审批：流程
	 * 
	 * @param  id
	 */
	ResultObj approve(OperationTicketEntity t,Map<String, Object> params);
	/**
	 * 设置典型票
	 * 
	 * @param  id
	 */
	ResultObj  isSet(OperationTicketEntity t,TypicalTicketEntity tEntity);
	/**
	 * 引用典型票
	 * 
	 * @param  id
	 */
	ResultObj  yydxp(OperationTicketEntity t);
	
	void update(OperationTicketEntity t);
	/**
	 * @Description:   鉴定
	 * @author         changl 
	 * @Date           2017年12月14日 下午4:51:03 
	 * @throws         Exception
	 */
	ResultObj saveInvalid(OperationTicketEntity operationTicketEntity);
	List<OperationTicketEntity> searchJan(String yearStart,String yearEnd ,Map<String, Object> params, Page<OperationTicketEntity> page,String identify,String qualifiedCount,String unQualifiedCount)throws Exception;
	
	ResultObj addValidate(SysUserEntity userEntity);
	
	ResultObj addCzpValidate(SysUserEntity userEntity);
	/**
	 * @Description: 查询台账处查询
	 * @author zhangxb
	 * @Date 2018年4月9日 下午11:00:01
	 * @throws Exception
	 */
	public <O> List<O> findByConditionForEquip(Map<String, Object> params, Page<O> page);
}