package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum WorkBtnTypeEnum implements BaseCodeEnum {
	/**
	 * "1", "签发"
	 */
	QF(1, "1", "签发"),
	/**
	 * "2", "收票"
	 */
	SP(2, "2", "收票"),
	/**
	 * "3", "值长签字"
	 */
	ZZQZ(3, "3", "值长签字"),
	/**
	 * 4", "许可
	 */
	XK(4, "4", "许可"),
	/**
	 * "5", "工作负责人变更"
	 */
	GZFZRBG(5, "5", "工作负责人变更"),
	/**
	 * "6", "签发(负责人变更)
	 */
	GZFZRBGQF(6, "6", "签发(负责人变更)"),
	/**
	 * "7", "许可(负责人变更)"
	 */
	GZFZRBGXK(7, "7", "许可(负责人变更)"),
	/**
	 * "8", "工作人员变动"
	 */
	GZRYBD(8, "8", "工作人员变动"),
	/**
	 * "9", "负责人确认（工作人员变动）"
	 */
	GZRYBDFZR(9, "9", "负责人确认（工作人员变动）"),
	/**
	 * "10", "延期"
	 */
	YQ(10, "10", "延期"),
	/**
	 * "11", "值长签字（延期）
	 */
	YQZZQZ(11, "11", "值长签字（延期）"),
	/**
	 * "12", "许可（延期）
	 */
	YQXK(12, "12", "许可（延期）"),
	/**
	 * "13", "负责人签字（延期）
	 */
	YQFZR(13, "13", "负责人签字（延期）"),
	/**
	 * "14", "终结")
	 */
	ZJ(14, "14", "终结"),
	/**
	 * "15", "许可（终结）
	 */
	ZJXK(15, "15", "许可（终结）"),
	/**
	 * "16", "申请试运"
	 */
	SQSY(16, "16", "申请试运"),
	/**
	 * "17", "许可（申请试运）
	 */
	SQSYXK(17, "17", "许可（申请试运）"),
	/**
	 * "18", "值长签字（申请试运）
	 */
	SQSYZZQZ(18, "18", "值长签字（申请试运）"),
	/**
	 * "19", "试运恢复"
	 */
	SYHF(19, "19", "试运恢复"),
	/**
	 * 20", "许可（试运恢复）
	 */
	SYHFXK(20, "20", "许可（试运恢复）"),
	/**
	 * "21", "值长签字（试运恢复）"
	 */
	SYHFZZQZ(21, "21", "值长签字（试运恢复）"),
	/**
	 * "22", "废票
	 */
	FP(22, "22", "废票"),
	/**
	 * "23", "再提交
	 */
	ZTJ(23, "23", "再提交"),
	/**
	 * "24", "值长（终结）
	 */
	ZJZZ(24, "24", "值长（终结）"),
	/**
	 * "25", "工作交底"
	 */
	GZJD(25, "25", "工作交底"),
	
	/**
	 * "26", "执行
	 */
	ZX(26, "26", "执行"),
	/**
	 * "27", "执行监护
	 */
	ZXJH(27, "27", "执行监护"),
	/**
	 * "28", "恢复
	 */
	HF(28, "28", "恢复"),
	/**
	 * "29", "恢复监护
	 */
	HFJH(29, "29", "恢复监护"),
	
	/**
	 * "30", "检修专工
	 */
	JXZG(30, "30", "检修专工"),
	/**
	 * "31", "检修主任
	 */
	JXZR(31, "31", "检修主任"),
	/**
	 * "32", "生技部主任
	 */
	SJBZR(32, "32", "生技部主任"),
	/**
	 * "33", "总工
	 */
	ZG(33, "33", "总工"),
	/**
	 * "34", "负责人提交
	 */
	FZRTJ(34, "34", "负责人提交"),
	/**
	 * "35", "撤销
	 */
	CX(35, "35", "撤销");
	
	private Integer id;
	
	private String code;
	
	private String name;
	
	WorkBtnTypeEnum(Integer id, String code, String name){
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
