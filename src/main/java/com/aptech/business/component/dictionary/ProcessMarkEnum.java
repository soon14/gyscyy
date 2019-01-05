package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/**
 * 
 * 流程标识的枚举
 *
 * @author wangjw
 * @created 2017年3月14日 下午3:07:12
 * @lastModified
 * @history
 *
 */
public enum ProcessMarkEnum implements BaseCodeEnum{
	/**
	 * 工作票流程试验用
	 */
	WORK_BILL_PROCESS_KEY(1, "WORK_BILL_PROCESS_KEY", "process_261"),
	/**
	 * 电气第一种工作票流程 正式
	 */
	WORK_TICKET_PROCESS_KEY(2, "WORK_TICKET_PROCESS_KEY", "process_269"),
	/**
	 * 电气第二种工作票流程 正式
	 */
	WORK_TICKET_SECOND_PROCESS_KEY(3, "WORK_TICKET_SECOND_PROCESS_KEY", "process_277"),	
	/**
	 * 检修计划流程 正式
	 */
	OVERHAUL_PLAN_PROCESS_KEY(4, "WORK_TICKEOCESS_KEY", "process_274"),
	/**
	 * 缺陷管理 正式
	 */
	DEFECT_PROCESS_KEY(10, "DEFECT_PROCESS_KEY", "process_304"),
	/**
	 * 操作票 正式
	 */
	OPERATIONTICKET_PROCESS_KEY(5,"OPERATIONTICKET_PROCESS_KEY","process_286"),
	/**
	 * 典型票并行正式
	 */
	TYPICALTICKET_PROCESS_KEY20(20,"TYPICALTICKET_PROCESS_KEY","process_289"),
	/**
	 * 典型票非并行正式
	 */
	TYPICALTICKET_PROCESS_KEY(6,"TYPICALTICKET_PROCESS_KEY","process_290"),
	/**
	 * 风力机械工作票流程 正式
	 */
	WORK_TICKET_WINDMECHANICAL_PROCESS_KEY(7, "WORK_TICKET_WINDMECHANICAL_KEY", "process_288"),
    /**
     * 保护投退正式
     */
//    PROTECT_PROCESS_KEY(8,"PROTECT_PROCESS_KEY","process_271"),
	PROTECT_PROCESS_KEY(8,"PROTECT_PROCESS_KEY","process_296"),
    /**
     * 定期工作计划正式
     */
    WORKPLAN_PROCESS_KEY(9,"WORKPLAN_PROCESS_KEY","process_275"),
    /**
     * 定期工作记录正式
     */
    WORKRECORD_PROCESS_KEY(10,"WORKRECORD_PROCESS_KEY","process_276"),
    /**
     * 动土工作票正式
     */
    WORK_TICKET_EARTH_PROCESS_KEY(11,"WORK_TICKET_EARTH_PROCESS_KEY","process_291"),
	/**
	 * 入库管理
	 */
	IN_STOCK_PROCESS_KEY(12,"IN_STOCK_PROCESS_KEY","process_292"),
	/**
	 * 出库管理
	 */
	OUT_STOCK_PROCESS_KEY(13,"OUT_STOCK_PROCESS_KEY","process_293"),

	/**
	 * 介入票
	 */
	INTERVENTIONTICKE_PROCESS_KEY(14, "WORK_TICKET_SECOND_PROCESS_KEY", "process_294"),
	/**
	 * 停送电管理
	 */
	POWER_PROCESS_KEY(15, "POWER_PROCESS_KEY", "process_295"),
	/**
	 * 抢修单
	 */
	REPAIRTICKE_PROCESS_KEY(16, "WORK_TICKET_SECOND_PROCESS_KEY", "process_297"),
	/**
	 * 设备异动流程
	 */
	EQUIP_ABNORMAL_PROCESS_KEY(19,"EQUIP_ABNORMAL_PROCESS_KEY","process_298"),
	/**
	 * 风力自控工作票流程
	 */
	WORK_TICKET_WINDAUTO_PROCESS_KEY(17, "WORK_TICKET_WINDAUTO_PROCESS_KEY", "process_300"),
	
    /**
     * 一级动火工作票
     */
    WORK_TICKET_FIRE_PROCESS_KEY(18, "WORK_TICKET_FIRE_PROCESS_KEY", "process_309"),
    
    /**
     * 二级动火工作票
     */
    WORK_TICKET_FIRE_TWO_PROCESS_KEY(20, "WORK_TICKET_FIRE_TWO_PROCESS_KEY", "process_308"),
	/**
	 * 线路第一种工作票流程 
	 */
	WORK_TICKET_LINE_PROCESS_KEY(21, "WORK_TICKET_LINE_PROCESS_KEY", "process_299"),
	/**
	 * 线路第二种工作票流程 
	 */
	WORK_TICKET_LINETWO_PROCESS_KEY(22, "WORK_TICKET_LINETWO_PROCESS_KEY", "process_300"),
	/**
	 * 继电保护安全措施票 
	 */
	WORK_TICKET_HELPSAFE_PROCESS_KEY(23, "WORK_TICKET_HELPSAFE_PROCESS_KEY", "process_301"),
	/**
	 * 计划管理 
	 */
	PLAN_PROCESS_KEY(24, "PLAN_PROCESS_KEY", "process_302"),
	/**
	 * 技术监督
	 */
	TECHNICAL_PROCESS_KEY(25, "TECHNICAL_PROCESS_KEY", "process_303"),
	
	/**
	 * 公司动态
	 */
	TECHNICAL_COMPANYTRENDS(26, "TECHNICAL_COMPANYTRENDS", "process_310"),
	/**
	 * 总结
	 */
	TECHNICAL_SUMMARY(27, "TECHNICAL_SUMMARY", "process_312"),


	/**
	 * 文件学习
	 */
	FILE_LEARN_PROCESS_KEY(28, "FILE_LEARN_PROCESS_KEY", "process_311"),
	
	/**
	 * 发文管理
	 */
	DISPATCH_MANAGEMENT_KEY(29, "DISPATCH_MANAGEMENT_KEY", "process_312"),
	/**
	 * 报废出库管理
	 */
	OUT_STOCK_DRAW_PROCESS_KEY(32,"OUT_STOCK_DRAW_PROCESS_KEY","process_313"),
	/**
	 * 报废库库外申请
	 */
	IN_STOCK_DRAW_PROCESS_KEY(33,"IN_STOCK_DRAW_PROCESS_KEY","process_314"),
	/**
	 * 报废库库内申请
	 */
	OUT_SCRAP_STOCK_DRAW_PROCESS_KEY(50,"OUT_SCRAP_STOCK_DRAW_PROCESS_KEY","process_315"),
	/**
	 * 合同管理
	 */

	CONTRACT_MANAGE_PROCESS_KEY(30, "CONTRACT_MANAGE_PROCESS_KEY", "process_316"),
	/**
	 * 年度采购
	 */
	YEAR_PURCHASE_PROCESS_KEY(35, "YEAR_PURCHASE_PROCESS_KEY", "process_318"),
	/**
	 * 投资计划
	 */
	INVESTMENT_PLAN_PROCESS_KEY(37, "INVESTMENT_PLAN_PROCESS_KEY", "process_321"),

	/**
	 * 物资采购
	 */
	GOODS_PURCHASE_PROCESS_KEY(45,"GOODS_PURCHASE_PROCESS_KEY","process_326"),

	SCIENCE_TECHNOLOGY_PLAN_PROCESS_KEY(40,"SCIENCE_TECHNOLOGY_PLAN_PROCESS_KEY","process_317"),
	/**
	 * 年度科技计划
	 */
	ANNUAL_TECHNICAL_PLAN_PROCESS_KEY(41,"ANNUAL_TECHNICAL_PLAN_PROCESS_KEY","process_319"),
	/**
	 * 年度技术监督计划
	 */
	ANNUAL_SUPERVISION_PLAN_PROCESS_KEY(42,"ANNUAL_SUPERVISION_PLAN_PROCESS_KEY","process_320"),
	/**
	 * 年度培训计划
	 */
	ANNUAL_TRAINING_PLAN_PROCESS_KEY(43,"ANNUAL_TRAINING_PLAN_PROCESS_KEY","process_322"),
	/**
	 * 年度反措施计划
	 */
	ACCIDENT_MEASURES_PLAN_PROCESS_KEY(44,"ACCIDENT_MEASURES_PLAN_PROCESS_KEY","process_323"),
	/**
	 * 年度检修维护计划
	 */
	ANNUAL_MAINTENANCE_PLAN_PROCESS_KEY(45,"ANNUAL_MAINTENANCE_PLAN_PROCESS_KEY","process_324"),
	/**
	 * 三年滚动检修计划
	 */
	ROLLING_MAINTENANCE_PLAN_PROCESS_KEY(46,"ROLLING_MAINTENANCE_PLAN_PROCESS_KEY","process_325"),
	/**
	 * 年度生产物资计划(集团年度生产量流程)
	 */
	ANNUAL_PRODUCTION_PLAN_PROCESS_KEY(47,"ANNUAL_PRODUCTION_PLAN_PROCESS_KEY","process_327"),
	/**
	 * 月度生产物资计划
	 */
	MONTHLY_PRODUCTION_PLAN_PROCESS_KEY(48,"MONTHLY_PRODUCTION_PLAN_PROCESS_KEY","process_328"),
	/**
	 * 收文管理
	 */
	RECEIPT_MANAGEMENT_KEY(49,"RECEIPT_MANAGEMENT_KEY","process_329"),
	/**
	 * 典型票修改并行正式
	 */
	TYPICALTICKET_PROCESS_KEY30(50,"TYPICALTICKET_PROCESS_KEY","process_330"),
	
	/**
	 * 培训管理
	 */
	TRAIN_MANAGEMENT_PROCESS_KEY(51,"TRAIN_MANAGEMENT_PROCESS_KEY","process_331"),
	/**
	 * 报废处理
	 */
	SCRAPLIBRARY_HANDLE_PROCESS_KEY(52,"SCRAPLIBRARY_HANDLE_PROCESS_KEY","process_332"),
	/**
	 * 总结流程
	 */
	TECHNICAL_SUMMARY_PROCESS_KEY(53,"TECHNICAL_SUMMARY_PROCESS_KEY","process_334"),
	/**
	 * 事件通报
	 */
	EVENT_NOTIFICATION_PROCESS_KEY(54,"EVENT_NOTIFICATION_PROCESS_KEY","process_335"),
	/**
	 * 试验报告流程
	 */
	TEST_REPORT_PROCESS_KEY(55,"TEST_REPORT_PROCESS_KEY","process_336"),
	/**
	 * 事件分析流程
	 */
	EVENT_ANALYSIS_PROCESS_KEY(56,"EVENT_ANALYSIS_PROCESS_KEY","process_337"),
	/**
	 * 设备异动报告流程
	 */
	EQUIP_ABNORMAL_REPORT_PROCESS_KEY(57,"EQUIP_ABNORMAL_REPORT_PROCESS_KEY","process_338"),
	/**
	 * 内控年度生产量流程
	 */
	NEIKONG_CAPACITY_KEY(58,"NEIKONG_CAPACITY_KEY","process_340"),
	/**
	 * 年度生产指标计划流程
	 */
	NUNNAL_QUOTA_KEY(59,"NUNNAL_QUOTA_KEY","process_341"),
	/**
	 * 年度生产工作计划流程
	 */
	NUNNAL_JOB_KEY(60,"NUNNAL_JOB_KEY","process_342"),
	/**
	 * 月度生产工作计划流程
	 */
	MONTH_JOB_KEY(61,"MONTH_JOB_KEY","process_343"),
	/**
	 * 文件学习通知流程
	 */
	FILE_LEARN_NOTIFICATION_PROCESS(62,"FILE_LEARN_NOTIFICATION_PROCESS","process_344");


	
	ProcessMarkEnum(Integer id, String code, String name){
		this.id = id;
		this.code = code;
	    this.name = name;
	}
	
	private Integer id;
	
	private String name;

	private String code;

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return this.code;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}	
}
