package com.aptech.business.overhaul.overhaulSpareconsume.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.overhaul.overhaulSpareconsume.domain.OverhaulSpareconsumeEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 备件消耗应用数据类
 *
 * @author 
 * @created 2018-03-15 10:23:12
 * @lastModified 
 * @history
 *
 */
@Repository("overhaulSpareconsumeDao")
public class OverhaulSpareconsumeDaoImpl extends AncestorDao<OverhaulSpareconsumeEntity> implements OverhaulSpareconsumeDao{
	private static boolean flag = true;
	
	@Override
	public void setFlag(boolean flag) {
		this.flag=flag;
		
	}
	
	@Override
	public String getNameSpace() {
		String nameSpade = "";
		if(flag){
			nameSpade =  "com.aptech.business.overhaulSpareconsume";
		}else{
			nameSpade =  "com.aptech.business.overhaulSpareconsumeHis";
		}
		return  nameSpade;
	}

	
}
