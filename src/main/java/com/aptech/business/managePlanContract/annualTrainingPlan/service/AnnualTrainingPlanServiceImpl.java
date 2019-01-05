package com.aptech.business.managePlanContract.annualTrainingPlan.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.ScienceTechnologyBtnEnum;
import com.aptech.business.component.dictionary.ScienceTechnologyPlanStatusEnum;
import com.aptech.business.dutyOrder.domain.DutyOrderEntity;
import com.aptech.business.managePlanContract.annualSupervisionPlan.domain.AnnualSupervisionPlanEntity;
import com.aptech.business.managePlanContract.annualTechnicalPlan.domain.AnnualTechnicalPlanEntity;
import com.aptech.business.managePlanContract.annualTrainingPlan.dao.AnnualTrainingPlanDao;
import com.aptech.business.managePlanContract.annualTrainingPlan.domain.AnnualTrainingPlanEntity;
import com.aptech.business.managePlanContract.scienceTechnologyPlan.domain.ScienceTechnologyPlanEntity;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 年度培训计划应用管理服务实现类
 *
 * @author 
 * @created 2018-04-13 15:24:06
 * @lastModified 
 * @history
 *
 */
@Service("annualTrainingPlanService")
@Transactional
public class AnnualTrainingPlanServiceImpl extends AbstractBaseEntityOperation<AnnualTrainingPlanEntity> implements AnnualTrainingPlanService {
	
	@Autowired
	private AnnualTrainingPlanDao annualTrainingPlanDao;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private ActTaskService actTaskService;
	@Override
	public IBaseEntityOperation<AnnualTrainingPlanEntity> getDao() {
		return annualTrainingPlanDao;
	}
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if(page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		List<Sort> orderList=new ArrayList<Sort>();//page.getOrders();
   		
   		orderList.addAll(OrmUtil.changeMapToOrders(params));
		//默认计划时间 倒叙
		page.setOrders(orderList);
   		
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		List<AnnualTrainingPlanEntity> resultList = (List<AnnualTrainingPlanEntity>) super.findByCondition(conditions, page);
		
		if(resultList!=null && resultList.size()>0){
			for(AnnualTrainingPlanEntity annualTrainingPlanEntity:resultList){
				if(annualTrainingPlanEntity.getTrainMember()!=null){
					String[] ids = annualTrainingPlanEntity.getTrainMember().split(",");
					conditions.clear();
					conditions.add(new Condition("a.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.IN,ids));
					List<SysUserEntity> userList = sysUserService.findByCondition(conditions, null);
					String userNames = "";
					for(int i=0;i<userList.size();i++){
						if(i==userList.size()-1){
							userNames+=userList.get(i).getName();
						}else{
							userNames+=userList.get(i).getName()+",";
						}
					}
					annualTrainingPlanEntity.setTrainMemberName(userNames);
				}
				
			}
		}
		
		return (List<O>) resultList;
	}
	@Override
	public void addEntity(AnnualTrainingPlanEntity  t) {
		t.setStatus(ScienceTechnologyPlanStatusEnum.TOBESUBMIT.getCode());
		super.addEntity(t);
		//提交
		String saveOrsubmit=t.getSaveOrSubmit();
		String selectUser=t.getSelectUser();
		if(saveOrsubmit!=null&&!saveOrsubmit.equals("")){
			//准备启动流程
  		    String key=ProcessMarkEnum.ANNUAL_TRAINING_PLAN_PROCESS_KEY.getName();	
			Map<String, Object> vars=new HashMap<String,Object>();
	  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
		    actTaskService.startProcess(key, t.getId().toString(), vars);
			
		    AnnualTrainingPlanEntity annualTrainingPlanEntity=this.findById(t.getId());
		    annualTrainingPlanEntity.setId(t.getId());
		    annualTrainingPlanEntity.setStatus(ScienceTechnologyPlanStatusEnum.TECHNICALAPPROVE.getCode());
			this.updateEntity(annualTrainingPlanEntity);
				}
	}
	@Override
	public ResultObj update(AnnualTrainingPlanEntity t, Long id) {
		// TODO Auto-generated method stub
		AnnualTrainingPlanEntity annualTrainingPlanEntity = super.findById(id);
		annualTrainingPlanEntity.setTrainContent(t.getTrainContent());
		annualTrainingPlanEntity.setTrainLocation(t.getTrainLocation());
		annualTrainingPlanEntity.setTrainMember(t.getTrainMember());
		annualTrainingPlanEntity.setTrainName(t.getTrainName());
		annualTrainingPlanEntity.setTrainTime(t.getTrainTime());
		annualTrainingPlanEntity.setTrainType(t.getTrainType());
		annualTrainingPlanEntity.setUnitId(t.getUnitId());
		super.updateEntity(annualTrainingPlanEntity);
		return new ResultObj();
	}

	@Override
	public ResultObj importAnnualTrainingPlan(HttpServletRequest request,
			File file, String OriginalFilename) throws Exception {
		// TODO Auto-generated method stub
		InputStream inputStream = new FileInputStream(file);
		if (OriginalFilename.contains("xlsx")) {
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);// XSSFWorkbook

			return readMap(xssfWorkbook,request);
		} else if (OriginalFilename.contains("xls")) {
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);// HSSFWorkbook

			return readMap(hssfWorkbook,request);
		}
		return new ResultObj();
	}
	// kks编码  设备名称  父级设备KKS编码  序号（设备树的ID）
			private ResultObj readMap(Workbook wb,HttpServletRequest request) throws ParseException, Exception{
					DateFormatUtil dUtil = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
			        SysUserEntity userEntity= RequestContext.get().getUser();
					boolean result = true;
					List<Condition> conditions = new ArrayList<Condition>();
					System.out.println(wb.getNumberOfSheets());
					for(int i = 0;i<wb.getNumberOfSheets();i++){
						Sheet sheet = wb.getSheetAt(i);  
						int rowCount = sheet.getPhysicalNumberOfRows();
						Row row = sheet.getRow(1);  
						for (int r = 2; r <=rowCount; r++){
							AnnualTrainingPlanEntity annualTrainingPlanEntity = new AnnualTrainingPlanEntity();
							row = sheet.getRow(r);  
							if (row == null|| isBlankRow(row)){  
								continue;  
							}
							/** 循环Excel的列 */  
							for (int c = 1; c < row.getPhysicalNumberOfCells(); c++){  
								Cell cell = row.getCell(c);  
								String cellValue = "";  
								if (null != cell){  
									// 以下是判断数据的类型  
									switch (cell.getCellType()){ 
									//XSSFCell可以达到相同的效果
									case HSSFCell.CELL_TYPE_NUMERIC: // 数字或时间 
										double d = cell.getNumericCellValue();
										if (HSSFDateUtil.isCellDateFormatted(cell)){//日期类型
											Date date = HSSFDateUtil.getJavaDate(d);
											
											cellValue =dUtil.format(date);
										}else {//数值类型
											int cellValue1 = (int)d;
											cellValue = cellValue1 + "";
										}
										break;
				                    case HSSFCell.CELL_TYPE_STRING: // 字符串  
				                         cellValue = cell.getStringCellValue();  
				                        break;  
					                case HSSFCell.CELL_TYPE_BLANK: // 空值  
					                    cellValue = "";  
					                    break;  
									default:  
										cellValue = "未知类型";  
										break;  
									}
								} 
								
								switch (c) {
								
									case 1://培训单位
										if(cellValue!=null && cellValue!=""){
											conditions.clear();
											conditions.add(new Condition("C_NAME", FieldTypeEnum.STRING, MatchTypeEnum.LIKE, cellValue));
											List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
											if(unitList!=null && unitList.size()>0){
												annualTrainingPlanEntity.setUnitId(unitList.get(0).getId().toString());
											}
										}
										break;
									case 2://培训名称
										annualTrainingPlanEntity.setTrainName(cellValue);
										break;
									case 3://培训内容
										annualTrainingPlanEntity.setTrainContent(cellValue);
										break;
									case 4://培训时间
										if(cellValue!=null && cellValue!=""){
											annualTrainingPlanEntity.setTrainTime(dUtil.parse(cellValue));
										}
										break;
									case 5://培训人员
										String name[]=cellValue.split("，");
										StringBuffer sb = new StringBuffer();
										conditions.clear();
										conditions.add(new Condition("a.C_NAME", FieldTypeEnum.STRING, MatchTypeEnum.IN, name));
										List<SysUserEntity> userList = sysUserService.findByCondition(conditions, null);
										if(userList!=null && userList.size()>0){
											String ids = "";
											for(SysUserEntity sysUserEntity : userList){
												ids+=sysUserEntity.getId().toString()+",";
											}
											ids.substring(0, userList.size()-1);
											annualTrainingPlanEntity.setTrainMember(ids);
										}
										break;
									case 6://培训地点
										annualTrainingPlanEntity.setTrainLocation(cellValue);
										annualTrainingPlanEntity.setStatus(ScienceTechnologyPlanStatusEnum.TOBESUBMIT.getCode());
										break;
									case 7://培训类别
										Map<String, SysDictionaryVO> specialCodeMap  =  DictionaryUtil.getDictionaries("TRAININGTYPE");
										for(SysDictionaryVO sysDictionaryVO : specialCodeMap.values()){
											if(sysDictionaryVO.getName().equals(cellValue) ){
												annualTrainingPlanEntity.setTrainType(sysDictionaryVO.getCode());
											}
										}
										break;
									case 8://申请人
										if(cellValue!=null && cellValue!=""){
											conditions.clear();
											conditions.add(new Condition("a.C_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, cellValue));
											List<SysUserEntity> sysUserList = sysUserService.findByCondition(conditions, null);
											if(sysUserList!=null && sysUserList.size()>0){
												annualTrainingPlanEntity.setUserId(sysUserList.get(0).getId().toString());
											}
										}
								default:
										break;
							
						}
			}
						super.addEntity(annualTrainingPlanEntity);
			}
					 
				}
					return new ResultObj(); 
			}
			/**
		     * @Description:   处理空格
		     * @author         wangcc 
		     * @Date           2016年11月23日 下午1:04:22 
		     * @throws         Exception
		     */
		  	public  boolean isBlankRow(Row row){
		          if(row == null) return true;
		          boolean result = true;
		          for(int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++){
		              Cell cell = row.getCell(i, HSSFRow.RETURN_BLANK_AS_NULL);
		              String value = "";
		              if(cell != null){
		                  switch (cell.getCellType()) {
		                  case Cell.CELL_TYPE_STRING:
		                      value = cell.getStringCellValue();
		                      break;
		                  case Cell.CELL_TYPE_NUMERIC:
		                      value = String.valueOf((int) cell.getNumericCellValue());
		                      break;
		                  case Cell.CELL_TYPE_BOOLEAN:
		                      value = String.valueOf(cell.getBooleanCellValue());
		                      break;
		                  case Cell.CELL_TYPE_FORMULA:
		                      value = String.valueOf(cell.getCellFormula());
		                      break;
		                  //case Cell.CELL_TYPE_BLANK:
		                  //    break;
		                  default:
		                      break;
		                  }
		                   
		                  if(!value.trim().equals("")){
		                      result = false;
		                      break;
		                  }
		              }
		          }
		           
		          return result;
		      }

			@Override
			public void submit(String id, String selectUser) {
				// TODO Auto-generated method stub
				//准备启动流程
			    String key=ProcessMarkEnum.ANNUAL_TRAINING_PLAN_PROCESS_KEY.getName();	
			    
			Map<String, Object> vars=new HashMap<String,Object>();
				vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
		    actTaskService.startProcess(key, id, vars);
			
			AnnualTrainingPlanEntity annualTrainingPlanEntity=this.findById(Long.valueOf(id));
			annualTrainingPlanEntity.setStatus(ScienceTechnologyPlanStatusEnum.TECHNICALAPPROVE.getCode()); 
			this.updateEntity(annualTrainingPlanEntity);
			}


		

			@Override
			public void approveTicketAgree(String taskId, String procInstId,
					Map<String, Object> variables,
					AnnualTrainingPlanEntity annualTrainingPlanEntity,
					SysUserEntity userEntity) {
				// TODO Auto-generated method stub
				actTaskService.complete(taskId,procInstId,variables);
				this.updateSpnrAgree(annualTrainingPlanEntity,userEntity);
			}
			@Override
			public void updateSpnrAgree(
					AnnualTrainingPlanEntity t,
					SysUserEntity userEntity) {
				// TODO Auto-generated method stub
				String spFlag=t.getSpFlag();
				if(spFlag.equals(ScienceTechnologyBtnEnum.DWFZR.getCode())){
					AnnualTrainingPlanEntity annualTrainingPlanEntity = this.findById(t.getId());//查询这个表的实体
					annualTrainingPlanEntity.setStatus(ScienceTechnologyPlanStatusEnum.PLANAPPROVE.getCode());
					super.updateEntity(annualTrainingPlanEntity);
				}else if(spFlag.equals(ScienceTechnologyBtnEnum.ZGFZR.getCode())){
					AnnualTrainingPlanEntity annualTrainingPlanEntity = this.findById(t.getId());//查询这个表的实体
					annualTrainingPlanEntity.setStatus(ScienceTechnologyPlanStatusEnum.MANAGE.getCode());
					super.updateEntity(annualTrainingPlanEntity);
				}else if(spFlag.equals(ScienceTechnologyBtnEnum.AQJCB.getCode())){
					AnnualTrainingPlanEntity annualTrainingPlanEntity = this.findById(t.getId());//查询这个表的实体
					annualTrainingPlanEntity.setStatus(ScienceTechnologyPlanStatusEnum.EXCUTE.getCode());
					super.updateEntity(annualTrainingPlanEntity);
				}else if(spFlag.equals(ScienceTechnologyBtnEnum.FZJL.getCode())){
					AnnualTrainingPlanEntity annualTrainingPlanEntity = this.findById(t.getId());//查询这个表的实体
					annualTrainingPlanEntity.setStatus(ScienceTechnologyPlanStatusEnum.END.getCode());
					super.updateEntity(annualTrainingPlanEntity);
				}else if(spFlag.equals(ScienceTechnologyBtnEnum.ZTJ.getCode())){
					AnnualTrainingPlanEntity annualTrainingPlanEntity = this.findById(t.getId());//查询这个表的实体
					annualTrainingPlanEntity.setStatus(ScienceTechnologyPlanStatusEnum.TECHNICALAPPROVE.getCode());
					super.updateEntity(annualTrainingPlanEntity);
				}
			}
			@Override
			public void approveTicketDisagree(String taskId, String procInstId,
					Map<String, Object> variables,
					AnnualTrainingPlanEntity annualTrainingPlanEntity,
					SysUserEntity userEntity) {
				// TODO Auto-generated method stub
				actTaskService.complete(taskId,procInstId,variables);
				this.updateSpnrDisagree(annualTrainingPlanEntity,userEntity);
			}
			
			@Override
			public void updateSpnrDisagree(
					AnnualTrainingPlanEntity t,
					SysUserEntity userEntity) {
				// TODO Auto-generated method stub
				String spFlag=t.getSpFlag();
				if(spFlag.equals(ScienceTechnologyBtnEnum.DWFZR.getCode())){
					AnnualTrainingPlanEntity annualTrainingPlanEntity = this.findById(t.getId());//查询这个表的实体
					annualTrainingPlanEntity.setStatus(ScienceTechnologyPlanStatusEnum.REJECT.getCode());
					super.updateEntity(annualTrainingPlanEntity);
				}else if(spFlag.equals(ScienceTechnologyBtnEnum.ZGFZR.getCode())){
					AnnualTrainingPlanEntity annualTrainingPlanEntity = this.findById(t.getId());//查询这个表的实体
					annualTrainingPlanEntity.setStatus(ScienceTechnologyPlanStatusEnum.REJECT.getCode());
					super.updateEntity(annualTrainingPlanEntity);
				}else if(spFlag.equals(ScienceTechnologyBtnEnum.AQJCB.getCode())){
					AnnualTrainingPlanEntity annualTrainingPlanEntity = this.findById(t.getId());//查询这个表的实体
					annualTrainingPlanEntity.setStatus(ScienceTechnologyPlanStatusEnum.REJECT.getCode());
					super.updateEntity(annualTrainingPlanEntity);
				}else if(spFlag.equals(ScienceTechnologyBtnEnum.FZJL.getCode())){
					AnnualTrainingPlanEntity annualTrainingPlanEntity = this.findById(t.getId());//查询这个表的实体
					annualTrainingPlanEntity.setStatus(ScienceTechnologyPlanStatusEnum.REJECT.getCode());
					super.updateEntity(annualTrainingPlanEntity);
				}else if(spFlag.equals(ScienceTechnologyBtnEnum.ZF.getCode())){
					AnnualTrainingPlanEntity annualTrainingPlanEntity = this.findById(t.getId());//查询这个表的实体
					annualTrainingPlanEntity.setStatus(ScienceTechnologyPlanStatusEnum.CANCAL.getCode());
					super.updateEntity(annualTrainingPlanEntity);
				}
			}

			
}