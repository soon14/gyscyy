package com.aptech.business.safeManage.educationTrain.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.safeManage.educationTrain.domain.EducationTrainEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 教育培训应用数据类
 *
 * @author 
 * @created 2018-03-31 12:43:38
 * @lastModified 
 * @history
 *
 */
@Repository("educationTrainDao")
public class EducationTrainDaoImpl extends AncestorDao<EducationTrainEntity> implements EducationTrainDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.educationTrain";
	}
}
