package com.aptech.business.run.joinLand.service;

import java.util.List;

import com.aptech.business.run.joinLand.domain.JoinLandEntity;
import com.aptech.business.run.runWay.domain.RunWayEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 接地线（刀闸）情况应用管理服务接口
 *
 * @author 
 * @created 2017-06-06 09:48:08
 * @lastModified 
 * @history
 *
 */
public interface JoinLandService  extends IBaseEntityOperation<JoinLandEntity> {
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
     ResultObj insertList(JoinLandEntity runWayEntity) throws Exception;
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
     ResultObj editList(JoinLandEntity runWayEntity) throws Exception;
     /**
     * @Title: changeList
     * @Description: 列表数据格式转换
     * @author sunliang
     * @date 2017年7月7日下午2:19:33
     * @param list
     * @return
     * @throws
     */
     List<JoinLandEntity>  changeList(List<JoinLandEntity> list);
     /**
      * @Description:   运行方式编辑
      * @author         wangcc 
      * @Date           2017年10月10日 上午17:53:38 
      * @throws         Exception
      */
     ResultObj save(String runWay) ;
}