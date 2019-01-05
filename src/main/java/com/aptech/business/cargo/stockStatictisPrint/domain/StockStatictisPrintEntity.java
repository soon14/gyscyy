package com.aptech.business.cargo.stockStatictisPrint.domain;

import java.util.Date;
import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.ibatis.type.Alias;
import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 库存统计打印表实体类
 *
 * @author 
 * @created 2018-09-08 16:00:12
 * @lastModified 
 * @history
 *
 */
@Alias("StockStatictisPrintEntity")
public class StockStatictisPrintEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 物资名称
		 */
    	private String materialName;
		/**
		 * 规格型号
		 */
    	private String materialType;
		/**
		 * 计数单位
		 */
    	private String technicalUnit;
		/**
		 * 所属部门id
		 */
    	private String unitId;
		/**
		 * 所属部门名称
		 */
    	private String unitName;
		/**
		 * 所属仓库
		 */
    	private String warehouse;
		/**
		 * 上月库存
		 */
    	private Integer lastMonthStock;
		/**
		 * 本月入库
		 */
    	private Integer monthInstockNum;
		/**
		 * 本月出库
		 */
    	private Integer monthOutstockNum;
		/**
		 * 本月库存
		 */
    	private Integer monthStock;
		/**
		 * 时间
		 */
    	private Date time;
    	private String materialId;
    	private String type;
    	private String stockCode;
    	
    	
    	
    	
    	
		public String getStockCode() {
			return stockCode;
		}
		public void setStockCode(String stockCode) {
			this.stockCode = stockCode;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getMaterialId() {
			return materialId;
		}
		public void setMaterialId(String materialId) {
			this.materialId = materialId;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getMaterialName(){
			return materialName;
		}
		public void setMaterialName(String materialName){
			this.materialName = materialName;
		}
		public String getMaterialType(){
			return materialType;
		}
		public void setMaterialType(String materialType){
			this.materialType = materialType;
		}
		public String getTechnicalUnit(){
			return technicalUnit;
		}
		public void setTechnicalUnit(String technicalUnit){
			this.technicalUnit = technicalUnit;
		}
		public String getUnitId(){
			return unitId;
		}
		public void setUnitId(String unitId){
			this.unitId = unitId;
		}
		public String getUnitName(){
			return unitName;
		}
		public void setUnitName(String unitName){
			this.unitName = unitName;
		}
		public String getWarehouse(){
			return warehouse;
		}
		public void setWarehouse(String warehouse){
			this.warehouse = warehouse;
		}
		public Integer getLastMonthStock(){
			return lastMonthStock;
		}
		public void setLastMonthStock(Integer lastMonthStock){
			this.lastMonthStock = lastMonthStock;
		}
		public Integer getMonthInstockNum(){
			return monthInstockNum;
		}
		public void setMonthInstockNum(Integer monthInstockNum){
			this.monthInstockNum = monthInstockNum;
		}
		public Integer getMonthOutstockNum(){
			return monthOutstockNum;
		}
		public void setMonthOutstockNum(Integer monthOutstockNum){
			this.monthOutstockNum = monthOutstockNum;
		}
		public Integer getMonthStock(){
			return monthStock;
		}
		public void setMonthStock(Integer monthStock){
			this.monthStock = monthStock;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getTime(){
			return time;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setTime(Date time){
			this.time = time;
		}
}