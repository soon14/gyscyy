package com.aptech.business.filelearnReceiveUser.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateTime1Deserializer;
import com.aptech.framework.json.JsonDateTime1Serializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 文件学习接受人实体类
 *
 * @author 
 * @created 2018-04-10 16:34:02
 * @lastModified 
 * @history
 *
 */
@Alias("FilelearnReceiveUserEntity")
public class FilelearnReceiveUserEntity extends BaseEntity{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 文件学习id
		 */
    	private Long fileLearnId;
		/**
		 * 用户id
		 */
    	private Long userId;
		/**
		 * 单位id
		 */
    	private Long unitId;
		/**
		 * 是否学习
		 */
    	private String isLearn;/**
		 * 是否学习dic
		 */
    	private String isLearnDic;
    	/**
		 * 用户NAME
		 */
    	private String userName;/**
		 * 学习心得
		 */
    	private String learnFeel;/**
		 * 学习时间
		 */
    	private Date learnTime;/**
		 * 附件
		 */
    	private String attachment;

		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public Long getFileLearnId(){
			return fileLearnId;
		}
		public void setFileLearnId(Long fileLearnId){
			this.fileLearnId = fileLearnId;
		}
		public Long getUserId(){
			return userId;
		}
		public void setUserId(Long userId){
			this.userId = userId;
		}
		public Long getUnitId(){
			return unitId;
		}
		public void setUnitId(Long unitId){
			this.unitId = unitId;
		}
		public String getIsLearn(){
			return isLearn;
		}
		public void setIsLearn(String isLearn){
			this.isLearn = isLearn;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getLearnFeel() {
			return learnFeel;
		}
		public void setLearnFeel(String learnFeel) {
			this.learnFeel = learnFeel;
		}
		@JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getLearnTime() {
			return learnTime;
		}
		@JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setLearnTime(Date learnTime) {
			this.learnTime = learnTime;
		}
		public String getAttachment() {
			return attachment;
		}
		public void setAttachment(String attachment) {
			this.attachment = attachment;
		}
		public String getIsLearnDic() {
			if("0".equals(this.isLearn)){
				return "否";
			}else if("1".equals(this.isLearn)){
				return "是";
			}else{
				return "";
			}
			
		}
		public void setIsLearnDic(String isLearnDic) {
			this.isLearnDic = isLearnDic;
		}
		
}