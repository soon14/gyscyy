package com.aptech.business.ticketManage.ticketStatistics.domain;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/**
 * @Description:   
 * @author         wangcc
 * @version        V1.0  
 * @Date           2017年7月5日 下午1:29:03 
 */
public enum TicketStatisticsEnum implements BaseCodeEnum{
	/**
	 * 1电气第一种工作票
	 */
	ELECTRICONE(1, "1", "电气第一种工作票"),
	/**
	 * 2 电气第二种工作票
	 */
	ELECTRICTWO(2, "2", "电气第二种工作票"),
	/**
	 * 12电力线路第一种工作票
	 */
	WORKLINE(12, "12", "电力线路第一种工作票"),
	/**
	 * 13电力线路第一种工作票
	 */
	WORKLINETWO(13, "13", "电力线路第二种工作票"),
	/**
	 * 6风力机械工作票
	 */
	WINDMECHANICAL(6, "6", "风机工作票"),
	/**
	 * 8紧急抢修单
	 */
	REPAIR(8, "8", "紧急抢修工作票"),
	/**
	 * 14继电保护安全措施票
	 */
	//WORKHELPSAFE(14, "14", "继电保护安全措施票"),
	/**
     * 11操作票
     */
    OPERATION(11, "11", "操作票"),
	/**
	 * 3一级动火工作票
	 */
	FIREONE(3, "3", "一级动火工作票"),
	/**
	 * 4二级动火工作票
	 */
	FIRETWO(4, "4", "二级动火工作票");
//	 /**
//     * 5动土工作票
//     */
//    EARTH(5, "5", "动土工作票"),
//	
//    /**
//     * 10风力自控工作票
//     */
//    WINDAUTO(10, "10", "风力自控工作票"),
//	/**
//	 * 7介入工作票
//	 */
//	INTERVENTION(7, "7", "介入工作票"),
//	
//    /**
//     * 9安全措施票
//     */
//    FIRE(9, "9", "安全措施票"),
//	
	
	
	
	TicketStatisticsEnum(Integer id, String code, String name){
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
