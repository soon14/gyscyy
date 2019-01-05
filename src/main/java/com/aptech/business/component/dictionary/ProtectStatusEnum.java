package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum ProtectStatusEnum implements BaseCodeEnum {
	ZZREJECT(0, "0", "驳回"),
	TOSUBMIT(1, "1", "待提交"), 
	ZZCHECK(2, "2", "待方式专工审批"), 
	ZRCHECK(3, "3", "待主任审批"),  
	SELEXECUTE(5, "5", "待选择执行人"), 
	EXECUTE(6, "6", "待执行人执行"), 
	ZRREJECT(8, "8", "主任驳回"), 
	FINISH(10, "10", "执行完毕"),
    CANCEL(15, "15", "取消"), 
    YXZGCHECK(11, "11", "待运行专工审批"), 
    YXZGREJECT(12, "12", "运行专工驳回"),
    YQPASS(13, "13", "延期审批通过"),
    YQREJECT(14, "14", "运行专工驳回");

	private Integer id;
	
	private String code;
	
	private String name;
	
	ProtectStatusEnum(Integer id, String code, String name){
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
