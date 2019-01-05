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
    			<li class="active">添加</li>
    		</ul><!-- /.breadcrumb -->
    		<div style="margin-right:200px;margin-top:10px;">
        		<button id="backBtnAdd" class="btn btn-xs btn-primary pull-right">
	   				<i class="glyphicon glyphicon-share-alt"></i>
	   				返回
	   			</button>
       		</div>
    		<h5 class="table-title header smaller blue" style="margin-left:30px;margin-right:20px">基础信息</h5>
    	</div>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:200px;" id="addKnowledgeForm">
							<input class="col-md-12" id="id" name="id" type="hidden" value="">
							<input class="col-md-12" id="" name="createPeopleId" type="hidden" value="${sysUserEntity.id }">
				<div class="form-group" style='padding-left:9.2%'>
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
					<div class="padding-zero text-left">
				    <label class="col-md-2 control-label" style='text-align:right;padding-right:4px;'>
						创建人：
				    </label>
				    </div>
				    <div class="col-md-9 padding-zero text-left">
						<input class="col-md-12" id="createPeopleId" name="" type="text" placeholder="" readOnly="readOnly" maxlength="64" value="${sysUserEntity.name }">
                    </div>
	               </div>
	               <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
	               <div class="padding-zero text-left">
					<label class="col-md-2 control-label" style='text-align:right;padding-right:4px;'>创建时间：</label>
                  	</div>
                  		<div class="col-md-10 padding-zero text-left">
                   		<input class="col-md-12" id="createDate" name="createDate" type="text" placeholder="" readOnly="readOnly" maxlength="64" value="">
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
						   <div id="searchunitId" class="padding-zerocol-md-12"></div>
                	</div>
	               </div>
	               <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
	               <div class="padding-zero text-left">
					<label class="col-md-2 control-label" style='text-align:right;padding-right:4px;'><span style="color:red">*</span>类型：</label>
                  	</div>
                  		<div class="col-md-10 padding-zero text-left">
                   		<select class="col-md-12 chosen-select" id="knowledgeType" name="knowledgeStyle"></select>
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
						   <input name="titleName" placeholder="请输入主题" resize:none;" class="col-md-12" type="text" maxlength="64" value="">
                	</div>
	               </div>
<!-- 	               <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12"> -->
<!-- 	               <div class="padding-zero text-left"> -->
<!-- 					<label class="col-md-2 control-label" style='text-align:right;padding-right:4px;'>下载次数：</label> -->
<!--                   	</div> -->
<!--                   		<div class="col-md-10 padding-zero text-left"> -->
<!--                    		 <input class="col-md-12" id="downloadCount" name="downloadCount" type="text" placeholder="" readOnly="readOnly" maxlength="64" value=""> -->
<!--                    	</div> -->
<!--                </div> -->
				</div>
		       
	           <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						关键字:
					</label>
					<div class="col-md-10">
						<textarea name="keyword" placeholder="请输入关键字" style="height:100px; resize:none;" class="col-md-12" maxlength="128"></textarea>
					</div>
               </div>
	           <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						备注:
					</label>
					<div class="col-md-10">
						<textarea name="remark" placeholder="请输入备注" style="height:100px; resize:none;" class="col-md-12" maxlength="128"></textarea>
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
    		<div style="margin-right:215px;">
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right" >
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					//单位名称树
					var unitIdCombotree = new A.combotree({
						render: "#searchunitId",
						name: 'unitId',
						//返回数据待后台返回TODO
						datasource: ${unitNameIdTreeList},
						width:"210px",
						options: {
							treeId: 'unitId',
							data: {
								key: {
									name: "name"
								},
								simpleData: {
									enable: true,
									idKey: "id",
									pIdKey: "parentId"
								}
							}
						}
					}).render();
					
					//获取当月 yyyy-MM-dd
						var date = new Date();
						var seperator1 = "-";
						var month = date.getMonth() + 1;
						var day = date.getDate();
						var strDate = date.getDate();
						if (month >= 1 && month <= 9) {
							month = "0" + month;
						}
						if (day >= 0 && day <= 9) {
							day = "0" + day;
						}
						var currentdate = date.getFullYear() + seperator1 + month + seperator1 + day
					//创建时间赋值
					$("#createDate").val(currentdate);
					//知识库类型
					var knowledgeTypeCombobox = new A.combobox({
						render: "#knowledgeType",
						datasource:${knowledgeTypeCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					//附件上传
					var uploaddropzone=new A.uploaddropzone({
						render : "#document",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					
					$('#addKnowledgeForm').validate({
						debug:true,
						rules:  {
							unitId:{required:true,maxlength:20},
							knowledgeStyle:{required:true,maxlength:20},
							titleName:{required:true,maxlength:64}
						},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/knowledgeManagement/saveAddPage");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addKnowledgeForm").serializeObject();
// 							var uploadFileList = [];
// 							var node = $("#fileDiv").children(".fileImage");
// 							node.each(function(){
// 								var uploadFileObj = {};
// 								var src = $(this).children("img:first").attr("src");
// 								var fileId = $(this).children(".idValue").val();
// 								var str = src.substr(src.length-7,src.length);
// 								var fileAddress = $(this).children(".fileAddress").val();
// 								uploadFileObj.fileAddress = fileAddress;
// 								uploadFileObj.id = fileId;
// 								uploadFileObj.fileName = fileAddress.substring(fileAddress.lastIndexOf("\/") + 1, fileAddress.length);
// 								uploadFileObj.fileSuffix = fileAddress.substring(fileAddress.lastIndexOf(".") + 1, fileAddress.length);
// 								//uploadFileObj.techDataId = id;
// 								uploadFileList.push(uploadFileObj);
// 							});
// 							if(uploadFileList.length==0){
// 								alert("请选择上传文件！");
// 								return;
// 							}
// 							obj.fileList = uploadFileList;
							obj.document=JSON.stringify(uploaddropzone.getValue());
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('添加成功');
									A.loadPage({
										render : '#page-container',
										url : format_url('/knowledgeManagement/index')
									});
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					$("#saveBtn").on("click", function(){
						$("#addKnowledgeForm").submit();
    				});
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