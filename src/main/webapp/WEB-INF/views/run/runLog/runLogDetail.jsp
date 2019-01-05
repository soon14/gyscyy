<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
      <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh">
	<head>
    	<meta charset="UTF-8">
    	<%@ include file="/WEB-INF/views/common/meta.jsp" %> 
    	<style type="text/css">
			.table{
				font-weight: normal ;
			}
		</style>
    </head>
	<body>
	<div class="page-content">		
			<div class="row">
		<div class="col-md-12">
			<form class="form-horizontal" role="form"  style="margin-right:210px;" id="editJTForm">
			    	<input type="hidden" id="id" name="id" value="${dataMap.id}" />
				<input id="persons" name="persons"  type="hidden" value="${dataMap.personsIds}" class="col-md-12">			    	
				<input id="givePersons" name="givePersons"  type="hidden" value="${dataMap.givePersonsIds}" class="col-md-12">	
			     <input class="col-md-12" id="jfState" name="jfState" type="hidden" value="${dataMap.jfState}">
			     <input class="col-md-12" id="grState" name="grState" type="hidden" value="${dataMap.grState}">
			   <div class="form-group">
  					<label class="col-md-2 control-label no-padding-right">
					    值班日期
				    </label>
				    <div class="col-md-4">
				    <input class="col-md-12" id="date" name="date" type="text" placeholder="${formField.toolTip}" maxlength="20" value="<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${dataMap.date}" type="date"/>" readonly="readonly">
					</div>					  
				    <label class="col-md-2 control-label no-padding-right">
					    接班日期
				    </label>
				    <div class="col-md-4">
				    <input class="col-md-12" id="giveDate" name="giveDate" type="text" placeholder="${formField.toolTip}" maxlength="20" value="<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${dataMap.giveDate}" type="date"/>" readonly="readonly">				    
				    </div>				   				   
				</div>
			    <div class="form-group">
			    <label class="col-md-2 control-label no-padding-right">
					    值班班次
				    </label>
				    <div class="col-md-4">
				    <select id="teamIdDiv" class="col-md-12" name="teamId" disabled="disabled"></select>					    					    				    
				    </div>	
			    <label class="col-md-2 control-label no-padding-right">
					    接班班次
				    </label>
				    <div class="col-md-4">
					 <select id="giveTeamIdDiv" class="col-md-12" name="giveTeamId" disabled="disabled"></select>					    					    
					</div>				   				    
				</div>
			   <div class="form-group">
			    <label class="col-md-2 control-label no-padding-right">
					    值班值次
				    </label>
				    <div class="col-md-4">
					 <select id="dutyIdDiv" class="col-md-12" name="dutyId" disabled="disabled"></select>					    					    			    					    				    				    					    				    				    
					</div>	
			    <label class="col-md-2 control-label no-padding-right">
					    接班值次
				    </label>
				    <div class="col-md-4">
					 <select id="giveDutyIdDiv" class="col-md-12" name="giveDutyId" disabled="disabled"></select>					    					    			    					    				    
				    </div>					     			   
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   值班负责人
				    </label>
				    <div class="col-md-4">
				    	<select id="chargeIdDiv" class="col-md-12" name="chargeId" disabled="disabled"></select>					    					    
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					  接班负责人
				    </label>
				    <div class="col-md-4">
				    	<select id="giveChargeIdDiv" class="col-md-12" name="giveChargeId" disabled="disabled"></select>					    					    
					</div>				  
				</div>
			   <div class="form-group">				   
				    <label class="col-md-2 control-label no-padding-right">
					   值班人员
				    </label>
				    <div class="col-md-4">
<select id="personsIdsDiv" class="col-md-12 chosen-select"
						name="personsIds" data-placeholder="请选择交班人员" disabled="disabled"></select>					</div>
					 <label class="col-md-2 control-label no-padding-right">
					    接班人员
				    </label>
				    <div class="col-md-4">
<select id="givePersonsIdsDiv" class="col-md-12 chosen-select"
						name="givePersonsIds" data-placeholder="请选择接班人员" disabled="disabled"></select>				    </div>					   
				</div>
				 <div class="form-group ">
						<label class="col-md-2 control-label no-padding-righ">检测状态：</label>
						 <div class="col-md-4">
	                   <select id="runCheckDiv" name="runCheck" disabled="disabled"></select>
	                   </div>
	        	 </div>
				<div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    备注
				    </label>
                     	<div class="col-md-10">
						<textarea placeholder="请输入备注" name="remark"  class="col-md-12" style="height:150px; resize:none;" readonly="readonly">${dataMap.remark}</textarea>
				    </div>
				</div>
				  <div class="form-group form-horizontal">
								<label class="col-md-2 control-label no-padding-right">附件</label>
								<div class="col-xs-10" id="divfile">
								</div>
							</div>
			</form>
        	
        </div>
        <div class="row">
				<div class="col-xs-12">
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' style="margin-bottom:0px!important;">交代事项</h5>
						<table id="runRecord_table"
							class="table table-striped table-bordered table-hover"
							style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;"><label
										class="pos-rel"> <input type="checkbox" class="ace" />
											<span class="lbl"></span>
									</label></th>
									<th>序号</th>
									<th>记录类型</th>
									<th>记录时间</th>
									<th>单位名称</th>
									<th>负责人</th>
									<th>记录内容</th>
<!-- 									<th>操作</th> -->
								</tr>
							</thead>
						</table>
					</div>
				</div>
		</div>
         <hr>			  		
        		 <div class="form-group" style='clear:both;margin-left: -14px'>
				    <div class="col-md-6">
				    	<table id="runRecord-table" class="table  table-bordered table-hover" style="width: 50%;display:inline;" >
						<thead><tr><th style="width:100%">五检查</th><td style="width:35px"></td></tr></thead>
							<tbody>
							<tr><td>检查设备运行及保养情况</td><td><input name="checkbox" type="checkbox" disabled="disabled"/></td></tr>
							<tr><td>检查区域卫生情况</td><td><input name="checkbox" type="checkbox" disabled="disabled"/></td></tr>
							<tr><td>检查运行记录及上班生产任务完成情况</td><td><input name="checkbox" type="checkbox" disabled="disabled"/></td></tr>
							<tr><td>检查上班临时任务完成情况</td><td><input name="checkbox" type="checkbox" disabled="disabled"/></td></tr>
							<tr><td>检查工具、用具及仪器是否齐全</td><td><input name="checkbox" type="checkbox" disabled="disabled"/></td></tr>					
						</tbody>
					</table>
				    </div>	
				    <div class="col-md-6">
				    	<table id="runRecord-table" class="table  table-bordered table-hover" style="width: 50%;display:inline">
						<thead><tr><th style="width:100%">五清楚</th><td style="width:35px"></td></tr></thead>
							<tbody>
							<tr><td>本班任务完成及设备运行情况记录清楚</td><td><input name="checkbox" type="checkbox" disabled="disabled"/></td></tr>
							<tr><td>本班发生的主要事情记录清楚</td><td><input name="checkbox" type="checkbox" disabled="disabled"/></td></tr>
							<tr><td>本班发生的异常现象记录清楚</td><td><input name="checkbox" type="checkbox" disabled="disabled"/></td></tr>
							<tr><td>上班或上级交待的事情记录清楚</td><td><input name="checkbox" type="checkbox" disabled="disabled"/></td></tr>
							<tr><td>记录、签名记录清楚</td><td><input name="checkbox" type="checkbox" disabled="disabled"/></td></tr>
						</tbody>
					</table>
					</div>				   				    
				</div>		  		
               <div class="form-group" style='clear:both;margin-left: -14px' >
				    <div class="col-md-6">
			    		<table id="runRecord-table" class="table  table-bordered table-hover" style="width: 50%;display:inline" >
							<thead><tr><th style="width:100%">五不接</th><td style="width:35px"></td></tr></thead>
								<tbody>
								<tr><td>不做好交班准备不接</td><td><input name="checkbox" type="checkbox" disabled="disabled"/></td></tr>
								<tr><td>在事故处理和重要操作过程中不接</td><td><input name="checkbox" type="checkbox" disabled="disabled"/></td></tr>
								<tr><td>工作、资料不全不接</td><td><input name="checkbox" type="checkbox" disabled="disabled"/></td></tr>
								<tr><td>清洁工作未作不接</td><td><input name="checkbox" type="checkbox" disabled="disabled"/></td></tr>
								<tr><td>上级命令或者通知不明确，有其他明显妨碍安全运行工作的不接</td><td><input name="checkbox" type="checkbox" disabled="disabled"/></td></tr>					
							</tbody>
						</table>
				    </div>	
				    <div class="col-md-6">
			    		<table id="runRecord-table" class="table  table-bordered table-hover" style="width: 50%;display:inline;" >
							<thead><tr><th style="width:100%">五不交</th><td style="width:35px"></td></tr></thead>
								<tbody>
								<tr><td>操作、实验、事故吃了未告已段落不交</td><td><input name="checkbox" type="checkbox" disabled="disabled"/></td></tr>
								<tr><td>记录不清、交代不明不交</td><td><input name="checkbox" type="checkbox" disabled="disabled"/></td></tr>
								<tr><td>现场不清洁、工作资料不全不交</td><td><input name="checkbox" type="checkbox" disabled="disabled"/></td></tr>
								<tr><td>安全措施不到位不交</td><td><input name="checkbox" type="checkbox" disabled="disabled"/></td></tr>
								<tr><td>发现接班者人数不够不交</td><td><input name="checkbox" type="checkbox" disabled="disabled"/></td></tr>				
							</tbody>
						</table>
					</div>				   				    
				</div>
                    <hr>
        </div>
        </div>
        <script type="text/javascript">
        	var conditions=[];
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					var appData = ${dataMapJson};
					var personsIds = $("#persons").val().split(",");    
					var givePersonsIds = $("#givePersons").val().split(","); 
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
					var chargeIdCombobox = new A.combobox({
						render: "#chargeIdDiv",
						datasource:${runLogCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10,
							"width":"100%"
						},
						initValue:"${dataMap.chargeId}"
					}).render();
					var giveChargeIdCombobox = new A.combobox({
						render: "#giveChargeIdDiv",
						datasource:${runLogCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,					
						options:{
							"disable_search_threshold":10,
							"width":"100%"
						},
						initValue:"${dataMap.giveChargeId}"
					}).render();
					var givePersonsIdsCombobox = new A.combobox({
						render: "#givePersonsIdsDiv",
						datasource:${runLogCombobox},
						multiple:true,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10,
							"width":"100%"
						},
						initValue:givePersonsIds
						}).render();
					var personsIdsCombobox = new A.combobox({
						render: "#personsIdsDiv",
						datasource:${runLogCombobox},
						multiple:true,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10,
							"width":"100%"							
						},
						initValue:personsIds
					}).render();
					var giveTeamIdCombobox = new A.combobox({
						render: "#giveTeamIdDiv",
						//返回数据待后台返回TODO
						datasource: ${teamMemCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10,
							"width":"100%"							
						},
						initValue:"${dataMap.giveTeamId}"
					}).render();
					
					var teamIdCombobox = new A.combobox({
						render: "#teamIdDiv",
						//返回数据待后台返回TODO
						datasource: ${teamMemCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10,
							"width":"100%"							
						},
						initValue:"${dataMap.teamId}"
					}).render();
					
					var giveDutyIdCombobox = new A.combobox({
						render: "#giveDutyIdDiv",
						//返回数据待后台返回TODO
						datasource: ${orgAppCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10,
							"width":"100%"							
						},
						initValue:"${dataMap.giveDutyId}"
					}).render();
					
					var dutyIdCombobox = new A.combobox({
						render: "#dutyIdDiv",
						//返回数据待后台返回TODO
						datasource: ${orgAppCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10,
							"width":"100%"							
						},
						initValue:"${dataMap.dutyId}"
					}).render();
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${dataMap.fileId},
						autoProcessQueue:false,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true,
					}).render();
					var runRecordDatatables = new A.datatables(
							{
								render : '#runRecord_table',
								options : {
									"ajax" : {
										"url" : format_url("/runRecord/search"),
										"contentType" : "application/json",
										"type" : "POST",
										"dataType" : "JSON",
										"data" : function(d) {
											conditions.push({
												field : 'C_RL_ID',
												fieldType : 'INT',
												matchType : 'EQ',
												value : ${rlId}
											});
											conditions.push({
					        					field: 'C_RECORD_TYPE',
					        					fieldType:'STRING',
					        					matchType:'EQ',
					        					value:'telItem'
					        				});
											d.conditions = conditions;
											return JSON.stringify(d);
										}
									},
									multiple : true,
									ordering : true,
									optWidth : 80,
									order : [ [ 4, "desc" ] ],
									columns : [
											{
												data : "id",
												visible : false,
												orderable : false
											},
											{
												orderable : false,
												"width" : "3%",
												"sClass" : "center",
												render : function(
														data, type,
														row, meta) {
													var startIndex = meta.settings._iDisplayStart;
													row.start = startIndex
															+ meta.row;
													return startIndex
															+ meta.row
															+ 1;
												}
											},
											{
												data : "recordTypeName",
												width : "10%",
												orderable : true
											},
											{
												data : "recordTime",
												width : "10%",
												orderable : true
											},
											{
												data : "unitName",
												width : "10%",
												orderable : true
											},
											{
												data : "fZR",
												width : "10%",
												orderable : true
											},
											{
												data : "recordContent",
												width : "auto",
												orderable : true
											} ]
								}
							}).render();
				});
			});
	        </script>
    </body>
</html>