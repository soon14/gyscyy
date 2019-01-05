package com.aptech.business.technical.technicalWork.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.Alias;

import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.json.JsonDateTime1Deserializer;
import com.aptech.framework.json.JsonDateTime1Serializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 技术监督工作表实体类
 *
 * @author 
 * @created 2017-11-13 16:16:04
 * @lastModified 
 * @history
 *
 */
@Alias("TechnicalWorkEntity")
public class TechnicalWorkEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 序号
		 */
    	private String orderSqe;
		/**
		 * 技术监督表的id
		 */
    	private Long technicalId;
		/**
		 * 工作类型
		 */
    	private Long workType;
		/**
		 * 工作内容
		 */
    	private String content;
		/**
		 * 负责人Id
		 */
    	private Long picId;
		/**
		 * 负责人姓名
		 */
    	private String picName;
		/**
		 * 完成状态
		 */
    	private String wcstatus;
		/**
		 * 是否删除
		 */
    	private Long status;
		/**
		 * 问题和风险
		 */
    	private String danger;
		/**
		 * 逻辑编码
		 */
    	private String uuidCode;
    	//监督专业
    	private String jdzyId;
    	private String jdzyName;
    	//时间
    	private Date time;
    	
    	
    	
    	@JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getTime() {
			return time;
		}
    	@JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setTime(Date time) {
			this.time = time;
		}
		public String getJdzyId() {
			return jdzyId;
		}
		public void setJdzyId(String jdzyId) {
			this.jdzyId = jdzyId;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getOrderSqe(){
			return orderSqe;
		}
		public void setOrderSqe(String orderSqe){
			this.orderSqe = orderSqe;
		}
		public Long getTechnicalId(){
			return technicalId;
		}
		public void setTechnicalId(Long technicalId){
			this.technicalId = technicalId;
		}
		public Long getWorkType(){
			return workType;
		}
		public void setWorkType(Long workType){
			this.workType = workType;
		}
		public String getContent(){
			return content;
		}
		public void setContent(String content){
			this.content = content;
		}
		public Long getPicId(){
			return picId;
		}
		public void setPicId(Long picId){
			this.picId = picId;
		}
		public String getPicName(){
			return picName;
		}
		public void setPicName(String picName){
			this.picName = picName;
		}
		public String getWcstatus(){
			return wcstatus;
		}
		public void setWcstatus(String wcstatus){
			this.wcstatus = wcstatus;
		}
		public Long getStatus(){
			return status;
		}
		public void setStatus(Long status){
			this.status = status;
		}
		public String getDanger(){
			return danger;
		}
		public void setDanger(String danger){
			this.danger = danger;
		}
		public String getUuidCode(){
			return uuidCode;
		}
		public void setUuidCode(String uuidCode){
			this.uuidCode = uuidCode;
		}
		// 监督专业名称
		public String getJdzyName() {
			if (!StringUtils.isEmpty(this.jdzyId)) {
				Map<String, SysDictionaryVO> jxzgMap = DictionaryUtil
						.getDictionaries("TECHNICAL_TYPE");
				Map<String, String> jxzgEnumMap = new HashMap<String, String>();
				for (String key : jxzgMap.keySet()) {
					SysDictionaryVO sysDictionaryVO = jxzgMap.get(key);
					jxzgEnumMap.put(sysDictionaryVO.getCode(),
							sysDictionaryVO.getName());
				}
				return jxzgEnumMap.get(this.jdzyId);
			}
			return null;
		}
		public void setJdzyName(String jdzyName) {
			this.jdzyName = jdzyName;
		}
		
}