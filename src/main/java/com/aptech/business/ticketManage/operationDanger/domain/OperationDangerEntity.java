package com.aptech.business.ticketManage.operationDanger.domain;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 操作票危险因素情况表实体类
 * 
 * @author
 * @created 2017-07-12 17:27:40
 * @lastModified
 * @history
 * 
 */
@Alias("OperationDangerEntity")
public class OperationDangerEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5972463608972287101L;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 危险因素
	 */
	private String careCard;
	/**
	 * 控制措施
	 */
	private String startPicId;
	/**
	 * 电气倒阀操作票的id
	 */
	private Long operationId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCareCard() {
		return careCard;
	}

	public void setCareCard(String careCard) {
		this.careCard = careCard;
	}

	public String getStartPicId() {
		return startPicId;
	}

	public void setStartPicId(String startPicId) {
		this.startPicId = startPicId;
	}

	public Long getOperationId() {
		return operationId;
	}

	public void setOperationId(Long operationId) {
		this.operationId = operationId;
	}
}