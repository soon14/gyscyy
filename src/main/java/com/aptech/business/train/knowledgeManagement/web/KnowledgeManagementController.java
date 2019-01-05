package com.aptech.business.train.knowledgeManagement.web;

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
import com.aptech.business.train.knowledgeManagement.domain.KnowledgeManagementEntity;
import com.aptech.business.train.knowledgeManagement.service.KnowledgeManagementService;
import com.aptech.business.train.trainPlan.domain.TrainPlanEntity;
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
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.util.StringUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 知识库管理配置控制器
 *
 * @author 
 * @created 2018-03-26 13:43:00
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/knowledgeManagement")
public class KnowledgeManagementController extends BaseController<KnowledgeManagementEntity> {
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private SysUnitService sysUnitService;
	
	@Autowired
	private KnowledgeManagementService knowledgeManagementService;
	
	@Override
	public IBaseEntityOperation<KnowledgeManagementEntity> getService() {
		return knowledgeManagementService;
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
		//知识库类型
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("KNOWLEDGE_TYPE");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("knowledgeTypeCombobox", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
        // 人员
 		ComboboxVO createPeople = new ComboboxVO();
 		List<Condition> userCondition = new ArrayList<Condition>();
 		SysUserEntity userEntity= RequestContext.get().getUser();
 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
 		List<SysUserEntity> userList = sysUserService.findByCondition(userCondition, null);
 		for (SysUserEntity user : userList) {
 			createPeople.addOption(user.getId().toString(), user.getName());
 		}
 		model.put("createPeopleCombobox", JsonUtil.toJson(createPeople.getOptions()));
 		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		return this.createModelAndView("train/knowledgeManagement/knowledgeManagementList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		// 部门下拉树
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		//知识库类型
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("KNOWLEDGE_TYPE");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("knowledgeTypeCombobox", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
		
		return this.createModelAndView("train/knowledgeManagement/knowledgeManagementAdd", model);
	}
	
	/**
	* 添加保存
	* @author ly
	* @date 2018年3月19日 下午5:04:26 
	* @param trainPlanEntity
	* @return
	* @return ResultObj
	*/
	@RequestMapping("/saveAddPage")
	public @ResponseBody ResultObj saveAddPage(@RequestBody KnowledgeManagementEntity knowledgeManagementEntity){
		ResultObj resultObj = new ResultObj();
		if(knowledgeManagementEntity.getDownloadCount()==null){
			knowledgeManagementEntity.setDownloadCount("0");
		}
		knowledgeManagementService.addEntity(knowledgeManagementEntity);
		return resultObj;
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		KnowledgeManagementEntity knowledgeManagementEntity = (KnowledgeManagementEntity)knowledgeManagementService.findById(id);
		model.put("entity", knowledgeManagementEntity);
		model.put("entityJson", JsonUtil.toJson(knowledgeManagementEntity));
		// 部门下拉树
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		//知识库类型
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("KNOWLEDGE_TYPE");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("knowledgeTypeCombobox", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
		
		return this.createModelAndView("train/knowledgeManagement/knowledgeManagementEdit", model);
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
	public @ResponseBody ResultObj saveEditPage(@RequestBody KnowledgeManagementEntity knowledgeManagementEntity){
		ResultObj resultObj = new ResultObj();
		knowledgeManagementService.updateEntity(knowledgeManagementEntity);	
		return resultObj;
	}
	
	/**
	* 跳转查看页
	* @author ly
	* @date 2018年3月20日 上午9:38:49 
	* @param request
	* @param id
	* @return
	* @return ModelAndView
	*/
	@RequestMapping("/showPage/{id}")
	public ModelAndView showPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		KnowledgeManagementEntity knowledgeManagementEntity = (KnowledgeManagementEntity)knowledgeManagementService.findById(id);
		model.put("entity", knowledgeManagementEntity);
		model.put("entityJson", JsonUtil.toJson(knowledgeManagementEntity));
		model.put("sysUnitEntity", sysUnitService.findById(Long.valueOf(knowledgeManagementEntity.getUnitId())).getName());
		return this.createModelAndView("train/knowledgeManagement/knowledgeManagementShow", model);
	}
	
	/**
	* 附件下载次数统计
	* @author ly
	* @date 2018年4月14日 上午10:04:39 
	* @param request
	* @param id
	* @return
	* @return ModelAndView
	*/
	@RequestMapping("/downloadCount/{id}")
	public @ResponseBody ResultObj downloadCount(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 每次点击递增
		KnowledgeManagementEntity knowledgeManagementEntity = (KnowledgeManagementEntity)knowledgeManagementService.findById(id);
		//获取数据库中下载次数
		String downloadCount = knowledgeManagementEntity.getDownloadCount();
		int downloadCountInt = Integer.valueOf(downloadCount);
		downloadCountInt++;
		knowledgeManagementEntity.setDownloadCount(String.valueOf(downloadCountInt));
		ResultObj resultObj = new ResultObj();
		knowledgeManagementService.updateEntity(knowledgeManagementEntity);
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
 		List<KnowledgeManagementEntity> dataList=knowledgeManagementService.findByCondition(params, null);
 		int i= 1;
 		DateFormatUtil format = DateFormatUtil.getInstance("yyyy-MM-dd");
 		for(KnowledgeManagementEntity knowledgeManagementEntity: dataList){
 			knowledgeManagementEntity.setNumber(i++);
 			String exportDate = format.format(knowledgeManagementEntity.getCreateDate());
 			knowledgeManagementEntity.setExportDate(exportDate);
 		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "知识库管理报表模板.xlsx","知识库管理.xlsx", resultMap);
	}
}