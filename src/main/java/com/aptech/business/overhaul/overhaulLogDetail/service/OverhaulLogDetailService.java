package com.aptech.business.overhaul.overhaulLogDetail.service;

import javax.servlet.http.HttpServletRequest;

import com.aptech.business.overhaul.overhaulLogDetail.domain.OverhaulLogDetailEntity;
import com.aptech.business.overhaul.overhaulSpareconsume.domain.OverhaulSpareconsumeEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 检修日志明细应用管理服务接口
 *
 * @author 
 * @created 2018-01-04 10:56:15
 * @lastModified 
 * @history
 *
 */
public interface OverhaulLogDetailService  extends IBaseEntityOperation<OverhaulLogDetailEntity> {
	/**
	 * 
	 * 添加明细(工作任务、安全交底、物资等)
	 * 
	 * @param @param t
	 * @param @param params
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月18日 下午1:21:36
	 * @lastModified
	 */
	public ResultObj addMore(HttpServletRequest request,OverhaulLogDetailEntity overhaulLogDetailEntity);
	
}