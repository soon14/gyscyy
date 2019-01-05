package com.aptech.business.run.workPlan.web;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.ProtectResultEnum;
import com.aptech.business.component.dictionary.ProtectStatusEnum;
import com.aptech.business.run.protect.domain.ProtectEntity;
import com.aptech.business.run.workPlan.domain.WorkPlanEntity;
import com.aptech.business.run.workPlan.service.WorkPlanService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.template.listForm.service.ListFormService;
import com.aptech.common.template.listForm.vo.AppListFormVO;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
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
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 定期工作计划配置控制器
 *
 * @author 
 * @created 2017-05-27 11:40:09
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/workPlan")
public class WorkPlanController extends BaseController<WorkPlanEntity> {
	
	@Autowired
	private WorkPlanService workPlanService;
	@Autowired
    private ListFormService listFormService;
	@Autowired
    private SysUnitService sysUnitService;
	@Autowired
    private SysUserService sysUserService;
	@Autowired
    private ActTaskService actTaskService;
	@Autowired
    private DefinitionService definitionService;
	@Autowired
    private NodeConfigService nodeConfigService;
	@Autowired
    private TodoTaskService todoTaskService;
    @Autowired
    private ProcessNodeAuthService processNodeAuthService;
	@Override
	public IBaseEntityOperation<WorkPlanEntity> getService() {
		return workPlanService;
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
        List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//TODO下拉树具体内容根据具体业务定制
		model.put("workPlanTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboWorkPlanVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		List<Condition> conditions = new ArrayList<Condition>();
		SysUserEntity userEntity= RequestContext.get().getUser();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> allUsers = sysUserService.findByCondition(
				conditions, null);
        for(SysUserEntity sysUserEntity : allUsers){
            comboWorkPlanVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        //TODO下拉框具体内容根据具体业务定制
        model.put("workPlanCombobox", JsonUtil.toJson(comboWorkPlanVO.getOptions()));   
        //编码类型
        ComboboxVO timeDimensionCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("TIME_DIMENSION");
        
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            timeDimensionCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("timeDimensionCombobox", JsonUtil.toJson(timeDimensionCombobox.getOptions()));   		
        return this.createModelAndView("run/workPlan/workPlanList", model);
	}
	
	/**
	 * list数据查询
	 * 
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/search/{appId}", method = RequestMethod.POST)
	public @ResponseBody ResultListObj commonList(HttpServletRequest request,
			@RequestBody Map<String, Object> params, @PathVariable Long appId) {
		AppListFormVO appListFormVO = listFormService.getAppListFormVOById(appId);
		Page<Map<String, Object>> page = PageUtil.getPage(params);
		List<Condition> conditions = new ArrayList<Condition>();
		if (params.get("conditions") != null) {
			conditions = OrmUtil.changeMapToCondition(params);
			if(!RequestContext.get().isDeveloperMode()){
				SysUserEntity sysUserEntity = (SysUserEntity)RequestContext.get().getUser();
				for(Condition condition: conditions){
					if(condition.getValue().toString().contains("${")){
						String flag = condition.getValue().toString();
						if(flag.equals("${currentUnitId}")){
							condition.setValue(sysUserEntity.getUnitId());
						}else if(flag.equals("${currentUserId}")){
							condition.setValue(sysUserEntity.getId());
						}else if(flag.equals("${currentRoleId}")){
							condition.setValue(sysUserEntity.getRoleIds());
						}
					}
				}
			}else{
				for(Condition condition: conditions){
					if(condition.getValue().toString().contains("${")){
						String flag = condition.getValue().toString();
						if(flag.equals("${currentUnitId}")){
							condition.setValue(0);
						}else if(flag.equals("${currentUserId}")){
							condition.setValue(0);
						}else if(flag.equals("${currentRoleId}")){
							condition.setValue(0);
						}
					}
				}
			}
		}
		List<Sort> orders = OrmUtil.changeMapToOrders(params);
		if (page != null) {
			page.setOrders(orders);
		}
		List<Map<String, Object>> entities = listFormService.findListByCondition(appListFormVO, conditions, page);
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer) params.get("draw"));
		if (entities != null) {
			resultObj.setData(entities);
			if (page != null) {
				resultObj.setRecordsTotal(page.getTotal());
			} else {
				resultObj.setRecordsTotal((long) entities.size());
			}
		}
		return resultObj;
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
        List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//TODO下拉树具体内容根据具体业务定制
		model.put("workPlanTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboWorkPlanVO = new ComboboxVO();
		List<Condition> conditions = new ArrayList<Condition>();
		SysUserEntity userEntity= RequestContext.get().getUser();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> allUsers = sysUserService.findByCondition(
				conditions, null);
        for(SysUserEntity sysUserEntity : allUsers){
            comboWorkPlanVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
		//TODO下拉框具体内容根据具体业务定制
		model.put("workPlanCombobox", JsonUtil.toJson(comboWorkPlanVO.getOptions()));	
		//编码类型
        ComboboxVO timeDimensionCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("TIME_DIMENSION");
        
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            timeDimensionCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("timeDimensionCombobox", JsonUtil.toJson(timeDimensionCombobox.getOptions()));   
        model.put("userEntity", userEntity);
		return this.createModelAndView("run/workPlan/workPlanAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		WorkPlanEntity workPlanEntity = (WorkPlanEntity)workPlanService.findById(id);
		model.put("dataMap", workPlanEntity);
		model.put("dataMapJson", JsonUtil.toJson(workPlanEntity));
		
		//TODO下拉树具体内容根据具体业务定制
        List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();

		model.put("workPlanTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboWorkPlanVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		List<Condition> conditions = new ArrayList<Condition>();
		SysUserEntity userEntity= RequestContext.get().getUser();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> allUsers = sysUserService.findByCondition(
				conditions, null);
	        for(SysUserEntity sysUserEntity : allUsers){
	            comboWorkPlanVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
	        }
		model.put("workPlanCombobox", JsonUtil.toJson(comboWorkPlanVO.getOptions()));
		//编码类型
        ComboboxVO timeDimensionCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("TIME_DIMENSION");       
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            timeDimensionCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("timeDimensionCombobox", JsonUtil.toJson(timeDimensionCombobox.getOptions())); 
		
		return this.createModelAndView("run/workPlan/workPlanEdit", model);
	}
	/**
	* @Title: save
	* @Description: 保存操作
	* @author sunliang
	* @date 2017年6月28日上午10:56:10
	* @param t
	* @param request
	* @return
	* @throws
	*/
	@RequestMapping(value = "/save")
    public @ResponseBody
    ResultObj save(@RequestBody WorkPlanEntity t, HttpServletRequest request){
        return workPlanService.add(t);
    }
    /**
    * @Title: sureSubmitPerson
    * @Description: 查询审批人
    * @author sunliang
    * @date 下午3:50:10
    * @param request
    * @return
    * @throws
    */
    @RequestMapping("/submitPerson")
    public ModelAndView submitPerson(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<String, Object>();
        List<Condition> conditions=new ArrayList<Condition>();
        conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.WORKPLAN_PROCESS_KEY.getName()));
        List<DefinitionEntity> defList=definitionService.findByCondition(conditions, null);
        String modelId="";
        if(!defList.isEmpty()){
            modelId=defList.get(0).getModelId();
        }
        //审批页面点击签发按钮的时候，把下一步的人查询出来
        SysUserEntity userEntity = RequestContext.get().getUser();
        List<SysUserEntity> userList= nodeConfigService.getFirstNodeTransactor(modelId,"",userEntity);
        request.setAttribute("userList", userList);
        return this.createModelAndView("run/workPlan/submitPerson", model);
    }
	/**
     *  提交审批
     */
	@RequestMapping("/toCheck/{id}")
    public @ResponseBody ResultObj toCheck(HttpServletRequest request, @PathVariable Long id,@RequestBody Map<String, Object> params){
        ResultObj resultObj = new ResultObj();
        workPlanService.tocheck(id,params);
        return resultObj;
    }
	/**
	* @Title: againSubmit
	* @Description: 再提交
	* @author sunliang
	* @date 2017年8月16日下午4:06:32
	* @param t
	* @return
	* @throws
	*/
	@RequestMapping("/againSubmit/{id}")
    public @ResponseBody ResultObj againSubmit(@RequestBody WorkPlanEntity t){
        ResultObj resultObj = new ResultObj();
        try {
             
            Map<String, Object> variables=new HashMap<String, Object>();
            variables.put("status", ProtectStatusEnum.ZRCHECK.getCode());
            variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
            variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
            variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
            variables.put(ExamMarkEnum.COMMENT.getCode(), t.getApproveIdea()==null?"":t.getApproveIdea());           
            return workPlanService.check(t,variables);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObj;
    }
	/**
     *  跳转到审批页面
     */
    @RequestMapping("/approve/{id}/{type}")
    public ModelAndView approve(HttpServletRequest request, @PathVariable Long id, @PathVariable String type){
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("type", type);
        // 返回前台数据项
        SysUserEntity userEntity= RequestContext.get().getUser();
        model.put("userEntity", userEntity);
        WorkPlanEntity workPlanEntity = (WorkPlanEntity)workPlanService.findById(id);
        SysUnitEntity sysUnitEntity=sysUnitService.findById((long)workPlanEntity.getUnitId());
        if(sysUnitEntity!=null){
            workPlanEntity.setUnitName(sysUnitEntity.getName());

        }
        model.put("dataMap", workPlanEntity);
        model.put("dataMapJson", JsonUtil.toJson(workPlanEntity));
        
        //TODO下拉树具体内容根据具体业务定制
        List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();

        model.put("workPlanTreeList", JsonUtil.toJson(treeNodeList));
        ComboboxVO comboWorkPlanVO = new ComboboxVO();
        //TODO下拉框具体内容根据具体业务定制
        List<SysUserEntity> allUsers = sysUserService.findAll();
        for(SysUserEntity sysUserEntity : allUsers){
            comboWorkPlanVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("workPlanCombobox", JsonUtil.toJson(comboWorkPlanVO.getOptions()));
        //编码类型
        ComboboxVO timeDimensionCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("TIME_DIMENSION");       
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            timeDimensionCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("timeDimensionCombobox", JsonUtil.toJson(timeDimensionCombobox.getOptions())); 
        //查询各个人的按钮权限 开始
        List<Condition> conditionsLc=new ArrayList<Condition>();
        conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
        conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
        conditionsLc.add(new Condition("task.end_time_ IS NULL"));
        List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditionsLc, null);
        TodoTaskEntity todoTaskEntity=null;
        if(!list.isEmpty()){
            todoTaskEntity=list.get(0);
            List<ProcessNodeAuthEntity> nodeList=processNodeAuthService.getAuthorityList(todoTaskEntity.getTaskDefKey());
            model.put("nodeList", nodeList);
        }
//查询各个人的按钮权限 结束
        return this.createModelAndView("run/workPlan/workPlanCheck", model);
    }
    /**
     *  主任审批通过
     */
    @RequestMapping("/pass/{id}")
    public @ResponseBody ResultObj pass(HttpServletRequest request,@RequestBody WorkPlanEntity t){
        Map<String, Object> params=new HashMap<String, Object>();
        params.put("status", ProtectStatusEnum.FINISH.getCode());
        params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
        params.put(BranchMarkEnum.BRANCH_KEY.getName(), ProtectResultEnum.AGREE.getId());
        params.put(ExamMarkEnum.COMMENT.getCode(), ProtectResultEnum.AGREE.getName());
        params.put(ExamMarkEnum.RESULT.getCode(), "");
        return workPlanService.check(t,params);

    }
    /**
    * @Title: noPass
    * @Description: 主任审批不通过
    * @author sunliang
    * @date 下午4:08:23
    * @param t
    * @return
    * @throws
    */
    @RequestMapping("/noPass/{id}")
    public @ResponseBody ResultObj noPass(@RequestBody WorkPlanEntity t){
        Map<String, Object> params=new HashMap<String, Object>();
        params.put("status", ProtectStatusEnum.ZRREJECT.getCode());
        SysUserEntity fzrEntity=sysUserService.findById(new Long(t.getPlanPersonId()));
        params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
          params.put(BranchMarkEnum.BRANCH_KEY.getName(), ProtectResultEnum.BACK_END.getId());
        params.put(ExamMarkEnum.COMMENT.getCode(), ProtectResultEnum.BACK_END.getName());
        params.put(ExamMarkEnum.RESULT.getCode(), "");
        return  workPlanService.check(t, params);
    }
    /**
    * @Title: cancel
    * @Description: 流程取消
    * @author sunliang
    * @date 2017年8月8日下午2:13:46
    * @param request
    * @param t
    * @return
    * @throws
    */
    @RequestMapping("/cancel/{id}")
    public @ResponseBody ResultObj cancel(HttpServletRequest request,@RequestBody WorkPlanEntity t){
        ResultObj resultObj = new ResultObj();
        try {
            String selectUser=request.getParameter("selectUser");
            Map<String, Object> taskVariables=new HashMap<String, Object>();
            taskVariables.put("status", ProtectStatusEnum.CANCEL.getCode());            
            taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
            taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
            taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
            taskVariables.put(ExamMarkEnum.COMMENT.getCode(),t.getApproveIdea()==null?"":t.getApproveIdea());//审批意见
            workPlanService.check(t,taskVariables);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObj;
    }
    /**
    * @Title: getDetail
    * @Description: 进入详细页
    * @author sunliang
    * @date 2017年6月27日下午7:14:51
    * @param request
    * @param id
    * @return
    * @throws
    */
    @RequestMapping("/getDetail/{id}")
    public ModelAndView getDetail(HttpServletRequest request, @PathVariable Long id){
        Map<String, Object> model = new HashMap<String, Object>();
        // 返回前台数据项
        WorkPlanEntity workPlanEntity = (WorkPlanEntity)workPlanService.findById(id);
        SysUnitEntity sysUnitEntity=sysUnitService.findById((long)workPlanEntity.getUnitId());
        if(sysUnitEntity!=null){
            workPlanEntity.setUnitName(sysUnitEntity.getName());

        }
        model.put("dataMap", workPlanEntity);
        model.put("dataMapJson", JsonUtil.toJson(workPlanEntity));
        
        //TODO下拉树具体内容根据具体业务定制
        List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();

        model.put("workPlanTreeList", JsonUtil.toJson(treeNodeList));
        ComboboxVO comboWorkPlanVO = new ComboboxVO();
        //TODO下拉框具体内容根据具体业务定制
           List<SysUserEntity> allUsers = sysUserService.findAll();
            for(SysUserEntity sysUserEntity : allUsers){
                comboWorkPlanVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
            }
        model.put("workPlanCombobox", JsonUtil.toJson(comboWorkPlanVO.getOptions()));
        //编码类型
        ComboboxVO timeDimensionCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("TIME_DIMENSION");       
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            timeDimensionCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("timeDimensionCombobox", JsonUtil.toJson(timeDimensionCombobox.getOptions())); 
        
        return this.createModelAndView("run/workPlan/workPlanDetail", model);
    }
    /**
    * @Title: selectWorkPlan
    * @Description: 选择定期工作计划
    * @author sunliang
    * @date 2017年7月21日下午5:22:33
    * @param request
    * @return
    * @throws
    */
    @RequestMapping("/selectWorkPlan")
    public ModelAndView selectWorkPlan(HttpServletRequest request){
        Map<String, Object> model = new HashMap<String, Object>();
        List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
        //TODO下拉树具体内容根据具体业务定制
        model.put("workPlanTreeList", JsonUtil.toJson(treeNodeList));
        ComboboxVO comboWorkPlanVO = new ComboboxVO();
        //TODO下拉框具体内容根据具体业务定制
        List<SysUserEntity> allUsers = sysUserService.findAll();
        for(SysUserEntity sysUserEntity : allUsers){
            comboWorkPlanVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        //TODO下拉框具体内容根据具体业务定制
        model.put("workPlanCombobox", JsonUtil.toJson(comboWorkPlanVO.getOptions()));   
        //编码类型
        ComboboxVO timeDimensionCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("TIME_DIMENSION");
        
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            timeDimensionCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("timeDimensionCombobox", JsonUtil.toJson(timeDimensionCombobox.getOptions()));  
        return new ModelAndView("run/workPlan/selectWorkPlanList",model);
    }
    /**
    * @Title: batchDelete
    * @Description: 批量删除
    * @author sunliang
    * @date 2017年8月3日下午3:12:01
    * @param ids
    * @return
    * @throws Exception
    * @throws
    */
    @RequestMapping(value = "/batchDelete")
    public @ResponseBody ResultObj batchDelete(@RequestBody List<Integer> ids)throws Exception {
        ResultObj resultObj = new ResultObj();
        for (Integer id : ids) {
            long longId = (long) id;
            WorkPlanEntity t = workPlanService.findById(longId);
            if (t != null) {                
                workPlanService.delete(longId);              
            }
        }
        return resultObj;
    }
}