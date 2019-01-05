package com.aptech.business.cargo.stockStatistics.domain;

import com.aptech.common.system.dictionary.domain.BaseCodeEnum;

/**
 * @Description:   
 * @author         wangcc
 * @version        V1.0  
 * @Date           2017年7月5日 下午1:29:03 
 */
public enum StockStatisticsEnum implements BaseCodeEnum{

	TOTAL(-1,"TOTAL","合计"),
	NOLMAL(1,"1","正常");
	StockStatisticsEnum(Integer id, String code, String name){
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
