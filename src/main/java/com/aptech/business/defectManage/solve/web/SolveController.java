package com.aptech.business.defectManage.solve.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.component.dictionary.DefectStatusEnum;
import com.aptech.business.defectManage.defect.domain.DefectEntity;
import com.aptech.business.defectManage.defect.service.DefectService;
import com.aptech.business.defectManage.defectEquipment.domain.DefectEquipmentEntity;
import com.aptech.business.defectManage.defectEquipment.service.DefectEquipmentService;
import com.aptech.business.defectManage.solve.domain.SolveEntity;
import com.aptech.business.defectManage.solve.service.SolveService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
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
@RequestMapping("/solve")
public class SolveController extends BaseController<SolveEntity> {

	@Autowired
	private SolveService solveService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Autowired
	private DefectService defectService;
	@Autowired
	private ActTaskService  actTaskService;
	@Autowired
	private DefectEquipmentService defectEquipmentService;
	@Override
	public IBaseEntityOperation<SolveEntity> getService() {
		return solveService;
	}

	@RequestMapping("/index")
	public ModelAndView list(HttpServletRequest request,
			Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		return this.createModelAndView("defectManage/solve/solveList", model);
	}

	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request,
			DefectEntity defectEntity) {
		
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
		// 缺陷类型
		ComboboxVO solveResult = new ComboboxVO();
		Map<String, SysDictionaryVO> solveResultMap = DictionaryUtil
				.getDictionaries("SOLVE_RESULT");
		for (String key : solveResultMap.keySet()) {
			SysDictionaryVO sysDictionaryVO = solveResultMap.get(key);
			solveResult.addOption(sysDictionaryVO.getCode(),
					sysDictionaryVO.getName());
			
		}
		model.put("solveResult", JsonUtil.toJson(solveResult.getOptions()));
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("date", new Date());
		Integer examResult=0;
//		if(t.getType().equals("3")||t.getType().equals("4")){
			 examResult=ExamResultEnum.AGREE.getId();
//		}else{
//			 examResult=ExamResultEnum.BACK.getId();
//		}
		userList=nodeConfigService.getNextNodeTransactor(defectEntity.getTaskId(),examResult.toString());
		model.put("userList", userList);
		return this.createModelAndView("defectManage/solve/solveAdd", model);
	}
	/**
	 * @Description:   处理提交(待验收)
	 * @author         changl 
	 * @Date           2017年6月15日 下午4:44:02 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/check")
	public @ResponseBody ResultObj check(@RequestBody SolveEntity t, HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(!t.getSolveResult().equals("1")){
			 params.put("status", DefectStatusEnum.MONITOR.getCode());
			 params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
			 params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());
		}else{
			 params.put("status", DefectStatusEnum.CHECK.getCode());
			 params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.BACK.getId());
			 params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.BACK.getName());
		}
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put("event", "审批意见");
		params.put(ExamMarkEnum.COMMENT.getCode(),t.getApproveIdea()==null?"":t.getApproveIdea());//审批意见
		DefectEntity defectEntity = defectService.findById(Long.valueOf(t.getDefectId()));
		defectEntity.setDefectTime(t.getSolveDate());
		defectService.updateEntity(defectEntity);
		return solveService.approve(t,params);
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
		Integer examResult=0;
		if(t.getType().equals("3")||t.getType().equals("4")){
			 examResult=ExamResultEnum.AGREE.getId();
		}else{
			 examResult=ExamResultEnum.BACK_END.getId();
		}
		List<SysUserEntity> userList1=nodeConfigService.getNextNodeTransactor(defectEntity.getTaskId(),examResult.toString());
		model.put("userList", userList1);
		List<SysUserEntity> userList2=nodeConfigService.getNextNodeTransactor(defectEntity.getTaskId(),ExamResultEnum.BACK.getId().toString());
		model.put("userList2", userList2);
		return this.createModelAndView("defectManage/solve/solveAddMonitor",
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
		return this.createModelAndView("defectManage/solve/solveAddBiotech",
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
		return this.createModelAndView("defectManage/solve/solveAddEngineer",
				model);
	}
	/**
	 * @Description:   审批：驳回
	 * @author         changl 
	 * @Date           2017年6月15日 下午4:50:08 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/reject")
	public @ResponseBody ResultObj reject(@RequestBody SolveEntity t, HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		String approveIdea = request.getParameter("approve");
		params.put("status", DefectStatusEnum.IMPLEMENT.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.BACK.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.BACK.getName());
		params.put("event", "审批意见");
		params.put(ExamMarkEnum.COMMENT.getCode(),t.getApproveIdea()==null?"":t.getApproveIdea());//审批意见
		return solveService.approveStatus(t,params);
	}
	/**
	 * @Description:   检修主任审批：同意
	 * @author         changl 
	 * @Date           2017年6月15日 下午4:50:08 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/biotech")
	public @ResponseBody ResultObj biotech(@RequestBody SolveEntity t, HttpServletRequest request) {
		DefectEntity defectEntity=defectService.findById(Long.parseLong(t.getDefectId()));
		String approveIdea = request.getParameter("approve");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		if(defectEntity.getType().equals("3")||defectEntity.getType().equals("4")){
			defectEntity.setDefectTime(new Date());
			params.put("status", DefectStatusEnum.BIOTECH_APPROVE.getCode());
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
			params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());
			defectService.updateEntity(defectEntity);
		}else{
			params.put("status", DefectStatusEnum.IMPLEMENT.getCode());
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.BACK_END.getId());
			params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.BACK_END.getName());
			defectEntity.setDefectTime(new Date());
			defectService.updateEntity(defectEntity);
		}
		params.put(ExamMarkEnum.COMMENT.getCode(),t.getApproveIdea()==null?"":t.getApproveIdea());//审批意见
		return solveService.approveStatus(t,params);
	}
	/**
	 * @Description:  生技部主任审批：解除挂起
	 * @author         changl 
	 * @Date           2017年6月15日 下午4:50:08 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/engineer")
	public @ResponseBody ResultObj engineer(@RequestBody SolveEntity t, HttpServletRequest request) {
		DefectEntity defectEntity=defectService.findById(Long.parseLong(t.getDefectId()));
		defectEntity.setDefectTime(new Date());
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		defectEntity.setSolveUserId(sysUserEntity.getId());
		defectService.updateEntity(defectEntity);
		Map<String, Object> params = new HashMap<String, Object>();
		String approveIdea = request.getParameter("approve");
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put("status", DefectStatusEnum.MONITOR.getCode());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());
		params.put("event", "审批意见");
		params.put(ExamMarkEnum.COMMENT.getCode(),t.getApproveIdea()==null?"":t.getApproveIdea());//审批意见
		List<Condition> statusCondition = new ArrayList<Condition>();
		statusCondition.add(new Condition("C_DEFECT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, defectEntity.getId()));
		List<DefectEquipmentEntity> defectEquipmentList = defectEquipmentService.findByCondition(statusCondition, null);
		if(defectEquipmentList!=null && defectEquipmentList.size()>0){
			for(DefectEquipmentEntity defectEquipmentEntity : defectEquipmentList){
				defectEquipmentEntity.setStatus(DefectStatusEnum.BIOTECH.getCode());
				defectEquipmentService.updateEntity(defectEquipmentEntity);
			}
		}
		return solveService.approveStatus(t,params);
	}
	/**
	 * @Description:  总工主任审批：同意
	 * @author         changl 
	 * @Date           2017年6月15日 下午4:50:08 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/implement")
	public @ResponseBody ResultObj implement(@RequestBody SolveEntity t, HttpServletRequest request) {
		String approveIdea = request.getParameter("approve");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		boolean finish=actTaskService.isParallelEndTask(t.getTaskId());
		if(finish){
			Object status= actTaskService.getVarialble(t.getTaskId(), BranchMarkEnum.PARLLEL_BRANCH_KEY.getName());
			if(String.valueOf(status).equals(String.valueOf(ExamResultEnum.BACK.getId()))){
				params.put("status", DefectStatusEnum.IMPLEMENT.getCode());
			}else{
				params.put("status", DefectStatusEnum.CHECK.getCode());
			}
		}
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());
		params.put("event", "审批意见");
		params.put(ExamMarkEnum.COMMENT.getCode(),t.getApproveIdea()==null?"":t.getApproveIdea());//审批意见
		return solveService.approveStatus(t,params);
	}
}