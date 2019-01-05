package com.aptech.business.cargo.inStock.service;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.cargo.inOutStock.domain.InOutStockEntity;
import com.aptech.business.cargo.inOutStock.service.InOutStockService;
import com.aptech.business.cargo.inStock.dao.InstockDao;
import com.aptech.business.cargo.inStock.domain.InstockEntity;
import com.aptech.business.cargo.inStock.exception.InstockException;
import com.aptech.business.cargo.inStock.exception.InstockExceptionType;
import com.aptech.business.cargo.inStockDetail.domain.InstockDetailEntity;
import com.aptech.business.cargo.inStockDetail.service.InstockDetailService;
import com.aptech.business.cargo.materialCategory.domain.MaterialCategoryEntity;
import com.aptech.business.cargo.materialCategory.service.MaterialCategoryService;
import com.aptech.business.cargo.stock.domain.StockEntity;
import com.aptech.business.cargo.stock.service.StockService;
import com.aptech.business.cargo.stockStatictisPrint.domain.StockStatictisPrintEntity;
import com.aptech.business.cargo.stockStatictisPrint.service.StockStatictisPrintService;
import com.aptech.business.component.dictionary.InstockApproveStatusEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.wareHouse.wareHouse.domain.WareHouseEntity;
import com.aptech.business.wareHouse.wareHouse.service.WareHouseService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.dictionary.domain.SysDictionaryEntity;
import com.aptech.common.system.dictionary.service.SysDictionaryService;
import com.aptech.common.system.duties.domain.SysDutiesEntity;
import com.aptech.common.system.duties.service.SysDutiesService;
import com.aptech.common.system.dutiesdetail.domain.SysDutiesDetailEntity;
import com.aptech.common.system.dutiesdetail.service.SysDutiesDetailService;
import com.aptech.common.system.role.domain.SysRoleEntity;
import com.aptech.common.system.role.service.SysRoleService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
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
 * 入库管理应用管理服务实现类
 *
 * @author 
 * @created 2017-07-19 11:32:25
 * @lastModified 
 * @history
 *
 */
@Service("instockService")
@Transactional
public class InstockServiceImpl extends AbstractBaseEntityOperation<InstockEntity> implements InstockService {
	
	@Autowired
	private InstockDao instockDao;
	
	@Autowired
	private ActTaskService actTaskService;
	
	@Autowired
	private StockService stockService;
	
	@Autowired
	private MaterialCategoryService materialCategoryService;
	
	@Autowired
	private OperateLogService operateLogService;
	
	@Autowired
	private InstockDetailService instockDetailService;
	
	@Autowired
	private InOutStockService inOutStockService;
	
	@Autowired
	private SysDutiesService sysDutiesService;
	
	@Autowired
	private SysDutiesDetailService sysDutiesDetailService;
	
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private WareHouseService wareHouseService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private UserUnitRelService userUnitRelService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private StockStatictisPrintService stockStatictisPrintService;
	@Override
	public IBaseEntityOperation<InstockEntity> getDao() {
		return instockDao;
	}
	DateFormatUtil sdf = DateFormatUtil.getInstance("yyyy-MM-dd");//日期转化
	/**
	 * 
	 * 生成入库单号
	 * 
	 * @param @param prefix
	 * @param @param unitId
	 * @param @param dateFmtString
	 * @param @param sequenLength
	 * @param @return
	 * @return String
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月3日 下午2:07:20
	 * @lastModified
	 */
	public String generateCode(String prefix,String userUnitId,int sequenLength){
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
	 * 根据日期生成序列号
	 * 
	 * @param @param prefixCode
	 * @param @param sequenLength
	 * @param @return
	 * @return String
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月3日 下午3:04:09
	 * @lastModified
	 */
	private String generateSequenceNum(String prefixCode, int sequenLength){
		String sequenceNum = "";
		//用条件模糊查询出当日最大入库单号
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("instockNum", FieldTypeEnum.STRING, MatchTypeEnum.LIKE,prefixCode));
		List<Object> List_instockNum = this.findByCondition("findMaxCode", conditions, null);
		if(null != List_instockNum && !List_instockNum.isEmpty()){
			InstockEntity instockEntity = (InstockEntity)List_instockNum.get(0);
			//取当日最大入库单号
			String maxInstockNum = instockEntity.getInstockNum();
			if(StringUtil.isNotEmpty(maxInstockNum)){
				//按序列号位数取入库单号序列号
				String tempNumString = maxInstockNum.substring(maxInstockNum.length()-sequenLength, maxInstockNum.length());
				//序列号增加1
				Long instockNumLong = Long.parseLong(tempNumString)+1;
				sequenceNum = String.format("%0" + sequenLength + "d", instockNumLong);
			}else{
				//当日没有入库单，从1开始，用0补齐
				sequenceNum = String.format("%0" + sequenLength + "d", 1);
				
			}
		}
		return sequenceNum;
	}
	
	/**
	 * 添加页面有多条物资明细
	 */
	public void addPageSave(String goodsAreaId,String goodsAllocationId,String materialIdString,String materialCountStringTemp,Date instockdate,String instockNum,String goodsPrice,String goodsValidity,String goodsAttribute,Long wareHouseId,Long unitId,InstockEntity instockEntity,InstockDetailEntity instockDetailEntity){
		//有多条物资明细
		String materialCountString = materialCountStringTemp.substring(1,materialCountStringTemp.length()-1);
		String[] materialArray = materialIdString.split(",");
		String[] materialCountArray = materialCountString.split(",");
		String goodsAreaIdString = goodsAreaId.substring(1,goodsAreaId.length()-1);
		String[] goodsAreaIdArray = goodsAreaIdString.split(",");
		String goodsAllocationIdString = goodsAllocationId.substring(1,goodsAllocationId.length()-1);
		String[] goodsAllocationIdArray = goodsAllocationIdString.split(",");  
		String goodsPriceString = goodsPrice.substring(1,goodsPrice.length()-1);
		String[] goodsPriceArray = goodsPriceString.split(",");
		String goodsValidityString = goodsValidity.substring(1,goodsValidity.length()-1);
		String[] goodsValidityArray = goodsValidityString.split(",");
		String goodsAttributeString = goodsAttribute.substring(1,goodsAttribute.length()-1);
		String[] goodsAttributeArray = goodsAttributeString.split(",");
		int num = 1;
		List<Double> list = new ArrayList<Double>();
		for(int i=0;i<materialArray.length;i++){
			//根据物资管理方式判断数量是整数或是小数
			MaterialCategoryEntity materialCategoryEntity = materialCategoryService.findById(Long.valueOf(materialArray[i].trim()));
			String materialManageMethod = materialCategoryEntity.getManagement();
			String materialCount = "0";
			//如果物资的管理方式为实例（1）时，数量为整数
			Double mCountDouble = Double.parseDouble(materialCountArray[i].trim());
			if(StringUtil.isNotEmpty(materialManageMethod) && materialManageMethod.equals("1")){
				int mCountInteger = (int) Math.floor(mCountDouble);
				materialCount = String.valueOf(mCountInteger);
				//如果物资的管理方式为记账（2）时，数量为小数
			}else if(StringUtil.isNotEmpty(materialManageMethod) && materialManageMethod.equals("2")){
				java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
				materialCount = String.valueOf(df.format(mCountDouble));
			}
			//增加入库明细
			instockDetailEntity.setInstockId(instockEntity.getId());
			instockDetailEntity.setMaterialId(Long.valueOf(materialArray[i].trim()));
			instockDetailEntity.setCount(materialCount);
			instockDetailEntity.setGoodsAreaId(goodsAreaIdArray[i].trim());
			instockDetailEntity.setGoodsAllocationId(goodsAllocationIdArray[i].trim());
			instockDetailEntity.setGoodsAttribute(goodsAttributeArray[i].trim());//新增物资属性 add by zhangxb
			instockDetailEntity.setGoodsPrice(goodsPriceArray[i].trim());//新增价格 add by zhangxb
			String goodsPriceStr = goodsPriceArray[i];
			int goodsPrices = Integer.parseInt(goodsPriceStr.trim());
			double materialCounts = Double.valueOf(materialCount);
			instockDetailEntity.setTotalPrice(String.valueOf(goodsPrices*materialCounts));
			list.add(goodsPrices*materialCounts);
			instockDetailEntity.setNumber(num++);
			//转换有效期为日期格式
			Date goodsValidityDate;
			try {
				goodsValidityDate = sdf.parse(goodsValidityArray[i].trim());
				instockDetailEntity.setGoodsValidity(goodsValidityDate);//新增有效期 add by zhangxb
			} catch (ParseException e) {
				e.printStackTrace();
			}
			instockDetailService.addEntity(instockDetailEntity);
			//修改物资引用字段
			materialCategoryEntity.setQuote("0");
			materialCategoryService.updateEntity(materialCategoryEntity);
			//增加出入库明细
			/*InOutStockEntity inOutStockEntity = new InOutStockEntity();
			inOutStockEntity.setCode(instockNum);
			inOutStockEntity.setUnitId(unitId);
			inOutStockEntity.setTime(instockdate);
			inOutStockEntity.setType("1");
			inOutStockEntity.setMaterialId(Long.valueOf(materialArray[i].trim()));
			inOutStockEntity.setCount(materialCount);
			inOutStockEntity.setWareHouseId(wareHouseId);
			inOutStockService.addEntity(inOutStockEntity);*/
		}
		for(int i = 0;i<list.size();i++){
			instockEntity.setGoodsTotalPrice(instockEntity.getGoodsTotalPrice()+list.get(i));
		}
		instockDao.updateEntity(instockEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.INSTOCKMOVE.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
	}
	
	/**
	 * 添加页面有一条物资明细
	 */
	public void addPageSaveSingle(String goodsAreaId,String goodsAllocationId,String materialIdString,String materialCountStringTemp,String instockNum,String goodsPrice,String goodsValidity,String goodsAttribute,Long wareHouseId,Date instockdate,Long unitId,InstockEntity instockEntity,InstockDetailEntity instockDetailEntity){
		MaterialCategoryEntity materialCategoryEntity = materialCategoryService.findById(Long.valueOf(materialIdString));
		String materialManageMethod = materialCategoryEntity.getManagement();
		String materialCount = "0";
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
		instockDetailEntity.setInstockId(instockEntity.getId());
		instockDetailEntity.setMaterialId(Long.valueOf(materialIdString));
		instockDetailEntity.setCount(materialCount);
		instockDetailEntity.setGoodsAreaId(goodsAreaId);
		instockDetailEntity.setGoodsAllocationId(goodsAllocationId);
		instockDetailEntity.setGoodsPrice(goodsPrice);//物资价格 add by zhangxb
		double goodsPriceD = Double.parseDouble(goodsPrice); 
		double materialCountD = Double.parseDouble(materialCount);
		instockDetailEntity.setTotalPrice(String.valueOf(goodsPriceD*materialCountD));
		instockDetailEntity.setNumber(1);
		instockEntity.setGoodsTotalPrice(goodsPriceD*materialCountD);
		instockDao.updateEntity(instockEntity);
		//转换有效期为日期格式
		Date goodsValidityDate;
		try {
			goodsValidityDate = sdf.parse(goodsValidity);
			instockDetailEntity.setGoodsValidity(goodsValidityDate);//新增有效期 add by zhangxb
		} catch (ParseException e) {
			e.printStackTrace();
		}
		instockDetailEntity.setGoodsAttribute(goodsAttribute);//物资属性  add by zhangxb
		instockDetailService.addEntity(instockDetailEntity);
		//物资修改引用字段
		materialCategoryEntity.setQuote("0");
		materialCategoryService.updateEntity(materialCategoryEntity);
		//增加出入库明细
		/*InOutStockEntity inOutStockEntity = new InOutStockEntity();
		inOutStockEntity.setCode(instockNum);
		inOutStockEntity.setUnitId(unitId);
		inOutStockEntity.setTime(instockdate);
		inOutStockEntity.setType("1");
		inOutStockEntity.setMaterialId(Long.valueOf(materialIdString));
		inOutStockEntity.setCount(materialCount);
		inOutStockEntity.setWareHouseId(wareHouseId);
		inOutStockService.addEntity(inOutStockEntity);*/
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.INSTOCKMOVE.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
	}
	
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
	 * @created 2017年8月4日 上午11:23:57
	 * @lastModified
	 */
	public void editPageSave(String goodsAreaId,String goodsAllocationId,Long instockId,List<String> materialIdList,String materialIdStringTemp,String materialCountStringTemp,String instockNumString,String goodsPrice,String goodsValidity,String goodsAttribute,Long wareHouseId,Date instockDate,SysUnitEntity sysUnitEntity){
		//根据入库单选择入库明细数据，先删除后添加
		InstockDetailEntity instockDetailEntity = new InstockDetailEntity();
		instockDetailEntity.setInstockId(instockId);
		instockDetailService.deleteByCondition("deleteByInstockId", instockDetailEntity);
		//根据出入库单号删除出入库明细
		InOutStockEntity inOutStock = new InOutStockEntity();
		inOutStock.setCode(instockNumString);
		inOutStockService.deleteByCondition("deleteByStockCode", inOutStock);
		int num = 1;
		List<Double> list = new ArrayList<Double>();
		for(int i=0;i<materialIdList.size();i++){
			String materialCountString = materialCountStringTemp.substring(1,materialCountStringTemp.length()-1);
			String materialIdString = materialIdStringTemp.substring(1,materialIdStringTemp.length()-1);
			String[] materialCountArray = materialCountString.split(",");
			String[] materialIdArray = materialIdString.split(",");
			String goodsAreaIdString = goodsAreaId.substring(1,goodsAreaId.length()-1);
			String[] goodsAreaIdArray = goodsAreaIdString.split(",");
			String goodsAllocationIdString = goodsAllocationId.substring(1,goodsAllocationId.length()-1);
			String[] goodsAllocationIdArray = goodsAllocationIdString.split(",");
			String goodsPriceString = goodsPrice.substring(1,goodsPrice.length()-1);
			String[] goodsPriceArray = goodsPriceString.split(",");
			String goodsValidityString = goodsValidity.substring(1,goodsValidity.length()-1);
			String[] goodsValidityArray = goodsValidityString.split(",");
			String goodsAttributeString = goodsAttribute.substring(1,goodsAttribute.length()-1);
			String[] goodsAttributeArray = goodsAttributeString.split(",");
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
			}else if(StringUtil.isNotEmpty(materialManageMethod) && materialManageMethod.equals("2")){
				java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
				materialCount = String.valueOf(df.format(mCountDouble));
			}
			
			instockDetailEntity.setMaterialId(Long.valueOf(materialIdArray[i].trim()));
			instockDetailEntity.setCount(materialCount);
			instockDetailEntity.setGoodsAreaId(goodsAreaIdArray[i].trim());
			instockDetailEntity.setGoodsAllocationId(goodsAllocationIdArray[i].trim());
			instockDetailEntity.setGoodsAttribute(goodsAttributeArray[i].trim());//新增物资属性 add by zhangxb
			instockDetailEntity.setGoodsPrice(goodsPriceArray[i].trim());//新增价格 add by zhangxb
			String goodsPriceStr = goodsPriceArray[i];
			int goodsPrices = Integer.parseInt(goodsPriceStr.trim());
			double materialCounts = Double.valueOf(materialCount);
			instockDetailEntity.setTotalPrice(String.valueOf(goodsPrices*materialCounts));
			list.add(goodsPrices*materialCounts);
			instockDetailEntity.setNumber(num++);
			//转换有效期为日期格式
			Date goodsValidityDate;
			try {
				goodsValidityDate = sdf.parse(goodsValidityArray[i].trim());
				instockDetailEntity.setGoodsValidity(goodsValidityDate);//新增有效期 add by zhangxb
			} catch (ParseException e) {
				e.printStackTrace();
			}
			instockDetailService.addEntity(instockDetailEntity);
			materialCategoryEntity.setQuote("0");
			materialCategoryService.updateEntity(materialCategoryEntity);
			//增加出入库明细
			/*InOutStockEntity inOutStockEntity = new InOutStockEntity();
			inOutStockEntity.setCode(instockNumString);
			inOutStockEntity.setUnitId(sysUnitEntity.getId());
			inOutStockEntity.setTime(instockDate);
			inOutStockEntity.setType("1");
			inOutStockEntity.setMaterialId(Long.valueOf(materialIdArray[i].trim()));
			inOutStockEntity.setCount(materialCount);
			inOutStockEntity.setWareHouseId(wareHouseId);
			inOutStockService.addEntity(inOutStockEntity);*/
		}
		InstockEntity instockEntity = instockDao.findById(Long.valueOf(instockDetailEntity.getInstockId()));
		for(int i = 0;i<list.size();i++){
			instockEntity.setGoodsTotalPrice(instockEntity.getGoodsTotalPrice()+list.get(i));
		}
		instockDao.updateEntity(instockEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.INSTOCKMOVE.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
	}
	
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
	 * @created 2017年8月4日 上午11:28:28
	 * @lastModified
	 */
	public void editPageSaveSingle(String goodsAreaId,String goodsAllocationId,Long instockId,String materialIdStringTemp,String materialCountStringTemp,String instockNumString,String goodsPrice,String goodsValidity,String goodsAttribute,Long wareHouseId,SysUnitEntity sysUnitEntity,Date instockDate){
		//根据入库单选择入库明细数据，先删除后添加
		InstockDetailEntity instockDetailEntity = new InstockDetailEntity();
		instockDetailEntity.setInstockId(instockId);
		instockDetailService.deleteByCondition("deleteByInstockId", instockDetailEntity);
		//根据出入库单号删除出入库明细
		InOutStockEntity inOutStock = new InOutStockEntity();
		inOutStock.setCode(instockNumString);
		inOutStockService.deleteByCondition("deleteByStockCode", inOutStock);
		String materialIdString = materialIdStringTemp.substring(1,materialIdStringTemp.length()-1);
		MaterialCategoryEntity materialCategoryEntity = materialCategoryService.findById(Long.valueOf(materialIdString));
		
		String materialManageMethod = materialCategoryEntity.getManagement();
		String materialCount = "0";
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
		
		instockDetailEntity.setMaterialId(Long.valueOf(materialIdString));
		instockDetailEntity.setCount(materialCount);
		instockDetailEntity.setGoodsAreaId(goodsAreaId);
		instockDetailEntity.setGoodsAllocationId(goodsAllocationId);
		instockDetailEntity.setGoodsPrice(goodsPrice);//物资价格 add by zhangxb
		instockDetailEntity.setTotalPrice(goodsPrice);
		instockDetailEntity.setNumber(1);
		InstockEntity instockEntity = instockDao.findById(instockDetailEntity.getInstockId());
		instockEntity.setGoodsTotalPrice(Double.valueOf(goodsPrice));
		instockDao.updateEntity(instockEntity);
		//转换有效期为日期格式
		Date goodsValidityDate;
		try {
			goodsValidityDate = sdf.parse(goodsValidity);
			instockDetailEntity.setGoodsValidity(goodsValidityDate);//新增有效期 add by zhangxb
		} catch (ParseException e) {
			e.printStackTrace();
		}
		instockDetailEntity.setGoodsAttribute(goodsAttribute);//物资属性  add by zhangxb
		instockDetailService.addEntity(instockDetailEntity);
		materialCategoryEntity.setQuote("0");
		materialCategoryService.updateEntity(materialCategoryEntity);
		//增加出入库明细
		/*InOutStockEntity inOutStockEntity = new InOutStockEntity();
		inOutStockEntity.setCode(instockNumString);
		inOutStockEntity.setUnitId(sysUnitEntity.getId());
		inOutStockEntity.setTime(instockDate);
		inOutStockEntity.setType("1");
		inOutStockEntity.setMaterialId(Long.valueOf(materialIdString));
		inOutStockEntity.setCount(materialCount);
		inOutStockEntity.setWareHouseId(wareHouseId);
		inOutStockService.addEntity(inOutStockEntity);*/
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.INSTOCKMOVE.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
	}
	
	/**
	 * 提交流程，开始流程
	 */
	@Override
	public ResultObj submit(Serializable id, Map<String, Object> params) {
		ResultObj resultObj = new ResultObj();
		InstockEntity t = this.findById(id);
		if(validate(t)){
			t.setApproveStatus(InstockApproveStatusEnum.STOREKEEPER.getCode());
			this.updateEntity(t);
			// 准备启动流程
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),params.get("userList"));
			actTaskService.startProcess(ProcessMarkEnum.IN_STOCK_PROCESS_KEY.getName(), id.toString(), vars);
		}
		return resultObj;
	}
	
	/**
	 * 审核后继续下一步流程
	 */
	@Override
	public ResultObj approve(InstockEntity t,Map<String, Object> params) {
		if(validateStatus(t)){
			// 修改状态
			InstockEntity instockEntity = this.findById(t.getId());
			instockEntity.setApproveStatus(params.get("status").toString());
			this.updateEntity(instockEntity);
			//设置当前流程,当前任务节点的下一个节点的办理人
			actTaskService.setVarialble(t.getTaskId(),params);
			// 调用流程接口
			params.put(ExamMarkEnum.RESULT.getCode(), params.get("result").toString());
			actTaskService.complete(t.getTaskId(), t.getProcInstId(),params);
			//入库审批完成后，修改库存物资数量
			if(null!=params && params.get("status").toString().equals("4")){
				List<Condition> conditions_detail=new ArrayList<Condition>();
				conditions_detail.add(new Condition("a.C_INSTOCK_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, instockEntity.getId()));
				List<InstockDetailEntity> instockDetailEntities = instockDetailService.findByCondition(conditions_detail, null);

				InOutStockEntity inOutStockEntity=null;
				for(InstockDetailEntity instockDetailEntity : instockDetailEntities){
					
					//增加出入库明细2018.4.26 start
					inOutStockEntity = new InOutStockEntity();
					inOutStockEntity.setCode(instockEntity.getInstockNum());
					inOutStockEntity.setUnitId(instockEntity.getUnitId());
					inOutStockEntity.setTime(instockEntity.getInstockTime());
					inOutStockEntity.setType("1");
					inOutStockEntity.setMaterialId(instockDetailEntity.getMaterialId());
					inOutStockEntity.setCount(instockDetailEntity.getCount().toString());
					inOutStockEntity.setWareHouseId(instockEntity.getWareHouseId());
					inOutStockService.addEntity(inOutStockEntity);
					//增加出入库明细2018.4.26 end
					
					Long unitId = instockEntity.getUnitId();
					List<Condition> conditions=new ArrayList<Condition>();
					conditions.add(new Condition("sk.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, unitId));
					conditions.add(new Condition("sk.C_MATERIAL_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, instockDetailEntity.getMaterialId()));
					conditions.add(new Condition("sk.C_GOODS_PRICE", FieldTypeEnum.LONG, MatchTypeEnum.EQ, instockDetailEntity.getGoodsPrice()));
					conditions.add(new Condition("sk.C_GOODS_VALIDITY", FieldTypeEnum.LONG, MatchTypeEnum.EQ, instockDetailEntity.getGoodsValidity()));
					List<StockEntity> list = stockService.findByCondition(conditions, null);
					String materialCount = instockDetailEntity.getCount();
					if(null != list && !list.isEmpty()){
						for(StockEntity stockEntity : list){
							stockEntity.setGoodsAttribute(instockDetailEntity.getGoodsAttribute());
							String kcslString = stockEntity.getInventoryQuantity();
							double kcsl = Double.parseDouble(kcslString)+Double.parseDouble(materialCount);
							MaterialCategoryEntity materialEntity = materialCategoryService.findById(stockEntity.getMaterialId());
							if(materialEntity.getManagement().equals("1")){
								stockEntity.setInventoryQuantity(String.valueOf((int)kcsl));
							}else{
								java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
								stockEntity.setInventoryQuantity(String.valueOf(df.format(kcsl)));
							}
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
							stockEntity.setWareHouseId(instockEntity.getWareHouseId());
							stockService.updateEntity(stockEntity);
						}
					}else{
						StockEntity stockEntity = new StockEntity();
						stockEntity.setMaterialId(instockDetailEntity.getMaterialId());
						stockEntity.setUnitId(instockEntity.getUnitId());
						stockEntity.setGoodsPrice(instockDetailEntity.getGoodsPrice());
						stockEntity.setGoodsValidity(instockDetailEntity.getGoodsValidity());
						stockEntity.setGoodsAttribute(instockDetailEntity.getGoodsAttribute());
						stockEntity.setType("2");
						MaterialCategoryEntity materialEntity = materialCategoryService.findById(stockEntity.getMaterialId());
						if(materialEntity.getManagement().equals("1")){
							stockEntity.setInventoryQuantity(Integer.valueOf(materialCount).toString());
						}else{
							java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
							Double materialCountDouble = Double.parseDouble(materialCount);
							stockEntity.setInventoryQuantity(String.valueOf(df.format(materialCountDouble)));
						}
						stockEntity.setWareHouseId(instockEntity.getWareHouseId());
						stockEntity.setIsShortage("0");
						stockService.addEntity(stockEntity);
					}
				}
				WareHouseEntity wareHouseEntity = wareHouseService.findById(Long.valueOf(instockEntity.getWareHouseId()));
				List<Condition> conditions = new ArrayList<Condition>();
				conditions.add(new Condition("a.C_INSTOCK_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, instockEntity.getId()));
				List<InstockDetailEntity> instockDetailList = instockDetailService.findByCondition(conditions, null);
				conditions.clear();
				conditions.add(new Condition("C_MATERIAL_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, instockDetailList.get(0).getMaterialId()));
				conditions.add(new Condition("C_WAREHOUSE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, wareHouseEntity.getWareHouseName()));
				conditions.add(new Condition("C_TIME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, instockEntity.getInstockTime()));
				conditions.add(new Condition("C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
				List<StockStatictisPrintEntity> stockStatictisPrintList = stockStatictisPrintService.findByCondition(conditions, null);
				if(stockStatictisPrintList!=null&&!stockStatictisPrintList.isEmpty()){
					StockStatictisPrintEntity stockStatictisPrintEntity = stockStatictisPrintList.get(0);
					double countd = Double.parseDouble(instockDetailList.get(0).getCount());
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
						stockStatictisPrintEntity.setMonthInstockNum(stockStatictisPrintEntity.getMonthInstockNum()+count);
					}else if(result>0){
						stockStatictisPrintEntity.setLastMonthStock(stockStatictisPrintEntity.getLastMonthStock()+count);
					}
					
					stockStatictisPrintEntity.setMonthStock(stockStatictisPrintEntity.getLastMonthStock()+stockStatictisPrintEntity.getMonthInstockNum()-stockStatictisPrintEntity.getMonthOutstockNum());
					stockStatictisPrintService.updateEntity(stockStatictisPrintEntity);
				}else{
					StockStatictisPrintEntity stockStatictisPrintEntity = new StockStatictisPrintEntity();
					InstockDetailEntity instockDetailEntity = instockDetailList.get(0);
					stockStatictisPrintEntity.setMaterialId(instockDetailEntity.getMaterialId().toString());
					MaterialCategoryEntity materialCategoryEntity = materialCategoryService.findById(instockDetailEntity.getMaterialId());
					stockStatictisPrintEntity.setMaterialName(materialCategoryEntity.getName());
					stockStatictisPrintEntity.setMaterialType(materialCategoryEntity.getModel());
					conditions.clear();
					conditions.add(new Condition("C_CATEGORY_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "DIGIT"));
					conditions.add(new Condition("C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, materialCategoryEntity.getUnit()));
					List<SysDictionaryEntity> sysDictionaryList = sysDictionaryService.findByCondition(conditions, null);
					stockStatictisPrintEntity.setTechnicalUnit(sysDictionaryList.get(0).getName());
					stockStatictisPrintEntity.setTime(instockEntity.getInstockTime());
					stockStatictisPrintEntity.setType("1");
					stockStatictisPrintEntity.setUnitId(instockEntity.getUnitId().toString());
					SysUnitEntity sysUnitEntity = sysUnitService.findById(Long.valueOf(stockStatictisPrintEntity.getUnitId()));
					stockStatictisPrintEntity.setUnitName(sysUnitEntity.getName());
					stockStatictisPrintEntity.setWarehouse(wareHouseEntity.getWareHouseName());
					stockStatictisPrintEntity.setStockCode(materialCategoryEntity.getCode());
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
					double countd = Double.parseDouble(instockDetailEntity.getCount());
					int count = (int) countd;
					if(result==0){
						stockStatictisPrintEntity.setMonthInstockNum(count);
						stockStatictisPrintEntity.setLastMonthStock(0);
						stockStatictisPrintEntity.setMonthOutstockNum(0);
						stockStatictisPrintEntity.setMonthStock(stockStatictisPrintEntity.getLastMonthStock()+count-stockStatictisPrintEntity.getMonthOutstockNum());
					}else if(result>0){
						stockStatictisPrintEntity.setLastMonthStock(count);
						stockStatictisPrintEntity.setMonthInstockNum(0);
						stockStatictisPrintEntity.setMonthOutstockNum(0);
						stockStatictisPrintEntity.setMonthStock(count+stockStatictisPrintEntity.getMonthInstockNum()-stockStatictisPrintEntity.getMonthOutstockNum());
					}
					stockStatictisPrintService.addEntity(stockStatictisPrintEntity);
				}
			}
		}
		return  new ResultObj();
	}
	
	/**
	 *  基本验证
	 * 
	 * @param @param t
	 * @param @return
	 * @return boolean
	 * @throws 
	 * @author liweiran
	 * @created 2017年7月31日 下午1:23:42
	 * @lastModified
	 */
	public boolean validate(InstockEntity t) {
		if (t == null) {
			throw new InstockException(InstockExceptionType.INSTOCK_CODE_NULL);
		}
		if (!(t.getApproveStatus().equals(InstockApproveStatusEnum.PENDING.getCode()) || t.getApproveStatus().equals(InstockApproveStatusEnum.REJECT.getCode()))) {
			throw new InstockException(InstockExceptionType.INSTOCK_CODE_STATUS);
		}
		return true;
	}
	/**
	 * 流程基本验证
	 * 
	 * @param @param t
	 * @param @return
	 * @return boolean
	 * @throws 
	 * @author liweiran
	 * @created 2017年7月31日 下午1:23:52
	 * @lastModified
	 */
	public boolean validateStatus(InstockEntity t) {
		InstockEntity tEntity = this.findById(t.getId());
		if (tEntity == null) {
			throw new InstockException(
					InstockExceptionType.INSTOCK_CODE_NULL);
		}
		if (!t.getApproveStatus().equals(tEntity.getApproveStatus())) {
			throw new InstockException(
					InstockExceptionType.INSTOCK_CODE_REPEAT);
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
				Long userUnitid = Long.parseLong(sysDutiesDetail.getUserUnitRelId());
				condition.add(new Condition("C_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userUnitid));
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
		condition_system.add(new Condition("C_CODE",FieldTypeEnum.STRING,MatchTypeEnum.EQ,"admin"));
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

}