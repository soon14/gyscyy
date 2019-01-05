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
		<div class="col-md-12"  style="width: 90%;margin-left: 20px;">
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
			   <input class="col-md-12" id="id" name="id" type="hidden" value="${entity.id}">
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
                	
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">发布人</label>
                	<div class="col-md-4">
						<input class="col-md-12" id="sendUserName" name="sendUserName" type="text" placeholder="" maxlength="128" value="${ entity.sendUserName }"  readonly="readonly" >
                    </div>
				    <label class="col-md-2 control-label no-padding-right">发文时间 </label>
                    <div class="col-md-4">
						<input class="col-md-12" id="sendTime" name="sendTime" type="text" placeholder="" maxlength="128" value='<fmt:formatDate value="${entity.sendTime}"  pattern="yyyy-MM-dd HH:mm" />'  readonly="readonly" >
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">学习提醒时间 </label>
                    <div class="col-md-4">
						<input class="col-md-12" id="warnTime" name="warnTime" type="text" placeholder="" maxlength="128" value='<fmt:formatDate value="${entity.warnTime}"  pattern="yyyy-MM-dd HH:mm" />'   readonly="readonly" >
                    </div>
               </div>
		       <div class="form-group">
                    <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>是否通知 </label>
				    <div class="col-md-4">
						<label class="control-label no-padding-right"> 
						<c:if test="${entity.isNotification=='1'}">
									<input    type="checkbox"   checked="checked"  value="1"   class="ace input"   disabled="disabled"/> 
									&nbsp;<span class="lbl bigger-100"></span>
						</c:if>
						<c:if test="${entity.isNotification!='1'}">
									<input    type="checkbox"     value="0"  class="ace input"  disabled="disabled" /> 
									&nbsp;<span class="lbl bigger-100"></span>
						</c:if>
						</label>
					</div>
                    <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>是否重要 </label>
				    <div class="col-md-4">
						<label class="control-label no-padding-right"> 
						<c:if test="${entity.isImportant=='1'}">
									<input    type="checkbox"   checked="checked"  value="1"   class="ace input"   disabled="disabled"/> 
									&nbsp;<span class="lbl bigger-100"></span>
						</c:if>
						<c:if test="${entity.isImportant!='1'}">
									<input    type="checkbox"     value="0"  class="ace input"  disabled="disabled" /> 
									&nbsp;<span class="lbl bigger-100"></span>
						</c:if>
						</label>
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
            <div class="row" id ="fileLearnApprove_table"  style="width: 91%;margin-left: 70px;">
				
               
            </div>
        	<div class="row">
				<div class="col-xs-12" style="width: 90%;margin-left: 80px;">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' style='margin-bottom:0px!important'>学习信息</h5>
						<table id="fileLearnReceive_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>接收人</th>
	                                <th>是否学习</th>
	                                <th>学习心得</th>
	                                <th>学习时间</th>
	                                <th>操作</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
               
            </div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				
				seajs.use(['datatables','confirm','dialog','combobox','combotree','uploaddropzone','my97datepicker','selectbox'], function(A){
					//发布人选择带回
					var entity = ${entityJson};
					
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
					//单选框
					if(entity.isAllNet=='1'){
						$(":radio[name=isAllNet][value="+1+"]").attr("checked","true");
					}else{

						$(":radio[name=isAllNet][value="+0+"]").attr("checked","true");
					}
					//审批信息
					if( '${todoTaskEntity.id_}' != ''){
						A.loadPage({
							render : fileLearnApprove_table,
							url : format_url("/todoTask/processDetail/"+'${todoTaskEntity.id_}'+"/"+'${todoTaskEntity.procInstId}')
						});
					}
					
					/* var fileLearnApproveDatatables = new A.datatables({
						render: '#fileLearnApprove_table',
						options: {
							"ajax": {
					            "url": format_url("/todoTask/processDetail/"+${todoTaskEntity.id_}+"/"+${todoTaskEntity.procInstId}),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions = [];
									d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
							serverSide: true,
					        multiple : true,
					        checked: false,
							ordering:true,
							optWidth: 80,
							columns: [
							          {data: "approvalResult",width: "auto",orderable: true}, 
							          {data: "cname",width: "auto",orderable: true}, 
							          {data: "approvalComment",width: "auto",orderable: true}, 
							          {data: "endTime",width: "auto",orderable: true}, 
							          ]
						}
					}).render(); */
					//接收人
					var fileLearnReceiveDatatables = new A.datatables({
						render: '#fileLearnReceive_table',
						options: {
							"ajax": {
					            "url": format_url("/filelearnReceiveUser/searchData"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions = [];
 					            	//默认查询条件
									conditions.push({
										field: 'C_FILE_LEARN_ID',
			        					fieldType:'STRING',
			        					matchType:'EQ',
			        					value:${entity.id}
			        				});
									d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
							serverSide: true,
					        multiple : true,
					        checked: false,
							ordering:true,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "userName",width: "auto",orderable: true}, 
							          {data: "isLearnDic",width: "auto",orderable: true}, 
							          {data: "learnFeel",width: "auto",orderable: true}, 
							          {data: "learnTime",width: "auto",orderable: true}, 
							          ],
					        btns: [{
								id: "show",
								label:"查看",
								icon: "fa fa-binoculars bigger-130",
								className: "blue search",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										listFormDialog = new A.dialog({
											width: '850',
											height: '471',
											title: "学习信息查看",
											url:format_url('/fileLearn/getReceiverShow/' + id),
											closed: function(){
												//contractManageDatatables.draw(false);
											}
										}).render();
										/*  var id = nData.id;
										A.loadPage({
											render : '#page-container',
											url : format_url('/fileLearn/getReceiverShow/' + id)
										}); */
									}
								}
							}]
						}
					}).render();
					//返回到列表页
    				$("#backBtnShow").on("click",function(){
    					reback();
    				});
				});
			});
			function reback(){
				A.loadPage({
					render : '#page-container',
					url : format_url("/fileLearn/index")
				});
			}
        </script>
    </body>
</html>