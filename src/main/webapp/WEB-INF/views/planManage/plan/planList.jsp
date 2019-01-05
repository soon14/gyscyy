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
					计划管理
				</li>
				<li class="active">计划管理</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-lg-12 col-md-12 col-sm-12 padding-zero search-content">
				<div class="form-inline text-left " role="form">
				 <div class="clearfix">
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="searchunitIdDiv">单位名称</label>：
                            <div id="searchunitIdDiv" class="inputWidth text-left padding-zero"></div>
                        </div>
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" >
                            <label class="searchLabel" for="searchuserId">填报人</label>：
                            <div class="padding-zero inputWidth  text-left">
                                <select id="searchuserId" class="" name="searchtype"></select>
                            </div>
                        </div>
                         <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="searchcycleDiv">计划周期</label>：
                               <div class="padding-zero inputWidth  text-left">
                              <select id="searchcycle" class="" name="searchcycle"></select>
                              </div>
                        </div>
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" >
                            <label class="searchLabel" for="searchtype">计划类型</label>：
                            <div class="padding-zero inputWidth  text-left">
                                <select id="searchtype" class="" name="searchtype"></select>
                            </div>
                        </div>
                    </div>
                    <div class="clearfix">
                        <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="searchtime">计划时间</label>：
                            <div class="form-group dateInputOther padding-zero text-left">
                             	<div id="searchtimeDiv" 		style="border:none; padding:0px;"></div>
                            </div>
<!--                             <div class="toLabel" for="searchendTime" >~</div> -->
<!--                             <div class="form-group dateInputOther padding-zero text-left"> -->
<!--                                 <div id="searchtimeEndDiv"   style="border:none; padding:0px;"></div> -->
<!--                             </div> -->
                        </div>
                         <div class="form-group col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-zero">
                         </div>
	                   <div class="form-group col-lg-2 col-md-2 col-sm-12 col-xs-12 padding-zero btnSearchBottom"style="text-align:right;" >
                            <button id="btnSearch" class="btn btn-xs btn-primary" >
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
						<h5 class='table-title header smaller blue' >计划管理</h5>
						<table id="plan_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>单位名称</th>
	                                <th>填报人</th>
	                                <th>计划周期</th>
	                                <th>计划类型</th>
	                                <th>计划时间</th>
	                                <th>备注</th>
	                                <th>状态</th>
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
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					//日期组件
					var searchtimeDatePicker = new A.my97datepicker({
						id: "searchtimeId",
						name:"searchtime",
						render:"#searchtimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy"
						}
					}).render();
					var timeEndDatePicker = new A.my97datepicker({
						id: "searchtimeEndId",
						name:"searchtimeEnd",
						render:"#searchtimeEndDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy"
						}
					}).render();
					var searchplanCycle = new A.combobox({
						render : "#searchcycle",
						datasource : ${planCycle},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					var searchplanType = new A.combobox({
						render : "#searchtype",
						datasource : ${planType},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					var searchuserId = new A.combobox({
						render : "#searchuserId",
						datasource : ${searchuser},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					//部门控件下拉树
					var searchunitId = new A.combotree({
					render: "#searchunitIdDiv",
					name: 'searchunitId',
					//返回数据待后台返回TODO
					datasource: ${unitNameIdTreeList},
					width:"110px",
					options: {
						treeId: 'searchunitId',
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
					var planDatatables = new A.datatables({
						render: '#plan_table',
						options: {
					        "ajax": {
					            "url": format_url("/plan/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	if(d.search.value){
						            	d.conditions = JSON.parse(d.search.value);
						            	if(d.conditions){
						            		for(var index in d.conditions){
						            			if(d.conditions[index].type==1){
						            			$("#"+d.conditions[index].name).val(d.conditions[index].value);
						            			$("#"+d.conditions[index].name).trigger("chosen:updated");
						            			}
						            			if(d.conditions[index].type==2){
						            				searchunitId.setValue(d.conditions[index].value);
							            		}
						            		}
						            	}
					            	}
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							bStateSave: true,
							searching: true,
							iCookieDuration:cookieTime,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "unitName",name: "unitId",width: "auto",orderable: true}, 
							          {data: "userName",name: "userId",width: "auto",orderable: true},
							          {data: "cycleName",name: "cycle",width: "auto",orderable: true}, 
							          {data: "typeName",name: "type",width: "auto",orderable: true},
							          {orderable: false,"width":"auto", "sClass": "center",render : function(data, type, row, meta) {
						                 	var time=row.time;
						                 	if(row.timeEnd){
						                 		time+="-"+row.timeEnd;
						                 	}
						                   return time;  
						               	} },
							          {data: "remark",width: "auto",orderable: true},
							          {data: "statusName",name: "status",width: "auto",orderable: true}, 
							          ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							A.loadPage({
											render : '#page-container',
											url : format_url('/plan/getAdd')
										});
            						}
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = planDatatables.getSelectRowDatas();
										var ids = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
											}
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										var url = format_url('/plan/allDelete');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													if(result.result=="success"){
														alert('删除成功');
														planDatatables.draw(false);
													}else{
														alert(result.errorMsg);
													}
												},
												error:function(v,n){
													alert('操作失败');
												}
											});
										});
									}
								}
							}],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								render: function(btnNode, data){
									if(!(data.status==0||data.status==1)){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										A.loadPage({
											render : '#page-container',
											url : format_url('/plan/getEdit'),
											data:{"id":id}
										});
									}
								}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								render: function(btnNode, data){
									if(data.status!=1){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/plan/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											if(result.result=="success"){
														alert('删除成功');
														planDatatables.draw(false);
													}else{
														alert(result.errorMsg);
													}
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						}, {
							id:"submit",
							label:"提交",
							icon: "fa fa-check-square-o bigger-130",
							className: "edit",
							render: function(btnNode, data){
								if(data.status!=1){
									btnNode.hide();
								}
							},
							events:{
								click: function(event, nRow, nData){
// 									if(!(nData.processStatus==0||nData.processStatus==1)){
// 										alert("该记录不是待提交或驳回状态，不能提交！");
// 										return;
// 									}
									listFormDialog = new A.dialog({
										width: 850,
										height: 481,
										title: "计划管理提交人",
										url:format_url('/plan/sureSubmitPerson'),
										closed: function(resule){
											var obj=new Object(); 
											var id = nData.id;
											var url =format_url('/plan/submit/'+ id);
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
				    												planDatatables.draw(false);
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
					},{
							id: "detail",
							label:"查看",
							icon: "fa fa-binoculars bigger-110",
							className: "blue ",
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									A.loadPage({
										render : '#page-container',
										url : format_url('/plan/getDetail'),
										data:{"id":id}
									});
								}
							}
						}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						if(searchunitId.getValue()!=null&&searchunitId.getValue()+""!=""){
	    					conditions.push({
	        					field: 'T.C_UNIT_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:searchunitId,
	        					type:2,
	        					value:searchunitId.getValue()
	        				});
						}
						if($('#searchuserId').val()){
	    					conditions.push({
	        					field: 'T.C_USER_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:'searchuserId',
	        					type:1,
	        					value:$('#searchuserId').val()
	        				});
						}
						if($('#searchcycle').val()){
	    					conditions.push({
	        					field: 'T.C_CYCLE',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:'searchcycle',
	        					type:1,
	        					value:$('#searchcycle').val()
	        				});
						}
						if($('#searchtype').val()){
	    					conditions.push({
	        					field: 'T.C_TYPE',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:'searchtype',
	        					type:1,
	        					value:$('#searchtype').val()
	        				});
						}
						if($('#searchtimeId').val()){
	    					conditions.push({
	        					field: 'T.C_TIME',
	        					fieldType:'STRING',
	        					matchType:'LE',
	        					name:'searchtimeId',
	        					type:1,
	        					value:$('#searchtimeId').val()
	        				});
						}
						if($('#searchtimeId').val()){
	    					conditions.push({
	        					field: 'T.C_TIME_END',
	        					fieldType:'STRING',
	        					matchType:'GE',
	        					name:'searchtimeId',
	        					type:1,
	        					value:$('#searchtimeId').val()
	        				});
						}
						planDatatables._datatables.search(JSON.stringify(conditions)).draw();
					});
					$('#btnReset').on('click',function(){
						searchunitId.setValue(null);
						$("#searchuserId").val("");
						$("#searchuserId").trigger("chosen:updated");
						$('#searchtimeId').val("");
						$("#searchcycle").val("");
						$("#searchcycle").trigger("chosen:updated");
						$("#searchtype").val("");
						$("#searchtype").trigger("chosen:updated");
					});
				});
			});
        </script>
    </body>
</html>