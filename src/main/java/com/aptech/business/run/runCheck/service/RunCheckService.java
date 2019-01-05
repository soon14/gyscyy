package com.aptech.business.run.runCheck.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aptech.business.run.runCheck.domain.RunCheckEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 运行检查应用管理服务接口
 *
 * @author 
 * @created 2017-09-08 15:00:42
 * @lastModified 
 * @history
 *
 */
public interface RunCheckService  extends IBaseEntityOperation<RunCheckEntity> {
 /**
  * 检查结果统计	
  */
   <O> List<O> statistics(Map<String, Object> params, List<int[]> list);
   
   ResultObj addRunCheckInfo(RunCheckEntity runCheckEntity, HttpServletRequest reques);

}