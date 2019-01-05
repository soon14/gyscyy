package com.aptech.business.wareHouse.wareHouse.domain;

import org.apache.ibatis.type.Alias;
import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 仓库管理实体类
 *
 * @author 
 * @created 2017-11-03 16:51:10
 * @lastModified 
 * @history
 *
 */
@Alias("WareHouseEntity")
public class WareHouseEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 仓库名称
		 */
    	private String wareHouseName;
		/**
		 * 所属单位
		 */
    	private Long unitId;
    	private String unitIdName;
		/**
		 * 父仓库
		 */
    	private String parentWareHouse;
    	private String parentWareHouseName;
		/**
		 * 仓库类型
		 */
    	private String wareHouseType;
    	private String wareHouseTypeName;
		/**
		 * 仓库地址
		 */
    	private String wareHouseAddress;
		public String getUnitIdName() {
			return unitIdName;
		}
		public void setUnitIdName(String unitIdName) {
			this.unitIdName = unitIdName;
		}
		public String getParentWareHouseName() {
			return parentWareHouseName;
		}
		public void setParentWareHouseName(String parentWareHouseName) {
			this.parentWareHouseName = parentWareHouseName;
		}
		public String getWareHouseTypeName() {
			return wareHouseTypeName;
		}
		public void setWareHouseTypeName(String wareHouseTypeName) {
			this.wareHouseTypeName = wareHouseTypeName;
		}
		public String getStatusName() {
			return statusName;
		}
		public void setStatusName(String statusName) {
			this.statusName = statusName;
		}
		/**
		 * 库管员
		 */
    	private String storeKeeper;
		/**
		 * 启停用状态
		 */
    	private String status;
    	private String statusName;
		/**
		 * 删除标记
		 */
    	private String deleteFlag;

		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getWareHouseName(){
			return wareHouseName;
		}
		public void setWareHouseName(String wareHouseName){
			this.wareHouseName = wareHouseName;
		}
		public Long getUnitId(){
			return unitId;
		}
		public void setUnitId(Long unitId){
			this.unitId = unitId;
		}
		public String getParentWareHouse(){
			return parentWareHouse;
		}
		public void setParentWareHouse(String parentWareHouse){
			this.parentWareHouse = parentWareHouse;
		}
		public String getWareHouseType(){
			return wareHouseType;
		}
		public void setWareHouseType(String wareHouseType){
			this.wareHouseType = wareHouseType;
		}
		public String getWareHouseAddress(){
			return wareHouseAddress;
		}
		public void setWareHouseAddress(String wareHouseAddress){
			this.wareHouseAddress = wareHouseAddress;
		}
		public String getStoreKeeper(){
			return storeKeeper;
		}
		public void setStoreKeeper(String storeKeeper){
			this.storeKeeper = storeKeeper;
		}
		public String getStatus(){
			return status;
		}
		public void setStatus(String status){
			this.status = status;
		}
		public String getDeleteFlag(){
			return deleteFlag;
		}
		public void setDeleteFlag(String deleteFlag){
			this.deleteFlag = deleteFlag;
		}
}