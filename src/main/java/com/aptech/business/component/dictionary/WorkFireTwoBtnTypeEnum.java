package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum WorkFireTwoBtnTypeEnum implements BaseCodeEnum {
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
	 * "5", "运行值班人员收票"
	 */
	SP(5, "5", "运行值班人员收票"),
	/**
	 * "6", "工作许可人审批"
	 */
	XK(6, "6", "工作许可人审批"),
	/**
	 * "7", "消防监护人审批（许可）"
	 */
	XFJHRXK(7, "7", "消防监护人审批（许可）"),
	/**
	 * "8", "验收"
	 */
	YS(8, "8", "验收"),
	/**
	 * "9", "动火负责人验收
	 */
	DHFZRYS(9, "9", "动火负责人验收"),
	/**
	 * "10", "消防监护人审批（终结）
	 */
	XFJHRZJ(10, "10", "消防监护人审批（终结）"),
	/**
	 * "11", "工作许可人审批（终结）
	 */
	XKZJ(11, "11", "工作许可人审批（终结）"),
	/**
	 * "12", "工作许可人审批（终结）
	 */
	GZJD(12, "12", "工作交底"),
	
	
	/**
	 * "21", "终结
	 */
	ZJ(21, "21", "终结"),
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
	
	WorkFireTwoBtnTypeEnum(Integer id, String code, String name){
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
