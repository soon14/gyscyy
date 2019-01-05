package com.aptech.business.component.dictionary;


/**
 * *************************************************************
 * <p>
 * File name: ErrorCodeConstants.java
 * <p>
 * Title: 异常编码 常量类
 * <p>
 * Description: 异常编码常量类
 * <p>
 * Company:
 * <p>
 * Department: 研发部
 * <p>
 * 
 * @author [wangjw]
 * @createtime 2013-12-23
 * 
 ************************************************************** 
 */
public class ErrorCodeConstants {
	// 两票管理 1000x
	public class TwoTicket {
		//电气第一种票  100100(前两位10为1级模块代码代表两票,中间两位代表2级模块代码代表电气第一种票,后两位代表模块下异常编码)
		public final static String WORKTICKET_ISEXECUTE = "100101"; 
		public final static String WORKTICKET_BATHDELETE = "100102";
		public final static String WORKTICKET_UPDATEDELETE = "100103";
	}
	
	// 缺陷票管理 2000x
	public class DefectTicket {

		
	}
	
	// 运行管理 3000x
	public class RunningTicket {

		
	}
	
	// 项目管理 4000x
	public class Project {

		
	}

	// 备品备件管理 5000x
	public class SpareParts {

		
	}
	
	// 检修管理 6000x
	public class Overhaul {

		
	}
	
	// 巡检管理 7000x
	public class RoutingInspection {

		
	}
	
	// 设备管理 8000x
	public class EquipmentLedger {

		
	}
	
	// 培训管理 9000x
	public class Training {

		
	}
}
