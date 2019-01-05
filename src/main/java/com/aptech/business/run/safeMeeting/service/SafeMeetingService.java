package com.aptech.business.run.safeMeeting.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aptech.business.run.runLog.domain.RunLogEntity;
import com.aptech.business.run.safeMeeting.domain.SafeMeetingEntity;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 安全例会应用管理服务接口
 *
 * @author 
 * @created 2017-06-07 16:00:28
 * @lastModified 
 * @history
 *
 */
public interface SafeMeetingService  extends IBaseEntityOperation<SafeMeetingEntity> {
	ResultObj update(SafeMeetingEntity t,Long id,boolean flag);
	ResultListObj searchDate(HttpServletRequest request,Map<String, Object> params);
	ResultListObj searchDataForWind(HttpServletRequest request,Map<String, Object> params);
	ResultObj addForWind(SafeMeetingEntity meetingEntity,HttpServletRequest request);
	void deleteEntityForWind(Long id);
	List<SafeMeetingEntity> findByConditionForWind(Long id);
	ResultObj updateForWind(SafeMeetingEntity t,Long id,boolean flag);
	ResultObj deleteForWind(Long id);
	SafeMeetingEntity findByIdForWind(Long id);
}