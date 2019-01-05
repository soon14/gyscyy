package com.aptech.business.ticketManage.workFireTwo.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.component.dictionary.WorkBtnTypeEnum;
import com.aptech.business.component.dictionary.WorkFireBtnTypeEnum;
import com.aptech.business.component.dictionary.WorkFireTwoBtnTypeEnum;
import com.aptech.business.component.dictionary.WorkSpResultEnum;
import com.aptech.business.orgaApp.service.OrgaAppService;
import com.aptech.business.ticketManage.workElectric.domain.WorkElectricEntity;
import com.aptech.business.ticketManage.workFire.domain.WorkFireEntity;
import com.aptech.business.ticketManage.workFireTwo.domain.WorkFireTwoEntity;
import com.aptech.business.ticketManage.workFireTwo.service.WorkFireTwoService;
import com.aptech.business.ticketManage.workLine.domain.WorkLineEntity;
import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.business.ticketManage.workTicketFireTwo.domain.WorkTicketFireTwoEntity;
import com.aptech.business.ticketManage.workTicketFireTwo.service.WorkTicketFireTwoService;
import com.aptech.business.ticketManage.workTicketLine.domain.WorkTicketLineEntity;
import com.aptech.business.ticketManage.workTicketUserRel.domain.WorkTicketUserRelEntity;
import com.aptech.business.ticketManage.workTicketUserRel.domain.WorkTicketUserRelEnum;
import com.aptech.business.ticketManage.workTicketUserRel.service.WorkTicketUserRelService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.role.service.SysRoleService;
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
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.IdUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 电气工作票配置控制器
 *
 * @author 
 * @created 2017-06-05 17:12:46
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/workFireTwo")
public class WorkFireTwoController extends BaseController<WorkFireTwoEntity> {
	
	@Autowired
	private WorkFireTwoService workFireTwoService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private WorkTicketFireTwoService workTicketFireTwoService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private OrgaAppService orgaAppService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private WorkTicketUserRelService workTicketUserRelService;
	@Override
	public IBaseEntityOperation<WorkFireTwoEntity> getService() {
		return workFireTwoService;
	}
	
		
	/**
	 *	跳转到签发页面
	 */
	@RequestMapping("/getAddQf")
	public ModelAndView getAddQf(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("dateQf", new Date());
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workFireTwo/workFireTwoAddQf", model);
	}
	/**
	 *	跳转到会签发页面
	 */
	@RequestMapping("/getAddHqf")
	public ModelAndView getAddHqf(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("dateHQf", new Date());
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workFireTwo/workFireTwoAddHqf", model);
	}
	/**
	 *	跳转到收票页面
	 */
	@RequestMapping("/getAddxffzrsh")
	public ModelAndView getAddxffzrsh(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("dateQf", new Date());
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workFireTwo/workFireTwoAddxffzrsh", model);
	}
	/**
	* @Title: getAddjhrsh
	* @Description: 跳转到监护人审核
	* @author sunliang
	* @date 2017年8月19日下午2:45:16
	* @param request
	* @return
	* @throws
	*/
	@RequestMapping("/getAddxfjhrsh")
    public ModelAndView getAddjhrsh(HttpServletRequest request){
        Map<String, Object> model = new HashMap<String, Object>();
        String electricId=request.getParameter("electricId");
        String taskId=request.getParameter("taskId");
        List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
        model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
        SysUserEntity userEntity= RequestContext.get().getUser();
        model.put("userEntity", userEntity);
        model.put("dateQf", new Date());
        model.put("electricId", electricId);
        return this.createModelAndView("ticketManage/workFireTwo/workFireTwoAddxfjhrsh", model);
    }
	/**
	 * @Title: getAddajfzrsh
	 * @Description: 跳转到安监部门负责人审核
	 * @author sunliang
	 * @date 2017年8月19日下午2:45:16
	 * @param request
	 * @return
	 * @throws
	 */
	@RequestMapping("/getAddajfzrsh")
	public ModelAndView getAddajfzrsh(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("dateQf", new Date());
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workFireTwo/workFireTwoAddajfzrsh", model);
	}
	
	/**
	 *	跳转到收票页面
	 */
	@RequestMapping("/getAddSp")
	public ModelAndView getAddSp(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		String taskId=request.getParameter("taskId");
		WorkFireTwoEntity workFireEntity = workFireTwoService.findById(Long.valueOf(electricId));
		WorkTicketFireTwoEntity workTicketEntity=workTicketFireTwoService.findById(workFireEntity.getWorkticketId());
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		
		//获取三种人记录
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.NE,WorkTicketUserRelEnum.PERMISSION.getId()));
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,workTicketEntity.getId()));
		List<WorkTicketUserRelEntity> list = workTicketUserRelService.findByCondition(conditions, null);
		//移除许可人列表中的工作负责人和签发人
		Iterator<SysUserEntity> iterator = userList.iterator();
		while (iterator.hasNext()) {
			SysUserEntity sysUserEntity = iterator.next();
            for(WorkTicketUserRelEntity workTicketUserRelEntity:list){
            	if(sysUserEntity.getId().equals(workTicketUserRelEntity.getUserId())){
            		iterator.remove();
            	}
            }
       }
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("dateQf", new Date());
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workFireTwo/workFireTwoAddSp", model);
	}
	
	/**
	 *	跳转到许可页面
	 */
	@RequestMapping("/getAddXk")
	public ModelAndView getAddXk(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		String taskId=request.getParameter("taskId");
		WorkFireTwoEntity workFireEntity = workFireTwoService.findById(Long.valueOf(electricId));
		WorkTicketFireTwoEntity workTicketEntity=workTicketFireTwoService.findById(workFireEntity.getWorkticketId());
		model.put("workTicketEntity", workTicketEntity);
		model.put("workFireEntity", workFireEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		model.put("sdate", df.format(workTicketEntity.getPlandateStart()));
		model.put("edate",df.format(workTicketEntity.getPlandateEnd()));
		model.put("xkdate",df.format(new Date()));
		model.put("dhdate",df.format(new Date()));
		model.put("electricId", electricId);
		
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		
		 //工作负责人下拉列表
  	    ComboboxVO requestUserVO = new ComboboxVO();

        //TODO下拉框具体内容根据具体业务定制
  	  List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> allUsers=sysUserService.findByCondition(conditions, null);
        for(SysUserEntity sysUserEntity : allUsers){
      	 requestUserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("requestUsers", JsonUtil.toJson(requestUserVO.getOptions()));
        
		return this.createModelAndView("ticketManage/workFireTwo/workFireTwoAddXk", model);
	}
	
	/**
	* @Title: getAddjhrqr
	* @Description: 跳转到监护人确认页面
	* @author sunliang
	* @date 2017年8月21日上午10:59:21
	* @param request
	* @return
	* @throws
	*/
	@RequestMapping("/getAddjhrqr")
    public ModelAndView getAddjhrqr(HttpServletRequest request){
        Map<String, Object> model = new HashMap<String, Object>();
        String electricId=request.getParameter("electricId");
        String taskId=request.getParameter("taskId");
        WorkFireTwoEntity workFireEntity = workFireTwoService.findById(Long.valueOf(electricId));
        WorkTicketFireTwoEntity workTicketEntity=workTicketFireTwoService.findById(workFireEntity.getWorkticketId());
        model.put("workTicketEntity", workTicketEntity);
        model.put("workFireEntity", workFireEntity);
        List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
        model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
        SysUserEntity userEntity= RequestContext.get().getUser();
        model.put("userEntity", userEntity);
        model.put("electricId", electricId);
    	model.put("dateQf", new Date());
        return this.createModelAndView("ticketManage/workFireTwo/workFireTwoAddJhrqr", model);
    }
	
	
	/**
	 *	跳转到终结页面
	 */
	@RequestMapping("/getAddZj")
	public ModelAndView getAddZj(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String workId=request.getParameter("workId");
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		String procDefId=request.getParameter("procDefId");
		model.put("workIdZj", workId);
		model.put("taskIdZj", taskId);
		model.put("procInstIdZj", procInstId);
		model.put("procDefIdZj", procDefId);
		String electricId=request.getParameter("electricId");
		 WorkFireTwoEntity workFireEntity = workFireTwoService.findById(Long.valueOf(electricId));
	     WorkTicketFireTwoEntity workTicketEntity=workTicketFireTwoService.findById(workFireEntity.getWorkticketId());
		model.put("workFireEntity", workFireEntity);
		model.put("workTicketEntity", workTicketEntity);
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
		
		//获取三种人记录
		/*List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.NE,WorkTicketUserRelEnum.DUTY.getId()));
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,workTicketEntity.getId()));
		List<WorkTicketUserRelEntity> list = workTicketUserRelService.findByCondition(conditions, null);
		//移除动火负责人列表中的签发人和许可人
		Iterator<SysUserEntity> iterator = userList.iterator();
		while (iterator.hasNext()) {
			SysUserEntity sysUserEntity = iterator.next();
            for(WorkTicketUserRelEntity workTicketUserRelEntity:list){
            	if(sysUserEntity.getId().equals(workTicketUserRelEntity.getUserId())){
            		iterator.remove();
            	}
            }
       }*/
				
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		
		DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		model.put("zjdate", df.format(new Date()));
		
		return this.createModelAndView("ticketManage/workFireTwo/workFireTwoAddZj", model);
	}
	
	
	/**
	 *	跳转到工作交底页面
	 */
	@RequestMapping("/getAddGzjd")
	public ModelAndView getAddGzjd(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		 WorkFireTwoEntity workFireEntity = workFireTwoService.findById(Long.valueOf(electricId));
	     WorkTicketFireTwoEntity workTicketEntity=workTicketFireTwoService.findById(workFireEntity.getWorkticketId());
		model.put("workTicketEntity", workTicketEntity);
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workFireTwo/workFireTwoAddGzjd", model);
	}
	
	
	/**
	* @Title: getAddfzrys
	* @Description: 跳转到工作负责人验收
	* @author sunliang
	* @date 2017年8月21日下午2:50:15
	* @param request
	* @return
	* @throws
	*/
	@RequestMapping("/getAddfzrys")
    public ModelAndView getAddfzrys(HttpServletRequest request){
        String electricId=request.getParameter("electricId");
        Map<String, Object> model = new HashMap<String, Object>();
        
        String workId=request.getParameter("workId");
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		String procDefId=request.getParameter("procDefId");
		model.put("workId", workId);
		model.put("taskId", taskId);
		model.put("procInstId", procInstId);
		model.put("procDefId", procDefId);
        
        
        List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
        model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
        SysUserEntity userEntity= RequestContext.get().getUser();
        model.put("userEntity", userEntity);
        model.put("electricId", electricId);
        
		 WorkFireTwoEntity workFireEntity = workFireTwoService.findById(Long.valueOf(electricId));
	     WorkTicketFireTwoEntity workTicketEntity=workTicketFireTwoService.findById(workFireEntity.getWorkticketId());
		model.put("workFireEntity", workFireEntity);
		model.put("workTicketEntity", workTicketEntity);
        
		 //工作负责人下拉列表
  	    ComboboxVO requestUserVO = new ComboboxVO();

        //TODO下拉框具体内容根据具体业务定制
  	  List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> allUsers=sysUserService.findByCondition(conditions, null);
        for(SysUserEntity sysUserEntity : allUsers){
      	 requestUserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("requestUsers", JsonUtil.toJson(requestUserVO.getOptions()));
        
        return this.createModelAndView("ticketManage/workFireTwo/workFireTwoAddDhys", model);
    }
	/**
	 * @Title: getAddXkrys
	 * @Description: 跳转到消防监护人审批（终结）
	 * @author sunliang
	 * @date 2017年8月21日下午4:13:36
	 * @param request
	 * @return
	 * @throws
	 */
	@RequestMapping("/getAddZjJh")
	public ModelAndView getAddJhzj(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		
        String workId=request.getParameter("workId");
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		String procDefId=request.getParameter("procDefId");
		model.put("workId", workId);
		model.put("taskId", taskId);
		model.put("procInstId", procInstId);
		model.put("procDefId", procDefId);
		
		WorkFireTwoEntity workFireEntity = workFireTwoService.findById(Long.valueOf(electricId));
		WorkTicketFireTwoEntity workTicketEntity=workTicketFireTwoService.findById(workFireEntity.getWorkticketId());
		model.put("workTicketEntity", workTicketEntity);
		model.put("workFireEntity", workFireEntity);
        
        List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
		//获取三种人记录
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.NE,WorkTicketUserRelEnum.PERMISSION.getId()));
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,workTicketEntity.getId()));
		List<WorkTicketUserRelEntity> list = workTicketUserRelService.findByCondition(conditions, null);
		//移除许可人列表中的工作负责人和签发人
		Iterator<SysUserEntity> iterator = userList.iterator();
		while (iterator.hasNext()) {
			SysUserEntity sysUserEntity = iterator.next();
            for(WorkTicketUserRelEntity workTicketUserRelEntity:list){
            	if(sysUserEntity.getId().equals(workTicketUserRelEntity.getUserId())){
            		iterator.remove();
            	}
            }
       }
        model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workFireTwo/workFireTwoAddZjJH", model);
	}
	/**
	* @Title: getAddXkrys
	* @Description: 跳转到许可人审批（终结）
	* @author sunliang
	* @date 2017年8月21日下午4:13:36
	* @param request
	* @return
	* @throws
	*/
	@RequestMapping("/getAddZjXk")
    public ModelAndView getAddXkrys(HttpServletRequest request){
        Map<String, Object> model = new HashMap<String, Object>();
        String electricId=request.getParameter("electricId");
        
        String workId=request.getParameter("workId");
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		String procDefId=request.getParameter("procDefId");
		model.put("workId", workId);
		model.put("taskId", taskId);
		model.put("procInstId", procInstId);
		model.put("procDefId", procDefId);
        
        
        WorkFireTwoEntity workFireEntity = workFireTwoService.findById(Long.valueOf(electricId));
        WorkTicketFireTwoEntity workTicketEntity=workTicketFireTwoService.findById(workFireEntity.getWorkticketId());
        model.put("workTicketEntity", workTicketEntity);
        model.put("workFireEntity", workFireEntity);
        SysUserEntity userEntity= RequestContext.get().getUser();
        model.put("userEntity", userEntity);
        model.put("electricId", electricId);
        return this.createModelAndView("ticketManage/workFireTwo/workFireTwoAddZjXK", model);
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
			workFireTwoService.submit(id,selectUser);
			resultObj.setResult("success");
		} catch (Exception e) {
			e.printStackTrace();
			resultObj.setResult("error");
		}
		return resultObj;
	}
	
	/**
	 * @Description:   签发人同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreeQf")
	public @ResponseBody ResultObj agreeQf(HttpServletRequest request,@RequestBody WorkFireTwoEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(WorkFireTwoBtnTypeEnum.QF.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			
			workFireTwoService.updateSpnrAgree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   签发人不同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeQf")
	public @ResponseBody ResultObj disAgreeQf(HttpServletRequest request,@RequestBody WorkFireTwoEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(WorkFireTwoBtnTypeEnum.QF.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			WorkFireTwoEntity workElectric = workFireTwoService.findById(workFireEntity.getId());
			WorkTicketFireTwoEntity workTicketEntity=workTicketFireTwoService.findById(workElectric.getWorkticketId());
			SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getGuarderId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workFireTwoService.updateSpnrDisagree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 * @Description:   会签发人同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreeHQf")
	public @ResponseBody ResultObj agreeHQf(HttpServletRequest request,@RequestBody WorkFireTwoEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(WorkFireTwoBtnTypeEnum.HQF.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			
			workFireTwoService.updateSpnrAgree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   签发人不同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeHQf")
	public @ResponseBody ResultObj disAgreeHQf(HttpServletRequest request,@RequestBody WorkFireTwoEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(WorkFireTwoBtnTypeEnum.HQF.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			WorkFireTwoEntity workElectric = workFireTwoService.findById(workFireEntity.getId());
			WorkTicketFireTwoEntity workTicketEntity=workTicketFireTwoService.findById(workElectric.getWorkticketId());
			SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getGuarderId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workFireTwoService.updateSpnrDisagree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 * @Description:   消防监护人审核
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreexfSh")
	public @ResponseBody ResultObj agreexfSh(HttpServletRequest request,@RequestBody WorkFireTwoEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(WorkFireTwoBtnTypeEnum.XFJHR.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workFireTwoService.updateSpnrAgree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   消防监护人审核不同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreexfSh")
	public @ResponseBody ResultObj disAgreexfSh(HttpServletRequest request,@RequestBody WorkFireTwoEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(WorkFireTwoBtnTypeEnum.XFJHR.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			WorkFireTwoEntity workElectric = workFireTwoService.findById(workFireEntity.getId());
			WorkTicketFireTwoEntity workTicketEntity=workTicketFireTwoService.findById(workElectric.getWorkticketId());
			SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getGuarderId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workFireTwoService.updateSpnrDisagree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	* @Title: agreeajSh
	* @Description: 安监部门负责人审核
	* @author sunliang
	* @date 2017年8月19日下午2:47:26
	* @param request
	* @param workFireEntity
	* @return
	* @throws
	*/
	@RequestMapping("/agreeajSh")
    public @ResponseBody ResultObj agreeajSh(HttpServletRequest request,@RequestBody WorkFireTwoEntity workFireEntity){
        ResultObj resultObj = new ResultObj();
        try {
            workFireEntity.setSpFlag(WorkFireTwoBtnTypeEnum.AJBM.getCode());
            String taskId=request.getParameter("taskId");
            String procInstId=request.getParameter("procInstId");
            String selectUser=request.getParameter("selectUser");
            SysUserEntity userEntity= RequestContext.get().getUser();
            
            Map<String, Object> taskVariables=new HashMap<String, Object>();
            taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
            taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
            
            taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
            taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
            
            actTaskService.complete(taskId,procInstId, taskVariables);
            workFireTwoService.updateSpnrAgree(workFireEntity,userEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObj;
    }
	
	   /**
	* @Title: disAgreexfSh
	* @Description: 安监部门负责人审核不同意
	* @author sunliang
	* @date 2017年8月19日下午5:15:13
	* @param request
	* @param workFireEntity
	* @return
	* @throws
	*/
	@RequestMapping("/disAgreeajSh")
	    public @ResponseBody ResultObj disAgreeajSh(HttpServletRequest request,@RequestBody WorkFireTwoEntity workFireEntity){
	        ResultObj resultObj = new ResultObj();
	        try {
	            workFireEntity.setSpFlag(WorkFireTwoBtnTypeEnum.AJBM.getCode());
	            String taskId=request.getParameter("taskId");
	            String procInstId=request.getParameter("procInstId");
	            String selectUser=request.getParameter("selectUser");
	            SysUserEntity userEntity= RequestContext.get().getUser();
	            //查询工作负责人的loginName
	            WorkFireTwoEntity workElectric = workFireTwoService.findById(workFireEntity.getId());
	            WorkTicketFireTwoEntity workTicketEntity=workTicketFireTwoService.findById(workElectric.getWorkticketId());
	            SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getGuarderId());
	            //查询工作负责人的loginName
	            Map<String, Object> taskVariables=new HashMap<String, Object>();
	            taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
	            taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
	            
	            taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
	            taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
	            actTaskService.complete(taskId,procInstId,taskVariables);
	            workFireTwoService.updateSpnrDisagree(workFireEntity,userEntity);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return resultObj;
	    }

	/**
	 * @Description:   运行值班值班人审批
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreeSp")
    public @ResponseBody ResultObj agreeSp(HttpServletRequest request,@RequestBody WorkFireTwoEntity workFireEntity){
        ResultObj resultObj = new ResultObj();
        try {
            workFireEntity.setSpFlag(WorkFireTwoBtnTypeEnum.SP.getCode());
            String taskId=request.getParameter("taskId");
            String procInstId=request.getParameter("procInstId");
            String selectUser=request.getParameter("selectUser");
            SysUserEntity userEntity= RequestContext.get().getUser();
            
            Map<String, Object> taskVariables=new HashMap<String, Object>();
            taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
            taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
            
            taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
            taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
            
            actTaskService.complete(taskId,procInstId, taskVariables);
            workFireTwoService.updateSpnrAgree(workFireEntity,userEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObj;
    }
	/**
	 * @Description:   运行值班值班人不同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeSp")
	public @ResponseBody ResultObj disAgreeSp(HttpServletRequest request,@RequestBody WorkFireTwoEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(WorkFireTwoBtnTypeEnum.SP.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			WorkFireTwoEntity workElectric = workFireTwoService.findById(workFireEntity.getId());
			WorkTicketFireTwoEntity workTicketEntity=workTicketFireTwoService.findById(workElectric.getWorkticketId());
			SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getGuarderId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			
			workFireTwoService.updateSpnrDisagree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   许可人同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreeXk")
	public @ResponseBody ResultObj agreeXk(HttpServletRequest request,@RequestBody WorkFireTwoEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			Long picId=workFireEntity.getAllowPicPersonId();//动火工作负责人的id
			SysUserEntity  sysUserEntity=sysUserService.findById(picId);//或取这个工作负责人的实体
			workFireEntity.setSpFlag(WorkFireTwoBtnTypeEnum.XK.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			String selectUser=request.getParameter("selectUser");
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workFireTwoService.updateSpnrAgree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	  
	/**
	 * 许可人审批不通过
	 * @param request
	 * @param workFireEntity
	 * @return
	 */
   @RequestMapping("/disagreeXk")
    public @ResponseBody ResultObj disAgreeXk(HttpServletRequest request,@RequestBody WorkFireTwoEntity workFireEntity){
        ResultObj resultObj = new ResultObj();
        try {
            workFireEntity.setSpFlag(WorkFireTwoBtnTypeEnum.XK.getCode());
            String taskId=request.getParameter("taskId");
            String procInstId=request.getParameter("procInstId");
            String selectUser=request.getParameter("selectUser");
            SysUserEntity userEntity= RequestContext.get().getUser();
            //查询工作负责人的loginName
            WorkFireTwoEntity workElectric = workFireTwoService.findById(workFireEntity.getId());
            WorkTicketFireTwoEntity workTicketEntity=workTicketFireTwoService.findById(workElectric.getWorkticketId());
            SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getGuarderId());
            //查询工作负责人的loginName
            Map<String, Object> taskVariables=new HashMap<String, Object>();
            taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
            taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
            taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
            taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
            actTaskService.complete(taskId,procInstId,taskVariables);
            workFireTwoService.updateSpnrDisagree(workFireEntity,userEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObj;
    }
	
	/**
	* @Title: agreeJhrqr
	* @Description: 监护人确认审核（许可）
	* @author sunliang
	* @date 2017年8月21日上午11:01:56
	* @param request
	* @param workFireEntity
	* @return
	* @throws
	*/
	@RequestMapping("/agreeJhrqr")
    public @ResponseBody ResultObj agreeJhrqr(HttpServletRequest request,@RequestBody WorkFireTwoEntity workFireEntity){
        ResultObj resultObj = new ResultObj();
        try {
            WorkFireTwoEntity workFire = workFireTwoService.findById(workFireEntity.getId());
            WorkTicketFireTwoEntity workTicketEntity=workTicketFireTwoService.findById(workFire.getWorkticketId());
            SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getGuarderId());
            workFireEntity.setSpFlag(WorkFireTwoBtnTypeEnum.XFJHRXK.getCode());
            String taskId=request.getParameter("taskId");
            String procInstId=request.getParameter("procInstId");
            String selectUser=request.getParameter("selectUser");
            SysUserEntity userEntity= RequestContext.get().getUser();
            
            Map<String, Object> taskVariables=new HashMap<String, Object>();
            taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
            taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
            
            taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
            taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
            
            actTaskService.complete(taskId,procInstId, taskVariables);
            workFireTwoService.updateSpnrAgree(workFireEntity,userEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObj;
    }
	/**
	* @Title: disAgreeJhrqr
	* @Description: 监护人确认驳回
	* @author sunliang
	* @date 2017年8月21日下午5:35:56
	* @param request
	* @param workFireEntity
	* @return
	* @throws
	*/
	@RequestMapping("/disagreeJhrqr")
    public @ResponseBody ResultObj disAgreeJhrqr(HttpServletRequest request,@RequestBody WorkFireTwoEntity workFireEntity){
        ResultObj resultObj = new ResultObj();
        try {
            workFireEntity.setSpFlag(WorkFireTwoBtnTypeEnum.XFJHRXK.getCode());
            String taskId=request.getParameter("taskId");
            String procInstId=request.getParameter("procInstId");
            String selectUser=request.getParameter("selectUser");
            SysUserEntity userEntity= RequestContext.get().getUser();
            //查询工作负责人的loginName
            WorkFireTwoEntity workElectric = workFireTwoService.findById(workFireEntity.getId());
            WorkTicketFireTwoEntity workTicketEntity=workTicketFireTwoService.findById(workElectric.getWorkticketId());
            SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getChangeAllowId());
            //查询工作负责人的loginName
            Map<String, Object> taskVariables=new HashMap<String, Object>();
            taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
            taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
            
            taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
            taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
            actTaskService.complete(taskId,procInstId,taskVariables);
            workFireTwoService.updateSpnrDisagree(workFireEntity,userEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObj;
    }
	
	/**
	 * @Description:   工作交底的  确定
	 * @author         zhangzq 
	 * @Date           201706016
	 * @throws         Exception
	 */
	@RequestMapping("/sureGzjd")
	public @ResponseBody ResultObj sureGzjd(HttpServletRequest request,@RequestBody  WorkFireTwoEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(WorkFireTwoBtnTypeEnum.GZJD.getCode());
			SysUserEntity userEntity= RequestContext.get().getUser();
			workFireTwoService.updateSpnrAgree(workFireEntity, userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 * @Description:   终结确定
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/sureZj")
	public @ResponseBody ResultObj sureZj(HttpServletRequest request,@RequestBody WorkFireTwoEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(WorkFireTwoBtnTypeEnum.YS.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			String selectUser=request.getParameter("selectUser");
			
			WorkFireTwoEntity workFire = workFireTwoService.findById(workFireEntity.getId());
            WorkTicketFireTwoEntity workTicketEntity=workTicketFireTwoService.findById(workFire.getWorkticketId());
            SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getGuarderId());
            
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workFireTwoService.updateSpnrAgree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	
	
	   /**
	* @Title: agreeFzrys
	* @Description: 动火工作负责人验收
	* @author sunliang
	* @date 2017年8月21日下午2:53:41
	* @param request
	* @param workFireEntity
	* @return
	* @throws
	*/
	@RequestMapping("/agreeFzrys")
       public @ResponseBody ResultObj agreeFzrys(HttpServletRequest request,@RequestBody WorkFireTwoEntity workFireEntity){
           ResultObj resultObj = new ResultObj();
           try {
               workFireEntity.setSpFlag(WorkFireTwoBtnTypeEnum.DHFZRYS.getCode());
               String taskId=request.getParameter("taskId");
               String procInstId=request.getParameter("procInstId");
               String selectUser=request.getParameter("selectUser");
               SysUserEntity userEntity= RequestContext.get().getUser();
               
               Map<String, Object> taskVariables=new HashMap<String, Object>();
               taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
               taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
               taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
               taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
               
               actTaskService.complete(taskId,procInstId, taskVariables);
               workFireTwoService.updateSpnrAgree(workFireEntity,userEntity);
           } catch (Exception e) {
               e.printStackTrace();
           }
           return resultObj;
       }

	
	/**
	* @Title: disAgreeFzrys
	* @Description: 动火负责人验收不通过
	* @author sunliang
	* @date 2017年8月21日下午8:05:57
	* @param request
	* @param workFireEntity
	* @return
	* @throws
	*/
	@RequestMapping("/disAgreeFzrys")
    public @ResponseBody ResultObj disAgreeFzrys(HttpServletRequest request,@RequestBody WorkFireTwoEntity workFireEntity){
        ResultObj resultObj = new ResultObj();
        try {
            workFireEntity.setSpFlag(WorkFireTwoBtnTypeEnum.DHFZRYS.getCode());
            String taskId=request.getParameter("taskId");
            String procInstId=request.getParameter("procInstId");
            String selectUser=request.getParameter("selectUser");
            SysUserEntity userEntity= RequestContext.get().getUser();
            //查询工作负责人的loginName
            WorkFireTwoEntity workElectric = workFireTwoService.findById(workFireEntity.getId());
            WorkTicketFireTwoEntity workTicketEntity=workTicketFireTwoService.findById(workElectric.getWorkticketId());
            SysUserEntity fzrEntity=sysUserService.findById(workElectric.getOtherExecutorId());
            //查询工作负责人的loginName
            Map<String, Object> taskVariables=new HashMap<String, Object>();
            taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
            taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
            
            taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
            taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
            actTaskService.complete(taskId,procInstId,taskVariables);
            workFireTwoService.updateSpnrDisagree(workFireEntity,userEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObj;
    }
	/**
	 * @Title: agreeXkrys
	 * @Description:消防监护审批同意（终结）
	 * @author sunliang
	 * @date 2017年8月21日下午8:05:45
	 * @param request
	 * @param workFireEntity
	 * @return
	 * @throws
	 */
	@RequestMapping("/agreeJhzj")
	public @ResponseBody ResultObj agreeJhzj(HttpServletRequest request,@RequestBody WorkFireTwoEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
            workFireEntity.setSpFlag(WorkFireTwoBtnTypeEnum.XFJHRZJ.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
		    String selectUser=request.getParameter("selectUser");
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workFireTwoService.updateSpnrAgree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	* @Title: disAgreeJhzj
	* @Description: 消防监护人审批（终结）不同意
	* @author sunliang
	* @date 2017年8月21日下午8:05:32
	* @param request
	* @param workFireEntity
	* @return
	* @throws
	*/
	@RequestMapping("/disAgreeJhzj")
    public @ResponseBody ResultObj disAgreeJhzj(HttpServletRequest request,@RequestBody WorkFireTwoEntity workFireEntity){
        ResultObj resultObj = new ResultObj();
        try {
            workFireEntity.setSpFlag(WorkFireTwoBtnTypeEnum.XFJHRZJ.getCode());
            String taskId=request.getParameter("taskId");
            String procInstId=request.getParameter("procInstId");
            String selectUser=request.getParameter("selectUser");
            SysUserEntity userEntity= RequestContext.get().getUser();
            //查询工作负责人的loginName
            WorkFireTwoEntity workElectric = workFireTwoService.findById(workFireEntity.getId());
            WorkTicketFireTwoEntity workTicketEntity=workTicketFireTwoService.findById(workElectric.getWorkticketId());
            SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getGuarderId());
            //查询工作负责人的loginName
            Map<String, Object> taskVariables=new HashMap<String, Object>();
            taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
            taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
            
            taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
            taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
            actTaskService.complete(taskId,procInstId,taskVariables);
            workFireTwoService.updateSpnrDisagree(workFireEntity,userEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObj;
    }
	
	/**
	* @Title: agreeXkrys
	* @Description:许可人验收通过
	* @author sunliang
	* @date 2017年8月21日下午8:05:45
	* @param request
	* @param workFireEntity
	* @return
	* @throws
	*/
	@RequestMapping("/agreeXkzj")
    public @ResponseBody ResultObj agreeXkzj(HttpServletRequest request,@RequestBody WorkFireTwoEntity workFireEntity){
        ResultObj resultObj = new ResultObj();
        try {
            workFireEntity.setSpFlag(WorkFireTwoBtnTypeEnum.XKZJ.getCode());
            String taskId=request.getParameter("taskId");
            String procInstId=request.getParameter("procInstId");
            SysUserEntity userEntity= RequestContext.get().getUser();
            
            Map<String, Object> taskVariables=new HashMap<String, Object>();
            taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
            taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
            taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
            taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
            
            actTaskService.complete(taskId,procInstId, taskVariables);
            workFireTwoService.updateSpnrAgree(workFireEntity,userEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObj;
    }
	/**
	* @Title: disAgreeXkrys
	* @Description: 许可人验收不通过
	* @author sunliang
	* @date 2017年8月21日下午8:05:32
	* @param request
	* @param workFireEntity
	* @return
	* @throws
	*/
	@RequestMapping("/disAgreeXkzj")
    public @ResponseBody ResultObj disAgreeXkzj(HttpServletRequest request,@RequestBody WorkFireTwoEntity workFireEntity){
        ResultObj resultObj = new ResultObj();
        try {
            workFireEntity.setSpFlag(WorkFireTwoBtnTypeEnum.XKZJ.getCode());
            String taskId=request.getParameter("taskId");
            String procInstId=request.getParameter("procInstId");
            String selectUser=request.getParameter("selectUser");
            SysUserEntity userEntity= RequestContext.get().getUser();
            //查询工作负责人的loginName
            WorkFireTwoEntity workElectric = workFireTwoService.findById(workFireEntity.getId());
            WorkTicketFireTwoEntity workTicketEntity=workTicketFireTwoService.findById(workElectric.getWorkticketId());
            SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getGuarderId());
            //查询工作负责人的loginName
            Map<String, Object> taskVariables=new HashMap<String, Object>();
            taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
            taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
            
            taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
            taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
            actTaskService.complete(taskId,procInstId,taskVariables);
            workFireTwoService.updateSpnrDisagree(workFireEntity,userEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObj;
    }
	
	
	/**
	 * @Description:   更新备注
	 * @author         zhangzq 
	 * @Date           2017年7月4日 上午10:20:44 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/updateBz/{id}")
	public @ResponseBody ResultObj updateBz(@RequestBody WorkFireTwoEntity t, HttpServletRequest request ,@PathVariable Long id) {
		return workFireTwoService.update(t,id);
	}
	/**
	 * @Description:   废票
	 * @author         zhangzq 
	 * @Date           2017年7月5日 上午10:08:02 
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeFp")
	public @ResponseBody ResultObj disAgreeFp(HttpServletRequest request,@RequestBody WorkFireTwoEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(WorkFireTwoBtnTypeEnum.FP.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workFireTwoService.updateSpnrDisagree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   再提交
	 * @author         zhangzq 
	 * @Date           2017年7月13日 上午9:26:10 
	 * @throws         Exception
	 */
	@RequestMapping("/againSubmit")
	public @ResponseBody ResultObj againSubmit(HttpServletRequest request){
		ResultObj resultObj = new ResultObj();
		try {
			WorkFireTwoEntity workFireEntity=new WorkFireTwoEntity();
			workFireEntity.setSpFlag(WorkFireTwoBtnTypeEnum.ZTJ.getCode());
			String workId=request.getParameter("workId");
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			workFireEntity.setId(Long.valueOf(workId));
			workFireTwoService.updateSpnrAgree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
}