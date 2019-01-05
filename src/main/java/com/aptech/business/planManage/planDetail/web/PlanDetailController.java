package com.aptech.business.planManage.planDetail.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.file.web.ZipUtils;
import com.aptech.business.planManage.planDetail.domain.PlanDetailEntity;
import com.aptech.business.planManage.planDetail.service.PlanDetailService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 详细计划配置控制器
 *
 * @author 
 * @created 2017-11-13 15:10:26
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/planDetail")
public class PlanDetailController extends BaseController<PlanDetailEntity> {
	
	@Autowired
	private PlanDetailService planDetailService;
	
	@Override
	public IBaseEntityOperation<PlanDetailEntity> getService() {
		return planDetailService;
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
		List<PlanDetailEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("planDetailTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboPlanDetailVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("planDetailCombobox", JsonUtil.toJson(comboPlanDetailVO.getOptions()));
		return this.createModelAndView("planManage/planDetail/planDetailList", model);
	}
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/indexDetail")
	public ModelAndView listDetail(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("planWholeId", request.getParameter("id"));
		return this.createModelAndView("planManage/planDetail/planDetailListDetail", model);
	}	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		String planWholeId=request.getParameter("planWholeId");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("planWholeId", planWholeId);
		model.put("num", request.getParameter("num"));
		//完成状态
		ComboboxVO typeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> typeMap  =  DictionaryUtil.getDictionaries("FINISH_TYPE");
		for(String key :  typeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = typeMap.get(key);
			typeCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("finishType", JsonUtil.toJson(typeCombobox.getOptions()));
		return this.createModelAndView("planManage/planDetail/planDetailAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		PlanDetailEntity planDetailEntity=planDetailService.findById(id);
		String planWholeId=request.getParameter("planWholeId");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("planWholeId", planWholeId);
		model.put("planDetailEntity", planDetailEntity);
		//完成状态
		ComboboxVO typeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> typeMap  =  DictionaryUtil.getDictionaries("FINISH_TYPE");
		for(String key :  typeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = typeMap.get(key);
			typeCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("finishType", JsonUtil.toJson(typeCombobox.getOptions()));;
		return this.createModelAndView("planManage/planDetail/planDetailEdit", model);
	}
	/**
	 * 添加
	 * 
	 * @Title: add
	 * @Description:
	 * @param T
	 * @return
	 */
	@RequestMapping(value = "/add" )
	public @ResponseBody ResultObj add(@RequestBody PlanDetailEntity planDetailEntity, HttpServletRequest request) {
		return planDetailService.add(planDetailEntity);
	}
	/**
	 * 下载
	 * @throws IOException 
	 */
	@RequestMapping("/upload/{id}")
	public void upload(HttpServletRequest request,@PathVariable Long id,HttpServletResponse response) throws IOException {
	        response.setContentType("APPLICATION/OCTET-STREAM");  
	        response.setHeader("Content-Disposition","attachment; filename=myfile.zip");
	        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
	        String realPath=request.getSession().getServletContext().getRealPath("/");
	        PlanDetailEntity planDetailEntity=planDetailService.findById(id);
	        JSONArray jSONArray=JSONArray.fromObject(planDetailEntity.getFileId());
	        		for (int i = 0; i < jSONArray.size() ;i++) {
	        			JSONObject jSONObject=(JSONObject) jSONArray.get(i);
	        			 ZipUtils.doCompress(realPath+jSONObject.get("url"), out);
	            }
	        	response.flushBuffer();
	            out.close();

	}
}