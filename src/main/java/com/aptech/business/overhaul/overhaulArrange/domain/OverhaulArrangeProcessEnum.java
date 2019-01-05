package com.aptech.business.overhaul.overhaulArrange.domain;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/**
 * @Description:   
 * @author         wangcc
 * @version        V1.0  
 * @Date           2017年7月5日 下午1:29:03 
 */
public enum OverhaulArrangeProcessEnum implements BaseCodeEnum{
	/**
	 * 流程状态
	 */
//	REJECT(0,"0", "驳回"),
	WATISUBMIT(1,"1", "待提交"),
	WAITCHECK(2,"2", "待填报"),
	RROCESSPEND(3,"3","流程结束");
//	WAITEXECUTE(3,"3", "待执行人审批"),
//	WAITLEDGERCHECK(4,"4", "待领导审批"),
//	SUBMITAGAIN(5,"5","待申请人处理"),
//	EXECUTE(6,"6","已执行"),
//	RROCESSPEND(7,"7","流程结束");
	
	OverhaulArrangeProcessEnum(Integer id, String code, String name){
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
