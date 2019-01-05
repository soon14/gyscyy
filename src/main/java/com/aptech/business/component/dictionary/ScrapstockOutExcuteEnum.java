package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum ScrapstockOutExcuteEnum implements BaseCodeEnum {
	 /**
     * 驳回
     */
    BACK_END(0, "BACK_END", "驳回"),
    /**
     * 可修复
     */
    CANREPAIR(1,"CANREPAIR","可修复"),
    /**
     * 不可修复
     */
    CANNOTREPAIR(2, "CANNOTREPAIR", "报废库专业人员不修复"),
	 /**
     * 生技部同意
     */
    SKILLAGREE(1, "SKILLAGREE", "生技部同意"),
    /**
     * 生产副总审核同意
     */
    LEADERAGREE(1,"LEADERAGREE","生产副总审核同意"),
	 /**
     * 库管员取消流程
     */
    FLOWCANCEL(2, "FLOWCANCEL", "库管员取消流程"),
    /**
     * 库管员再次提交
     */
    SUBMITAGAIN(1,"SUBMITAGAIN","库管员再次提交"),
    /**
     * 库管员变卖
     */
    SHOP(1,"SHOP","库管员变卖"),
    /**
     * 库管员丢弃
     */
    DISCARD(2,"DISCARD","库管员丢弃"),
    /**
     * 财务人员已入账
     */
    MONEYIN(1,"MONEYIN","财务人员已入账"),
    /**
     * 专业人员已修复
     */
    TECHNOLOGYREPAIR(1,"TECHNOLOGYREPAIR","专业人员已修复"),
    /**
     * 专业人员不修复
     */
    TECHNOLOGYNOREPAIR(2,"TECHNOLOGYNOREPAIR","专业人员不修复"),
    /**
     * 专业人员已确认修复
     */
    SUREREPAIR(1,"SUREREPAIR","专业人员已修复"),
    /**
     * 专业人员确认未修复
     */
    REPAIRFALSE(0,"REPAIRFALSE","专业人员不修复"),
    /**
     * 同意
     */
    AGREE(3,"AGREE","同意"),
    /**
     * 不同意
     */
    DISAGREE(2,"DISAGREE","不同意"),
    /**
     * 库管员确认
     */
    CONFIRM(3,"CONFIRM","库管员确认"),
    /**
     * 丢弃
     */
    DROP(4,"DROP","丢弃"),
    /**
     * 变卖
     */
    SALE(5,"SALE","变卖"),
    /**
     * 报废
     */
    SCRAP(6,"SCRAP","报废");
	
	private int id ;
	
	private String code;
	
	private String name;
	
	ScrapstockOutExcuteEnum(Integer id, String code, String name){
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
