package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

public enum SupplierTypeEnum implements BaseCodeEnum {
	
	TYPE1(0, "0", "设备物资采购类"),
	
	TYPE2(1, "1", "咨询服务类"),
	
	TYPE3(2, "2", "劳务类"),
	
	TYPE4(3, "3", "文化建设类"),
	
	TYPE5(4, "4", "安全类");

	
	private Integer id;
	
	private String code;
	
	private String name;
	
	SupplierTypeEnum(Integer id, String code, String name){
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
