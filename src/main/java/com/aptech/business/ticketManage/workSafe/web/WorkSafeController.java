package com.aptech.business.ticketManage.workSafe.web;

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

import com.aptech.business.ticketManage.workSafe.domain.WorkSafeEntity;
import com.aptech.business.ticketManage.workSafe.service.WorkSafeService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 安全措施配置控制器
 *
 * @author 
 * @created 2017-06-05 17:12:33
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/workSafe")
public class WorkSafeController extends BaseController<WorkSafeEntity> {
	
	@Autowired
	private WorkSafeService workSafeService;
	
	@Override
	public IBaseEntityOperation<WorkSafeEntity> getService() {
		return workSafeService;
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView list(HttpServletRequest request, Map<String, Object> params, @PathVariable Long appId) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<WorkSafeEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("workSafeTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboWorkSafeVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("workSafeCombobox", JsonUtil.toJson(comboWorkSafeVO.getOptions()));
		return this.createModelAndView("ticketManage/worksafe/workSafeList", model);
	}
	
	
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String flag=request.getParameter("flag");
		String workId=request.getParameter("workId");
		String uuid=request.getParameter("uuid");
		String total=request.getParameter("total");
		//下拉框
		//编码类型
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("EXECUTE_SITUATION");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("executeSituation", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
        model.put("flag", flag);
        model.put("workId", workId); 
        model.put("uuid", uuid); 
        model.put("total", total); 
		return this.createModelAndView("ticketManage/worksafe/workSafeAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}/{flag}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id, @PathVariable String flag){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		WorkSafeEntity workSafeEntity=workSafeService.findById(id);
		model.put("workSafeEntity", workSafeEntity);
		//下拉框
		//编码类型
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("EXECUTE_SITUATION");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("executeSituation", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
        model.put("flag", flag); 
		return this.createModelAndView("ticketManage/worksafe/workSafeEdit", model);
	}
	/**
	 * @Description:   审批的时候可以修改 执行情况，跳页面
	 * @author         zhangzq 
	 * @Date           2017年6月21日 下午2:54:51 
	 * @throws         Exception
	 */
	@RequestMapping("/getApproveEdit/{id}/{flag}")
	public ModelAndView getApproveEdit(HttpServletRequest request, @PathVariable Long id, @PathVariable String flag){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		WorkSafeEntity workSafeEntity=workSafeService.findById(id);
		model.put("workSafeEntity", workSafeEntity);
		//下拉框
		//编码类型
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("EXECUTE_SITUATION");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("executeSituation", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
        model.put("flag", flag); 
		return this.createModelAndView("ticketManage/worksafe/workSafeApproveEdit", model);
	}
	
	/**
	 *	跳转到添加页面  为继电保护安全措施票用 20171026 zzq
	 */
	@RequestMapping("/getHelpSafeAdd")
	public ModelAndView getHelpSafeAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String flag=request.getParameter("flag");
		String workId=request.getParameter("workId");
		String uuid=request.getParameter("uuid");
		String total=request.getParameter("total");
		//下拉框
		//编码类型
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("EXECUTE_SITUATION");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("executeSituation", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
        model.put("flag", flag);
        model.put("workId", workId); 
        model.put("uuid", uuid); 
        model.put("total", total); 
		return this.createModelAndView("ticketManage/workHelpSafe/helpSafeAdd", model);
	}
	
	/**
	 *	跳转到修改页面  为继电保护安全措施票用 20171026 zzq
	 */
	@RequestMapping("/getHelpSafeEdit/{id}/{flag}")
	public ModelAndView getHelpSafeEditPage(HttpServletRequest request, @PathVariable Long id, @PathVariable String flag){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		WorkSafeEntity workSafeEntity=workSafeService.findById(id);
		model.put("workSafeEntity", workSafeEntity);
		//下拉框
		//编码类型
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("EXECUTE_SITUATION");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("executeSituation", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
        model.put("flag", flag); 
		return this.createModelAndView("ticketManage/workHelpSafe/helpSafeEdit", model);
	}
	/**
	 * @Description:   审批的时候可以修改 执行情况，跳页面  为继电保护安全措施票用 20171026 zzq
	 * @author         zhangzq 
	 * @Date           20171026
	 * @throws         Exception
	 */
	@RequestMapping("/getHelpSafeApproveEdit/{id}/{flag}")
	public ModelAndView getHelpSafeApproveEdit(HttpServletRequest request, @PathVariable Long id, @PathVariable String flag){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		WorkSafeEntity workSafeEntity=workSafeService.findById(id);
		model.put("workSafeEntity", workSafeEntity);
		//下拉框
		//编码类型
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("EXECUTE_SITUATION");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("executeSituation", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
        model.put("flag", flag); 
		return this.createModelAndView("ticketManage/workHelpSafe/helpSafeApproveEdit", model);
	}
	/**
	 * @Description:   审批的时候可以修改 执行情况，跳页面  为继电保护安全措施票用  zzq 恢复的时候的
	 * @author         zhangzq 
	 * @Date           20171103
	 * @throws         Exception
	 */
	@RequestMapping("/getHelpSafeApproveEditHf/{id}/{flag}")
	public ModelAndView getHelpSafeApproveEditHf(HttpServletRequest request, @PathVariable Long id, @PathVariable String flag){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		WorkSafeEntity workSafeEntity=workSafeService.findById(id);
		model.put("workSafeEntity", workSafeEntity);
		//下拉框
		//编码类型
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("EXECUTE_SITUATION");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("hfSituation", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
        model.put("flag", flag); 
		return this.createModelAndView("ticketManage/workHelpSafe/helpSafeApproveEditHf", model);
	}
	
	
	/**
	 * @Description:   zzq修改第二次需求
	 * @author         zhangzq 
	 * @Date           20180322
	 * @throws         Exception
	 */
	@RequestMapping("/getHelpSafeAqcsAdd")
	public ModelAndView getHelpSafeAqcsAdd(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String flag=request.getParameter("flag");
		String uuid=request.getParameter("uuid");
		String total=request.getParameter("total");
		String workId=request.getParameter("workId");
		//下拉框
		//编码类型
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("EXECUTE_SITUATION");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("executeSituation", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
        model.put("hfSituation", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
        model.put("flag", flag);
        model.put("uuid", uuid); 
        model.put("total", total); 
        model.put("workId", workId); 
		return this.createModelAndView("ticketManage/workHelpSafe/helpSafeAqcsAdd", model);
	}
	/**
	 * @Description:   zzq修改第二次需求
	 * @author         zhangzq 
	 * @Date           20180322
	 * @throws         Exception
	 */
	@RequestMapping("/getHelpSafeAqcsEdit/{id}")
	public ModelAndView getHelpSafeAqcsEdit(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		WorkSafeEntity workSafeEntity=workSafeService.findById(id);
		model.put("workSafeEntity", workSafeEntity);
		//下拉框
		//编码类型
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("EXECUTE_SITUATION");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("executeSituation", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
        model.put("hfSituation", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
        return this.createModelAndView("ticketManage/workHelpSafe/helpSafeAqcsEdit", model);
	}
	
	/**
	 *	添加安措相关数据
	 */
	@RequestMapping("/addSafeInfo/{uuid}/{type}")
	public @ResponseBody <T> ResultObj addSafeInfo(HttpServletRequest request,@RequestBody Map<String, Object> params, @PathVariable String uuid,@PathVariable Long type){
		return workSafeService.addSafeInfo(request, params,uuid,type);
	}
	
	/**
	 *	添加安措相关数据-修改页面
	 */
	@RequestMapping("/editSafeInfo/{workid}/{type}")
	public @ResponseBody <T> ResultObj editSafeInfo(HttpServletRequest request,@RequestBody Map<String, Object> params, @PathVariable Long workid,@PathVariable Long type){
		return workSafeService.editSafeInfo(request, params,workid,type);
	}
}