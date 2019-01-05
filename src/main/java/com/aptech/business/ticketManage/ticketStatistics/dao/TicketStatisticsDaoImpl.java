package com.aptech.business.ticketManage.ticketStatistics.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.ticketStatistics.domain.TicketStatisticsVO;
import com.aptech.framework.orm.AncestorDao;

/**
 * 两票统计数据类
 */
@Repository("ticketStatisticsDao")
public class TicketStatisticsDaoImpl extends AncestorDao<TicketStatisticsVO> implements TicketStatisticsDao{
	
	@Override
	public String getNameSpace() {
		return "com.aptech.business.ticketStatistics";
	}
}
