package com.aptech.business.cargo.scarpLibrary.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.aptech.business.cargo.scarpLibrary.domain.ScrapLibraryEntity;
import com.aptech.business.cargo.scrapLibraryDetail.domain.ScrapLibraryDetailEntity;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 报废库管理应用管理服务接口
 *
 * @author 
 * @created 2018-03-15 15:37:32
 * @lastModified 
 * @history
 *
 */
public interface ScrapLibraryService  extends IBaseEntityOperation<ScrapLibraryEntity> {
	ResultObj add(ScrapLibraryEntity t);
	public void addPageSave(/*String goodsAreaId,String goodsAllocationId,*/String materialIdString,String materialCountStringTemp,Date instockTime,Long wareHouseId,SysUnitEntity sysUnitEntity,ScrapLibraryEntity scrapLibraryEntity,ScrapLibraryDetailEntity scrapLibraryDetailEntity,Map<String,Object> detailPropertyMap);
	public void addPageSaveSingle(/*String goodsAreaId,String goodsAllocationId,*/String materialIdString,String materialCountStringTemp,Long wareHouseId,Date instockTime,SysUnitEntity sysUnitEntity,ScrapLibraryEntity scrapLibraryEntity,ScrapLibraryDetailEntity scrapLibraryDetailEntity,Map<String,Object> detailPropertyMap);
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
	public void editPageSave(Long scrapId,List<String> materialIdList,String materialIdStringTemp,String materialCountStringTemp,Long wareHouseIdDate,Date instockTime,SysUnitEntity sysUnitEntity,Map<String,Object> detailPropertyMap);

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
	public void editPageSaveSingle(Long scrapId,String materialIdStringTemp,String materialCountStringTemp,Long wareHouseId,SysUnitEntity sysUnitEntity,Date instockTime,Map<String, Object>detailPropertyMap);
	/**
	 * 
	 * 调用工作流流程开始接口
	 * 
	 * @param @param id
	 * @param @param params
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author zhangxb
	 * @created 2017年8月1日 下午6:37:04
	 * @lastModified
	 */
	public ResultObj submit(Serializable id, Map<String, Object> params);
	/**
	 * 
	 * 提交报废处理
	 * 
	 * @param @param id
	 * @param @param params
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author zhangxb
	 * @created 2017年8月1日 下午6:37:04
	 * @lastModified
	 */
	public ResultObj submitHandle(Serializable id, Map<String, Object> params);
	/**
	 * 
	 * 调用工作流下一步流程接口
	 * 
	 * @param @param t
	 * @param @param params
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author zhangxb
	 * @created 2017年8月1日 下午6:37:27
	 * @lastModified
	 */
	public ResultObj approve(ScrapLibraryEntity t,Map<String, Object> params);
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
	public Boolean compareKc(String materialId,String materialCount,String warehouseId);
}