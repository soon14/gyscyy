package com.aptech.business.managePlanContract.supplierManage.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.managePlanContract.purchaseOrganization.domain.PurchaseOrganizationEntity;
import com.aptech.business.managePlanContract.purchaseType.domain.PurchaseTypeEntity;
import com.aptech.business.managePlanContract.purchaseType.service.PurchaseTypeService;
import com.aptech.business.managePlanContract.supplierManage.domain.SupplierManageEntity;
import com.aptech.business.managePlanContract.supplierManage.service.SupplierManageService;
import com.aptech.business.wareHouse.wareHouse.domain.WareHouseEntity;
import com.aptech.common.system.duties.domain.SysDutiesEntity;
import com.aptech.common.system.duties.service.SysDutiesService;
import com.aptech.common.system.dutiesdetail.domain.SysDutiesDetailEntity;
import com.aptech.common.system.dutiesdetail.service.SysDutiesDetailService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.userUnit.domain.UserUnitRelEntity;
import com.aptech.common.system.userUnit.service.UserUnitRelService;
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
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 供应商管理配置控制器
 *
 * @author 
 * @created 2018-07-26 17:15:46
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/supplierManage")
public class SupplierManageController extends BaseController<SupplierManageEntity> {
	
	@Autowired
	private SupplierManageService supplierManageService;
	@Autowired
	private PurchaseTypeService purchaseTypeService;
	@Autowired
	  private SysDutiesService sysDutiesService;
	  @Autowired
	  private SysDutiesDetailService sysDutiesDetailService;
	  @Autowired
	  private UserUnitRelService userUnitRelService;
	
	@Override
	public IBaseEntityOperation<SupplierManageEntity> getService() {
		return supplierManageService;
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index/{id}")
	public ModelAndView list(HttpServletRequest request, Map<String, Object> params,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<SupplierManageEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("supplierManageTreeList", JsonUtil.toJson(treeNodeList));
		
	    PurchaseTypeEntity typeEntity = purchaseTypeService.findById(id);
        model.put("typeEntity", typeEntity);
        model.put("type", id);
        SysUserEntity sysUserEntity=RequestContext.get().getUser();
        List<Condition> conditions = new ArrayList<Condition>();
	    conditions.add(new Condition("C_USER_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getId()));
	    List<UserUnitRelEntity> userUnitRelList = userUnitRelService.findByCondition(conditions, null);
	    List<Long> userUnitIds = new ArrayList<Long>();
	    for(UserUnitRelEntity userUnitRelEntity : userUnitRelList){
	      userUnitIds.add(userUnitRelEntity.getId());
	    }
	    conditions.clear();
	    conditions.add(new Condition("C_USER_UNIT_REL_ID", FieldTypeEnum.LONG, MatchTypeEnum.IN, userUnitIds.toArray()));
	    List<SysDutiesDetailEntity> sysDutiesDetailList = sysDutiesDetailService.findByCondition(conditions, null);
	    List<String> dutiesIds = new ArrayList<String>();
	    for(SysDutiesDetailEntity sysDutiesDetailEntity : sysDutiesDetailList){
	      dutiesIds.add(sysDutiesDetailEntity.getDutiesId());
	    }
	    conditions.clear();
	    conditions.add(new Condition("C_ID", FieldTypeEnum.STRING, MatchTypeEnum.IN, dutiesIds.toArray()));
	    List<SysDutiesEntity> sysDutiesList = sysDutiesService.findByCondition(conditions, null);
	    if(!sysDutiesList.isEmpty()){
	      for(SysDutiesEntity sysDutiesEntity : sysDutiesList){
	        if(sysDutiesEntity.getName().equals("计划经营处负责人")){ 
	          model.put("answerht", "1");//1有权限2没有权限
	          break;
	        }else{
	          model.put("answerht", "2");
	        }
	      }
	    }else{
	      model.put("answerht", "2");
	    }
		return this.createModelAndView("managePlanContract/supplierManage/supplierManageList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd/{type}")
	public ModelAndView getAddPage(HttpServletRequest request,@PathVariable String type){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SupplierManageEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("supplierManageTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSupplierManageVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("supplierManageCombobox", JsonUtil.toJson(comboSupplierManageVO.getOptions()));
		
		 //获取日期
  		DateFormatUtil df= DateFormatUtil.getInstance("yyyy");
        String dateStr = df.format(new Date());
        model.put("giveDate", dateStr);
        
        PurchaseTypeEntity typeEntity = purchaseTypeService.findById(Long.parseLong(type));
        model.put("typeEntity", typeEntity);
		
		return this.createModelAndView("managePlanContract/supplierManage/supplierManageAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}/{type}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id,@PathVariable String type){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		SupplierManageEntity supplierManageEntity = (SupplierManageEntity)supplierManageService.findById(id);
		model.put("entity", supplierManageEntity);
		model.put("entityJson", JsonUtil.toJson(supplierManageEntity));
		
	    PurchaseTypeEntity typeEntity = purchaseTypeService.findById(Long.parseLong(type));
        model.put("typeEntity", typeEntity);
		
		return this.createModelAndView("managePlanContract/supplierManage/supplierManageEdit", model);
	}
	
	/**
	 * 
	 * 改为启用状态
	 * 
	 * @param @param request
	 * @param @param id
	 * @param @param status
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @lastModified
	 */
	@RequestMapping("/resultConfirmStart/{id}/{status}")
	public @ResponseBody ResultObj resultConfirmStart(HttpServletRequest request, @PathVariable Long id, @PathVariable String status){
		ResultObj resultObj = new ResultObj();
		SupplierManageEntity entity = supplierManageService.findById(id);
			entity.setStatus("0");
			supplierManageService.updateEntity(entity);
			resultObj.setResult("success");
		return resultObj;
	}
	
	/**
	 * 
	 * 改为停用状态
	 * 
	 * @param @param request
	 * @param @param id
	 * @param @param status
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @lastModified
	 */
	@RequestMapping("/resultConfirmStop/{id}/{status}")
	public @ResponseBody ResultObj resultConfirmStop(HttpServletRequest request, @PathVariable Long id, @PathVariable String status){
		ResultObj resultObj = new ResultObj();
		SupplierManageEntity entity = supplierManageService.findById(id);
		entity.setStatus("1");
		supplierManageService.updateEntity(entity);
		return resultObj;
	}
	
	@RequestMapping("/bulkCopyAdd/{ids}")
	public @ResponseBody ResultObj bulkCopyAdd(HttpServletRequest request, @PathVariable String ids) throws ParseException{
		ResultObj resultObj = new ResultObj();
		supplierManageService.bulkCopyAdd(ids);
		return resultObj;
	}
	
}