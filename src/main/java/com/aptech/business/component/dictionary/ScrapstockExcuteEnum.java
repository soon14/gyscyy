package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum ScrapstockExcuteEnum implements BaseCodeEnum {

    /**
     * 再次提交
     */
    SUBMITAGAIN(1,"SUBMITAGAIN","再次提交"),
    /**
     * 驳回
     */
    BACK_END(2, "BACK_END", "驳回"),
	 /**
     * 同意
     */
    AGREE(3, "AGREE", "同意"),
	 /**
     * 生技部同意
     */
    PRODUCTAGREE(4, "PRODUCTAGREE", "生技部同意"),
    /**
     * 生产副总审核同意
     */
    LEADER(5,"LEADER","生产副总审核同意"),
    /**
     * 取消流程
     */
    CANCELPROCESS(6,"CANCELPROCESS","取消流程"),
    /**
     * 库管员确认
     */
    CONFIRM(7,"CONFIRM","库管员确认"),
    /**
     * 丢弃
     */
    DROP(8,"DROP","丢弃"),
    /**
     * 变卖
     */
    SALE(9,"SALE","变卖"),
    /**
     * 报废
     */
    SCRAP(13,"SCRAP","报废");
	
	private int id ;
	
	private String code;
	
	private String name;
	
	ScrapstockExcuteEnum(Integer id, String code, String name){
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
