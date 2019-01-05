package com.aptech.business.overhaul.overhaulSpareconsume.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aptech.business.overhaul.overhaulSafe.domain.OverhaulSafeEntity;
import com.aptech.business.overhaul.overhaulSpareconsume.domain.OverhaulSpareconsumeEntity;
import com.aptech.business.overhaul.overhaulWorkTask.domain.OverhaulWorkTaskEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 备件消耗应用管理服务接口
 *
 * @author 
 * @created 2018-03-15 10:23:12
 * @lastModified 
 * @history
 *
 */
public interface OverhaulSpareconsumeService  extends IBaseEntityOperation<OverhaulSpareconsumeEntity> {
	
	/**
	 * @Description:   检修日志-工作任务-新增设备
	 * @author         wangcc 
	 * @Date           2018年1月3日 上午10:50:32 
	 * @throws         Exception
	 */
	public @ResponseBody <T>  ResultObj addEquipInfo(HttpServletRequest request,Map<String, Object> params, @PathVariable Long overhaulArrangeId);
	

	public <T> ResultObj delete(HttpServletRequest request, Long id);
	
	public void update(OverhaulSpareconsumeEntity overhaulSpareconsumeEntity);

}