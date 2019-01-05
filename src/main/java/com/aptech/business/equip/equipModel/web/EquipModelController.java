package com.aptech.business.equip.equipModel.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.equip.equipLedger.domain.EquipLedgerEnum;
import com.aptech.business.equip.equipModel.domain.EquipModelEntity;
import com.aptech.business.equip.equipModel.domain.EquipModelEnum;
import com.aptech.business.equip.equipModel.exception.EquipModelException;
import com.aptech.business.equip.equipModel.exception.EquipModelExceptionType;
import com.aptech.business.equip.equipModel.service.EquipModelService;
import com.aptech.business.equip.equipTree.domain.EquipTreeEnum;
import com.aptech.business.equip.modelParameter.domain.ModelParameterEntity;
import com.aptech.business.equip.modelParameter.domain.ModelParameterEnum;
import com.aptech.business.equip.modelParameter.service.ModelParameterService;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.common.fourcode.service.FourCodeService;
import com.aptech.common.system.dictionary.domain.SysDictionaryEntity;
import com.aptech.common.system.dictionary.service.SysDictionaryService;
import com.aptech.common.system.user.domain.SysUserEntity;
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
import com.aptech.framework.util.PropertyUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 *  设备模版配置控制器
 *
 * @author 
 * @created 2017-06-12 14:04:41
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/equipModel")
public class EquipModelController extends BaseController<EquipModelEntity> {
	
	@Autowired
	private EquipModelService equipModelService;
	
	@Autowired
	private ModelParameterService modelParameterService;
	
	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	@Autowired
	private FourCodeService fourCodeService;
	@Autowired
	private OperateLogService operateLogService;
	
	@Override
	public IBaseEntityOperation<EquipModelEntity> getService() {
		return equipModelService;
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index/{appId}")
	public ModelAndView list(HttpServletRequest request, Map<String, Object> params, @PathVariable Long appId) {
		Map<String, Object> model = new HashMap<String, Object>();
		//设备类型
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_CATEGORY_CODE",FieldTypeEnum.STRING, MatchTypeEnum.EQ,EquipTreeEnum.EQUIPTYPE.getCode()));
		List<SysDictionaryEntity> sysDictionaryEntitiesList  = sysDictionaryService.findByCondition(conditions, null);
        ComboboxVO comboequipType = new ComboboxVO();
        for(SysDictionaryEntity sysdictionaryentity : sysDictionaryEntitiesList){
        	comboequipType.addOption(sysdictionaryentity.getCode().toString(), sysdictionaryentity.getName());
        }
	    model.put("equipType", JsonUtil.toJson(comboequipType.getOptions()));
		return this.createModelAndView("equip/equipModel/equipModelList", model);
	}
	/**
	 *	list模版选择带回
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/selectModelList/{equipType}")
	public ModelAndView selectModelList(HttpServletRequest request, Map<String, Object> params, @PathVariable Long equipType) {
		Map<String, Object> model = new HashMap<String, Object>();
	    model.put("equipType", equipType);
		return this.createModelAndView("equip/equipModel/equipSelectModelList", model);
	}
	
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		
		Map<String, Object> model = new HashMap<String, Object>();
		//设备类型
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_CATEGORY_CODE",FieldTypeEnum.STRING, MatchTypeEnum.EQ,EquipTreeEnum.EQUIPTYPE.getCode()));
		List<SysDictionaryEntity> sysDictionaryEntitiesList  = sysDictionaryService.findByCondition(conditions, null);
        ComboboxVO comboequipType = new ComboboxVO();
        for(SysDictionaryEntity sysdictionaryentity : sysDictionaryEntitiesList){
        	comboequipType.addOption(sysdictionaryentity.getCode().toString(), sysdictionaryentity.getName());
        }
	    model.put("equipType", JsonUtil.toJson(comboequipType.getOptions()));
	    //模版编码
	    Map<String, Object> params=new HashMap<String, Object> ();
		params.put("date", new Date());
		String modelCode=fourCodeService.getBusinessCode(EquipModelEnum.EQUIPMODLCODE.getName(), params);
        model.put("modelCode", modelCode);
		return this.createModelAndView("equip/equipModel/equipModelAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		//设备类型
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_CATEGORY_CODE",FieldTypeEnum.STRING, MatchTypeEnum.EQ,EquipTreeEnum.EQUIPTYPE.getCode()));
		List<SysDictionaryEntity> sysDictionaryEntitiesList  = sysDictionaryService.findByCondition(conditions, null);
        ComboboxVO comboequipType = new ComboboxVO();
        for(SysDictionaryEntity sysdictionaryentity : sysDictionaryEntitiesList){
        	comboequipType.addOption(sysdictionaryentity.getCode().toString(), sysdictionaryentity.getName());
        }
        model.put("equipType", JsonUtil.toJson(comboequipType.getOptions()));
    	//返回前台数据项
		EquipModelEntity equipModelEntity = (EquipModelEntity)equipModelService.findById(id);
		model.put("equipModelEntity", equipModelEntity);
		return this.createModelAndView("equip/equipModel/equipModelEdit", model);
	}
	
	/**
	 * @Description:   模版新增
	 * @author         wangcc 
	 * @Date           2017年6月15日 上午9:21:34 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody ResultObj Add(@RequestBody Map<String, Object> params,HttpServletRequest request){
		ResultObj resultObj = new ResultObj();
		//设备信息
		EquipModelEntity equipModelEntity = PropertyUtil.mapToBean((Map<String, Object>)params.get("equipModelInfo"), EquipModelEntity.class);
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition(" C_EQUIP_NAME ", FieldTypeEnum.LONG, MatchTypeEnum.EQ,equipModelEntity.getEquipName()));
		conditions.add(new Condition(" C_EQUIP_TYPE ", FieldTypeEnum.LONG, MatchTypeEnum.EQ,equipModelEntity.getEquipType()));
		conditions.add(new Condition(" C_MANU_FACTURER ", FieldTypeEnum.LONG, MatchTypeEnum.EQ,equipModelEntity.getManuFacturer()));
		conditions.add(new Condition(" C_SPECIFICATION_MODEL ", FieldTypeEnum.LONG, MatchTypeEnum.EQ,equipModelEntity.getSpecificationModel()));
		List<EquipModelEntity> equipModelEntities = equipModelService.findByCondition(conditions, null);
		List<Map<String, Object>> baseParameters = (List<Map<String, Object>>)params.get("baseParameter");
		List<Map<String, Object>> technologyParameter = (List<Map<String, Object>>)params.get("technologyParameter");
		boolean result  = volidationParameter(baseParameters,baseParameters,true);
		if(baseParameters!=null){
			result  = volidationParameter(baseParameters,baseParameters,true);
			if(result){
				throw new EquipModelException(EquipModelExceptionType.BASE_PARAMETER_REPEAT);
			}
		}
		if(technologyParameter!=null){
		result  = volidationParameter(technologyParameter,technologyParameter,true);
			if(result){
				throw new EquipModelException(EquipModelExceptionType.THCHNOLOGY_PARAMETER_REPEAT);
			}
		}
		if(baseParameters!=null && technologyParameter!=null){
			result  = volidationParameter(baseParameters,technologyParameter,false);
			if(result){
				throw new EquipModelException(EquipModelExceptionType.BASE_THCHNOLOGY_PARAMETER_REPEAT);
			}
		}
		if(equipModelEntities.isEmpty()){
			//新增模版
			resultObj = this.add(equipModelEntity, request);
			//基础参数
			for(Map<String, Object> map:baseParameters){
				Object o = map.get("defaultValue");
				if(o!=null){
					ModelParameterEntity modelParameterEntity = new ModelParameterEntity();
					modelParameterEntity.setDefaultValue(String.valueOf(map.get("defaultValue")));
					modelParameterEntity.setModelId(equipModelEntity.getId());
					modelParameterEntity.setParameter(String.valueOf(map.get("parameter")));
					modelParameterEntity.setParameterType(EquipLedgerEnum.BASEPARAMETERTYPE.getId());
					modelParameterEntity.setStatus(ModelParameterEnum.NORMAL.getId());
					modelParameterService.addEntity(modelParameterEntity);
				}
			}
			//基础参数
			for(Map<String, Object> map:technologyParameter){
				Object o = map.get("defaultValue");
				if(o!=null){
					ModelParameterEntity modelParameterEntity = new ModelParameterEntity();
					modelParameterEntity.setDefaultValue(String.valueOf(map.get("defaultValue")));
					modelParameterEntity.setModelId(equipModelEntity.getId());
					modelParameterEntity.setParameter(String.valueOf(map.get("parameter")));
					modelParameterEntity.setParameterType(EquipLedgerEnum.TECHNOLOGYPARAMETERSTYPE.getId());
					modelParameterEntity.setStatus(ModelParameterEnum.NORMAL.getId());
					modelParameterService.addEntity(modelParameterEntity);
				}
			}
		}else{
			throw new EquipModelException(EquipModelExceptionType.BASE_THCHNOLOGY_PARAMETER_REPEAT);
		}
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPMODEL.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		return resultObj;
	}
	/**
	 * @Description:   模版修改
	 * @author         wangcc 
	 * @Date           2017年7月12日 上午9:21:34 
	 * @throws         Exception
	 */
	@RequestMapping("/edit/{id}")
	public @ResponseBody ResultObj edit(HttpServletRequest request,@RequestBody Map<String, Object> params){
		ResultObj resultObj = new ResultObj();
		//设备信息
		EquipModelEntity equipModelEntity = PropertyUtil.mapToBean((Map<String, Object>)params.get("equipModelInfo"), EquipModelEntity.class);
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition(" C_EQUIP_NAME ", FieldTypeEnum.LONG, MatchTypeEnum.EQ,equipModelEntity.getEquipName()));
		conditions.add(new Condition(" C_EQUIP_TYPE ", FieldTypeEnum.LONG, MatchTypeEnum.EQ,equipModelEntity.getEquipType()));
		conditions.add(new Condition(" C_MANU_FACTURER ", FieldTypeEnum.LONG, MatchTypeEnum.EQ,equipModelEntity.getManuFacturer()));
		conditions.add(new Condition(" C_SPECIFICATION_MODEL ", FieldTypeEnum.LONG, MatchTypeEnum.EQ,equipModelEntity.getSpecificationModel()));
		conditions.add(new Condition(" C_ID ", FieldTypeEnum.LONG, MatchTypeEnum.NE,equipModelEntity.getId()));
		List<EquipModelEntity> equipModelEntities = equipModelService.findByCondition(conditions, null);
		List<Map<String, Object>> baseParameters = (List<Map<String, Object>>)params.get("baseParameter");
		List<Map<String, Object>> technologyParameter = (List<Map<String, Object>>)params.get("technologyParameter");
		boolean result = false;
		if(baseParameters!=null){
			result  = volidationParameter(baseParameters,baseParameters,true);
			if(result){
				throw new EquipModelException(EquipModelExceptionType.BASE_PARAMETER_REPEAT);
			}
		}
		if(technologyParameter!=null){
			result  = volidationParameter(technologyParameter,technologyParameter,true);
			if(result){
				throw new EquipModelException(EquipModelExceptionType.THCHNOLOGY_PARAMETER_REPEAT);
			}
		}
		if(baseParameters!=null && technologyParameter!=null){
			result  = volidationParameter(baseParameters,technologyParameter,false);
			if(result){
				throw new EquipModelException(EquipModelExceptionType.BASE_THCHNOLOGY_PARAMETER_REPEAT);
			}
		}
		if(equipModelEntities.isEmpty()){
			equipModelEntity.setStatus(EquipModelEnum.NORMAL.getId());
			//修改模版
			equipModelService.updateEntity(equipModelEntity);
			//删除设备参数
			modelParameterService.deleteByCondition("delByModelId", equipModelEntity.getId());
			//基础参数
			for(Map<String, Object> map:baseParameters){
				Object o = map.get("defaultValue");
				if(o!=null){
					ModelParameterEntity modelParameterEntity = new ModelParameterEntity();
					modelParameterEntity.setDefaultValue(String.valueOf(map.get("defaultValue")));
					modelParameterEntity.setModelId(equipModelEntity.getId());
					modelParameterEntity.setParameter(String.valueOf(map.get("parameter")));
					modelParameterEntity.setParameterType(EquipLedgerEnum.BASEPARAMETERTYPE.getId());
					modelParameterEntity.setStatus(ModelParameterEnum.NORMAL.getId());
					modelParameterService.addEntity(modelParameterEntity);
				}
			}
			//基础参数
			for(Map<String, Object> map:technologyParameter){
				Object o = map.get("defaultValue");
				if(o!=null){
					ModelParameterEntity modelParameterEntity = new ModelParameterEntity();
					modelParameterEntity.setDefaultValue(String.valueOf(map.get("defaultValue")));
					modelParameterEntity.setModelId(equipModelEntity.getId());
					modelParameterEntity.setParameter(String.valueOf(map.get("parameter")));
					modelParameterEntity.setParameterType(EquipLedgerEnum.TECHNOLOGYPARAMETERSTYPE.getId());
					modelParameterEntity.setStatus(ModelParameterEnum.NORMAL.getId());
					modelParameterService.addEntity(modelParameterEntity);
				}
			}
			resultObj = new ResultObj();
			resultObj.setData(equipModelEntity);
		}else{
			resultObj = new ResultObj();
		}
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPMODEL.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		return resultObj;
	}
	
	/**
	 * @Description:   模版修改
	 * @author         wangcc 
	 * @Date           2017年8月14日 上午9:21:34 
	 * @throws         Exception
	 */
	@RequestMapping("/detail/{id}")
	public ModelAndView detail(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		//设备类型
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_CATEGORY_CODE",FieldTypeEnum.STRING, MatchTypeEnum.EQ,EquipTreeEnum.EQUIPTYPE.getCode()));
		List<SysDictionaryEntity> sysDictionaryEntitiesList  = sysDictionaryService.findByCondition(conditions, null);
        ComboboxVO comboequipType = new ComboboxVO();
        for(SysDictionaryEntity sysdictionaryentity : sysDictionaryEntitiesList){
        	comboequipType.addOption(sysdictionaryentity.getCode().toString(), sysdictionaryentity.getName());
        }
        model.put("equipType", JsonUtil.toJson(comboequipType.getOptions()));
    	//返回前台数据项
		EquipModelEntity equipModelEntity = (EquipModelEntity)equipModelService.findById(id);
		model.put("equipModelEntity", equipModelEntity);
		return this.createModelAndView("equip/equipModel/equipModelDetail", model);
	}
	
	/**
	 * @Description:   模版删除
	 * @author         wangcc 
	 * @Date           2017年8月14日 上午9:21:34 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResultObj delete(@PathVariable Long id) {
		ResultObj resultObj = new ResultObj();
		EquipModelEntity equipModelEntity = equipModelService.findById(id);
		//更新模版状态
		equipModelEntity.setStatus(EquipModelEnum.DELETE.getId());
		equipModelService.updateEntity(equipModelEntity);
		//设备类型
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_MODEL_ID",FieldTypeEnum.INT, MatchTypeEnum.EQ,equipModelEntity.getId()));
		List<ModelParameterEntity> modelParameterEntities =  modelParameterService.findByCondition(conditions, null);
		for(ModelParameterEntity modelParameterEntity:modelParameterEntities){
			modelParameterEntity.setStatus(ModelParameterEnum.DELETE.getId());
			modelParameterService.updateEntity(modelParameterEntity);
		}
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPMODEL.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		return resultObj;
	}
	
	/**
	 * @Description:   模版批量删除
	 * @author         wangcc 
	 * @Date           2017年8月14日 下午15:21:34 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/bulkDelete/{ids}", method = RequestMethod.DELETE)
	public @ResponseBody ResultObj bulkDelete(@PathVariable List<Integer> ids) {
		ResultObj resultObj = new ResultObj();
		for (Integer id : ids) {
			long longId = (long)id;
			EquipModelEntity equipModelEntity = equipModelService.findById(longId);
			//更新模版状态
			equipModelEntity.setStatus(EquipModelEnum.DELETE.getId());
			equipModelService.updateEntity(equipModelEntity);
			//设备类型
			List<Condition> conditions = new ArrayList<Condition>();
			conditions.add(new Condition("C_MODEL_ID",FieldTypeEnum.INT, MatchTypeEnum.EQ,equipModelEntity.getId()));
			List<ModelParameterEntity> modelParameterEntities =  modelParameterService.findByCondition(conditions, null);
			for(ModelParameterEntity modelParameterEntity:modelParameterEntities){
				modelParameterEntity.setStatus(ModelParameterEnum.DELETE.getId());
				modelParameterService.updateEntity(modelParameterEntity);
			}
		}
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPMODEL.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		return resultObj;
	}
	
	/**
	 * 列表查询
	 */
	@RequestMapping(value = "/dataList")
	public @ResponseBody ResultListObj List(HttpServletRequest request, @RequestBody Map<String, Object> params) {
			List<Condition> conditions = OrmUtil.changeMapToCondition(params);
			conditions.add(new Condition("C_STATUS", FieldTypeEnum.INT, MatchTypeEnum.EQ, EquipModelEnum.NORMAL.getId()));
			ResultListObj resultObj = new ResultListObj();
			Page<EquipModelEntity> page = PageUtil.getPage(params);
			page.setOrders(OrmUtil.changeMapToOrders(params));
			page.addOrder(Sort.desc("id"));
			List<EquipModelEntity> equipModelEntities = equipModelService.findByCondition(conditions, page);
			resultObj.setDraw((Integer)params.get("draw"));
			if (equipModelEntities != null) {
				resultObj.setData(equipModelEntities);
				if (page != null) {
					resultObj.setRecordsTotal(page.getTotal());
				}else{
					resultObj.setRecordsTotal((long) equipModelEntities.size());
				}
			}
			return resultObj;
	}
	/**
	 * @Description:   校验参数是否重复
	 * @author         wangcc 
	 * @Date           2017年9月1日 下午1:34:41 
	 * @throws         Exception
	 */
	public boolean volidationParameter(List<Map<String, Object>> lp1,List<Map<String, Object>> lp2,boolean tag){
		String key1="",key2="";
		boolean result = false;
		int flag = 0;
		for(int i = 0,length1 = lp1.size();i<length1;i++){
			Object o = ((Map)lp1.get(i)).get("defaultValue");
			if(o!=null){
				key1 = ((Map)lp1.get(i)).get(ModelParameterEnum.PARAMETER.getCode().toLowerCase()).toString();
				for(int j = 0,length2 = lp2.size();j<length2;j++){
					System.out.println(" i   ==  "+i);
					System.out.println(" j   ==  "+j);
					Map map = ((Map)lp2.get(j));
					key2 = map.get(ModelParameterEnum.PARAMETER.getCode().toLowerCase()).toString();
//					key2= ((Map)lp2.get(j)).get(ModelParameterEnum.PARAMETER.getCode().toLowerCase()).toString();
					if(StringUtils.equals(key1,key2)){
						//如果是相同的参数对比
						if(tag){
							flag++;
							if(flag > 1){
								result = true;
								break;
							}
						}else{
							result = true;
							break;
						}
					}
				}
				flag = 0;
			}
		}
		return result;
	}
	
	
}