package com.aptech.business.ticketManage.ticketStatistics.domain;

import com.aptech.framework.orm.BaseEntity;

/**
 * 两票统计实体类
 * @author Administrato
 *
 */
public class TicketStatisticsItem extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -197884350644291232L;
	
	/**
	 * 票据张数
	 */
	private int ticketCount=0;

	/**
	 *  票据合格数
	 */
	private int qualifiedCount=0;

	/**
	 *  票据不合格数
	 */
	private int unQualifiedCount=0;

	/**
	 *  票据合格率
	 */
	private String qualifiedRate;
	
	/**
	 * 是否鉴定
	 */
	private int identify;


	public int getIdentify() {
		return identify;
	}

	public void setIdentify(int identify) {
		this.identify = identify;
	}

	public int getTicketCount() {
		return ticketCount;
	}

	public void setTicketCount(int ticketCount) {
		this.ticketCount = ticketCount;
	}

	public int getQualifiedCount() {
		return qualifiedCount;
	}

	public void setQualifiedCount(int qualifiedCount) {
		this.qualifiedCount = qualifiedCount;
	}

	public int getUnQualifiedCount() {
		return unQualifiedCount;
	}

	public void setUnQualifiedCount(int unQualifiedCount) {
		this.unQualifiedCount = unQualifiedCount;
	}

	public String getQualifiedRate() {
		return qualifiedRate;
	}

	public void setQualifiedRate(String qualifiedRate) {
		this.qualifiedRate = qualifiedRate;
	}


}