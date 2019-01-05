package com.aptech.business.cargo.outStock.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.aptech.business.cargo.outStock.domain.OutstockEntity;
import com.aptech.business.cargo.outStockDetail.domain.OutstockDetailEntity;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 出库管理应用管理服务接口
 *
 * @author 
 * @created 2017-07-21 09:26:05
 * @lastModified 
 * @history
 *
 */
public interface OutstockService  extends IBaseEntityOperation<OutstockEntity> {
	
	/**
	 * 
	 * 每天重新生成出库单号
	 * 
	 * @param @param prefix
	 * @param @param userUnitId
	 * @param @param sequenLength
	 * @param @return
	 * @return String
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月3日 下午3:45:15
	 * @lastModified
	 */
	public String generateOutstockNum(String prefix,String userUnitId,int sequenLength);
	
	/**
	 * 
	 * 调用工作流流程开始接口
	 * 
	 * @param @param id
	 * @param @param params
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月1日 下午6:37:04
	 * @lastModified
	 */
	public ResultObj submit(Serializable id, Map<String, Object> params);
	
	/**
	 * 
	 * 添加页面有多条物资明细
	 * 
	 * @param @param materialCountStringTemp
	 * @param @param materialIdString
	 * @param @param outstockEntity
	 * @param @param outscotckNumString
	 * @param @param sysUnitEntity
	 * @param @param outstockdate
	 * @return void
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月4日 上午11:41:50
	 * @lastModified
	 */
//	public void addPageSave(String materialGoodAreaStringTemp,String materialGoodsPositionStringTemp,String materialCountStringTemp,String materialIdString,OutstockEntity outstockEntity,String outscotckNumString,Long wareHouseId,SysUnitEntity sysUnitEntity,OutstockDetailEntity outstockDetailEntity,Date outstockdate);
	public void addPageSave(String materialCountStringTemp,String materialIdString,OutstockEntity outstockEntity,String outscotckNumString,Long wareHouseId,SysUnitEntity sysUnitEntity,OutstockDetailEntity outstockDetailEntity,Date outstockdate,Map<String,Object> detailPropertyMap);
	/**
	 * 
	 * 添加页面有一条物资明细
	 * 
	 * @param @param materialIdString
	 * @param @param materialCountStringTemp
	 * @param @param outstockEntity
	 * @param @param outscotckNumString
	 * @param @param sysUnitEntity
	 * @param @param outstockdate
	 * @return void
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月4日 上午11:45:41
	 * @lastModified
	 */
//	public void addPageSaveSingle(String materialGoodAreaStringTemp,String materialGoodsPositionStringTemp,String materialIdString,String materialCountStringTemp,OutstockEntity outstockEntity,String outscotckNumString,Long wareHouseId,SysUnitEntity sysUnitEntity,OutstockDetailEntity outstockDetailEntity,Date outstockdate);
	public void addPageSaveSingle(String materialIdString,String materialCountStringTemp,OutstockEntity outstockEntity,String outscotckNumString,Long wareHouseId,SysUnitEntity sysUnitEntity,OutstockDetailEntity outstockDetailEntity,Date outstockdate,Map<String,Object> detailPropertyMap);
	
	/**
	 * 
	 * 修改页面有多条物资明细
	 * 
	 * @param @param materialIdList
	 * @param @param materialIdStringTemp
	 * @param @param materialCountStringTemp
	 * @param @param outstockNum
	 * @param @param sysUnitEntity
	 * @param @param outstockDate
	 * @return void
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月4日 下午1:01:59
	 * @lastModified
	 */
	public void editPageSave(/*String materialGoodAreaStringTemp,String materialGoodsPositionStringTemp,*/Long outstockId, List<String> materialIdList,String materialIdStringTemp,String materialCountStringTemp,String outstockNum,Long wareHouseId,SysUnitEntity sysUnitEntity,Date outstockDate,Map<String,Object> detailPropertyMap);
	
	/**
	 * 
	 * 修改页面有一条物资明细
	 * 
	 * @param @param materialIdStringTemp
	 * @param @param materialCountStringTemp
	 * @param @param outstockNum
	 * @param @param sysUnitEntity
	 * @param @param outstockDate
	 * @return void
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月4日 下午1:02:18
	 * @lastModified
	 */
	public void editPageSaveSingle(/*String materialGoodAreaStringTemp,String materialGoodsPositionStringTemp,*/Long outstockId, String materialIdStringTemp,String materialCountStringTemp,String outstockNum,Long wareHouseId,SysUnitEntity sysUnitEntity,Date outstockDate);
	
	/**
	 * 
	 * 调用工作流下一步流程接口
	 * 
	 * @param @param t
	 * @param @param params
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月1日 下午6:37:27
	 * @lastModified
	 */
	public ResultObj approve(OutstockEntity t,Map<String, Object> params);

	/**
	 * 
	 * 页面初始化通过库管员name取得库管员id
	 * 
	 * @param @param params
	 * @param @return
	 * @return int
	 * @throws 
	 * @author wangyue
	 * @created 2017年8月8日 下午5:24:12
	 * @lastModified
	 */
	public int getDutyId (Map<String, Object> params);

	/**
	 * 
	 * 根据职务id检索得到该职务下的用户id
	 * 
	 * @param @param params
	 * @param @return
	 * @return List<Object>
	 * @throws 
	 * @author wangyue
	 * @created 2017年8月8日 下午5:24:30
	 * @lastModified
	 */
	public List<Long> getDutyDetailId(Map<String, Object> params);

	/**
	 * 
	 * 系统管理员的角色id
	 * 
	 * @param @param params
	 * @param @return
	 * @return int
	 * @throws 
	 * @author wangyue
	 * @created 2017年8月8日 下午5:24:51
	 * @lastModified
	 */
	public int getSystemId(Map<String, Object> params);

	/**
	 * 
	 * 根据角色id得到用户表中的用户id
	 * 
	 * @param @param params
	 * @param @return
	 * @return List<Object>
	 * @throws 
	 * @author wangyue
	 * @created 2017年8月8日 下午5:25:11
	 * @lastModified
	 */
	public List<Long> getUserId(Map<String, Object> params);
	
	/**
	 * 
	 * 出库数量和库存数量比较
	 * 
	 * @param @param materialId
	 * @param @param materialCount
	 * @param @return
	 * @return Boolean
	 * @throws 
	 * @author wangyue
	 * @created 2017年8月9日 下午7:31:58
	 * @lastModified
	 */
	public Boolean compareKc(String materialId,String materialCount);
}