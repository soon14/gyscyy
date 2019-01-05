package com.aptech.business.equip.equipAbnormal.domain;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/**
 * @Description:   
 * @author         wangcc
 * @version        V1.0  
 * @Date           2017年7月5日 下午1:29:03 
 */
public enum EquipAbnormalEnum implements BaseCodeEnum{
	/**
	 * 流程状态
	 */
	REJECT(0,"0", "驳回"),
	WATISUBMIT(1,"1", "待提交"),
	WAITPROFESSIONWORK(2,"2", "待检修、集控专工及以上人员审批"),
	WAITCHECK(3,"3", "待生技部人员审批"),
	WAITEXECUTE(4,"4", "待生技部主任审批"),
	WAITLEDGERCHECK(5,"5", "待领导审批"),
	EXECUTE(6,"6","已执行"),
	RROCESSPEND(7,"7","流程结束"),
	UNEXECUTE(8,"8","不同意执行");
	EquipAbnormalEnum(Integer id, String code, String name){
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
