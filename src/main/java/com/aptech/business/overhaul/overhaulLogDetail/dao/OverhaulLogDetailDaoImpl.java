package com.aptech.business.overhaul.overhaulLogDetail.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.overhaul.overhaulLogDetail.domain.OverhaulLogDetailEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 检修日志明细应用数据类
 *
 * @author 
 * @created 2018-01-04 10:56:15
 * @lastModified 
 * @history
 *
 */
@Repository("overhaulLogDetailDao")
public class OverhaulLogDetailDaoImpl extends AncestorDao<OverhaulLogDetailEntity> implements OverhaulLogDetailDao{
	
	private static boolean flag = true;
	@Override
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	@Override
	public String getNameSpace() {
		String nameSpade = "";
		if(flag){
			nameSpade = "com.aptech.business.overhaul.overhaulLogDetail";
		}else{
			nameSpade = "com.aptech.business.overhaul.overhaulLogDetailHis";
		}
		return nameSpade;
	}
}
