package com.aptech.business.wareHouse.wareHouseAreaPosition.web;

import java.text.ParseException;
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
import com.aptech.business.wareHouse.wareHouseAreaPosition.domain.WareHouseAreaPositionEntity;
import com.aptech.business.wareHouse.wareHouseAreaPosition.service.WareHouseAreaPositionService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 货区货位关联表配置控制器
 *
 * @author 
 * @created 2017-11-06 20:01:23
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/wareHouseAreaPosition")
public class WareHouseAreaPositionController extends BaseController<WareHouseAreaPositionEntity> {
	
	@Autowired
	private WareHouseAreaPositionService wareHouseAreaPositionService;
	@Autowired
	private InstockDetailService instockDetailService;
	@Override
	public IBaseEntityOperation<WareHouseAreaPositionEntity> getService() {
		return wareHouseAreaPositionService;
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
		List<WareHouseAreaPositionEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("wareHouseAreaPositionTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboWareHouseAreaPositionVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("wareHouseAreaPositionCombobox", JsonUtil.toJson(comboWareHouseAreaPositionVO.getOptions()));
		return this.createModelAndView("wareHouse/wareHouseAreaPosition/wareHouseAreaPositionList", model);
	}
	
	/**
	 * 
	 * 根据所属货区跳转页面
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @param id
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月7日 上午9:38:20
	 * @lastModified
	 */
	@RequestMapping("/goodsPosition/{id}")
	public ModelAndView goodsPosition(HttpServletRequest request, Map<String, Object> params, @PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("wareAreaId", JsonUtil.toJson(id));
		return this.createModelAndView("wareHouse/wareHouseAreaPosition/wareHouseAreaPositionList", model);
	}
	
	/**
	 * 
	 * 根据所属货区列表检索
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @param chooseId
	 * @param @return
	 * @return ResultListObj
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月7日 上午9:39:00
	 * @lastModified
	 */
	@RequestMapping(value = "/data/list/{wareAreaId}")
	public  @ResponseBody ResultListObj dataList(HttpServletRequest request,@RequestBody Map<String, Object> params,@PathVariable Long wareAreaId){
		List<Condition> conditions=OrmUtil.changeMapToCondition(params);
		Page<WareHouseAreaPositionEntity> page = PageUtil.getPage(params);
		conditions.add(new Condition("p.C_GOOD_AREA_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,wareAreaId));
		conditions.add(new Condition("p.C_STATUS", FieldTypeEnum.LONG, MatchTypeEnum.EQ,'N'));
		List<WareHouseAreaPositionEntity> wareHouseAreaPositionEntities=wareHouseAreaPositionService.findByCondition(conditions,page);
		//获得返回结果
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (wareHouseAreaPositionEntities != null) {
			resultObj.setData(wareHouseAreaPositionEntities);
			resultObj.setRecordsTotal(page.getTotal());
		}
		return resultObj;
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd/{wareAreaId}")
	public ModelAndView getAddPage(HttpServletRequest request, @PathVariable Long wareAreaId){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("wareAreaId", JsonUtil.toJson(wareAreaId));
		return this.createModelAndView("wareHouse/wareHouseAreaPosition/wareHouseAreaPositionAdd", model);
	}
	
	/**
	 * 
	 * 添加保存
	 * 
	 * @param @param entityMap
	 * @param @param wareAreaId
	 * @param @return
	 * @param @throws ParseException
	 * @return ResultObj
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月9日 上午10:32:38
	 * @lastModified
	 */
	@RequestMapping("/saveAddPage/{wareAreaId}")
	public @ResponseBody ResultObj saveAddPage(@RequestBody Map<String, Object> entityMap,@PathVariable Long wareAreaId) throws ParseException{
		ResultObj resultObj = new ResultObj();
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("p.C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,entityMap.get("code").toString()));
		conditions.add(new Condition("p.C_GOOD_AREA_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,wareAreaId));
		List<WareHouseAreaPositionEntity> wareHouseAreaPositionEntities = wareHouseAreaPositionService.findByCondition(conditions, null);
		if(wareHouseAreaPositionEntities.size()>0){
			resultObj.setResult("exception");
			return resultObj;
		}
		WareHouseAreaPositionEntity wareHouseAreaPositionEntity = new WareHouseAreaPositionEntity();
		wareHouseAreaPositionEntity.setGoodAreaId(wareAreaId);
		wareHouseAreaPositionEntity.setCode(entityMap.get("code").toString());
		wareHouseAreaPositionEntity.setName(entityMap.get("name").toString());
		wareHouseAreaPositionEntity.setStatus("N");
		if(null!=entityMap.get("remark")&&""!=entityMap.get("remark")){
			wareHouseAreaPositionEntity.setRemark(entityMap.get("remark").toString());
		}
		wareHouseAreaPositionService.addEntity(wareHouseAreaPositionEntity);
		return resultObj;
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		WareHouseAreaPositionEntity wareHouseAreaPositionEntity = (WareHouseAreaPositionEntity)wareHouseAreaPositionService.findById(id);
		model.put("entity", wareHouseAreaPositionEntity);
		model.put("entityJson", JsonUtil.toJson(wareHouseAreaPositionEntity));
		
		List<WareHouseAreaPositionEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("wareHouseAreaPositionTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboWareHouseAreaPositionVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("wareHouseAreaPositionCombobox", JsonUtil.toJson(comboWareHouseAreaPositionVO.getOptions()));
		
		return this.createModelAndView("wareHouse/wareHouseAreaPosition/wareHouseAreaPositionEdit", model);
	}
	
	/**
	 * 
	 * 修改保存
	 * 
	 * @param @param wareHouseAreaEntity
	 * @param @param request
	 * @param @param id
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月9日 下午1:38:12
	 * @lastModified
	 */
	@RequestMapping("/saveEditPage/{id}")
	public @ResponseBody ResultObj saveEditPage(@RequestBody WareHouseAreaPositionEntity wareHouseAreaPositionEntity, HttpServletRequest request,@PathVariable Long id) {
		ResultObj resultObj = new ResultObj();
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("p.C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,wareHouseAreaPositionEntity.getCode()));
		conditions.add(new Condition("p.C_GOOD_AREA_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,wareHouseAreaPositionEntity.getGoodAreaId()));
		conditions.add(new Condition("p.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.NE,id));
		List<WareHouseAreaPositionEntity> wareHouseAreaPositionEntities = wareHouseAreaPositionService.findByCondition(conditions, null);
		if(wareHouseAreaPositionEntities.size()>0){
			resultObj.setResult("exception");
			return resultObj;
		}
		wareHouseAreaPositionService.updateEntity(wareHouseAreaPositionEntity);
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
	 * @created 2017年11月9日 上午10:33:02
	 * @lastModified
	 */
	@RequestMapping("/deleteSingle/{id}")
    public @ResponseBody ResultObj deleteSingle(@PathVariable Long id){
		ResultObj resultObj = new ResultObj();
		List<Long> instockPositionIds=new ArrayList<Long>();
		List<InstockDetailEntity> instockDetailEntities = instockDetailService.findAll();
//		for(InstockDetailEntity instockDetailEntity:instockDetailEntities){
//			instockPositionIds.add(Long.valueOf(instockDetailEntity.getGoodsAllocationId()));
//		}
		if(!instockPositionIds.contains(id)){
			WareHouseAreaPositionEntity wareHouseAreaPositionEntity = wareHouseAreaPositionService.findById(id);
			wareHouseAreaPositionService.deleteEntity(wareHouseAreaPositionEntity.getId());
			resultObj.setResult("success");
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
	 * @created 2017年11月9日 上午10:33:21
	 * @lastModified
	 */
	@RequestMapping("/deleteMulti/{ids}")
    public @ResponseBody ResultObj deleteMulti(HttpServletRequest request, @PathVariable String ids){
		ResultObj resultObj = new ResultObj();
		String[] warePositionIds = ids.split(",");
		List<String> instockPositionIds=new ArrayList<String>();
		List<InstockDetailEntity> instockDetailEntities = instockDetailService.findAll();
//		for(InstockDetailEntity instockDetailEntity:instockDetailEntities){
//			instockPositionIds.add(instockDetailEntity.getGoodsAllocationId().toString());
//		}
		boolean isDelete=true;
		for(int i=0;i<warePositionIds.length;i++){
			if(instockPositionIds.indexOf(warePositionIds[i])>=0){
				isDelete=false;
				break;
			}
		}
		if(isDelete==true){
			for(int i=0;i<warePositionIds.length;i++){
				WareHouseAreaPositionEntity wareHouseAreaPositionEntity = wareHouseAreaPositionService.findById(Long.valueOf(warePositionIds[i]));
				wareHouseAreaPositionService.deleteEntity(wareHouseAreaPositionEntity.getId());
				resultObj.setResult("success");
			}
		}else{
			resultObj.setResult("exception");
		}
    	return resultObj;
    }
}