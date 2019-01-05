<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
		<style>
			.main-container:before{
				width: auto;
			}
			.banner{
				width:100% !important;
			}
			#page-container{
				min-height:0 !important;
				margin-right: 2000px;
				overflow-y: auto;
			}
			#todo-table-firstpage thead,#finishTask-table-firstPage thead{
				display:none;
			}
			#todo-table-firstpage thead,#fileLearn-table-firstPage thead{
				display:none;
			}
			#todo-table-firstpage thead,#companyTrends-table-firstPage thead{
				display:none;
			}
			#todo-table-firstpage_wrapper .table-striped>tbody>tr:nth-of-type(odd){
			  	background-color: #fff;
			}
			#todo-table-firstpage_wrapper .table-striped>tbody>tr:nth-of-type(even){
			  	background-color: rgb(248,248,248);
			}
			.table-bordered>tbody>tr>td{
				border:none;
			}
			.banner img{
				width:100%;
				height:100%;
				border-radius:5px;
			}
			div.banner,div.banner ul li,div.banner ul{
				height:100% !important;
			}
			table{
				color:#666;
				font-size:12px;
			}
			#finishTask-table-firstPage>tbody>tr:nth-of-type(odd),#finishTask-table-firstPage>tbody>tr:nth-of-type(even){
				background-color: #fff;
			}
			#fileLearn-table-firstPage>tbody>tr:nth-of-type(odd),#fileLearn-table-firstPage>tbody>tr:nth-of-type(even){
				background-color: #fff;
			}
			#companyTrends-table-firstPage>tbody>tr:nth-of-type(odd),#fileLearn-table-firstPage>tbody>tr:nth-of-type(even){
				background-color: #fff;
			}
			.paddingLeftTable>tbody>tr>td:nth-child(1){
				padding-left:20px;
				padding-top: 8px;
    			padding-bottom: 9px;
			}
			#finishTask-table-firstPage>tbody>tr{
				border-bottom:1px solid #ddd;
			}
			#fileLearn-table-firstPage>tbody>tr{
				border-bottom:1px solid #ddd;
			}
			#companyTrends-table-firstPage>tbody>tr{
				border-bottom:1px solid #ddd;
			}
			#finishTask-table-firstPage>tbody>tr>td:nth-child(2){
				font-size:12px;
				color:#999;
			}
			#fileLearn-table-firstPage>tbody>tr>td:nth-child(2){
				font-size:12px;
				color:#999;
			}
			#companyTrends-table-firstPage>tbody>tr>td:nth-child(2){
				white-space:nowrap;
				font-size:12px;
				color:#999;
			}
			.table-striped>tbody>tr>td:nth-child(3){
				padding-right:20px;
			}
			.widget-box{
				border-radius:5px;
			}
			.widget-header{
				background:#fff;
				border-radius:5px;
			}
			.main-content .row{
				margin-bottom:10px;
			}
			.main-content{
				padding-left:1.5%;
				padding-right:1.5%;
			}
			.index .paddingTable{
				padding-left:20px;
				padding-right:20px;
			}
			.index .forHeightTable{
				
			}
			.forHeightTable>tbody>tr>td{
				padding-top:8px;
				padding-bottom:8px;
			}
			.testUl{
				padding:0 20px 0 20px;
				color:#666;
				font-size:12px;
			}
			.testUl li{
				border-bottom:1px solid #ddd;
				padding-top:7px;
				padding-bottom:7px;
			}
			.testUl li span{
				float:right;
				color:#999;
			}
			td a{
				color:#666;
			}
			.main-content,.widget-header{
				padding:0 !important;
			}
			.sidebar+.main-content,body{
				background:#fff;
			}

			#dispatch-data-table-firstPage>tbody>tr>td:nth-child(2){
				font-size:12px;
				color:#999;
			}
			#dispatch-data-table-firstPage>tbody>tr{
				border-bottom:1px solid #ddd;
			}
			#dispatch-data-table-firstPage>tbody>tr:nth-of-type(odd),#dispatch-data-table-firstPage>tbody>tr:nth-of-type(even){
				background-color: #fff;
			}
			#todo-table-firstpage thead,#dispatch-data-table-firstPage thead{
				display:none;
			}
			
			#receipt-data-table-firstPage>tbody>tr>td:nth-child(2){
				font-size:12px;
				color:#999;
			}
			#receipt-data-table-firstPage>tbody>tr{
				border-bottom:1px solid #ddd;
			}
			#receipt-data-table-firstPage>tbody>tr:nth-of-type(odd),#receipt-data-table-firstPage>tbody>tr:nth-of-type(even){
				background-color: #fff;
			}
			#todo-table-firstpage thead,#receipt-data-table-firstPage thead{
				display:none;
			}

		</style>
	</head>
	<body>
<div class="main-container ace-save-state" id="main-container">
		<script type="text/javascript">
	        try{ace.settings.loadState('main-container')}catch(e){}
		</script>
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content index">
					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<div class="invisible" id="main-widget-container">
	
								<div class="row col-xs-4">
									<div class=" widget-container-col">
										<div class="widget-box">
											<div class="widget-header  padding-zero">
												<div class="rowHeigth headImgRow">
													<div class="banner" id="banner">
													    <ul style="margin:0;padding:0" id = "groomUl">
													     	<li style="display:inline-block;float:left"><img src='../static/images/headImg/1.jpg' height='100%'></li>
													        <li style="display:inline-block;float:left"><img src='../static/images/headImg/2.jpg' height='100%'></li>
													        <li style="display:inline-block;float:left"><img src='../static/images/headImg/3.jpg' height='100%'></li>
													        <li style="display:inline-block;float:left"><img src='../static/images/headImg/4.jpg' height='100%'></li>
													        <li style="display:inline-block;float:left"><img src='../static/images/headImg/5.jpg' height='100%'></li>
													        <li style="display:inline-block;float:left"><img src='../static/images/headImg/6.jpg' height='100%'></li>
													        <c:forEach var="commendList" items="${companyTrendsEntity }">
													        	<li style="display:inline-block;float:left;cursor:pointer;"><img src="${commendList.commendPictureUrl}" height='100%' onclick='inCompanyTrendsgGroom("${commendList.id}")'></li>
													        </c:forEach>
													    </ul>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
	
								<div class="row col-xs-4">
									<div class=" widget-container-col">
										<div class="widget-box">
											<div class="widget-header  padding-zero">
												<div class="headDiv"><label  class="glyphicon glyphicon-volume-up" aria-hidden="true"></label><span class="leftHeadSpan">公司动态</span><a id="moreCompanyTrendsMessage" class="rightHeadSpan" onclick='moreCompanyTrendsMessage()'>更多</a></div>
												<div class="rowHeigth paddingTable">
													<table id="companyTrends-table-firstPage" class="table table-striped table-bordered table-hover forHeightTable" style="width:100%;border: none;">
														<thead>
															<tr>
																<th style="display:none;">主键</th>
																<th>标题</th>
																<th>报送时间</th>
																<th>操作</th>
															</tr>
														</thead>
													</table>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="row col-xs-4">
									<div class=" widget-container-col">
										<div class="widget-box">
											<div class="widget-header  padding-zero">
												<div class="headDiv"><label  class="glyphicon glyphicon-bell" aria-hidden="true"></label><span class="leftHeadSpan">消息</span><a id="moreMessage" class="rightHeadSpan" onclick='moreMessage()'>更多</a></div>
												<div class="rowHeigth paddingTable">
													<table id="finishTask-table-firstPage" class="table table-striped table-bordered table-hover forHeightTable" style="width:100%;border: none;">
														<thead>
															<tr>
																<th style="display:none;">主键</th>
																<th>标题</th>
																<th>报送时间</th>
																<th>操作</th>
															</tr>
														</thead>
													</table>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="row col-xs-8">
									<div class=" widget-container-col">
										<div class="widget-box">
											<div class="widget-header  padding-zero">
												<div class="headDiv"><label  class="glyphicon glyphicon-check" aria-hidden="true"></label><span class="leftHeadSpan">待办任务</span><a id="moreTodoTask" class="rightHeadSpan" onclick='moreTodoTask()'>更多</a></div>
												<div class="rowHeigth">
													<table id="todo-table-firstpage" class="table table-striped table-bordered table-hover paddingLeftTable" style="width:100%;border: none;">
														<thead>
															<tr>
																<th style="display:none;">主键</th>
																<th>标题</th>
																<th>报送单位</th>
																<th>报送时间</th>
																<th>办理</th>
															</tr>
														</thead>
													</table>
												</div>
											</div>
										</div>
									</div>
								</div>
	
								
	
								<div class="row col-xs-4">
									<div class=" widget-container-col">
										<div class="widget-box">
											<div class="widget-header  padding-zero">
												<div class="headDiv"><label  class="glyphicon glyphicon-bell" aria-hidden="true"></label><span class="leftHeadSpan">发文管理</span><a id="moreDispatchMessage" class="rightHeadSpan" onclick='moreDispatchManagementMessage()'>更多</a></div>
												<div class="rowHeigth paddingTable">
													<table id="dispatch-data-table-firstPage" class="table table-striped table-bordered table-hover forHeightTable" style="width:100%;border: none;">
														<thead>
															<tr>
																<th style="display:none;">主键</th>
																<th>标题</th>
																<th>时间</th>
															</tr>
														</thead>
													</table>
												</div>
											</div>
										</div>
									</div>
								</div>
	
								<div class="row col-xs-6">
									<div class=" widget-container-col">
										<div class="widget-box">
											<div class="widget-header  padding-zero">
											<div class="headDiv"><label  class="glyphicon glyphicon-bell" aria-hidden="true"></label><span class="leftHeadSpan">签报管理</span><a id="moreReceiptMessage" class="rightHeadSpan" onclick='moreReceiptManagementMessage()'>更多</a></div>
												<div class="rowHeigth  paddingTable">
													<table id="receipt-data-table-firstPage" class="table table-striped table-bordered table-hover forHeightTable" style="width:100%;border: none;">
														<thead>
															<tr>
																<th style="display:none;">主键</th>
																<th>标题</th>
																<th>时间</th>
															</tr>
														</thead>
													</table>
												</div>
											</div>
										</div>
									</div>
								</div>
	
								<!-- <div class="row col-xs-4">
									<div class=" widget-container-col">
										<div class="widget-box">
											<div class="widget-header  padding-zero">
												<div class="headDiv"><label  class="glyphicon glyphicon-signal" aria-hidden="true"></label><span class="leftHeadSpan">缺陷类型占比</span></div>
												<div class="rowHeigth" id='compareId'>
												</div>
											</div>
										</div>
									</div>
								</div> -->
	
								<div class="row col-xs-6">
									<div class=" widget-container-col">
										<div class="widget-box">
											<div class="widget-header  padding-zero">
												<div class="headDiv"><label  class="glyphicon glyphicon-bell" aria-hidden="true"></label><span class="leftHeadSpan">文件学习</span><a id="moreMessage" class="rightHeadSpan" onclick='moreFileLearnMessage()'>更多</a></div>
												<div class="rowHeigth paddingTable">
													<table id="fileLearn-table-firstPage" class="table table-striped table-bordered table-hover forHeightTable" style="width:100%;border: none;">
														<thead>
															<tr>
																<th style="display:none;">主键</th>
																<th>标题</th>
																<th>报送时间</th>
																<th>操作</th>
															</tr>
														</thead>
													</table>
												</div>
											</div>
										</div>
									</div>
								</div>
						</div><!-- /.col -->
					</div><!-- /.row -->
				</div><!-- /.page-content -->
			</div>
		</div><!-- /.main-content -->
	</div><!-- /.main-container -->
</div>
	<!-- inline scripts related to this page -->
	<script type="text/javascript">
	var listFormDialog ;
	    jQuery(function($){
	    	
	    	seajs.use(['datatables', 'confirm', 'dialog','echarts'], function(A){
	    		
				var conditions=[];
				var conditionsFish=[];
				  var container_list = ace.data.get('demo', 'widget-order', true);
		            if(container_list) {
		                for(var container_id in container_list) if(container_list.hasOwnProperty(container_id)) {
		                    var widgets_inside_container = container_list[container_id];
		                    if(widgets_inside_container.length == 0) continue;
		                    for(var i = 0; i < widgets_inside_container.length; i++) {
		                        var widget = widgets_inside_container[i];
		                        $('#'+widget).appendTo('#'+container_id);
		                    }
		                }
		            }
		            //restore widget state
		            var widgets = ace.data.get('demo', 'widget-state', true);
		            if(widgets != null) {
		                for(var id in widgets) if(widgets.hasOwnProperty(id)) {
		                    var state = widgets[id];
		                    var widget = $('#'+id);
		                    if
		                    (
		                        (state == 'shown' && widget.hasClass('collapsed'))
		                        ||
		                        (state == 'hidden' && !widget.hasClass('collapsed'))
		                    )
		                    {
		                        widget.widget_box('toggleFast');
		                    }
		                    else if(state == 'closed') {
		                        widget.widget_box('closeFast');
		                    }
		                }
		            }
		            $('#main-widget-container').removeClass('invisible');
		            
		            //请求消息列表的数据
			        finishTaskDatatables = new A.datatables({
						render: '#finishTask-table-firstPage',
						options: {
							"ajax": {
					            "url": format_url("/messageCenter/recList"),
					           // "url": format_url("/messageCenter/recList"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	/* d.conditions = conditionsFish;
					            	d.assign = $("#assign").val();
					            	d.currentPage = ${currentPage};
					            	d.pageSize = ${pageSize}; */
					                return JSON.stringify(d);
					              }
					        },
					        checked:false,
							bInfo:false,
							paging:false,
							ordering:true,
							columns: [
								{data:"id", "visible": false, orderable: false},      
								{ orderable: true,"width":"60%", "render": function(data, type, row,other){
									var getRow = other.row;
									if(row.isnotImportant=="1"){
										return "<a href='#'  onclick='checkCodeA("+getRow+")'><span style='color:red'>"+row.title+"</span></a>";
									}else{
										return "<a href='#'  onclick='checkCodeA("+getRow+")'>"+row.title+"</a>";
									}
									
									}
								},
								{data:"sendTime", "width":"30%",sClass:'right'}
							]
						}
					}).render();
		            
			      //请求文件学习列表的数据
					 fileLearnDatatables = new A.datatables({
						render: '#fileLearn-table-firstPage',
						options: {
							"ajax": {
					            "url": format_url("/fileLearn/noLearnMessList"),
					           // "url": format_url("/messageCenter/recList"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	/* d.conditions = conditionsFish;
					            	d.assign = $("#assign").val();
					            	d.currentPage = ${currentPage};
					            	d.pageSize = ${pageSize}; */
					                return JSON.stringify(d);
					              }
					        },
					        checked:false,
							bInfo:false,
							paging:false,
							ordering:true,
							columns: [
								{data:"id", "visible": false, orderable: false},      
								{ orderable: true,"width":"60%", "render": function(data, type, row,other){
									//var getRow = other.row;
									return "<a href='#'  onclick='checkFileLearnMess("+row.id+")'>"+row.fileName+"</a>";
									}
								},
								{data:"sendTime", "width":"30%",sClass:'right'}
							]
						}
					}).render();
		            
					//请求公司动态列表的数据
				companyTrendsDatatables = new A.datatables({
					render: '#companyTrends-table-firstPage',
					options: {
						"ajax": {
							"url": format_url("/companyTrends/companyTrendsMessList"),
							"contentType": "application/json",
							"type": "POST",
							"dataType": "JSON",
							"data": function (d) {
								 return JSON.stringify(d);
								 }
							},
							checked:false,
							bInfo:false,
							paging:false,
							ordering:true,
							columns: [
								{data:"id", "visible": false, orderable: false},      
								{ orderable: true,"width":"60%", "render": function(data, type, row,other){
										var html ="";
										if (row.readFlag == "1") {
											html = "<font color='gray'>【已读】</font>";
										} else {
											html = "<font color='red'>【未读】</font>";
										}
										var content;
										if (row.title.length > 20) {
											content = row.title.substring(0,20);
										} else {
											content = row.title;
										}
										var returnHtml = "";
										if (row.setTop == '1') {
											returnHtml ="<a  title='" + row.title + "' href='#'  onclick='checkCompanyTrendsMess("+row.id+")'>"+  html + "<font color='red'>" + content +"</font></a>";
										} else {
											returnHtml ="<a  title='" + row.title + "' href='#'  onclick='checkCompanyTrendsMess("+row.id+")'>"+  html + content +"</a>";
										}
										
										//var getRow = other.row;
										return returnHtml;
										}
									},
								{data:"publishTimeShow", "width":"40%" ,sClass:'right companyDateTd'}
							]
						}
						}).render();
						
				//代办任务加载信息
				todoTaskDatatables = new A.datatables({
					render: '#todo-table-firstpage',
					options: {
				        "ajax": {
				            "url": format_url("/todoTask/search"),
				            "contentType": "application/json",
				            "type": "POST",
				            "dataType": "JSON"  ,
				            "data": function (d) {
				            	conditions.push({
									'field':'task.end_time_',
									'fieldType':'STRING',
									'matchType':'NULL',
									'value':""
								});
				            	d.conditions = conditions;
				                return JSON.stringify(d);
				              }  
				        },
						ordering:true,
						checked:false,
						bInfo:false,
						paging:false,
						columns: [
							{data:"id_", "visible": false, orderable: false},  
							{ orderable: false, "width":"auto", "render": function(data, type, row,other){
								var getRow = other.row;
								var setData = JSON.stringify(row)
								return "<a href='#'  onclick='checkCodeWork("+getRow+","+setData+")'>"+row.startUserName+" 发起的 "+row.processName+" 等待您的办理";"</a>";
								}
							},
							{ data:"unitName", "width":"auto"},
							{ data:"startTime", "width":"auto",sClass:'right'},
							/* { data:"name", "width":"auto"}, */
							/* { data:"cname", "width":"auto"}, */
						]
					}
				}).render();
					
				//发文管理
		        var dispatchManageDatatables = new A.datatables({
					render: '#dispatch-data-table-firstPage',
					options: {
						"ajax": {
				            "url": format_url("/dispatchManagement/searchDispatchInfoForFirstPage"),
				            "contentType": "application/json",
				            "type": "POST",
				            "dataType": "JSON",
				            "data": function (d) {
				            	conditions = [];
								d.conditions = conditions;
				                return JSON.stringify(d);
				              }
				        },
				        checked:false,
						bInfo:false,
						paging:false,
						ordering:true,
						columns: [
							{data:"id", "visible": false, orderable: false},      
							{ orderable: true,"width":"60%", "render": function(data, type, row,other){
								//var getRow = other.row;
								return "<a href='#'  onclick='goFeedbackPage("+row.id+")'>"+row.title+"</a>";
								}
							},
							{data:"draftTime", "width":"30%",sClass:'right'}
						]
					}
				}).render();
				
		      //收文管理
				var receiptManageDatatables = new A.datatables({
					render: '#receipt-data-table-firstPage',
					options: {
						"ajax": {
				            "url": format_url("/receiptManagement/searchReceiptDataForFirstPage"),
				            "contentType": "application/json",
				            "type": "POST",
				            "dataType": "JSON",
				            "data": function (d) {
				            	conditions = [];
								d.conditions = conditions;
				                return JSON.stringify(d);
				              }
				        },
				        checked:false,
						bInfo:false,
						paging:false,
						ordering:true,
						columns: [
							{data:"id", "visible": false, orderable: false},      
							{ orderable: true,"width":"60%", "render": function(data, type, row,other){
								//var getRow = other.row;
								return "<a href='#'  onclick='goReceiptViewPage("+row.id+")'>"+row.title+"</a>";
								}
							},
							{data:"draftTime", "width":"30%",sClass:'right'}
						]
					}
				}).render();

				var unslider = $('#banner').unslider({
					dots: true
				});
				var banner = unslider.data('unslider');
//				var defData =  eval('(' + ${defectData} + ')');
//			 		    	var getPowerEcharts = new A.echarts({
//		 		    		render: '#LosePointId',
//		 		    		options:{
//		 		    		    color: ['#3398DB'],
//		 		    		    tooltip : {
//		 		    		        trigger: 'axis',
//		 		    		        show:false,
//		 		    		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
//		 		    		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
//		 		    		        }
//		 		    		    },
//		 		    		    grid: {
//		 		    		        left: '3%',
//		 		    		        right: '4%',
//		 		    		        bottom: '3%',
//		 		    		        containLabel: true
//		 		    		    },
//		 		    		    xAxis : [
//		 		    		        {
//		 		    		            type : 'category',
//		 		    		            data : ['单位1', '单位2', '单位3', '单位4', '单位5', '单位6', '单位7'],
//		 		    		            axisTick: {
//		 		    		                alignWithLabel: true
//		 		    		            }
//		 		    		        }
//		 		    		    ],
//		 		    		    yAxis : [
//		 		    		        {
//		 		    		            type : 'value'
//		 		    		        }
//		 		    		    ],
//		 		    		    series : [
//		 		    		        {
//		 		    		            name:'直接访问',
//		 		    		            type:'bar',
//		 		    		            barWidth: '45%',
//		 		    		            itemStyle: {
//		 		    		                normal: {
//		 		    		                	barBorderRadius:[6, 6, 0, 0],
//		 		    		                	borderWidth:1,
//		 		    		                	cursor:'point',
//		 		    		                	borderColor: '#188df0',
//		 		    		                    color: new echarts.graphic.LinearGradient(
//		 		    		                        0, 0, 0, 1,
//		 		    		                        [
//		 		    		                            {offset: 0, color: '#fff'},
//		 		    		                            {offset: 0.7, color: '#188df0'},
//		 		    		                            {offset: 0.8, color: '#188df0'}
//		 		    		                        ]
//		 		    		                    )
//		 		    		                }
//		 		    		            },
//		 		    		            data:[50, 52, 60,48, 70, 56, 70]
//		 		    		        }
//		 		    		    ]
//		 		    		}
//		 		    	}).render();
//		 		    	var getCompare = new A.echarts({
//		 		    		render: '#compareId',
//		 		    		options:{
//		 		    			tooltip: {
//		 		    	            trigger: 'item',
//		 		    	            formatter: "{a} <br/>{b}"
//		 		    	        },
//		 		    	        series: [
//		 		    	            {
//		 		    	                name:'访问来源',
//		 		    	                type: 'pie',
//		 		    	                radius: ['35%', '56%'],
//		 		    	                itemStyle: {
//		 		    	                    normal:{
//		 		    	                        label:{
//		 		    	                            show:true,
//		 		    	                            textStyle:{color:'#3c4858',fontSize:"13",textAlign:'center'},
//		 		    	                            formatter:function(val){   //让series 中的文字进行换行
//		 		    	                                return val.name.split("-").join("\n");}
//		 		    	                        },
//		 		    	                        title:{
//		 		    	                          text:'aaaa'
//		 		    	                        },
//		 		    	                        labelLine:{
//		 		    	                            show:true,
//		 		    	                            lineStyle:{color:'#3c4858'}
//		 		    	                        }
//		 		    	                    },
//		 		    	                    emphasis: {
//		 		    	                        shadowBlur: 10,
//		 		    	                        shadowOffsetX: 0,
//		 		    	                        shadowColor: 'rgba(0, 0, 0, 0.5)',
//		 		    	                        textColor:'#000'
//		 		    	                    }
//		 		    	                },
//		 		    	             /*    data: [
//		 		    	                    {value: 30, name: '30%-缺陷1'},
//		 		    	                    {value: 20, name: '20%-缺陷2'},
//		 		    	                    {value: 25, name: '25%-缺陷3'},
//		 		    	                    {value: 25, name: '25%-缺陷4'},
//		 		    	                ] */
//		 		    	                data : defData
//		 		    	            }
//		 		    	        ],
//		 		    	        color: ['rgb(61,171,232)','rgb(95,193,215)','rgb(146,198,96)','rgb(59,175,134)']
//		 		    		}
//		 		    	}).render();
//		 		    	var getLosePoint =  new A.echarts({
//		 		    		render: '#powerId',
//		 		    		options:{
//		 		    		    tooltip : {
//		 		    		        trigger: 'axis',
//		 		    		        show:false,
//		 		    		        axisPointer: {
//		 		    		            type: 'cross',
//		 		    		            label: {
//		 		    		                backgroundColor: '#6a7985'
//		 		    		            }
//		 		    		        }
//		 		    		    },
//		 		    		    grid: {
//		 		    		        left: '3%',
//		 		    		        right: '4%',
//		 		    		        bottom: '3%',
//		 		    		        containLabel: true
//		 		    		    },
//		 		    		    itemStyle: {
//		 		                    normal: {
//		 		                        color: '#54a2d5'
//		 		                    }
//		 		                },
//		 		    		    xAxis : [
//		 		    		        {
//		 		    		            type : 'category',
//		 		    		            boundaryGap : false,
//		 		    		            data : ['8-24','8-25','8-26','8-27','8-28','8-29','8-30']
//		 		    		        }
//		 		    		    ],
//		 		    		    yAxis : [
//		 		    		        {
//		 		    		            type : 'value'
//		 		    		        }
//		 		    		    ],
//		 		    		    series : [
//		 		    		        {
//		 		    		            name:'',
//		 		    		            type:'line',
//		 		    		            stack: '总量',
//		 		    		            symbolSize:'9',
//		 		    		            label: {
//		 		    		                normal: {
//		 		    		                    show: false,
//		 		    		                    position: 'top'
//		 		    		                }
//		 		    		            },
//		 		    		            areaStyle: {normal: {
//		 		    		            	color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
//		 		    	                        offset: 0,
//		 		    	                        color: '#54a2d5'
//		 		    	                    }, {
//		 		    	                        offset: 1,
//		 		    	                        color: '#fff'
//		 		    	                    }])
//		 		    		            }},
//		 		    		            data:[189, 220, 200, 250, 280, 320, 300]
//		 		    		        }
//		 		    		    ]
//		 		    		}
//		 		    	}).render();
			});
	    });
	    function checkCodeA(index){
 			var getId = finishTaskDatatables.getDatas()[index].id;
 			 listFormDialog = new A.dialog({
				width: 850,
				height: 471,
				title: "消息查看",
				url:format_url('/messageCenter/showPage/' + getId),
				closed: function(){
					//宋毅提供，和消息快捷方式同步
					changeHeaderMessage();
				}
			}).render()
 		}

 	    function checkCodeWork(index,nData){
 			var getId = nData.id_;
             var currentPage = todoTaskDatatables.getCurrentPage();
             var pageSize = todoTaskDatatables.getPageSize();
             var procInstId = nData.procInstId;//流程实例ID
             var procDefId = nData.procDefId;////流程定义ID
             var formURL=nData.startJspFile+"/"+nData.businessKey_;
             A.loadPage({
               render : '#page-container',
               url : format_url("/todoTask/detail?id=" + getId + "&currentPage="+ currentPage 
                   + "&pageSize=" + pageSize+"&procInstId="+procInstId+
                   "&procDefId="+procDefId+"&formURL="+formURL)
             });
 		}
 	    //更多消息
 	    function moreMessage(){
 	    	A.loadPage({
 				render : '#page-container',
 				url : format_url('/messageCenter/index')
 			});
 	    }
 	    //文件学习查看
 	   function checkFileLearnMess(index){
			var getId = index;
			A.loadPage({
 				render : '#page-container',
 				url:format_url('/fileLearn/goFileLeanPage/' + getId)
 			});
		}
 		 //更多文件学习消息
 	    function moreFileLearnMessage(){
 	    	A.loadPage({
 				render : '#page-container',
 				url : format_url('/fileLearn/releaseIndex')
 			});
 	    }
 	    //公司动态查看
 	   function checkCompanyTrendsMess(index){
			var getId = index;
			A.loadPage({
 				render : '#page-container',
 				url:format_url('/companyTrends/getPreviewPage/' + getId)
 			});
		}
 		 //更多公司动态消息
 	    function moreCompanyTrendsMessage(){
 	    	A.loadPage({
 				render : '#page-container',
 				url : format_url('/companyTrends/index')
 			});
 	    }
 	    function moreTodoTask(){
 	    	A.loadPage({
 				render : '#page-container',
 				url : format_url('/todoTask/list/1/10')
 			});	
 	    }
 	    

 	    
		 //图片点击进入相应预览页面
 	    function inCompanyTrendsgGroom(id){
 	    	A.loadPage({
 				render : '#page-container',
 				url : format_url('/companyTrends/getPreviewPage/'+id)
 			});
 	    }
 	 //发文详细信息查看
		function goFeedbackPage(index){
			A.loadPage({
				render : '#page-container',
					url:format_url('/dispatchManagement/goFeedbackPage/' + index)
			});
		}
		//更多发文消息
		function moreDispatchManagementMessage(){
			A.loadPage({
				render : '#page-container',
				url : format_url('/dispatchManagement/releaseIndex')
			});
		}
		//更多收文消息
		function moreReceiptManagementMessage(){
			A.loadPage({
				render : '#page-container',
				url : format_url('/receiptManagement/index')
			});
		}
	 	 //收文详细信息查看
		function goReceiptViewPage (index){
			A.loadPage({
				render : '#page-container',
					url:format_url('/receiptManagement/goReceiptViewPage/' + index)
			});
		}
		
		//跳转抄送列表
		function goCopyTaskList() {
			listFormDialog.close();
			A.loadPage({
				render : '#page-container',
				url : format_url('/wf/copyTask/list/1/10')
			});
		}
		
		//跳转流程结束抄送列表
		function goOverCopyTaskList() {
			listFormDialog.close();
			A.loadPage({
				render : '#page-container',
				url : format_url('/wf/copyFinish/list/1/10')
			});
		}

	</script>
	</body>
</html>