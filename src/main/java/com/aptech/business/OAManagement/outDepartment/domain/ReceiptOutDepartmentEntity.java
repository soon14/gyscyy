package com.aptech.business.OAManagement.outDepartment.domain;

import org.apache.ibatis.type.Alias;

import com.aptech.business.OAManagement.dispatchManagement.domain.ApprovalResultEnum;
import com.aptech.framework.orm.BaseEntity;


@Alias("ReceiptOutDepartment")
public class ReceiptOutDepartmentEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4432980523767983308L;
	// 收发文ID
	private Long receiptId;
	// 审查结果,1同意，2驳回
	private String result;
	// 审查结果,1同意，2驳回
	private String resultCN;
	// 审查意见
	private String comment;
	// 审查人id
	private String persionId;
	// 审查人姓名
	private String persionName;
	// 审查时间
	private String time;
	
	/**
	 * 组织id
	 */
	private String unitId;
	
	/**
	 * 组织名称
	 */
	private String unitName;
	//会签类型 1 生产部门会签 2 部门内部会签 3 外部部门会签
	private String type;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getResultCN() {
		for (ApprovalResultEnum resultEnum : ApprovalResultEnum.values()) {
			if (resultEnum.getCode().equals(result)) {
				resultCN = resultEnum.getName();
			}
		}
		return resultCN;
	}
	public void setResultCN(String resultCN) {
		this.resultCN = resultCN;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getPersionId() {
		return persionId;
	}
	public void setPersionId(String persionId) {
		this.persionId = persionId;
	}
	public String getPersionName() {
		return persionName;
	}
	public void setPersionName(String persionName) {
		this.persionName = persionName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	
	 
}