package com.aptech.business.defectManage.defectStatistics.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.defectManage.defectStatistics.domain.DefectStatisticsEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 缺陷统计应用数据类
 *
 * @author 
 * @created 2017-06-09 09:12:26
 * @lastModified 
 * @history
 *
 */
@Repository("defectStatisticsDao")
public class DefectStatisticsDaoImpl extends AncestorDao<DefectStatisticsEntity> implements DefectStatisticsDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.defectManage.defectStatistics";
	}
}
