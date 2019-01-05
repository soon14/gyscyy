package com.aptech.business.managePlanContract.goodsPurchase.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.GoodsPurchaseBtnTypeEnum;
import com.aptech.business.component.dictionary.GoodsPurchaseStatusEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.managePlanContract.goodsPurchase.dao.GoodsPurchaseDao;
import com.aptech.business.managePlanContract.goodsPurchase.domain.GoodsPurchaseEntity;
import com.aptech.business.managePlanContract.goodsRelation.domain.GoodsRelationEntity;
import com.aptech.business.managePlanContract.goodsRelation.service.GoodsRelationService;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.workflow.modelEditor.domain.BranchMarkEnum;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.common.workflow.todoTask.domain.ExamResultEnum;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 物资采购应用管理服务实现类
 *
 * @author 
 * @created 2018-04-17 15:00:02
 * @lastModified 
 * @history
 *
 */
@Service("goodsPurchaseService")
@Transactional
public class GoodsPurchaseServiceImpl extends AbstractBaseEntityOperation<GoodsPurchaseEntity> implements GoodsPurchaseService {
	
	@Autowired
	private GoodsPurchaseDao goodsPurchaseDao;
	@Autowired
	private GoodsRelationService goodsRelationService;
	@Autowired
   	private ActTaskService actTaskService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<GoodsPurchaseEntity> getDao() {
		return goodsPurchaseDao;
	}
	
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if(page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("C_ID"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getUnitId()));
		List<O> resultList = super.findByCondition(conditions, page);
		return resultList;
	}
	
	
	@Override
	public ResultObj addEntityList(GoodsPurchaseEntity t,HttpServletRequest request) {
		ResultObj resultObj = new ResultObj();
		t.setCreateDate(new Date());
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setCreateUserId(userEntity.getId().toString());
		t.setStatus(GoodsPurchaseStatusEnum.PENDING.getCode());
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		t.setUnitId(sysUserEntity.getUnitId().toString());
		super.addEntity(t);
		
		GoodsPurchaseEntity purchaseEntity = goodsPurchaseDao.findById(t.getId());
		List<GoodsRelationEntity> rList = t.getGoodsList();
		if(rList!=null&&rList.size()>0){
			for (GoodsRelationEntity goodsEntity : rList) {

			GoodsRelationEntity relationEntity = new GoodsRelationEntity();
			relationEntity.setCreateDate(new Date());
			relationEntity.setCreateUserId(userEntity.getId().toString());
			relationEntity.setGoodsPurchaseId(purchaseEntity.getId());
			relationEntity.setYearPurchaseId(goodsEntity.getId());
			relationEntity.setEquipName(goodsEntity.getEquipName());
			relationEntity.setEquipType(goodsEntity.getEquipType());
			relationEntity.setSpecification(goodsEntity.getSpecification());
			relationEntity.setAmount(goodsEntity.getAmount());
			relationEntity.setUnit(goodsEntity.getUnit());
			relationEntity.setBudgePrice(goodsEntity.getBudgePrice());
			relationEntity.setTotalPrice(goodsEntity.getTotalPrice());
			relationEntity.setProjectName(goodsEntity.getProjectName());
			goodsRelationService.addEntity(relationEntity);

		}
	}
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.GOODSPURCHASE.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
			return resultObj;
	}

	@Override
	public void updateEntity(GoodsPurchaseEntity t) {
		super.updateEntity(t);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		GoodsPurchaseEntity purchaseEntity = goodsPurchaseDao.findById(t.getId());
		
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("O.C_GOODS_PURCHASE_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,purchaseEntity.getId()));
		List<GoodsRelationEntity> taskEntities = goodsRelationService.findByCondition(conditions, null);
		
		//删除计划关联deleteByGoodsId
//		if (!taskEntities.isEmpty()) {
//			for (GoodsRelationEntity goodsRelationEntity : taskEntities) {
//			
//				goodsRelationService.deleteEntity(goodsRelationEntity.getId());
//			}
//		}
//		
		   List<GoodsRelationEntity> rList = t.getGoodsList();
			
			for (GoodsRelationEntity goodsEntity : rList) {
				
				if (goodsEntity.getEquipName()==null&&goodsEntity.getEquipType()==null&& goodsEntity.getSpecification()==null&&goodsEntity.getProjectName()==null) {
					GoodsRelationEntity relationEntity = goodsRelationService.findById(goodsEntity.getId());
					relationEntity.setCreateDate(relationEntity.getCreateDate());
					relationEntity.setCreateUserId(relationEntity.getCreateUserId());
					goodsEntity.setGoodsPurchaseId(relationEntity.getGoodsPurchaseId());
					goodsEntity.setYearPurchaseId(relationEntity.getYearPurchaseId());
					goodsEntity.setEquipName(relationEntity.getEquipName());
					goodsEntity.setEquipType(relationEntity.getEquipType());
					goodsEntity.setSpecification(relationEntity.getSpecification());
					goodsEntity.setAmount(relationEntity.getAmount());
					goodsEntity.setUnit(relationEntity.getUnit());
					goodsEntity.setBudgePrice(relationEntity.getBudgePrice());
					goodsEntity.setTotalPrice(relationEntity.getTotalPrice());
					goodsEntity.setProjectName(relationEntity.getProjectName());
					goodsRelationService.updateEntity(goodsEntity);
				}else{
			
					GoodsRelationEntity relationEntity = new GoodsRelationEntity();
					relationEntity.setCreateDate(new Date());
					relationEntity.setCreateUserId(userEntity.getId().toString());
					relationEntity.setGoodsPurchaseId(purchaseEntity.getId());
					relationEntity.setYearPurchaseId(goodsEntity.getId());
					relationEntity.setEquipName(goodsEntity.getEquipName());
					relationEntity.setEquipType(goodsEntity.getEquipType());
					relationEntity.setSpecification(goodsEntity.getSpecification());
					relationEntity.setAmount(goodsEntity.getAmount());
					relationEntity.setUnit(goodsEntity.getUnit());
					relationEntity.setBudgePrice(goodsEntity.getBudgePrice());
					relationEntity.setTotalPrice(goodsEntity.getTotalPrice());
					relationEntity.setProjectName(goodsEntity.getProjectName());
					goodsRelationService.addEntity(relationEntity);
				}
					
				}
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.GOODSPURCHASE.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
			}
	
	

	/**提交
	 * @Description:   
	 * @author         
	 * @Date           
	 * @throws         Exception
	 */
	@Override
	public ResultObj submit(String id,  String selectUser) {
		ResultObj resultObj = new ResultObj();
		GoodsPurchaseEntity entity  = this.findById(Long.parseLong(id));
		entity.setStatus(GoodsPurchaseStatusEnum.OVERHAUL.getCode());
		super.updateEntity(entity);
		// 准备启动流程
		ProcessInstance processInstance = actTaskService.startProcess(ProcessMarkEnum.GOODS_PURCHASE_PROCESS_KEY.getName(), id, null);
		
		//越过第一步
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);
		vars.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
		actTaskService.completeFirstTask(processInstance.getId(), vars);
//		ProcessInstance pInstance = actTaskService.startProcess(ProcessMarkEnum.YEAR_PURCHASE_PROCESS_KEY.getName(), id.toString(), vars);
//		actTaskService.complete(taskId, pInstance.getId(), vars);
//		actTaskService.complete(taskId,pInstance.getId(),null);
		return resultObj;
	}
//同意
	@Override
	public void updateSpnrAgree(GoodsPurchaseEntity t,SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(GoodsPurchaseBtnTypeEnum.synmangeBtn.getCode())){
		    GoodsPurchaseEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
		     workFireEntity.setStatus(GoodsPurchaseStatusEnum.PLANEXECUTED.getCode());
			super.updateEntity(workFireEntity);
		
		}else if(spFlag.equals(GoodsPurchaseBtnTypeEnum.technologyBtn.getCode())){
			GoodsPurchaseEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
			  workFireEntity.setStatus(GoodsPurchaseStatusEnum.PLANEXECUTED.getCode());
			super.updateEntity(workFireEntity);

		}else if(spFlag.equals(GoodsPurchaseBtnTypeEnum.planBtn.getCode())){
			GoodsPurchaseEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
			 workFireEntity.setStatus(GoodsPurchaseStatusEnum.LEADER.getCode());
			super.updateEntity(workFireEntity);

		}else if(spFlag.equals(GoodsPurchaseBtnTypeEnum.leaderBtn.getCode())){
			GoodsPurchaseEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
			 workFireEntity.setStatus(GoodsPurchaseStatusEnum.EXECUTED.getCode());
			super.updateEntity(workFireEntity);

		}else if(spFlag.equals(GoodsPurchaseBtnTypeEnum.planoperationBtn.getCode())){
			GoodsPurchaseEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
			 workFireEntity.setStatus(GoodsPurchaseStatusEnum.FINISH.getCode());
            super.updateEntity(workFireEntity);
            
		}else if(spFlag.equals(GoodsPurchaseBtnTypeEnum.ZTJ.getCode())){
			//更新主表状态
			GoodsPurchaseEntity workFireEntity=goodsPurchaseDao.findById(t.getId());
			 workFireEntity.setStatus(GoodsPurchaseStatusEnum.OVERHAUL.getCode());
			 super.updateEntity(workFireEntity);
		}
	}
	/**
	 * 驳回   zzq 20170614
	 */
	@Override
	public void updateSpnrDisagree(GoodsPurchaseEntity t,SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(GoodsPurchaseBtnTypeEnum.synmangeBtn.getCode())|| spFlag.equals(GoodsPurchaseBtnTypeEnum.technologyBtn.getCode())){
			GoodsPurchaseEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
			 workFireEntity.setStatus(GoodsPurchaseStatusEnum.REJECT.getCode());
			super.updateEntity(workFireEntity);
			
		}else if(spFlag.equals(GoodsPurchaseBtnTypeEnum.planBtn.getCode())){
			GoodsPurchaseEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
			  workFireEntity.setStatus(GoodsPurchaseStatusEnum.REJECT.getCode());
			  super.updateEntity(workFireEntity);
		}else if(spFlag.equals(GoodsPurchaseBtnTypeEnum.leaderBtn.getCode())){
			GoodsPurchaseEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
			  workFireEntity.setStatus(GoodsPurchaseStatusEnum.REJECT.getCode());
			  super.updateEntity(workFireEntity); 
		    
        }else if(spFlag.equals(GoodsPurchaseBtnTypeEnum.FP.getCode())){
        	GoodsPurchaseEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
            //更新主表状态为废票
            workFireEntity.setStatus(GoodsPurchaseStatusEnum.UNFINISH.getCode());
            goodsPurchaseDao.updateEntity(workFireEntity);
        }
	}
	@Override
	public void deleteEntity(Serializable id) {
		getDao().deleteEntity(id);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.GOODSPURCHASE.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}

	@Override
	public void bulkDelete(Serializable[] ids) {
		getDao().bulkDelete(ids);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.GOODSPURCHASE.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}	
}