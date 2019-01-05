package com.aptech.business.run.runRecord.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.run.runRecord.domain.RunRecordEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 运行记事应用数据类
 *
 * @author 
 * @created 2017-06-05 15:28:10
 * @lastModified 
 * @history
 *
 */
@Repository("runRecordDao")
public class RunRecordDaoImpl extends AncestorDao<RunRecordEntity> implements RunRecordDao{
	private static boolean flag = true;
	@Override
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	@Override
	public String getNameSpace() {
		String namespace = "";
		if(flag){
			namespace = "com.aptech.business.runRecord";
		}else{
			namespace = "com.aptech.business.runRecordWind";
		}
		return namespace;
	}
}
