package com.aptech.business.cargo.securityApparatus.web;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.cargo.securityApparatus.domain.SecurityApparatusEntity;
import com.aptech.business.cargo.securityApparatus.service.SecurityApparatusService;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
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
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.util.StringUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 安全工器具管理配置控制器
 *
 * @author 
 * @created 2018-03-15 13:41:51
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/securityApparatus")
public class SecurityApparatusController extends BaseController<SecurityApparatusEntity> {
	
	@Autowired
	private SecurityApparatusService securityApparatusService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private SysUserService sysUserService;
	@Override
	public IBaseEntityOperation<SecurityApparatusEntity> getService() {
		return securityApparatusService;
	}
	DateFormatUtil sdf = DateFormatUtil.getInstance("yyyy-MM-dd");//日格式
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
		// 部门下拉树
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		for (int i = 0; i < treeNodeList.size(); i++) {
			treeNodeList.get(i).setOpen("true");
		}
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSecurityApparatusVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("securityApparatusCombobox", JsonUtil.toJson(comboSecurityApparatusVO.getOptions()));
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		return this.createModelAndView("cargo/securityApparatus/securityApparatusList", model);
	}
	
	@RequestMapping(value = "/data/list")
	public  @ResponseBody ResultListObj securityApparatusList(HttpServletRequest request,@RequestBody Map<String, Object> params){
		List<Condition> condition=OrmUtil.changeMapToCondition(params);
		Page<SecurityApparatusEntity> pages = PageUtil.getPage(params);
		//按照时间倒序排列
		/*pages.addOrder(Sort.desc("C_ID"));*/
		pages.setOrders(OrmUtil.changeMapToOrders(params));
		pages.addOrder(Sort.desc("C_ID"));
		System.out.println(pages.getOrders());
		SysUserEntity sysUserEntity =RequestContext.get().getUser();
		condition.add(new Condition("sa.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getUnitId()));
		List<SecurityApparatusEntity> entities = securityApparatusService.findByCondition(condition, pages);
		//获得返回结果
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (entities != null) {
			if (pages != null) {
				resultObj.setData(entities);
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
		Map<String, Object> model = new HashMap<String, Object>();
		// 部门下拉树
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		for (int i = 0; i < treeNodeList.size(); i++) {
			treeNodeList.get(i).setOpen("true");
		}
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSecurityApparatusVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("securityApparatusCombobox", JsonUtil.toJson(comboSecurityApparatusVO.getOptions()));
		
		return this.createModelAndView("cargo/securityApparatus/securityApparatusAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		SecurityApparatusEntity securityApparatusEntity = (SecurityApparatusEntity)securityApparatusService.findById(id);
		model.put("entity", securityApparatusEntity);
		model.put("entityJson", JsonUtil.toJson(securityApparatusEntity));
		
		// 部门下拉树
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		for (int i = 0; i < treeNodeList.size(); i++) {
			treeNodeList.get(i).setOpen("true");
		}
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSecurityApparatusVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("securityApparatusCombobox", JsonUtil.toJson(comboSecurityApparatusVO.getOptions()));
		
		return this.createModelAndView("cargo/securityApparatus/securityApparatusEdit", model);
	}
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/showPage/{id}")
	public ModelAndView getShowPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		SecurityApparatusEntity securityApparatusEntity = (SecurityApparatusEntity)securityApparatusService.findById(id);
		model.put("entity", securityApparatusEntity);
		model.put("entityJson", JsonUtil.toJson(securityApparatusEntity));
		
		// 部门下拉树
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		for (int i = 0; i < treeNodeList.size(); i++) {
			treeNodeList.get(i).setOpen("true");
		}
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSecurityApparatusVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("securityApparatusCombobox", JsonUtil.toJson(comboSecurityApparatusVO.getOptions()));
		
		return this.createModelAndView("cargo/securityApparatus/securityApparatusShow", model);
	}
	/**
	 * 
	 * 添加页面保存
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @return
	 * @param @throws UnsupportedEncodingException
	 * @param @throws ParseException
	 * @return ResultObj
	 * @throws 
	 * @author zhangxb
	 * 
	 * @created 2018年3月15日 上午10:22:19
	 * @lastModified
	 */
	@RequestMapping("/add")
	public @ResponseBody ResultObj saveAddPage(@RequestBody Map<String, Object> entityMap) throws ParseException{
		//添加数据到入库表
		SecurityApparatusEntity securityApparatusEntity = new SecurityApparatusEntity();
		securityApparatusEntity.setName((entityMap.get("name").toString()));
		
		Date endDate = sdf.parse(entityMap.get("endDate").toString());
		Date checkDate = sdf.parse(entityMap.get("checkDate").toString());
		//用户信息
		SysUserEntity userEntity = RequestContext.get().getUser();
		securityApparatusEntity.setCreateDate(new Date());
		securityApparatusEntity.setCreateUserId(userEntity.getId().toString());
		securityApparatusEntity.setCheckDate(checkDate);
		securityApparatusEntity.setEndDate(endDate);
		//通过用户id查找用户单位
		//SysUserEntity applicantUser = sysUserService.findById(Long.valueOf(entityMap.get("applicant").toString()));
		//securityApparatusEntity.setCreateUserId(applicantUser.getId().toString());
		//入库单位
		securityApparatusEntity.setUnitId(entityMap.get("unitId").toString());
		
		if(StringUtil.isNotEmpty((String)entityMap.get("attachment"))){
			securityApparatusEntity.setAttachment(entityMap.get("attachment").toString());
		}
		if(StringUtil.isNotEmpty((String)entityMap.get("remark"))){
			securityApparatusEntity.setRemark(entityMap.get("remark").toString());
		}
		securityApparatusService.addEntity(securityApparatusEntity);
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SECURITYAPPARATUS.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		ResultObj resultObj = new ResultObj();
		return resultObj;
	}
	/**
	 * 
	 * 修改页面保存功能
	 * 
	 * @param @param request
	 * @param @param id
	 * @param @return
	 * @return ResultObj
	 * @throws ParseException 
	 * @throws 
	 * @author liweiran
	 * @created 2017年7月26日 上午9:59:16
	 * @lastModified
	 */
	@RequestMapping("/saveEditPage")
	public @ResponseBody ResultObj saveEditPage(@RequestBody Map<String, Object> entityMap) throws ParseException{
		Long securityApparatusId = Long.valueOf(entityMap.get("id").toString());
		SecurityApparatusEntity securityApparatusEntity = securityApparatusService.findById(securityApparatusId);
		securityApparatusEntity.setName((entityMap.get("name").toString()));
		Date endDate = sdf.parse(entityMap.get("endDate").toString());
		Date checkDate = sdf.parse(entityMap.get("checkDate").toString());
		securityApparatusEntity.setCheckDate(checkDate);
		securityApparatusEntity.setEndDate(endDate);
		//用户信息
		SysUserEntity userEntity = RequestContext.get().getUser();
		securityApparatusEntity.setUpdateDate(new Date());
		securityApparatusEntity.setUpdateUserId(userEntity.getId().toString());
		//通过用户id查找用户单位
		//SysUserEntity applicantUser = sysUserService.findById(Long.valueOf(entityMap.get("applicant").toString()));
		//securityApparatusEntity.setCreateUserId(applicantUser.getId().toString());
		//入库单位
		securityApparatusEntity.setUnitId(entityMap.get("unitId").toString());
		
		if(StringUtil.isNotEmpty((String)entityMap.get("attachment"))){
			securityApparatusEntity.setAttachment(entityMap.get("attachment").toString());
		}
		if(StringUtil.isNotEmpty((String)entityMap.get("remark"))){
			securityApparatusEntity.setRemark(entityMap.get("remark").toString());
		}
		securityApparatusService.updateEntity(securityApparatusEntity);	
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SECURITYAPPARATUS.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		ResultObj resultObj = new ResultObj();
		return resultObj;
	}
	/**
	 * 
	 * 安全工器具管理导出功能
	 * 
	 * @param @param req
	 * @param @param res
	 * @param @throws UnsupportedEncodingException
	 * @return void
	 * @throws 
	 * @author zhangxb
	 * @created 2017年7月25日 下午4:16:46
	 * @lastModified
	 */
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res) throws UnsupportedEncodingException{
		String name = req.getParameter("name");
		String startTime = req.getParameter("startTime");
		String endTime = req.getParameter("endTime");
		String searchCheckStartTime = req.getParameter("searchCheckStartTime");
		String searchCheckEndTime = req.getParameter("searchCheckEndTime");
		String unitId = req.getParameter("unitId");
		
		Page<SecurityApparatusEntity> pages = new Page<SecurityApparatusEntity>();
		pages.addOrder(Sort.desc("id"));
		pages.setPageSize(Integer.MAX_VALUE);
		List<Condition> conditions=new ArrayList<Condition>();
		if(StringUtil.isNotEmpty(name)&&!name.equals("undefined")){
			conditions.add(new Condition("s.C_NAME", FieldTypeEnum.STRING,MatchTypeEnum.LIKE,name));
		}
		if(StringUtil.isNotEmpty(startTime)){
			conditions.add(new Condition("s.C_END_DATE", FieldTypeEnum.DATE,MatchTypeEnum.GE,startTime));
		}
		if(StringUtil.isNotEmpty(endTime)){
			conditions.add(new Condition("s.C_END_DATE", FieldTypeEnum.DATE,MatchTypeEnum.LE,endTime));
		}
		if(StringUtil.isNotEmpty(startTime)){
			conditions.add(new Condition("s.C_CHECK_DATE", FieldTypeEnum.DATE,MatchTypeEnum.GE,searchCheckStartTime));
		}
		if(StringUtil.isNotEmpty(endTime)){
			conditions.add(new Condition("s.C_CHECK_DATE", FieldTypeEnum.DATE,MatchTypeEnum.LE,searchCheckEndTime));
		}
		if(StringUtil.isNotEmpty(unitId)&&!unitId.equals("undefined")){
			conditions.add(new Condition("s.C_UNIT_ID",FieldTypeEnum.STRING,MatchTypeEnum.EQ, unitId));
		}
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		conditions.add(new Condition("sa.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getUnitId()));
		List<SecurityApparatusEntity> dataList=securityApparatusService.findByCondition(conditions, pages);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "安全工器具管理报表模板.xlsx","安全工器具管理.xlsx", resultMap);
	}
}