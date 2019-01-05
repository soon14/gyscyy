package com.aptech.business.fileLearnAttachment.domain;

import org.apache.ibatis.type.Alias;
import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 文件学习附件实体类
 *
 * @author 
 * @created 2018-04-10 16:34:05
 * @lastModified 
 * @history
 *
 */
@Alias("FileLearnAttachmentEntity")
public class FileLearnAttachmentEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 名称
		 */
    	private String name;
		/**
		 * 路径
		 */
    	private String url;
		/**
		 * 是否是学习文件
		 */
    	private String isLearnFile;
		/**
		 * 文件学习id
		 */
    	private Long fileLearnId;

		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getName(){
			return name;
		}
		public void setName(String name){
			this.name = name;
		}
		public String getUrl(){
			return url;
		}
		public void setUrl(String url){
			this.url = url;
		}
		public String getIsLearnFile(){
			return isLearnFile;
		}
		public void setIsLearnFile(String isLearnFile){
			this.isLearnFile = isLearnFile;
		}
		public Long getFileLearnId(){
			return fileLearnId;
		}
		public void setFileLearnId(Long fileLearnId){
			this.fileLearnId = fileLearnId;
		}
}