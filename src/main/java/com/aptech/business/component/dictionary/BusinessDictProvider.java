package com.aptech.business.component.dictionary;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aptech.common.system.dictionary.service.SysDictionaryService;
import com.aptech.common.system.dictionary.util.SysDictionaryCategoryProvider;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;

@Component
public class BusinessDictProvider implements SysDictionaryCategoryProvider {
	
	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	@Override
	public List<SysDictionaryVO> getDBDictionaryCategories() {
		List<SysDictionaryVO> dictionaryVOs = BusinessDictCategoryEnum.getDBCategoryList(null);
		//建立虚拟根节点
		SysDictionaryVO rootDictionaryVO = new SysDictionaryVO();
		rootDictionaryVO.setId(new Long(-1));
		rootDictionaryVO.setCode("business");
		rootDictionaryVO.setName("业务字典");
		rootDictionaryVO.setOrder(1);
		rootDictionaryVO.setParentId(new Long(-3));
		rootDictionaryVO.setCategoryCode("root");
		dictionaryVOs.add(rootDictionaryVO);
		return dictionaryVOs;
	}

	@Override
	public SysDictionaryVO getDictionaryCategoryById(Integer id) {
		return BusinessDictCategoryEnum.getCategoryById(id);
	}

	@Override
	public List<SysDictionaryVO> getEnumDictionaryCategories() {
		List<SysDictionaryVO> dictionaryVOs = BusinessDictCategoryEnum.getEnumCategoryList(null);
		return dictionaryVOs;
	}

	@Override
	public Map<String, SysDictionaryVO> getDidctionaryItemsByCategoryCode(
			String categoryCode) {
		return Enum.valueOf(BusinessDictCategoryEnum.class, categoryCode).getDictItems();
	}
}
