package com.aptech.business.run.workRecord.service;

import java.io.Serializable;
import java.util.Map;

import com.aptech.business.run.protect.domain.ProtectEntity;
import com.aptech.business.run.workRecord.domain.WorkRecordEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 定期工作记录应用管理服务接口
 *
 * @author 
 * @created 2017-06-01 15:08:52
 * @lastModified 
 * @history
 *
 */
public interface WorkRecordService  extends IBaseEntityOperation<WorkRecordEntity> {
   
    /**
    * @Title: tocheck
    * @Description: 提交审批
    * @author sunliang
    * @date 2017年6月29日下午6:16:53
    * @param id
    * @param params
    * @throws
    */
    void tocheck(Long id, Map<String, Object> params);
    /**
    * @Title: check
    * @Description:审批操作
    * @author sunliang
    * @date 2017年6月30日下午2:36:33
    * @param t
    * @param params
    * @return
    * @throws
    */
    ResultObj check(WorkRecordEntity t,Map<String, Object> params);
    /**
    * @Title: delete
    * @Description: 删除实体
    * @author sunliang
    * @date 2017年8月3日下午3:18:35
    * @param id
    * @return
    * @throws Exception
    * @throws
    */
    ResultObj delete(Serializable id)throws Exception;

	
}