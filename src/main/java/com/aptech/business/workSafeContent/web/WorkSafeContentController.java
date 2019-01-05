package com.aptech.business.workSafeContent.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.component.dictionary.ThreeBusCategoryTypeEnum;
import com.aptech.business.equip.equipTree.domain.EquipTreeEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.managePlanContract.goodsRelation.domain.GoodsRelationEntity;
import com.aptech.business.managePlanContract.yearPurchase.domain.YearPurchaseEntity;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.safeManage.infoSubmit.domain.InfoSubmitEntity;
import com.aptech.business.safeManage.threeBusiness.domain.ThreeBusinessEntity;
import com.aptech.business.safeManage.threeBusiness.service.ThreeBusinessService;
import com.aptech.business.ticketManage.workSafe.domain.WorkSafeEntity;
import com.aptech.business.ticketManage.workSafe.service.WorkSafeService;
import com.aptech.business.workSafeContent.domain.WorkSafeContentEntity;
import com.aptech.business.workSafeContent.service.WorkSafeContentService;
import com.aptech.common.system.dictionary.domain.SysDictionaryEntity;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
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
 * 安全措施信息配置控制器
 *
 * @author 
 * @created 2018-11-16 13:17:33
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/workSafeContent")
public class WorkSafeContentController extends BaseController<WorkSafeContentEntity> {
	
	@Autowired
	private WorkSafeContentService workSafeContentService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUnitService sysUnitService;
	
	@Autowired
	private WorkSafeService workSafeService;
	
	/**
	 * 操作日志
	 */
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<WorkSafeContentEntity> getService() {
		return workSafeContentService;
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
		
		//类别
		ComboboxVO typeEnumCombobox = new ComboboxVO();
		for(ThreeBusCategoryTypeEnum key : ThreeBusCategoryTypeEnum.values()){
			typeEnumCombobox.addOption(key.getCode(), key.getName());
		}
		model.put("categoryCombobox", JsonUtil.toJson(typeEnumCombobox.getOptions()));
       //获取登录人信息
		SysUserEntity userEntity= RequestContext.get().getUser();
  		model.put("sysUserId", JsonUtil.toJson(userEntity.getId()));
		model.put("loginName", JsonUtil.toJson(userEntity.getLoginName()));
		model.put("userName", userEntity.getName());	
		
		//单位下拉框
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_GENERATION_TYPE = 1 "));
		companyConditions.add(new Condition(" C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4"));
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
	    model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
	    
		return this.createModelAndView("workSafeContent/workSafeContentList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
       //获取登录人信息
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userName", userEntity.getName());	
		//单位下拉框
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_GENERATION_TYPE = 1 "));
		companyConditions.add(new Condition(" C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4"));
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
        }
	    model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
			    
		return this.createModelAndView("workSafeContent/workSafeContentAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		WorkSafeContentEntity safeContentEntity = (WorkSafeContentEntity)workSafeContentService.findById(id);
		model.put("entity", safeContentEntity);
		model.put("entityJson", JsonUtil.toJson(safeContentEntity));
		
       //获取登录人信息
		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(safeContentEntity.getCreateUserId()));
		model.put("userName", userEntity.getName());
		
		//单位下拉框
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_GENERATION_TYPE = 1 "));
		companyConditions.add(new Condition(" C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4"));
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
        }
	    model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
			    
		return this.createModelAndView("workSafeContent/workSafeContentEdit", model);
	}
	
	/**
	 * 跳转到查看页面
	 */
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetail(HttpServletRequest request,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		WorkSafeContentEntity targetEntity=workSafeContentService.findById(id);
		model.put("entity", targetEntity);
       //获取登录人信息
		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(targetEntity.getCreateUserId()));
		model.put("userName", userEntity.getName());
		
		SysUnitEntity unitEntity = sysUnitService.findById(Long.parseLong(targetEntity.getUnitId().toString()));
		model.put("unitName", unitEntity.getName());

		return this.createModelAndView("workSafeContent/workSafeContentDetail", model);
	}
	
	
	
	/**
	 * 修改保存
	 * @param t
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/savePage/{id}")
	public @ResponseBody ResultObj saveTrainPage(@RequestBody WorkSafeContentEntity entity, HttpServletRequest request) {
		ResultObj resultObj = new ResultObj();
		workSafeContentService.updateEntity(entity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.WORK_SAFE_CONTENT.getName(), OperateUserEnum.EDIT.getName(), "", OperateUserEnum.EDIT.getId());
		return resultObj;
	}
	/**
	 * @Description: 删除
	 * @param id 删除对象id
	 * @return
	 */
	@RequestMapping(value = "/deleteOne/{id}")
	public @ResponseBody
	ResultObj deleteOne(@PathVariable Long id) {
		
		ResultObj resultObj = new ResultObj();
		workSafeContentService.deleteEntity(id);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.WORK_SAFE_CONTENT.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
		return resultObj;
	}
	/**
	 * @Description: 批量删除
	 * @param ids 删除对象id
	 * @return
	 */
	@RequestMapping(value = "/allDelete")
	public @ResponseBody
	ResultObj allDelete(@RequestBody List<String> ids) {
		ResultObj resultObj = new ResultObj();
		SysUserEntity userEntity = RequestContext.get().getUser();
		for (String id : ids) {
			Long longId = new Long(id);
			WorkSafeContentEntity entity = workSafeContentService.findById(longId);
			
			if (entity != null) {
				workSafeContentService.deleteEntity(longId);
				operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.WORK_SAFE_CONTENT.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
			}
		}
		return resultObj;
	}
	
	/**
	 * @Description:   选择带回 
	 * @author         changl 
	 * @Date           2017年6月26日 下午2:30:45 
	 * @throws         Exception
	 */
	@RequestMapping("/selectSafeContent")
	public ModelAndView userSelectRevalBox(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//当前用户
		SysUserEntity userEntity= RequestContext.get().getUser();
		
		//单位下拉框
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_GENERATION_TYPE = 1 "));
		companyConditions.add(new Condition(" C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4"));
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
		resultMap.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
			    
		//列表中已存在的数据id
		String[] existDataIdArray = request.getParameterValues("dataIdArray");
		resultMap.put("existDataId", JsonUtil.toJson(existDataIdArray));
	
		return new ModelAndView("workSafeContent/selectSafeContent",resultMap);
	}
	

	/**
	 * 列表检索
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping("/searchList")
	public @ResponseBody ResultListObj getInstockMCList(HttpServletRequest request,@RequestBody Map<String, Object> params){
		ResultListObj resultListObj = new ResultListObj();
		//接收前台传送已有数据的物资id
		String existDataString = request.getParameter("existDataId");
		Page<WorkSafeContentEntity> pages = PageUtil.getPage(params);
		pages.addOrder(Sort.asc("id"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		//排除前台数组
		conditions.clear();
		String[] existDataIdArray = existDataString.split(",");
		if (existDataString != null && !"null".equals(existDataString)) {
			if(null != existDataIdArray && existDataIdArray.length!=0){
		     conditions.add(new Condition("C_ID",FieldTypeEnum.INT,MatchTypeEnum.IN,existDataIdArray));
			}
		}
		List<WorkSafeEntity> workSafeEntities = workSafeService.findByCondition(conditions, null);
		List<Long> goodIds=new ArrayList<Long>();
		for (WorkSafeEntity goEntity : workSafeEntities) {
			if (goEntity.getSafeId()!=null) {
				goodIds.add(goEntity.getSafeId());
				
			}else {
				goodIds.add(goEntity.getId());
			}
		}
		
    	conditions.clear();
    	List<Condition> condition =  (List<Condition>) params.get("conditions");
    	List<WorkSafeContentEntity> safeContentEntities = new ArrayList<WorkSafeContentEntity>();
    	if(condition.size()==0&&existDataIdArray==null){
        	conditions.add(new Condition("a.C_ID",FieldTypeEnum.INT,MatchTypeEnum.NOT_IN,existDataIdArray));
        	safeContentEntities = workSafeContentService.findByCondition(conditions, null);	
    	}else if(condition.size()==0&&existDataIdArray!=null){
        	conditions.add(new Condition("a.C_ID",FieldTypeEnum.INT,MatchTypeEnum.NOT_IN,existDataIdArray));
    		 List<WorkSafeContentEntity> workcContentEntities = workSafeContentService.findByCondition(conditions, null);	
    			List<Long> yearIds=new ArrayList<Long>();
    			for (WorkSafeContentEntity purchaseEntity : workcContentEntities) { 
    				yearIds.add(purchaseEntity.getId());
    			}

    			if (existDataString != null && !"null".equals(existDataString)) {
    				if(null != goodIds && goodIds.size()!=0){
    				conditions.add(new Condition("a.C_ID",FieldTypeEnum.LONG,MatchTypeEnum.NOT_IN,goodIds.toArray()));
    				conditions.add(new Condition("a.C_ID",FieldTypeEnum.LONG,MatchTypeEnum.IN,yearIds.toArray()));
    			}
    			}
    			//取所有物资返回
    			safeContentEntities = workSafeContentService.findByCondition(conditions, pages);
    	}
    	else{
    		List<Condition> conditions1 = OrmUtil.changeMapToCondition(params);
//    		conditions1.add(new Condition("a.C_ID",FieldTypeEnum.INT,MatchTypeEnum.NOT_IN,existDataIdArray));
    		conditions1.add(new Condition("a.C_ID",FieldTypeEnum.INT,MatchTypeEnum.NOT_IN,goodIds.toArray()));
    		safeContentEntities = workSafeContentService.findByCondition(conditions1, null);	
    	}

		//获得返回结果
		resultListObj.setDraw((Integer)params.get("draw"));
		if (safeContentEntities != null) {
			if (pages != null) {
				resultListObj.setData(safeContentEntities);
				resultListObj.setRecordsTotal(pages.getTotal());
			}
		}
		return resultListObj;
	}
}