package com.aptech.business.companyTrends.web;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.OAManagement.dispatchManagement.util.SortUtil;
import com.aptech.business.companyTrends.domain.CompanyTrendsEntity;
import com.aptech.business.companyTrends.service.CompanyTrendsService;
import com.aptech.business.companyTrendsReadUser.domain.CompanyTrendsReadUserEntity;
import com.aptech.business.companyTrendsReadUser.service.CompanyTrendsReadUserService;
import com.aptech.business.component.dictionary.CompanyTrendsStatusEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.common.workflow.definition.domain.DefinitionEntity;
import com.aptech.common.workflow.definition.service.DefinitionService;
import com.aptech.common.workflow.modelEditor.domain.BranchMarkEnum;
import com.aptech.common.workflow.modelEditor.service.NodeConfigService;
import com.aptech.common.workflow.processNodeAuth.domain.ProcessNodeAuthEntity;
import com.aptech.common.workflow.processNodeAuth.service.ProcessNodeAuthService;
import com.aptech.common.workflow.todoTask.domain.ExamMarkEnum;
import com.aptech.common.workflow.todoTask.domain.TodoTaskEntity;
import com.aptech.common.workflow.todoTask.service.TodoTaskService;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 公司动态配置控制器
 *
 * @author 
 * @created 2018-04-03 11:20:52
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/companyTrends")
public class CompanyTrendsController extends BaseController<CompanyTrendsEntity> {
	
	@Autowired
	private CompanyTrendsService companyTrendsService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Autowired
	private DefinitionService definitionService;
	@Autowired
	private TodoTaskService todoTaskService;
	@Autowired
	private ProcessNodeAuthService processNodeAuthService;
	
	@Autowired
	CompanyTrendsReadUserService companyTrendsReadUserService;
	
	@Autowired
	private OperateLogService operateLogService;
	
	@Override
	public IBaseEntityOperation<CompanyTrendsEntity> getService() {
		return companyTrendsService;
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
		//状态下拉
		ComboboxVO codeDateTypesCombobox = new ComboboxVO();
		for(CompanyTrendsStatusEnum key : CompanyTrendsStatusEnum.values()){
			codeDateTypesCombobox.addOption(key.getCode(), key.getName());
		}
		model.put("companyTrendsTypeCombobox", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		return this.createModelAndView("companyTrends/companyTrendsList", model);
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
		
		return this.createModelAndView("companyTrends/companyTrendsAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		CompanyTrendsEntity companyTrendsEntity = (CompanyTrendsEntity)companyTrendsService.findById(id);
		model.put("entity", companyTrendsEntity);
		model.put("entityJson", JsonUtil.toJson(companyTrendsEntity));
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		
		return this.createModelAndView("companyTrends/companyTrendsEdit", model);
	}
	/**
	 *	跳转到查看页面
	 */
	@RequestMapping("/getshowPage/{id}")
	public ModelAndView getShowPage(HttpServletRequest request,@PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		CompanyTrendsEntity companyTrendsEntity = (CompanyTrendsEntity)companyTrendsService.findById(id);
		model.put("entity", companyTrendsEntity);
		model.put("entityJson", JsonUtil.toJson(companyTrendsEntity));

		return this.createModelAndView("companyTrends/companyTrendsDetail", model);
	}
	/**
	 *	跳转到预览页面
	 */
	@RequestMapping("/getPreviewPage/{id}")
	public ModelAndView getPreviewPage(HttpServletRequest request,@PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		CompanyTrendsEntity companyTrendsEntity = (CompanyTrendsEntity)companyTrendsService.findById(id);
		model.put("entity", companyTrendsEntity);
		model.put("entityJson", JsonUtil.toJson(companyTrendsEntity));
		
		//获取登录人信息
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		List<Condition> conditions = new ArrayList<Condition>(); 
		conditions.add(new Condition("C_COMPANY_TRENDS_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ, id));
		conditions.add(new Condition("C_READ_USER_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ, sysUserEntity.getId()));
		List<CompanyTrendsReadUserEntity> readDataList = companyTrendsReadUserService.findByCondition(conditions, null);
		if (readDataList == null || readDataList.size() == 0) {
			CompanyTrendsReadUserEntity readUserEntity = new CompanyTrendsReadUserEntity();
			readUserEntity.setCompanyTrendsId(id);
			readUserEntity.setUserId(sysUserEntity.getId());
			readUserEntity.setUserName(sysUserEntity.getName());
			this.companyTrendsReadUserService.addEntity(readUserEntity);
		}
		
		
		return this.createModelAndView("companyTrends/companyTrendsPreview", model);
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
	public @ResponseBody ResultObj saveEditPage(@RequestBody CompanyTrendsEntity companyTrendsEntity){
		ResultObj resultObj = new ResultObj();
		DateFormatUtil format = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		Date publishDate = cal.getTime();
		try {
			publishDate =  format.parse(companyTrendsEntity.getPublishTimeStr());
		} catch (Exception e) {
			
		}
		Date endDate = cal.getTime();
		try {
			endDate =  format.parse(companyTrendsEntity.getEndTimeStr());
		} catch (Exception e) {
			
		}
		companyTrendsEntity.setPublishTime(publishDate);
		companyTrendsEntity.setEndTime(endDate);
		
		companyTrendsService.updateEntity(companyTrendsEntity);	
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.COMPANY_TRENDS.getName(), OperateUserEnum.EDIT.getName(), "", OperateUserEnum.EDIT.getId());
		return resultObj;
	}
	
	/** 批量删除
	 * @author         zhangzq 
	 * @Date           2017年6月20日 下午3:34:59 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/bulkDelete/{ids}", method = RequestMethod.DELETE)
	public @ResponseBody ResultObj bulkDelete(@RequestBody List<Integer> ids) {
		ResultObj resultObj = new ResultObj();
		companyTrendsService.deleteBulk(ids);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.COMPANY_TRENDS.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
		return resultObj;
	}
	
	
	/**
	* 提交前查询提交人
	* @author ly
	* @date 2018年4月11日 下午2:49:19 
	* @param request
	* @return
	* @return ModelAndView
	*/
	@RequestMapping("/sureSubmitPerson")
	public ModelAndView sureSubmitPerson(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,
				ProcessMarkEnum.TECHNICAL_COMPANYTRENDS.getName()));
		List<DefinitionEntity> defList=definitionService.findByCondition(conditions, null);
		
		if(!defList.isEmpty()){
			String modelId=defList.get(0).getModelId();
			//审批页面点击签发按钮的时候，把下一步的人查询出来
			SysUserEntity userEntity = RequestContext.get().getUser();
			List<SysUserEntity> userList= nodeConfigService.getFirstNodeTransactor(modelId,"",userEntity);
			model.put("userList", userList);
		}
		return this.createModelAndView("companyTrends/sureSubmitPerson", model);
	}
	
	
	/**
	* 提交操作
	* @author ly
	* @date 2018年4月11日 下午3:37:29 
	* @param request
	* @param id
	* @param params
	* @return
	* @return ResultObj
	*/
	@RequestMapping(value = "/submit/{id}")
	public @ResponseBody
	ResultObj submit(HttpServletRequest request,@PathVariable Long id,@RequestBody Map<String, Object> params) {
		return companyTrendsService.submit(id,params);
	}
	
	
	/**
	* 公司动态流程审批页面
	* @author ly
	* @date 2018年4月12日 上午9:08:58 
	* @param request
	* @param id
	* @param type
	* @return
	* @throws ParseException
	* @return ModelAndView
	*/
	@RequestMapping("/approve/{id}/{type}")
	public ModelAndView approve(HttpServletRequest request, @PathVariable Long id
			,@PathVariable String type) throws ParseException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("type",type);
		//查询各个人的按钮权限 开始
		SysUserEntity userEntity= RequestContext.get().getUser();
		resultMap.put("userEntity", userEntity);
		List<Condition> conditionsLc=new ArrayList<Condition>();
		conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
		conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.TECHNICAL_COMPANYTRENDS.getName()));
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
		CompanyTrendsEntity companyTrendsEntity = (CompanyTrendsEntity)companyTrendsService.findById(id);
		resultMap.put("entity", companyTrendsEntity);
		resultMap.put("entityJson", JsonUtil.toJson(companyTrendsEntity));
  		
		return this.createModelAndView("companyTrends/companyTrendsApprove", resultMap);
	}
	
	/**
	* 把下一流程节点党群部人员查出来
	* @author ly
	* @date 2018年4月12日 上午10:30:11 
	* @param request
	* @param taskId
	* @return
	* @return ModelAndView
	*/
	@RequestMapping("/submitPersonLeader/{taskId}")
	public ModelAndView submitPersonLeader(HttpServletRequest request,@PathVariable String taskId) {
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,CompanyTrendsStatusEnum.MONITOR.getCode());
		model.put("userList", userList);
		return this.createModelAndView("companyTrends/approveSureSubmitPerson", model);
	}
	
	/**
	* 需要党群部审核
	* @author ly
	* @date 2018年4月12日 下午1:33:03 
	* @param equipAbnormalEntity
	* @param flag
	* @return
	* @return ResultObj
	*/
	@RequestMapping("/leader")
	public @ResponseBody ResultObj leader(@RequestBody CompanyTrendsEntity companyTrendsEntity){
		CompanyTrendsEntity entity = companyTrendsService.findById(companyTrendsEntity.getId());
		entity.setTaskId(companyTrendsEntity.getTaskId());
		entity.setProcInstId(companyTrendsEntity.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), CompanyTrendsStatusEnum.MONITOR.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), companyTrendsEntity.getApproveIdea());
//		params.put("processStatus",CompanyTrendsStatusEnum.FOREMANCONFIRM.getCode());
//		params.put("result",CompanyTrendsStatusEnum.FOREMANCONFIRM.getName());
		return companyTrendsService.approve(entity, params);
	}
	
	/**
	* 驳回
	* @author ly
	* @date 2018年4月12日 下午2:06:15 
	* @param equipAbnormalEntity
	* @param flag
	* @return
	* @return ResultObj
	*/
	@RequestMapping("/reject/{flag}")
	public @ResponseBody ResultObj reject(@RequestBody CompanyTrendsEntity companyTrendsEntity,@PathVariable Long flag){
		CompanyTrendsEntity entity = companyTrendsService.findById(companyTrendsEntity.getId());
		entity.setTaskId(companyTrendsEntity.getTaskId());
		entity.setProcInstId(companyTrendsEntity.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), CompanyTrendsStatusEnum.GUARDIAN.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), companyTrendsEntity.getApproveIdea());
		if(flag==1){
			params.put("processStatus",CompanyTrendsStatusEnum.IMPLEMENT.getCode());
			params.put("result",CompanyTrendsStatusEnum.IMPLEMENT.getName());
		}else{
//			params.put("processStatus",CompanyTrendsStatusEnum.FOREMANREJECT.getCode());
//			params.put("result",CompanyTrendsStatusEnum.FOREMANREJECT.getName());
		}
		return companyTrendsService.approve(entity, params);
	}
	
	/**
	 * @Description: 选择审批人(再提交专用)
	 * @author wangcc
	 * @Date 2017年8月18日 下午18:11:30
	 * @throws Exception
	 */
	@RequestMapping("/submitPersonLeaderForProposer/{taskId}/{processStatus}")
	public ModelAndView submitPersonLeaderForProposer(HttpServletRequest request,@PathVariable String taskId,@PathVariable String processStatus) {
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,CompanyTrendsStatusEnum.PENDING.getCode());
		model.put("userList", userList);
		model.put("processStatus", processStatus);
		return this.createModelAndView("companyTrends/sureSubmitPerson", model);
	}
	
	/**
	 * @Description:   再次提交
	 * @author         wangcc 
	 * @Date           2017年8月22日 上午9:40:23 
	 * @throws         Exception
	 */
	@RequestMapping("/submitAgain")
	public @ResponseBody ResultObj submitAgain(@RequestBody CompanyTrendsEntity companyTrendsEntity){
		CompanyTrendsEntity entity = companyTrendsService.findById(companyTrendsEntity.getId());
		entity.setTaskId(companyTrendsEntity.getTaskId());
		entity.setProcInstId(companyTrendsEntity.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ExamMarkEnum.COMMENT.getCode(), companyTrendsEntity.getApproveIdea());
		params.put("processStatus",CompanyTrendsStatusEnum.FOREMANEXAMINE.getCode());
		params.put("result",CompanyTrendsStatusEnum.FOREMANEXAMINE.getName());
		return companyTrendsService.approve(entity, params);
	}
	
	/**
	 * @Description:   流程结束
	 * @author         wangcc 
	 * @Date           2017年8月22日 上午9:40:03 
	 * @throws         Exception
	 */
	@RequestMapping("/success")
	public @ResponseBody ResultObj success(@RequestBody CompanyTrendsEntity companyTrendsEntity){
		CompanyTrendsEntity entity = companyTrendsService.findById(companyTrendsEntity.getId());
		entity.setTaskId(companyTrendsEntity.getTaskId());
		entity.setProcInstId(companyTrendsEntity.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), CompanyTrendsStatusEnum.PENDING.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("processStatus",CompanyTrendsStatusEnum.RESULTS.getCode());
		params.put("result",CompanyTrendsStatusEnum.RESULTS.getName());
		return companyTrendsService.approve(entity, params);
	}
	
	/**
	* 公司动态首页信息展示
	* @author ly
	* @date 2018年4月17日 上午9:46:33 
	* @param request
	* @param params
	* @return
	* @return ResultListObj
	*/
	@RequestMapping(value = "/companyTrendsMessList")
	public  @ResponseBody ResultListObj messList(HttpServletRequest request,@RequestBody Map<String, Object> params){
		
		//置顶公司动态数据IdList
		List<String> setTopTompanyTrendIdList = new ArrayList<String>();
		//置顶数据查询
		List<Condition> condition = new ArrayList<Condition>(); 
		Page<CompanyTrendsEntity> page = new Page<CompanyTrendsEntity>();
		page.addOrder(Sort.desc("C_SET_TOP_TIME"));
		page.setPageSize(5);
		condition.add(new Condition("C_SET_TOP", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
		condition.add(new Condition("C_STATUS",FieldTypeEnum.STRING,MatchTypeEnum.EQ,CompanyTrendsStatusEnum.GUARDIAN.getCode()));
		List<CompanyTrendsEntity> topDataList = companyTrendsService.findByCondition("findFirstMassageByCondition",condition, page);
		//非置顶数据
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("C_STATUS",FieldTypeEnum.STRING,MatchTypeEnum.EQ,CompanyTrendsStatusEnum.GUARDIAN.getCode()));
		List<CompanyTrendsEntity> messList = companyTrendsService.findByCondition("findFirstMassageByCondition",conditions, null);
		
		//返回数据list
		List<CompanyTrendsEntity> returnDataList = new ArrayList<CompanyTrendsEntity>();
		
		//获取登录人信息
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		condition.clear();
		condition.add(new Condition("C_READ_USER_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ, sysUserEntity.getId()));
		List<CompanyTrendsReadUserEntity> readDataList = companyTrendsReadUserService.findByCondition(condition, null);
		//置顶排序数据list
		List<CompanyTrendsEntity> sortTopDataList = new ArrayList<CompanyTrendsEntity>();
		if (topDataList != null && topDataList.size() >0) {
			if (readDataList != null && readDataList.size() >0) {
				for (CompanyTrendsEntity entity : topDataList) {
					setTopTompanyTrendIdList.add(entity.getId().toString());
					boolean isRead = false;
					for (CompanyTrendsReadUserEntity readUserEntity : readDataList) {
						if (entity.getId().intValue() == readUserEntity.getCompanyTrendsId().intValue()) {
							isRead = true;
							break;
						} 
					}
					if (isRead) {
						entity.setReadFlag("1");
					} else {
						entity.setReadFlag("0");
					}
					sortTopDataList.add(entity);			}
			} else {
				for (CompanyTrendsEntity entity : topDataList) {
					entity.setReadFlag("0");
					sortTopDataList.add(entity);
					setTopTompanyTrendIdList.add(entity.getId().toString());
				}
			}
			Collections.sort(sortTopDataList, new SortUtil () {
				@Override
				public int compare(Object obj1, Object obj2) {  
					CompanyTrendsEntity entity1 = (CompanyTrendsEntity)obj1;
					CompanyTrendsEntity entity2 = (CompanyTrendsEntity)obj2;
					
					return entity1.getReadFlag().compareTo(entity2.getReadFlag());
				}
			});
			for (CompanyTrendsEntity entity : sortTopDataList) {
				returnDataList.add(entity);
			}
		}
		//非置顶排序数据list
		List<CompanyTrendsEntity> unSortTopDataList = new ArrayList<CompanyTrendsEntity>();
		if (messList != null && messList.size() >0) {
			if (readDataList != null && readDataList.size() >0) {
				for (CompanyTrendsEntity entity : messList) {
					if (!setTopTompanyTrendIdList.contains(entity.getId().toString())) {
						boolean isRead = false;
						for (CompanyTrendsReadUserEntity readUserEntity : readDataList) {
							if (entity.getId().intValue() == readUserEntity.getCompanyTrendsId().intValue()) {
								isRead = true;
								break;
							} 
						}
						if (isRead) {
							entity.setReadFlag("1");
						} else {
							entity.setReadFlag("0");
						}
						unSortTopDataList.add(entity);	
					}
				}
			} else {
				for (CompanyTrendsEntity entity : messList) {
					if (!setTopTompanyTrendIdList.contains(entity.getId().toString())) {
						entity.setReadFlag("0");
						unSortTopDataList.add(entity);
					}
				}
			}
			Collections.sort(unSortTopDataList, new SortUtil () {
				@Override
				public int compare(Object obj1, Object obj2) {  
					CompanyTrendsEntity entity1 = (CompanyTrendsEntity)obj1;
					CompanyTrendsEntity entity2 = (CompanyTrendsEntity)obj2;
					
					return entity1.getReadFlag().compareTo(entity2.getReadFlag());
				}
			});
			for (CompanyTrendsEntity entity : unSortTopDataList) {
				returnDataList.add(entity);
			}
		}
		
		//获得返回结果
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (returnDataList != null) {
			resultObj.setData(returnDataList);
		}
		return resultObj;
	}
	
	
	/**
	* 取消发布
	* @author ly
	* @date 2018年4月17日 下午1:09:38 
	* @param companyTrendsEntity
	* @return
	* @return ResultObj
	*/
	@RequestMapping("/cancelPublish/{id}")
	public @ResponseBody ResultObj cancelPublish(HttpServletRequest request,@PathVariable Long id){
		ResultObj resultObj = new ResultObj();
		CompanyTrendsEntity companyTrendsEntity = (CompanyTrendsEntity)companyTrendsService.findById(id);
		companyTrendsEntity.setStatus("3");
		companyTrendsService.updateEntity(companyTrendsEntity);	
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
 		List<CompanyTrendsEntity> dataList=companyTrendsService.findByCondition(params, null);
 		int i= 1;
 		for(CompanyTrendsEntity companyTrendsEntity: dataList){
 			companyTrendsEntity.setNumber(i++);
 		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);

		ExcelUtil.export(req, res, "公司动态报表模板.xlsx","公司动态报表.xlsx", resultMap);

	}
	
	/**
	* 置顶
	*/
	@RequestMapping("/setTop/{id}")
	public @ResponseBody ResultObj setTop(HttpServletRequest request, @PathVariable Long id){
		CompanyTrendsEntity companyTrendsEntity = companyTrendsService.findById(id);
		companyTrendsEntity.setSetTop("1");
		companyTrendsEntity.setSetTopDate(new Date());
		companyTrendsService.updateEntity(companyTrendsEntity);	
		return new ResultObj();
	}
	/**
	 * 取消置顶
	 */
	@RequestMapping("/cancelTop/{id}")
	public @ResponseBody ResultObj cancelTop(HttpServletRequest request, @PathVariable Long id){
		CompanyTrendsEntity companyTrendsEntity = companyTrendsService.findById(id);
		companyTrendsEntity.setSetTop("0");
		companyTrendsEntity.setSetTopDate(new Date());
		companyTrendsService.updateEntity(companyTrendsEntity);	
		return new ResultObj();
	}
	
	/**
	 * 添加
	 * 
	 * @Title: add
	 * @Description:
	 * @param T
	 * @return
	 */
	@RequestMapping(value = "/addEntity", method = RequestMethod.POST)
	public @ResponseBody ResultObj addEntity(@RequestBody CompanyTrendsEntity companyTrendsEntity, HttpServletRequest request) {
		
		DateFormatUtil format = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		Date publishDate = cal.getTime();
		try {
			publishDate =  format.parse(companyTrendsEntity.getPublishTimeStr());
		} catch (Exception e) {
			
		}
		Date endDate = cal.getTime();
		try {
			endDate =  format.parse(companyTrendsEntity.getEndTimeStr());
		} catch (Exception e) {
			
		}
		companyTrendsEntity.setPublishTime(publishDate);
		companyTrendsEntity.setEndTime(endDate);
		this.companyTrendsService.addEntity(companyTrendsEntity);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.COMPANY_TRENDS.getName(), OperateUserEnum.ADD.getName(), "", OperateUserEnum.ADD.getId());
		
		ResultObj resultObj = new ResultObj();
		resultObj.setData(companyTrendsEntity);
		return resultObj;
	}
	
	/**
	 * @Description: 删除
	 * @param id 删除对象id
	 * @return
	 */
	@RequestMapping(value = "/deleteOne/{id}")
	public @ResponseBody
	ResultObj deleteOne( @PathVariable Long id) {
		ResultObj resultObj = new ResultObj();
		companyTrendsService.deleteEntity(id);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.COMPANY_TRENDS.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
		return resultObj;
	}
}