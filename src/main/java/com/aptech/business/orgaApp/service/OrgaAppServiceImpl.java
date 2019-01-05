package com.aptech.business.orgaApp.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.dutyOrder.domain.DutyOrderEntity;
import com.aptech.business.orgaApp.dao.OrgaAppDao;
import com.aptech.business.orgaApp.domain.OrgaAppEntity;
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
 * 班组应用管理服务实现类
 *
 * @author 
 * @created 2017-09-14 09:37:02
 * @lastModified 
 * @history
 *
 */
@Service("orgaAppService")
@Transactional
public class OrgaAppServiceImpl extends AbstractBaseEntityOperation<OrgaAppEntity> implements OrgaAppService {
	
	@Autowired
	private OrgaAppDao orgaAppDao;
	@Autowired
	private SysUserService sysUserService;
	@Override
	public IBaseEntityOperation<OrgaAppEntity> getDao() {
		return orgaAppDao;
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
		List<OrgaAppEntity> resultList = (List<OrgaAppEntity>) super.findByCondition(conditions, page);
		
		if(resultList!=null && resultList.size()>0){
			for(OrgaAppEntity orgaAppEntity:resultList){
				if(orgaAppEntity.getTeamMember()!=null){
					String[] ids = orgaAppEntity.getTeamMember().split(",");
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
					orgaAppEntity.setTeamMember(userNames);
				}
				
			}
		}
		
		return (List<O>) resultList;
	}
}