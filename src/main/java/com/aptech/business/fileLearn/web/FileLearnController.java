package com.aptech.business.fileLearn.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;
import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.OAManagement.dispatchManagement.domain.DispatchManagementEntity;
import com.aptech.business.component.dictionary.FileLearnApproveStatusEnum;
import com.aptech.business.component.dictionary.FileLearnExcuteEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.fileLearn.domain.FileLearnEntity;
import com.aptech.business.fileLearn.domain.FileTreeEntity;
import com.aptech.business.fileLearn.service.FileLearnService;
import com.aptech.business.fileLearnAttachment.domain.FileLearnAttachmentEntity;
import com.aptech.business.fileLearnAttachment.service.FileLearnAttachmentService;
import com.aptech.business.filelearnReceiveUser.domain.FilelearnReceiveUserEntity;
import com.aptech.business.filelearnReceiveUser.service.FilelearnReceiveUserService;
import com.aptech.business.messageCenter.domain.MessageCenterEntity;
import com.aptech.business.messageCenter.service.MessageCenterService;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.common.system.dutiesdetail.domain.SysDutiesDetailEntity;
import com.aptech.common.system.dutiesdetail.service.SysDutiesDetailService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.system.userUnit.domain.UserUnitRelEntity;
import com.aptech.common.system.userUnit.service.UserUnitRelService;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.common.workflow.definition.domain.DefinitionEntity;
import com.aptech.common.workflow.definition.service.DefinitionService;
import com.aptech.common.workflow.finishTask.service.FinishTaskService;
import com.aptech.common.workflow.modelEditor.domain.BranchMarkEnum;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
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
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 文件学习配置控制器
 *
 * @author hjw
 * @created 2018-04-02 16:50:12
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/fileLearn")
public class FileLearnController extends BaseController<FileLearnEntity> {
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private FileLearnService fileLearnService;
	@Autowired
	private FileLearnAttachmentService fileLearnAttachmentService;
	@Autowired
	private FilelearnReceiveUserService filelearnReceiveUserService;
	@Autowired
	private TodoTaskService todoTaskService;
	@Autowired
	private FinishTaskService finishTaskService;
	@Autowired
	private ProcessNodeAuthService processNodeAuthService;
	@Autowired
	private DefinitionService definitionService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Autowired
	private MessageCenterService messageCenterService;
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysDutiesDetailService sysDutiesDetailService;
	@Autowired
	private UserUnitRelService userUnitRelService;
	@Override
	public IBaseEntityOperation<FileLearnEntity> getService() {
		return fileLearnService;
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
		//发文类型-从数据字典取
		/*Map<String, SysDictionaryVO> sendFileTypeMap  =  DictionaryUtil.getDictionaries("SEND_FILE_TYPE");
		ComboboxVO comboSendFileTypeVO = new ComboboxVO();
		for(String key : sendFileTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = sendFileTypeMap.get(key);
			comboSendFileTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("sendFileTypeCombobox", JsonUtil.toJson(comboSendFileTypeVO.getOptions()));*/
		// 部门下拉树
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		for (int i = 0; i < treeNodeList.size(); i++) {
			treeNodeList.get(i).setOpen("true");
		}
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		return this.createModelAndView("fileLearn/fileLearnList", model);
	}
	/**
	 * 列表查询
	 */
	@RequestMapping(value = "/list")
	public @ResponseBody ResultListObj List(HttpServletRequest request, @RequestBody Map<String, Object> params) {
			List<Condition> conditions = OrmUtil.changeMapToCondition(params);
			ResultListObj resultObj = new ResultListObj();
			Page<FileLearnEntity> page = PageUtil.getPage(params);
			page.setOrders(OrmUtil.changeMapToOrders(params));
			page.addOrder(Sort.desc("sendTime"));
			List<Condition> userConditions = new ArrayList<Condition>();
			userConditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "85"));
			List<SysDutiesDetailEntity> sysDutiesDetailList = sysDutiesDetailService.findByCondition(userConditions, null);
			List<String> userUnitIds = new ArrayList<String>();
			for(SysDutiesDetailEntity sysDutiesDetailEntity : sysDutiesDetailList){
				userUnitIds.add(sysDutiesDetailEntity.getUserUnitRelId());
			}
			userConditions.clear();
			userConditions.add(new Condition("C_ID", FieldTypeEnum.STRING, MatchTypeEnum.IN, userUnitIds.toArray()));
			List<UserUnitRelEntity> userUnitRelList = userUnitRelService.findByCondition(userConditions, null);
			List<String> userIds = new ArrayList<String>();
			for(UserUnitRelEntity userUnitRelEntity : userUnitRelList){
				userIds.add(userUnitRelEntity.getUserId().toString());
			}
			SysUserEntity sysUserEntity = RequestContext.get().getUser();
			String userId = sysUserEntity.getId().toString();
			List<FileLearnEntity> list = new ArrayList<FileLearnEntity>();
			if(userIds.contains(userId)){
				list = fileLearnService.findByCondition(conditions, page);
			}else{
				conditions.add(new Condition("C_SEND_USER_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userId));
				list = fileLearnService.findByCondition(conditions, page);
			}
			resultObj.setDraw((Integer)params.get("draw"));
			if (list != null) {
				resultObj.setData(list);
				resultObj.setRecordsTotal(page.getTotal());
			}
			return resultObj;
	}
	
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		String userUnitId = String.valueOf(sysUserEntity.getUnitId());
		model.put("sysUserEntity", JsonUtil.toJson(sysUserEntity));
				
		//获取系统时间
		Date now = new Date(); 
		DateFormatUtil sdf = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		String time = sdf.format( now ); 
		model.put("systemdate", JsonUtil.toJson(time));
		
		//单位下拉框 
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		
		//接收人
		List<FileTreeEntity> recipientTree = fileLearnService.getRecipientTree();
		model.put("recipientTree", JsonUtil.toJson(recipientTree));
		return this.createModelAndView("fileLearn/fileLearnAdd", model);
	}
	/**
	 * 
	 * 添加页面保存
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @return
	 * @param @throws UnsupportedEncodingException
	 * @param @throws ParseException
	 * @return ResultObj
	 * @throws 
	 * @author haojw
	 * @created 2018年4月10日 上午10:00:19
	 * @lastModified
	 */
	@RequestMapping("/saveAddPage")
	public @ResponseBody ResultObj saveAddPage(@RequestBody FileLearnEntity entity, HttpServletRequest request){
		//添加主表
		fileLearnService.addEntity(entity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.FILE_LEARN.getName(), OperateUserEnum.ADD.getName(), "", OperateUserEnum.ADD.getId());
		//添加接收人
		List<FilelearnReceiveUserEntity> receiveUserList = new ArrayList<FilelearnReceiveUserEntity>();
		String receiveUserIds = entity.getReceiveUserIds();
		if(!StringUtil.isEmpty(receiveUserIds)){
			String [] receiveUserArg = receiveUserIds.split(",");
			for(String id : receiveUserArg){
				FilelearnReceiveUserEntity user = new FilelearnReceiveUserEntity();
				user.setUserId(Long.parseLong(id));
				user.setFileLearnId(entity.getId());
				user.setIsLearn("0");
				receiveUserList.add(user);
			}
		}
		filelearnReceiveUserService.batchInsert(receiveUserList);
		ResultObj resultObj = new ResultObj();
		return resultObj;
	}
	/**
	 *	查看
	 */
	@RequestMapping("/getShow/{id}")
	public ModelAndView getShowPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 文件学习表数据
		FileLearnEntity fileLearnEntity = (FileLearnEntity)fileLearnService.findById(id);
		//接收人
		List<Condition> receiveUserConditions = new ArrayList<Condition>();
		receiveUserConditions.add(new Condition("C_FILE_LEARN_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,fileLearnEntity.getId()));
		List<FilelearnReceiveUserEntity> receiveUserList = filelearnReceiveUserService.findByCondition(receiveUserConditions, null);
		String receiveUserIds = new String();
		String receiveUserNames = new String();
		for(FilelearnReceiveUserEntity receiveUser : receiveUserList){
			receiveUserIds += receiveUser.getUserId() + ",";
			receiveUserNames += receiveUser.getUserName() + ",";
		}
		if(!StringUtil.isEmpty(receiveUserIds) && !StringUtil.isEmpty(receiveUserNames)){
			receiveUserIds = receiveUserIds.substring(0, receiveUserIds.length()-1);
			receiveUserNames = receiveUserNames.substring(0, receiveUserNames.length()-1);
		}
		fileLearnEntity.setReceiveUserIds(receiveUserIds);
		fileLearnEntity.setReceiveUserNames(receiveUserNames);
		//流程节点信息
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		List<Condition> conditionsLc=new ArrayList<Condition>();
		conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getLoginName()));
		conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		//conditionsLc.add(new Condition("task.end_time_ IS NULL"));
		conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.FILE_LEARN_PROCESS_KEY.getName()));
		List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditionsLc, null);
		TodoTaskEntity todoTaskEntity=null;
		if(null != list && !list.isEmpty()){
			todoTaskEntity=list.get(0);
		}
		//单位下拉框 
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//修改学习状态
		/*FilelearnReceiveUserEntity receiveUserEntity= updateLearnStatus(id);*/
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		model.put("todoTaskEntity", todoTaskEntity);
		model.put("entity", fileLearnEntity);
		model.put("entityJson", JsonUtil.toJson(fileLearnEntity));
		model.put("receiveUserList", JsonUtil.toJson(receiveUserList));
		/*model.put("receiveUserEntity", JsonUtil.toJson(receiveUserEntity));*/
		
		
		return this.createModelAndView("fileLearn/fileLearnShow", model);
	}
	/**
	 *	接收人学习
	 */
	@RequestMapping("/getCheck/{id}")
	public ModelAndView getCheckPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 文件学习表数据
		FileLearnEntity fileLearnEntity = (FileLearnEntity)fileLearnService.findById(id);
		//接收人
		List<Condition> receiveUserConditions = new ArrayList<Condition>();
		receiveUserConditions.add(new Condition("C_FILE_LEARN_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,fileLearnEntity.getId()));
		List<FilelearnReceiveUserEntity> receiveUserList = filelearnReceiveUserService.findByCondition(receiveUserConditions, null);
		String receiveUserIds = new String();
		String receiveUserNames = new String();
		
		for(FilelearnReceiveUserEntity receiveUser : receiveUserList){
			receiveUserIds += receiveUser.getUserId() + ",";
			receiveUserNames += receiveUser.getUserName() + ",";
		}
		if(!StringUtil.isEmpty(receiveUserIds) && !StringUtil.isEmpty(receiveUserNames)){
			receiveUserIds = receiveUserIds.substring(0, receiveUserIds.length()-1);
			receiveUserNames = receiveUserNames.substring(0, receiveUserNames.length()-1);
		}
		fileLearnEntity.setReceiveUserIds(receiveUserIds);
		fileLearnEntity.setReceiveUserNames(receiveUserNames);
		//单位下拉框 
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//修改学习状态
		FilelearnReceiveUserEntity receiveUserEntity= updateLearnStatus(id);
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		model.put("entity", fileLearnEntity);
		model.put("entityJson", JsonUtil.toJson(fileLearnEntity));
		model.put("receiveUserList", JsonUtil.toJson(receiveUserList));
		model.put("receiveUserEntity", receiveUserEntity);
		model.put("receiveUserJson", JsonUtil.toJson(receiveUserEntity));
		
		
		return this.createModelAndView("fileLearn/fileLearnCheck", model);
	}
	
	/**
	 *	跳转文件学习学习页面
	 */
	@RequestMapping("/goFileLeanPage/{id}")
	public ModelAndView goFileLeanPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 文件学习表数据
		FileLearnEntity fileLearnEntity = (FileLearnEntity)fileLearnService.findById(id);
		//接收人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		List<Condition> receiveUserConditions = new ArrayList<Condition>();
		receiveUserConditions.add(new Condition("C_FILE_LEARN_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,fileLearnEntity.getId()));
		List<FilelearnReceiveUserEntity> receiveUserList = filelearnReceiveUserService.findByCondition(receiveUserConditions, null);
		
		FilelearnReceiveUserEntity receiveUserEntity = null;
		String receiveUserIds = new String();
		String receiveUserNames = new String();
		for(FilelearnReceiveUserEntity receiveUser : receiveUserList){
			if (id.intValue() == receiveUser.getFileLearnId().intValue() && sysUserEntity.getId().intValue() == receiveUser.getUserId().intValue()) {
				receiveUserEntity = receiveUser;
			}
			receiveUserIds += receiveUser.getUserId() + ",";
			receiveUserNames += receiveUser.getUserName() + ",";
		}
		if(!StringUtil.isEmpty(receiveUserIds) && !StringUtil.isEmpty(receiveUserNames)){
			receiveUserIds = receiveUserIds.substring(0, receiveUserIds.length()-1);
			receiveUserNames = receiveUserNames.substring(0, receiveUserNames.length()-1);
		}
		fileLearnEntity.setReceiveUserIds(receiveUserIds);
		fileLearnEntity.setReceiveUserNames(receiveUserNames);
		//单位下拉框 
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();

		if (receiveUserEntity == null) {
			receiveUserEntity = new FilelearnReceiveUserEntity();
			receiveUserEntity.setFileLearnId(fileLearnEntity.getId());
			receiveUserEntity.setIsLearn("1");
			receiveUserEntity.setLearnTime(new Date());
			receiveUserEntity.setUserId(sysUserEntity.getId());
			receiveUserEntity.setUserName(sysUserEntity.getName());
			receiveUserEntity.setLearnTime(new Date());
		}
		if (receiveUserEntity != null) {
			if(receiveUserEntity.getAttachment() == null){
				receiveUserEntity.setAttachment("[]");
			}
		}
//		FilelearnReceiveUserEntity receiveUserEntity= new FilelearnReceiveUserEntity();
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		model.put("entity", fileLearnEntity);
		model.put("entityJson", JsonUtil.toJson(fileLearnEntity));
		model.put("receiveUserList", JsonUtil.toJson(receiveUserList));
		model.put("receiveUserEntity", receiveUserEntity);
		model.put("receiveUserJson", JsonUtil.toJson(receiveUserEntity));
		
		
		return this.createModelAndView("fileLearn/fileLearnCheck", model);
	}
	/**
	 * 
	 * 添加页面保存
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @return
	 * @param @throws UnsupportedEncodingException
	 * @param @throws ParseException
	 * @return ResultObj
	 * @throws 
	 * @author haojw
	 * @created 2018年4月10日 上午10:00:19
	 * @lastModified
	 */
	@RequestMapping("/saveLearnFeel")
	public @ResponseBody ResultObj saveLearnFeel(@RequestBody FilelearnReceiveUserEntity entity, HttpServletRequest request){
		
		if (entity.getLearnTime() == null) {
			Calendar cal = Calendar.getInstance();
			entity.setLearnTime(cal.getTime());
		}
		//保存学习感受
		if (entity.getId() == null) {
			entity.setIsLearn("1");
			filelearnReceiveUserService.addEntity(entity);
		} else {
			entity.setIsLearn("1");
			filelearnReceiveUserService.updateEntity(entity);
		}
		ResultObj resultObj = new ResultObj();
		return resultObj;
	}
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 文件学习表数据
		FileLearnEntity fileLearnEntity = (FileLearnEntity)fileLearnService.findById(id);
		//接收人
		List<Condition> receiveUserConditions = new ArrayList<Condition>();
		receiveUserConditions.add(new Condition("C_FILE_LEARN_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,fileLearnEntity.getId()));
		List<FilelearnReceiveUserEntity> receiveUserList = filelearnReceiveUserService.findByCondition(receiveUserConditions, null);
		String receiveUserIds = new String();
		String receiveUserNames = new String();
		for(FilelearnReceiveUserEntity receiveUser : receiveUserList){
			receiveUserIds += receiveUser.getUserId() + ",";
			receiveUserNames += receiveUser.getUserName() + ",";
		}
		if(!StringUtil.isEmpty(receiveUserIds) && !StringUtil.isEmpty(receiveUserNames)){
			receiveUserIds = receiveUserIds.substring(0, receiveUserIds.length()-1);
			receiveUserNames = receiveUserNames.substring(0, receiveUserNames.length()-1);
		}
		fileLearnEntity.setReceiveUserIds(receiveUserIds);
		fileLearnEntity.setReceiveUserNames(receiveUserNames);
		
		model.put("entity", fileLearnEntity);
		model.put("entityJson", JsonUtil.toJson(fileLearnEntity));
		
		//单位下拉框 add   by  zhangxb   2018/3/1
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		
		//接收人
		List<FileTreeEntity> recipientTree = fileLearnService.getRecipientTree();
		model.put("recipientTree", JsonUtil.toJson(recipientTree));
		return this.createModelAndView("fileLearn/fileLearnEdit", model);
	}
	/**
	 * 
	 * 修改
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @return
	 * @param @throws UnsupportedEncodingException
	 * @param @throws ParseException
	 * @return ResultObj
	 * @throws 
	 * @author haojw
	 * @created 2018年4月10日 上午10:00:19
	 * @lastModified
	 */
	@RequestMapping("/saveEditPage")
	public @ResponseBody ResultObj saveEditPage(@RequestBody FileLearnEntity entity, HttpServletRequest request){
		//修改主表
		fileLearnService.updateEntity(entity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.FILE_LEARN.getName(), OperateUserEnum.EDIT.getName(), "", OperateUserEnum.EDIT.getId());
		//删除接收人
		filelearnReceiveUserService.deleteByFileId(entity.getId());
		//添加接收人
		List<FilelearnReceiveUserEntity> receiveUserList = new ArrayList<FilelearnReceiveUserEntity>();
		String receiveUserIds = entity.getReceiveUserIds();
		if(!StringUtil.isEmpty(receiveUserIds)){
			String [] receiveUserArg = receiveUserIds.split(",");
			for(String id : receiveUserArg){
				FilelearnReceiveUserEntity user = new FilelearnReceiveUserEntity();
				user.setUserId(Long.parseLong(id));
				user.setFileLearnId(entity.getId());
				user.setIsLearn("0");
				receiveUserList.add(user);
			}
		}
		filelearnReceiveUserService.batchInsert(receiveUserList);
		ResultObj resultObj = new ResultObj();
		return resultObj;
	}
	/**
	 * 删除
	 * 
	 * @Title: delete
	 * @Description:
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@Override
	public @ResponseBody ResultObj delete(@PathVariable Long id) {
		fileLearnService.deleteEntity(id);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.FILE_LEARN.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
		filelearnReceiveUserService.deleteByFileId(id);
		ResultObj resultObj = new ResultObj();
		return resultObj;
	}

	/**
	 * 批量删除
	 * 
	 * @Title: delete
	 * @Description:
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/bulkDelete", method = RequestMethod.DELETE)
	@Override
	public @ResponseBody ResultObj bulkDelete(@RequestBody List<Integer> ids) {
		/*List<Long> paramIds = new ArrayList<Long>();
		this.getService().bulkDelete(paramIds.toArray(new Long[paramIds.size()]));*/
		for (Integer id : ids) {
			long longId = (long)id;
			FileLearnEntity t = this.getService().findById(longId);
			if(t != null){
				fileLearnService.deleteEntity(longId);
				SysUserEntity userEntity = RequestContext.get().getUser();
				operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.FILE_LEARN.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
				filelearnReceiveUserService.deleteByFileId(longId);
			}
		}
		ResultObj resultObj = new ResultObj();
		return resultObj;
	}
	
	/**
	 * @Description:   选择下一节点负责人
	 * @author         hjw 
	 * @Date           2018年4月10日 下午7:06:41 
	 * @throws         Exception
	 */
	@RequestMapping(value="/sureSubmitPerson/{processStatus}/{id}")
	public ModelAndView sureSubmitPerson(@PathVariable String processStatus,@PathVariable Long id,HttpServletRequest request){
		//驳回后提交
		if("1".equals(processStatus)){
			TodoTaskEntity todoTaskEntity = getTaskEntityByBusiniseId(id);
			if(null != todoTaskEntity){
				//审批页面点击签发按钮的时候，把下一步的人查询出来
				Map<String, Object> model = new HashMap<String, Object>();
				List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(todoTaskEntity.getId_().toString(),FileLearnExcuteEnum.LEADER.getId().toString());
				model.put("userList", userList);
				return this.createModelAndView("fileLearn/sureSubmitPerson", model);
			}
		}
		//首次提交
		Map<String, Object> model = new HashMap<String, Object>();
		FileLearnEntity fileLearnEntity = fileLearnService.findById(id);
		List<Condition> conditions=new ArrayList<Condition>();
		if(fileLearnEntity.getIsNotification().equals("0")){
			conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.FILE_LEARN_PROCESS_KEY.getName()));
		}else{
			conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.FILE_LEARN_NOTIFICATION_PROCESS.getName()));
		}
		List<DefinitionEntity> defList=definitionService.findByCondition(conditions, null);
		if(!defList.isEmpty()){
			String modelId=defList.get(0).getModelId();
			//审批页面点击签发按钮的时候，把下一步的人查询出来
			SysUserEntity userEntity = RequestContext.get().getUser();
			List<SysUserEntity> userList= nodeConfigService.getFirstNodeTransactor(modelId,"",userEntity);
			model.put("userList", userList);
		}
		return this.createModelAndView("fileLearn/sureSubmitPerson", model);
	}
	
	/**
	 * @Description:   文件学习提交审核
	 * @author         hjw 
	 * @Date           2018年4月10日 下午7:40:31 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/submit/{id}")
	public @ResponseBody ResultObj submit(HttpServletRequest request,@PathVariable Long id,@RequestBody Map<String, Object> params) {
		
		//驳回后提交
		FileLearnEntity fileLearnEntity = (FileLearnEntity)fileLearnService.findById(id);
		if("1".equals(fileLearnEntity.getApproveStatus())){
			TodoTaskEntity todoTaskEntity = getTaskEntityByBusiniseId(id);
			if(todoTaskEntity!=null){
				fileLearnEntity.setTaskId(todoTaskEntity.getId_().toString());
				fileLearnEntity.setProcInstId(todoTaskEntity.getProcInstId());
				Map<String, Object> rebackParams = new HashMap<String, Object>();
				rebackParams.put("status", FileLearnApproveStatusEnum.LEADER.getCode());
				rebackParams.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),params.get("userList"));
				rebackParams.put(BranchMarkEnum.BRANCH_KEY.getName(), FileLearnExcuteEnum.SUBMITAGAIN.getId());
				rebackParams.put(ExamMarkEnum.COMMENT.getCode(), "");
				rebackParams.put("result", FileLearnExcuteEnum.SUBMITAGAIN.getName());
				return fileLearnService.approve(fileLearnEntity, rebackParams);
			}
		}
		
		//首次提交
		return fileLearnService.submit(id,params);
	}
	/**
	 * 文件学习审批页
	 * @author hjw
	 * @created 2018年4月10日 上午10:03:51
	 * @lastModified
	 */
	@RequestMapping("/approve/{id}/{type}")
	public ModelAndView approve(HttpServletRequest request, @PathVariable Long id, @PathVariable String type){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("type", type);
		// 文件学习表数据
				FileLearnEntity fileLearnEntity = (FileLearnEntity)fileLearnService.findById(id);
				//接收人
				List<Condition> receiveUserConditions = new ArrayList<Condition>();
				receiveUserConditions.add(new Condition("C_FILE_LEARN_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,fileLearnEntity.getId()));
				List<FilelearnReceiveUserEntity> receiveUserList = filelearnReceiveUserService.findByCondition(receiveUserConditions, null);
				String receiveUserIds = new String();
				String receiveUserNames = new String();
				for(FilelearnReceiveUserEntity receiveUser : receiveUserList){
					receiveUserIds += receiveUser.getUserId() + ",";
					receiveUserNames += receiveUser.getUserName() + ",";
				}
				if(!StringUtil.isEmpty(receiveUserIds) && !StringUtil.isEmpty(receiveUserNames)){
					receiveUserIds = receiveUserIds.substring(0, receiveUserIds.length()-1);
					receiveUserNames = receiveUserNames.substring(0, receiveUserNames.length()-1);
				}
				fileLearnEntity.setReceiveUserIds(receiveUserIds);
				fileLearnEntity.setReceiveUserNames(receiveUserNames);
				//附件
				List<Condition> fileConditions = new ArrayList<Condition>();
				fileConditions.add(new Condition("C_FILE_LEARN_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,fileLearnEntity.getId()));
				List<FileLearnAttachmentEntity> fileList = fileLearnAttachmentService.findByCondition(fileConditions, null);
				
				
				model.put("entity", fileLearnEntity);
				model.put("entityJson", JsonUtil.toJson(fileLearnEntity));
				
				//单位下拉框 add   by  zhangxb   2018/3/1
				List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
				model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", JsonUtil.toJson(sysUserEntity));
		List<Condition> conditionsLc=new ArrayList<Condition>();
		conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getLoginName()));
		conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		conditionsLc.add(new Condition("task.end_time_ IS NULL"));
		if(fileLearnEntity.getIsNotification().equals("0")){
			conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.FILE_LEARN_PROCESS_KEY.getName()));
		}else{
			conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.FILE_LEARN_NOTIFICATION_PROCESS.getName()));
		}
		
		List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditionsLc, null);
		TodoTaskEntity todoTaskEntity=null;
		if(null != list && !list.isEmpty()){
			todoTaskEntity=list.get(0);
			List<ProcessNodeAuthEntity> nodeList=processNodeAuthService.getAuthorityList(todoTaskEntity.getTaskDefKey());
			model.put("nodeList", nodeList);
		}
		
		return this.createModelAndView("fileLearn/fileLearnApprove",model);
	}
	/**
	 * 领导驳回
	 */
	@RequestMapping("/reject")
	public @ResponseBody ResultObj reject(@RequestBody FileLearnEntity t){
		FileLearnEntity fileLearnEntity = (FileLearnEntity)fileLearnService.findById(t.getId());
		fileLearnEntity.setTaskId(t.getTaskId());
		fileLearnEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", FileLearnApproveStatusEnum.REJECT.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), FileLearnExcuteEnum.BACK_END.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getApproveComment());
		params.put("result", FileLearnExcuteEnum.BACK_END.getName());
		return fileLearnService.approve(fileLearnEntity, params);
	}
	
	/**
	 * 领导同意
	 */
	@RequestMapping("/pass")
	public @ResponseBody ResultObj pass(@RequestBody FileLearnEntity t){
		FileLearnEntity fileLearnEntity = (FileLearnEntity)fileLearnService.findById(t.getId());
		fileLearnEntity.setTaskId(t.getTaskId());
		fileLearnEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", FileLearnApproveStatusEnum.PUBLISH.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), FileLearnExcuteEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getApproveComment());
		params.put("result", FileLearnExcuteEnum.AGREE.getName());
		return fileLearnService.approve(fileLearnEntity, params);
	}
	
	/**
	 * 发布
	 */
	@RequestMapping("/publish")
	public @ResponseBody ResultObj publish(@RequestBody FileLearnEntity filelearnEntity){
		FileLearnEntity fileLearnEntity = (FileLearnEntity)fileLearnService.findById(filelearnEntity.getId());
		fileLearnEntity.setTaskId(filelearnEntity.getTaskId());
		fileLearnEntity.setProcInstId(filelearnEntity.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", FileLearnApproveStatusEnum.END.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),filelearnEntity.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), FileLearnExcuteEnum.PUBLISH.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), filelearnEntity.getApproveComment());
		params.put("result", FileLearnExcuteEnum.PUBLISH.getName());
		MessageCenterEntity messageEntity =new MessageCenterEntity();
		String receiveUserId = filelearnEntity.getReceiveUserIds();
		String[] receivedUserIds = null;
		if(receiveUserId!=null){
			if(receiveUserId.contains(",")){
				receivedUserIds = receiveUserId.split(",");
			}else{
				receivedUserIds = new String[]{receiveUserId};
			}
			if(receivedUserIds!=null){
				if(filelearnEntity.getIsNotification().equals("1")){
					for(String rUserId:receivedUserIds){
						messageEntity.setTitle(fileLearnEntity.getFileName());
						messageEntity.setSendTime(new Date());
						messageEntity.setReceivePerson(rUserId);
						messageEntity.setSendPerson(fileLearnEntity.getSendUserId().toString());
						messageEntity.setContext(fileLearnEntity.getRemark());
						messageEntity.setType("private");
						if(filelearnEntity.getIsImportant().equals("1")){
							messageEntity.setIsnotImportant("1");
						}
						messageCenterService.addMessage(messageEntity); 
					}
				}
			}
		}else{
			if(filelearnEntity.getIsNotification().equals("1")){
				List<SysUserEntity> userLit = sysUserService.findAll();
				for(SysUserEntity sysUserEntity:userLit){
					messageEntity.setTitle(fileLearnEntity.getFileName());
					messageEntity.setSendTime(new Date());
					messageEntity.setReceivePerson(sysUserEntity.getId().toString());
					messageEntity.setSendPerson(fileLearnEntity.getSendUserId().toString());
					messageEntity.setContext(fileLearnEntity.getRemark());
					messageEntity.setType("private");
					if(filelearnEntity.getIsImportant().equals("1")){
						messageEntity.setIsnotImportant("1");
					}
					messageCenterService.addMessage(messageEntity);
				}
			}
		}
		return fileLearnService.approve(fileLearnEntity, params);
	}
	/**
	 * @Description: 再次提交(查询下一步提交人)
	 * @author hjw
	 * @Date 2018年4月10日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping("/submitPersonLeader/{taskId}")
	public ModelAndView submitPersonLeader(HttpServletRequest request,@PathVariable String taskId) {
		
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,FileLearnExcuteEnum.LEADER.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("fileLearn/sureSubmitPerson", model);
	}
	/**
	 * 再次提交
	 */
	@RequestMapping("/submitAgain")
	public @ResponseBody ResultObj submitAgain(@RequestBody FileLearnEntity t){
		FileLearnEntity fileLearnEntity = (FileLearnEntity)fileLearnService.findById(t.getId());
		fileLearnEntity.setTaskId(t.getTaskId());
		fileLearnEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", FileLearnApproveStatusEnum.LEADER.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), FileLearnExcuteEnum.SUBMITAGAIN.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getApproveComment());
		params.put("result", FileLearnExcuteEnum.SUBMITAGAIN.getName());
		return fileLearnService.approve(fileLearnEntity, params);
	}
	
	/**
	 * 
	 * 文件学习消息列表(未读)
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @return
	 * @return ResultListObj
	 * @throws 
	 * @author hjw
	 * @created 2018年4月10日 下午1:31:57
	 * @lastModified
	 */
	@RequestMapping(value = "/noLearnMessList")
	public  @ResponseBody ResultListObj messList(HttpServletRequest request,@RequestBody Map<String, Object> params){
		//获取登录人信息
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		//按照时间倒序排列
		Page<FileLearnEntity> pages = PageUtil.getPage(params);
		pages.addOrder(Sort.asc("C_SEND_TIME"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		//conditions.add(new Condition("C_RECEIVE_PERSON",FieldTypeEnum.STRING,MatchTypeEnum.EQ,sysUserEntity.getId()));
		//conditions.add(new Condition("C_IS_LEARN",FieldTypeEnum.STRING,MatchTypeEnum.EQ,'0'));
		Map<String,Object> otherParams = new HashMap<String,Object>();
		otherParams.put("receiveUserId", sysUserEntity.getId());
		List<FileLearnEntity> messList = fileLearnService.findByCondition("findNotLearnMessByCondition",conditions,otherParams, pages);
		//获得返回结果
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (messList != null) {
			if (pages != null) {
				resultObj.setData(messList);
				resultObj.setRecordsTotal(pages.getTotal());
			}
		}
		return resultObj;
	}
	/**
	 *	文件学习消息列表页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/allLearnMessIndex")
	public ModelAndView allLearnMessIndex(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 部门下拉树
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		for (int i = 0; i < treeNodeList.size(); i++) {
			treeNodeList.get(i).setOpen("true");
		}
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		//获取登录人信息
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		
		return this.createModelAndView("fileLearn/fileLearnMessList", model);
	}
	/**
	 * 
	 * 文件学习消息列表（全部）
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @return
	 * @return ResultListObj
	 * @throws 
	 * @author hjw
	 * @created 2018年4月10日 下午1:31:57
	 * @lastModified
	 */
	@RequestMapping(value = "/allLearnMessList")
	public  @ResponseBody ResultListObj allLearnMessList(HttpServletRequest request,@RequestBody Map<String, Object> params){
		//获取登录人信息
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		//按照时间倒序排列
		Page<FileLearnEntity> pages = PageUtil.getPage(params);
		pages.setOrders(OrmUtil.changeMapToOrders(params));
		pages.addOrder(Sort.desc("C_SEND_TIME"));
		
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		//conditions.add(new Condition("C_RECEIVE_PERSON",FieldTypeEnum.STRING,MatchTypeEnum.EQ,sysUserEntity.getId()));
		//conditions.add(new Condition("C_IS_LEARN",FieldTypeEnum.STRING,MatchTypeEnum.EQ,'0'));
		Map<String,Object> otherParams = new HashMap<String,Object>();
		otherParams.put("receiveUserId", sysUserEntity.getId());
		List<FileLearnEntity> messList = fileLearnService.findByCondition("findAllMessByCondition",conditions,otherParams, pages);
		//获得返回结果
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (messList != null) {
			if (pages != null) {
				resultObj.setData(messList);
				resultObj.setRecordsTotal(pages.getTotal());
			}
		}
		return resultObj;
	}
	/**
	 * 查看后修改学习状态
	 * 
	 * @Title: updateLearnStatus
	 * @Description:
	 * @param id
	 * @return 
	 * @return
	 */
	/*@RequestMapping("/updateLearnStatus/{id}")*/
	public FilelearnReceiveUserEntity updateLearnStatus(Long id){
		FilelearnReceiveUserEntity ru = null;
		//获取登录人信息
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		// 文件学习表数据
		FileLearnEntity fileLearnEntity = (FileLearnEntity)fileLearnService.findById(id);
		//接收人
		List<Condition> receiveUserConditions = new ArrayList<Condition>();
		receiveUserConditions.add(new Condition("C_FILE_LEARN_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,fileLearnEntity.getId()));
		receiveUserConditions.add(new Condition("C_USER_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,sysUserEntity.getId()));
		List<FilelearnReceiveUserEntity> receiveUserList = filelearnReceiveUserService.findByCondition(receiveUserConditions, null);
		if("4".equals(fileLearnEntity.getApproveStatus())){
			if("1".equals(fileLearnEntity.getIsAllNet()) && (receiveUserList == null || receiveUserList.isEmpty())){
				//插入已学习
				ru = new FilelearnReceiveUserEntity();
				ru.setFileLearnId(fileLearnEntity.getId());
				ru.setIsLearn("1");
				ru.setLearnTime(new Date());
				ru.setUserId(sysUserEntity.getId());
				ru.setUserName(sysUserEntity.getName());
				ru.setLearnTime(new Date());
				filelearnReceiveUserService.addEntity(ru);
			}else if("0".equals(fileLearnEntity.getIsAllNet()) && (receiveUserList != null && !receiveUserList.isEmpty()) && "0".equals(receiveUserList.get(0).getIsLearn()) ){
				//更新学习状态
				ru = receiveUserList.get(0);
				ru.setIsLearn("1");
				ru.setLearnTime(new Date());
				filelearnReceiveUserService.updateEntity(ru);
			}else if(receiveUserList != null && !receiveUserList.isEmpty() ){
				ru = receiveUserList.get(0);
			}
		}
		if (ru != null) {
			if(ru.getAttachment() == null){
				ru.setAttachment("[]");
			}
		}
		return ru;
	}
	/**
	 *	跳转到接收人查看页面
	 */
	@RequestMapping("/getReceiverShow/{id}")
	public ModelAndView getReceiverShow(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		FilelearnReceiveUserEntity filelearnReceiveUserEntity = (FilelearnReceiveUserEntity)filelearnReceiveUserService.findById(id);
		if(filelearnReceiveUserEntity.getAttachment()==null||filelearnReceiveUserEntity.getAttachment()==""){
			filelearnReceiveUserEntity.setAttachment("[]");
		}
		model.put("entity", filelearnReceiveUserEntity);
		model.put("entityJson", JsonUtil.toJson(filelearnReceiveUserEntity));
		
		return this.createModelAndView("fileLearn/filelearnReceiveShow", model);
	}
	/**
	 * 获取当前流程节点信息
	 * @param id 业务数据id
	 * @return
	 */
	public TodoTaskEntity getTaskEntityByBusiniseId(Long id){
		//流程节点信息
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		List<Condition> conditionsLc=new ArrayList<Condition>();
		conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getLoginName()));
		conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.FILE_LEARN_PROCESS_KEY.getName()));
		List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditionsLc, null);
		TodoTaskEntity todoTaskEntity=null;
		if(null != list && !list.isEmpty()){
			todoTaskEntity=list.get(list.size()-1);
		}
		return todoTaskEntity;
	}
	
	@RequestMapping(value = "/getRecipientTree")
	public  @ResponseBody ResultListObj getRecipientTree(HttpServletRequest request){
		List<FileTreeEntity> list = fileLearnService.getRecipientTree();
		//获得返回结果
		ResultListObj resultObj = new ResultListObj();
		if (list != null) {
				resultObj.setData(list);
		}
		return resultObj;
	}
	
	/**
	 * 导出
	 * @param req
	 * @param res
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		String conditionsChan=req.getParameter("conditions");
		params.put("conditions", JSONArray.fromObject(conditionsChan));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		Page<FileLearnEntity> page=new Page<FileLearnEntity>();
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("sendTime"));
		page.setPageSize(Integer.MAX_VALUE);
        ////////////sml添加开始
		List<Condition> userConditions = new ArrayList<Condition>();
		userConditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "85"));
		List<SysDutiesDetailEntity> sysDutiesDetailList = sysDutiesDetailService.findByCondition(userConditions, null);
		List<String> userUnitIds = new ArrayList<String>();
		for(SysDutiesDetailEntity sysDutiesDetailEntity : sysDutiesDetailList){
			userUnitIds.add(sysDutiesDetailEntity.getUserUnitRelId());
		}
		userConditions.clear();
		userConditions.add(new Condition("C_ID", FieldTypeEnum.STRING, MatchTypeEnum.IN, userUnitIds.toArray()));
		List<UserUnitRelEntity> userUnitRelList = userUnitRelService.findByCondition(userConditions, null);
		List<String> userIds = new ArrayList<String>();
		for(UserUnitRelEntity userUnitRelEntity : userUnitRelList){
			userIds.add(userUnitRelEntity.getUserId().toString());
		}
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		String userId = sysUserEntity.getId().toString();
		List<FileLearnEntity> list = new ArrayList<FileLearnEntity>();
		if(userIds.contains(userId)){
			list = fileLearnService.findByCondition(conditions, page);
		}else{
			conditions.add(new Condition("C_SEND_USER_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userId));
			list = fileLearnService.findByCondition(conditions, page);
		}
		/////////////////添加结束
//		List<FileLearnEntity> list = fileLearnService.findByCondition(conditions, page);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", list);
		ExcelUtil.export(req, res, "文件学习模板.xlsx","文件学习.xlsx", resultMap);
	}
	/**
	 * 导出
	 * @param req
	 * @param res
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/exportReleaseExcel")
	public void exportReleaseExcel(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		String conditionsChan=req.getParameter("conditions");
		params.put("conditions", JSONArray.fromObject(conditionsChan));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		Page<FileLearnEntity> page=new Page<FileLearnEntity>();
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("sendTime"));
		page.setPageSize(Integer.MAX_VALUE);
		List<FileLearnEntity> list = fileLearnService.findByCondition(conditions, page);

		Map<String,Object> resultMap=new HashMap<String, Object>();
		
		resultMap.put("dataList", list);

		ExcelUtil.export(req, res, "挂网文件学习模板.xlsx","挂网文件学习.xlsx", resultMap);
	}
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/releaseIndex")
	public ModelAndView releaseIndex(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 部门下拉树
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		for (int i = 0; i < treeNodeList.size(); i++) {
			treeNodeList.get(i).setOpen("true");
		}
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		return this.createModelAndView("fileLearn/releaseFileLearnList", model);
	}
	
}