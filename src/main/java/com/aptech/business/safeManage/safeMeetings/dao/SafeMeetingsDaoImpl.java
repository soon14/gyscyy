package com.aptech.business.safeManage.safeMeetings.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.safeManage.safeMeetings.domain.SafeMeetingsEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 安全会议应用数据类
 *
 * @author 
 * @created 2018-03-29 10:03:15
 * @lastModified 
 * @history
 *
 */
@Repository("safeMeetingsDao")
public class SafeMeetingsDaoImpl extends AncestorDao<SafeMeetingsEntity> implements SafeMeetingsDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.safeMeetings";
	}
}
