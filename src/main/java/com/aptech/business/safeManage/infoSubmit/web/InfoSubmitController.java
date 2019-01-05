package com.aptech.business.safeManage.infoSubmit.web;

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

import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.safeManage.infoSubmit.domain.InfoSubmitEntity;
import com.aptech.business.safeManage.infoSubmit.service.InfoSubmitService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.DataStatusEnum;
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
 * 信息报送配置控制器
 *
 * @author 
 * @created 2018-03-28 18:05:15
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/infoSubmit")
public class InfoSubmitController extends BaseController<InfoSubmitEntity> {
	
	@Autowired
	private InfoSubmitService infoSubmitService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	/**
	 * 操作日志
	 */
	@Autowired
	private OperateLogService operateLogService;	
	@Override
	public IBaseEntityOperation<InfoSubmitEntity> getService() {
		return infoSubmitService;
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
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		//获取公司列表
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_LEVEL = 2 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7 OR C_ORGANIZATION = 13"));
//		companyConditions.add(new Condition("parentId",FieldTypeEnum.INT,MatchTypeEnum.EQ,0));
//		companyConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
	    model.put("unitCombobox", JsonUtil.toJson(companyVo.getOptions()));
	    List<InfoSubmitEntity> li=infoSubmitService.findAll();
	    
		return this.createModelAndView("safeManage/infoSubmit/infoSubmitList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		SysUserEntity userEntity= RequestContext.get().getUser();
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> userList = sysUserService.findByCondition(
				conditions, null);
		
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		model.put("userEntity", userEntity);
		//获取公司列表
		List<Condition> companyConditions = new ArrayList<Condition>();
//		companyConditions.add(new Condition("parentId",FieldTypeEnum.INT,MatchTypeEnum.EQ,0));
//		companyConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		companyConditions.add(new Condition("C_LEVEL = 2 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7 OR C_ORGANIZATION = 13"));
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
	    model.put("unitCombobox", JsonUtil.toJson(companyVo.getOptions()));

		return this.createModelAndView("safeManage/infoSubmit/infoSubmitAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		// 返回前台数据项
		InfoSubmitEntity infoSubmitEntity = (InfoSubmitEntity)infoSubmitService.findById(id);
		model.put("entity", infoSubmitEntity);
		model.put("entityJson", JsonUtil.toJson(infoSubmitEntity));
		
		//获取公司列表
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_LEVEL = 2 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7 OR C_ORGANIZATION = 13"));
//		companyConditions.add(new Condition("parentId",FieldTypeEnum.INT,MatchTypeEnum.EQ,0));
//		companyConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
	    model.put("unitCombobox", JsonUtil.toJson(companyVo.getOptions()));
	    
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> userList = sysUserService.findByCondition(
				conditions, null);
		
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		model.put("userEntity", userEntity);
		SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(infoSubmitEntity.getCreatorId()));
		model.put("sysUserEntity", sysUserEntity);
		return this.createModelAndView("safeManage/infoSubmit/infoSubmitEdit", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetailPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		InfoSubmitEntity infoSubmitEntity = (InfoSubmitEntity)infoSubmitService.findById(id);
		model.put("entity", infoSubmitEntity);
		model.put("entityJson", JsonUtil.toJson(infoSubmitEntity));
		
	    //获取登录人信息
		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(infoSubmitEntity.getCreatorId()));
		model.put("userName", userEntity.getName());	
		
		return this.createModelAndView("safeManage/infoSubmit/infoSubmitDetail", model);
	}
	
	/**
	 * 列表查询
	 */
	@RequestMapping(value = "/dataList")
	public @ResponseBody ResultListObj List(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		ResultListObj resultObj = new ResultListObj();
		Page<InfoSubmitEntity> page = PageUtil.getPage(params);
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("c_id"));
		List<InfoSubmitEntity> entities = infoSubmitService.findByCondition(conditions, page);
		resultObj.setDraw((Integer)params.get("draw"));
		if (entities != null) {
			resultObj.setData(entities);
			resultObj.setRecordsTotal(page.getTotal());
		}
		return resultObj;
	}
	
	/**
	* 报表导出
	* @return void
	*/
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		String conditionsChan=req.getParameter("conditions");
		params.put("conditions", JSONArray.fromObject(conditionsChan));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		Page<InfoSubmitEntity> page= new Page<InfoSubmitEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("c_id"));
 		List<InfoSubmitEntity> dataList=infoSubmitService.findByCondition(conditions, page);
 		int i= 1;
 		for(InfoSubmitEntity enity: dataList){
 			enity.setNumber(i++);
 		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		
		ExcelUtil.export(req, res, "信息报送模版.xlsx","信息报送.xlsx", resultMap);
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
		infoSubmitService.deleteEntity(id);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.INFORMATIONSUBMISSION.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
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
			InfoSubmitEntity entity = infoSubmitService.findById(longId);
			if (entity != null) {
				infoSubmitService.deleteEntity(longId);
				operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.INFORMATIONSUBMISSION.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
			}
		}
		return resultObj;
	}
}