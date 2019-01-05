package com.aptech.business.cargo.inStock.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.aptech.business.cargo.inStock.domain.InstockEntity;
import com.aptech.business.cargo.inStockDetail.domain.InstockDetailEntity;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 入库管理应用管理服务接口
 *
 * @author 
 * @created 2017-07-19 11:32:25
 * @lastModified 
 * @history
 *
 */
public interface InstockService  extends IBaseEntityOperation<InstockEntity> {
	
	/**
	 * 
	 * 按照日期生成每天的入库单号
	 * 
	 * @param @param prefix
	 * @param @param userUnitId
	 * @param @param dateFmtString
	 * @param @param sequenLength
	 * @param @return
	 * @return String
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月3日 下午3:13:25
	 * @lastModified
	 */
	public String generateCode(String prefix,String userUnitId,int sequenLength);
	
	/**
	 * 
	 * 提交实体，开始流程
	 * 
	 * @param @param id
	 * @param @param params
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年7月31日 下午1:21:36
	 * @lastModified
	 */
	public ResultObj submit(Serializable id, Map<String, Object> params);
	
	/**
	 * 
	 * 添加页面有多条物资明细时，保存入库单的同时添加入库明细和出入库记录，修改物资引用标记
	 * 
	 * @param @param materialIdString
	 * @param @param materialCountStringTemp
	 * @param @param instockdate
	 * @param @param instockNum
	 * @param @param sysUnitEntity 变更为 Long unitId edit by zhangxb
	 * @param @param instockEntity
	 * @param @param instockDetailEntity
	 * @param @param goodsPrice add by zhangxb
	 * @param @param goodsValidity add by zhangxb
	 * @param @param goodsAttribute add by zhangxb
	 * @return void
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月4日 上午10:51:25
	 * @lastModified
	 */
	public void addPageSave(String wareHouseIdString,String goodsAllocationIdString,String materialIdString,String materialCountStringTemp,Date instockdate,String instockNum,String goodsPrice,String goodsValidity,String goodsAttribute,Long wareHouseId,Long unitId,InstockEntity instockEntity,InstockDetailEntity instockDetailEntity);
	
	/**
	 * 
	 * 添加页面有一条物资明细时，保存入库单的同时添加入库明细和出入库记录，修改物资引用标记
	 * 
	 * @param @param materialIdString
	 * @param @param materialCountStringTemp
	 * @param @param instockNum
	 * @param @param instockdate
	 * @param @param sysUnitEntity  变更为 Long unitId edit by zhangxb
	 * @param @param instockEntity
	 * @param @param instockDetailEntity
	 * @param @param goodsPrice add by zhangxb
	 * @param @param goodsValidity add by zhangxb
	 * @param @param goodsAttribute add by zhangxb
	 * @return void
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月4日 上午10:56:53
	 * @lastModified
	 */
	public void addPageSaveSingle(String wareHouseIdString,String goodsAllocationIdString,String materialIdString,String materialCountStringTemp,String instockNum,String goodsPrice,String goodsValidity,String goodsAttribute,Long wareHouseId,Date instockdate,Long unitId,InstockEntity instockEntity,InstockDetailEntity instockDetailEntity);
	
	/**
	 * 
	 * 修改页面有多条物资明细
	 * 
	 * @param @param materialIdList
	 * @param @param materialIdStringTemp
	 * @param @param materialCountStringTemp
	 * @param @param instockNumString
	 * @param @param instockDate
	 * @param @param instockDetailEntity
	 * @param @param sysUnitEntity
	 * @return void
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月4日 上午11:25:14
	 * @lastModified
	 */
	public void editPageSave(String goodsAreaId,String goodsAllocationId,Long instockId,List<String> materialIdList,String materialIdStringTemp,String materialCountStringTemp,String instockNumString,String goodsPrice,String goodsValidity,String goodsAttribute,Long wareHouseIdDate,Date instockDate,SysUnitEntity sysUnitEntity);
	
	/**
	 * 
	 * 修改页面有一条物资明细
	 * 
	 * @param @param materialIdStringTemp
	 * @param @param materialCountStringTemp
	 * @param @param instockDetailEntity
	 * @param @param instockNumString
	 * @param @param sysUnitEntity
	 * @param @param instockDate
	 * @return void
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月4日 上午11:29:42
	 * @lastModified
	 */
	public void editPageSaveSingle(String goodsAreaId,String goodsAllocationId,Long instockId,String materialIdStringTemp,String materialCountStringTemp,String instockNumString,String goodsPrice,String goodsValidity,String goodsAttribute,Long wareHouseId,SysUnitEntity sysUnitEntity,Date instockDate);
	
	/**
	 * 
	 * 继续下一布流程
	 * 
	 * @param @param t
	 * @param @param params
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年7月31日 下午1:21:57
	 * @lastModified
	 */
	public ResultObj approve(InstockEntity t,Map<String, Object> params);
	
	/**
	 * 
	 * 页面初始化通过库管员name取得库管员id
	 * 
	 * @param @param params
	 * @param @return
	 * @return int
	 * @throws 
	 * @author wangyue
	 * @created 2017年8月8日 下午5:13:18
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
	 * @created 2017年8月8日 下午5:13:36
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
	 * @created 2017年8月8日 下午5:13:54
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
	 * @created 2017年8月8日 下午5:14:15
	 * @lastModified
	 */
	public List<Long> getUserId(Map<String, Object> params);
	
}