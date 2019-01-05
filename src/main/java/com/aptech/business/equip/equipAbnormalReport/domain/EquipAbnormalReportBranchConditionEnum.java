package com.aptech.business.equip.equipAbnormalReport.domain;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/**
 * @Description:   
 * @author         wangcc
 * @version        V1.0  
 * @Date           2017年7月5日 下午1:29:03 
 */
public enum EquipAbnormalReportBranchConditionEnum implements BaseCodeEnum{
	/**
	 * 流程状态
	 */
	DISAGREE(0,"0","驳回"),//不同意
	AGREE(1,"1","同意"),//再提交、送领导审批
	TOEXECUTE(2,"2","送执行人"),//再提交、待核审
	PROCESSOVER(0,"0","流程结束");
	EquipAbnormalReportBranchConditionEnum(Integer id, String code, String name){
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
