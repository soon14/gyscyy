package com.aptech.business.run.safeMeeting.domain;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/**
 * @Description:   
 * @author         wangcc
 * @version        V1.0  
 * @Date           2017年7月5日 下午1:29:03 
 */
public enum SafeMeetingEnum implements BaseCodeEnum{
	/**
	 * 机房设备巡视
	 */
	MACHINEROOMINSPECTION(1,"MACHINEROOMINSPECTION","机房设备巡视。"),
	/**
	 * 集控中心设备巡视
	 */
	CONTROLCENTERINSPECTION(2,"CONTROLCENTERINSPECTION","集控中心设备巡视。"),
	/**
	 * 两个细则
	 */
	TWORULE(3,"TWORULE","‘两个细则’考核情况查询。"),
	/**
	 * 未检测
	 */
	UNCHECK(0,"uncheck","未完成"),
	/**
	 * 
	 */
	ONE(1,"ONE","ONE"),
	/**
	 * 安全交底
	 */
	SAFEONE(1,"1","safe"),
	/**
	 * 安全交底
	 */
	SAFETWO(1,"2","safe"),
	/**
	 * 安全交底
	 */
	SAFETHREE(1,"3","safe");
	SafeMeetingEnum(Integer id, String code, String name){
		this.id = id;
		this.code = code;
	    this.name = name;
	}
	
	private Integer id;
	
	private String name;

	private String code;

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return this.code;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

}
