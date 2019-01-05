package com.aptech.business.overhaul.overhaulSafe.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.Alias;

import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 安全交底实体类
 *
 * @author 
 * @created 2017-08-11 09:28:01
 * @lastModified 
 * @history
 *
 */
@Alias("OverhaulSafeEntity")
public class OverhaulSafeEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 检修记录ID
		 */
    	private Long overhaulRecordId;
		/**
    	 * 创建时间
    	 */
    	private Date createDate;
		/**
		 * 创建时间
		 */
    	private Long createUserId;
		/**
    	 * 修改时间
    	 */
    	private Date updateDate;
    	/**
		 * 创建时间
		 */
    	private Long updateUserId;
		/**
		 * 风险事项
		 */
    	private String danger;
		/**
		 * 措施检查
		 */
    	private String wayCheck;
    	/**
		 * 措施检查名称
		 */
    	private String wayCheckName;
    	List<SysUnitEntity> treeNodeList ;
		/**
		 * 删除状态
		 */
    	private String status;
		/**
		 * 分类
		 */
    	private String logType;
    	public Long getOverhaulRecordId() {
			return overhaulRecordId;
		}
		public void setOverhaulRecordId(Long overhaulRecordId) {
			this.overhaulRecordId = overhaulRecordId;
		}
    	public Long getCreateUserId() {
			return createUserId;
		}
		public void setCreateUserId(Long createUserId) {
			this.createUserId = createUserId;
		}
		@JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getUpdateDate() {
			return updateDate;
		}
		@JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setUpdateDate(Date updateDate) {
			this.updateDate = updateDate;
		}
		public Long getUpdateUserId() {
			return updateUserId;
		}
		public void setUpdateUserId(Long updateUserId) {
			this.updateUserId = updateUserId;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getCreateDate(){
			return createDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setCreateDate(Date createDate){
			this.createDate = createDate;
		}
		public String getDanger(){
			return danger;
		}
		public void setDanger(String danger){
			this.danger = danger;
		}
		public String getWayCheck(){
			return wayCheck;
		}
		public void setWayCheck(String wayCheck){
			this.wayCheck = wayCheck;
		}
		public String getStatus(){
			return status;
		}
		public void setStatus(String status){
			this.status = status;
		}
		public String getLogType(){
			return logType;
		}
		public void setLogType(String logType){
			this.logType = logType;
		}
		public List<SysUnitEntity> getTreeNodeList() {
			return treeNodeList;
		}
		public void setTreeNodeList(List<SysUnitEntity> treeNodeList) {
			this.treeNodeList = treeNodeList;
		}
		public String getWayCheckName() {
			if(!StringUtils.isEmpty(this.wayCheck)){
				Map<String, SysDictionaryVO> checkStateMap  =  DictionaryUtil.getDictionaries("CHECK_STATE");
				Map<String,String> checkStateEnumMap = new HashMap<String, String>();
				for(String key : checkStateMap.keySet()){
					SysDictionaryVO sysDictionaryVO = checkStateMap.get(key);
					checkStateEnumMap.put(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
				}
				return checkStateEnumMap.get(this.wayCheck);
			}
			return null;
		}
		public void setWayCheckName(String wayCheckName) {
			this.wayCheckName = wayCheckName;
		}
		
}