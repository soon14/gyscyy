<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.aptech.business.component.dictionary.PowerStatusEnum"%>


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
				<li class="active">停送电管理</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content defectList">
			<div class=" col-lg-12 col-md-12 col-sm-12  padding-zero search-content">
				<div class="form-inline text-left" role="form">
					<div class="clearfix">
						<div class="form-group  col-lg-3 col-md-3 col-sm-3 padding-zero" >
								<label class="searchLabel" for="form-field-1">申请单位</label>：
						   		<div id="searchunitNameId"  class="inputWidth text-left padding-zero"></div>
                   		</div>
					   	<div  class="form-group  col-lg-3 col-md-3 col-sm-3 padding-zero">
					   			<label class="searchLabel" for="form-field-1">停送电联系人</label>：
								<input id="searchrequestUsers" class="inputWidth text-left padding-zero "   placeholder="请输入停送电联系人" type="text"></input>
<!-- 								<div  class="inputWidth padding-zero  text-left"> -->
<!-- 					                <select id="searchrequestUsers" class="" onclick="getUserList()" > -->
<!-- 					                </select> -->
<!-- 				                </div> -->
					   		</div>
                   		<div class="form-group  col-lg-3 col-md-3 col-sm-3 padding-zero" style="height: 31px;">
						<label class="searchLabel"   for="form-field-1">工作任务</label>：
		                <input id="searchcontent" class="inputWidth text-left padding-zero "   placeholder="请输入工作任务" type="text"></input>
                   		</div>
<!-- 		               <div class="form-group  col-lg-3 col-md-3 col-sm-3  padding-zero" style="height: 31px;"> -->
<!-- 								<label class="searchLabel" for="form-field-1">设备名称</label>： -->
<!-- 								 <div  class="inputWidth padding-zero  text-left"  id="searchequipmentNameDiv" > -->
<!-- 			                	</div> -->
<!--                    		</div> -->
		                	<div  class="form-group  col-lg-3 col-md-3 col-sm-3  padding-zero">
			                	<label class="searchLabel longSearchLabel" for="form-field-1">停送电方式</label>：
								<div  class="inputWidth padding-zero  text-left">
				                <select id="searchpowerTypes" class="" >
				                </select>
	                   			</div>
		                	</div>
                   		
				   </div>
				   <div class="clearfix">
		           	 <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero">
                   	         <label class="searchLabel">申请停电时间</label>：
                            <div class="form-group dateInputOther padding-zero text-left">
                                <div id="searchRequestDateStartDiv"  style="border:none; padding:0px;"></div>
                            </div>
                            <div class="toLabel" for="searchStartTimeRightDiv" >~</div>
                            <div class="form-group dateInputOther padding-zero text-left">
                                <div id="searchRequestDateEndDiv"   style="border:none; padding:0px;"></div>
                            </div>
                        </div>
                          <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero">
                   	         <label class="searchLabel">协调停电时间</label>：
                            <div class="form-group dateInputOther padding-zero text-left">
                                <div id="searchendDateDiv"  style="border:none; padding:0px;"></div>
                            </div>
                            <div class="toLabel" for="searchStartTimeRightDiv" >~</div>
                            <div class="form-group dateInputOther padding-zero text-left">
                                <div id="searchendDateEndDiv"   style="border:none; padding:0px;"></div>
                            </div>
                        </div>
						
	<!-- 					<div class="form-group  col-lg-3 col-md-3 col-sm-3  padding-zero" style="height: 31px;">
					   			<label class="searchLabel" for="form-field-1">执行人</label>：
					   			 <input id="searchexcuteUserId" class="inputWidth text-left padding-zero "   placeholder="请输入执行人" type="text"></input>
								<div  class="inputWidth padding-zero  text-left">
					                <select id="searchexcuteUserId" class="" >
					                </select>
				                </div>
					   </div> -->
                   </div>
                   <div class="clearfix">
                 		 <div class="form-group  col-lg-3 col-md-3 col-sm-3  padding-zero" style="height: 31px;">
<!-- 							<label class="searchLabel" for="form-field-1">状态</label>： -->
<!-- 								<div  class="inputWidth padding-zero  text-left"> -->
<!-- 				                <select id="searchApproveStatus" class="" > -->
<!-- 				                </select> -->
<!-- 			               		 </div> -->
                   		</div>
                   		<div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero">
                       </div>
            
	             	 <div  class="form-group col-lg-3 col-md-3 col-sm-3 padding-zero text-right btnSearchBottom">
						<button id="btnSearch" class="btn btn-xs btn-primary">
							<i class="glyphicon glyphicon-search"></i>
							查询
						</button>
						<button id="btnReset" class="btn btn-xs btn-primary">
							<i class="glyphicon glyphicon-repeat"></i>
							重置
						</button>								
					</div>
                </div>
            </div>
            </div>
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' >停送电管理</h5>
						<table id="power_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
	                                <th>申请编号</th>
	                                <th>申请单位</th>
	                                <th>工作任务</th>
	                                <th>检修计划名称</th>
	                                <th>停送电方式</th>
	                                <th>申请停电时间</th>
	                                <th>停送电联系人</th>
	                                <th>协调停电时间</th>
<!-- 	                                <th>执行人</th> -->
<!-- 	                                <th>状态</th> -->
                                    <th>操作</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var listFormDialog;
			var unitNameIdCombotree;
			var requestUsersCombobox;
			var powerDatatables ;
			var conditions=[];
			
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker','duallistbox','selectbox'], function(A){
				
					 unitNameIdCombotree = new A.combotree({
						render: "#searchunitNameId",
						name: 'unitNameId',
						//返回数据待后台返回TODO
						datasource: ${unitNameIdTreeList},
						width:"800px",
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
							}
						}
					}).render();
					 $(".aptech-combotree-dropdown").on('click', function(){
						 $(".aptech-combotree .select-close").on('click', function(){
// 								getUserList();
							});
// 							getUserList();
					 });
// 					 $(".aptech-combotree-dropdown").css("width","62%");
					
					//状态下拉框
					var ztTypeCombobox = new A.combobox({
						render: '#searchpowerTypes',
						datasource:${powerTypes},
						width:"150px",
						multiple:false,
						allowBlank:true,
						options:{
							"allow_single_deselect": true,
							"disable_search_threshold":10
						}
					}).render();
					//申请人下拉框
// 					 requestUsersCombobox = new A.combobox({
// 						render: '#searchrequestUsers',
// 						datasource:${requestUsers},
// 						width:"150px",
// 						multiple:false,
// 						allowBlank:true,
// 						options:{
// 							"allow_single_deselect": true,
// 							"disable_search_threshold":10
// 						}
// 					}).render();
// 					//申请人下拉框
// 					 excuteUserIdCombobox = new A.combobox({
// 						render: '#searchexcuteUserId',
// 						datasource:${requestUsers},
// 						width:"150px",
// 						multiple:false,
// 						allowBlank:true,
// 						options:{
// 							"allow_single_deselect": true,
// 							"disable_search_threshold":10
// 						}
// 					}).render();
// 						var searchApproveStatus = new A.combobox({
// 							render: '#searchApproveStatus',
// 							datasource:${approveStatus},
// 							width:"150px",
// 							multiple:false,
// 							allowBlank:true,
// 							options:{
// 								"allow_single_deselect": true,
// 								"disable_search_threshold":10
// 							}
// 						}).render();
					
					
					var searchRequestDateStartDiv = new A.my97datepicker({
						id: 'searchRequestDateStartDivId',
						name:'requestDate',
						render:'#searchRequestDateStartDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "#F{$dp.$D(\\'searchRequestDateEndDivId\\')}",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					
					
					var searchRequestDateStartDiv = new A.my97datepicker({
						id: 'searchRequestDateEndDivId',
						name:'requestDate',
						render:'#searchRequestDateEndDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'searchRequestDateStartDivId\\')}",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					
					var searchendDateDiv = new A.my97datepicker({
						id: 'searchendDateDivId',
						name:'endDate',
						render:'#searchendDateDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "#F{$dp.$D(\\'searchendDateEndDivId\\')}",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					
					
					var searchendDateDiv = new A.my97datepicker({
						id: 'searchendDateEndDivId',
						name:'endDate',
						render:'#searchendDateEndDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'searchendDateDivId\\')}",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					
					
					var exportExcel="";
					 powerDatatables = new A.datatables({
						render: '#power_table',
						options: {
					        "ajax": {
					            "url": format_url("/power/searchData"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "async":false, 
					            "data": function (d) {
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false},
							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "requestNumber",visible:false,width: "120px",orderable: false},
							          {data: "unitName",width: "15%",orderable: true}, 
							          {data: "powerDec",width: "25%",orderable: true},
							          {data: "planName",width: "11%",orderable: true}, 
							          {data: "powerStatusText",width: "10%",name:"powerStatus",orderable: true,render : function(data, type, row, meta) {
						                   if(data=="停电"){
						                	   return "<span style='color:red'>"+data+"</span>";
						                   }  return data;
						               	}},
							          {data: "requestDateString",width: "10%",name:"requestDate",orderable: true},
							          {data: "requestUserName",width: "11%",orderable: true},
							          {data: "endDateString",width: "10%",name:"endDate",orderable: true},
							         // {data: "excuteUserName",width: "5%",name:"endDate",orderable: true},
							         // {data: "statusText",width: "12%",name:"status",orderable: true}
							          ],
							          
							          fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											 if(exportExcel){
												 exportExcels(format_url("/power/exportExcel"),JSON.stringify(conditions));
											 }
											 exportExcel="";
										 },
										 
							toolbars: [   
// 							     <c:if test="${fn:indexOf(dutyName,'检修专工') > -1}">
//         					</c:if>
        					{
								id:"dc",
        						label:"导出",
        						icon:"glyphicon glyphicon-download",
        						className:"btn-primary",
        						events:{
            						click:function(event){
//             							getConditions();
//             							powerDatatables.draw();
                                        $('#btnSearch').click();
                                        exportExcel="exportExcel";
//             							window.location.href=format_url("/power/exportExcel/"+JSON.stringify(conditions)); 
            						}
        						}
        					}],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								render: function(btnNode, data){
									
									var userId =${sysUserId};
									var loginName =${loginName};
									if(data.requestUserId!=userId && loginName!='super'){
										btnNode.hide();
									}
									
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										A.loadPage({
											render : '#page-container',
											url : format_url('/power/getShowEdit/' + id)
										});
									}
								}
							}, 
						      {
								id: "detail",
								label:"查看",
								icon: "fa fa-binoculars bigger-130",
								className: "blue ",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										A.loadPage({
											render : '#page-container',
											url : format_url('/power/getShowDetail/' + id)
										});
									}
								}
							},{
								id: "print",
								label:"打印",
								icon: "glyphicon glyphicon-print",
								className: "blue ",
								events:{
									click: function(event, nRow, nData){
										birtPrint("power.rptdesign",nData.id);
									}
								}
							}
					]
						}
					}).render();
					
					
					$('#btnSearch').on('click',function(){
						getConditions();
						powerDatatables.draw();
					});
					$('#btnReset').on('click',function(){
// 						$("#searchrequestNumber").val("");
						$("#searchRequestDateStartDivId").val("");
						$("#searchRequestDateStartDiv").val("");
						$("#searchRequestDateEndDivId").val("");
						$("#searchRequestDateEndDiv").val("");
						$("#searchcontent").val("");
						unitNameIdCombotree.setValue(null);
						$('#searchrequestUsers').val('');
// 						$("#searchrequestUsers").trigger("chosen:updated");
// 						$('#searchequipName').val('');
						$('#searchpowerTypes').val('');
						$("#searchpowerTypes").trigger("chosen:updated");
// 						$('#searchApproveStatus').val('');
// 						$("#searchApproveStatus").trigger("chosen:updated");
// 						$('#searchexcuteUserId').val('');
// 						$("#searchexcuteUserId").trigger("chosen:updated");
// 						getUserList();

						$("#searchendDateDivId").val("");
						$("#searchendDateDiv").val("");
						$("#searchendDateEndDivId").val("");
						$("#searchendDateEndDiv").val("");

					});
					function getConditions(){
						conditions=[];
// 						if($("#searchrequestNumber").val()!=""){
// 							conditions.push({
// 								field: 'requestNumber',
// 								fieldType:'STRING',
// 								matchType:'LIKE',
// 								value:$("#searchrequestNumber").val().trim()
// 							});
// 						}
						if(unitNameIdCombotree.getValue()!=null&&unitNameIdCombotree.getValue()+""!=""){
							conditions.push({
		    					field: 'unitId',
		    					fieldType:'STRING',
		    					matchType:'EQ',
		    					value:unitNameIdCombotree.getValue()
		    				});
						}

						
						
						if($("#searchrequestUsers").val()!=""){
							conditions.push({
		    					field: 'requestUserName',
		    					fieldType:'STRING',
		    					matchType:'LIKE',
		    					value:$('#searchrequestUsers').val().trim()
		    				});
						}
// 						if($("#searchexcuteUserId").val()!=""){
// 							conditions.push({
// 		    					field: 'excuteUserName',
// 		    					fieldType:'STRING',
// 		    					matchType:'LIKE',
// 		    					value:$('#searchexcuteUserId').val().trim()
// 		    				});
// 						}
						
						if($("#searchpowerTypes").val()!=""){
							conditions.push({
								field: 'powerStatus',
								fieldType:'STRING',
								matchType:'EQ',
								value:$('#searchpowerTypes').val()
							});
						}
						
// 						if($("#searchequipName").val()!=""){
// 							conditions.push({
// 		    					field: 'equipName',
// 		    					fieldType:'STRING',
// 		    					matchType:'LIKE',
// 		    					value:$('#searchequipName').val()
// 		    				});
// 						}
						
						if($("#searchRequestDateStartDivId").val()!=""){
							conditions.push({
								field: 'requestDate',
								fieldType:'DATE',
								matchType:'GE',
								value:$("#searchRequestDateStartDivId").val()+":00"
							});
						}
						if($("#searchRequestDateEndDivId").val()!=""){
							conditions.push({
								field: 'requestDate',
								fieldType:'DATE',
								matchType:'LE',
								value:$("#searchRequestDateEndDivId").val()+":59"
							});
						}
						
						if($("#searchendDateDivId").val()!=""){
							conditions.push({
								field: 'endDate',
								fieldType:'DATE',
								matchType:'GE',
								value:$("#searchendDateDivId").val()+":00"
							});
						}
						if($("#searchendDateEndDivId").val()!=""){
							conditions.push({
								field: 'endDate',
								fieldType:'DATE',
								matchType:'LE',
								value:$("#searchendDateEndDivId").val()+":59"
							});
						}
						
// 						if($("#searchApproveStatus").val()!=""){
// 							conditions.push({
// 		    					field: 'status',
// 		    					fieldType:'STRING',
// 		    					matchType:'EQ',
// 		    					value:$('#searchApproveStatus').val()
// 		    				});
// 						}
						if($("#searchcontent").val()!=""){
							conditions.push({
								field: 'powerDec',
								fieldType:'STRING',
								matchType:'LIKE',
								value:$('#searchcontent').val().trim()
							});
						}
					}
				});
			});
		
			
			function getUserList(){
				var selectunitId= 0;
				if(unitNameIdCombotree.getValue()!=null && unitNameIdCombotree.getValue()!="" && unitNameIdCombotree.getValue()!=0){
					selectunitId = unitNameIdCombotree.getValue();
					$.ajax({
						url: format_url("/overhaulLog/getUserList/"+selectunitId),
						contentType: "application/json",
						dataType: 'JSON',
						type: 'POST',
						data :"",
						success: function(result){
							var userList = eval(result.data);
							requestUsersCombobox = new A.combobox({
								render : "#searchrequestUsers",
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
				}else{
					requestUsersCombobox = new A.combobox({
						render : "#searchrequestUsers",
						datasource :${requestUsers},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						},
						callback: function(data){
						
						}
					}).render();
				}
			}
        </script>
    </body>
</html>