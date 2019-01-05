package com.aptech.business.cargo.scrapLibraryDetailOut.domain;

import java.util.Date;
import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.ibatis.type.Alias;
import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 报废申请外库物资明细实体类
 *
 * @author 
 * @created 2018-08-22 16:54:34
 * @lastModified 
 * @history
 *
 */
@Alias("ScrapLibraryDetailOutEntity")
public class ScrapLibraryDetailOutEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 物资编码
		 */
    	private String materialCode;
		/**
		 * 物资名称
		 */
    	private String materialName;
		/**
		 * 规格型号
		 */
    	private String materialModel;
		/**
		 * 生产厂家
		 */
    	private String materialManufacturer;
		/**
		 * 数量
		 */
    	private String count;
		/**
		 * 单位
		 */
    	private String materialUnitName;
		/**
		 * 物资属性
		 */
    	private String goodsAttribute;
		/**
		 * 有效期
		 */
    	private Date goodsValidity;
		/**
		 * 价格
		 */
    	private String goodsPrice;
		/**
		 * 外库id
		 */
    	private String scrapLibraryId;

		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getMaterialCode(){
			return materialCode;
		}
		public void setMaterialCode(String materialCode){
			this.materialCode = materialCode;
		}
		public String getMaterialName(){
			return materialName;
		}
		public void setMaterialName(String materialName){
			this.materialName = materialName;
		}
		public String getMaterialModel(){
			return materialModel;
		}
		public void setMaterialModel(String materialModel){
			this.materialModel = materialModel;
		}
		public String getMaterialManufacturer(){
			return materialManufacturer;
		}
		public void setMaterialManufacturer(String materialManufacturer){
			this.materialManufacturer = materialManufacturer;
		}
		public String getCount(){
			return count;
		}
		public void setCount(String count){
			this.count = count;
		}
		public String getMaterialUnitName(){
			return materialUnitName;
		}
		public void setMaterialUnitName(String materialUnitName){
			this.materialUnitName = materialUnitName;
		}
		public String getGoodsAttribute(){
			return goodsAttribute;
		}
		public void setGoodsAttribute(String goodsAttribute){
			this.goodsAttribute = goodsAttribute;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getGoodsValidity(){
			return goodsValidity;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setGoodsValidity(Date goodsValidity){
			this.goodsValidity = goodsValidity;
		}
		public String getGoodsPrice(){
			return goodsPrice;
		}
		public void setGoodsPrice(String goodsPrice){
			this.goodsPrice = goodsPrice;
		}
		public String getScrapLibraryId(){
			return scrapLibraryId;
		}
		public void setScrapLibraryId(String scrapLibraryId){
			this.scrapLibraryId = scrapLibraryId;
		}
}