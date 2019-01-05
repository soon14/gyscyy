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
			<div class="col-lg-12 col-md-12 col-sm-12 search-content  padding-zero">
				<div class="form-inline text-left" role="form">
				<div class="clearfix">
	                <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="searchunitIdDiv">单位名称</label>：
                            <div id="searchunitIdDiv" class="inputWidth text-left padding-zero"></div>
                        </div>
	                <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
	                    <label class="searchLabel" for="searchunitNameId">计划分类</label>：
	                    <div class="inputWidth padding-zero  text-left">
	                    <select id="searchPlanType" class="" name="searchPlanType"></select>
	                    </div>
	                </div>
	
						<div class="form-group col-lg-6 col-md-12 col-sm-6 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; ">
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
						<h5 class='table-title header smaller blue' >年度科技计划</h5>
						<table id="scienceTechnologyPlan_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
	                                <th>单位名称</th>
	                                <th>计划名称</th>
	                                <th>内容描述</th>
	                                <th>计划分类</th>
	                                <th>上传人</th>
	                                <th>上传时间</th>
	                                <th>计划完成时间</th>
<!-- 	                                <th>状态</th> -->
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
			var exportExcel = "";
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					var answerht = ${answerht};
					$('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
						//年度科技计划
						if($(e.target).attr('href') == "#scienceTechnology"){
							A.loadPage({
								render: "#scienceTechnology",
								url: format_url("/scienceTechnologyPlan/index")
							});
						}
						//年度技改计划
						if($(e.target).attr('href') == "#annualTechnicalPlan"){
							$('#scienceTechnology').show();
							A.loadPage({
								render: "#annualTechnicalPlan",
								url : format_url("/annualTechnicalPlan/index")
							});
						}
						//年度技术监督计划
						if($(e.target).attr('href') == "#runWayDiv"){
							A.loadPage({
								render: "#runWayDiv",
								url : format_url("/annualTechnicalPlan/index/")
							});
						}
						//年度培训计划
						if($(e.target).attr('href') == "#joinLandDiv"){
							A.loadPage({
								render: "#joinLandDiv",
								url : format_url("/joinLand/index/"+rlId)
							});
						}
					});
					var unitNameIdCombotree = new A.combotree({
						render: "#searchunitIdDiv",
						name: 'unitId',
						//返回数据待后台返回TODO
						datasource: ${unitNameIdTreeList},
						width:"210px",
						options: {
							treeId: 'unitId',
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
					var planTypeCombobox = new A.combobox({
						render: "#searchPlanType",
						datasource:${planTypeCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"allow_single_deselect": true,
							"disable_search_threshold":10
						}
					}).render();
					var scienceTechnologyPlanDatatables = new A.datatables({
						render: '#scienceTechnologyPlan_table',
						options: {
					        "ajax": {
					            "url": format_url("/scienceTechnologyPlan/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.conditions = conditions;
					            	dd=d;
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
							          {data: "planName",width: "auto",orderable: true}, 
							          {data: "description",width: "auto",orderable: true}, 
							          {data: "planTypeName",name:"planType",width: "auto",orderable: true}, 
							          {data: "userName",name:"userId",width: "auto",orderable: true}, 
							          {data: "uploadTime",width: "auto",orderable: true}, 
							          {data: "planCompleteTime",width: "auto",orderable: true},
// 							          {data: "statusName",name:"status",width: "auto",orderable: true}
							          ],
							          fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											 if(exportExcel){
												 exportExcels(format_url("/scienceTechnologyPlan/exportExcel"),JSON.stringify(dd));
											 }
											 exportExcel="";
										 },
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							if(answerht=="1"){
            								A.loadPage({
    											render : '#page-container',
    											url : format_url('/scienceTechnologyPlan/getAdd')
    										});
            							}else{
            								 alert("只有计划经营处负责人可以新增！");
            			                      return;
            							}
            							
            						}
        						}
        					}, {
        						label:"批量导入",
        						icon:"glyphicon glyphicon-upload",
        						className:"btn-primary",
        						events:{
            						click:function(event){
            							workSafeOneDialog = new A.dialog({
                    						width: 800,
                    						height: 300,
                    						title: "批量导入",
                    						url:format_url("/scienceTechnologyPlan/getBatchAdd"),
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
										var data = scienceTechnologyPlanDatatables.getSelectRowDatas();
										var ids = [];
										var userIds = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
												userIds.push(data[i].userId);
											}
										}
										if(data[0].status != "0"){
											alert("只有待提交的状态才可以删除!");
											return;
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										var loginUser = '${sysUserEntity.id}';
										var loginName = '${sysUserEntity.loginName}'
										for(var j=0;j<userIds.length;j++){
											if(userIds[j]!=loginUser&&loginName!="super"){
												alert('记录中包含不是当前登陆人的记录不能删除!');
												return;
											}
										}
										var url = format_url('/scienceTechnologyPlan/bulkDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													scienceTechnologyPlanDatatables.draw(false);
												},
												error:function(v,n){
													alert('删除失败');
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
            							$('#btnSearch').click();
            							exportExcel="exportExcel";
            						}
        						}
        					},{  
        						id:"dc",
        						label:"模板下载",
        						icon:"glyphicon glyphicon-download",
        						className:"btn-primary",
        						events:{
        							click:function(event){
    								 	exportExcels(format_url("/scienceTechnologyPlan/exportExcelModel"));
            						}
        						}
        					}],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								render: function(btnNode, data){
									var userId = ${sysUserEntity.id}
									var loginName = '${sysUserEntity.loginName}'
									if(data.userId!=userId&&loginName!="super"){
										btnNode.hide();
									}
									if(data.status!="0"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										A.loadPage({
											render : '#page-container',
											url : format_url('/scienceTechnologyPlan/getEdit/' + id)
										});
									}
								}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								render: function(btnNode, data){
									var userId = ${sysUserEntity.id}
									var loginName = '${sysUserEntity.loginName}'
									if(data.userId!=userId&&loginName!="super"){
										btnNode.hide();
									}
									if(data.status!="0"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/scienceTechnologyPlan/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											scienceTechnologyPlanDatatables.draw(false);
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
// 								if(data.status!="0"){
// 									btnNode.hide();
// 								}
// 								if(data.userId!=loginUserId){
// 									btnNode.hide();
// 								}
// 							},
// 							events:{
// 								click: function(event, nRow, nData){
// 									debugger;
// 									var id = nData.id;
// 												workticketDialog = new A.dialog({
// 													title:"选择单位负责人",
// 													url:format_url("/scienceTechnologyPlan/sureSubmit?id="+ id),
// 													height:450,
// 													width:500,
// 												}).render();
// 								}
// 							}
// 						}
					,
						{
							id: "detail",
							label:"查看",
							icon: "fa fa-binoculars bigger-130",
							className: "blue ",
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									A.loadPage({
										render : '#page-container',
										url : format_url("/scienceTechnologyPlan/getDetail/"+ id)
									});
								}
							}
						}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						if(unitNameIdCombotree.getValue()!=null
    							&&unitNameIdCombotree.getValue()+""!=""){
    						conditions.push({
            					field: 't.C_UNIT_ID',
            					fieldType:'STRING',
            					matchType:'EQ',
            					value:unitNameIdCombotree.getValue()
            				});
    					}
						if($("#searchPlanType").val()!=""){
	    					conditions.push({
	        					field: 't.C_PLAN_TYPE',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#searchPlanType').val()
	        				});
    					}
						scienceTechnologyPlanDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						unitNameIdCombotree.setValue();
						$("#searchPlanType").val("");
						$("#searchPlanType").trigger("chosen:updated");
					});
				});
			});
			function goBackToSubmitPerson(id,selectUser){//回调函数
				var url =format_url("/scienceTechnologyPlan/submit?workId="+id+"&selectUser="+selectUser);
				$.ajax({
					contentType: "application/json",
					dataType:"JSON",
					url : url,
					success: function(result){
						if(result.result=="success"){
							alert('操作成功');
							A.loadPage({
								render : '#page-container',
								url:format_url("/scienceTechnologyPlan/list"),
							});
						}else{
							alert(result.errorMsg);
						}
					},
					error:function(v,n){
						alert('操作失败');
					}
				});
				
			}
        </script>
    </body>
</html>