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
				<li class="active">设备管理</li>
				<li class="active">设备异动管理</li>
				<li class="active">设备异动报告</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-lg-12 col-md-12 col-sm-12 search-content  padding-zero">
				<div class="form-inline text-left" role="form">
				<div class="clearfix" style="width: 95%">
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
                            <label class="" for="reportCode">报告编码：</label>
                            <input id="reportCode" class="input-width text-left" placeholder="请输入报告编码" type="text">
                        </div>
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
                            <label class="" for="reportName">报告名称：</label>
                            <input id="reportName" class="input-width text-left" placeholder="请输入报告名称" type="text">
                        </div>
                         <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
                            <label class="">提交时间：</label>
                            <div class="form-group date-input padding-zero text-left">
                                <div id="submitDateBeginDiv"  style="border:none; padding:0px;"></div>
                            </div>
                            <label style="width: 2.6%;text-align: center">~</label>
                            <div class="form-group date-input padding-zero text-left">
                                <div id="submitDateEndDiv"   style="border:none; padding:0px;"></div>
                            </div>
                        </div>
                    </div>
                    <div class="clearfix" style="width: 95%">
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px;margin-left: 12px;" >
                            <label class="" for="submitPersionName">提交人：</label>
                            <input id="submitPersionName" class="input-width text-left" placeholder="请输入提交人" type="text">
                        </div>
                    </div>
                   <div class="clearfix" >
						<div class="form-group col-lg-12 col-md-12 col-sm-6 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; ">
                            <button id="btnSearch" class="btn btn-xs btn-primary">
                                <i class="glyphicon glyphicon-search"></i>
                           		     查询
                            </button>
                            <button id="btnReset" class="btn btn-xs btn-primary" >
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
						<h5 class='table-title header smaller blue' >设备异动报告</h5>
						<table id="equipAbnormalReport_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>报告编码</th>
	                                <th>报告名称</th>
	                                <th>提交人</th>
	                                <th>提交时间</th>
	                                <th>审批状态</th>
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
			var answer = ${answer};
			var listFormDialog;
			var userUnitRels = ${userUnitRels};
			//初始数据备份
			var processUserUnitRels = ${userUnitRels};
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					//开始时间
					var searchBeginStartfindTime = new A.my97datepicker({
						id: 'submitDateBegin',
						name:'submitDateBegin',
						render:'#submitDateBeginDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "#F{$dp.$D(\\'submitDateEnd\\')}",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					//结束时间
					var searchBeginEndtfindTime = new A.my97datepicker({
						id: 'submitDateEnd',
						name:'submitDateEnd',
						render:'#submitDateEndDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'submitDateBegin\\')}",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					var equipAbnormalReportDatatables = new A.datatables({
						render: '#equipAbnormalReport_table',
						options: {
					        "ajax": {
					            "url": format_url("/equipAbnormalReport/getDataList"),
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
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false},
							          {data: "reportCode",width: "20%",orderable: true},
							          {data: "reportName",width: "20%",orderable: true},
							          {data: "submitPersionName",width: "auto",orderable: true},
							          {data: "submitDate",width: "auto",orderable: true},
							          {data: "processStatusName",name:"processStatus",width: "10%",orderable: true}],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							if(answer!=false){
	                    					A.loadPage({
			    	    						render : '#page-container',
			    	    						url : format_url("/equipAbnormalReport/getAdd")
			    	    					});
	            						}else{
											alert("只有检修、集控中心专工及以上人员可以申请！");
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
										var data = equipAbnormalReportDatatables.getSelectRowDatas();
										var ids = [];
										var userIds = [];
										var status = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
												userIds.push(data[i].submitPersionId);
												status.push(data[i].processStatus);
											}
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										var loginUser = ${userId};
										var loginName ="${userLoginName}";
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
										var url = format_url('/equipAbnormalReport/deleteMore');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													equipAbnormalReportDatatables.draw(false);
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
									var loginUser = ${userId};
									var loginName ="${userLoginName}";
									if(data.submitPersionId!=loginUser&&loginName!="super"){
										btnNode.hide();
									}
									var processStatus = data.processStatus;
									if(processStatus!="1"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										A.loadPage({
		    	    						render : '#page-container',
		    	    						url:format_url('/equipAbnormalReport/getEdit/' + id),
		    	    					});
									}
								}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								render: function(btnNode, data){
									var loginUser = ${userId};
									var loginName ="${userLoginName}";
									if(data.submitPersionId!=loginUser&&loginName!="super"){
										btnNode.hide();
									}
									if(data.processStatus!="1"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/equipAbnormalReport/deleteOnlyOne/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											equipAbnormalReportDatatables.draw(false);
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
							id:"abnormalSubmit",
							label:"提交",
							icon: "fa fa-check-square-o bigger-130",
							className: "edit",
							render: function(btnNode, data){
								var loginUser = ${userId};
								var loginName ="${userLoginName}";
								if(data.submitPersionId!=loginUser&&loginName!="super"){
									btnNode.hide();
								}
								if(data.processStatus!="1"){
									btnNode.hide();
								}
							},
							events:{
								click: function(event, nRow, nData){
									if(!(nData.processStatus==0||nData.processStatus==1)){
										alert("该记录不是待提交或驳回状态，不能提交！");
										return;
									}
									processStatus = nData.processStatus;
									listFormDialog = new A.dialog({
										width: 850,
										height: 531,
										title: "提交审核",
										url:format_url('/equipAbnormalReport/sureSubmitPerson/'+processStatus),
										closed: function(resule){
											var obj=new Object(); 
											var id = nData.id;
											var status = nData.status;
											var url =format_url('/equipAbnormalReport/submit/'+ id);
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
				    												equipAbnormalReportDatatables.draw(false);
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
							icon: "fa fa-binoculars bigger-130",
							className: "blue ",
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									A.loadPage({
										render : '#page-container',
										url : format_url("/equipAbnormalReport/getDetail/"+ id)
									});
								}
							}
						}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						if($('#reportCode').val()){
							conditions.push({
								field: 'T.C_REPORT_CODE',
								fieldType:'STRING',
								matchType:'LIKE',
								name:"reportCode",
								value:$('#reportCode').val()
							});
						}
						if($('#reportName').val()){
							conditions.push({
								field: 'T.C_REPORT_NAME',
								fieldType:'STRING',
								matchType:'LIKE',
								name:"reportName",
								value:$('#reportName').val()
							});
						}
						if($('#submitDateBegin').val()){
							conditions.push({
								field: 'T.C_SUBMIT_DATE',
								fieldType:'DATE',
								matchType:'GE',
								name:"submitDateBegin",
								value:$('#submitDateBegin').val()
							});
						}
						if($('#submitDateEnd').val()){
							conditions.push({
								field: 'T.C_SUBMIT_DATE',
								fieldType:'DATE',
								matchType:'LE',
								name:"submitDateEnd",
								value:$('#submitDateEnd').val()
							});
						}
						if($('#submitPersionName').val()){
							conditions.push({
								field: 'T.C_SUBMIE_PERSION_NAME',
								fieldType:'STRING',
								matchType:'LIKE',
								name:"submitPersionName",
								value:$('#submitPersionName').val()
							});
						}
						equipAbnormalReportDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$('#reportCode').val("");
						$('#reportName').val("");
						$("#submitDateBegin").val("");
						$('#submitDateEnd').val("");
						$('#submitPersionName').val("");
					});
				});
			});
        </script>
    </body>
</html>