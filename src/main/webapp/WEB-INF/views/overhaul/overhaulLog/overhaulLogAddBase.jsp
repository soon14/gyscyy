<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
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
				<li class="active">填报检修日志</li>
			</ul>
		</div>
<div class="page-content" style="margin-left:30px;margin-right:30px;">
							<div style="float:right; margin-right:50px;">
								<button id="btnBack"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style="    margin-top: 14px;">
				        			<i class="fa fa-reply"></i>
				        			返回
				        		</button>
							</div>
	<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >基础信息</h5>
<div class="widget-main no-padding">
			<form class="form-horizontal" role="form"  style="margin-right:100px;margin-top:20px;" id="addForm">
			     <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>单位名称
                    </label>
					<div class="col-md-4">
				    	<select id="unitId" name="unitId" onchange="getUserList()"></select>
				    </div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;"></span>日志时间
                    </label>
					<div class="col-md-4">
                    	<input id="logDate" class="col-md-12" name="logDate" readOnly type="text"/>
                    </div>
                </div>
		        <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>填报人
                    </label>
					<div class="col-md-4">
                    	<select id="submitUserId" name="dutyUserId" ></select>
                    </div>
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>检修人员
                    </label>
					<div class="col-md-4">
                    	<select id="UserId" name="dutyUserId" ></select>
                    </div>
                </div>
		        <div class="form-group">
		        	<div class="form-group col-xs-12" style="margin-top:20px">
					<label class="col-md-1 control-label no-padding-right"  style="text-align:right;">
						请假人员及事由
                    </label>
					<div class="col-md-11">
                   		 <textarea id="other" class="col-md-12" style="height:150px; resize:none;" name="other" type="text" placeholder="请假人员及事由" maxlength="512" value=""></textarea>
                    </div>
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
        			  <h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;margin-top:30px;' >工作安排</h5>			
		       
        <div class="col-xs-12" style="margin-top:30px;">	
<!-- 	    <h5 class="table-title header smaller blue ">检修工作安排</h5> -->
		<div class="widget-main no-padding">
		 <form id="overhaulWork-table-form" class="form-horizontal"  onsubmit="return false;">
				<table id="overhaulWork-table" class="table table-striped table-bordered table-hover" style="width:100%;margin-bottom:20px;">
					<thead>
						<tr>
							<th style="display:none;">主键</th>
							<th>序号</th>
							<th><span style="color:red;"></span>工作内容</th>
                       		<th><span style="color:red;"></span>负责人</th>
                       		<th><span style="color:red;"></span>检修人员</th>
                       		<th>操作</th>
						</tr>
					</thead>
				</table>
			</form>
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
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
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
						},
						callback: function(data){
							alert("unitId");
							
						}
					}).render();
					unitId.setValue(${userEntity.unitId});
				});
			});
			//
			function getUserList(){
				var selectunitId= 0;
				if(unitId.getValue()!=null && unitId.getValue()!="" && unitId.getValue()!=0){
					selectunitId = unitId.getValue();
				}
				$.ajax({
					url: format_url("/overhaulLog/getUserList/"+selectunitId),
					contentType: "application/json",
					dataType: 'JSON',
					type: 'POST',
					data :"",
					success: function(result){
						var userList = eval(result.data);
						dutyUsersId = new A.combobox({
							render : "#dutyUserId",
							datasource :userList,
							allowBlank: true,
							options : {
								"disable_search_threshold" : 10
							},
							callback: function(data){
							
							}
						}).render();
					}
				});
			}
        </script>
    </body>
</html>