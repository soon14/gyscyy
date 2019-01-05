package com.aptech.business.wareHouse.wareHouseAreaPosition.domain;

import org.apache.ibatis.type.Alias;
import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 货区货位关联表实体类
 *
 * @author 
 * @created 2017-11-06 20:01:23
 * @lastModified 
 * @history
 *
 */
@Alias("WareHouseAreaPositionEntity")
public class WareHouseAreaPositionEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 货位编码
		 */
    	private String code;
		/**
		 * 货位名称
		 */
    	private String name;
		public String getGoodAreaIdName() {
			return goodAreaIdName;
		}
		public void setGoodAreaIdName(String goodAreaIdName) {
			this.goodAreaIdName = goodAreaIdName;
		}
		/**
		 * 货区id
		 */
    	private Long goodAreaId;
    	private String goodAreaIdName;
		/**
		 * 备注
		 */
    	private String remark;
    	public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		/**
    	 * 是否删除
    	 */
    	private String status;

		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getCode(){
			return code;
		}
		public void setCode(String code){
			this.code = code;
		}
		public String getName(){
			return name;
		}
		public void setName(String name){
			this.name = name;
		}
		public Long getGoodAreaId(){
			return goodAreaId;
		}
		public void setGoodAreaId(Long goodAreaId){
			this.goodAreaId = goodAreaId;
		}
		public String getRemark(){
			return remark;
		}
		public void setRemark(String remark){
			this.remark = remark;
		}
}