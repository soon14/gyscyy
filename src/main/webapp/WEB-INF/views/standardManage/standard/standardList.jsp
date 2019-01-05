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
					标准管理
				</li>
				<li class="active">${standardType}标准管理</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
				<form id="downloadForm" name="downloadForm" action="" hidden="hidden">
		 		</form>
			<div class="col-lg-12 col-md-12 col-sm-12 padding-zero search-content">
				<div class="form-inline text-left" role="form">
					<div class="clearfix">
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="name">文件名称</label>：
                           <input id="searchname"  placeholder="请输入文件名称" class="input-width padding-zero   text-left"  type="text">
                        </div>
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="uploadUserIdDiv">上传人</label>：
                             <div class="padding-zero inputWidth  text-left">
                             <select id="searchuploadUserId" class="" name="uploadUser"></select>
                             </div>
                        </div>
                         <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero" >
	                            <label class="searchLabel" for="searchStartUploadTimeDiv">上传时间</label>：
	                            <div class="form-group dateInputOther padding-zero text-left">
	                                <div id="searchStartUploadTimeDiv"  style="border:none; padding:0px;"></div>
	                            </div>
	                            <div class="toLabel" for="searchEndUploadTimeDiv" >~</div>
	                            <div class="form-group dateInputOther padding-zero text-left">
	                                <div id="searchUploadTimeDiv"   style="border:none; padding:0px;"></div>
	                            </div>
	                     </div>
                        
                   </div> 
                      <div class="clearfix">
                      <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" >
                            <label class="searchLabel" for="edition">版本</label>：
                                 <input id="searchedition"  placeholder="请输入版本" class="input-width padding-zero   text-left"  type="text">
                      	</div>
	                    <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="searchUnitId">归口单位</label>：
                             <div class="padding-zero inputWidth  text-left">
                             <select id="searchUnitId" class="" name="unitId"></select>
                             </div>
                        </div>
	                      	<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 padding-zero btnSearchBottom"style="text-align:right;" >
	                            <button id="btnSearch" class="btn btn-xs btn-primary" >
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
						<h5 class='table-title header smaller blue' >${standardType}标准管理</h5>
						<input class="col-md-12" id="type" name="type"  value="${type}" type="hidden">
						<table id="standard_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
	                                <th>文件名称</th>
	                                <th>文件编号</th>
	                                <th>版本</th>
	                                <th>上传人</th>
	                                <th>上传时间</th>
									<th>归口单位</th>
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
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					var searchuploadUserId = new A.combobox({
						render : "#searchuploadUserId",
						datasource : ${searchuser},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					
					var searchStartUploadTimeDiv = new A.my97datepicker({
						id: 'searchStartUploadTimeId',
						name:'uploadTime',
						render:'#searchStartUploadTimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					
					var searchEndUploadTimeDiv = new A.my97datepicker({
						id: 'searchEndUploadTimeId',
						name:'uploadTime',
						render:'#searchUploadTimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'searchStartUploadTimeId\\')}",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					
					//归口单位
					var unitCombobox = new A.combobox({
						render : "#searchUnitId",
						name : "unitId",
						datasource : ${unitList},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					
					var standardDatatables = new A.datatables({
						render: '#standard_table',
						options: {
					        "ajax": {
					            "url": format_url("/standard/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
			        					field: 'C_TYPE',
			        					fieldType:'STRING',
			        					matchType:'EQ',
			        					value:${type}
			        				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							columns: [
							          {data:"id", visible:false,orderable:false}, 
							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "name",width: "auto",orderable: true},
							          {data: "code",width: "auto",orderable: true},
							          {data: "edition",width: "auto",orderable: true},
							          {data: "uploadUserName",width: "auto",orderable: true},
							          {data: "uploadTime",width: "auto",orderable: true},
									{data: "unitName",width: "auto",orderable: true},
							],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
                						listFormDialog = new A.dialog({
                    						width: 850,
                    						height:521 ,
                    						title: "${standardType}标准管理增加",
                    						url:format_url('/standard/getAdd'),
                    						closed: function(){
                    							standardDatatables.draw(false);
                    						}	
                    					}).render()
            						}
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = standardDatatables.getSelectRowDatas();
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
										var url = format_url('/standard/allDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													standardDatatables.draw(false);
												},
												error:function(v,n){
													alert('操作失败');
												}
											});
										});
									}
								}
							}, {
								label:"下载",
								icon:"glyphicon glyphicon-download-alt",
								className:"btn btn-xs btn-primary",
								events:{
									click: function(event){
										var data = standardDatatables.getSelectRowDatas();
										var ids = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
											}
										}
										if(ids.length < 1){
											alert('请选择要下载的数据');
											return;
										}
										$("#downloadForm").attr("action",format_url("/standard/upload/" + ids));
										$("#downloadForm").submit();
									}
								}
							}],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										listFormDialog = new A.dialog({
											width:850 ,
											height: 521,
											title: "${standardType}标准管理编辑",
											url:format_url('/standard/getEdit/' + id),
											closed: function(){
												standardDatatables.draw(false);
											}
										}).render();
									}
								}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/standard/deleteOne/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											standardDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						},{
							id: "detail",
							label:"修改",
							icon: "fa fa-binoculars bigger-130",
							className: "blue",
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									listFormDialog = new A.dialog({
										width:850 ,
										height: 521,
										title: "${standardType}标准管理查看",
										url:format_url('/standard/getDetail/' + id),
										closed: function(){
											standardDatatables.draw(false);
										}
									}).render();
								}
							}
						}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						if($('#searchname').val()){
	    					conditions.push({
	        					field: 'T.C_NAME',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					name:'searchname',
	        					type:1,
	        					value:$('#searchname').val()
	        				});
						}
						if($('#searchuploadUserId').val()){
	    					conditions.push({
	        					field: 'C_UPLOAD_USER',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:'searchuploadUserId',
	        					type:1,
	        					value:$('#searchuploadUserId').val()
	        				});
						}
						if($('#searchedition').val()){
	    					conditions.push({
	        					field: 'C_EDITION',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					name:'searchedition',
	        					type:1,
	        					value:$('#searchedition').val()
	        				});
						}
						if($('#searchStartUploadTimeId').val()){
	    					conditions.push({
	        					field: 'C_UPLOAD_TIME',
	        					fieldType:'DATE',
	        					matchType:'GE',
	        					name:'searchStartUploadTime',
	        					type:1,
	        					value:$('#searchStartUploadTimeId').val()
	        				});
						}
						
						if ($("#searchUnitId").val() !=null && $("#searchUnitId").val()+"" != "") {
	    					conditions.push({
	        					field: 't.C_UNIT_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#searchUnitId').val()
	        				});
						}

						if($('#searchEndUploadTimeId').val()){
	    					conditions.push({
	        					field: 'C_UPLOAD_TIME',
	        					fieldType:'DATE',
	        					matchType:'LE',
	        					name:'searchEndUploadTime',
	        					type:1,
	        					value:$('#searchEndUploadTimeId').val()
	        				});
						}
						
						standardDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$("#searchname").val("");
						$("#searchedition").val("");
						$("#searchuploadUserId").val("");
						$("#searchuploadUserId").trigger("chosen:updated");
						$("#searchStartUploadTimeId").val("");
						$("#searchEndUploadTimeId").val("");
						$("#searchUnitId").val("");
						$("#searchUnitId").trigger("chosen:updated");
					});
				});
			});
        </script>
    </body>
</html>