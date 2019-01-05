package com.aptech.business.ticketManage.workTicketTest.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 可燃易爆实验实体类
 *
 * @author 
 * @created 2017-08-22 10:11:16
 * @lastModified 
 * @history
 *
 */
@Alias("WorkTicketTestEntity")
public class WorkTicketTestEntity extends BaseEntity{
		/**
	 * 
	 */
	private static final long serialVersionUID = -2240618531814954362L;
		/**
		 * 工作票ID
		 */
    	private Integer workticketId;
		/**
		 * 测试时间
		 */
    	private Date testDate;
		/**
		 * 氧气测定值
		 */
    	private String testValue;
		/**
		 * 有毒有害气体测定值
		 */
    	private String testToxicGas;
		/**
		 * 测试位置
		 */
    	private String testAddress;
		/**
		 * 测量人
		 */
    	private String testPerson;


		public Integer getWorkticketId(){
			return workticketId;
		}
		public void setWorkticketId(Integer workticketId){
			this.workticketId = workticketId;
		}
        @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getTestDate(){
			return testDate;
		}
        @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setTestDate(Date testDate){
			this.testDate = testDate;
		}
		public String getTestValue(){
			return testValue;
		}
		public void setTestValue(String testValue){
			this.testValue = testValue;
		}
		public String getTestToxicGas(){
			return testToxicGas;
		}
		public void setTestToxicGas(String testToxicGas){
			this.testToxicGas = testToxicGas;
		}
		public String getTestAddress(){
			return testAddress;
		}
		public void setTestAddress(String testAddress){
			this.testAddress = testAddress;
		}
		public String getTestPerson(){
			return testPerson;
		}
		public void setTestPerson(String testPerson){
			this.testPerson = testPerson;
		}
}