package com.aptech.business.run.joinLand.domain;

import java.util.List;

import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.framework.orm.BaseEntity;

/** @ClassName:    JoinLandVo.java 
 * @author         wangcc
 * @version        V1.0  
 * @Date           2017年10月11日 上午19:30:34 
 */

public class JoinLandVo extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SysUnitEntity sysUnitEntity=null;
	

	private List<JoinLandEntity> joinLandList=null;

	public SysUnitEntity getSysUnitEntity() {
		return sysUnitEntity;
	}
	
	public void setSysUnitEntity(SysUnitEntity sysUnitEntity) {
		this.sysUnitEntity = sysUnitEntity;
	}
	
	public List<JoinLandEntity> getJoinLandList() {
		return joinLandList;
	}
	
	public void setJoinLandList(List<JoinLandEntity> joinLandList) {
		this.joinLandList = joinLandList;
	}
	
}
