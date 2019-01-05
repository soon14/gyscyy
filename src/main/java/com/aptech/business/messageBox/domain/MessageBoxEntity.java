package com.aptech.business.messageBox.domain;

import org.apache.ibatis.type.Alias;
import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 收件箱实体类
 *
 * @author 
 * @created 2017-08-16 09:59:57
 * @lastModified 
 * @history
 *
 */
@Alias("MessageBoxEntity")
public class MessageBoxEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 消息接收人
		 */
    	private String receivePerson;
		/**
		 * 消息内容
		 */
    	private Long contextId;
		/**
		 * 消息状态
		 */
    	private String status;

		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getReceivePerson(){
			return receivePerson;
		}
		public void setReceivePerson(String receivePerson){
			this.receivePerson = receivePerson;
		}
		public Long getContextId(){
			return contextId;
		}
		public void setContextId(Long contextId){
			this.contextId = contextId;
		}
		public String getStatus(){
			return status;
		}
		public void setStatus(String status){
			this.status = status;
		}
}