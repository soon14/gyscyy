package com.aptech.business.equip.equipLedger.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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

import com.aptech.business.component.dictionary.EquipVoltageEnum;
import com.aptech.business.equip.equipLedger.dao.EquipLedgerDao;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEnum;
import com.aptech.business.equip.equipTree.domain.EquipTreeEntity;
import com.aptech.business.equip.equipTree.domain.EquipTreeEnum;
import com.aptech.business.equip.equipTree.service.EquipTreeService;
import com.aptech.business.equip.equiplParameter.domain.EquipParameterEntity;
import com.aptech.business.equip.equiplParameter.service.EquipParameterService;
import com.aptech.business.equip.modelParameter.service.ModelParameterService;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.PropertyUtil;
import com.aptech.framework.web.base.ResultObj;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * 
 * 设备管理应用管理服务实现类
 *
 * @author 
 * @created 2017-06-08 10:50:56
 * @lastModified 
 * @history
 *
 */
@Service("equipLedgerService")
@Transactional
public class EquipLedgerServiceImpl extends AbstractBaseEntityOperation<EquipLedgerEntity> implements EquipLedgerService {
	
	@Autowired
	private EquipTreeService equiptreeService;
	@Autowired
	private EquipLedgerDao equipLedgerDao;
	@Autowired
	private EquipTreeService equipTreeService;
	@Autowired
	private ModelParameterService modelParameterService;
	@Autowired
	private EquipParameterService equipParameterService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<EquipLedgerEntity> getDao() {
		return equipLedgerDao;
	}
	
	/**
	 * 设备新增
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResultObj addEquimentLedger(Map<String, Object> params,HttpServletRequest request){
			ResultObj resultObj = new ResultObj();
			//登录人信息
			SysUserEntity userEntity= RequestContext.get().getUser();
			List<Condition> conditions = new ArrayList<Condition>();
			//设备信息
			EquipLedgerEntity equipLedgerEntity = PropertyUtil.mapToBean((Map<String, Object>)params.get("equipmentInfo"), EquipLedgerEntity.class);
			conditions.add(new Condition("L.C_CODE",FieldTypeEnum.STRING, MatchTypeEnum.EQ, equipLedgerEntity.getCode()));
			conditions.add(new Condition("L.C_NAME",FieldTypeEnum.STRING, MatchTypeEnum.EQ, equipLedgerEntity.getName()));
			conditions.add(new Condition("L.C_STATUS",FieldTypeEnum.INT, MatchTypeEnum.EQ, EquipLedgerEnum.NORMAL.getId()));
			List<EquipLedgerEntity> equipLedgerEntities =  equipLedgerDao.findByCondition(conditions, null);
			if(!equipLedgerEntities.isEmpty()){
				//数据重复
				resultObj.setResult(EquipLedgerEnum.DATAREPETITION.getCode());
			}else{
				equipLedgerEntity.setCreateUserId(userEntity.getId());
				equipLedgerEntity.setCreateUserName(userEntity.getLoginName());
				equipLedgerEntity.setStatus(EquipLedgerEnum.NORMAL.getId());
				equipLedgerEntity.setCreateDate(new Date());
				equipLedgerDao.addEntity(equipLedgerEntity);
				//结点表
				EquipTreeEntity equipTreeEntity  = new EquipTreeEntity();
				equipTreeEntity.setName(equipLedgerEntity.getName());
				equipTreeEntity.setCode(equipLedgerEntity.getCode());
				equipTreeEntity.setParentId(equipLedgerEntity.getEquipParentId());
				equipTreeEntity.setUnitId(equipLedgerEntity.getUnitId());
				//物理设备
				equipTreeEntity.setTreeType(EquipTreeEnum.PHYSICSEQUIP.getId());
				equipTreeEntity.setEquipId(equipLedgerEntity.getId());
				equipTreeEntity.setCreateUserId(userEntity.getId());
				equipTreeEntity.setCreateDate(new Date());
				equipTreeEntity.setStatus(EquipTreeEnum.NORMAL.getId());
				equipTreeEntity.setPathCode(equipLedgerEntity.getPathCode());
				equipTreeService.addPhysicsEntity(equipTreeEntity);
				
				//pathCode=父级pathCode-子Id
				equipTreeEntity.setPathCode(equipLedgerEntity.getPathCode()+"-"+equipTreeEntity.getId());
				equipTreeService.updateEntity(equipTreeEntity);
				//基础参数
				List<Map<String, Object>> baseParameters = (List<Map<String, Object>>)params.get("equipParameter");
				for(Map<String, Object> map:baseParameters){
					Object o = map.get("defaultValue");
					if(o!=null){
						EquipParameterEntity equipParameterEntity = new EquipParameterEntity();
						equipParameterEntity.setDefaultValue(String.valueOf(map.get("defaultValue")));
						equipParameterEntity.setEquipLedgerId(equipLedgerEntity.getId());
						equipParameterEntity.setParameter(String.valueOf(map.get("parameter")));
						equipParameterEntity.setParameterType(EquipLedgerEnum.BASEPARAMETERTYPE.getId());
						equipParameterEntity.setStatus(EquipLedgerEnum.NORMAL.getId());
						equipParameterService.addEntity(equipParameterEntity);
					}
				}
				//技术参数
				List<Map<String, Object>> technologyParameters = (List<Map<String, Object>>)params.get("equipTechnology");
				for(Map<String, Object> map:technologyParameters){
					Object o = map.get("defaultValue");
					if(o!=null){
						EquipParameterEntity equipParameterEntity = new EquipParameterEntity();
						equipParameterEntity.setDefaultValue(String.valueOf(map.get("defaultValue")));
						equipParameterEntity.setEquipLedgerId(equipLedgerEntity.getId());
						equipParameterEntity.setParameter(String.valueOf(map.get("parameter")));
						equipParameterEntity.setParameterType(EquipLedgerEnum.TECHNOLOGYPARAMETERSTYPE.getId());
						equipParameterEntity.setStatus(EquipLedgerEnum.NORMAL.getId());
						equipParameterService.addEntity(equipParameterEntity);
					}
				}
				resultObj.setData(equipLedgerEntity);
			}
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPTREE.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
			return resultObj;
	}
	
	
	/**
	 * 设备修改
	 */
	@Override
	public ResultObj updateEquimentLedger(Map<String, Object> params,HttpServletRequest request){
		ResultObj resultObj = new ResultObj();
		//登录人信息
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<Condition> conditions = new ArrayList<Condition>();
		//设备信息
		EquipLedgerEntity equipLedgerEntity = PropertyUtil.mapToBean((Map<String, Object>)params.get("equipmentInfo"), EquipLedgerEntity.class);
		conditions.add(new Condition("L.C_CODE",FieldTypeEnum.STRING, MatchTypeEnum.EQ, equipLedgerEntity.getCode()));
		conditions.add(new Condition("L.C_NAME",FieldTypeEnum.STRING, MatchTypeEnum.EQ, equipLedgerEntity.getName()));
		conditions.add(new Condition("L.C_STATUS",FieldTypeEnum.INT, MatchTypeEnum.EQ, EquipLedgerEnum.NORMAL.getId()));
		conditions.add(new Condition("L.C_ID != "+equipLedgerEntity.getId()));
		List<EquipLedgerEntity> equipLedgerEntities =  equipLedgerDao.findByCondition(conditions, null);
		if(!equipLedgerEntities.isEmpty()){
			//数据重复
			resultObj.setResult(EquipLedgerEnum.DATAREPETITION.getCode());
		}else{
			EquipLedgerEntity oldEquipLedgerEntity =  equipLedgerDao.findById(equipLedgerEntity.getId());
			equipLedgerEntity.setCreateUserId(oldEquipLedgerEntity.getCreateUserId());
			equipLedgerEntity.setCreateUserName(oldEquipLedgerEntity.getCreateUserName());
			equipLedgerEntity.setCreateDate(oldEquipLedgerEntity.getCreateDate());
			equipLedgerEntity.setUpdateUserId(userEntity.getId());
			equipLedgerEntity.setUpdateUserName(userEntity.getLoginName());
			equipLedgerEntity.setStatus(EquipLedgerEnum.NORMAL.getId());
			equipLedgerEntity.setUpdateDate(new Date());
			equipLedgerDao.updateEntity(equipLedgerEntity);
			conditions = new ArrayList<Condition>();
			conditions.add(new Condition("C_EQUIP_ID",FieldTypeEnum.LONG, MatchTypeEnum.EQ, equipLedgerEntity.getId()));
			//获取结点表对象
			List<EquipTreeEntity> equiptreeEntity_list = equipTreeService.findByCondition(conditions, null);
			
			//修改结点表
			if(!equiptreeEntity_list.isEmpty()){
				EquipTreeEntity equipTreeEntity = equiptreeEntity_list.get(0);
				//物理设备
				equipTreeEntity.setTreeType(EquipTreeEnum.PHYSICSEQUIP.getId());
				equipTreeEntity.setCode(equipLedgerEntity.getCode());
				equipTreeEntity.setName(equipLedgerEntity.getName());
				equipTreeEntity.setUpdateDate(new Date());
				equipTreeEntity.setUpdateUserId(userEntity.getId());
				equipTreeService.updateEntityWithEquip(equipTreeEntity);
			}
			
			//删除设备参数
			equipParameterService.deleteByCondition("delByEquipId", equipLedgerEntity.getId());
			
			//基础参数
			List<Map<String, Object>> baseParameters = (List<Map<String, Object>>)params.get("equipParameter");
			for(Map<String, Object> map:baseParameters){
				Object o = map.get("defaultValue");
				if(o!=null){
					EquipParameterEntity equipParameterEntity = new EquipParameterEntity();
					equipParameterEntity.setDefaultValue(String.valueOf(map.get("defaultValue")));
					equipParameterEntity.setEquipLedgerId(equipLedgerEntity.getId());
					equipParameterEntity.setParameter(String.valueOf(map.get("parameter")));
					equipParameterEntity.setParameterType(EquipLedgerEnum.BASEPARAMETERTYPE.getId());
					equipParameterService.addEntity(equipParameterEntity);
				}
			}
			
			//技术参数
			List<Map<String, Object>> technologyParameters = (List<Map<String, Object>>)params.get("equipTechnology");
			for(Map<String, Object> map:technologyParameters){
				Object o = map.get("defaultValue");
				if(o!=null){
					EquipParameterEntity equipParameterEntity = new EquipParameterEntity();
					equipParameterEntity.setDefaultValue(String.valueOf(map.get("defaultValue")));
					equipParameterEntity.setEquipLedgerId(equipLedgerEntity.getId());
					equipParameterEntity.setParameter(String.valueOf(map.get("parameter")));
					equipParameterEntity.setParameterType(EquipLedgerEnum.TECHNOLOGYPARAMETERSTYPE.getId());
					equipParameterService.addEntity(equipParameterEntity);
				}
			}
			resultObj.setData(equipLedgerEntity);
		}
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPTREE.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		return resultObj;
	}

	@Override
	public boolean importEquipLedger(HttpServletRequest request,File file,String OriginalFilename) throws Exception {
		boolean result = true;
		InputStream inputStream = new FileInputStream(file);
		if (OriginalFilename.contains("xls")) {
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);// HSSFWorkbook
			return read(hssfWorkbook,request);
		} else if (OriginalFilename.contains("xlsx")) {
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);// XSSFWorkbook
			return read(xssfWorkbook,request);
		}
		return result;
	}
	
	 /**
     * 文件操作 获取文件扩展名
     * 
     * @Author: wangcc
     * @param filename
     *            文件名称包含扩展名
     * @return
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
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
  	
  	/**
	 * @Description:   读取EXCEL
	 * @author         wangcc 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 * @Date           2016年11月23日 下午1:04:52 
	 * @throws         Exception
	 */
	private boolean read(Workbook wb,HttpServletRequest request) throws ParseException, Exception{
		List<EquipLedgerEntity> list = new ArrayList<EquipLedgerEntity>();
		String pathCode = "";
		long unitId = 0;
		long equipParentId = 0;
		boolean result = true;
		for(int i = 0;i<wb.getNumberOfSheets();i++){
			Sheet sheet = wb.getSheetAt(i);  
			int rowCount = sheet.getPhysicalNumberOfRows();
			Row row = sheet.getRow(1);  
			for (int r = 1; r <=rowCount; r++){
				EquipLedgerEntity equipLedgerEntity = new EquipLedgerEntity();
				row = sheet.getRow(r);  
				if (row == null|| isBlankRow(row)){  
					continue;  
				}
				/** 循环Excel的列 */  
				for (int c = 0; c < row.getPhysicalNumberOfCells(); c++){  
					List<Condition> conditions = new ArrayList<Condition>();
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
								DateFormatUtil dUtil = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss");
								cellValue =dUtil.format(date);
							}else {//数值类型
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
						case 0:
							equipLedgerEntity.setCode(cellValue);
							break;
						case 1:
							equipLedgerEntity.setName(cellValue);
							break;
						case 3:
							conditions.add(new Condition("T.C_NAME",FieldTypeEnum.STRING, MatchTypeEnum.EQ, cellValue));
							List<EquipTreeEntity> equipTreeEntity = equiptreeService.findByCondition(conditions, null);
							equipLedgerEntity.setFileId("[]");
							if(!equipTreeEntity.isEmpty()){
								EquipTreeEntity tempEquipTreeEntity  = (EquipTreeEntity)equipTreeEntity.get(0);
								pathCode = tempEquipTreeEntity.getPathCode();
								equipLedgerEntity.setPathCode(pathCode);
								unitId = tempEquipTreeEntity.getUnitId();
								equipLedgerEntity.setUnitId(unitId);
								equipParentId = tempEquipTreeEntity.getId();
								equipLedgerEntity.setEquipParentId(equipParentId);
							}
							break;
						case 4:
							if(StringUtils.equals(cellValue, EquipLedgerEnum.MAKEELECTRICEQUIP.getName())){
								equipLedgerEntity.setEquipType(Long.parseLong(EquipLedgerEnum.MAKEELECTRICEQUIP.getId().toString()));
							}else if(StringUtils.equals(cellValue, EquipLedgerEnum.CHANGELECTRICEQUIP.getName())){
								equipLedgerEntity.setEquipType(Long.parseLong(EquipLedgerEnum.CHANGELECTRICEQUIP.getId().toString()));
							}else if(StringUtils.equals(cellValue, EquipLedgerEnum.TRANSMITELECTRICEQUIP.getName())){
								equipLedgerEntity.setEquipType(Long.parseLong(EquipLedgerEnum.TRANSMITELECTRICEQUIP.getId().toString()));
							}else if(StringUtils.equals(cellValue, EquipLedgerEnum.SECONDEQUIP.getName())){
								equipLedgerEntity.setEquipType(Long.parseLong(EquipLedgerEnum.SECONDEQUIP.getId().toString()));
							}else if(StringUtils.equals(cellValue, EquipLedgerEnum.INSTRUMENTMETER.getName())){
								equipLedgerEntity.setEquipType(Long.parseLong(EquipLedgerEnum.INSTRUMENTMETER.getId().toString()));
							}else if(StringUtils.equals(cellValue, EquipLedgerEnum.INSTRUMENTEQUIP.getName())){
								equipLedgerEntity.setEquipType(Long.parseLong(EquipLedgerEnum.INSTRUMENTEQUIP.getId().toString()));
							}else{
								equipLedgerEntity.setEquipType(Long.parseLong(EquipLedgerEnum.LIFEFACILITY.getCode().toString()));
							}
							break;
						case 5:
							if(StringUtils.equals(cellValue, EquipLedgerEnum.EQUIPSTATUSINTERFACE.getName())){
								equipLedgerEntity.setBusinessType(EquipLedgerEnum.EQUIPSTATUSINTERFACE.getCode());
							}else if(StringUtils.equals(cellValue, EquipLedgerEnum.GROUNDKNIFEINTERFACE.getName())){
								equipLedgerEntity.setBusinessType(EquipLedgerEnum.GROUNDKNIFEINTERFACE.getCode());
							}else{
								equipLedgerEntity.setBusinessType(EquipLedgerEnum.BOTHINTERFACE.getCode());
							}
							break;
						case 6:
							if(StringUtils.equals(cellValue, EquipVoltageEnum.KV35.getName())){
								equipLedgerEntity.setEquipVoltageId(Long.parseLong(EquipVoltageEnum.KV35.getId().toString()));
							}else if(StringUtils.equals(cellValue, EquipVoltageEnum.KV110.getName())){
								equipLedgerEntity.setEquipVoltageId(Long.parseLong(EquipVoltageEnum.KV110.getId().toString()));
							}else if(StringUtils.equals(cellValue, EquipVoltageEnum.V220.getName())){
								equipLedgerEntity.setEquipVoltageId(Long.parseLong(EquipVoltageEnum.V220.getId().toString()));
							}else if(StringUtils.equals(cellValue, EquipVoltageEnum.V380.getName())){
								equipLedgerEntity.setEquipVoltageId(Long.parseLong(EquipVoltageEnum.V380.getId().toString()));
							}else if(StringUtils.equals(cellValue, EquipVoltageEnum.V400.getName())){
								equipLedgerEntity.setEquipVoltageId(Long.parseLong(EquipVoltageEnum.V400.getId().toString()));
							}else if(StringUtils.equals(cellValue, EquipVoltageEnum.V620.getName())){
								equipLedgerEntity.setEquipVoltageId(Long.parseLong(EquipVoltageEnum.V620.getId().toString()));
							}else if(StringUtils.equals(cellValue, EquipVoltageEnum.V690.getName())){
								equipLedgerEntity.setEquipVoltageId(Long.parseLong(EquipVoltageEnum.V690.getId().toString()));
							}
							break;
						default:
							break;
						}
				}
				if(equipLedgerEntity.getPathCode()!=null){
					list.add(equipLedgerEntity);
				}
			}
		}
		for(EquipLedgerEntity entity:list){
			addEquimentLedgerForImpExcel(entity);
		}
		return result;  
	}   
	
	public ResultObj addEquimentLedgerForImpExcel(EquipLedgerEntity equipLedgerEntity){
			ResultObj resultObj = new ResultObj();
			//登录人信息
			SysUserEntity userEntity= RequestContext.get().getUser();
			List<Condition> conditions = new ArrayList<Condition>();
			//设备信息
			conditions.add(new Condition("L.C_CODE",FieldTypeEnum.STRING, MatchTypeEnum.EQ, equipLedgerEntity.getCode()));
			conditions.add(new Condition("L.C_NAME",FieldTypeEnum.STRING, MatchTypeEnum.EQ, equipLedgerEntity.getName()));
			conditions.add(new Condition("L.C_STATUS",FieldTypeEnum.INT, MatchTypeEnum.EQ, EquipLedgerEnum.NORMAL.getId()));
			List<EquipLedgerEntity> equipLedgerEntities =  equipLedgerDao.findByCondition(conditions, null);
			if(!equipLedgerEntities.isEmpty()){
				//数据重复
				resultObj.setResult(EquipLedgerEnum.DATAREPETITION.getCode());
			}else{
				equipLedgerEntity.setCreateUserId(userEntity.getId());
				equipLedgerEntity.setCreateUserName(userEntity.getLoginName());
				equipLedgerEntity.setStatus(EquipLedgerEnum.NORMAL.getId());
				equipLedgerEntity.setCreateDate(new Date());
				equipLedgerDao.addEntity(equipLedgerEntity);
				//结点表
				EquipTreeEntity equipTreeEntity  = new EquipTreeEntity();
				equipTreeEntity.setName(equipLedgerEntity.getName());
				equipTreeEntity.setCode(equipLedgerEntity.getCode());
				equipTreeEntity.setParentId(equipLedgerEntity.getEquipParentId());
				equipTreeEntity.setUnitId(equipLedgerEntity.getUnitId());
				//物理设备
				equipTreeEntity.setTreeType(EquipTreeEnum.PHYSICSEQUIP.getId());
				equipTreeEntity.setEquipId(equipLedgerEntity.getId());
				equipTreeEntity.setCreateUserId(userEntity.getId());
				equipTreeEntity.setCreateDate(new Date());
				equipTreeEntity.setStatus(EquipTreeEnum.NORMAL.getId());
				equipTreeEntity.setPathCode(equipLedgerEntity.getPathCode());
				equipTreeService.addPhysicsEntity(equipTreeEntity);
				
				//pathCode=父级pathCode-子Id
				equipTreeEntity.setPathCode(equipLedgerEntity.getPathCode()+"-"+equipTreeEntity.getId());
				equipTreeService.updateEntity(equipTreeEntity);
				resultObj.setData(equipLedgerEntity);
			}
			return resultObj;
	}

	@Override
	public List<EquipLedgerEntity> getEquipLedgerTreeList(Long unitId){
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("U.C_ID",FieldTypeEnum.INT, MatchTypeEnum.EQ,unitId));
		return equipLedgerDao.findByCondition(conditions, null);
	}
}		
				