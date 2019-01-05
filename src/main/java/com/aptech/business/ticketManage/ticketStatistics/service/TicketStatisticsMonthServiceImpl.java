package com.aptech.business.ticketManage.ticketStatistics.service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.aptech.business.component.dictionary.WorkStatusEnum;
import com.aptech.business.component.dictionary.WorkTypeEnum;
import com.aptech.business.ticketManage.operationTicket.domain.OperationTicketEntity;
import com.aptech.business.ticketManage.ticketStatistics.dao.TicketStatisticsMonthDao;
import com.aptech.business.ticketManage.ticketStatistics.domain.TicketStatisticsEnum;
import com.aptech.business.ticketManage.ticketStatistics.domain.TicketStatisticsItem;
import com.aptech.business.ticketManage.ticketStatistics.domain.TicketStatisticsMonthVO;
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
@Service("ticketStatisticsMonthService")
@Transactional
public class TicketStatisticsMonthServiceImpl extends AbstractBaseEntityOperation<TicketStatisticsMonthVO> implements TicketStatisticsMonthService{
	@Autowired
	private TicketStatisticsMonthDao ticketStatisticsMonthDao;

	@Autowired
	private SysUnitService sysUnitService;
	
	@Override
	public List<Map<String, String>> getStatisticDataList(String unitId,
			String searchMonth) {
		DecimalFormat df = new DecimalFormat("0.00");
		List<Condition> conditions = new ArrayList<Condition>();
		List<Long> unitIdList = new ArrayList<Long>();
//		if (unitId == null || "".equals(unitId)) {
//			unitId = "0";
//		}
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
		} 
		Calendar searchCal = Calendar.getInstance();
		if (searchMonth !=null && !"".equals(searchMonth)) {
//			searchCal.set(Calendar.YEAR, Integer.valueOf(searchYear));
			SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM");
			try {
				Date date = format.parse(searchMonth);
				searchCal.setTime(date);
//				int searchMonthOld=searchCal.get(Calendar.MONTH);
//				int searchMonthNew=searchMonthOld+1;
//				searchCal.set(searchCal.get(Calendar.YEAR),searchMonthNew, 1, 0, 0, 0);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			searchCal.set(searchCal.get(Calendar.YEAR),searchCal.get(Calendar.MONTH), 1, 0, 0, 0);
		}
		
		//月开始时间
		Calendar monthStartCal =  Calendar.getInstance();
		monthStartCal.setTime(searchCal.getTime());
		//年结束时间
		Calendar monthEndCal =  Calendar.getInstance();
		monthEndCal.setTime(searchCal.getTime());
		monthEndCal.add(Calendar.MONTH, 1);
		monthEndCal.add(Calendar.SECOND, -1);
		
		conditions.clear();
		if (unitIdList != null && unitIdList.size() >0 ) {
			conditions.add(new Condition("C_UNIT_NAME_ID", FieldTypeEnum.STRING, MatchTypeEnum.IN, unitIdList.toArray()));
		}
		conditions.add(new Condition("C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, monthStartCal.getTime()));
		conditions.add(new Condition("C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LE, monthEndCal.getTime()));
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.INT, MatchTypeEnum.EQ, DataStatusEnum.NORMAL.ordinal()));
		conditions.add(new Condition("C_WORK_STATUS", FieldTypeEnum.INT, MatchTypeEnum.EQ, WorkStatusEnum.END.getId()));
		
		List<WorkTicketEntity> ticketList = this.findByCondition(conditions, null);
		
		//操作票数据取得
		conditions.clear();
		if (unitId != null && !"".equals(unitId)) {
			conditions.add(new Condition("C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, unitId));
		}
		conditions.add(new Condition("C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, monthStartCal.getTime()));
		conditions.add(new Condition("C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LE, monthEndCal.getTime()));
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.INT, MatchTypeEnum.EQ, OperationStatusEnum.RESULTS.getId()));
		
		List<OperationTicketEntity> opeationTicketList = this.findByCondition("findOperationTicketData", conditions, null);
		
		//按类型保存工作票数据的map
		Map<String, TicketStatisticsItem[]> ticketTypeDataMap= new HashMap<String, TicketStatisticsItem[]>();
		
		for (WorkTypeEnum workTypeEnum: WorkTypeEnum.values()) {
			String workTypeCode = workTypeEnum.getCode();
			TicketStatisticsItem[] tmpTicketList = new TicketStatisticsItem[32];
			int totalCount = 0;
			int totalIdentifyYes = 0;
			int totalIdentifyNo = 0;
			int totalIdentifyResult = 0;
			if (WorkTypeEnum.OPERATION.getCode().equals(workTypeCode)){
				if (opeationTicketList != null && opeationTicketList.size() > 0) {
					for(int i=0; i<32; i++){
						Calendar startCal = Calendar.getInstance();
						startCal.set(searchCal.get(Calendar.YEAR),searchCal.get(Calendar.MONTH), i+1, 0, 0, 0);
						
						Calendar endCal = Calendar.getInstance();
						endCal.set(searchCal.get(Calendar.YEAR),searchCal.get(Calendar.MONTH), i+1, 23, 59, 59);
//						endCal.add(Calendar.MONTH, 1);
//						endCal.add(Calendar.SECOND, -1);
						System.out.println(startCal.getTime());
						System.out.println(endCal.getTime());
						if (ticketTypeDataMap.containsKey(workTypeCode)) {
							tmpTicketList = ticketTypeDataMap.get(workTypeCode);
						} else {
							tmpTicketList = new TicketStatisticsItem[32];
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
					tmpTicketList[31] = totalStatisticsItem;
				} else {
					for(int i=0; i<32; i++){
						if (ticketTypeDataMap.containsKey(workTypeCode)) {
							tmpTicketList = ticketTypeDataMap.get(workTypeCode);
						} else {
							tmpTicketList = new TicketStatisticsItem[32];
							ticketTypeDataMap.put(workTypeCode, tmpTicketList);
						}
						tmpTicketList[i] = new TicketStatisticsItem();
					}
				}
			} else {
				if (ticketList != null && ticketList.size() > 0) {
					for(int i=0; i<31; i++){
						Calendar startCal = Calendar.getInstance();
						startCal.set(searchCal.get(Calendar.YEAR),searchCal.get(Calendar.MONTH), i, 0, 0, 0);
						startCal.add(Calendar.DAY_OF_MONTH, 1);
						Calendar endCal = Calendar.getInstance();
						endCal.set(searchCal.get(Calendar.YEAR),searchCal.get(Calendar.MONTH), i, 1, 0, 0);
						endCal.add(Calendar.DAY_OF_MONTH, 2);
						endCal.add(Calendar.SECOND, -1);
						System.out.println(endCal.getTime());
						System.out.println(startCal.getTime());
						if (ticketTypeDataMap.containsKey(workTypeCode)) {
							tmpTicketList = ticketTypeDataMap.get(workTypeCode);
						} else {
							tmpTicketList = new TicketStatisticsItem[32];
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
					tmpTicketList[31] = totalStatisticsItem;
				} else {
					for(int i=0; i<32; i++){
						if (ticketTypeDataMap.containsKey(workTypeCode)) {
							tmpTicketList = ticketTypeDataMap.get(workTypeCode);
						} else {
							tmpTicketList = new TicketStatisticsItem[32];
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
			for(int i=0; i<32; i++){
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
			//这里就应该放进去了   然后应该在上面的循环里计算 我关了 
			
			
		}
		
		//合计map
		Map<String, String> totalResultMap  = new HashMap<String, String>();
		totalResultMap.put("ticketTypeName", "合计");
		//合计月票数
		int totalMonthCount = 0;
		//合计月合格票数
		int totalMonthIdentifyYes = 0;
		//合计月不合格票数
		int totalMonthIdentifyNo = 0;
		//合计月是否鉴定数
		int totalMonthIdentify = 0;
		for(int i=0; i<31; i++){
			//日合计票数
			int totalCount = 0;
			//日合计合格票数
			int totalIdentifyYes = 0;
			//日合计不合格票数
			int totalIdentifyNo = 0;
			//日合计是否鉴定数
			int totalIdentify = 0;
			if (ticketList != null && ticketList.size() > 0) {
				Calendar startCal = Calendar.getInstance();
				startCal.set(searchCal.get(Calendar.YEAR),searchCal.get(Calendar.MONTH), i, 0, 0, 0);
				startCal.add(Calendar.DAY_OF_MONTH, 1);
				Calendar endCal = Calendar.getInstance();
				endCal.set(searchCal.get(Calendar.YEAR),searchCal.get(Calendar.MONTH), i, 1, 0, 0);
				endCal.add(Calendar.DAY_OF_MONTH, 2);
				endCal.add(Calendar.SECOND, -1);
				System.out.println(startCal.getTime());
				System.out.println(endCal.getTime());
				for (WorkTicketEntity workTicketEntity : ticketList) {
					if (workTicketEntity.getEndTime().after(startCal.getTime())&&
							workTicketEntity.getEndTime().before(endCal.getTime())) {
						totalCount++;
						totalMonthCount++;
						if(IdentifyEnum.IDENTIFYYES.getCode().equals(workTicketEntity.getIdentify())){
							totalIdentifyYes++;
							totalMonthIdentifyYes++;
						}else if(IdentifyEnum.IDENTIFYNO.getCode().equals(workTicketEntity.getIdentify())){
							totalIdentifyNo++;
							totalMonthIdentifyNo++;
						}else{
							totalIdentify++;
							totalMonthIdentify++;
						}
					}
				}
			}
			if (opeationTicketList != null && opeationTicketList.size() > 0) {
				Calendar startCal = Calendar.getInstance();
				startCal.set(searchCal.get(Calendar.YEAR),searchCal.get(Calendar.MONTH), i, 0, 0, 0);
				startCal.add(Calendar.DAY_OF_MONTH, 1);
				Calendar endCal = Calendar.getInstance();
				endCal.set(searchCal.get(Calendar.YEAR),searchCal.get(Calendar.MONTH), i, 1, 0, 0);
				endCal.add(Calendar.DAY_OF_MONTH, 2);
				endCal.add(Calendar.SECOND, -1);
				System.out.println(startCal.getTime());
				System.out.println(endCal.getTime());
				for (OperationTicketEntity operationTicketEntity : opeationTicketList) {
					if (operationTicketEntity.getEndTime().after(startCal.getTime())&&
							operationTicketEntity.getEndTime().before(endCal.getTime())) {
						totalCount++;
						totalMonthCount++;
						if(IdentifyEnum.IDENTIFYYES.getCode().equals(operationTicketEntity.getIdentify())){
							totalIdentifyYes++;
							totalMonthIdentifyYes++;
						}else if(IdentifyEnum.IDENTIFYNO.getCode().equals(operationTicketEntity.getIdentify())){
							totalIdentifyNo++;
							totalMonthIdentifyNo++;
						}else{
							totalIdentify++;
							totalMonthIdentify++;
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
		if (totalMonthCount != 0) {
			ticketTotalCount = String.valueOf(totalMonthCount);
		}
		totalResultMap.put("ticketCount_32", ticketTotalCount);
		
		String qualifiedTotalCount = "0";
		if (totalMonthIdentifyYes != 0) {
			qualifiedTotalCount = String.valueOf(totalMonthIdentifyYes);
		}
		totalResultMap.put("qualifiedCount_32", qualifiedTotalCount);

		
		String unQualifiedTotalCount = "0";
		if (totalMonthIdentifyNo != 0) {
			unQualifiedTotalCount = String.valueOf(totalMonthIdentifyNo);
		}
		totalResultMap.put("unQualifiedCount_32", unQualifiedTotalCount);

		String qualifiedTotalRate = "-";
		if (totalMonthIdentifyYes + totalMonthIdentifyNo != 0) {
			qualifiedTotalRate = df.format((double)totalMonthIdentifyYes/(totalMonthIdentifyYes + totalMonthIdentifyNo)*100);
		}
		totalResultMap.put("qualifiedRate_32", qualifiedTotalRate);
		
		String identifyTotalCount = "0";
		if(totalMonthIdentify != 0){
			identifyTotalCount = String.valueOf(totalMonthIdentify);
		}
		totalResultMap.put("identify_32", identifyTotalCount);
		
		resultMapList.add(totalResultMap);
		
		return resultMapList;
	}

	@Override
	public IBaseEntityOperation<TicketStatisticsMonthVO> getDao() {
		// TODO Auto-generated method stub
		return ticketStatisticsMonthDao;
	}

}
