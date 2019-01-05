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
					设备管理
				</li>
				<li class="active">设备异动管理</li>
				<li class="active">设备异动报告</li>
				<li class="active">新增</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content" style="margin-left:30px;margin-right:30px;">
		<div style="float:right;margin-right:50px;">
			<button id="btnBack"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style="    margin-top: 14px;">
       			<i class="fa fa-reply"></i>
       			返回
       		</button>
		</div>
       <h5 class='table-title-withoutbtn header smaller blue' style="margin-bottom:0px;">基础信息</h5>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addForm">
			<input class="col-md-12" id="id" name="id" type="hidden"     value="${equipAbnormalReportEntity.id}">
			       <div class="form-group" style="margin-top: 30px">
					    <label class="col-md-2 control-label no-padding-right">
							报告编码
					    </label>
					    <div class="col-md-4">
							<input class="col-md-12" id="reportCode" name="reportCode" type="text" placeholder="" maxlength="64" value="${equipAbnormalReportEntity.reportCode}" readonly="readonly">
	                    </div>
	                    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;" >*</span>报告名称
						</label>
						<div class="col-md-4">
		                   <input class="col-md-12" id="reportName" name="reportName" type="text" placeholder="" maxlength="64" value="${equipAbnormalReportEntity.reportName}">
	                	</div>
	               </div>
			       <div class="form-group">
			       		 <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;" >*</span>设备编号
					    </label>
					    <div class="col-md-4">
							<div id="equipCodeDiv"></div>
	                    </div>
						<label class="col-md-2 control-label no-padding-right">
							设备名称
						</label>
						<div class="col-md-4">
		                   <input class="col-md-12" id="equipName" name="equipName" type="text" placeholder="" maxlength="64" value="" readonly="readonly">
	                	</div>
	               </div>
			       <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;" >*</span>提交时间
						</label>
						<div class="col-md-4">
		                   <div id="submitDateDiv"></div>
	                	</div>
		       		    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;" >*</span>提交人
					    </label>
					    <div class="col-md-4">
							<select id="submitPersionId" class="" name="submitPersionId"></select>
							<input class="col-md-12" id="submitPersionName" name="submitPersionName" type="hidden" placeholder="" maxlength="64" >
	                    </div>
	               </div>
	               <div class="form-group">
	               <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;" >*</span>设备所属
					    </label>
	               <div class="col-md-4">
		                    <input class="col-md-12" id="equipBelong" name="equipBelong" value="${equipAbnormalReportEntity.equipBelong }"  placeholder="" maxlength="64">
	                    </div>
	               </div>
			       <div class="form-group">
						 <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;" >*</span>执行情况说明
					    </label>
					    <div class="col-md-10">
							<textarea class="col-md-12" id="excuteInfoExplain" name="excuteInfoExplain" type="text"  maxlength="128" style="height:100px; resize:none;">${equipAbnormalReportEntity.excuteInfoExplain}</textarea>
	                    </div>
	               </div>
			       <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
							备注
						</label>
						<div class="col-md-10">
		                   <textarea class="col-md-12" id="remark" name="remark" type="text"  maxlength="128" style="height:100px; resize:none;">${equipAbnormalReportEntity.remark}</textarea>
	                	</div>
	               </div>
			       <div class="form-group">
					    <label class="col-md-2 control-label no-padding-right">
							附件
					    </label>
						<div class="col-md-10" id="basefielid"  ></div>
	               </div>
            </form>
    		<div style="margin-right:100px;">
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		</div>
		<script type="text/javascript">
			var selectEquipLedger;
			var userUnitRels = ${userUnitRels};
			//初始数据备份
			var processUserUnitRels = ${userUnitRels};
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg','selectbox'], function(A){
					//基础信息附件上传
					var baseuploaddropzone =new A.uploaddropzone({
						render : "#basefielid",
						fileId:${equipAbnormalReportEntity.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					//设备编号
					selectEquipLedger = new A.selectbox({
						id: 'equipCode',
						name:'equipCode',
						title:'设备台账',
						url:'/equipLedger/selectEquipLedger',
						render:'#equipCodeDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data,self){
							idList = "";
							nameList = "";
							codeList = "";
							for(var i=0; i< data.length;i++){
								codeList += data[i].code+',';
 								nameList += data[i].name+',';
 								idList += data[i].id+',';
						    }
							selectEquipLedger.setValue(codeList,codeList);
							if(data&&data[0]){
								$("#equipCode").val(codeList);
								$("#equipName").val(nameList);
							};
						}
					}).render();
					$("#equipName").val('${equipAbnormalReportEntity.equipName}');
					selectEquipLedger.setValue('${equipAbnormalReportEntity.equipCode}','${equipAbnormalReportEntity.equipCode}');
					//提交人
					var userIdCombobox = new A.combobox({
						render: "#submitPersionId",
						datasource:${userId},
						multiple:false,
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					userIdCombobox.setValue(${equipAbnormalReportEntity.submitPersionId});
					userIdCombobox.change(function(){
						$("#submitPersionName").val($("#submitPersionId_chosen").children().children()[0].innerText);
					});
					//提交时间
					var planEndDatePicker = new A.my97datepicker({
						id: "submitDate",
						name:"submitDate",
						render:"#submitDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					planEndDatePicker.setValue('<fmt:formatDate pattern="yyyy-MM-dd" value="${equipAbnormalReportEntity.submitDate}" type="both"/>');
					$('#addForm').validate({
						debug:true,
						rules:  {
							reportCode:{required: true},
							reportName:{required: true},
							equipCode:{required: true},
							equipName:{required: true},
							submitPersionId:{required: true},
							submitDate:{required: true},
							equipBelong:{required: true},
							excuteInfoExplain:{required: true}},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/equipAbnormalReport/updateEquipAbnormalReport");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							obj.fileId=JSON.stringify(baseuploaddropzone.getValue());
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('添加成功');
									$("#page-container").load(format_url('/equipAbnormalReport/index'));
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					$("#saveBtn").on("click", function(){
						$("#addForm").submit();
    				});
					//返回
					$("#btnBack").on("click", function(e){
						window.scrollTo(0,0);
						$("#page-container").load(format_url('/equipAbnormalReport/index'));
					});
				});
			});
        </script>
    </body>
</html>