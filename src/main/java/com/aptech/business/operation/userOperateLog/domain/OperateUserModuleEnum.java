package com.aptech.business.operation.userOperateLog.domain;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum OperateUserModuleEnum implements BaseCodeEnum{
	INDEX(0,"首页配置","首页配置"),
	COLUMN(1,"栏目","栏目"),
	RUNLOG(2,"集控运行日志","集控运行日志"),
	DISPACOM(3,"调度命令","调度命令"),
	PROTECT(4,"保护投退","保护投退"),
	RUNANLYSIS(5,"运行分析","运行分析"),
	WORKPLAN(6,"定期工作计划","定期工作计划"),
	WORKRECORD(7,"定期工作记录","定期工作记录"),
	WORKELECTRIC(8,"电气第一种工作票","电气第一种工作票"),
	WORKELECTRICTWO(9,"电气第二种工作票","电气第二种工作票"),
	WORKFIRE(10,"一级动火工作票","一级动火工作票"),
	WORKFIRETWO(11,"二级动火工作票","二级动火工作票"),
	WORKEARTH(12,"动土工作票","动土工作票"),
	WORKWINDMECHANICAL(13,"风机工作票","风机工作票"),
	WORKWINDAUTO(14,"风力自控工作票","风力自控工作票"),
	INTERVENTIONTICKET(15,"介入工作票","介入工作票"),
	REPAIRTICKET(16,"紧急抢修工作票","紧急抢修工作票"),
	OPERATIONTICKET(17,"操作票","操作票"),
	TYPICALTICKET(18,"典型票","典型票"),
	DEFECT(19,"缺陷","缺陷"),
	OVERHAULPLAN(20,"检修计划","检修计划"),
	OVERHAULLOG(21,"检修日志","检修日志"),
	OVERHAULFILE(22,"检修文件","检修文件"),
	POWER(23,"停送电计划","停送电计划"),
	INSPECTRECORD(24,"巡检记录","巡检记录"),
	INSPECTPROJECTSETTING(25,"巡检项目设置","巡检项目设置"),
	EQUIPLEDGER(26,"设备管理","设备管理"),
	EQUIPTREE(27,"设备","设备"),
	EQUIPMODEL(28,"设备模板","设备模板"),
	EQUIPABNORMAL(29,"设备异动","设备异动"),
	MATERIALCATEGORY(30,"物资信息","物资信息"),
	INSTOCKMOVE(31,"入库单","入库单"),
	OUTSTOCKMOVE(32,"出库单","出库单"),
	INFOSET(33,"信息集","信息集"),
	PROJECTPLAN(34,"技改项目","技改项目"),
	TECHNOLOGYPROJECTPLAN(35,"科技信息化项目","科技信息化项目"),
	PROJECTSITUATION(36,"技改项目完成情况","技改项目完成情况"),
	TECHNOLOGYPROJECTSITUATION(37,"科技信息化项目完成情况","科技信息化项目完成情况"),
	SYSUNIT(38,"组织机构","组织机构"),
	PERSONALMANAGE(39,"人员","人员"),
	ORGAAPP(40,"班组","班组"),
	TEAMMEMAPP(41,"班次","班次"),
	DUTYORDER(42,"值次","值次"),
	SYSUSER(43,"用户","用户"),
	SYSROLE(44,"角色","角色"),
	SYSMENUITEM(45,"菜单","菜单"),
	SYSDICTIONARY(46,"数据字典","数据字典"),
	SYSFUNCTION(47,"功能","功能"),
	SYSDUTIES(48,"职务","职务"),
	SYSCONFIG(49,"配置","配置"),
	MESSAGECENTER(50,"消息中心","消息中心"),
	FOURCODE(51,"编码","编码"),
	SUPPLIER(52,"供应商","供应商"),
	WAREHOUSE(53,"仓库","仓库"),
	WAREHOUSEAREA(54,"货区/货位","货区/货位"),
	SYS(55,"系统","系统"),
	RUNLOGWIND(56,"场站运行日志","场站运行日志"),
	DAILYREPORT(57,"日例会","日例会"),
	WORK(58,"工作安排","工作安排"),
	RUNRECORD(59,"运行记事","运行记事"),
	RUNWAY(60,"运行方式","运行方式"),
	JOINLAND(61,"接地线刀闸","接地线刀闸"),
	ROUTEINSPECT(62,"巡检记录","巡检记录"),
	WORKTICKETLINE(63,"电力线路第一种工作票","电力线路第一种工作票"),
	WORKTICKETLINGTWO(64,"电力线路第二种工作票","电力线路第二种工作票"),
	EQUIPAPPRAISE(65,"设备评级","设备评级"),
	STOCK(66,"库存","库存"),
	SECURITYAPPARATUS(67,"安全工器具","安全工器具"),
	SCRAPLIBRARY(68,"报废库","报废库"),
	TECHNICAL(69,"技术监督","技术监督"),
	SUMMARY(70,"技术监督总部","技术监督总部"),
	PLAN(71,"计划管理","计划管理"),
	INVESTNEBTPLAN(72,"投资计划","投资计划"),
	YEARPURCHASE(73,"年度采购计划","年度采购计划"),
	GOODSPURCHASE(74,"物资采购计划","物资采购计划"),
	DISPATCH(75,"发文管理","发文管理"),
	RECEIPT(76,"签报管理","签报管理"),
	TRAINMANAGEMENT(77,"培训管理","培训管理"),
	TRAINPLAN(78,"培训计划","培训计划"),
	STANDARD(79,"标准管理","标准管理"),
	REGULATIONS(80,"制度管理","制度管理"),
	SAFEREPORT(81,"安全月报","安全月报"),
	SAFESENDING(82,"安全发文","安全发文"),
	SAFESAFEMEETINGS(83,"安全会议","安全会议"),
	TARGETMANAGEHEADQUARTERS(84,"目标管理-总部","目标管理-总部"),
	TARGETMANAGEPRODUCTIONUNIT(85,"目标管理-生产单位","目标管理-生产单位"),
	ORGANIZATIONHEADQUARTERS(86,"组织机构-总部","组织机构-总部"),
	ORGANIZATIONPRODUCTIONUNIT(87,"组织机构-生产单位","组织机构-生产单位"),
	SUPERIORFILEGROUPCOMPANY(88,"上级文件-集团公司","上级文件-集团公司"),
	SUPERIORFILEGUIYANGYUAN(89,"上级文件-贵阳院","上级文件-贵阳院"),
	SUPERIORFILESUPERVISEUNIT(90,"上级文件-监管单位","上级文件-监管单位"),
	SAFECHECKHIGHUPCHECK(91,"安全检查-上级检查","安全检查-上级检查"),
	SAFECHECKPRODUCTIONUNIT(92,"安全检查-生产单位","安全检查-生产单位"),
	SAFECHECKPROBLEMANDABARBEITUNGINFO(93,"安全检查-存在问题及整改情况","安全检查-存在问题及整改情况"),
	QUALIFICATIONMANAGEMENTHEADQUARTERS(94,"资质管理-总部","资质管理-总部"),
	QUALIFICATIONMANAGEMENTRODUCTIONUNIT(95,"资质管理-生产单位","资质管理-生产单位"),
	SAFECULTUREA(96,"安全文化、安全活动-安全文化","安全文化、安全活动-安全文化"),
	SAFEACTIVITY (97,"安全文化、安全活动-安全活动","安全文化、安全活动-安全活动"),
	EDUCATIONTRAINHEADQUARTERS (98,"教育培训-总部","教育培训-总部"),
	EDUCATIONTRAINRODUCTIONUNIT(99,"教育培训-生产单位","教育培训-生产单位"),
	HIDDENTROUBLEDISPOSEHEADQUARTERS(100,"隐患排查-总部","隐患排查-总部"),
	HIDDENTROUBLEDISPOSEUNIT(101,"隐患排查-生产单位","隐患排查-生产单位"),
	HIDDENTROUBLE_HAVE_TROUBLE(114,"隐患排查-存在隐患及排查情况","隐患排查-存在隐患及排查情况"),
	EMERGENCIESMANAGE(102,"应急管理","应急管理"),
	ACCIDENTDISPOSE(103,"事故处理","事故处理"),
	INFORMATIONSUBMISSION (104,"信息报送","信息报送"),
	SAFESPRODUCTIONTANDARDCONSTRUCTION (105,"安全生产标准化建设","安全生产标准化建设"),

	THREETERMSBUSINESS(106,"三项业务","三项业务"),
	FILE_LEARN(107,"文件学习","文件学习"),
	COMPANY_TRENDS(108,"公司动态","公司动态"),
	
	EQUIPRECORD(109,"设备检修记录","设备检修记录"),
	BUDGETMANAGE(110,"预算管理","预算管理"),
	BUDGETMANAGEORGANIZATION(111,"预算管理组织机构","预算管理组织机构"),
	SUPPLIERMANAGE(112,"供应商管理","供应商管理"),
	PURCHASETYPE(113,"采购类型","采购类型"),
	EQUIPROVERHAULORGANIZATION(114,"设备检修记录单位","设备检修记录单位"),
	SAFE_CHECK_PROBLEM(114,"设备检修记录单位","设备检修记录单位"),
	HIDDEN_TROUBLE_CHECK(114,"设备检修记录单位","设备检修记录单位"),
	CONTRACT(117,"合同管理","合同管理"),
	EVENT_ANALYSIS(119,"事件分析","事件分析"),
	EVENT_NOTIFICATION(120,"事件通报","事件通报"),
	TECHNICAL_EXCHANGE(121,"技术交流","技术交流"),
	TEST_REPORT(122,"试验报告","试验报告"),
	CONTRACT_APPROVE(123,"合同审批","合同审批"),
	ANNUAL_PRODUCTION_JOB(124,"年度生产工作计划","年度生产工作计划"),
	MONTH_PRODUCTION_JOB(125,"月度生产工作计划","月度生产工作计划"),
	CONTRACT_DEAL(126,"合同签订","合同签订"),
	DEAL_STANDARD(127,"定标请示函","定标请示函"),
	PRODUCE_REPLY(128,"立项批复","立项批复"),
	WORK_SAFE_CONTENT(129,"安全措施信息","安全措施信息"),
	EQUIPABNORMALREPORT(118,"设备异动报告","设备异动报告");
	
	
	Integer id;
	String code;
	String name;
	
	private OperateUserModuleEnum(Integer id,String code,String name) {
		this.id = id;
		this.name = name;
		this.code = code;
	}
	@Override
	public String getName(){
		return this.name;
	}
	@Override
	public Integer getId(){
		return this.id;
	}

	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return code;
	}

}
