package com.aptech.business.technical.technicalWork.web;

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

import com.aptech.business.technical.technicalWork.domain.TechnicalWorkEntity;
import com.aptech.business.technical.technicalWork.service.TechnicalWorkService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
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
 * 技术监督工作表配置控制器
 *
 * @author 
 * @created 2017-11-13 16:16:04
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/technicalWork")
public class TechnicalWorkController extends BaseController<TechnicalWorkEntity> {
	
	@Autowired
	private TechnicalWorkService technicalWorkService;
	@Autowired
	private SysUserService sysUserService;
	@Override
	public IBaseEntityOperation<TechnicalWorkEntity> getService() {
		return technicalWorkService;
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
		List<TechnicalWorkEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("technicalWorkTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboTechnicalWorkVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("technicalWorkCombobox", JsonUtil.toJson(comboTechnicalWorkVO.getOptions()));
		return this.createModelAndView("technical/technicalWork/technicalWorkList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String flag=request.getParameter("flag");
		String uuid=request.getParameter("uuid");
		String total=request.getParameter("total");
		String technicalId=request.getParameter("technicalId");
		 //工作负责人下拉列表
  	    ComboboxVO requestUserVO = new ComboboxVO();
        //TODO下拉框具体内容根据具体业务定制
  	    List<Condition> conditions=new ArrayList<Condition>();
	    conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
	    SysUserEntity userEntity= RequestContext.get().getUser();
	    conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));//选择人员时只列出当前场站的人员
	    List<SysUserEntity> allUsers = sysUserService.findByCondition(conditions, null);
        for(SysUserEntity sysUserEntity : allUsers){
      	 requestUserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("requestUsers", JsonUtil.toJson(requestUserVO.getOptions()));
        model.put("flag", flag);
        model.put("uuid", uuid); 
        model.put("total", total); 
        model.put("technicalId", technicalId); 
      //监督专业
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("TECHNICAL_TYPE");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("statusTypes", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
		return this.createModelAndView("technical/technicalWork/technicalWorkAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}/{flag}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id, @PathVariable String flag){
		Map<String, Object> model = new HashMap<String, Object>();
		TechnicalWorkEntity technicalWorkEntity=technicalWorkService.findById(id);
		 //工作负责人下拉列表
  	    ComboboxVO requestUserVO = new ComboboxVO();
        //TODO下拉框具体内容根据具体业务定制
  	    List<Condition> conditions=new ArrayList<Condition>();
	    conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
	    SysUserEntity userEntity= RequestContext.get().getUser();
	    conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));//选择人员时只列出当前场站的人员
	    List<SysUserEntity> allUsers = sysUserService.findByCondition(conditions, null);
        for(SysUserEntity sysUserEntity : allUsers){
      	 requestUserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("requestUsers", JsonUtil.toJson(requestUserVO.getOptions()));
        model.put("technicalWorkEntity", technicalWorkEntity);
        model.put("flag", flag);
      //监督专业
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("TECHNICAL_TYPE");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("statusTypes", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
		return this.createModelAndView("technical/technicalWork/technicalWorkEdit", model);
	}
	/**
	 * @Description:   自己写的修改方法
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/update/{id}")
	public @ResponseBody ResultObj update(@RequestBody TechnicalWorkEntity t, HttpServletRequest request ,@PathVariable Long id) {
		return technicalWorkService.update(t,id);
	}
}