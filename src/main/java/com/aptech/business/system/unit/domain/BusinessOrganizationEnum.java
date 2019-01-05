package com.aptech.business.system.unit.domain;

public enum BusinessOrganizationEnum {
	/*0 中电建贵阳院  1 风场 2 期次 3 集控中心 4 检修中心  5 处室 6 投资运营部 7 清镇水务公司 8 政府机构 9 外部单位 10工会 11 党支部 12 其它*/
	ZDJGYY("0", "中电建贵阳院"),
	WIND("1", "风场"),
	ISSUE("2", "期次"),
	CENTRALIZEDCENTER("3", "集控中心"),
	OVERHAULCENTER("4", "检修中心"),
	OFFICE("5", "处室"),
	INVESTMENT("6", "投资运营部"),
	WATERFACTORY("7", "清镇水务公司"),
	GOVERNMENT("8", "政府机构"),
	OUTCOMPANRY("9", "外部单位"),
	UNION ("10", "工会"),
	PARTY ("11", "党支部"),
	OTHER ("12", "其它");

	private String code;
	
	private String name = null;
	
	BusinessOrganizationEnum(String code, String name){
		this.code = code;
		this.name = name;
	}
	
	public String getCode(){
		return code;
	}
	
	public String getName(){
		return name;
	}
}
