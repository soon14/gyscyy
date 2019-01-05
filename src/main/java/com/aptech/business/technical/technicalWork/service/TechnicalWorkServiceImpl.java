package com.aptech.business.technical.technicalWork.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.TechnicalStatusEnum;
import com.aptech.business.component.dictionary.TechnicalWcztEnum;
import com.aptech.business.technical.technical.domain.TechnicalEntity;
import com.aptech.business.technical.technical.exception.TechnicalException;
import com.aptech.business.technical.technical.exception.TechnicalExceptionType;
import com.aptech.business.technical.technicalPlandetail.domain.TechnicalPlandetailEntity;
import com.aptech.business.technical.technicalPlandetail.service.TechnicalPlandetailService;
import com.aptech.business.technical.technicalWork.dao.TechnicalWorkDao;
import com.aptech.business.technical.technicalWork.domain.TechnicalWorkEntity;
import com.aptech.common.system.dictionary.domain.SysDictionaryEntity;
import com.aptech.common.system.dictionary.service.SysDictionaryService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 技术监督工作表应用管理服务实现类
 *
 * @author 
 * @created 2017-11-13 16:16:04
 * @lastModified 
 * @history
 *
 */
@Service("technicalWorkService")
@Transactional
public class TechnicalWorkServiceImpl extends AbstractBaseEntityOperation<TechnicalWorkEntity> implements TechnicalWorkService {
	
	@Autowired
	private TechnicalWorkDao technicalWorkDao;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private TechnicalPlandetailService technicalPlandetailService;
	@Override
	public IBaseEntityOperation<TechnicalWorkEntity> getDao() {
		return technicalWorkDao;
	}
	/**
	 * 查询列表 zzq 20170606
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if(page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		page.setOrders(OrmUtil.changeMapToOrders(params));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		List<TechnicalWorkEntity> resultList = (List<TechnicalWorkEntity>) super.findByCondition(conditions, page);
		for (TechnicalWorkEntity technicalWorkEntity : resultList) {
//			conditions.clear();
//			conditions.add(new Condition("C_CATEGORY_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "TECHNICAL_TYPE"));
//			conditions.add(new Condition("C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, technicalWorkEntity.getJdzyId()));
//			List<SysDictionaryEntity> sysDictionaryList = sysDictionaryService.findByCondition(conditions, null);
//			technicalWorkEntity.setJdzyName(sysDictionaryList.get(0).getName());
			if(isStatusValidate(technicalWorkEntity.getId())){
				technicalWorkEntity.setWcstatus(TechnicalWcztEnum.QUALIFIED.getName());
			}else{
				technicalWorkEntity.setWcstatus(TechnicalWcztEnum.NOQUALIFIED.getName());
			}
		}
		return (List<O>)resultList;
	}
	@Override
	public void addEntity(TechnicalWorkEntity t) {
		SysUserEntity sysUserEntity=sysUserService.findById(Long.valueOf(t.getPicId()));
		t.setPicName(sysUserEntity.getName());
		t.setStatus(Long.valueOf(DataStatusEnum.NORMAL.ordinal()));
		super.addEntity(t);
	}
	@Override
	public ResultObj update(TechnicalWorkEntity t, Long id) {
		TechnicalWorkEntity technicalWorkEntity=this.findById(id);
		technicalWorkEntity.setId(id);
		technicalWorkEntity.setPicId(t.getPicId());
		SysUserEntity sysUserEntity=sysUserService.findById(Long.valueOf(t.getPicId()));
		technicalWorkEntity.setPicName(sysUserEntity.getName());
		technicalWorkEntity.setContent(t.getContent());
		technicalWorkEntity.setDanger(t.getDanger());
		technicalWorkEntity.setJdzyId(t.getJdzyId());
		technicalWorkEntity.setTime(t.getTime());
		super.updateEntity(technicalWorkEntity);
		return new ResultObj();
	}
	
	private boolean isStatusValidate(Long id) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("C_TECHNICAL_WORKID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		List<TechnicalPlandetailEntity> list=technicalPlandetailService.findByCondition(conditions, null);
		if(list.isEmpty()){
			return false;
		}else{
			for (TechnicalPlandetailEntity technicalPlandetailEntity : list) {
				String wcStatus=technicalPlandetailEntity.getWcStatus().toString();
				if(wcStatus.equals(TechnicalWcztEnum.NOQUALIFIED.getCode())){
					return false;
				}
			}
		}
		return true;
	}
}