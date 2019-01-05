package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum ContractManageExcuteEnum implements BaseCodeEnum {
	/**
     * 提交
     */
    SUBMIT(1,"SUBMIT","提交"),
	 /**
     * 技术同意
     */
    TECH_AGREE(1, "TECH_AGREE", "同意"),
   /**
     * 技术驳回
     */
    TECH_BACK_END(2, "TECH_BACK_END", "驳回"),
    
    /**
     * 再次提交
     */
    SUBMITAGAIN(1,"SUBMITAGAIN","再次提交"),
    /**
     * 运营同意
     */
    OPERATE_AGREE(1, "OPERATE_AGREE", "同意"),
   /**
     * 运营驳回
     */
    OPERATE_BACK_END(2, "OPERATE_BACK_END", "驳回"),
    /**
     * 领导同意
     */
    LEADER_AGREE(1, "LEADER_AGREE", "同意"),
   /**
     * 领导驳回
     */
    LEADER_BACK_END(2, "LEADER_BACK_END", "驳回"),
    /**
     * 执行
     */
    EXCUTE(1,"EXCUTE","执行");
	
	private int id ;
	
	private String code;
	
	private String name;
	
	ContractManageExcuteEnum(Integer id, String code, String name){
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
