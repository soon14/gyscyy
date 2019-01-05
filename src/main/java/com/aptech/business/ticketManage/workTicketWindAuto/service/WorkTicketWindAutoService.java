package com.aptech.business.ticketManage.workTicketWindAuto.service;

import java.util.List;

import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.business.ticketManage.workTicketWindFlow.domain.WorkTicketWindEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 风力机械工作票应用应用管理服务接口
 *
 * @author
 * @created 2017-06-02 11:50:39
 * @lastModified
 * @history
 *
 */
public interface WorkTicketWindAutoService extends IBaseEntityOperation<WorkTicketEntity> {
	/**
	 * 修改
	 * @param t
	 * @param id
	 * @return
	 */
	ResultObj update(WorkTicketEntity t, Long id);
	/**
	 * 修改前验证
	 * @param id
	 * @return
	 */
	ResultObj isupdateValidate(Long id);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	ResultObj deleteBulk(List<Integer> ids);
	/**
	 * 删除验证
	 * @param id
	 * @return
	 */
	ResultObj deleteValidate(Long id);
	
	/**
	 * @Description:   作废
	 * @author         zhangzq 
	 * @Date           2017年6月21日 下午5:01:36 
	 * @throws         Exception
	 */
	ResultObj saveInvalid(WorkTicketEntity workTicketEntity);
	/**
	 * @Description:   验证是否执行了安全措施
	 * @author         zhangzq 
	 * @Date           2017年6月28日 下午3:58:01 
	 * @throws         Exception
	 */
	ResultObj isExecute(Long id);
	/**
	 * @Description:   申请试运
	 * @author         zhangzq 
	 * @Date           2017年6月28日 下午5:17:16 
	 * @throws         Exception
	 */
	ResultObj isExecuteSqsy(Long id);
	/**
	 * @Description:   试运恢复
	 * @author         zhangzq 
	 * @Date           2017年6月28日 下午5:17:16 
	 * @throws         Exception
	 */
	ResultObj isExecuteSyhf(Long id);
	/**
	 * @Description:   提交时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	ResultObj tijiaoValidate(Long id);
	/**
	 * @Description:   鉴定的时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午2:00:37 
	 * @throws         Exception
	 */
	ResultObj invalidValidate(Long id);
	/**
	 * @Description:   设置标准票时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午2:00:37 
	 * @throws         Exception
	 */
	ResultObj setValidate(Long id);
	
	/**提交电气第一种工作票
	 * @Description:   
	 * @author         zhangzq 
	 * @Date           2017年6月12日 下午6:10:01 
	 * @throws         Exception
	 */
	public void submit(String id, String selectUser);
	/**
	 * @Description:  更新票的 信息，更新的是工作票的从表属性,同意的时候
	 * @author         zhangzq 
	 * @Date           2017年6月8日 下午4:48:14 
	 * @throws         Exception
	 */
	void updateSpnrAgree(WorkTicketWindEntity workTicketWindEntity,SysUserEntity userEntity);
	/**
	 * @Description:  更新票的 信息，更新的是工作票的从表属性,驳回的时候
	 * @author         zhangzq 
	 * @Date           2017年6月8日 下午4:48:14 
	 * @throws         Exception
	 */
	void updateSpnrDisagree(WorkTicketWindEntity workTicketWindEntity,SysUserEntity userEntity);
}