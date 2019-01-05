package com.aptech.business.overhaul.overhaulLog.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.aptech.business.overhaul.overhaulLog.domain.OverhaulLogEntity;
import com.aptech.framework.orm.AncestorDao;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.search.Condition;

/**
 * 
 * 检修日志应用数据类
 *
 * @author 
 * @created 2017-06-30 18:28:04
 * @lastModified 
 * @history
 *
 */
@Repository("overhaulLogDao")
public class OverhaulLogDaoImpl extends AncestorDao<OverhaulLogEntity> implements OverhaulLogDao{
	private static boolean flag = true;
	@Override
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	@Override
	public String getNameSpace() {
		String nameSpade = "";
		if(flag){
			nameSpade = "com.aptech.business.overhaul.overhaulLog";
		}else{
			nameSpade = "com.aptech.business.overhaul.overhaulLogHis";
		}
		return nameSpade;
	}

	@Override
	public List<OverhaulLogEntity> findByDate(List<Condition> conditions,
			Page<OverhaulLogEntity> page) {
		// TODO Auto-generated method stub
		return super.findByCondition("findByDate",conditions,page);
	}

}
