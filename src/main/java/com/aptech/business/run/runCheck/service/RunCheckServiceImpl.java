package com.aptech.business.run.runCheck.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aptech.business.run.runCheck.dao.RunCheckDao;
import com.aptech.business.run.runCheck.domain.RunCheckEntity;
import com.aptech.business.run.runCheck.domain.RunCheckEnum;
import com.aptech.business.run.runLog.dao.RunLogDao;
import com.aptech.business.run.runLog.domain.RunLogEntity;
import com.aptech.common.system.duties.dao.SysDutiesDao;
import com.aptech.common.system.duties.domain.SysDutiesEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 运行检查应用管理服务实现类
 *
 * @author 
 * @param <O>
 * @created 2017-09-08 15:00:42
 * @lastModified 
 * @history
 *
 */
@Service("runCheckService")
@Transactional
public class RunCheckServiceImpl extends AbstractBaseEntityOperation<RunCheckEntity> implements RunCheckService {
	
	@Autowired
	private RunCheckDao runCheckDao;
	@Autowired
	private RunLogDao runLogDao;
	@Autowired
	private SysDutiesDao sysDutiesDao;
	
	@Override
	public IBaseEntityOperation<RunCheckEntity> getDao() {
		return runCheckDao;
	}
	
	public int merge = 0;
	
	/**
	 * @Description: 检查结果统计
	 * @author wangcc
	 * @Date 2017年9月12日 上午10:44:00
	 */
	@SuppressWarnings("unchecked")
	public <O> List<O> statistics(Map<String, Object> params,List<int[]> list){
		//默认月份字符(yyyy-MM)
		String defCurMonthString = DateFormatUtil.getInstance("yyyy-MM").format(Calendar.getInstance().getTime());   
		List<Map<String, Object>> queryParams = (List<Map<String, Object>>) params.get("conditions");
		List<O> resultList =new ArrayList<O>();
		//集控中心主任条件集合
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		//集控中心专责条件集合
		List<Condition> cdscMCDResponsibility = new ArrayList<Condition>();
		//合计集合
		List<Condition> cMonthTotal = new ArrayList<Condition>();
		//XX月运行日志数量、检查结果数量条件集合
		List<Condition> conditionsForCM = new ArrayList<Condition>();
		//检查结果统计条件集合
		List<Condition> conditionsForSearchType = new ArrayList<Condition>();
		String[] type = {RunCheckEnum.CENTRALIZEDRESPONSIBILITY.getName(),RunCheckEnum.CENTRALIZEDDIRECTOR.getName()};
		//xx月查询
		boolean flag0 = true,flag1 = true;
		String curMonthString = defCurMonthString;
	    //判断条件中是否存在月份、检查统计类型
	    for (Map<String, Object> param : queryParams){
			if(param.get("field").equals("date_format(L.C_GIVE_DATE,\"%Y-%m\")")){
				curMonthString = param.get("value").toString();
				conditionsForCM.add(new Condition("date_format(L.C_GIVE_DATE,\"%Y-%m\")", FieldTypeEnum.DATE, MatchTypeEnum.EQ, param.get("value")));
				cdscMCDResponsibility.add(new Condition("date_format(L.C_GIVE_DATE,\"%Y-%m\")", FieldTypeEnum.DATE, MatchTypeEnum.EQ, param.get("value")));
				cMonthTotal.add(new Condition("date_format(L.C_GIVE_DATE,\"%Y-%m\")", FieldTypeEnum.DATE, MatchTypeEnum.EQ, param.get("value")));
				flag0 = false;
			}
			if(param.get("field").equals("SD.C_CODE")){
				String C_CODE = param.get("value").toString();
				String[] codeArray = C_CODE.split(",");
				conditions.add(new Condition("SD.C_CODE", FieldTypeEnum.INT, MatchTypeEnum.IN,codeArray));
				conditionsForSearchType.add(new Condition("SD.C_CODE", FieldTypeEnum.INT, MatchTypeEnum.IN,codeArray));
				cdscMCDResponsibility.add(new Condition("SD.C_CODE", FieldTypeEnum.INT, MatchTypeEnum.IN,codeArray));
				cMonthTotal.add(new Condition("SD.C_CODE", FieldTypeEnum.INT, MatchTypeEnum.IN,codeArray));
				flag1 = false;
			}
		}
	    //工作检查结果统计
	    if(flag1){
	    	conditions.add(new Condition("SD.C_CODE", FieldTypeEnum.INT, MatchTypeEnum.NE, RunCheckEnum.RUNTYPEALL.getId()));
	    	conditionsForSearchType.add(new Condition("SD.C_CODE", FieldTypeEnum.INT, MatchTypeEnum.NE, RunCheckEnum.RUNTYPEALL.getId()));
			cdscMCDResponsibility.add(new Condition("SD.C_CODE", FieldTypeEnum.INT, MatchTypeEnum.NE, RunCheckEnum.RUNTYPEALL.getId()));
			cMonthTotal.add(new Condition("SD.C_CODE", FieldTypeEnum.INT, MatchTypeEnum.NE, RunCheckEnum.RUNTYPEALL.getId()));
	    }
	    conditionsForSearchType.add(new Condition("SD.C_CATEGORY_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, RunCheckEnum.WORK_RECORD_TYPE.getCode()));
	    //运行类型列表-集控中心主任
	    List<RunCheckEntity>  checkEntitiesForCentralizedDirector = runCheckDao.findByCondition("findSearchType", conditionsForSearchType, null);
	    //克隆运行类型列表-集控中心专责
	    List<RunCheckEntity> checkEntitiesForCentralizedDirectorResponsibility = new ArrayList<RunCheckEntity>();
	    for(RunCheckEntity checkEntity:checkEntitiesForCentralizedDirector){
	    	RunCheckEntity entity = checkEntity.cloneForUpdate();
	    	checkEntitiesForCentralizedDirectorResponsibility.add(entity);
	    }
	    //运行类型列表-集控中心主任、专责
	    List<RunCheckEntity> cMtotal = new ArrayList<RunCheckEntity>();
	    for(RunCheckEntity checkEntity:checkEntitiesForCentralizedDirector){
	    	RunCheckEntity entity = checkEntity.cloneForUpdate();
	    	cMtotal.add(entity);
	    }
	    
	    //默认时间为当前月份
	    if(flag0){
	    	conditions.add(new Condition("date_format(L.C_GIVE_DATE,\"%Y-%m\")", FieldTypeEnum.DATE, MatchTypeEnum.EQ, defCurMonthString));
	    	conditionsForCM.add(new Condition("date_format(L.C_GIVE_DATE,\"%Y-%m\")", FieldTypeEnum.DATE, MatchTypeEnum.EQ, defCurMonthString));
			cdscMCDResponsibility.add(new Condition("date_format(L.C_GIVE_DATE,\"%Y-%m\")", FieldTypeEnum.DATE, MatchTypeEnum.EQ, defCurMonthString));
			cMonthTotal.add(new Condition("date_format(L.C_GIVE_DATE,\"%Y-%m\")", FieldTypeEnum.DATE, MatchTypeEnum.EQ, defCurMonthString));
	    }
	    
		//统计XX月运行日志数量
	    List<RunLogEntity> cMonthLogEntities = runLogDao.findByCondition("findCountByCondition", conditionsForCM, null);
	    //统计XX月检查结果数量
	    List<RunCheckEntity> cMonthRunCheckEntities = runCheckDao.findByCondition("findCountWithRunCheck",conditionsForCM, null);
	    
//		conditions.add(new Condition("D.C_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, RunCheckEnum.CENTRALIZEDDIRECTOR.getName()));
		//XX月数据(集控中心主任)
		List<RunCheckEntity> cMonthCentralizedDirector = runCheckDao.findByCondition("findByConditionStatistics", conditions, null);
//		cdscMCDResponsibility.add(new Condition("D.C_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, RunCheckEnum.CENTRALIZEDRESPONSIBILITY.getName()));
		//XX月数据(集控中心专责)
		List<RunCheckEntity> cMonthCentralizedDirectorResponsibility = runCheckDao.findByCondition("findByConditionStatistics", cdscMCDResponsibility, null);
//		cMonthTotal.add(new Condition("D.C_NAME", FieldTypeEnum.STRING, MatchTypeEnum.IN, type));
		//XX月合计
		List<RunCheckEntity> cMtotalCheckEntities =  runCheckDao.findByCondition("findByConditionStatistics", cMonthTotal, null);
		//1-xx月
		boolean flag3 = true,flag4 = true;
		//
		List<Condition> conditionsForOtoCd = new ArrayList<Condition>();
		List<Condition> conditionsForOtoCdr = new ArrayList<Condition>();
		List<Condition> oToMTotal = new ArrayList<Condition>();
		for (Map<String, Object> param : queryParams){
			if(param.get("field").equals("date_format(L.C_GIVE_DATE,\"%Y-%m\")")){
				param.put("matchType", MatchTypeEnum.LE);
				conditionsForOtoCd.add(new Condition("date_format(L.C_GIVE_DATE,\"%Y-%m\")", FieldTypeEnum.DATE, MatchTypeEnum.LE, param.get("value")));
				conditionsForOtoCdr.add(new Condition("date_format(L.C_GIVE_DATE,\"%Y-%m\")", FieldTypeEnum.DATE, MatchTypeEnum.LE, param.get("value")));
				oToMTotal.add(new Condition("date_format(L.C_GIVE_DATE,\"%Y-%m\")", FieldTypeEnum.DATE, MatchTypeEnum.LE, param.get("value")));
				flag3 = false;
			}
		}
		//默认当前月份
		if(flag3){
			conditionsForOtoCd.add(new Condition("date_format(L.C_GIVE_DATE,\"%Y-%m\")", FieldTypeEnum.DATE, MatchTypeEnum.LE, defCurMonthString));
			conditionsForOtoCdr.add(new Condition("date_format(L.C_GIVE_DATE,\"%Y-%m\")", FieldTypeEnum.DATE, MatchTypeEnum.LE, defCurMonthString));
			oToMTotal.add(new Condition("date_format(L.C_GIVE_DATE,\"%Y-%m\")", FieldTypeEnum.DATE, MatchTypeEnum.LE, defCurMonthString));
		}
		conditionsForOtoCd.add(new Condition("date_format(L.C_GIVE_DATE,\"%Y-%m\")", FieldTypeEnum.DATE, MatchTypeEnum.GE, defCurMonthString.substring(0,5)+"01"));
		conditionsForOtoCdr.add(new Condition("date_format(L.C_GIVE_DATE,\"%Y-%m\")", FieldTypeEnum.DATE, MatchTypeEnum.GE, defCurMonthString.substring(0,5)+"01"));
		oToMTotal.add(new Condition("date_format(L.C_GIVE_DATE,\"%Y-%m\")", FieldTypeEnum.DATE, MatchTypeEnum.GE, defCurMonthString.substring(0,5)+"01"));
		
		//统计1月-XX月份运行日志数量
		List<RunLogEntity> oTocMonthLogEntities = runLogDao.findByCondition("findCountByCondition", conditionsForOtoCd, null);
		//统计1月-XX月份检查结果数量
		List<RunCheckEntity> oTocMonthRunCheckEntities = runCheckDao.findByCondition("findCountWithRunCheck",conditionsForOtoCd, null);
		
		//判断检查统计类型
		for (Map<String, Object> param : queryParams){
			if(param.get("field").equals("SD.C_CODE")){
				String C_CODE = param.get("value").toString();
				String[] codeArray = C_CODE.split(",");
				conditionsForOtoCd.add(new Condition("SD.C_CODE", FieldTypeEnum.INT, MatchTypeEnum.IN,codeArray));
				conditionsForOtoCdr.add(new Condition("SD.C_CODE", FieldTypeEnum.INT, MatchTypeEnum.IN,codeArray));
				oToMTotal.add(new Condition("SD.C_CODE", FieldTypeEnum.INT, MatchTypeEnum.IN,codeArray));
				flag4 = false;
			}
		}
	    //默认全部工作类型
	    if(flag4){
	    	conditionsForOtoCd.add(new Condition("SD.C_CODE", FieldTypeEnum.INT, MatchTypeEnum.NE, RunCheckEnum.RUNTYPEALL.getId()));
	    	conditionsForOtoCdr.add(new Condition("SD.C_CODE", FieldTypeEnum.INT, MatchTypeEnum.NE, RunCheckEnum.RUNTYPEALL.getId()));
	    	oToMTotal.add(new Condition("SD.C_CODE", FieldTypeEnum.INT, MatchTypeEnum.NE, RunCheckEnum.RUNTYPEALL.getId()));
	    }
//	    conditionsForOtoCd.add(new Condition("D.C_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, RunCheckEnum.CENTRALIZEDDIRECTOR.getName()));
		//1月-XX月数据(集控中心主任)
		List<RunCheckEntity> oToCurrentMonthCentralizedDirector = runCheckDao.findByCondition("findByConditionStatistics", conditionsForOtoCd, null);
//		conditionsForOtoCdr.add(new Condition("D.C_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, RunCheckEnum.CENTRALIZEDRESPONSIBILITY.getName()));
		//1月-XX月数据(集控中心专责)
		List<RunCheckEntity> oTcMonthCentralizedDirectorResponsibility = runCheckDao.findByCondition("findByConditionStatistics", conditionsForOtoCdr, null);
//		oToMTotal.add(new Condition("D.C_NAME", FieldTypeEnum.STRING, MatchTypeEnum.IN, type));
		//1-XX月合计
		List<RunCheckEntity> cToMtotalCheckEntities =  runCheckDao.findByCondition("findByConditionStatistics", oToMTotal, null);
		//本月运行记录、检查记录数量、1月-本月运行记录、检查数量
		int cMRLCount = 0,cMCKCount = 0,oToCMRLCount = 0,oToCMCKCount = 0;
		if(!cMonthLogEntities.isEmpty()){
			cMRLCount = cMonthLogEntities.size();
		}
		if(!cMonthRunCheckEntities.isEmpty()){
			cMCKCount = cMonthRunCheckEntities.size();
		}
		if(!oTocMonthLogEntities.isEmpty()){
			oToCMRLCount = oTocMonthLogEntities.size();
		}
		if(!oTocMonthRunCheckEntities.isEmpty()){
			oToCMCKCount = oTocMonthRunCheckEntities.size();
		}
	
		//存储合并开始、结束行号、开始、结束列号
		//集控中心主任数据处理
		//合并数据
		mergeData(resultList,checkEntitiesForCentralizedDirector,cMonthCentralizedDirector,oToCurrentMonthCentralizedDirector,cMRLCount,cMCKCount,oToCMRLCount,oToCMCKCount,RunCheckEnum.CENTRALIZEDDIRECTOR.getName(),list,curMonthString,1);
		//集控中心专责数据处理
		//合并数据
		mergeData(resultList,checkEntitiesForCentralizedDirectorResponsibility,cMonthCentralizedDirectorResponsibility,oTcMonthCentralizedDirectorResponsibility,cMRLCount,cMCKCount,oToCMRLCount,oToCMCKCount,RunCheckEnum.CENTRALIZEDRESPONSIBILITY.getName(),list,curMonthString,2);
		//集控中心主任、专责数据处理
		//合并数据
		mergeData(resultList,cMtotal,cMtotalCheckEntities,cToMtotalCheckEntities,cMRLCount,cMCKCount,oToCMRLCount,oToCMCKCount,RunCheckEnum.TOTAL.getName(),list,curMonthString,3);
		merge = 0;
		return resultList;
	}
	
	
	
	/**
	 * @Description: 合并数据
	 * @author wangcc
	 * @param <O>
	 * @Date 2017年9月12日 上午10:44:00
	 */
	@SuppressWarnings("unchecked")
	public <O> void mergeData(List<O> targetEntityList,List<RunCheckEntity> source0,List<RunCheckEntity> cMonthData,List<RunCheckEntity> oToCMonthData,int cMRLCount,int cMCKCount,int oToCMRLCount,int oToCMCKCount,String duty,List<int[]> list,String curMonth,int type) {
		List<Condition> conditions = new ArrayList<Condition>();
		if(type==1){
			//过滤XX月数据
			conditions.add(new Condition("D.C_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, RunCheckEnum.CENTRALIZEDDIRECTOR.getName()));
			List<SysDutiesEntity> dutiesEntities = sysDutiesDao.findByCondition("findByInfo", conditions, null);
			cMonthData = executeData(cMonthData,dutiesEntities);
			//过滤1-XX数据
			oToCMonthData = executeData(oToCMonthData,dutiesEntities);
		} 
		if(type==2){
			//过滤XX月数据
			conditions.add(new Condition("D.C_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, RunCheckEnum.CENTRALIZEDRESPONSIBILITY.getName()));
			List<SysDutiesEntity> entities = sysDutiesDao.findByCondition("findByInfo", conditions, null);
			cMonthData = executeData(cMonthData,entities);
			//过滤1-XX数据
			oToCMonthData = executeData(oToCMonthData,entities);
		}
		if(type==3){
			//过滤XX月数据
			conditions.add(new Condition("(D.C_NAME ='"+RunCheckEnum.CENTRALIZEDDIRECTOR.getName()+"' OR D.C_NAME ='"+RunCheckEnum.CENTRALIZEDRESPONSIBILITY.getName()+"')"));
			List<SysDutiesEntity> entities = sysDutiesDao.findByCondition("findByInfo", conditions, null);
			cMonthData = executeData(cMonthData,entities);
			//过滤1-XX数据
			oToCMonthData = executeData(oToCMonthData,entities);
		}
		int count=0;
		for(RunCheckEntity targetEntity:source0){
			targetEntity.setCurMonth(curMonth.substring(5,7));
			targetEntity.setJanuary("01");
			targetEntity.setCheckResult(RunCheckEnum.ZERO.getId().toString());
			targetEntity.setRecordCount(RunCheckEnum.ZERO.getId().toString());
			targetEntity.setCheckRecordCount(RunCheckEnum.ZERO.getId().toString());
			targetEntity.setCheckCount(RunCheckEnum.ZERO.getId().toString());
			targetEntity.setUnCheckCount(RunCheckEnum.ZERO.getId().toString());
			targetEntity.setQualiFiedCount(RunCheckEnum.ZERO.getId().toString());
			targetEntity.setUnQualiFiedCount(RunCheckEnum.ZERO.getId().toString());
			targetEntity.setCheckRate(RunCheckEnum.ZERO.getId().toString());
			targetEntity.setUnCheckRate(RunCheckEnum.ZERO.getId().toString());
			targetEntity.setQualiFiedRate(RunCheckEnum.ZERO.getId().toString());
			targetEntity.setUnQualiFiedRate(RunCheckEnum.ZERO.getId().toString());
			targetEntity.setoTcCheckResult(RunCheckEnum.ZERO.getId().toString());
			targetEntity.setoTcRecordCount(RunCheckEnum.ZERO.getId().toString());
			targetEntity.setoTcCheckRecordCount(RunCheckEnum.ZERO.getId().toString());
			targetEntity.setoTcCheckCount(RunCheckEnum.ZERO.getId().toString());
			targetEntity.setoTcUnCheckCount(RunCheckEnum.ZERO.getId().toString());
			targetEntity.setoTcQualiFiedCount(RunCheckEnum.ZERO.getId().toString());
			targetEntity.setoTcUnQualiFiedCount(RunCheckEnum.ZERO.getId().toString());
			targetEntity.setoTcCheckRate(RunCheckEnum.ZERO.getId().toString());
			targetEntity.setoTcUnCheckRate(RunCheckEnum.ZERO.getId().toString());
			targetEntity.setoTcQualiFiedRate(RunCheckEnum.ZERO.getId().toString());
			targetEntity.setoTcUnQualiFiedRate(RunCheckEnum.ZERO.getId().toString());
			int tempCheckCount = 0,tempCheckResult = 0;
			for(RunCheckEntity sourceEntity1:cMonthData){
				//如果工作纪录类型相同
				if(targetEntity.getRunTypeCode()==sourceEntity1.getRunTypeCode()){
					//本月数据
					//运行日志数量
					targetEntity.setRecordCount(String.valueOf(cMRLCount));
					//检查记录数量
					targetEntity.setCheckRecordCount(String.valueOf(cMCKCount));
					//检查数量
					tempCheckCount += Integer.parseInt(sourceEntity1.getCheckCount());
					targetEntity.setCheckCount(tempCheckCount+"");
					//检查结果(合格次数)
					tempCheckResult += Integer.parseInt(sourceEntity1.getCheckResult());
					targetEntity.setCheckResult(tempCheckResult+"");
					//检查率(检查次数/运行日志数量)
					if(!StringUtils.equals(String.valueOf(targetEntity.getRecordCount()),"0")){
						//如果大于1则比率为100%
						if(new BigDecimal(targetEntity.getCheckCount()).compareTo(new BigDecimal(cMRLCount)) == 1){
							targetEntity.setCheckRate(RunCheckEnum.ZERO.getId().toString());
						}else{
							targetEntity.setCheckRate(new BigDecimal(targetEntity.getCheckCount()).multiply(new BigDecimal(100)).divide(new BigDecimal(cMRLCount),2,BigDecimal.ROUND_HALF_UP).toString());
						}
					}else{
						targetEntity.setCheckRate("0.00");
					}
					//未检查次数(运行日志数量-检查次数)
					if(cMRLCount < Integer.parseInt(targetEntity.getCheckCount())){
						targetEntity.setUnCheckCount("0");
					}else{
						targetEntity.setUnCheckCount(String.valueOf(cMRLCount - Integer.parseInt(targetEntity.getCheckCount())));
					}
					//未查率(未检查次数/运行日志数量)
					if(cMRLCount!=0){
						if(new BigDecimal(targetEntity.getUnCheckCount()).compareTo(new BigDecimal(cMRLCount)) == 1){
							targetEntity.setUnCheckRate(RunCheckEnum.ZERO.getId().toString());
						}else{
							targetEntity.setUnCheckRate(new BigDecimal(targetEntity.getUnCheckCount()).multiply(new BigDecimal(100)).divide(new BigDecimal(cMRLCount),2,BigDecimal.ROUND_HALF_UP).toString());
						}
					}else{
						targetEntity.setUnCheckRate("0.00");
					}
					//合格次数
					targetEntity.setQualiFiedCount(targetEntity.getCheckResult());
					//合格率(合格次数/运行检查记录数量)
					if(cMCKCount!=0){
						if(new BigDecimal(targetEntity.getQualiFiedCount()).compareTo(new BigDecimal(cMCKCount)) == 1){
							targetEntity.setQualiFiedRate(RunCheckEnum.ZERO.getId().toString());
						}else{
							targetEntity.setQualiFiedRate(new BigDecimal(targetEntity.getQualiFiedCount()).multiply(new BigDecimal(100)).divide(new BigDecimal(cMCKCount),2,BigDecimal.ROUND_HALF_UP).toString());
						}
					}else{
						targetEntity.setQualiFiedRate("0.00");
					}
					//不合格次数(检查次数-合格次数)
					if(!StringUtils.equals(targetEntity.getCheckCount(),"0")){
						targetEntity.setUnQualiFiedCount(String.valueOf(Integer.parseInt(targetEntity.getCheckCount()) - Integer.parseInt(targetEntity.getQualiFiedCount())));
					}else{
						targetEntity.setUnQualiFiedRate("0");
					}
					//不合格率(不合格次数/运行检查记录数量)
					if(cMCKCount!=0){
						if(new BigDecimal(targetEntity.getUnQualiFiedCount()).compareTo(new BigDecimal(cMCKCount)) == 1){
							targetEntity.setUnQualiFiedRate(RunCheckEnum.ZERO.getId().toString());
						}else{
							targetEntity.setUnQualiFiedRate(new BigDecimal(targetEntity.getUnQualiFiedCount()).multiply(new BigDecimal(100)).divide(new BigDecimal(cMCKCount),2,BigDecimal.ROUND_HALF_UP).toString());
						}
					}else{
						targetEntity.setUnQualiFiedRate("0.00");
					}
				}
			}
			int tempoToCheckCount = 0,tempoToCheckResult = 0;
			//1月到本月数据
			for(RunCheckEntity sourceEntity2:oToCMonthData){
				if(targetEntity.getRunTypeCode()==sourceEntity2.getRunTypeCode()){
					//职务
					targetEntity.setDuties(sourceEntity2.getDuties());
					//运行日志数量
					targetEntity.setoTcRecordCount(oToCMRLCount+"");
					//检查记录数量
					targetEntity.setoTcCheckRecordCount(oToCMCKCount+"");
					//检查数量
					tempoToCheckCount += Integer.parseInt(sourceEntity2.getCheckCount());
					targetEntity.setoTcCheckCount(tempoToCheckCount+"");
					//检查结果(合格次数)
					tempoToCheckResult += Integer.parseInt(sourceEntity2.getCheckCount());
					targetEntity.setoTcCheckResult(tempoToCheckResult+"");
					//检查率(检查次数/运行日志数量)
					if(!StringUtils.equals(targetEntity.getoTcRecordCount(),"0")){
						if(new BigDecimal(targetEntity.getoTcCheckCount()).compareTo(new BigDecimal(oToCMRLCount)) == 1){
							targetEntity.setoTcCheckRate(RunCheckEnum.ZERO.getId().toString());
						}else{
							targetEntity.setoTcCheckRate(new BigDecimal(targetEntity.getoTcCheckCount()).multiply(new BigDecimal(100)).divide(new BigDecimal(oToCMRLCount),2,BigDecimal.ROUND_HALF_UP).toString());
						}
					}else{
						targetEntity.setoTcCheckRate("0.00");
					}
					//未检查次数(运行日志数量-检查次数)
					if(oToCMRLCount < Integer.parseInt(targetEntity.getoTcCheckCount())){
						targetEntity.setoTcUnCheckCount("0");
					}else{
						targetEntity.setoTcUnCheckCount(String.valueOf(oToCMRLCount - Integer.parseInt(targetEntity.getoTcCheckCount())));
					}
					//未查率(未检查次数/运行日志数量)
					if(cMRLCount!=0){
						if(new BigDecimal(targetEntity.getoTcUnCheckCount()).compareTo(new BigDecimal(oToCMRLCount)) == 1){
							targetEntity.setoTcUnCheckRate(RunCheckEnum.ZERO.getId().toString());
						}else{
							targetEntity.setoTcUnCheckRate(new BigDecimal(targetEntity.getoTcUnCheckCount()).multiply(new BigDecimal(100)).divide(new BigDecimal(oToCMRLCount),2,BigDecimal.ROUND_HALF_UP).toString());
						}
					}else{
						targetEntity.setoTcUnCheckRate("0.00");
					}
					//合格次数
					targetEntity.setoTcQualiFiedCount(targetEntity.getoTcCheckResult());
					//合格率(合格次数/运行检查记录数量)
					if(oToCMCKCount!=0){
						if(new BigDecimal(targetEntity.getoTcQualiFiedCount()).compareTo(new BigDecimal(oToCMCKCount)) == 1){
							targetEntity.setoTcQualiFiedRate(RunCheckEnum.ZERO.getId().toString());
						}else{
							targetEntity.setoTcQualiFiedRate(new BigDecimal(targetEntity.getoTcQualiFiedCount()).multiply(new BigDecimal(100)).divide(new BigDecimal(oToCMCKCount),2,BigDecimal.ROUND_HALF_UP).toString());
						}
					}else{
						targetEntity.setoTcQualiFiedRate("0.00");
					}
					//不合格次数(检查次数-合格次数)
					if(!StringUtils.equals(targetEntity.getoTcCheckCount(),"0")){
						targetEntity.setoTcUnQualiFiedCount(String.valueOf(Integer.parseInt(targetEntity.getoTcCheckCount()) - Integer.parseInt(targetEntity.getoTcQualiFiedCount())));
					}else{
						targetEntity.setoTcUnQualiFiedRate("0");
					}
					//不合格率(不合格次数/运行检查记录数量)
					if(cMCKCount!=0){
						if(new BigDecimal(targetEntity.getoTcUnQualiFiedCount()).compareTo(new BigDecimal(oToCMCKCount)) == 1){
							targetEntity.setoTcUnQualiFiedRate(RunCheckEnum.ZERO.getId().toString());
						}else{
							targetEntity.setoTcUnQualiFiedRate(new BigDecimal(targetEntity.getoTcUnQualiFiedCount()).multiply(new BigDecimal(100)).divide(new BigDecimal(oToCMCKCount),2,BigDecimal.ROUND_HALF_UP).toString());
						}
					}else{
						targetEntity.setoTcUnQualiFiedRate("0.00");
					}
				}
			}
			
			if(count==0){//第一行显示
				int mergeRowNumber = merge+source0.size();
				//职务
				targetEntity.setDuties(duty);
				targetEntity.setRowspanNum(String.valueOf(mergeRowNumber));
				targetEntity.setTdHide("show");
				if(list!=null){
					if(list.isEmpty()){
						list.add(new int[]{3,mergeRowNumber+3-1,0,0});
					}else{
						list.add(new int[]{merge,mergeRowNumber-1,0,0});
					}
					if(merge==0){
						merge = mergeRowNumber+3;
					}else{
						merge = mergeRowNumber;
					}
				}
			}else{//其它行隐藏
				targetEntity.setTdHide("hide");
			}
			targetEntityList.add((O)targetEntity);
			count++;
		}
	}
	
	/**
	 * @Description:   计算合计(平均值)
	 * @author         wangcc 
	 * @Date           2017年9月14日 下午9:05:03 
	 * @throws         Exception
	 */
	public RunCheckEntity computeTotal(List<RunCheckEntity> list){
		    //本月
			BigDecimal recordCountTotal=new BigDecimal(0);//记录次数合计
			BigDecimal checkCountTotal=new BigDecimal(0);//检查次数合计
			BigDecimal unCheckCountTotal=new BigDecimal(0);//未检查次数合计
			BigDecimal qualiFiedCountTotal=new BigDecimal(0);//合格次数合计
			BigDecimal unQualiFiedCountTotal=new BigDecimal(0);//不合格次数合计
			BigDecimal checkRateTotal=new BigDecimal(0);//检查率合计
			BigDecimal unCheckRateTotal=new BigDecimal(0);//未检查率合计
			BigDecimal qualiFiedRateTotal=new BigDecimal(0);//合格率合计
			BigDecimal unQualiFiedRateTotal=new BigDecimal(0);//不合格率合计
			//1月到本月
			BigDecimal oTcRecordCountTotal=new BigDecimal(0);//记录次数合计
			BigDecimal oTcCheckCountTotal=new BigDecimal(0);//检查次数合计
			BigDecimal oTcUnCheckCountTotal=new BigDecimal(0);//未检查次数合计
			BigDecimal oTcQualiFiedCountTotal=new BigDecimal(0);//合格次数合计
			BigDecimal oTcUnQualiFiedCountTotal=new BigDecimal(0);//不合格次数合计
			BigDecimal oTcCheckRateTotal=new BigDecimal(0);//检查率合计
			BigDecimal oTcUnCheckRateTotal=new BigDecimal(0);//未检查率合计
			BigDecimal oTcQualiFiedRateTotal=new BigDecimal(0);//合格率合计
			BigDecimal oTcUnQualiFiedRateTotal=new BigDecimal(0);//不合格率合计
		
			for (int i=0;i<list.size();i++) {
				RunCheckEntity checkEntityList = list.get(i);
				//本月数据
				if(i==0){
					recordCountTotal = new BigDecimal(checkEntityList.getRecordCount());
				}
				//检查次数合计
				checkCountTotal = checkCountTotal.add(new BigDecimal(checkEntityList.getCheckCount()));
				//未检查次数合计
				unCheckCountTotal = unCheckCountTotal.add(new BigDecimal(checkEntityList.getUnCheckCount()));
				//合格次数统计
				qualiFiedCountTotal = qualiFiedCountTotal.add(new BigDecimal(checkEntityList.getQualiFiedCount()));
				//不合格次数合计
				unQualiFiedCountTotal = unQualiFiedCountTotal.add(new BigDecimal(checkEntityList.getUnQualiFiedCount()));
				//检查率合计
				checkRateTotal = checkRateTotal.add(new BigDecimal(checkEntityList.getCheckRate()));
				//未检查率合计
				unCheckRateTotal = unCheckRateTotal.add(new BigDecimal(checkEntityList.getUnCheckRate()));
				//合格率合计
				qualiFiedRateTotal = qualiFiedRateTotal.add(new BigDecimal(checkEntityList.getQualiFiedRate()));
				//不合格率合计
				unQualiFiedRateTotal = unQualiFiedRateTotal.add(new BigDecimal(checkEntityList.getUnQualiFiedRate()));
				//1月到本月
				//记录次数合计
				if(i==0){
					oTcRecordCountTotal = new BigDecimal(checkEntityList.getoTcRecordCount());
				}
				//检查次数合计
				oTcCheckCountTotal = oTcCheckCountTotal.add(new BigDecimal(checkEntityList.getoTcCheckCount()));
				//未检查次数合计
				oTcUnCheckCountTotal = oTcUnCheckCountTotal.add(new BigDecimal(checkEntityList.getoTcUnCheckCount()));
				//合格次数统计
				oTcQualiFiedCountTotal = oTcQualiFiedCountTotal.add(new BigDecimal(checkEntityList.getoTcQualiFiedCount()));
				//不合格次数合计
				oTcUnQualiFiedCountTotal = oTcUnQualiFiedCountTotal.add(new BigDecimal(checkEntityList.getoTcUnQualiFiedCount()));
				//检查率合计
				oTcCheckRateTotal = oTcCheckRateTotal.add(new BigDecimal(checkEntityList.getoTcCheckRate()));
				//未检查率合计
				oTcUnCheckRateTotal = oTcUnCheckRateTotal.add(new BigDecimal(checkEntityList.getoTcUnCheckRate()));
				//合格率合计
				oTcQualiFiedRateTotal = oTcQualiFiedRateTotal.add(new BigDecimal(checkEntityList.getoTcQualiFiedRate()));
				//不合格率合计
				oTcUnQualiFiedRateTotal = oTcUnQualiFiedRateTotal.add(new BigDecimal(checkEntityList.getoTcUnQualiFiedRate()));
			}
			//计算合计
			RunCheckEntity totalCheckEntity = new RunCheckEntity();
			BigDecimal datasize = new  BigDecimal(list.size());
			totalCheckEntity.setTypeName(RunCheckEnum.TOTAL.getName());
			totalCheckEntity.setCheckResult("0");
			if(list.size()!=0){
				//XX月
				totalCheckEntity.setRecordCount(String.valueOf(recordCountTotal.divide(datasize,2,BigDecimal.ROUND_HALF_UP).toString()));
				totalCheckEntity.setCheckCount(String.valueOf(checkCountTotal.divide(datasize,2,BigDecimal.ROUND_HALF_UP).toString()));
				totalCheckEntity.setUnCheckCount(String.valueOf(unCheckCountTotal.divide(datasize,2,BigDecimal.ROUND_HALF_UP).toString()));
				totalCheckEntity.setQualiFiedCount(String.valueOf(qualiFiedCountTotal.divide(datasize,2,BigDecimal.ROUND_HALF_UP).toString()));
				totalCheckEntity.setUnQualiFiedCount(String.valueOf(unQualiFiedCountTotal.divide(datasize,2,BigDecimal.ROUND_HALF_UP).toString()));
				totalCheckEntity.setCheckRate(String.valueOf(checkRateTotal.divide(datasize,2,BigDecimal.ROUND_HALF_UP).toString()));
				totalCheckEntity.setUnCheckRate(String.valueOf(unCheckRateTotal.divide(datasize,2,BigDecimal.ROUND_HALF_UP).toString()));
				totalCheckEntity.setQualiFiedRate(String.valueOf(qualiFiedRateTotal.divide(datasize,2,BigDecimal.ROUND_HALF_UP).toString()));
				totalCheckEntity.setUnQualiFiedRate(String.valueOf(unQualiFiedRateTotal.divide(datasize,2,BigDecimal.ROUND_HALF_UP).toString()));
				//1月到XX月
				totalCheckEntity.setoTcRecordCount(String.valueOf(oTcRecordCountTotal.divide(datasize,2,BigDecimal.ROUND_HALF_UP).toString()));
				totalCheckEntity.setoTcCheckCount(String.valueOf(oTcCheckCountTotal.divide(datasize,2,BigDecimal.ROUND_HALF_UP).toString()));
				totalCheckEntity.setoTcUnCheckCount(String.valueOf(oTcUnCheckCountTotal.divide(datasize,2,BigDecimal.ROUND_HALF_UP).toString()));
				totalCheckEntity.setoTcQualiFiedCount(String.valueOf(oTcQualiFiedCountTotal.divide(datasize,2,BigDecimal.ROUND_HALF_UP).toString()));
				totalCheckEntity.setoTcUnQualiFiedCount(String.valueOf(oTcUnCheckCountTotal.divide(datasize,2,BigDecimal.ROUND_HALF_UP).toString()));
				totalCheckEntity.setoTcCheckRate(String.valueOf(oTcCheckRateTotal.divide(datasize,2,BigDecimal.ROUND_HALF_UP).toString()));
				totalCheckEntity.setoTcUnCheckRate(String.valueOf(oTcUnCheckRateTotal.divide(datasize,2,BigDecimal.ROUND_HALF_UP).toString()));
				totalCheckEntity.setoTcQualiFiedRate(String.valueOf(oTcQualiFiedRateTotal.divide(datasize,2,BigDecimal.ROUND_HALF_UP).toString()));
				totalCheckEntity.setoTcUnQualiFiedRate(String.valueOf(oTcUnQualiFiedRateTotal.divide(datasize,2,BigDecimal.ROUND_HALF_UP).toString()));
			}else{
				//XX月
				totalCheckEntity.setRecordCount(BigDecimal.ZERO.toString());
				totalCheckEntity.setCheckCount(BigDecimal.ZERO.toString());
				totalCheckEntity.setUnCheckCount(BigDecimal.ZERO.toString());
				totalCheckEntity.setQualiFiedCount(BigDecimal.ZERO.toString());
				totalCheckEntity.setUnQualiFiedCount(BigDecimal.ZERO.toString());
				totalCheckEntity.setCheckRate(BigDecimal.ZERO.toString());
				totalCheckEntity.setUnCheckRate(BigDecimal.ZERO.toString());
				totalCheckEntity.setQualiFiedRate(BigDecimal.ZERO.toString());
				totalCheckEntity.setUnQualiFiedRate(BigDecimal.ZERO.toString());
				//1月到XX月
				totalCheckEntity.setoTcRecordCount(BigDecimal.ZERO.toString());
				totalCheckEntity.setoTcCheckCount(BigDecimal.ZERO.toString());
				totalCheckEntity.setoTcUnCheckCount(BigDecimal.ZERO.toString());
				totalCheckEntity.setoTcQualiFiedCount(BigDecimal.ZERO.toString());
				totalCheckEntity.setoTcUnQualiFiedCount(BigDecimal.ZERO.toString());
				totalCheckEntity.setoTcCheckRate(BigDecimal.ZERO.toString());
				totalCheckEntity.setoTcUnCheckRate(BigDecimal.ZERO.toString());
				totalCheckEntity.setoTcQualiFiedRate(BigDecimal.ZERO.toString());
				totalCheckEntity.setoTcUnQualiFiedRate(BigDecimal.ZERO.toString());
			}
			return totalCheckEntity;
	}
	/**
	 * @Description:   计算全部合计(平均值)
	 * @author         wangcc 
	 * @Date           2017年9月26日 下午17:39:03 
	 * @throws         Exception
	 */
	public RunCheckEntity allTotal(RunCheckEntity TotalCheckEntityCentralizedDirector,RunCheckEntity TotalCheckEntityCentralizedDirectorResponsibility){
			RunCheckEntity alltotal = new RunCheckEntity();
			alltotal.setCheckResult(RunCheckEnum.ZERO.getId().toString());
			alltotal.setRecordCount(RunCheckEnum.ZERO.getId().toString());
			alltotal.setCheckRecordCount(RunCheckEnum.ZERO.getId().toString());
			alltotal.setCheckCount(RunCheckEnum.ZERO.getId().toString());
			alltotal.setUnCheckCount(RunCheckEnum.ZERO.getId().toString());
			alltotal.setQualiFiedCount(RunCheckEnum.ZERO.getId().toString());
			alltotal.setUnQualiFiedCount(RunCheckEnum.ZERO.getId().toString());
			alltotal.setCheckRate(RunCheckEnum.ZERO.getId().toString());
			alltotal.setUnCheckRate(RunCheckEnum.ZERO.getId().toString());
			alltotal.setQualiFiedRate(RunCheckEnum.ZERO.getId().toString());
			alltotal.setUnQualiFiedRate(RunCheckEnum.ZERO.getId().toString());
			alltotal.setoTcCheckResult(RunCheckEnum.ZERO.getId().toString());
			alltotal.setoTcRecordCount(RunCheckEnum.ZERO.getId().toString());
			alltotal.setoTcCheckRecordCount(RunCheckEnum.ZERO.getId().toString());
			alltotal.setoTcCheckCount(RunCheckEnum.ZERO.getId().toString());
			alltotal.setoTcUnCheckCount(RunCheckEnum.ZERO.getId().toString());
			alltotal.setoTcQualiFiedCount(RunCheckEnum.ZERO.getId().toString());
			alltotal.setoTcUnQualiFiedCount(RunCheckEnum.ZERO.getId().toString());
			alltotal.setoTcCheckRate(RunCheckEnum.ZERO.getId().toString());
			alltotal.setoTcUnCheckRate(RunCheckEnum.ZERO.getId().toString());
			alltotal.setoTcQualiFiedRate(RunCheckEnum.ZERO.getId().toString());
			alltotal.setoTcUnQualiFiedRate(RunCheckEnum.ZERO.getId().toString());
			//xx月
			alltotal.setRecordCount(String.valueOf(new BigDecimal(TotalCheckEntityCentralizedDirector.getRecordCount()).add(new BigDecimal(TotalCheckEntityCentralizedDirectorResponsibility.getRecordCount()))));
			alltotal.setCheckCount(String.valueOf(new BigDecimal(TotalCheckEntityCentralizedDirector.getCheckCount()).add(new BigDecimal(TotalCheckEntityCentralizedDirectorResponsibility.getCheckCount()))));
			alltotal.setUnCheckCount(String.valueOf(new BigDecimal(TotalCheckEntityCentralizedDirector.getUnCheckCount()).add(new BigDecimal(TotalCheckEntityCentralizedDirectorResponsibility.getUnCheckCount()))));
			alltotal.setQualiFiedCount(String.valueOf(new BigDecimal(TotalCheckEntityCentralizedDirector.getQualiFiedCount()).add(new BigDecimal(TotalCheckEntityCentralizedDirectorResponsibility.getQualiFiedCount()))));
			alltotal.setUnQualiFiedCount(String.valueOf(new BigDecimal(TotalCheckEntityCentralizedDirector.getUnQualiFiedCount()).add(new BigDecimal(TotalCheckEntityCentralizedDirectorResponsibility.getUnQualiFiedCount()))));
			alltotal.setCheckRate(String.valueOf(new BigDecimal(TotalCheckEntityCentralizedDirector.getCheckRate()).add(new BigDecimal(TotalCheckEntityCentralizedDirectorResponsibility.getCheckRate()))));
			alltotal.setUnCheckRate(String.valueOf(new BigDecimal(TotalCheckEntityCentralizedDirector.getUnCheckRate()).add(new BigDecimal(TotalCheckEntityCentralizedDirectorResponsibility.getUnCheckRate()))));
			alltotal.setQualiFiedRate(String.valueOf(new BigDecimal(TotalCheckEntityCentralizedDirector.getQualiFiedRate()).add(new BigDecimal(TotalCheckEntityCentralizedDirectorResponsibility.getQualiFiedRate()))));
			alltotal.setUnQualiFiedRate(String.valueOf(new BigDecimal(TotalCheckEntityCentralizedDirector.getUnQualiFiedRate()).add(new BigDecimal(TotalCheckEntityCentralizedDirectorResponsibility.getUnQualiFiedRate()))));
			//1月到XX月
			alltotal.setoTcRecordCount(String.valueOf(new BigDecimal(TotalCheckEntityCentralizedDirector.getoTcRecordCount()).add(new BigDecimal(TotalCheckEntityCentralizedDirectorResponsibility.getoTcRecordCount()))));
			alltotal.setoTcCheckCount(String.valueOf(new BigDecimal(TotalCheckEntityCentralizedDirector.getoTcCheckCount()).add(new BigDecimal(TotalCheckEntityCentralizedDirectorResponsibility.getoTcCheckCount()))));
			alltotal.setoTcUnCheckCount(String.valueOf(new BigDecimal(TotalCheckEntityCentralizedDirector.getoTcUnCheckCount()).add(new BigDecimal(TotalCheckEntityCentralizedDirectorResponsibility.getoTcUnCheckCount()))));
			alltotal.setoTcQualiFiedCount(String.valueOf(new BigDecimal(TotalCheckEntityCentralizedDirector.getoTcQualiFiedCount()).add(new BigDecimal(TotalCheckEntityCentralizedDirectorResponsibility.getoTcQualiFiedCount()))));
			alltotal.setoTcUnQualiFiedCount(String.valueOf(new BigDecimal(TotalCheckEntityCentralizedDirector.getoTcUnQualiFiedCount()).add(new BigDecimal(TotalCheckEntityCentralizedDirectorResponsibility.getoTcUnQualiFiedCount()))));
			alltotal.setoTcCheckRate(String.valueOf(new BigDecimal(TotalCheckEntityCentralizedDirector.getoTcCheckRate()).add(new BigDecimal(TotalCheckEntityCentralizedDirectorResponsibility.getoTcCheckRate()))));
			alltotal.setoTcUnCheckRate(String.valueOf(new BigDecimal(TotalCheckEntityCentralizedDirector.getoTcUnCheckRate()).add(new BigDecimal(TotalCheckEntityCentralizedDirectorResponsibility.getoTcUnCheckRate()))));
			alltotal.setoTcQualiFiedRate(String.valueOf(new BigDecimal(TotalCheckEntityCentralizedDirector.getoTcQualiFiedRate()).add(new BigDecimal(TotalCheckEntityCentralizedDirectorResponsibility.getoTcQualiFiedRate()))));
			alltotal.setoTcUnQualiFiedRate(String.valueOf(new BigDecimal(TotalCheckEntityCentralizedDirector.getoTcUnQualiFiedRate()).add(new BigDecimal(TotalCheckEntityCentralizedDirectorResponsibility.getoTcUnQualiFiedRate()))));
			return alltotal;
	}


	/**
	 * @Description:   运行检查新增
	 * @author         wangcc 
	 * @Date           2017年10月15日 下午01:00:03 
	 * @throws         Exception
	 */
	@Override
	public @ResponseBody ResultObj addRunCheckInfo(RunCheckEntity runCheckEntity,HttpServletRequest reques) {
		runCheckDao.addEntity(runCheckEntity);
		ResultObj resultObj = new ResultObj();
		resultObj.setData(runCheckEntity);
		return resultObj;
		
	}
	
	/**
	 * @Description:   处理结果集
	 * @author         wangcc 
	 * @Date           2018年7月5日 下午01:00:03 
	 * @throws         Exception
	 */
	public List<RunCheckEntity> executeData(List<RunCheckEntity> dataList,List<SysDutiesEntity> dutiesList){
		List<RunCheckEntity> resultLit = new ArrayList<RunCheckEntity>();
		for(RunCheckEntity checkEntity:dataList){
			for(SysDutiesEntity dutiesEntity:dutiesList){
				if(checkEntity.getCheckPerson()==dutiesEntity.getUserId() && checkEntity.getUserUnit()==dutiesEntity.getUnitId()){
					resultLit.add(checkEntity);
				}
			}
		}
		return resultLit;
	}
}