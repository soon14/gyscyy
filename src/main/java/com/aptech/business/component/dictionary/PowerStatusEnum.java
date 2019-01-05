package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/** @ClassName:    PowerStatusEnum.java 
 * @author         
 * @version        V1.0  
 * @Date           2017年7月31日 下午13:19:22 
 */
public enum PowerStatusEnum implements BaseCodeEnum{
	/**
	 * 0 待提交
	 */
	PENDING(0, "0", "待提交"),
	/**
	 * 1检修班长审核中
	 */
	OVERHAUL(1, "1", "待检修中心主任或副主任审批"),
	/**
	 * 2值长审核中
	 */
	MONITOR(2, "2", "待集控中心主任或副主任审批"),
	/**
	 * 3未执行
	 */
	UNEXECUTED(3, "3", "待执行人执行操作并填写执行结果"),
	/**
	 * 4 已执行
	 */
	EXECUTED(4, "4", "已执行"),
	/**
	 * 已驳回
	 */
	REJECT(5,"5","已驳回"),
	
	/**
	 * 取消流程
	 */
	CANCEL(6, "6", "取消流程"),//CANCEL
	/**
	 * 待总工、检检修分管主任审批
	 */
	LEADER(7, "7", "待总工、检检修分管主任审批"),//CANCEL
	/**
	 * 待检修中心主任或副主任指派执行人
	 */
	IMPLEMENT(8, "8", "检修中心主任或副主任指派执行人");//CANCEL
	


	private Integer id;
	
	private String code;
	
	private String name;
	
	PowerStatusEnum(Integer id, String code, String name){
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
