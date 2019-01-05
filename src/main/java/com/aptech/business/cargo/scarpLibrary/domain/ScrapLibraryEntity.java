package com.aptech.business.cargo.scarpLibrary.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.ScrapLibraryHandleStatusEnum;
import com.aptech.business.component.dictionary.ScrapSourceEnum;
import com.aptech.business.component.dictionary.ScrapstockApproveStatusEnum;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 报废库管理实体类
 *
 * @author 
 * @created 2018-03-15 15:37:32
 * @lastModified 
 * @history
 *
 */
@Alias("ScrapLibraryEntity")
public class ScrapLibraryEntity extends BaseEntity{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 报废库单号
		 */
    	private String code;
		/**
		 * 入库时间
		 */
    	private Date instockTime;
		/**
		 * 报废来源
		 */
    	private String scrapSource;
    	private String scrapSourceName;
		/**
		 * 来源电站id
		 */
    	private String stationSourceId;
    	private String stationSourceName;
		/**
		 * 入库类型
		 */
    	private String instockType;
    	private String instockTypeName;
		/**
		 * 处理方式
		 */
    	private String processMode;
    	private String processModeName;
		/**
		 * 附件id
		 */
    	private String fileId;
		/**
		 * 备注
		 */
    	private String remark;
		/**
		 * 仓库Id
		 */
    	private String warehouseId;
		/**
		 * 审批状态
		 */
    	private String status;
    	private String statusName;
    	private String statusHandleName;
    	private String userId;
    	private String userName;
    	private int number;
    	private String type;
    	//库管员
    	private String storeKeeper;
    	//审核人
    	private String approveUser;
    	private String stockId;
    	
    	
    	
    	
    	
    	public String getStockId() {
			return stockId;
		}
		public void setStockId(String stockId) {
			this.stockId = stockId;
		}
		public String getStoreKeeper() {
			return storeKeeper;
		}
		public void setStoreKeeper(String storeKeeper) {
			this.storeKeeper = storeKeeper;
		}
		public String getApproveUser() {
			return approveUser;
		}
		public void setApproveUser(String approveUser) {
			this.approveUser = approveUser;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getTaskId() {
			return taskId;
		}
		public void setTaskId(String taskId) {
			this.taskId = taskId;
		}
		public String getProcInstId() {
			return procInstId;
		}
		public void setProcInstId(String procInstId) {
			this.procInstId = procInstId;
		}
		public String getUserList() {
			return userList;
		}
		public void setUserList(String userList) {
			this.userList = userList;
		}
		private String taskId;

    	private String procInstId;

    	private String userList;
    	
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
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
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getInstockTime(){
			return instockTime;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setInstockTime(Date instockTime){
			this.instockTime = instockTime;
		}
		public String getScrapSource(){
			return scrapSource;
		}
		public void setScrapSource(String scrapSource){
			this.scrapSource = scrapSource;
		}
		public String getStationSourceId(){
			return stationSourceId;
		}
		public void setStationSourceId(String stationSourceId){
			this.stationSourceId = stationSourceId;
		}
		public String getInstockType(){
			return instockType;
		}
		public void setInstockType(String instockType){
			this.instockType = instockType;
		}
		public String getProcessMode(){
			return processMode;
		}
		public void setProcessMode(String processMode){
			this.processMode = processMode;
		}
		public String getFileId(){
			return fileId;
		}
		public void setFileId(String fileId){
			this.fileId = fileId;
		}
		public String getRemark(){
			return remark;
		}
		public void setRemark(String remark){
			this.remark = remark;
		}
		public String getWarehouseId(){
			return warehouseId;
		}
		public void setWarehouseId(String warehouseId){
			this.warehouseId = warehouseId;
		}
		public String getStatus(){
			return status;
		}
		public void setStatus(String status){
			this.status = status;
		}
		
		public String getStationSourceName() {
			return stationSourceName;
		}
		public void setStationSourceName(String stationSourceName) {
			this.stationSourceName = stationSourceName;
		}
		public String getStatusName() {
			for (ScrapstockApproveStatusEnum  scrapstockApproveStatusEnum: ScrapstockApproveStatusEnum.values()) {
				if (scrapstockApproveStatusEnum.getCode().equals(this.getStatus())) {
					statusName =  scrapstockApproveStatusEnum.getName();
				}
			}
			return statusName;
		}
		public String getStatusHandleName() {
			for (ScrapLibraryHandleStatusEnum  scrapLibraryHandleStatusEnum: ScrapLibraryHandleStatusEnum.values()) {
				if (scrapLibraryHandleStatusEnum.getCode().equals(this.getStatus())) {
					statusHandleName =  scrapLibraryHandleStatusEnum.getName();
				}
			}
			return statusHandleName;
		}
		public String getScrapSourceName() {
			for (ScrapSourceEnum  scrapSourceEnum: ScrapSourceEnum.values()) {
				if (scrapSourceEnum.getCode().equals(this.getScrapSource())) {
					scrapSourceName =  scrapSourceEnum.getName();
				}
			}
			return scrapSourceName;
		}
		public String getInstockTypeName() {
			if(!StringUtils.isEmpty(this.instockType)){
				Map<String, SysDictionaryVO> instockTypeMap  =  DictionaryUtil.getDictionaries("SCRAPTYPE");
				Map<String,String> instockTypeEnumMap = new HashMap<String, String>();
				for(String key : instockTypeMap.keySet()){
					SysDictionaryVO sysDictionaryVO = instockTypeMap.get(key);
					instockTypeEnumMap.put(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
				}
				return instockTypeEnumMap.get(this.instockType);
			}
			return null;
		}
		public String getProcessModeName() {
			if(!StringUtils.isEmpty(this.processMode)){
				Map<String, SysDictionaryVO> processModeMap  =  DictionaryUtil.getDictionaries("PROCESSMODE");
				Map<String,String> processModeEnumMap = new HashMap<String, String>();
				for(String key : processModeMap.keySet()){
					SysDictionaryVO sysDictionaryVO = processModeMap.get(key);
					processModeEnumMap.put(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
				}
				return processModeEnumMap.get(this.processMode);
			}
			return null;
		}
		public String getInstockTimeString() {
			if(instockTime!=null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				return  sdf.format(instockTime);
			}
			return "";
		}
}