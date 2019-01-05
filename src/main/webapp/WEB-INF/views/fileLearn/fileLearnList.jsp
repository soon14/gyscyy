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
				<li class="active">OA管理</li>
				<li class="active">文件学习</li>
				<li class="active">文件学习</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-lg-12 col-md-12 col-sm-12 search-content  padding-zero">
				<div class="form-inline text-left" role="form">
					<input class="col-md-12" id="loginUserId" value="${sysUserEntity.id}" type="hidden">
				   <div class="clearfix">
					<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
						<div  class="clearfix groupDiv">
							<label class="searchLabel" for="fileName">文件名称</label>：
			                <input id="fileName" class="inputWidth text-left" placeholder="请输入文件名称" type="text" >
		                </div>
<!-- 		                <div  class="clearfix groupDiv">
							<label class="searchLabel" for="sendUserDiv">发布人</label>：
			              <div  class='inputWidth'>
							<div id="sendUserDiv">
							</div>	
						</div>
		                </div> -->
		                <div  class="clearfix groupDiv">
							<label class="searchLabel" for="sendUnitName">发文单位</label>：
			              <div  class='inputWidth'>
								<div id="sendUnitNameDiv"  ></div>
							</div>	
						</div>
		            </div>
		            <div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
							<label class="searchLabel" for="sendStartTimeDiv">发文时间</label>：
								<div class="form-group dateInput padding-zero text-left">
	                    	  <div id="sendStartTimeDiv" style="border:none; padding:0px;"></div>
	                    	</div>
								<div class="toLabel" for="sendStartTimeRightDiv" >~</div>
								<div class="form-group dateInput padding-zero text-left">
	                          <div id="sendEndTimeDiv" style="border:none; padding:0px;"></div>
	                        </div>
                 	</div>
                   </div>
                   <div class="clearfix" >
						<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
						<div  class="clearfix  groupRightDiv">
		                 <div id="sendUnitNameDiv" class="inputWidth text-left padding-zero" style="width:63.5%"></div>
                    	</div>
		                </div>
		                 <div  class="form-group col-lg-6 col-md-6 col-sm-6 padding-zero text-right btnSearchBottom">
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
						<h5 class='table-title header smaller blue' >文件学习</h5>
						<table id="fileLearn_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>文件名称</th>
	                                <th>发文单位</th>
	                                <th>发文时间</th>
	                                <th>发布人</th>
	                                <th>内容</th>
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
			var listFormDialog;
			var submitUserDialog;
			
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','selectbox','combotree','my97datepicker'], function(A){
					var exportExcel="";
					var conditions=[];
					//发文时间
					var startDatePicker = new A.my97datepicker({
						id: 'sendStartTime',
						name: 'time',
						render:'#sendStartTimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "#F{$dp.$D(\\'sendEndTime\\')}",
								minDate: "",
								dateFmt: 'yyyy-MM-dd HH:mm'
						}
					}).render();
					var endDatePicker = new A.my97datepicker({
						id: 'sendEndTime',
						name: 'time',
						render:'#sendEndTimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'sendStartTime\\')}",
								dateFmt: 'yyyy-MM-dd HH:mm'
						}
					}).render();
					/* //发布人 
					var sendUserNamesDiv=new A.selectbox({
						id: 'sendUserId',
						name:'sendUserName',
						title:'人员',
						url:'/sysUser/userSelect?singleSelect=1',
						render:'#sendUserDiv',
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
					}).render(); */
					//单位下拉树
					var unitNameTreeData = ${unitNameIdTreeList};
					var sendUnitIdTree = new A.combotree({
						render: "#sendUnitNameDiv",
						name: 'sendUnitId',
						//返回数据待后台返回
						datasource: unitNameTreeData,
						width:"110px",
						options: {
							treeId: 'sendUnitId',
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
					//table
					var fileLearnDatatables = new A.datatables({
						render: '#fileLearn_table',
						options: {
					        "ajax": {
					            "url": format_url("/fileLearn/list"),
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
							          {data: "fileName",width: "auto",orderable: true,"render": function(data, type, row,other){
											var getRow = other.row;
											if(row.isImportant=="1"){
												return "<a href='#'  onclick='checkCodeA("+getRow+")'><span style='color:red'>"+data+"</span></a>";
											}else{
												return "<a href='#'  onclick='checkCodeA("+getRow+")'>"+data+"</a>";
											}
										}
							          }, 
							          {data: "sendUnitName",width: "auto",orderable: true}, 
							          {data: "sendTime",width: "auto",orderable: true}, 
							          {data: "sendUserName",name:"sendUserId",width: "auto",orderable: true}, 
							          {data: "remark",width: "auto",orderable: true}, 
							          {data: "approveStatusDic",name:"approveStatus",width: "auto",orderable: true}, 
							          ],
							fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
									if(exportExcel){
										exportExcels(format_url("/fileLearn/exportExcel"),JSON.stringify(conditions));
									}
									exportExcel="";
							},
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
                						/* listFormDialog = new A.dialog({
                    						width: '850',
                    						height: '471',
                    						title: "文件学习增加",
                    						url:format_url('/fileLearn/getAdd'),
                    						closed: function(){
                    							fileLearnDatatables.draw(false);
                    						}	
                    					}).render() */
                    					A.loadPage({
											render : '#page-container',
											url : format_url('/fileLearn/getAdd')
										});
            						}
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = fileLearnDatatables.getSelectRowDatas();
										var ids = [];
										var loginUser = '${sysUserEntity.id}';
										var loginName = '${sysUserEntity.loginName}'
										debugger;
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												if (data[i].sendUserId != loginUser && "super" != loginName) {
													alert('记录中包含不是当前登陆人的记录不能删除!');
													return;
												}
												ids.push(data[i].id);
											}
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										//判断该数据的审核状态，为0时可删除
										for(var i=0;i<data.length;i++){
											if(data[i].approveStatus!="0"){
												alert("有数据在流程中不可删除！");
												return;
											}
										}
										var url = format_url('/fileLearn/bulkDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													fileLearnDatatables.draw(false);
												},
												error:function(v,n){
													alert('操作失败');
												}
											});
										});
									}
								}
							}, {
								label:"导出",
								icon: "glyphicon glyphicon-download",
								className: "btn-primary",
								events:{
									click:function(event){
										$('#btnSearch').click();
										exportExcel="exportExcel";
									}
								}
							}],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								render: function(btnNode, data){
									var loginUser = '${sysUserEntity.id}';
									var loginName = '${sysUserEntity.loginName}'
									if (data.sendUserId != loginUser && "super" != loginName) {
										btnNode.hide();
									}
									var status = data.approveStatus;
									//审核状态为非未提交（0）、驳回（1）不能修改
									if(status!=0&&status!=1){
										btnNode.hide();
									}
			                      },
								events:{
									click: function(event, nRow, nData){
										 var id = nData.id;
										 /*listFormDialog = new A.dialog({
											width: '850',
											height: '471',
											title: "文件学习编辑",
											url:format_url('/fileLearn/getEdit/' + id),
											closed: function(){
												fileLearnDatatables.draw(false);
											}
										}).render(); */
										A.loadPage({
											render : '#page-container',
											url : format_url('/fileLearn/getEdit/' + id)
										});
									}
								}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								render: function(btnNode, data){
									var loginUser = '${sysUserEntity.id}';
									var loginName = '${sysUserEntity.loginName}'
									if (data.sendUserId != loginUser && "super" != loginName) {
										btnNode.hide();
									}
									var status = data.approveStatus;
									//审核状态为非未提交（0）不能删除
									if(status!=0&&status!=1){
										btnNode.hide();
									}
			                      },
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/fileLearn/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											fileLearnDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						},{
							id: "show",
							label:"查看",
							icon: "fa fa-binoculars bigger-130",
							className: "blue search",
							events:{
								click: function(event, nRow, nData){
									 var id = nData.id;
									A.loadPage({
										render : '#page-container',
										url : format_url('/fileLearn/getShow/' + id)
									});
								}
							}
						},{
							id:"submit",
							label:"提交审核",
							icon: "fa fa-check-square-o bigger-130",
							className: "edit",
							render: function(btnNode, data){
								var loginUser = '${sysUserEntity.id}';
								var loginName = '${sysUserEntity.loginName}'
								if (data.sendUserId != loginUser && "super" != loginName) {
									btnNode.hide();
								}
								if(data.approveStatus!="1"&&data.approveStatus!="0"){//data.approveStatus!="1"&&
									btnNode.hide();
								} 
							},
							events:{
								click: function(event, nRow, nData){
									if(!(nData.approveStatus==0||nData.approveStatus==1)){
										alert("该记录不是待提交或驳回状态，不能提交！");
										return;
									}
									approveStatus = nData.approveStatus;
									submitUserDialog = new A.dialog({
										width: 850,
										height: 531,
										title: "提交审核",
										url:format_url('/fileLearn/sureSubmitPerson/'+approveStatus+'/'+nData.id),
										closed: function(resule){
											var obj=new Object(); 
											var id = nData.id;
											var status = nData.status;
											var url =format_url('/fileLearn/submit/'+ id);
											if(submitUserDialog.resule){
												  obj.userList=submitUserDialog.resule.join(",");
														$.ajax({
															url : url,
				    										contentType : 'application/json',
				    										dataType : 'JSON',
				    										type : 'POST',
				    										data:JSON.stringify(obj),
				    										success: function(result){
				    											if(result.result=="success"){
				    												alert("提交成功");
				    												fileLearnDatatables.draw(false);
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
					}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						//文件名称
    					if($('#fileName').val()){
        					conditions.push({
        						field: 'F.C_FILE_NAME',
        						fieldType:'STRING',
        						matchType:'LIKE',
        						value:$('#fileName').val()
        					});
						}
    					//时间开始
						if($("#sendStartTime").val()){
        					conditions.push({
        						field: 'F.C_SEND_TIME',
        						fieldType:'DATE',
        						matchType:'GE',
        						value:$("#sendStartTime").val()
        					});
						}
						//时间结束
						if($("#sendEndTime").val()){
        					conditions.push({
        						field: 'F.C_SEND_TIME',
        						fieldType:'DATE',
        						matchType:'LE',
        						value:$("#sendEndTime").val()
        					});
						}
						if(sendUnitIdTree.getValue()!=null&&sendUnitIdTree.getValue()+""!=""){
        					conditions.push({
        						field: 'F.C_SEND_UNIT_ID',
        						fieldType:'LONG',
        						matchType:'EQ',
        						value:sendUnitIdTree.getValue()
        					});
						}
						fileLearnDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$('#fileName').val('');
						//发布人清空
						//sendUserNamesDiv.setValue(null);
						//单位清空
						sendUnitIdTree.setValue(null);
						$('#sendStartTime').val('');
						$('#sendEndTime').val('');
					});
				});
			});
        </script>
    </body>
</html>