package com.aptech.business.equip.equipAbnormalReport.domain;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/**
 * @Description:   
 * @author         wangcc
 * @version        V1.0  
 * @Date           2017年7月5日 下午1:29:03 
 */
public enum EquipAbnormalReportConstantEnum implements BaseCodeEnum{
	/**
	 * 正常
	 */
	NORMAL(1,"1","正常"),
	DELETE(0,"0","删除"),
	EQUIPABNORMALREPORTCODE(2,"EQUIPABNORMALREPORTCODE","设备异动报告编码");
	EquipAbnormalReportConstantEnum(Integer id, String code, String name){
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
