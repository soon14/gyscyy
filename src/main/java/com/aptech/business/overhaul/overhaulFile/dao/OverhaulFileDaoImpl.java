package com.aptech.business.overhaul.overhaulFile.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.overhaul.overhaulFile.domain.OverhaulFileEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 检修文件包应用数据类
 *
 * @author 
 * @created 2017-08-04 14:04:07
 * @lastModified 
 * @history
 *
 */
@Repository("overhaulFileDao")
public class OverhaulFileDaoImpl extends AncestorDao<OverhaulFileEntity> implements OverhaulFileDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.overhaulFile";
	}
}
