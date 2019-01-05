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
				<li class="active">合同管理</li>
				<li class="active">合格供应商管理</li>
				<li class="active">供应商填报</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-lg-12 col-md-12 col-sm-12 search-content  padding-zero">
				<div class="form-inline text-left" role="form">
                   <div class="clearfix" >
                       <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="searchNameDiv">单位名称</label>：
                            <input id="searchName" class="input-width text-left"  placeholder="请输入单位名称" type="text"></input>
                        </div>
						<div class="form-group col-lg-9 col-md-9 col-sm-6 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; ">
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
						<h5 class='table-title header smaller blue' >供应商管理</h5>
						<table id="supplierManage_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
	                                <th>单位名称(全称)</th>
	                                <th>营业执照号码</th>
	                                <th>资质等级</th>
	                                <th>法人代表</th>
	                                <th>从业范围</th>
	                                <th>主要从业范围</th>
	                                <th>公司地址</th>
	                                <th>公司电话</th>
	                                <th>联系人</th>
	                                <th>联系电话</th>
	                                <th>联系人电子邮箱</th>
	                                <th>采购类型</th>
	                                <th>备注</th>
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
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					
					var type = ${type};
					var answerht = ${answerht};
					var supplierManageDatatables = new A.datatables({
						render: '#supplierManage_table',
						options: {
					        "ajax": {
					            "url": format_url("/supplierManage/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
				     					field: 'C_TYPE',
				     					fieldType:'STRING',
				     					matchType:'EQ',
				     					value:type
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
							          {data: "name",width: "8%",orderable: true}, 
							          {data: "code",width: "6%",orderable: true},
							          {data: "level",width: "7%",orderable: true}, 
							          {data: "delegateUserId",width: "6%",orderable: true}, 
							          {data: "workRange",width: "7%",orderable: true}, 
							          {data: "mainWorkRange",width: "11%",orderable: true}, 
							          {data: "address",width: "6%",orderable: true},
							          {data: "companyPhone",width: "6%",orderable: true}, 
							          {data: "userId",width: "5%",orderable: true}, 
							          {data: "phone",width: "6%",orderable: true}, 
							          {data: "email",width: "7%",orderable: true}, 
							          {data: "typeName",name:"type",width: "6%",orderable: true}, 
							          {data: "remark",width: "7%",orderable: true},
							          {data: "statusName",name:"status",width: "4%",orderable: true}
							          ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							if(answerht=="1"){
            								listFormDialog = new A.dialog({
                        						width: '850',
                        						height: '820',
                        						title: "供应商管理增加",
                        						url:format_url('/supplierManage/getAdd/'+type),
                        						closed: function(){
                        							supplierManageDatatables.draw(false);
                        						}	
                        					}).render();
            							}else{
            								 alert("只有计划经营处负责人可以新增！");
            			                      return;
            							}
                						
            						}
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = supplierManageDatatables.getSelectRowDatas();
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
										var url = format_url('/supplierManage/bulkDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													supplierManageDatatables.draw(false);
												},
												error:function(v,n){
													alert('删除失败');
												}
											});
										});
									}
								}
							},{
        						label:"复制",
        						icon:"glyphicon glyphicon-copy",
        						className:"btn-primary",
        						events:{
									click: function(event){
										var data = supplierManageDatatables.getSelectRowDatas();
										var ids = [];
										debugger;
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
											}
										}
										if(ids.length < 1){
											alert('请选择要复制的数据');
											return;
										}
										var url = format_url('/supplierManage/bulkCopyAdd/'+ids);
										A.confirm('您确认复制么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type:'POST',
												data : JSON.stringify(ids),
												success: function(result){
													alert('复制成功');
													supplierManageDatatables.draw(false);
												},
												error:function(v,n){
													alert('操作失败');
												}
											});
										});
									}
								}
        					},{
								label:"返回",
								icon: "fa fa-reply",
								className: "btn-primary ",
								events:{
									click: function(event, nRow, nData){
										A.loadPage({
											render : '#page-container',
											url : format_url("/purchaseType/index")
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
											width: '850',
											height: '820',
											title: "供应商管理编辑",
											url:format_url('/supplierManage/getEdit/' + id+'/'+type),
											closed: function(){
												supplierManageDatatables.draw(false);
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
										var url =format_url('/supplierManage/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											supplierManageDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						}, {
							id:"start",
						 	label: "启用",
							icon: "fa fa-check bigger-130",
							className: "blue search",
							render: function(btnNode, data){
								var status = data.status;
								if(status==0){
									btnNode.hide();
								}
		                      },
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									var status = nData.status;
									var url =format_url('/supplierManage/resultConfirmStart/'+ id + '/' + status);
										A.confirm('您确定要启用该供应商吗？',function(){
											$.ajax({
												url : url,
	    										contentType : 'application/json',
	    										dataType : 'JSON',
	    										type : 'POST',
	    										success: function(result){
	    												supplierManageDatatables.draw(false);
	        											alert('启用成功');
	    											
	    										},
	    										error:function(v,n){
	    											alert('操作失败');
	    										}
											});
										});
								}
							}
					}, {
							id:"stop",
						 	label: "禁用",
							icon: "fa fa-times bigger-130",
							className: "blue search",
							render: function(btnNode, data){
								var status = data.status;
								if(status==1){
									btnNode.hide();
								}
		                      },
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									var status = nData.status;
									var url =format_url('/supplierManage/resultConfirmStop/'+ id + '/' + status);
										A.confirm('您确定要禁用该供应商吗？',function(){
											$.ajax({
												url : url,
	    										contentType : 'application/json',
	    										dataType : 'JSON',
	    										type : 'POST',
	    										success: function(result){
	    											supplierManageDatatables.draw(false);
	    											alert('禁用成功');
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
						if($('#searchName').val()){
	    					conditions.push({
	        					field: 'C_NAME',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$('#searchName').val().trim()
	        				});
						}
						supplierManageDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$("#searchName").val('');
					});
				});
			});
        </script>
    </body>
</html>