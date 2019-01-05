package com.aptech.business.managePlanContract.goodsPurchase.service;

import javax.servlet.http.HttpServletRequest;

import com.aptech.business.managePlanContract.goodsPurchase.domain.GoodsPurchaseEntity;
import com.aptech.business.managePlanContract.yearPurchase.domain.YearPurchaseEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 物资采购应用管理服务接口
 *
 * @author 
 * @created 2018-04-17 15:00:02
 * @lastModified 
 * @history
 *
 */
public interface GoodsPurchaseService  extends IBaseEntityOperation<GoodsPurchaseEntity> {
	
	/**
	 * 提交
	 * @param id
	 * @param params
	 * @return
	 */
	ResultObj submit(String  id,  String selectUser);
	
	void updateSpnrAgree(GoodsPurchaseEntity entity,SysUserEntity userEntity);
	/**
	 * @Description:  更新电气第一种票的 信息，更新的是工作票的从表属性,驳回的时候
	 * @author         zhangzq 
	 * @Date           2017年6月8日 下午4:48:14 
	 * @throws         Exception
	 */
	void updateSpnrDisagree(GoodsPurchaseEntity entity,SysUserEntity userEntity);
	
	
	public ResultObj addEntityList(GoodsPurchaseEntity t,HttpServletRequest request);
}