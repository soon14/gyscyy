package com.aptech.business.mobile.ticketManager.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.WorkBtnTypeEnum;
import com.aptech.business.component.dictionary.WorkSafeTypeEnum;
import com.aptech.business.component.dictionary.WorkSpResultEnum;
import com.aptech.business.mobile.util.MobileUtil;
import com.aptech.business.mobile.util.TokenUtil;
import com.aptech.business.orgaApp.domain.OrgaAppEntity;
import com.aptech.business.orgaApp.service.OrgaAppService;
import com.aptech.business.ticketManage.controlCardRisk.service.ControlCardRiskService;
import com.aptech.business.ticketManage.workControlCard.service.WorkControlCardService;
import com.aptech.business.ticketManage.workElectricTwo.domain.WorkElectricTwoEntity;
import com.aptech.business.ticketManage.workElectricTwo.service.WorkElectricTwoService;
import com.aptech.business.ticketManage.workSafe.domain.WorkSafeEntity;
import com.aptech.business.ticketManage.workSafe.service.WorkSafeService;
import com.aptech.business.ticketManage.workTicketTwo.domain.WorkTicketTwoEntity;
import com.aptech.business.ticketManage.workTicketTwo.service.WorkTicketTwoService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.dutiesdetail.domain.SysDutiesDetailEntity;
import com.aptech.common.system.dutiesdetail.service.SysDutiesDetailService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.system.userUnit.domain.UserUnitRelEntity;
import com.aptech.common.system.userUnit.service.UserUnitRelService;
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
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.util.StringUtil;

/**
 * 
 * 电气第二种工作票的接口
 *
 * @author 
 * @created 2017-11-01 10:50:39
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/mobile/workTicketTwo")
public class MobileWorkTicketTwoController extends BaseController<WorkTicketTwoEntity> {
	
	@Autowired
	private WorkTicketTwoService workTicketTwoService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private OrgaAppService orgaAppService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private WorkElectricTwoService workElectricTwoService;
	@Autowired
	private WorkControlCardService workControlCardService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Autowired
	private DefinitionService definitionService;
	@Autowired
	private TodoTaskService todoTaskService;
	@Autowired
	private ProcessNodeAuthService processNodeAuthService;
	@Autowired
	private WorkSafeService workSafeService;
	@Autowired
	private ControlCardRiskService controlCardRiskService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private SysDutiesDetailService sysDutiesDetailService;
	@Autowired
	private UserUnitRelService userUnitRelService;
	@Override
	public IBaseEntityOperation<WorkTicketTwoEntity> getService() {
		return workTicketTwoService;
	}
	
	/**
	 *	查询审批页面的  接口 zzq  20171027
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getElectricInfo")
	public @ResponseBody String  getElectricOneInfo(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		Map<String,Object> map=JsonUtil.getMapFromJson(json);
		
		List<Map<String, Object>> listmap=(List<Map<String, Object>>)map.get("workList");
		String workId="";
		String key="";
		String procInstId="";
		String taskId="";
		if(!listmap.isEmpty()){
			 Map<String, Object> mapp= listmap.get(0);
			 workId=mapp.get("businessKey_").toString();
			 key=mapp.get("key_").toString();
			 procInstId=mapp.get("procInstId").toString();
			 taskId=mapp.get("id_").toString();
		}
		
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
				Map<String, Object> resultMapTicket = new HashMap<String, Object>();
				resultMap.put("type","dealing");
				//查询各个人的按钮权限 开始
				List<Condition> conditionsLc=new ArrayList<Condition>();
				conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
				conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, workId));
				conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, key));
				conditionsLc.add(new Condition("task.end_time_ IS NULL"));
				List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditionsLc, null);
				TodoTaskEntity todoTaskEntity=null;
				if(!list.isEmpty()){
					todoTaskEntity=list.get(0);
					List<ProcessNodeAuthEntity> nodeList=processNodeAuthService.getAuthorityList(todoTaskEntity.getTaskDefKey());
					resultMapTicket.put("nodeList", nodeList);
				}
				//查询各个人的按钮权限 结束
				// 返回前台数据项
				WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(Long.valueOf(workId));
				
				SysUnitEntity sysUnitEntity =sysUnitService.findById(Long.valueOf(workTicketTwoEntity.getUnitNameId()));
				workTicketTwoEntity.setUnitName(sysUnitEntity.getName());
				
				OrgaAppEntity orgaEntity = orgaAppService.findById(Long.valueOf(workTicketTwoEntity.getGroupId()));
				workTicketTwoEntity.setGroupName(orgaEntity.getName());//
				
				resultMapTicket.put("workTicketTwoEntity", workTicketTwoEntity);
				
				List<Condition> conditions=new ArrayList<Condition>();
		        //查询电气一开始
		        conditions.clear();
		  		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, Long.valueOf(workId)));
		  		WorkElectricTwoEntity workElectricTwoEntity=new WorkElectricTwoEntity();
		  		List<WorkElectricTwoEntity> workElectricList=workElectricTwoService.findByCondition(conditions, null);
		  		String electricId="";
		        if(!workElectricList.isEmpty()){
			          workElectricTwoEntity=workElectricList.get(0);
			          resultMapTicket.put("workElectricTwoEntity", workElectricTwoEntity);
			          electricId=workElectricTwoEntity.getId().toString();
		          }else{
		        	  resultMapTicket.put("workElectricTwoEntity", workElectricTwoEntity);
		        	  electricId="";
		        }
		        //查询电气一结束
				//查询安全措施
		        conditions.clear();
		        conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, Long.valueOf(workId)));
		        conditions.add(new Condition("C_SAFE_TYPE", FieldTypeEnum.INT, MatchTypeEnum.EQ, WorkSafeTypeEnum.SAFETHREE.getCode()));
		        List<WorkSafeEntity> workSafeOneList=workSafeService.findByCondition(conditions, null);
		        resultMapTicket.put("workSafeOneList", workSafeOneList);
		        conditions.clear();
		        conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, Long.valueOf(workId)));
		        conditions.add(new Condition("C_SAFE_TYPE", FieldTypeEnum.INT, MatchTypeEnum.EQ, WorkSafeTypeEnum.SAFEFIVE.getCode()));
		        List<WorkSafeEntity> workSafeTwoList=workSafeService.findByCondition(conditions, null);
		        resultMapTicket.put("workSafeTwoList", workSafeTwoList);
		        conditions.clear();
		        conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, Long.valueOf(workId)));
		        conditions.add(new Condition("C_SAFE_TYPE", FieldTypeEnum.INT, MatchTypeEnum.EQ, WorkSafeTypeEnum.SAFESIX.getCode()));
		        List<WorkSafeEntity> workSafeThreeList=workSafeService.findByCondition(conditions, null);
		        resultMapTicket.put("workSafeThreeList", workSafeThreeList);
				//查询流程明细
		        conditions.clear();
		        conditions.add(new Condition("task.proc_inst_id_", FieldTypeEnum.STRING, MatchTypeEnum.LIKE, procInstId));
		        List<TodoTaskEntity> todoTasklist = todoTaskService.findByCondition("findDetailByCondition", conditions, null);
		        resultMapTicket.put("todoTasklist", todoTasklist);
		        
		        resultMap.put("taskId", taskId);
		        resultMap.put("workId", workId);
		        resultMap.put("procInstId", procInstId);
		        resultMap.put("electricId", electricId);
		        resultMap.put("approveIdea", "");
		        resultMap.put("workDisclosure", "");//工作交底
		        resultMap.put("workPersonGroup", "");//工作人员变动
		        
				resultMap.put("statusCode", "200");
				resultMap.put("message", "调用成功");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				resultMap.put("obj", resultMapTicket);
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
	/**
	 *	签发弹出框  接口 zzq  20171027
	 * @throws ParseException 
	 */
	@RequestMapping("/getQfDetail")
	public @ResponseBody String  getQfDetail(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		JSONObject object=JSONObject.fromObject(json);
		String taskId=object.getString("taskId");
		String electricId=object.getString("electricId");
		String procInstId=object.getString("procInstId");
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
				Map<String, Object> resultMapTicket = new HashMap<String, Object>();
				List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
				resultMapTicket.put("userEntity", userEntity);
				resultMap.put("dateQf", new Date());
				resultMap.put("electricId", electricId);
				resultMap.put("procInstId", procInstId);
				resultMap.put("taskId", taskId);
				resultMap.put("selectUser", "");
				resultMap.put("approveIdea", "");
				
				resultMapTicket.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
				resultMap.put("obj", resultMapTicket);
				
				resultMap.put("statusCode", "200");
				resultMap.put("message", "调用成功");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
	/**
	 *	签发同意  接口 zzq  20171030
	 * @throws ParseException 
	 */
	@RequestMapping("/agreeQf")
	public @ResponseBody String  agreeQf(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		JSONObject object=JSONObject.fromObject(json);
		String taskId=object.getString("taskId");
		String procInstId=object.getString("procInstId");
		String electricId=object.getString("electricId");
		String selectUser=object.getString("selectUser");
		String approveIdea=object.getString("approveIdea");
		String dateQf=object.getString("dateQf");
		
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
				
				if(selectUser!=null&&!selectUser.equals("")){
					WorkElectricTwoEntity workElectricEntityYz=workElectricTwoService.findById(Long.valueOf(electricId));
					if(workElectricEntityYz!=null){
						if(workElectricEntityYz.getSignerId()!=null&&!workElectricEntityYz.getSignerId().toString().equals("")){
							resultMap.put("statusCode", "300");
							resultMap.put("message", "调用失败,签发人已经签发过了");
							resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
						}else{
							RequestContext.get().setUser(userEntity);
							WorkElectricTwoEntity workElectricTwoEntity=new WorkElectricTwoEntity();
							workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.QF.getCode());
							workElectricTwoEntity.setId(Long.valueOf(electricId));
							workElectricTwoEntity.setApproveIdea(approveIdea);
							DateFormatUtil sdf = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss");
							workElectricTwoEntity.setSignerDate(sdf.parse(dateQf));
							Map<String, Object> variables=new HashMap<String, Object>();
							variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
							variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
							variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
							variables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
							actTaskService.complete(taskId,procInstId, variables);
							workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
							
							resultMap.put("statusCode", "200");
							resultMap.put("message", "调用成功");
							resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
						}
					}
				}else{
					resultMap.put("statusCode", "300");
					resultMap.put("message", "调用失败,未传入下一级审批人");
					resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				}
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
	/**
	 *	签发不同意  接口 zzq  20171030
	 * @throws ParseException 
	 */
	@RequestMapping("/disAgreeQf")
	public @ResponseBody String  disAgreeQf(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		JSONObject object=JSONObject.fromObject(json);
		String taskId=object.getString("taskId");
		String procInstId=object.getString("procInstId");
		String electricId=object.getString("electricId");
		String approveIdea=object.getString("approveIdea");
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
					RequestContext.get().setUser(userEntity);
					WorkElectricTwoEntity workElectricTwoEntity=new WorkElectricTwoEntity();
					workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.QF.getCode());
					workElectricTwoEntity.setId(Long.valueOf(electricId));
					workElectricTwoEntity.setApproveIdea(approveIdea);
					//查询工作负责人的loginName
					WorkElectricTwoEntity workElectric = workElectricTwoService.findById(workElectricTwoEntity.getId());
					WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(workElectric.getWorkticketId());
					SysUserEntity fzrEntity=sysUserService.findById(workTicketTwoEntity.getGuarderId());
					//查询工作负责人的loginName
					Map<String, Object> taskVariables=new HashMap<String, Object>();
					taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
					taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
					taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
					taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
					actTaskService.complete(taskId,procInstId,taskVariables);
					workElectricTwoService.updateSpnrDisagree(workElectricTwoEntity,userEntity);
					
					resultMap.put("statusCode", "200");
					resultMap.put("message", "调用成功");
					resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
	/**
	 *	收票人弹出框  接口 zzq  20171030
	 * @throws ParseException 
	 */
	@RequestMapping("/getSpDetail")
	public @ResponseBody String  getSpDetail(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		JSONObject object=JSONObject.fromObject(json);
		String taskId=object.getString("taskId");
		String electricId=object.getString("electricId");
		String procInstId=object.getString("procInstId");
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
				Map<String, Object> resultMapTicket = new HashMap<String, Object>();
				List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
				resultMapTicket.put("userEntity", userEntity);
				resultMap.put("dateQf", new Date());
				resultMap.put("electricId", electricId);
				resultMap.put("procInstId", procInstId);
				resultMap.put("taskId", taskId);
				resultMap.put("userId", userEntity.getId().toString());
				resultMap.put("selectUser", "");
				resultMap.put("approveIdea", "");
				
				resultMapTicket.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
				resultMap.put("obj", resultMapTicket);
				
				resultMap.put("statusCode", "200");
				resultMap.put("message", "调用成功");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
	/**
	 * 收票同意  接口 zzq  20171030
	 * @throws ParseException 
	 */
	@RequestMapping("/agreeSp")
	public @ResponseBody String  agreeSp(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		JSONObject object=JSONObject.fromObject(json);
		String taskId=object.getString("taskId");
		String procInstId=object.getString("procInstId");
		String electricId=object.getString("electricId");
		String selectUser=object.getString("selectUser");
		String approveIdea=object.getString("approveIdea");
		String userId=object.getString("userId");
		String dateQf=object.getString("dateQf");
		
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
				
				if(selectUser!=null&&!selectUser.equals("")){
					WorkElectricTwoEntity workElectricEntityYz=workElectricTwoService.findById(Long.valueOf(electricId));
					if(workElectricEntityYz!=null){
						if(workElectricEntityYz.getOndutyId()!=null&&!workElectricEntityYz.getOndutyId().toString().equals("")){
							resultMap.put("statusCode", "300");
							resultMap.put("message", "调用失败,收票人已经收票了");
							resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
						}else{
							RequestContext.get().setUser(userEntity);
							SysUserEntity userEntityChuan=sysUserService.findById(Long.valueOf(userId));
							WorkElectricTwoEntity workElectricTwoEntity=new WorkElectricTwoEntity();
							workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.SP.getCode());
							workElectricTwoEntity.setId(Long.valueOf(electricId));
							workElectricTwoEntity.setApproveIdea(approveIdea);
							DateFormatUtil sdf = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss");
							workElectricTwoEntity.setSignerDate(sdf.parse(dateQf));
							Map<String, Object> variables=new HashMap<String, Object>();
							variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
							variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
							variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
							variables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
							actTaskService.complete(taskId,procInstId, variables);
							workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntityChuan);
							
							resultMap.put("statusCode", "200");
							resultMap.put("message", "调用成功");
							resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
						}
					}
				}else{
					resultMap.put("statusCode", "300");
					resultMap.put("message", "调用失败,未传入下一级审批人");
					resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				}
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
	/**
	 *	收票不同意  接口 zzq  20171030
	 * @throws ParseException 
	 */
	@RequestMapping("/disAgreeSp")
	public @ResponseBody String  disAgreeSp(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		JSONObject object=JSONObject.fromObject(json);
		String taskId=object.getString("taskId");
		String procInstId=object.getString("procInstId");
		String electricId=object.getString("electricId");
		String approveIdea=object.getString("approveIdea");
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
					RequestContext.get().setUser(userEntity);
					WorkElectricTwoEntity workElectricTwoEntity=new WorkElectricTwoEntity();
					workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.SP.getCode());
					workElectricTwoEntity.setId(Long.valueOf(electricId));
					workElectricTwoEntity.setApproveIdea(approveIdea);
					//查询工作负责人的loginName
					WorkElectricTwoEntity workElectric = workElectricTwoService.findById(workElectricTwoEntity.getId());
					WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(workElectric.getWorkticketId());
					SysUserEntity fzrEntity=sysUserService.findById(workTicketTwoEntity.getGuarderId());
					//查询工作负责人的loginName
					Map<String, Object> taskVariables=new HashMap<String, Object>();
					taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
					taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
					taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
					taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
					actTaskService.complete(taskId,procInstId,taskVariables);
					workElectricTwoService.updateSpnrDisagree(workElectricTwoEntity,userEntity);
					
					resultMap.put("statusCode", "200");
					resultMap.put("message", "调用成功");
					resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
	/**
	 *	废票  接口 zzq  20171030
	 * @throws ParseException 
	 */
	@RequestMapping("/disAgreeFp")
	public @ResponseBody String  disAgreeFp(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		JSONObject object=JSONObject.fromObject(json);
		String taskId=object.getString("taskId");
		String procInstId=object.getString("procInstId");
		String electricId=object.getString("electricId");
		String approveIdea=object.getString("approveIdea");
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
				RequestContext.get().setUser(userEntity);
					WorkElectricTwoEntity workElectricTwoEntity=new WorkElectricTwoEntity();
					workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.FP.getCode());
					workElectricTwoEntity.setId(Long.valueOf(electricId));
					workElectricTwoEntity.setApproveIdea(approveIdea);
					
					Map<String, Object> taskVariables=new HashMap<String, Object>();
					taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
					taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
					taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
					taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
					actTaskService.complete(taskId,procInstId,taskVariables);
					workElectricTwoService.updateSpnrDisagree(workElectricTwoEntity,userEntity);
					
					resultMap.put("statusCode", "200");
					resultMap.put("message", "调用成功");
					resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
	/**
	 *	在提交弹出框 接口 zzq  20171030
	 * @throws ParseException 
	 */
	@RequestMapping("/againSureList")
	public @ResponseBody String  againSureList(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		JSONObject object=JSONObject.fromObject(json);
		String workId=object.getString("workId");
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
				Map<String, Object> resultMapTicket = new HashMap<String, Object>();
				List<Condition> conditions = new ArrayList<Condition>();
				resultMap.put("workId", workId);
				conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.WORK_TICKET_PROCESS_KEY.getName()));
				List<DefinitionEntity> defList=definitionService.findByCondition(conditions, null);
				String modelId="";
				if(!defList.isEmpty()){
					modelId=defList.get(0).getModelId();
				}
//				SysUserEntity starter= null;
//				if(!RequestContext.get().isDeveloperMode()){
//					starter = RequestContext.get().getUser();
//				}
				List<SysUserEntity> userList= nodeConfigService.getFirstNodeTransactor(modelId,"",userEntity);
				resultMapTicket.put("userList", userList);
				resultMap.put("obj", resultMapTicket);
				resultMap.put("statusCode", "200");
				resultMap.put("message", "调用成功");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
	
	/**
	 *	再提交  接口 zzq  20171030
	 * @throws ParseException 
	 */
	@RequestMapping("/againSubmit")
	public @ResponseBody String  againSubmit(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		JSONObject object=JSONObject.fromObject(json);
		String workId=object.getString("workId");
		String taskId=object.getString("taskId");
		String procInstId=object.getString("procInstId");
		String selectUser=object.getString("selectUser");
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
				if(selectUser!=null&&!selectUser.equals("")){
					RequestContext.get().setUser(userEntity);
					WorkElectricTwoEntity workElectricTwoEntity=new WorkElectricTwoEntity();
					workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.ZTJ.getCode());
					
					Map<String, Object> variables=new HashMap<String, Object>();
					variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
					variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
					variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
					variables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
					actTaskService.complete(taskId,procInstId, variables);
					workElectricTwoEntity.setId(Long.valueOf(workId));
					workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
						
					resultMap.put("statusCode", "200");
					resultMap.put("message", "调用成功");
					resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				}else{
					resultMap.put("statusCode", "300");
					resultMap.put("message", "调用失败,未传入下一级审批人");
					resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				}
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
	/**
	 *	许可人弹出框  接口 zzq  20171030
	 * @throws ParseException 
	 */
	@RequestMapping("/getAddXk")
	public @ResponseBody String  getAddXk(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		JSONObject object=JSONObject.fromObject(json);
		String taskId=object.getString("taskId");
		String electricId=object.getString("electricId");
		String procInstId=object.getString("procInstId");
		String workId=object.getString("workId");
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
				resultMap.put("workId", workId);
				resultMap.put("taskId", taskId);
				resultMap.put("procInstId", procInstId);
				resultMap.put("electricId", electricId);
				resultMap.put("qksjZhu", "");//许可时间
				resultMap.put("wireway", "");
				resultMap.put("quarantine", "");
				resultMap.put("other", "");
				resultMap.put("otherSafe", "");
				resultMap.put("approveIdea", "");
				resultMap.put("userEntity", userEntity);
				
				resultMap.put("statusCode", "200");
				resultMap.put("message", "调用成功");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
	/**
	 * 许可同意  接口 zzq  20171030
	 * @throws ParseException 
	 */
	@RequestMapping("/agreeXk")
	public @ResponseBody String  agreeXk(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		JSONObject object=JSONObject.fromObject(json);
		String workId=object.getString("workId");
		String taskId=object.getString("taskId");
		String procInstId=object.getString("procInstId");
		String electricId=object.getString("electricId");
		String qksjZhu=object.getString("qksjZhu");
		String wireway=object.getString("wireway");
		String quarantine=object.getString("quarantine");
		String other=object.getString("other");
		String otherSafe=object.getString("otherSafe");
		String approveIdea=object.getString("approveIdea");
		
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
					WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(Long.valueOf(workId));
					if(workTicketTwoEntity!=null){
						if(workTicketTwoEntity.getChangeAllowId()!=null&&!workTicketTwoEntity.getChangeAllowId().toString().equals("")){
							resultMap.put("statusCode", "300");
							resultMap.put("message", "调用失败,许可人已经许可了");
							resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
						}else{
							if(StringUtil.isEmpty(qksjZhu)){
								resultMap.put("statusCode", "300");
								resultMap.put("message", "调用失败,许可时间不能为空");
								resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
							}else{
								RequestContext.get().setUser(userEntity);
								WorkElectricTwoEntity workElectricTwoEntity=new WorkElectricTwoEntity();
								SysUserEntity  sysUserEntity=sysUserService.findById(workTicketTwoEntity.getGuarderId());//或取这个工作负责人的实体
								workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.XK.getCode());
								workElectricTwoEntity.setId(Long.valueOf(electricId));
								workElectricTwoEntity.setApproveIdea(approveIdea);
								workElectricTwoEntity.setAllowPicPersonId(workTicketTwoEntity.getGuarderId());
								workElectricTwoEntity.setAllowPicPersonName(workTicketTwoEntity.getGuarderName());
								DateFormatUtil sdf = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss");
								workElectricTwoEntity.setQksjZhu(sdf.parse(qksjZhu));
								workElectricTwoEntity.setWireway(wireway);
								workElectricTwoEntity.setQuarantine(quarantine);
								workElectricTwoEntity.setOther(other);
								workElectricTwoEntity.setOtherSafe(otherSafe);
								
								Map<String, Object> taskVariables=new HashMap<String, Object>();
								taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
								taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
								taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
								taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
								actTaskService.complete(taskId,procInstId, taskVariables);
								workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
								
								resultMap.put("statusCode", "200");
								resultMap.put("message", "调用成功");
								resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
							}
						}
					}
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
	
	/**
	 * 安全交底  接口 zzq  20171030
	 * @throws ParseException 
	 */
	@RequestMapping("/sureGzjd")
	public @ResponseBody String  sureGzjd(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		JSONObject object=JSONObject.fromObject(json);
		String electricId=object.getString("electricId");
		String workDisclosure=object.getString("workDisclosure");//工作交底
		
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
				WorkElectricTwoEntity workElectricTwoEntity=workElectricTwoService.findById(Long.valueOf(electricId));
				workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.GZJD.getCode());
				workElectricTwoEntity.setWorkDisclosure(workDisclosure);
				workElectricTwoEntity.setId(Long.valueOf(electricId));
				workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
				
				resultMap.put("statusCode", "200");
				resultMap.put("message", "调用成功");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
	
	/**
	 * 工作人员变动  接口 zzq  20171030
	 * @throws ParseException 
	 */
	@RequestMapping("/workPersonChange")
	public @ResponseBody String  workPersonChange(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		JSONObject object=JSONObject.fromObject(json);
		String electricId=object.getString("electricId");
		String workPersonGroup=object.getString("workPersonGroup");//
		
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
				WorkElectricTwoEntity workElectricTwoEntity=workElectricTwoService.findById(Long.valueOf(electricId));
				workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.GZRYBD.getCode());
				workElectricTwoEntity.setWorkPersonGroup(workPersonGroup);
				workElectricTwoEntity.setId(Long.valueOf(electricId));
				workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
				
				resultMap.put("statusCode", "200");
				resultMap.put("message", "调用成功");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
	/**
	 * 终结详细页面  接口 zzq  20171031
	 * @throws ParseException 
	 */
	@RequestMapping("/getAddZj")
	public @ResponseBody String  getAddZj(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		JSONObject object=JSONObject.fromObject(json);
		String workId=object.getString("workId");
		String electricId=object.getString("electricId");
		String taskId=object.getString("taskId");
		String procInstId=object.getString("procInstId");
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
				Map<String, Object> resultMapTicket = new Hashtable<String, Object>();
				List<Condition> conditions = new ArrayList<Condition>();
				conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.WORK_TICKET_PROCESS_KEY.getName()));
				List<DefinitionEntity> defList=definitionService.findByCondition(conditions, null);
				String modelId="";
				if(!defList.isEmpty()){
					modelId=defList.get(0).getModelId();
				}
				List<SysUserEntity> userList= nodeConfigService.getFirstNodeTransactor(modelId,"",userEntity);
				resultMapTicket.put("userList", userList);
				
				WorkElectricTwoEntity workElectricTwoEntity = workElectricTwoService.findById(Long.valueOf(electricId));
				WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
				//判断工作负责人  到底是谁
				if(workElectricTwoEntity.getChangeNewPicName()!=null&&!workElectricTwoEntity.getChangeNewPicName().equals("")){
					resultMap.put("workPersonId", workElectricTwoEntity.getChangeNewPicId());
				}else{
					resultMap.put("workPersonId", workTicketTwoEntity.getGuarderId());
				}
				resultMap.put("obj", resultMapTicket);
				resultMap.put("workId", workId);
				resultMap.put("electricId", electricId);
				resultMap.put("taskId", taskId);
				resultMap.put("procInstId", procInstId);
				if(workTicketTwoEntity.getEndTime()==null||workTicketTwoEntity.getEndTime().equals("")){
					resultMap.put("endTimeZhu","");
				}else{
					resultMap.put("endTimeZhu", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(workTicketTwoEntity.getEndTime()));
				}
				resultMap.put("remarkOther", workElectricTwoEntity.getRemarkOther()==null?"":workElectricTwoEntity.getRemarkOther());
				resultMap.put("selectUser", "");
				
				resultMap.put("userEntity", userEntity);
				resultMap.put("statusCode", "200");
				resultMap.put("message", "调用成功");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
	
	/**
	 * 终结确定  接口 zzq  20171031
	 * @throws ParseException 
	 */
	@RequestMapping("/sureZj")
	public @ResponseBody String  sureZj(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		JSONObject object=JSONObject.fromObject(json);
		String electricId=object.getString("electricId");
		String taskId=object.getString("taskId");
		String procInstId=object.getString("procInstId");
		String selectUser=object.getString("selectUser");
		String endTimeZhu=object.getString("endTimeZhu");
		String remarkOther=object.getString("remarkOther");
		String approveIdea=object.getString("approveIdea");
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
				if(selectUser!=null&&!selectUser.equals("")){
					if(endTimeZhu!=null&&!endTimeZhu.equals("")){
						RequestContext.get().setUser(userEntity);
						WorkElectricTwoEntity workElectricTwoEntity=new WorkElectricTwoEntity();
						workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.ZJ.getCode());
						workElectricTwoEntity.setSelectUser(selectUser);
						workElectricTwoEntity.setId(Long.valueOf(electricId));
						workElectricTwoEntity.setRemarkOther(remarkOther);
						workElectricTwoEntity.setApproveIdea(approveIdea);
						DateFormatUtil sdf = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss");
						workElectricTwoEntity.setEndTimeZhu(sdf.parse(endTimeZhu));
						workElectricTwoEntity.setEndPicIdZhu(userEntity.getId());
						workElectricTwoEntity.setEndPicNameZhu(userEntity.getName());
						
						Map<String, Object> taskVariables=new HashMap<String, Object>();
						taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),workElectricTwoEntity.getSelectUser());
						taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),WorkSpResultEnum.THEEND.getCode());
						
						taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
						taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
						
						actTaskService.complete(taskId,procInstId, taskVariables);
						workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
						
						resultMap.put("statusCode", "200");
						resultMap.put("message", "调用成功");
						resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
					}else{
						resultMap.put("statusCode", "300");
						resultMap.put("message", "调用失败，终结时间不能为空");
						resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
					}
				}else{
					resultMap.put("statusCode", "300");
					resultMap.put("message", "调用失败,未传入下一级审批人");
					resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				}
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}

	/**
	 * 终结许可同意  接口 zzq  20171031
	 * @throws ParseException 
	 */
	@RequestMapping("/agreeZjXk")
	public @ResponseBody String  agreeZjXk(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		JSONObject object=JSONObject.fromObject(json);
		String electricId=object.getString("electricId");
		String taskId=object.getString("taskId");
		String procInstId=object.getString("procInstId");
		String approveIdea=object.getString("approveIdea");
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
				RequestContext.get().setUser(userEntity);
				WorkElectricTwoEntity workElectricTwoEntity=new WorkElectricTwoEntity();
				workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.ZJXK.getCode());
				workElectricTwoEntity.setId(Long.valueOf(electricId));
				workElectricTwoEntity.setApproveIdea(approveIdea);
				workElectricTwoEntity.setEndAllowIdZhu(userEntity.getId());
				workElectricTwoEntity.setEndAllowNameZhu(userEntity.getName());
				
				Map<String, Object> taskVariables=new HashMap<String, Object>();
				taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
				taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
				
				taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
				taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
				
				actTaskService.complete(taskId,procInstId, taskVariables);
				workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
						
				resultMap.put("statusCode", "200");
				resultMap.put("message", "调用成功");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
					
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
	/**
	 * 终结许可不同意  接口 zzq  20171031
	 * @throws ParseException 
	 */
	@RequestMapping("/disagreeZjXk")
	public @ResponseBody String  disagreeZjXk(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		JSONObject object=JSONObject.fromObject(json);
		String electricId=object.getString("electricId");
		String taskId=object.getString("taskId");
		String procInstId=object.getString("procInstId");
		String workPersonId=object.getString("workPersonId");
		String approveIdea=object.getString("approveIdea");
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
				RequestContext.get().setUser(userEntity);
				WorkElectricTwoEntity workElectricTwoEntity=new WorkElectricTwoEntity();
				workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.ZJXK.getCode());
				workElectricTwoEntity.setId(Long.valueOf(electricId));
				workElectricTwoEntity.setApproveIdea(approveIdea);
				
				SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(workPersonId));//或许这个工作负责人的实体
				Map<String, Object> taskVariables=new HashMap<String, Object>();
				taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
				taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
				//增加判断++
				WorkElectricTwoEntity workElectric=workElectricTwoService.findById(Long.valueOf(workElectricTwoEntity.getId()));
				if(workElectric!=null){
					if(workElectric.getChangeNewPicName()!=null&&!workElectric.getChangeNewPicName().equals("")){
						SysUserEntity  sysUserEntityChuan=sysUserService.findById(Long.valueOf(workElectric.getChangeNewPicId()));//或许这个变更后的工作负责人的实体
						taskVariables.put(CandidateMarkEnum.STARTER.getName(),sysUserEntityChuan);
					}
				}
				//增加判断++
				taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
				taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
				actTaskService.complete(taskId,procInstId,taskVariables);
				workElectricTwoService.updateSpnrDisagree(workElectricTwoEntity,userEntity);
						
				resultMap.put("statusCode", "200");
				resultMap.put("message", "调用成功");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
					
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
	
	
	/**
	 *	延期弹出框  接口 zzq  20171031
	 * @throws ParseException 
	 */
	@RequestMapping("/getAddYq")
	public @ResponseBody String  getAddYq(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		JSONObject object=JSONObject.fromObject(json);
		String taskId=object.getString("taskId");
		String electricId=object.getString("electricId");
		String procInstId=object.getString("procInstId");
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
				WorkElectricTwoEntity workElectricEntityYq = workElectricTwoService.findById(Long.valueOf(electricId));
				WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(workElectricEntityYq.getWorkticketId());
				//判断工作负责人  到底是谁
				if(workElectricEntityYq.getChangeNewPicName()!=null&&!workElectricEntityYq.getChangeNewPicName().equals("")){
					resultMap.put("workPersonId", workElectricEntityYq.getChangeNewPicId());
				}else{
					resultMap.put("workPersonId", workTicketTwoEntity.getGuarderId());
				}
				
			    Map<String, Object> resultMapTicket = new Hashtable<String, Object>();
				List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, WorkSpResultEnum.EXTENSION.getCode());
				resultMapTicket.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
				resultMap.put("obj", resultMapTicket);
				resultMap.put("taskId", taskId);
				resultMap.put("procInstId", procInstId);
				resultMap.put("electricId", electricId);
				resultMap.put("selectUser", "");
				resultMap.put("approveIdea", "");
				if(workElectricEntityYq.getDelayDate()==null||workElectricEntityYq.getDelayDate().equals("")){
					resultMap.put("delayDate", "");//延期时间
				}else{
					resultMap.put("delayDate", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(workElectricEntityYq.getDelayDate()));//延期时间
				}
				if(workElectricEntityYq.getDelayAllowId()==null||workElectricEntityYq.getDelayAllowId().toString().equals("")){
					resultMap.put("delayAllowId", userEntity.getId());
					resultMap.put("delayAllowName", userEntity.getName());
				}else{
					resultMap.put("delayAllowId", workElectricEntityYq.getDelayAllowId());
					resultMap.put("delayAllowName", workElectricEntityYq.getDelayAllowName());
				}
				resultMap.put("delayPicId", userEntity.getId());
				resultMap.put("delayPicName", userEntity.getName());
				
				
				resultMap.put("statusCode", "200");
				resultMap.put("message", "调用成功");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
	
	/**
	 *	延期保存  接口 zzq  20171031
	 * @throws ParseException 
	 */
	@RequestMapping("/sureYq")
	public @ResponseBody String  sureYq(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		JSONObject object=JSONObject.fromObject(json);
		String taskId=object.getString("taskId");
		String electricId=object.getString("electricId");
		String procInstId=object.getString("procInstId");
		String approveIdea=object.getString("approveIdea");
		String selectUser=object.getString("selectUser");
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
				WorkElectricTwoEntity workElectricEntityYq = workElectricTwoService.findById(Long.valueOf(electricId));
				if(workElectricEntityYq.getDelayDate()!=null&&!workElectricEntityYq.getDelayDate().equals("")){
					resultMap.put("statusCode", "300");
					resultMap.put("message", "调用失败,延期只能延一次");
					resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				}else{
					RequestContext.get().setUser(userEntity);
					WorkElectricTwoEntity workElectricTwoEntity=new WorkElectricTwoEntity();
					workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.YQ.getCode());
					workElectricTwoEntity.setId(Long.valueOf(electricId));
					workElectricTwoEntity.setApproveIdea(approveIdea);
					
					Map<String, Object> taskVariables=new HashMap<String, Object>();
					taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
					taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),WorkSpResultEnum.EXTENSION.getCode());
					
					taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
					taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
					
					actTaskService.complete(taskId,procInstId, taskVariables);
					workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
					
					resultMap.put("statusCode", "200");
					resultMap.put("message", "调用成功");
					resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				}
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
	
	/**
	 *	延期许可的保存  接口 zzq  20171031
	 * @throws ParseException 
	 */
	@RequestMapping("/sureYqxk")
	public @ResponseBody String  sureYqxk(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		JSONObject object=JSONObject.fromObject(json);
		String taskId=object.getString("taskId");
		String electricId=object.getString("electricId");
		String procInstId=object.getString("procInstId");
		String approveIdea=object.getString("approveIdea");
		String selectUser=object.getString("selectUser");
		String delayDate=object.getString("delayDate");//延期时间
		String delayAllowId=object.getString("delayAllowId");
		String delayAllowName=object.getString("delayAllowName");
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
				if(StringUtil.isEmpty(delayDate)){
					resultMap.put("statusCode", "300");
					resultMap.put("message", "调用失败,延期日期不能为空");
					resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				}else{
					RequestContext.get().setUser(userEntity);
					WorkElectricTwoEntity workElectricTwoEntity=new WorkElectricTwoEntity();
					workElectricTwoEntity.setId(Long.valueOf(electricId));
					workElectricTwoEntity.setApproveIdea(approveIdea);
					workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.YQXK.getCode());
					workElectricTwoEntity.setDelayAllowId(Long.valueOf(delayAllowId));
					workElectricTwoEntity.setDelayAllowName(delayAllowName);
					DateFormatUtil sdf=DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss");
					workElectricTwoEntity.setDelayDate(sdf.parse(delayDate));
					Map<String, Object> taskVariables=new HashMap<String, Object>();
					taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
					taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
					//增加判断++
					WorkElectricTwoEntity workElectric=workElectricTwoService.findById(Long.valueOf(workElectricTwoEntity.getId()));
					if(workElectric!=null){
						if(workElectric.getChangeNewPicName()!=null&&!workElectric.getChangeNewPicName().equals("")){
							SysUserEntity  sysUserEntityChuan=sysUserService.findById(Long.valueOf(workElectric.getChangeNewPicId()));//或许这个变更后的工作负责人的实体
							taskVariables.put(CandidateMarkEnum.STARTER.getName(),sysUserEntityChuan);
						}
					}
					//增加判断++
					taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
					taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
					
					actTaskService.complete(taskId,procInstId, taskVariables);
					workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
						
					resultMap.put("statusCode", "200");
					resultMap.put("message", "调用成功");
					resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				}
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
	
	/**
	 *	延期负责人的保存  接口 zzq  20171031
	 * @throws ParseException 
	 */
	@RequestMapping("/agreeYqFzr")
	public @ResponseBody String  agreeYqFzr(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		JSONObject object=JSONObject.fromObject(json);
		String taskId=object.getString("taskId");
		String electricId=object.getString("electricId");
		String procInstId=object.getString("procInstId");
		String approveIdea=object.getString("approveIdea");
		String workPersonId=object.getString("workPersonId");
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
				RequestContext.get().setUser(userEntity);
				WorkElectricTwoEntity workElectricTwoEntity=new WorkElectricTwoEntity();
				workElectricTwoEntity.setId(Long.valueOf(electricId));
				workElectricTwoEntity.setApproveIdea(approveIdea);
				workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.YQFZR.getCode());
				workElectricTwoEntity.setDelayPicId(userEntity.getId());
				workElectricTwoEntity.setDelayPicName(userEntity.getName());
				
				SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(workPersonId));//或许这个工作负责人的实体
				Map<String, Object> taskVariables=new HashMap<String, Object>();
				taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
				taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
				//增加判断++
				WorkElectricTwoEntity workElectric=workElectricTwoService.findById(Long.valueOf(workElectricTwoEntity.getId()));
				if(workElectric!=null){
					if(workElectric.getChangeNewPicName()!=null&&!workElectric.getChangeNewPicName().equals("")){
						SysUserEntity  sysUserEntityChuan=sysUserService.findById(Long.valueOf(workElectric.getChangeNewPicId()));//或许这个变更后的工作负责人的实体
						taskVariables.put(CandidateMarkEnum.STARTER.getName(),sysUserEntityChuan);
					}
				}
				//增加判断++
				taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
				taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
				
				actTaskService.complete(taskId,procInstId, taskVariables);
				workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
						
				resultMap.put("statusCode", "200");
				resultMap.put("message", "调用成功");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
	
	/**
	 *	工作负责人变更的弹出框  接口 zzq  20171031
	 * @throws ParseException 
	 */
	@RequestMapping("/getAddGzfzrbd")
	public @ResponseBody String  getAddGzfzrbd(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		JSONObject object=JSONObject.fromObject(json);
		String taskId=object.getString("taskId");
		String electricId=object.getString("electricId");
		String procInstId=object.getString("procInstId");
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
				Map<String, Object> resultMapTicket = new HashMap<String, Object>();
				List<SysUserEntity> userList=new ArrayList<SysUserEntity>();
					userList=nodeConfigService.getNextNodeTransactor(taskId,WorkSpResultEnum.WORKPICCHANGE.getCode());
				if(userList.isEmpty()){
					userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
				}
				resultMapTicket.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
				
				WorkElectricTwoEntity workElectricTwoEntity = workElectricTwoService.findById(Long.valueOf(electricId));
				WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
				//查询职务下面的人
				List<Condition> conditions=new ArrayList<Condition>();
				conditions.clear();
				conditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,WorkSafeTypeEnum.WORKPERSON.getCode()));
				List<SysDutiesDetailEntity> dutiesList =sysDutiesDetailService.findByCondition(conditions, null);
				List<String> userCodeList=new ArrayList<String>();
				if(!dutiesList.isEmpty()){
					for (SysDutiesDetailEntity sysDutiesDetailEntity : dutiesList) {
						if(!sysDutiesDetailEntity.getUserUnitRelId().equals(workTicketTwoEntity.getGuarderId().toString())){
							List<Condition> condition = new ArrayList<Condition>();
							Long userUnitid = Long.parseLong(sysDutiesDetailEntity.getUserUnitRelId());
							condition.add(new Condition("C_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userUnitid));
							List<UserUnitRelEntity> list_userUnitRel = userUnitRelService.findByCondition(condition, null);
							for(UserUnitRelEntity tempuserUnitRel:list_userUnitRel){
								userCodeList.add(tempuserUnitRel.getUserId().toString());
							}
						}
					}
				}
				//变更人的下拉框
				conditions.clear();
				conditions.add(new Condition("unitId", FieldTypeEnum.STRING, MatchTypeEnum.EQ,workTicketTwoEntity.getUnitNameId() ));
				conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
				conditions.add(new Condition("a.C_ID", FieldTypeEnum.STRING, MatchTypeEnum.IN, userCodeList.toArray()));
				List<SysUserEntity> userListBox=sysUserService.findByCondition(conditions, null);
				ComboboxVO comboWorkTicketVO = new ComboboxVO();
				for(SysUserEntity sysUserEntity : userListBox){
					comboWorkTicketVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
			    }
				resultMapTicket.put("userListBox", (comboWorkTicketVO.getOptions()));
				
				resultMap.put("changeOldPicId", workTicketTwoEntity.getGuarderId()==null?"":workTicketTwoEntity.getGuarderId());
				resultMap.put("changeOldPicName", workTicketTwoEntity.getGuarderName()==null?"":workTicketTwoEntity.getGuarderName());
				resultMap.put("taskId", taskId);
				resultMap.put("procInstId", procInstId);
				resultMap.put("electricId", electricId);
				resultMap.put("changeNewPicId", workElectricTwoEntity.getChangeNewPicId()==null?"":workElectricTwoEntity.getChangeNewPicId());
				resultMap.put("changeNewPicName", workElectricTwoEntity.getChangeNewPicName()==null?"":workElectricTwoEntity.getChangeNewPicName());
				resultMap.put("changeSignerId", workElectricTwoEntity.getChangeSignerId()==null?"":workElectricTwoEntity.getChangeSignerId());
				resultMap.put("changeSignerName",workElectricTwoEntity.getChangeSignerName()==null?"":workElectricTwoEntity.getChangeSignerName());
				if(workElectricTwoEntity.getChangeSignerDate()!=null&&!workElectricTwoEntity.getChangeSignerDate().equals("")){
					DateFormatUtil sdf =DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss");
					resultMap.put("changeSignerDate", sdf.format(workElectricTwoEntity.getChangeSignerDate()));
				}else{
					resultMap.put("changeSignerDate","");
				}
				resultMap.put("approveIdea", "");
				resultMap.put("selectUser", "");
				resultMap.put("obj", resultMapTicket);
				resultMap.put("statusCode", "200");
				resultMap.put("message", "调用成功");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
	
	/**
	 *	工作负责人变更的保存  接口 zzq  20171031
	 * @throws ParseException 
	 */
	@RequestMapping("/sureGzfzrbg")
	public @ResponseBody String  sureGzfzrbg(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		JSONObject object=JSONObject.fromObject(json);
		String taskId=object.getString("taskId");
		String electricId=object.getString("electricId");
		String procInstId=object.getString("procInstId");
		String approveIdea=object.getString("approveIdea");
		String selectUser=object.getString("selectUser");
		String changeNewPicId=object.getString("changeNewPicId");
		String changeOldPicId=object.getString("changeOldPicId");
		String changeOldPicName=object.getString("changeOldPicName");
		
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
				if(StringUtil.isEmpty(selectUser)){
					resultMap.put("statusCode", "300");
					resultMap.put("message", "调用失败,下一步审批人为空");
					resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				}else{
					if(StringUtil.isEmpty(changeNewPicId)){
						resultMap.put("statusCode", "300");
						resultMap.put("message", "调用失败,新工作负责人为空");
						resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
					}else{
						WorkElectricTwoEntity workElectric = workElectricTwoService.findById(Long.valueOf(electricId));
						if(workElectric.getChangeNewPicId()!=null&&!workElectric.getChangeNewPicId().toString().equals("")){
							resultMap.put("statusCode", "300");
							resultMap.put("message", "调用失败,工作负责人只能变更一次");
							resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
						}else{
							RequestContext.get().setUser(userEntity);
							WorkElectricTwoEntity workElectricTwoEntity=new WorkElectricTwoEntity();
							workElectricTwoEntity.setId(Long.valueOf(electricId));
							workElectricTwoEntity.setApproveIdea(approveIdea);
							workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.GZFZRBG.getCode());
							workElectricTwoEntity.setChangeNewPicId(Long.valueOf(changeNewPicId));
							workElectricTwoEntity.setChangeOldPicId(Long.valueOf(changeOldPicId));
							workElectricTwoEntity.setChangeOldPicName(changeOldPicName);
							Map<String, Object> taskVariables=new HashMap<String, Object>();
							taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
							taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),WorkSpResultEnum.WORKPICCHANGE.getCode());
							taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
							taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
							actTaskService.complete(taskId,procInstId, taskVariables);
							workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
									
							resultMap.put("statusCode", "200");
							resultMap.put("message", "调用成功");
							resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
						}
					}
				}
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
	
	/**
	 *	工作负责人变更签发的同意  接口 zzq  20171031
	 * @throws ParseException 
	 */
	@RequestMapping("/agreeGzfzrbgQf")
	public @ResponseBody String  agreeGzfzrbgQf(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		JSONObject object=JSONObject.fromObject(json);
		String taskId=object.getString("taskId");
		String electricId=object.getString("electricId");
		String procInstId=object.getString("procInstId");
		String approveIdea=object.getString("approveIdea");
		String selectUser=object.getString("selectUser");
		
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
				if(StringUtil.isEmpty(selectUser)){
					resultMap.put("statusCode", "300");
					resultMap.put("message", "调用失败,下一步审批人为空");
					resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				}else{
					RequestContext.get().setUser(userEntity);
					WorkElectricTwoEntity workElectricTwoEntity=new WorkElectricTwoEntity();
					workElectricTwoEntity.setId(Long.valueOf(electricId));
					workElectricTwoEntity.setApproveIdea(approveIdea);
					workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.GZFZRBGQF.getCode());
					workElectricTwoEntity.setChangeSignerId(userEntity.getId());
					workElectricTwoEntity.setChangeSignerName(userEntity.getName());
					workElectricTwoEntity.setChangeSignerDate(new Date());
					Map<String, Object> taskVariables=new HashMap<String, Object>();
					taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
					taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
					taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
					taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
					actTaskService.complete(taskId,procInstId, taskVariables);
					workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
							
					resultMap.put("statusCode", "200");
					resultMap.put("message", "调用成功");
					resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				}
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
	/**
	 *	工作负责人变更签发的不同意  接口 zzq  20171031
	 * @throws ParseException 
	 */
	@RequestMapping("/disAgreeGzfzrbgQf")
	public @ResponseBody String  disAgreeGzfzrbgQf(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		JSONObject object=JSONObject.fromObject(json);
		String taskId=object.getString("taskId");
		String electricId=object.getString("electricId");
		String procInstId=object.getString("procInstId");
		String approveIdea=object.getString("approveIdea");
		String changeOldPicId=object.getString("changeOldPicId");
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
				RequestContext.get().setUser(userEntity);
				WorkElectricTwoEntity workElectricTwoEntity=new WorkElectricTwoEntity();
				workElectricTwoEntity.setId(Long.valueOf(electricId));
				workElectricTwoEntity.setApproveIdea(approveIdea);
				workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.GZFZRBGQF.getCode());
				
				SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(changeOldPicId));//或许这个工作负责人的实体
				Map<String, Object> taskVariables=new HashMap<String, Object>();
				taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
				taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
				//增加判断++
				WorkElectricTwoEntity workElectric=workElectricTwoService.findById(Long.valueOf(workElectricTwoEntity.getId()));
				if(workElectric!=null){
					if(workElectric.getChangeOldPicName()!=null&&!workElectric.getChangeOldPicName().equals("")){
						SysUserEntity  sysUserEntityChuan=sysUserService.findById(Long.valueOf(workElectric.getChangeOldPicId()));//或许这个变更后的工作负责人的实体
						taskVariables.put(CandidateMarkEnum.STARTER.getName(),sysUserEntityChuan);
					}
				}
				//增加判断++
				taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
				taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
				actTaskService.complete(taskId,procInstId,taskVariables);
				workElectricTwoService.updateSpnrDisagree(workElectricTwoEntity,userEntity);
						
				resultMap.put("statusCode", "200");
				resultMap.put("message", "调用成功");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
	
	/**
	 *	工作负责人变更许可的同意  接口 zzq  20171031
	 * @throws ParseException 
	 */
	@RequestMapping("/agreeGzfzrbgXk")
	public @ResponseBody String  agreeGzfzrbgXk(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		JSONObject object=JSONObject.fromObject(json);
		String taskId=object.getString("taskId");
		String electricId=object.getString("electricId");
		String procInstId=object.getString("procInstId");
		String approveIdea=object.getString("approveIdea");
		String changeNewPicId=object.getString("changeNewPicId");
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
				RequestContext.get().setUser(userEntity);
				WorkElectricTwoEntity workElectricTwoEntity=new WorkElectricTwoEntity();
				workElectricTwoEntity.setId(Long.valueOf(electricId));
				workElectricTwoEntity.setApproveIdea(approveIdea);
				workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.GZFZRBGXK.getCode());
				workElectricTwoEntity.setChangeAllowId(userEntity.getId());
				workElectricTwoEntity.setChangeAllowName(userEntity.getName());
				workElectricTwoEntity.setChangeAllowDate(new Date());
				SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(changeNewPicId));//或许这个工作负责人的实体
				Map<String, Object> taskVariables=new HashMap<String, Object>();
				taskVariables.put(CandidateMarkEnum.STARTER.getName(),sysUserEntity);
				taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
				taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
				taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
				taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
				
				actTaskService.complete(taskId,procInstId, taskVariables);
				workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
				resultMap.put("statusCode", "200");
				resultMap.put("message", "调用成功");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
	
	/**
	 *	工作负责人变更许可的不同意  接口 zzq  20171031
	 * @throws ParseException 
	 */
	@RequestMapping("/disAgreeGzfzrbgXk")
	public @ResponseBody String  disAgreeGzfzrbgXk(HttpServletRequest request) throws Exception{
		String token=request.getParameter("token");
		String json=request.getParameter("json");
		//解密
		json=MobileUtil.decodeString(json);
		JSONObject object=JSONObject.fromObject(json);
		String taskId=object.getString("taskId");
		String electricId=object.getString("electricId");
		String procInstId=object.getString("procInstId");
		String approveIdea=object.getString("approveIdea");
		String changeOldPicId=object.getString("changeOldPicId");
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		if (user == null) {
			resultMap.put("statusCode", "301");
			resultMap.put("message", "token无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}else{
			SysUserEntity userEntity = user.get(token);
			if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
				resultMap.put("statusCode", "302");
				resultMap.put("message", "token过期");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TokenUtil.clearUserEntity(token);
			}else{
				RequestContext.get().setUser(userEntity);
				WorkElectricTwoEntity workElectricTwoEntity=new WorkElectricTwoEntity();
				workElectricTwoEntity.setId(Long.valueOf(electricId));
				workElectricTwoEntity.setApproveIdea(approveIdea);
				workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.GZFZRBGXK.getCode());
				SysUserEntity  sysUserEntity=sysUserService.findById(changeOldPicId);//或许这个工作负责人的实体
				Map<String, Object> taskVariables=new HashMap<String, Object>();
				taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
				taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
				//增加判断++
				WorkElectricTwoEntity workElectric=workElectricTwoService.findById(Long.valueOf(workElectricTwoEntity.getId()));
				if(workElectric!=null){
					if(workElectric.getChangeOldPicName()!=null&&!workElectric.getChangeOldPicName().equals("")){
						SysUserEntity  sysUserEntityChuan=sysUserService.findById(Long.valueOf(workElectric.getChangeOldPicId()));//或许这个变更后的工作负责人的实体
						taskVariables.put(CandidateMarkEnum.STARTER.getName(),sysUserEntityChuan);
					}
				}
				//增加判断++
				taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
				taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
				actTaskService.complete(taskId,procInstId,taskVariables);
				workElectricTwoService.updateSpnrDisagree(workElectricTwoEntity,userEntity);
				
				resultMap.put("statusCode", "200");
				resultMap.put("message", "调用成功");
				resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			}
		}
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
}