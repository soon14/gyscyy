package com.aptech.business.run.workPlan.service;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Map;

import com.aptech.business.run.protect.domain.ProtectEntity;
import com.aptech.business.run.workPlan.domain.WorkPlanEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 定期工作计划应用管理服务接口
 *
 * @author 
 * @created 2017-05-27 11:40:09
 * @lastModified 
 * @history
 *
 */
public interface WorkPlanService  extends IBaseEntityOperation<WorkPlanEntity> {
    
    /**
    * @Title: tocheck
    * @Description: 提交审批
    * @author sunliang
    * @date 下午3:06:30
    * @param id
    * @param params
    * @throws
    */
    void tocheck(Long id, Map<String, Object> params);
    
    /**
    * @Title: check
    * @Description: 审批
    * @author sunliang
    * @date 下午3:07:37
    * @param t
    * @param params
    * @return
    * @throws
    */
    ResultObj check(WorkPlanEntity t,Map<String, Object> params);
    /**
    * @Title: add
    * @Description: 保存实体
    * @author sunliang
    * @date 2017年6月28日上午10:31:00
    * @param t
    * @return
    * @throws ParseException
    * @throws
    */
    ResultObj add(WorkPlanEntity t) ;
    /**
    * @Title: delete
    * @Description: 删除实体
    * @author sunliang
    * @date 2017年8月3日下午3:13:18
    * @param id
    * @return
    * @throws Exception
    * @throws
    */
    ResultObj delete(Serializable id)throws Exception;
}