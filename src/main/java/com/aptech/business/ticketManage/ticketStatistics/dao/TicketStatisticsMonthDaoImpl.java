package com.aptech.business.ticketManage.ticketStatistics.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.ticketStatistics.domain.TicketStatisticsMonthVO;
import com.aptech.framework.orm.AncestorDao;
/**
 * 两票月统计数据类
 */
@Repository("ticketStatisticsMonthDao")
public class TicketStatisticsMonthDaoImpl extends AncestorDao<TicketStatisticsMonthVO> implements TicketStatisticsMonthDao{

	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.ticketStatisticsMonth";
	}

}
