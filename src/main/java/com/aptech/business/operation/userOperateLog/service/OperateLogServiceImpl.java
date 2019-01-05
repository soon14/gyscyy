package com.aptech.business.operation.userOperateLog.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.operation.userOperateLog.dao.OperateLogDao;
import com.aptech.business.operation.userOperateLog.domain.OperateLogEntity;
import com.aptech.common.system.config.domain.SysConfigEntity;
import com.aptech.common.system.config.domain.SysConfigEnum;
import com.aptech.common.system.config.service.SysConfigService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;

/**
 * 
 * 用户操作应用管理服务实现类
 *
 * @author 
 * @created 2018-04-09 10:36:54
 * @lastModified 
 * @history
 *
 */
@Service("operateLogService")
@Transactional
public class OperateLogServiceImpl extends AbstractBaseEntityOperation<OperateLogEntity> implements OperateLogService {
	private static String filepath = "D:\\log\\";

	@Autowired
	private OperateLogDao userOperateLogDao;
	@Autowired
	private SysConfigService sysConfigService;
	
	@Override
	public IBaseEntityOperation<OperateLogEntity> getDao() {
		return userOperateLogDao;
	}
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if (page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}     
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("id"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String todayFirst = sdf.format(today)+" 00:00:00";
		String todatEnd = sdf.format(today)+" 23:59:59";
		
		conditions.add(new Condition("C_CREATE_DATE", FieldTypeEnum.DATE, MatchTypeEnum.GE, todayFirst));
		conditions.add(new Condition("C_CREATE_DATE", FieldTypeEnum.DATE, MatchTypeEnum.LE, todatEnd));
		return findByCondition(conditions, page);
	}
	
	@Override
	public void addOperateLog(SysUserEntity userEntity,String operateModule,String operate,String code,int operateUser) {
		if(userEntity==null ){
			return;
		}
//		String operateDesc = operate+"了"+operateModule+" "+code;
		String operateDesc = operate+" "+code;
		Date operateDate = new Date();
		List<SysConfigEntity> sysConfigList = sysConfigService.findAll();
		if(sysConfigList!=null && !sysConfigList.isEmpty()){
			if(sysConfigList.get(0).getLogAddress()!=null && !"".equals(sysConfigList.get(0).getLogAddress())){
				filepath = sysConfigList.get(0).getLogAddress();
			}
			if(SysConfigEnum.YES.getId().toString().equals(sysConfigList.get(0).getStorageType())){
				OperateLogEntity userOperateLogEntity = new OperateLogEntity();
				userOperateLogEntity.setModuleName(operateModule);
				userOperateLogEntity.setOperateDesc(operateDesc);
				userOperateLogEntity.setUserId(userEntity.getId());
				userOperateLogEntity.setIpAddress(userEntity.getLastLoginIP());
				userOperateLogEntity.setLoginName(userEntity.getLoginName());
				userOperateLogEntity.setUserName(userEntity.getName());
				userOperateLogEntity.setCreateDate(operateDate);
				userOperateLogEntity.setOperateType(Long.parseLong(Integer.toString(operateUser)));
//				userOperateLogEntity.setOperateUser(operateUser);
//				userOperateLogEntity.setOperateUserName(operate);
				this.addEntity(userOperateLogEntity);
			}
		}
		//添加到日志文件中
		addLogFile(operateDesc,operateDate,userEntity);
	}
	
	private void addLogFile(String operateDesc,Date operateDate,SysUserEntity userEntity){
		DateFormatUtil dfTime =  DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss");
		DateFormatUtil df =  DateFormatUtil.getInstance("yyyy-MM-dd");
		File file = null;
		File file1 = null;
		BufferedWriter bw = null;
		try {
		   file1 = new File(filepath);
		   file = new File(filepath+df.format(operateDate)+".txt");
		   if(!file1.exists()){
			   file1.mkdirs();
		   }
		   if(!file.exists()){
			   file.createNewFile();
		   }
		   bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
			bw.write("用户 " + userEntity.getName() + " 在 "
					+ dfTime.format(operateDate) + " "+operateDesc + "\n");
			bw.flush();
		}catch (FileNotFoundException e) {
			System.out.println("文件未找到");
		} catch (IOException e) {
			System.out.println("路径不存在");
		}finally {
			if (null != bw) {
				try {
					bw.close();
				} catch (IOException e) {
					System.out.println("流操作异常");
				}
			}
		}
		
	}
}