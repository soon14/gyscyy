package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum WorkFireBtnTypeEnum implements BaseCodeEnum {
	/**
	 * "1", "签发"
	 */
	QF(1, "1", "签发"),
	/**
	 * "2", "会签人签发"
	 */
	HQF(2, "2", "会签人签发"),
	/**
	 * "3", "消防监护人审批"
	 */
	XFJHR(3, "3", "消防监护人审批"),
	/**
	 * 4", "安监部门负责人审批
	 */
	AJBM(4, "4", "安监部门负责人审批"),
	/**
	 * "5", "安全总监审批"
	 */
	AQZJ(5, "5", "安全总监审批"),
	/**
	 * "6", "分管生产负责人（或总工程师）审批
	 */
	FGSCFZR(6, "6", "分管生产负责人（或总工程师）审批"),
	/**
	 * "7", "运行值班人员收票"
	 */
	SP(7, "7", "运行值班人员收票"),
	/**
	 * "8", "工作许可人审批"
	 */
	XK(8, "8", "工作许可人审批"),
	/**
	 * "9", "消防监护人审批（许可）"
	 */
	XFJHRXK(9, "9", "消防监护人审批（许可）"),
	/**
	 * "10", "验收"
	 */
	YS(10, "10", "验收"),
	/**
	 * "11", "动火负责人验收
	 */
	DHFZRYS(11, "11", "动火负责人验收"),
	/**
	 * "12", "消防监护人审批（终结）
	 */
	XFJHRZJ(12, "12", "消防监护人审批（终结）"),
	/**
	 * "13", "工作许可人审批（终结）
	 */
	XKZJ(13, "13", "工作许可人审批（终结）"),
	/**
	 * "14", "工作许可人审批（终结）
	 */
	GZJD(14, "14", "工作交底"),
	
	
	
	
	/**
	 * "22", "废票
	 */
	FP(22, "22", "废票"),
	/**
	 * "23", "再提交
	 */
	ZTJ(23, "23", "再提交");


	private Integer id;
	
	private String code;
	
	private String name;
	
	WorkFireBtnTypeEnum(Integer id, String code, String name){
		this.id = id;
		this.code = code;
		this.name = name;
	}
	
	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Integer getId() {
		return this.id;
	}

}
