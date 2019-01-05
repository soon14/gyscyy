package com.aptech.business.ticketManage.operationItem.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 操作票项目表实体类
 * 
 * @author
 * @created 2017-07-12 17:27:36
 * @lastModified
 * @history
 * 
 */
@Alias("OperationItemEntity")
public class OperationItemEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6739353973959245020L;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 操作项目
	 */
	private String operationItem;
	/**
	 * 完成时间
	 */
	private Date finishDate;
	/**
	 * 电气倒阀操作票的id
	 */
	private Long operationId;
	/**
	 * 序号
	 */
	private Long order;
	/**
	 * 模拟
	 */
	private String simulation;
	/**
	 * 模拟
	 */
	private String simulationName;
	/**
	 * 实际
	 */
	private String actual;
	/**
	 * 实际
	 */
	private String actualName;

	private String reason;
	
	private Integer operateItemNum;
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getOperateItemNum() {
		return operateItemNum;
	}

	public void setOperateItemNum(Integer operateItemNum) {
		this.operateItemNum = operateItemNum;
	}

	public String getSimulationName() {
		return simulationName;
	}

	public void setSimulationName(String simulationName) {
		this.simulationName = simulationName;
	}

	public String getActualName() {
		return actualName;
	}

	public void setActualName(String actualName) {
		this.actualName = actualName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOperationItem() {
		return operationItem;
	}

	public void setOperationItem(String operationItem) {
		this.operationItem = operationItem;
	}

	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getFinishDate() {
		return finishDate;
	}

	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Long getOperationId() {
		return operationId;
	}

	public void setOperationId(Long operationId) {
		this.operationId = operationId;
	}

	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

	public String getSimulation() {
		return simulation;
	}

	public void setSimulation(String simulation) {
		this.simulation = simulation;
	}

	public String getActual() {
		return actual;
	}

	public void setActual(String actual) {
		this.actual = actual;
	}
}