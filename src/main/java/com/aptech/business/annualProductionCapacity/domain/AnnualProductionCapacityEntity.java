package com.aptech.business.annualProductionCapacity.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 年度生产量计划实体类
 *
 * @author 
 * @created 2018-09-17 18:30:24
 * @lastModified 
 * @history
 *
 */
@Alias("AnnualProductionCapacityEntity")
public class AnnualProductionCapacityEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 场站名称id
		 */
    	private String farmId;
    	/**
    	 * 场站中文名称
    	 */
    	private String farmName;
		/**
		 * 指标名称id
		 */
    	private String quotaId;
    	/**
    	 * 指标中文名称
    	 */
    	private String quotaName;
		/**
		 * 计划对比id
		 */
    	private String planCompareId;
    	/**
    	 * 计划对比中文名称
    	 */
    	private String planCompareName;
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
    	 * 合计
    	 */
    	private String total;
		/**
		 * 数据类型，1集团年度生产量，2内控年度生产量，3年度生产指标计划
		 */
    	private String type;
    	/**
    	 * 数据年份
    	 */
    	private Date year;
    	/**
    	 * 流程状态
    	 */
    	private String status;
    	/**
    	 * 流程状态中文名称
    	 */
    	private String statusName;
    	/**
    	 * 场站名称合并单元格使用
    	 */
    	private String tdHide;
    	/**
    	 * 指标名称合并单元格使用
    	 */
    	private String tdHideQuota;

		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getFarmId(){
			return farmId;
		}
		public void setFarmId(String farmId){
			this.farmId = farmId;
		}
		public String getQuotaId(){
			return quotaId;
		}
		public void setQuotaId(String quotaId){
			this.quotaId = quotaId;
		}
		public String getPlanCompareId(){
			return planCompareId;
		}
		public void setPlanCompareId(String planCompareId){
			this.planCompareId = planCompareId;
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
		public String getType(){
			return type;
		}
		public void setType(String type){
			this.type = type;
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
		public String getTotal() {
			return total;
		}
		public void setTotal(String total) {
			this.total = total;
		}
		@JsonSerialize(using = JsonDateSerializer.class)
		public Date getYear() {
			return year;
		}
		@JsonDeserialize(using = JsonDateDeserializer.class)
		public void setYear(Date year) {
			this.year = year;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getStatusName() {
			return statusName;
		}
		public void setStatusName(String statusName) {
			this.statusName = statusName;
		}
		public String getTdHide() {
			return tdHide;
		}
		public void setTdHide(String tdHide) {
			this.tdHide = tdHide;
		}
		public String getTdHideQuota() {
			return tdHideQuota;
		}
		public void setTdHideQuota(String tdHideQuota) {
			this.tdHideQuota = tdHideQuota;
		}
}