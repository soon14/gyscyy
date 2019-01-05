package com.aptech.business.run.routeInspect.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;

import com.aptech.business.run.routeInspect.domain.RouteInspectEntity;
import com.aptech.business.run.runRecord.domain.RunRecordEntity;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 巡检记录应用管理服务接口
 *
 * @author 
 * @created 2018-03-13 17:34:53
 * @lastModified 
 * @history
 *
 */
public interface RouteInspectService  extends IBaseEntityOperation<RouteInspectEntity> {
	
	public List<RouteInspectEntity> findByConditionDescId(List<Condition> conditions, Page<RouteInspectEntity> page);
	ResultListObj searchData(HttpServletRequest request,@RequestBody Map<String, Object> params);
	ResultObj addForWind(@RequestBody RouteInspectEntity routeInspectEntity, HttpServletRequest request);
	void deleteEntityForWind(Long id);
	List<RouteInspectEntity> findByConditionForWind(Long rlId);
	void updateEntityForWind(RouteInspectEntity routeInspectEntity);
	RouteInspectEntity findByIdForWind(Long id);
	ResultObj deleteForWind(Long id);
}