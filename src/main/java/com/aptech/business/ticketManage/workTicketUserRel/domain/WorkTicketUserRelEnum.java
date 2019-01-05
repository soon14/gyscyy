package com.aptech.business.ticketManage.workTicketUserRel.domain;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/**
 * @Description:   
 * @author         wangcc
 * @version        V1.0  
 * @Date           2017年7月5日 下午1:29:03 
 */
public enum WorkTicketUserRelEnum implements BaseCodeEnum{
	/**
	 * 签发人
	 */
	SIGNATURE(1, "signature ", "签发人"),
	/**
	 * 工作负责人
	 */
	DUTY(2, "duty ", "工作负责人"),
	/**
	 * 许可人
	 */
	PERMISSION (3, "permission  ", "许可人");
	WorkTicketUserRelEnum(Integer id, String code, String name){
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
