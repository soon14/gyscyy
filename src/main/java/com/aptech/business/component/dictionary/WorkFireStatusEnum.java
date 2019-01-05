package com.aptech.business.component.dictionary;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/** @ClassName:    WorkStatusEnum.java 
 * @author         zhangzq
 * @version        V1.0  
 * @Date           2017年6月5日 下午3:30:22 
 */
public enum WorkFireStatusEnum implements BaseCodeEnum{
	/**
	 * 待提交1
	 */
	TOBESUBMIT(1, "1", "待提交"),
	/**
	 * 已提交待签发2
	 */
	TOBEISSUED(2, "2", "已提交待签发"),
	/**
	 * 签发人已签发待会签人签发3
	 */
	TOBEXFSH(3, "3", "签发人已签发待会签人签发"),
	/**
	 * 已签发待消防监护人审批4
	 */
	TOBEJHSP(4, "4", "已签发待消防监护人审批"),
	/**
	 * 待安监部门负责人审批5
	 */
	TOBEAJSH(5, "5", "待安监部门负责人审批"),
	/**
     * 待安全总监审批5
     */
    TOBELDSH(6, "6", "待安全总监审批"),
	/**
	 * 分管生产负责人（或总工程师）审批6
	 */
	ALLOW(7, "7", "分管生产负责人（或总工程师）审批"),
    /**
     * 待运行值班人员收票7
     */
    EXESURE(8, "8", "待运行值班人员收票"),	
	/**
	 * 待工作许可人审批9
	 */
	XFSURE(9, "9", "待工作许可人审批"),
	   /**
     * 待消防监护人审批10（许可）
     */
    FZRSURE(10, "10", "待消防监护人审批"),
	/**
	 * 待动火工作负责人确认11
	 */
    AJYSURE(11, "11", "待动火工作负责人确认"),
	/**
	 * 待动火负责人验收12
	 */
	LDSURE(12, "12", "待动火负责人验收"),
	/**
	 * 待消防监护人审批（终结）13
	 */
	TOEXE(13, "13", "待消防监护人审批"),
	/**
	 * 待工作许可人审批（终结）14
	 */
	JHRCHECK(14, "14", "待工作许可人审批"),
	/**
	 * 驳回到工作负责人15
	 */
	FZRCHECK(15, "15", "驳回到工作负责人"),


	/**
	 * 已执行16
	 */
	END(16, "16", "已执行"),
	/**
	 * 动火工作负责人开始工作16
	 */
	TODO(17, "17", "动火工作负责人开始工作"),
    /**
     * 作废18
     */
    WORKSTATUS_TYPE_INVALID(18, "18", "工作票取消");
   
	private Integer id;
	
	private String code;
	
	private String name;
	
	WorkFireStatusEnum(Integer id, String code, String name){
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
