<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
	<style>
		.fileImage{
		  	float: left;
		   display: inline-block;
		   text-align: center;
		   width: 120px;
		   overflow: hidden;
		   margin-right: 5px;
		   height: 120px;
		   margin-bottom: 5px;
		   position: relative;
	       border: 1px solid #ddd;
		}
		.fileImage img{
			width:100%;
			height:100px;
		}
		.fileImage a{
		    position: absolute;
		    bottom: 1px;
		    left: 40%;
		    cursor:pointer;
		}
		.ace-file-input .remove{
			display:none !important;
		}
	</style>
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="breadcrumbs ace-save-state" id="breadcrumbs" style="margin-bottom:100px;">
    		<ul class="breadcrumb">
    			<li>
    				<i class="ace-icon fa fa-home home-icon"></i>
    				<a href="javascript:void(0);" onclick="firstPage()">首页</a>
    			</li>
    
    			<li>生产技术培训管理</li>
    			<li>知识库管理</li>
    			<li class="active">查看</li>
    		</ul><!-- /.breadcrumb -->
    		<div style="margin-right:200px;margin-top:10px;">
        		<button id="backBtnAdd" class="btn btn-xs btn-primary pull-right">
	   				<i class="fa fa-reply"></i>
	   				返回
	   			</button>
       		</div>
    		<h5 class="table-title header smaller blue" style="margin-left:30px;margin-right:20px">基础信息</h5>
    	</div>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:200px;" id="addKnowledgeForm">
					<input class="col-md-12" id="id" name="id" type="hidden" value="${entity.id }">
					<input class="col-md-12" id="" name="createPeopleId" type="hidden" value="${entity.createPeopleId }">
				<div class="form-group" style='padding-left:9.2%'>
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
				<div class="padding-zero text-left">
				    <label class="col-md-2 control-label" style='text-align:right;padding-right:4px;'>
						创建人：
				    </label>
				    </div>
				    <div class="col-md-9 padding-zero text-left">
						<input class="col-md-12" id="createPeopleId" name="" type="text" placeholder="" readOnly="readOnly" maxlength="64" value="${entity.createPeopleName }">
                    </div>
	               </div>
	               <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
	               <div class="padding-zero text-left">
					<label class="col-md-2 control-label" style='text-align:right;padding-right:4px;'>创建时间：</label>
					</div>
                  		<div class="col-md-10 padding-zero text-left">
                   		<input class="col-md-12" id="createDate" name="createDate" type="text" placeholder="" readOnly="readOnly" maxlength="64" value="${entity.showDate }">
                   	</div>
               </div>
				</div>
				
				<div class="form-group" style='padding-left:9.2%'>
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
					<div class="padding-zero text-left">
			       		<label class="col-md-2 control-label" style='text-align:right;padding-right:4px;'>
							<span style="color:red;">*</span>单位名称：
						</label>
						</div>
						<div class="text-left padding-zero col-md-9" >
						   <input class="col-md-12" id="unitIdDiv" name="unitIdDiv" type="text" placeholder="" maxlength="64" readOnly="readOnly" value="${sysUnitEntity}">
                	</div>
	               </div>
	               <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
	               <div class="padding-zero text-left">
					<label class="col-md-2 control-label" style='text-align:right;padding-right:4px;'><span style="color:red">*</span>类型：</label>
                  	</div>
                  		<div class="col-md-10 padding-zero text-left">
                   		<input class="col-md-12" id="knowledgeStyleName" name="knowledgeStyleName" type="text" placeholder="" maxlength="64" readOnly="readOnly" value="${entity.knowledgeStyleName}">	
                   	</div>
               </div>
				</div>
				
				<div class="form-group" style='padding-left:9.2%'>
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
					<div class="padding-zero text-left">
			       		<label class="col-md-2 control-label" style='text-align:right;padding-right:4px;'>
							<span style="color:red;">*</span>主题：
						</label>
						</div>
						<div class="text-left padding-zero col-md-9" >
						   <input name="titleName" placeholder="请输入主题" resize:none;" class="col-md-12" type="text" readOnly="readOnly" maxlength="64" value="${entity.titleName }">
                	</div>
	               </div>
	               <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
	               <div class="padding-zero text-left">
					<label class="col-md-2 control-label" style='text-align:right;padding-right:4px;'>下载次数：</label>
                  	</div>
                  		<div class="col-md-10 padding-zero text-left">
                   		 <input class="col-md-12" id="downloadCount" name="downloadCount" type="text" placeholder="" readOnly="readOnly" maxlength="64" value="${entity.downloadCount }">	
                   	</div>
               </div>
				</div>

	           <div class="form-group">
					<label class="col-md-2 control-label" style='text-align:right;padding-right:4px;'>
						关键字:
					</label>
					<div class="col-md-10">
						<textarea name="keyword" placeholder="请输入关键字" style="height:100px; resize:none;" readOnly="readOnly" class="col-md-12" maxlength="128">${entity.keyword }</textarea>
					</div>
               </div>
	           <div class="form-group">
					<label class="col-md-2 control-label" style='text-align:right;padding-right:4px;'>
						备注:
					</label>
					<div class="col-md-10">
						<textarea name="remark" placeholder="请输入备注" style="height:100px; resize:none;" readOnly="readOnly" class="col-md-12" maxlength="128">${entity.remark }</textarea>
					</div>
               </div>
            </form>
            <div class="page-content" style='padding:0'>
            	<h5 class="table-title-withoutbtn header smaller blue" style="margin-left:30px;margin-right:20px">文献资料</h5>
				<div class="col-md-12" id="fileUpload" style='padding-right:200px'>
					<div class="form-group">
						<label class="col-md-2 control-label" style='text-align:right;padding-right:10px;'>文献资料:</label>
						<div class="col-md-10" style='padding-left:0'>
							<div id="document" name="document"></div>
						</div>
					</div>
				</div>
			</div>
            </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					var appData = ${entityJson};
					var id = ${id};
					//附件上传
					var uploaddropzone=new A.uploaddropzone({
						render : "#document",
						fileId:${entity.document},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true,
						clickCallBack:function(){
							var url =format_url('/knowledgeManagement/downloadCount/'+ id);
								$.ajax({
									url : url,
									contentType : 'application/json',
									dataType : 'JSON',
									type : 'POST',
									success: function(result){
										alert('下载成功');
									},
									error:function(v,n){
										alert('下载失败');
									}
								});
						}
					}).render();
					
					//由添加迁移页返回到列表页
    				$("#backBtnAdd").on("click",function(){
    					window.scrollTo(0,0);
    					A.loadPage({
    						render : '#page-container',
    						url : format_url("/knowledgeManagement/index")
    					});
    				});
    				
				});
			});
			
			
        </script>
    </body>
</html>