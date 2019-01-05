<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="breadcrumbs ace-save-state" id="breadcrumbs">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="javascript:void(0);" onclick="firstPage()">首页</a>
				</li>
				<li>
					检修管理
				</li>
				<li class="active">检修日志</li>
				<li class="active">修改检修日志</li>
			</ul>
		</div>
<div class="page-content" style="margin-left:30px;margin-right:30px;">
		<div style="float:right;margin-right:10px;">
			<button id="btnBack"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style="    margin-top: 14px;">
       			<i class="fa fa-reply"></i>
       			返回
       		</button>
		</div>
	<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >基础信息</h5>
<div class="widget-main no-padding">
			<form class="form-horizontal" role="form"  style="margin-right:210px;margin-top:20px;" id="overhaulLogForm">
<!-- 			     <input id="overhaulLogid" class="col-md-12" name="overhaulLogid"   readonly  type="hidden"/> -->
			     <input id="id" class="col-md-12" name="id"   readonly  value="${overhaulLogEntity.id}" type="hidden" />
			     <input id="overhaulNumber" class="col-md-12" name="overhaulNumber"   value="${overhaulLogEntity.overhaulNumber}"  type="hidden"/>
			     <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>单位名称
                    </label>
					<div class="col-md-4">
					<input id="unitName" class="col-md-12" name="unitName" value="${overhaulLogEntity.unitName}" readonly type="text"/>
					<input id="unitId" class="col-md-12" name="unitId" value="${overhaulLogEntity.unitId}" readonly type="hidden"/>
<!-- 				    	<select id="unitId" name="unitId" ></select> -->
<!--                     	<input id="unitName"  name="unitName"  type="hidden"/> -->
				    </div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;"></span>日志时间
                    </label>
					<div class="col-md-4">
                    	<input id="logDate" class="col-md-12" name="logDate" value="${overhaulLogEntity.logDateString}" readonly type="text"/>
                    </div>
                </div>
		        <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>填报人
                    </label>
					<div class="col-md-4">
                    	<input id="submitUserName" class="col-md-12" name="submitUserName" value="${overhaulLogEntity.submitUserName}" readOnly type="text"/>
                    	<input id="submitUserId" class="col-md-12" name="submitUserId" value="${overhaulLogEntity.submitUserId}" readOnly type="hidden"/>
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
					                检修分类
                    </label>
					<div class="col-md-4">
						<input id="overhaulClass" class="col-md-12" name="overhaulClass" value="${overhaulLogEntity.overhaulClassName}" readonly type="text"/>
<!--                     	<select id="overhaulClass" name="overhaulClass" class="col-md-12 chosen-select" name="chargeId" data-placeholder="请选择检修分类"></select> -->
                    </div>
                </div>
                <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
						   请假人员
						</label>
						<div class="col-md-4">
							 <select id="askPersonId"  name="askPersonId"></select>
	                	</div>
                 </div>
                <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						附件
                    </label>
					<div class="col-md-10" id="divfile">
                    </div>
                </div>
        </form>   
       <div class="col-xs-12" style="margin-top:50px;">	
			<div class="widget-main no-padding">
		   		<h5 class='table-title header smaller blue'>工作安排</h5>			
				<table id="overhaulArrange-table" class="table table-striped table-bordered table-hover" style="width:100%;margin-bottom:0px;">
					<thead>
						<tr>
							<th style="display:none;">主键</th>
							<th class="center sorting_disabled" style="width:50px;">
     								<label class="pos-rel">
     									<input type="checkbox" class="ace" />
     									<span class="lbl"></span>
     								</label>
        					</th>
							<th>序号</th>
	                      	<th>负责人</th>
	                      	<th>完成状态</th>
	                      	<th>工作人员</th>
	                      	<th>备注</th>
	                      	<th>操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
        </div>
        <div class="col-xs-12" style="margin-right:100px;margin-top:20px;">
   			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
   				<i class="ace-icon fa fa-floppy-o"></i>
   				保存
   			</button>
   		</div>
   		<div class="col-xs-12" style="height:50px;">
   		</div>	
</div>
		<script type="text/javascript">
		var loginUserId = '${userEntity.id}';
		var overhaullogDate = "${overhaulLogEntity.logDateString}";
		var companyId;
		var unitId;
		var dutyUsersId;
		var overhaulLogId = ${id};
		var overhaulArrangeDatatables;
			jQuery(function($) {
				seajs.use(['datatables','combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					var tempOverhaulLogId=${id};
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${overhaulLogEntity.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					
					//请假人员
					askUsersId = new A.combobox({
						render : "#askPersonId",
						datasource : ${dutyUsers},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					askUsersId.setValue(${overhaulLogEntity.askPersonId})
					
					//检修单位
// 					 unitId = new A.combobox({
// 						render : "#unitId",
// 						datasource : ${unitList},
// 						allowBlank: true,
// 						options : {
// 							"disable_search_threshold" : 10
// 						}
// 					}).render();
// 					unitId.setValue(${overhaulLogEntity.unitId});
// 					$("#unitName").val('${overhaulLogEntity.unitName}');
					
					//检修分类
// 					var overhaulClass = new A.combobox({
// 						render : "#overhaulClass",
// 						datasource : ${overhaulClass},
// 						allowBlank: true,
// 						options : {
// 							"disable_search_threshold" : 10
// 						}
// 					}).render();
// 					overhaulClass.setValue('${overhaulLogEntity.overhaulClass}')
					
					//工作安排
					overhaulArrangeDatatables = new A.datatables({
						render: '#overhaulArrange-table',
						options: {
					        "ajax": {
					            "url": format_url("/overhaulArrange/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "async":false, 
					            "data": function (d) {
					            	conditions = [];
					            	if(tempOverhaulLogId){
					            		conditions.push({
											field: 'O.C_OVERHAUL_LOG_ID',
					    					fieldType:'INT',
					    					matchType:'EQ',
					    					value:tempOverhaulLogId
					    				});
					            	}else{
					            		conditions.push({
											field: 'O.C_OVERHAUL_LOG_ID',
					    					fieldType:'INT',
					    					matchType:'EQ',
					    					value:-1
					    				});
					            	}
					            	conditions.push({
										field: 'O.C_STATUS',
				    					fieldType:'STRING',
				    					matchType:'EQ',
				    					value:'1'
				    				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false},
							          {orderable: false,"width":"5%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	}},
									  {data: "dutyPersonName",width: "25%",orderable: true},  
									  {data: "finishStatusString",name:"finishStatus",width: "10%",orderable: true}, 
									  {data: "askPersonName",width: "25%",orderable: true}, 
									  {data: "remark",width: "30%",orderable: true}],
						    fnInfoCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
		                           if(iTotal==0){
		                                $("#overhaulArrange-table .odd td").attr("colspan",7);
		                           }
		                    },
							toolbars: [{
								label:"新增",
								icon:"glyphicon glyphicon-plus",
								className:"btn-success",
								events:{
									click: function(event){
										listFormDialog = new A.dialog({
	                						width: 850,
	                						height: 840,
	                						title: "工作安排新增",
	                						url:format_url('/overhaulArrange/getAdd'),
	                						closed: function(){
	                							overhaulArrangeDatatables.draw(false);
	                						}	
	                					}).render()
									}
								}
							}],
							btns:[{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green",
								render: function(btnNode, data){
									if(isHidden(data.createDateString)){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										listFormDialog = new A.dialog({
	                						width: 850,
	                						height: 840,
	                						title: "工作安排修改",
	                						url:format_url('/overhaulArrange/getEdit/'+id),
	                						closed: function(){
	                							overhaulArrangeDatatables.draw(false);
	                						}	
	                					}).render()
									}
								}
							},{
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								render: function(btnNode, data){
									if(isHidden(data.createDateString)){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										url =format_url('/overhaulArrange/deleteOnlyOne/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										success: function(result){
        											alert('删除成功');
        											overhaulArrangeDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
							},{
								id: "addDetail",
								label:"查看",
								icon: "fa fa-binoculars bigger-130",
								className: "blue ",
								render: function(btnNode, data){
									if(isHidden(data.createDateString)){
										btnNode.hide();
									}
									
									var dataRole=data.dutyPersonId;
									var sysUserId=${sysUserId};
									if(dataRole!=sysUserId){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										listFormDialog = new A.dialog({
	                						width: 850,
	                						height: 840,
	                						title: "工作安排查看",
	                						url:format_url('/overhaulArrange/getDetail/'+id),
	                						closed: function(){
	                							overhaulArrangeDatatables.draw(false);
	                						}	
	                					}).render()
									}
								}
// 								events:{
// 									click: function(event, nRow, nData){
// 										var overhaulArrangeId = nData.id;
// 										A.loadPage({
// 											render : '#page-container',
// 											url:format_url('/overhaulArrange/getEditMore/'+overhaulLogId+'/'+overhaulArrangeId+'/'+overhaullogDate),
// 										});
// 									}
// 								}
							 },{
									id: "Detail",
									label:"查看",
									icon: "fa fa-binoculars  bigger-130",
									className: "blue ",
									render: function(btnNode, data){
										if(!isHidden(data.createDateString)){
											btnNode.hide();
										}
									},
									events:{
										click: function(event, nRow, nData){
											var id = nData.id;
											listFormDialog = new A.dialog({
		                						width: 850,
		                						height: 840,
		                						title: "工作安排查看",
		                						url:format_url('/overhaulArrange/getDetail/'+id),
		                						closed: function(){
		                							overhaulArrangeDatatables.draw(false);
		                						}	
		                					}).render()
										}
									}
// 									events:{
// 										click: function(event, nRow, nData){
// 											var overhaulArrangeId = nData.id;
// 											A.loadPage({
// 												render : '#page-container',
// 												url:format_url('/overhaulArrange/getDetailMore/'+overhaulLogId+'/'+overhaulArrangeId+'/'+overhaullogDate),
// 											});
// 										}
// 									}
								 }
						  ]
						}
					}).render();
					$('#overhaulLogForm').validate({
						debug:true,
						rules:  {							
							unitId:{required:true},
							dutyUserId:{required:true},
						},
						submitHandler: function (form) {
							$("#saveBtn").attr("disabled",true);
							//添加按钮
							var url = format_url("/overhaulLog/overhaulLogEdit");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#overhaulLogForm").serializeObject();
							obj.logDate = obj.logDate+" 00:00:00";
							//附件上传
							obj.fileId=JSON.stringify(uploaddropzone.getValue());
							if(overhaulLogId){
								obj.overhaulLogId = overhaulLogId;
							}
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									tempOverhaulLogId = overhaulLogId = result.data.id;
									$("#page-container").load(format_url('/overhaulLog/index'));
									alert('添加成功');
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					//保存按钮
					$("#saveBtn").on("click", function(){
						$("#overhaulLogForm").submit();
					});
					//返回
					$("#btnBack").on("click", function(e){
						window.scrollTo(0,0);
						$("#page-container").load(format_url('/overhaulLog/index'));
					});
				});
			});
			//验证是否到了封存时间
			function isHidden(plogDate){
				var logDate = new Date(plogDate).getTime();
				var myDate = new Date();
				var nowDate = myDate.getTime();
				//一天毫秒值
				var firstDay = 24*3600*1000;
				//五天毫秒值
				var fifthDay = 5*24*3600*1000
				return nowDate-logDate>fifthDay;
			}
        </script>
    </body>
</html>