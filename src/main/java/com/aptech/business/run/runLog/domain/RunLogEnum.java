package com.aptech.business.run.runLog.domain;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/**
 * @Description:   
 * @author         wangcc
 * @version        V1.0  
 * @Date           2017年7月5日 下午1:29:03 
 */
public enum RunLogEnum implements BaseCodeEnum{
	/**
	 * 交接班巡视完成状态
	 */
	JFState(1,"1","交接班巡视完成状态"),
	/**
	 * 交接班状态
	 */
	GRSTATEUNDO(2,"undo","未完成"),
	/**
	 * 交接班状态
	 */
	GRSTATEDONE(3,"done","已完成"),
	/**
	 * 交班密码
	 */
	PASSWORD(4,"123456","交班密码");
	RunLogEnum(Integer id, String code, String name){
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
