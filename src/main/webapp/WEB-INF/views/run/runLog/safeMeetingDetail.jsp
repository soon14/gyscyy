<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="page-content">	
			<div style="text-align: center;font-size: 20px;color: #478fca;font-weight: lighter;margin-right:100px;">工  作  安  排</div>
			
					<!-- div.dataTables_borderWrap -->
					<h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;"></h5>
					<div class="widget-main no-padding">
					<table id="safeMeeting-table2" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>内容</th>
	                                <th>措施检查状态</th>
<!--                                     <th> </th> -->
                                </tr>
                            </thead>
                        </table>
					<!-- <table id="safeMeeting-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>工作内容</th>
	                                <th>完成情况</th>
	                                <th>负责人</th>
	                                <th>安排时间</th>	                                
                                </tr>
                            </thead>
                        </table> -->
                    </div>
                    <div style="text-align: center;font-size: 20px;color: #478fca;font-weight: lighter;margin-right:100px;">安  全  交  底  内  容</div>
                    <div class="row">
				<div class="col-xs-12">
                    	<div class="widget-main no-padding">
				<h5 class="table-title-withoutbtn header smaller lighter blue" style='margin-bottom:0px;'>交代当日任务中作业环境、存在风险</h5>                        						
						<table id="safeMeeting-table1" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>内容</th>
	                                <th>措施检查状态</th>
<!--                                     <th> </th> -->
                                </tr>
                            </thead>
                        </table>
				<h5 class="table-title-withoutbtn header smaller lighter blue" style='margin-bottom:0px;'>交代预防当日任务风险的安全作业事项</h5>  
				<table id="safeMeeting-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>工作内容</th>
	                                <th>完成情况</th>
	                                <th>负责人</th>
	                                <th>安排时间</th>	                                
                                </tr>
                            </thead>
                        </table>                      
                        	<!-- <table id="safeMeeting-table2" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>内容</th>
	                                <th>措施检查状态</th>
                                    <th> </th>
                                </tr>
                            </thead>
                        </table> -->
                    </div>
						<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addForm">				
						<input type="hidden" name="id" value="${rlId}">
						<input id="btmPersons" name="btmPersons"  type="hidden" value="${dataMap.btmPersonsIds}" class="col-md-12">
						<input id="atmPersons" name="atmPersons"  type="hidden" value="${dataMap.atmPersonsIds}" class="col-md-12">	
					<hr>
					<div style="text-align: center;font-size: 20px;color: #478fca;font-weight: lighter;">班&nbsp&nbsp&nbsp&nbsp&nbsp前&nbsp&nbsp&nbsp&nbsp&nbsp会</div>				                    
                   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>班前会内容
				    </label>
                     	<div class="col-md-10">
						<textarea placeholder="" name="btmContent"  class="col-md-12" style="height:150px; resize:none;" readonly="readonly">${dataMap.btmContent}</textarea>
				    </div>
				</div>
				 <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>参加人员
				    </label>
				    <div class="col-md-10">
				    	<select id="btmPersonsIdsDiv" class="col-md-12" name="btmPersonsIds" disabled="disabled"></select>									    
					</div>
				</div>
				 <hr>
				 					<div style="text-align: center;font-size: 20px;color: #478fca;font-weight: lighter;">班&nbsp&nbsp&nbsp&nbsp&nbsp后&nbsp&nbsp&nbsp&nbsp&nbsp会</div>				                    
				 
                      <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>班后会内容
				    </label>
                     	<div class="col-md-10">
						<textarea placeholder="" name="atmContent"  class="col-md-12" style="height:150px; resize:none;" readonly="readonly">${dataMap.atmContent}</textarea>
				    </div>
				</div>
				 <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					  <span style="color:red;">*</span>  参加人员
				    </label>
				    <div class="col-md-10">
				    	<select id="atmPersonsIdsDiv" class="col-md-12" name="atmPersonsIds" disabled="disabled"></select>									    
					</div>
				</div>
				</div>
				</form>
        	
        						                
            </div>
        </div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var listFormDialog;
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					var btmPersonsIds = $("#btmPersons").val().split(",");
					var atmPersonsIds = $("#atmPersons").val().split(",");					
					//combobox组件
					var btmPersonsIdsCombobox = new A.combobox({
						render: "#btmPersonsIdsDiv",
						datasource:${safeMeetingCombobox},
						multiple:true,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						},
						initValue:btmPersonsIds
					}).render();
					//combobox组件
					var atmPersonsIdsCombobox = new A.combobox({
						render: "#atmPersonsIdsDiv",
						datasource:${safeMeetingCombobox},
						multiple:true,						
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						},
						initValue:atmPersonsIds
					}).render();
					var safeMeetingDatatables = new A.datatables({
						render: '#safeMeeting-table',
						options: {
					        "ajax": {
					            "url": format_url("/safeMeeting/searchData"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {					            	
					            	conditions.push({
			        					field: 'C_RL_ID',
			        					fieldType:'INT',
			        					matchType:'EQ',
			        					value:${rlId}
			        				});
			        				conditions.push({
			        					field: 'C_MEETING_FLAG',
			        					fieldType:'INT',
			        					matchType:'EQ',
			        					value:3
			        				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							checked:false,
							bInfo:false,
							paging:false,
							aLengthMenu: [5],
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "meetingContent",width: "70%",orderable: true}, 
							          {data: "checkStateName",width: "auto",orderable: true}, 
							          {data: "fZR",width: "auto",orderable: true},
							          {data: "workTime",width: "auto",orderable: true}],
						}
					}).render();
					var conditions1=[];
					
					var safeMeetingDatatables1 = new A.datatables({
						render: '#safeMeeting-table1',
						options: {
					        "ajax": {
					            "url": format_url("/safeMeeting/searchData"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions1.push({
			        					field: 'C_MEETING_FLAG',
			        					fieldType:'INT',
			        					matchType:'EQ',
			        					value:1
			        				});
					            	conditions1.push({
			        					field: 'C_RL_ID',
			        					fieldType:'INT',
			        					matchType:'EQ',
			        					value:${rlId}
			        				});
					            	d.conditions = conditions1;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							aLengthMenu: [5],
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "meetingContent",width: "auto",orderable: true}, 
							          {data: "checkStateName",width: "auto",orderable: true}],
							
							
						}
					}).render();	
					var conditions2=[];
					var safeMeetingDatatables2 = new A.datatables({
						render: '#safeMeeting-table2',
						options: {
					        "ajax": {
					            "url": format_url("/safeMeeting/searchData"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions2.push({
			        					field: 'C_MEETING_FLAG',
			        					fieldType:'INT',
			        					matchType:'EQ',
			        					value:2
			        				});
					            	conditions2.push({
			        					field: 'C_RL_ID',
			        					fieldType:'INT',
			        					matchType:'EQ',
			        					value:${rlId}
			        				});
					            	d.conditions = conditions2;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							aLengthMenu: [5],
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "meetingContent",width: "auto",orderable: true}, 
							          {data: "checkStateName",width: "auto",orderable: true}],
							
						}
					}).render();							
				});
			});
        </script>
    </body>
</html>