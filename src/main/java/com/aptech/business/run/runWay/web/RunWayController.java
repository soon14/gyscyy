package com.aptech.business.run.runWay.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.component.dictionary.TreeTypeEnum;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
import com.aptech.business.equip.equipLedger.service.EquipLedgerService;
import com.aptech.business.run.runLog.domain.RunLogEntity;
import com.aptech.business.run.runLog.service.RunLogService;
import com.aptech.business.run.runWay.domain.RunWayEntity;
import com.aptech.business.run.runWay.domain.RunWayVo;
import com.aptech.business.run.runWay.service.RunWayService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.domain.UnitLevelEnum;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 运行方式配置控制器
 *
 * @author 
 * @created 2017-06-20 09:26:27
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/runWay")
public class RunWayController extends BaseController<RunWayEntity> {
	
	@Autowired
	private RunWayService runWayService;
	@Autowired
    private SysUnitService sysUnitService;
	@Autowired
    private EquipLedgerService equipLedgerService;
	@Autowired
    private RunLogService runLogService;
	@Autowired
    private SysUserService sysUserService;
	@Override
	public IBaseEntityOperation<RunWayEntity> getService() {
		return runWayService;
	}
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index/{rlId}")
	public ModelAndView list(HttpServletRequest request, Map<String, Object> params, @PathVariable Long rlId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("rlId", rlId);
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_LEVEL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,UnitLevelEnum.FARM.getCode()));
	    conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ,String.valueOf(DataStatusEnum.NORMAL.ordinal())));
	    conditions.add(new Condition("C_GENERATION_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,String.valueOf(UnitLevelEnum.COMPANY.getCode())));
	    conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.EQ,1));
		List<SysUnitEntity>sysUnitList=sysUnitService.findByCondition(conditions, null);
		List<RunWayVo>runWayVoList=new ArrayList<RunWayVo>();
		conditions.clear();
		for (SysUnitEntity sysUnit:sysUnitList) {
			conditions.add(new Condition("C_RL_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,rlId));
			conditions.add(new Condition("t.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,sysUnit.getId()));
			conditions.add(new Condition("C_TREE_TYPE", FieldTypeEnum.LONG, MatchTypeEnum.EQ,TreeTypeEnum.TREE_TYPE2.getCode()));
			List<RunWayEntity>runWayList=runWayService.findByCondition("findRunWayByCondition",conditions, null);
			RunWayVo vo=new RunWayVo();
			vo.setSysUnitEntity(sysUnit);
			vo.setRunWayList(runWayList);
			runWayVoList.add(vo);
			conditions.clear();
			
		}
		model.put("sysUnitList", sysUnitList);
		model.put("runWayVoList", runWayVoList);
		model.put("runWayVoJsonList", JsonUtil.toJson(runWayVoList));
        Map<String, SysDictionaryVO> runWayMap  =  DictionaryUtil.getDictionaries("RUN_WAY");
        //非风机运行方式
        ComboboxVO runWayCombobox = new ComboboxVO();
        for(String key :  runWayMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = runWayMap.get(key);
        	runWayCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("runWayCombobox", JsonUtil.toJson(runWayCombobox.getOptions()));
        //风机运行方式
    	ComboboxVO runWayComboboxForWind = new ComboboxVO();
    	System.out.println(runWayMap.keySet());
        for(String key :  runWayMap.keySet()){
        	if(StringUtils.equals(key, "run")||StringUtils.equals(key, "overhaul")){
	            SysDictionaryVO sysDictionaryVO = runWayMap.get(key);
	            runWayComboboxForWind.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        	}
        }
        model.put("runWayComboboxForWind", JsonUtil.toJson(runWayComboboxForWind.getOptions()));
		return this.createModelAndView("run/runLog/runWayList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd/{rlId}")
	public ModelAndView getAddPage(HttpServletRequest request, @PathVariable Long rlId){
		Map<String, Object> model = new HashMap<String, Object>();
        model.put("rlId", rlId);    
	        return this.createModelAndView("run/runLog/runWayAddList", model);//list空该机构无运行方式数据，为第一次运行且无交接班发生，跳转到addList页面。
	}
	/**
	 * @Description: 添加
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public @ResponseBody ResultObj save(String runWay, HttpServletRequest request) {
		runWayService.save(runWay);
		return new ResultObj();
	}
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{unitId}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long unitId){
		Map<String, Object> model = new HashMap<String, Object>();
		String rlId=request.getParameter("rlId");
		model.put("unitId", unitId);
        model.put("rlId", rlId);	
        List<Condition> conditions=new ArrayList<Condition>();
        conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,unitId));
        conditions.add(new Condition("a.C_RL_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,rlId));
        List<RunWayEntity> runWayEntityDetailList=runWayService.findByCondition("findByCondition",conditions, null);  //设备清单表初始化查询运行方式表信息
        if(runWayEntityDetailList.size()!=0){       
        if(runWayEntityDetailList.get(0).getRunWay()==null||"".equals(runWayEntityDetailList.get(0).getRunWay())){
        RunLogEntity runLogEntityTemp=runLogService.findById(new Long(rlId));
        conditions.clear();
        conditions.add(new Condition("a.C_GIVE_DATE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,runLogEntityTemp.getDate()));  
        List<RunLogEntity> runLogEntityDetailList=runLogService.findByCondition("findByCondition", conditions, null);
        RunLogEntity runLogEntity=runLogEntityDetailList.get(0);
        conditions.clear();
        conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,unitId));
        conditions.add(new Condition("a.C_RL_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,runLogEntity.getId()));
         runWayEntityDetailList=runWayService.findByCondition("findByCondition",conditions, null);
        }
        }
        if (runWayEntityDetailList.size() == 0) {
            return this.createModelAndView("run/runLog/runWayAddList", model);//list空该机构无运行方式数据，为第一次运行且无交接班发生，跳转到addList页面。
        } 
		return this.createModelAndView("run/runLog/runWayEditList", model);
	}
	/**
	* @Title: detail
	* @Description: 跳转详细页
	* @author sunliang
	* @date 上午11:48:41
	* @param request
	* @param params
	* @param rlId
	* @return
	* @throws
	*/
	@RequestMapping("/detail/{rlId}")
    public ModelAndView detail(HttpServletRequest request, Map<String, Object> params, @PathVariable Long rlId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("rlId", rlId);
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_LEVEL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,UnitLevelEnum.FARM.getCode()));
	    conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ,String.valueOf(DataStatusEnum.NORMAL.ordinal())));
	    conditions.add(new Condition("C_GENERATION_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, UnitLevelEnum.COMPANY.getCode()));
		List<SysUnitEntity>sysUnitList=sysUnitService.findByCondition(conditions, null);
		List<RunWayVo>runWayVoList=new ArrayList<RunWayVo>();
		conditions.clear();
		for (SysUnitEntity sysUnit:sysUnitList) {
			conditions.add(new Condition("C_RL_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,rlId));
			conditions.add(new Condition("t.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,sysUnit.getId()));
			conditions.add(new Condition("C_TREE_TYPE", FieldTypeEnum.LONG, MatchTypeEnum.EQ,TreeTypeEnum.TREE_TYPE2.getCode()));
			List<RunWayEntity>runWayList=runWayService.findByCondition("findRunWayByCondition",conditions, null);
			RunWayVo vo=new RunWayVo();
			vo.setSysUnitEntity(sysUnit);
			vo.setRunWayList(runWayList);
			runWayVoList.add(vo);
			conditions.clear();
			
		}
		model.put("sysUnitList", sysUnitList);
		model.put("runWayVoList", runWayVoList);
		model.put("runWayVoJsonList", JsonUtil.toJson(runWayVoList));
         Map<String, SysDictionaryVO> runWayMap  =  DictionaryUtil.getDictionaries("RUN_WAY");
    	 ComboboxVO runWayCombobox = new ComboboxVO();
         for(String key :  runWayMap.keySet()){
             SysDictionaryVO sysDictionaryVO = runWayMap.get(key);
             runWayCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
         }
         model.put("runWayCombobox", JsonUtil.toJson(runWayCombobox.getOptions()));
        return this.createModelAndView("run/runLog/runWayDetail", model);
    }
    /**
    * @Title: searchDetail
    * @Description: 运行方式列表查询
    * @author sunliang
    * @date 2017年7月4日上午10:48:00
    * @param request
    * @param RLId
    * @return
    * @throws
    */
    @RequestMapping(value = "/searchDetail/{RLId}", method = RequestMethod.POST)
    public @ResponseBody ResultListObj searchDetail(HttpServletRequest request, @PathVariable Long RLId, @RequestBody Map<String, Object> params){
        List<Condition> conditions=new ArrayList<Condition>();
        RunLogEntity runLogEntity=runLogService.findById(RLId);
        SysUserEntity sysUserEntity=sysUserService.findById((long)runLogEntity.getChargeId());
        SysUnitEntity SysUnitEntity=sysUnitService.findById((long)sysUserEntity.getUnitId());
        if("1".equals(SysUnitEntity.getLevel())){
            conditions.add(new Condition("C_PARENT_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,SysUnitEntity.getId()));
       }else {
           conditions.add(new Condition("C_PARENT_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,SysUnitEntity.getParentId()));
       }
        List<SysUnitEntity> sysUnitEntityList=sysUnitService.findByCondition(conditions, null);
        String sysUnitStr="";   
        List<RunWayEntity> resultList =new ArrayList<RunWayEntity>();
            for(int i=0;i<sysUnitEntityList.size();i++){
                SysUnitEntity runWayEntityTemp= sysUnitEntityList.get(i);
                sysUnitStr+=runWayEntityTemp.getId()+",";
            }
        
        
        List<Condition> conditions1=new ArrayList<Condition>();
        conditions1.add(new Condition("T.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.IN,sysUnitStr.substring(0, sysUnitStr.length()-1).split(",")));
        conditions1.add(new Condition("T.C_RL_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,RLId));
        List<RunWayEntity> runWayEntityDetailList=runWayService.findByCondition("findRunWayByCondition",conditions1, null);
        sysUnitStr="";
        for(int i=0;i<runWayEntityDetailList.size();i++){
            sysUnitStr+=runWayEntityDetailList.get(i).getUnitId()+","; //取有运行方式记录的场站
        }
        if(runWayEntityDetailList.size()!=0){
            
            for(int i=0;i<sysUnitEntityList.size();i++){
                if(sysUnitStr.indexOf(sysUnitEntityList.get(i).getId().toString())<0){//遍历所有场站与运行方式表里场站对比
                    RunWayEntity runWayEntityTemp=new RunWayEntity();
                    runWayEntityTemp.setUnitId(sysUnitEntityList.get(i).getId().intValue());
                    runWayEntityTemp.setUnitName(sysUnitEntityList.get(i).getName());     
                    runWayEntityDetailList.add(runWayEntityTemp);
                }
               
            }
            resultList=runWayService.changeList(runWayEntityDetailList);

        } else{//运行方式表中无数据，第一次运行记录时
               for(int i=0;i<sysUnitEntityList.size();i++){
                RunWayEntity runWayEntityTemp=new RunWayEntity();
                runWayEntityTemp.setUnitId(sysUnitEntityList.get(i).getId().intValue());
                runWayEntityTemp.setUnitName(sysUnitEntityList.get(i).getName());     
                runWayEntityDetailList.add(runWayEntityTemp);
            }
            resultList=runWayService.changeList(runWayEntityDetailList);            
        }
        ResultListObj resultObj = new ResultListObj();
        if (resultList != null) {
            resultObj.setData(resultList);
        }
        resultObj.setDraw((Integer)params.get("draw"));
        return resultObj;
    }
    /**
    * @Title: addSearch
    * @Description: 第一个运行期间设备清单列表查询（无交接班关联的运行方式数据初始化）
    * @author sunliang
    * @date 2017年7月5日上午10:30:45
    * @param request
    * @param unitId
    * @return
    * @throws
    */
    @RequestMapping(value = "/addSearch/{unitId}")
    public @ResponseBody ResultListObj addSearch(HttpServletRequest request, @PathVariable Long unitId){
      //  RunLogEntity runLogEntityTemp=runLogService.findById(rlId);
      //  SysUserEntity sysUserEntity=sysUserService.findById(new Long(runLogEntityTemp.getChargeId()));
     //   SysUnitEntity SysUnitEntity=sysUnitService.findById((long)sysUserEntity.getUnitId());

        List<Condition> conditions=new ArrayList<Condition>();
      
           conditions.add(new Condition("TR.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,unitId));
       
        List<EquipLedgerEntity> equipLedgerEntityList=equipLedgerService.findByCondition("findByCondition",conditions, null);   //第一次运行设备清单页查询的是设备表信息
        List<EquipLedgerEntity> resultList =new ArrayList<EquipLedgerEntity>();

        for(int i=0;i<equipLedgerEntityList.size();i++){   
            EquipLedgerEntity equipLedgerEntity=equipLedgerEntityList.get(i);

                resultList.add(equipLedgerEntity);
            }
            

        ResultListObj resultObj = new ResultListObj();
        if (resultList != null) {
            resultObj.setData(resultList);
        }
        return resultObj;
    }
    /**
    * @Title: insertList
    * @Description: 运行方式新增
    * @author sunliang
    * @date 2017年7月5日上午9:52:31
    * @param request
    * @param runWayEntity
    * @param id
    * @return
    * @throws Exception
    * @throws
    */
    @RequestMapping("/insertList")
    public @ResponseBody ResultObj insertList(HttpServletRequest request,@RequestBody RunWayEntity runWayEntity) throws Exception{
        
        return runWayService.insertList(runWayEntity);
    }
    /**
    * @Title: editSearch
    * @Description:设备编辑列表查询
    * @author sunliang
    * @date 2017年7月5日下午1:38:16
    * @param request
    * @param unitId
    * @return
    * @throws
    */
    @RequestMapping(value = "/editSearch/{unitId}", method = RequestMethod.POST)
    public @ResponseBody ResultListObj editSearch(HttpServletRequest request, @PathVariable Long unitId){
        List<Condition> conditions=new ArrayList<Condition>();
        String rlId= request.getParameter("rlId");
        conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,unitId));
        conditions.add(new Condition("a.C_RL_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,rlId));
        List<RunWayEntity> runWayEntityDetailList=runWayService.findByCondition("findByCondition",conditions, null);  //设备清单表初始化查询运行方式表信息
        List<RunWayEntity> resultList =new ArrayList<RunWayEntity>();
        if(runWayEntityDetailList.size()!=0){       
        if(runWayEntityDetailList.get(0).getRunWay()==null||"".equals(runWayEntityDetailList.get(0).getRunWay())){   //运行方式为空则查询上个班次运行方式
            RunLogEntity runLogEntityTemp=runLogService.findById(new Long(rlId));
            conditions.clear();
            conditions.add(new Condition("a.C_GIVE_DATE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,runLogEntityTemp.getDate()));  
            List<RunLogEntity> runLogEntityDetailList=runLogService.findByCondition("findByCondition", conditions, null);
            RunLogEntity runLogEntity=runLogEntityDetailList.get(0);
            conditions.clear();
            conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,unitId));
            conditions.add(new Condition("a.C_RL_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,runLogEntity.getId()));
            List<RunWayEntity> runWayEntityDetailListTemp=runWayService.findByCondition("findByCondition",conditions, null);
            runWayEntityDetailList.clear();
            for(int i=0;i<runWayEntityDetailListTemp.size();i++){
                RunWayEntity runWayEntity= runWayEntityDetailListTemp.get(i);
                runWayEntity.setId(new Long(0));//区分当次修改标志
                runWayEntityDetailList.add(runWayEntity);

            }
        }                                   
            resultList=runWayService.changeList(runWayEntityDetailList);     
        }
        ResultListObj resultObj = new ResultListObj();
        if (resultList != null) {
            resultObj.setData(resultList);
        }
        return resultObj;
    }
    /**
    * @Title: editList
    * @Description: 运行方式编辑
    * @author sunliang
    * @date 2017年7月27日下午2:34:14
    * @param request
    * @param runWayEntity
    * @return
    * @throws Exception
    * @throws
    */
    @RequestMapping("/editList")
    public @ResponseBody ResultObj editList(HttpServletRequest request,@RequestBody RunWayEntity runWayEntity) throws Exception{
        
        return runWayService.editList(runWayEntity);
    }
}