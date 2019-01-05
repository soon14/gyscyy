package com.aptech.business.personalManage.domain;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.Alias;

import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.orm.DataStatusEnum;

/**
 * 
 * 人员管理实体类
 *
 * @author 
 * @created 2017-10-19 17:34:22
 * @lastModified 
 * @history
 *
 */
@Alias("PersonalManageEntity")
public class PersonalManageEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 姓名
		 */
    	private String name;
		/**
		 * 工号
		 */
    	private String code;
		/**
		 * 性别
		 */
    	private String sex;
		/**
		 * 单位
		 */
    	private String unit;
		/**
		 * 联系方式
		 */
    	private String mobile;
		/**
		 * 邮箱
		 */
    	private String mail;
		/**
		 * 相片地址
		 */
    	private String photoUrl;
		/**
		 * 状态
		 */
    	private String status;
		/**
		 * 备注
		 */
    	private String remark;
    	/**
    	 * 是否关联用户
    	 */
    	private Integer associateUser;
    	/**
         * 是否为外部人员 1：是0 否
         */
    	private String outStatus;
    	/**
    	 * 参建单位
    	 */
    	private String constructionUnits;
    	private String unitName;
    	private String oldName;
    	private String outStatusName;
    	private String constructionUnitsName;
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getName(){
			return name;
		}
		public void setName(String name){
			this.name = name;
		}
		public String getCode(){
			return code;
		}
		public void setCode(String code){
			this.code = code;
		}
		public String getSex(){
			return sex;
		}
		public void setSex(String sex){
			this.sex = sex;
		}
		public String getUnit(){
			return unit;
		}
		public void setUnit(String unit){
			this.unit = unit;
		}
		public String getMobile(){
			return mobile;
		}
		public void setMobile(String mobile){
			this.mobile = mobile;
		}
		public String getMail(){
			return mail;
		}
		public void setMail(String mail){
			this.mail = mail;
		}
		public String getPhotoUrl(){
			return photoUrl;
		}
		public void setPhotoUrl(String photoUrl){
			this.photoUrl = photoUrl;
		}
		public String getStatus(){
			return status;
		}
		public void setStatus(String status){
			this.status = status;
		}
		public String getRemark(){
			return remark;
		}
		public void setRemark(String remark){
			this.remark = remark;
		}
		public String getSexName(){
			if(!StringUtils.isEmpty(this.sex)){
				Map<String, SysDictionaryVO> instockTypeMap  =  DictionaryUtil.getDictionaries("CODE_SEX_TYPE");
				Map<String,String> instockTypeEnumMap = new HashMap<String, String>();
				for(String key : instockTypeMap.keySet()){
					SysDictionaryVO sysDictionaryVO = instockTypeMap.get(key);
					instockTypeEnumMap.put(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
				}
				return instockTypeEnumMap.get(this.sex);
			}
			return null;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public Integer getAssociateUser() {
			return associateUser;
		}
		public void setAssociateUser(Integer associateUser) {
			this.associateUser = associateUser;
		}
		public String getOldName() {
			return oldName;
		}
		public void setOldName(String oldName) {
			this.oldName = oldName;
		}
		public String getOutStatus() {
			return outStatus;
		}
		public void setOutStatus(String outStatus) {
			this.outStatus = outStatus;
		}
		public String getConstructionUnits() {
			return constructionUnits;
		}
		public void setConstructionUnits(String constructionUnits) {
			this.constructionUnits = constructionUnits;
		}
		public String getOutStatusName() {
			if(!StringUtils.isEmpty(this.outStatus)){
				Map<String, SysDictionaryVO> instockTypeMap  =  DictionaryUtil.getDictionaries("OUT_STATUS");
				Map<String,String> instockTypeEnumMap = new HashMap<String, String>();
				for(String key : instockTypeMap.keySet()){
					SysDictionaryVO sysDictionaryVO = instockTypeMap.get(key);
					instockTypeEnumMap.put(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
				}
				return instockTypeEnumMap.get(this.outStatus);
			}
			return "";
		}
		
		public void setOutStatusName(String outStatusName) {
			this.outStatusName = outStatusName;
		}
		public void setConstructionUnitsName(String constructionUnitsName) {
			this.constructionUnitsName = constructionUnitsName;
		}
		public String getConstructionUnitsName() {
			if(!StringUtils.isEmpty(this.constructionUnits)){
				Map<String, SysDictionaryVO> instockTypeMap  =  DictionaryUtil.getDictionaries("CONSTRUCTION_UNITS");
				Map<String,String> instockTypeEnumMap = new HashMap<String, String>();
				for(String key : instockTypeMap.keySet()){
					SysDictionaryVO sysDictionaryVO = instockTypeMap.get(key);
					instockTypeEnumMap.put(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
				}
				return instockTypeEnumMap.get(this.constructionUnits);
			}
			return "";
		}
	
		
}