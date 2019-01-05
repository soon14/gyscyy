package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum OutstockExcuteEnum implements BaseCodeEnum {
	 /**
     * 同意
     */
    AGREE(1, "AGREE", "同意"),
   /**
     * 驳回
     */
    BACK_END(2, "BACK_END", "驳回"),
    /**
     * 提交领导
     */
    LEADER(3,"LEADER","领导审核"),
    /**
     * 再次提交
     */
    SUBMITAGAIN(4,"SUBMITAGAIN","再次提交"),
    /**
     * 取消流程
     */
    CANCELPROCESS(5,"CANCELPROCESS","取消流程");
	
	private int id ;
	
	private String code;
	
	private String name;
	
	OutstockExcuteEnum(Integer id, String code, String name){
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
