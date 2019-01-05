package com.aptech.business.component.dictionary;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.aptech.business.overhaul.overhaulArrange.domain.OverhaulArrangeEnum;
import com.aptech.business.overhaul.overhaulArrange.domain.OverhaulWorkTaskStatusEnum;
import com.aptech.business.ticketManage.ticketStatistics.domain.TicketDateUtilEnum;
import com.aptech.common.system.dictionary.domain.SexEnum;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.util.ReflectionUtil;
import com.aptech.framework.util.StringUtil;

/**
 * 
 * 业务字典枚举类  枚举ID 100------65535
 *
 * @author zhangjx
 * @created 2017年3月14日 下午2:08:56 
 * @lastModified 
 * @history
 *
 */
public enum BusinessDictCategoryEnum {

	MODULE_TYPE(101, "业务类型"),
	DEVICE_TYPE(102, "设备类型"),
	CODE_SEX_TYPE(103, "性别类型", SexEnum.class),
	DEFECT_TICKET_TYPE(104, "缺陷票类型"),
	HANDLE_PRIORITY(105, "办理优先级"),
    DEFECT_TYPE(201, "缺陷类型"),
	DEFECT_STATUS(202, "缺陷流程状态",DefectStatusEnum.class),
	SOLVE_RESULT(203, "消缺结果"),
	OPERATION_STATUS(301, "操作票流程状态",OperationStatusEnum.class),
	OPERATION_ACTUAL(302, "操作票实际"),
	TYPICALTICKET_TYPE(401, "典型票类型",TypicalTicketTypeEnum.class),
	TYPICALTICKET_APPROVE_STATUS(402, "典型票流程状态",TypicalTicketApproveStatusEnum.class),
	WORKTICKET_TYPE(131, "工作票状态",WorkStatusEnum.class),//zzq  为了避免冲突 我从131 开始写  
	ISSET(132, "是否被设置为典型票",IssetEnum.class),//changl 票通用
	EXECUTE_SITUATION(133, "执行情况",ExecuteEnum.class),
	PARAMETER(134,"参数类型"),
    TIME_DIMENSION(106, "时间维度"),
    PROTECT_WAY(107, "保护变动方式"), 
    MEETING_FLAG(108, "会议种类"),
    CHECK_STATE(109, "措施检查状态"),
    RECORD_TYPE(110, "记录类型"),
    WORK_CYCLE(111, "实验周期"),
    DISPATCH_UNIT(112, "调度"),
    GR_STATE(113, "交接班状态"),
    DELAY_STATES(114, "是否延期"),
    DECLARE_TYPE(115, "项目申报类型"),
    RANGE_TYPE(116, "项目范围类型"),
    CONTENT_TYPE(117, "项目内容类型"),
    CHARGE_SOURCE(118, "资金来源"),
    PUR_WAY(119, "采购方式"),
    PROFESSION_TYPE(120, "专业类型"),
    JG_TYPE(121, "技改类型"),
    CARD_SORT(135, "作业类别第一种"),
    CARD_SORT_TWO(136, "作业类别第二种"),
    CARD_SORT_THREE(137, "作业类别第三种"),
    CARD_SORT_FOUR(138, "作业类别第四种"),
    IDENTITY(139, "鉴定结果",IdentifyEnum.class),
	MANAGEMENT_TYPE(140,"管理方式",ManagementTypeEnum.class),
	INSTOCK_TYPE(141,"入库类型"),
	OUTSTOCK_TYPE(142,"出库类型"),
	BILL_TYPE(143,"单据类型"),
	APPROVE_STATUS(144,"入库审核状态",InstockApproveStatusEnum.class),
	POWER_TYPE(145,"停送电类型",PowerTypeEnum.class),
	POWER_STATUS(146,"停送电流程状态",PowerStatusEnum.class),
	OUT_APPROVE_STATUS(147,"出库审核状态",OutstockApproveStatusEnum.class),
	PLANTIME_WEEK(122,"计划时间周"),
	KJ_CHARGE_SOURCE(123,"科技资金来源"),
	OVERHAUL_PLAN_STATUS(148,"检修计划流程状态",OverhaulPlanStatusEnum.class),
	MESSAGE_STATUS(149,"消息状态",MessageStatusEnum.class),
	MESSAGE_TYPE(150,"消息类型"),
	MESSAGE_CATEGORY(151,"消息分类"),
	INSPECT_AREA(153,"巡检区域"),
	PRODUCTION_FACTORY(152,"生产厂家"),
	DIGIT(153,"计数单位"),
	FIRE_STYLE(124,"动火方式"),
	MATERIAL_CATEGORY(125,"物资类别"),
    WORKTICKETFIRE_TYPE(126, "动火工作票状态",WorkFireStatusEnum.class),
	APPLYTYPE(127, "申请类别",ApplyTypeEnum.class),
	EQUIPVOLTAGE(128, "设备电压",EquipVoltageEnum.class),
	PROTECTSTATUS(129, "保护投退状态",ProtectStatusEnum.class),
    JOIN_LAND_TYPE(130,"接地刀闸状态",JoinLandTypeEnum.class),
    RUN_CHECK_RESULT(155,"运行检查结果",RunCheckResultEnum.class),
    WORK_RECORD_TYPE(156,"工作记录类型"),
    RUN_CHECK(157,"检修状态"),
    RUN_WAY(158,"运行方式"),
    SYS_UNIT_LEVEL(159,"等级",SysUnitLevelEnum.class),
    BUSINESS_TYPE(160,"运行类型"),
	EQUIP_GRADE_PRE(161,"设备等级原评"),
	EQUIP_GRADE_NOW(162,"设备等级现评"),
	CONSTRUCTION_UNITS(163,"参建单位"),
	FLOORLINE_SWORDBRAKE_STATUS(164,"接地线刀闸状态"),
	GRADE_TYPE(165,"缺陷等级"),
	REPEAT_TYPE(166,"是否属于重复缺陷"),
	APPRAISAL_RESULT(167,"鉴定结果"),
	PLAN_CYCLE(168,"计划类型",PlanCycleEnum.class),
	PLAN_TYPE(169,"计划类型"),
	TECHNICAL_TYPE(170, "技术监督"),
	WORKHELP_TYPE(171,"继电状态",WorkHelpStatusEnum.class),
	WORKLINE_TYPE(172,"电力线路状态",WorkLineStatusEnum.class),
	FINISH_TYPE(173, "完成状态"),
	TECHNICAL_WCZT(174,"运行检查结果",TechnicalWcztEnum.class),
	STANDARD_TYPE(175,"标准管理",StandardTypeEnum.class),
	OVERHUALARRANGE_FINISHSTATUS(176,"完成状态",OverhaulArrangeEnum.class),
	OVERHUALWORKTASK_STATUS(177,"完成状态",OverhaulWorkTaskStatusEnum.class),
	DATEUTIL(178,"时间单位",TicketDateUtilEnum.class),
	WAREHOUSE_TYPE(179,"仓库类型"),
	WAREHOUSE_STATUS(180,"仓库启停用状态"),
	PARENT_WAREHOUSE(181,"父仓库"),
	GOODS_ATTRIBUTE(182,"物资属性"),
	EQUIPMANAGETYPE(183,"设备管理类型"),
	FIRETWO_TYPE(184, "动火二票状态",WorkFireTwoStatusEnum.class),
	OVERHAUL_CLASS(185, "检修分类"),
	YEARNUM(186,"年号"),
	SYSMANAGENTMENTTYPE(187,"制度管理类别"),
	SYSMANAGEMENTSTATUS(188,"制度管理状态",SysManagementStatusEnum.class),
	SCRAPTYPE(189,"报废入库类型"),
	PROCESSMODE(190,"处理方式"),
	SCRAPLIBRARYSTATUS(191,"报废库状态",ScrapLibraryStatusEnum.class),
	SCRAPSOUCE(192,"报废来源",ScrapSourceEnum.class),
	KNOWLEDGE_TYPE(193,"知识库管理类型"),
	ASSET_DEFUALT_TYPE(194,"资质管理类型"),
	EMERGENCYTYPE(195,"应急类型"),
	SGGL_TYPE(196, "类别",SgglTypeEnum.class),

	SAFESTANDARD_TYPE(197,"安全标准化类型"),
	PLANTYPE(199,"计划分类"),
	DISPATHCH_FILE_TYPE(198,"发文文件分类"),

	COMPANY_TRENDS_TYPE(200,"公司动态类型"),
	DISPATHCH_NUMBER(201,"发文文号"),
	SEND_FILE_TYPE(202,"发文类型"),
	SUPERVISIONMAJOR(204,"监督专业"),
	TRAININGTYPE(205,"培训类别"),
	EQUIPTYPE(206,"物资采购设备类别"),
	GOODS_PLAN_TYPE(207,"物资采购计划类别"),
	YEAR_PURCHASE_STATUS(210,"年度采购状态",YearPurchaseStatusEnum.class),
	INVESTMENT_PLANE_STATUS(213,"投资计划状态",InvestmentPlanStatusEnum.class),
	HIDDEN_TROUBLE_TYPE(214,"隐患排查类别"),
	GOODS_PURCHASE_STATUS(215,"物资采购状态",GoodsPurchaseStatusEnum.class),
	CONTRACT_MANAGE_STATUS(216,"合同管理状态",ContractManageApproveStatusEnum.class),
	DISPATCH_FILE_TYPE(217,"发文类型"),
	RECEIPT_TYPE(218,"收文分类"),
	RECEIPT_OPERATE_TYPE(219, "收文操作类型"),
	IS_POWER(220,"是否停送电",IsPowerEnum.class),
	POWER_LEVEL(221,"电压等级",PowerLevelEnum.class),
	SAFE_CHECK_MODE(222,"安全问题检查检查方式"),
	HIDDEN_TROUBLE_CHECK_MODE(223,"隐患排查方式"),
	ZBFS(224, "招标方式",ZbfsEnum.class),
	PURCHASE_MODE(225,"采购方式"),
	SAFE_CLASSIFY(226,"安措分类"),
	ANNUAL_PRODUCTION_TYPE(227,"安措分类"),
	ANNUALQUOTATYPE(228,"安措分类"),
	QUOTATYPE(229,"安措分类"),
	ASSESSMENT_METHODS(230,"考核方式"),
	RESULT(231,"鉴定结果"),
	COMPLETE_STATUS(232,"完成状态"),
	SEAL_STATUS(233,"封存状态"),
	INSPECTION_TYPE(234, "场站运行日志巡检类型"),
	FAN_FACTORY_RECORD_TYPE(235, "场站运行日志记录类型");

	private Integer id;
	
	/**
	 * 码表的一级分类名字
	 */
	private String name;
	
	/**
	 * 码表二级分类的获取方式，如果该字段为null，则系统默认从数据中对应的码表中获取二级分类
	 */
	private Class<?> itemProviderClass;
	
	BusinessDictCategoryEnum(Integer id, String name){
		this.id = id;
		this.name = name;
	}
	
	private BusinessDictCategoryEnum(Integer id, String name,Class<?> itemProviderClass ) {
		this.id = id;
		this.name = name;
		this.itemProviderClass = itemProviderClass;
	}
	
	public Integer getId(){
		return id;
	}

	public String getName() {
		return name;
	}
	
	public Class<?> getItemProviderClass(){
		return itemProviderClass;
	}
	
	
	/**
	 * 
	 * 获取数据库存储的编码类型
	 * 
	 * @param @param name
	 * @param @return
	 * @return List<Code>
	 * @throws 
	 * @author zhangjx
	 * @created 2017年3月13日 下午3:03:28
	 * @lastModified
	 */
	public static List<SysDictionaryVO> getDBCategoryList(String name){
		List<SysDictionaryVO> dictionaryList = new ArrayList<SysDictionaryVO>();
		for(BusinessDictCategoryEnum codeEnum : BusinessDictCategoryEnum.values()){
			if(StringUtil.isEmpty(name) || codeEnum.getName().matches(".*" + name + ".*")) {
				if(codeEnum.getItemProviderClass()==null){
					SysDictionaryVO dictionary = new SysDictionaryVO();
					dictionary.setId(Long.valueOf(codeEnum.getId()));
					dictionary.setCode(codeEnum.name());
					dictionary.setCategoryCode("business");
					dictionary.setParentId(new Long(-1));
					dictionary.setOrder(codeEnum.ordinal());
					dictionary.setName(codeEnum.getName());
					dictionary.setType("business");
					dictionaryList.add(dictionary);
				}
			}
		}
		return dictionaryList;
	}
	
	/**
	 * 
	 * 获取枚举的数据库字典类型
	 * 
	 * @param @param name
	 * @param @return
	 * @return List<SysDictionaryVO>
	 * @throws 
	 * @author zhangjx
	 * @created 2017年4月8日 下午2:02:18
	 * @lastModified
	 */
	public static List<SysDictionaryVO> getEnumCategoryList(String name){
		List<SysDictionaryVO> dictionaryList = new ArrayList<SysDictionaryVO>();
		for(BusinessDictCategoryEnum codeEnum : BusinessDictCategoryEnum.values()){
			if(StringUtil.isEmpty(name) || codeEnum.getName().matches(".*" + name + ".*")) {
				if(codeEnum.getItemProviderClass()!=null){
					SysDictionaryVO dictionary = new SysDictionaryVO();
					dictionary.setId(Long.valueOf(codeEnum.getId()));
					dictionary.setCode(codeEnum.name());
					dictionary.setCategoryCode("business");
					dictionary.setParentId(new Long(-1));
					dictionary.setOrder(codeEnum.ordinal());
					dictionary.setName(codeEnum.getName());
					dictionary.setType("business");
					dictionaryList.add(dictionary);
				}
			}
		}
		return dictionaryList;
	}
	
	/**
	 * 
	 * 根据ID获取字典分类
	 * 
	 * @param @param id
	 * @param @return
	 * @return SysDictionaryVO
	 * @throws 
	 * @author zhangjx
	 * @created 2017年3月13日 下午6:32:25
	 * @lastModified
	 */
	public static SysDictionaryVO getCategoryById(Integer id){
		SysDictionaryVO dictionary = null;
		if(id != null){
			for(BusinessDictCategoryEnum codeEnum : BusinessDictCategoryEnum.values()){
				if(codeEnum.getId().equals(id)){
					dictionary = new SysDictionaryVO();
					dictionary.setId(Long.valueOf(codeEnum.ordinal()));
					dictionary.setCode(codeEnum.name());
					dictionary.setParentId(new Long(-1));
					dictionary.setCategoryCode(codeEnum.name());
					dictionary.setOrder(codeEnum.ordinal());
					dictionary.setName(codeEnum.getName());
				}
			}
		}
		return dictionary;
	}
	
	/**
	 * 
	 * 获取字典项
	 * 
	 * @param @return
	 * @return Map<String,SysDictionaryVO>
	 * @throws 
	 * @author zhangjx
	 * @created 2017年3月13日 下午8:41:57
	 * @lastModified
	 */
	public Map<String, SysDictionaryVO> getDictItems() {
		Map<String, SysDictionaryVO> result = new LinkedHashMap<String, SysDictionaryVO>();
		if (itemProviderClass == null) {// 默认从数据中对应的码表中获取二级分类
			return DictionaryUtil.getDictionaries(name());
		} else if (itemProviderClass.isEnum()) {
			Method ennumCodeMethod = ReflectionUtil.getDeclaredMethod(itemProviderClass,"getCode");
			Method idMethod = ReflectionUtil.getDeclaredMethod(itemProviderClass,"getId");
			Method ordinalMethod = ReflectionUtil.getDeclaredMethod(itemProviderClass,"ordinal");
			Method nameMethod = ReflectionUtil.getDeclaredMethod(itemProviderClass,"getName");
			for (Object enumConstant : itemProviderClass.getEnumConstants()) {
				SysDictionaryVO codeObj = new SysDictionaryVO();
				String code = (String)ReflectionUtil.invokeMethod(ennumCodeMethod,enumConstant);
				Integer id = (Integer)ReflectionUtil.invokeMethod(idMethod,enumConstant);
				int ordinal = (Integer)ReflectionUtil.invokeMethod(ordinalMethod,enumConstant);
				String displayName = (String)ReflectionUtil.invokeMethod(nameMethod,enumConstant);
				codeObj.setId(Long.valueOf(id));
				codeObj.setCode(code);
				codeObj.setCategoryCode(this.name());
				codeObj.setName(displayName);
				codeObj.setOrder(ordinal);
				codeObj.setParentId(new Long(this.getId()));
				result.put(code, codeObj);
			}
		}
		return result;
	}
}
