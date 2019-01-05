package com.aptech.business.technical.technical.web;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.ScrapstockExcuteEnum;
import com.aptech.business.component.dictionary.TechnicalBtnTypeEnum;
import com.aptech.business.component.dictionary.WorkBtnTypeEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.orgaApp.service.OrgaAppService;
import com.aptech.business.technical.technical.domain.TechnicalEntity;
import com.aptech.business.technical.technical.service.TechnicalService;
import com.aptech.business.technical.technicalWork.domain.TechnicalWorkEntity;
import com.aptech.business.technical.technicalWork.service.TechnicalWorkService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.workflow.definition.domain.DefinitionEntity;
import com.aptech.common.workflow.definition.service.DefinitionService;
import com.aptech.common.workflow.modelEditor.domain.BranchMarkEnum;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.common.workflow.modelEditor.service.NodeConfigService;
import com.aptech.common.workflow.processNodeAuth.domain.ProcessNodeAuthEntity;
import com.aptech.common.workflow.processNodeAuth.service.ProcessNodeAuthService;
import com.aptech.common.workflow.todoTask.domain.ExamMarkEnum;
import com.aptech.common.workflow.todoTask.domain.ExamResultEnum;
import com.aptech.common.workflow.todoTask.domain.TodoTaskEntity;
import com.aptech.common.workflow.todoTask.service.TodoTaskService;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.IdUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 技术监督配置控制器
 *
 * @author 
 * @created 2017-11-13 16:15:05
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/technical")
public class TechnicalController extends BaseController<TechnicalEntity> {
	
	@Autowired
	private TechnicalService technicalService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private OrgaAppService orgaAppService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private DefinitionService definitionService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Autowired
	private TodoTaskService todoTaskService;
	@Autowired
	private ProcessNodeAuthService processNodeAuthService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private TechnicalWorkService technicalWorkService;
	@Override
	public IBaseEntityOperation<TechnicalEntity> getService() {
		return technicalService;
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
		String uuid=request.getParameter("uuid");
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//TODO下拉树具体内容根据具体业务定制
		resultMap.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		SysUserEntity userEntity= RequestContext.get().getUser();
		resultMap.put("userEntity", userEntity);
		
		//监督专业
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("TECHNICAL_TYPE");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        resultMap.put("statusTypes", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
        
        //完成状态
        ComboboxVO codeDateTypesCombobox1 = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap1  =  DictionaryUtil.getDictionaries("TECHNICAL_WCZT");
        for(String key :  codeDateTypeMap1.keySet()){
        	SysDictionaryVO sysDictionaryVO1 = codeDateTypeMap1.get(key);
        	codeDateTypesCombobox1.addOption(sysDictionaryVO1.getCode(), sysDictionaryVO1.getName());
        }
        resultMap.put("wcStatus", JsonUtil.toJson(codeDateTypesCombobox1.getOptions()));
        
        //工作负责人下拉列表
  	    ComboboxVO requestUserVO = new ComboboxVO();
        //TODO下拉框具体内容根据具体业务定制
  	    List<Condition> conditions=new ArrayList<Condition>();
  	    conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
  	    conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));//选择人员时只列出当前场站的人员
        List<SysUserEntity> allUsers = sysUserService.findByCondition(conditions, null);
        for(SysUserEntity sysUserEntity : allUsers){
      	 requestUserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        resultMap.put("requestUsers", JsonUtil.toJson(requestUserVO.getOptions()));
        
        //删除垃圾数据
        if(uuid!=null){
        	TechnicalWorkEntity technicalWorkEntity=new TechnicalWorkEntity();
        	technicalWorkEntity.setUuidCode(uuid);
    		technicalWorkService.deleteByCondition("deleteByUuid", technicalWorkEntity);
        }
        resultMap.put("userId", userEntity.getId());
        resultMap.put("loginName", userEntity.getLoginName());
        resultMap.put("unitId", userEntity.getUnitId());
		return this.createModelAndView("technical/technical/technicalList", resultMap);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String recovUuid=request.getParameter("recovUuid");
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//TODO下拉树具体内容根据具体业务定制
		resultMap.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		//工作负责人下拉列表
  	    ComboboxVO requestUserVO = new ComboboxVO();
        //TODO下拉框具体内容根据具体业务定制
  	    List<Condition> conditions=new ArrayList<Condition>();
	    conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
        List<SysUserEntity> allUsers = sysUserService.findByCondition(conditions, null);
        for(SysUserEntity sysUserEntity : allUsers){
      	 requestUserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        resultMap.put("requestUsers", JsonUtil.toJson(requestUserVO.getOptions()));
        //监督专业
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("TECHNICAL_TYPE");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        resultMap.put("statusTypes", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
        
        //完成状态
        ComboboxVO codeDateTypesCombobox1 = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap1  =  DictionaryUtil.getDictionaries("TECHNICAL_WCZT");
        for(String key :  codeDateTypeMap1.keySet()){
        	SysDictionaryVO sysDictionaryVO1 = codeDateTypeMap1.get(key);
        	codeDateTypesCombobox1.addOption(sysDictionaryVO1.getCode(), sysDictionaryVO1.getName());
        }
        resultMap.put("wcStatus", JsonUtil.toJson(codeDateTypesCombobox1.getOptions()));
        
		SysUserEntity userEntity= RequestContext.get().getUser();
		resultMap.put("userEntity", userEntity);
		if(recovUuid!=null&&!recovUuid.equals("")){
			 resultMap.put("uuid", recovUuid);
		}else{
			 resultMap.put("uuid", IdUtil.creatUUID());
		}
		return this.createModelAndView("technical/technical/technicalAdd", resultMap);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//TODO下拉树具体内容根据具体业务定制
		resultMap.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		//工作负责人下拉列表
  	    ComboboxVO requestUserVO = new ComboboxVO();
        //TODO下拉框具体内容根据具体业务定制
  	    List<Condition> conditions=new ArrayList<Condition>();
	    conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
        List<SysUserEntity> allUsers = sysUserService.findByCondition(conditions, null);
        for(SysUserEntity sysUserEntity : allUsers){
      	 requestUserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        resultMap.put("requestUsers", JsonUtil.toJson(requestUserVO.getOptions()));
        //监督专业
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("TECHNICAL_TYPE");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        resultMap.put("statusTypes", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
        
        //完成状态
        ComboboxVO codeDateTypesCombobox1 = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap1  =  DictionaryUtil.getDictionaries("TECHNICAL_WCZT");
        for(String key :  codeDateTypeMap1.keySet()){
        	SysDictionaryVO sysDictionaryVO1 = codeDateTypeMap1.get(key);
        	codeDateTypesCombobox1.addOption(sysDictionaryVO1.getCode(), sysDictionaryVO1.getName());
        }
        resultMap.put("wcStatus", JsonUtil.toJson(codeDateTypesCombobox1.getOptions()));
        
		SysUserEntity userEntity= RequestContext.get().getUser();
		resultMap.put("userEntity", userEntity);
		
		TechnicalEntity technicalEntity = (TechnicalEntity)technicalService.findById(id);
		resultMap.put("technicalEntity", technicalEntity);
		return this.createModelAndView("technical/technical/technicalEdit", resultMap);
	}
	
	/**
	 *	跳转到详细页面
	 */
	@RequestMapping("/detail/{id}")
	public ModelAndView detail(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SysUserEntity userEntity= RequestContext.get().getUser();
		resultMap.put("userEntity", userEntity);
		TechnicalEntity technicalEntity = (TechnicalEntity)technicalService.findById(id);
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("t.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		List<TechnicalEntity> list=technicalService.findByCondition(conditions, null);
		if(!list.isEmpty()){
			 technicalEntity.setUnitName(list.get(0).getUnitName());
			 technicalEntity.setJdzyName(list.get(0).getJdzyName());
			 technicalEntity.setWcztName(list.get(0).getWcztName());
		}
        resultMap.put("technicalEntity", technicalEntity);
		return this.createModelAndView("technical/technical/technicalDetail", resultMap);
	}
	/**
	 * @Description:   修改时候的验证
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/updateValidate/{id}")
	public @ResponseBody ResultObj updateValidate(HttpServletRequest request ,@PathVariable Long id) {
		return technicalService.updateValidate(id);
	}
	
	/**
	 * @Description:   自己写的修改方法
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/update/{id}")
	public @ResponseBody ResultObj update(@RequestBody TechnicalEntity t, HttpServletRequest request ,@PathVariable Long id) {
		return technicalService.update(t,id);
	}
	
	/** 批量删除
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/bulkDelete/{ids}", method = RequestMethod.DELETE)
	public @ResponseBody ResultObj bulkDelete(@RequestBody List<Integer> ids) {
		ResultObj resultObj = new ResultObj();
		technicalService.deleteBulk(ids);
		return resultObj;
	}
	
	/**
	 * @Description:   单个删除时候的验证
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/deleteValidate/{id}")
	public @ResponseBody ResultObj deleteValidate(HttpServletRequest request ,@PathVariable Long id) {
		return technicalService.deleteValidate(id);
	}
	
	/**
	 * @Description:   提交时候的验证
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/tijiaoValidate/{id}")
	public @ResponseBody ResultObj tijiaoValidate(HttpServletRequest request ,@PathVariable Long id) {
		return technicalService.tijiaoValidate(id);
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
		String id=request.getParameter("id");
		resultMap.put("id", id);
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.TECHNICAL_PROCESS_KEY.getName()));
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
		return new ModelAndView("/technical/technical/sureSubmitPerson",resultMap);
	}
	/**
	 * @Description:   提交方法
	 * @author         zhangzq 
	 * @Date           2017年6月12日 上午11:39:16 
	 * @throws         Exception
	 */
	@RequestMapping("/submit")
	public @ResponseBody ResultObj submit(HttpServletRequest request){
		ResultObj resultObj = new ResultObj();
		try {
			String id=request.getParameter("workId");
			String selectUser=request.getParameter("selectUser");
			technicalService.submit(id,selectUser);
			resultObj.setResult("success");
		} catch (Exception e) {
			e.printStackTrace();
			resultObj.setResult("error");
		}
		return resultObj;
	}
	
	@RequestMapping("/approve/{id}/{type}")
	public ModelAndView approve(HttpServletRequest request, @PathVariable Long id
			,@PathVariable String type) throws ParseException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//查询各个人的按钮权限 开始
		SysUserEntity userEntity= RequestContext.get().getUser();
		resultMap.put("userEntity", userEntity);
		List<Condition> conditionsLc=new ArrayList<Condition>();
		conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
		conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.TECHNICAL_PROCESS_KEY.getName()));
		conditionsLc.add(new Condition("task.end_time_ IS NULL"));
		List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditionsLc, null);
		TodoTaskEntity todoTaskEntity=null;
		if(!list.isEmpty()){
			todoTaskEntity=list.get(0);
			List<ProcessNodeAuthEntity> nodeList=processNodeAuthService.getAuthorityList(todoTaskEntity.getTaskDefKey());
			resultMap.put("nodeList", nodeList);
		}
		//查询各个人的按钮权限 结束
		
		// 返回前台数据项
		TechnicalEntity technicalEntity = (TechnicalEntity)technicalService.findById(id);
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("t.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		List<TechnicalEntity> listtechnical=technicalService.findByCondition(conditions, null);
		if(!listtechnical.isEmpty()){
			 technicalEntity.setUnitName(listtechnical.get(0).getUnitName());
			 technicalEntity.setJdzyName(listtechnical.get(0).getJdzyName());
			 technicalEntity.setWcztName(listtechnical.get(0).getWcztName());
		}
        resultMap.put("technicalEntity", technicalEntity);
	
		return this.createModelAndView("technical/technical/technicalApprove", resultMap);
	}
	/**
	 *	跳转到负责人再提交页面
	 */
	@RequestMapping("/getAddZtj")
	public ModelAndView getAddZtj(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String technicalId=request.getParameter("technicalId");
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		model.put("technicalId", technicalId);
		return this.createModelAndView("technical/technical/technicalAddZtj", model);
	}
	/**
	 * @Description:  负责人提交同意
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping("/agreeFzrtj")
	public @ResponseBody ResultObj agreeFzrtj(HttpServletRequest request,@RequestBody TechnicalEntity technicalEntity){
		ResultObj resultObj = new ResultObj();
		try {
			technicalEntity.setSpFlag(TechnicalBtnTypeEnum.REJECT.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),technicalEntity.getApproveIdea()==null?"":technicalEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			
			technicalService.updateSpnrAgree(technicalEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 *	跳转到生技部页面
	 */
	@RequestMapping("/getAddBiotech")
	public ModelAndView getAddBiotech(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String technicalId=request.getParameter("technicalId");
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		model.put("technicalId", technicalId);
		return this.createModelAndView("technical/technical/technicalAddBiotech", model);
	}
	/**
	 * @Description:   生技部同意
	 * @author         huoxy 
	 * @throws         Exception
	 */
	@RequestMapping("/agreeBiotech")
	public @ResponseBody ResultObj agreeBiotech(HttpServletRequest request,@RequestBody TechnicalEntity technicalEntity){
		ResultObj resultObj = new ResultObj();
		try {
			technicalEntity.setSpFlag(TechnicalBtnTypeEnum.BIOTECH.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),technicalEntity.getApproveIdea()==null?"":technicalEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			
			technicalService.updateSpnrAgree(technicalEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   生技部不同意
	 * @author         huoxy 
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeBiotech")
	public @ResponseBody ResultObj disAgreeBiotech(HttpServletRequest request,@RequestBody TechnicalEntity technicalEntity){
		ResultObj resultObj = new ResultObj();
		try {
			technicalEntity.setSpFlag(TechnicalBtnTypeEnum.BIOTECH.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			TechnicalEntity technicalEn=technicalService.findById(technicalEntity.getId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),technicalEn.getGzfzrtxWcqk());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),technicalEntity.getApproveIdea()==null?"":technicalEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			technicalService.updateSpnrDisagree(technicalEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 *	跳转到生技主任页面
	 */
	@RequestMapping("/getAddBiotechManage")
	public ModelAndView getAddBiotechManage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String technicalId=request.getParameter("technicalId");
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		model.put("technicalId", technicalId);
		return this.createModelAndView("technical/technical/technicalAddBiotechManage", model);
	}
	/**
	 * @Description:   生技主任同意
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping("/agreeBiotechManage")
	public @ResponseBody ResultObj agreeBiotechManage(HttpServletRequest request,@RequestBody TechnicalEntity technicalEntity){
		ResultObj resultObj = new ResultObj();
		try {
			technicalEntity.setSpFlag(TechnicalBtnTypeEnum.BIOTECHMANAGE.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),technicalEntity.getApproveIdea()==null?"":technicalEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			
			technicalService.updateSpnrAgree(technicalEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   生技主任不同意
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeBiotechManage")
	public @ResponseBody ResultObj disAgreeBiotechManage(HttpServletRequest request,@RequestBody TechnicalEntity technicalEntity){
		ResultObj resultObj = new ResultObj();
		try {
			technicalEntity.setSpFlag(TechnicalBtnTypeEnum.BIOTECHMANAGE.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			TechnicalEntity technicalEn=technicalService.findById(technicalEntity.getId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),technicalEn.getGzfzrtxWcqk());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),technicalEntity.getApproveIdea()==null?"":technicalEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			technicalService.updateSpnrDisagree(technicalEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   生技主任驳回到上一级
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeBiotechManageUp")
	public @ResponseBody ResultObj disAgreeBiotechManageUp(HttpServletRequest request,@RequestBody TechnicalEntity technicalEntity){
		ResultObj resultObj = new ResultObj();
		try {
			technicalEntity.setSpFlag(TechnicalBtnTypeEnum.BIOTECHMANAGE.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			TechnicalEntity technicalEn=technicalService.findById(technicalEntity.getId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),technicalEn.getGzfzrtxWcqk());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK_END.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK_END.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),technicalEntity.getApproveIdea()==null?"":technicalEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			technicalService.updateSpnrDisagreeUp(technicalEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 *	跳转到上级领导页面
	 */
	@RequestMapping("/getAddLeader")
	public ModelAndView getAddLeader(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String technicalId=request.getParameter("technicalId");
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		model.put("technicalId", technicalId);
		return this.createModelAndView("technical/technical/technicalAddLeader", model);
	}
	/**
	 * @Description:  上级领导同意
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping("/agreeLeader")
	public @ResponseBody ResultObj agreeLeader(HttpServletRequest request,@RequestBody TechnicalEntity technicalEntity){
		ResultObj resultObj = new ResultObj();
		try {
			technicalEntity.setSpFlag(TechnicalBtnTypeEnum.LEADER.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),technicalEntity.getApproveIdea()==null?"":technicalEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			
			technicalService.updateSpnrAgree(technicalEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   上级领导不同意
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeLeader")
	public @ResponseBody ResultObj disAgreeLeader(HttpServletRequest request,@RequestBody TechnicalEntity technicalEntity){
		ResultObj resultObj = new ResultObj();
		try {
			technicalEntity.setSpFlag(TechnicalBtnTypeEnum.LEADER.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			TechnicalEntity technicalEn=technicalService.findById(technicalEntity.getId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),technicalEn.getGzfzrtxWcqk());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),technicalEntity.getApproveIdea()==null?"":technicalEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			technicalService.updateSpnrDisagree(technicalEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   上级领导驳回到上一级
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeLeaderUp")
	public @ResponseBody ResultObj disAgreeLeaderUp(HttpServletRequest request,@RequestBody TechnicalEntity technicalEntity){
		ResultObj resultObj = new ResultObj();
		try {
			technicalEntity.setSpFlag(TechnicalBtnTypeEnum.LEADER.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			TechnicalEntity technicalEn=technicalService.findById(technicalEntity.getId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),technicalEn.getGzfzrtxWcqk());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK_END.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK_END.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),technicalEntity.getApproveIdea()==null?"":technicalEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			technicalService.updateSpnrDisagreeUp(technicalEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 *	跳转到总工页面
	 */
	@RequestMapping("/getAddZg")
	public ModelAndView getAddZg(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String technicalId=request.getParameter("technicalId");
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		model.put("technicalId", technicalId);
		return this.createModelAndView("technical/technical/technicalAddZg", model);
	}
	/**
	 * @Description:  总工同意
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping("/agreeZg")
	public @ResponseBody ResultObj agreeZg(HttpServletRequest request,@RequestBody TechnicalEntity technicalEntity){
		ResultObj resultObj = new ResultObj();
		try {
			technicalEntity.setSpFlag(WorkBtnTypeEnum.ZG.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),technicalEntity.getApproveIdea()==null?"":technicalEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			
			technicalService.updateSpnrAgree(technicalEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   总工不同意
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeZg")
	public @ResponseBody ResultObj disAgreeZg(HttpServletRequest request,@RequestBody TechnicalEntity technicalEntity){
		ResultObj resultObj = new ResultObj();
		try {
			technicalEntity.setSpFlag(WorkBtnTypeEnum.ZG.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			TechnicalEntity technicalEn=technicalService.findById(technicalEntity.getId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),technicalEn.getGzfzrtxWcqk());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),technicalEntity.getApproveIdea()==null?"":technicalEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			technicalService.updateSpnrDisagree(technicalEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	//废票
	@RequestMapping("/disAgreeFp")
	public @ResponseBody ResultObj disAgreeFp(HttpServletRequest request,@RequestBody TechnicalEntity technicalEntity){
		ResultObj resultObj = new ResultObj();
		try {
			technicalEntity.setSpFlag(TechnicalBtnTypeEnum.REJECT.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),technicalEntity.getApproveIdea()==null?"":technicalEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			technicalService.updateSpnrDisagree(technicalEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	//撤销
	@RequestMapping("/disAgreeCx")
	public @ResponseBody ResultObj disAgreeCx(HttpServletRequest request,@RequestBody TechnicalEntity technicalEntity){
		ResultObj resultObj = new ResultObj();
		try {
			technicalEntity.setSpFlag(WorkBtnTypeEnum.CX.getCode());
			String id=request.getParameter("id");
			technicalEntity.setId(Long.valueOf(id));
			//查询各个人的按钮权限 开始
			SysUserEntity userEntity= RequestContext.get().getUser();
			List<Condition> conditionsLc=new ArrayList<Condition>();
			//conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
			conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
			conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.TECHNICAL_PROCESS_KEY.getName()));
			conditionsLc.add(new Condition("task.end_time_ IS NULL"));
			List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditionsLc, null);
			TodoTaskEntity todoTaskEntity=null;
			if(!list.isEmpty()){
				todoTaskEntity=list.get(0);
				Map<String, Object> taskVariables=new HashMap<String, Object>();
				taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
				taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
				taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
				taskVariables.put(ExamMarkEnum.COMMENT.getCode(),technicalEntity.getApproveIdea()==null?"":technicalEntity.getApproveIdea());//审批意见
				actTaskService.complete(todoTaskEntity.getId_(),todoTaskEntity.getProcInstId(),taskVariables);
				technicalService.updateSpnrDisagree(technicalEntity,userEntity);
			}
			//查询各个人的按钮权限 结束
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 * @Description:   保存的验证
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/saveValidate")
	public @ResponseBody ResultObj saveValidate(HttpServletRequest request ) {
		String unitId=request.getParameter("unitId");
		String jdzyId=request.getParameter("jdzyId");
		String time=request.getParameter("time");
		String technicalId=request.getParameter("technicalId");
		String uuid=request.getParameter("uuid");
		return technicalService.saveValidate(unitId,jdzyId,time,technicalId,uuid);
	}
	/**
	 * @Description:   审批的验证
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/approveValidate")
	public @ResponseBody ResultObj approveValidate(HttpServletRequest request ) {
		String technicalId=request.getParameter("technicalId");
		return technicalService.approveValidate(technicalId);
	}
	/**
	 * @Description:   
	 * @author         zhangzq 
	 * @Date           2017年6月28日 下午6:32:33 
	 * @throws         Exception
	 */
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		String conditionsChan=req.getParameter("conditions");
		params.put("conditions", JSONArray.fromObject(conditionsChan));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		Page<TechnicalEntity> page=new Page<TechnicalEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("C_CREATE_DATE"));
		List<TechnicalEntity> dataList=technicalService.findByCondition(conditions, page);
		List<String []> dataStyle=new ArrayList<String[]>();
 		for (int i = 0; i < dataList.size(); i++) {
			if(dataList.get(i).getWcztName()!=null&&dataList.get(i).getWcztName().equals("未完成")){
				dataStyle.add(new String []{String.valueOf(i+2),"6",
						dataList.get(i).getWcztName(),String.valueOf(Cell.CELL_TYPE_STRING)});
			}
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		for (int j = 0; j < dataList.size(); j++) {
			dataList.get(j).setNumber(j+1);
		}
		resultMap.put("dataStyle", dataStyle);
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "技术监督模板.xlsx","计划.xlsx", resultMap);
	}
	
	/**
	 * @Description:   撤销时候的验证
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/cexiaoValidate/{id}")
	public @ResponseBody ResultObj cexiaoValidate(HttpServletRequest request ,@PathVariable Long id) {
		return technicalService.cexiaoValidate(id);
	}
}