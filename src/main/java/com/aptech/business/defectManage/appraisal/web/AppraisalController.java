package com.aptech.business.defectManage.appraisal.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.aptech.business.defectManage.defect.domain.DefectEntity;
import com.aptech.business.defectManage.defect.service.DefectService;
import com.aptech.business.defectManage.defectEquipment.domain.DefectEquipmentEntity;
import com.aptech.business.defectManage.defectEquipment.service.DefectEquipmentService;
import com.aptech.business.messageCenter.domain.MessageCenterEntity;
import com.aptech.business.messageCenter.service.MessageCenterService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.dictionary.domain.SysDictionaryEntity;
import com.aptech.common.system.dictionary.service.SysDictionaryService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.duties.domain.SysDutiesEntity;
import com.aptech.common.system.duties.service.SysDutiesService;
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
@RequestMapping("/appraisal")
public class AppraisalController extends BaseController<AppraisalEntity> {

	@Autowired
	private AppraisalService appraisalService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private DefectService defectService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private MessageCenterService messageCenterService;
	@Autowired
	private SysDutiesService sysDutiesService;
	@Autowired
	private SysDutiesDetailService sysDutiesDetailService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private DefectEquipmentService defectEquipmentService;
	@Autowired
	private UserUnitRelService userUnitRelService;
	@Override
	public IBaseEntityOperation<AppraisalEntity> getService() {
		return appraisalService;
	}

	@RequestMapping("/index")
	public ModelAndView list(HttpServletRequest request,
			Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		return this.createModelAndView("defectManage/appraisal/appraisalList",
				model);
	}
	/**
	 * @Description:   检修专工鉴定
	 * @author         changl 
	 * @Date           2017年6月21日 上午9:19:29 
	 * @throws         Exception
	 */
	@RequestMapping("/getAddOverhaul")
	public ModelAndView getAddOverhaul(HttpServletRequest request,DefectEntity defectEntity) {
		Map<String, Object> model = new HashMap<String, Object>();
		DefectEntity defect = defectService.findById(defectEntity.getId());
		model.put("defect", defect);
		model.put("defectEntity", defectEntity);
		// 部门
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		// TODO下拉树具体内容根据具体业务定制
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
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
		List<SysUserEntity> userList1=nodeConfigService.getNextNodeTransactor(defectEntity.getTaskId(),ExamResultEnum.AGREE.getId().toString());
		model.put("userList1", userList1);
		// 缺陷类型
		Map<String, SysDictionaryVO> typeMap = DictionaryUtil
						.getDictionaries("DEFECT_TYPE");
		ComboboxVO searchtype=getTypeMap(typeMap);
		model.put("type", JsonUtil.toJson(searchtype.getOptions()));
		
		Map<String, SysDictionaryVO> repeatTypeMap = DictionaryUtil
				.getDictionaries("REPEAT_TYPE");
		ComboboxVO repeatType=new  ComboboxVO();
		for(String key :  repeatTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = repeatTypeMap.get(key);
        	repeatType.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
		model.put("repeatType", JsonUtil.toJson(repeatType.getOptions()));
		
		
		Map<String, SysDictionaryVO> resultTypeMap = DictionaryUtil
				.getDictionaries("APPRAISAL_RESULT");
		ComboboxVO resultType=new  ComboboxVO();
		for(String key :  resultTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = resultTypeMap.get(key);
        	resultType.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
		model.put("resultType", JsonUtil.toJson(resultType.getOptions()));
		model.put("appraisalTime", new Date());
		model.put("reportTime", new Date());
		return this.createModelAndView("defectManage/appraisal/appraisalAddOverhaul",
				model);
	}
	/**
	 * @Description:   检修主任鉴定
	 * @author         changl 
	 * @Date           2017年6月21日 上午9:19:29 
	 * @throws         Exception
	 */
	@RequestMapping("/getAddMonitor")
	public ModelAndView getAddMonitor(HttpServletRequest request,DefectEntity defectEntity) {
		Map<String, Object> model = new HashMap<String, Object>();
		DefectEntity t=defectService.findById(defectEntity.getId());
//		Integer examResult=0;
//		if(t.getType().equals("3")||t.getType().equals("4")){
//			 examResult=ExamResultEnum.AGREE.getId();
//		}else{
//			 examResult=ExamResultEnum.BACK_END.getId();
//		}
		List<SysUserEntity> userList1=nodeConfigService.getNextNodeTransactor(defectEntity.getTaskId(),ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList1);
//		List<SysUserEntity> userList2=nodeConfigService.getNextNodeTransactor(defectEntity.getTaskId(),ExamResultEnum.BACK.getId().toString());
//		model.put("userList2", userList2);
		return this.createModelAndView("defectManage/appraisal/appraisalAddMonitor",
				model);
	}
	/**
	 * @Description:   生技部主任鉴定
	 * @author         changl 
	 * @Date           2017年6月21日 上午9:19:29 
	 * @throws         Exception
	 */
	@RequestMapping("/getAddBiotech")
	public ModelAndView getAddBiotech(HttpServletRequest request,DefectEntity defectEntity) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList1=nodeConfigService.getNextNodeTransactor(defectEntity.getTaskId(),ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList1);
		List<SysUserEntity> userList2=nodeConfigService.getNextNodeTransactor(defectEntity.getTaskId(),ExamResultEnum.BACK.getId().toString());
		model.put("userList2", userList2);
		return this.createModelAndView("defectManage/appraisal/appraisalAddBiotech",
				model);
	}/**
	 * @Description:   总工、生产分管领导鉴定
	 * @author         changl 
	 * @Date           2017年6月21日 上午9:19:29 
	 * @throws         Exception
	 */
	@RequestMapping("/getAddEngineer")
	public ModelAndView getAddEngineer(HttpServletRequest request,DefectEntity defectEntity) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList1=nodeConfigService.getNextNodeTransactor(defectEntity.getTaskId(),ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList1);
		List<SysUserEntity> userList2=nodeConfigService.getNextNodeTransactor(defectEntity.getTaskId(),ExamResultEnum.BACK.getId().toString());
		model.put("userList2", userList2);
		return this.createModelAndView("defectManage/appraisal/appraisalAddEngineer",
				model);
	}
	/**
	 * @Description:   检修专工鉴定：是缺陷
	 * @author         changl 
	 * @Date           2017年6月15日 下午4:50:08 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/monitor")
	public @ResponseBody ResultObj monitor(@RequestBody AppraisalEntity t, HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", DefectStatusEnum.MONITOR.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());
		params.put(ExamMarkEnum.COMMENT.getCode(),t.getAppraisalOpinions()==null?"":t.getAppraisalOpinions());//审批意见
		SysUserEntity userEntity = RequestContext.get().getUser();
		List<Condition> statusCondition = new ArrayList<Condition>();
		DefectEntity defectEntity =defectService.findById(Long.valueOf(t.getDefectId()));
		statusCondition.add(new Condition("C_DEFECT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, defectEntity.getId()));
		List<DefectEquipmentEntity> defectEquipmentList = defectEquipmentService.findByCondition(statusCondition, null);
		if(defectEquipmentList!=null && defectEquipmentList.size()>0){
			for(DefectEquipmentEntity defectEquipmentEntity : defectEquipmentList){
				defectEquipmentEntity.setStatus(DefectStatusEnum.MONITOR.getCode());
				defectEquipmentEntity.setEquipType(t.getGrade());
				defectEquipmentEntity.setAppraisalResult(t.getAppraisalResult());
				defectEquipmentService.updateEntity(defectEquipmentEntity);
			}
			
		}
		return appraisalService.approve(t,params);
	}
	/**
	 * @Description:   检修专工鉴定：不是缺陷
	 * @author         changl 
	 * @Date           2017年6月15日 下午4:50:08 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/function")
	public @ResponseBody ResultObj function(@RequestBody AppraisalEntity t, HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", DefectStatusEnum.NODEFECT.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.BACK.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.BACK.getName());
		params.put(ExamMarkEnum.COMMENT.getCode(),t.getAppraisalOpinions()==null?"":t.getAppraisalOpinions());//审批意见
		return appraisalService.approve(t,params);
	}
	/**
	 * @Description:   鉴定：驳回
	 * @author         changl 
	 * @Date           2017年6月15日 下午4:50:08 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/reject")
	public @ResponseBody ResultObj reject(@RequestBody AppraisalEntity t, HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", DefectStatusEnum.BIOTECH_APPROVE.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.BACK.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), DefectStatusEnum.BIOTECH_APPROVE.getName());
		params.put(ExamMarkEnum.COMMENT.getCode(),t.getApprovalOpinions()==null?"":t.getApprovalOpinions());//审批意见
		DefectEntity defectEntity = defectService.findById(Long.valueOf(t.getDefectId()));
		List<Condition> statusCondition = new ArrayList<Condition>();
		statusCondition.add(new Condition("C_DEFECT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, defectEntity.getId()));
		List<DefectEquipmentEntity> defectEquipmentList = defectEquipmentService.findByCondition(statusCondition, null);
		if(defectEquipmentList!=null && defectEquipmentList.size()>0){
			for(DefectEquipmentEntity defectEquipmentEntity : defectEquipmentList){
				defectEquipmentEntity.setStatus(DefectStatusEnum.BIOTECH_APPROVE.getCode());
				defectEquipmentService.updateEntity(defectEquipmentEntity);
			}
		}
		return appraisalService.approveStatus(t,params);
	}
	/**
	 * @Description:   检修主任鉴定：同意
	 * @author         changl 
	 * @Date           2017年6月15日 下午4:50:08 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/biotech")
	public @ResponseBody ResultObj biotech(@RequestBody AppraisalEntity t, HttpServletRequest request) {
		DefectEntity defectEntity=defectService.findById(Long.parseLong(t.getDefectId()));
		defectEntity.setDefectTime(new Date());
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		defectEntity.setSolveUserId(sysUserEntity.getId());
		
		Map<String, Object> params = new HashMap<String, Object>();
		
//		if(defectEntity.getType().equals("3")||defectEntity.getType().equals("4")){
			params.put("status", DefectStatusEnum.BIOTECH.getCode());
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
			params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());
//		}else{
//			params.put("status", DefectStatusEnum.IMPLEMENT.getCode());
//			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.BACK_END.getId());
//			params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.BACK_END.getName());
//		}
		params.put(ExamMarkEnum.COMMENT.getCode(),t.getApprovalOpinions()==null?"":t.getApprovalOpinions());//审批意见
		List<Condition> statusCondition = new ArrayList<Condition>();
		statusCondition.add(new Condition("C_DEFECT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, defectEntity.getId()));
		List<DefectEquipmentEntity> defectEquipmentList = defectEquipmentService.findByCondition(statusCondition, null);
		if(defectEquipmentList!=null && defectEquipmentList.size()>0){
			for(DefectEquipmentEntity defectEquipmentEntity : defectEquipmentList){
				defectEquipmentEntity.setStatus(DefectStatusEnum.BIOTECH.getCode());
				defectEquipmentService.updateEntity(defectEquipmentEntity);
			}
		}
		List<SysUserEntity> copyUserList=nodeConfigService.getNextNodeCopyTransactor(t.getTaskId(), ExamResultEnum.AGREE.getId().toString());
		String copyUser = "";
		String setCopyUserIds = "";
		if (copyUserList != null && copyUserList.size() > 0) {
			for (int i = 0;i<copyUserList.size(); i++) {
				SysUserEntity userEntity  = copyUserList.get(i);
				copyUser += userEntity.getLoginName();
				setCopyUserIds += userEntity.getId().toString();
				if (i != copyUserList.size() -1) {
					copyUser += ",";
					setCopyUserIds += ",";
				}
			} 
		}
		
		defectEntity.setCopyUserIds(setCopyUserIds);
		defectService.updateEntity(defectEntity);
		//抄送跳转
		if (copyUser.length() > 0) {
			params.put(CandidateMarkEnum.COPY_HANDLERS.getName(), copyUser);
		}
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		return appraisalService.approveStatus(t,params);
	}
	/**
	 * @Description:  生技部主任鉴定：同意
	 * @author         changl 
	 * @Date           2017年6月15日 下午4:50:08 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/engineer")
	public @ResponseBody ResultObj engineer(@RequestBody AppraisalEntity t, HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put("status", DefectStatusEnum.ENGINEER.getCode());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());
//		params.put("event", "审批意见");
		params.put(ExamMarkEnum.COMMENT.getCode(),t.getApprovalOpinions()==null?"":t.getApprovalOpinions());//审批意见
		List<Condition> statusCondition = new ArrayList<Condition>();
		statusCondition.add(new Condition("C_DEFECT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, t.getId()));
		List<DefectEquipmentEntity> defectEquipmentList = defectEquipmentService.findByCondition(statusCondition, null);
		if(defectEquipmentList!=null && defectEquipmentList.size()>0){
			DefectEquipmentEntity defectEquipmentEntity = defectEquipmentList .get(0);
			defectEquipmentEntity.setStatus(DefectStatusEnum.ENGINEER.getCode());
			defectEquipmentService.updateEntity(defectEquipmentEntity);
		}
		return appraisalService.approveStatus(t,params);
	}
	/**
	 * @Description:  总工主任鉴定：同意
	 * @author         changl 
	 * @Date           2017年6月15日 下午4:50:08 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/implement")
	public @ResponseBody ResultObj implement(@RequestBody AppraisalEntity t, HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		boolean finish=actTaskService.isParallelEndTask(t.getTaskId());
		if(finish){
			Object status= actTaskService.getVarialble(t.getTaskId(), BranchMarkEnum.PARLLEL_BRANCH_KEY.getName());
			if(String.valueOf(status).equals(String.valueOf(ExamResultEnum.BACK.getId()))){
				params.put("status", DefectStatusEnum.OVERHAUL.getCode());
			}else{
				params.put("status", DefectStatusEnum.IMPLEMENT.getCode());
			}
		}
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());
		params.put(ExamMarkEnum.COMMENT.getCode(),t.getApprovalOpinions()==null?"":t.getApprovalOpinions());//审批意见
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		return appraisalService.approveStatus(t,params);
	}
	private ComboboxVO  getTypeMap(Map<String, SysDictionaryVO> typeMap ){
		ComboboxVO searchtype = new ComboboxVO();
		List<Map.Entry<String, SysDictionaryVO>> list = new ArrayList<Map.Entry<String, SysDictionaryVO>>(typeMap.entrySet()); 
		  Collections.sort(list, new Comparator<Map.Entry<String, SysDictionaryVO>>() { 
		      public int compare(Map.Entry<String, SysDictionaryVO> o1, Map.Entry<String, SysDictionaryVO> o2) { 
		          return (o1.getKey()).toString().compareTo(o2.getKey()); 

		      } 

		  }); 
		for (Map.Entry<String, SysDictionaryVO> entry : list) {
			SysDictionaryVO sysDictionaryVO = entry.getValue();
			searchtype.addOption(sysDictionaryVO.getCode(),
					sysDictionaryVO.getName());

		}
		return searchtype;
	}
}