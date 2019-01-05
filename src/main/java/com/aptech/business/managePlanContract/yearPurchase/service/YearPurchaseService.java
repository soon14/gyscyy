package com.aptech.business.managePlanContract.yearPurchase.service;

import java.io.Serializable;
import java.util.Map;

import com.aptech.business.managePlanContract.yearPurchase.domain.YearPurchaseEntity;
import com.aptech.business.ticketManage.workFire.domain.WorkFireEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 年度采购应用管理服务接口
 *
 * @author 
 * @created 2018-04-12 13:45:42
 * @lastModified 
 * @history
 *
 */
public interface YearPurchaseService  extends IBaseEntityOperation<YearPurchaseEntity> {
	
	/**
	 * 提交
	 * @param id
	 * @param params
	 * @return
	 */
	ResultObj submit(String  id,  String selectUser);
	
	void updateSpnrAgree(YearPurchaseEntity workFireEntity,SysUserEntity userEntity);
	/**
	 * @Description:  更新电气第一种票的 信息，更新的是工作票的从表属性,驳回的时候
	 * @author         zhangzq 
	 * @Date           2017年6月8日 下午4:48:14 
	 * @throws         Exception
	 */
	void updateSpnrDisagree(YearPurchaseEntity workFireEntity,SysUserEntity userEntity);
}