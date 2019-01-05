package com.aptech.business.wareHouse.wareHouseArea.domain;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 仓库货区表实体类
 *
 * @author 
 * @created 2017-11-06 19:58:49
 * @lastModified 
 * @history
 *
 */
@Alias("WareHouseAreaEntity")
public class WareHouseAreaEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 货区编码
		 */
    	private String code;
		/**
		 * 货区名称
		 */
    	private String name;
		public String getWarehouseIdName() {
			return warehouseIdName;
		}
		public void setWarehouseIdName(String warehouseIdName) {
			this.warehouseIdName = warehouseIdName;
		}
		/**
		 * 仓库id
		 */
    	private Long warehouseId;
    	private String warehouseIdName;
		/**
		 * 备注
		 */
    	private String remark;
    	/**
    	 * 是否删除
    	 */
    	private String status;
    	//单位名称
    	private String unitName;
    	
    	
    	
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
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
		public Long getWarehouseId(){
			return warehouseId;
		}
		public void setWarehouseId(Long warehouseId){
			this.warehouseId = warehouseId;
		}
		public String getRemark(){
			return remark;
		}
		public void setRemark(String remark){
			this.remark = remark;
		}
}