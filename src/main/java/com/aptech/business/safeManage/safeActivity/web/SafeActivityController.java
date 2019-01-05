package com.aptech.business.safeManage.safeActivity.web;

import java.io.UnsupportedEncodingException;
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

import com.aptech.business.component.dictionary.SafeActivityTypeEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.safeManage.safeActivity.domain.SafeActivityEntity;
import com.aptech.business.safeManage.safeActivity.service.SafeActivityService;
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
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 安全活动配置控制器
 *
 * @author 
 * @created 2018-03-28 16:39:53
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/safeActivity")
public class SafeActivityController extends BaseController<SafeActivityEntity> {
	
	@Autowired
	private SafeActivityService safeActivityService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<SafeActivityEntity> getService() {
		return safeActivityService;
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
		//获取类型下拉列表
		ComboboxVO comboSafeCultureTypeVO = new ComboboxVO();
		SafeActivityTypeEnum[] typeArr=SafeActivityTypeEnum.values();
		for (SafeActivityTypeEnum enu : typeArr) {
			comboSafeCultureTypeVO.addOption(enu.getCode(), enu.getName());
		}
		model.put("safeActivityComboboxType", JsonUtil.toJson(comboSafeCultureTypeVO.getOptions()));
		return this.createModelAndView("safeManage/safeCultureActivity/safeActivity/safeActivityList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		ComboboxVO comboSafeActivityVO = new ComboboxVO();
		SafeActivityTypeEnum[] typeArr=SafeActivityTypeEnum.values();
		for (SafeActivityTypeEnum enu : typeArr) {
			comboSafeActivityVO.addOption(enu.getCode(), enu.getName());
		}
		model.put("safeActivityComboboxType", JsonUtil.toJson(comboSafeActivityVO.getOptions()));
		
		return this.createModelAndView("safeManage/safeCultureActivity/safeActivity/safeActivityAdd", model);
	}
	
   /**
	 *	新增
	 */
	@RequestMapping("/add")
	public @ResponseBody ResultObj  add(HttpServletRequest request,@RequestBody SafeActivityEntity safeActivityEntity){
		safeActivityService.addEntity(safeActivityEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFEACTIVITY.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		ResultObj resultObj = new ResultObj();
		resultObj.setData(safeActivityEntity);
		return resultObj;
	}
	
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		
		// 返回前台数据项
		SafeActivityEntity safeActivityEntity = (SafeActivityEntity)safeActivityService.findById(id);
		model.put("entity", safeActivityEntity);
		model.put("entityJson", JsonUtil.toJson(safeActivityEntity));
		SysUserEntity userEntity= sysUserService.findById(Long.valueOf(safeActivityEntity.getCreatorId()));
		model.put("userEntity", userEntity);
		//获取类别列表
		ComboboxVO comboSafeActivityVO = new ComboboxVO();
		SafeActivityTypeEnum[] typeArr=SafeActivityTypeEnum.values();
		for (SafeActivityTypeEnum enu : typeArr) {
			comboSafeActivityVO.addOption(enu.getCode(), enu.getName());
		}
		model.put("safeActivityComboboxType", JsonUtil.toJson(comboSafeActivityVO.getOptions()));
		
		return this.createModelAndView("safeManage/safeCultureActivity/safeActivity/safeActivityEdit", model);
	}
	
	/**
	 * 修改
	 * @param safeActivityEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update/{id}")
	public @ResponseBody
	ResultObj update(@RequestBody SafeActivityEntity safeActivityEntity, HttpServletRequest request,@PathVariable Long id) {
		safeActivityService.updateEntity(safeActivityEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFEACTIVITY.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		ResultObj resultObj = new ResultObj();
		resultObj.setData(safeActivityEntity);
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
		safeActivityService.deleteEntity(id);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFEACTIVITY.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		ResultObj resultObj = new ResultObj();
		return resultObj;
	}
	
	 /**
		 * 批量删除
		 * 
		 * @Title: bulkDelete
		 * @Description:
		 * @param id
		 * @return
		 */
		@RequestMapping(value = "/bulkDelete", method = RequestMethod.DELETE)
		public @ResponseBody ResultObj bulkDelete(@RequestBody List<Integer> ids) {
			SysUserEntity userEntity = RequestContext.get().getUser();
			for (Integer id : ids) {
				long longId = (long)id;
				SafeActivityEntity safeActivityEntity = this.getService().findById(longId);
				if(safeActivityEntity != null){
					this.getService().deleteEntity(longId);
				}
			}
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFEACTIVITY.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
			ResultObj resultObj = new ResultObj();
			return resultObj;
		}
	
	
	/**
	 *	跳转到详情页面
	 */
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetailPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		SafeActivityEntity safeActivityEntity = (SafeActivityEntity)safeActivityService.findById(id);
		model.put("entity", safeActivityEntity);
		SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(safeActivityEntity.getCreatorId()));
		model.put("userEntity", sysUserEntity);
		return this.createModelAndView("safeManage/safeCultureActivity/safeActivity/safeActivityDetail", model);
	}
	
	/**
	 * 列表查询
	 */
	@RequestMapping(value = "/dataList")
	public @ResponseBody ResultListObj List(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		ResultListObj resultObj = new ResultListObj();
		Page<SafeActivityEntity> page = PageUtil.getPage(params);
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("c_id"));
		List<SafeActivityEntity> entities = safeActivityService.findByCondition(conditions, page);
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
	public void expData(HttpServletRequest req,HttpServletResponse res, Map<String, Object> params) throws UnsupportedEncodingException{
 		String conditionsChan=req.getParameter("conditions");
		params.put("conditions", JSONArray.fromObject(conditionsChan));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		Page<SafeActivityEntity> page = new Page<SafeActivityEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("c_id"));
 		List<SafeActivityEntity> dataList=safeActivityService.findByCondition(conditions, page);
 		int i= 1;
 		for(SafeActivityEntity enity: dataList){
 			enity.setNumber(i++);
 		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		
		ExcelUtil.export(req, res, "安全活动模版.xlsx","安全活动.xlsx", resultMap);
	}
}