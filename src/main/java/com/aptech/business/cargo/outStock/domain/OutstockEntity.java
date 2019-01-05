package com.aptech.business.cargo.outStock.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.Alias;

import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 出库管理实体类
 *
 * @author
 * @created 2017-07-21 09:26:05
 * @lastModified
 * @history
 *
 */
@Alias("OutstockEntity")
public class OutstockEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2153103596227587480L;
	/**
	 * 出库单号
	 */
	private String outstockNum;
	/**
	 * 出库时间
	 */
	private Date outstockTime;
	/**
	 * 出库类型
	 */
	private String outstockType;
	/**
	 * 机构ID
	 */
	private Long unitId;
	
	/**
	 * 机构名称
	 */
	private String unitName;
	
	/**
	 * 申请人
	 */
	private String applicant;

	/**
	 * 审核状态
	 */
	private String approveStatus;
	/**
	 * 附件
	 */
	private String attachment;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 申请人姓名
	 */
	private String applicantName;

	private String taskId;

	private String procInstId;

	private String userList;

	/**
	 * 仓库ID
	 */
	private Long wareHouseId;
	
	/**
	 * 仓库名称
	 */
	private String wareHouseName;
	//库管员
	private String storeKeeper;
	//审核人
	private String approveUser;
	
	
	
	
	
	
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

	public Long getWareHouseId() {
		return wareHouseId;
	}

	public void setWareHouseId(Long wareHouseId) {
		this.wareHouseId = wareHouseId;
	}

	public String getWareHouseName() {
		return wareHouseName;
	}

	public void setWareHouseName(String wareHouseName) {
		this.wareHouseName = wareHouseName;
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

	public String getOutstockNum() {
		return outstockNum;
	}

	public void setOutstockNum(String outstockNum) {
		this.outstockNum = outstockNum;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getOutstockTime() {
		return outstockTime;
	}

	@JsonDeserialize(using = JsonDateDeserializer.class)
	public void setOutstockTime(Date outstockTime) {
		this.outstockTime = outstockTime;
	}

	public String getOutstockType() {
		return outstockType;
	}

	public void setOutstockType(String outstockType) {
		this.outstockType = outstockType;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	// 出库类型
	public String getOutstockTypeName() {
		if (!StringUtils.isEmpty(this.outstockType)) {
			Map<String, SysDictionaryVO> outstockTypeMap = DictionaryUtil
					.getDictionaries("OUTSTOCK_TYPE");
			Map<String, String> outstockTypeEnumMap = new HashMap<String, String>();
			for (String key : outstockTypeMap.keySet()) {
				SysDictionaryVO sysDictionaryVO = outstockTypeMap.get(key);
				outstockTypeEnumMap.put(sysDictionaryVO.getCode(),
						sysDictionaryVO.getName());
			}
			return outstockTypeEnumMap.get(this.outstockType);
		}
		return null;
	}

	// 审核状态
	public String getApproveStatusName() {
		if (!StringUtils.isEmpty(this.approveStatus)) {
			Map<String, SysDictionaryVO> approveStatusMap = DictionaryUtil
					.getDictionaries("OUT_APPROVE_STATUS");
			Map<String, String> approveStatusEnumMap = new HashMap<String, String>();
			for (String key : approveStatusMap.keySet()) {
				SysDictionaryVO sysDictionaryVO = approveStatusMap.get(key);
				approveStatusEnumMap.put(sysDictionaryVO.getCode(),
						sysDictionaryVO.getName());
			}
			return approveStatusEnumMap.get(this.approveStatus);
		}
		return null;
	}
	
	public String getShowOutstockTime() {
		if (outstockTime != null) {
			DateFormatUtil dfu = DateFormatUtil.getInstance("yyyy-MM-dd");
			return dfu.format(this.outstockTime);
		}
		return "";
	}

}