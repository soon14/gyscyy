package com.aptech.business.wareHouse.wareHouseArea.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.cargo.inStockDetail.domain.InstockDetailEntity;
import com.aptech.business.cargo.inStockDetail.service.InstockDetailService;
import com.aptech.business.wareHouse.wareHouse.domain.WareHouseEntity;
import com.aptech.business.wareHouse.wareHouse.service.WareHouseService;
import com.aptech.business.wareHouse.wareHouseArea.domain.WareHouseAreaEntity;
import com.aptech.business.wareHouse.wareHouseArea.service.WareHouseAreaService;
import com.aptech.business.wareHouse.wareHouseAreaPosition.domain.WareHouseAreaPositionEntity;
import com.aptech.business.wareHouse.wareHouseAreaPosition.service.WareHouseAreaPositionService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 仓库货区表配置控制器
 *
 * @author 
 * @created 2017-11-06 19:58:49
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/wareHouseArea")
public class WareHouseAreaController extends BaseController<WareHouseAreaEntity> {
	
	@Autowired
	private WareHouseAreaService wareHouseAreaService;
	@Autowired
	private WareHouseService wareHouseService;
	@Autowired
	private WareHouseAreaPositionService wareHouseAreaPositionService;
	@Autowired
	private InstockDetailService instockDetailService;
	@Autowired
	private SysUnitService sysUnitService;
	@Override
	public IBaseEntityOperation<WareHouseAreaEntity> getService() {
		return wareHouseAreaService;
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView list(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("w.C_DELETE_FLAG", FieldTypeEnum.LONG, MatchTypeEnum.EQ,"N"));
		List<WareHouseEntity> wareHouseEntities=wareHouseService.findByCondition(conditions, null);
		ComboboxVO wareHouseVO = new ComboboxVO();
		for(WareHouseEntity wareHouseEntity:wareHouseEntities){
			wareHouseVO.addOption(wareHouseEntity.getId().toString(), wareHouseEntity.getWareHouseName());
		}
		model.put("wareHouse", JsonUtil.toJson(wareHouseVO.getOptions()));
		return this.createModelAndView("wareHouse/wareHouseArea/wareHouseAreaList", model);
	}
	
	/**
	 * 
	 * 列表检索
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @return
	 * @return ResultListObj
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月9日 上午9:19:47
	 * @lastModified
	 */
	@RequestMapping(value = "/data/list")
	public  @ResponseBody ResultListObj dataList(HttpServletRequest request,@RequestBody Map<String, Object> params){
		Page<WareHouseAreaEntity> pages=PageUtil.getPage(params);
		pages.addOrder(Sort.desc("id"));
		List<Condition>conditions=OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.LONG, MatchTypeEnum.EQ,'N'));
		List<WareHouseAreaEntity>wareHouseAreaEntities = wareHouseAreaService.findByCondition(conditions, pages);
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (wareHouseAreaEntities != null) {
			if (pages != null) {
				resultObj.setData(wareHouseAreaEntities);
				resultObj.setRecordsTotal(pages.getTotal());
			}
		}
		return resultObj;
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		Map<String, Object> model = new HashMap<String, Object>();
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("w.C_DELETE_FLAG", FieldTypeEnum.LONG, MatchTypeEnum.EQ,"N"));
		conditions.add(new Condition("w.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,sysUserEntity.getUnitId()));
		List<WareHouseEntity> wareHouseEntities=wareHouseService.findByCondition(conditions, null);
		ComboboxVO wareHouseVO = new ComboboxVO();
		for(WareHouseEntity wareHouseEntity:wareHouseEntities){
			wareHouseVO.addOption(wareHouseEntity.getId().toString(), wareHouseEntity.getWareHouseName());
		}
		model.put("wareHouse", JsonUtil.toJson(wareHouseVO.getOptions()));
		return this.createModelAndView("wareHouse/wareHouseArea/wareHouseAreaAdd", model);
	}
	
	/**
	 * 
	 * 添加保存
	 * 
	 * @param @param warningQuotaEntity
	 * @param @param request
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月9日 下午1:08:27
	 * @lastModified
	 */
	@RequestMapping("/saveAddPage")
	public @ResponseBody ResultObj saveAddPage(@RequestBody WareHouseAreaEntity wareHouseAreaEntity, HttpServletRequest request) {
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("a.C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,wareHouseAreaEntity.getCode()));
		conditions.add(new Condition("a.C_WAREHOUSE_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,wareHouseAreaEntity.getWarehouseId()));
		List<WareHouseAreaEntity> list = wareHouseAreaService.findByCondition(conditions, null);
		ResultObj resultObj = new ResultObj();
		if (list.size() > 0) {
			resultObj.setResult("error");
			return resultObj;
		}
		wareHouseAreaService.addEntity(wareHouseAreaEntity);
		resultObj.setData(wareHouseAreaEntity);
		return resultObj;
	}
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		WareHouseAreaEntity wareHouseAreaEntity = (WareHouseAreaEntity)wareHouseAreaService.findById(id);
		model.put("entity", wareHouseAreaEntity);
		model.put("entityJson", JsonUtil.toJson(wareHouseAreaEntity));
		
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("w.C_DELETE_FLAG", FieldTypeEnum.LONG, MatchTypeEnum.EQ,"N"));
		List<WareHouseEntity> wareHouseEntities=wareHouseService.findByCondition(conditions, null);
		ComboboxVO wareHouseVO = new ComboboxVO();
		for(WareHouseEntity wareHouseEntity:wareHouseEntities){
			wareHouseVO.addOption(wareHouseEntity.getId().toString(), wareHouseEntity.getWareHouseName());
		}
		model.put("wareHouse", JsonUtil.toJson(wareHouseVO.getOptions()));
		return this.createModelAndView("wareHouse/wareHouseArea/wareHouseAreaEdit", model);
	}
	
	/**
	 * 
	 * 修改保存
	 * 
	 * @param @param wareHouseAreaEntity
	 * @param @param request
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月9日 下午1:14:40
	 * @lastModified
	 */
	@RequestMapping("/saveEditPage/{id}")
	public @ResponseBody ResultObj saveEditPage(@RequestBody WareHouseAreaEntity wareHouseAreaEntity, HttpServletRequest request,@PathVariable Long id) {
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("a.C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,wareHouseAreaEntity.getCode()));
		conditions.add(new Condition("a.C_WAREHOUSE_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,wareHouseAreaEntity.getWarehouseId()));
		conditions.add(new Condition("a.C_ID", FieldTypeEnum.STRING, MatchTypeEnum.NE,id));
		List<WareHouseAreaEntity> list = wareHouseAreaService.findByCondition(conditions, null);
		ResultObj resultObj = new ResultObj();
		if (list.size() > 0) {
			resultObj.setResult("error");
			return resultObj;
		}
		wareHouseAreaService.updateEntity(wareHouseAreaEntity);
		resultObj.setData(wareHouseAreaEntity);
		return resultObj;
	}
	
	/**
	 * 
	 * 单行删除
	 * 
	 * @param @param id
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月9日 上午9:27:12
	 * @lastModified
	 */
	@RequestMapping("/deleteSingle/{id}")
    public @ResponseBody ResultObj deleteSingle(@PathVariable Long id){
		ResultObj resultObj = new ResultObj();
		List<String> instockGoodAreaIds= new ArrayList<String>();
		List<InstockDetailEntity> instockDetailEntities = instockDetailService.findAll();
		for(InstockDetailEntity instockDetailEntity:instockDetailEntities){
			instockGoodAreaIds.add(instockDetailEntity.getGoodsAreaId());
		}
		if(!instockGoodAreaIds.contains(id.toString())){
			//删除货区信息
			WareHouseAreaEntity wareHouseAreaEntity = wareHouseAreaService.findById(id);
			wareHouseAreaService.deleteEntity(id);
			//删除货区货位关联信息
			List<Condition> conditions = new ArrayList<Condition>();
			conditions.add(new Condition("C_GOOD_AREA_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,id));
			List<WareHouseAreaPositionEntity> wareHouseAreaPositionEntities = wareHouseAreaPositionService.findByCondition(conditions, null);
			for (WareHouseAreaPositionEntity wareHouseAreaPositionEntity:wareHouseAreaPositionEntities) {
				wareHouseAreaPositionService.deleteEntity(wareHouseAreaPositionEntity.getId());
				resultObj.setResult("success");
			}
		}else{
			resultObj.setResult("exception");
		}
    	return resultObj;
    }
	
	/**
	 * 
	 * 多行删除
	 * 
	 * @param @param request
	 * @param @param ids
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月9日 上午9:27:36
	 * @lastModified
	 */
	@RequestMapping("/deleteMulti/{ids}")
    public @ResponseBody ResultObj deleteMulti(HttpServletRequest request, @PathVariable String ids){
		ResultObj resultObj = new ResultObj();
		String[] wareAreaIds = ids.split(",");
		List<String> instockGoodAreaIds= new ArrayList<String>();
		List<InstockDetailEntity> instockDetailEntities = instockDetailService.findAll();
		for(InstockDetailEntity instockDetailEntity:instockDetailEntities){
			instockGoodAreaIds.add(instockDetailEntity.getGoodsAreaId());
		}
		boolean isDelete=true;
		for(int i=0;i<wareAreaIds.length;i++){
			if(instockGoodAreaIds.indexOf(wareAreaIds[i])>=0){
				isDelete=false;
				break;
			}
		}
		if(isDelete==true){
			for(int i=0;i<wareAreaIds.length;i++){
				wareHouseAreaService.deleteEntity(Long.valueOf(wareAreaIds[i]));
				//删除货区货位关联信息
				List<Condition> conditions = new ArrayList<Condition>();
				conditions.add(new Condition("C_GOOD_AREA_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,Long.valueOf(wareAreaIds[i])));
				List<WareHouseAreaPositionEntity> wareHouseAreaPositionEntities = wareHouseAreaPositionService.findByCondition(conditions, null);
				for (WareHouseAreaPositionEntity wareHouseAreaPositionEntity:wareHouseAreaPositionEntities) {
					wareHouseAreaPositionService.deleteEntity(wareHouseAreaPositionEntity.getId());
					resultObj.setResult("success");
				}
			}
		}else{
			resultObj.setResult("exception");
		}
    	return resultObj;
    }
	@RequestMapping("/changeUnit")
	public @ResponseBody ResultObj changeUnit(HttpServletRequest request, @RequestBody WareHouseAreaEntity wareHouseAreaEntity){
		WareHouseEntity wareHouseEntity = wareHouseService.findById(Long.valueOf(wareHouseAreaEntity.getWarehouseId()));
		SysUnitEntity sysUnitEntity = sysUnitService.findById(Long.valueOf(wareHouseEntity.getUnitId()));
		ResultObj resultObj = new ResultObj();
		resultObj.setData(sysUnitEntity);
		return resultObj;
		
	}
}