package com.aptech.business.run.safeMeeting.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.run.safeMeeting.domain.SafeMeetingEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 安全例会应用数据类
 *
 * @author 
 * @created 2017-06-07 16:00:28
 * @lastModified 
 * @history
 *
 */
@Repository("safeMeetingDao")
public class SafeMeetingDaoImpl extends AncestorDao<SafeMeetingEntity> implements SafeMeetingDao{
	private static boolean flag = true;
	@Override
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	@Override
	public String getNameSpace() {
		String namespace = "";
		// TODO Auto-generated method stub
		if(flag){
			namespace = "com.aptech.business.safeMeeting";
		}else{
			namespace = "com.aptech.business.safeMeetingWind";
		}
		return namespace;
	}
}
