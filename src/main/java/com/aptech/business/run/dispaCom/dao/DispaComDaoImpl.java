package com.aptech.business.run.dispaCom.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.run.dispaCom.domain.DispaComEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 调度命令应用数据类
 *
 * @author 
 * @created 2017-06-07 11:31:01
 * @lastModified 
 * @history
 *
 */
@Repository("dispaComDao")
public class DispaComDaoImpl extends AncestorDao<DispaComEntity> implements DispaComDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.dispatchCommand";
	}
}
