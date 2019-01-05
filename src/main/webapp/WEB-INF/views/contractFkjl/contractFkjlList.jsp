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
				<li class="active">合同台账管理</li>
				<li class="active">付款记录</li>
			</ul>
		</div>
		<div class="page-content">
			<!-- <div class="col-lg-12 col-md-12 col-sm-12 search-content  padding-zero">
				<div class="form-inline text-left" role="form">
                   <div class="clearfix" >
						<div class="form-group col-lg-12 col-md-12 col-sm-6 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; ">
                            <button id="btnBackTicket" class="btn btn-xs btn-primary">
								<i class="fa fa-reply"></i>
								返回
							</button>
                       	</div>
				   </div>
                </div>
            </div>	 -->		
			<div class="row">
				<div class="col-xs-12">
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' >合同付款记录</h5>
						<table id="contractFkjl_table" class="table table-striped table-bordered table-hover" style="width:100%;">
						<input  type="hidden" id="zbid" value="${zbid}"/>
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<!-- <th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th> -->
	                                <th>第几次付款</th>
	                                <th>未付合同金额</th>
	                                <th>付款金额</th>
	                                <th>现未付合同金额</th>
	                                <th>付款时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
		<script type="text/javascript">
			var listFkjlDialog;
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					
					var contractFkjlDatatables = new A.datatables({
						render: '#contractFkjl_table',
						options: {
					        "ajax": {
					            "url": format_url("/contractFkjl/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.conditions = conditions;
					            	d.zbid=$("#zbid").val();
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							checked:false,
							columns: [
							          {data:"id", visible:false,orderable:false},
							          {data: "xh",width: "15%",orderable: true}, 
							          {data: "wfhtje",width: "20%",orderable: true},
							          {data: "fkje",width: "20%",orderable: true}, 
							          {data: "falg1",width: "20%",orderable: true}, 
							          {data: "fksj",width: "15%",orderable: true}],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							var zbid=$("#zbid").val();
                						listFkjlDialog = new A.dialog({
                    						width: '600',
                    						height: '400',
                    						title: "付款记录新增",
                    						url:format_url('/contractFkjl/getAdd/'+zbid),
                    						closed: function(){
                    							contractFkjlDatatables.draw(false);
                    						}	
                    					}).render()
            						}
        						}
        					}, {
								label:"返回",
								icon:"fa fa-reply",
								className:"btn-primary",
								events:{
									click: function(event){
										A.loadPage({
											render : '#page-container',
											url : format_url("/contractManage/index")
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
										listFkjlDialog = new A.dialog({
											width: '600',
											height: '400',
											title: "付款记录修改",
											url:format_url('/contractFkjl/getEdit/'+ id),
											closed: function(){
												contractFkjlDatatables.draw(false);
											}
										}).render();
									}
								}
							}
							/* , {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/contractFkjl/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											contractFkjlDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						} */
							]
						}
					}).render();
					
					$('#btnBackTicket').on('click',function(){
							A.loadPage({
								render : '#page-container',
								url : format_url("/contractManage/index")
							});
					});
				});
			});
        </script>
    </body>
</html>