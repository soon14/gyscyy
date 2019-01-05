package com.aptech.business.assetManage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.assetManage.dao.AssetManagementDao;
import com.aptech.business.assetManage.domain.AssetManagementEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation; 

@Service("assetManagementService")
@Transactional
public class AssetManagementServiceImpl extends AbstractBaseEntityOperation<AssetManagementEntity> implements AssetManagementService {

    @Autowired
    private AssetManagementDao assetManagementDao;

   	@Override
	public IBaseEntityOperation<AssetManagementEntity> getDao() {
		return assetManagementDao;
	}
}