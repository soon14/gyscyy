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
	                    <label class="searchLabel" for="searchunitIdDiv">责任单位</label>：
	                    <div id="searchunitIdDivSupervision" class="inputWidth text-left padding-zero"></div>
	                </div>
	                <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
	                    <label class="searchLabel " for="form-field-1">项目名称</label>：
	                    <input id="searchSubject" class="inputWidth text-left" placeholder="请输入项目名称" type="text">
	                </div>
					<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
	                    <label class="searchLabel" for="searchunitNameId">监督专业</label>：
	                    <div class="inputWidth padding-zero  text-left">
	                    <select id="searchSupervisionMajor" class="" name="searchPlanType"></select>
	                    </div>
	                </div>
						<div class="form-group col-lg-3 col-md-12 col-sm-6 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; ">
                            <button id="btnSearchSupervision" class="btn btn-xs btn-primary">
                                <i class="glyphicon glyphicon-search"></i>
                           		     查询
                            </button>
                            <button id="btnResetSupervision" class="btn btn-xs btn-primary" >
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
						<h5 class='table-title header smaller blue' >年度技术监督计划</h5>
						<table id="annualSupervisionPlan_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
	                                <th>项目名称</th>
	                                <th>责任单位</th>
	                                <th>目的及方案</th>
	                                <th>计划完成时间</th>
	                                <th>监督专业</th>
	                                <th>备注</th>
	                                <th>上传人</th>
	                                <th>上传时间</th>
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
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					var answerht = ${answerht};
					var exportExcelSupervision="";
					var unitNameIdCombotree = new A.combotree({
						render: "#searchunitIdDivSupervision",
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
					var supervisionMajorCombobox = new A.combobox({
						render: "#searchSupervisionMajor",
						datasource:${supervisionMajorCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"allow_single_deselect": true,
							"disable_search_threshold":10
						}
					}).render();
					var annualSupervisionPlanDatatables = new A.datatables({
						render: '#annualSupervisionPlan_table',
						options: {
					        "ajax": {
					            "url": format_url("/annualSupervisionPlan/search"),
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
							          {data: "subject",width: "auto",orderable: true}, 
							          {data: "unitName",name:"unitId",width: "auto",orderable: true}, 
							          {data: "purposePlan",width: "auto",orderable: true}, 
							          {data: "planDate",width: "auto",orderable: true}, 
							          {data: "supervisionMajorName",name:"supervisionMajor",width: "auto",orderable: true}, 
							          {data: "remark",width: "auto",orderable: true}, 
							          {data: "userName",name:"userId",width: "auto",orderable: true}, 
							          {data: "uploadTime",width: "auto",orderable: true}, 
							          ],
							          fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											 if(exportExcelSupervision){
												 debugger;
												 exportExcels(format_url("/annualSupervisionPlan/exportExcelSupervision"),JSON.stringify(dd));
											 }
											 exportExcelSupervision="";
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
    											url : format_url('/annualSupervisionPlan/getAdd')
    										});
            							}else{
            								 alert("只有计划经营处负责人可以新增！");
            			                      return;
            							}
            							
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
                    						url:format_url("/annualSupervisionPlan/getBatchAdd"),
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
										var data = annualSupervisionPlanDatatables.getSelectRowDatas();
										var ids = [];
										var userIds = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
												userIds.push(data[i].userId);
											}
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
										var url = format_url('/annualSupervisionPlan/bulkDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													annualSupervisionPlanDatatables.draw(false);
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
        								debugger;
            							$('#btnSearchSupervision').click();
            							exportExcelSupervision="exportExcelSupervision";
            						}
        						}
        					},{  
        						id:"dc",
        						label:"模板下载",
        						icon:"glyphicon glyphicon-download",
        						className:"btn-primary",
        						events:{
        							click:function(event){
    								 	exportExcels(format_url("/annualSupervisionPlan/exportExcelModel"));
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
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										A.loadPage({
											render : '#page-container',
											url : format_url('/annualSupervisionPlan/getEdit/' + id)
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
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/annualSupervisionPlan/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											annualSupervisionPlanDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						} 
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
										url : format_url("/annualSupervisionPlan/getDetail/"+ id)
									});
								}
							}
						}]
						}
					}).render();
					$('#btnSearchSupervision').on('click',function(){
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
						if($("#searchSupervisionMajor").val()!=""){
	    					conditions.push({
	        					field: 't.C_SUPERVISION_MAJOR',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#searchSupervisionMajor').val()
	        				});
    					}
						if($("#searchSubject").val()!=""){
	    					conditions.push({
	        					field: 't.C_SUBJECT',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$('#searchSubject').val()
	        				});
    					}
						annualSupervisionPlanDatatables.draw();
					});
					$('#btnResetSupervision').on('click',function(){
						unitNameIdCombotree.setValue(null);
						$("#searchSupervisionMajor").val("");
						$("#searchSupervisionMajor").trigger("chosen:updated");
						$("#searchSubject").val("");
					});
				});
			});
			function goBackToSubmitPerson(id,selectUser){//回调函数
				var url =format_url("/annualSupervisionPlan/submit?workId="+id+"&selectUser="+selectUser);
				$.ajax({
					contentType: "application/json",
					dataType:"JSON",
					url : url,
					success: function(result){
						if(result.result=="success"){
							alert('操作成功');
							A.loadPage({
								render : '#page-container',
								url:format_url("/annualSupervisionPlan/list"),
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