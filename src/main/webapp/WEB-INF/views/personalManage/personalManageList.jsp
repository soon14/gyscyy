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
					基本信息
				</li>
				<li class="active">人员管理</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class=" col-lg-12 col-md-12 col-sm-12  padding-zero search-content">
				<div class="form-inline text-left" role="form">
					<div class="clearfix">
						<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
							<div  class="clearfix groupDiv">
							<label class="searchLabel" for="searchcode">工号</label>：
			                <input id="searchcode" class="inputWidth text-left"placeholder="请输入工号" type="text" >
	                   		</div>
	                   		<div  class="clearfix  groupRightDiv">
	                   		<label class="searchLabel" for="searchname">姓名</label>：
			                <input id="searchname" class="inputWidth text-left" placeholder="请输入姓名" type="text" >
	                   		</div>
	                    </div>
	                   <div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
		                   <div  class="clearfix groupDiv">
		                   <label class="searchLabel" for="searchSex">性别</label>：
			                <div class="inputWidth padding-zero  text-left">
				                <select id="searchSex" ></select>
							</div>
		                   </div>
		                   <div  class="clearfix  groupRightDiv">
		                   <label class="searchLabel" for="searchUnitNameDiv">单位</label>：
			                <div id="searchUnitNameDiv"  class="inputWidth text-left padding-zero"></div>
		                   </div>
	                   </div> 
                   </div>
	              <div  class="form-group col-lg-12 col-md-12 col-sm-12 padding-zero text-right btnSearchBottom">
	              		<div class="form-group">
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
						<h5 class='table-title header smaller blue' >人员管理</h5>
						<table id="personalManage_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>工号</th>
	                                <th>姓名</th>
	                                <th>性别</th>
	                                <th>单位</th>
	                                <th>是否为外部人员</th>
	                                <th>联系方式</th>
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
					//combobx组件
					var sexComboboxData = ${sexCombobox};
					var sexCombobox = new A.combobox({
						render: "#searchSex",
						//返回数据待后台返回
						datasource:sexComboboxData,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					var outStatusCombobox = ${outStatusCombobox};
					var sexCombobox = new A.combobox({
						render: "#searchOutStatus",
						name:"outStatus",
						//返回数据待后台返回
						datasource:outStatusCombobox,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//combotree组件
					var unitTreeList = ${unitTreeList};
					var unitCombotree = new A.combotree({
						render: "#searchUnitNameDiv",
						name: "unit",
						//返回数据待后台返回
						datasource: unitTreeList,
						width:"210px",
						options: {
							treeId: 'unit',
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
					var personalManageDatatables = new A.datatables({
						render: '#personalManage_table',
						options: {
					        "ajax": {
					            "url": format_url("/personalManage/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
		        						field: 'a.C_STATUS',
		        						fieldType:'STRING',
		        						matchType:'EQ',
		        						value:'1'
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
							          {data: "code",width: "20%",orderable: true}, 
							          {data: "name",width: "20%",orderable: true}, 
							          {data: "sexName",width: "10%",orderable: true}, 
							          {data: "unitName",width: "20%",orderable: true}, 
							          {data: "outStatusName",width: "20%",orderable: true}, 
							          {data: "mobile",width: "20%",orderable: true}
							         ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
                						listFormDialog = new A.dialog({
                    						width: 850,
                    						height: 471,
                    						title: "人员管理增加",
                    						url:format_url('/personalManage/getAdd'),
                    						closed: function(){
                    							personalManageDatatables.draw(false);
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
										var data = personalManageDatatables.getSelectRowDatas();
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
										var url = format_url('/personalManage/bulkDel/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													personalManageDatatables.draw(false);
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
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										listFormDialog = new A.dialog({
											width: 850,
											height: 471,
											title: "人员管理编辑",
											url:format_url('/personalManage/getEdit/' + id),
											closed: function(){
												personalManageDatatables.draw(false);
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
										var url =format_url('/personalManage/singleDel/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											personalManageDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						if($('#searchname').val()){
        					conditions.push({
        						field: 'a.C_NAME',
        						fieldType:'STRING',
        						matchType:'LIKE',
        						value:$('#searchname').val()
        					});
						}

						if($('#searchcode').val()){
        					conditions.push({
        						field: 'a.C_CODE',
        						fieldType:'STRING',
        						matchType:'LIKE',
        						value:$('#searchcode').val()
        					});
						}
						if($('#searchSex').val()){
							conditions.push({
    							field: 'sex',
    							fieldType:'STRING',
    							matchType:'EQ',
    							value:$("#searchSex").val()
    						});
						}
						if($('#searchOutStatus').val()){
							conditions.push({
    							field: 'outStatus',
    							fieldType:'STRING',
    							matchType:'EQ',
    							value:$("#searchOutStatus").val()
    						});
						}
						if(unitCombotree.getValue()!=null&&unitCombotree.getValue()+""!=""){
							conditions.push({
	        					field: 'unit',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:unitCombotree.getValue()
	        				});
						}

						personalManageDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$('#searchname').val('');
						$('#searchcode').val('');
						sexCombobox.setValue('9998');
						unitCombotree.setValue(null);
					});
				});
			});
        </script>
    </body>
</html>