package com.aptech.business.equip.equipLedger.service;
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 设备管理应用管理服务接口
 *
 * @author 
 * @created 2017-06-08 10:50:56
 * @lastModified 
 * @history
 *
 */
public interface EquipLedgerService  extends IBaseEntityOperation<EquipLedgerEntity> {
	/**
	 * @Description:   新增
	 * @author         wangcc 
	 * @Date           2017年6月26日 下午10:08:50 
	 * @throws         Exception
	 */
	ResultObj addEquimentLedger(Map<String, Object> params, HttpServletRequest request);
	/**
	 * @Description:   修改
	 * @author         wangcc 
	 * @Date           2017年6月26日 下午10:08:50 
	 * @throws         Exception
	 */
	ResultObj updateEquimentLedger(Map<String, Object> params, HttpServletRequest request);
	/**
	 * @Description:   设备导入
	 * @author         wangcc 
	 * @Date           2017年10月26日 下午8:08:50 
	 * @throws         Exception
	 */
	boolean importEquipLedger(HttpServletRequest request,File file,String filepath) throws Exception;
	/**
	 * @Description:   获取设备下拉树
	 * @author         wangcc 
	 * @Date           2017年10月26日 下午8:08:50 
	 * @throws         Exception
	 */
	List<EquipLedgerEntity> getEquipLedgerTreeList(Long unitId);
}