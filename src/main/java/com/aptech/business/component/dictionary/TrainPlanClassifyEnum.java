package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum TrainPlanClassifyEnum implements BaseCodeEnum {
	/**
	 * 定期培训
	 */
	REGULARTRAIN(0, "0", "定期培训"),
	/**
	 * 非定期培训
	 */
	NOREGULARTRAIN(1, "1", "非定期培训");
	
	private Integer id;
	
	private String code;
	
	private String name;
	
	TrainPlanClassifyEnum(Integer id, String code, String name){
		this.id = id;
		this.code = code;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
