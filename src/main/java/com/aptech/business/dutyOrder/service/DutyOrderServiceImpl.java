package com.aptech.business.dutyOrder.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.dutyOrder.dao.DutyOrderDao;
import com.aptech.business.dutyOrder.domain.DutyOrderEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;

/**
 * 
 * 值次应用管理服务实现类
 *
 * @author 
 * @created 2017-09-13 17:23:46
 * @lastModified 
 * @history
 *
 */
@Service("dutyOrderService")
@Transactional
public class DutyOrderServiceImpl extends AbstractBaseEntityOperation<DutyOrderEntity> implements DutyOrderService {
	
	@Autowired
	private DutyOrderDao dutyOrderDao;
	@Autowired
	private SysUserService sysUserService;
	
	@Override
	public IBaseEntityOperation<DutyOrderEntity> getDao() {
		return dutyOrderDao;
	}
	
	/**
	 * 查询列表 zzq 20170605
	 */
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if(page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		List<Sort> orderList=new ArrayList<Sort>();//page.getOrders();
   		
   		orderList.addAll(OrmUtil.changeMapToOrders(params));
		//默认计划时间 倒叙
		page.setOrders(orderList);
   		
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		List<DutyOrderEntity> resultList = (List<DutyOrderEntity>) super.findByCondition(conditions, page);
		
		if(resultList!=null && resultList.size()>0){
			for(DutyOrderEntity dutyOrderEntity:resultList){
				if(dutyOrderEntity.getTeamMember()!=null){
					String[] ids = dutyOrderEntity.getTeamMember().split(",");
					conditions.clear();
					conditions.add(new Condition("a.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.IN,ids));
					List<SysUserEntity> userList = sysUserService.findByCondition(conditions, null);
					String userNames = "";
					for(int i=0;i<userList.size();i++){
						if(i==userList.size()-1){
							userNames+=userList.get(i).getName();
						}else{
							userNames+=userList.get(i).getName()+",";
						}
					}
					dutyOrderEntity.setTeamMember(userNames);
				}
				
			}
		}
		
		return (List<O>) resultList;
	}
	
}