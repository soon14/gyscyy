package com.aptech.business.ticketManage.ticketStatistics.domain;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.orm.BaseEntity;

/**
 * 两票统计实体类
 * @author Administrator
 *
 */
@Alias("ticketStatisticsEntity")
public class TicketStatisticsVO extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -484988545408679508L;
	/**
	 * 票据种类
	 */
	private String ticketType;
	/**
	 * 票据种类中文名称
	 */
	private String ticketTypeName;
	
	/**
	 * 1月票据张数
	 */
	private int ticketCount_1;

	/**
	 *  1月票据合格数
	 */
	private int qualifiedCount_1;

	/**
	 *  1月票据不合格数
	 */
	private int unQualifiedCount_1;

	/**
	 *  1月票据合格率
	 */
	private String qualifiedRate_1;

	/**
	 * 2月票据张数
	 */
	private int ticketCount_2;

	/**
	 *  2月票据合格数
	 */
	private int qualifiedCount_2;

	/**
	 *  2月票据不合格数
	 */
	private int unQualifiedCount_2;

	/**
	 *  2月票据合格率
	 */
	private String qualifiedRate_2;

	/**
	 * 3月票据张数
	 */
	private int ticketCount_3;

	/**
	 *  3月票据合格数
	 */
	private int qualifiedCount_3;

	/**
	 *  3月票据不合格数
	 */
	private int unQualifiedCount_3;

	/**
	 *  3月票据合格率
	 */
	private String qualifiedRate_3;

	/**
	 * 4月票据张数
	 */
	private int ticketCount_4;

	/**
	 *  4月票据合格数
	 */
	private int qualifiedCount_4;

	/**
	 *  4月票据不合格数
	 */
	private int unQualifiedCount_4;

	/**
	 *  4月票据合格率
	 */
	private String qualifiedRate_4;

	/**
	 * 5月票据张数
	 */
	private int ticketCount_5;

	/**
	 *  5月票据合格数
	 */
	private int qualifiedCount_5;

	/**
	 *  5月票据不合格数
	 */
	private int unQualifiedCount_5;

	/**
	 *  5月票据合格率
	 */
	private String qualifiedRate_5;
	/**
	 * 6月票据张数
	 */
	private int ticketCount_6;

	/**
	 *  6月票据合格数
	 */
	private int qualifiedCount_6;

	/**
	 *  6月票据不合格数
	 */
	private int unQualifiedCount_6;

	/**
	 *  6月票据合格率
	 */
	private String qualifiedRate_6;

	/**
	 * 1月票据张数
	 */
	private int ticketCount_7;

	/**
	 *  7月票据合格数
	 */
	private int qualifiedCount_7;

	/**
	 *  7月票据不合格数
	 */
	private int unQualifiedCount_7;
	
	/**
	 *  7月票据合格率
	 */
	private String qualifiedRate_7;

	/**
	 * 8月票据张数
	 */
	private int ticketCount_8;

	/**
	 *  8月票据合格数
	 */
	private int qualifiedCount_8;
	
	/**
	 *  8月票据不合格数
	 */
	private int unQualifiedCount_8;

	/**
	 *  8月票据合格率
	 */
	private String qualifiedRate_8;

	/**
	 * 9月票据张数
	 */
	private int ticketCount_9;

	/**
	 *  9月票据合格数
	 */
	private int qualifiedCount_9;

	/**
	 *  9月票据不合格数
	 */
	private int unQualifiedCount_9;

	/*
	 *  9月票据合格率
	 */
	private String qualifiedRate_9; 

	/**
	 * 10月票据张数
	 */
	private int ticketCount_10;

	/**
	 *  10月票据合格数
	 */
	private int qualifiedCount_10;

	/**
	 *  10月票据不合格数
	 */
	private int unQualifiedCount_10;

	/**
	 *  10月票据合格率
	 */
	private String qualifiedRate_10;

	/**
	 * 11月票据张数
	 */
	private int ticketCount_11;
	
	/**
	 *  11月票据合格数
	 */
	private int qualifiedCount_11;

	/**
	 *  11月票据不合格数
	 */
	private int unQualifiedCount_11;
	
	/**
	 *  11月票据合格率
	 */
	private String qualifiedRate_11;

	/**
	 * 12月票据张数
	 */
	private int ticketCount_12;

	/**
	 *  12月票据合格数
	 */
	private int qualifiedCount_12;

	/**
	 *  12月票据不合格数
	 */
	private int unQualifiedCount_12;

	/**
	 *  12月票据合格率
	 */
	private String qualifiedRate_12;

	/**
	 * 年票据张数
	 */
	private int ticketCount_13;

	/**
	 *  年票据合格数
	 */
	private int qualifiedCount_13;

	/**
	 *  年票据不合格数
	 */
	private int unQualifiedCount_13;

	/**
	 *  年票据合格率
	 */
	private String qualifiedRate_13;
	private String yearText;
	
	
	
	public String getYearText() {
		return yearText;
	}

	public void setYearText(String yearText) {
		this.yearText = yearText;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public String getTicketTypeName() {
		return ticketTypeName;
	}

	public void setTicketTypeName(String ticketTypeName) {
		this.ticketTypeName = ticketTypeName;
	}

	public int getTicketCount_1() {
		return ticketCount_1;
	}

	public void setTicketCount_1(int ticketCount_1) {
		this.ticketCount_1 = ticketCount_1;
	}

	public int getQualifiedCount_1() {
		return qualifiedCount_1;
	}

	public void setQualifiedCount_1(int qualifiedCount_1) {
		this.qualifiedCount_1 = qualifiedCount_1;
	}

	public int getUnQualifiedCount_1() {
		return unQualifiedCount_1;
	}

	public void setUnQualifiedCount_1(int unQualifiedCount_1) {
		this.unQualifiedCount_1 = unQualifiedCount_1;
	}

	public String getQualifiedRate_1() {
		return qualifiedRate_1;
	}

	public void setQualifiedRate_1(String qualifiedRate_1) {
		this.qualifiedRate_1 = qualifiedRate_1;
	}

	public int getTicketCount_2() {
		return ticketCount_2;
	}

	public void setTicketCount_2(int ticketCount_2) {
		this.ticketCount_2 = ticketCount_2;
	}

	public int getQualifiedCount_2() {
		return qualifiedCount_2;
	}

	public void setQualifiedCount_2(int qualifiedCount_2) {
		this.qualifiedCount_2 = qualifiedCount_2;
	}

	public int getUnQualifiedCount_2() {
		return unQualifiedCount_2;
	}

	public void setUnQualifiedCount_2(int unQualifiedCount_2) {
		this.unQualifiedCount_2 = unQualifiedCount_2;
	}

	public String getQualifiedRate_2() {
		return qualifiedRate_2;
	}

	public void setQualifiedRate_2(String qualifiedRate_2) {
		this.qualifiedRate_2 = qualifiedRate_2;
	}

	public int getTicketCount_3() {
		return ticketCount_3;
	}

	public void setTicketCount_3(int ticketCount_3) {
		this.ticketCount_3 = ticketCount_3;
	}

	public int getQualifiedCount_3() {
		return qualifiedCount_3;
	}

	public void setQualifiedCount_3(int qualifiedCount_3) {
		this.qualifiedCount_3 = qualifiedCount_3;
	}

	public int getUnQualifiedCount_3() {
		return unQualifiedCount_3;
	}

	public void setUnQualifiedCount_3(int unQualifiedCount_3) {
		this.unQualifiedCount_3 = unQualifiedCount_3;
	}

	public String getQualifiedRate_3() {
		return qualifiedRate_3;
	}

	public void setQualifiedRate_3(String qualifiedRate_3) {
		this.qualifiedRate_3 = qualifiedRate_3;
	}

	public int getTicketCount_4() {
		return ticketCount_4;
	}

	public void setTicketCount_4(int ticketCount_4) {
		this.ticketCount_4 = ticketCount_4;
	}

	public int getQualifiedCount_4() {
		return qualifiedCount_4;
	}

	public void setQualifiedCount_4(int qualifiedCount_4) {
		this.qualifiedCount_4 = qualifiedCount_4;
	}

	public int getUnQualifiedCount_4() {
		return unQualifiedCount_4;
	}

	public void setUnQualifiedCount_4(int unQualifiedCount_4) {
		this.unQualifiedCount_4 = unQualifiedCount_4;
	}

	public String getQualifiedRate_4() {
		return qualifiedRate_4;
	}

	public void setQualifiedRate_4(String qualifiedRate_4) {
		this.qualifiedRate_4 = qualifiedRate_4;
	}

	public int getTicketCount_5() {
		return ticketCount_5;
	}

	public void setTicketCount_5(int ticketCount_5) {
		this.ticketCount_5 = ticketCount_5;
	}

	public int getQualifiedCount_5() {
		return qualifiedCount_5;
	}

	public void setQualifiedCount_5(int qualifiedCount_5) {
		this.qualifiedCount_5 = qualifiedCount_5;
	}

	public int getUnQualifiedCount_5() {
		return unQualifiedCount_5;
	}

	public void setUnQualifiedCount_5(int unQualifiedCount_5) {
		this.unQualifiedCount_5 = unQualifiedCount_5;
	}

	public String getQualifiedRate_5() {
		return qualifiedRate_5;
	}

	public void setQualifiedRate_5(String qualifiedRate_5) {
		this.qualifiedRate_5 = qualifiedRate_5;
	}

	public int getTicketCount_6() {
		return ticketCount_6;
	}

	public void setTicketCount_6(int ticketCount_6) {
		this.ticketCount_6 = ticketCount_6;
	}

	public int getQualifiedCount_6() {
		return qualifiedCount_6;
	}

	public void setQualifiedCount_6(int qualifiedCount_6) {
		this.qualifiedCount_6 = qualifiedCount_6;
	}

	public int getUnQualifiedCount_6() {
		return unQualifiedCount_6;
	}

	public void setUnQualifiedCount_6(int unQualifiedCount_6) {
		this.unQualifiedCount_6 = unQualifiedCount_6;
	}

	public String getQualifiedRate_6() {
		return qualifiedRate_6;
	}

	public void setQualifiedRate_6(String qualifiedRate_6) {
		this.qualifiedRate_6 = qualifiedRate_6;
	}

	public int getTicketCount_7() {
		return ticketCount_7;
	}

	public void setTicketCount_7(int ticketCount_7) {
		this.ticketCount_7 = ticketCount_7;
	}

	public int getQualifiedCount_7() {
		return qualifiedCount_7;
	}

	public void setQualifiedCount_7(int qualifiedCount_7) {
		this.qualifiedCount_7 = qualifiedCount_7;
	}

	public int getUnQualifiedCount_7() {
		return unQualifiedCount_7;
	}

	public void setUnQualifiedCount_7(int unQualifiedCount_7) {
		this.unQualifiedCount_7 = unQualifiedCount_7;
	}

	public String getQualifiedRate_7() {
		return qualifiedRate_7;
	}

	public void setQualifiedRate_7(String qualifiedRate_7) {
		this.qualifiedRate_7 = qualifiedRate_7;
	}

	public int getTicketCount_8() {
		return ticketCount_8;
	}

	public void setTicketCount_8(int ticketCount_8) {
		this.ticketCount_8 = ticketCount_8;
	}

	public int getQualifiedCount_8() {
		return qualifiedCount_8;
	}

	public void setQualifiedCount_8(int qualifiedCount_8) {
		this.qualifiedCount_8 = qualifiedCount_8;
	}

	public int getUnQualifiedCount_8() {
		return unQualifiedCount_8;
	}

	public void setUnQualifiedCount_8(int unQualifiedCount_8) {
		this.unQualifiedCount_8 = unQualifiedCount_8;
	}

	public String getQualifiedRate_8() {
		return qualifiedRate_8;
	}

	public void setQualifiedRate_8(String qualifiedRate_8) {
		this.qualifiedRate_8 = qualifiedRate_8;
	}

	public int getTicketCount_9() {
		return ticketCount_9;
	}

	public void setTicketCount_9(int ticketCount_9) {
		this.ticketCount_9 = ticketCount_9;
	}

	public int getQualifiedCount_9() {
		return qualifiedCount_9;
	}

	public void setQualifiedCount_9(int qualifiedCount_9) {
		this.qualifiedCount_9 = qualifiedCount_9;
	}

	public int getUnQualifiedCount_9() {
		return unQualifiedCount_9;
	}

	public void setUnQualifiedCount_9(int unQualifiedCount_9) {
		this.unQualifiedCount_9 = unQualifiedCount_9;
	}

	public String getQualifiedRate_9() {
		return qualifiedRate_9;
	}

	public void setQualifiedRate_9(String qualifiedRate_9) {
		this.qualifiedRate_9 = qualifiedRate_9;
	}

	public int getTicketCount_10() {
		return ticketCount_10;
	}

	public void setTicketCount_10(int ticketCount_10) {
		this.ticketCount_10 = ticketCount_10;
	}

	public int getQualifiedCount_10() {
		return qualifiedCount_10;
	}

	public void setQualifiedCount_10(int qualifiedCount_10) {
		this.qualifiedCount_10 = qualifiedCount_10;
	}

	public int getUnQualifiedCount_10() {
		return unQualifiedCount_10;
	}

	public void setUnQualifiedCount_10(int unQualifiedCount_10) {
		this.unQualifiedCount_10 = unQualifiedCount_10;
	}

	public String getQualifiedRate_10() {
		return qualifiedRate_10;
	}

	public void setQualifiedRate_10(String qualifiedRate_10) {
		this.qualifiedRate_10 = qualifiedRate_10;
	}

	public int getTicketCount_11() {
		return ticketCount_11;
	}

	public void setTicketCount_11(int ticketCount_11) {
		this.ticketCount_11 = ticketCount_11;
	}

	public int getQualifiedCount_11() {
		return qualifiedCount_11;
	}

	public void setQualifiedCount_11(int qualifiedCount_11) {
		this.qualifiedCount_11 = qualifiedCount_11;
	}

	public int getUnQualifiedCount_11() {
		return unQualifiedCount_11;
	}

	public void setUnQualifiedCount_11(int unQualifiedCount_11) {
		this.unQualifiedCount_11 = unQualifiedCount_11;
	}

	public String getQualifiedRate_11() {
		return qualifiedRate_11;
	}

	public void setQualifiedRate_11(String qualifiedRate_11) {
		this.qualifiedRate_11 = qualifiedRate_11;
	}

	public int getTicketCount_12() {
		return ticketCount_12;
	}

	public void setTicketCount_12(int ticketCount_12) {
		this.ticketCount_12 = ticketCount_12;
	}

	public int getQualifiedCount_12() {
		return qualifiedCount_12;
	}

	public void setQualifiedCount_12(int qualifiedCount_12) {
		this.qualifiedCount_12 = qualifiedCount_12;
	}

	public int getUnQualifiedCount_12() {
		return unQualifiedCount_12;
	}

	public void setUnQualifiedCount_12(int unQualifiedCount_12) {
		this.unQualifiedCount_12 = unQualifiedCount_12;
	}

	public String getQualifiedRate_12() {
		return qualifiedRate_12;
	}

	public void setQualifiedRate_12(String qualifiedRate_12) {
		this.qualifiedRate_12 = qualifiedRate_12;
	}

	public int getTicketCount_13() {
		return ticketCount_13;
	}

	public void setTicketCount_13(int ticketCount_13) {
		this.ticketCount_13 = ticketCount_13;
	}

	public int getQualifiedCount_13() {
		return qualifiedCount_13;
	}

	public void setQualifiedCount_13(int qualifiedCount_13) {
		this.qualifiedCount_13 = qualifiedCount_13;
	}

	public int getUnQualifiedCount_13() {
		return unQualifiedCount_13;
	}

	public void setUnQualifiedCount_13(int unQualifiedCount_13) {
		this.unQualifiedCount_13 = unQualifiedCount_13;
	}

	public String getQualifiedRate_13() {
		return qualifiedRate_13;
	}

	public void setQualifiedRate_13(String qualifiedRate_13) {
		this.qualifiedRate_13 = qualifiedRate_13;
	}

}