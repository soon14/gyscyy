package com.aptech.business.quotaPlanHistory.domain;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 指标类计划历史数据实体类
 *
 * @author 
 * @created 2018-09-19 18:20:50
 * @lastModified 
 * @history
 *
 */
@Alias("QuotaPlanHistoryEntity")
public class QuotaPlanHistoryEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 年度计划指标id
		 */
    	private String apcId;
		/**
		 * 1月
		 */
    	private String jan;
		/**
		 * 2月
		 */
    	private String feb;
		/**
		 * 3月
		 */
    	private String mar;
		/**
		 * 4月
		 */
    	private String apr;
		/**
		 * 5月
		 */
    	private String may;
		/**
		 * 6月
		 */
    	private String jun;
		/**
		 * 7月
		 */
    	private String jul;
		/**
		 * 8月
		 */
    	private String aug;
		/**
		 * 9月
		 */
    	private String sep;
		/**
		 * 10月
		 */
    	private String oct;
		/**
		 * 11月
		 */
    	private String nov;
		/**
		 * 12月
		 */
    	private String dec;
    	/**
    	 * 场站中文名称
    	 */
    	private String farmName;
    	/**
    	 * 指标中文名称
    	 */
    	private String quotaName;
    	/**
    	 * 计划对比中文名称
    	 */
    	private String planCompareName;
    	/**
    	 * 数据类型
    	 */
    	private String type;

		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getApcId(){
			return apcId;
		}
		public void setApcId(String apcId){
			this.apcId = apcId;
		}
		public String getJan(){
			return jan;
		}
		public void setJan(String jan){
			this.jan = jan;
		}
		public String getFeb(){
			return feb;
		}
		public void setFeb(String feb){
			this.feb = feb;
		}
		public String getMar(){
			return mar;
		}
		public void setMar(String mar){
			this.mar = mar;
		}
		public String getApr(){
			return apr;
		}
		public void setApr(String apr){
			this.apr = apr;
		}
		public String getMay(){
			return may;
		}
		public void setMay(String may){
			this.may = may;
		}
		public String getJun(){
			return jun;
		}
		public void setJun(String jun){
			this.jun = jun;
		}
		public String getJul(){
			return jul;
		}
		public void setJul(String jul){
			this.jul = jul;
		}
		public String getAug(){
			return aug;
		}
		public void setAug(String aug){
			this.aug = aug;
		}
		public String getSep(){
			return sep;
		}
		public void setSep(String sep){
			this.sep = sep;
		}
		public String getOct(){
			return oct;
		}
		public void setOct(String oct){
			this.oct = oct;
		}
		public String getNov(){
			return nov;
		}
		public void setNov(String nov){
			this.nov = nov;
		}
		public String getDec(){
			return dec;
		}
		public void setDec(String dec){
			this.dec = dec;
		}
		public String getFarmName() {
			return farmName;
		}
		public void setFarmName(String farmName) {
			this.farmName = farmName;
		}
		public String getQuotaName() {
			return quotaName;
		}
		public void setQuotaName(String quotaName) {
			this.quotaName = quotaName;
		}
		public String getPlanCompareName() {
			return planCompareName;
		}
		public void setPlanCompareName(String planCompareName) {
			this.planCompareName = planCompareName;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
}