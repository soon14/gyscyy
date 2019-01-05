package com.aptech.business.ticketManage.workSafe.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aptech.business.ticketManage.workSafe.domain.WorkSafeEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 安全措施应用管理服务接口
 *
 * @author 
 * @created 2017-06-05 17:12:33
 * @lastModified 
 * @history
 *
 */
public interface WorkSafeService  extends IBaseEntityOperation<WorkSafeEntity> {
	
	
	/**
	 * @Description:   工作票-添加安全措施
	 * @author         
	 * @Date           
	 * @throws         Exception
	 */
	public @ResponseBody <T>  ResultObj addSafeInfo(HttpServletRequest request,Map<String, Object> params, @PathVariable String uuid,@PathVariable Long type);
	
	public @ResponseBody <T>  ResultObj editSafeInfo(HttpServletRequest request,Map<String, Object> params, @PathVariable Long workid,@PathVariable Long type);
	
}