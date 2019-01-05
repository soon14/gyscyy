package com.aptech.business.run.routeInspect.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.run.routeInspect.domain.RouteInspectEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 巡检记录应用数据类
 *
 * @author 
 * @created 2018-03-13 17:34:53
 * @lastModified 
 * @history
 *
 */
@Repository("routeInspectDao")
public class RouteInspectDaoImpl extends AncestorDao<RouteInspectEntity> implements RouteInspectDao{
	private static boolean flag = true;
	@Override
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	@Override
	public String getNameSpace() {
		String nameSpace = "";
		if(flag){
			nameSpace = "com.aptech.business.run.routeInspect";
		}else{
			nameSpace = "com.aptech.business.run.routeInspectWind";
		}
		return nameSpace;
	}
}
