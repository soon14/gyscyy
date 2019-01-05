package com.aptech.business.cargo.scarpLibrary.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
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
import com.aptech.business.cargo.outStockDetail.domain.OutstockDetailEntity;
import com.aptech.business.cargo.scarpLibrary.dao.ScrapLibraryDao;
import com.aptech.business.cargo.scarpLibrary.domain.ScrapLibraryEntity;
import com.aptech.business.cargo.scarpLibrary.exception.ScrapException;
import com.aptech.business.cargo.scarpLibrary.exception.ScrapExceptionType;
import com.aptech.business.cargo.scrapLibraryDetail.domain.ScrapLibraryDetailEntity;
import com.aptech.business.cargo.scrapLibraryDetail.service.ScrapLibraryDetailService;
import com.aptech.business.cargo.scrapLibraryDetailOut.domain.ScrapLibraryDetailOutEntity;
import com.aptech.business.cargo.scrapLibraryDetailOut.service.ScrapLibraryDetailOutService;
import com.aptech.business.cargo.stock.domain.StockEntity;
import com.aptech.business.cargo.stock.service.StockService;
import com.aptech.business.cargo.stockStatictisPrint.domain.StockStatictisPrintEntity;
import com.aptech.business.cargo.stockStatictisPrint.service.StockStatictisPrintService;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.ScrapLibraryHandleStatusEnum;
import com.aptech.business.component.dictionary.ScrapstockApproveStatusEnum;
import com.aptech.business.component.dictionary.ScrapstockOutExcuteEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.wareHouse.wareHouse.domain.WareHouseEntity;
import com.aptech.business.wareHouse.wareHouse.service.WareHouseService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.fourcode.service.FourCodeService;
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
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.StringUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 报废库管理应用管理服务实现类
 *
 * @author 
 * @created 2018-03-15 15:37:32
 * @lastModified 
 * @history
 *
 */
@Service("scrapLibraryService")
@Transactional
public class ScrapLibraryServiceImpl extends AbstractBaseEntityOperation<ScrapLibraryEntity> implements ScrapLibraryService {
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private ScrapLibraryDao scrapLibraryDao;
	@Autowired
	private MaterialCategoryService materialCategoryService;
	@Autowired
	private ScrapLibraryDetailService scrapLibraryDetailService;
	@Autowired
	private StockService stockService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysDutiesService sysDutiesService;
	@Autowired
	private SysDutiesDetailService sysDutiesDetailService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private FourCodeService fourCodeService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private InOutStockService inOutStockService;
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private UserUnitRelService userUnitRelService;
	@Autowired
	private WareHouseService wareHouseService;
	@Autowired
	private ScrapLibraryDetailOutService scrapLibraryDetailOutService;
	@Autowired
	private StockStatictisPrintService stockStatictisPrintService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Override
	public IBaseEntityOperation<ScrapLibraryEntity> getDao() {
		return scrapLibraryDao;
	}
	DateFormatUtil sdf = DateFormatUtil.getInstance("yyyy-MM-dd");//日期转化
	/**
	 * @Description: 查询
	 * @author changl
	 * @Date 2016年11月7日 下午6:00:01
	 * @throws Exception
	 */
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if (page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("instockTime"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		return findByCondition(conditions, page);
	}
	@Override
	public ResultObj add(ScrapLibraryEntity t) {
		ScrapLibraryEntity scrapLibraryEntity = new ScrapLibraryEntity();
		scrapLibraryEntity.setFileId(t.getFileId());
		scrapLibraryEntity.setInstockTime(t.getInstockTime());
		scrapLibraryEntity.setInstockType(t.getInstockType());
		scrapLibraryEntity.setProcessMode(t.getProcessMode());
		scrapLibraryEntity.setRemark(t.getRemark());
		scrapLibraryEntity.setScrapSource(t.getScrapSource());
		scrapLibraryEntity.setStationSourceId(t.getStationSourceId());
		scrapLibraryEntity.setStatus(ScrapstockApproveStatusEnum.PENDING.getCode());
		scrapLibraryEntity.setWarehouseId(t.getWarehouseId());
		scrapLibraryEntity.setType(t.getType());
		super.addEntity(scrapLibraryEntity);
		return new ResultObj();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addPageSave(/*String goodsAreaId,String goodsAllocationId,*/String materialIdString,String materialCountStringTemp,Date instockTime,Long wareHouseId,SysUnitEntity sysUnitEntity,ScrapLibraryEntity scrapLibraryEntity,ScrapLibraryDetailEntity scrapLibraryDetailEntity,Map<String,Object> detailPropertyMap) {
		//有多条物资明细
				String materialCountString = materialCountStringTemp.substring(1,materialCountStringTemp.length()-1);
				String[] materialArray = materialIdString.split(",");
				String[] materialCountArray = materialCountString.split(",");
/*				String goodsAreaIdString = goodsAreaId.substring(1,goodsAreaId.length()-1);
				String[] goodsAreaIdArray = goodsAreaIdString.split(",");
				String goodsAllocationIdString = goodsAllocationId.substring(1,goodsAllocationId.length()-1);
				String[] goodsAllocationIdArray = goodsAllocationIdString.split(",");*/
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
					scrapLibraryDetailEntity.setInstockId(scrapLibraryEntity.getId());
					scrapLibraryDetailEntity.setMetrialId(Long.valueOf(materialArray[i].trim()));
					scrapLibraryDetailEntity.setCount(materialCount);
					/*scrapLibraryDetailEntity.setGoodsAreaId(goodsAreaIdArray[i].trim());
					scrapLibraryDetailEntity.setGoodsAllocationId(goodsAllocationIdArray[i].trim());*/
					Map<String,Object> detail = (Map<String, Object>) detailPropertyMap.get(materialArray[i].trim());
					if(detail!=null && detail.get("goodsAttribute")!=null){
						scrapLibraryDetailEntity.setGoodsAttribute(detail.get("goodsAttribute").toString());
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
					    scrapLibraryDetailEntity.setGoodsValidity(date); 
					}
					if(detail!=null && detail.get("goodsPrice")!=null){
						scrapLibraryDetailEntity.setGoodsPrice(detail.get("goodsPrice").toString());
					}
					scrapLibraryDetailService.addEntity(scrapLibraryDetailEntity);
					//修改物资引用字段
					materialCategoryEntity.setQuote("3");//是否出入库（0入库1出库2删除3报废）
					materialCategoryService.updateEntity(materialCategoryEntity);
				}
	}
	/**
	 * 
	 * 验证异常
	 * 
	 * @param @param t
	 * @param @return
	 * @return boolean
	 * @throws 
	 * @author zhangxb
	 * @created 2017年8月1日 下午6:41:22
	 * @lastModified
	 */
	public boolean validate(ScrapLibraryEntity t) {
		if (t == null) {
			throw new ScrapException(ScrapExceptionType.SCRAP_CODE_NULL);
		}
		if (!(t.getStatus().equals(ScrapstockApproveStatusEnum.PENDING.getCode()) || t.getStatus().equals(ScrapstockApproveStatusEnum.REJECT.getCode()))) {
			throw new ScrapException(ScrapExceptionType.SCRAP_CODE_STATUS);
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
	 * @author zhangxb
	 * @created 2017年8月1日 下午6:42:02
	 * @lastModified
	 */
	public boolean validateStatus(ScrapLibraryEntity t) {
		ScrapLibraryEntity tEntity = this.findById(t.getId());
		if (tEntity == null) {
			throw new ScrapException(
					ScrapExceptionType.SCRAP_CODE_NULL);
		}
		if (!t.getStatus().equals(tEntity.getStatus())) {
			throw new ScrapException(
					ScrapExceptionType.SCRAP_CODE_REPEAT);
		}
		return true;
	}
	@Override
	public void addPageSaveSingle(/*String goodsAreaId, String goodsAllocationId,*/
			String materialIdString, String materialCountStringTemp,
			Long wareHouseId, Date instockTime,
			SysUnitEntity sysUnitEntity, ScrapLibraryEntity scrapLibraryEntity,ScrapLibraryDetailEntity scrapLibraryDetailEntity,Map<String,Object> detailPropertyMap) {
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
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(2);
			materialCount=nf.format(mCountDouble);
//			java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
//			materialCount = String.valueOf(df.format(mCountDouble));
		}
		//增加入库物资明细
		scrapLibraryDetailEntity.setInstockId(scrapLibraryEntity.getId());
		scrapLibraryDetailEntity.setMetrialId(Long.valueOf(materialIdString));
		scrapLibraryDetailEntity.setCount(materialCount);
/*		scrapLibraryDetailEntity.setGoodsAllocationId(goodsAllocationId);
		scrapLibraryDetailEntity.setGoodsAreaId(goodsAreaId);*/
		Map<String,Object> detail = (Map<String, Object>) detailPropertyMap.get(materialIdString);
		if(detail.get("goodsAttribute")!=null){
			scrapLibraryDetailEntity.setGoodsAttribute(detail.get("goodsAttribute").toString());
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
		    scrapLibraryDetailEntity.setGoodsValidity(date); 
		}
		if(detail.get("goodsPrice")!=null){
			scrapLibraryDetailEntity.setGoodsPrice(detail.get("goodsPrice").toString());
			double goodsPriceD = Double.parseDouble(detail.get("goodsPrice").toString());
			double materialCountD = Double.parseDouble(materialCount);
			double totalPrice = goodsPriceD * materialCountD;
			scrapLibraryDetailEntity.setTotalPrice(totalPrice);
		}
		scrapLibraryDetailService.addEntity(scrapLibraryDetailEntity);
		//物资修改引用字段
		materialCategoryEntity.setQuote("3");//是否出入库（0入库1出库2删除3报废）
		materialCategoryService.updateEntity(materialCategoryEntity);
	}

	@Override
	public void editPageSave(Long scrapId, List<String> materialIdList,
			String materialIdStringTemp, String materialCountStringTemp,
			 Long wareHouseIdDate, Date instockTime,
			SysUnitEntity sysUnitEntity,Map<String,Object> detailPropertyMap) {
		List<ScrapLibraryDetailEntity> oldList=new ArrayList<ScrapLibraryDetailEntity>();
		for(int i=0;i<materialIdList.size();i++){
			String materialIdString = materialIdStringTemp.substring(1,materialIdStringTemp.length()-1);
			String[] materialIdArray = materialIdString.split(",");
			List<Condition> conditions=new ArrayList<Condition>();
			conditions.add(new Condition("C_INSTOCK_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,scrapId ));
			conditions.add(new Condition("C_METRIAL_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,Long.valueOf(materialIdArray[i].trim()) ));
			List<ScrapLibraryDetailEntity> oldListSearch=scrapLibraryDetailService.findByCondition(conditions, null);
			ScrapLibraryDetailEntity oldEntity=new ScrapLibraryDetailEntity();
			if(!oldListSearch.isEmpty()){
				oldEntity=oldListSearch.get(0);
			}
			oldList.add(oldEntity);
		}
		//根据入库单选择入库明细数据，先删除后添加
		ScrapLibraryDetailEntity scrapLibraryDetailEntity = new ScrapLibraryDetailEntity();
		scrapLibraryDetailEntity.setInstockId(scrapId);
		scrapLibraryDetailService.deleteByCondition("deleteByInstockId", scrapLibraryDetailEntity);

				for(int i=0;i<materialIdList.size();i++){
					String materialCountString = materialCountStringTemp.substring(1,materialCountStringTemp.length()-1);
					String materialIdString = materialIdStringTemp.substring(1,materialIdStringTemp.length()-1);
					String[] materialCountArray = materialCountString.split(",");
					String[] materialIdArray = materialIdString.split(",");
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
					
					//++++++++++++++++++++++++++
					Map<String,Object> detail = (Map<String, Object>) detailPropertyMap.get(materialIdArray[i].trim());
					if(detail.get("goodsAttribute")==null){
						ScrapLibraryDetailEntity beforeDeleteEntity = oldList.get(i);//没删除之前得到的实体bean
						scrapLibraryDetailEntity.setGoodsAttribute(beforeDeleteEntity.getGoodsAttribute());
						scrapLibraryDetailEntity.setGoodsValidity(beforeDeleteEntity.getGoodsValidity());
						scrapLibraryDetailEntity.setGoodsPrice(beforeDeleteEntity.getGoodsPrice());
					}else{
						if(detail!=null && detail.get("goodsAttribute")!=null){
							scrapLibraryDetailEntity.setGoodsAttribute(detail.get("goodsAttribute").toString());
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
						    scrapLibraryDetailEntity.setGoodsValidity(date); 
						}
						if(detail!=null && detail.get("goodsPrice")!=null){
							scrapLibraryDetailEntity.setGoodsPrice(detail.get("goodsPrice").toString());
						}
					}
					//+++++++++++++++++++++++++++++
					scrapLibraryDetailEntity.setMetrialId(Long.valueOf(materialIdArray[i].trim()));
					scrapLibraryDetailEntity.setCount(materialCount);
					scrapLibraryDetailService.addEntity(scrapLibraryDetailEntity);
					materialCategoryEntity.setQuote("3");
					materialCategoryService.updateEntity(materialCategoryEntity);
				}
	}

	@Override
	public void editPageSaveSingle(Long scrapId,
			String materialIdStringTemp, String materialCountStringTemp,
			Long wareHouseId,SysUnitEntity sysUnitEntity, Date instockTime,Map<String,Object> detailPropertyMap) {
		//根据入库单选择入库明细数据，先删除后添加
		ScrapLibraryDetailEntity scrapLibraryDetailEntity = new ScrapLibraryDetailEntity();
		List<Condition> conditions=new ArrayList<Condition>();
		String materialIdStringTempStr = materialIdStringTemp.replace("[", "").replace("]", "");
		conditions.add(new Condition("C_INSTOCK_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,scrapId ));
		conditions.add(new Condition("C_METRIAL_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,Long.valueOf(materialIdStringTempStr) ));
		List<ScrapLibraryDetailEntity> oldListSearch=scrapLibraryDetailService.findByCondition(conditions, null);
		if(!oldListSearch.isEmpty()){
			scrapLibraryDetailEntity=oldListSearch.get(0);
		}
		scrapLibraryDetailEntity.setInstockId(scrapId);
//		scrapLibraryDetailService.deleteByCondition("deleteByInstockId", scrapLibraryDetailEntity);
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
		Map<String,Object> detail = (Map<String, Object>) detailPropertyMap.get(materialIdString);
		if(detail.get("goodsAttribute")!=null){
			scrapLibraryDetailEntity.setGoodsAttribute(detail.get("goodsAttribute").toString());
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
		    scrapLibraryDetailEntity.setGoodsValidity(date); 
		}
		if(detail.get("goodsPrice")!=null){
			scrapLibraryDetailEntity.setGoodsPrice(detail.get("goodsPrice").toString());
			double goodsPriceD = Double.parseDouble(detail.get("goodsPrice").toString());
			double materialCountD = Double.parseDouble(materialCount);
			double totalPrice = goodsPriceD * materialCountD;
			scrapLibraryDetailEntity.setTotalPrice(totalPrice);
		}
		scrapLibraryDetailEntity.setMetrialId(Long.valueOf(materialIdString));
		scrapLibraryDetailEntity.setCount(materialCount);
		if(oldListSearch.isEmpty()){
			scrapLibraryDetailService.addEntity(scrapLibraryDetailEntity);
		}else{
			scrapLibraryDetailService.updateEntity(scrapLibraryDetailEntity);
		}
		
		materialCategoryEntity.setQuote("3");
		materialCategoryService.updateEntity(materialCategoryEntity);
}
	/**
	 * 提交调用开始流程
	 * @author zhangxb
	 */
	public ResultObj submit(Serializable id, Map<String, Object> params){
		ResultObj resultObj = new ResultObj();
		ScrapLibraryEntity t = this.findById(id);
		if(validate(t)){
			
			// 准备启动流程
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),params.get("userList"));
			if("1".equals(t.getType())){
				actTaskService.startProcess(ProcessMarkEnum.OUT_SCRAP_STOCK_DRAW_PROCESS_KEY.getName(), id.toString(), vars);
			}else{
				actTaskService.startProcess(ProcessMarkEnum.IN_STOCK_DRAW_PROCESS_KEY.getName(), id.toString(), vars);
			}
			//报废库编号开始
	        Map<String, Object> codeparams=new HashMap<String, Object> ();
	        SysUserEntity sysUserEntity = RequestContext.get().getUser();
	        SysUnitEntity sysunit=sysUnitService.findById(sysUserEntity.getUnitId());
			codeparams.put("farmCode", sysunit.getNameAB());
			codeparams.put("year", new Date());
			String code=fourCodeService.getBusinessCode("报废库编码", codeparams);
			t.setCode(code);
			t.setStatus(ScrapstockApproveStatusEnum.DIRECTORAPPROVE.getCode());
			super.updateEntity(t);
			//报废库编号结束
		}
		return resultObj;
	}
	/**
	 * 提交报废处理
	 * @author zhangxb
	 */
	public ResultObj submitHandle(Serializable id, Map<String, Object> params){
		ResultObj resultObj = new ResultObj();
		ScrapLibraryEntity t = this.findById(id);
		if(validate(t)){
			
			// 准备启动流程
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),params.get("userList"));
				actTaskService.startProcess(ProcessMarkEnum.SCRAPLIBRARY_HANDLE_PROCESS_KEY.getName(), id.toString(), vars);
			//报废库编号开始
			Map<String, Object> codeparams=new HashMap<String, Object> ();
			SysUserEntity sysUserEntity = RequestContext.get().getUser();
			SysUnitEntity sysunit=sysUnitService.findById(sysUserEntity.getUnitId());
			codeparams.put("farmCode", sysunit.getNameAB());
			codeparams.put("year", new Date());
			String code=fourCodeService.getBusinessCode("报废库编码", codeparams);
			t.setCode(code);
			t.setStatus(ScrapLibraryHandleStatusEnum.TOBEMANAGE.getCode());
			super.updateEntity(t);
			//报废库编号结束
		}
		return resultObj;
	}
	
	/**
	 * 审批调用下一步流程
	 * @author zhangxb
	 */
	public ResultObj approve(ScrapLibraryEntity t,Map<String, Object> params){
		if(validateStatus(t)){
			// 修改状态
			ScrapLibraryEntity scrapLibraryEntity = this.findById(t.getId());
			scrapLibraryEntity.setStatus(params.get("status").toString());
			String result=params.get("result").toString();
			if(ScrapstockOutExcuteEnum.DISCARD.getName().equals(result)){
				scrapLibraryEntity.setProcessMode("2");//丢弃2
			}
			if(ScrapstockOutExcuteEnum.SHOP.getName().equals(result)){
				scrapLibraryEntity.setProcessMode("0");//变卖0
			}
			if(ScrapstockOutExcuteEnum.TECHNOLOGYREPAIR.getName().equals(result)){
				scrapLibraryEntity.setProcessMode("1");//修复1
			}
			if(ScrapstockOutExcuteEnum.CANNOTREPAIR.getName().equals(result)){
				scrapLibraryEntity.setProcessMode("3");//不可修复3
			}
			if(ScrapstockOutExcuteEnum.TECHNOLOGYNOREPAIR.getName().equals(result)){
				scrapLibraryEntity.setProcessMode("3");//不可修复3
			}
			if(ScrapstockOutExcuteEnum.CONFIRM.getName().equals(result)){
				scrapLibraryEntity.setProcessMode("1");//修复1
			}
			if(ScrapstockOutExcuteEnum.DROP.getName().equals(result)){
				scrapLibraryEntity.setProcessMode("2");//丢弃2
			}
			if(ScrapstockOutExcuteEnum.SALE.getName().equals(result)){
				scrapLibraryEntity.setProcessMode("0");//变卖0
			}
			if(ScrapstockOutExcuteEnum.SCRAP.getName().equals(result)){
				scrapLibraryEntity.setProcessMode("3");//报废3
			}
			if(t.getStoreKeeper()!=null){
				scrapLibraryEntity.setStoreKeeper(t.getStoreKeeper());
			}
			if(t.getApproveUser()!=null){
				scrapLibraryEntity.setApproveUser(t.getApproveUser());
			}
			if(params.get("status").toString().equals("11")){
				//处理方式为丢弃
				scrapLibraryEntity.setProcessMode("0");
			}
			if(params.get("status").toString().equals("10")){
				//处理方式为变卖
				scrapLibraryEntity.setProcessMode("2");
			}
			this.updateEntity(scrapLibraryEntity);
			//设置当前流程,当前任务节点的下一个节点的办理人
			actTaskService.setVarialble(t.getTaskId(),params);
			// 调用流程接口
			params.put(ExamMarkEnum.RESULT.getCode(), params.get("result").toString());
			actTaskService.complete(t.getTaskId(), t.getProcInstId(),params);
			if(params.get("status").toString().equals("14")||params.get("status").toString().equals("15")||params.get("status").toString().equals("16")){
					
				List<Condition> conditions_detail=new ArrayList<Condition>();
				conditions_detail.add(new Condition("a.C_INSTOCK_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryEntity.getId()));
				List<ScrapLibraryDetailEntity> scrapLibraryDetailEntities = scrapLibraryDetailService.findByCondition(conditions_detail, null);
				InOutStockEntity inOutStockEntity=null;
				for(ScrapLibraryDetailEntity scrapLibraryDetailEntity : scrapLibraryDetailEntities){
					//增加出入库明细2018.4.26 start   by  zhangxb
					inOutStockEntity = new InOutStockEntity();
					inOutStockEntity.setCode(scrapLibraryEntity.getCode());
					inOutStockEntity.setUnitId(Long.valueOf(scrapLibraryEntity.getStationSourceId()));
					inOutStockEntity.setTime(scrapLibraryEntity.getInstockTime());
					inOutStockEntity.setType("2");//报废出库
					inOutStockEntity.setMaterialId(scrapLibraryDetailEntity.getMetrialId());
					inOutStockEntity.setCount(scrapLibraryDetailEntity.getCount().toString());
					inOutStockEntity.setWareHouseId(Long.valueOf(scrapLibraryEntity.getWarehouseId()));
					inOutStockService.addEntity(inOutStockEntity);
					//增加出入库明细2018.4.26 end by  zhangxb
					
					Long unitId = scrapLibraryDetailEntity.getUnitId();
					Long materialId = scrapLibraryDetailEntity.getMetrialId();
					String materialCount = scrapLibraryDetailEntity.getCount();
					List<Condition> conditions=new ArrayList<Condition>();
//					conditions.add(new Condition("sk.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, unitId));
					conditions.add(new Condition("sk.C_MATERIAL_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, materialId));
					conditions.add(new Condition("sk.C_GOODS_PRICE", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryDetailEntity.getGoodsPrice()));
					conditions.add(new Condition("sk.C_GOODS_VALIDITY", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryDetailEntity.getGoodsValidity()));
					List<StockEntity> stockEntities = stockService.findByCondition(conditions, null);
					for(StockEntity stockEntity : stockEntities){
						String kcslString = stockEntity.getInventoryQuantity();
						double kcsl = Double.parseDouble(kcslString)-Double.parseDouble(materialCount);
						stockEntity.setInventoryQuantity(String.valueOf(kcsl));
						stockEntity.setWareHouseId(Long.valueOf(scrapLibraryEntity.getWarehouseId()));
						stockEntity.setGoodsAttribute(scrapLibraryDetailEntity.getGoodsAttribute());
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
			}
			if(params.get("status").toString().equals("5")&&scrapLibraryEntity.getType().equals("1")){
				List<Condition> conditions_detail=new ArrayList<Condition>();
				conditions_detail.add(new Condition("a.C_INSTOCK_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryEntity.getId()));
				List<ScrapLibraryDetailEntity> scrapLibraryDetailEntities = scrapLibraryDetailService.findByCondition(conditions_detail, null);

				InOutStockEntity inOutStockEntity=null;
				for(ScrapLibraryDetailEntity scrapLibraryDetailEntity : scrapLibraryDetailEntities){
					inOutStockEntity = new InOutStockEntity();
					inOutStockEntity.setCode(scrapLibraryEntity.getCode());
					inOutStockEntity.setUnitId(Long.valueOf(scrapLibraryEntity.getStationSourceId()));
					inOutStockEntity.setTime(scrapLibraryEntity.getInstockTime());
					inOutStockEntity.setType("5");//报废库内申请
					inOutStockEntity.setMaterialId(scrapLibraryDetailEntity.getMetrialId());
					inOutStockEntity.setCount(scrapLibraryDetailEntity.getCount().toString());
					inOutStockEntity.setWareHouseId(Long.valueOf(scrapLibraryEntity.getWarehouseId()));
					inOutStockService.addEntity(inOutStockEntity);
					inOutStockEntity.setCode(scrapLibraryEntity.getCode());
					inOutStockEntity.setUnitId(Long.valueOf(scrapLibraryEntity.getStationSourceId()));
					inOutStockEntity.setTime(scrapLibraryEntity.getInstockTime());
					inOutStockEntity.setType("2");//正常库出库
					inOutStockEntity.setMaterialId(scrapLibraryDetailEntity.getMetrialId());
					inOutStockEntity.setCount(scrapLibraryDetailEntity.getCount().toString());
					inOutStockEntity.setWareHouseId(Long.valueOf(scrapLibraryEntity.getWarehouseId()));
					inOutStockService.addEntity(inOutStockEntity);
					String unitId = scrapLibraryEntity.getStationSourceId();
					List<Condition> conditions=new ArrayList<Condition>();
//					conditions.add(new Condition("sk.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, unitId));
					conditions.add(new Condition("sk.C_MATERIAL_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryDetailEntity.getMetrialId()));
					conditions.add(new Condition("sk.C_GOODS_PRICE", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryDetailEntity.getGoodsPrice()));
					conditions.add(new Condition("sk.C_GOODS_VALIDITY", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryDetailEntity.getGoodsValidity()));
					conditions.add(new Condition("sk.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "2"));
					List<StockEntity> list = stockService.findByCondition(conditions, null);
					String materialCount = scrapLibraryDetailEntity.getCount();
					if(null != list && !list.isEmpty()){
						for(StockEntity stockEntity : list){
							stockEntity.setGoodsAttribute(scrapLibraryDetailEntity.getGoodsAttribute());
							String kcslString = stockEntity.getInventoryQuantity();
							double kcsl = Double.parseDouble(kcslString)-Double.parseDouble(materialCount);
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
							stockEntity.setWareHouseId(Long.valueOf(scrapLibraryEntity.getWarehouseId()));
							stockEntity.setMaterialCode(scrapLibraryEntity.getCode());
							stockEntity.setScrapLibraryId(scrapLibraryEntity.getId().toString());
							stockService.updateEntity(stockEntity);
						}
					}else{
						StockEntity stockEntity = new StockEntity();
						stockEntity.setMaterialId(scrapLibraryDetailEntity.getMetrialId());
						stockEntity.setUnitId(Long.valueOf(scrapLibraryEntity.getStationSourceId()));
						stockEntity.setGoodsPrice(scrapLibraryDetailEntity.getGoodsPrice());
						stockEntity.setGoodsValidity(scrapLibraryDetailEntity.getGoodsValidity());
						stockEntity.setGoodsAttribute(scrapLibraryDetailEntity.getGoodsAttribute());
						stockEntity.setType("1");
						MaterialCategoryEntity materialEntity = materialCategoryService.findById(stockEntity.getMaterialId());
						if(materialEntity.getManagement().equals("1")){
							stockEntity.setInventoryQuantity(Integer.valueOf(materialCount).toString());
						}else{
							java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
							Double materialCountDouble = Double.parseDouble(materialCount);
							stockEntity.setInventoryQuantity(String.valueOf(df.format(materialCountDouble)));
						}
						stockEntity.setWareHouseId(Long.valueOf(scrapLibraryEntity.getWarehouseId()));
						stockEntity.setIsShortage("0");
						stockEntity.setScrapLibraryId(scrapLibraryEntity.getId().toString());
						stockService.addEntity(stockEntity);
					}
					conditions.clear();
					conditions.add(new Condition("sk.C_MATERIAL_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryDetailEntity.getMetrialId()));
					conditions.add(new Condition("sk.C_GOODS_PRICE", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryDetailEntity.getGoodsPrice()));
					conditions.add(new Condition("sk.C_GOODS_VALIDITY", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryDetailEntity.getGoodsValidity()));
					conditions.add(new Condition("sk.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
					List<StockEntity> stockList = stockService.findByCondition(conditions, null);
					if(null != stockList && !stockList.isEmpty()){
						for(StockEntity stockEntity : stockList){
							stockEntity.setGoodsAttribute(scrapLibraryDetailEntity.getGoodsAttribute());
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
							stockEntity.setWareHouseId(Long.valueOf(scrapLibraryEntity.getWarehouseId()));
							stockEntity.setMaterialCode(scrapLibraryEntity.getCode());
							stockEntity.setScrapLibraryId(scrapLibraryEntity.getId().toString());
							stockService.updateEntity(stockEntity);
						}
					}else{
						StockEntity stockEntity = new StockEntity();
						stockEntity.setMaterialId(scrapLibraryDetailEntity.getMetrialId());
						stockEntity.setUnitId(Long.valueOf(scrapLibraryEntity.getStationSourceId()));
						stockEntity.setGoodsPrice(scrapLibraryDetailEntity.getGoodsPrice());
						stockEntity.setGoodsValidity(scrapLibraryDetailEntity.getGoodsValidity());
						stockEntity.setGoodsAttribute(scrapLibraryDetailEntity.getGoodsAttribute());
						stockEntity.setType("1");
						MaterialCategoryEntity materialEntity = materialCategoryService.findById(stockEntity.getMaterialId());
						if(materialEntity.getManagement().equals("1")){
							stockEntity.setInventoryQuantity(Integer.valueOf(materialCount).toString());
						}else{
							java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
							Double materialCountDouble = Double.parseDouble(materialCount);
							stockEntity.setInventoryQuantity(String.valueOf(df.format(materialCountDouble)));
						}
						stockEntity.setWareHouseId(Long.valueOf(scrapLibraryEntity.getWarehouseId()));
						stockEntity.setIsShortage("0");
						stockEntity.setScrapLibraryId(scrapLibraryEntity.getId().toString());
						stockService.addEntity(stockEntity);
					}
				}
				WareHouseEntity wareHouseEntity = wareHouseService.findById(Long.valueOf(scrapLibraryEntity.getWarehouseId()));
				List<Condition> conditions = new ArrayList<Condition>();
				conditions.add(new Condition("a.C_INSTOCK_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryEntity.getId()));
				List<ScrapLibraryDetailEntity> scrapLibraryDetailList = scrapLibraryDetailService.findByCondition(conditions, null);
				conditions.clear();
				conditions.add(new Condition("C_MATERIAL_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, scrapLibraryDetailList.get(0).getMetrialId()));
				conditions.add(new Condition("C_WAREHOUSE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, wareHouseEntity.getWareHouseName()));
				conditions.add(new Condition("C_TIME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, scrapLibraryEntity.getInstockTime()));
				conditions.add(new Condition("C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
				List<StockStatictisPrintEntity> stockStatictisPrintList = stockStatictisPrintService.findByCondition(conditions, null);
				if(stockStatictisPrintList!=null&&!stockStatictisPrintList.isEmpty()){
					StockStatictisPrintEntity stockStatictisPrintEntity = stockStatictisPrintList.get(0);
					double countd = Double.parseDouble(scrapLibraryDetailList.get(0).getCount());
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
					int resultTime = instockTime.compareTo(sysTime);
					if(resultTime==0){
						stockStatictisPrintEntity.setMonthInstockNum(stockStatictisPrintEntity.getMonthInstockNum()+count);
						stockStatictisPrintEntity.setMonthOutstockNum(stockStatictisPrintEntity.getMonthOutstockNum()+count);
					}else if(resultTime>0){
						stockStatictisPrintEntity.setLastMonthStock(stockStatictisPrintEntity.getLastMonthStock()+count);
					}
					
					stockStatictisPrintEntity.setMonthStock(stockStatictisPrintEntity.getLastMonthStock()+stockStatictisPrintEntity.getMonthInstockNum()-stockStatictisPrintEntity.getMonthOutstockNum());
					stockStatictisPrintService.updateEntity(stockStatictisPrintEntity);
				}else{
					StockStatictisPrintEntity stockStatictisPrintEntity = new StockStatictisPrintEntity();
					ScrapLibraryDetailEntity scrapLibraryDetailEntity = scrapLibraryDetailList.get(0);
					stockStatictisPrintEntity.setMaterialId(scrapLibraryDetailEntity.getMetrialId().toString());
					MaterialCategoryEntity materialCategoryEntity = materialCategoryService.findById(scrapLibraryDetailEntity.getMetrialId());
					stockStatictisPrintEntity.setMaterialName(materialCategoryEntity.getName());
					stockStatictisPrintEntity.setMaterialType(materialCategoryEntity.getModel());
					conditions.clear();
					conditions.add(new Condition("C_CATEGORY_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "DIGIT"));
					conditions.add(new Condition("C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, materialCategoryEntity.getUnit()));
					List<SysDictionaryEntity> sysDictionaryList = sysDictionaryService.findByCondition(conditions, null);
					stockStatictisPrintEntity.setTechnicalUnit(sysDictionaryList.get(0).getName());
					stockStatictisPrintEntity.setTime(scrapLibraryEntity.getInstockTime());
					stockStatictisPrintEntity.setType("1");
					stockStatictisPrintEntity.setUnitId(scrapLibraryEntity.getStationSourceId().toString());
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
					
					int resultTime = instockTime.compareTo(sysTime);
					double countd = Double.parseDouble(scrapLibraryDetailEntity.getCount());
					int count = (int) countd;
					if(resultTime==0){
						stockStatictisPrintEntity.setMonthInstockNum(count);
						stockStatictisPrintEntity.setLastMonthStock(0);
						stockStatictisPrintEntity.setMonthOutstockNum(0);
						stockStatictisPrintEntity.setMonthStock(stockStatictisPrintEntity.getLastMonthStock()+count-stockStatictisPrintEntity.getMonthOutstockNum());
					}else if(resultTime>0){
						stockStatictisPrintEntity.setLastMonthStock(count);
						stockStatictisPrintEntity.setMonthInstockNum(0);
						stockStatictisPrintEntity.setMonthOutstockNum(0);
						stockStatictisPrintEntity.setMonthStock(count+stockStatictisPrintEntity.getMonthInstockNum()-stockStatictisPrintEntity.getMonthOutstockNum());
					}
					stockStatictisPrintService.addEntity(stockStatictisPrintEntity);
				}
			}
			if(params.get("status").toString().equals("5")&&scrapLibraryEntity.getType().equals("3")){
				List<Condition> conditions_detail=new ArrayList<Condition>();
				conditions_detail.add(new Condition("a.C_INSTOCK_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryEntity.getId()));
				List<ScrapLibraryDetailEntity> scrapLibraryDetailEntities = scrapLibraryDetailService.findByCondition(conditions_detail, null);

				InOutStockEntity inOutStockEntity=null;
				for(ScrapLibraryDetailEntity scrapLibraryDetailEntity : scrapLibraryDetailEntities){
					inOutStockEntity = new InOutStockEntity();
					inOutStockEntity.setCode(scrapLibraryEntity.getCode());
					inOutStockEntity.setUnitId(Long.valueOf(scrapLibraryEntity.getStationSourceId()));
					inOutStockEntity.setTime(scrapLibraryEntity.getInstockTime());
					inOutStockEntity.setType("3");//报废库外申请
					inOutStockEntity.setMaterialId(scrapLibraryDetailEntity.getMetrialId());
					inOutStockEntity.setCount(scrapLibraryDetailEntity.getCount().toString());
					List<Condition> conditions = new ArrayList<Condition>();
					conditions.add(new Condition("w.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, scrapLibraryEntity.getStationSourceId()));
					conditions.add(new Condition("w.C_WARE_HOUSE_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "报废外库"));
					List<WareHouseEntity> wareHouseList = wareHouseService.findByCondition(conditions, null);
					inOutStockEntity.setWareHouseId(wareHouseList.get(0).getId());
					inOutStockService.addEntity(inOutStockEntity);
					String unitId = scrapLibraryEntity.getStationSourceId();
					conditions.clear();
//					conditions.add(new Condition("sk.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, unitId));
					conditions.add(new Condition("sk.C_MATERIAL_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryDetailEntity.getMetrialId()));
					conditions.add(new Condition("sk.C_GOODS_PRICE", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryDetailEntity.getGoodsPrice()));
					conditions.add(new Condition("sk.C_GOODS_VALIDITY", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryDetailEntity.getGoodsValidity()));
					conditions.add(new Condition("sk.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "3"));
					List<StockEntity> list = stockService.findByCondition(conditions, null);
					String materialCount = scrapLibraryDetailEntity.getCount();
					if(null != list && !list.isEmpty()){
						for(StockEntity stockEntity : list){
							stockEntity.setGoodsAttribute(scrapLibraryDetailEntity.getGoodsAttribute());
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
//							stockEntity.setWareHouseId(Long.valueOf(scrapLibraryEntity.getWarehouseId()));
							stockEntity.setMaterialCode(scrapLibraryEntity.getCode());
							stockEntity.setScrapLibraryId(scrapLibraryEntity.getId().toString());
							stockService.updateEntity(stockEntity);
						}
					}else{
						StockEntity stockEntity = new StockEntity();
						stockEntity.setMaterialId(scrapLibraryDetailEntity.getMetrialId());
						stockEntity.setUnitId(Long.valueOf(scrapLibraryEntity.getStationSourceId()));
						stockEntity.setGoodsPrice(scrapLibraryDetailEntity.getGoodsPrice());
						stockEntity.setGoodsValidity(scrapLibraryDetailEntity.getGoodsValidity());
						stockEntity.setGoodsAttribute(scrapLibraryDetailEntity.getGoodsAttribute());
						stockEntity.setInventoryQuantity(scrapLibraryDetailEntity.getCount());
						stockEntity.setWareHouseId(wareHouseList.get(0).getId());
						stockEntity.setType("3");
						MaterialCategoryEntity materialEntity = materialCategoryService.findById(stockEntity.getMaterialId());
						if(materialEntity.getManagement().equals("1")){
							stockEntity.setInventoryQuantity(materialCount);
						}else{
							java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
							Double materialCountDouble = Double.parseDouble(materialCount);
							stockEntity.setInventoryQuantity(String.valueOf(df.format(materialCountDouble)));
						}
//						stockEntity.setWareHouseId(Long.valueOf(scrapLibraryEntity.getWarehouseId()));
						stockEntity.setIsShortage("0");
						stockEntity.setScrapLibraryId(scrapLibraryEntity.getId().toString());
						stockService.addEntity(stockEntity);
					}
				}
				List<Condition> conditions = new ArrayList<Condition>();
				conditions.add(new Condition("a.C_INSTOCK_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryEntity.getId()));
				List<ScrapLibraryDetailEntity> scrapLibraryDetailList = scrapLibraryDetailService.findByCondition(conditions, null);
				conditions.clear();
				conditions.add(new Condition("C_MATERIAL_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, scrapLibraryDetailList.get(0).getMetrialId()));
				conditions.add(new Condition("C_WAREHOUSE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "报废外库"));
				conditions.add(new Condition("C_TIME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, scrapLibraryEntity.getInstockTime()));
				conditions.add(new Condition("C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "2"));
				List<StockStatictisPrintEntity> stockStatictisPrintList = stockStatictisPrintService.findByCondition(conditions, null);
				if(stockStatictisPrintList!=null&&!stockStatictisPrintList.isEmpty()){
					StockStatictisPrintEntity stockStatictisPrintEntity = stockStatictisPrintList.get(0);
					double countd = Double.parseDouble(scrapLibraryDetailList.get(0).getCount());
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
					int resultTime = instockTime.compareTo(sysTime);
					if(resultTime==0){
						stockStatictisPrintEntity.setMonthInstockNum(stockStatictisPrintEntity.getMonthInstockNum()+count);
					}else if(resultTime>0){
						stockStatictisPrintEntity.setLastMonthStock(stockStatictisPrintEntity.getLastMonthStock()+count);
					}
					
					stockStatictisPrintEntity.setMonthStock(stockStatictisPrintEntity.getLastMonthStock()+stockStatictisPrintEntity.getMonthInstockNum()-stockStatictisPrintEntity.getMonthOutstockNum());
					stockStatictisPrintService.updateEntity(stockStatictisPrintEntity);
				}else{
					conditions.clear();
					conditions.add(new Condition("a.C_INSTOCK_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryEntity.getId()));
					StockStatictisPrintEntity stockStatictisPrintEntity = new StockStatictisPrintEntity();
					ScrapLibraryDetailEntity scrapLibraryDetailEntity = scrapLibraryDetailList.get(0);
					stockStatictisPrintEntity.setMaterialId(scrapLibraryDetailEntity.getMetrialId().toString());
					MaterialCategoryEntity materialCategoryEntity = materialCategoryService.findById(scrapLibraryDetailEntity.getMetrialId());
					stockStatictisPrintEntity.setMaterialName(materialCategoryEntity.getName());
					stockStatictisPrintEntity.setMaterialType(materialCategoryEntity.getModel());
					conditions.clear();
					conditions.add(new Condition("C_CATEGORY_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "DIGIT"));
					conditions.add(new Condition("C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, materialCategoryEntity.getUnit()));
					List<SysDictionaryEntity> sysDictionaryList = sysDictionaryService.findByCondition(conditions, null);
					stockStatictisPrintEntity.setTechnicalUnit(sysDictionaryList.get(0).getName());
					stockStatictisPrintEntity.setTime(scrapLibraryEntity.getInstockTime());
					stockStatictisPrintEntity.setType("2");//报废外库
					stockStatictisPrintEntity.setUnitId(scrapLibraryEntity.getStationSourceId().toString());
					SysUnitEntity sysUnitEntity = sysUnitService.findById(Long.valueOf(stockStatictisPrintEntity.getUnitId()));
					stockStatictisPrintEntity.setUnitName(sysUnitEntity.getName());
					stockStatictisPrintEntity.setWarehouse("报废外库");
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
					int resultTime = instockTime.compareTo(sysTime);
					double countd = Double.parseDouble(scrapLibraryDetailEntity.getCount());
					int count = (int) countd;
					if(resultTime==0){
						stockStatictisPrintEntity.setMonthInstockNum(count);
						stockStatictisPrintEntity.setLastMonthStock(0);
						stockStatictisPrintEntity.setMonthOutstockNum(0);
						stockStatictisPrintEntity.setMonthStock(stockStatictisPrintEntity.getLastMonthStock()+count-stockStatictisPrintEntity.getMonthOutstockNum());
					}else if(resultTime>0){
						stockStatictisPrintEntity.setLastMonthStock(count);
						stockStatictisPrintEntity.setMonthInstockNum(0);
						stockStatictisPrintEntity.setMonthOutstockNum(0);
						stockStatictisPrintEntity.setMonthStock(count+stockStatictisPrintEntity.getMonthInstockNum()-stockStatictisPrintEntity.getMonthOutstockNum());
					}
					stockStatictisPrintService.addEntity(stockStatictisPrintEntity);
				}
				
			}
			if(params.get("status").toString().equals("10")||params.get("status").toString().equals("11")||params.get("status").toString().equals("13")&&scrapLibraryEntity.getType().equals("2")){
				List<Condition> conditions_detail=new ArrayList<Condition>();
				conditions_detail.add(new Condition("a.C_INSTOCK_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryEntity.getId()));
				List<ScrapLibraryDetailEntity> scrapLibraryDetailEntities = scrapLibraryDetailService.findByCondition(conditions_detail, null);

				InOutStockEntity inOutStockEntity=null;
				for(ScrapLibraryDetailEntity scrapLibraryDetailEntity : scrapLibraryDetailEntities){
					inOutStockEntity = new InOutStockEntity();
					inOutStockEntity.setCode(scrapLibraryEntity.getCode());
					inOutStockEntity.setUnitId(Long.valueOf(scrapLibraryEntity.getStationSourceId()));
					inOutStockEntity.setTime(scrapLibraryEntity.getInstockTime());
					inOutStockEntity.setType("4");//报废出库
					inOutStockEntity.setMaterialId(scrapLibraryDetailEntity.getMetrialId());
					inOutStockEntity.setCount(scrapLibraryDetailEntity.getCount().toString());
					if(params.get("type").equals("3")){
						List<Condition> conditions = new ArrayList<Condition>();
						conditions.add(new Condition("w.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, scrapLibraryEntity.getStationSourceId()));
						conditions.add(new Condition("w.C_WARE_HOUSE_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "报废外库"));
						List<WareHouseEntity> wareHouseList = wareHouseService.findByCondition(conditions, null);
						inOutStockEntity.setWareHouseId(wareHouseList.get(0).getId());
					}else{
						inOutStockEntity.setWareHouseId(Long.valueOf(scrapLibraryEntity.getWarehouseId()));
						
					}
					
					inOutStockService.addEntity(inOutStockEntity);
					String unitId = scrapLibraryEntity.getStationSourceId();
					List<Condition> conditions=new ArrayList<Condition>();
//					conditions.add(new Condition("sk.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, unitId));
					conditions.add(new Condition("sk.C_MATERIAL_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryDetailEntity.getMetrialId()));
					conditions.add(new Condition("sk.C_GOODS_PRICE", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryDetailEntity.getGoodsPrice()));
					conditions.add(new Condition("sk.C_GOODS_VALIDITY", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryDetailEntity.getGoodsValidity()));
					conditions.add(new Condition("sk.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.NE, "2"));
					List<StockEntity> list = stockService.findByCondition(conditions, null);
					String materialCount = scrapLibraryDetailEntity.getCount();
					if(null != list && !list.isEmpty()){
						for(StockEntity stockEntity : list){
							stockEntity.setGoodsAttribute(scrapLibraryDetailEntity.getGoodsAttribute());
							String kcslString = stockEntity.getInventoryQuantity();
							double kcsl = Double.parseDouble(kcslString)-Double.parseDouble(materialCount);
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
							if(params.get("type").equals("3")){
								conditions.clear();
								conditions.add(new Condition("w.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, scrapLibraryEntity.getStationSourceId()));
								conditions.add(new Condition("w.C_WARE_HOUSE_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "报废外库"));
								List<WareHouseEntity> wareHouseList = wareHouseService.findByCondition(conditions, null);
								stockEntity.setWareHouseId(wareHouseList.get(0).getId());
							}
							stockEntity.setMaterialCode(scrapLibraryEntity.getCode());
							stockService.updateEntity(stockEntity);
						}
					}else{
						StockEntity stockEntity = new StockEntity();
						stockEntity.setMaterialId(scrapLibraryDetailEntity.getMetrialId());
						stockEntity.setUnitId(Long.valueOf(scrapLibraryEntity.getStationSourceId()));
						stockEntity.setGoodsPrice(scrapLibraryDetailEntity.getGoodsPrice());
						stockEntity.setGoodsValidity(scrapLibraryDetailEntity.getGoodsValidity());
						stockEntity.setGoodsAttribute(scrapLibraryDetailEntity.getGoodsAttribute());
						stockEntity.setInventoryQuantity(scrapLibraryDetailEntity.getCount());
						if(!params.get("type").equals("3")){
							conditions.clear();
							conditions.add(new Condition("w.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, scrapLibraryEntity.getStationSourceId()));
							conditions.add(new Condition("w.C_WARE_HOUSE_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "报废外库"));
							List<WareHouseEntity> wareHouseList = wareHouseService.findByCondition(conditions, null);
							stockEntity.setWareHouseId(wareHouseList.get(0).getId());
						}
						stockEntity.setType(params.get("type").toString());
						MaterialCategoryEntity materialEntity = materialCategoryService.findById(stockEntity.getMaterialId());
						if(materialEntity.getManagement().equals("1")){
							stockEntity.setInventoryQuantity(Integer.valueOf(materialCount).toString());
						}else{
							java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
							Double materialCountDouble = Double.parseDouble(materialCount);
							stockEntity.setInventoryQuantity(String.valueOf(df.format(materialCountDouble)));
						}
//						stockEntity.setWareHouseId(Long.valueOf(scrapLibraryEntity.getWarehouseId()));
						stockEntity.setIsShortage("0");
						stockService.addEntity(stockEntity);
					}
				}
				WareHouseEntity wareHouseEntity = wareHouseService.findById(Long.valueOf(scrapLibraryEntity.getWarehouseId()));
				List<Condition> conditions = new ArrayList<Condition>();
				conditions.add(new Condition("a.C_INSTOCK_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryEntity.getId()));
				List<ScrapLibraryDetailEntity> scrapLibraryDetailList = scrapLibraryDetailService.findByCondition(conditions, null);
				conditions.clear();
				conditions.add(new Condition("C_MATERIAL_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, scrapLibraryDetailList.get(0).getMetrialId()));
				conditions.add(new Condition("C_WAREHOUSE = '" + wareHouseEntity.getWareHouseName()+"'OR C_WAREHOUSE = '报废外库'"));
				conditions.add(new Condition("C_TIME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, scrapLibraryEntity.getInstockTime()));
				List<StockStatictisPrintEntity> stockStatictisPrintList = stockStatictisPrintService.findByCondition(conditions, null);
				if(stockStatictisPrintList!=null&&!stockStatictisPrintList.isEmpty()){
					for(StockStatictisPrintEntity stockStatictisPrintEntity : stockStatictisPrintList){
						double countd = Double.parseDouble(scrapLibraryDetailList.get(0).getCount());
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
						int resultTime = instockTime.compareTo(sysTime);
						if(resultTime==0){
							stockStatictisPrintEntity.setMonthOutstockNum(stockStatictisPrintEntity.getMonthOutstockNum()+count);;
						}else if(resultTime>0){
							stockStatictisPrintEntity.setLastMonthStock(stockStatictisPrintEntity.getLastMonthStock()-count);
						}
						
						stockStatictisPrintEntity.setMonthStock(stockStatictisPrintEntity.getLastMonthStock()+stockStatictisPrintEntity.getMonthInstockNum()-stockStatictisPrintEntity.getMonthOutstockNum());
						stockStatictisPrintService.updateEntity(stockStatictisPrintEntity);
					}
					
			}
			}
			if(params.get("status").toString().equals("12")&&scrapLibraryEntity.getType().equals("2")){
				List<Condition> conditions_detail=new ArrayList<Condition>();
				String warehouseId="";
				String stationSourceId ="";
				if(params.get("warehouseId")!=null){
					 warehouseId = params.get("warehouseId").toString();
				}else{
					warehouseId = scrapLibraryEntity.getWarehouseId();
				}
				if(params.get("stationSourceId")!=null){
					stationSourceId = params.get("stationSourceId").toString();
				}else{
					stationSourceId= scrapLibraryEntity.getStationSourceId();
				}
				conditions_detail.add(new Condition("a.C_INSTOCK_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryEntity.getId()));
				List<ScrapLibraryDetailEntity> scrapLibraryDetailEntities = scrapLibraryDetailService.findByCondition(conditions_detail, null);

				InOutStockEntity inOutStockEntity=null;
				for(ScrapLibraryDetailEntity scrapLibraryDetailEntity : scrapLibraryDetailEntities){
					inOutStockEntity = new InOutStockEntity();
					inOutStockEntity.setCode(scrapLibraryEntity.getCode());
					inOutStockEntity.setUnitId(Long.valueOf(scrapLibraryEntity.getStationSourceId()));
					inOutStockEntity.setTime(scrapLibraryEntity.getInstockTime());
					inOutStockEntity.setType("4");//报废出库
					inOutStockEntity.setMaterialId(scrapLibraryDetailEntity.getMetrialId());
					inOutStockEntity.setCount(scrapLibraryDetailEntity.getCount().toString());
					inOutStockEntity.setWareHouseId(Long.valueOf(scrapLibraryEntity.getWarehouseId()));
					inOutStockService.addEntity(inOutStockEntity);
					inOutStockEntity.setCode(scrapLibraryEntity.getCode());
					inOutStockEntity.setUnitId(Long.valueOf(stationSourceId));
					inOutStockEntity.setTime(scrapLibraryEntity.getInstockTime());
					inOutStockEntity.setType("1");//入库
					inOutStockEntity.setMaterialId(scrapLibraryDetailEntity.getMetrialId());
					inOutStockEntity.setCount(scrapLibraryDetailEntity.getCount().toString());
					inOutStockEntity.setWareHouseId(Long.valueOf(warehouseId));
					inOutStockService.addEntity(inOutStockEntity);
					InOutStockEntity inOutStock = new InOutStockEntity();
					String unitId = scrapLibraryEntity.getStationSourceId();
					List<Condition> conditions=new ArrayList<Condition>();
//					conditions.add(new Condition("sk.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, unitId));
					conditions.add(new Condition("sk.C_MATERIAL_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryDetailEntity.getMetrialId()));
					conditions.add(new Condition("sk.C_GOODS_PRICE", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryDetailEntity.getGoodsPrice()));
					conditions.add(new Condition("sk.C_GOODS_VALIDITY", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryDetailEntity.getGoodsValidity()));
					conditions.add(new Condition("sk.C_INVENTORY_QUANTITY", FieldTypeEnum.LONG, MatchTypeEnum.GT, "0.0"));
					conditions.add(new Condition("sk.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.NE, 2));
					List<StockEntity> list = stockService.findByCondition(conditions, null);
					String materialCount = scrapLibraryDetailEntity.getCount();
					if(null != list && !list.isEmpty()){
						for(StockEntity stockEntity : list){
							stockEntity.setGoodsAttribute(scrapLibraryDetailEntity.getGoodsAttribute());
							String kcslString = stockEntity.getInventoryQuantity();
							double kcsl = Double.parseDouble(kcslString)-Double.parseDouble(materialCount);
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
//							if(params.get("type").equals("2")){
//								stockEntity.setWareHouseId(5l);
//							}
							
							stockEntity.setMaterialCode(scrapLibraryEntity.getCode());
							stockService.updateEntity(stockEntity);
						}
					}else{
						StockEntity stockEntity = new StockEntity();
						stockEntity.setMaterialId(scrapLibraryDetailEntity.getMetrialId());
						stockEntity.setUnitId(Long.valueOf(scrapLibraryEntity.getStationSourceId()));
						stockEntity.setGoodsPrice(scrapLibraryDetailEntity.getGoodsPrice());
						stockEntity.setGoodsValidity(scrapLibraryDetailEntity.getGoodsValidity());
						stockEntity.setGoodsAttribute(scrapLibraryDetailEntity.getGoodsAttribute());
						stockEntity.setInventoryQuantity(scrapLibraryDetailEntity.getCount());
//						if(params.get("type").equals("2")){
//							stockEntity.setWareHouseId(5l);
//						}
						stockEntity.setType("3");
						MaterialCategoryEntity materialEntity = materialCategoryService.findById(stockEntity.getMaterialId());
						if(materialEntity.getManagement().equals("1")){
							stockEntity.setInventoryQuantity(Integer.valueOf(materialCount).toString());
						}else{
							java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
							Double materialCountDouble = Double.parseDouble(materialCount);
							stockEntity.setInventoryQuantity(String.valueOf(df.format(materialCountDouble)));
						}
//						stockEntity.setWareHouseId(Long.valueOf(scrapLibraryEntity.getWarehouseId()));
						stockEntity.setIsShortage("0");
						stockService.addEntity(stockEntity);
					}
					conditions.clear();
					conditions.add(new Condition("sk.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, unitId));
					conditions.add(new Condition("sk.C_MATERIAL_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryDetailEntity.getMetrialId()));
					conditions.add(new Condition("sk.C_GOODS_PRICE", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryDetailEntity.getGoodsPrice()));
					conditions.add(new Condition("sk.C_GOODS_VALIDITY", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryDetailEntity.getGoodsValidity()));
					conditions.add(new Condition("sk.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "2"));
					conditions.add(new Condition("sk.C_INVENTORY_QUANTITY", FieldTypeEnum.LONG, MatchTypeEnum.GT, "0.0"));
					List<StockEntity> stockList = stockService.findByCondition(conditions, null);
					if(null != stockList && !stockList.isEmpty()){
						for(StockEntity stockEntity : stockList){
							stockEntity.setGoodsAttribute(scrapLibraryDetailEntity.getGoodsAttribute());
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
							stockEntity.setMaterialCode(scrapLibraryEntity.getCode());
							stockService.updateEntity(stockEntity);
						}
					}else{
						StockEntity stockEntity = new StockEntity();
						stockEntity.setMaterialId(scrapLibraryDetailEntity.getMetrialId());
						stockEntity.setGoodsPrice(scrapLibraryDetailEntity.getGoodsPrice());
						stockEntity.setGoodsValidity(scrapLibraryDetailEntity.getGoodsValidity());
						stockEntity.setGoodsAttribute(scrapLibraryDetailEntity.getGoodsAttribute());
						stockEntity.setInventoryQuantity(scrapLibraryDetailEntity.getCount());
						stockEntity.setType("2");
						MaterialCategoryEntity materialEntity = materialCategoryService.findById(stockEntity.getMaterialId());
						if(materialEntity.getManagement().equals("1")){
							stockEntity.setInventoryQuantity(Integer.valueOf(materialCount).toString());
						}else{
							java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
							Double materialCountDouble = Double.parseDouble(materialCount);
							stockEntity.setInventoryQuantity(String.valueOf(df.format(materialCountDouble)));
						}
//						stockEntity.setWareHouseId(Long.valueOf(scrapLibraryEntity.getWarehouseId()));
						stockEntity.setIsShortage("0");
						
						stockEntity.setWareHouseId(Long.valueOf(warehouseId));
						stockEntity.setUnitId(Long.valueOf(stationSourceId));
						stockService.addEntity(stockEntity);
					}
				}
				WareHouseEntity wareHouseEntity = wareHouseService.findById(Long.valueOf(scrapLibraryEntity.getWarehouseId()));
				List<Condition> conditions = new ArrayList<Condition>();
				conditions.add(new Condition("a.C_INSTOCK_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryEntity.getId()));
				List<ScrapLibraryDetailEntity> scrapLibraryDetailList = scrapLibraryDetailService.findByCondition(conditions, null);
				conditions.clear();
				conditions.add(new Condition("C_MATERIAL_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, scrapLibraryDetailList.get(0).getMetrialId()));
				conditions.add(new Condition("C_WAREHOUSE = '" + wareHouseEntity.getWareHouseName()+"'OR C_WAREHOUSE = '报废外库'"));
				conditions.add(new Condition("C_TIME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, scrapLibraryEntity.getInstockTime()));
				List<StockStatictisPrintEntity> stockStatictisPrintList = stockStatictisPrintService.findByCondition(conditions, null);
				if(stockStatictisPrintList!=null&&!stockStatictisPrintList.isEmpty()){
					for(StockStatictisPrintEntity stockStatictisPrintEntity : stockStatictisPrintList){
						double countd = Double.parseDouble(scrapLibraryDetailList.get(0).getCount());
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
						int resultTime = instockTime.compareTo(sysTime);
						if(resultTime==0){
							stockStatictisPrintEntity.setMonthOutstockNum(stockStatictisPrintEntity.getMonthOutstockNum()+count);
						}else if(resultTime>0){
							stockStatictisPrintEntity.setLastMonthStock(stockStatictisPrintEntity.getLastMonthStock()-count);
						}
						
						stockStatictisPrintEntity.setMonthStock(stockStatictisPrintEntity.getLastMonthStock()+stockStatictisPrintEntity.getMonthInstockNum()-stockStatictisPrintEntity.getMonthOutstockNum());
						stockStatictisPrintService.updateEntity(stockStatictisPrintEntity);
					}
			}
				WareHouseEntity wareHouseNew = wareHouseService.findById(Long.valueOf(warehouseId));
				conditions.clear();
				conditions.add(new Condition("C_MATERIAL_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, scrapLibraryDetailList.get(0).getMetrialId()));
				conditions.add(new Condition("C_WAREHOUSE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, wareHouseNew.getWareHouseName()));
				conditions.add(new Condition("C_TIME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, scrapLibraryEntity.getInstockTime()));
				List<StockStatictisPrintEntity> stockStatictisPrintListNew = stockStatictisPrintService.findByCondition(conditions, null);
				if(stockStatictisPrintListNew!=null&&!stockStatictisPrintListNew.isEmpty()){
					double countd = Double.parseDouble(scrapLibraryDetailList.get(0).getCount());
					int count = (int) countd;
					StockStatictisPrintEntity stockStatictisPrintNew =stockStatictisPrintListNew.get(0);
					stockStatictisPrintNew.setMonthInstockNum(stockStatictisPrintNew.getMonthInstockNum()+count);
					stockStatictisPrintNew.setMonthStock(stockStatictisPrintNew.getMonthInstockNum()-stockStatictisPrintNew.getMonthOutstockNum());
					stockStatictisPrintService.updateEntity(stockStatictisPrintNew);
				}else{
					StockStatictisPrintEntity stockStatictisPrintEntity = new StockStatictisPrintEntity();
					ScrapLibraryDetailEntity scrapLibraryDetailEntity = scrapLibraryDetailList.get(0);
					stockStatictisPrintEntity.setMaterialId(scrapLibraryDetailEntity.getMetrialId().toString());
					MaterialCategoryEntity materialCategoryEntity = materialCategoryService.findById(scrapLibraryDetailEntity.getMetrialId());
					stockStatictisPrintEntity.setMaterialName(materialCategoryEntity.getName());
					stockStatictisPrintEntity.setMaterialType(materialCategoryEntity.getModel());
					conditions.clear();
					conditions.add(new Condition("C_CATEGORY_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "DIGIT"));
					conditions.add(new Condition("C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, materialCategoryEntity.getUnit()));
					List<SysDictionaryEntity> sysDictionaryList = sysDictionaryService.findByCondition(conditions, null);
					stockStatictisPrintEntity.setTechnicalUnit(sysDictionaryList.get(0).getName());
					stockStatictisPrintEntity.setTime(scrapLibraryEntity.getInstockTime());
					stockStatictisPrintEntity.setType("1");
					stockStatictisPrintEntity.setUnitId(stationSourceId);
					SysUnitEntity sysUnitEntity = sysUnitService.findById(Long.valueOf(stationSourceId));
					stockStatictisPrintEntity.setUnitName(sysUnitEntity.getName());
					stockStatictisPrintEntity.setWarehouse(wareHouseNew.getWareHouseName());
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
					
					int resultTime = instockTime.compareTo(sysTime);
					double countd = Double.parseDouble(scrapLibraryDetailEntity.getCount());
					int count = (int) countd;
					if(resultTime==0){
						stockStatictisPrintEntity.setMonthInstockNum(count);
						stockStatictisPrintEntity.setLastMonthStock(0);
						stockStatictisPrintEntity.setMonthOutstockNum(0);
						stockStatictisPrintEntity.setMonthStock(stockStatictisPrintEntity.getLastMonthStock()+count-stockStatictisPrintEntity.getMonthOutstockNum());
					}else if(resultTime>0){
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
	public Boolean compareKc(String materialId,String materialCount,String warehouseId){
		boolean result = false;
		if(materialId.contains(",")){
			//多条物资
			String[] materialIdArray = materialId.split(",");
			String[] materialCountArray = materialCount.replaceAll("[\\[\\]]", "").split(",");
			for(int i=0;i<materialIdArray.length;i++){
				List<Condition> conditions = new ArrayList<Condition>();
				conditions.add(new Condition("materialId",FieldTypeEnum.LONG,MatchTypeEnum.EQ,materialIdArray[i]));
				List<StockEntity> list_stockEntity = stockService.findByCondition(conditions, null);
				StockEntity stockEntity = list_stockEntity.get(0);
				Double kcsl = Double.parseDouble(stockEntity.getInventoryQuantity());
				Double cksl = Double.parseDouble(materialCountArray[i]);
				if(kcsl>=cksl){
					result = true;
				}
			}
		}else{
			//单条物资
			List<Condition> conditions = new ArrayList<Condition>();
			conditions.add(new Condition("materialId",FieldTypeEnum.LONG,MatchTypeEnum.EQ,materialId));
			conditions.add(new Condition("sk.C_WARE_HOUSE_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,warehouseId));
			conditions.add(new Condition("inventoryQuantity",FieldTypeEnum.LONG,MatchTypeEnum.GT,"0.0"));
			List<StockEntity> list_stockEntity = stockService.findByCondition(conditions, null);
			if(list_stockEntity.size()>1){
				conditions.add(new Condition("type", FieldTypeEnum.STRING, MatchTypeEnum.NE, "2"));
			}
			Double kcsl = Double.parseDouble(list_stockEntity.get(0).getInventoryQuantity());
			Double cksl = Double.parseDouble(materialCount);
			if(kcsl>=cksl){
				result = true;
			}
		}
		return result;
	}
	@Override
	public void deleteEntity(Serializable id) {
		getDao().deleteEntity(id);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SCRAPLIBRARY.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}

	@Override
	public void bulkDelete(Serializable[] ids) {
		getDao().bulkDelete(ids);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SCRAPLIBRARY.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
}