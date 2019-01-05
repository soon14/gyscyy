package com.aptech.business.run.runLog.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.run.runLog.domain.RunLogEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 运行日志应用数据类
 *
 * @author 
 * @created 2017-06-05 10:52:45
 * @lastModified 
 * @history
 *
 */
@Repository("runLogDao")
public class RunLogDaoImpl extends AncestorDao<RunLogEntity> implements RunLogDao{
	private static boolean flag = true;
	@Override
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	@Override
	public String getNameSpace() {
		String nameSpade = "";
		if(flag){
			nameSpade = "com.aptech.business.runLog";
		}else{
			nameSpade = "com.aptech.business.runLogWind";
		}
		return nameSpade;
	}

}
