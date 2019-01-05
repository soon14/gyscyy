package com.aptech.business.equip.modelParameter.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.aptech.business.equip.equipModel.domain.EquipModelEntity;
import com.aptech.business.equip.modelParameter.domain.ModelParameterEntity;
import com.aptech.business.equip.modelParameter.domain.ModelParameterEnum;
import com.aptech.business.equip.modelParameter.service.ModelParameterService;
import com.aptech.common.system.dictionary.domain.SysDictionaryEntity;
import com.aptech.common.system.dictionary.service.SysDictionaryService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 模版参数配置控制器
 *
 * @author 
 * @created 2017-06-20 15:59:01
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/modelParameter")
public class ModelParameterController extends BaseController<ModelParameterEntity> {
	
	@Autowired
	private ModelParameterService modelParameterService;
	
	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	@Override
	public IBaseEntityOperation<ModelParameterEntity> getService() {
		return modelParameterService;
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index/{modelId}")
	public ModelAndView list(HttpServletRequest request, Map<String, Object> params, @PathVariable Long modelId) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<ModelParameterEntity> treeNodeList = null; 
		model.put("modelId", modelId);
		return this.createModelAndView("equip/modelParameter/modelParameterList", model);
	}
	
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd/{modelId}")
	public ModelAndView getAddPage(HttpServletRequest request,@PathVariable Long modelId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<Condition> conditions = new ArrayList<Condition>(); 
		conditions.add(new Condition("C_CATEGORY_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ModelParameterEnum.PARAMETER.getCode()));
		List<SysDictionaryEntity> sysDictionaryEntitieslist =  sysDictionaryService.findByCondition(conditions, null);
		ComboboxVO comboModelParameterVO = new ComboboxVO();
		for(SysDictionaryEntity sysDictionaryEntity : sysDictionaryEntitieslist){
			comboModelParameterVO.addOption(sysDictionaryEntity.getCode().toString(), sysDictionaryEntity.getName());
		}
		//参数类型
		model.put("parameterType", JsonUtil.toJson(comboModelParameterVO.getOptions()));
		model.put("modelId", modelId);
		return this.createModelAndView("equip/modelParameter/modelParameterAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		ModelParameterEntity modelParameterEntity = modelParameterService.findById(id);
		model.put("modelParameterEntity", modelParameterEntity);
		return this.createModelAndView("equip/modelParameter/modelParameterEdit", model);
	}
	
	/**
	 * @Description:   添加
	 * @author         wangcc 
	 * @Date           2017年7月12日 下午7:46:25 
	 * @throws         Exception
	 */
	@RequestMapping("/add")
	public @ResponseBody ResultObj Add(HttpServletRequest request,@RequestBody ModelParameterEntity modelParameterEntity){
		ResultObj resultObj = null;
		try {
			List<Condition> conditions = new ArrayList<Condition>();
			conditions.add(new Condition(" C_PARAMETER ", FieldTypeEnum.LONG, MatchTypeEnum.EQ,modelParameterEntity.getParameter()));
			conditions.add(new Condition(" C_DEFAULT_VALUE ", FieldTypeEnum.LONG, MatchTypeEnum.EQ,modelParameterEntity.getDefaultValue()));
			conditions.add(new Condition(" C_PARAMETER_TYPE ", FieldTypeEnum.LONG, MatchTypeEnum.EQ,modelParameterEntity.getParameterType()));
			conditions.add(new Condition(" C_MODEL_ID ", FieldTypeEnum.LONG, MatchTypeEnum.EQ,modelParameterEntity.getModelId()));
			List<EquipModelEntity> modelEntitieslist = modelParameterService.findByCondition(conditions, null);
			if(modelEntitieslist.isEmpty()){
				resultObj = this.add(modelParameterEntity, request);
			}else{
				resultObj = new ResultObj();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 * @Description:   
	 * @author         wangcc 
	 * @Date           2017年7月12日 上午9:21:34 
	 * @throws         Exception
	 */
	@RequestMapping("/edit/{id}")
	public @ResponseBody ResultObj edit(HttpServletRequest request,@RequestBody ModelParameterEntity modelParameterEntity,@PathVariable Long id){
		ResultObj resultObj = null;
		try {
			List<Condition> conditions = new ArrayList<Condition>();
			conditions.add(new Condition(" C_PARAMETER ", FieldTypeEnum.LONG, MatchTypeEnum.EQ,modelParameterEntity.getParameter()));
			conditions.add(new Condition(" C_DEFAULT_VALUE ", FieldTypeEnum.LONG, MatchTypeEnum.EQ,modelParameterEntity.getDefaultValue()));
			conditions.add(new Condition(" C_PARAMETER_TYPE ", FieldTypeEnum.LONG, MatchTypeEnum.EQ,modelParameterEntity.getParameterType()));
			conditions.add(new Condition(" C_MODEL_ID ", FieldTypeEnum.LONG, MatchTypeEnum.EQ,modelParameterEntity.getModelId()));
			conditions.add(new Condition(" C_ID ", FieldTypeEnum.LONG, MatchTypeEnum.NE,modelParameterEntity.getId()));
			List<EquipModelEntity> modelEntitieslist = modelParameterService.findByCondition(conditions, null);
			if(modelEntitieslist.isEmpty()){
				resultObj = this.edit(request, modelParameterEntity, id);
			}else{
				resultObj = new ResultObj();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
}