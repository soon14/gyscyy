<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
<!-- 				<div class="col-xs-12"> -->
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' >操作项目</h5>
						<table id="operationItem_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>模拟</th>
	                                <th>实际</th>
	                                <th>操作项目</th>
<!-- 	                                <th>完成时间</th> -->
                                    <th >操作</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
<!--                 </div> -->
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var listFormDialog;
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					var operationItemDatatables = new A.datatables({
						render: '#operationItem_table',
						options: {
					        "ajax": {
					            "url": format_url("/operationItem/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.length=2147483647;
					            	if($('#id').val()){
				    					conditions.push({
				        					field: "operationId",
				        					fieldType:'STRING',
				        					matchType:'EQ',
				        					value:$('#id').val()
				        				});
									}
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked:false,
							paging:false,
							bInfo:false,
							optWidth: 40,
							columns: [{data:"id", visible:false,orderable:false},
							          {orderable: false,"width":"5%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "simulationName",width: "7%",orderable: true}, 
							          {data: "actualName",width: "7%",orderable: true},
// 							          {data: "order",width: "10%",orderable: true},
							          {data: "operationItem",width: "75%",orderable: true},
// 							          {data: "finishDate",width: "15%",orderable: true}
							          ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
                						listFormDialog = new A.dialog({
                    						width: 850,
                    						height:281 ,
                    						title: "操作票项目表增加",
                    						url:format_url('/operationItem/getAdd'),
                    						closed: function(){
                    							operationItemDatatables.draw(false);
                    						}	
                    					}).render()
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
											height:281 ,
											title: "操作项目编辑",
											url:format_url('/operationItem/getEdit/' + id),
											closed: function(){
												operationItemDatatables.draw(false);
											}
										}).render();
									}
								}
							},{
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/operationItem/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											if(result.result=="success"){
	        											alert('删除成功');
	        											operationItemDatatables.draw(false);
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
						}]
						}
					}).render();
				});
			});
        </script>
    </body>
</html>