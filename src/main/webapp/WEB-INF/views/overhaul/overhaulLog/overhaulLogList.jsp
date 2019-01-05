<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
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
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-lg-12 col-md-12 col-sm-12  padding-zero search-content">
				<div class="form-inline text-left" role="form">
					 <div class="clearfix">
					 
					 	<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
	 						<div  class="clearfix groupDiv">
								<label class="searchLabel" for="form-field-1">单位名称</label>：
						   		<div id="searchUnitId"  class="inputWidth text-left padding-zero"></div>
	                   		</div>
                   		
							<div  class="clearfix  groupRightDiv" >
							<label class="searchLabel" for="form-field-1">填报人</label>：
								<div class="inputWidth padding-zero text-left" >
				                <select id="searchsubmitUserName" class="" onclick="getUserList()" >
					                </select>
			                </div>
                   			</div>
                   		</div>
                   		
			        	<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
							<label class="searchLabel" >检修时间</label>：
							<div class="form-group dateInput padding-zero text-left">
		                		<div id="searchStartfindTimeDiv"  style="border:none; padding:0px;"></div>
                   			</div>
							<div class="toLabel" for="searchStartTimeRightDiv" >~</div>
                         
							<div class="form-group dateInput padding-zero text-left">
		               		 	<div id="searchEndfindTimeDiv"  style="border:none; padding:0px;"></div>
                   			</div>
		          		 </div>
				   </div>
				   
				  <div class="clearfix">
                		<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
	 						<div  class="clearfix groupDiv">
								<label class="searchLabel" for="form-field-1">检修人员</label>： 
					            <input id="searchcheckAttendance"class="inputWidth text-left"  placeholder="请输入检修人员" type="text"></input>
	                   		</div>
	                   	   	<div  class="clearfix groupRightDiv">
	                   	   	   <label class="searchLabel" for="form-field-1">请假人员</label>： 
					            <input id="searchcAskPersonName"class="inputWidth text-left"  placeholder="请输入请假人员" type="text" ></input>
<!-- 								<label class="searchLabel" for="form-field-1">外协单位</label>：  -->
<!-- 					            <input id="searchcOutUnitName"class="inputWidth text-left"  placeholder="请输入外协单位" type="text" style="width: 65%"></input> -->
	                   		</div>
                   		  </div>
                		
	                 <div  class="form-group col-lg-6 col-md-6 col-sm-6 padding-zero">
	                 	<div  class="clearfix groupDiv">
<!-- 								<label class="searchLabel" for="form-field-1">请假人员</label>：  -->
<!-- 					            <input id="searchcAskPersonName"class="inputWidth text-left"  placeholder="请输入请假人员" type="text" ></input> -->
	                   </div>
	                   
	                   <div  class="clearfix groupDiv padding-zero text-right btnSearchBottom" style="float:right;" >
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
		   
<!-- 		        	<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;"> -->
<!-- 			                <label class="searchLabel" for="form-field-1">请假人员及事由</label>： -->
<!-- 				            <input id="searchOther"  name="searchOther" class="input-width padding-zero derect-input   text-left"  placeholder="请输入请假人员" type="text"></input> -->
<!-- 	          		</div> -->
<!--                   </div> -->
            </div>
            </div>
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' >检修日志</h5>
						<table id="overhaulLog_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
	                                <th>单位名称</th>
	                                <th>检修人员</th>
	                                <th>填报人</th>
	                                <th>检修时间</th>
                                    <th>请假人员</th>
<!--                                     <th>外协单位</th> -->
                                    <th>完成状态</th>
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
			var searchsubmitUserName;
			var overhaulLogDatatables;
			var conditions=[];
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var searchStartfindTime = new A.my97datepicker({
						id: 'searchStartfindTimeDivId',
						name:'searchStartfindTime',
						render:'#searchStartfindTimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "#F{$dp.$D(\\'searchEndfindTimeDivId\\')}",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					var searchEndfindTime = new A.my97datepicker({
						id: 'searchEndfindTimeDivId',
						name:'searchEndfindTime',
						render:'#searchEndfindTimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'searchStartfindTimeDivId\\')}",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					//单位
					 unitNameIdCombotree = new A.combotree({
						render: "#searchUnitId",
						name: 'unitId',
						//返回数据待后台返回TODO
						datasource: ${unitList},
						width:"800px",
						options: {
							treeId: 'unitId',
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
								getUserList();
							});
							getUserList();
					 });

					//填报人
					 searchsubmitUserName = new A.combobox({
						render : "#searchsubmitUserName",
						datasource : ${dutyUsers},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
						var exportExcel="";
					overhaulLogDatatables = new A.datatables({
						render: '#overhaulLog_table',
						options: {
					        "ajax": {
					            "url": format_url("/overhaulLog/searchDate"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "async":false, 
					            "data": function (d) {
// 					            	if(d.search.value){
// 						            	d.conditions = JSON.parse(d.search.value);
// 						            	if(d.conditions){
// 						            		for(var index in d.conditions){
// 						            			if(d.conditions[index].type==1){
// 						            			$("#"+d.conditions[index].name).val(d.conditions[index].value);
// 						            			$("#"+d.conditions[index].name).trigger("chosen:updated");
// 						            			}
// 						            			if(d.conditions[index].type==2){
// 						            				unitNameIdCombotree.setValue(d.conditions[index].value);
// 							            		}
// 						            		}
// 						            	}
// 					            	}
									d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							searching: true,
							bStateSave: true,
							optWidth: 80,
							order: [[ 0, "desc" ]],
							columns: [{data:"id", visible:false,orderable:true},
							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
									  {data: "unitName",width: "18%",orderable: true}, 
									  {data: "dutyUserName",width: "18%",orderable: true}, 
									  {data: "submitUserName",width: "18%",orderable: true},  
									  {data: "logDateString",name:"logDate",width: "15%",orderable: true}, 
									  {data: "askPersonName",width: "16%",orderable: true},
// 									  {data: "outUnitName",width: "15%",orderable: true},
									  {data: "finishStatusString",name:"finishStatus",width: "7%",orderable: true}],
									  
									  fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											 if(exportExcel){
												 exportExcels(format_url("/overhaulLog/exportExcel/"),JSON.stringify(conditions));
											 }
											 exportExcel="";
										 },
										 
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click: function(event, nRow, nData){
											 var currentPage = overhaulLogDatatables.getCurrentPage();
												A.loadPage({
															render : '#page-container',
															url : format_url("/overhaulLog/getAdd")
												});
									}
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = overhaulLogDatatables.getSelectRowDatas();
										var ids = [];
										var undelids = [];
										var userIds = [];
								
										var sysUserId=${sysUserId};
										var loginName =${loginName};
										
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
												userIds.push(data[i].submitUserId);
												
												if(isHidden(data[i].logDate)){
													undelids.push(data[i].id);
												}
											}
										}
										if(undelids>0 && loginName!='super'){
											alert("检修日志已封存，无法删除！");
											return;
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										
										for(var j=0;j<userIds.length;j++){
											if(userIds[j]!=sysUserId&&loginName!="super"){
												alert('记录中包含不是当前登陆人的记录不能删除!');
												return;
											}
										}
										
										
										var url = format_url('/overhaulLog/batchDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													overhaulLogDatatables.draw(false);
												},
												error:function(v,n){
													alert('操作失败');
												}
											});
										});
									}
								}
							},{
								id:"dc",
        						label:"导出",
        						icon:"glyphicon glyphicon-download",
        						className:"btn-primary",
        						events:{
            						click:function(event){
            							$('#btnSearch').click();
            							exportExcel="exportExcel";
//             							searchFunction();
//             							overhaulLogDatatables.draw();
//             							window.location.href=format_url("/overhaulLog/exportExcel/"+JSON.stringify(conditions)); 
            						}
        						}
        					}],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								render: function(btnNode, data){
									var loginName =${loginName};
									var userId = ${sysUserId};

									if(isHidden(data.logDate) && loginName!='super'){
										btnNode.hide();
									}
									
									if(data.submitUserId!=userId){
										btnNode.hide();
									}

								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										A.loadPage({
											render : '#page-container',
											url : format_url("/overhaulLog/getEdit/"+ id)
										});
									}
								}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								render: function(btnNode, data){
									var sysUserId=${sysUserId};
									var loginName =${loginName};
									if(isHidden(data.logDate)&& loginName!='super' ){
										btnNode.hide();
									}
									if(data.submitUserId!=sysUserId){
										btnNode.hide();
									}

								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/overhaulLog/deleteOnlyOne/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										success: function(result){
        											alert('删除成功');
        											overhaulLogDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
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
										url : format_url("/overhaulLog/getDetail/"+ id)
									});
								}
							}
						},
						{
							id: "history",
							label:"历史",
							icon: "fa fa-history bigger-130",
							className: "grey ",
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									A.loadPage({
										render : '#page-container',
										url : format_url("/overhaulLog/getHistory/"+ id)
									});
								}
							}
						}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						if($('#searchsubmitUserName').val()){
							conditions.push({
								field: 'a.C_SUBMIT_USER_ID',
								fieldType:'INT',
								matchType:'EQ',
								name:'searchsubmitUserName',
								type:1,
								value:$('#searchsubmitUserName').val()
							});
						}
						if($("#searchcheckAttendance").val()!=""){
							conditions.push({
								field: 'a.C_DUTY_USER_NAME',
								fieldType:'STRING',
								matchType:'LIKE',
								name:'searchcheckAttendance',
								type:1,
								value:$("#searchcheckAttendance").val().trim()
							});
						}
						if(unitNameIdCombotree.getValue()!=undefined&&unitNameIdCombotree.getValue()+""!=""){
							conditions.push({
								field: 'a.C_UNIT_ID',
								fieldType:'INT',
								matchType:'EQ',
								name:unitNameIdCombotree,
								type:2,
								value:unitNameIdCombotree.getValue()
							});
						}
						if($('#searchStartfindTimeDivId').val()){
							conditions.push({
								field: 'C_LOG_DATE',
								fieldType:'DATE',
								matchType:'GE',
								name:"searchStartfindTime",
								type:1,
								value:$('#searchStartfindTimeDivId').val()+" 00:00:00"
							});
						}
						if($('#searchEndfindTimeDivId').val()){
							conditions.push({
								field: 'C_LOG_DATE',
								fieldType:'DATE',
								matchType:'LE',
								name:"searchEndfindTime",
								type:1,
								value:$('#searchEndfindTimeDivId').val()+" 23:59:59"
							});
						}
				
						if($('#searchcAskPersonName').val()){
							conditions.push({
								field: 'askPersonName',
								fieldType:'STRING',
								matchType:'LIKE',
								name:"searchcAskPersonName",
								type:1,
								value:$('#searchcAskPersonName').val()
							});
						}
							overhaulLogDatatables.draw();
					});
					
					$('#btnReset').on('click',function(){
						$("#searchsubmitUserName").val("");
						$("#searchsubmitUserName").trigger("chosen:updated");
						$("#searchcheckAttendance").val("");
						unitNameIdCombotree.setValue(null);
						$("#searchStartfindTimeDiv").val("");
						$("#searchEndfindTimeDiv").val("");
						$("#searchStartfindTimeDivId").val("");
						$("#searchEndfindTimeDivId").val("");
						$("#searchcAskPersonName").val("");
						getUserList();
					});
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
								render : "#searchsubmitUserName",
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
						render : "#searchsubmitUserName",
						datasource :${dutyUsers},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						},
						callback: function(data){
						
						}
					}).render();
				}
			}
		/* function searchFunction(){
			conditions=[];
			if($('#searchsubmitUserName').val()){
				conditions.push({
					field: 'a.C_SUBMIT_USER_ID',
					fieldType:'INT',
					matchType:'EQ',
					name:'searchsubmitUserName',
					type:1,
					value:$('#searchsubmitUserName').val()
				});
			}
			if($("#searchcheckAttendance").val()!=""){
				conditions.push({
					field: 'a.C_DUTY_USER_NAME',
					fieldType:'STRING',
					matchType:'LIKE',
					name:'searchcheckAttendance',
					type:1,
					value:$("#searchcheckAttendance").val().trim()
				});
			}
			if(unitNameIdCombotree.getValue()!=undefined&&unitNameIdCombotree.getValue()+""!=""){
				conditions.push({
					field: 'a.C_UNIT_ID',
					fieldType:'INT',
					matchType:'EQ',
					name:unitNameIdCombotree,
					type:2,
					value:unitNameIdCombotree.getValue()
				});
			}
			if($('#searchStartfindTimeDivId').val()){
				conditions.push({
					field: 'C_LOG_DATE',
					fieldType:'DATE',
					matchType:'GE',
					name:"searchStartfindTime",
					type:1,
					value:$('#searchStartfindTimeDivId').val()+" 00:00:00"
				});
			}
			if($('#searchEndfindTimeDivId').val()){
				conditions.push({
					field: 'C_LOG_DATE',
					fieldType:'DATE',
					matchType:'LE',
					name:"searchEndfindTime",
					type:1,
					value:$('#searchEndfindTimeDivId').val()+" 23:59:59"
				});
			}
			if($('#searchOutUnitName').val()){
				conditions.push({
					field: 'outUnitName',
					fieldType:'STRING',
					matchType:'LIKE',
					name:"searchOutUnitName",
					type:1,
					value:$('#searchOutUnitName').val()
				});
			}
			if($('#searchcAskPersonName').val()){
				conditions.push({
					field: 'askPersonName',
					fieldType:'STRING',
					matchType:'LIKE',
					name:"searchcAskPersonName",
					type:1,
					value:$('#searchcAskPersonName').val()
				});
			}
// 			overhaulLogDatatables._datatables.search(JSON.stringify(conditions)).draw();
		} */
		//验证是否到了封存时间
		function isHidden(plogDate){
			var logDate = new Date(plogDate).getTime();
			var myDate = new Date();
			var nowDate = myDate.getTime();
			//一天毫秒值
			var firstDay = 24*3600*1000;
			//五天毫秒值
			var fifthDay = 5*24*3600*1000
		return nowDate-logDate>fifthDay;
// 			return false;
		}
		
        </script>
    </body>
</html>