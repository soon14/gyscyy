<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
<!-- 		<meta http-equiv="refresh" content="1;url=http://www.baidu.com"> -->
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="breadcrumbs ace-save-state" id="breadcrumbs">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="javascript:void(0);" onclick="firstPage()">首页</a>
				</li>
				<li>安全管理</li>
				<li>安全检查</li>
				<li class="active">存在问题及整改情况</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-lg-12 col-md-12 col-sm-12 search-content  padding-zero">
				<div class="form-inline text-left" role="form">
					<div class="clearfix">
						<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
							<label class="searchLabel" for="form-field-1">名称</label>：
							<input id="searchtitle" class="input-width text-left" placeholder="请输入名称" type="text" >
	                   </div>
<!-- 						<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" > -->
<!-- 							<label class="searchLabel" for="form-field-1">年号</label>： -->
<!-- 			                <div class="form-group dateInputOther padding-zero text-left"> -->
<!-- 	                    		<div id="yearNum" style="border:none; padding:0px;height:30px;width:260px"></div> -->
<!-- 	                    	</div> -->
<!-- 	                   </div> -->
			           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
							<label class="searchLabel" for="form-field-1">类别</label>：
							<div class="input-width padding-zero  text-left">
			                	<select id="category" class="form-control chosen-select" style="width:150px;"></select>
			                </div>
			           </div>
			           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
	                    <label class="searchLabel" for="searchunitIdDiv">单位</label>：
	                    <div class="input-width padding-zero  text-left">
	                   <select id="unitIdSelect" name="unitId" class="col-md-12 chosen-select"></select>
	                   </div>
	                </div>
	                   <div class="form-group col-lg-3 col-md-9 col-sm-6 col-xs-12 padding-zero btnSearchBottom" style="text-align:right;">
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
						<h5 class='table-title header smaller blue' >存在问题及整改情况</h5>
						<table id="trainPlan_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
        							<th>单位</th>
	                                <th>名称</th>
	                                <th>发现时间</th>
	                                <th>整改情况</th>
	                                <th>整改时间</th>
	                                <th>填写人</th>
	                                <th>类别</th>
	                                <th>检查人员</th>
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
				seajs.use(['datatables', 'confirm', 'dialog','combobox','my97datepicker','combotree'], function(A){
					var conditions=[];
					var yearNumDatePicker = new A.my97datepicker({
						id: 'yearNumTime',
						name: 'yearNum',
						render:'#yearNum',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: 'yyyy'
						}
					}).render();
					//安全检查类别
					var statusCombobox = new A.combobox({
						render: "#category",
						datasource:${safeCheckTypeEnumCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//单位
					 searchunitid = new A.combobox({
						render : "#unitIdSelect",
						datasource : ${unitList},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					var exportExcel="";
					var trainPlanDatatables = new A.datatables({
						render: '#trainPlan_table',
						options: {
					        "ajax": {
					            "url": format_url("/safeCheck/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
			        					field: 'c.C_TYPE',
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
							order:[[0,'desc']],
							columns: [
							          {data:"id", visible:false,orderable:true}, 
							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
						              {data: "unitName",name:"unitId",width: "auto",orderable: true}, 
							          {data: "checkName",width: "auto",orderable: true},
							          {data: "checkDate",name:"checkDate",width: "auto",orderable: true}, 
							          {data: "correctionRectify",width: "auto",orderable: true}, 
							          {data: "correctionDate",width: "auto",orderable: true}, 
							          {data: "userName",name:"createPeopleId",width: "auto",orderable: true}, 
							          {data: "categoryName",name:"category",width: "auto",orderable: true}, 
							          {data: "landWaiter",width: "auto",orderable: true}, 
							          
							          ],
							          fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											 if(exportExcel){
												 exportExcels(format_url("/safeCheck/exportExcel/3"),JSON.stringify(conditions));
											 }
											 exportExcel="";
										 },
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
                    						listFormDialog = new A.dialog({
                        						width:870 ,
                        						height:600 ,
                        						title: "存在问题及整改情况增加",
                        						url:format_url('/safeCheck/getAdd/3'),
                        						closed: function(){
                        							trainPlanDatatables.draw(false);
                        						}	
                        					}).render();
            						}
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = trainPlanDatatables.getSelectRowDatas();
										var ids = [];
										var userIds = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
												userIds.push(data[i].createPeopleId);
											}
										}
										var loginUser = '${sysUserEntity.id}';
					                    var loginName = '${sysUserEntity.loginName}'
				                    	for(var j=0;j<userIds.length;j++){
				                            if(userIds[j]!=loginUser&&loginName!="super"){
				                              alert('记录中包含不是当前登陆人的记录不能删除!');
				                              return;
				                            }
				                          }
										
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										var url = format_url('/safeCheck/bulkDelete/3');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													trainPlanDatatables.draw(false);
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
            							searchFunction();
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
									if(data.createPeopleId!=loginUser&&loginName!="super"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										listFormDialog = new A.dialog({
											width: 870,
											height:600 ,
											title: "存在问题及整改情况修改",
											url:format_url('/safeCheck/getEdit/3/' + id),
											closed: function(){
												trainPlanDatatables.draw(false);
											}
										}).render();
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
									if(data.createPeopleId!=loginUser&&loginName!="super"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/safeCheck/delete/'+ id+"/3");
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											trainPlanDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						}, {
						 	label: "查看",
							icon: "fa fa-binoculars bigger-130",
							className: "blue search",
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									listFormDialog = new A.dialog({
										width: 870,
										height:600 ,
										title: "存在问题及整改情况查看",
										url:format_url('/safeCheck/getshowPage/3/' + id),
										closed: function(){
											trainPlanDatatables.draw(false);
										}
									}).render();
								}
							}
						}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						searchFunction();
					});
					function searchFunction(){
						conditions=[];

						if($('#searchtitle').val()){
        					conditions.push({
        						field: 'c.C_CHECK_NAME',
        						fieldType:'STRING',
        						matchType:'LIKE',
        						value:$('#searchtitle').val()
        					});
						}

						if($("#yearNumTime").val()){
        					conditions.push({
        						field: 'c.C_YEAR_NUM',
        						fieldType:'STRING',
        						matchType:'EQ',
        						value:$("#yearNumTime").val()
        					});
						}
						
						if($("#category").val()){
    						conditions.push({
    							field: 'c.C_CATEGORY',
    							fieldType:'STRING',
    							matchType:'EQ',
    							value:$("#category").val()
    						});
						}
						if($('#unitIdSelect').val()){
	    					conditions.push({
	        					field: 'c.C_UNIT_ID',
	        					fieldType:'LONG',
	        					matchType:'EQ',
	        					value:$('#unitIdSelect').val()
	        				});
						}
						trainPlanDatatables.draw();
					};
					$('#btnReset').on('click',function(){
						$('#searchtitle').val('')
						$('#yearNumTime').val('')
						$('#category').val('')
						$("#category").trigger("chosen:updated");
						$("#unitIdSelect").val("");
						$("#unitIdSelect").trigger("chosen:updated");
					});
				});
			});
        </script>
    </body>
</html>