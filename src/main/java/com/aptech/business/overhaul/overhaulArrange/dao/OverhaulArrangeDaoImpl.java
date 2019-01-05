package com.aptech.business.overhaul.overhaulArrange.dao;

import org.springframework.stereotype.Repository;
import com.aptech.business.overhaul.overhaulArrange.domain.OverhaulArrangeEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 工作安排应用数据类
 *
 * @author 
 * @created 2017-12-20 16:24:01
 * @lastModified 
 * @history
 *
 */
@Repository("overhaulArrangeDao")
public class OverhaulArrangeDaoImpl extends AncestorDao<OverhaulArrangeEntity> implements OverhaulArrangeDao{
	
	private static boolean flag = true;
	
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	@Override
	public String getNameSpace() {
		String nameSpade = "";
		if(flag){
			nameSpade = "com.aptech.business.overhaulArrange";
		}else{
			nameSpade = "com.aptech.business.overhaulArrangeHis";
		}
		return nameSpade;
	}
}
