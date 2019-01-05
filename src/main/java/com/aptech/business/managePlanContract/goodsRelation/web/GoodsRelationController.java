package com.aptech.business.managePlanContract.goodsRelation.web;

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

import com.aptech.business.managePlanContract.goodsRelation.domain.GoodsRelationEntity;
import com.aptech.business.managePlanContract.goodsRelation.service.GoodsRelationService;
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
 * 物资关联配置控制器
 *
 * @author 
 * @created 2018-04-19 09:23:45
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/goodsRelation")
public class GoodsRelationController extends BaseController<GoodsRelationEntity> {
	
	@Autowired
	private GoodsRelationService goodsRelationService;
	
	@Override
	public IBaseEntityOperation<GoodsRelationEntity> getService() {
		return goodsRelationService;
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
		List<GoodsRelationEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("goodsRelationTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboGoodsRelationVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("goodsRelationCombobox", JsonUtil.toJson(comboGoodsRelationVO.getOptions()));
		return this.createModelAndView("goodsRelation/resource/views/goodsRelationList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<GoodsRelationEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("goodsRelationTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboGoodsRelationVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("goodsRelationCombobox", JsonUtil.toJson(comboGoodsRelationVO.getOptions()));
		
		return this.createModelAndView("goodsRelation/resource/views/goodsRelationAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		GoodsRelationEntity goodsRelationEntity = (GoodsRelationEntity)goodsRelationService.findById(id);
		model.put("entity", goodsRelationEntity);
		model.put("entityJson", JsonUtil.toJson(goodsRelationEntity));
		
		List<GoodsRelationEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("goodsRelationTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboGoodsRelationVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("goodsRelationCombobox", JsonUtil.toJson(comboGoodsRelationVO.getOptions()));
		
		return this.createModelAndView("goodsRelation/resource/views/goodsRelationEdit", model);
	}
	
	/**
	 *	删除
	 */
	@RequestMapping("/delete/{id}")
	public @ResponseBody <T> ResultObj delete(HttpServletRequest request, @PathVariable Long id){
		return goodsRelationService.delete(request, id);
	}
	
	/**
	 *	添加设备相关数据
	 */
	@RequestMapping("/addGoodsInfo")
	public @ResponseBody <T> ResultObj addMaterialInfo(HttpServletRequest request,@RequestBody Map<String, Object> params){

		return goodsRelationService.addGoodsInfo(request, params);
	}

	/**
	   * 
	   * 修改页面的列表检索
	   * 
	   * @param @param request
	   * @param @param params
	   * @param @param outstockId
	   * @param @return
	   * @return ResultListObj
	   * @throws 
	   * @author wangyue
	   * @created 2017年7月27日 下午7:19:00
	   * @lastModified
	   */
	  @RequestMapping("/editData/list/{goodspurchaseId}")
	  public @ResponseBody ResultListObj EditList(HttpServletRequest request,@RequestBody Map<String, Object> params, @PathVariable Long goodspurchaseId){
	    Page<GoodsRelationEntity> pages = PageUtil.getPage(params);
//	    pages.addOrder(Sort.asc("O.C_ID"));
	    List<Condition> conditions = OrmUtil.changeMapToCondition(params);
	    conditions.add(new Condition("O.C_GOODS_PURCHASE_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,goodspurchaseId));
	    List<GoodsRelationEntity> goodsRelationEntities = goodsRelationService.findByCondition(conditions, pages);
	    //获得返回结果
	    ResultListObj resultObj = new ResultListObj();
	    resultObj.setDraw((Integer)params.get("draw"));
	    if (goodsRelationEntities != null) {
	      if (pages != null) {
	        resultObj.setData(goodsRelationEntities);
	        resultObj.setRecordsTotal(pages.getTotal());
	      }
	    }
	    return resultObj;
	  }

}