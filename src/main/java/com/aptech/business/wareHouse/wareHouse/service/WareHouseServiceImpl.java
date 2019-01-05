package com.aptech.business.wareHouse.wareHouse.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.dutyOrder.domain.DutyOrderEntity;
import com.aptech.business.wareHouse.wareHouse.dao.WareHouseDao;
import com.aptech.business.wareHouse.wareHouse.domain.WareHouseEntity;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;

/**
 * 
 * 仓库管理应用管理服务实现类
 *
 * @author 
 * @created 2017-11-03 16:51:10
 * @lastModified 
 * @history
 *
 */
@Service("wareHouseService")
@Transactional
public class WareHouseServiceImpl extends AbstractBaseEntityOperation<WareHouseEntity> implements WareHouseService {
	
	@Autowired
	private WareHouseDao wareHouseDao;
	@Autowired
	private SysUserService sysUserService;
	@Override
	public IBaseEntityOperation<WareHouseEntity> getDao() {
		return wareHouseDao;
	}

	
//	@Override
//	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
//		if(page == null) {
//			page = new Page<O>();
//			page.setPageSize(Integer.MAX_VALUE);
//		}
//   		List<Sort> orderList=new ArrayList<Sort>();//page.getOrders();
//   		
//   		orderList.addAll(OrmUtil.changeMapToOrders(params));
//		//默认计划时间 倒叙
//		page.setOrders(orderList);
//   		
//		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
//		List<WareHouseEntity> resultList = (List<WareHouseEntity>) super.findByCondition(conditions, page);
//		
//		if(resultList!=null && resultList.size()>0){
//			for(WareHouseEntity wareHouseEntity:resultList){
//				String[] ids = wareHouseEntity.getStoreKeeper().toString().split(",");
//				conditions.clear();
//				conditions.add(new Condition("a.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.IN,ids));
//				List<SysUserEntity> userList = sysUserService.findByCondition(conditions, null);
//				String userNames = "";
//				for(int i=0;i<userList.size();i++){
//					if(i==userList.size()-1){
//						userNames+=userList.get(i).getName();
//					}else{
//						userNames+=userList.get(i).getName()+",";
//					}
//				}
//				wareHouseEntity.setStoreKeeper(userNames);
//			}
//		}
//		
//		return (List<O>) resultList;
//	}
	
	@Override
	public ComboboxVO getWareHouseType() {
		Map<String, SysDictionaryVO> wareHouseTypeMap  =  DictionaryUtil.getDictionaries("WAREHOUSE_TYPE");
		ComboboxVO wareHouseTypeVO = new ComboboxVO();
		for(String key : wareHouseTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = wareHouseTypeMap.get(key);
			wareHouseTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		return wareHouseTypeVO;
	}

	@Override
	public ComboboxVO getStoreKeeper() {
		List<SysUserEntity> sysUserEntities = sysUserService.findAll();
		ComboboxVO comboStoreKeeperVO = new ComboboxVO();
		for(SysUserEntity sysUserEntity:sysUserEntities){
			comboStoreKeeperVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
		}
		return comboStoreKeeperVO;
	}

	@Override
	public ComboboxVO getStatus() {
		Map<String, SysDictionaryVO> wareHouseStatusMap  =  DictionaryUtil.getDictionaries("WAREHOUSE_STATUS");
		ComboboxVO wareHouseStatusVO = new ComboboxVO();
		for(String key : wareHouseStatusMap.keySet()){
			SysDictionaryVO sysDictionaryVO = wareHouseStatusMap.get(key);
			wareHouseStatusVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		return wareHouseStatusVO;
	}

	@Override
	public ComboboxVO getParentWareHouse() {
		Map<String, SysDictionaryVO> parentWareHouseMap  =  DictionaryUtil.getDictionaries("PARENT_WAREHOUSE");
		ComboboxVO parentWareHouseVO = new ComboboxVO();
		for(String key : parentWareHouseMap.keySet()){
			SysDictionaryVO sysDictionaryVO = parentWareHouseMap.get(key);
			parentWareHouseVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		return parentWareHouseVO;
	}


	@Override
	public int findUnitTotal(Long unitId) {
		int allIds=wareHouseDao.findUnitTotal(unitId);
		return allIds;
	}
}