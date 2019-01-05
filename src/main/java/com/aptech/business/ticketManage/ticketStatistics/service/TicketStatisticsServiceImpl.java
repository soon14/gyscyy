package com.aptech.business.ticketManage.ticketStatistics.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.IdentifyEnum;
import com.aptech.business.component.dictionary.OperationStatusEnum;
import com.aptech.business.component.dictionary.WorkFireStatusEnum;
import com.aptech.business.component.dictionary.WorkStatusEnum;
import com.aptech.business.component.dictionary.WorkTypeEnum;
import com.aptech.business.ticketManage.operationTicket.domain.OperationTicketEntity;
import com.aptech.business.ticketManage.ticketStatistics.dao.TicketStatisticsDao;
import com.aptech.business.ticketManage.ticketStatistics.domain.TicketStatisticsEnum;
import com.aptech.business.ticketManage.ticketStatistics.domain.TicketStatisticsItem;
import com.aptech.business.ticketManage.ticketStatistics.domain.TicketStatisticsVO;
import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.domain.UnitLevelEnum;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;


/**
 * 两票统计服务实现类
 * @author Administrator
 *
 */
@Service("ticketStatisticsService")
@Transactional
public class TicketStatisticsServiceImpl extends AbstractBaseEntityOperation<TicketStatisticsVO> implements TicketStatisticsService {
	//日志对象
//	private static Log logger = Log.getInstance(TicketStatisticsServiceImpl.class);
	
	@Autowired
	private TicketStatisticsDao ticketStatisticsDao;

	@Autowired
	private SysUnitService sysUnitService;
	

	@Override
	public IBaseEntityOperation<TicketStatisticsVO> getDao() {
		return ticketStatisticsDao;
	}
	@Override
	public List<Map<String, String>>  getStatisticDataList(String unitId, String searchYear) {
		DecimalFormat df = new DecimalFormat("0.00");
		List<Condition> conditions = new ArrayList<Condition>();
		List<Long> unitIdList = new ArrayList<Long>();
		if (unitId == null || "".equals(unitId)) {
			unitId = "0";
		}
		if ("0".equals(unitId)) {
			//查询分公司
			conditions.clear();
			conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
			conditions.add(new Condition("C_LEVEL", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(UnitLevelEnum.COMPANY.getCode())));
			conditions.add(new Condition("C_PARENT_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ, unitId));
			List<SysUnitEntity> companyEntityList = this.sysUnitService.findByCondition(conditions, null);
			List<Long> companyIdList = new ArrayList<Long>();
			if (companyEntityList !=null && companyEntityList.size() >0  && companyEntityList.get(0) != null) {
				for (SysUnitEntity entity : companyEntityList) {
					companyIdList.add(entity.getId());
				}
			}
			//查询分公司下属风场
			conditions.clear();
			conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
			conditions.add(new Condition("C_LEVEL", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(UnitLevelEnum.FARM.getCode())));
			conditions.add(new Condition("C_PARENT_ID", FieldTypeEnum.INT, MatchTypeEnum.IN, companyIdList.toArray()));
			List<SysUnitEntity> farmEntityList = this.sysUnitService.findByCondition(conditions, null);
			if (farmEntityList !=null && farmEntityList.size() >0  && farmEntityList.get(0) != null) {
				for (SysUnitEntity entity : farmEntityList) {
					unitIdList.add(entity.getId());
				}
			}
		} else {
			SysUnitEntity unitEntity = this.sysUnitService.findById(new Long(unitId));
			if (UnitLevelEnum.COMPANY.getCode().equals(unitEntity.getLevel())){
				conditions.clear();
				conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
				conditions.add(new Condition("C_LEVEL", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(UnitLevelEnum.FARM.getCode())));
				conditions.add(new Condition("C_PARENT_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ, unitEntity.getId()));
				List<SysUnitEntity> unitEntityList = this.sysUnitService.findByCondition(conditions, null);
				if (unitEntityList !=null && unitEntityList.size() >0  && unitEntityList.get(0) != null) {
					for (SysUnitEntity entity : unitEntityList) {
						unitIdList.add(entity.getId());
					}
				}
			} else {
				unitIdList.add(new Long(unitId));
			}
		}
		Calendar searchCal = Calendar.getInstance();
		if (searchYear !=null && !"".equals(searchYear)) {
//			searchCal.set(Calendar.YEAR, Integer.valueOf(searchYear));
			searchCal.set(Integer.valueOf(searchYear), Calendar.JANUARY, 1, 0, 0, 0);
		} else {
			searchCal.set(searchCal.get(Calendar.YEAR), Calendar.JANUARY, 1, 0, 0, 0);
		}
		
		//年开始时间
		Calendar yearStartCal =  Calendar.getInstance();
		yearStartCal.setTime(searchCal.getTime());
		//年结束时间
		Calendar yearEndCal =  Calendar.getInstance();
		yearEndCal.setTime(searchCal.getTime());
		yearEndCal.add(Calendar.YEAR, 1);
		yearEndCal.add(Calendar.SECOND, -1);
		System.out.println(yearStartCal.getTime());
		System.out.println(yearEndCal.getTime());
		conditions.clear();
		if (unitIdList != null && unitIdList.size() >0 ) {
			conditions.add(new Condition("C_UNIT_NAME_ID", FieldTypeEnum.STRING, MatchTypeEnum.IN, unitIdList.toArray()));
		}
		conditions.add(new Condition("C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, yearStartCal.getTime()));
		conditions.add(new Condition("C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LE, yearEndCal.getTime()));
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.INT, MatchTypeEnum.EQ, DataStatusEnum.NORMAL.ordinal()));
		
		//conditions.add(new Condition("C_WORK_STATUS", FieldTypeEnum.INT, MatchTypeEnum.EQ, WorkStatusEnum.END.getId()));
		String [] arr = {WorkStatusEnum.END.getCode(),WorkFireStatusEnum.END.getCode()};
		conditions.add(new Condition("C_WORK_STATUS", FieldTypeEnum.INT, MatchTypeEnum.IN,arr ));
		conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.NE, WorkTypeEnum.WORKHELPSAFE.getCode()));
		List<WorkTicketEntity> ticketList = this.findByCondition(conditions, null);
		
		//操作票数据取得
		conditions.clear();
		if (unitIdList != null && unitIdList.size() >0 ) {
			conditions.add(new Condition("C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, unitIdList.toArray()));
		}
		conditions.add(new Condition("C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, yearStartCal.getTime()));
		conditions.add(new Condition("C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LE, yearEndCal.getTime()));
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.INT, MatchTypeEnum.EQ, OperationStatusEnum.RESULTS.getId()));
		
		List<OperationTicketEntity> opeationTicketList = this.findByCondition("findOperationTicketData",  conditions, null);
		
		//按类型保存工作票数据的map
		Map<String, TicketStatisticsItem[]> ticketTypeDataMap= new HashMap<String, TicketStatisticsItem[]>();
		
		for (WorkTypeEnum workTypeEnum: WorkTypeEnum.values()) {
			String workTypeCode = workTypeEnum.getCode();
			TicketStatisticsItem[] tmpTicketList = new TicketStatisticsItem[13];
			int totalCount = 0;
			int totalIdentifyYes = 0;
			int totalIdentifyNo = 0;
			int totalIdentifyResult = 0;
			if (WorkTypeEnum.OPERATION.getCode().equals(workTypeCode)){
				if (opeationTicketList != null && opeationTicketList.size() > 0) {
					for(int i=0; i<12; i++){
						Calendar startCal = Calendar.getInstance();
						startCal.set(searchCal.get(Calendar.YEAR), i, 1, 0, 0, 0);
						
						Calendar endCal = Calendar.getInstance();
						endCal.set(searchCal.get(Calendar.YEAR), i, 1, 0, 0, 0);
						endCal.add(Calendar.MONTH, 1);
						endCal.add(Calendar.SECOND, -1);
						if (ticketTypeDataMap.containsKey(workTypeCode)) {
							tmpTicketList = ticketTypeDataMap.get(workTypeCode);
						} else {
							tmpTicketList = new TicketStatisticsItem[13];
							ticketTypeDataMap.put(workTypeCode, tmpTicketList);
						}
						for (OperationTicketEntity operationTicketEntity : opeationTicketList) {
							TicketStatisticsItem statisticsItem = null;
							if (operationTicketEntity.getEndTime().after(startCal.getTime())&&
									operationTicketEntity.getEndTime().before(endCal.getTime())) {
								if(tmpTicketList[i] == null){
									statisticsItem = new TicketStatisticsItem();
									tmpTicketList[i] = statisticsItem;
								}else{
									statisticsItem = tmpTicketList[i];	
								}
								statisticsItem.setTicketCount(statisticsItem.getTicketCount() + 1);
								totalCount++;
								if(IdentifyEnum.IDENTIFYYES.getCode().equals(operationTicketEntity.getIdentify())){
									statisticsItem.setQualifiedCount(statisticsItem.getQualifiedCount() + 1);
									totalIdentifyYes++;
								}else if(IdentifyEnum.IDENTIFYNO.getCode().equals(operationTicketEntity.getIdentify())){
									statisticsItem.setUnQualifiedCount(statisticsItem.getUnQualifiedCount() + 1);
									totalIdentifyNo++;
								}else{
									statisticsItem.setIdentify(statisticsItem.getIdentify() + 1);
									totalIdentifyResult++;
								}
								
								int tmpCount = statisticsItem.getQualifiedCount() + statisticsItem.getUnQualifiedCount();
								if (tmpCount == 0) {
									statisticsItem.setQualifiedRate("-");
								} else {
									statisticsItem.setQualifiedRate(df.format((double)statisticsItem.getQualifiedCount()/tmpCount*100));
								}
								
							}
						}
					}
					TicketStatisticsItem totalStatisticsItem = new TicketStatisticsItem();
					totalStatisticsItem.setTicketCount(totalCount);
					totalStatisticsItem.setQualifiedCount(totalIdentifyYes);
					totalStatisticsItem.setUnQualifiedCount(totalIdentifyNo);
					totalStatisticsItem.setIdentify(totalIdentifyResult);
					int tmpTotalCount = totalStatisticsItem.getQualifiedCount() + totalStatisticsItem.getUnQualifiedCount();
					if (tmpTotalCount == 0) {
						totalStatisticsItem.setQualifiedRate("-");
					} else {
						totalStatisticsItem.setQualifiedRate(df.format((double)totalStatisticsItem.getQualifiedCount()/tmpTotalCount*100));
					}
					tmpTicketList[12] = totalStatisticsItem;
				} else {
					for(int i=0; i<12; i++){
						if (ticketTypeDataMap.containsKey(workTypeCode)) {
							tmpTicketList = ticketTypeDataMap.get(workTypeCode);
						} else {
							tmpTicketList = new TicketStatisticsItem[13];
							ticketTypeDataMap.put(workTypeCode, tmpTicketList);
						}
						tmpTicketList[i] = new TicketStatisticsItem();
					}
				}
			} else {
				if (ticketList != null && ticketList.size() > 0) {
					for(int i=0; i<12; i++){
						Calendar startCal = Calendar.getInstance();
						startCal.set(searchCal.get(Calendar.YEAR), i, 1, 0, 0, 0);
						
						Calendar endCal = Calendar.getInstance();
						endCal.set(searchCal.get(Calendar.YEAR), i, 1, 0, 0, 0);
						endCal.add(Calendar.MONTH, 1);
						endCal.add(Calendar.SECOND, -1);
						if (ticketTypeDataMap.containsKey(workTypeCode)) {
							tmpTicketList = ticketTypeDataMap.get(workTypeCode);
						} else {
							tmpTicketList = new TicketStatisticsItem[13];
							ticketTypeDataMap.put(workTypeCode, tmpTicketList);
						}
						for (WorkTicketEntity workTicketEntity : ticketList) {
							TicketStatisticsItem statisticsItem = null;
							if (workTypeCode.equals(workTicketEntity.getType().toString())&& 
									workTicketEntity.getEndTime().after(startCal.getTime())&&
									workTicketEntity.getEndTime().before(endCal.getTime())) {
								if(tmpTicketList[i] == null){
									statisticsItem = new TicketStatisticsItem();
									tmpTicketList[i] = statisticsItem;
								}else{
									statisticsItem = tmpTicketList[i];	
								}
								statisticsItem.setTicketCount(statisticsItem.getTicketCount() + 1);
								totalCount++;
								if(IdentifyEnum.IDENTIFYYES.getCode().equals(workTicketEntity.getIdentify())){
									statisticsItem.setQualifiedCount(statisticsItem.getQualifiedCount() + 1);
									totalIdentifyYes++;
								}else if(IdentifyEnum.IDENTIFYNO.getCode().equals(workTicketEntity.getIdentify())){
									statisticsItem.setUnQualifiedCount(statisticsItem.getUnQualifiedCount() + 1);
									totalIdentifyNo++;
								}else{
									statisticsItem.setIdentify(statisticsItem.getIdentify() + 1);
									totalIdentifyResult++;
								}
								
								int tmpCount = statisticsItem.getQualifiedCount() + statisticsItem.getUnQualifiedCount();
								if (tmpCount == 0) {
									statisticsItem.setQualifiedRate("-");
								} else {
									statisticsItem.setQualifiedRate(df.format((double)statisticsItem.getQualifiedCount()/tmpCount*100));
								}
								
							}
						}
					}
					TicketStatisticsItem totalStatisticsItem = new TicketStatisticsItem();
					totalStatisticsItem.setTicketCount(totalCount);
					totalStatisticsItem.setQualifiedCount(totalIdentifyYes);
					totalStatisticsItem.setUnQualifiedCount(totalIdentifyNo);
					totalStatisticsItem.setIdentify(totalIdentifyResult);
					int tmpTotalCount = totalStatisticsItem.getQualifiedCount() + totalStatisticsItem.getUnQualifiedCount();
					if (tmpTotalCount == 0) {
						totalStatisticsItem.setQualifiedRate("-");
					} else {
						totalStatisticsItem.setQualifiedRate(df.format((double)totalStatisticsItem.getQualifiedCount()/tmpTotalCount*100));
					}
					tmpTicketList[12] = totalStatisticsItem;
				} else {
					for(int i=0; i<12; i++){
						if (ticketTypeDataMap.containsKey(workTypeCode)) {
							tmpTicketList = ticketTypeDataMap.get(workTypeCode);
						} else {
							tmpTicketList = new TicketStatisticsItem[13];
							ticketTypeDataMap.put(workTypeCode, tmpTicketList);
						}
						tmpTicketList[i] = new TicketStatisticsItem();
					}
				}
			}
		}
		
		List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>();
		
		//根据票类型取得页面显示数据
		Map<String, String> resultMap = null;
		for(TicketStatisticsEnum workTypeEnum : TicketStatisticsEnum.values()){
			resultMap = new HashMap<String, String>();
			resultMap.put("ticketTypeName", workTypeEnum.getName());
			resultMap.put("ticketType", workTypeEnum.getCode());
			for(int i=0; i<13; i++){
				TicketStatisticsItem[] ticketStatisticsItems = ticketTypeDataMap.get(workTypeEnum.getCode());
				String ticketCount = "0";
				if(ticketStatisticsItems[i] != null){
					ticketCount = String.valueOf(ticketStatisticsItems[i].getTicketCount());
				}
				
				resultMap.put("ticketCount_" + String.valueOf(i + 1), ticketCount);
				
				String qualifiedCount = "0";
				if(ticketStatisticsItems[i] != null){
					qualifiedCount = String.valueOf(ticketStatisticsItems[i].getQualifiedCount());
				}
				resultMap.put("qualifiedCount_" + String.valueOf(i + 1), qualifiedCount);
				
				String unQualifiedCount = "0";
				if(ticketStatisticsItems[i] != null){
					unQualifiedCount = String.valueOf(ticketStatisticsItems[i].getUnQualifiedCount());
				}
				resultMap.put("unQualifiedCount_" + String.valueOf(i + 1), unQualifiedCount);
				
				String qualifiedRate = "-";
				if (ticketStatisticsItems[i] != null ){
					int tmpTotalCount = ticketStatisticsItems[i].getQualifiedCount() + ticketStatisticsItems[i].getUnQualifiedCount();
					if ( tmpTotalCount != 0) {
						qualifiedRate = df.format((double)ticketStatisticsItems[i].getQualifiedCount()/tmpTotalCount*100);
					}
				}
				resultMap.put("qualifiedRate_" + String.valueOf(i + 1), qualifiedRate);
				String identifyString = "0";
				if (ticketStatisticsItems[i] != null ){
					identifyString = String.valueOf(ticketStatisticsItems[i].getIdentify());
				}
				resultMap.put("identify_" + String.valueOf(i + 1), identifyString);
			}
			resultMapList.add(resultMap);
		}
		
		//合计map
		Map<String, String> totalResultMap  = new HashMap<String, String>();
		totalResultMap.put("ticketTypeName", "合计");
		//合计年票数
		int totalYearCount = 0;
		//合计年合格票数
		int totalYearIdentifyYes = 0;
		//合计年不合格票数
		int totalYearIdentifyNo = 0;
		//合计年是否鉴定数
		int totalYearIdentify = 0;
		for(int i=0; i<12; i++){
			//月合计票数
			int totalCount = 0;
			//月合计合格票数
			int totalIdentifyYes = 0;
			//月合计不合格票数
			int totalIdentifyNo = 0;
			//月合计是否鉴定数
			int totalIdentify = 0;
			if (ticketList != null && ticketList.size() > 0) {
				Calendar startCal = Calendar.getInstance();
				startCal.set(searchCal.get(Calendar.YEAR), i, 1, 0, 0, 0);

				Calendar endCal = Calendar.getInstance();
				endCal.set(searchCal.get(Calendar.YEAR), i, 1, 0, 0, 0);
				endCal.add(Calendar.MONTH, 1);
				endCal.add(Calendar.SECOND, -1);
				for (WorkTicketEntity workTicketEntity : ticketList) {
					if (workTicketEntity.getEndTime().after(startCal.getTime())&&
							workTicketEntity.getEndTime().before(endCal.getTime())) {
						totalCount++;
						totalYearCount++;
						if(IdentifyEnum.IDENTIFYYES.getCode().equals(workTicketEntity.getIdentify())){
							totalIdentifyYes++;
							totalYearIdentifyYes++;
						}else if(IdentifyEnum.IDENTIFYNO.getCode().equals(workTicketEntity.getIdentify())){
							totalIdentifyNo++;
							totalYearIdentifyNo++;
						}else{
							totalIdentify++;
							totalYearIdentify++;
						}
					}
				}
			}
			if (opeationTicketList != null && opeationTicketList.size() > 0) {
				Calendar startCal = Calendar.getInstance();
				startCal.set(searchCal.get(Calendar.YEAR), i, 1, 0, 0, 0);
				
				Calendar endCal = Calendar.getInstance();
				endCal.set(searchCal.get(Calendar.YEAR), i, 1, 0, 0, 0);
				endCal.add(Calendar.MONTH, 1);
				endCal.add(Calendar.SECOND, -1);
				for (OperationTicketEntity operationTicketEntity : opeationTicketList) {
					if (operationTicketEntity.getEndTime().after(startCal.getTime())&&
							operationTicketEntity.getEndTime().before(endCal.getTime())) {
						totalCount++;
						totalYearCount++;
						if(IdentifyEnum.IDENTIFYYES.getCode().equals(operationTicketEntity.getIdentify())){
							totalIdentifyYes++;
							totalYearIdentifyYes++;
						}else if(IdentifyEnum.IDENTIFYNO.getCode().equals(operationTicketEntity.getIdentify())){
							totalIdentifyNo++;
							totalYearIdentifyNo++;
						}else{
							totalIdentify++;
							totalYearIdentify++;
						}
					}
				}
			}

			totalResultMap.put("ticketCount_" + String.valueOf(i + 1), String.valueOf(totalCount));
			totalResultMap.put("qualifiedCount_" + String.valueOf(i + 1), String.valueOf(totalIdentifyYes));
			
			totalResultMap.put("unQualifiedCount_" + String.valueOf(i + 1), String.valueOf(totalIdentifyNo));
			
			String qualifiedRate = "-";
			if (totalIdentifyYes + totalIdentifyNo != 0) {
				qualifiedRate = df.format((double)totalIdentifyYes/(totalIdentifyYes + totalIdentifyNo)*100);
			}
			totalResultMap.put("qualifiedRate_" + String.valueOf(i + 1), qualifiedRate);
			totalResultMap.put("identify_" + String.valueOf(i + 1), String.valueOf(totalIdentify));
		}
		
		String ticketTotalCount = "0";
		if (totalYearCount != 0) {
			ticketTotalCount = String.valueOf(totalYearCount);
		}
		totalResultMap.put("ticketCount_13", ticketTotalCount);
		
		String qualifiedTotalCount = "0";
		if (totalYearIdentifyYes != 0) {
			qualifiedTotalCount = String.valueOf(totalYearIdentifyYes);
		}
		totalResultMap.put("qualifiedCount_13", qualifiedTotalCount);

		
		String unQualifiedTotalCount = "0";
		if (totalYearIdentifyNo != 0) {
			unQualifiedTotalCount = String.valueOf(totalYearIdentifyNo);
		}
		totalResultMap.put("unQualifiedCount_13", unQualifiedTotalCount);

		String qualifiedTotalRate = "-";
		if (totalYearIdentifyYes + totalYearIdentifyNo != 0) {
			qualifiedTotalRate = df.format((double)totalYearIdentifyYes/(totalYearIdentifyYes + totalYearIdentifyNo)*100);
		}
		totalResultMap.put("qualifiedRate_13", qualifiedTotalRate);
		
		String identifyTotalCount = "0";
		if(totalYearIdentify != 0){
			identifyTotalCount = String.valueOf(totalYearIdentify);
		}
		totalResultMap.put("identify_13", identifyTotalCount);
		
		resultMapList.add(totalResultMap);
		
		return resultMapList;
	}
	
	
	public List<Map<String, String>>  getStatisticDataListForMonth(String unitId, String searchMonth) {
		DecimalFormat df = new DecimalFormat("0.00");
		List<Condition> conditions = new ArrayList<Condition>();
		List<Long> unitIdList = new ArrayList<Long>();
		if (unitId == null || "".equals(unitId)) {
			unitId = "0";
		}
		if ("0".equals(unitId)) {
			//查询分公司
			conditions.clear();
			conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
			conditions.add(new Condition("C_LEVEL", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(UnitLevelEnum.COMPANY.getCode())));
			conditions.add(new Condition("C_PARENT_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ, unitId));
			List<SysUnitEntity> companyEntityList = this.sysUnitService.findByCondition(conditions, null);
			List<Long> companyIdList = new ArrayList<Long>();
			if (companyEntityList !=null && companyEntityList.size() >0  && companyEntityList.get(0) != null) {
				for (SysUnitEntity entity : companyEntityList) {
					companyIdList.add(entity.getId());
				}
			}
			//查询分公司下属风场
			conditions.clear();
			conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
			conditions.add(new Condition("C_LEVEL", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(UnitLevelEnum.FARM.getCode())));
			conditions.add(new Condition("C_PARENT_ID", FieldTypeEnum.INT, MatchTypeEnum.IN, companyIdList.toArray()));
			List<SysUnitEntity> farmEntityList = this.sysUnitService.findByCondition(conditions, null);
			if (farmEntityList !=null && farmEntityList.size() >0  && farmEntityList.get(0) != null) {
				for (SysUnitEntity entity : farmEntityList) {
					unitIdList.add(entity.getId());
				}
			}
		} else {
			SysUnitEntity unitEntity = this.sysUnitService.findById(new Long(unitId));
			if (UnitLevelEnum.COMPANY.getCode().equals(unitEntity.getLevel())){
				conditions.clear();
				conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
				conditions.add(new Condition("C_LEVEL", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(UnitLevelEnum.FARM.getCode())));
				conditions.add(new Condition("C_PARENT_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ, unitEntity.getId()));
				List<SysUnitEntity> unitEntityList = this.sysUnitService.findByCondition(conditions, null);
				if (unitEntityList !=null && unitEntityList.size() >0  && unitEntityList.get(0) != null) {
					for (SysUnitEntity entity : unitEntityList) {
						unitIdList.add(entity.getId());
					}
				}
			} else {
				unitIdList.add(new Long(unitId));
			}
		}
		String SearchYearPart = "",searchMonthPartString = "";
		SearchYearPart = searchMonth.split("-")[0];
		searchMonthPartString = searchMonth.split("-")[1];
//		Calendar searchCal = Calendar.getInstance();
//		if (searchMonth !=null && !"".equals(searchMonth)) {
////			searchCal.set(Calendar.YEAR, Integer.valueOf(searchYear));
//			
//			searchCal.set(Integer.valueOf(searchMonth), Calendar.JANUARY, 1, 0, 0, 0);
//		} else {
//			searchCal.set(searchCal.get(Calendar.YEAR), Calendar.JANUARY, 1, 0, 0, 0);
//		}
//		
//		//年开始时间
//		Calendar yearStartCal =  Calendar.getInstance();
//		yearStartCal.setTime(searchCal.getTime());
//		//年结束时间
//		Calendar yearEndCal =  Calendar.getInstance();
//		yearEndCal.setTime(searchCal.getTime());
////		yearEndCal.add(Calendar.YEAR, 1);
//		yearEndCal.add(Calendar.MONTH, 1);
//		yearEndCal.add(Calendar.SECOND, -1);
//		
//		conditions.clear();
//		if (unitIdList != null && unitIdList.size() >0 ) {
//			conditions.add(new Condition("C_UNIT_NAME_ID", FieldTypeEnum.STRING, MatchTypeEnum.IN, unitIdList.toArray()));
//		}
//		conditions.add(new Condition("C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, yearStartCal.getTime()));
//		conditions.add(new Condition("C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LE, yearEndCal.getTime()));
//		conditions.add(new Condition("C_STATUS", FieldTypeEnum.INT, MatchTypeEnum.EQ, DataStatusEnum.NORMAL.ordinal()));
//		conditions.add(new Condition("C_WORK_STATUS", FieldTypeEnum.INT, MatchTypeEnum.EQ, WorkStatusEnum.END.getId()));
//		
//		List<WorkTicketEntity> ticketList = this.findByCondition(conditions, null);
//		
//		//操作票数据取得
//		conditions.clear();
//		if (unitId != null && !"".equals(unitId)) {
//			conditions.add(new Condition("C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, unitId));
//		}
//		conditions.add(new Condition("C_END_DATE", FieldTypeEnum.DATE, MatchTypeEnum.GE, yearStartCal.getTime()));
//		conditions.add(new Condition("C_END_DATE", FieldTypeEnum.DATE, MatchTypeEnum.LE, yearEndCal.getTime()));
//		conditions.add(new Condition("C_STATUS", FieldTypeEnum.INT, MatchTypeEnum.EQ, WorkStatusEnum.END.getId()));
//		
//		List<OperationTicketEntity> opeationTicketList = this.findByCondition("findOperationTicketData", conditions, null);
//		
//		//按类型保存工作票数据的map
//		Map<String, TicketStatisticsItem[]> ticketTypeDataMap= new HashMap<String, TicketStatisticsItem[]>();
//		
//		for (WorkTypeEnum workTypeEnum: WorkTypeEnum.values()) {
//			String workTypeCode = workTypeEnum.getCode();
//			TicketStatisticsItem[] tmpTicketList = new TicketStatisticsItem[13];
//			int totalCount = 0;
//			int totalIdentifyYes = 0;
//			int totalIdentifyNo = 0;
//			int totalIdentifyResult = 0;
//			if (WorkTypeEnum.OPERATION.getCode().equals(workTypeCode)){
//				if (opeationTicketList != null && opeationTicketList.size() > 0) {
//					for(int i=0; i<12; i++){
//						Calendar startCal = Calendar.getInstance();
//						startCal.set(searchCal.get(Calendar.YEAR), i, 1, 0, 0, 0);
//						
//						Calendar endCal = Calendar.getInstance();
//						endCal.set(searchCal.get(Calendar.YEAR), i, 1, 0, 0, 0);
//						endCal.add(Calendar.MONTH, 1);
//						endCal.add(Calendar.SECOND, -1);
//						if (ticketTypeDataMap.containsKey(workTypeCode)) {
//							tmpTicketList = ticketTypeDataMap.get(workTypeCode);
//						} else {
//							tmpTicketList = new TicketStatisticsItem[13];
//							ticketTypeDataMap.put(workTypeCode, tmpTicketList);
//						}
//						for (OperationTicketEntity operationTicketEntity : opeationTicketList) {
//							TicketStatisticsItem statisticsItem = null;
//							if (operationTicketEntity.getEndDate().after(startCal.getTime())&&
//									operationTicketEntity.getEndDate().before(endCal.getTime())) {
//								if(tmpTicketList[i] == null){
//									statisticsItem = new TicketStatisticsItem();
//									tmpTicketList[i] = statisticsItem;
//								}else{
//									statisticsItem = tmpTicketList[i];	
//								}
//								statisticsItem.setTicketCount(statisticsItem.getTicketCount() + 1);
//								totalCount++;
//								if(IdentifyEnum.IDENTIFYYES.getCode().equals(operationTicketEntity.getIdentify())){
//									statisticsItem.setQualifiedCount(statisticsItem.getQualifiedCount() + 1);
//									totalIdentifyYes++;
//								}else if(IdentifyEnum.IDENTIFYNO.getCode().equals(operationTicketEntity.getIdentify())){
//									statisticsItem.setUnQualifiedCount(statisticsItem.getUnQualifiedCount() + 1);
//									totalIdentifyNo++;
//								}else{
//									statisticsItem.setIdentify(statisticsItem.getIdentify() + 1);
//									totalIdentifyResult++;
//								}
//								
//								int tmpCount = statisticsItem.getQualifiedCount() + statisticsItem.getUnQualifiedCount();
//								if (tmpCount == 0) {
//									statisticsItem.setQualifiedRate("-");
//								} else {
//									statisticsItem.setQualifiedRate(df.format((double)statisticsItem.getQualifiedCount()/tmpCount*100));
//								}
//								
//							}
//						}
//					}
//					TicketStatisticsItem totalStatisticsItem = new TicketStatisticsItem();
//					totalStatisticsItem.setTicketCount(totalCount);
//					totalStatisticsItem.setQualifiedCount(totalIdentifyYes);
//					totalStatisticsItem.setUnQualifiedCount(totalIdentifyNo);
//					totalStatisticsItem.setIdentify(totalIdentifyResult);
//					int tmpTotalCount = totalStatisticsItem.getQualifiedCount() + totalStatisticsItem.getUnQualifiedCount();
//					if (tmpTotalCount == 0) {
//						totalStatisticsItem.setQualifiedRate("-");
//					} else {
//						totalStatisticsItem.setQualifiedRate(df.format((double)totalStatisticsItem.getQualifiedCount()/tmpTotalCount*100));
//					}
//					tmpTicketList[12] = totalStatisticsItem;
//				} else {
//					for(int i=0; i<12; i++){
//						if (ticketTypeDataMap.containsKey(workTypeCode)) {
//							tmpTicketList = ticketTypeDataMap.get(workTypeCode);
//						} else {
//							tmpTicketList = new TicketStatisticsItem[13];
//							ticketTypeDataMap.put(workTypeCode, tmpTicketList);
//						}
//						tmpTicketList[i] = new TicketStatisticsItem();
//					}
//				}
//			} else {
//				if (ticketList != null && ticketList.size() > 0) {
//					for(int i=0; i<12; i++){
//						Calendar startCal = Calendar.getInstance();
//						startCal.set(searchCal.get(Calendar.YEAR), i, 1, 0, 0, 0);
//						
//						Calendar endCal = Calendar.getInstance();
//						endCal.set(searchCal.get(Calendar.YEAR), i, 1, 0, 0, 0);
//						endCal.add(Calendar.MONTH, 1);
//						endCal.add(Calendar.SECOND, -1);
//						if (ticketTypeDataMap.containsKey(workTypeCode)) {
//							tmpTicketList = ticketTypeDataMap.get(workTypeCode);
//						} else {
//							tmpTicketList = new TicketStatisticsItem[13];
//							ticketTypeDataMap.put(workTypeCode, tmpTicketList);
//						}
//						for (WorkTicketEntity workTicketEntity : ticketList) {
//							TicketStatisticsItem statisticsItem = null;
//							if (workTypeCode.equals(workTicketEntity.getType().toString())&& 
//									workTicketEntity.getEndTime().after(startCal.getTime())&&
//									workTicketEntity.getEndTime().before(endCal.getTime())) {
//								if(tmpTicketList[i] == null){
//									statisticsItem = new TicketStatisticsItem();
//									tmpTicketList[i] = statisticsItem;
//								}else{
//									statisticsItem = tmpTicketList[i];	
//								}
//								statisticsItem.setTicketCount(statisticsItem.getTicketCount() + 1);
//								totalCount++;
//								if(IdentifyEnum.IDENTIFYYES.getCode().equals(workTicketEntity.getIdentify())){
//									statisticsItem.setQualifiedCount(statisticsItem.getQualifiedCount() + 1);
//									totalIdentifyYes++;
//								}else if(IdentifyEnum.IDENTIFYNO.getCode().equals(workTicketEntity.getIdentify())){
//									statisticsItem.setUnQualifiedCount(statisticsItem.getUnQualifiedCount() + 1);
//									totalIdentifyNo++;
//								}else{
//									statisticsItem.setIdentify(statisticsItem.getIdentify() + 1);
//									totalIdentifyResult++;
//								}
//								
//								int tmpCount = statisticsItem.getQualifiedCount() + statisticsItem.getUnQualifiedCount();
//								if (tmpCount == 0) {
//									statisticsItem.setQualifiedRate("-");
//								} else {
//									statisticsItem.setQualifiedRate(df.format((double)statisticsItem.getQualifiedCount()/tmpCount*100));
//								}
//								
//							}
//						}
//					}
//					TicketStatisticsItem totalStatisticsItem = new TicketStatisticsItem();
//					totalStatisticsItem.setTicketCount(totalCount);
//					totalStatisticsItem.setQualifiedCount(totalIdentifyYes);
//					totalStatisticsItem.setUnQualifiedCount(totalIdentifyNo);
//					totalStatisticsItem.setIdentify(totalIdentifyResult);
//					int tmpTotalCount = totalStatisticsItem.getQualifiedCount() + totalStatisticsItem.getUnQualifiedCount();
//					if (tmpTotalCount == 0) {
//						totalStatisticsItem.setQualifiedRate("-");
//					} else {
//						totalStatisticsItem.setQualifiedRate(df.format((double)totalStatisticsItem.getQualifiedCount()/tmpTotalCount*100));
//					}
//					tmpTicketList[12] = totalStatisticsItem;
//				} else {
//					for(int i=0; i<12; i++){
//						if (ticketTypeDataMap.containsKey(workTypeCode)) {
//							tmpTicketList = ticketTypeDataMap.get(workTypeCode);
//						} else {
//							tmpTicketList = new TicketStatisticsItem[13];
//							ticketTypeDataMap.put(workTypeCode, tmpTicketList);
//						}
//						tmpTicketList[i] = new TicketStatisticsItem();
//					}
//				}
//			}
//		}
//		
		List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>();
//		
//		//根据票类型取得页面显示数据
//		Map<String, String> resultMap = null;
//		for(TicketStatisticsEnum workTypeEnum : TicketStatisticsEnum.values()){
//			resultMap = new HashMap<String, String>();
//			resultMap.put("ticketTypeName", workTypeEnum.getName());
//			for(int i=0; i<13; i++){
//				TicketStatisticsItem[] ticketStatisticsItems = ticketTypeDataMap.get(workTypeEnum.getCode());
//				String ticketCount = "0";
//				if(ticketStatisticsItems[i] != null){
//					ticketCount = String.valueOf(ticketStatisticsItems[i].getTicketCount());
//				}
//				
//				resultMap.put("ticketCount_" + String.valueOf(i + 1), ticketCount);
//				
//				String qualifiedCount = "0";
//				if(ticketStatisticsItems[i] != null){
//					qualifiedCount = String.valueOf(ticketStatisticsItems[i].getQualifiedCount());
//				}
//				resultMap.put("qualifiedCount_" + String.valueOf(i + 1), qualifiedCount);
//				
//				String unQualifiedCount = "0";
//				if(ticketStatisticsItems[i] != null){
//					unQualifiedCount = String.valueOf(ticketStatisticsItems[i].getUnQualifiedCount());
//				}
//				resultMap.put("unQualifiedCount_" + String.valueOf(i + 1), unQualifiedCount);
//				
//				String qualifiedRate = "-";
//				if (ticketStatisticsItems[i] != null ){
//					int tmpTotalCount = ticketStatisticsItems[i].getQualifiedCount() + ticketStatisticsItems[i].getUnQualifiedCount();
//					if ( tmpTotalCount != 0) {
//						qualifiedRate = df.format((double)ticketStatisticsItems[i].getQualifiedCount()/tmpTotalCount*100);
//					}
//				}
//				resultMap.put("qualifiedRate_" + String.valueOf(i + 1), qualifiedRate);
//				String identifyString = "0";
//				if (ticketStatisticsItems[i] != null ){
//					identifyString = String.valueOf(ticketStatisticsItems[i].getIdentify());
//				}
//				resultMap.put("identify_" + String.valueOf(i + 1), identifyString);
//			}
//			resultMapList.add(resultMap);
//		}
//		
//		//合计map
//		Map<String, String> totalResultMap  = new HashMap<String, String>();
//		totalResultMap.put("ticketTypeName", "合计");
//		//合计年票数
//		int totalYearCount = 0;
//		//合计年合格票数
//		int totalYearIdentifyYes = 0;
//		//合计年不合格票数
//		int totalYearIdentifyNo = 0;
//		//合计年是否鉴定数
//		int totalYearIdentify = 0;
//		for(int i=0; i<12; i++){
//			//月合计票数
//			int totalCount = 0;
//			//月合计合格票数
//			int totalIdentifyYes = 0;
//			//月合计不合格票数
//			int totalIdentifyNo = 0;
//			//月合计是否鉴定数
//			int totalIdentify = 0;
//			if (ticketList != null && ticketList.size() > 0) {
//				Calendar startCal = Calendar.getInstance();
//				startCal.set(searchCal.get(Calendar.YEAR), i, 1, 0, 0, 0);
//
//				Calendar endCal = Calendar.getInstance();
//				endCal.set(searchCal.get(Calendar.YEAR), i, 1, 0, 0, 0);
//				endCal.add(Calendar.MONTH, 1);
//				endCal.add(Calendar.SECOND, -1);
//				for (WorkTicketEntity workTicketEntity : ticketList) {
//					if (workTicketEntity.getEndTime().after(startCal.getTime())&&
//							workTicketEntity.getEndTime().before(endCal.getTime())) {
//						totalCount++;
//						totalYearCount++;
//						if(IdentifyEnum.IDENTIFYYES.getCode().equals(workTicketEntity.getIdentify())){
//							totalIdentifyYes++;
//							totalYearIdentifyYes++;
//						}else if(IdentifyEnum.IDENTIFYNO.getCode().equals(workTicketEntity.getIdentify())){
//							totalIdentifyNo++;
//							totalYearIdentifyNo++;
//						}else{
//							totalIdentify++;
//							totalYearIdentify++;
//						}
//					}
//				}
//			}
//			if (opeationTicketList != null && opeationTicketList.size() > 0) {
//				Calendar startCal = Calendar.getInstance();
//				startCal.set(searchCal.get(Calendar.YEAR), i, 1, 0, 0, 0);
//				
//				Calendar endCal = Calendar.getInstance();
//				endCal.set(searchCal.get(Calendar.YEAR), i, 1, 0, 0, 0);
//				endCal.add(Calendar.MONTH, 1);
//				endCal.add(Calendar.SECOND, -1);
//				for (OperationTicketEntity operationTicketEntity : opeationTicketList) {
//					if (operationTicketEntity.getEndDate().after(startCal.getTime())&&
//							operationTicketEntity.getEndDate().before(endCal.getTime())) {
//						totalCount++;
//						totalYearCount++;
//						if(IdentifyEnum.IDENTIFYYES.getCode().equals(operationTicketEntity.getIdentify())){
//							totalIdentifyYes++;
//							totalYearIdentifyYes++;
//						}else if(IdentifyEnum.IDENTIFYNO.getCode().equals(operationTicketEntity.getIdentify())){
//							totalIdentifyNo++;
//							totalYearIdentifyNo++;
//						}else{
//							totalIdentify++;
//							totalYearIdentify++;
//						}
//					}
//				}
//			}
//
//			totalResultMap.put("ticketCount_" + String.valueOf(i + 1), String.valueOf(totalCount));
//			totalResultMap.put("qualifiedCount_" + String.valueOf(i + 1), String.valueOf(totalIdentifyYes));
//			
//			totalResultMap.put("unQualifiedCount_" + String.valueOf(i + 1), String.valueOf(totalIdentifyNo));
//			
//			String qualifiedRate = "-";
//			if (totalIdentifyYes + totalIdentifyNo != 0) {
//				qualifiedRate = df.format((double)totalIdentifyYes/(totalIdentifyYes + totalIdentifyNo)*100);
//			}
//			totalResultMap.put("qualifiedRate_" + String.valueOf(i + 1), qualifiedRate);
//			totalResultMap.put("identify_" + String.valueOf(i + 1), String.valueOf(totalIdentify));
//		}
//		
//		String ticketTotalCount = "0";
//		if (totalYearCount != 0) {
//			ticketTotalCount = String.valueOf(totalYearCount);
//		}
//		totalResultMap.put("ticketCount_13", ticketTotalCount);
//		
//		String qualifiedTotalCount = "0";
//		if (totalYearIdentifyYes != 0) {
//			qualifiedTotalCount = String.valueOf(totalYearIdentifyYes);
//		}
//		totalResultMap.put("qualifiedCount_13", qualifiedTotalCount);
//
//		
//		String unQualifiedTotalCount = "0";
//		if (totalYearIdentifyNo != 0) {
//			unQualifiedTotalCount = String.valueOf(totalYearIdentifyNo);
//		}
//		totalResultMap.put("unQualifiedCount_13", unQualifiedTotalCount);
//
//		String qualifiedTotalRate = "-";
//		if (totalYearIdentifyYes + totalYearIdentifyNo != 0) {
//			qualifiedTotalRate = df.format((double)totalYearIdentifyYes/(totalYearIdentifyYes + totalYearIdentifyNo)*100);
//		}
//		totalResultMap.put("qualifiedRate_13", qualifiedTotalRate);
//		
//		String identifyTotalCount = "0";
//		if(totalYearIdentify != 0){
//			identifyTotalCount = String.valueOf(totalYearIdentify);
//		}
//		totalResultMap.put("identify_13", identifyTotalCount);
//		
//		resultMapList.add(totalResultMap);
		
		return resultMapList;
	}
	public static void main(String[] args) {
		Calendar endCal = Calendar.getInstance();
		endCal.set(Calendar.YEAR, 1, 1, 0, 0, 0);
		endCal.add(Calendar.MONTH, 1);
//		endCal.add(Calendar.SECOND, -1);
		System.out.println(endCal.getTime());
	}
}