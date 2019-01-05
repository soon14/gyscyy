package com.aptech.business.safeManage.accident.web;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
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

import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.safeManage.accident.domain.AccidentEntity;
import com.aptech.business.safeManage.accident.service.AccidentService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.common.workflow.definition.domain.DefinitionEntity;
import com.aptech.common.workflow.definition.service.DefinitionService;
import com.aptech.common.workflow.modelEditor.service.NodeConfigService;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.DataStatusEnum;
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
 * 技术监督总结配置控制器
 *
 * @author 
 * @created 2018-04-02 09:02:22
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/accident")
public class AccidentController extends BaseController<AccidentEntity> {
	
	@Autowired
	private AccidentService accidentService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private DefinitionService definitionService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Override
	public IBaseEntityOperation<AccidentEntity> getService() {
		return accidentService;
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
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//单位下拉框
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition(" C_LEVEL = 2 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7 OR C_ORGANIZATION = 13"));
		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
		resultMap.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("SGGL_TYPE");
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        resultMap.put("sgglType", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
        
        SysUserEntity userEntity= RequestContext.get().getUser();
		resultMap.put("userEntity", userEntity);
		
		return this.createModelAndView("safeManage/accident/accidentList", resultMap);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//单位下拉框
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition(" C_LEVEL = 2 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7 OR C_ORGANIZATION = 13"));
		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
		resultMap.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
		
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("SGGL_TYPE");
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        resultMap.put("sgglType", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
		
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
		resultMap.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		resultMap.put("userEntity", userEntity);
		return this.createModelAndView("safeManage/accident/accidentAdd", resultMap);
	}
	
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//单位下拉框
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition(" C_LEVEL = 2 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7 OR C_ORGANIZATION = 13"));
		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
		resultMap.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
		
		ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("SGGL_TYPE");
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        resultMap.put("sgglType", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));        
	
		AccidentEntity accidentEntity=accidentService.findById(id);
		resultMap.put("accidentEntity", accidentEntity);
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
		resultMap.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		resultMap.put("userEntity", userEntity);
		//获取登录人信息
 		SysUserEntity sysUserEntity= sysUserService.findById(Long.parseLong(accidentEntity.getCreateUserId()));
 		resultMap.put("sysUserEntity", sysUserEntity);
		return this.createModelAndView("safeManage/accident/accidentEdit", resultMap);
	}
	
	/**
	 *	跳转到详细页面
	 */
	@RequestMapping("/detail/{id}")
	public ModelAndView detail(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SysUserEntity userEntity= RequestContext.get().getUser();
		resultMap.put("userEntity", userEntity);
		AccidentEntity accidentEntity = (AccidentEntity)accidentService.findById(id);
		if(accidentEntity!=null){
			SysUnitEntity sysUnitEntity=sysUnitService.findById(Long.valueOf(accidentEntity.getUnitId()));
			accidentEntity.setUnitName(sysUnitEntity.getName());
			SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			accidentEntity.setTimeString(dateFm.format(accidentEntity.getTime()));
		}
        resultMap.put("accidentEntity", accidentEntity);
		SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(accidentEntity.getCreateUserId()));
		resultMap.put("userName", sysUserEntity.getName());
        return this.createModelAndView("safeManage/accident/accidentDetail", resultMap);
	}
	
	/**
	 * @Description:   提交确认  弹出框      这里要调用第一个流程接口，得到下一步的那些人的id集合
	 * @author         zhangzq 
	 * @Date           2017年6月13日 
	 * @throws         Exception
	 */
	@RequestMapping("/sureSubmit")
	public ModelAndView sureSubmit(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.TECHNICAL_SUMMARY.getName()));
		List<DefinitionEntity> defList=definitionService.findByCondition(conditions, null);
		String modelId="";
		if(!defList.isEmpty()){
			modelId=defList.get(0).getModelId();
		}
		SysUserEntity starter= null;
		if(!RequestContext.get().isDeveloperMode()){
			starter = RequestContext.get().getUser();
		}
		List<SysUserEntity> userList= nodeConfigService.getFirstNodeTransactor(modelId,"",starter);
		resultMap.put("userList", userList);
		return new ModelAndView("/technical/accident/sureSubmitPerson",resultMap);
	}
	
	/**
	 * @Description:   修改时候的验证
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/updateValidate/{id}")
	public @ResponseBody ResultObj updateValidate(HttpServletRequest request ,@PathVariable Long id) {
		return accidentService.updateValidate(id);
	}
	
	/**
	 * @Description:   自己写的修改方法
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/update/{id}")
	public @ResponseBody ResultObj update(@RequestBody AccidentEntity t, HttpServletRequest request ,@PathVariable Long id) {
		return accidentService.update(t,id);
	}
	/** 批量删除
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/bulkDelete/{ids}", method = RequestMethod.DELETE)
	public @ResponseBody ResultObj bulkDelete(@RequestBody List<Integer> ids) {
		ResultObj resultObj = new ResultObj();
		accidentService.deleteBulk(ids);
		return resultObj;
	}
	
	/**
	 * @Description:   单个删除时候的验证
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/deleteValidate/{id}")
	public @ResponseBody ResultObj deleteValidate(HttpServletRequest request ,@PathVariable Long id) {
		return accidentService.deleteValidate(id);
	}
	
	/**
	 * @Description:   提交时候的验证
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/tijiaoValidate/{id}")
	public @ResponseBody ResultObj tijiaoValidate(HttpServletRequest request ,@PathVariable Long id) {
		return accidentService.tijiaoValidate(id);
	}
	
	/**
	 * 列表查询
	 */
	@RequestMapping(value = "/dataList")
	public @ResponseBody ResultListObj List(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		ResultListObj resultObj = new ResultListObj();
		Page<AccidentEntity> page = PageUtil.getPage(params);
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("c_id"));
		List<AccidentEntity> entities = accidentService.findByCondition(conditions, page);
		for (AccidentEntity accidentEntity : entities) {
			String unitId=accidentEntity.getUnitId();
			SysUnitEntity sysUnitEntity=sysUnitService.findById(Long.valueOf(unitId));
			accidentEntity.setUnitName(sysUnitEntity.getName());
		}
		resultObj.setDraw((Integer)params.get("draw"));
		if (entities != null) {
			resultObj.setData(entities);
			resultObj.setRecordsTotal(page.getTotal());
		}
		return resultObj;
	}
	
	
	/**
	 * @Description:   
	 * @author         zhangzq 
	 * @Date           20180320
	 * @throws         Exception
	 */
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		String conditionsChan=req.getParameter("conditions");
		params.put("conditions", JSONArray.fromObject(conditionsChan));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		Page<AccidentEntity> page=new Page<AccidentEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("c_id"));
		List<AccidentEntity> dataList=accidentService.findByCondition(conditions, page);
		DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss");
		int i= 1;
		for (AccidentEntity accidentEntity : dataList) {
			if(accidentEntity.getTime()!=null){
				accidentEntity.setTimeString(df.format(accidentEntity.getTime()));
			}
			String unitId=accidentEntity.getUnitId();
			SysUnitEntity sysUnitEntity=sysUnitService.findById(Long.valueOf(unitId));
			accidentEntity.setUnitName(sysUnitEntity.getName());
			accidentEntity.setNumber(i++);
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "事故管理模板.xlsx","事故管理.xlsx", resultMap);
	}
}