package com.aptech.business.overhaul.overhaulLogDetail.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.cargo.materialCategory.domain.MaterialCategoryEntity;
import com.aptech.business.cargo.materialCategory.service.MaterialCategoryService;
import com.aptech.business.cargo.stock.domain.StockEntity;
import com.aptech.business.cargo.stock.service.StockService;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
import com.aptech.business.equip.equipLedger.service.EquipLedgerService;
import com.aptech.business.overhaul.overhaulArrange.service.OverhaulArrangeService;
import com.aptech.business.overhaul.overhaulLog.domain.OverhaulLogEntity;
import com.aptech.business.overhaul.overhaulLog.service.OverhaulLogService;
import com.aptech.business.overhaul.overhaulLogDetail.dao.OverhaulLogDetailDao;
import com.aptech.business.overhaul.overhaulLogDetail.domain.OverhaulLogDetailEntity;
import com.aptech.business.overhaul.overhaulLogDetail.domain.OverhaulLogDetailEnum;
import com.aptech.business.overhaul.overhaulRecord.service.OverhaulRecordService;
import com.aptech.business.overhaul.overhaulSafe.dao.OverhaulSafeDao;
import com.aptech.business.overhaul.overhaulSafe.domain.OverhaulSafeEntity;
import com.aptech.business.overhaul.overhaulSafe.service.OverhaulSafeService;
import com.aptech.business.overhaul.overhaulSpareconsume.dao.OverhaulSpareconsumeDao;
import com.aptech.business.overhaul.overhaulSpareconsume.domain.OverhaulSpareconsumeEntity;
import com.aptech.business.overhaul.overhaulSpareconsume.service.OverhaulSpareconsumeService;
import com.aptech.business.overhaul.overhaulWorkTask.dao.OverhaulWorkTaskDao;
import com.aptech.business.overhaul.overhaulWorkTask.domain.OverhaulWorkTaskEntity;
import com.aptech.business.overhaul.overhaulWorkTask.service.OverhaulWorkTaskService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 检修日志明细应用管理服务实现类
 *
 * @author 
 * @created 2018-01-04 10:56:15
 * @lastModified 
 * @history
 *
 */
@Service("overhaulLogDetailService")
@Transactional
public class OverhaulLogDetailServiceImpl extends AbstractBaseEntityOperation<OverhaulLogDetailEntity> implements OverhaulLogDetailService {
	
	@Autowired
	private OverhaulSafeService overhaulSafeService;
	@Autowired
	private OverhaulWorkTaskService overhaulWorkTaskService;
	@Autowired
	private OverhaulLogDetailDao overhaulLogDetailDao;
	@Autowired
	private OverhaulWorkTaskDao overhaulWorkTaskDao;
	@Autowired
	private OverhaulSafeDao overhaulSafeDao;
	@Autowired
	private OverhaulSpareconsumeDao overhaulSpareconsumeDao;
	@Autowired
	private OverhaulSpareconsumeService overhaulSpareconsumeService;
	@Autowired
	private OverhaulArrangeService overhaulArrangeService;
	@Autowired
	private EquipLedgerService equipLedgerService;
	@Autowired
	private OverhaulRecordService overhaulRecordService;
	@Autowired
	private OverhaulLogService overhaulLogService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private StockService stockService;
	@Autowired
	private MaterialCategoryService materialCategoryService;
	@Override
	public IBaseEntityOperation<OverhaulLogDetailEntity> getDao() {
		return overhaulLogDetailDao;
	}

	
	@Override
	public void addEntity(OverhaulLogDetailEntity t) {
		//当前用户
		 SysUserEntity userEntity= RequestContext.get().getUser();
		 t.setCreateDate(new Date());
		 t.setCreateUserId(userEntity.getId());
		 t.setStatus(String.valueOf(DataStatusEnum.NORMAL.ordinal()));
		super.addEntity(t);
	}
	
	@Override
	public void updateEntity(OverhaulLogDetailEntity t) {
		
		OverhaulLogDetailEntity logDetailEntity = overhaulLogDetailDao.findById(t.getId());
		SysUserEntity userEntity= RequestContext.get().getUser();
		t.setCreateDate(logDetailEntity.getCreateDate());
		t.setCreateUserId(logDetailEntity.getCreateUserId());
		t.setUpdateDate(new Date());
		t.setUpdateUserId(userEntity.getId());
		t.setStatus(String.valueOf(DataStatusEnum.NORMAL.ordinal()));
		overhaulLogDetailDao.updateEntity(t);
		
	}


	
	@Override
	public ResultObj addMore(HttpServletRequest request,OverhaulLogDetailEntity poverhaulLogDetailEntity) {
		ResultObj resultObj = new ResultObj();
		//当前用户
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_OVERHAUL_RECORD_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,poverhaulLogDetailEntity.getOverhaulRecordId()));
		List<OverhaulLogDetailEntity> overhaulLogDetailEntityList =  overhaulLogDetailDao.findByCondition(conditions, null);
		//获取风场名称
		if (overhaulLogDetailEntityList.size()>0) {
			OverhaulLogDetailEntity overhaulLogDetailEntity = overhaulLogDetailEntityList.get(0);
			overhaulLogDetailEntity.setUnitId(poverhaulLogDetailEntity.getUnitId());
			overhaulLogDetailEntity.setUnitName(poverhaulLogDetailEntity.getUnitName());
			overhaulLogDetailEntity.setDutyPerson(poverhaulLogDetailEntity.getDutyPerson());
			overhaulLogDetailEntity.setLogDate(poverhaulLogDetailEntity.getLogDate());
			overhaulLogDetailEntity.setEndDate(poverhaulLogDetailEntity.getEndDate());
			overhaulLogDetailEntity.setAfterMeet(poverhaulLogDetailEntity.getAfterMeet());
			overhaulLogDetailEntity.setTeamMember(poverhaulLogDetailEntity.getTeamMember());
			overhaulLogDetailEntity.setWorkFinishInfo(poverhaulLogDetailEntity.getWorkFinishInfo());
			overhaulLogDetailEntity.setProblemMeasures(poverhaulLogDetailEntity.getProblemMeasures());
			overhaulLogDetailEntity.setFileId(poverhaulLogDetailEntity.getFileId());
			overhaulLogDetailEntity.setUpdateDate(new Date());
			overhaulLogDetailEntity.setUpdateUserId(userEntity.getId());
			overhaulLogDetailDao.updateEntity(overhaulLogDetailEntity);
		}else{
			poverhaulLogDetailEntity.setCreateDate(new Date());
			poverhaulLogDetailEntity.setCreateUserId(userEntity.getId());
			poverhaulLogDetailEntity.setStatus(OverhaulLogDetailEnum.NORMAL.getId().toString());
			overhaulLogDetailDao.addEntity(poverhaulLogDetailEntity);
		}
		
		boolean overhaulworkTaskAddFlag = false;
		if(overhaulWorkTaskService.findByCondition(conditions, null).isEmpty()){
			overhaulworkTaskAddFlag = true;
		}
		List<OverhaulWorkTaskEntity> workTaskList = poverhaulLogDetailEntity.getWorkTaskList();
		for(OverhaulWorkTaskEntity workTaskEntity:workTaskList){
			OverhaulWorkTaskEntity oriWorkTaskEntity = overhaulWorkTaskService.findById(workTaskEntity.getId());
			if(oriWorkTaskEntity!=null){
				oriWorkTaskEntity.setOverhaulRecordId(poverhaulLogDetailEntity.getOverhaulRecordId());
				oriWorkTaskEntity.setUpdateDate(new Date());
				oriWorkTaskEntity.setUpdateUserId(userEntity.getId());
				oriWorkTaskEntity.setWorkTask(workTaskEntity.getWorkTask());
				oriWorkTaskEntity.setFinishStatus(workTaskEntity.getFinishStatus());
				overhaulWorkTaskService.updateEntity(oriWorkTaskEntity);
				if(overhaulworkTaskAddFlag){
					overhaulWorkTaskDao.addEntity(oriWorkTaskEntity);
				}
			}
		}
		boolean overhaulSafeFlag = false;
		if(overhaulSafeService.findByCondition(conditions, null).isEmpty()){
			overhaulSafeFlag = true;
		}
		//删除安全交底
		overhaulSafeService.deleteByCondition("deleteByoverhaulArrangeId", poverhaulLogDetailEntity.getOverhaulRecordId());
		List<OverhaulSafeEntity> safeList1 = poverhaulLogDetailEntity.getSaftList1();
		for(OverhaulSafeEntity safeEntity:safeList1){
			safeEntity.setOverhaulRecordId(poverhaulLogDetailEntity.getOverhaulRecordId());
			safeEntity.setCreateDate(new Date());
			safeEntity.setCreateUserId(userEntity.getId());
			safeEntity.setStatus( String.valueOf(DataStatusEnum.NORMAL.ordinal()));
			safeEntity.setLogType("jobRisk");
			overhaulSafeDao.addEntity(safeEntity);
		}
		List<OverhaulSafeEntity> safeList2 = poverhaulLogDetailEntity.getSaftList2();
		for(OverhaulSafeEntity safeEntity:safeList2){
			safeEntity.setOverhaulRecordId(poverhaulLogDetailEntity.getOverhaulRecordId());
			safeEntity.setCreateDate(new Date());
			safeEntity.setCreateUserId(userEntity.getId());
			safeEntity.setStatus( String.valueOf(DataStatusEnum.NORMAL.ordinal()));
			safeEntity.setLogType("safItem");
			overhaulSafeDao.addEntity(safeEntity);
		}
		
		//删除备件
		//overhaulSpareconsumeService.deleteByCondition("deleteByoverhaulArrangeId", overhaulLogDetailEntity.getOverhaulArrangeId());
		boolean overhaulSpareconsumeFlag = false;
		if(overhaulSpareconsumeService.findByCondition(conditions, null).isEmpty()){
			overhaulSpareconsumeFlag = true;
		}
		List<OverhaulSpareconsumeEntity> spareconsumeEntities = poverhaulLogDetailEntity.getSpareconsumeList();
		
		for (OverhaulSpareconsumeEntity spareconsumeEntity : spareconsumeEntities) {
			OverhaulSpareconsumeEntity  overhauleEntity= overhaulSpareconsumeService.findById(spareconsumeEntity.getId());
			if(overhauleEntity!=null){
				overhauleEntity.setNumber(spareconsumeEntity.getNumber());
				overhauleEntity.setEquipId(spareconsumeEntity.getEquipId());
				EquipLedgerEntity  equipLedgerEntity  = equipLedgerService.findById(spareconsumeEntity.getEquipId());
				if(equipLedgerEntity!=null){
					overhauleEntity.setEquipName(equipLedgerEntity.getName());
				}
				overhaulSpareconsumeService.updateEntity(overhauleEntity);
				if (overhaulSpareconsumeFlag) {
					overhaulSpareconsumeDao.addEntity(overhauleEntity);
				}
				
				List<Condition> conditions2 = new ArrayList<Condition>();
				conditions2.add(new Condition("O.C_OVERHAUL_RECORD_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,poverhaulLogDetailEntity.getOverhaulRecordId()));
				List<OverhaulSpareconsumeEntity> overhaulEntities = overhaulSpareconsumeDao.findByCondition(conditions2, null);
				
				List<MaterialCategoryEntity> categoryEntities = new ArrayList<MaterialCategoryEntity>();
				List<StockEntity> stList  = new ArrayList<StockEntity>();
				for (OverhaulSpareconsumeEntity overhaulSpareconsumeEntity : overhaulEntities) {
					conditions2.clear();
					conditions2.add(new Condition("m.C_CODE",FieldTypeEnum.STRING,MatchTypeEnum.EQ,overhaulSpareconsumeEntity.getCode()));
					categoryEntities = materialCategoryService.findByCondition(conditions2, null);
				}
				
//				for (int i = 0; i < categoryEntities.size(); i++) {
//					conditions2.clear();
//					conditions2.add(new Condition("sk.C_MATERIAL_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,categoryEntities.get(i).getId()));
//					conditions2.add(new Condition("sk.C_TYPE",FieldTypeEnum.STRING,MatchTypeEnum.EQ,'2'));
//					conditions2.add(new Condition("sk.C_GOODS_PRICE",FieldTypeEnum.STRING,MatchTypeEnum.EQ,overhaulEntities.get(i).getGoodsPrice()));
//					conditions2.add(new Condition("mc.C_CODE",FieldTypeEnum.STRING,MatchTypeEnum.EQ,categoryEntities.get(i).getCode()));
//					conditions2.add(new Condition("sk.C_GOODS_ATTRIBUTE",FieldTypeEnum.STRING,MatchTypeEnum.EQ,overhaulEntities.get(i).getGoodsAttribute()));
//					conditions2.add(new Condition("sk.C_INVENTORY_QUANTITY",FieldTypeEnum.STRING,MatchTypeEnum.EQ,overhaulEntities.get(i).getInventoryCount()));
//					stList =  stockService.findByCondition(conditions2, null);
//				}
				
				for (MaterialCategoryEntity materialCategoryEntity : categoryEntities) {
					conditions2.clear();
					conditions2.add(new Condition("sk.C_MATERIAL_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,materialCategoryEntity.getId()));
					conditions2.add(new Condition("sk.C_TYPE",FieldTypeEnum.STRING,MatchTypeEnum.EQ,'2'));
					conditions2.add(new Condition("sk.C_GOODS_PRICE",FieldTypeEnum.STRING,MatchTypeEnum.EQ,materialCategoryEntity.getGoodsPrice()));
//					conditions2.add(new Condition("mc.C_CODE",FieldTypeEnum.STRING,MatchTypeEnum.EQ,materialCategoryEntity.getCode()));
					conditions2.add(new Condition("sk.C_GOODS_ATTRIBUTE",FieldTypeEnum.STRING,MatchTypeEnum.EQ,materialCategoryEntity.getGoodsAttribute()));
					conditions2.add(new Condition("sk.C_INVENTORY_QUANTITY",FieldTypeEnum.STRING,MatchTypeEnum.EQ,materialCategoryEntity.getInventoryCount()));
					stList =  stockService.findByCondition(conditions2, null);
				}

				if (!stList.isEmpty()) {
					
					for (int i = 0; i < stList.size(); i++) {
						int num=Integer.parseInt(stList.get(i).getInventoryQuantity())-Integer.parseInt(overhaulEntities.get(i).getNumber())  ;
						StockEntity stockEntity =stockService.findById(stList.get(i).getId());
						stockEntity.setSpareconsumeNum(String.valueOf(num));
						stockService.updateEntity(stockEntity);
					}
						
				}
			}
		}
		resultObj.setResult(OverhaulLogDetailEnum.SUCESS.getCode());
		return resultObj;
	}
}