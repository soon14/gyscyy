package com.aptech.business.overhaul.overhaulSafe.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.overhaul.overhaulSafe.domain.OverhaulSafeEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 安全交底应用数据类
 *
 * @author 
 * @created 2017-08-11 09:28:01
 * @lastModified 
 * @history
 *
 */
@Repository("overhaulSafeDao")
public class OverhaulSafeDaoImpl extends AncestorDao<OverhaulSafeEntity> implements OverhaulSafeDao{
	private static boolean flag = true;
	@Override
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	@Override
	public String getNameSpace() {
		String nameSpade = "";
		if(flag){
			nameSpade =  "com.aptech.business.overhaulSafe";
		}else{
			nameSpade =  "com.aptech.business.overhaulSafeHis";
		}
		return nameSpade;
	}
}
