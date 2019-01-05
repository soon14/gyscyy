package com.aptech.business.managePlanContract.goodsRelation.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.managePlanContract.goodsRelation.dao.GoodsRelationDao;
import com.aptech.business.managePlanContract.goodsRelation.domain.GoodsRelationEntity;
import com.aptech.business.overhaul.overhaulArrange.domain.OverhaulWorkTaskStatusEnum;
import com.aptech.business.overhaul.overhaulSpareconsume.domain.OverhaulSpareconsumeEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.DataStatusEnum;
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
 * 物资关联应用管理服务实现类
 *
 * @author 
 * @created 2018-04-19 09:23:45
 * @lastModified 
 * @history
 *
 */
@Service("goodsRelationService")
@Transactional
public class GoodsRelationServiceImpl extends AbstractBaseEntityOperation<GoodsRelationEntity> implements GoodsRelationService {
	
	@Autowired
	private GoodsRelationDao goodsRelationDao;
	
	@Override
	public IBaseEntityOperation<GoodsRelationEntity> getDao() {
		return goodsRelationDao;
	}
	
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if(page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		page.setOrders(OrmUtil.changeMapToOrders(params));
   		
   		String goodsId = (String)params.get("goodsId");

		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("O.C_GOODS_PURCHASE_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, goodsId));
   		
		List<O> resultList = super.findByCondition(conditions, page);
		return resultList;
	}

	//删除
	@Override
	public <T> ResultObj delete(HttpServletRequest request, Long id) {
		ResultObj resultObj = new ResultObj();
		GoodsRelationEntity goodEntity = goodsRelationDao.findById(id);
		if(goodEntity!=null){

			goodsRelationDao.deleteEntity(id);
		}
		return resultObj;
	}
	
	
	@Override
	public <T> ResultObj addGoodsInfo(HttpServletRequest request,Map<String, Object> params) {
		ResultObj resultObj = new ResultObj();
		//登录人信息
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<Condition> conditions = new ArrayList<Condition>();
		//物资信息
		List<Map<String, Object>> baseParameters = (List<Map<String, Object>>)params.get("goodsInfo");
		for(Map<String, Object> map:baseParameters){
			Long  equipid = Long.parseLong(map.get("equipid").toString());
			List<Condition> Conditions = new ArrayList<Condition>();
//			Conditions.add(new Condition("O.C_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,equipid));
//			Conditions.add(new Condition("O.TB_GOODS_PURCHASE",FieldTypeEnum.INT,MatchTypeEnum.EQ,overhaulArrangeId));
			List<GoodsRelationEntity> workTaskEntities =  goodsRelationDao.findByCondition(Conditions, null);
			//如果没有该设备
			if(workTaskEntities.isEmpty()){
				GoodsRelationEntity  workTaskEntity = new GoodsRelationEntity();
//				workTaskEntity.setGoodsPurchaseId(overhaulArrangeId);
				workTaskEntity.setCreateDate(new Date());
				workTaskEntity.setCreateUserId(userEntity.getId().toString());
				workTaskEntity.setEquipName(map.get("name").toString());
				workTaskEntity.setEquipType(map.get("typeName").toString());
				workTaskEntity.setSpecification(map.get("specification").toString());
				workTaskEntity.setAmount(map.get("amount").toString());
				workTaskEntity.setBudgePrice(map.get("budgetPrice").toString());
				workTaskEntity.setTotalPrice(map.get("totalPrice").toString());
				workTaskEntity.setProjectName(map.get("projectName").toString());
				goodsRelationDao.addEntity(workTaskEntity);
			}
		}
//		conditions.add(new Condition("O.C_ID",FieldTypeEnum.STRING,MatchTypeEnum.NE,overhaulArrangeId));
		List<GoodsRelationEntity> workTaskEntities =  goodsRelationDao.findByCondition(conditions, null);
		if(workTaskEntities.isEmpty()){
			resultObj.setData(workTaskEntities);
		}
		return resultObj;
	}
}