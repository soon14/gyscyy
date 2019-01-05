package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/** @ClassName:    WorkStatusEnum.java 
 * @author         zhangzq
 * @version        V1.0  
 * @Date           2017年6月29日 下午3:30:22 
 */
public enum WorkSafeTypeEnum implements BaseCodeEnum{
	/**
	 * 1应拉开的断路器、隔离开关，应取下的熔断器，应解除的继电保护连接片等（包括填写前已断开、取下、解除的，注明编号）
	 */
	SAFEONE(1, "1", "SAFEONE"),
	/**
	 * 2 应装设接地线、应合上接地刀闸（注明确切地点、名称及接地线编号，接地线编号运行填写）
	 */
	SAFETWO(2, "2", "SAFETWO"),
	/**
	 * 3应设遮栏、应挂标示牌及防止二次回路误碰等措施
	 */
	SAFETHREE(3, "3", "SAFETHREE"),
	/**
	 * 4工作地点保留带电部分或注意事项（由工作票签发人或负责人填写）
	 */
	SAFEFOUR(4, "4", "SAFEFOUR"),
	/**
	 * 5工作地点保留带电部分或注意事项（由工作票签发人或负责人填写）
	 */
	SAFEFIVE(5, "5", "SAFEFIVE"),
	/**
	 * 6工作票间断措施（间断只允许五天
	 */
	SAFESIX(6, "6", "SAFESIX"),
	/**
	 * 7申请试运的安全措施
	 */
	SAFESEVEN(7, "7", "SAFESEVEN"),
	/**
	 * 8试运恢复的安全措施
	 */
	SAFEEIGHT(8, "8", "SAFEEIGHT"),
    /**
     * 8动土的安全措施
     */
    SAFENINE(9, "9", "SAFENINE"),
    /**
     * 8动土的地下设施安全措施
     */
    SAFETEN(10, "10", "SAFETEN"),
    /**
     * 风力机械安全措施
     */
    SAFEELEVEN(11, "11", "SAFEELEVEN"),
    /**
     * 一级动火运行部门应采取的安全措施
     */
    SAFETWELVE(12, "12", "SAFETWELVE"),
    /**
     * 一级动火动火部门应采取的安全措施
     */
    SAFETHIRTEEN(13, "13", "SAFETHIRTEEN"),
    /**
     * 风力自控安全措施
     */
    SAFEFOURTEEN(14,"14","SAFEFOURTEEN"),
    /**
     * 二级动火运行部门采取的安全措施
     */
    SAFEFIFTEEN(15,"15","SAFEFIFTEEN"),
    /**
     * 二级动火动火部门应采取的安全措施
     */
    SAFESIXTEEN(16,"16","SAFESIXTEEN"),
	 /**
     * 工作负责人，借用
     */
    WORKPERSON(17,"16","SAFESIXTEEN");
    
	private Integer id;
	
	private String code;
	
	private String name;
	
	WorkSafeTypeEnum(Integer id, String code, String name){
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
