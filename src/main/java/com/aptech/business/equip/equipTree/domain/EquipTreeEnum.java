package com.aptech.business.equip.equipTree.domain;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/**
 * @Description:   
 * @author         wangcc
 * @version        V1.0  
 * @Date           2017年7月5日 下午1:29:03 
 */
public enum EquipTreeEnum implements BaseCodeEnum{
	/**
	 * 逻辑设备(C_TREE_TYPE)
	 */
	LOGICALEQUIP(1, "LOGICALEQUIP", "逻辑设备"),
	/**
	 * 物理设备(C_TREE_TYPE)
	 */
	PHYSICSEQUIP(2,"PHYSICSEQUIP","物理设备"),
	/**
	 * 设备类型
	 */
	EQUIPTYPE(1,"DEVICE_TYPE","设备类型"),
	/**
	 * 缺陷
	 */
	DEFECT(1,"DEFECT","缺陷"),
	/**
	 * 工作票
	 */
	WORKTICKET(2,"WORKTICKET","工作票"),
	/**
	 * 定期工作
	 */
	WORKRECORD(3,"WORKRECORD","工作票"),
	/**
	 * 正常状态
	 */
	NORMAL(1,"NORMAL","正常"),
	/**
	 * 删除状态
	 */
	DELETE(0,"DELETE","删除"),
	/**
	 * 逻辑设备(C_EQUIP_ID)
	 */
	LOGICALEQUIPID(-2,"LOGICALEQUIPID","逻辑设备ID"),
	/**
	 * 组织机构
	 */
	UNIT(-1,"UNIT","组织机构"),
	/**
	 * 有子节点
	 */
	HAVESUB(0,"HAVESUB","存在子节点"),
	/**
	 * 是否存在
	 */
	DATAREPETITION(0,"DATAREPETITION","数据重复"),
	/**
	 * 设备类型
	 */
	BUSINESSTYPE(1,"BUSINESS_TYPE","业务类型");
	EquipTreeEnum(Integer id, String code, String name){
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
