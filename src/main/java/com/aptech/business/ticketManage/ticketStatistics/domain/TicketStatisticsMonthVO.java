package com.aptech.business.ticketManage.ticketStatistics.domain;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.orm.BaseEntity;
/**
 * 两票统计实体类
 * @author Administrator
 *
 */
@Alias("ticketStatisticsMonthEntity")
public class TicketStatisticsMonthVO extends BaseEntity{
	/**
	 * 票据种类
	 */
	private String ticketType;
	/**
	 * 票据种类中文名称
	 */
	private String ticketTypeName;
	/**
	 * 1日票据张数
	 */
	private int ticketCount_1;	
	/**
	 *  1日票据合格数
	 */
	private int qualifiedCount_1;	
	/**
	 *  1日票据不合格数
	 */
	private int unQualifiedCount_1;
	/**
	 *  1日票据合格率
	 */
	private int qualifiedRate_1;	
	/**
	 * 2日票据张数
	 */
	private int ticketCount_2;	
	/**
	 *  2日票据合格数
	 */
	private int qualifiedCount_2;	
	/**
	 *  2日票据不合格数
	 */
	private int unQualifiedCount_2;
	/**
	 *  2日票据合格率
	 */
	private int qualifiedRate_2;	
	/**
	 * 3日票据张数
	 */
	private int ticketCount_3;	
	/**
	 *  3日票据合格数
	 */
	private int qualifiedCount_3;	
	/**
	 *  3日票据不合格数
	 */
	private int unQualifiedCount_3;
	/**
	 *  3日票据合格率
	 */
	private int qualifiedRate_3;	
	/**
	 * 4日票据张数
	 */
	private int ticketCount_4;	
	/**
	 *  4日票据合格数
	 */
	private int qualifiedCount_4;	
	/**
	 *  4日票据不合格数
	 */
	private int unQualifiedCount_4;
	/**
	 *  4日票据合格率
	 */
	private int qualifiedRate_4;	
	/**
	 * 5日票据张数
	 */
	private int ticketCount_5;	
	/**
	 *  5日票据合格数
	 */
	private int qualifiedCount_5;	
	/**
	 *  5日票据不合格数
	 */
	private int unQualifiedCount_5;
	/**
	 *  5日票据合格率
	 */
	private int qualifiedRate_5;	
	/**
	 * 6日票据张数
	 */
	private int ticketCount_6;	
	/**
	 *  6日票据合格数
	 */
	private int qualifiedCount_6;	
	/**
	 *  6日票据不合格数
	 */
	private int unQualifiedCount_6;
	/**
	 *  6日票据合格率
	 */
	private int qualifiedRate_6;	
	/**
	 * 7日票据张数
	 */
	private int ticketCount_7;	
	/**
	 *  7日票据合格数
	 */
	private int qualifiedCount_7;	
	/**
	 *  7日票据不合格数
	 */
	private int unQualifiedCount_7;
	/**
	 *  7日票据合格率
	 */
	private int qualifiedRate_7;	
	/**
	 * 8日票据张数
	 */
	private int ticketCount_8;	
	/**
	 *  8日票据合格数
	 */
	private int qualifiedCount_8;	
	/**
	 *  8日票据不合格数
	 */
	private int unQualifiedCount_8;
	/**
	 *  8日票据合格率
	 */
	private int qualifiedRate_8;	
	/**
	 * 9日票据张数
	 */
	private int ticketCount_9;	
	/**
	 *  9日票据合格数
	 */
	private int qualifiedCount_9;	
	/**
	 *  9日票据不合格数
	 */
	private int unQualifiedCount_9;
	/**
	 *  10日票据合格率
	 */
	private int qualifiedRate_9;	
	/**
	 * 1日票据张数
	 */
	private int ticketCount_10;	
	/**
	 *  10日票据合格数
	 */
	private int qualifiedCount_10;	
	/**
	 *  10日票据不合格数
	 */
	private int unQualifiedCount_10;
	/**
	 *  10日票据合格率
	 */
	private int qualifiedRate_10;	
	/**
	 * 11日票据张数
	 */
	private int ticketCount_11;	
	/**
	 *  11日票据合格数
	 */
	private int qualifiedCount_11;	
	/**
	 *  11日票据不合格数
	 */
	private int unQualifiedCount_11;
	/**
	 *  11日票据合格率
	 */
	private int qualifiedRate_11;	
	/**
	 * 12日票据张数
	 */
	private int ticketCount_12;	
	/**
	 *  12日票据合格数
	 */
	private int qualifiedCount_12;	
	/**
	 *  12日票据不合格数
	 */
	private int unQualifiedCount_12;
	/**
	 *  12日票据合格率
	 */
	private int qualifiedRate_12;	
	/**
	 * 13日票据张数
	 */
	private int ticketCount_13;	
	/**
	 *  13日票据合格数
	 */
	private int qualifiedCount_13;	
	/**
	 *  13日票据不合格数
	 */
	private int unQualifiedCount_13;
	/**
	 *  13日票据合格率
	 */
	private int qualifiedRate_13;	
	/**
	 * 14日票据张数
	 */
	private int ticketCount_14;	
	/**
	 *  14日票据合格数
	 */
	private int qualifiedCount_14;	
	/**
	 *  14日票据不合格数
	 */
	private int unQualifiedCount_14;
	/**
	 *  14日票据合格率
	 */
	private int qualifiedRate_14;	
	/**
	 * 15日票据张数
	 */
	private int ticketCount_15;	
	/**
	 *  15日票据合格数
	 */
	private int qualifiedCount_15;	
	/**
	 *  15日票据不合格数
	 */
	private int unQualifiedCount_15;
	/**
	 *  16日票据合格率
	 */
	private int qualifiedRate_15;	
	/**
	 * 16日票据张数
	 */
	private int ticketCount_16;	
	/**
	 *  16日票据合格数
	 */
	private int qualifiedCount_16;	
	/**
	 *  16日票据不合格数
	 */
	private int unQualifiedCount_16;
	/**
	 *  16日票据合格率
	 */
	private int qualifiedRate_16;	
	/**
	 * 17日票据张数
	 */
	private int ticketCount_17;	
	/**
	 *  17日票据合格数
	 */
	private int qualifiedCount_17;	
	/**
	 *  17日票据不合格数
	 */
	private int unQualifiedCount_17;
	/**
	 *  17日票据合格率
	 */
	private int qualifiedRate_17;	
	/**
	 * 18日票据张数
	 */
	private int ticketCount_18;	
	/**
	 *  18日票据合格数
	 */
	private int qualifiedCount_18;	
	/**
	 *  18日票据不合格数
	 */
	private int unQualifiedCount_18;
	/**
	 *  18日票据合格率
	 */
	private int qualifiedRate_18;	
	/**
	 * 19日票据张数
	 */
	private int ticketCount_19;	
	/**
	 *  19日票据合格数
	 */
	private int qualifiedCount_19;	
	/**
	 *  19日票据不合格数
	 */
	private int unQualifiedCount_19;
	/**
	 *  19日票据合格率
	 */
	private int qualifiedRate_19;	
	/**
	 * 20日票据张数
	 */
	private int ticketCount_20;	
	/**
	 *  20日票据合格数
	 */
	private int qualifiedCount_20;	
	/**
	 *  20日票据不合格数
	 */
	private int unQualifiedCount_20;
	/**
	 *  20日票据合格率
	 */
	private int qualifiedRate_20;	
	/**
	 * 21日票据张数
	 */
	private int ticketCount_21;	
	/**
	 *  21日票据合格数
	 */
	private int qualifiedCount_21;	
	/**
	 *  21日票据不合格数
	 */
	private int unQualifiedCount_21;
	/**
	 *  21日票据合格率
	 */
	private int qualifiedRate_21;	
	/**
	 * 22日票据张数
	 */
	private int ticketCount_22;	
	/**
	 *  22日票据合格数
	 */
	private int qualifiedCount_22;	
	/**
	 *  22日票据不合格数
	 */
	private int unQualifiedCount_22;
	/**
	 *  22日票据合格率
	 */
	private int qualifiedRate_22;	
	/**
	 * 23日票据张数
	 */
	private int ticketCount_23;	
	/**
	 *  23日票据合格数
	 */
	private int qualifiedCount_23;	
	/**
	 *  23日票据不合格数
	 */
	private int unQualifiedCount_23;
	/**
	 *  23日票据合格率
	 */
	private int qualifiedRate_23;	
	/**
	 * 24日票据张数
	 */
	private int ticketCount_24;	
	/**
	 *  24日票据合格数
	 */
	private int qualifiedCount_24;	
	/**
	 *  24日票据不合格数
	 */
	private int unQualifiedCount_24;
	/**
	 *  24日票据合格率
	 */
	private int qualifiedRate_24;	
	/**
	 * 25日票据张数
	 */
	private int ticketCount_25;	
	/**
	 *  25日票据合格数
	 */
	private int qualifiedCount_25;	
	/**
	 *  25日票据不合格数
	 */
	private int unQualifiedCount_25;
	/**
	 *  25日票据合格率
	 */
	private int qualifiedRate_25;	
	/**
	 * 26日票据张数
	 */
	private int ticketCount_26;	
	/**
	 *  26日票据合格数
	 */
	private int qualifiedCount_26;	
	/**
	 *  26日票据不合格数
	 */
	private int unQualifiedCount_26;
	/**
	 *  26日票据合格率
	 */
	private int qualifiedRate_26;	
	/**
	 * 27日票据张数
	 */
	private int ticketCount_27;	
	/**
	 *  27日票据合格数
	 */
	private int qualifiedCount_27;	
	/**
	 *  27日票据不合格数
	 */
	private int unQualifiedCount_27;
	/**
	 *  27日票据合格率
	 */
	private int qualifiedRate_27;	
	/**
	 * 28日票据张数
	 */
	private int ticketCount_28;	
	/**
	 *  28日票据合格数
	 */
	private int qualifiedCount_28;	
	/**
	 *  28日票据不合格数
	 */
	private int unQualifiedCount_28;
	/**
	 *  28日票据合格率
	 */
	private int qualifiedRate_28;	
	/**
	 * 29日票据张数
	 */
	private int ticketCount_29;	
	/**
	 *  29日票据合格数
	 */
	private int qualifiedCount_29;	
	/**
	 *  29日票据不合格数
	 */
	private int unQualifiedCount_29;
	/**
	 *  29日票据合格率
	 */
	private int qualifiedRate_29;	
	/**
	 * 30日票据张数
	 */
	private int ticketCount_30;	
	/**
	 *  30日票据合格数
	 */
	private int qualifiedCount_30;	
	/**
	 *  30日票据不合格数
	 */
	private int unQualifiedCount_30;
	/**
	 *  30日票据合格率
	 */
	private int qualifiedRate_30;	
	/**
	 * 31日票据张数
	 */
	private int ticketCount_31;	
	/**
	 *  31日票据合格数
	 */
	private int qualifiedCount_31;	
	/**
	 *  31日票据不合格数
	 */
	private int unQualifiedCount_31;
	/**
	 *  31日票据合格率
	 */
	private int qualifiedRate_31;
	/**
	 * 全月票据张数
	 */
	private int ticketCount_32;	
	/**
	 *  全月票据合格数
	 */
	private int qualifiedCount_32;	
	/**
	 *  全月票据不合格数
	 */
	private int unQualifiedCount_32;
	/**
	 *  全月票据合格率
	 */
	private int qualifiedRate_32;
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
	public int getQualifiedRate_1() {
		return qualifiedRate_1;
	}
	public void setQualifiedRate_1(int qualifiedRate_1) {
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
	public int getQualifiedRate_2() {
		return qualifiedRate_2;
	}
	public void setQualifiedRate_2(int qualifiedRate_2) {
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
	public int getQualifiedRate_3() {
		return qualifiedRate_3;
	}
	public void setQualifiedRate_3(int qualifiedRate_3) {
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
	public int getQualifiedRate_4() {
		return qualifiedRate_4;
	}
	public void setQualifiedRate_4(int qualifiedRate_4) {
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
	public int getQualifiedRate_5() {
		return qualifiedRate_5;
	}
	public void setQualifiedRate_5(int qualifiedRate_5) {
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
	public int getQualifiedRate_6() {
		return qualifiedRate_6;
	}
	public void setQualifiedRate_6(int qualifiedRate_6) {
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
	public int getQualifiedRate_7() {
		return qualifiedRate_7;
	}
	public void setQualifiedRate_7(int qualifiedRate_7) {
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
	public int getQualifiedRate_8() {
		return qualifiedRate_8;
	}
	public void setQualifiedRate_8(int qualifiedRate_8) {
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
	public int getQualifiedRate_9() {
		return qualifiedRate_9;
	}
	public void setQualifiedRate_9(int qualifiedRate_9) {
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
	public int getQualifiedRate_10() {
		return qualifiedRate_10;
	}
	public void setQualifiedRate_10(int qualifiedRate_10) {
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
	public int getQualifiedRate_11() {
		return qualifiedRate_11;
	}
	public void setQualifiedRate_11(int qualifiedRate_11) {
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
	public int getQualifiedRate_12() {
		return qualifiedRate_12;
	}
	public void setQualifiedRate_12(int qualifiedRate_12) {
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
	public int getQualifiedRate_13() {
		return qualifiedRate_13;
	}
	public void setQualifiedRate_13(int qualifiedRate_13) {
		this.qualifiedRate_13 = qualifiedRate_13;
	}
	public int getTicketCount_14() {
		return ticketCount_14;
	}
	public void setTicketCount_14(int ticketCount_14) {
		this.ticketCount_14 = ticketCount_14;
	}
	public int getQualifiedCount_14() {
		return qualifiedCount_14;
	}
	public void setQualifiedCount_14(int qualifiedCount_14) {
		this.qualifiedCount_14 = qualifiedCount_14;
	}
	public int getUnQualifiedCount_14() {
		return unQualifiedCount_14;
	}
	public void setUnQualifiedCount_14(int unQualifiedCount_14) {
		this.unQualifiedCount_14 = unQualifiedCount_14;
	}
	public int getQualifiedRate_14() {
		return qualifiedRate_14;
	}
	public void setQualifiedRate_14(int qualifiedRate_14) {
		this.qualifiedRate_14 = qualifiedRate_14;
	}
	public int getTicketCount_15() {
		return ticketCount_15;
	}
	public void setTicketCount_15(int ticketCount_15) {
		this.ticketCount_15 = ticketCount_15;
	}
	public int getQualifiedCount_15() {
		return qualifiedCount_15;
	}
	public void setQualifiedCount_15(int qualifiedCount_15) {
		this.qualifiedCount_15 = qualifiedCount_15;
	}
	public int getUnQualifiedCount_15() {
		return unQualifiedCount_15;
	}
	public void setUnQualifiedCount_15(int unQualifiedCount_15) {
		this.unQualifiedCount_15 = unQualifiedCount_15;
	}
	public int getQualifiedRate_15() {
		return qualifiedRate_15;
	}
	public void setQualifiedRate_15(int qualifiedRate_15) {
		this.qualifiedRate_15 = qualifiedRate_15;
	}
	public int getTicketCount_16() {
		return ticketCount_16;
	}
	public void setTicketCount_16(int ticketCount_16) {
		this.ticketCount_16 = ticketCount_16;
	}
	public int getQualifiedCount_16() {
		return qualifiedCount_16;
	}
	public void setQualifiedCount_16(int qualifiedCount_16) {
		this.qualifiedCount_16 = qualifiedCount_16;
	}
	public int getUnQualifiedCount_16() {
		return unQualifiedCount_16;
	}
	public void setUnQualifiedCount_16(int unQualifiedCount_16) {
		this.unQualifiedCount_16 = unQualifiedCount_16;
	}
	public int getQualifiedRate_16() {
		return qualifiedRate_16;
	}
	public void setQualifiedRate_16(int qualifiedRate_16) {
		this.qualifiedRate_16 = qualifiedRate_16;
	}
	public int getTicketCount_17() {
		return ticketCount_17;
	}
	public void setTicketCount_17(int ticketCount_17) {
		this.ticketCount_17 = ticketCount_17;
	}
	public int getQualifiedCount_17() {
		return qualifiedCount_17;
	}
	public void setQualifiedCount_17(int qualifiedCount_17) {
		this.qualifiedCount_17 = qualifiedCount_17;
	}
	public int getUnQualifiedCount_17() {
		return unQualifiedCount_17;
	}
	public void setUnQualifiedCount_17(int unQualifiedCount_17) {
		this.unQualifiedCount_17 = unQualifiedCount_17;
	}
	public int getQualifiedRate_17() {
		return qualifiedRate_17;
	}
	public void setQualifiedRate_17(int qualifiedRate_17) {
		this.qualifiedRate_17 = qualifiedRate_17;
	}
	public int getTicketCount_18() {
		return ticketCount_18;
	}
	public void setTicketCount_18(int ticketCount_18) {
		this.ticketCount_18 = ticketCount_18;
	}
	public int getQualifiedCount_18() {
		return qualifiedCount_18;
	}
	public void setQualifiedCount_18(int qualifiedCount_18) {
		this.qualifiedCount_18 = qualifiedCount_18;
	}
	public int getUnQualifiedCount_18() {
		return unQualifiedCount_18;
	}
	public void setUnQualifiedCount_18(int unQualifiedCount_18) {
		this.unQualifiedCount_18 = unQualifiedCount_18;
	}
	public int getQualifiedRate_18() {
		return qualifiedRate_18;
	}
	public void setQualifiedRate_18(int qualifiedRate_18) {
		this.qualifiedRate_18 = qualifiedRate_18;
	}
	public int getTicketCount_19() {
		return ticketCount_19;
	}
	public void setTicketCount_19(int ticketCount_19) {
		this.ticketCount_19 = ticketCount_19;
	}
	public int getQualifiedCount_19() {
		return qualifiedCount_19;
	}
	public void setQualifiedCount_19(int qualifiedCount_19) {
		this.qualifiedCount_19 = qualifiedCount_19;
	}
	public int getUnQualifiedCount_19() {
		return unQualifiedCount_19;
	}
	public void setUnQualifiedCount_19(int unQualifiedCount_19) {
		this.unQualifiedCount_19 = unQualifiedCount_19;
	}
	public int getQualifiedRate_19() {
		return qualifiedRate_19;
	}
	public void setQualifiedRate_19(int qualifiedRate_19) {
		this.qualifiedRate_19 = qualifiedRate_19;
	}
	public int getTicketCount_20() {
		return ticketCount_20;
	}
	public void setTicketCount_20(int ticketCount_20) {
		this.ticketCount_20 = ticketCount_20;
	}
	public int getQualifiedCount_20() {
		return qualifiedCount_20;
	}
	public void setQualifiedCount_20(int qualifiedCount_20) {
		this.qualifiedCount_20 = qualifiedCount_20;
	}
	public int getUnQualifiedCount_20() {
		return unQualifiedCount_20;
	}
	public void setUnQualifiedCount_20(int unQualifiedCount_20) {
		this.unQualifiedCount_20 = unQualifiedCount_20;
	}
	public int getQualifiedRate_20() {
		return qualifiedRate_20;
	}
	public void setQualifiedRate_20(int qualifiedRate_20) {
		this.qualifiedRate_20 = qualifiedRate_20;
	}
	public int getTicketCount_21() {
		return ticketCount_21;
	}
	public void setTicketCount_21(int ticketCount_21) {
		this.ticketCount_21 = ticketCount_21;
	}
	public int getQualifiedCount_21() {
		return qualifiedCount_21;
	}
	public void setQualifiedCount_21(int qualifiedCount_21) {
		this.qualifiedCount_21 = qualifiedCount_21;
	}
	public int getUnQualifiedCount_21() {
		return unQualifiedCount_21;
	}
	public void setUnQualifiedCount_21(int unQualifiedCount_21) {
		this.unQualifiedCount_21 = unQualifiedCount_21;
	}
	public int getQualifiedRate_21() {
		return qualifiedRate_21;
	}
	public void setQualifiedRate_21(int qualifiedRate_21) {
		this.qualifiedRate_21 = qualifiedRate_21;
	}
	public int getTicketCount_22() {
		return ticketCount_22;
	}
	public void setTicketCount_22(int ticketCount_22) {
		this.ticketCount_22 = ticketCount_22;
	}
	public int getQualifiedCount_22() {
		return qualifiedCount_22;
	}
	public void setQualifiedCount_22(int qualifiedCount_22) {
		this.qualifiedCount_22 = qualifiedCount_22;
	}
	public int getUnQualifiedCount_22() {
		return unQualifiedCount_22;
	}
	public void setUnQualifiedCount_22(int unQualifiedCount_22) {
		this.unQualifiedCount_22 = unQualifiedCount_22;
	}
	public int getQualifiedRate_22() {
		return qualifiedRate_22;
	}
	public void setQualifiedRate_22(int qualifiedRate_22) {
		this.qualifiedRate_22 = qualifiedRate_22;
	}
	public int getTicketCount_23() {
		return ticketCount_23;
	}
	public void setTicketCount_23(int ticketCount_23) {
		this.ticketCount_23 = ticketCount_23;
	}
	public int getQualifiedCount_23() {
		return qualifiedCount_23;
	}
	public void setQualifiedCount_23(int qualifiedCount_23) {
		this.qualifiedCount_23 = qualifiedCount_23;
	}
	public int getUnQualifiedCount_23() {
		return unQualifiedCount_23;
	}
	public void setUnQualifiedCount_23(int unQualifiedCount_23) {
		this.unQualifiedCount_23 = unQualifiedCount_23;
	}
	public int getQualifiedRate_23() {
		return qualifiedRate_23;
	}
	public void setQualifiedRate_23(int qualifiedRate_23) {
		this.qualifiedRate_23 = qualifiedRate_23;
	}
	public int getTicketCount_24() {
		return ticketCount_24;
	}
	public void setTicketCount_24(int ticketCount_24) {
		this.ticketCount_24 = ticketCount_24;
	}
	public int getQualifiedCount_24() {
		return qualifiedCount_24;
	}
	public void setQualifiedCount_24(int qualifiedCount_24) {
		this.qualifiedCount_24 = qualifiedCount_24;
	}
	public int getUnQualifiedCount_24() {
		return unQualifiedCount_24;
	}
	public void setUnQualifiedCount_24(int unQualifiedCount_24) {
		this.unQualifiedCount_24 = unQualifiedCount_24;
	}
	public int getQualifiedRate_24() {
		return qualifiedRate_24;
	}
	public void setQualifiedRate_24(int qualifiedRate_24) {
		this.qualifiedRate_24 = qualifiedRate_24;
	}
	public int getTicketCount_25() {
		return ticketCount_25;
	}
	public void setTicketCount_25(int ticketCount_25) {
		this.ticketCount_25 = ticketCount_25;
	}
	public int getQualifiedCount_25() {
		return qualifiedCount_25;
	}
	public void setQualifiedCount_25(int qualifiedCount_25) {
		this.qualifiedCount_25 = qualifiedCount_25;
	}
	public int getUnQualifiedCount_25() {
		return unQualifiedCount_25;
	}
	public void setUnQualifiedCount_25(int unQualifiedCount_25) {
		this.unQualifiedCount_25 = unQualifiedCount_25;
	}
	public int getQualifiedRate_25() {
		return qualifiedRate_25;
	}
	public void setQualifiedRate_25(int qualifiedRate_25) {
		this.qualifiedRate_25 = qualifiedRate_25;
	}
	public int getTicketCount_26() {
		return ticketCount_26;
	}
	public void setTicketCount_26(int ticketCount_26) {
		this.ticketCount_26 = ticketCount_26;
	}
	public int getQualifiedCount_26() {
		return qualifiedCount_26;
	}
	public void setQualifiedCount_26(int qualifiedCount_26) {
		this.qualifiedCount_26 = qualifiedCount_26;
	}
	public int getUnQualifiedCount_26() {
		return unQualifiedCount_26;
	}
	public void setUnQualifiedCount_26(int unQualifiedCount_26) {
		this.unQualifiedCount_26 = unQualifiedCount_26;
	}
	public int getQualifiedRate_26() {
		return qualifiedRate_26;
	}
	public void setQualifiedRate_26(int qualifiedRate_26) {
		this.qualifiedRate_26 = qualifiedRate_26;
	}
	public int getTicketCount_27() {
		return ticketCount_27;
	}
	public void setTicketCount_27(int ticketCount_27) {
		this.ticketCount_27 = ticketCount_27;
	}
	public int getQualifiedCount_27() {
		return qualifiedCount_27;
	}
	public void setQualifiedCount_27(int qualifiedCount_27) {
		this.qualifiedCount_27 = qualifiedCount_27;
	}
	public int getUnQualifiedCount_27() {
		return unQualifiedCount_27;
	}
	public void setUnQualifiedCount_27(int unQualifiedCount_27) {
		this.unQualifiedCount_27 = unQualifiedCount_27;
	}
	public int getQualifiedRate_27() {
		return qualifiedRate_27;
	}
	public void setQualifiedRate_27(int qualifiedRate_27) {
		this.qualifiedRate_27 = qualifiedRate_27;
	}
	public int getTicketCount_28() {
		return ticketCount_28;
	}
	public void setTicketCount_28(int ticketCount_28) {
		this.ticketCount_28 = ticketCount_28;
	}
	public int getQualifiedCount_28() {
		return qualifiedCount_28;
	}
	public void setQualifiedCount_28(int qualifiedCount_28) {
		this.qualifiedCount_28 = qualifiedCount_28;
	}
	public int getUnQualifiedCount_28() {
		return unQualifiedCount_28;
	}
	public void setUnQualifiedCount_28(int unQualifiedCount_28) {
		this.unQualifiedCount_28 = unQualifiedCount_28;
	}
	public int getQualifiedRate_28() {
		return qualifiedRate_28;
	}
	public void setQualifiedRate_28(int qualifiedRate_28) {
		this.qualifiedRate_28 = qualifiedRate_28;
	}
	public int getTicketCount_29() {
		return ticketCount_29;
	}
	public void setTicketCount_29(int ticketCount_29) {
		this.ticketCount_29 = ticketCount_29;
	}
	public int getQualifiedCount_29() {
		return qualifiedCount_29;
	}
	public void setQualifiedCount_29(int qualifiedCount_29) {
		this.qualifiedCount_29 = qualifiedCount_29;
	}
	public int getUnQualifiedCount_29() {
		return unQualifiedCount_29;
	}
	public void setUnQualifiedCount_29(int unQualifiedCount_29) {
		this.unQualifiedCount_29 = unQualifiedCount_29;
	}
	public int getQualifiedRate_29() {
		return qualifiedRate_29;
	}
	public void setQualifiedRate_29(int qualifiedRate_29) {
		this.qualifiedRate_29 = qualifiedRate_29;
	}
	public int getTicketCount_30() {
		return ticketCount_30;
	}
	public void setTicketCount_30(int ticketCount_30) {
		this.ticketCount_30 = ticketCount_30;
	}
	public int getQualifiedCount_30() {
		return qualifiedCount_30;
	}
	public void setQualifiedCount_30(int qualifiedCount_30) {
		this.qualifiedCount_30 = qualifiedCount_30;
	}
	public int getUnQualifiedCount_30() {
		return unQualifiedCount_30;
	}
	public void setUnQualifiedCount_30(int unQualifiedCount_30) {
		this.unQualifiedCount_30 = unQualifiedCount_30;
	}
	public int getQualifiedRate_30() {
		return qualifiedRate_30;
	}
	public void setQualifiedRate_30(int qualifiedRate_30) {
		this.qualifiedRate_30 = qualifiedRate_30;
	}
	public int getTicketCount_31() {
		return ticketCount_31;
	}
	public void setTicketCount_31(int ticketCount_31) {
		this.ticketCount_31 = ticketCount_31;
	}
	public int getQualifiedCount_31() {
		return qualifiedCount_31;
	}
	public void setQualifiedCount_31(int qualifiedCount_31) {
		this.qualifiedCount_31 = qualifiedCount_31;
	}
	public int getUnQualifiedCount_31() {
		return unQualifiedCount_31;
	}
	public void setUnQualifiedCount_31(int unQualifiedCount_31) {
		this.unQualifiedCount_31 = unQualifiedCount_31;
	}
	public int getQualifiedRate_31() {
		return qualifiedRate_31;
	}
	public void setQualifiedRate_31(int qualifiedRate_31) {
		this.qualifiedRate_31 = qualifiedRate_31;
	}
	public int getTicketCount_32() {
		return ticketCount_32;
	}
	public void setTicketCount_32(int ticketCount_32) {
		this.ticketCount_32 = ticketCount_32;
	}
	public int getQualifiedCount_32() {
		return qualifiedCount_32;
	}
	public void setQualifiedCount_32(int qualifiedCount_32) {
		this.qualifiedCount_32 = qualifiedCount_32;
	}
	public int getUnQualifiedCount_32() {
		return unQualifiedCount_32;
	}
	public void setUnQualifiedCount_32(int unQualifiedCount_32) {
		this.unQualifiedCount_32 = unQualifiedCount_32;
	}
	public int getQualifiedRate_32() {
		return qualifiedRate_32;
	}
	public void setQualifiedRate_32(int qualifiedRate_32) {
		this.qualifiedRate_32 = qualifiedRate_32;
	}	
	
}
