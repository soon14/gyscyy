package com.aptech.business.annualProductionCapacity.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.annualProductionCapacity.dao.AnnualProductionCapacityDao;
import com.aptech.business.annualProductionCapacity.domain.AnnualProductionCapacityEntity;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.util.ReflectionUtil;

/**
 * 
 * 年度生产量计划应用管理服务实现类
 *
 * @author 
 * @created 2018-09-17 18:30:24
 * @lastModified 
 * @history
 *
 */
@Service("annualProductionCapacityService")
@Transactional
public class AnnualProductionCapacityServiceImpl extends AbstractBaseEntityOperation<AnnualProductionCapacityEntity> implements AnnualProductionCapacityService {
	
	@Autowired
	private AnnualProductionCapacityDao annualProductionCapacityDao;
	@Autowired
	private SysUnitService sysUnitService;
	
	@Override
	public IBaseEntityOperation<AnnualProductionCapacityEntity> getDao() {
		return annualProductionCapacityDao;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if(page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		page.setOrders(OrmUtil.changeMapToOrders(params));
   		page.addOrder(Sort.desc("C_ID"));
		String type = params.get("type").toString();
		List<Map<Object, Object>> searchConditions = (List<Map<Object, Object>>) params.get("conditions");
		String farmId = null;
		for(Map<Object, Object> map : searchConditions){
			if(map.get("field").toString().equals("ac.C_FARM_ID")){
				farmId = map.get("value").toString();
			}
		}
		List<Condition> conditions = new ArrayList<Condition>();
		List<AnnualProductionCapacityEntity>  list =null;
		List<AnnualProductionCapacityEntity> apcEntityList = new ArrayList<AnnualProductionCapacityEntity>();
		AnnualProductionCapacityEntity apcEntity = null;
		if(type.equals("3")){
			//对应年度生产指标计划
			conditions=OrmUtil.changeMapToCondition(params);
			//查找出固定统计表头信息
			List<Condition> conditionsUnit = new ArrayList<Condition>();
			if(farmId!=null){
				conditionsUnit.add(new Condition("C_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, farmId));
			}
			conditionsUnit.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
			conditionsUnit.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
			conditionsUnit.add(new Condition("C_ORGANIZATION", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
	  		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditionsUnit, null);
	  		for(SysUnitEntity unitEntity : unitList){
	  			Map<String, SysDictionaryVO> purchaseModeIdMap = DictionaryUtil.getDictionaries("ANNUALQUOTATYPE");
	  			Map<String, SysDictionaryVO> quotaTypeIdMap = DictionaryUtil.getDictionaries("QUOTATYPE");
	  			int count=0;
	  			for(String key : purchaseModeIdMap.keySet()){
	  				SysDictionaryVO planTypeVO = purchaseModeIdMap.get(key);
	  				int countQuota = 0;
	  				for(String quotaKey : quotaTypeIdMap.keySet()){
	  					apcEntity = new AnnualProductionCapacityEntity();
	  					SysDictionaryVO quotaKeyVO = quotaTypeIdMap.get(quotaKey);
	  					apcEntity.setFarmId(unitEntity.getId().toString());
	  					apcEntity.setFarmName(unitEntity.getName());
	  					apcEntity.setQuotaId(planTypeVO.getCode());
	  					apcEntity.setQuotaName(planTypeVO.getName());
	  					apcEntity.setPlanCompareId(quotaKeyVO.getCode());
	  					apcEntity.setPlanCompareName(quotaKeyVO.getName());
	  					//1月
	  					apcEntity.setJan("0.00");
						//2月
	  					apcEntity.setFeb("0.00");
						//3月
	  					apcEntity.setMar("0.00");
						//4月
	  					apcEntity.setApr("0.00");
						//5月
	  					apcEntity.setMay("0.00");
						//6月
	  					apcEntity.setJun("0.00");
						//7月
	  					apcEntity.setJul("0.00");
						//8月
	  					apcEntity.setAug("0.00");
						//9月
	  					apcEntity.setSep("0.00");
						//10月
	  					apcEntity.setOct("0.00");
						//11月
	  					apcEntity.setNov("0.00");
						//12月
	  					apcEntity.setDec("0.00");
	  					apcEntity.setTotal("0.00");
	  					if(count==0){
	  						apcEntity.setTdHide("show");
	  					}else{
	  						apcEntity.setTdHide("hide");
	  					}
	  					if(countQuota==0){
	  						apcEntity.setTdHideQuota("show");
	  					}else{
	  						apcEntity.setTdHideQuota("hide");
	  					}
	  					apcEntityList.add(apcEntity);
	  					count++;
	  					countQuota++;
	  				}
	  			}
	  		}
			//取数据库中数据
			list =this.findByCondition("findQuotaByCondition", conditions, null);
			//固定表头与数据库中数据对比
			if(list.size()!=0){
				for(AnnualProductionCapacityEntity gdEntity : apcEntityList){
					for(AnnualProductionCapacityEntity dtEntity : list){
						if(gdEntity.getFarmId().equals(dtEntity.getFarmId())&&gdEntity.getQuotaId().equals(dtEntity.getQuotaId())
								&&gdEntity.getPlanCompareId().equals(dtEntity.getPlanCompareId())){
							//赋值表id
							gdEntity.setId(dtEntity.getId());
							//1月
							gdEntity.setJan((dtEntity.getJan()!=null)?dtEntity.getJan():"0.00");
							//2月
							gdEntity.setFeb((dtEntity.getFeb()!=null)?dtEntity.getFeb():"0.00");
							//3月
							gdEntity.setMar((dtEntity.getFeb()!=null)?dtEntity.getMar():"0.00");
							//4月
							gdEntity.setApr((dtEntity.getApr()!=null)?dtEntity.getApr():"0.00");
							//5月
							gdEntity.setMay((dtEntity.getMay()!=null)?dtEntity.getMay():"0.00");
							//6月
							gdEntity.setJun((dtEntity.getJun()!=null)?dtEntity.getJun():"0.00");
							//7月
							gdEntity.setJul((dtEntity.getJul()!=null)?dtEntity.getJul():"0.00");
							//8月
							gdEntity.setAug((dtEntity.getAug()!=null)?dtEntity.getAug():"0.00");
							//9月
							gdEntity.setSep((dtEntity.getSep()!=null)?dtEntity.getSep():"0.00");
							//10月
							gdEntity.setOct((dtEntity.getOct()!=null)?dtEntity.getOct():"0.00");
							//11月
							gdEntity.setNov((dtEntity.getNov()!=null)?dtEntity.getNov():"0.00");
							//12月
							gdEntity.setDec((dtEntity.getDec()!=null)?dtEntity.getDec():"0.00");
							//合计
							gdEntity.setTotal(dtEntity.getTotal());
							
						}
					}
				}
			}
			
		}else{
			DecimalFormat df = new DecimalFormat("#0.00");
			//对应年度生产量计划
			conditions=OrmUtil.changeMapToCondition(params);
			//查找出固定统计表头信息
			List<Condition> conditionsUnit = new ArrayList<Condition>();
			if(farmId!=null){
				conditionsUnit.add(new Condition("C_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, farmId));
			}
			conditionsUnit.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
			conditionsUnit.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
			conditionsUnit.add(new Condition("C_ORGANIZATION = 1 "));
	  		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditionsUnit, null);
	  		SysUnitEntity sysUnitEntity = new SysUnitEntity();
	  		sysUnitEntity.setId(160l);
	  		sysUnitEntity.setName("投资运营事业部");
	  		unitList.add(sysUnitEntity);
	  		for(SysUnitEntity unitEntity : unitList){
	  			Map<String, SysDictionaryVO> purchaseModeIdMap = DictionaryUtil.getDictionaries("ANNUAL_PRODUCTION_TYPE");
	  			Map<String, SysDictionaryVO> quotaTypeIdMap = DictionaryUtil.getDictionaries("QUOTATYPE");
	  			int count=0;
	  			for(String key : purchaseModeIdMap.keySet()){
	  				SysDictionaryVO planTypeVO = purchaseModeIdMap.get(key);
	  				int countQuota = 0;
	  				for(String quotaKey : quotaTypeIdMap.keySet()){
	  					apcEntity = new AnnualProductionCapacityEntity();
	  					SysDictionaryVO quotaKeyVO = quotaTypeIdMap.get(quotaKey);
	  					apcEntity.setFarmId(unitEntity.getId().toString());
	  					apcEntity.setFarmName(unitEntity.getName());
	  					apcEntity.setQuotaId(planTypeVO.getCode());
	  					apcEntity.setQuotaName(planTypeVO.getName());
	  					apcEntity.setPlanCompareId(quotaKeyVO.getCode());
	  					apcEntity.setPlanCompareName(quotaKeyVO.getName());
	  					//1月
	  					apcEntity.setJan("0.00");
						//2月
	  					apcEntity.setFeb("0.00");
						//3月
	  					apcEntity.setMar("0.00");
						//4月
	  					apcEntity.setApr("0.00");
						//5月
	  					apcEntity.setMay("0.00");
						//6月
	  					apcEntity.setJun("0.00");
						//7月
	  					apcEntity.setJul("0.00");
						//8月
	  					apcEntity.setAug("0.00");
						//9月
	  					apcEntity.setSep("0.00");
						//10月
	  					apcEntity.setOct("0.00");
						//11月
	  					apcEntity.setNov("0.00");
						//12月
	  					apcEntity.setDec("0.00");
	  					apcEntity.setTotal("0.00");
	  					if(count==0){
	  						apcEntity.setTdHide("show");
	  					}else{
	  						apcEntity.setTdHide("hide");
	  					}
	  					if(countQuota==0){
	  						apcEntity.setTdHideQuota("show");
	  					}else{
	  						apcEntity.setTdHideQuota("hide");
	  					}
	  					apcEntityList.add(apcEntity);
	  					count++;
	  					countQuota++;
	  				}
	  			}
	  		}
			//取数据库中数据
	  		list=super.findByCondition(params, null);
			//固定表头与数据库中数据对比
			if(list.size()!=0){
				for(AnnualProductionCapacityEntity gdEntity : apcEntityList){
					for(AnnualProductionCapacityEntity dtEntity : list){
						if(gdEntity.getFarmId().equals(dtEntity.getFarmId())&&gdEntity.getQuotaId().equals(dtEntity.getQuotaId())
								&&gdEntity.getPlanCompareId().equals(dtEntity.getPlanCompareId())){
							//赋值表id
							gdEntity.setId(dtEntity.getId());
							//1月
							gdEntity.setJan((dtEntity.getJan()!=null)?dtEntity.getJan():"0.00");
							//2月
							gdEntity.setFeb((dtEntity.getFeb()!=null)?dtEntity.getFeb():"0.00");
							//3月
							gdEntity.setMar((dtEntity.getFeb()!=null)?dtEntity.getMar():"0.00");
							//4月
							gdEntity.setApr((dtEntity.getApr()!=null)?dtEntity.getApr():"0.00");
							//5月
							gdEntity.setMay((dtEntity.getMay()!=null)?dtEntity.getMay():"0.00");
							//6月
							gdEntity.setJun((dtEntity.getJun()!=null)?dtEntity.getJun():"0.00");
							//7月
							gdEntity.setJul((dtEntity.getJul()!=null)?dtEntity.getJul():"0.00");
							//8月
							gdEntity.setAug((dtEntity.getAug()!=null)?dtEntity.getAug():"0.00");
							//9月
							gdEntity.setSep((dtEntity.getSep()!=null)?dtEntity.getSep():"0.00");
							//10月
							gdEntity.setOct((dtEntity.getOct()!=null)?dtEntity.getOct():"0.00");
							//11月
							gdEntity.setNov((dtEntity.getNov()!=null)?dtEntity.getNov():"0.00");
							//12月
							gdEntity.setDec((dtEntity.getDec()!=null)?dtEntity.getDec():"0.00");
							//合计
							gdEntity.setTotal(dtEntity.getTotal());
						}
					}
				}
			}
			
			String[] monthNames = {"jan", "feb", "mar","apr","may", "jun", "jul","aug","sep", "oct", "nov","dec","total"};
		    for(int i = 0;i<monthNames.length;i++){
		    	double aJan = 0;
				double bJan = 0;
				double cJan = 0;
				double dJan = 0;
				double eJan = 0;
				double fJan = 0;
				double gJan = 0;
				double hJan = 0;
		    	for(AnnualProductionCapacityEntity gdEntity : apcEntityList){
		    		Double monthPlanValue = Double.parseDouble((String) ReflectionUtil.getFieldValue(gdEntity, monthNames[i]));
					if(gdEntity.getPlanCompareId().equals("1")&&gdEntity.getQuotaId().equals("1")&&!gdEntity.getFarmId().equals("160")){
						aJan+= monthPlanValue;
					}else if(gdEntity.getPlanCompareId().equals("2")&&gdEntity.getQuotaId().equals("1")&&!gdEntity.getFarmId().equals("160")){
						bJan+= monthPlanValue;
					}
					if(gdEntity.getPlanCompareId().equals("1")&&gdEntity.getQuotaId().equals("2")&&!gdEntity.getFarmId().equals("160")){
						cJan+= monthPlanValue;
					}else if(gdEntity.getPlanCompareId().equals("2")&&gdEntity.getQuotaId().equals("2")&&!gdEntity.getFarmId().equals("160")){
						dJan+= monthPlanValue;
					}
					if(gdEntity.getPlanCompareId().equals("1")&&gdEntity.getQuotaId().equals("3")&&!gdEntity.getFarmId().equals("160")){
						eJan+= monthPlanValue;
					}else if(gdEntity.getPlanCompareId().equals("2")&&gdEntity.getQuotaId().equals("3")&&!gdEntity.getFarmId().equals("160")){
						fJan+= monthPlanValue;
					}
					if(gdEntity.getPlanCompareId().equals("1")&&gdEntity.getQuotaId().equals("4")&&!gdEntity.getFarmId().equals("160")){
						gJan+= monthPlanValue;
					}else if(gdEntity.getPlanCompareId().equals("2")&&gdEntity.getQuotaId().equals("4")&&!gdEntity.getFarmId().equals("160")){
						hJan+= monthPlanValue;
					}
					
					
					
					if(gdEntity.getPlanCompareId().equals("1")&&gdEntity.getQuotaId().equals("1")&&gdEntity.getFarmId().equals("160")){
						ReflectionUtil.setFieldValue(gdEntity, monthNames[i],df.format(aJan));
					}else if(gdEntity.getPlanCompareId().equals("2")&&gdEntity.getQuotaId().equals("1")&&gdEntity.getFarmId().equals("160")){
						ReflectionUtil.setFieldValue(gdEntity, monthNames[i], df.format(bJan));
					}
					if(gdEntity.getPlanCompareId().equals("1")&&gdEntity.getQuotaId().equals("2")&&gdEntity.getFarmId().equals("160")){
						ReflectionUtil.setFieldValue(gdEntity, monthNames[i], df.format(cJan));
					}else if(gdEntity.getPlanCompareId().equals("2")&&gdEntity.getQuotaId().equals("2")&&gdEntity.getFarmId().equals("160")){
						ReflectionUtil.setFieldValue(gdEntity, monthNames[i], df.format(dJan));
					}
					if(gdEntity.getPlanCompareId().equals("1")&&gdEntity.getQuotaId().equals("3")&&gdEntity.getFarmId().equals("160")){
						ReflectionUtil.setFieldValue(gdEntity, monthNames[i], df.format(eJan));
					}else if(gdEntity.getPlanCompareId().equals("2")&&gdEntity.getQuotaId().equals("3")&&gdEntity.getFarmId().equals("160")){
						ReflectionUtil.setFieldValue(gdEntity, monthNames[i], df.format(fJan));
					}
					if(gdEntity.getPlanCompareId().equals("1")&&gdEntity.getQuotaId().equals("4")&&gdEntity.getFarmId().equals("160")){
						ReflectionUtil.setFieldValue(gdEntity, monthNames[i], df.format(gJan));
					}else if(gdEntity.getPlanCompareId().equals("2")&&gdEntity.getQuotaId().equals("4")&&gdEntity.getFarmId().equals("160")){
						ReflectionUtil.setFieldValue(gdEntity, monthNames[i], df.format(hJan));
					}
				}
		    }
		    	
		    }
				
		return (List<O>) apcEntityList;
	}

	@Override
	public void deleteByType(AnnualProductionCapacityEntity entity) {
		annualProductionCapacityDao.deleteByType(entity);
		
	}
	
}