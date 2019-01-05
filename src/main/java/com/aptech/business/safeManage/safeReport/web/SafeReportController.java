package com.aptech.business.safeManage.safeReport.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

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
import com.aptech.business.safeManage.safeReport.domain.SafeReportEntity;
import com.aptech.business.safeManage.safeReport.service.SafeReportService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 安全月报配置控制器
 *
 * @author 
 * @created 2018-03-28 11:18:00
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/safeReport")
public class SafeReportController extends BaseController<SafeReportEntity> {
	
	@Autowired
	private SafeReportService safeReportService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Override
	public IBaseEntityOperation<SafeReportEntity> getService() {
		return safeReportService;
	}
	@Autowired
	private OperateLogService operateLogService;
	
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
		List<SafeReportEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("safeReportTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSafeReportVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("safeReportCombobox", JsonUtil.toJson(comboSafeReportVO.getOptions()));
		SysUserEntity sysUserEntity=RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		return this.createModelAndView("safeManage/safeReport/safeReportList", model);
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
		ComboboxVO comboSafeReportVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("safeReportCombobox", JsonUtil.toJson(comboSafeReportVO.getOptions()));
		SysUserEntity sysUserEntity=RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		return this.createModelAndView("safeManage/safeReport/safeReportAdd", model);
	}
	
	/**
	 *	新增
	 */
	@RequestMapping("/add")
	public @ResponseBody ResultObj  add(HttpServletRequest request,@RequestBody SafeReportEntity safeReportEntity){
		safeReportService.add(safeReportEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFEREPORT.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		ResultObj resultObj = new ResultObj();
		resultObj.setData(safeReportEntity);
		return resultObj;
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		SafeReportEntity safeReportEntity = (SafeReportEntity)safeReportService.findById(id);
		model.put("entity", safeReportEntity);
		model.put("entityJson", JsonUtil.toJson(safeReportEntity));
		SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(safeReportEntity.getUserId()));
		// 部门
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_LEVEL = 2 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 13 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7"));
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
        }
        model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
		ComboboxVO comboSafeReportVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("safeReportCombobox", JsonUtil.toJson(comboSafeReportVO.getOptions()));
		model.put("sysUserEntity", sysUserEntity);
		return this.createModelAndView("safeManage/safeReport/safeReportEdit", model);
	}
	
	/**
	 * 修改
	 * @param safeReportEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update")
	public @ResponseBody
	ResultObj update(@RequestBody SafeReportEntity safeReportEntity, HttpServletRequest request) {
		safeReportService.update(safeReportEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFEREPORT.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		ResultObj resultObj = new ResultObj();
		resultObj.setData(safeReportEntity);
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
		safeReportService.deleteEntity(id);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFEREPORT.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
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
			SafeReportEntity safeReportEntity = this.getService().findById(longId);
			if(safeReportEntity != null){
				this.getService().deleteEntity(longId);
			}
		}
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFEREPORT.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		ResultObj resultObj = new ResultObj();
		return resultObj;
	}
	
	/**
	 * @Description:   导出
	 * @author         changl 
	 * @Date           2017年6月28日 上午11:03:09 
	 * @throws         Exception
	 */
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		String conditions=req.getParameter("conditions");
		Page<SafeReportEntity> page = new Page<SafeReportEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("id"));
		params = (Map<String, Object>)JSONObject.fromObject(conditions);
		List<SafeReportEntity> dataList=safeReportService.findByCondition(params, null);
		for (int i = 0; i < dataList.size(); i++) {
			dataList.get(i).setNumber(i+1);
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "安全月报模板.xlsx","安全月报.xlsx", resultMap);
	}
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetail(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		SafeReportEntity safeReportEntity = (SafeReportEntity)safeReportService.findById(id);
		model.put("entity", safeReportEntity);
		model.put("entityJson", JsonUtil.toJson(safeReportEntity));
		
		List<SafeReportEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("safeReportTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSafeReportVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("safeReportCombobox", JsonUtil.toJson(comboSafeReportVO.getOptions()));
		SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(safeReportEntity.getUserId()));
		model.put("sysUserEntity", sysUserEntity);
		SysUnitEntity sysUnitEntity = sysUnitService.findById(Long.valueOf(safeReportEntity.getUnitId()));
		model.put("sysUnitEntity", sysUnitEntity);
		return this.createModelAndView("safeManage/safeReport/safeReportDetail", model);
	}
}