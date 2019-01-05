package com.aptech.business.safeManage.safeSending.web;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.safeManage.safeSending.domain.SafeSendingEntity;
import com.aptech.business.safeManage.safeSending.service.SafeSendingService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 安全发文配置控制器
 *
 * @author 
 * @created 2018-04-02 09:52:16
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/safeSending")
public class SafeSendingController extends BaseController<SafeSendingEntity> {
	
	@Autowired
	private SafeSendingService safeSendingService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<SafeSendingEntity> getService() {
		return safeSendingService;
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
		List<SafeSendingEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("safeSendingTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSafeSendingVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("safeSendingCombobox", JsonUtil.toJson(comboSafeSendingVO.getOptions()));
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		return this.createModelAndView("/safeManage/safeSending/safeSendingList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		// 部门
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_LEVEL = 2 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 13 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7"));
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
        }
        model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
		ComboboxVO comboSafeSendingVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("safeSendingCombobox", JsonUtil.toJson(comboSafeSendingVO.getOptions()));
		
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		return this.createModelAndView("/safeManage/safeSending/safeSendingAdd", model);
	}
	
	/**
	 *	新增
	 */
	@RequestMapping("/add")
	public @ResponseBody ResultObj  add(HttpServletRequest request,@RequestBody SafeSendingEntity safeSendingEntity){
		safeSendingService.add(safeSendingEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFESENDING.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		ResultObj resultObj = new ResultObj();
		resultObj.setData(safeSendingEntity);
		return resultObj;
	}
	

	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		SafeSendingEntity safeSendingEntity = (SafeSendingEntity)safeSendingService.findById(id);
		model.put("entity", safeSendingEntity);
		model.put("entityJson", JsonUtil.toJson(safeSendingEntity));
		// 部门
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_LEVEL = 2 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 13 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7"));
//		conditions.add(new Condition("C_GENERATION_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, 1));
//		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.STRING, MatchTypeEnum.EQ, 13));
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
        }
        model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
		ComboboxVO comboSafeSendingVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("safeSendingCombobox", JsonUtil.toJson(comboSafeSendingVO.getOptions()));
		SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(safeSendingEntity.getUserId()));
		model.put("sysUserEntity", sysUserEntity);
		return this.createModelAndView("/safeManage/safeSending/safeSendingEdit", model);
	}
	
	/**
	 * 修改
	 * @param safeSendingEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update")
	public @ResponseBody
	ResultObj update(@RequestBody SafeSendingEntity safeSendingEntity, HttpServletRequest request) {
		safeSendingService.update(safeSendingEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFESENDING.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		ResultObj resultObj = new ResultObj();
		resultObj.setData(safeSendingEntity);
		return resultObj;
	}
	
	/**
	 * 删除
	 * @Title: delete
	 * @Description:
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResultObj delete(@PathVariable Long id) {
		safeSendingService.deleteEntity(id);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFESENDING.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		ResultObj resultObj = new ResultObj();
		return resultObj;
	}
	
	/**
	 * 批量删除
	 * 
	 * @Title: delete
	 * @Description:
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/bulkDelete", method = RequestMethod.DELETE)
	public @ResponseBody ResultObj bulkDelete(@RequestBody List<Integer> ids) {
		SysUserEntity userEntity = RequestContext.get().getUser();
		for (Integer id : ids) {
			long longId = (long)id;
			SafeSendingEntity safeSendingEntity = safeSendingService.findById(longId);
			if(safeSendingEntity != null){
				this.getService().deleteEntity(longId);
			}
		}
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFESENDING.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		ResultObj resultObj = new ResultObj();
		return resultObj;
	}
	
	/**
	 *	跳转到查看页面
	 */
	@RequestMapping("/getshowPage/{id}")
	public ModelAndView getShowPage(HttpServletRequest request,@PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		SafeSendingEntity safeSendingEntity = (SafeSendingEntity)safeSendingService.findById(id);
		model.put("entity", safeSendingEntity);
		model.put("entityJson", JsonUtil.toJson(safeSendingEntity));
		SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(safeSendingEntity.getUserId()));
		model.put("sysUserEntity", sysUserEntity);
		SysUnitEntity sysUnitEntity = sysUnitService.findById(Long.valueOf(safeSendingEntity.getUnitId()));
		model.put("sysUnitEntity", sysUnitEntity);
		return this.createModelAndView("safeManage/safeSending/safeSendingDetail", model);
	}
	/**
	* 修改保存
	* @author ly
	* @date 2018年3月20日 上午9:32:04 
	* @param trainPlanEntity
	* @return
	* @return ResultObj
	*/
	@RequestMapping("/saveEditPage")
	public @ResponseBody ResultObj saveEditPage(@RequestBody SafeSendingEntity safeSendingEntity){
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFESENDING.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		ResultObj resultObj = new ResultObj();
		safeSendingService.updateEntity(safeSendingEntity);	
		return resultObj;
	}
	
	/**
	* 导出功能
	* @author ly
	* @date 2018年3月19日 下午5:57:30 
	* @param req
	* @param res
	* @param params
	* @throws UnsupportedEncodingException
	* @return void
	*/
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		
		String conditions=req.getParameter("conditions");
		params.put("conditions", JSONArray.fromObject(conditions));
 		List<SafeSendingEntity> dataList=safeSendingService.findByCondition(params, null);
 		int i= 1;
 		for(SafeSendingEntity safeSendingEntity: dataList){
 			safeSendingEntity.setNumber(i++);
 		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);

		ExcelUtil.export(req, res, "安全发文报表模板.xlsx","安全发文报表.xlsx", resultMap);

	}
}