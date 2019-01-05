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
					安全管理
				</li>
				<li>
					目标管理
				</li>
				<li class="active">${targetType}</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
				<div class="col-lg-12 col-md-12 col-sm-12 padding-zero search-content">
				<div class="form-inline text-left " role="form">
				 <div class="clearfix">
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="searchSignUnitIdDiv">单位</label>：
                            <div class="padding-zero inputWidth  text-left">
                              <select id="searchSignUnitIdDiv" class="" ></select>
                              </div>
                        </div>
                         <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="searchTargetDateDiv">年号</label>：
                              <div class="form-group dateInputOther padding-zero text-left" style="width: 63%">
                             	<div id="searchTargetDateDiv" style="border:none; padding:0px;"></div>
                            </div>
                        </div>
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" >
                            <label class="searchLabel" for="searchName">名称</label>：
                             <input id="searchName" class="input-width text-left"  placeholder="请输入名称" type="text"></input>
                        </div>
                            <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" >
                            <label class="searchLabel" for="searchsignDate">时间</label>：
                            <div class="form-group dateInputOther padding-zero text-left" style="width: 63%">
                             	<div id="searchsignDate" style="border:none; padding:0px;"></div>
                            </div>
                        </div>
                          </div>
                          <div class="clearfix">
                         <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero btnSearchBottom"style="text-align:right;width: 100%" >
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
						<h5 class='table-title header smaller blue' >${targetType}</h5>
						<input class="col-md-12" id="type" name="type"  value="${type}" type="hidden">
						<table id="targetManage_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
	                                <th>名称</th>
	                                <th>年号</th>
	                                <th>单位</th>
	                                <th>时间</th>
	                                <th>填写人</th>
<!-- 	                                <th>备注</th> -->
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
					
					var type=${type};
					
					//日期组件
					var searchtimeDatePicker = new A.my97datepicker({
						id: "searchTargetDateId",
						name:"searchTargetDate",
						render:"#searchTargetDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy"
						}
					}).render();
					
					//日期组件
					var searchsignDate = new A.my97datepicker({
						id: "searchsignDateId",
						name:"searchsignDate",
						render:"#searchsignDate",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					
					//单位
					 searchunitid = new A.combobox({
						render : "#searchSignUnitIdDiv",
						datasource : ${unitList},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
// 					 $(".aptech-combotree-dropdown").css("width","62%");
//TODO 查询
					var exportExcel="";
					var targetManageDatatables = new A.datatables({
						render: '#targetManage_table',
						options: {
					        "ajax": {
					            "url": format_url("/targetManage/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
			        					field: 't.C_TYPE',
			        					fieldType:'LONG',
			        					matchType:'EQ',
			        					value:${type}
			        				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
						    multiple : true,
							ordering: true,
							searching: true,
							bStateSave: true,
							optWidth: 80,
							order: [[ 0, "desc" ]],
							columns: [
							          {data:"id", visible:false,orderable:false}, 
							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "name",width: "25%",orderable: true}, 
							          {data: "targetDateString",name:"targetDate",width: "12%",orderable: true}, 
							          {data: "signUnitName",name:"signUnitId",width: "19%",orderable: true},
							          {data: "signDateString",name:"signDate",width: "18%",orderable: true},
							          {data: "userName",name:"createUserId",width: "18%",orderable: true}
// 							          {data: "remark",width: "20%",orderable: true}
							         ],
							         
							         fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
										 if(exportExcel){
											 exportExcels(format_url("/targetManage/exportExcel/"+type),JSON.stringify(conditions));
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
                    						height:500 ,
                    						title: "生产单位增加",
                    						url:format_url('/targetManage/getAddPro'),
                    						closed: function(){
                    							targetManageDatatables.draw(false);
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
										var data = targetManageDatatables.getSelectRowDatas();
										var ids = [];
										var userIds = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
												userIds.push(data[i].createUserId);
											}
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										
										var loginUser = ${sysUserId};
										var loginName =${loginName};
										for(var j=0;j<userIds.length;j++){
											if(userIds[j]!=loginUser&&loginName!="super"){
												alert('记录中包含不是当前登陆人的记录不能删除!');
												return;
											}
										}
										var url = format_url('/targetManage/allDelete/2');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													targetManageDatatables.draw(false);
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
									var userId = ${sysUserId};
									var loginName =${loginName};
									if(data.createUserId!=userId && loginName!='super'){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										listFormDialog = new A.dialog({
											width: 870,
											height:500 ,
											title: "生产单位修改",
											url:format_url('/targetManage/getEditPro/' + id),
											closed: function(){
												targetManageDatatables.draw(false);
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
									var userId = ${sysUserId};
									var loginName =${loginName};
									if(data.createUserId!=userId && loginName!='super'){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/targetManage/deleteOne/'+ id+"/2");
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											targetManageDatatables.draw(false);
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
							label:"查看",
							icon: "fa fa-binoculars bigger-130",
							className: "blue ",
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									listFormDialog = new A.dialog({
										width: 870,
										height:500 ,
										title: "生产单位查看",
										url:format_url('/targetManage/getDetailPro/' + id),
										closed: function(){
											targetManageDatatables.draw(false);
										}
									}).render();
								}
							}
						}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						if($('#searchSignUnitIdDiv').val()){
	    					conditions.push({
	        					field: 't.C_SIGN_UNIT_ID',
	        					fieldType:'LONG',
	        					matchType:'EQ',
	        					value:$('#searchSignUnitIdDiv').val()
	        				});
						}
						if($('#searchTargetDateId').val()){
	    					conditions.push({
	        					field: 't.C_TARGET_DATE',
	        					fieldType:'DATE',
	        					matchType:'EQ',
	        					value:$('#searchTargetDateId').val()+"-01-01 00:00:00"
	        				});
						}
				
						if($("#searchName").val()!=""){
							conditions.push({
								field: 't.C_NAME',
								fieldType:'STRING',
								matchType:'LIKE',
								value:$("#searchName").val().trim()
							});
						}
						if($('#searchsignDateId').val()){
	    					conditions.push({
	        					field: 't.C_SIGN_DATE',
	        					fieldType:'DATE',
	        					matchType:'EQ',
	        					value:$('#searchsignDateId').val()+" 00:00:00"
	        				});
						}
						targetManageDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$("#searchSignUnitIdDiv").val("");
						$("#searchSignUnitIdDiv").trigger("chosen:updated");
						$('#searchTargetDateId').val("");
						$('#searchsignDateId').val("");
						$("#searchName").val('');
					});
				});
			});
        </script>
    </body>
</html>