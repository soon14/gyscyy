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
				<li class="active">生产运营计划</li>
				<li class="active">指标类计划</li>
				<li class="active">月度生产工作计划</li>
			</ul><!-- /.breadcrumb -->
		</div>
	
		<div class="page-content">
			<div class="col-lg-12 col-md-12 col-sm-12 search-content  padding-zero">
				<div class="form-inline text-left" role="form">
				<div class="clearfix">
					<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                       <label class="searchLabel">单位名称</label>：
                       <div class="padding-zero inputWidth  text-left">
                          <select id="unitId" class="" ></select>
                       </div>
                    </div>
                    
                    <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12  padding-zero">
	                    <label class="searchLabel">计划日期</label>：
	                    <div class="form-group dateInputOther padding-zero text-left">
	                        <div id="searchPlanTimeStartDiv" style="border:none; padding:0px;"></div>
	                    </div>
	                    <div class="toLabel" for="searchPlanTimeStartDiv" >~</div>
	                    <div class="form-group dateInputOther padding-zero text-left">
	                        <div id="searchPlanTimeEndDiv" style="border:none; padding:0px;"></div>
	                    </div>
	                </div>
                    
                    <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; ">
                          <button id="btnSearchAccident" class="btn btn-xs btn-primary">
                              <i class="glyphicon glyphicon-search"></i>
                         		     查询
                          </button>
                          <button id="btnResetAccident" class="btn btn-xs btn-primary" >
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
						<h5 class='table-title header smaller blue' >月度生产工作计划</h5>
						<table id="annualProductionJob_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
	                                <th>计划内容</th>
	                                <th>工作名称</th>
	                                <th>计划日期</th>
<!-- 	                                <th>流程状态</th> -->
	                                <th>完成状态</th>
	                                <th>完成时间</th>
	                                <th>鉴定结果</th>
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
			var exportExcelAccident="";
			var listFormDialog;
			var annualProductionJobDatatables;
			jQuery(function($) {
				seajs.use(['datatables', 'confirm','combobox','my97datepicker'], function(A){
					var conditions=[];
					var type = ${type};
					var answerht = ${answerht};
					//单位名称
					var unitIdCombobox = new A.combobox({
						render: "#unitId",
						datasource:${unitIdList},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					//计划月份开始时间 
					var searchPlanTimeStartDiv = new A.my97datepicker({
						id: 'searchPlanTimeStartDivId',
						name:'planTime',
						render:'#searchPlanTimeStartDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "#F{$dp.$D(\\'searchPlanTimeEndDivId\\')}",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					//计划月份结束时间
					var searchPlanTimeEndDiv = new A.my97datepicker({
						id: 'searchPlanTimeEndDivId',
						name:'planTime',
						render:'#searchPlanTimeEndDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'searchPlanTimeStartDivId\\')}",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					
					annualProductionJobDatatables = new A.datatables({
						render: '#annualProductionJob_table',
						options: {
					        "ajax": {
					            "url": format_url("/annualProductionJob/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
			    						field: 'ap.C_TYPE',
			    						fieldType:'STRING',
			    						matchType:'EQ',
			    						value:type
			    					});
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
						              {data: "unitName",name:"unitId",width: "auto",orderable: true},
						              {data: "content",width: "auto",orderable: true},
						              {data: "workName",width: "auto",orderable: true},
						              {data: "planMonthTimeStr",name:"planTime",width: "auto",orderable: true},
// 						              {data: "approveStatusName",name:"approveStatus",width: "auto",orderable: true},
						              {data: "completeStatusName",name:"completeStatus",width: "auto",orderable: true},
						              {data: "completeDate",width: "auto",orderable: true},
						              {data: "resultName",name:"result",width: "auto",orderable: true}
							          ],
							          fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											 if(exportExcelAccident){
												 exportExcels(format_url("/annualProductionJob/exportExcel/"+type),JSON.stringify(conditions));
											 }
											 exportExcelAccident="";
										 },
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							if(answerht=="1"){
            								A.loadPage({
    											render : '#page-container',
    											url : format_url('/annualProductionJob/getAdd/'+type)
    										});
            							}else{
            								 alert("只有计划经营处负责人可以新增！");
            			                      return;
            							}
            							
            						}
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = annualProductionJobDatatables.getSelectRowDatas();
										var ids = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												if (data[i].approveStatus != "0") {
													alert('记录中包含已经在审批流程中的记录不能删除!');
													return;
												}
												ids.push(data[i].id);
											}
										}
										
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										var url = format_url('/annualProductionJob/bulkDelete/'+ids);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													if(result.result == 'success'){
														alert('删除成功');
														annualProductionJobDatatables.draw(false);
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
							},{  
        						id:"dc",
        						label:"导出",
        						icon:"glyphicon glyphicon-download",
        						className:"btn-primary",
        						events:{
        							click:function(event){
            							$('#btnSearchAccident').click();
            							exportExcelAccident="exportExcelAccident";
            						}
        						}
        					}],
							btns: [
// 							       {
// 								id:"submit",
// 							 	label: "提交审核",
// 								icon: "fa fa-check-square-o bigger-130",
// 								className: "edit",
// 								render: function(btnNode, data){
// //	 								var loginUserId = '${sysUserEntity.id}'
// 									if(data.approveStatus!="0"){
// 										btnNode.hide();
// 									}
// //	 								if(data.uploadPerson!=loginUserId){
// //	 									btnNode.hide();
// //	 								}
// 								},
// 								events:{
// 									click: function(event, nRow, nData){
// 										var id = nData.id;
// 										workticketDialog = new A.dialog({
// 											title:"选择生产技术处人员",
// 											url:format_url("/annualProductionJob/sureSubmit?id="+ id+"&type="+type),
// 											height:500,
// 											width:600,
// 										}).render();
// 									}
// 								}
// 							},
							       {
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								render: function(btnNode, data){
//	 								var loginUserId = '${sysUserEntity.id}'
									if(data.approveStatus!="0"){
										btnNode.hide();
									}
//	 								if(data.uploadPerson!=loginUserId){
//	 									btnNode.hide();
//	 								}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										A.loadPage({
											render : '#page-container',
											url : format_url('/annualProductionJob/getEdit/' + id)
										});
									}
								}
							}
							, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								render: function(btnNode, data){
//	 								var loginUserId = '${sysUserEntity.id}'
									if(data.approveStatus!="0"){
										btnNode.hide();
									}
//	 								if(data.uploadPerson!=loginUserId){
//	 									btnNode.hide();
//	 								}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/annualProductionJob/deleteOne/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											annualProductionJobDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						} ,
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
										url : format_url("/annualProductionJob/getDetail/"+ id)
									});
								}
							}
						},{
							id:"441",
						 	label: "鉴定",
							icon: "fa fa-gavel bigger-130",
							className: " edit",
							render: function(btnNode, data){
								var unitId = '${sysUserEntity.unitId}'
								if(unitId!="144"){
									btnNode.hide();
								}
							},
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
												workticketDialog = new A.dialog({
													title:"鉴定",
													url:format_url("/annualProductionJob/invalid?id="+ id),
													height:350,
													width:600
												}).render();
								}
							}
					}]
						}
					}).render();
					$('#btnSearchAccident').on('click',function(){
						conditions=[];
						//生产单位
						if($("#unitId").val()){
	    					conditions.push({
	    						field: 'ap.C_UNIT_ID',
	    						fieldType:'STRING',
	    						matchType:'EQ',
	    						value:$("#unitId").val()
	    					});
    					}
						if($("#searchPlanTimeStartDivId").val()!=""){
							conditions.push({
								field: 'ap.C_PLAN_TIME',
								fieldType:'DATE',
								matchType:'GE',
								value:$("#searchPlanTimeStartDivId").val()+" 00:00:00"
							});
						}
						if($("#searchPlanTimeEndDivId").val()!=""){
							conditions.push({
								field: 'ap.C_PLAN_TIME',
								fieldType:'DATE',
								matchType:'LE',
								value:$("#searchPlanTimeEndDivId").val()+" 23:59:59"
							});
						}
						annualProductionJobDatatables.draw();
					});
					//重置条件并查询
					$('#btnResetAccident').on('click',function(){
						$("#unitId").val("");
						$("#unitId").trigger("chosen:updated");
						$("#searchPlanTimeStartDivId").val("");
						$("#searchPlanTimeEndDivId").val("");
						conditions=[];
						annualProductionJobDatatables.draw();
					});
				});
			});
			function goBackToSubmitPerson(id,selectUser){//回调函数
				var url =format_url("/annualProductionJob/submit?workId="+id+"&selectUser="+selectUser);
				$.ajax({
					contentType: "application/json",
					dataType:"JSON",
					url : url,
					success: function(result){
						if(result.result=="success"){
							alert('审批成功');
							A.loadPage({
								render : '#page-container',
								url:format_url("/annualProductionJob/index/2"),
							});
						}else{
							alert(result.errorMsg);
						}
					},
					error:function(v,n){
						alert('审批失败');
					}
				});
				
			}
        </script>
    </body>
</html>