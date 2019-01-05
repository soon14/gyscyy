package com.aptech.business.run.runRecord.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;

import com.aptech.business.run.runLog.domain.RunLogEntity;
import com.aptech.business.run.runRecord.domain.RunRecordEntity;
import com.aptech.business.run.safeMeeting.domain.SafeMeetingEntity;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 运行记事应用管理服务接口
 *
 * @author 
 * @created 2017-06-05 15:28:10
 * @lastModified 
 * @history
 *
 */
public interface RunRecordService  extends IBaseEntityOperation<RunRecordEntity> {
	ResultListObj searchDate(HttpServletRequest request,@RequestBody Map<String, Object> params);
	ResultListObj searchDateForWind(HttpServletRequest request,@RequestBody Map<String, Object> params);
	ResultObj addForWind(@RequestBody RunRecordEntity runRecordEntity, HttpServletRequest request);
	void deleteEntityForWind(Long id);
	List<RunRecordEntity> findByConditionForWind(Long rlId);
	void updateEntityForWind(RunRecordEntity recordEntity);
	RunRecordEntity findByIdForWind(Long id);
	ResultObj deleteForWind(Long id);
	ResultObj addRun(RunRecordEntity t,Long rlId);
}