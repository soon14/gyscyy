package com.aptech.business.defectManage.check.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.component.dictionary.DefectStatusEnum;
import com.aptech.business.defectManage.appraisal.domain.AppraisalEntity;
import com.aptech.business.defectManage.appraisal.service.AppraisalService;
import com.aptech.business.defectManage.check.domain.CheckEntity;
import com.aptech.business.defectManage.check.service.CheckService;
import com.aptech.business.defectManage.defect.domain.DefectEntity;
import com.aptech.business.defectManage.defect.service.DefectService;
import com.aptech.business.defectManage.defectEquipment.domain.DefectEquipmentEntity;
import com.aptech.business.defectManage.defectEquipment.service.DefectEquipmentService;
import com.aptech.business.messageCenter.domain.MessageCenterEntity;
import com.aptech.business.messageCenter.service.MessageCenterService;
import com.aptech.common.system.dictionary.domain.SysDictionaryEntity;
import com.aptech.common.system.dictionary.service.SysDictionaryService;
import com.aptech.common.system.duties.domain.SysDutiesEntity;
import com.aptech.common.system.dutiesdetail.domain.SysDutiesDetailEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.system.userUnit.domain.UserUnitRelEntity;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.workflow.modelEditor.domain.BranchMarkEnum;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.common.workflow.modelEditor.service.NodeConfigService;
import com.aptech.common.workflow.todoTask.domain.ExamMarkEnum;
import com.aptech.common.workflow.todoTask.domain.ExamResultEnum;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

@Controller
@RequestMapping("/check")
public class CheckController extends BaseController<CheckEntity> {

	@Autowired
	private CheckService checkService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Autowired
	private DefectEquipmentService defectEquipmentService;
	@Autowired
	private DefectService defectService;
	@Autowired
	private AppraisalService appraisalService;
	@Autowired
	private MessageCenterService messageCenterService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Override
	public IBaseEntityOperation<CheckEntity> getService() {
		return checkService;
	}

	@RequestMapping("/index")
	public ModelAndView list(HttpServletRequest request,
			Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		return this.createModelAndView("defectManage/check/checkList", model);
	}

	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request,DefectEntity defectEntity) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("defectEntity", defectEntity);
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		List<SysUserEntity> userList = sysUserService.findByCondition(
				new ArrayList<Condition>(), null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("date", new Date());
		userList=nodeConfigService.getNextNodeTransactor(defectEntity.getTaskId(),ExamResultEnum.BACK.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("defectManage/check/checkAdd", model);
	}
	/**
	 * @Description:   验收:同意(已消缺)
	 * @author         changl 
	 * @Date           2017年6月15日 下午4:44:02 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/solve")
	public @ResponseBody ResultObj solve(@RequestBody CheckEntity t, HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		String approveIdea = request.getParameter("approve");
		params.put("status", DefectStatusEnum.SOLVE.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());
		params.put("event", "审批意见");
		params.put(ExamMarkEnum.COMMENT.getCode(),approveIdea==null?"":approveIdea);//审批意见
		List<Condition> statusCondition = new ArrayList<Condition>();
		DefectEntity defectEntity = defectService.findById(Long.valueOf(t.getDefectId()));
		statusCondition.add(new Condition("C_DEFECT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, defectEntity.getId()));
		List<DefectEquipmentEntity> defectEquipmentList = defectEquipmentService.findByCondition(statusCondition, null);
		if(defectEquipmentList!=null && defectEquipmentList.size()>0){
			for(DefectEquipmentEntity defectEquipmentEntity : defectEquipmentList){
				defectEquipmentEntity.setStatus(DefectStatusEnum.SOLVE.getCode());
				defectEquipmentService.updateEntity(defectEquipmentEntity);
			}
			
		}
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("T.C_DEFECT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, defectEntity.getId()));
		List<AppraisalEntity> appraisalList = appraisalService.findByCondition(conditions, null);
		AppraisalEntity appraisalEntity = appraisalList.get(0);
		conditions.clear();
		conditions.add(new Condition("C_CATEGORY_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "DEFECT_TYPE"));
		conditions.add(new Condition("C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, appraisalEntity.getGrade()));
		List<SysDictionaryEntity> sysDictionaryList = sysDictionaryService.findByCondition(conditions, null);
		if(appraisalEntity.getGrade()!=null){
		SysDictionaryEntity sysDictionaryEntity = sysDictionaryList.get(0);
			if(appraisalEntity.getGrade().equals("3")||appraisalEntity.getGrade().equals("4")){
				String setCopyUserIds = defectEntity.getCopyUserIds();
				if (setCopyUserIds != null && setCopyUserIds.length()> 0) {
					String [] uids = setCopyUserIds.split(",");
					for (String uid: uids) {
						MessageCenterEntity messageEntity =new MessageCenterEntity();
						messageEntity.setTitle("缺陷管理已消缺");
						messageEntity.setSendTime(new Date());
						messageEntity.setReceivePerson(uid);
						messageEntity.setSendPerson(uid);
						messageEntity.setContext("<a href='#' onclick='goOverCopyTaskList()'>" +"设备"+defectEntity.getEquipName()+"缺陷类型为"+sysDictionaryEntity.getName()+  "已消缺</a>");
						messageEntity.setType("private");
						messageCenterService.addMessage(messageEntity); 
					} 
				}
				
			}
			}
		return checkService.approve(t,params);
	}
	/**
	 * @Description:   验收:驳回(验收驳回)
	 * @author         changl 
	 * @Date           2017年6月15日 下午4:44:02 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/checkReject")
	public @ResponseBody ResultObj checkReject(@RequestBody CheckEntity t, HttpServletRequest request) {
		String approveIdea = request.getParameter("approve");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", DefectStatusEnum.IMPLEMENT.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.BACK.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.BACK.getName());
		params.put("event", "审批意见");
		params.put(ExamMarkEnum.COMMENT.getCode(),approveIdea==null?"":approveIdea);//审批意见
		return checkService.approve(t,params);
	}
}