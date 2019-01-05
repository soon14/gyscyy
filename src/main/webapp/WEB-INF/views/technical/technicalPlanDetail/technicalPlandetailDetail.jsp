<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
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
					<li class="active">技术监督</li>
					<li class="active">计划</li>
					<li class="active">查看</li>
					<li class="active">详细计划</li>
				</ul>
		</div>
		<div class="col-md-12" >
		<div class="page-content">
		<div class="tabbable" style="margin-top: 20px;">
		 		<ul class="nav nav-tabs" id="myTab">
		 			<li class="active">
			 			<a   data-toggle="tab" href="#workitem" aria-expanded="true">
							<i class="green ace-icon fa fa-home bigger-120"></i>
							详细计划
						</a>
		 			</li>
		 		</ul>
		 		<div style="float:right; margin-top:-35px;margin-right:55px;">
					<button id="btnBackTicket" class="btn btn-xs btn-primary">
						<i class="fa fa-reply"></i>
						返回
					</button>
				</div>	
				<div class="tab-content">
				<div id="workitem" class="tab-pane fade active in">
				
    			<div class="widget-main no-padding">
				<h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">详细计划</h5>
	 			<table id="technicalPlandetail_table" class="table table-striped table-bordered table-hover" style="width:100%;">
	 			<input type="hidden" id="technicalWorkId" value="${technicalWorkId}"/>
	 			<input type="hidden" id="technicalId" value="${technicalId}"/>
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>项目名称</th>
	                                <th>定检周期</th>
	                                <th>本年计划时间</th>
	                                <th>实际完成时间</th>
	                                <th>完成状态</th>
	                                <th>完成情况</th>
	                                <th>风险和问题</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                </table>
		    	</div>
    		</div>
    	</div>
    </div>
</div>
<script type="text/javascript">
var listFormDialog;
var technicalPlandetailDatatables ="";
jQuery(function($) {
	seajs.use(['datatables', 'confirm', 'dialog'], function(A){
		var conditions=[];
			var technicalId=$("#technicalId").val();
	    	var technicalWorkId=$("#technicalWorkId").val();
		    technicalPlandetailDatatables = new A.datatables({
			render: '#technicalPlandetail_table',
			options: {
		        "ajax": {
		            "url": format_url("/technicalPlandetail/search"),
		            "contentType": "application/json",
		            "type": "POST",
		            "dataType": "JSON",
		            "data": function (d) {
		            	    conditions.push({
        					field: 'C_TECHNICAL_WORKID',
        					fieldType:'STRING',
        					matchType:'EQ',
        					value:'${technicalWorkId}'
        				});
		            	d.orderSeq='${orderSqe}';
		            	d.conditions = conditions;
		                return JSON.stringify(d);
		              }
		        },
		        multiple : true,
				ordering: true,
				checked: false,
				paging:false,
				bInfo:false,
				columns: [{data:"id", visible:false,orderable:false},
				          {data: "orderSeq",width: "5%",orderable: true},
				          {data: "planName",width: "10%",orderable: true}, 
				          {data: "djzqStr",name:"djzq",width: "8%",orderable: true}, 
				          {data: "nowTime",width: "10%",orderable: true}, 
				          {data: "wcTime",width: "10%",orderable: true},
				          {data: "wcStatusStr",name:"wcStatus",width: "8%",orderable: true}, 
				          {data: "wcqk",width: "20%",orderable: true}, 
				          {data: "danger",width: "20%",orderable: true}],
				
				btns: [ 
						{
						    id:"440",
							label:"查看",
							icon: "fa fa-binoculars bigger-110",
							className: "blue",
							events:{
							   click:function(event, nRow, nData){
									var id = nData.id;  
								    listFormDialog = new A.dialog({
                						width: 1400,
                						height: 600,
                						title: "查看",
                						url:format_url("/technicalPlandetail/getdetail/"+id),
                						closed: function(){
                							technicalPlandetailDatatables.draw(false);
                						}	
                					}).render();
    							}
							}
						}
				   ]
			}
		}).render();
		    
	    $('#btnBackTicket').on('click',function(){
	    		A.loadPage({
					render : '#page-container',
					url:format_url("/technical/detail/"+ technicalId),
				});
	    });
	});
});
        </script>
    </body>
</html>