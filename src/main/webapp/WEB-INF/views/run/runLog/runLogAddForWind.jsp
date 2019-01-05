<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
	<head>
    	<meta charset="UTF-8">
    	<%@ include file="/WEB-INF/views/common/meta.jsp" %> 
    	<!--  <style>
			.even{background:#FFF38F;}
			.odd{background:#FFFFEE;}
			.selected{background:#FF9900;}
		</style> -->
		<style type="text/css">
			.table{
				font-weight: normal ;
			}
		</style>
    </head>
	<body>
		<div class="breadcrumbs ace-save-state" id="breadcrumbs">
				<ul class="breadcrumb">
					<li>
						<i class="ace-icon fa fa-home home-icon"></i>
						<a href="javascript:void(0);" onclick="firstPage()">首页</a>
					</li>
					<li class="active">运行管理</li>
					<li class="active">场站运行日志</li>
					<li class="active">新增</li>
				</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
		<div style="margin-right:100px;">
		 <button id="button" class="btn btn-xs btn-primary  pull-right"data-dismiss="modal"  >
		        			<i class="fa fa-reply"></i>
		        			返回
		        </button>
		 </div>
		<div class="col-md-12" >
					<form class="form-horizontal" role="form"  style="margin-right:100px;margin-top: 20px" id="addForm">
			    	<input type="hidden" id="id" name="id" value="${dataMap.id}" />
				 <input id="persons" name="persons"  type="hidden" value="${dataMap.personsIds}" class="col-md-12">			    	
				 <input id="givePersons" name="givePersons"  type="hidden" value="${dataMap.givePersonsIds}" class="col-md-12">			    							
			     <input class="col-md-12" id="jfState" name="jfState" type="hidden">
			     <input class="col-md-12" id="grState" name="grState" type="hidden" value="${dataMap.grState}">
			   <div class="form-group">
  					<label class="col-md-2 control-label no-padding-right">
  					<span style="color:red;">*</span>
					    值班时间
				    </label>
				    <div class="col-md-4">
				    <input class="col-md-12" id="date" name="date" type="text" placeholder="${formField.toolTip}" maxlength="20" value="${giveDate}" readonly="readonly">				    
					</div>	
					<label class="col-md-2 control-label no-padding-righ">检测状态：</label>
						 <div class="col-md-4">
	                   <select id="runCheckDiv" name="check"></select>
	                   </div>				  
				</div>
			   <div class="form-group">
<!-- 			    <label class="col-md-2 control-label no-padding-right"> -->
<!-- 			    <span style="color:red;">*</span> -->
<!-- 					    值班班次 -->
<!-- 				    </label> -->
<!-- 				    <div class="col-md-4"> -->
<!--     					 <select id="giveTeamIdDiv" class="col-md-12" name="giveTeamId"></select>					    					    				     -->
<!-- 				    </div>	 -->
		       <label class="col-md-2 control-label no-padding-right">
			    <span style="color:red;">*</span>
					    值班值次
				    </label>
				    <div class="col-md-4">
					 <select id="giveDutyIdDiv" class="col-md-12" name="giveDutyId"></select>					    					    			    					    				    				    					    				    				    
					</div>	
					<label class="col-md-2 control-label no-padding-right">
			    	<span style="color:red;">*</span>值班人员
			    </label>
			    <div class="col-md-4">
					<select id="givePersonsIdsDiv" class="col-md-12 chosen-select"
					name="givePersonsIds" data-placeholder="请选择交班人员"></select>					
				</div>
			</div>
			 <div class="form-group">
			   	<label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>值班负责人
			    </label>
			    <div class="col-md-4">
			    	<select id="giveChargeIdDiv" class="col-md-12" name="giveChargeId"></select>					    
			    </div>
			    
			</div>
				   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    备注
				    </label>
                     	<div class="col-md-10">
						<textarea placeholder="请输入备注" name="remark"  class="col-md-12" style="height:150px; resize:none;"></textarea>
				    </div>
				</div>
				  <div class="form-group form-horizontal">
								<label class="col-md-2 control-label no-padding-right">附件</label>
								<div class="col-xs-10" id="divfile">
								</div>
							</div>
			</form>
			</div> 
<!-- 			<div class="row"> -->
<!-- 				<div class="col-xs-12"> -->
<!-- 					<div class="widget-main no-padding"> -->
<!-- 						<h5 class='table-title header smaller blue' style="margin-bottom:0px!important;">交代事项</h5> -->
<!-- 						<table id="runRecord_table" -->
<!-- 							class="table table-striped table-bordered table-hover" -->
<!-- 							style="width:100%;"> -->
<!-- 							<thead> -->
<!-- 								<tr> -->
<!-- 									<th style="display:none;">主键</th> -->
<!-- 									<th class="center sorting_disabled" style="width:50px;"><label -->
<!-- 										class="pos-rel"> <input type="checkbox" class="ace" /> -->
<!-- 											<span class="lbl"></span> -->
<!-- 									</label></th> -->
<!-- 									<th>序号</th> -->
<!-- 									<th>记录类型</th> -->
<!-- 									<th>记录时间</th> -->
<!-- 									<th>单位名称</th> -->
<!-- 									<th>负责人</th> -->
<!-- 									<th>记录内容</th> -->
<!-- <!-- 									<th>操作</th> --> 
<!-- 								</tr> -->
<!-- 							</thead> -->
<!-- 						</table> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
               <div class="form-group" style='clear:both'>
				    <div class="col-md-6">
				    	<table id="runRecord-table" class="table  table-bordered table-hover" style="width: 50%;display:inline;" >
						<thead><tr><th style="width:100%">五检查</th><td style="width:35px"></td></tr></thead>
							<tbody>
							<tr><td>检查设备运行及保养情况</td><td><input name="checkbox" type="checkbox" /></td></tr>
							<tr><td>检查区域卫生情况</td><td><input name="checkbox" type="checkbox" /></td></tr>
							<tr><td>检查运行记录及上班生产任务完成情况</td><td><input name="checkbox" type="checkbox" /></td></tr>
							<tr><td>检查上班临时任务完成情况</td><td><input name="checkbox" type="checkbox" /></td></tr>
							<tr><td>检查工具、用具及仪器是否齐全</td><td><input name="checkbox" type="checkbox" /></td></tr>					
						</tbody>
					</table>
				    </div>	
				    <div class="col-md-6">
				    	<table id="runRecord-table" class="table  table-bordered table-hover" style="width: 50%;display:inline">
						<thead><tr><th style="width:100%">五清楚</th><td style="width:35px"></td></tr></thead>
							<tbody>
							<tr><td>本班任务完成及设备运行情况记录清楚</td><td><input name="checkbox" type="checkbox" /></td></tr>
							<tr><td>本班发生的主要事情记录清楚</td><td><input name="checkbox" type="checkbox" /></td></tr>
							<tr><td>本班发生的异常现象记录清楚</td><td><input name="checkbox" type="checkbox" /></td></tr>
							<tr><td>上班或上级交待的事情记录清楚</td><td><input name="checkbox" type="checkbox" /></td></tr>
							<tr><td>记录、签名记录清楚</td><td><input name="checkbox" type="checkbox" /></td></tr>
						</tbody>
					</table>
					</div>				   				    
				</div>		  		
               <div class="form-group" style='clear:both'>
				    <div class="col-md-6">
			    		<table id="runRecord-table" class="table  table-bordered table-hover" style="width: 50%;display:inline" >
							<thead><tr><th style="width:100%">五不接</th><td style="width:35px"></td></tr></thead>
								<tbody>
								<tr><td>不做好交班准备不接</td><td><input name="checkbox" type="checkbox" /></td></tr>
								<tr><td>在事故处理和重要操作过程中不接</td><td><input name="checkbox" type="checkbox" /></td></tr>
								<tr><td>工作、资料不全不接</td><td><input name="checkbox" type="checkbox" /></td></tr>
								<tr><td>清洁工作未作不接</td><td><input name="checkbox" type="checkbox" /></td></tr>
								<tr><td>上级命令或者通知不明确，有其他明显妨碍安全运行工作的不接</td><td><input name="checkbox" type="checkbox" /></td></tr>					
							</tbody>
						</table>
				    </div>	
				    <div class="col-md-6">
			    		<table id="runRecord-table" class="table  table-bordered table-hover" style="width: 50%;display:inline;" >
							<thead><tr><th style="width:100%">五不交</th><td style="width:35px"></td></tr></thead>
								<tbody>
								<tr><td>操作、实验、事故吃了未告已段落不交</td><td><input name="checkbox" type="checkbox" /></td></tr>
								<tr><td>记录不清、交代不明不交</td><td><input name="checkbox" type="checkbox" /></td></tr>
								<tr><td>现场不清洁、工作资料不全不交</td><td><input name="checkbox" type="checkbox" /></td></tr>
								<tr><td>安全措施不到位不交</td><td><input name="checkbox" type="checkbox" /></td></tr>
								<tr><td>发现接班者人数不够不交</td><td><input name="checkbox" type="checkbox" /></td></tr>				
							</tbody>
						</table>
					</div>				   				    
				</div>
				 <hr>
		            <div style="margin-top:226px;">
		            <div style='height:40px;'>
		            	<button id="qrjjBtnSave" class="btn btn-xs btn-success pull-right"  >
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				保存
		    			</button> 
		            </div>   
		        	</div>
        </div>
		<script type="text/javascript">
			var conditions=[];
		 	var runCheckCombobox = ${runCheckCombobox};
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					var personsIds = $("#persons").val().split(",");    
					var givePersonsIds = $("#givePersons").val().split(",");  
					//日期组件
					var giveDateDatePicker = new A.my97datepicker({
						id: "giveDateId",
						name: "giveDate",
						render: "#giveDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					giveDateDatePicker.setValue('${giveDate}');	
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
						},
						initValue:"${dataMap.check}"
					}).render();
					//combobox组件
					var giveChargeIdCombobox = new A.combobox({
						render: "#giveChargeIdDiv",
						datasource:${chargeCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						},
						initValue:"${dataMap.chargeId}"
					}).render();
					var chargeIdCombobox = new A.combobox({
						render: "#chargeIdDiv",
						datasource:null,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
						}).render();
					var givePersonsIdsCombobox = new A.combobox({
						render: "#givePersonsIdsDiv",
						datasource:${runLogCombobox},
						allowBlank: true,
						multiple:true,
						options:{
							"disable_search_threshold":10
						},
						initValue:personsIds
						}).render();
					var personsIdsCombobox = new A.combobox({
						render: "#personsIdsDiv",
						datasource:null,
						allowBlank: true,
						multiple:true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
// 					var giveTeamIdCombobox = new A.combobox({
// 						render: "#giveTeamIdDiv",
// 						//返回数据待后台返回TODO
// 						datasource: ${giveteamMemCombobox},
// 						//multiple为true时select可以多选
// 						multiple:false,
// 						//allowBlank为false表示不允许为空
// 						allowBlank: true,
// 						options:{
// 							"disable_search_threshold":10
// 						},
// 						initValue:"${dataMap.teamId}"
// 					}).render();
					
					var teamIdCombobox = new A.combobox({
						render: "#teamIdDiv",
						//返回数据待后台返回TODO
						datasource: ${teamMemCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					var giveDutyIdCombobox = new A.combobox({
						render: "#giveDutyIdDiv",
						//返回数据待后台返回TODO
						datasource: ${giveorgAppCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						},
						initValue:"${dataMap.dutyId}"
					}).render();
					giveDutyIdCombobox.change(function(event, value){
						var dutyId = value.selected;
						$.ajax({
							url : format_url("/runLog/getCharge/"+dutyId),
							contentType: "application/json",
							dataType: "JSON",
							success: function(result){
								chargeIdCombobox = new A.combobox({
									render: "#giveChargeIdDiv",
									datasource : result.leaderCombobox,
									multiple:false,
									allowBlank:true,
									options:{
										"allow_single_deselect":true,
										"disable_search_threshold":10
									}
								}).render();
								personsIdsCombobox = new A.combobox({
									render: "#givePersonsIdsDiv",
									datasource : result.sysUserEntityCombobox,
									multiple:true,
									allowBlank:true,
									options:{
										"allow_single_deselect":true,
										"disable_search_threshold":10
									}
								}).render();
							}
						});
					});
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					$('#addForm').validate({
						debug:true,
						rules: {teamId:{required:true,maxlength:20},
							dutyId:{required:true,maxlength:20},
							chargeId:{required:true,maxlength:20},
							personsIds:{required:true,maxlength:64},
							giveDate:{required:true,date:true,maxlength:0},
							giveTeamId:{required:true,maxlength:20},
							giveDutyId:{required:true,maxlength:20},
							giveChargeId:{required:true,maxlength:20},
							givePersonsIds:{required:true,maxlength:64},
							},
						submitHandler: function (form) {	
							var id = $('#id').val();
							var obj = $("#addForm").serializeObject();
							//给交班信息赋值
							$("#giveTeamIdDiv").attr("disabled",false);
							obj.giveTeamId=$("#giveTeamIdDiv").val();	
							$("#giveDutyIdDiv").attr("disabled",false);
							obj.giveDutyId=$("#giveDutyIdDiv").val();	
							$("#giveChargeIdDiv").attr("disabled",false);
							obj.giveChargeId=$("#giveChargeIdDiv").val();	
							if($('[name="checkbox"]:checked').length!=$('[name="checkbox"]').length){
								alert('注意事项未全选，不能保存!');
								$('#qrjjBtn').attr("disabled",false);
								return false;
							}
							var givePersonsIds = givePersonsIdsCombobox.getValue();
							if(givePersonsIds){
								obj.givePersonsIds = givePersonsIds;
							}
							var personsIds = personsIdsCombobox.getValue();
							if(personsIds){
								obj.personsIds = personsIds;
							}
							if(givePersonsIds.indexOf($('#giveChargeIdDiv').val())>=0){
								alert('值班负责人与值班人员不能相同！');
								$('#qrjjBtn').attr("disabled",false);
								return false;
							}
							//添加按钮
							var url = format_url("/runLog/addForWind");
							if($('[name="checkbox"]:checked').length!=$('[name="checkbox"]').length){
								alert('注意事项未全选，不能保存！');
								$('#qrjjBtnSave').attr("disabled",false);
								return false;
							}
							$('#qrjjBtnSave').attr("disabled",true);
							obj.jfState=1;
							obj.fileId=JSON.stringify(uploaddropzone.getValue());
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){																									
									if(result.result=="success"){
										alert('操作成功');	
										A.loadPage({
											render : '#page-container',
											url : format_url("/runLog/indexForWind")
										});	
										}else{
										alert(result.errorMsg);
										$('#qrjjBtnSave').attr("disabled",false);
									}
									},
								error:function(v,n){
									alert('操作失败');
								}
							});
						}
					});
					$("#qrjjBtnSave").on("click", function(){
    					$("#addForm").submit();
    				});
					$('#button').on('click',function(){
						A.loadPage({
							render : '#page-container',
							url : format_url("/runLog/indexForWind")
						});
					});
					$("#btn1").on('click',function(){
					     $("[name='checkbox']").attr("checked",'true');//全选
					    });
// 					var runRecordDatatables = new A.datatables(
// 							{
// 								render : '#runRecord_table',
// 								options : {
// 									"ajax" : {
// 										"url" : format_url("/runRecord/search"),
// 										"contentType" : "application/json",
// 										"type" : "POST",
// 										"dataType" : "JSON",
// 										"data" : function(d) {
// 											conditions.push({
// 					        					field: 'C_RECORD_TYPE',
// 					        					fieldType:'STRING',
// 					        					matchType:'EQ',
// 					        					value:'telItem'
// 					        				});
// 											d.conditions = conditions;
// 											return JSON.stringify(d);
// 										}
// 									},
// 									multiple : true,
// 									ordering : true,
// 									optWidth : 80,
// 									order : [ [ 4, "desc" ] ],
// 									columns : [
// 											{
// 												data : "id",
// 												visible : false,
// 												orderable : false
// 											},
// 											{
// 												orderable : false,
// 												"width" : "3%",
// 												"sClass" : "center",
// 												render : function(
// 														data, type,
// 														row, meta) {
// 													var startIndex = meta.settings._iDisplayStart;
// 													row.start = startIndex
// 															+ meta.row;
// 													return startIndex
// 															+ meta.row
// 															+ 1;
// 												}
// 											},
// 											{
// 												data : "recordTypeName",
// 												width : "10%",
// 												orderable : true
// 											},
// 											{
// 												data : "recordTime",
// 												width : "10%",
// 												orderable : true
// 											},
// 											{
// 												data : "unitName",
// 												width : "10%",
// 												orderable : true
// 											},
// 											{
// 												data : "fZR",
// 												width : "10%",
// 												orderable : true
// 											},
// 											{
// 												data : "recordContent",
// 												width : "auto",
// 												orderable : true
// 											} ]
// 								}
// 							}).render();
				});
			});
        </script>
</body>
</html>