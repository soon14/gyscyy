<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/views/common/meta.jsp"%>
</head>
<body>
	<div class="col-md-12">
		<form class="form-horizontal" role="form" style="margin-right: 100px;"
			id="addForm">
			<input class="col-md-12" id="jfState" name="jfState" type="hidden"
				value="1"> <input class="col-md-12" id="grState"
				name="grState" type="hidden" value="">

			<div class="form-group">
				<label class="col-md-2 control-label no-padding-right"> 交班日期
				</label>
				<div class="col-md-4">
					<div id="giveDateDiv"></div>
				</div>
				<label class="col-md-2 control-label no-padding-right"> 接班日期
				</label>
				<div class="col-md-4">
					<div id="dateDiv"></div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-2 control-label no-padding-right"> 交班班次
				</label>
				<div class="col-md-4">
					<input class="col-md-12" id="giveTeamId" name="giveTeamId"
						type="text" placeholder="${formField.toolTip}" maxlength="20"
						value="${formField.defaultValue}">
				</div>
				<label class="col-md-2 control-label no-padding-right"> 接班班次
				</label>
				<div class="col-md-4">
					<input class="col-md-12" id="teamId" name="teamId" type="text"
						placeholder="${formField.toolTip}" maxlength="20"
						value="${formField.defaultValue}">
				</div>
			</div>
			<div class="form-group">

				<label class="col-md-2 control-label no-padding-right"> 交班值次
				</label>
				<div class="col-md-4">
					<input class="col-md-12" id="giveDutyId" name="giveDutyId"
						type="text" placeholder="${formField.toolTip}" maxlength="20"
						value="${formField.defaultValue}">
				</div>
				<label class="col-md-2 control-label no-padding-right"> 接班值次
				</label>
				<div class="col-md-4">
					<input class="col-md-12" id="dutyId" name="dutyId" type="text"
						placeholder="${formField.toolTip}" maxlength="20"
						value="${formField.defaultValue}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-2 control-label no-padding-right"> <span
					style="color: red;">*</span>交班负责人
				</label>
				<div class="col-md-4">
					<select id="giveChargeIdDiv" class="col-md-12 chosen-select"
						name="giveChargeId" data-placeholder="请选择交班负责人"></select>
				</div>
				<label class="col-md-2 control-label no-padding-right"> <span
					style="color: red;">*</span>接班负责人
				</label>
				<div class="col-md-4">
					<select id="chargeIdDiv" class="col-md-12 chosen-select"
						name="chargeId" data-placeholder="请选择接班负责人"></select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-2 control-label no-padding-right"> 交班人员
				</label>
				<div class="col-md-4">
					<select id="givePersonsIdsDiv" class="col-md-12 chosen-select"
						name="givePersonsIds" data-placeholder="请选择交班人员"></select>
				</div>
				<label class="col-md-2 control-label no-padding-right"> 接班人员
				</label>
				<div class="col-md-4">
					<select id="personsIdsDiv" class="col-md-12 chosen-select"
						name="personsIds" data-placeholder="请选择接班人员"></select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-2 control-label no-padding-right"> <span
					style="color: red;">*</span>交班密码
				</label>
				<div class="col-md-4">
					<input class="col-md-12" id="givePassword" name="givePassword"
						type="password" placeholder="" maxlength="128" value="">
				</div>
				<label class="col-md-2 control-label no-padding-right"> <span
					style="color: red;">*</span>接班密码
				</label>
				<div class="col-md-4">
					<input class="col-md-12" id="receivePassword"
						name="receivePassword" type="password" placeholder=""
						maxlength="128" value="">
				</div>
			</div>
			 <div class="form-group ">
						<label class="col-md-2 control-label no-padding-righ">检测状态：</label>
						 <div class="col-md-4">
	                   <select id="runCheckDiv" name="runCheck"></select>
	                   </div>
	        	 </div>
		</form>
		<div style="margin-right: 100px;">
			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
				<i class="ace-icon fa fa-times"></i> 取消
			</button>
			<button id="saveBtn" class="btn btn-xs btn-success pull-right"
				style="margin-right: 15px;">
				<i class="ace-icon fa fa-floppy-o"></i> 保存
			</button>
			<button id="qrjjBtn" class="btn btn-xs btn-success pull-right"
				style="margin-right: 15px;">
				<i class="ace-icon fa fa-floppy-o"></i> 确认交接
			</button>
		</div>
		<div class="widget-main no-padding">
			<div id="tableTitle"></div>
			<table id="runRecord-table"
				class="table table-striped table-bordered table-hover"
				style="width: 100%;">
				<thead>
					<tr>
						<th>序号</th>
						<th>五清楚</th>
						<th>确认</th>
					</tr>
					<tr>
						<th>1</th>
						<th>本班任务完成及设备运行情况记录清楚</th>
						<th><input type="checkbox" /></th>
					</tr>
					<tr>
						<th>2</th>
						<th>本班发生的主要事情记录清楚</th>
						<th><input type="checkbox" /></th>
					</tr>
					<tr>
						<th>3</th>
						<th>本班发生的异常现象记录清楚</th>
						<th><input type="checkbox" /></th>
					</tr>
					<tr>
						<th>4</th>
						<th>上班或上级交待的事情记录清楚</th>
						<th><input type="checkbox" /></th>
					</tr>
					<tr>
						<th>5</th>
						<th>记录、签名记录清楚</th>
						<th><input type="checkbox" /></th>
					</tr>
					<tr>
						<th>序号</th>
						<th>五检查</th>
						<th>确认</th>
					</tr>
					<tr>
						<th>1</th>
						<th>检查设备运行及保养情况</th>
						<th><input type="checkbox" /></th>
					</tr>
					<tr>
						<th>2</th>
						<th>检查区域卫生情况</th>
						<th><input type="checkbox" /></th>
					</tr>
					<tr>
						<th>3</th>
						<th>检查运行记录及上班生产任务完成情况</th>
						<th><input type="checkbox" /></th>
					</tr>
					<tr>
						<th>4</th>
						<th>检查上班临时任务完成情况</th>
						<th><input type="checkbox" /></th>
					</tr>
					<tr>
						<th>5</th>
						<th>检查工具、用具及仪器是否齐全</th>
						<th><input type="checkbox" /></th>
					</tr>
					<tr>
						<th>序号</th>
						<th>五不交</th>
						<th>确认</th>
					</tr>
					<tr>
						<th>1</th>
						<th>操作、实验、事故吃了未告已段落不交</th>
						<th><input type="checkbox" /></th>
					</tr>
					<tr>
						<th>2</th>
						<th>记录不清、交代不明不交</th>
						<th><input type="checkbox" /></th>
					</tr>
					<tr>
						<th>3</th>
						<th>现场不清洁、工作资料不全不交</th>
						<th><input type="checkbox" /></th>
					</tr>
					<tr>
						<th>4</th>
						<th>安全措施不到位不交</th>
						<th><input type="checkbox" /></th>
					</tr>
					<tr>
						<th>5</th>
						<th>发现接班者人数不够不交</th>
						<th><input type="checkbox" /></th>
					</tr>
					<tr>
						<th>序号</th>
						<th>五不接</th>
						<th>确认</th>
					</tr>
					<tr>
						<th>1</th>
						<th>不做好交班准备不接</th>
						<th><input type="checkbox" /></th>
					</tr>
					<tr>
						<th>2</th>
						<th>在事故处理和重要操作过程中不接</th>
						<th><input type="checkbox" /></th>
					</tr>
					<tr>
						<th>3</th>
						<th>工作、资料不全不接</th>
						<th><input type="checkbox" /></th>
					</tr>
					<tr>
						<th>4</th>
						<th>清洁工作未作不接</th>
						<th><input type="checkbox" /></th>
					</tr>
					<tr>
						<th>5</th>
						<th>上级命令或者通知不明确，有其他明显妨碍安全运行工作的不接</th>
						<th><input type="checkbox" /></th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					//日期组件
					var dateDatePicker = new A.my97datepicker({
						id: "dateId",
						name:"date",
						render:"#dateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					var runCheckCombobox = new A.combobox({
						render: "#runCheckDiv",
						//返回数据待后台返回TODO
						datasource: ${runCheckCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10,
							"width":"100%"	
						}
					}).render();
					//日期组件
					var giveDateDatePicker = new A.my97datepicker({
						id: "giveDateId",
						name:"giveDate",
						render:"#giveDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					//combobox组件
					var giveChargeIdCombobox = new A.combobox({
						render: "#giveChargeIdDiv",
						datasource:${runLogCombobox},
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//combobox组件
					var chargeIdCombobox = new A.combobox({
						render: "#chargeIdDiv",
						datasource:${runLogCombobox},
						options:{
							"disable_search_threshold":10
						}
					}).render();
					var givePersonsIdsCombobox = new A.combobox({
						render: "#givePersonsIdsDiv",
						datasource:${runLogCombobox},
						multiple:true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					var personsIdsCombobox = new A.combobox({
						render: "#personsIdsDiv",
						datasource:${runLogCombobox},
						multiple:true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					$('#addForm').validate({
						debug:true,
						rules:  {date:{required:true,date:true,maxlength:0},
							teamId:{required:true,maxlength:20},
							dutyId:{required:true,maxlength:20},
							chargeId:{required:true,maxlength:20},
							personsIds:{required:true,maxlength:64},
							giveDate:{required:true,date:true,maxlength:0},
							giveTeamId:{required:true,maxlength:20},
							giveDutyId:{required:true,maxlength:20},
							giveChargeId:{required:true,maxlength:20},
							givePersonsIds:{required:true,maxlength:64},
							givePassword:{required:true,maxlength:128},
							receivePassword:{required:true,maxlength:128}},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/runLog");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							var givePersonsIds = givePersonsIdsCombobox.getValue();
							if(givePersonsIds){
								obj.givePersonsIds = givePersonsIds;
							}
							var personsIds = personsIdsCombobox.getValue();
							if(personsIds){
								obj.personsIds = personsIds;
							}
							$("#saveBtn").attr({"disabled":"disabled"});
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('添加成功');
									listFormDialog.close();
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
					$("#qrjjBtn").on("click", function(){
						var obj = $("#addForm").serializeObject();
						if(obj.jfState!='1'){
							alert('交接班巡视未完成，不可确认交接班！')
							return false;
						}
						obj.grState = '1';
						$.ajax({
							url : format_url("/runLog"),
							contentType : 'application/json',
							dataType : 'JSON',
							data : JSON.stringify(obj),
							cache: false,
							type : 'POST',
							success: function(result){
								alert('交接成功');
								listFormDialog.close();
							},
							error:function(v,n){
								alert('交接失败');
							}
						});
					});
				});
			});
        </script>
</body>
</html>