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
				<li class="active">检修日志提交</li>
			</ul>
		</div>
<div class="page-content" style="margin-left:30px;margin-right:30px;">
		<div style="float:right;margin-right:50px;">
			<button id="btnBack"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style="    margin-top: 14px;">
       			<i class="fa fa-reply"></i>
       			返回
       		</button>
		</div>
	<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >基础信息</h5>
<div class="widget-main no-padding">
			<form class="form-horizontal" role="form"  style="margin-right:100px;margin-top:20px;" id="overhaulLogForm">
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
                </div>
		        <div class="form-group">
					<label class="col-md-2 control-label no-padding-right"  style="text-align:right;">
						请假人员及事由
                    </label>
					<div class="col-md-10">
                   		 <textarea id="other" class="col-md-12" style="height:150px; resize:none;" name="other" type="text" placeholder="请输入请假人员及事由" maxlength="128" value=""></textarea>
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
   		<h5 class='table-title header smaller blue'>工作安排</h5>			
       <div class="col-xs-12" style="margin-top:50px;">	
			<div class="widget-main no-padding">
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
	                      	<th>备注</th>
	                      	<th>详细记录</th>
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
		var companyId;
		var unitId;
		var dutyUsersId;
		var overhaulLogId = new Date().getTime();
		var overhaulArrangeDatatables;
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
					$("#unitName").val('${userEntity.unitName}');
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
									  {data: "dutyPersonName",width: "15%",orderable: true},  
									  {data: "finishStatusString",name:"finishStatus",width: "10%",orderable: true}, 
									  {data: "remark",width: "60%",orderable: true}],
						    fnInfoCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
		                           if(iTotal==0){
		                                $("#overhaulArrange-table .odd td").attr("colspan",6);
		                           }
		                    },
							btns:[{
								id:"abnormalSubmit",
								label:"提交",
								icon: "fa fa-check-square-o bigger-130",
								className: "edit",
								render: function(btnNode, data){
									if(data.processStatus!="1"&&data.processStatus!="0"){
										btnNode.hide();
									}
								},
								render: function(btnNode, data){
									if(!(data.processStatus==0||data.processStatus==1)){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										console.log(nData.processStatus==0||nData.processStatus==1);
// 										if(!(nData.processStatus==0||nData.processStatus==1)){
// 											alert("该记录不是待提交或驳回状态，不能提交！");
// 											return;
// 										}
										var id = nData.id;
	 									listFormDialog = new A.dialog({
	 										width: 850,
	 										height: 531,
	 										title: "提交审核",
	 										closed: function(resule){
	 											var obj=new Object(); 
	 											var id = nData.id;
	 											var status = nData.status;
	 											var url =format_url('/overhaulArrange/submit/'+ id);
	 											if(listFormDialog.resule){
	 												  obj.userList=listFormDialog.resule.join(",");
	 														$.ajax({
	 															url : url,
	 				    										contentType : 'application/json',
	 				    										dataType : 'JSON',
	 				    										type : 'POST',
	 				    										data:JSON.stringify(obj),
	 				    										success: function(result){
	 				    											if(result.result=="success"){
	 				    												alert("提交成功");
	 				    												overhaulArrangeDatatables.draw(false);
	 				    											}else{
	 				    												alert(result.errorMsg);
	 				    											}
	 				    										},
	 				    										error:function(v,n){
	 				    											alert('提交失败');
	 				    										}
	 													});
	 											}
	 										}
	 									}).render();
									}
								}
							}]
						}
					}).render();
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