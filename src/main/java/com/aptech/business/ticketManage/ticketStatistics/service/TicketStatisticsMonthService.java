package com.aptech.business.ticketManage.ticketStatistics.service;

import java.util.List;
import java.util.Map;

import com.aptech.business.ticketManage.ticketStatistics.domain.TicketStatisticsMonthVO;
import com.aptech.framework.orm.IBaseEntityOperation;

public interface TicketStatisticsMonthService extends IBaseEntityOperation<TicketStatisticsMonthVO>{
	/**
	 * 取得统计数据
	 * @param unitId 组织ID
	 * @param searchMonth 查询月
	 * @return
	 */
	List<Map<String, String>> getStatisticDataList(String unitId, String searchMonth);
}
