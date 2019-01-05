package com.aptech.business.run.runWay.domain;

import java.util.List;

import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.framework.orm.BaseEntity;

/** @ClassName:     JoinLandVo.java 
 * @author         changl
 * @version        V1.0  
 * @Date           2017年10月10日 上午9:30:34 
 */

public class RunWayVo extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SysUnitEntity sysUnitEntity=null;
	
	private List<RunWayEntity> runWayList=null;

	public SysUnitEntity getSysUnitEntity() {
		return sysUnitEntity;
	}

	public void setSysUnitEntity(SysUnitEntity sysUnitEntity) {
		this.sysUnitEntity = sysUnitEntity;
	}

	public List<RunWayEntity> getRunWayList() {
		return runWayList;
	}

	public void setRunWayList(List<RunWayEntity> runWayList) {
		this.runWayList = runWayList;
	} 
	
}
