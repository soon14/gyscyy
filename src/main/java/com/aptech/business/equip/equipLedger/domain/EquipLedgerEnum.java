package com.aptech.business.equip.equipLedger.domain;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/**
 * @Description:   
 * @author         wangcc
 * @version        V1.0  
 * @Date           2017年7月5日 下午1:29:03 
 */
public enum EquipLedgerEnum implements BaseCodeEnum{
	/**
	 * 基础参数
	 */
	BASEPARAMETERTYPE(1, "BASE", "基础参数"),
	/**
	 * 基础参数
	 */
	TECHNOLOGYPARAMETERSTYPE(2, "TECHNOLOGY", "技术参数"),
	/**
	 * 正常状态
	 */
	NORMAL(1, "NORMAL", "正常状态"),
	/**
	 * 删除状态
	 */
	DELETE(0,"DELETE","删除状态"),
	/**
	 * 数据重复
	 */
	DATAREPETITION(0,"DATAREPETITION","数据重复"),
	/**
	 * 发电设备
	 */
	MAKEELECTRICEQUIP(1,"MAKEELECTRICEQUIP","发电设备"),
	/**
	 * 变电设备
	 */
	CHANGELECTRICEQUIP(2,"CHANGELECTRICEQUIP","变电设备"),
	/**
	 * 输电设备
	 */
	TRANSMITELECTRICEQUIP(3,"TRANSMITELECTRICEQUIP","输电设备"),
	/**
	 * 二次设备
	 */
	SECONDEQUIP(4,"SECONDEQUIP","二次设备"),
	/**
	 * 仪器仪表
	 */
	INSTRUMENTMETER(5,"INSTRUMENTMETER","仪器仪表"),
	/**
	 * 工器具
	 */
	INSTRUMENTEQUIP(6,"INSTRUMENTEQUIP","工器具"),
	/**
	 * 生活设施
	 */
	LIFEFACILITY(7,"LIFEFACILITY","生活设施"),
	/**
	 * 设备状态界面
	 */
	EQUIPSTATUSINTERFACE(1,"01","设备状态界面"),
	/**
	 * 刀闸状态界面
	 */
	GROUNDKNIFEINTERFACE(2,"02","接地刀闸界面"),
	/**
	 * 两都都有
	 */
	BOTHINTERFACE(3,"03","两者都有");
	EquipLedgerEnum(Integer id, String code, String name){
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
