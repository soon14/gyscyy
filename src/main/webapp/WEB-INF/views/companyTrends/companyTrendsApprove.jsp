<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="col-md-12" style='height:560px;overflow:auto;'>
			<form class="form-horizontal" role="form"  style="margin-right:200px;" id="addForm">
			<input id="id" class="col-md-12" name="id"   readonly  type="hidden" value="${entity.id }"/>
			<input id="uploadPersonid" class="col-md-12" name="uploadPerson"   readonly  type="hidden" value="${sysUserEntity.name }"/>
			<input id="uploadTimeid" class="col-md-12" name="uploadTime"   readonly  type="hidden"/>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>标题：
					</label>
					<div class="col-md-10">
	                   <input class="col-md-12" id="titleId" name="title" type="text" placeholder="" maxlength="20" readonly value="${entity.title }">
                	</div>
               	</div>
               	<div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						副标题：
				    </label>
				    <div class="col-md-10">
						<input class="col-md-12" id="subTitleId" name="subTitle" type="text" placeholder="" maxlength="256" readonly value="${entity.subTitle }">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>发布时间：</label>
                 		<div class="col-md-4">
                  		<input id="publishTimeDiv" class="col-md-12" readonly type="text" value="${entity.publishTimeShow}">
                  		</div>
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>结束时间：</label>
                 		<div class="col-md-4">
                  		<input id="endTimeDiv" class="col-md-12" readonly type="text" value="${entity.endTimeShow}">
                  		</div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">是否上推荐位</label>
					<div class="col-md-10">
					<input id = "commendCheckbox" name="" type="checkbox" disabled="disabled" value="" />
					</div>
               </div>
		       <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">推荐位图片</label>
							<div class="col-md-10" id="commendPicturefile"></div>
               </div>
<!-- 		       <div class="form-group"> -->
<!-- 						<label class="col-md-2 control-label no-padding-right">首页图片</label> -->
<!-- 							<div class="col-md-10" id="homePagePicturefile"></div> -->
<!--                </div> -->
               <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>内容
					</label>
					<div class="col-md-10">
	                   <textarea class="col-md-12" id="contentId" name="content"style="height:80px; resize:none;" readonly placeholder="" maxlength="128" >${entity.content }</textarea>
                	</div>
               </div>
               <label class="col-md-2 control-label no-padding-right" style="font-size:5px;">
				<span style="color:red;">*</span>预览内容
				</label>
               <div class="form-group" style="margin-left:17.4%;">
					
					<div class="col-md-10" id="previewDiv" name = "previewName" style="width: 1200px;">
                	</div>
               </div>
		       <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">附件</label>
							<div class="col-md-10" id="fileAddressfile"></div>
               </div>
		</div>
		</form>
		<div style="margin-right:2.6%;margin-top: 20px;">
	
			<c:forEach items="${nodeList}" var="nodeList" varStatus="vs">
				<c:if test="${nodeList.authFactorCode=='ztjGsdtBtn'}">
					<button id="ztjGsdtBtn" class="btn btn-xs btn-success pull-right"
						style="margin-right:15px;" type="button">
						<i class="ace-icon glyphicon glyphicon-ok"></i> 再提交
					</button>
				</c:if>
	
				<c:if test="${nodeList.authFactorCode=='zjGsdtBtn'}">
					<button id="zjGsdtBtn" class="btn btn-xs btn-success pull-right"
						style="margin-right:15px;">
						<i class="ace-icon fa fa-floppy-o"></i> 终结
					</button>
				</c:if>
				<c:if test="${nodeList.authFactorCode=='xyGsdtBtn'}">
					<button id="xyGsdtBtn" class="btn btn-xs btn-success pull-right"
						style="margin-right:15px;" type="button">
						<i class="ace-icon glyphicon glyphicon-level-up"></i> 需要党群部审核
					</button>
				</c:if>
				<c:if test="${nodeList.authFactorCode=='bmldBhGsdtBtn'}">
					<button id="bmldBhGsdtBtn" title="部门领导驳回" class="btn btn-xs btn-danger pull-right"
						style="margin-right:15px;">
						<i class="ace-icon glyphicon glyphicon-remove-circle"></i> 驳回
					</button>
				</c:if>
				<c:if test="${nodeList.authFactorCode=='dqbBhBtn'}">
					<button id="dqbBhBtn" title="党群部驳回" class="btn btn-xs btn-danger pull-right"
						style="margin-right:15px;">
						<i class="ace-icon glyphicon glyphicon-remove-circle"></i> 驳回
					</button>
				</c:if>
				<c:if test="${nodeList.authFactorCode=='tyGsdtBtn'}">
					<button id="tyGsdtBtn" class="btn btn-xs btn-success pull-right"
						style="margin-right:15px;">
						<i class="ace-icon fa fa-floppy-o"></i> 同意
					</button>
				</c:if>
			</c:forEach>
	
			</div>
		<script type="text/javascript">
			var listFormDialog,approveIdea;
			jQuery(function($) {
				seajs.use(['combobox','combotree','uploaddropzone','my97datepicker','wysiwyg'], function(A){
					var appData = ${entityJson};
					var id = ${id};
					//checkbox赋值
					var commendStatus = ${entity.commendStatus};
					if(true == commendStatus){
						$("#commendCheckbox").attr("checked","checked");
					}
					//推荐位图片
					var commendPictureZone =new A.uploaddropzone({
						render : "#commendPicturefile",
						fileId:${entity.commendPicture},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						acceptedFiles:".png,.jpg",//指定上传文件类型
						chargeUp:true,
						options:{
							maxFiles:1,
						}
					}).render('1');
					//首页图片
// 					var homePagePictureZone =new A.uploaddropzone({
// 						render : "#homePagePicturefile",
// 						fileId:${entity.homePagePicture},
// 						autoProcessQueue:true,//是否自动上传
// 						addRemoveLinks : false,//显示删除按钮
// 						acceptedFiles:".png,.jpg",//指定上传文件类型
// 						chargeUp:true,
// 						options:{
// 							maxFiles:1,
// 						}
// 					}).render('2');
					//附件
					var fileAddressZone =new A.uploaddropzone({
						render : "#fileAddressfile",
						fileId:${entity.fileAddress},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true,
					}).render('3');
					
					//预览内容填写
					var contentWysiwyg = new A.wysiwyg({
						render : "#previewDiv",
						options:{
							height:"400px",
							toolbar:[
										'font',
										null,
										'fontSize',
										null,
										{name:'bold', className:'btn-info'},
										{name:'italic', className:'btn-info'},
										{name:'strikethrough', className:'btn-info'},
										{name:'underline', className:'btn-info'},
										null,
										{name:'insertunorderedlist', className:'btn-success'},
										{name:'insertorderedlist', className:'btn-success'},
										{name:'outdent', className:'btn-purple'},
										{name:'indent', className:'btn-purple'},
										null,
										{name:'justifyleft', className:'btn-primary'},
										{name:'justifycenter', className:'btn-primary'},
										{name:'justifyright', className:'btn-primary'},
										{name:'justifyfull', className:'btn-inverse'},
										null,
										//	{name:'createLink', className:'btn-pink'},
										//	{name:'unlink', className:'btn-pink'},
										//	null,
										//{name:'insertImage', className:'btn-success'},
										null,
										'foreColor',
										null,
										{name:'undo', className:'btn-grey'},
										{name:'redo', className:'btn-grey'}
								    ],
							editable:false,
						}
					}).render();
					contentWysiwyg.setValue(appData.preview);
					
					//需要党群部审核(部门领导)
    				$("#xyGsdtBtn").on("click", function(e){
    					var urls=format_url('/companyTrends/submitPersonLeader/'+$("#taskId").val());
						var obj = $("#addForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						listFormDialog = new A.dialog({
							width: 850,
							height: 531,
							title: "选择党群部人员",
							url:urls,
							closed: function(){
								if(listFormDialog.resule){
								 	obj.userList=listFormDialog.resule.join(",");
								 	obj.approveIdea=approveIdea;
									$.ajax({
										url : format_url("/companyTrends/leader"),
										contentType : 'application/json',
										dataType : 'JSON',
										type : 'POST',
										data : JSON.stringify(obj),
										success: function(result){
											if(result.result=="success"){
												alert("审批成功");
												window.scrollTo(0,0);
												A.loadPage({
													render : '#page-container',
													url : format_url("/todoTask/list/1/10")
												});
											}else{
												alert(result.errorMsg);
											}
										},
										error:function(v,n){
											alert("审批失败");
										}
									});
								}
							}
						}).render();
    				});
					
    				//驳回（部门领导）
    				$("#bmldBhGsdtBtn").on("click", function(e){
    					$("#bmldBhGsdtBtn").attr({"disabled":"disabled"});
    					var obj = $("#addForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					$.ajax({
							url : format_url("/companyTrends/reject/1"),
							contentType : 'application/json',
							dataType : 'JSON',
							type : 'POST',
							data : JSON.stringify(obj),
							success: function(result){
								if(result.result=="success"){
									alert("审批成功");
									window.scrollTo(0,0);
									A.loadPage({
										render : '#page-container',
										url : format_url("/todoTask/list/1/10")
									});
								}else{
									alert(result.errorMsg);
								}
							},
							error:function(v,n){
								alert("审批失败");
							}
						});
    				});
    				//驳回（党群部）
    				$("#dqbBhBtn").on("click", function(e){
    					$("#dqbBhBtn").attr({"disabled":"disabled"});
    					var obj = $("#addForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					$.ajax({
							url : format_url("/companyTrends/reject/2"),
							contentType : 'application/json',
							dataType : 'JSON',
							type : 'POST',
							data : JSON.stringify(obj),
							success: function(result){
								if(result.result=="success"){
									alert("审批成功");
									window.scrollTo(0,0);
									A.loadPage({
										render : '#page-container',
										url : format_url("/todoTask/list/1/10")
									});
								}else{
									alert(result.errorMsg);
								}
							},
							error:function(v,n){
								alert("审批失败");
							}
						});
    				});
    				
    				//再次提交
    				$("#ztjGsdtBtn").on("click", function(e){
    					var urls=format_url('/companyTrends/submitPersonLeaderForProposer/'+$("#taskId").val()+"/"+$("#processStatus").val());
						var obj = $("#addForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						listFormDialog = new A.dialog({
							width: 850,
							height: 531,
							title: "流程再次提交",
							url:urls,
							closed: function(){
									$.ajax({
										url : format_url("/companyTrends/submitAgain"),
										contentType : 'application/json',
										dataType : 'JSON',
										type : 'POST',
										data : JSON.stringify(obj),
										success: function(result){
											if(result.result=="success"){
												alert("审批成功");
												window.scrollTo(0,0);
												A.loadPage({
													render : '#page-container',
													url : format_url("/todoTask/list/1/10")
												});
											}else{
												alert(result.errorMsg);
											}
										},
										error:function(v,n){
											alert("审批失败");
										}
									});
							}
						}).render();
    				});
    				
    				//终结流程
    				$("#zjGsdtBtn").on("click", function(e){
    					$("#zjGsdtBtn").attr({"disabled":"disabled"});
						var obj = $("#addForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
							$.ajax({
								url : format_url("/companyTrends/success"),
								contentType : 'application/json',
								dataType : 'JSON',
								type : 'POST',
								data : JSON.stringify(obj),
								success: function(result){
									if(result.result=="success"){
										alert("审批成功");
										window.scrollTo(0,0);
										A.loadPage({
											render : '#page-container',
											url : format_url("/todoTask/list/1/10")
										});
									}else{
										alert(result.errorMsg);
									}
								},
								error:function(v,n){
									alert("审批失败");
								}
							});
    				});
    				//同意流程
    				$("#tyGsdtBtn").on("click", function(e){
    					$("#tyGsdtBtn").attr({"disabled":"disabled"});
						var obj = $("#addForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
							$.ajax({
								url : format_url("/companyTrends/success"),
								contentType : 'application/json',
								dataType : 'JSON',
								type : 'POST',
								data : JSON.stringify(obj),
								success: function(result){
									if(result.result=="success"){
										alert("审批成功");
										window.scrollTo(0,0);
										A.loadPage({
											render : '#page-container',
											url : format_url("/todoTask/list/1/10")
										});
									}else{
										alert(result.errorMsg);
									}
								},
								error:function(v,n){
									alert("审批失败");
								}
							});
    				});
					
				});
			});
			
        </script>
    </body>
</html>