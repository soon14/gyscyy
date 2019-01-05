package com.aptech.business.train.trainStatistics.domain;

import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 培训统计页面展示实体类
 *
 * @author 
 * @created 2018-03-20 13:39:12
 * @lastModified 
 * @history
 *
 */
public class TrainStatisticsVO extends BaseEntity{
		/**
	 * 
	 */
	private static final long serialVersionUID = 2860280856830685087L;
		/**
		 * 主键
		 */
    	private Long id;
    	/**
		 * 办班单位名称
		 */
    	private String unitName;
		/**
		 * 统计时间
		 */
    	private String showTime;
		/**
		 * 培训总次数
		 */
    	private int totalCount;
		/**
		 * 定期培训次数
		 */
    	private int regularCount;
		/**
		 * 非定期培训次数
		 */
    	private int noRegularCount;
		/**
		 * 总培训人数
		 */
    	private int totalPeopleCount;
    	/**
    	 * 导出序号（用于导出excel）
    	 */
    	private int number;
    	
    	public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}

		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		
		public int getTotalCount() {
			return totalCount;
		}
		public void setTotalCount(int totalCount) {
			this.totalCount = totalCount;
		}
		public int getRegularCount() {
			return regularCount;
		}
		public void setRegularCount(int regularCount) {
			this.regularCount = regularCount;
		}
		public int getNoRegularCount() {
			return noRegularCount;
		}
		public void setNoRegularCount(int noRegularCount) {
			this.noRegularCount = noRegularCount;
		}
		public int getTotalPeopleCount() {
			return totalPeopleCount;
		}
		public void setTotalPeopleCount(int totalPeopleCount) {
			this.totalPeopleCount = totalPeopleCount;
		}
		public String getShowTime() {
			return showTime;
		}
		public void setShowTime(String showTime) {
			this.showTime = showTime;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		
}