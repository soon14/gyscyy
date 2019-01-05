package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum OutstockBreakEnum implements BaseCodeEnum {

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
    CANCELPROCESS(6,"CANCELPROCESS","取消流程");
	
	private int id ;
	
	private String code;
	
	private String name;
	
	OutstockBreakEnum(Integer id, String code, String name){
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
