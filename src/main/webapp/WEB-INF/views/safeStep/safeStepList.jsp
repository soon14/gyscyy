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
					计划管理
				</li>
				<li class="active">安全计划</li>
				<li class="active">安全技术措施计划</li>
			</ul><!-- /.breadcrumb -->
		</div>
	
		<div class="page-content">
			<div class="col-lg-12 col-md-12 col-sm-12 search-content  padding-zero">
				<div class="form-inline text-left" role="form">
				<div class="clearfix">
					<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                       <label class="searchLabel">生产单位</label>：
                       <div class="padding-zero inputWidth  text-left">
                          <select id="unitId" class="" ></select>
                       </div>
                    </div>
                    
					<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                       <label class="searchLabel">分类</label>：
                       <div class="padding-zero inputWidth  text-left">
                          <select id="classify" class="" ></select>
                       </div>
                    </div>
                    
					<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                       <label class="searchLabel">内容</label>：
                       <input id="contentId" class="input-width text-left" placeholder="请输入内容" type="text" >
                    </div>
                    
                    <div class="form-group col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; ">
                          <button id="btnSearchAccident" class="btn btn-xs btn-primary">
                              <i class="glyphicon glyphicon-search"></i>
                         		     查询
                          </button>
                          <button id="btnResetAccident" class="btn btn-xs btn-primary" >
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
						<h5 class='table-title header smaller blue' >安全技术措施计划</h5>
						<table id="safeStep_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
	                                <th>生产单位</th>
	                                <th>分类</th>
	                                <th>内容</th>
	                                <th>费用预算(万元)</th>
	                                <th>完成时间</th>
	                                <th>备注</th>
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
			var exportExcelAccident="";
			var listFormDialog;
			jQuery(function($) {
				seajs.use(['datatables', 'confirm','combobox','my97datepicker'], function(A){
					var conditions=[];
					//生产单位
					var unitIdCombobox = new A.combobox({
						render: "#unitId",
						datasource:${unitIdList},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					//分类
					var classifyCombobox = new A.combobox({
						render: "#classify",
						datasource:${classifyList},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					var safeStepDatatables = new A.datatables({
						render: '#safeStep_table',
						options: {
					        "ajax": {
					            "url": format_url("/safeStep/search"),
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
							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "unitName",name:"unitId",width: "auto",orderable: true}, 
							          {data: "classifyName",name:"classify",width: "auto",orderable: true}, 
							          {data: "content",width: "auto",orderable: true}, 
							          {data: "money",width: "auto",orderable: true}, 
							          {data: "endTime",width: "auto",orderable: true},  
							          {data: "remark",width: "auto",orderable: true}
							          ],
							          fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											 if(exportExcelAccident){
												 exportExcels(format_url("/safeStep/exportExcel"),JSON.stringify(conditions));
											 }
											 exportExcelAccident="";
										 },
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							A.loadPage({
											render : '#page-container',
											url : format_url('/safeStep/getAdd')
										});
            						}
        						}
        					},{
        						label:"批量导入",
        						icon:"glyphicon glyphicon-upload",
        						className:"btn-primary",
        						events:{
            						click:function(event){
            							workSafeOneDialog = new A.dialog({
                    						width: 800,
                    						height: 300,
                    						title: "批量导入",
                    						url:format_url("/safeStep/getBatchAdd"),
                    						closed: function(){
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
										var data = safeStepDatatables.getSelectRowDatas();
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
										var url = format_url('/safeStep/bulkDelete/'+ids);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													if(result.result == 'success'){
														alert('删除成功');
														safeStepDatatables.draw(false);
	            									}else{
	            										alert(result.errorMsg);
	            									}
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
            							$('#btnSearchAccident').click();
            							exportExcelAccident="exportExcelAccident";
            						}
        						}
        					},{  
        						id:"dc",
        						label:"模板下载",
        						icon:"glyphicon glyphicon-download",
        						className:"btn-primary",
        						events:{
        							click:function(event){
    								 	exportExcels(format_url("/safeStep/exportExcelModel"));
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
										A.loadPage({
											render : '#page-container',
											url : format_url('/safeStep/getEdit/' + id)
										});
									}
								}
							}
							, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/safeStep/deleteOne/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											safeStepDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						} 
// 							,{
// 							id:"submit",
// 						 	label: "提交审核",
// 							icon: "fa fa-check-square-o bigger-130",
// 							className: "edit",
// 							render: function(btnNode, data){
// 								var loginUserId = '${sysUserEntity.id}'
// 								console.log(data);
// 								console.log(data.status);
// 								if(data.status!="0"){
// 									btnNode.hide();
// 								}
// 								if(data.uploadPerson!=loginUserId){
// 									btnNode.hide();
// 								}
// 							},
// 							events:{
// 								click: function(event, nRow, nData){
// 									debugger;
// 									var id = nData.id;
// 												workticketDialog = new A.dialog({
// 													title:"选择单位负责人",
// 													url:format_url("/accidentMeasuresPlan/sureSubmit?id="+ id),
// 													height:500,
// 													width:600,
// 												}).render();
// 								}
// 							}
// 						} 
					,{
							id: "detail",
							label:"查看",
							icon: "fa fa-binoculars bigger-130",
							className: "blue ",
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									A.loadPage({
										render : '#page-container',
										url : format_url("/safeStep/getDetail/"+ id)
									});
								}
							}
						}]
						}
					}).render();
					$('#btnSearchAccident').on('click',function(){
						conditions=[];
						//生产单位
						if($("#unitId").val()){
	    					conditions.push({
	    						field: 's.C_UNIT_ID',
	    						fieldType:'STRING',
	    						matchType:'EQ',
	    						value:$("#unitId").val()
	    					});
    					}
						//内容
						if($("#contentId").val()){
	    					conditions.push({
	    						field: 's.C_CONTENT',
	    						fieldType:'STRING',
	    						matchType:'LIKE',
	    						value:$("#contentId").val()
	    					});
    					}
						//分类
						if($("#classify").val()){
	    					conditions.push({
	    						field: 's.C_CLASSIFY',
	    						fieldType:'STRING',
	    						matchType:'EQ',
	    						value:$("#classify").val()
	    					});
    					}
    					safeStepDatatables.draw();
					});
					//重置条件并查询
					$('#btnResetAccident').on('click',function(){
						$("#unitId").val("");
						$("#unitId").trigger("chosen:updated");
						$("#contentId").val("");
						$("#classify").val("");
						$("#classify").trigger("chosen:updated");
						conditions=[];
						safeStepDatatables.draw();
					});
				});
			});
        </script>
    </body>
</html>