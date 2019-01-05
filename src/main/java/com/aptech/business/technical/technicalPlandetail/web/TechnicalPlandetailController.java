package com.aptech.business.technical.technicalPlandetail.web;

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

import com.aptech.business.technical.technicalPlandetail.domain.TechnicalPlandetailEntity;
import com.aptech.business.technical.technicalPlandetail.service.TechnicalPlandetailService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 技术监督详细计划配置控制器
 *
 * @author 
 * @created 2017-11-13 16:16:12
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/technicalPlandetail")
public class TechnicalPlandetailController extends BaseController<TechnicalPlandetailEntity> {
	
	@Autowired
	private TechnicalPlandetailService technicalPlandetailService;
	
	@Override
	public IBaseEntityOperation<TechnicalPlandetailEntity> getService() {
		return technicalPlandetailService;
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
		String id=request.getParameter("id");
		String technical=request.getParameter("technical");
		String taskId=request.getParameter("taskId");//审批页面传过来的 为了第二层的返回按钮加的方法
		String procInstId=request.getParameter("procInstId");
		String procDefId=request.getParameter("procDefId");
		String orderSqe=request.getParameter("orderSqe");
		model.put("technicalWorkId", id);
		model.put("technicalId", technical);
		model.put("taskId", taskId);
		model.put("procInstId", procInstId);
		model.put("procDefId", procDefId);
		model.put("orderSqe", orderSqe);
		return this.createModelAndView("technical/technicalPlanDetail/technicalPlandetailList", model);
	}
	/**
	 *	list页面跳转 审批那些不能新增详细计划的人跳转的页面
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/indexSpnotAdd")
	public ModelAndView indexSpnotAdd(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		String id=request.getParameter("id");
		String technical=request.getParameter("technical");
		String taskId=request.getParameter("taskId");//审批页面传过来的 为了第二层的返回按钮加的方法
		String procInstId=request.getParameter("procInstId");
		String procDefId=request.getParameter("procDefId");
		String orderSqe=request.getParameter("orderSqe");
		model.put("technicalWorkId", id);
		model.put("technicalId", technical);
		model.put("taskId", taskId);
		model.put("procInstId", procInstId);
		model.put("procDefId", procDefId);
		model.put("orderSqe", orderSqe);
		return this.createModelAndView("technical/technicalPlanDetail/technicalPlandetailNoAddList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String uuid=request.getParameter("uuid");
		String technicalId=request.getParameter("technicalId");
		String technicalWorkId=request.getParameter("technicalWorkId");
		String orderSqe=request.getParameter("orderSqe");
		String detailoneTotal=request.getParameter("detailoneTotal");
		 //完成状态
        ComboboxVO codeDateTypesCombobox1 = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap1  =  DictionaryUtil.getDictionaries("TECHNICAL_WCZT");
        for(String key :  codeDateTypeMap1.keySet()){
        	SysDictionaryVO sysDictionaryVO1 = codeDateTypeMap1.get(key);
        	codeDateTypesCombobox1.addOption(sysDictionaryVO1.getCode(), sysDictionaryVO1.getName());
        }
        model.put("wcStatus", JsonUtil.toJson(codeDateTypesCombobox1.getOptions()));
        
      //单年多年
        ComboboxVO codeDateTypesCombobox2 = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap2  =  DictionaryUtil.getDictionaries("PLAN_CYCLE");
        for(String key :  codeDateTypeMap2.keySet()){
        	SysDictionaryVO sysDictionaryVO2 = codeDateTypeMap2.get(key);
        	codeDateTypesCombobox2.addOption(sysDictionaryVO2.getCode(), sysDictionaryVO2.getName());
        }
        model.put("djzqStatus", JsonUtil.toJson(codeDateTypesCombobox2.getOptions()));
        model.put("uuid", uuid);
        model.put("technicalId", technicalId);
        model.put("technicalWorkId", technicalWorkId);
        model.put("orderSqe", orderSqe);
        model.put("detailoneTotal", detailoneTotal);
		return this.createModelAndView("technical/technicalPlanDetail/technicalPlandetailAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		TechnicalPlandetailEntity technicalPlandetailEntity=technicalPlandetailService.findById(id);
		 //完成状态
        ComboboxVO codeDateTypesCombobox1 = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap1  =  DictionaryUtil.getDictionaries("TECHNICAL_WCZT");
        for(String key :  codeDateTypeMap1.keySet()){
        	SysDictionaryVO sysDictionaryVO1 = codeDateTypeMap1.get(key);
        	codeDateTypesCombobox1.addOption(sysDictionaryVO1.getCode(), sysDictionaryVO1.getName());
        }
        model.put("wcStatus", JsonUtil.toJson(codeDateTypesCombobox1.getOptions()));
        
      //单年多年
        ComboboxVO codeDateTypesCombobox2 = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap2  =  DictionaryUtil.getDictionaries("PLAN_CYCLE");
        for(String key :  codeDateTypeMap2.keySet()){
        	SysDictionaryVO sysDictionaryVO2 = codeDateTypeMap2.get(key);
        	codeDateTypesCombobox2.addOption(sysDictionaryVO2.getCode(), sysDictionaryVO2.getName());
        }
        model.put("djzqStatus", JsonUtil.toJson(codeDateTypesCombobox2.getOptions()));
        model.put("technicalPlandetailEntity", technicalPlandetailEntity);
		return this.createModelAndView("technical/technicalPlanDetail/technicalPlandetailEdit", model);
	}
	/**
	 * @Description:   自己写的修改方法
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/update/{id}")
	public @ResponseBody ResultObj update(@RequestBody TechnicalPlandetailEntity t, HttpServletRequest request ,@PathVariable Long id) {
		return technicalPlandetailService.update(t,id);
	}
	
	/**
	 *	详细列表
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/detail")
	public ModelAndView detail(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		String id=request.getParameter("id");
		String technical=request.getParameter("technical");
		String orderSqe=request.getParameter("orderSqe");
		model.put("technicalWorkId", id);
		model.put("technicalId", technical);
		model.put("orderSqe", orderSqe);
		return this.createModelAndView("technical/technicalPlanDetail/technicalPlandetailDetail", model);
	}
	
	/**
	 *	跳转到详细页面
	 */
	@RequestMapping("/getdetail/{id}")
	public ModelAndView getdetail(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		TechnicalPlandetailEntity technicalPlandetailEntity=technicalPlandetailService.findById(id);
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		List<TechnicalPlandetailEntity> list=technicalPlandetailService.findByCondition(conditions, null);
		if(!list.isEmpty()){
			technicalPlandetailEntity.setDjzqStr(list.get(0).getDjzqStr());
			technicalPlandetailEntity.setWcStatusStr(list.get(0).getWcStatusStr());
		}
        model.put("technicalPlandetailEntity", technicalPlandetailEntity);
		return this.createModelAndView("technical/technicalPlanDetail/technicalPlanDetail", model);
	}
}