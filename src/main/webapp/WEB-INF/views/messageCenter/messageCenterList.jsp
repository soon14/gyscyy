<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="page-content">
			<div class=" col-lg-12 col-md-12 col-sm-12  padding-zero search-content">
				<div class="form-inline text-left" role="form">
					<div class="clearfix">
					
                     <div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
                        <div  class="clearfix groupDiv">
							<label class="searchLabel" for="searchMessageType">消息类型</label>：
							<div class="inputWidth padding-zero  text-left">
								<select id="searchMessageType" name="searchMessageType" class="form-control chosen-select" searchtype style="width: 150px;"></select>
							</div>
						</div>
                   		<div  class="clearfix  groupRightDiv">
	                   		<label class="searchLabel" for="searchSenderDiv">消息发送人</label>：
							 <div  class='inputWidth'>
								<div id="searchSenderDiv">
								</div>	
							</div>
						</div>
					</div>
					<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
						<label class="searchLabel" for="searchStartInstockTimeDiv">发送时间</label>：
							<div class="form-group dateInput padding-zero text-left">
		                    <div id="searchStartSendTimeDiv" style="border:none; padding:0px;"></div>
		                </div>
							<div class="toLabel" for="searchStartTimeRightDiv" >~</div>
							<div class="form-group dateInput padding-zero text-left">
                            <div id="searchEndSendTimeDiv" style="border:none; padding:0px;"></div>
					    </div>
                    </div>
					
					</div>
					<div class="clearfix">
                     <div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
                    	 <div  class="clearfix groupDiv">
						<label class="searchLabel" for="searchStatus">消息状态</label>：
						<div class="inputWidth padding-zero  text-left">
			                <select id="searchStatus" name="searchStatus" class="form-control chosen-select" searchtype ></select>
						</div>
						</div>
                    </div>

	              <div  class="form-group col-lg-6 col-md-6 col-sm-6 padding-zero text-right btnSearchBottom">
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
						<h5 class='table-title header smaller blue' >消息内容</h5>
						<table id="messageCenter_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>主题</th>
	                                <th>发送时间</th>
	                                <th>发送人</th>
	                                <th>消息类型</th>
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
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker','selectbox'], function(A){
					var conditions=[];
					//消息类型下拉框
					var messageTypeCombo = ${messageTypeCombo};
					var messageTypeCombobox = new A.combobox({
						render: "#searchMessageType",
						//返回数据待后台返回
						datasource:messageTypeCombo,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					var flowManagerNamesDiv=new A.selectbox({
						id: 'searchSenderDivId',
						name:'searchSenderNames',
						title:'人员',
						url:'/sysUser/userSelect?singleSelect=1',
						render:'#searchSenderDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data,self){
							var names =[];
							var ids=[];
							for(var i =0; i<data.length; i++){
								names.push(data[i].name);
								ids.push(data[i].id);
							}
							self.setValue(names,ids);
						}
					}).render();
					//消息状态下拉框
					var messageStatusCombo = ${messageStatusCombo};
					var messageStatusCombobox = new A.combobox({
						render: "#searchStatus",
						//返回数据待后台返回
						datasource:messageStatusCombo,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					//消息发送开始日期
					var startDatePicker = new A.my97datepicker({
						id: 'searchStartTime',
						name: 'sendTime',
						render:'#searchStartSendTimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: 'yyyy-MM-dd HH:mm'
						}
					}).render();
					
					//消息发送截止日期
					var endDatePicker = new A.my97datepicker({
						id: 'searchEndTime',
						name: 'sendTime',
						render:'#searchEndSendTimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: 'yyyy-MM-dd HH:mm'
						}
					}).render();
					var messageCenterDatatables = new A.datatables({
						render: '#messageCenter_table',
						options: {
					        "ajax": {
					            "url": format_url("/messageCenter/recList"),
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
							columns: [
							          {data:"id", visible:false,orderable:false}, 
							          {data: "title",width: "20%",orderable: true}, 
							          {data: "sendTime",width: "20%",orderable: true}, 
							          {data: "sendPerson",width: "20%",orderable: true}, 
							          {data: "messageTypeName",width: "20%",orderable: true},
							          {data: "messageStatusName",width: "auto",orderable: true}
							         ],
							toolbars: [{
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = messageCenterDatatables.getSelectRowDatas();
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
										var url = format_url('/messageCenter/bulkDel/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													messageCenterDatatables.draw(false);
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
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/messageCenter/singleDel/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											messageCenterDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						}, {
							id:"show",
						 	label: "查看",
							icon: "fa fa-binoculars bigger-130",
							className: "blue search",
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									listFormDialog = new A.dialog({
                						width: 850,
                						height: 471,
                						title: "消息查看",
                						url:format_url('/messageCenter/showPage/' + id),
                						closed: function(){
                							//宋毅提供，和消息快捷方式同步
                							changeHeaderMessage();
                							messageCenterDatatables.draw(false);
                						}
                					}).render()
								}
							}
						}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						if($('#searchMessageType').val()){
							conditions.push({
	    						field: 'type',
	    						fieldType:'STRING',
	    						matchType:'EQ',
	    						value:$('#searchMessageType').val()
	    					});
						}
						if($("#searchSenderDivId").next('input').val()){
							conditions.push({
	    						field: 'sendPerson',
	    						fieldType:'STRING',
	    						matchType:'EQ',
	    						value:$("#searchSenderDivId").next('input').val()
	    					});
						}
						if($('#searchStatus').val()){
							conditions.push({
	    						field: 'status',
	    						fieldType:'STRING',
	    						matchType:'EQ',
	    						value:$('#searchStatus').val()
	    					});
						}
						//发送时间开始
						if($("#searchStartTime").val()){
        					conditions.push({
        						field: 'sendTime',
        						fieldType:'DATE',
        						matchType:'GE',
        						value:$("#searchStartTime").val()
        					});
						}
						//发送时间结束
						if($("#searchEndTime").val()){
        					conditions.push({
        						field: 'sendTime',
        						fieldType:'DATE',
        						matchType:'LE',
        						value:$("#searchEndTime").val()
        					});
						}
						messageCenterDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$('#searchSender').val('');
						$("#searchStartTime").val('');
						$("#searchEndTime").val('');
						messageTypeCombobox.setValue('99');
						messageStatusCombobox.setValue('99');
						flowManagerNamesDiv.setValue('');
					});
				});
			});
        </script>
    </body>
</html>