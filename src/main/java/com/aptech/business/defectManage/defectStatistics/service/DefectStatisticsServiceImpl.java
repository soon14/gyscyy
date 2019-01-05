package com.aptech.business.defectManage.defectStatistics.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.aptech.business.component.dictionary.DefectStatusEnum;
import com.aptech.business.defectManage.defect.domain.DefectEntity;
import com.aptech.business.defectManage.defect.service.DefectService;
import com.aptech.business.defectManage.defectEquipment.domain.DefectEquipmentEntity;
import com.aptech.business.defectManage.defectEquipment.service.DefectEquipmentService;
import com.aptech.business.defectManage.defectStatistics.dao.DefectStatisticsDao;
import com.aptech.business.defectManage.defectStatistics.domain.DefectStatisticsEntity;
import com.aptech.common.system.dictionary.domain.SysDictionaryEntity;
import com.aptech.common.system.dictionary.service.SysDictionaryService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.domain.UnitLevelEnum;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.StringUtil;

/**
 * 
 * 缺陷统计应用管理服务实现类
 *
 * @author 
 * @created 2017-06-09 09:12:26
 * @lastModified 
 * @history
 *
 */
@Service("defectStatisticsService")
@Transactional
public class DefectStatisticsServiceImpl extends AbstractBaseEntityOperation<DefectStatisticsEntity> implements DefectStatisticsService {
	
	@Autowired
	private DefectStatisticsDao defectStatisticsDao;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private DefectService defectService;
	@Autowired
	private DefectEquipmentService defectEquipmentService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Override
	public IBaseEntityOperation<DefectStatisticsEntity> getDao() {
		return defectStatisticsDao;
	}
	/**
	 * @Description: 缺陷统计查询
	 * @author changl
	 * @Date 2016年11月7日 下午6:00:01
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		List<DefectStatisticsEntity>  list=(List<DefectStatisticsEntity>) findByConditionFlag(params,page);
		params.put("flag", "flag");
		List<DefectStatisticsEntity>  allList=(List<DefectStatisticsEntity>) findByConditionFlag(params,page);
		for (DefectStatisticsEntity d:list) {
			allList.add(d);
		}
		
		for(DefectStatisticsEntity d:allList){
			if(d.getFindNum()!=null && !"".equals(d.getFindNum()) && d.getSolveNum()!=null && !"".equals(d.getSolveNum())){
				BigDecimal findNum = new BigDecimal(d.getFindNum());
				BigDecimal solveNum = new BigDecimal(d.getSolveNum());
				BigDecimal findNumAll =new BigDecimal(d.getFindNumAll());
				BigDecimal solveNumAll=new BigDecimal(d.getSolveNumAll());
				if(findNum.compareTo(BigDecimal.ZERO)!=0 &&  solveNum.compareTo(BigDecimal.ZERO)!=0 ){
//					BigDecimal solveRate = solveNum.divide(findNum);
					BigDecimal solveRate = solveNum.multiply(new BigDecimal(100))
							.divide(findNum,2,BigDecimal.ROUND_HALF_UP);
					d.setSolveScale(solveRate.toString()+"%");
//					hangScaleAll=solveNum.multiply(new BigDecimal(100))
//							.divide(findNum,2,BigDecimal.ROUND_HALF_UP);//挂起比例
				}
				if(findNumAll.compareTo(BigDecimal.ZERO)!=0 &&  solveNumAll.compareTo(BigDecimal.ZERO)!=0 ){
					BigDecimal solveRate = solveNumAll.multiply(new BigDecimal(100))
							.divide(findNumAll,2,BigDecimal.ROUND_HALF_UP);
					d.setSolveScaleAll(solveRate.toString()+"%");
					d.setScaleAll(solveRate.toString()+"%");
				}
				
			}
			
		}
		for(DefectStatisticsEntity d:allList){
			
//			if( d.getSolveNum()!=null && !"".equals(d.getSolveNum())){
				if(d.getTypeName().equals("合计")){
						BigDecimal findNumAll =new BigDecimal(d.getFindNumAll());
						BigDecimal solveNumAll=new BigDecimal(d.getSolveNumAll());
					if(findNumAll.compareTo(BigDecimal.ZERO)!=0 &&  solveNumAll.compareTo(BigDecimal.ZERO)!=0 ){
						BigDecimal solveRate = solveNumAll.multiply(new BigDecimal(100))
								.divide(findNumAll,2,BigDecimal.ROUND_HALF_UP);
						d.setSolveScaleAll(solveRate.toString()+"%");
						d.setScaleAll(solveRate.toString()+"%");
					}
				}
//			}
		}
		return (List<O>)allList;
	}
	/**
	 * @Description: 缺陷统计查询
	 * @author changl
	 * @Date 2016年11月7日 下午6:00:01
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public <O> List<O> findByConditionFlag(Map<String, Object> params, Page<O> page) {
		String flag=(String) params.get("flag");
		Object searchunitId= params.get("searchunitId");
		//本月集合
		List<DefectStatisticsEntity>  list=new ArrayList<DefectStatisticsEntity>(); 
		//1-本月集合
		List<DefectStatisticsEntity>  allList=new ArrayList<DefectStatisticsEntity>();
		//查询部门
		List<Condition> conditions =new ArrayList<Condition>();
		if(flag!=null){
			conditions.add(new Condition("C_LEVEL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,UnitLevelEnum.COMPANY.getCode()));
		}else{
			conditions.add(new Condition("C_GENERATION_TYPE = 1 "));
			conditions.add(new Condition(" C_LEVEL = 2 "));
			conditions.add(new Condition(" C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4  "));
		}
	    conditions.add(new Condition("C_STATUS", FieldTypeEnum.LONG, MatchTypeEnum.EQ,String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		if(searchunitId!=null&&!searchunitId.equals("")){
		conditions.add(new Condition("C_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,searchunitId.toString()));	
		
		}
	    List<SysUnitEntity> unitList=sysUnitService.findByCondition(conditions, null);
		// 缺陷类型
		Map<String, SysDictionaryVO> typeMap = DictionaryUtil.getDictionaries("DEFECT_TYPE");
		List<Map.Entry<String, SysDictionaryVO>> typelist = new ArrayList<Map.Entry<String, SysDictionaryVO>>(typeMap.entrySet()); 
		  Collections.sort(typelist, new Comparator<Map.Entry<String, SysDictionaryVO>>() { 
		      public int compare(Map.Entry<String, SysDictionaryVO> o1, Map.Entry<String, SysDictionaryVO> o2) { 
		          return (o1.getKey()).toString().compareTo(o2.getKey()); 

		      } 

		  });
		  // 设备管理类型
		  Map<String, SysDictionaryVO> equiptypeMap = DictionaryUtil.getDictionaries("EQUIPMANAGETYPE");
		  List<Map.Entry<String, SysDictionaryVO>> equiptypelist = new ArrayList<Map.Entry<String, SysDictionaryVO>>(equiptypeMap.entrySet()); 
		  Collections.sort(equiptypelist, new Comparator<Map.Entry<String, SysDictionaryVO>>() { 
			  public int compare(Map.Entry<String, SysDictionaryVO> o1, Map.Entry<String, SysDictionaryVO> o2) { 
				  return (o1.getKey()).toString().compareTo(o2.getKey()); 
				  
			  } 
			  
		  });

		List<Map.Entry<String, SysDictionaryVO>> equiptypelist1 = new ArrayList<Map.Entry<String, SysDictionaryVO>>();
		Object searchequipmentType = params.get("searchequipmentType");
		if (searchequipmentType != null && !"".equals(searchequipmentType.toString())) {
			for(Map.Entry<String, SysDictionaryVO> equipEntry : equiptypelist){
				if (equipEntry.getKey().equals(searchequipmentType.toString())) {
					equiptypelist1.add(equipEntry);
				}
			}
		} else {
			equiptypelist1.addAll(equiptypelist);
		}
		

		  //组装部门类型记录数
		  for (SysUnitEntity sysUnitEntity:unitList) {
			  for (Map.Entry<String, SysDictionaryVO> entry : typelist) {
				  SysDictionaryVO sysDictionaryVO = entry.getValue();
				 
				  for(Map.Entry<String, SysDictionaryVO> equipEntry : equiptypelist1){
					  DefectStatisticsEntity defectStatisticsEntity =new DefectStatisticsEntity();
					  defectStatisticsEntity.setUnitId(sysUnitEntity.getId().toString());
					  defectStatisticsEntity.setUnitName(sysUnitEntity.getName());
					  defectStatisticsEntity.setType(sysDictionaryVO.getCode());
					  defectStatisticsEntity.setTypeName(sysDictionaryVO.getName());
					  SysDictionaryVO dictionaryVO = equipEntry.getValue();
					  defectStatisticsEntity.setEquiptypeName(dictionaryVO.getName());
					  list.add(defectStatisticsEntity);
				  }
				  
				  
				  for(Map.Entry<String, SysDictionaryVO> equipEntry : equiptypelist1){
					  DefectStatisticsEntity defectStatisticsAllEntity =new DefectStatisticsEntity();
					  defectStatisticsAllEntity.setUnitId(sysUnitEntity.getId().toString());
					  defectStatisticsAllEntity.setUnitName(sysUnitEntity.getName());
					  defectStatisticsAllEntity.setType(sysDictionaryVO.getCode());
					  defectStatisticsAllEntity.setTypeName(sysDictionaryVO.getName());
					  SysDictionaryVO dictionaryVO = equipEntry.getValue();
					  defectStatisticsAllEntity.setEquiptypeName(dictionaryVO.getName());
					  allList.add(defectStatisticsAllEntity);
				  }
				  
				  
				  
			  }
		  }
		  //本月缺陷
		 List<DefectEntity>defectList= defectService.findByCondition("defectStatistics",  getConditions(params,true), null);
		 conditions.clear();
		 List<DefectEquipmentEntity> defectEquipmentList = defectEquipmentService.findByCondition("findByCondition",  conditions, null);
		 //计算数据
		 list=findByConditionMonth(list,defectList,flag,defectEquipmentList);
		 //1月至本月缺陷
		 List<DefectEntity>defectAllList=defectService.findByCondition("defectStatistics",  getConditions(params,false), null);
		 conditions.clear();
		 List<DefectEquipmentEntity> defectEquipmenAlltList = defectEquipmentService.findByCondition("findByCondition",  conditions, null);
		 //计算数据
		 allList=findByConditionMonth(allList,defectAllList,flag,defectEquipmenAlltList);
		 //合并本月1月至本月
		 for (DefectStatisticsEntity tAll:allList) {
			 for (DefectStatisticsEntity t:(List<DefectStatisticsEntity> )list) {
				 if(t.getTypeName()!=null&&t.getUnitId()!=null&&t.getUnitId().equals(tAll.getUnitId())&&t.getTypeName().equals(tAll.getTypeName())&&t.getEquiptypeName()!=null
							&&t.getEquiptypeName().equals(tAll.getEquiptypeName())
							||(tAll.getTypeName().equals("合计")&&t.getTypeName().equals("合计")&&t.getUnitId().equals(tAll.getUnitId()))){
					tAll.setFindNum(t.getFindNumAll());
					tAll.setSolveNum(t.getSolveNumAll());
					tAll.setAvoid(t.getAvoidAll());
					tAll.setHangNum(t.getHangNumAll());
					tAll.setFindSum(t.getFindSumAll());
					tAll.setScale(t.getScaleAll());//缺陷类型比率
					tAll.setSolveScale(t.getSolveScaleAll());//消除比例
					tAll.setAvoidScale(t.getAvoidScaleAll());//未消除比例
					tAll.setHangScale(t.getHangScaleAll());// 挂起比例
				}
			}
		}
		return (List<O>) allList;
	}
	//本月开始最后 时间 falg (ture:本月，false:1-本月) 
	public  List<Condition> getConditions(Map<String, Object> params ,boolean flag) {
		String searchstatisticsMonth=(String) params.get("searchstatisticsMonth");
		String searchequipmentType=(String) params.get("searchequipmentType");
		if(StringUtil.isEmpty(searchstatisticsMonth)){
			searchstatisticsMonth=DateFormatUtil.getInstance("yyyy-MM").format(new Date());
		}
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-01  00:00:00");  
		String []dates=searchstatisticsMonth.split("-");
		Calendar c=Calendar.getInstance();  
		c.set(Calendar.YEAR,Integer.parseInt(dates[0]));
		String gtime1="";
		String gtime2="";
		if(flag){
			c.set(Calendar.MONTH, Integer.parseInt(dates[1])-1);
			 gtime1 = sdf2.format(c.getTime()); //本月开始
			c.add(Calendar.MONTH, 1);  
			 gtime2 = sdf2.format(c.getTime()); //本月最后  
		}else{
			c.set(Calendar.MONTH, 0);
			gtime1 = sdf2.format(c.getTime()); //1-本月开始
			c.set(Calendar.MONTH, Integer.parseInt(dates[1]));  
			 gtime2 = sdf2.format(c.getTime()); //1-本月最后  
		}
		List<Condition> conditions = new ArrayList<Condition>();
		if(StringUtil.isNotEmpty(searchequipmentType)){
			conditions.add(new Condition("C_EQUIPMENT_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,searchequipmentType));
		}
		conditions.add(new Condition("C_FIND_TIME", FieldTypeEnum.STRING, MatchTypeEnum.GE,gtime1));
		conditions.add(new Condition("C_FIND_TIME", FieldTypeEnum.STRING, MatchTypeEnum.LT,gtime2));
		return conditions;
	}
	/**
	 * @Description: 缺陷统计查询（月份区分）
	 * @author changl
	 * @Date 2016年11月7日 下午6:00:01
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public <O> List<O> findByConditionMonth(List<DefectStatisticsEntity> list,List<DefectEntity>defectList,String flag,List<DefectEquipmentEntity> defectEquipmentList) {
		 for (DefectStatisticsEntity defectStatisticsEntity:list) {
			int findNum=0;
			int findSum=0;
			int solveNum=0;
			int avoid=0;
			int hangNum=0;
			for (DefectEquipmentEntity defectEquipmentEntity: defectEquipmentList) {
				//统计缺陷状态
				if(defectEquipmentEntity.getStatus().equals(DefectStatusEnum.IMPLEMENT.getCode())||
						defectEquipmentEntity.getStatus().equals(DefectStatusEnum.MONITOR.getCode())||
						defectEquipmentEntity.getStatus().equals(DefectStatusEnum.BIOTECH_APPROVE.getCode())||
						defectEquipmentEntity.getStatus().equals(DefectStatusEnum.BIOTECH.getCode())||
						defectEquipmentEntity.getStatus().equals(DefectStatusEnum.CHECK.getCode())||
						defectEquipmentEntity.getStatus().equals(DefectStatusEnum.SOLVE.getCode())){
					//查询一级单位并替换
					if(flag!=null){
						defectEquipmentEntity.setUnitId(getLevelUnitId(Long.valueOf(defectEquipmentEntity.getUnitId())).toString());
					}
					if(defectStatisticsEntity.getUnitId().equals(defectEquipmentEntity.getUnitId().toString())
							&&defectStatisticsEntity.getType().equals(defectEquipmentEntity.getEquipType())&&defectStatisticsEntity.getEquiptypeName().equals(defectEquipmentEntity.getEquipManageTypeName())){
						findNum++;
						if(defectEquipmentEntity.getStatus().equals(DefectStatusEnum.SOLVE.getCode())&&defectEquipmentEntity.getAppraisalResult().equals("1")){
							solveNum++;
						}
						if(defectEquipmentEntity.getStatus().equals(DefectStatusEnum.SOLVE.getCode())&&defectEquipmentEntity.getAppraisalResult().equals("2")){
							avoid++;
						}
//						if(defectEntity.getProcessStatus().equals(DefectStatusEnum.HANG.getCode())){
//							hangNum++;
//						}
					}
					if(defectStatisticsEntity.getUnitId().equals(defectEquipmentEntity.getUnitId().toString())){
						findSum++;
					}
				}
				
			}
			defectStatisticsEntity.setFindNumAll(String.valueOf(findNum));
			defectStatisticsEntity.setFindSumAll(String.valueOf(findSum));
			defectStatisticsEntity.setSolveNumAll(String.valueOf(solveNum));
			defectStatisticsEntity.setAvoidAll(String.valueOf(avoid));
			defectStatisticsEntity.setHangNumAll(String.valueOf(hangNum));
		}
		List<DefectStatisticsEntity> resultList =new ArrayList<DefectStatisticsEntity>();
		BigDecimal findNumAll=new BigDecimal(0);//发现次数
		BigDecimal solveNumAll=new BigDecimal(0);//消除次数
		BigDecimal avoidAll=new BigDecimal(0);//未消除次数
		BigDecimal hangNumAll=new BigDecimal(0);//挂起次数
		int rowshow=0;//是否显示
		int rowCount=0;//合并行数
		int count=0;//合计占用行数
//		int rowshowNumber=0;
//		int rowCountNumber=0;
//		int countNumber=0;
		for (int i=0;i<list.size();i++) {
			DefectStatisticsEntity t=(DefectStatisticsEntity) list.get(i);
			//计算比例
			t=scaleEntity(t);
			//计算合计
			if(i!=0&&!t.getUnitId().equals(resultList.get(resultList.size()-1).getUnitId())){
				DefectStatisticsEntity sumEntity=sumEntity(findNumAll,solveNumAll,avoidAll,
								hangNumAll,resultList.get(resultList.size()-1).getFindSumAll(),
								resultList.get(resultList.size()-1).getUnitId());
				//计算合计比例
				 sumEntity=scaleEntity(sumEntity);
				 resultList.add(sumEntity);
				 findNumAll=new BigDecimal(0);//发现次数
				 solveNumAll=new BigDecimal(0);//消除次数
				 avoidAll=new BigDecimal(0);//未消除次数
				 hangNumAll=new BigDecimal(0);//挂起次数
				 resultList.get(i-rowCount+count).setRowspanNum(String.valueOf(rowCount+1));
				 rowshow=0;
				 rowCount=0;
				 count++;
			}
//			if(i!=0&&!t.getType().equals(resultList.get(resultList.size()-1).getType())){
//				DefectStatisticsEntity sumEntity=sumEntity(findNumAll,solveNumAll,avoidAll,
//						hangNumAll,resultList.get(resultList.size()-1).getFindSumAll(),
//						resultList.get(resultList.size()-1).getUnitId());
//				//计算合计比例
//				sumEntity=scaleEntity(sumEntity);
//				resultList.add(sumEntity);
//				findNumAll=new BigDecimal(0);//发现次数
//				solveNumAll=new BigDecimal(0);//消除次数
//				avoidAll=new BigDecimal(0);//未消除次数
//				hangNumAll=new BigDecimal(0);//挂起次数
//				resultList.get(i-rowCountNumber+countNumber).setRowspanNumber(String.valueOf(rowCountNumber+1));
//				rowshowNumber=0;
//				rowCountNumber=0;
//				countNumber++;
//			}
			//合拼还是隐藏
			if(rowshow==0){
				t.setTdHide("show");
			}else{
				t.setTdHide("hide");
			}
			resultList.add(t);
			//计算
			findNumAll=findNumAll.add(new BigDecimal( t.getFindNumAll()));
			solveNumAll=solveNumAll.add(new BigDecimal( t.getSolveNumAll()));
			avoidAll=avoidAll.add(new BigDecimal( t.getAvoidAll()));
			hangNumAll=hangNumAll.add(new BigDecimal( t.getHangNumAll()));
			//最后计算合计
			if(i==list.size()-1){
				DefectStatisticsEntity sumEntity=sumEntity(findNumAll,solveNumAll,avoidAll,hangNumAll,
										resultList.get(resultList.size()-1).getFindSumAll(),
										resultList.get(resultList.size()-1).getUnitId());
				//计算合计比例
				sumEntity=scaleEntity(sumEntity);
				resultList.add(sumEntity);
				resultList.get(i-rowCount+count).setRowspanNum(String.valueOf(rowCount+1+1));
//				resultList.get(i-rowCountNumber+countNumber).setRowspanNumber(String.valueOf(rowCountNumber+1+1));
			}
			rowshow++;
			rowCount++;
//			rowshowNumber++;
//			rowCountNumber++;
		}

		return (List<O>)resultList;
	}
	//计算合计
	private DefectStatisticsEntity sumEntity(BigDecimal findNumAll,BigDecimal solveNumAll,
			BigDecimal avoidAll,BigDecimal hangNumAll,String findSumAll,String unitId){
		DefectStatisticsEntity sumEntity=new DefectStatisticsEntity();
		sumEntity.setUnitId(unitId);
		sumEntity.setTypeName("合计");
		sumEntity.setFindNumAll(findNumAll.toString());
		sumEntity.setSolveNumAll(solveNumAll.toString());
		sumEntity.setAvoidAll(avoidAll.toString());
		sumEntity.setHangNumAll(hangNumAll.toString());
		sumEntity.setFindSumAll(findSumAll);
		if(unitId.equals("0")){
			System.out.println(sumEntity);
		}
		return sumEntity;
	}
	//计算比例
	private DefectStatisticsEntity scaleEntity( DefectStatisticsEntity t){
		BigDecimal scaleAll=new BigDecimal("0");
		BigDecimal solveScaleAll=new BigDecimal("0");
		BigDecimal avoidScaleAll=new BigDecimal("0");
		BigDecimal hangScaleAll=new BigDecimal("0");
		if(t.getFindSumAll().equals("0")){
				t.setFindSumAll("1");
		}
			scaleAll=new BigDecimal(t.getFindNumAll()).multiply(new BigDecimal(100))
					.divide(new BigDecimal(t.getFindSumAll()),2,BigDecimal.ROUND_HALF_UP); //发现比例
			solveScaleAll=new BigDecimal(t.getSolveNumAll()).multiply(new BigDecimal(100))
					.divide(new BigDecimal(t.getFindSumAll()),2,BigDecimal.ROUND_HALF_UP);//消除比例
			avoidScaleAll=new BigDecimal(t.getAvoidAll()).multiply(new BigDecimal(100))
					.divide(new BigDecimal(t.getFindSumAll()),2,BigDecimal.ROUND_HALF_UP);//未消除比例
			hangScaleAll=new BigDecimal(t.getHangNumAll()).multiply(new BigDecimal(100))
					.divide(new BigDecimal(t.getFindSumAll()),2,BigDecimal.ROUND_HALF_UP);//挂起比例
		//计算比例
		t.setScaleAll(scaleAll.toString()+"%");//缺陷类型比率
		t.setSolveScaleAll(solveScaleAll.toString()+"%");//消除比例
		t.setAvoidScaleAll(avoidScaleAll.toString()+"%");//未消除比例
		t.setHangScaleAll(hangScaleAll.toString()+"%");// 挂起比例
		
//		if(t.getTypeName().equals("合计")&&!t.getFindNumAll().equals("0")){
//			t.setScaleAll("100%");//缺陷类型比率
//		};
//		if(t.getTypeName().equals("合计")&&!t.getSolveNumAll().equals("0")){
//			t.setSolveScaleAll("100%");//缺陷类型比率
//		};
//		if(t.getTypeName().equals("合计")&&!t.getAvoidAll().equals("0")){
//			t.setAvoidScaleAll("100%");//缺陷类型比率
//		};
//		if(t.getTypeName().equals("合计")&&!t.getHangNumAll().equals("0")){
//			t.setHangScaleAll("100%");//缺陷类型比率
//		};
			return t;
	}
	//查询一级单位id
	public  Long getLevelUnitId(Long unitId){
		SysUnitEntity sysUnit= sysUnitService.findById(unitId);
		if(Integer.parseInt(sysUnit.getLevel())<=Integer.parseInt(UnitLevelEnum.COMPANY.getCode())){
			 return unitId;
		}else{
			 return getLevelUnitId(sysUnit.getParentId());
		}
	}
}