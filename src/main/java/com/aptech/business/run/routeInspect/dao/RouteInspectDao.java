package com.aptech.business.run.routeInspect.dao;

import com.aptech.business.run.routeInspect.domain.RouteInspectEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 巡检记录应用数据接口
 *
 * @author 
 * @created 2018-03-13 17:34:53
 * @lastModified 
 * @history
 *
 */
public interface RouteInspectDao  extends IBaseEntityOperation<RouteInspectEntity>{
	public void setFlag(boolean flag);
}