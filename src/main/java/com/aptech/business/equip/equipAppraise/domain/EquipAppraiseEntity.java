package com.aptech.business.equip.equipAppraise.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.Alias;

import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 设备评价实体类
 *
 * @author 
 * @created 2017-09-18 16:41:54
 * @lastModified 
 * @history
 *
 */
@Alias("EquipAppraiseEntity")
public class EquipAppraiseEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 设备现评
		 */
    	private String equipGradeNow;
		/**
		 * 设备评价意见
		 */
    	private String equipAppraiseComment;
		/**
		 * 设备原评
		 */
    	private String 	equipGradePre;
		/**
		 * 设备评价日期
		 */
    	private Date equipAppraiseDate;
		/**
		 * 设备评价人
		 */
    	private String equipAppraisePerson;
    	/**
		 * 设备编号
		 */
    	private String equipCode;
    	/**
		 * 设备名称
		 */
    	private String equipName;
    	/**
		 * 原因
		 */
    	private String 	equipAppraiseReason;
    	/**
		 * 影响
		 */
    	private String equipAppraiseInfluence;
    	/**
		 * 顶级设备
		 */
    	private String equipTopName;
    	/**
		 * 单位名称
		 */
    	private String unitName;
    	/**
    	 * 状态
    	 */
    	private Integer status;
    	/**
    	 * 设备编码(设备台账用)
    	 */
    	private String equipLedgerCode;
    	/**
		 * 设备名称(设备台账用)
		 */
    	private String equipLedgerName;
    	public String getEquipLedgerCode() {
			return equipLedgerCode;
		}
		public void setEquipLedgerCode(String equipLedgerCode) {
			this.equipLedgerCode = equipLedgerCode;
		}
		public String getEquipLedgerName() {
			return equipLedgerName;
		}
		public void setEquipLedgerName(String equipLedgerName) {
			this.equipLedgerName = equipLedgerName;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public String getEquipTopName() {
			return equipTopName;
		}
		public void setEquipTopName(String equipTopName) {
			this.equipTopName = equipTopName;
		}
		public String getEquipCode() {
			return equipCode;
		}
		public void setEquipCode(String equipCode) {
			this.equipCode = equipCode;
		}
		public String getEquipName() {
			return equipName;
		}
		public void setEquipName(String equipName) {
			this.equipName = equipName;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getEquipAppraiseComment(){
			return equipAppraiseComment;
		}
		public void setEquipAppraiseComment(String equipAppraiseComment){
			this.equipAppraiseComment = equipAppraiseComment;
		}		
	    public String getEquipGradeNow() {
			return equipGradeNow;
		}
		public String getEquipGradeNowName(){
			if(!StringUtils.isEmpty(this.equipGradeNow)){
				Map<String, SysDictionaryVO> equipGradeNowMap  =  DictionaryUtil.getDictionaries("EQUIP_GRADE_NOW");
				Map<String,String> equipGradeNowEnumMap = new HashMap<String, String>();
				for(String key : equipGradeNowMap.keySet()){
					SysDictionaryVO sysDictionaryVO = equipGradeNowMap.get(key);
					equipGradeNowEnumMap.put(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
				}
				return equipGradeNowEnumMap.get(this.equipGradeNow);
			}
			return null;
		}
		public void setEquipGradeNow(String equipGradeNow) {
			this.equipGradeNow = equipGradeNow;
		}
		public String getEquipGradePre() {
			return equipGradePre;
		}
		public String getEquipGradePreName(){
			if(!StringUtils.isEmpty(this.equipGradePre)){
				Map<String, SysDictionaryVO> equipGradeNowMap  =  DictionaryUtil.getDictionaries("EQUIP_GRADE_PRE");
				Map<String,String> equipGradePreEnumMap = new HashMap<String, String>();
				for(String key : equipGradeNowMap.keySet()){
					SysDictionaryVO sysDictionaryVO = equipGradeNowMap.get(key);
					equipGradePreEnumMap.put(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
				}
				return equipGradePreEnumMap.get(this.equipGradePre);
			}
			return null;
		}
		public void setEquipGradePre(String equipGradePre) {
			this.equipGradePre = equipGradePre;
		}
		public String getEquipAppraiseReason() {
			return equipAppraiseReason;
		}
		public void setEquipAppraiseReason(String equipAppraiseReason) {
			this.equipAppraiseReason = equipAppraiseReason;
		}
		public String getEquipAppraiseInfluence() {
			return equipAppraiseInfluence;
		}
		public void setEquipAppraiseInfluence(String equipAppraiseInfluence) {
			this.equipAppraiseInfluence = equipAppraiseInfluence;
		}
		@JsonSerialize(using = JsonDateSerializer.class)
		public Date getEquipAppraiseDate(){
			return equipAppraiseDate;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setEquipAppraiseDate(Date equipAppraiseDate){
			this.equipAppraiseDate = equipAppraiseDate;
		}
		public String getEquipAppraisePerson(){
			return equipAppraisePerson;
		}
		public void setEquipAppraisePerson(String equipAppraisePerson){
			this.equipAppraisePerson = equipAppraisePerson;
		}
}