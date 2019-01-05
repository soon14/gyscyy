<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>  
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
					<li class="active">
					计划管理</li>
					<li class="active">
					生产运营计划</li>
					<li class="active">指标类计划</li>
					<li class="active">年度生产指标计划</li>
					<li class="active">年度生产指标计划流程</li>
					<li class="active">查看</li>
				</ul>
		</div>
	<div class="page-content">
			<div style="float:right; margin-right:50px;">
				<button id="cancelBtn"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style="    margin-top: 14px;">
	       			<i class="fa fa-reply"></i>
	       			返回
	       		</button>
		    </div>
			<h5 class='table-title-withoutbtn header smaller blue' style="margin-right:50px;margin-left:30px;" >基础信息</h5>
							
		<div class="widget-main no-padding">
				<form class="form-horizontal" role="form"   id="addForm">
					<input class="col-md-12" id="farmId"  type="hidden" placeholder="" maxlength="20" value="${ entity.farmId }">
					<input class="col-md-12" id="year"  type="hidden" placeholder="" maxlength="20" value="${ entity.yearStr }">
				    <div class="col-md-12" style="margin-top:20px">
			       <div class="form-group">
			       		<label class="col-md-1 control-label no-padding-right">
							<span style="color:red;">*</span>场站名称
						</label>
						<div class="col-md-4">
               				<input class="col-md-12" readOnly type="text" value="${entity.farmName}">
	                	</div>
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;"></span>计划年份
					    </label>
					    <div class="col-md-4">
							<input  class="col-md-12"  type="text"  value="${ entity.yearStr }" readOnly/>
	                    </div>
					    
	               </div>
		           <div class="form-group">
		           						<label class="col-md-1 control-label no-padding-right">
							<span style="color:red;"></span>生产运行单位
						</label>
						<div class="col-md-4">
							<input class="col-md-12"  type="text"  value="${ entity.productionRunningName }" readOnly />

	                	</div>
	                	<label class="col-md-2 control-label no-padding-right">
					     生产技术处
						</label>
						   <div class="col-md-4">
		                       <input class="col-md-12"  readonly="readonly" type="text" value="${entity.productionSkillName}" placeholder="" >
		                   </div>
	               </div>
	    			<div class="form-group">
						<label class="col-md-1 control-label no-padding-right">
							计划经营处
						</label>
						<div class="col-md-4">
						<input class="col-md-12" readonly="readonly" type="text" value="${entity.planRunningName}" placeholder="" >
						</div>
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;"></span>单位领导
					    </label>
					    <div class="col-md-4">
							<input class="col-md-12"  type="text"  value="${ entity.leaderName }" readOnly />
	                    </div>
	               </div>
	               <div class="form-group">
						<label class="col-md-1 control-label no-padding-right">
							计划经营处下文执行
						</label>
						<div class="col-md-4">
						<input class="col-md-12" readonly="readonly" type="text" value="${entity.planRunningDownName}" placeholder="" >
						</div>
	               </div>
	               </div>
	            </form>
	       </div>
	   <div class="row" style="margin-right:70px;margin-left:30px;">
	    <div class="col-xs-12">	
	    <h5 class=" header smaller blue " style="margin-bottom:0px;border-bottom: 2px solid #438eb9!important;    font-weight: bold;">计划指标</h5>
		<div class="widget-main no-padding">
			<form id="overhaulProject">
				<table id="overhaulProject-table2"  class="table table-striped table-bordered table-hover" style="width:100%;">
					<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th style="text-align:center">场站名称</th>
	                                <th style="text-align:center">指标名称</th>
	                                <th style="text-align:center">计划对比</th>
	                                <th style="text-align:center">1月</th>
	                                <th style="text-align:center">2月</th>
	                                <th style="text-align:center">3月</th>
	                                <th style="text-align:center">4月</th>
	                                <th style="text-align:center">5月</th>
	                                <th style="text-align:center">6月</th>
	                                <th style="text-align:center">7月</th>
	                                <th style="text-align:center">8月</th>
	                                <th style="text-align:center">9月</th>
	                                <th style="text-align:center">10月</th>
	                                <th style="text-align:center">11月</th>
	                                <th style="text-align:center">12月</th>
	                                <th style="text-align:center">合计</th>
	                                <th style="text-align:center">操作</th>
                                </tr>
                            </thead>
				</table>
			</form>
		</div>
		</div>
		</div>
		
		 
</div>
		<script type="text/javascript">
		var listFormDialog;
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','selectbox','uploaddropzone'], function(A){
					var type = ${type};
					var unitId = $("#farmId").val();
					var year = $("#year").val();
					
					var conditions=[];
					overhaulProjectDatatables2 = new A.datatables({
						render: '#overhaulProject-table2',
						options: {
					        "ajax": {
					            "url": format_url("/annualProductionCapacity/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
				     					field: 'ac.C_TYPE',
				     					fieldType:'STRING',
				     					matchType:'EQ',
				     					value:type
				     				});
					            	conditions.push({
			        					field: 'date_format(ac.C_YEAR,"%Y")',
			        					fieldType:'DATE',
			        					matchType:'EQ',
			        					value:year
			        				});
					            	conditions.push({
			        					field: 'ac.C_FARM_ID',
			        					fieldType:'STRING',
			        					matchType:'EQ',
			        					value:unitId
			        				});
					            	d.type = type;
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							checked:false,
							bInfo:false,
							paging:false,
							ordering:false,
							columns: [{data:"id", visible:false},
							          {data: "farmName",width: "8%",orderable: false,
							        	  "createdCell": function (td, cellData, rowData, row, col) { 
								        	     if (rowData.tdHide=="show" ) {
								        	       	$(td).attr('rowspan',10);
								        	       	$(td).attr('align','center');
								        	       	var height=10*32;
								        	       	$(td).attr('style','height:'+height+'px;line-height:'+height+'px');
								        	     }else{
								        	    	 $(td).hide();
								        	     }
								        	}, 
								      },
							          {data: "quotaName",width: "10%",
								    	  "createdCell": function (td, cellData, rowData, row, col) { 
								        	     if (rowData.tdHideQuota=="show" ) {
								        	       	$(td).attr('rowspan',2);
								        	       	$(td).attr('align','center');
								        	       	var height=2*32;
								        	       	$(td).attr('style','height:'+height+'px;line-height:'+height+'px');
								        	     }else{
								        	    	 $(td).hide();
								        	     }
								        	}, 
								      }, 
							          {data: "planCompareName",width: "6%","sClass": "center"}, 
							          {data: "jan",width: "5.6%","sClass": "center"}, 
							          {data: "feb",width: "5.6%","sClass": "center"}, 
							          {data: "mar",width: "5.6%","sClass": "center"},  
							          {data: "apr",width: "5.6%","sClass": "center"},  
							          {data: "may",width: "5.6%","sClass": "center"},  
							          {data: "jun",width: "5.6%","sClass": "center"},  
							          {data: "jul",width: "5.6%","sClass": "center"},  
							          {data: "aug",width: "5.6%","sClass": "center"},  
							          {data: "sep",width: "5.6%","sClass": "center"},  
							          {data: "oct",width: "5.6%","sClass": "center"},  
							          {data: "nov",width: "5.6%","sClass": "center"},  
							          {data: "dec",width: "5.6%","sClass": "center"},  
							          {data: "total",width: "6%","sClass": "center"}
							          ],
							          fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											 if(exportExcelAccident){
												 exportExcels(format_url("/annualProductionCapacity/exportExcel/"+type),JSON.stringify(conditions));
											 }
											 exportExcelAccident="";
										 },
							toolbars: [
// 							           {
//         						label:"新增",
//         						icon:"glyphicon glyphicon-plus",
//         						className:"btn-success",
//         						events:{
//             						click:function(event){
//             							A.loadPage({
// 											render : '#page-container',
// 											url : format_url('/produceReply/getAdd')
// 										});
//             						}
//         						}
//         					},
// 							{  
//         						id:"dc",
//         						label:"导出",
//         						icon:"glyphicon glyphicon-download",
//         						className:"btn-primary",
//         						events:{
//         							click:function(event){
//             							$('#btnSearchAccident').click();
//             							exportExcelAccident="exportExcelAccident";
//             						}
//         						}
//         					}
							],
							btns: [
// 							{
// 							id:"submit",
// 						 	label: "提交审核",
// 							icon: "fa fa-check-square-o bigger-130",
// 							className: "edit",
// 							render: function(btnNode, data){
// 								var loginUserId = '${sysUserEntity.id}'
// 								if(data.planCompareId!="2"){
// 									btnNode.hide();
// 								}
// // 								if(data.uploadPerson!=loginUserId){
// // 									btnNode.hide();
// // 								}
// 							},
// 							events:{
// 								click: function(event, nRow, nData){
// 									debugger;
// 									var id = nData.id;
// 												workticketDialog = new A.dialog({
// 													title:"选择单位负责人",
// 													url:format_url("/accidentMeasuresPlan/sureSubmit?id="+ id),
// 													height:500,
// 													width:600,
// 												}).render();
// 								}
// 							}
// 						}, 
						{
							id: "history",
							label:"历史",
							icon: "fa fa-history bigger-130",
							className: "red ",
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
// 									if(!id){
// 										alert("没有历史数据！");
// 									}
            						listFormDialog = new A.dialog({
                						width: '1000',
                						height: '350',
                						title: "历史",
                						url:format_url("/quotaPlanHistory/toHistoryMessage/"+ id+"/"+type),
                						closed: function(){
//                 							annualProductionCapacityDatatables.draw(false);
                						}	
                					}).render()
									
								}
							}
						}]
						}
					}).render();
					$("#cancelBtn").on("click", function(){
						 $("#page-container").load(format_url('/annualProductionCapacity/quotaList'));
   					});
				});
			});
        </script>
    </body>
</html>