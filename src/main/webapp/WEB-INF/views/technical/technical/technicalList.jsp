<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/views/common/meta.jsp"%>
</head>
<body>
	<div class="breadcrumbs ace-save-state" id="breadcrumbs">
		<ul class="breadcrumb">
			<li><i class="ace-icon fa fa-home home-icon"></i> <a
				href="javascript:void(0);" onclick="firstPage()">首页</a></li>
			<li class="active">技术监督</li>
			<li class="active">计划</li>
		</ul>
	</div>
	<div class="page-content">
		<div
			class="col-lg-12 col-md-12 col-sm-12 search-content  padding-zero">
			<div class="form-inline text-left" role="form">
				<div class="clearfix">
					<div
						class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
						<label class="searchLabel" for="searchunitNameId">单位名称</label>：
						<div id="searchunitNameId"
							class="inputWidth text-left padding-zero"></div>
					</div>

					<div
						class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12 padding-zero">
						<label class="searchLabel" for="searchgroupId">填报人</label>：
						<div class="inputWidth padding-zero  text-left">
							<select id="searchguarderName" class="form-control chosen-select"></select>
						</div>
					</div>
					<div
						class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12  padding-zero">
						<label class="searchLabel " for="form-field-1">时间</label>：
						<div class="form-group dateInputOther padding-zero text-left">
							<div id="searchStartplandateEndDiv"
								style="border: none; padding: 0px;"></div>
						</div>
						<div class="toLabel" for="searchStartTimeRightDiv">~</div>
						<div class="form-group dateInputOther padding-zero text-left">
							<div id="searchEndplandateEndDiv"
								style="border: none; padding: 0px;"></div>
						</div>
					</div>
				</div>
				<div class="clearfix">
					<div
						class="form-group col-lg-12 col-md-12 col-sm-6 col-xs-12 padding-zero btnSearchBottom"
						style="text-align: right;">
						<button id="btnSearch" class="btn btn-xs btn-primary">
							<i class="glyphicon glyphicon-search"></i> 查询
						</button>
						<button id="btnReset" class="btn btn-xs btn-primary">
							<i class="glyphicon glyphicon-repeat"></i> 重置
						</button>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-xs-12">
				<div class="widget-main no-padding">
					<h5 class="table-title header smaller blue">计划</h5>
					<table id="workTicket-table"
						class="table table-striped table-bordered table-hover"
						style="width: 100%;">
						<thead>
							<tr>
								<th style="display: none;">主键</th>
								<th class="center sorting_disabled" style="width: 50px;"><label
									class="pos-rel"> <input type="checkbox" class="ace" />
										<span class="lbl"></span>
								</label></th>
								<th>序号</th>
								<th>单位名称</th>
								<th>填报人</th>
								<th>时间</th>
								<th>状态</th>
								<th>操作</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
			var workticketDialog;
			var workTicketDatatables ="";
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker','selectbox'], function(A){
					var conditions=[];
					
					var unitNameIdCombotree = new A.combotree({
						render: "#searchunitNameId",
						name: 'unitId',
						//返回数据待后台返回TODO
						datasource: ${unitNameIdTreeList},
						width:"210px",
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
					
					
					//工作负责人
					var searchguarderName = new A.combobox({
						render : "#searchguarderName",
						datasource : ${requestUsers},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					
					
					//状态下拉框
					var jdzyCombobox = new A.combobox({
						render: '#searchworkStatus',
						datasource:${statusTypes},
						allowBlank:true,
						options:{
							"allow_single_deselect": true,
							"disable_search_threshold":10
						}
					}).render();
					
					
					
					var searchStartplandateEndDiv = new A.my97datepicker({
						id: 'searchStartplandateEndDivId',
						name:'changeAllowDate',
						render:'#searchStartplandateEndDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "#F{$dp.$D(\\'searchEndplandateEndDivId\\')}",
								minDate: "",
								dateFmt: "yyyy"
						}
					}).render();
					
					var searchEndplandateEndDiv = new A.my97datepicker({
						id: 'searchEndplandateEndDivId',
						name:'changeAllowDate',
						render:'#searchEndplandateEndDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'searchStartplandateEndDivId\\')}",
								dateFmt: "yyyy"
						}
					}).render();
					var exportExcel="";
					 workTicketDatatables = new A.datatables({
						render: '#workTicket-table',
						options: {
					        "ajax": {
					            "url": format_url("/technical/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 280,
							columns: [
							          {data:"id", visible:false,orderable:false},
							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	}},
							          {data: "unitName",width: "23%",orderable: true},
							          {data: "fillName",width: "22%",orderable: true}, 
							          {data: "time",width: "22%",orderable: true}, 
							          {data: "spStatusName",name:"spStatus",width: "22%",orderable: true}
									 ],
									 fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
										 if(exportExcel){
											 exportExcels(format_url("/technical/exportExcel"),JSON.stringify(conditions));
										 }
										 exportExcel="";
									 },
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
        							click: function(event, nRow, nData){
        								var unitId = ${unitId};
        								if(unitId=="112"||unitId=="113"||unitId=="114"||unitId=="115"||unitId=="116"||unitId=="117"||unitId=="138"||unitId=="141"){
        									A.loadPage({
    											render : '#page-container',
    											url : format_url("/technical/getAdd")
    										});
        								}else{
        									alert("只有风场和中心人员才可以新增！");
        								}
										
									}
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = workTicketDatatables.getSelectRowDatas();
										var ids = [];
										var userIds = [];
										var status = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
												userIds.push(data[i].fillId);
												status.push(data[i].spStatus);
											}
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										var loginUser = ${userId};
										var loginName ="${loginName}";
										for(var j=0;j<userIds.length;j++){
											if(userIds[j]!=loginUser&&loginName!="super"){
												alert('记录中包含不是当前登陆人的记录不能删除!');
												return;
											}
											if(status[j]!="1"){
												alert('记录中包含不是待提交的记录不能删除!');
												return;
											}
										}
										var url = format_url('/technical/bulkDelete/'+ids);
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
														workTicketDatatables.draw(false);
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
            							$('#btnSearch').click();
            							exportExcel="exportExcel";
            						}
        						}
        					}
							],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green",
								render: function(btnNode, data){
									if(data.spStatus=="1"||data.spStatus=="5"){
										var loginName='${userEntity.loginName}';
										var loginId='${userEntity.id}';
										if(loginName=="super"||data.fillId==loginId){
											//审核通过的，登陆人是super的，可以修改,不隐藏
										}else{
											btnNode.hide();
										}
									}else{
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										$.ajax({
											url: format_url('/technical/updateValidate/'+id),
											contentType : 'application/json',
											dataType : 'JSON',
											type: 'POST',
											data : JSON.stringify(nData),
											success: function(result){
												if(result.result == 'success'){
													A.loadPage({
														render : '#page-container',
														url:format_url("/technical/getEdit/"+ id),
													});
            									} else{
            										alert(result.errorMsg);
            									}
											}
										});
									}
								}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								render: function(btnNode, data){
									if(data.spStatus=="1"){
										var loginName='${userEntity.loginName}';
										var loginId='${userEntity.id}';
										if(loginName=="super"||data.fillId==loginId){
											//审核通过的，登陆人是super的，可以删除,不隐藏
										}else{
											btnNode.hide();
										}
										
									}else{
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										$.ajax({
											url: format_url('/technical/deleteValidate/'+id),
											contentType : 'application/json',
											dataType : 'JSON',
											type: 'POST',
											data : JSON.stringify(nData),
											success: function(result){
												if(result.result == 'success'){
													var url =format_url('/technical/'+ id);
													A.confirm('您确认删除么？',function(){
														$.ajax({
															url : url,
			        										contentType : 'application/json',
			        										dataType : 'JSON',
			        										type : 'DELETE',
			        										success: function(result){
			        											alert('删除成功');
			        											workTicketDatatables.draw(false);
			        										},
			        										error:function(v,n){
			        											alert('操作失败');
			        										}
														});
													});
            									} else{
            										alert(result.errorMsg);
            									}
											}
										});
									}
								}
						}, {
								id:"436",
							 	label: "提交审核",
								icon: "fa fa-check-square-o bigger-130",
								className: " edit",
								render: function(btnNode, data){
									if(data.spStatus!="1"){
										btnNode.hide();
									}else{//这里控制自己提交自己的记录zzq20180313
										var tbr=data.fillName;
										var name='${userEntity.name}';
										if(tbr!=name){
											btnNode.hide();
										}
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										$.ajax({
											url: format_url('/technical/tijiaoValidate/'+id),
											contentType : 'application/json',
											dataType : 'JSON',
											type: 'POST',
											data : JSON.stringify(nData),
											success: function(result){
												if(result.result == 'success'){
													workticketDialog = new A.dialog({
														title:"提交确认",
														url:format_url("/technical/sureSubmit?id="+ id),
														height:400,
														width:500
													}).render();
            									} else{
            										alert(result.errorMsg);
            									}
											}
										});
									}
								}
						}, 
						
						{
							    id:"440",
								label:"查看",
								icon: "fa fa-binoculars bigger-110",
								className: "blue",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										A.loadPage({
											render : '#page-container',
											url:format_url("/technical/detail/"+ id),
										});
									}
								}
						},{
							id:"cexiao",
							label:"撤销",
							icon: "fa fa-arrow-left bigger-130",
							className: "red del",
							render: function(btnNode, data){
								if(data.spStatus!="9"){
									btnNode.hide();
								}else{
									var tbr=data.fillName;
									var name='${userEntity.name}';
									if(tbr!=name){
										btnNode.hide();
									}
								}
							},
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									$.ajax({
										url: format_url('/technical/cexiaoValidate/'+id),
										contentType : 'application/json',
										dataType : 'JSON',
										type: 'POST',
										data : JSON.stringify(nData),
										success: function(result){
											if(result.result == 'success'){
												var url = format_url("/technical/disAgreeCx?id="+ id);
												A.confirm('您确认撤销么？',function(){
													$.ajax({
														url : url,
		        										contentType : 'application/json',
		        										dataType : 'JSON',
		        										type : 'POST',
		        										data : JSON.stringify(nData),
		        										success: function(result){
		        											alert('撤销成功');
		        											workTicketDatatables.draw(false);
		        										},
		        										error:function(v,n){
		        											alert('撤销失败');
		        										}
													});
												});
        									} else{
        										alert(result.errorMsg);
        									}
										}
									});
								}
							}
					}
					]}
					}).render();
					
					
					
					$('#btnSearch').on('click',function(){
						conditions=[];
						if(unitNameIdCombotree.getValue()!=null&&unitNameIdCombotree.getValue()!=""){
    						conditions.push({
            					field: 't.C_UNIT_ID',
            					fieldType:'LONG',
            					matchType:'EQ',
            					value:unitNameIdCombotree.getValue()
            				});
    					}
    					if($("#searchguarderName").val()!=""){
	    					conditions.push({
	        					field: 't.C_FILL_ID',
	        					fieldType:'LONG',
	        					matchType:'EQ',
	        					value:$('#searchguarderName').val()
	        				});
    					}
    					if($("#searchworkStatus").val()!=""){
    						//alert($('#searchworkStatus').val());
	    					conditions.push({
	    						field: 't.C_JDZY_ID',
	    						fieldType:'LONG',
	    						matchType:'EQ',
	    						value:$('#searchworkStatus').val()
	    					});
    					}
    					if($("#searchWcztStatus").val()!=""){
	    					conditions.push({
	    						field: 't.C_WCZT',
	    						fieldType:'LONG',
	    						matchType:'EQ',
	    						value:$('#searchWcztStatus').val()
	    					});
    					}
    					if($("#searchcontent").val()!=""){
	    					conditions.push({
	    						field: 't.C_GZZJ',
	    						fieldType:'STRING',
	    						matchType:'LIKE',
	    						value:$('#searchcontent').val()
	    					});
    					}
    					
    					if($("#searchStartplandateEndDivId").val()!=""){
	    					conditions.push({
	    						field: 't.C_TIME',
	    						fieldType:'STRING',
	    						matchType:'GE',
	    						value:$("#searchStartplandateEndDivId").val()
	    					});
    					}
    					if($("#searchEndplandateEndDivId").val()!=""){
	    					conditions.push({
	    						field: 't.C_TIME',
	    						fieldType:'STRING',
	    						matchType:'LE',
	    						value:$("#searchEndplandateEndDivId").val()
	    					});
    					}
    					
						workTicketDatatables.draw();
					});
					
					
					$('#btnReset').on('click',function(){
						conditions=[];
						unitNameIdCombotree.setValue();//
						$('#searchguarderName').val('');//
						$("#searchguarderName").trigger("chosen:updated");//
						$('#searchworkStatus').val('');//
						$("#searchworkStatus").trigger("chosen:updated");//
						$('#searchWcztStatus').val('');
						$("#searchWcztStatus").trigger("chosen:updated");
						$('#searchcontent').val('');
						
						$("#searchStartplandateEndDivId").val("");
						$("#searchEndplandateEndDivId").val("");
						
					});
					
				});
			});
			
			function goBackToSubmitPerson(id,selectUser){//回调函数
				
				var url =format_url("/technical/submit?workId="+id+"&selectUser="+selectUser);
				$.ajax({
					contentType: "application/json",
					dataType:"JSON",
					url : url,
					success: function(result){
						if(result.result=="success"){
							alert('操作成功');
							workTicketDatatables.draw(false);
						}else{
							alert(result.errorMsg);
							workTicketDatatables.draw(false);
						}
					},
					error:function(v,n){
						alert('操作失败');
						workTicketDatatables.draw(false);
					}
				});
				
			}
        </script>
</body>
</html>