package com.aptech.business.safeManage.safeStandard.web;

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
import com.aptech.business.safeManage.safeStandard.domain.SafeStandardEntity;
import com.aptech.business.safeManage.safeStandard.service.SafeStandardService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 安全标准化建设配置控制器
 *
 * @author 
 * @created 2018-04-02 13:58:10
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/safeStandard")
public class SafeStandardController extends BaseController<SafeStandardEntity> {
	
	@Autowired
	private SafeStandardService safeStandardService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUnitService sysUnitService;
	
	/**
	 * 操作日志
	 */
	@Autowired
	private OperateLogService operateLogService;
	
	@Override
	public IBaseEntityOperation<SafeStandardEntity> getService() {
		return safeStandardService;
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
		//标准化类型
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("SAFESTANDARD_TYPE");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("safeStanardTypeCombobox", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
       //获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		return this.createModelAndView("safeManage/safeStandard/safeStandardList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//标准化类型
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("SAFESTANDARD_TYPE");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("safeStanardTypeCombobox", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));

// 		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
// 		// TODO下拉树具体内容根据具体业务定制
// 		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList)); 
		//单位下拉框
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition(" C_LEVEL = 2 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7 "));
		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
	    model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
     // 人员
 		ComboboxVO searchuser = new ComboboxVO();
 		List<Condition> conditions = new ArrayList<Condition>();
 		SysUserEntity userEntity= RequestContext.get().getUser();
 		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
 		List<SysUserEntity> userList = sysUserService.findByCondition(
 				conditions, null);
 		
 		for (SysUserEntity user : userList) {
 			searchuser.addOption(user.getId().toString(), user.getName());
 		}
 		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
 		model.put("userEntity", userEntity);
		//获取登录人
//		SysUserEntity sysUserEntity = RequestContext.get().getUser();
//		model.put("sysUserEntity", sysUserEntity);
		
		return this.createModelAndView("safeManage/safeStandard/safeStandardAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		SafeStandardEntity safeStandardEntity = (SafeStandardEntity)safeStandardService.findById(id);
		model.put("entity", safeStandardEntity);
		model.put("entityJson", JsonUtil.toJson(safeStandardEntity));
		
		//标准化类型
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("SAFESTANDARD_TYPE");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("safeStanardTypeCombobox", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
      //单位下拉框
  		List<Condition> companyConditions = new ArrayList<Condition>();
  		companyConditions.add(new Condition(" C_LEVEL = 2 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7 "));
  		
  		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
  		ComboboxVO companyVo = new ComboboxVO();
  		
  		for(SysUnitEntity sysUnitEntity : unitList){
  			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
         }
  	    model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
         // 人员
		ComboboxVO searchuser = new ComboboxVO();
		List<Condition> conditions = new ArrayList<Condition>();
		SysUserEntity userEntity= RequestContext.get().getUser();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> userList = sysUserService.findByCondition(
				conditions, null);
		
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		model.put("userEntity", userEntity);
		SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(safeStandardEntity.getCreatePeopleId()));
		model.put("sysUserEntity", sysUserEntity);
		return this.createModelAndView("safeManage/safeStandard/safeStandardEdit", model);
	}
	/**
	 *	跳转到查看页面
	 */
	@RequestMapping("/getshowPage/{id}")
	public ModelAndView getShowPage(HttpServletRequest request,@PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		SafeStandardEntity safeStandardEntity = (SafeStandardEntity)safeStandardService.findById(id);
		model.put("entity", safeStandardEntity);
		model.put("entityJson", JsonUtil.toJson(safeStandardEntity));
		SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(safeStandardEntity.getCreatePeopleId()));
		model.put("userName", sysUserEntity.getName());
		SysUnitEntity sysUnitEntity=sysUnitService.findById(Long.valueOf(safeStandardEntity.getUnitId()));
		model.put("unitName", sysUnitEntity.getName());
		return this.createModelAndView("safeManage/safeStandard/safeStandardDetail", model);
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
	public @ResponseBody ResultObj saveEditPage(@RequestBody SafeStandardEntity safeStandardEntity){
		ResultObj resultObj = new ResultObj();
		safeStandardService.updateEntity(safeStandardEntity);	
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
 		List<SafeStandardEntity> dataList=safeStandardService.findByCondition(params, null);
 		int i= 1;
 		for(SafeStandardEntity safeStandardEntity: dataList){
 			safeStandardEntity.setNumber(i++);
 		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);

		ExcelUtil.export(req, res, "安全生产标准化报表模板.xlsx","安全生产标准化建设报表.xlsx", resultMap);

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
		safeStandardService.deleteEntity(id);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.SAFESPRODUCTIONTANDARDCONSTRUCTION.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
		return resultObj;
	}
	/**
	 * @Description: 批量删除
	 * @param ids 删除对象id
	 * @return
	 */
	@RequestMapping(value = "/allDelete")
	public @ResponseBody ResultObj allDelete(@RequestBody List<String> ids) {
		ResultObj resultObj = new ResultObj();
		SysUserEntity userEntity = RequestContext.get().getUser();
		for (String id : ids) {
			Long longId = new Long(id);
			SafeStandardEntity entity = safeStandardService.findById(longId);
			if (entity != null) {
				safeStandardService.deleteEntity(longId);
				operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.SAFESPRODUCTIONTANDARDCONSTRUCTION.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
			}
		}
		return resultObj;
	}
}