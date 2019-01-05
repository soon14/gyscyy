package com.aptech.business.run.runCheck.domain;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/**
 * @Description:   
 * @author         wangcc
 * @version        V1.0  
 * @Date           2017年7月5日 下午1:29:03 
 */
public enum RunCheckEnum implements BaseCodeEnum{
	/**
	 * 集控中心主任
	 */
	CENTRALIZEDDIRECTOR(30,"CENTRALIZEDDIRECTOR","集控中心主任"),
	/**
	 * 集控中心专责
	 */
	CENTRALIZEDRESPONSIBILITY(31,"CENTRALIZEDRESPONSIBILITY","集控中心专责"),
	/**
	 * 运行方式(数据字典中C_CATEGORY_CODE的值)
	 */
	WORK_RECORD_TYPE(1,"WORK_RECORD_TYPE","运行方式"),
	/**
	 * 全部运行方式
	 */
	RUNTYPEALL(1,"RUNTYPEALL","全部"),
	/**
	 * 检查结果
	 */
	CHECKRESULT(-1,"CHECKRESULT","运行结果"),
	/**
	 * 零
	 */
	ZERO(0,"ZERO","零"),
	/**
	 * 一百
	 */
	 HUNDRED(100,"100","壹百"),
	/**
	 * 合计
	 */
	TOTAL(-1,"TOTAL","合计"),
	NOLMAL(1,"1","正常");
	RunCheckEnum(Integer id, String code, String name){
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
