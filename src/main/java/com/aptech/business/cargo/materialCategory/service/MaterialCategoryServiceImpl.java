package com.aptech.business.cargo.materialCategory.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.cargo.materialCategory.dao.MaterialCategoryDao;
import com.aptech.business.cargo.materialCategory.domain.MaterialCategoryEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.util.DateFormatUtil;

/**
 * 
 * 物资类别应用管理服务实现类
 *
 * @author 
 * @created 2017-07-14 11:59:28
 * @lastModified 
 * @history
 *
 */
@Service("materialCategoryService")
@Transactional
public class MaterialCategoryServiceImpl extends AbstractBaseEntityOperation<MaterialCategoryEntity> implements MaterialCategoryService {
	
	@Autowired
	private MaterialCategoryDao materialCategoryDao;
	
	@Override
	public IBaseEntityOperation<MaterialCategoryEntity> getDao() {
		return materialCategoryDao;
	}
	
	public String generateMaterialCode(String prefix,int sequenceLength){
		StringBuffer sbf = new StringBuffer();
		sbf.append(prefix);
		DateFormatUtil sdf = DateFormatUtil.getInstance("yyyyMMdd");
		String riqi = sdf.format(new Date());
		sbf.append(riqi);
		String tempCodeString = this.generateSequenceNum(sequenceLength);
		sbf.append(tempCodeString);
		String GenerateCode = sbf.toString();
		return GenerateCode;
	}
	
	/**
	 * 
	 * 根据物资类别表中最大编码生成物资类别序列码
	 * 
	 * @param @param sequenceLength
	 * @param @return
	 * @return String
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月4日 下午1:23:07
	 * @lastModified
	 */
	private String generateSequenceNum(int sequenceLength){
		String tempCode = "";
		List<Object> List_materialCategoryEntity = this.findByCondition("findMaxCode", null, null);
		if(null != List_materialCategoryEntity && !List_materialCategoryEntity.isEmpty()){
			MaterialCategoryEntity materialCategoryEntity =  (MaterialCategoryEntity)List_materialCategoryEntity.get(0);
			String maxCode = materialCategoryEntity.getCode();
			String tempCodeString = maxCode.substring(maxCode.length()-sequenceLength, maxCode.length());
			Long sequence = Long.parseLong(tempCodeString)+1;
			tempCode = String.format("%0" + sequenceLength + "d", sequence);
		}else{
			tempCode = String.format("%0" + sequenceLength + "d", 1);
		}
		return tempCode;
	}
}