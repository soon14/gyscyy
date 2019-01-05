package com.aptech.business.cargo.outStock.service;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.cargo.inOutStock.domain.InOutStockEntity;
import com.aptech.business.cargo.inOutStock.service.InOutStockService;
import com.aptech.business.cargo.inStockDetail.domain.InstockDetailEntity;
import com.aptech.business.cargo.materialCategory.domain.MaterialCategoryEntity;
import com.aptech.business.cargo.materialCategory.service.MaterialCategoryService;
import com.aptech.business.cargo.outStock.dao.OutstockDao;
import com.aptech.business.cargo.outStock.domain.OutstockEntity;
import com.aptech.business.cargo.outStock.exception.OutstockException;
import com.aptech.business.cargo.outStock.exception.OutstockExceptionType;
import com.aptech.business.cargo.outStockDetail.domain.OutstockDetailEntity;
import com.aptech.business.cargo.outStockDetail.service.OutstockDetailService;
import com.aptech.business.cargo.scarpLibrary.domain.ScrapLibraryEntity;
import com.aptech.business.cargo.scarpLibrary.service.ScrapLibraryService;
import com.aptech.business.cargo.scrapLibraryDetail.domain.ScrapLibraryDetailEntity;
import com.aptech.business.cargo.scrapLibraryDetail.service.ScrapLibraryDetailService;
import com.aptech.business.cargo.stock.domain.StockEntity;
import com.aptech.business.cargo.stock.service.StockService;
import com.aptech.business.cargo.stockStatictisPrint.domain.StockStatictisPrintEntity;
import com.aptech.business.cargo.stockStatictisPrint.service.StockStatictisPrintService;
import com.aptech.business.component.dictionary.OutstockApproveStatusEnum;
import com.aptech.business.component.dictionary.OutstockBreakApproveStatusEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.ScrapSourceEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.wareHouse.wareHouse.domain.WareHouseEntity;
import com.aptech.business.wareHouse.wareHouse.service.WareHouseService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.duties.domain.SysDutiesEntity;
import com.aptech.common.system.duties.service.SysDutiesService;
import com.aptech.common.system.dutiesdetail.domain.SysDutiesDetailEntity;
import com.aptech.common.system.dutiesdetail.service.SysDutiesDetailService;
import com.aptech.common.system.role.domain.SysRoleEntity;
import com.aptech.common.system.role.service.SysRoleService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.system.userUnit.domain.UserUnitRelEntity;
import com.aptech.common.system.userUnit.service.UserUnitRelService;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.common.workflow.todoTask.domain.ExamMarkEnum;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.StringUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 出库管理应用管理服务实现类
 *
 * @author 
 * @created 2017-07-21 09:26:05
 * @lastModified 
 * @history
 *
 */
@Service("outstockService")
@Transactional
public class OutstockServiceImpl extends AbstractBaseEntityOperation<OutstockEntity> implements OutstockService {
	
	@Autowired
	private ScrapLibraryDetailService scrapLibraryDetailService;
	@Autowired
	private ScrapLibraryService scrapLibraryService;
	@Autowired
	private InOutStockService inOutStockService;
	@Autowired
	private OutstockDetailService outstockDetailService;
	@Autowired
	private MaterialCategoryService materialCategoryService;
	@Autowired
	private StockService stockService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private OutstockDao outstockDao;
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private SysDutiesService sysDutiesService;
	@Autowired
	private WareHouseService wareHouseService;
	@Autowired
	private SysDutiesDetailService sysDutiesDetailService;
	
	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private UserUnitRelService userUnitRelService;
	@Autowired
	private StockStatictisPrintService stockStatictisPrintService;
	@Override
	public IBaseEntityOperation<OutstockEntity> getDao() {
		return outstockDao;
	}
	
	/**
	 * 每天从第一单开始生成出库单号
	 */
	public String generateOutstockNum(String prefix,String userUnitId,int sequenLength){
		StringBuffer sbf = new StringBuffer();
		//前缀
		sbf.append(prefix);
		//追加单位id
		sbf.append(userUnitId);
		//追加日期
		DateFormatUtil format = DateFormatUtil.getInstance("yyyyMMdd");
		String riqi = format.format(new Date());
		sbf.append(riqi);
		//追加序号
		String sequenceNum = generateSequenceNum(sbf.toString(), sequenLength);
		sbf.append(sequenceNum);
		return sbf.toString();
	}
	
	/**
	 * 
	 * 根据日期生成每日序列号
	 * 
	 * @param @param prefixCode
	 * @param @param sequenLength
	 * @param @return
	 * @return String
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月3日 下午4:02:20
	 * @lastModified
	 */
	private String generateSequenceNum(String prefixCode, int sequenLength){
		String sequenceNum = "";
		//用条件模糊查询出当日最大出库单号
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("outstockNum", FieldTypeEnum.STRING, MatchTypeEnum.LIKE,prefixCode));
		List<Object> List_outstockNum = this.findByCondition("findMaxCode", conditions, null);
		if(null != List_outstockNum && !List_outstockNum.isEmpty()){
			OutstockEntity outstockEntity = (OutstockEntity)List_outstockNum.get(0);
			//取当日最大入库单号
			String maxOutstockNum = outstockEntity.getOutstockNum();
			if(StringUtil.isNotEmpty(maxOutstockNum)){
				//按序列号位数取入库单号序列号
				String tempNumString = maxOutstockNum.substring(maxOutstockNum.length()-sequenLength, maxOutstockNum.length());
				//序列号增加1
				Long instockNumLong = Long.parseLong(tempNumString)+1;
				if(instockNumLong.toString().length()!=sequenLength){
					//不够序列号位数前面补零
					sequenceNum = String.format("%0" + sequenLength + "d", instockNumLong);
				}
			}else{
				//当日没有入库单，从1开始，用0补齐序列号位数
				sequenceNum = String.format("%0" + sequenLength + "d", 1);
			}
		}
		return sequenceNum;
	}
	
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
	 * @created 2017年8月4日 上午11:40:57
	 * @lastModified
	 */
	@SuppressWarnings("unchecked")
	public void addPageSave(/*String materialGoodAreaStringTemp,String materialGoodsPositionStringTemp,*/String materialCountStringTemp,String materialIdString,OutstockEntity outstockEntity,String outscotckNumString,Long wareHouseId,SysUnitEntity sysUnitEntity,OutstockDetailEntity outstockDetailEntity,Date outstockdate,Map<String,Object> detailPropertyMap){
		//有多条物资明细
		String materialCountString = materialCountStringTemp.substring(1,materialCountStringTemp.length()-1);
		String[] materialArray = materialIdString.split(",");
		String[] materialCountArray = materialCountString.split(",");
//		String goodsAreaIdString = materialGoodAreaStringTemp.substring(1,materialGoodAreaStringTemp.length()-1);
//		String[] goodsAreaIdArray = goodsAreaIdString.split(",");
//		String goodsAllocationIdString = materialGoodsPositionStringTemp.substring(1,materialGoodsPositionStringTemp.length()-1);
//		String[] goodsAllocationIdArray = goodsAllocationIdString.split(",");
		for(int i=0;i<materialArray.length;i++){
			//根据物资管理方式判断数量是整数或是小数
			MaterialCategoryEntity materialCategoryEntity = materialCategoryService.findById(Long.valueOf(materialArray[i].trim()));
			String materialManageMethod = materialCategoryEntity.getManagement();
			String materialCount = "0";
			//如果物资的管理方式为实例（1）时，数量为整数
			Double mCountDouble = Double.parseDouble(materialCountArray[i].trim());
			if(null != materialManageMethod && materialManageMethod.equals("1")){
				int mCountInteger = (int) Math.floor(mCountDouble);
				materialCount = String.valueOf(mCountInteger);
				//如果物资的管理方式为记账（2）时，数量为小数
			}else if(null != materialManageMethod && materialManageMethod.equals("2")){
				java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
				materialCount = String.valueOf(df.format(mCountDouble));
			}
			//增加出库明细
			outstockDetailEntity.setOutstockId(outstockEntity.getId());
			outstockDetailEntity.setMaterialId(Long.valueOf(materialArray[i].trim()));
			outstockDetailEntity.setCount(materialCount);
			Map<String,Object> detail = (Map<String, Object>) detailPropertyMap.get(materialArray[i].trim());
			if(detail!=null && detail.get("goodsAttribute")!=null){
				outstockDetailEntity.setGoodsAttribute(detail.get("goodsAttribute").toString());
			}
			if(detail!=null && detail.get("goodsValidity")!=null){
				SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
				Long time=new Long(detail.get("goodsValidity").toString());  
				String d = format.format(time); 
				Date date = null;
			    try {
					date=format.parse(d);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				outstockDetailEntity.setGoodsValidity(date); 
			}
			if(detail!=null && detail.get("goodsPrice")!=null){
				outstockDetailEntity.setGoodsPrice(detail.get("goodsPrice").toString());
			}
//			outstockDetailEntity.setGoodsAreaId(goodsAreaIdArray[i].trim());
//			outstockDetailEntity.setGoodsAllocationId(goodsAllocationIdArray[i].trim());
			outstockDetailService.addEntity(outstockDetailEntity);
			//修改物资引用字段
			materialCategoryEntity.setQuote("1");
			materialCategoryService.updateEntity(materialCategoryEntity);
			//增加出入库明细
			/*InOutStockEntity inOutStockEntity = new InOutStockEntity();
			inOutStockEntity.setCode(outscotckNumString);
			inOutStockEntity.setUnitId(sysUnitEntity.getId());
			inOutStockEntity.setTime(outstockdate);
			inOutStockEntity.setType("2");
			inOutStockEntity.setMaterialId(Long.valueOf(materialArray[i].trim()));
			inOutStockEntity.setCount(materialCount);
			inOutStockEntity.setWareHouseId(wareHouseId);
			inOutStockService.addEntity(inOutStockEntity);*/
		}
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.OUTSTOCKMOVE.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
	}
	
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
	 * @created 2017年8月4日 上午11:44:38
	 * @lastModified
	 */
	@SuppressWarnings("unchecked")
	public void addPageSaveSingle(/*String materialGoodAreaStringTemp,String materialGoodsPositionStringTemp,*/String materialIdString,String materialCountStringTemp,OutstockEntity outstockEntity,String outscotckNumString,Long wareHouseId,SysUnitEntity sysUnitEntity,OutstockDetailEntity outstockDetailEntity,Date outstockdate,Map<String,Object> detailPropertyMap){
		MaterialCategoryEntity materialCategoryEntity = materialCategoryService.findById(Long.valueOf(materialIdString));
		String materialManageMethod = materialCategoryEntity.getManagement();
		String materialCount = "0";
//		String goodsAreaId = materialGoodAreaStringTemp.substring(1,materialGoodAreaStringTemp.length()-1);
//		String goodsPositionId = materialGoodsPositionStringTemp.substring(1,materialGoodsPositionStringTemp.length()-1);
		//如果物资的管理方式为实例（1）时，数量为整数
		Double mCountDouble = Double.parseDouble(materialCountStringTemp);
		if(null != materialManageMethod && materialManageMethod.equals("1")){
			int mCountInteger = (int) Math.floor(mCountDouble);
			materialCount = String.valueOf(mCountInteger);
		//如果物资的管理方式为记账（2）时，数量为小数
		}else if(null != materialManageMethod && materialManageMethod.equals("2")){
			java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
			materialCount = String.valueOf(df.format(mCountDouble));
		}
		//增加入库物资明细
		outstockDetailEntity.setOutstockId(outstockEntity.getId());
		outstockDetailEntity.setMaterialId(Long.valueOf(materialIdString));
		outstockDetailEntity.setCount(materialCount);
		Map<String,Object> detail = (Map<String, Object>) detailPropertyMap.get(materialIdString);
		if(detail.get("goodsAttribute")!=null){
			outstockDetailEntity.setGoodsAttribute(detail.get("goodsAttribute").toString());
		}
		if(detail.get("goodsValidity")!=null){
			SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			Long time=new Long(detail.get("goodsValidity").toString());  
			String d = format.format(time); 
			Date date = null;
		    try {
				date=format.parse(d);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			outstockDetailEntity.setGoodsValidity(date); 
		}
		if(detail.get("goodsPrice")!=null){
			outstockDetailEntity.setGoodsPrice(detail.get("goodsPrice").toString());
		}
//		outstockDetailEntity.setGoodsAreaId(goodsAreaId);
//		outstockDetailEntity.setGoodsAllocationId(goodsPositionId);
		outstockDetailService.addEntity(outstockDetailEntity);
		//物资修改引用字段
		materialCategoryEntity.setQuote("1");
		materialCategoryService.updateEntity(materialCategoryEntity);
		//增加出入库明细
		/*InOutStockEntity inOutStockEntity = new InOutStockEntity();
		inOutStockEntity.setCode(outscotckNumString);
		inOutStockEntity.setUnitId(sysUnitEntity.getId());
		inOutStockEntity.setTime(outstockdate);
		inOutStockEntity.setType("2");
		inOutStockEntity.setMaterialId(Long.valueOf(materialIdString));
		inOutStockEntity.setCount(materialCount);
		inOutStockEntity.setWareHouseId(wareHouseId);
		inOutStockService.addEntity(inOutStockEntity);*/
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.OUTSTOCKMOVE.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
	}
	
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
	 * @created 2017年8月4日 上午11:57:48
	 * @lastModified
	 */
	public void editPageSave(/*String materialGoodAreaStringTemp,String materialGoodsPositionStringTemp,*/Long outstockId, List<String> materialIdList,String materialIdStringTemp,String materialCountStringTemp,String outstockNum,Long wareHouseId,SysUnitEntity sysUnitEntity,Date outstockDate,Map<String,Object> detailPropertyMap){
		List<OutstockDetailEntity> oldList=new ArrayList<OutstockDetailEntity>();
		for(int i=0;i<materialIdList.size();i++){
			String materialIdString = materialIdStringTemp.substring(1,materialIdStringTemp.length()-1);
			String[] materialIdArray = materialIdString.split(",");
			List<Condition> conditions=new ArrayList<Condition>();
			conditions.add(new Condition("C_OUTSTOCK_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,outstockId ));
			conditions.add(new Condition("C_MATERIAL_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,Long.valueOf(materialIdArray[i].trim()) ));
			List<OutstockDetailEntity> oldListSearch=outstockDetailService.findByCondition(conditions, null);
			OutstockDetailEntity oldEntity=new OutstockDetailEntity();
			if(!oldListSearch.isEmpty()){
				oldEntity=oldListSearch.get(0);
			}
			oldList.add(oldEntity);
		}
		
		//根据入库单选择入库明细数据，先删除后添加
		OutstockDetailEntity outstockDetailEntity = new OutstockDetailEntity();
		outstockDetailEntity.setOutstockId(outstockId);
		outstockDetailService.deleteByCondition("deleteByOutstockId", outstockDetailEntity);
		//根据出入库单号删除出入库明细
		InOutStockEntity inOutStock = new InOutStockEntity();
		inOutStock.setCode(outstockNum);
		inOutStockService.deleteByCondition("deleteByStockCode", inOutStock);
		for(int i=0;i<oldList.size();i++){
			String materialCountString = materialCountStringTemp.substring(1,materialCountStringTemp.length()-1);
			String materialIdString = materialIdStringTemp.substring(1,materialIdStringTemp.length()-1);
			String[] materialCountArray = materialCountString.split(",");
			String[] materialIdArray = materialIdString.split(",");
//			String goodsAreaIdString = materialGoodAreaStringTemp.substring(1,materialGoodAreaStringTemp.length()-1);
//			String[] goodsAreaIdArray = goodsAreaIdString.split(",");
//			String goodsAllocationIdString = materialGoodsPositionStringTemp.substring(1,materialGoodsPositionStringTemp.length()-1);
//			String[] goodsAllocationIdArray = goodsAllocationIdString.split(",");
			MaterialCategoryEntity materialCategoryEntity = materialCategoryService.findById(Long.valueOf(materialIdArray[i].trim()));
			
			//根据物资管理方式判断数量是整数或是小数
			String materialManageMethod = materialCategoryEntity.getManagement();
			String materialCount = "0";
			//如果物资的管理方式为实例（1）时，数量为整数
			Double mCountDouble = Double.parseDouble(materialCountArray[i].trim());
			if(StringUtil.isNotEmpty(materialManageMethod) && materialManageMethod.equals("1")){
				int mCountInteger = (int) Math.floor(mCountDouble);
				materialCount = String.valueOf(mCountInteger);
			//如果物资的管理方式为记账（2）时，数量为小数
			}else if(null != materialManageMethod && materialManageMethod.equals("2")){
				java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
				materialCount = String.valueOf(df.format(mCountDouble));
			}
			//++++++++++++++++++++++++++
			Map<String,Object> detail = (Map<String, Object>) detailPropertyMap.get(materialIdArray[i].trim());
			if(detail.get("goodsAttribute")==null){
				OutstockDetailEntity beforeDeleteEntity = oldList.get(i);//没删除之前得到的实体bean
				outstockDetailEntity.setGoodsAttribute(beforeDeleteEntity.getGoodsAttribute());
				outstockDetailEntity.setGoodsValidity(beforeDeleteEntity.getGoodsValidity());
				outstockDetailEntity.setGoodsPrice(beforeDeleteEntity.getGoodsPrice());
			}else{
				if(detail!=null && detail.get("goodsAttribute")!=null){
					outstockDetailEntity.setGoodsAttribute(detail.get("goodsAttribute").toString());
				}
				if(detail!=null && detail.get("goodsValidity")!=null){
					SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
					Long time=new Long(detail.get("goodsValidity").toString());  
					String d = format.format(time); 
					Date date = null;
				    try {
						date=format.parse(d);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					outstockDetailEntity.setGoodsValidity(date); 
				}
				if(detail!=null && detail.get("goodsPrice")!=null){
					outstockDetailEntity.setGoodsPrice(detail.get("goodsPrice").toString());
				}
			}
			//+++++++++++++++++++++++++++++
			outstockDetailEntity.setMaterialId(Long.valueOf(materialIdArray[i].trim()));
			outstockDetailEntity.setCount(materialCount);
//			outstockDetailEntity.setGoodsAreaId(goodsAreaIdArray[i].trim());
//			outstockDetailEntity.setGoodsAllocationId(goodsAllocationIdArray[i].trim());
			outstockDetailService.addEntity(outstockDetailEntity);
			materialCategoryEntity.setQuote("1");
			materialCategoryService.updateEntity(materialCategoryEntity);
			//增加出入库明细
			/*InOutStockEntity inOutStockEntity = new InOutStockEntity();
			inOutStockEntity.setCode(outstockNum);
			inOutStockEntity.setUnitId(sysUnitEntity.getId());
			inOutStockEntity.setTime(outstockDate);
			inOutStockEntity.setType("2");
			inOutStockEntity.setMaterialId(Long.valueOf(materialIdArray[i].trim()));
			inOutStockEntity.setCount(materialCount);
			inOutStockEntity.setWareHouseId(wareHouseId);
			inOutStockService.addEntity(inOutStockEntity);*/
		}
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.OUTSTOCKMOVE.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
	}
	
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
	 * @created 2017年8月4日 下午12:00:56
	 * @lastModified
	 */
	public void editPageSaveSingle(/*String materialGoodAreaStringTemp,String materialGoodsPositionStringTemp,*/Long outstockId, String materialIdStringTemp,String materialCountStringTemp,String outstockNum,Long wareHouseId,SysUnitEntity sysUnitEntity,Date outstockDate){
		//根据入库单选择入库明细数据，先删除后添加
		String materialIdString = materialIdStringTemp.substring(1,materialIdStringTemp.length()-1);
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_OUTSTOCK_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,outstockId ));
		conditions.add(new Condition("C_MATERIAL_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,Long.valueOf(materialIdString) ));
		OutstockDetailEntity outstockDetailEntity = new OutstockDetailEntity();
		List<OutstockDetailEntity> oldList=outstockDetailService.findByCondition(conditions, null);
		if(!oldList.isEmpty()){
			outstockDetailEntity=oldList.get(0);
		}
		
		outstockDetailEntity.setOutstockId(outstockId);
		outstockDetailService.deleteByCondition("deleteByOutstockId", outstockDetailEntity);
		//根据出入库单号删除出入库明细
		InOutStockEntity inOutStock = new InOutStockEntity();
		inOutStock.setCode(outstockNum);
		inOutStockService.deleteByCondition("deleteByStockCode", inOutStock);
		
		
		MaterialCategoryEntity materialCategoryEntity = materialCategoryService.findById(Long.valueOf(materialIdString));
		
		String materialManageMethod = materialCategoryEntity.getManagement();
		String materialCount = "0";
//		String goodsAreaId = materialGoodAreaStringTemp.substring(1,materialGoodAreaStringTemp.length()-1);
//		String goodsPositionId = materialGoodsPositionStringTemp.substring(1,materialGoodsPositionStringTemp.length()-1);
		//如果物资的管理方式为实例（1）时，数量为整数
		Double mCountDouble = Double.parseDouble(materialCountStringTemp);
		if(StringUtil.isNotEmpty(materialManageMethod) && materialManageMethod.equals("1")){
			int mCountInteger = (int) Math.floor(mCountDouble);
			materialCount = String.valueOf(mCountInteger);
		//如果物资的管理方式为记账（2）时，数量为小数
		}else if(null != materialManageMethod && materialManageMethod.equals("2")){
			java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
			materialCount = String.valueOf(df.format(mCountDouble));
		}
		
		outstockDetailEntity.setMaterialId(Long.valueOf(materialIdString));
		outstockDetailEntity.setCount(materialCount);
//		outstockDetailEntity.setGoodsAreaId(goodsAreaId);
//		outstockDetailEntity.setGoodsAllocationId(goodsPositionId);
		outstockDetailService.addEntity(outstockDetailEntity);
		materialCategoryEntity.setQuote("1");
		materialCategoryService.updateEntity(materialCategoryEntity);
		//增加出入库明细
		/*InOutStockEntity inOutStockEntity = new InOutStockEntity();
		inOutStockEntity.setCode(outstockNum);
		inOutStockEntity.setUnitId(sysUnitEntity.getId());
		inOutStockEntity.setTime(outstockDate);
		inOutStockEntity.setType("2");
		inOutStockEntity.setMaterialId(Long.valueOf(materialIdString));
		inOutStockEntity.setCount(materialCount);
		inOutStockEntity.setWareHouseId(wareHouseId);
		inOutStockService.addEntity(inOutStockEntity);*/
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.OUTSTOCKMOVE.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
	}
	
	
	/**
	 * 提交调用开始流程
	 */
	public ResultObj submit(Serializable id, Map<String, Object> params){
		ResultObj resultObj = new ResultObj();
		OutstockEntity t = this.findById(id);
		if(validate(t)){
			if("1".equals(t.getOutstockType())){
				t.setApproveStatus(OutstockApproveStatusEnum.STOREKEEPER.getCode());
			}else{
				t.setApproveStatus(OutstockBreakApproveStatusEnum.DIRECTORAPPROVE.getCode());
			}
			
			this.updateEntity(t);
			// 准备启动流程
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),params.get("userList"));
			if("1".equals(t.getOutstockType())){
				actTaskService.startProcess(ProcessMarkEnum.OUT_STOCK_PROCESS_KEY.getName(), id.toString(), vars);
			}else{
				actTaskService.startProcess(ProcessMarkEnum.OUT_STOCK_DRAW_PROCESS_KEY.getName(), id.toString(), vars);
			}
			
		}
		return resultObj;
	}
	
	/**
	 * 审批调用下一步流程
	 */
	public ResultObj approve(OutstockEntity t,Map<String, Object> params){
		if(validateStatus(t)){
			// 修改状态
			OutstockEntity outstockEntity = this.findById(t.getId());
			System.out.println(params.get("status").toString());
			outstockEntity.setApproveStatus(params.get("status").toString());
			if(t.getStoreKeeper()!=null){
				outstockEntity.setStoreKeeper(t.getStoreKeeper());
			}
			if(t.getApproveUser()!=null){
				outstockEntity.setApproveUser(t.getApproveUser());
			}
			this.updateEntity(outstockEntity);
			//设置当前流程,当前任务节点的下一个节点的办理人
			actTaskService.setVarialble(t.getTaskId(),params);
			// 调用流程接口
			params.put(ExamMarkEnum.RESULT.getCode(), params.get("result").toString());
			actTaskService.complete(t.getTaskId(), t.getProcInstId(),params);
			//入库审批完成后，修改库存物资数量
			if(null!=params && params.get("status").toString().equals("5")){
				List<Condition> conditions_detail=new ArrayList<Condition>();
				conditions_detail.add(new Condition("a.C_OUTSTOCK_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, outstockEntity.getId()));
				List<OutstockDetailEntity> outstockDetailEntities = outstockDetailService.findByCondition(conditions_detail, null);
				InOutStockEntity inOutStockEntity=null;
				for(OutstockDetailEntity outstockDetailEntity : outstockDetailEntities){
					//增加出入库明细2018.4.26 start
					inOutStockEntity = new InOutStockEntity();
					inOutStockEntity.setCode(outstockEntity.getOutstockNum());
					inOutStockEntity.setUnitId(outstockEntity.getUnitId());
					inOutStockEntity.setTime(outstockEntity.getOutstockTime());
					inOutStockEntity.setType("2");
					inOutStockEntity.setMaterialId(outstockDetailEntity.getMaterialId());
					inOutStockEntity.setCount(outstockDetailEntity.getCount().toString());
					inOutStockEntity.setWareHouseId(outstockEntity.getWareHouseId());
					inOutStockService.addEntity(inOutStockEntity);
					//增加出入库明细2018.4.26 end
					
					Long unitId = outstockEntity.getUnitId();
					Long materialId = outstockDetailEntity.getMaterialId();
					String materialCount = outstockDetailEntity.getCount();
					List<Condition> conditions=new ArrayList<Condition>();
					conditions.add(new Condition("sk.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, unitId));
					conditions.add(new Condition("sk.C_MATERIAL_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, materialId));
					conditions.add(new Condition("sk.C_GOODS_PRICE", FieldTypeEnum.LONG, MatchTypeEnum.EQ, outstockDetailEntity.getGoodsPrice()));
					conditions.add(new Condition("sk.C_GOODS_VALIDITY", FieldTypeEnum.LONG, MatchTypeEnum.EQ, outstockDetailEntity.getGoodsValidity()));
					List<StockEntity> stockEntities = stockService.findByCondition(conditions, null);
					for(StockEntity stockEntity : stockEntities){
						String kcslString = stockEntity.getInventoryQuantity();
						double kcsl = Double.parseDouble(kcslString)-Double.parseDouble(materialCount);
						stockEntity.setInventoryQuantity(String.valueOf(kcsl));
						stockEntity.setWareHouseId(outstockEntity.getWareHouseId());
						//判断库存数量与标准下限对比，是否短缺，短缺数量
						//标准下限
						Double lowerLimit = 0.0;
						if(stockEntity.getLowerLimit()!=null){
							lowerLimit = Double.parseDouble(stockEntity.getLowerLimit());
						}
						int mCountInteger = (int) Math.floor(kcsl);
						int lowerCountInteger = 0;
						if(lowerLimit!=null){
							lowerCountInteger = (int) Math.floor(lowerLimit);
						}
						if(lowerLimit>kcsl){
							stockEntity.setIsShortage("1");
							int shortageInteger = lowerCountInteger-mCountInteger;
							stockEntity.setShortage(String.valueOf(shortageInteger));
						}else{
							stockEntity.setIsShortage("0");
							stockEntity.setShortage("0");
						}
						stockService.updateEntity(stockEntity);
					}
				}
				WareHouseEntity wareHouseEntity = wareHouseService.findById(Long.valueOf(outstockEntity.getWareHouseId()));
				List<Condition> conditions = new ArrayList<Condition>();
				conditions.add(new Condition("a.C_OUTSTOCK_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, outstockEntity.getId()));
				List<OutstockDetailEntity> outstockDetailList = outstockDetailService.findByCondition(conditions, null);
				conditions.clear();
				conditions.add(new Condition("C_MATERIAL_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, outstockDetailList.get(0).getMaterialId()));
				conditions.add(new Condition("C_WAREHOUSE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, wareHouseEntity.getWareHouseName()));
				conditions.add(new Condition("C_TIME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, outstockEntity.getOutstockTime()));
				conditions.add(new Condition("C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
				List<StockStatictisPrintEntity> stockStatictisPrintList = stockStatictisPrintService.findByCondition(conditions, null);
				if(stockStatictisPrintList!=null&&!stockStatictisPrintList.isEmpty()){
					StockStatictisPrintEntity stockStatictisPrintEntity = stockStatictisPrintList.get(0);
					double countd = Double.parseDouble(outstockDetailList.get(0).getCount());
					int count = (int) countd;
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
					String time = sdf.format(stockStatictisPrintEntity.getTime());
					Date instockTime = null;
					String systimes = sdf.format(new Date());
					Date sysTime = null;
					try {
						instockTime = sdf.parse(time);
						sysTime = sdf.parse(systimes);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					int result = instockTime.compareTo(sysTime);
					if(result==0){
						stockStatictisPrintEntity.setMonthOutstockNum(stockStatictisPrintEntity.getMonthOutstockNum()+count);;
					}else if(result>0){
						stockStatictisPrintEntity.setLastMonthStock(stockStatictisPrintEntity.getLastMonthStock()-count);
					}
					
					stockStatictisPrintEntity.setMonthStock(stockStatictisPrintEntity.getLastMonthStock()+stockStatictisPrintEntity.getMonthInstockNum()-stockStatictisPrintEntity.getMonthOutstockNum());
					stockStatictisPrintService.updateEntity(stockStatictisPrintEntity);
			}
			}
			//报废出库审批通过  自动新增入库单
			if(null!=params&&params.get("status").toString().equals("9")){
				List<Condition> conditions_detail=new ArrayList<Condition>();
				conditions_detail.add(new Condition("a.C_OUTSTOCK_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, outstockEntity.getId()));
				List<OutstockDetailEntity> outstockDetailEntities = outstockDetailService.findByCondition(conditions_detail, null);
				ScrapLibraryEntity scrapLibraryEntity= new ScrapLibraryEntity();
				scrapLibraryEntity.setFileId(outstockEntity.getAttachment());
				scrapLibraryEntity.setInstockTime(outstockEntity.getOutstockTime());
				scrapLibraryEntity.setInstockType("1");//入库1  出库0
				scrapLibraryEntity.setRemark(outstockEntity.getRemark());
				scrapLibraryEntity.setScrapSource(ScrapSourceEnum.AUTO.getCode());//报废来源 报废出库1手动添加0
				scrapLibraryEntity.setStationSourceId(outstockEntity.getUnitId().toString());
				scrapLibraryEntity.setStatus(OutstockBreakApproveStatusEnum.END.getCode());
				scrapLibraryEntity.setWarehouseId(outstockEntity.getWareHouseId().toString());
				scrapLibraryService.addEntity(scrapLibraryEntity);
				long scrapLibraryEntityId=scrapLibraryEntity.getId();
				for(OutstockDetailEntity outstockDetailEntity : outstockDetailEntities){
					ScrapLibraryDetailEntity scrapLibraryDetailEntity=new ScrapLibraryDetailEntity();
					
					scrapLibraryDetailEntity.setMetrialId(outstockDetailEntity.getMaterialId());
					scrapLibraryDetailEntity.setCount(outstockDetailEntity.getCount());
					scrapLibraryDetailEntity.setGoodsAreaId(outstockDetailEntity.getGoodsAreaId());
					scrapLibraryDetailEntity.setGoodsAllocationId(outstockDetailEntity.getGoodsAllocationId());
					scrapLibraryDetailEntity.setInstockId(scrapLibraryEntityId);
					scrapLibraryDetailService.addEntity(scrapLibraryDetailEntity);
				}
				
			}
		}
		return  new ResultObj();
	}
	
	/**
	 * 
	 * 验证异常
	 * 
	 * @param @param t
	 * @param @return
	 * @return boolean
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月1日 下午6:41:22
	 * @lastModified
	 */
	public boolean validate(OutstockEntity t) {
		if (t == null) {
			throw new OutstockException(OutstockExceptionType.OUTSTOCK_CODE_NULL);
		}
		if (!(t.getApproveStatus().equals(OutstockApproveStatusEnum.PENDING.getCode()) || t.getApproveStatus().equals(OutstockApproveStatusEnum.REJECT.getCode()))) {
			throw new OutstockException(OutstockExceptionType.OUTSTOCK_CODE_STATUS);
		}
		return true;
	}
	
	/**
	 * 
	 * 流程基本验证
	 * 
	 * @param @param t
	 * @param @return
	 * @return boolean
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月1日 下午6:42:02
	 * @lastModified
	 */
	public boolean validateStatus(OutstockEntity t) {
		OutstockEntity tEntity = this.findById(t.getId());
		if (tEntity == null) {
			throw new OutstockException(
					OutstockExceptionType.OUTSTOCK_CODE_NULL);
		}
		if (!t.getApproveStatus().equals(tEntity.getApproveStatus())) {
			throw new OutstockException(
					OutstockExceptionType.OUTSTOCK_CODE_REPEAT);
		}
		return true;
	}
	/**
	 * 页面初始化通过库管员name取得库管员id
	 */
	@Override
	public int getDutyId(Map<String, Object> params) {
		List<Condition> condition_duty = OrmUtil.changeMapToCondition(params);
		condition_duty.add(new Condition("C_NAME",FieldTypeEnum.STRING,MatchTypeEnum.EQ,"库管员"));
		List<SysDutiesEntity> list_duty = sysDutiesService.findByCondition(condition_duty, null);
		int dutyId = 0;
		if(null!=list_duty && !list_duty.isEmpty()){
			for(SysDutiesEntity sysDuties : list_duty){
				dutyId = Integer.parseInt(sysDuties.getId().toString());
			}
		}
		return dutyId;
	}
	/**
	 * 根据职务id检索得到该职务下的用户id
	 */
	@Override
	public List<Long> getDutyDetailId(Map<String, Object> params) {
		List<Long> lsit_dutyDetailId = new ArrayList<Long>();
		List<Condition> condition_dutyDetail = OrmUtil.changeMapToCondition(params);
		condition_dutyDetail.add(new Condition("C_DUTIES_ID",FieldTypeEnum.STRING,MatchTypeEnum.EQ,this.getDutyId(params)));
		List<SysDutiesDetailEntity> list_dutyDetailUser = sysDutiesDetailService.findByCondition(condition_dutyDetail, null);
		if(null!=list_dutyDetailUser && !list_dutyDetailUser.isEmpty()){
			for(SysDutiesDetailEntity sysDutiesDetail : list_dutyDetailUser){
				List<Condition> condition = new ArrayList<Condition>();
				Long id = Long.parseLong(sysDutiesDetail.getUserUnitRelId());
				condition.add(new Condition("C_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,id));
				List<UserUnitRelEntity> list_userUnitRel = userUnitRelService.findByCondition(condition, null);
				for(UserUnitRelEntity tempuserUnitRel:list_userUnitRel){
					lsit_dutyDetailId.add(tempuserUnitRel.getUserId());
				}
			}
		}
		return lsit_dutyDetailId;
	}
    /**
     * 系统管理员的角色id
     */
	@Override
	public int getSystemId(Map<String, Object> params) {
		List<Condition> condition_system = OrmUtil.changeMapToCondition(params);
		condition_system.add(new Condition("C_NAME",FieldTypeEnum.STRING,MatchTypeEnum.EQ,"系统管理员"));
		List<SysRoleEntity> list_role = sysRoleService.findByCondition(condition_system, null);
		int roleId = 0;
		if(null!=list_role && !list_role.isEmpty()){
			for(SysRoleEntity sysRole : list_role){
				roleId = Integer.parseInt(sysRole.getId().toString());
			}
		}
		return roleId;
	}
    /**
     * 根据角色id得到用户表中的用户id
     */
	@Override
	public List<Long> getUserId(Map<String, Object> params) {
		List<Long> list_userId = new ArrayList<Long>();
		List<SysUserEntity> userEntities = sysUserService.findAll();
		if(null != userEntities && userEntities.size()!=0){
			for(SysUserEntity user : userEntities){
				String userRoleString = user.getRoleIds();
				if(userRoleString.startsWith(",")){
					userRoleString.substring(1, userRoleString.length());
				}
				if(userRoleString.contains(",")){
					String[] userRoleIdArray = userRoleString.split(",");
					if(userRoleIdArray.length!=0){
						for(String userRole:userRoleIdArray){
							int roleId = this.getSystemId(params);
							if(userRole.equals(String.valueOf(roleId))){
								list_userId.add(user.getId());
							}
						}
					}
				}else{
					if(userRoleString.equals(String.valueOf(this.getSystemId(params)))){
						list_userId.add(user.getId());
					}
				}
			}
		}
		return list_userId;
	}
	/**
	 * 出库数量和库存数量比较
	 */
	@Override
	public Boolean compareKc(String materialId,String materialCount){
		boolean result = false;
		if(materialId.contains(",")){
			//多条物资
			String[] materialIdArray = materialId.split(",");
			String[] materialCountArray = materialCount.replaceAll("[\\[\\]]", "").replaceAll(" ", "").split(",");
			for(int i=0;i<materialIdArray.length;i++){
				List<Condition> conditions = new ArrayList<Condition>();
				conditions.add(new Condition("materialId",FieldTypeEnum.LONG,MatchTypeEnum.EQ,materialIdArray[i]));
				List<StockEntity> list_stockEntity = stockService.findByCondition(conditions, null);
				StockEntity stockEntity = list_stockEntity.get(0);
				//仓库中库存数量
				Double kcsl = Double.parseDouble(stockEntity.getInventoryQuantity());
				//对同一场站除了审核通过和取消流程的正在出库相同物资数量进行求和
				//去除出库审核为“审核通过”和“取消流程”状态数据
				String notInStatus = "5,6";
				conditions.add(new Condition("c.C_APPROVE_STATUS",FieldTypeEnum.STRING,MatchTypeEnum.NOT_IN,notInStatus.split(",")));
				//TODO
				List<OutstockDetailEntity> list_outStockDetail = outstockDetailService.findByCondition(conditions, null);
				Double ckOtherCount = 0.0;
				for(OutstockDetailEntity otd : list_outStockDetail){
					//出库详细表里物资数量
					Double otdCount = Double.parseDouble(otd.getCount());
					ckOtherCount = ckOtherCount+otdCount;
				}
				
				//页面用户填写出库数据
				Double cksl = Double.parseDouble(materialCountArray[i]);
				Double totalCount = ckOtherCount+cksl;
				if(kcsl>=totalCount){
					result = true;
				}else{
					result = false;
					break;
				}
			}
		}else{
			//单条物资
			List<Condition> conditions = new ArrayList<Condition>();
			conditions.add(new Condition("materialId",FieldTypeEnum.LONG,MatchTypeEnum.EQ,materialId));
			List<StockEntity> list_stockEntity = stockService.findByCondition(conditions, null);
			Double kcsl = Double.parseDouble(list_stockEntity.get(0).getInventoryQuantity());
			Double cksl = Double.parseDouble(materialCount);
			if(kcsl>=cksl){
				result = true;
			}
		}
		return result;
	}
}