package com.aptech.business.safeManage.safeMeetings.web;

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
import com.aptech.business.safeManage.safeMeetings.domain.SafeMeetingsEntity;
import com.aptech.business.safeManage.safeMeetings.service.SafeMeetingsService;
import com.aptech.business.safeManage.safeSending.domain.SafeSendingEntity;
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
 * 安全会议配置控制器
 *
 * @author 
 * @created 2018-03-29 10:03:15
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/safeMeetings")
public class SafeMeetingsController extends BaseController<SafeMeetingsEntity> {
	
	@Autowired
	private SafeMeetingsService safeMeetingsService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<SafeMeetingsEntity> getService() {
		return safeMeetingsService;
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
		List<SafeMeetingsEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("safeMeetingsTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSafeMeetingVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("safeMeetingsCombobox", JsonUtil.toJson(comboSafeMeetingVO.getOptions()));
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		return this.createModelAndView("/safeManage/safeMeetings/safeMeetingsList", model);
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
		ComboboxVO comboSafeMeetingVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("safeMeetingCombobox", JsonUtil.toJson(comboSafeMeetingVO.getOptions()));
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		
		return this.createModelAndView("/safeManage/safeMeetings/safeMeetingsAdd", model);
	}
	
	/**
	 *	新增
	 */
	@RequestMapping("/add")
	public @ResponseBody ResultObj  add(HttpServletRequest request,@RequestBody SafeMeetingsEntity safeMeetingsEntity){
		safeMeetingsService.add(safeMeetingsEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFESAFEMEETINGS.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		ResultObj resultObj = new ResultObj();
		resultObj.setData(safeMeetingsEntity);
		return resultObj;
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		SafeMeetingsEntity safeMeetingsEntity = (SafeMeetingsEntity)safeMeetingsService.findById(id);
		model.put("entity", safeMeetingsEntity);
		model.put("entityJson", JsonUtil.toJson(safeMeetingsEntity));
		// 部门
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_LEVEL = 2 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 13 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7"));
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
        }
        model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
		SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(safeMeetingsEntity.getUserId()));
		model.put("sysUserEntity", sysUserEntity); 
		return this.createModelAndView("/safeManage/safeMeetings/safeMeetingsEdit", model);
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
		safeMeetingsService.deleteEntity(id);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFESAFEMEETINGS.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
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
			SafeMeetingsEntity safeMeetingsEntity = safeMeetingsService.findById(longId);
			if(safeMeetingsEntity != null){
				this.getService().deleteEntity(longId);
			}
		}
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFESAFEMEETINGS.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
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
		SafeMeetingsEntity safeMeetingsEntity = (SafeMeetingsEntity)safeMeetingsService.findById(id);
		model.put("entity", safeMeetingsEntity);
		model.put("entityJson", JsonUtil.toJson(safeMeetingsEntity));
		SysUnitEntity sysUnitEntity = sysUnitService.findById(Long.valueOf(safeMeetingsEntity.getUnitId()));
		model.put("sysUnitEntity", sysUnitEntity);
		SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(safeMeetingsEntity.getUserId()));
		model.put("sysUserEntity", sysUserEntity); 
		return this.createModelAndView("safeManage/safeMeetings/safeMeetingsDetail", model);
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
	public @ResponseBody ResultObj saveEditPage(@RequestBody SafeMeetingsEntity safeMeetingsEntity){
		ResultObj resultObj = new ResultObj();
		safeMeetingsService.updateEntity(safeMeetingsEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFESAFEMEETINGS.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
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
 		List<SafeMeetingsEntity> dataList=safeMeetingsService.findByCondition(params, null);
 		int i= 1;
 		for(SafeMeetingsEntity safeMeetingsEntity: dataList){
 			safeMeetingsEntity.setNumber(i++);
 		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);

		ExcelUtil.export(req, res, "安全会议报表模板.xlsx","安全会议报表.xlsx", resultMap);

	}
}