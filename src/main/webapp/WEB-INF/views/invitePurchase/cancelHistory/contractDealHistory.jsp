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
					招标采购
				</li>
				<li class="active">合同签订</li>
				<li class="active">历史</li>
			</ul><!-- /.breadcrumb -->
		</div>
	
		<div class="page-content">
			<div class="col-lg-12 col-md-12 col-sm-12 search-content  padding-zero">
				<div class="form-inline text-left" role="form">
				<div class="clearfix">
					<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                       <label class="searchLabel">所属部门</label>：
                       <div class="padding-zero inputWidth  text-left">
                          <select id="unitId" class="" ></select>
                       </div>
                    </div>
                    <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
	                   <label class="searchLabel " for="form-field-1">签订合同名称</label>：
	                    <input id="nameId" class="inputWidth text-left" placeholder="请输入签订合同名称" type="text" >
	                </div>
					<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                       <label class="searchLabel">供应商</label>：
                       <div class="padding-zero inputWidth  text-left">
                          <select id="supplierId" class="" ></select>
                       </div>
                    </div>
					
                 </div>
                 <div class="clearfix" >
                 	<div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12  padding-zero">
	                    <label class="searchLabel " for="form-field-1">签约时间</label>：
	                    <div class="form-group dateInputOther padding-zero text-left">
	                        <div id="dealTimeStartDiv" style="border:none; padding:0px;"></div>
	                    </div>
	                    <div class="toLabel" for="searchStartTimeRightDiv" >~</div>
	                    <div class="form-group dateInputOther padding-zero text-left">
	                        <div id="dealTimeEndDiv" style="border:none; padding:0px;"></div>
	                    </div>
	                </div>
                 	<div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12  padding-zero">
	                    <label class="searchLabel " for="form-field-1">存档时间</label>：
	                    <div class="form-group dateInputOther padding-zero text-left">
	                        <div id="createTimeStartDiv" style="border:none; padding:0px;"></div>
	                    </div>
	                    <div class="toLabel" for="searchStartTimeRightDiv" >~</div>
	                    <div class="form-group dateInputOther padding-zero text-left">
	                        <div id="createTimeEndDiv" style="border:none; padding:0px;"></div>
	                    </div>
	                </div>
		   		</div>
		   		<div class="clearfix" >
		   			<div class="form-group col-lg-12 col-md-6 col-sm-6 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; ">
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
						<h5 class='table-title header smaller blue' >合同签订撤回历史</h5>
						<table id="contractDealHis_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>所属部门</th>
	                                <th>合同编号</th>
	                                <th>合同名称</th>
	                                <th>供应商</th>
	                                <th>签约时间</th>
	                                <th>存档时间</th>
	                                <th>撤回原因</th>
	                                <th>撤回时间</th>
	                                <th>撤回操作人员</th>
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
			jQuery(function($) {
				seajs.use(['datatables', 'confirm','combobox','my97datepicker'], function(A){
					var conditions=[];
					var itemType = ${itemType};
					//所属部门
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
					
					//供应商
					var supplierIdCombobox = new A.combobox({
						render: "#supplierId",
						datasource:${supplierIdList},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					//签约开始时间
					var dealTimeStartDiv = new A.my97datepicker({
						id: 'dealTimeStartDivId',
						name:'dealTime',
						render:'#dealTimeStartDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "#F{$dp.$D(\\'dealTimeEndDivId\\')}",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					
					//签约结束时间
					var dealTimeEndDiv = new A.my97datepicker({
						id: 'dealTimeEndDivId',
						name:'dealTime',
						render:'#dealTimeEndDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'dealTimeStartDivId\\')}",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					
					//存档开始时间
					var createTimeStartDiv = new A.my97datepicker({
						id: 'createTimeStartDivId',
						name:'createTime',
						render:'#createTimeStartDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "#F{$dp.$D(\\'createTimeEndDivId\\')}",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					
					//存档结束时间
					var createTimeEndDiv = new A.my97datepicker({
						id: 'createTimeEndDivId',
						name:'createTime',
						render:'#createTimeEndDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'createTimeStartDivId\\')}",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					
					var contractDealHisDatatables = new A.datatables({
						render: '#contractDealHis_table',
						options: {
					        "ajax": {
					            "url": format_url("/cancelHistory/seachContractDeal"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
				     					field: 'cd.C_STATUS',
				     					fieldType:'STRING',
				     					matchType:'EQ',
				     					value:0
				     				});
					            	conditions.push({
				     					field: 'ca.C_ITEM_TYPE',
				     					fieldType:'STRING',
				     					matchType:'EQ',
				     					value:4
				     				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false},
							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "unitName",width: "auto",orderable: false}, 
							          {data: "contractCode",width: "auto",orderable: false}, 
							          {data: "itemName",width: "auto",orderable: false}, 
							          {data: "supplierName",width: "auto",orderable: false}, 
							          {data: "itemTimeStr",width: "auto",orderable: false},  
							          {data: "createTimeStr",width: "auto",orderable: false},
							          {data: "cancelReason",width: "auto",orderable: false},
							          {data: "cancelTimeStr",width: "auto",orderable: false},
							          {data: "userName",width: "auto",orderable: false}
							          ],
							toolbars: [{
        						label:"返回",
        						icon:"fa fa-reply",
        						className:"btn-primary",
        						events:{
            						click:function(event){
            							A.loadPage({
											render : '#page-container',
											url : format_url('/contractDeal/index')
										});
            						}
        						}
        					}],
							btns: [
// 							, {
// 								id:"delete",
// 								label:"删除",
// 								icon: "fa fa-trash-o bigger-130",
// 								className: "red del",
// 								events:{
// 									click: function(event, nRow, nData){
// 										var id = nData.id;
// 										var url =format_url('/produceReply/deleteOne/'+ id);
// 										A.confirm('您确认删除么？',function(){
// 											$.ajax({
// 												url : url,
//         										contentType : 'application/json',
//         										dataType : 'JSON',
//         										type : 'DELETE',
//         										success: function(result){
//         											alert('删除成功');
//         											produceReplyDatatables.draw(false);
//         										},
//         										error:function(v,n){
//         											alert('操作失败');
//         										}
// 											});
// 										});
// 									}
// 								}
// 						} 
// 							,{
// 							id:"submit",
// 						 	label: "提交审核",
// 							icon: "fa fa-check-square-o bigger-130",
// 							className: "edit",
// 							render: function(btnNode, data){
// 								var loginUserId = '${sysUserEntity.id}'
// 								console.log(data);
// 								console.log(data.status);
// 								if(data.status!="0"){
// 									btnNode.hide();
// 								}
// 								if(data.uploadPerson!=loginUserId){
// 									btnNode.hide();
// 								}
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
// 						} 
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
										url : format_url("/cancelHistory/getDetail/"+ id+"/"+itemType)
									});
								}
							}
						}]
						}
					}).render();
					$('#btnSearchAccident').on('click',function(){
						conditions=[];
						//所属部门
						if($("#unitId").val()){
	    					conditions.push({
	    						field: 'cd.C_UNIT_ID',
	    						fieldType:'STRING',
	    						matchType:'EQ',
	    						value:$("#unitId").val()
	    					});
    					}

						//签订合同名称
						if($("#nameId").val()){
	    					conditions.push({
	    						field: 'cd.C_NAME',
	    						fieldType:'STRING',
	    						matchType:'LIKE',
	    						value:$("#nameId").val()
	    					});
    					}
						//供应商
						if($("#supplierId").val()){
	    					conditions.push({
	    						field: 'cd.C_SUPPLIER_ID',
	    						fieldType:'STRING',
	    						matchType:'EQ',
	    						value:$("#supplierId").val()
	    					});
    					}
						//签约开始时间
						if($("#dealTimeStartDivId").val()){
	    					conditions.push({
	    						field: 'cd.C_DEAL_TIME',
	    						fieldType:'DATE',
	    						matchType:'GE',
	    						value:$("#dealTimeStartDivId").val()+" 00:00:00"
	    					});
    					}
						//签约结束时间
    					if($("#dealTimeEndDivId").val()){
	    					conditions.push({
	    						field: 'cd.C_DEAL_TIME',
	    						fieldType:'DATE',
	    						matchType:'LE',
	    						value:$("#dealTimeEndDivId").val()+" 23:59:59"
	    					});
    					}
						//存档开始时间
						if($("#createTimeStartDivId").val()){
	    					conditions.push({
	    						field: 'cd.C_CREATE_TIME',
	    						fieldType:'DATE',
	    						matchType:'GE',
	    						value:$("#createTimeStartDivId").val()+" 00:00:00"
	    					});
    					}
						//存档结束时间
    					if($("#createTimeEndDivId").val()){
	    					conditions.push({
	    						field: 'cd.C_CREATE_TIME',
	    						fieldType:'DATE',
	    						matchType:'LE',
	    						value:$("#createTimeEndDivId").val()+" 23:59:59"
	    					});
    					}
						contractDealHisDatatables.draw();
					});
					//重置条件并查询
					$('#btnResetAccident').on('click',function(){
						$("#unitId").val("");
						$("#unitId").trigger("chosen:updated");
						$("#nameId").val("");
						$("#supplierId").val("");
						$("#supplierId").trigger("chosen:updated");
						$("#dealTimeStartDivId").val("");
						$("#dealTimeStartDiv").val("");
						$("#dealTimeEndDivId").val("");
						$("#dealTimeEndDiv").val("");
						$("#createTimeStartDivId").val("");
						$("#createTimeStartDiv").val("");
						$("#createTimeEndDivId").val("");
						$("#createTimeEndDiv").val("");
						conditions=[];
						contractDealHisDatatables.draw();
					});
				});
			});
        </script>
    </body>
</html>