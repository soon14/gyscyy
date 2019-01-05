package com.aptech.business.cargo.inStock.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.Alias;

import com.aptech.business.util.JsonDateDeserializer;
import com.aptech.business.util.JsonDateSerializer;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 入库管理实体类
 *
 * @author
 * @created 2017-07-19 11:32:25
 * @lastModified
 * @history
 *
 */
@Alias("InstockEntity")
public class InstockEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6567581615515859151L;

	/**
	 * 入库单号
	 */
	private String instockNum;
	/**
	 * 入库时间
	 */
	private Date instockTime;

	/**
	 * 入库类型
	 */
	private String instockType;
	
	/**
	 * 组织机构ID
	 */
	private Long unitId;

	/**
	 * 组织名称
	 */
	private String unitName;

	/**
	 * 申请人
	 */
	private String applicant;

	/**
	 * 申请人姓名
	 */
	private String applicantName;

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

	private String taskId;

	private String procInstId;

	private String userList;

	private Long wareHouseId;
	
	private String wareHouseName;
	//入库明细物资总价打印用
	private double goodsTotalPrice;
	
	
	

	public double getGoodsTotalPrice() {
		return goodsTotalPrice;
	}

	public void setGoodsTotalPrice(double goodsTotalPrice) {
		this.goodsTotalPrice = goodsTotalPrice;
	}

	public Long getWareHouseId() {
		return wareHouseId;
	}

	public String getWareHouseName() {
		return wareHouseName;
	}

	public void setWareHouseName(String wareHouseName) {
		this.wareHouseName = wareHouseName;
	}

	public void setWareHouseId(Long wareHouseId) {
		this.wareHouseId = wareHouseId;
	}

	public String getInstockNum() {
		return instockNum;
	}

	public void setInstockNum(String instockNum) {
		this.instockNum = instockNum;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getInstockTime() {
		return instockTime;
	}

	@JsonDeserialize(using = JsonDateDeserializer.class)
	public void setInstockTime(Date instockTime) {
		this.instockTime = instockTime;
	}

	public String getInstockType() {
		return instockType;
	}

	public void setInstockType(String instockType) {
		this.instockType = instockType;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
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

	// 入库类型
	public String getInstockTypeName() {
		if (!StringUtils.isEmpty(this.instockType)) {
			Map<String, SysDictionaryVO> instockTypeMap = DictionaryUtil
					.getDictionaries("INSTOCK_TYPE");
			Map<String, String> instockTypeEnumMap = new HashMap<String, String>();
			for (String key : instockTypeMap.keySet()) {
				SysDictionaryVO sysDictionaryVO = instockTypeMap.get(key);
				instockTypeEnumMap.put(sysDictionaryVO.getCode(),
						sysDictionaryVO.getName());
			}
			return instockTypeEnumMap.get(this.instockType);
		}
		return null;
	}

	// 审核状态
	public String getApproveStatusName() {
		if (!StringUtils.isEmpty(this.approveStatus)) {
			Map<String, SysDictionaryVO> approveStatusMap = DictionaryUtil
					.getDictionaries("APPROVE_STATUS");
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

	public String getShowInstockTime() {
		if (instockTime != null) {
			DateFormatUtil dfu = DateFormatUtil.getInstance("yyyy-MM");
			return dfu.format(this.instockTime);
		}
		return "";
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
	
}