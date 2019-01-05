package com.aptech.business.equip.equipTree.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.cargo.materialCategory.domain.MaterialCategoryEntity;
import com.aptech.business.cargo.materialCategory.service.MaterialCategoryService;
import com.aptech.business.defectManage.defect.service.DefectService;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEnum;
import com.aptech.business.equip.equipLedger.service.EquipLedgerService;
import com.aptech.business.equip.equipTree.dao.EquipTreeDao;
import com.aptech.business.equip.equipTree.domain.EquipTreeEntity;
import com.aptech.business.equip.equipTree.domain.EquipTreeEnum;
import com.aptech.business.equip.equiplParameter.domain.EquipParameterEntity;
import com.aptech.business.equip.equiplParameter.service.EquipParameterService;
import com.aptech.business.equip.modelParameter.service.ModelParameterService;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.overhaul.overhaulSpareconsume.domain.OverhaulSpareconsumeEntity;
import com.aptech.business.overhaul.overhaulSpareconsume.service.OverhaulSpareconsumeService;
import com.aptech.business.run.workRecord.service.WorkRecordService;
import com.aptech.business.ticketManage.workTicket.service.WorkTicketService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.PropertyUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 设备节点表应用管理服务实现类
 *
 * @author 
 * @created 2017-06-26 15:26:43
 * @lastModified 
 * @history
 *
 */
@Service("equiptreeService")
@Transactional
public class EquipTreeServiceImpl extends AbstractBaseEntityOperation<EquipTreeEntity> implements EquipTreeService {
	
	@Autowired
	private EquipTreeDao equipTreeDao;
	@Autowired
	private EquipLedgerService equipLedgerService;
	@Autowired
	private ModelParameterService modelParameterService;
	@Autowired
	private EquipParameterService equipParameterService;
	@Autowired
	private DefectService defectService;
	@Autowired
	private WorkRecordService workRecordService;
	@Autowired
	private WorkTicketService workTicketService;
	@Autowired
	private OverhaulSpareconsumeService overhaulSpareconsumeService;
	@Autowired
	private MaterialCategoryService materialCategoryService;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<EquipTreeEntity> getDao() {
		return equipTreeDao;
	}
	
	/**
	 * 新增(逻辑设备)
	 */
	@Override
	public void addEntity(EquipTreeEntity equipTreeEntity){
		SysUserEntity userEntity= RequestContext.get().getUser();
		//父级pathCode
		String pathCode = equipTreeEntity.getPathCode();
		equipTreeEntity.setStatus(EquipTreeEnum.NORMAL.getId());
		equipTreeEntity.setCreateUserId(userEntity.getId());
		equipTreeEntity.setCreateDate(new Date());
		equipTreeEntity.setTreeType(EquipTreeEnum.LOGICALEQUIP.getId());
		equipTreeEntity.setEquipId(Long.valueOf(EquipTreeEnum.LOGICALEQUIPID.getId()));
		equipTreeDao.addEntity(equipTreeEntity);
		//pathCode=父级pathCode-子Id
		equipTreeEntity.setPathCode(pathCode+"-"+equipTreeEntity.getId());
		equipTreeDao.updateEntity(equipTreeEntity);
	}
	
	/**
	 * 新增(物理设备)
	 */
	@Override
	public void addPhysicsEntity(EquipTreeEntity equipTreeEntity) {
		equipTreeDao.addEntity(equipTreeEntity);
		//pathCode=父级pathCode-子Id
		equipTreeEntity.setPathCode(equipTreeEntity.getPathCode()+"-"+equipTreeEntity.getId());
		equipTreeDao.updateEntity(equipTreeEntity);
	}
	
	
	/**
	 * 修改(逻辑设备)
	 */
	@Override
	public void updateEntity(EquipTreeEntity equipTreeEntity){
		SysUserEntity userEntity= RequestContext.get().getUser();
		equipTreeEntity = equipTreeDao.findById(equipTreeEntity.getId());
		equipTreeEntity.setUpdateUserId(userEntity.getId());;
		equipTreeEntity.setUpdateDate(new Date());
		equipTreeDao.updateEntity(equipTreeEntity);
	}
	
	
	/**
	 * 修改树节点(设备)
	 */
	@Override
	public void updateEntityWithEquip(EquipTreeEntity equipTreeEntity){
		equipTreeDao.updateEntity(equipTreeEntity);
	}
	


	/**
	 * @Description:   删除树节点
	 * @author         wangcc 
	 * @Date           2017年7月3日 下午6:04:55 
	 * @throws         Exception
	 */
	@Override
	public ResultObj isdel(Long id) {
		ResultObj resultObj = new ResultObj();
		EquipTreeEntity equipTreeEntity =  equipTreeDao.findById(id);
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_PATH_CODE", FieldTypeEnum.LONG, MatchTypeEnum.LIKE,equipTreeEntity.getPathCode()));
		conditions.add(new Condition("T.C_STATUS", FieldTypeEnum.LONG, MatchTypeEnum.EQ,EquipTreeEnum.NORMAL.getId()));
		List<EquipTreeEntity> equipTreeEntities =  equipTreeDao.findByCondition(conditions, null);
		if(!equipTreeEntities.isEmpty() && equipTreeEntities.size() > 1){
			resultObj.setResult(EquipTreeEnum.HAVESUB.getCode());
		}else{
			if(equipTreeEntity!=null){
				int treeType = equipTreeEntity.getTreeType();
				equipTreeEntity.setStatus(EquipTreeEnum.DELETE.getId());
				//更新为删除状态
				equipTreeDao.updateEntity(equipTreeEntity);
				//如果是物理设备
				if(treeType==EquipTreeEnum.PHYSICSEQUIP.getId()){
					Long equipId = equipTreeEntity.getEquipId();
					EquipLedgerEntity equipLedgerEntity =  equipLedgerService.findById(equipId);
					equipLedgerEntity.setStatus(EquipLedgerEnum.DELETE.getId());
					//更新设备状态
					equipLedgerService.updateEntity(equipLedgerEntity);
					conditions = new ArrayList<Condition>();
					conditions.add(new Condition("C_EQUIP_LEDGER_ID", FieldTypeEnum.LONG, MatchTypeEnum.LIKE,equipId));
					List<EquipParameterEntity> equipParameterEntities =  equipParameterService.findByCondition(conditions, null);
					if(!equipParameterEntities.isEmpty()){
						for(EquipParameterEntity equipParameterEntity:equipParameterEntities){
							equipParameterEntity.setStatus(EquipTreeEnum.DELETE.getId());
							//更新设备参数
							equipParameterService.updateEntity(equipParameterEntity);
						}
					}
					
				}
			}
		}
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPTREE.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		return resultObj;
	}
	
	/**
	 * @Description:   是否可以新增
	 * @author         wangcc 
	 * @Date           2017年7月3日 下午6:04:55 
	 * @throws         Exception
	 */
	@Override
	public ResultObj isAdd(Map<String, Object> params,HttpServletRequest request) {
		SysUserEntity userEntity= RequestContext.get().getUser();
		ResultObj resultObj = new ResultObj();
		EquipTreeEntity equipTreeEntity = PropertyUtil.mapToBean((Map<String, Object>)params.get("equipTreeInfo"), EquipTreeEntity.class);
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("T.C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,equipTreeEntity.getCode()));
		conditions.add(new Condition("T.C_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ,equipTreeEntity.getName()));
		conditions.add(new Condition("T.C_TREE_TYPE", FieldTypeEnum.INT, MatchTypeEnum.EQ,EquipTreeEnum.LOGICALEQUIP.getId()));
		List<EquipTreeEntity> equipTreeEntities =  equipTreeDao.findByCondition(conditions, null);
		if(!equipTreeEntities.isEmpty()){
			//数据重复
			resultObj.setResult(EquipTreeEnum.DATAREPETITION.getCode());
		}else{
			equipTreeEntity.setTreeType(EquipTreeEnum.LOGICALEQUIP.getId());
			equipTreeEntity.setCreateUserId(userEntity.getId());
			equipTreeEntity.setCreateDate(new Date());
			equipTreeEntity.setStatus(EquipTreeEnum.NORMAL.getId());
			equipTreeEntity.setEquipId(Long.valueOf(EquipTreeEnum.LOGICALEQUIPID.getId()));
			equipTreeDao.addEntity(equipTreeEntity);
			equipTreeEntity.setPathCode(equipTreeEntity.getPathCode()+"-"+equipTreeEntity.getId());
			equipTreeDao.updateEntity(equipTreeEntity);
		}
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPTREE.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		return resultObj;
	}
	
	/**
	 * @Description:   是否可以修改
	 * @author         wangcc 
	 * @Date           2017年7月3日 下午6:04:55 
	 * @throws         Exception
	 */
	@Override
	public ResultObj isEdit(Map<String, Object> params,Long id,HttpServletRequest request) {
		SysUserEntity userEntity= RequestContext.get().getUser();
		ResultObj resultObj = new ResultObj();
		EquipTreeEntity equipTreeEntity = PropertyUtil.mapToBean((Map<String, Object>)params.get("equipTreeInfo"), EquipTreeEntity.class);
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("T.C_ID", FieldTypeEnum.INT, MatchTypeEnum.NE,id));
		conditions.add(new Condition("T.C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,equipTreeEntity.getCode()));
		conditions.add(new Condition("T.C_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ,equipTreeEntity.getName()));
		conditions.add(new Condition("T.C_TREE_TYPE", FieldTypeEnum.INT, MatchTypeEnum.EQ,EquipTreeEnum.LOGICALEQUIP.getId()));
		conditions.add(new Condition("T.C_STATUS", FieldTypeEnum.INT, MatchTypeEnum.EQ,EquipTreeEnum.NORMAL.getId()));
		List<EquipTreeEntity> equipTreeEntities =  equipTreeDao.findByCondition(conditions, null);
		if(!equipTreeEntities.isEmpty()){
			//数据重复
			resultObj.setResult(EquipTreeEnum.DATAREPETITION.getCode());
		}else{
			EquipTreeEntity equipTreeEntityOld =  equipTreeDao.findById(id);
			equipTreeEntity.setCreateUserId(equipTreeEntityOld.getCreateUserId());
			equipTreeEntity.setCreateDate(equipTreeEntityOld.getCreateDate());
			equipTreeEntity.setTreeType(EquipTreeEnum.LOGICALEQUIP.getId());
			equipTreeEntity.setUpdateUserId(userEntity.getId());
			equipTreeEntity.setUpdateDate(new Date());
			equipTreeEntity.setStatus(EquipTreeEnum.NORMAL.getId());
			equipTreeEntity.setEquipId(Long.valueOf(EquipTreeEnum.LOGICALEQUIPID.getId()));
			equipTreeDao.updateEntity(equipTreeEntity);
		}
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPTREE.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		return resultObj;
	}

	@Override
	public List<MaterialCategoryEntity> searchMaterialCategory(String equipIdString,Map<String, Object> params) {
		// TODO Auto-generated method stub
		ResultObj resultObj = new ResultObj();
		List<Condition> conditions1 = new ArrayList<Condition>();
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		Page<MaterialCategoryEntity> page =  PageUtil.getPage(params);
		Page<OverhaulSpareconsumeEntity> page1 =  PageUtil.getPage(params);
		conditions1.add(new Condition("O.C_EQUIP_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, equipIdString));
		List<OverhaulSpareconsumeEntity> overhaulSpareconsumeList = overhaulSpareconsumeService.findByCondition(conditions1, page1);
		if(overhaulSpareconsumeList!=null&&overhaulSpareconsumeList.size()>0){
			for(OverhaulSpareconsumeEntity overhaulSpareconsumeEntity : overhaulSpareconsumeList){
				conditions.add(new Condition("m.C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, overhaulSpareconsumeEntity.getCode()));
				List<MaterialCategoryEntity> materialCategoryList = materialCategoryService.findByCondition(conditions, page);
				if(materialCategoryList!=null&&materialCategoryList.size()>0){
					return materialCategoryList;
				}
			}
		}
			return (List<MaterialCategoryEntity>) new ArrayList<MaterialCategoryEntity>();
		
	}
	  /**
	   * 查询
	   */
	  @Override
	  public List<EquipTreeEntity> findChildTreeById(Long id){
	    List<EquipTreeEntity> tree = new ArrayList<EquipTreeEntity>();
	    EquipTreeEntity rootTree = this.findById(id);
	    List<EquipTreeEntity>  equipTreeList = this.findAll();
	    List<EquipTreeEntity> childTree = findChildTree(equipTreeList ,id);
	    tree.add(rootTree);
	    tree.addAll(childTree);
	    return tree;
	  }
	  /**
	   * 递归查询树节点
	   */
	  public List<EquipTreeEntity> findChildTree(List<EquipTreeEntity> equipTreeList ,Long id){
	    List<EquipTreeEntity> tree = new ArrayList<EquipTreeEntity>();
	    for(EquipTreeEntity treeEntity : equipTreeList){
	      if(treeEntity.getParentId().equals(id)){
	        tree.add(treeEntity);
	        tree.addAll(findChildTree(equipTreeList,treeEntity.getId()));
	      }
	    }
	    return tree;
	  }

}