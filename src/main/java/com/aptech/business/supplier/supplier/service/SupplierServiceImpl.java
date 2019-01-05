package com.aptech.business.supplier.supplier.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.supplier.supplier.dao.SupplierDao;
import com.aptech.business.supplier.supplier.domain.SupplierEntity;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.web.ComboboxVO;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 供应商管理应用管理服务实现类
 *
 * @author 
 * @created 2017-11-02 10:30:36
 * @lastModified 
 * @history
 *
 */
@Service("supplierService")
@Transactional
public class SupplierServiceImpl extends AbstractBaseEntityOperation<SupplierEntity> implements SupplierService {
	
	@Autowired
	private SupplierDao supplierDao;
	
	@Override
	public IBaseEntityOperation<SupplierEntity> getDao() {
		return supplierDao;
	}

	@Override
	public ComboboxVO getSupplierType() {
		Map<String, SysDictionaryVO> supplierTypeMap  =  DictionaryUtil.getDictionaries("SUPPLIER_TYPE");
		ComboboxVO configStatusVO = new ComboboxVO();
		for(String key : supplierTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = supplierTypeMap.get(key);
			configStatusVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		return configStatusVO;
	}

	@Override
	public ComboboxVO getSexType() {
		Map<String, SysDictionaryVO> sexTypeMap  =  DictionaryUtil.getDictionaries("SEX_TYPE");
		ComboboxVO sexTypesVO = new ComboboxVO();
		for(String key : sexTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = sexTypeMap.get(key);
			sexTypesVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		return sexTypesVO;
	}
}