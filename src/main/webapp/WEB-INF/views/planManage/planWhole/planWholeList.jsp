<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="">
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' >整体计划表</h5>
						<table id="planWhole_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
	                                <th>计划开工时间</th>
	                                <th>计划完成时间</th>
	                                <th>计划总投资（万元）</th>
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
			var listFormDialog;
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					
						planWholeDatatables = new A.datatables({
						render: '#planWhole_table',
						options: {
					        "ajax": {
					            "url": format_url("/planWhole/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	if($("#id").val()){
						            	conditions.push({
				        					field: 'C_PlAN_ID',
				        					fieldType:'STRING',
				        					matchType:'EQ',
				        					value:$("#id").val()
				        				});
					            	}
					            	if($("#uuid").val()){
						            	conditions.push({
				        					field: 'C_UUID',
				        					fieldType:'STRING',
				        					matchType:'EQ',
				        					value:$("#uuid").val()
				        				});
					            	}
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false},
							          {data: "num",width: "auto",orderable: true},
							          {data: "projectName",width: "auto",orderable: true},
							          {data: "stratTime",width: "auto",orderable: true},
							          {data: "endTime",width: "auto",orderable: true}, 
							          {data: "planTotal",width: "auto",orderable: true}, 
							          {data: "remark",width: "auto",orderable: true}
							          ],
							toolbars: [
							           {
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							A.confirm("是否保存?",
    										function(){
            									flag="add";
            									$("#addForm").submit();
    										},
    										function(){
		            							var tmp=planWholeDatatables.getDatasValue("num");
		            							var max = 0;
		            							for(var i=0;i<tmp.length;i++){ 
		            							  if(max<tmp[i])max=tmp[i];
		            							 }
		            							var  num=max+1;
		            							A.loadPage({
													render : '#page-container',
													url : format_url('/planWhole/getAdd'),
													data:{"planUuid":$("#uuid").val(),"planId":$("#id").val(),"num":num}
												});
    									});
            						}
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = planWholeDatatables.getSelectRowDatas();
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
										var url = format_url('/planWhole/bulkDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													planWholeDatatables.draw(false);
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
										A.confirm("是否保存?",
											function(){
												flag=id;
												$("#addForm").submit();
											},
											function(){
												A.loadPage({
													render : '#page-container',
													url : format_url('/planWhole/getEdit'),
													data:{"id":id}
												});
											}
										);
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
										var url =format_url('/planWhole/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											planWholeDatatables.draw(false);
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
				});
			});
        </script>
    </body>
</html>