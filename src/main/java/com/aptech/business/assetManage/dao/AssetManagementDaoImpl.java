package com.aptech.business.assetManage.dao;
 
import org.springframework.stereotype.Repository;

import com.aptech.business.assetManage.domain.AssetManagementEntity;
import com.aptech.framework.orm.AncestorDao;

@Repository("assetManagementDao")
public class AssetManagementDaoImpl extends AncestorDao<AssetManagementEntity> implements AssetManagementDao {
    @Override
	public String getNameSpace() {
		return "com.aptech.business.assetManage.AssetManagement";
	}
    
}