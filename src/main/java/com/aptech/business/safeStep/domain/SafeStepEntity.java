package com.aptech.business.safeStep.domain;

import org.apache.ibatis.type.Alias;
import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 安全技术措施计划实体类
 *
 * @author 
 * @created 2018-09-14 14:40:53
 * @lastModified 
 * @history
 *
 */
@Alias("SafeStepEntity")
public class SafeStepEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 生产单位id
		 */
    	private String unitId;
    	/**
    	 * 生产单位中文名称
    	 */
    	private String unitName;
		/**
		 * 分类id
		 */
    	private String classify;
    	/**
    	 * 分类中文名称
    	 */
    	private String classifyName;
		/**
		 * 内容
		 */
    	private String content;
		/**
		 * 费用预算
		 */
    	private String money;
		/**
		 * 完成时间
		 */
    	private String endTime;
		/**
		 * 备注
		 */
    	private String remark;

		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getUnitId(){
			return unitId;
		}
		public void setUnitId(String unitId){
			this.unitId = unitId;
		}
		public String getClassify(){
			return classify;
		}
		public void setClassify(String classify){
			this.classify = classify;
		}
		public String getContent(){
			return content;
		}
		public void setContent(String content){
			this.content = content;
		}
		public String getMoney(){
			return money;
		}
		public void setMoney(String money){
			this.money = money;
		}
		public String getEndTime(){
			return endTime;
		}
		public void setEndTime(String endTime){
			this.endTime = endTime;
		}
		public String getRemark(){
			return remark;
		}
		public void setRemark(String remark){
			this.remark = remark;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public String getClassifyName() {
			return classifyName;
		}
		public void setClassifyName(String classifyName) {
			this.classifyName = classifyName;
		}
		
}