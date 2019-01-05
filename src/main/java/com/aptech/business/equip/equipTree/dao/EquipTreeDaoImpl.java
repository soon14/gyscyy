package com.aptech.business.equip.equipTree.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.equip.equipTree.domain.EquipTreeEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 设备节点表应用数据类
 *
 * @author 
 * @created 2017-06-26 15:26:43
 * @lastModified 
 * @history
 *
 */
@Repository("equiptreeDao")
public class EquipTreeDaoImpl extends AncestorDao<EquipTreeEntity> implements EquipTreeDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.equiptree";
	}
}
