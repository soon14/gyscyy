package com.aptech.business.managePlanContract.accidentMeasuresPlan.service;

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
import javax.validation.Valid;

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

import com.aptech.business.component.dictionary.AccidentMeasureStatusEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.ScienceTechnologyBtnEnum;
import com.aptech.business.component.dictionary.ScienceTechnologyPlanStatusEnum;
import com.aptech.business.managePlanContract.accidentMeasuresPlan.dao.AccidentMeasuresPlanDao;
import com.aptech.business.managePlanContract.accidentMeasuresPlan.domain.AccidentMeasuresPlanEntity;
import com.aptech.business.managePlanContract.annualSupervisionPlan.domain.AnnualSupervisionPlanEntity;
import com.aptech.business.managePlanContract.annualTechnicalPlan.domain.AnnualTechnicalPlanEntity;
import com.aptech.common.act.service.ActTaskService;
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
import com.aptech.framework.validate.ValidateUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 年度反事故措施计划应用管理服务实现类
 *
 * @author 
 * @created 2018-04-16 15:12:09
 * @lastModified 
 * @history
 *
 */
@Service("accidentMeasuresPlanService")
@Transactional
public class AccidentMeasuresPlanServiceImpl extends AbstractBaseEntityOperation<AccidentMeasuresPlanEntity> implements AccidentMeasuresPlanService {
	
	@Autowired
	private AccidentMeasuresPlanDao accidentMeasuresPlanDao;
	@Autowired 
	private SysUserService sysUserService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private ActTaskService actTaskService;
	@Override
	public IBaseEntityOperation<AccidentMeasuresPlanEntity> getDao() {
		return accidentMeasuresPlanDao;
	}
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if (page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("id"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		return findByCondition(conditions, page);
	}
	@Override
	public void addEntity(AccidentMeasuresPlanEntity t) {
		if(!t.getFileId().equals("[]")&&t.getFileId()!=null){
			t.setUploadTime(new Date());
		}
		t.setStatus(ScienceTechnologyPlanStatusEnum.TOBESUBMIT.getCode());
		getDao().addEntity(t);
		//提交
		String saveOrsubmit=t.getSaveOrSubmit();
		String selectUser=t.getSelectUser();
		if(saveOrsubmit!=null&&!saveOrsubmit.equals("")){
			//准备启动流程
  		    String key=ProcessMarkEnum.ACCIDENT_MEASURES_PLAN_PROCESS_KEY.getName();	
			Map<String, Object> vars=new HashMap<String,Object>();
	  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
		    actTaskService.startProcess(key, t.getId().toString(), vars);
			
		    AccidentMeasuresPlanEntity accidentMeasuresPlanEntity=this.findById(t.getId());
		    accidentMeasuresPlanEntity.setId(t.getId());
		    accidentMeasuresPlanEntity.setStatus(ScienceTechnologyPlanStatusEnum.TECHNICALAPPROVE.getCode());
			this.updateEntity(accidentMeasuresPlanEntity);
								}
	}
	@Override
	public ResultObj update(AccidentMeasuresPlanEntity t, Long id) {
		// TODO Auto-generated method stub
		AccidentMeasuresPlanEntity accidentMeasuresPlanEntity = super.findById(id);
		accidentMeasuresPlanEntity.setCharge(t.getCharge());
		accidentMeasuresPlanEntity.setContent(t.getContent());
		accidentMeasuresPlanEntity.setPlanDate(t.getPlanDate());
		accidentMeasuresPlanEntity.setRemark(t.getRemark());
		accidentMeasuresPlanEntity.setSubject(t.getSubject());
		accidentMeasuresPlanEntity.setUnitId(t.getUnitId());
		accidentMeasuresPlanEntity.setUploadPerson(t.getUploadPerson());
		if(!t.getFileId().equals("[]")&&t.getFileId()!=null){
			accidentMeasuresPlanEntity.setFileId(t.getFileId());
			accidentMeasuresPlanEntity.setUploadTime(new Date());
		}
		super.updateEntity(accidentMeasuresPlanEntity);
		return new ResultObj();
	}
	@Override
	public ResultObj importAccidentMeasuresPlan(HttpServletRequest request,
			File file, String OriginalFilename) throws Exception {
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

					for(int i = 0;i<wb.getNumberOfSheets();i++){
						Sheet sheet = wb.getSheetAt(i);  
						int rowCount = sheet.getPhysicalNumberOfRows();
						Row row = sheet.getRow(1);  
						for (int r = 2; r <=rowCount; r++){
							AccidentMeasuresPlanEntity accidentMeasuresPlanEntity = new AccidentMeasuresPlanEntity();
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
											double cellValue1 = (double)d;
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
								
									case 1://项目名称
										accidentMeasuresPlanEntity.setSubject(cellValue);
										break;
									case 2://项目内容
										accidentMeasuresPlanEntity.setContent(cellValue);
										break;
									case 3://计划费用（万元）
										if(cellValue!=null){
											accidentMeasuresPlanEntity.setCharge(cellValue);
										}
										break;
									case 4://计划完成时间
										accidentMeasuresPlanEntity.setPlanDate(cellValue);
										break;
									case 5://责任单位
										if(cellValue!=null && cellValue!=""){
											conditions.clear();
											conditions.add(new Condition("C_NAME", FieldTypeEnum.STRING, MatchTypeEnum.LIKE, cellValue));
											List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
											if(unitList!=null && unitList.size()>0){
												accidentMeasuresPlanEntity.setUnitId(unitList.get(0).getId().toString());
											}
										}
										break;
									case 6://上传时间
										accidentMeasuresPlanEntity.setUploadTime(null);
										break;
									case 7://上传人
										if(cellValue!=null && cellValue!=""){
											conditions.clear();
											conditions.add(new Condition("a.C_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, cellValue));
											List<SysUserEntity> userList = sysUserService.findByCondition(conditions, null);
											if(userList!=null && userList.size()>0){
												accidentMeasuresPlanEntity.setUploadPerson(userList.get(0).getId().toString());
											}
										}
										break;
									case 8://审核状态
										accidentMeasuresPlanEntity.setStatus(ScienceTechnologyPlanStatusEnum.TOBESUBMIT.getCode());
										accidentMeasuresPlanEntity.setRemark(cellValue);
										accidentMeasuresPlanEntity.setFileId("[]");
										break;
									case 9://备注
										accidentMeasuresPlanEntity.setRemark(cellValue);
										accidentMeasuresPlanEntity.setFileId("[]");
										break;
									case 10://附件
										accidentMeasuresPlanEntity.setFileId("[]");
										break;
									default:
										break;
							
						}
			}
						super.addEntity(accidentMeasuresPlanEntity);
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
			    String key=ProcessMarkEnum.ACCIDENT_MEASURES_PLAN_PROCESS_KEY.getName();	
			    
			Map<String, Object> vars=new HashMap<String,Object>();
				vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
		    actTaskService.startProcess(key, id, vars);
			
		    AccidentMeasuresPlanEntity accidentMeasuresPlanEntity=this.findById(Long.valueOf(id));
		    accidentMeasuresPlanEntity.setStatus(ScienceTechnologyPlanStatusEnum.TECHNICALAPPROVE.getCode()); 
			this.updateEntity(accidentMeasuresPlanEntity);
			}
			
			@Override
			public void approveTicketAgree(String taskId, String procInstId,
					Map<String, Object> variables,
					AccidentMeasuresPlanEntity accidentMeasuresPlanEntity,
					SysUserEntity userEntity) {
				// TODO Auto-generated method stub
				actTaskService.complete(taskId,procInstId,variables);
				this.updateSpnrAgree(accidentMeasuresPlanEntity,userEntity);
			}
			@Override
			public void updateSpnrAgree(
					AccidentMeasuresPlanEntity t,
					SysUserEntity userEntity) {
				// TODO Auto-generated method stub
				String spFlag=t.getSpFlag();
				if(spFlag.equals(ScienceTechnologyBtnEnum.DWFZR.getCode())){
					AccidentMeasuresPlanEntity accidentMeasuresPlanEntity = this.findById(t.getId());//查询这个表的实体
					accidentMeasuresPlanEntity.setStatus(ScienceTechnologyPlanStatusEnum.PLANAPPROVE.getCode());
					super.updateEntity(accidentMeasuresPlanEntity);
				}else if(spFlag.equals(ScienceTechnologyBtnEnum.ZGFZR.getCode())){
					AccidentMeasuresPlanEntity accidentMeasuresPlanEntity = this.findById(t.getId());//查询这个表的实体
					accidentMeasuresPlanEntity.setStatus(ScienceTechnologyPlanStatusEnum.MANAGE.getCode());
					super.updateEntity(accidentMeasuresPlanEntity);
				}else if(spFlag.equals(ScienceTechnologyBtnEnum.AQJCB.getCode())){
					AccidentMeasuresPlanEntity accidentMeasuresPlanEntity = this.findById(t.getId());//查询这个表的实体
					accidentMeasuresPlanEntity.setStatus(ScienceTechnologyPlanStatusEnum.EXCUTE.getCode());
					super.updateEntity(accidentMeasuresPlanEntity);
				}else if(spFlag.equals(ScienceTechnologyBtnEnum.FZJL.getCode())){
					AccidentMeasuresPlanEntity accidentMeasuresPlanEntity = this.findById(t.getId());//查询这个表的实体
					accidentMeasuresPlanEntity.setStatus(ScienceTechnologyPlanStatusEnum.END.getCode());
					super.updateEntity(accidentMeasuresPlanEntity);
				}else if(spFlag.equals(ScienceTechnologyBtnEnum.ZTJ.getCode())){
					AccidentMeasuresPlanEntity accidentMeasuresPlanEntity = this.findById(t.getId());//查询这个表的实体
					accidentMeasuresPlanEntity.setStatus(ScienceTechnologyPlanStatusEnum.TECHNICALAPPROVE.getCode());
					super.updateEntity(accidentMeasuresPlanEntity);
				}
			}
			@Override
			public void approveTicketDisagree(String taskId, String procInstId,
					Map<String, Object> variables,
					AccidentMeasuresPlanEntity accidentMeasuresPlanEntity,
					SysUserEntity userEntity) {
				// TODO Auto-generated method stub
				actTaskService.complete(taskId,procInstId,variables);
				this.updateSpnrDisagree(accidentMeasuresPlanEntity,userEntity);
			}
			@Override
			public void updateSpnrDisagree(
					AccidentMeasuresPlanEntity t,
					SysUserEntity userEntity) {
				// TODO Auto-generated method stub
				String spFlag=t.getSpFlag();
				if(spFlag.equals(ScienceTechnologyBtnEnum.DWFZR.getCode())){
					AccidentMeasuresPlanEntity accidentMeasuresPlanEntity = this.findById(t.getId());//查询这个表的实体
					accidentMeasuresPlanEntity.setStatus(ScienceTechnologyPlanStatusEnum.REJECT.getCode());
					super.updateEntity(accidentMeasuresPlanEntity);
				}else if(spFlag.equals(ScienceTechnologyBtnEnum.ZGFZR.getCode())){
					AccidentMeasuresPlanEntity accidentMeasuresPlanEntity = this.findById(t.getId());//查询这个表的实体
					accidentMeasuresPlanEntity.setStatus(ScienceTechnologyPlanStatusEnum.REJECT.getCode());
					super.updateEntity(accidentMeasuresPlanEntity);
				}else if(spFlag.equals(ScienceTechnologyBtnEnum.AQJCB.getCode())){
					AccidentMeasuresPlanEntity accidentMeasuresPlanEntity = this.findById(t.getId());//查询这个表的实体
					accidentMeasuresPlanEntity.setStatus(ScienceTechnologyPlanStatusEnum.REJECT.getCode());
					super.updateEntity(accidentMeasuresPlanEntity);
				}else if(spFlag.equals(ScienceTechnologyBtnEnum.FZJL.getCode())){
					AccidentMeasuresPlanEntity accidentMeasuresPlanEntity = this.findById(t.getId());//查询这个表的实体
					accidentMeasuresPlanEntity.setStatus(ScienceTechnologyPlanStatusEnum.REJECT.getCode());
					super.updateEntity(accidentMeasuresPlanEntity);
				}else if(spFlag.equals(ScienceTechnologyBtnEnum.ZF.getCode())){
					AccidentMeasuresPlanEntity accidentMeasuresPlanEntity = this.findById(t.getId());//查询这个表的实体
					accidentMeasuresPlanEntity.setStatus(ScienceTechnologyPlanStatusEnum.CANCAL.getCode());
					super.updateEntity(accidentMeasuresPlanEntity);
				
			}
			}
}