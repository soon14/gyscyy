package com.aptech.business.run.runWay.service;

import java.util.List;

import com.aptech.business.run.runLog.domain.RunLogEntity;
import com.aptech.business.run.runWay.domain.RunWayEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 运行方式应用管理服务接口
 *
 * @author 
 * @created 2017-06-20 09:26:27
 * @lastModified 
 * @history
 *
 */
public interface RunWayService  extends IBaseEntityOperation<RunWayEntity> {
    
    /**
    * @Title: insertList
    * @Description: 运行方式新增
    * @author sunliang
    * @date 2017年7月5日上午9:53:55
    * @param runWayEntity
    * @return
    * @throws Exception
    * @throws
    */
    ResultObj insertList(RunWayEntity runWayEntity) throws Exception;
    /**
    * @Title: editList
    * @Description: 运行方式编辑
    * @author sunliang
    * @date 2017年7月5日下午3:20:22
    * @param runWayEntity
    * @return
    * @throws Exception
    * @throws
    */
    ResultObj editList(RunWayEntity runWayEntity) throws Exception;
    /**
    * @Title: changeList
    * @Description: 列表数据格式转换
    * @author sunliang
    * @date 2017年7月7日下午2:19:33
    * @param list
    * @return
    * @throws
    */
    List<RunWayEntity>  changeList(List<RunWayEntity> list);
    /**
     * @Description:   运行方式编辑
     * @author         changl 
     * @Date           2017年10月10日 上午11:38:38 
     * @throws         Exception
     */
    ResultObj save(String runWay) ;
}