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
						<h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">操作项目</h5>
						<table id="operationItem_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>模拟</th>
	                                <th>实际</th>
	                                <th>操作项目</th>
<!-- 	                                <th>完成时间</th> -->
	                                <th>操作</th>
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
							optWidth: 40,
							checked:false,
							paging:false,
							bInfo:false,
							columns: [{data:"id", visible:false,orderable:false},
							          {orderable: false,"width":"5%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "simulationName",width: "7%",orderable: true}, 
							          {data: "actualName",width: "7%",orderable: true},
// 							          {data: "order",width: "10%",orderable: true},
// 							          {data: "operationItem",width: "60%",orderable: true},
							          {width: "75%",orderable: false,"sClass": "center",orderable: true,"render": function(data, type, row,meta){
						                   	row.num= meta.row; 
							        	  	return row.operationItem ;
								          	}
								        },
// 							          {data: "finishDate",width: "15%",orderable: true}
								        ],
							          btns: [{
											id: "edit",
											label:"修改",
											icon: "fa fa-pencil-square-o bigger-130",
											className: "green edit",
											render: function(btnNode, data){
											},
											events:{
												click: function(event, nRow, nData){
													var operateItemNum=$("#operateItemNum").val();
													if(operateItemNum&&operateItemNum>=$("#operationItem_table tbody tr").length){
														operateItemNum=operateItemNum-$("#operationItem_table tbody tr").length;
													}
// 													if(nData.num!=operateItemNum){
// 														alert("请按照顺序填写");
// 														return ;
// 													};
													var id = nData.id;
													listFormDialog = new A.dialog({
														width:850 ,
														height:281 ,
														title: "操作项目编辑",
														url:format_url('/operationItem/getListEdit/' + id),
														closed: function(){
// 															operationItemDatatables.draw(false);
															A.loadPage({
																render : '#formDetailContent',
																url : format_url('/operationTicket/approve/' + $("#id").val()+'/dealing')
															});
														}
													}).render();
												}
											}
										}],
										fnInitComplete: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											if(!($("#status").val()=="5"||$("#status").val()=="7")){
												 $("#operationItem_table ").find("tr").each(function(i){
													if($(this).children().text()!="没有数据"){
													   $(this).children().last().hide();
													}
											     	});
											}
										 }
						}
					}).render();
				});
			
			});
        </script>
    </body>
</html>