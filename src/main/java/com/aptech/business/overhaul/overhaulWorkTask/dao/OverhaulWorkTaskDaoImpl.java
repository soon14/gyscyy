package com.aptech.business.overhaul.overhaulWorkTask.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.overhaul.overhaulWorkTask.domain.OverhaulWorkTaskEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 检修工作任务应用数据类
 *
 * @author 
 * @created 2017-12-22 10:15:21
 * @lastModified 
 * @history
 *
 */
@Repository("overhaulWorkTaskDao")
public class OverhaulWorkTaskDaoImpl extends AncestorDao<OverhaulWorkTaskEntity> implements OverhaulWorkTaskDao{
	private static boolean flag = true;
	@Override
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	@Override
	public String getNameSpace() {
		String nameSpade = "";
		if(flag){
			nameSpade =  "com.aptech.business.overhaulWorkTask";
		}else{
			nameSpade =  "com.aptech.business.overhaulWorkTaskHis";
		}
		return nameSpade;
	}
}
