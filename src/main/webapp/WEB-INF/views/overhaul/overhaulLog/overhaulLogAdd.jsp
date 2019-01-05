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
				<li class="active">检修日志新增</li>
			</ul>
		</div>
<div class="page-content" style="margin-left:30px;margin-right:30px;">
		<div style="float:right;margin-right:10px;">
			<button id="btnBack"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style="margin-top: 14px;">
       			<i class="fa fa-reply"></i>
       			返回
       		</button>
		</div>
	<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >基础信息</h5>
	<div class="widget-main no-padding">
				<form class="form-horizontal" role="form"  style="margin-right:210px;margin-top:20px;" id="overhaulLogForm">
				     <input id="overhaulLogid" class="col-md-12" name="overhaulLogid"   readonly  type="hidden"/>
				     <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>单位名称
	                    </label>
						<div class="col-md-4">
					    	<select id="unitId" name="unitId" ></select>
					    	<input id="unitName"  name="unitName"  type="hidden"/>
					    </div>
					    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;"></span>日志时间
	                    </label>
						<div class="col-md-4">
	                    	<input id="logDate" class="col-md-12" name="logDate" value="${giveDate}" readonly type="text"/>
	                    </div>
	                </div>
			        <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>填报人
	                    </label>
						<div class="col-md-4">
	                    	<input id="submitUserName" class="col-md-12" name="submitUserName" value="${userEntity.name}" readOnly type="text"/>
	                    	<input id="submitUserId" class="col-md-12" name="submitUserId" value="${userEntity.id}" readOnly type="hidden"/>
	                    </div>
	                    <label class="col-md-2 control-label no-padding-right">
					                检修分类
	                    </label>
						<div class="col-md-4">
	                    	<select id="overhaulClass" name="overhaulClass" class="col-md-12 chosen-select" name="chargeId" data-placeholder="请选择检修分类"></select>
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
<!-- 		                      	<th>请假人员</th> -->
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
			var unitId;
			var dutyUsersId;
		    var listFormDialog;
			var overhaulArrangeDatatables;
			var overhaulLogId = new Date().getTime();
			jQuery(function($) {
				seajs.use(['datatables','combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					var tempOverhaulLogId;
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					//检修单位
					 unitId = new A.combobox({
						render : "#unitId",
						datasource : ${unitList},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					unitId.setValue(${userEntity.unitId});
					$("#unitName").val('${overhaulLogEntity.unitName}');
					//检修负责人
					dutyUsersId = new A.combobox({
						render : "#dutyUserId",
						datasource : ${dutyUsers},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						},
						callback: function(data){
							if(data&&data[0]){
								$("#dutyUserName").val(data[0].Name);
							};
						}
					}).render();
					dutyUsersId.setValue(${userEntity.id});
					
					//请假人员
					askUsersId = new A.combobox({
						render : "#askPersonId",
						datasource : ${dutyUsers},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					
					
					//检修分类
					var overhaulClass = new A.combobox({
						render : "#overhaulClass",
						datasource : ${overhaulClass},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					
					
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
					            	console.log();
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
// 									  {data: "other",width: "30%",orderable: true},
									  {data: "remark",width: "30%",orderable: true}],
						    fnInfoCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
		                           if(iTotal==0){
		                                $("#overhaulArrange-table .odd td").attr("colspan",8);
		                           }
		                    },
							toolbars: [{
								label:"新增",
								icon:"glyphicon glyphicon-plus",
								className:"btn-success",
								events:{
									click: function(event){
										tempOverhaulLogId = overhaulLogId;
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
							}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = overhaulArrangeDatatables.getSelectRowDatas();
										var ids = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
											}
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										var url = format_url('/overhaulArrange/bulkDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
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
							}],
							btns:[{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green",
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
								id: "delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url = format_url('/overhaulArrange/'+id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
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
								id: "detail",
								label:"查看",
								icon: "fa fa-binoculars bigger-110",
								className: "blue ",
								render: function(btnNode, data){
									
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
// 										var overhaullogDate = nData.createDateString;
// 										A.loadPage({
// 												render : '#page-container',
// 												url:format_url('/overhaulArrange/getAddMore/'+overhaulLogId+'/'+overhaulArrangeId+'/'+overhaullogDate),
// 										});
// 									}
// 								}
							 }
						  ]
						}
					}).render();
					//
					$('#overhaulLogForm').validate({
						debug:true,
						rules:  {							
							unitId:{required:true},
							dutyUserId:{required:true},
						},
						submitHandler: function (form) {
							$("#saveBtn").attr("disabled",true);
							//添加按钮
							var url = format_url("/overhaulLog/overhaulLogAdd");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#overhaulLogForm").serializeObject();
							var myDate = new Date();
							var nowHour = myDate.getHours();
							var nowMinutes = myDate.getMinutes();
							var nowSeconds = myDate.getSeconds();
							obj.logDate = obj.logDate+' '+(nowHour>10?nowHour:('0'+nowHour))+':'+(nowMinutes>10?nowMinutes:('0'+nowMinutes))+':'+(nowSeconds>10?nowSeconds:('0'+nowSeconds));
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
									if(result.result == "success"){
										tempOverhaulLogId = overhaulLogId = result.data.id;
	 									A.loadPage({
	 										render : '#page-container',
	 										url : format_url("/overhaulLog/index")
	 									});
	 									alert('添加成功');
									}else{
										alert(result.errorMsg);
									}
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
        </script>
    </body>
</html>