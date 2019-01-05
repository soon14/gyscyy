package com.aptech.business.managePlanContract.budgetManage.domain;

import java.util.Date;
import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.ibatis.type.Alias;
import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 预算管理实体类
 *
 * @author 
 * @created 2018-07-25 13:17:36
 * @lastModified 
 * @history
 *
 */
@Alias("BudgetManageEntity")
public class BudgetManageEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 年度
		 */
    	private Date year;
		/**
		 * 编码
		 */
    	private String code;
		/**
		 * 项目
		 */
    	private String projectName;
		/**
		 * 创建人
		 */
    	private String createUserId;
		/**
		 * 创建时间
		 */
    	private Date createDate;
		/**
		 * 本年发生数
		 */
    	private String occurNum;
		/**
		 * 本年预算数
		 */
    	private String budgetNum;
		/**
		 * 执行预算差额
		 */
    	private String differentNum;
		/**
		 * 执行率
		 */
    	private String implementRate;
		/**
		 * 单位
		 */
    	private Long unitId;

		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getYear(){
			return year;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setYear(Date year){
			this.year = year;
		}
		public String getCode(){
			return code;
		}
		public void setCode(String code){
			this.code = code;
		}
		public String getProjectName(){
			return projectName;
		}
		public void setProjectName(String projectName){
			this.projectName = projectName;
		}
		public String getCreateUserId(){
			return createUserId;
		}
		public void setCreateUserId(String createUserId){
			this.createUserId = createUserId;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getCreateDate(){
			return createDate;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setCreateDate(Date createDate){
			this.createDate = createDate;
		}
		public String getOccurNum(){
			return occurNum;
		}
		public void setOccurNum(String occurNum){
			this.occurNum = occurNum;
		}
		public String getBudgetNum(){
			return budgetNum;
		}
		public void setBudgetNum(String budgetNum){
			this.budgetNum = budgetNum;
		}
		public String getDifferentNum(){
			return differentNum;
		}
		public void setDifferentNum(String differentNum){
			this.differentNum = differentNum;
		}
		public String getImplementRate(){
			return implementRate;
		}
		public void setImplementRate(String implementRate){
			this.implementRate = implementRate;
		}
		public Long getUnitId(){
			return unitId;
		}
		public void setUnitId(Long unitId){
			this.unitId = unitId;
		}
}