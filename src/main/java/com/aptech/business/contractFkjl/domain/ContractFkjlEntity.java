package com.aptech.business.contractFkjl.domain;

import java.util.Date;
import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.ibatis.type.Alias;
import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 合同付款记录实体类
 *
 * @author 
 * @created 2018-09-11 15:39:29
 * @lastModified 
 * @history
 *
 */
@Alias("ContractFkjlEntity")
public class ContractFkjlEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 未付合同金额
		 */
    	private String wfhtje;
		/**
		 * 序号
		 */
    	private String xh;
		/**
		 * 创建人
		 */
    	private String createUserId;
		/**
		 * 付款金额
		 */
    	private String fkje;
		/**
		 * 创建时间
		 */
    	private Date createDate;
		/**
		 * 付款时间
		 */
    	private Date fksj;
		/**
		 * 状态
		 */
    	private String status;
		/**
		 * 主表id
		 */
    	private String contractManageID;
		/**
		 * 备用1
		 */
    	private String falg1;
		/**
		 * 备用2
		 */
    	private String flag2;
		/**
		 * 备用3
		 */
    	private String flag3;

		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getWfhtje(){
			return wfhtje;
		}
		public void setWfhtje(String wfhtje){
			this.wfhtje = wfhtje;
		}
		public String getXh(){
			return xh;
		}
		public void setXh(String xh){
			this.xh = xh;
		}
		public String getCreateUserId(){
			return createUserId;
		}
		public void setCreateUserId(String createUserId){
			this.createUserId = createUserId;
		}
		public String getFkje(){
			return fkje;
		}
		public void setFkje(String fkje){
			this.fkje = fkje;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getCreateDate(){
			return createDate;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setCreateDate(Date createDate){
			this.createDate = createDate;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getFksj(){
			return fksj;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setFksj(Date fksj){
			this.fksj = fksj;
		}
		public String getStatus(){
			return status;
		}
		public void setStatus(String status){
			this.status = status;
		}
		public String getContractManageID(){
			return contractManageID;
		}
		public void setContractManageID(String contractManageID){
			this.contractManageID = contractManageID;
		}
		public String getFalg1(){
			return falg1;
		}
		public void setFalg1(String falg1){
			this.falg1 = falg1;
		}
		public String getFlag2(){
			return flag2;
		}
		public void setFlag2(String flag2){
			this.flag2 = flag2;
		}
		public String getFlag3(){
			return flag3;
		}
		public void setFlag3(String flag3){
			this.flag3 = flag3;
		}
}