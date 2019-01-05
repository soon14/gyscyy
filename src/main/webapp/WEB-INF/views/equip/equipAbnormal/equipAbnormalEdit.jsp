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
				<li class="active">设备异动申请</li>
				<li class="active">修改</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content" style="margin-left:30px;margin-right:30px;">
		<div style="float:right;margin-right:0px;">
			<button id="btnBack"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style="    margin-top: 14px;">
       			<i class="fa fa-reply"></i>
       			返回
       		</button>
		</div>
		<h5 class='table-title header smaller blue' style="margin-bottom:0px;">基础信息</h5>
		<div class="col-md-12">
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addForm">
			<input class="col-md-12" id="id" name="id" type="hidden"  type="hidden"   value="${equipAbnormalEntities.id}">
			       <div class="form-group" style="margin-top: 30px">
						<label class="col-md-2 control-label no-padding-right">
							设备异动单号
						</label>
						<div class="col-md-4">
		                   <input class="col-md-12" id="equipAbnormalBill" name="equipAbnormalBill" type="text"  value="${equipAbnormalEntities.equipAbnormalBill}" readonly="readonly" >
	                	</div>
					    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>单位所属
					    </label>
					    <div class="col-md-4">
							<div id="unitIdDiv"></div>
	                    </div>
	               </div>
			       <div class="form-group">
	                    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>设备异动编号
						</label>
						<div class="col-md-4">
		                   <div id="equipNumberDiv"></div>
	                	</div>
					    <label class="col-md-2 control-label no-padding-right">
							设备名称
					    </label>
					    <div class="col-md-4">
							<input class="col-md-12" id="equipName" name="equipName" type="text"   value="${equipAbnormalEntities.equipName}" readonly="readonly">
	                    </div>
	               </div>
			       <div class="form-group">
					    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>申请时间
					    </label>
					    <div class="col-md-4">
							<div id="applyDateDiv"></div>
	                    </div>
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>申请人
						</label>
						<div class="col-md-4">
		                   <select id="applyUserId" class="" name="applyUserId"></select>
	                	</div>
	               </div>
			       <div class="form-group">
					    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>计划开始时间
					    </label>
					    <div class="col-md-4">
							<div id="planBeginDateDiv" ></div>
	                    </div>
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>计划结束时间
						</label>
						<div class="col-md-4">
		                   <div id="planEndDateDiv" ></div>
	                	</div>
	               </div>
			       <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>设备异动说明
						</label>
						<div class="col-md-10">
		                   <textarea class="col-md-12" id="abnormalDesc" name="abnormalDesc" type="text"  maxlength="500" style="height:100px; resize:none;">${equipAbnormalEntities.abnormalDesc}</textarea>
	                	</div>
	               </div>
			       <div class="form-group">
					    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>设备异动原因
					    </label>
					    <div class="col-md-10">
							<textarea class="col-md-12" id="abnormalCause" name="abnormalCause" type="text"  maxlength="500" style="height:100px; resize:none;">${equipAbnormalEntities.abnormalCause}</textarea>
	                    </div>
	               </div>
			       <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
							备注
						</label>
						<div class="col-md-10">
		                   <textarea class="col-md-12" id="remark" name="remark" type="text"  maxlength="500" style="height:100px; resize:none;">${equipAbnormalEntities.remark}</textarea>
	                	</div>
	               </div>
		        	<div class="form-group">
					    <label class="col-md-2 control-label no-padding-right">
							附件
					    </label>
						<div class="col-md-10" id="basefielid"  ></div>
               		</div>
               </div>
            </div>
            </form>
    		<div style="margin-right:100px;">
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			var selectEquipLedger;
			var unitIdCombotree;
			var userUnitRels = ${userUnitRels};
			//初始数据备份
			var processUserUnitRels = ${userUnitRels};
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','selectbox'], function(A){
					//附件上传
					var baseuploaddropzone =new A.uploaddropzone({
						render : "#basefielid",
						fileId:${equipAbnormalEntities.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true//显示删除按钮
					}).render();
					//申请人
					var userIdCombobox = new A.combobox({
						render: "#applyUserId",
						datasource:${userId},
						multiple:false,
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					userIdCombobox.setValue(${equipAbnormalEntities.applyUserId});
					//单位名称
					unitIdCombotree = new A.combotree({
						render: "#unitIdDiv",
						name: 'unitId',
						datasource: ${unitNameIdTreeList},
						width:"210px",
						options: {
							treeId: 'unitNameId',
							data: {
								key: {
									name: "name"
								},
								simpleData: {
									enable: true,
									idKey: "id",
									pIdKey: "parentId"
								}
							},
							callTreeBack:function(){
								selectEquipLedger.setValue("","");
								$("#equipmentId").val("");
								$("#equipName").val("");
							}
						}
					}).render();
					unitIdCombotree.setValue('${equipAbnormalEntities.unitId}');
					//计划开始时间
					var planStartTimeDatePicker = new A.my97datepicker({
						id: "planBeginDate",
						name:"planBeginDate",
						render:"#planBeginDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "#F{$dp.$D(\\'planEndDate\\')}",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					planStartTimeDatePicker.setValue('<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${equipAbnormalEntities.planBeginDate}" type="both"/>');
					//计划结束时间
					var planEndTimeDatePicker = new A.my97datepicker({
						id: "planEndDate",
						name:"planEndDate",
						render:"#planEndDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'planBeginDate\\')}",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					planEndTimeDatePicker.setValue('<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${equipAbnormalEntities.planEndDate}" type="both"/>');
					//申请时间
					var applyTimeDatePicker = new A.my97datepicker({
						id: "applyDate",
						name:"applyDate",
						render:"#applyDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					applyTimeDatePicker.setValue('<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${equipAbnormalEntities.applyDate}" type="both"/>');
					//设备编号
					var selectEquipLedger = new A.selectbox({
						id: 'equipmentCode',
						name:'equipmentCode',
						title:'设备台账',
						url:'/equipLedger/selectEquipLedger',
						render:'#equipNumberDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data,self){
// 							if(data&&data[0]){
// 								$("#equipName").val(data[0].name);
// 								self.setValue(data[0].code);
// 							};
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
								$("#equipmentCode").val(codeList);
								$("#equipName").val(nameList);
							};
						}
					}).render();
					$("#equipmentCode").val('${equipAbnormalEntities.equipmentId}');
					selectEquipLedger.setValue('${equipAbnormalEntities.equipmentId}','${equipAbnormalEntities.equipmentId}');
					$('#addForm').validate({
						debug:true,
						rules:  {
							unitId: {
								required: true,
							},
							equipmentCode: {
								required: true,
// 								maxlength: 64
							},
							planBeginDate: {
								required: true
							},
							planEndDate: {
								required: true
							},
							applyDate: {
								required: true
							},
							applyUserId: {
								required: true
							},
							abnormalDesc: {
								required: true,
								maxlength: 500
							}
						},
						submitHandler: function (form) {
							var apDate =  $("#applyDate").val();
							var planDateBegin = $("#planBeginDate").val();
							var planDateEnd = $("#planEndDate").val();
							if(planDateBegin>planDateEnd){
								alert("计划结束时间不能早于计划开始时间!");
								return;
							}
							if(apDate>planDateBegin){
								alert("计划开始时间不能早于申请时间!");
								return;
							}
							//添加按钮
							var url = format_url("/equipAbnormal/updateEquipAbnormal");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							obj.fileId=JSON.stringify(baseuploaddropzone.getValue());
							$.ajax({
								url :  url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								type:'POST',
								success:function(result){
									alert('修改成功');
									currentPage = null;
									pageSize = null;
									 $("#page-container").load(format_url('/equipAbnormal/index/10'));
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					//保存
					$("#saveBtn").on("click", function(){
						$("#addForm").submit();
    				});
					//返回
					$("#btnBack").on("click", function(e){
						window.scrollTo(0,0);
						$("#page-container").load(format_url('/equipAbnormal/index/10'));
					});
				});
			});
        </script>
    </body>
</html>