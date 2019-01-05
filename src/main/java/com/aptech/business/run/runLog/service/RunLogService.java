package com.aptech.business.run.runLog.service;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aptech.business.run.runLog.domain.RunLogEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 运行日志应用管理服务接口
 *
 * @author 
 * @created 2017-06-05 10:52:45
 * @lastModified 
 * @history
 *
 */
public interface RunLogService  extends IBaseEntityOperation<RunLogEntity> {
    /**
     * 
     * 交接班
     * 
     * @param @param id
     * @return void
     * @throws Exception 
     * @throws 
     * @author sunl
     * @created 2017年6月10日 下午2:52:56
     * @lastModified
     */
    ResultObj joinTeamWind(RunLogEntity runLogEntity) throws Exception;
    /**
     * 
     * 场站运行日志交接班
     * 
     * @param @param id
     * @return void
     * @throws Exception 
     * @throws 
     * @author sunl
     * @created 2017年6月10日 下午2:52:56
     * @lastModified
     */
    ResultObj joinTeam(RunLogEntity runLogEntity) throws Exception;
    /**
     * 
     * 修改会议信息
     * 
     * @param @param id
     * @return void
     * @throws Exception 
     * @throws 
     * @author sunl
     * @created 2017年6月10日 下午2:52:56
     * @lastModified
     */
    void updateMeeting(RunLogEntity runLogEntity) throws Exception;
    /**
    * @Title: delete
    * @Description: 删除实体
    * @author sunliang
    * @date 2017年7月31日下午1:52:56
    * @param id
    * @return
    * @throws
    */
    ResultObj delete(Serializable id)throws Exception;
    /**
     * @Title: validate
     * @Description: 验证方法
     * @author sunliang
     * @date 2017年8月8日下午7:39:03
     * @param id
     * @return
     * @throws
     */
    ResultObj invalidValidateWind(Long id)throws Exception;
    /**
     * @Title: validate
     * @Description: 场站运行日志验证方法
     * @author sunliang
     * @date 2017年8月8日下午7:39:03
     * @param id
     * @return
     * @throws
     */
    ResultObj invalidValidate(Long id)throws Exception;
    /**
     * @Title: addForWind
     * @Description: 添加场站运行日志
     * @author wangcc
     * @date 2018年4月23日下午7:39:03
     * @param id
     * @return
     * @throws
     */
    ResultObj addForWind(RunLogEntity t, HttpServletRequest request)throws Exception;
    /**
    * @Title: searchDataForWind
    * @Description: 查询场站运行日志
    * @author wangcc
    * @date 2018年4月24日下午10:00:00
    * @param id
    * @return
    * @throws
    */
    ResultListObj searchDataForWind(HttpServletRequest request,Map<String, Object> param)throws Exception;
    /**
     * 
     * 修改会议信息
     * 
     * @param @param id
     * @return void
     * @throws Exception 
     * @throws 
     * @author sunl
     * @created 2017年6月10日 下午2:52:56
     * @lastModified
     */
    void updateMeetingForWind(RunLogEntity runLogEntity) throws Exception;
    /**
     * 
     * 修改会议信息
     * 
     * @param @param id
     * @return void
     * @throws Exception 
     * @throws 
     * @author sunl
     * @created 2017年6月10日 下午2:52:56
     * @lastModified
     */
    RunLogEntity getDataById(Long rlId);
}