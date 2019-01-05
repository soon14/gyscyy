package com.aptech.business.train.trainStatistics.service;

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

import com.aptech.business.train.trainManagement.domain.TrainApprovalStatusEnum;
import com.aptech.business.train.trainStatistics.dao.TrainStatisticsDao;
import com.aptech.business.train.trainStatistics.domain.TrainStatisticsEntity;
import com.aptech.business.train.trainStatistics.domain.TrainStatisticsVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;

/**
 * 
 * 培训统计应用管理服务实现类
 *
 * @author 
 * @created 2018-03-20 13:39:12
 * @lastModified 
 * @history
 *
 */
@Service("trainStatisticsService")
@Transactional
public class TrainStatisticsServiceImpl extends AbstractBaseEntityOperation<TrainStatisticsEntity> implements TrainStatisticsService {
	
	@Autowired
	private TrainStatisticsDao trainStatisticsDao;
	
	@Autowired
	private SysUnitService sysUnitService;
	
	
	@Override
	public IBaseEntityOperation<TrainStatisticsEntity> getDao() {
		return trainStatisticsDao;
	}
	
	/**
	* 培训统计查询
	* @author ly
	* @date 2018年3月21日 上午10:04:58 
	* @param params
	* @param page
	* @return
	 * @throws ParseException 
	*/
	@Override
	public List<TrainStatisticsVO> findByCondition1(Map<String, Object> params) throws ParseException {
		//时间维度
		String timespan=(String) params.get("timespan");
		//季度开始标识
		String quarter1=(String) params.get("quarter_select_area1");
		//季度结束标识
		String quarter2=(String) params.get("quarter_select_area2");
		//查询开始时间
		String time1=(String) params.get("time1");
		//查询结束时间
		String time2=(String) params.get("time2");
		//查询办班单位Id
		String unitId=(String) params.get("unitId");
		//取得月份相关时间
		Map<String,Calendar> timeMap = null;
		if("month".equals(timespan)){
			timeMap = getMonthDateMap(time1,time2);
		}else if("quarter".equals(timespan)){
			timeMap = getQuarterDateMap(time1, quarter1,quarter2);
		}else{
			timeMap = getYearDateMap(time1,time2);
		}
		
		DateFormatUtil dfu = DateFormatUtil.getInstance("yyyy-MM");
		DateFormatUtil dfu1 = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss");
		
		List<TrainStatisticsVO> allList = new ArrayList<TrainStatisticsVO>();
		List<Condition> conditions = new ArrayList<Condition>();
		//根据时间去培训计划表读取数据
		conditions.clear();
		String startTime = dfu1.format(timeMap.get("startTime").getTime());
		String endTime = dfu1.format(timeMap.get("endTime").getTime());
		conditions.add(new Condition("C_TIME", FieldTypeEnum.STRING, MatchTypeEnum.GE, startTime));
		conditions.add(new Condition("C_TIME", FieldTypeEnum.STRING, MatchTypeEnum.LE, endTime));
		if (unitId != null && !"".equals(unitId)) {
			conditions.add(new Condition("C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, unitId));
		}
		conditions.add(new Condition("p.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, TrainApprovalStatusEnum.RESULTS.getCode()));
		
		//取得满足条件的所有数据
		List<TrainStatisticsEntity> dataList = this.findByCondition(conditions, null);
		
		//取得所有办班单位
		conditions.clear();
		if (unitId != null && !"".equals(unitId)) {
			int id = 0;
			try {
				id = Integer.parseInt(unitId);
			} catch(NumberFormatException e) {
				
			}
			conditions.add(new Condition("C_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ, id));
		}
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));	
		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"N"));
		List<SysUnitEntity> treeNodeList = sysUnitService.findByCondition(conditions, null);
		
		//总培训次数
		int allTrainCount = 0;
		//定期培训总次数
		int allRegularCount = 0;
		//非定期培训总次数
		int allNoRegularCount = 0;
		//培训总人数
		int allPeopleCount = 0;
		
		//根据季度和年维度对数据进行汇总
		Calendar startCal = Calendar.getInstance();
		Calendar endCal =  Calendar.getInstance();
		if("quarter".equals(timespan)){
			int startQuarter = 1;
			int endQuarter = 1;
			try {
				startQuarter = Integer.parseInt(quarter1);
			} catch(NumberFormatException e) {
				
			}
			try {
				endQuarter = Integer.parseInt(quarter2);
			} catch(NumberFormatException e) {
				
			}
			int year = startCal.get(Calendar.YEAR);
			try {
				year = Integer.parseInt(time1);
			} catch(NumberFormatException e) {
				
			}
			for (SysUnitEntity unitEntity : treeNodeList) {
				for (int i=startQuarter;i <= endQuarter; i++) { 
					TrainStatisticsVO staticVo = new TrainStatisticsVO();
					staticVo.setUnitName(unitEntity.getName());
					//培训次数
					int trainCount = 0;
					//定期培训次数
					int regularCount = 0;
					//非定期培训次数
					int noRegularCount = 0;
					//培训人数
					int peopleCount = 0;
					if (1 == i ) {
						staticVo.setShowTime(time1 + "年第一季度");
						startCal.set(year, 0, 1, 0, 0, 0);
						endCal.set(year, 2, 31, 23, 59, 59);
					} else if (2 == i ) {
						staticVo.setShowTime(time1 + "年第二季度");
						startCal.set(year, 3, 1, 0, 0, 0);
						endCal.set(year, 5, 30, 23, 59, 59);
					} else if (3 == i ) {
						staticVo.setShowTime(time1 + "年第三季度");
						startCal.set(year, 6, 1, 0, 0, 0);
						endCal.set(year, 8, 30, 23, 59, 59);
					} else if (4 == i ) {
						staticVo.setShowTime(time1 + "年第四季度");
						startCal.set(year, 9, 1, 0, 0, 0);
						endCal.set(year, 11, 31, 23, 59, 59);
					}
					for (TrainStatisticsEntity statisticsEntity :dataList) {
						if (unitEntity.getId().toString().equals(statisticsEntity.getUnitId().toString()) 
								&& statisticsEntity.getTime().after(startCal.getTime()) 
								&& statisticsEntity.getTime().before(endCal.getTime())) {
							trainCount++;
							//总培训次数
							allTrainCount++;
							if ("0".equals(statisticsEntity.getClassify())) {
								regularCount++;
								allRegularCount++;
							} else if ("1".equals(statisticsEntity.getClassify())) {
								noRegularCount++;
								allNoRegularCount++;
							}
							int people = 0;
							try {
								people = Integer.parseInt(statisticsEntity.getCount());
							} catch(NumberFormatException e) {
								
							}
							peopleCount += people;
							allPeopleCount += people;
						}
					}
					staticVo.setNoRegularCount(noRegularCount);
					staticVo.setRegularCount(regularCount);
					staticVo.setTotalPeopleCount(peopleCount);
					staticVo.setTotalCount(trainCount);
					allList.add(staticVo);
				}
			}
			TrainStatisticsVO staticVoAll = new TrainStatisticsVO();
			staticVoAll.setUnitName("合计");
			staticVoAll.setNoRegularCount(allNoRegularCount);
			staticVoAll.setRegularCount(allRegularCount);
			staticVoAll.setTotalPeopleCount(allPeopleCount);
			staticVoAll.setTotalCount(allTrainCount);
			allList.add(staticVoAll);

		} else if("year".equals(timespan)) {
			int startYear = startCal.get(Calendar.YEAR);
			int endYear = endCal.get(Calendar.YEAR);
			try {
				startYear = Integer.parseInt(time1);
			} catch(NumberFormatException e) {
				
			}
			try {
				endYear = Integer.parseInt(time2);
			} catch(NumberFormatException e) {
				
			}
			
			for (SysUnitEntity unitEntity : treeNodeList) {
				for (int i=startYear;i <= endYear; i++) { 
					//培训次数
					int trainCount = 0;
					//定期培训次数
					int regularCount = 0;
					//非定期培训次数
					int noRegularCount = 0;
					//培训人数
					int peopleCount = 0;
					
					TrainStatisticsVO staticVo = new TrainStatisticsVO();
					staticVo.setUnitName(unitEntity.getName());
					staticVo.setShowTime(i + "年");
					startCal.set(i, 0, 1, 0, 0, 0);
					endCal.set(i, 11, 31, 23, 59, 59);
					for (TrainStatisticsEntity statisticsEntity :dataList) {
						if (unitEntity.getId().toString().equals(statisticsEntity.getUnitId().toString()) 
								&& statisticsEntity.getTime().after(startCal.getTime()) 
								&& statisticsEntity.getTime().before(endCal.getTime())) {
							trainCount++;
							//总培训次数
							allTrainCount++;
							if ("0".equals(statisticsEntity.getClassify())) {
								regularCount++;
								allRegularCount++;
							} else if ("1".equals(statisticsEntity.getClassify())) {
								noRegularCount++;
								allNoRegularCount++;
							}
							int people = 0;
							try {
								people = Integer.parseInt(statisticsEntity.getCount());
							} catch(NumberFormatException e) {
								
							}
							peopleCount += people;
							allPeopleCount += people;
						}
					}
					staticVo.setNoRegularCount(noRegularCount);
					staticVo.setRegularCount(regularCount);
					staticVo.setTotalPeopleCount(peopleCount);
					staticVo.setTotalCount(trainCount);
					allList.add(staticVo);
				}
				
			}
			TrainStatisticsVO staticVoAll = new TrainStatisticsVO();
			staticVoAll.setUnitName("合计");
			staticVoAll.setNoRegularCount(allNoRegularCount);
			staticVoAll.setRegularCount(allRegularCount);
			staticVoAll.setTotalPeopleCount(allPeopleCount);
			staticVoAll.setTotalCount(allTrainCount);
			allList.add(staticVoAll);
		} else if("month".equals(timespan)) {
			List<Date> months = this.getMonthsBetweenTwoDate(timeMap.get("startTime").getTime(),timeMap.get("endTime").getTime());
			for (SysUnitEntity unitEntity : treeNodeList) {
				for (int i=0;i < months.size(); i++) { 
					Date date = months.get(i);
					TrainStatisticsVO staticVo = new TrainStatisticsVO();
					staticVo.setUnitName(unitEntity.getName());
					//培训次数
					int trainCount = 0;
					//定期培训次数
					int regularCount = 0;
					//非定期培训次数
					int noRegularCount = 0;
					//培训人数
					int peopleCount = 0;
					staticVo.setShowTime(dfu.format(date));
					
					startCal.setTime(date);
					startCal.set(Calendar.DAY_OF_MONTH, 1);
					startCal.set(Calendar.HOUR_OF_DAY, 0);
					startCal.set(Calendar.MINUTE, 0);
					startCal.set(Calendar.SECOND, 0);
					endCal.setTime(startCal.getTime());
					endCal.add(Calendar.MONTH, 1);
					endCal.add(Calendar.SECOND, -1);

					for (TrainStatisticsEntity statisticsEntity :dataList) {
						if (unitEntity.getId().toString().equals(statisticsEntity.getUnitId().toString()) 
								&& statisticsEntity.getTime().after(startCal.getTime()) 
								&& statisticsEntity.getTime().before(endCal.getTime())) {
							trainCount++;
							//总培训次数
							allTrainCount++;
							if ("0".equals(statisticsEntity.getClassify())) {
								regularCount++;
								allRegularCount++;
							} else if ("1".equals(statisticsEntity.getClassify())) {
								noRegularCount++;
								allNoRegularCount++;
							}
							int people = 0;
							try {
								people = Integer.parseInt(statisticsEntity.getCount());
							} catch(NumberFormatException e) {
								
							}
							peopleCount += people;
							allPeopleCount += people;
						}
					}
					staticVo.setNoRegularCount(noRegularCount);
					staticVo.setRegularCount(regularCount);
					staticVo.setTotalPeopleCount(peopleCount);
					staticVo.setTotalCount(trainCount);
					allList.add(staticVo);
				}
			}
			TrainStatisticsVO staticVoAll = new TrainStatisticsVO();
			staticVoAll.setUnitName("合计");
			staticVoAll.setNoRegularCount(allNoRegularCount);
			staticVoAll.setRegularCount(allRegularCount);
			staticVoAll.setTotalPeopleCount(allPeopleCount);
			staticVoAll.setTotalCount(allTrainCount);
			allList.add(staticVoAll);
		}
		return allList;
	}
	
	/**
	 * <p>取得月维度相关时间</p>
	 * @param time1 统计开始时间
	 * @param time2 统计结束时间
	 * @return
	 * @time 2017年6月28日 下午5:48:10
	 */
	private Map<String,Calendar> getMonthDateMap (String time1,String time2) {
		Map<String, Calendar>  returnMap = new HashMap<String, Calendar>(); 
		//统计时间
		Calendar statisticsCal = Calendar.getInstance();
		//开始时间
		Calendar startCal = Calendar.getInstance();
		//结束时间
		Calendar endCal = Calendar.getInstance();
		try {
			DateFormatUtil dfuYM = DateFormatUtil.getInstance("yyyy-MM");
			statisticsCal.setTime(dfuYM.parse(time1));
			startCal.setTime(statisticsCal.getTime());
	
			endCal.setTime(dfuYM.parse(time2));
			endCal.add(Calendar.MONTH, 1);
			endCal.add(Calendar.SECOND, -1);

		} catch (ParseException e) {
			
		}
		
		returnMap.put("startTime", startCal);
		returnMap.put("endTime", endCal);
		
		return returnMap;
	}
	
	/**
	 * <p>取得季度维度相关时间</p>
	 * @param quarter1 季度开始时间
	 * @param quarter2 季度结束时间
	 * @param time1 年维度时间
	 * @return
	 * @time 2017年6月28日 下午5:48:10
	 */
	private Map<String,Calendar> getQuarterDateMap (String time1, String quarter1,String quarter2) {
		Map<String, Calendar>  returnMap = new HashMap<String, Calendar>(); 
		
		//统计时间
		Calendar statisticsCal = Calendar.getInstance();
		//开始时间
		Calendar startCal = Calendar.getInstance();
		//结束时间
		Calendar endCal = Calendar.getInstance();
	
		try {
			DateFormatUtil dfuYM = DateFormatUtil.getInstance("yyyy");
			statisticsCal.setTime(dfuYM.parse(time1));
			//开始季度时间取值
			if ("1".equals(quarter1)) {
				//第一季度
				startCal.setTime(statisticsCal.getTime());
				startCal.set(Calendar.MONTH, Calendar.JANUARY);
			} else if ("2".equals(quarter1)) {
				//第二季度
				startCal.setTime(statisticsCal.getTime());
				startCal.set(Calendar.MONTH, Calendar.APRIL);
			} else if ("3".equals(quarter1)) {
				//第三季度
				startCal.setTime(statisticsCal.getTime());
				startCal.set(Calendar.MONTH, Calendar.JULY);
				
			} else if ("4".equals(quarter1)) {
				//第四季度
				startCal.setTime(statisticsCal.getTime());
				startCal.set(Calendar.MONTH, Calendar.OCTOBER);
			}
			//结束季度时间取值
			if ("1".equals(quarter2)) {
				//第一季度
				endCal.setTime(dfuYM.parse(time1));
				endCal.set(Calendar.MONTH, Calendar.MARCH);
				endCal.add(Calendar.MONTH, 1);
				endCal.add(Calendar.SECOND, -1);
			} else if ("2".equals(quarter2)) {
				//第二季度
				endCal.setTime(dfuYM.parse(time1));
				endCal.set(Calendar.MONTH, Calendar.JUNE);
				endCal.add(Calendar.MONTH, 1);
				endCal.add(Calendar.SECOND, -1);
			} else if ("3".equals(quarter2)) {
				//第三季度
				endCal.setTime(dfuYM.parse(time1));
				endCal.set(Calendar.MONTH, Calendar.SEPTEMBER);
				endCal.add(Calendar.MONTH, 1);
				endCal.add(Calendar.SECOND, -1);
				
			} else if ("4".equals(quarter2)) {
				//第四季度
				endCal.setTime(dfuYM.parse(time1));
				endCal.set(Calendar.MONTH, Calendar.DECEMBER);
				endCal.add(Calendar.MONTH, 1);
				endCal.add(Calendar.SECOND, -1);
			}
		} catch (ParseException e) {
			
		}
		
	
		returnMap.put("startTime", startCal);
		returnMap.put("endTime", endCal);
		
		return returnMap;
	}
	
	/**
	 * <p>取得年维度相关时间</p>
	 * @param time 统计时间
	 * @return
	 * @time 2017年6月28日 下午5:48:10
	 */
	private Map<String,Calendar> getYearDateMap (String time1,String time2) {
		Map<String, Calendar>  returnMap = new HashMap<String, Calendar>(); 
		
		//统计时间
		Calendar statisticsCal = Calendar.getInstance();
		//开始时间
		Calendar startCal = Calendar.getInstance();
		//结束时间
		Calendar endCal = Calendar.getInstance();
	
		try {
			DateFormatUtil dfuYM = DateFormatUtil.getInstance("yyyy");
			statisticsCal.setTime(dfuYM.parse(time1));
			
			startCal.setTime(dfuYM.parse(time1));
			endCal.setTime(dfuYM.parse(time2));
			endCal.add(Calendar.YEAR, 1);
			endCal.add(Calendar.SECOND, -1);
		} catch (ParseException e) {
			
		}
		
		returnMap.put("startTime", startCal);
		returnMap.put("endTime", endCal);
		
		return returnMap;
	}
	
	/**
	* 取两个月份时间段内所有月份
	* @author ly
	* @date 2018年3月22日 下午5:50:33 
	* @param beginDate
	* @param endDate
	* @return
	* @return List<Date>
	*/
	public List<Date> getMonthsBetweenTwoDate(Date beginDate,
			Date endDate) {
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(beginDate);// 把开始时间加入集合
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(beginDate);
		boolean bContinue = true;
		while (bContinue) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cal.add(Calendar.MONTH, 1);
			// 测试此日期是否在指定日期之后
			if (endDate.after(cal.getTime())) {
				lDate.add(cal.getTime());
			} else {
				break;
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM"); 
		boolean lastIndex = false;
		for(Date time:lDate){
		    if(sdf.format(time).equals(sdf.format(endDate))){
		        lastIndex = true;
		        break;
		    }
		}
		if(lastIndex == false){
		    lDate.add(endDate);
		}
		return lDate;
	}
	
	/**
	* 取两个年份时间段内所有年份
	* @author ly
	* @date 2018年3月22日 下午5:51:42 
	* @param beginDate
	* @param endDate
	* @return
	* @return List<Date>
	*/
	public List<Date> getYearsBetweenTwoDate(Date beginDate,
			Date endDate) {
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(beginDate);// 把开始时间加入集合
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(beginDate);
		boolean bContinue = true;
		while (bContinue) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cal.add(Calendar.YEAR, 1);
			// 测试此日期是否在指定日期之后
			if (endDate.after(cal.getTime())) {
				lDate.add(cal.getTime());
			} else {
				break;
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); 
		boolean lastIndex = false;
		for(Date time:lDate){
		    if(sdf.format(time).equals(sdf.format(endDate))){
		        lastIndex = true;
		        break;
		    }
		}
		if(lastIndex == false){
		    lDate.add(endDate);
		}
		return lDate;
	}
	
	
	/**
	* 取得两个季度时间段内所有季度
	* @author ly
	* @date 2018年3月23日 上午8:36:46 
	* @param beginDate
	* @param endDate
	* @return
	* @return List<Date>
	*/
	public List<String> getQuarterBetweenTwoDate(String beginDate,
			String endDate) {
		int beginQuarter = Integer.valueOf(beginDate);
		int endQuarter = Integer.valueOf(endDate);
		List<String> lDate = new ArrayList<String>();
		for(;beginQuarter<endQuarter;beginQuarter++){
			lDate.add(String.valueOf(beginQuarter));
		}
		lDate.add(String.valueOf(endQuarter));
		return lDate;
	}
	
}