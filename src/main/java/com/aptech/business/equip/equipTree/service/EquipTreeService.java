package com.aptech.business.equip.equipTree.service;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;

import com.aptech.business.cargo.materialCategory.domain.MaterialCategoryEntity;
import com.aptech.business.equip.equipTree.domain.EquipTreeEntity;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 设备节点表应用管理服务接口
 *
 * @author 
 * @created 2017-06-26 15:26:43
 * @lastModified 
 * @history
 *
 */
public interface EquipTreeService  extends IBaseEntityOperation<EquipTreeEntity> {

	/**
	 * @Description:   是否删除
	 * @author         wangcc 
	 * @Date           2017年7月31日 下午1:07:52 
	 * @throws         Exception
	 */
	ResultObj isdel(Long id);
	/**
	 * @Description:   修改节点(设备)
	 * @author         wangcc 
	 * @Date           2017年8月7日 下午4:07:00 
	 * @throws         Exception
	 */
	void updateEntityWithEquip(EquipTreeEntity equipTreeEntity);
	/**
	 * @Description:   新增物理设备
	 * @author         wangcc 
	 * @Date           2017年8月7日 下午4:07:00 
	 * @throws         Exception
	 */
	void addPhysicsEntity(EquipTreeEntity equipTreeEntity);
	/**
	 * @Description:   是否可以新增设备
	 * @author         wangcc 
	 * @Date           2017年8月13日 下午23:21:00 
	 * @throws         Exception
	 */
	ResultObj isAdd(Map<String, Object> params,HttpServletRequest request);
	/**
	 * @Description:   是否可以新增设备
	 * @author         wangcc 
	 * @Date           2017年8月13日 下午23:21:00 
	 * @throws         Exception
	 */
	ResultObj isEdit(Map<String, Object> params,Long id,HttpServletRequest request);
	/**
	 * @Description:   获取物资类别
	 * @author         霍新宇 
	 * @Date           2018年4月13日 下午12:21:00 
	 * @throws         Exception
	 */
	List<MaterialCategoryEntity> searchMaterialCategory(String equipIdString,Map<String, Object> params);
	/**
	 * @Description:   获取设备树(递归调用)
	 * @author         王春面 
	 * @Date           2018年6月29日 下午12:21:00 
	 * @throws         Exception
	 */
	List<EquipTreeEntity>findChildTreeById(Long id);
}