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
					检修管理
				</li>
				<li class="active">检修文件包</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class=" col-lg-12 col-md-12 col-sm-12  padding-zero search-content">
				<div class="form-inline text-left" role="form">
					<div class="clearfix">
			        	<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
			        	 <div  class="clearfix groupDiv">
						<label class="searchLabel"   for="form-field-1">文件名称</label>：
		                <input id="searchfileName"  class="inputWidth text-left"   placeholder="请输入文件名称" type="text"></input>
		                </div>  
		                  
		                   <div  class="clearfix groupRightDiv">
							<label class="searchLabel" for="form-field-1">上传人</label>：
							 <div class="padding-zero input-width  text-left">
				                <select id="searchUploadUsers" class="" >
				                </select>
			                </div>
			                </div>
                   		</div>
                 		<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
						<label class="searchLabel" >上传时间</label>：
							<div class="form-group dateInput padding-zero text-left">
		                		<div id="searchUploadDateStartDiv" style="border:none; padding:0px;"></div>
                   			</div>
							<div class="toLabel" for="searchStartTimeRightDiv" >~</div>
                         
							<div class="form-group dateInput padding-zero text-left">
		               		 	<div id="searchUploadDateEndDiv"  style="border:none; padding:0px;"></div>
                   			</div>
		           		</div>
                   </div>
 	              <div  class="form-group col-lg-12 col-md-12 col-sm-12 padding-zero text-right btnSearchBottom">
	              		<div class="form-group">
						<button id="btnSearch"  class="btn btn-xs btn-primary" >
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
						<h5 class='table-title header smaller blue' >检修文件</h5>
						<table id="overhaulFile_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">序号</th>
									<th class="center sorting_disabled" >
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>序号</th>
	                                <th>文件名称</th>
	                                <th>上传人</th>
	                                <th>上传时间</th>
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
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker','duallistbox'], function(A){
					var conditions=[];
					//申请人下拉框
					var UploadUsersCombobox = new A.combobox({
						render: '#searchUploadUsers',
						datasource:${uploadUsers},
						width:"150px",
						multiple:false,
						allowBlank:true,
						options:{
							"allow_single_deselect": true,
							"disable_search_threshold":10
						}
					}).render();
					
					
					var searchUploadDateStartDiv = new A.my97datepicker({
						id: 'searchUploadDateStartDivId',
						name:'uploadDate',
						render:'#searchUploadDateStartDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "#F{$dp.$D(\\'searchUploadDateEndDivId\\')}",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					
					
					var searchUploadDateEndDiv = new A.my97datepicker({
						id: 'searchUploadDateEndDivId',
						name:'uploadDate',
						render:'#searchUploadDateEndDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'searchUploadDateStartDivId\\')}",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					
					
					
					var overhaulFileDatatables = new A.datatables({
						render: '#overhaulFile_table',
						options: {
					        "ajax": {
					            "url": format_url("/overhaulFile/search"),
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
							optWidth: 100,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						              } },
							          {data: "fileName",width: "60%",orderable: true}, 
							          {data: "uploadUserName",width: "15%",orderable: true},
							          {data: "uploadDate",width: "15%",orderable: true}],
							toolbars: [
							{
								label:"上传",
								icon: "glyphicon glyphicon-upload",
								className: "btn-primary",
								id:"uploadBtn",
								events:{
									click: function(event){
										$.ajax({
											url : format_url("/overhaulFile/checkUpload/"),
											contentType : 'application/json',
											dataType : 'JSON',
											type : 'POST',
											data : "",
											success: function(result){
												if(result.result=="true"){
													listFormDialog = new A.dialog({
														width: 850,
														height: 350,
														title: "上传",
														url:format_url('/overhaulFile/uploadFile'),
														closed: function(resule){
														}
													}).render();
												}else{
													alert("只有检修专工和系统管理员可以上传文件包！");
												}
												
											},
											error:function(v,n){
												alert('操作失败');
											}
										});
										
									}
								}
							},
							{
								label:"下载",
								icon: "glyphicon glyphicon-download",
								className: "btn-primary",
								events:{
									click: function(event){
										var data = overhaulFileDatatables.getSelectRowDatas();
										var ids = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
											}
										}
										if(ids.length < 1){
											alert('请选择要下载的文件');
											return;
										}
										A.confirm('您确认要下载么？',function(){
		            						window.location.href=format_url("/overhaulFile/bulkDowload/"+JSON.stringify(ids)); 
										});
									}
								}
							}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = overhaulFileDatatables.getSelectRowDatas();
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
										var url = format_url('/overhaulFile/bulkDelete');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													overhaulFileDatatables.draw(false);
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
										var url =format_url('/overhaulFile/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											overhaulFileDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						},{
							id:"download",
							label:"下载",
							icon: "fa fa-download bigger-130",
							className: "bule ",
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									var url =format_url('/overhaulFile/checkFile/'+ id);
									$.ajax({
										url : url,
										contentType : 'application/json',
										dataType : 'JSON',
										type : 'GET',
										success: function(result){
											if(result.result =="success"){
			            						window.location.href=format_url("/overhaulFile/downloadFile/"+id); 
											}else{
												alert("文件不存在")
											}
										},
										error:function(v,n){
											alert('文件不存在');
										}
									});
								}
							}
					}]
						}
					}).render();
					
					
					$('#btnSearch').on('click',function(){
						conditions=[];
						if($("#searchUploadUsers").val()!=""){
							conditions.push({
		    					field: 'uploadUserId',
		    					fieldType:'INT',
		    					matchType:'EQ',
		    					value:$('#searchUploadUsers').val()
		    				});
						}
						
						
						if($("#searchUploadDateStartDivId").val()!=""){
							conditions.push({
								field: 'uploadDate',
								fieldType:'DATE',
								matchType:'GE',
								value:$("#searchUploadDateStartDivId").val()
							});
						}
						if($("#searchUploadDateEndDivId").val()!=""){
							conditions.push({
								field: 'uploadDate',
								fieldType:'DATE',
								matchType:'LE',
								value:$("#searchUploadDateEndDivId").val()
							});
						}
						if($("#searchfileName").val()!=""){
							conditions.push({
								field: 'fileName',
								fieldType:'STRING',
								matchType:'LIKE',
								value:$('#searchfileName').val().trim()
							});
						}
						overhaulFileDatatables.draw();
					});
					
					
					
					$('#btnReset').on('click',function(){
						
						$("#searchfileName").val("");
						$("#searchUploadDateStartDivId").val("");
						$("#searchUploadDateStartDiv").val("");
						$("#searchUploadDateEndDivId").val("");
						$("#searchUploadDateEndDiv").val("");
						$('#searchUploadUsers').val('');
						$("#searchUploadUsers").trigger("chosen:updated");
						
					});
				});
			});
        </script>
    </body>
</html>