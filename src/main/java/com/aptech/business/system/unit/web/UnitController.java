package com.aptech.business.system.unit.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.aptech.business.system.unit.domain.BusinessGenerationTypeEnum;
import com.aptech.business.system.unit.domain.BusinessOrganizationEnum;
import com.aptech.business.system.unit.domain.BusinessUnitLevelEnum;
import com.aptech.business.system.unit.domain.UnitEntity;
import com.aptech.business.system.unit.service.UnitService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;
 
@Controller
@RequestMapping("/unit") 
public class UnitController extends BaseController<UnitEntity> {
    @Autowired
	private UnitService unitService;

	@Override
	public IBaseEntityOperation<UnitEntity> getService() {
		return unitService;
	}
	
    @RequestMapping("/index")
    public String index() {
    	
        return "/system/unit/index";
    }
    @RequestMapping(value = "/getUnit")
	public  @ResponseBody List<UnitEntity> list(){
    	List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"1"));
		List<UnitEntity> SysUnitEntitites = unitService.findByCondition(conditions,null);
		return  SysUnitEntitites;
	}
    

    @RequestMapping("/add/{parentId}")
    public ModelAndView add(HttpServletRequest request, @PathVariable String parentId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("parentId", parentId);
        
      //组织机构层次
        ComboboxVO comboUnitLevelVO = new ComboboxVO();
        for(BusinessUnitLevelEnum unitLevelEnum : BusinessUnitLevelEnum.values()){
        	comboUnitLevelVO.addOption(unitLevelEnum.getCode(), unitLevelEnum.getName());
        }
        resultMap.put("unitLevel", JsonUtil.toJson(comboUnitLevelVO.getOptions()));
        ComboboxVO comboGenerationTypeVO = new ComboboxVO();
        for(BusinessGenerationTypeEnum generEnum : BusinessGenerationTypeEnum.values()){
        	comboGenerationTypeVO.addOption(generEnum.getCode(), generEnum.getName());
        }
        resultMap.put("generationTypes", JsonUtil.toJson(comboGenerationTypeVO.getOptions()));
        //组织机构类型
        ComboboxVO BusinessOrganizationcomboTypeVO = new ComboboxVO();
        for(BusinessOrganizationEnum generEnum : BusinessOrganizationEnum.values()){
        	BusinessOrganizationcomboTypeVO.addOption(generEnum.getCode(), generEnum.getName());
        }
        resultMap.put("organization", JsonUtil.toJson(BusinessOrganizationcomboTypeVO.getOptions()));
        return new ModelAndView("/system/unit/add", resultMap);
    }
    
    @RequestMapping("/edit/{id}")
    public ModelAndView edit(HttpServletRequest request, @PathVariable Long id) {
        UnitEntity sysUnitEntity = this.unitService.findById(id);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("unit", sysUnitEntity);
        //组织机构层次
        ComboboxVO comboUnitLevelVO = new ComboboxVO();
        for(BusinessUnitLevelEnum unitLevelEnum : BusinessUnitLevelEnum.values()){
//        	if(!unitLevelEnum.getCode().equals(unitLevelEnum.MASHINE.getCode())){//国华尚义故障诊断的组织机构不支持单机，其他项目打开
        		comboUnitLevelVO.addOption(unitLevelEnum.getCode(), unitLevelEnum.getName());
//        	}
        }
        resultMap.put("unitLevel", JsonUtil.toJson(comboUnitLevelVO.getOptions()));
        ComboboxVO comboGenerationTypeVO = new ComboboxVO();
        for(BusinessGenerationTypeEnum generEnum : BusinessGenerationTypeEnum.values()){
        	comboGenerationTypeVO.addOption(generEnum.getCode(), generEnum.getName());
        }
        resultMap.put("generationTypes", JsonUtil.toJson(comboGenerationTypeVO.getOptions()));
        //组织机构类型
        ComboboxVO BusinessOrganizationcomboTypeVO = new ComboboxVO();
        for(BusinessOrganizationEnum generEnum : BusinessOrganizationEnum.values()){
        	BusinessOrganizationcomboTypeVO.addOption(generEnum.getCode(), generEnum.getName());
        }
        resultMap.put("organization", JsonUtil.toJson(BusinessOrganizationcomboTypeVO.getOptions()));
        return new ModelAndView("/system/unit/edit", resultMap);
    }
    
    /**
     * 
     * 删除菜单
     * 
     * @param @return
     * @return ModelAndView
     * @throws 
     * @author liweiran
     * @created 2017年5月3日 下午5:54:34
     * @lastModified
     */
    @RequestMapping(value = "/del/{id}", method = RequestMethod.DELETE)
    public @ResponseBody ResultObj delete(HttpServletRequest request, @PathVariable Long id){
    	ResultObj resultObj = new ResultObj();
    	/*List<SysUnitEntity> sysMenuItemEntityList = sysUnitService.findAll();
    	List<SysUnitEntity> sysMenuItemList = sysUnitService.findSubUnitsFromList(sysMenuItemEntityList, id);
    	for(SysUnitEntity sysUnitEntity:sysMenuItemList){
    		sysUnitService.deleteEntity(sysUnitEntity.getId());
    	}*/
    	UnitEntity sysUnitEntity = unitService.findById(id);
    	//sysUnitEntity.setDeleteFlag("Y");
    	sysUnitEntity.setStatus("0");
    	unitService.updateEntity(sysUnitEntity);
    	
    	return resultObj;
    }
}