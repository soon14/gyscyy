package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/**
 * @Description:   
 * @author         wangcc
 * @version        V1.0  
 * @Date           2017年12月18日 上午10:15:59 
 */
/** 
 * 平台 
 * @author isea533 
 */  
public enum EPlatform implements BaseCodeEnum{  
    Linux(1,"Linux","Linux"),  
    Windows(2,"Windows","Windows");
    
    private Integer id;
	
	private String code;
	
	private String name;
      
    EPlatform(Integer id, String code, String name){
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