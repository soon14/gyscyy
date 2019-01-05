package com.aptech.business.run.protect.service;

import java.io.Serializable;
import java.util.Map;

import com.aptech.business.run.protect.domain.ProtectEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 保护投退应用管理服务接口
 *
 * @author 
 * @created 2017-06-02 14:38:25
 * @lastModified 
 * @history
 *
 */
public interface ProtectService  extends IBaseEntityOperation<ProtectEntity> {
    /**
     * 
     * 提交审批
     * 
     * @param @param id
     * @return void
     * @throws 
     * @author sunl
     * @created 2017年6月10日 下午2:52:56
     * @lastModified
     */
    void tocheck(Long id, Map<String, Object> params);
    /**
     * 审批：流程
     * 
     * @param  id
     */
    ResultObj check(ProtectEntity t,Map<String, Object> params);
    /**
    * @Title: checkAndUpdate
    * @Description: 审批并修改保护投退信息
    * @author sunliang
    * @date 下午3:46:54
    * @param t
    * @param params
    * @return
    * @throws
    */
    ResultObj checkAndUpdate(ProtectEntity t,Map<String, Object> params);
    /**
     * @throws ParseException 
    * @Title: add
    * @Description: 保存实体
    * @author sunliang
    * @date 2017年6月28日上午9:32:28
    * @param t
    * @return
    * @throws
    */
    ResultObj add(ProtectEntity t) throws Exception ;
    /**
    * @Title: update
    * @Description: 修改实体
    * @author sunliang
    * @date 2017年8月1日下午2:59:29
    * @param t
    * @return
    * @throws Exception
    * @throws
    */
    ResultObj update(ProtectEntity t) throws Exception ;
    /**
    * @Title: delete
    * @Description: 删除方法
    * @author sunliang
    * @date 2017年8月3日下午1:47:43
    * @param ids
    * @return
    * @throws
    */
    ResultObj delete(Serializable id,String role)throws Exception;
    String findByEquipId(String id);
}