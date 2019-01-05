package com.aptech.business.ticketManage.workLineTwo.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.workLineTwo.domain.WorkLineTwoEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 电气工作票应用数据类
 *
 * @author zzq
 * @created 2017-06-05 17:12:46
 * @lastModified 
 * @history
 *
 */
@Repository("workLineTwoDao")
public class WorkLineTwoDaoImpl extends AncestorDao<WorkLineTwoEntity> implements WorkLineTwoDao{
	
	@Override
	public String getNameSpace() {
		return "com.aptech.business.workLineTwo";
	}
}
