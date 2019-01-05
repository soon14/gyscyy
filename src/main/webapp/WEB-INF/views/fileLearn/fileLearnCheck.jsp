<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh">
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
    
    			<li>OA管理</li>
    			<li>文件学习</li>
    			<li class="active">文件学习查看</li>
    		</ul><!-- /.breadcrumb -->
    		<div style="margin-right:100px;margin-top:10px;">
        		<button id="backBtnShow" class="btn btn-xs btn-primary pull-right">
    				<i class="fa fa-reply"></i>
    				返回
    			</button>
        	</div>
    		<h5 class="table-title header smaller blue" style="margin-left:10px">文件学习查看</h5>
    	</div>
		<div class="col-md-12"  style="width: 100%;padding-left:0">
			<form class="form-horizontal" role="form"  style="margin-right:140px;" >
			   <input class="col-md-12" id="id" name="id" type="hidden" value="${entity.id}">
			   <input class="col-md-12" id="userId" name="userId" type="hidden" >
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">文件名称  </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="fileName" name="fileName" type="text" placeholder="" maxlength="128" value="${ entity.fileName }"  readonly="readonly" >
                    </div>
                    <label class="col-md-2 control-label no-padding-right">发文单位</label>
				    <div class="col-md-4">
						<input class="col-md-12" id="sendUnitName" name="sendUnitName" type="text" placeholder="" maxlength="128" value="${ entity.sendUnitName }"  readonly="readonly" >
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">发布人</label>
                	<div class="col-md-4">
						<input class="col-md-12" id="sendUserName" name="sendUserName" type="text" placeholder="" maxlength="128" value="${ entity.sendUserName }"  readonly="readonly" >
                    </div>
				    <label class="col-md-2 control-label no-padding-right">发文时间 </label>
                    <div class="col-md-4">
						<input class="col-md-12" id="sendTime" name="sendTime" type="text" placeholder="" maxlength="128" value='<fmt:formatDate value="${entity.sendTime}" />'  readonly="readonly" >
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">学习提醒时间 </label>
                    <div class="col-md-4">
						<input class="col-md-12" id="warnTime" name="warnTime" type="text" placeholder="" maxlength="128" value='<fmt:formatDate value="${entity.warnTime}" />'   readonly="readonly" >
                    </div>
               </div>
                <div class="form-group form-horizontal" >
					<label class="col-md-2 control-label no-padding-right">学习文件</label>
					<div class="col-md-10" id="learnFileDiv">
					</div>
	            </div>
	            <div class="form-group" >
	            	<label class="col-md-2 control-label no-padding-right">全部挂网</label>
					<div class="col-md-1">
						 <input name="isAllNet" type="radio" placeholder="" maxlength="128" value="1"   disabled="true">
					</div>
					<label class="col-md-1 control-label no-padding-right">特定接收人</label>
					<div class="col-md-2">
						<input name="isAllNet" type="radio" placeholder="" maxlength="128" value="0"  disabled="true"> 
					</div>
					<label class="col-md-2 control-label no-padding-right">接收人</label>
                	<div class="col-md-4">
						<input class="col-md-12" id="receiveUser" name="receiveUserNames" type="text" placeholder="" maxlength="128" value="${ entity.receiveUserNames }"  readonly="readonly" >
                    </div>
               </div>
                <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">内容</label>
					<div class="col-md-10">
						<textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128"  readonly="readonly">${entity.remark}</textarea>
					</div>
               </div>
                <div class="form-group" >
					<label class="col-md-2 control-label no-padding-right">附件</label>
					<div class="col-md-10" id="attachmentDiv">
					</div>
	            </div>
            </form>
        	<div id="breadcrumbs" style="margin-bottom:100px;padding-left:10px">
    		<h5 class="table-title header smaller blue">学习信息</h5>
    	    </div>
    	    <form class="form-horizontal" role="form"  style="margin-right:140px;" id="learnInfo">
			   <input class="col-md-12" id="id" name="id" type="hidden" value="${entity.id}">
		      
                <div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>学习心得</label>
					<div class="col-md-10">
						<textarea id="learnFeel" name="learnFeel" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128" >${receiveUserEntity.learnFeel}</textarea>
					</div>
               </div>
                <div class="form-group" >
					<label class="col-md-2 control-label no-padding-right">附件</label>
					<div class="col-md-10" id="learnAttachmentDiv">
					</div>
	            </div>
	            <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">学习人</label>
					<div class="col-md-4">
	                  <!-- <div style="width:500px;display:inline-block">
							<div id="learnUserDiv">
							</div>	
						</div> -->
						<input class="col-md-12" id="learnUserDiv"  type="text" maxlength="128"  readonly="readonly" >
                	</div>
				    <label class="col-md-2 control-label no-padding-right">学习时间 </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="learnTimeId"  type="text" maxlength="128"  readonly="readonly" >
                    </div>
               </div>
            </form>
            <div style="margin-right:100px;">
    			<button id="cancelBtn"  class="btn btn-xs btn-danger pull-right" >
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				提交
    			</button>
    			
    		</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				
				seajs.use(['datatables','confirm','dialog','combobox','combotree','uploaddropzone','my97datepicker','selectbox'], function(A){
					//发布人选择带回
					var entity = ${entityJson};
					var receiveUserJson = ${receiveUserJson};
/* 					//学习人
					var learnUserNamesDiv=new A.selectbox({
						id: 'userName',
						name:'userId',
						title:'人员',
						url:'/sysUser/userSelect?singleSelect=1',
						render:'#learnUserDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data,self){
							var names =[];
							var ids=[];
							for(var i =0; i<data.length; i++){
								names.push(data[i].name);
								ids.push(data[i].id);
							}
							self.setValue(names,ids);
						}
					}).render();
					learnUserNamesDiv.setValueBack("#userName",receiveUserJson.userName,receiveUserJson.userId); 
					//学习时间
					var learnTimeDatePicker = new A.my97datepicker({
						id: 'learnTimeId',
						name: 'learnTime',
						render:'#learnTimeDiv',
						options : {
								isShowWeek : false,
								isShowToday:true,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: 'yyyy-MM-dd HH:mm'
						}
					}).render(); */
					$("#learnTimeId").val(receiveUserJson.learnTime);
					$("#userId").val(receiveUserJson.userId);
					$("#learnUserDiv").val(receiveUserJson.userName);
					//附件上传
					var uploadLearnFile=new A.uploaddropzone({
						render : "#learnFileDiv",
						fileId:${entity.learnFile},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render();
					//附件上传
					var uploadattachment=new A.uploaddropzone({
						render : "#attachmentDiv",
						fileId:${entity.attachment},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render(1);
					//附件上传
					var uploadLearnAttachment=new A.uploaddropzone({
						render : "#learnAttachmentDiv",
						fileId:${receiveUserEntity.attachment},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render(2);
					//单选框
					if(entity.isAllNet=='1'){
						$(":radio[name=isAllNet][value="+1+"]").attr("checked","true");
					}else{

						$(":radio[name=isAllNet][value="+0+"]").attr("checked","true");
					}
					$('#learnInfo').validate({
						debug:true,
						rules:  {
							learnFeel : {required:true}
						},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/fileLearn/saveLearnFeel");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = receiveUserJson;//$("#learnInfo").serializeObject();
							//附件属性
							obj.attachment=JSON.stringify(uploadLearnAttachment.getValue());
							obj.learnFeel=$("#learnFeel").val();
							obj.userId = $("#userId").val();
							if ($("#learnTimeId").val() != "") {
								obj.learnTime = $("#learnTimeId").val();
							}
							//console.log(obj.attachment);
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('保存成功');
									moreFileLearnMessage();
								},
								error:function(v,n){
									alert('保存失败');
								}
							});
						}
					});
					//返回到列表页
    				$("#backBtnShow").on("click",function(){
    					reback();
    				});
    				//保存
					$("#saveBtn").on("click", function(){
						$("#learnInfo").submit();
    				});
    				//返回到列表页
    				$("#cancelBtn").on("click",function(){
    					reback();
    				});
				});
			});
			function reback(){
				A.loadPage({
					render : '#page-container',
					url : format_url("/fileLearn/releaseIndex")
				});
			}
        </script>
    </body>
</html>