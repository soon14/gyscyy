<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%>       
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		 <div class="page-content">
		 		<div class="row">
		 			<div class="col-xs-12">
						<!-- div.dataTables_borderWrap -->
						<div class="widget-main no-padding">
							<form id="userForm" class="form-horizontal" role="form">
								<table id="user-table" class="table table-striped table-bordered table-hover" style="width:100%;">
									<thead>
										<tr>
											<th>序号</th>
											<th>种类</th>
											<th>内容</th>
											<th>措施检查</th>
											<th></th>
										</tr>
									</thead>
								</table>
							</form>
						</div>
					</div>
					
				<div class="">
				<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
					<i class="ace-icon fa fa-times"></i>
					取消
				</button>
				
				<button id="btnSave" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i>
					保存
				</button>
			</div>		
				</div>
			</div>		 	
					
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var userDialog;
			var index=1;
			jQuery(function($) {
				seajs.use(['datatables','combobox','combotree','confirm','my97datepicker','dialog'], function(A){
					var conditions=[];
					userDatatables = new A.datatables({
						render: '#user-table',
						options: {
					        serverSide: false,
					        multiple : true,
					        checked:false,
							bInfo:false,
							paging:false,
							ordering:true,
							columns: [
								{data:"id",  orderable: false},    
								{ data:"meetingFlag", "width":"30%", orderable: false, editType: "combobox", cfg:{
									datasource:${sysRoles},
									multiple:false,
									name:"roleIds",
									options:{
										"disable_search_threshold":10,
										"width": '100%'
									}
								}},
								{ data:"meetingContent", "width":"50%", orderable: false, editType:"input"},
								{ data:"checkState", "width":"10%", "height":"30px", orderable: false, editType:"input"}
							],
							optWidth:50,
							
							toolbars:[
							    {
									label:"新增",
									icon: "glyphicon glyphicon-plus",
									className:"btn-success",
									events:{
										click: function(event){
											userDatatables.addRow({"id":index, "meetingFlag":"", "meetingContent":"","checkState":""});
											index++;
										}
									}
							   }
							],
							btns:[
								{
									id:"delete",
									label:"删除",
									icon: "fa fa-trash-o bigger-130",
									className: "red del",
									events:{
										"click": function(event, nRow, nData){
											var id = nData.id;
											console.log(id);
											A.confirm('您确认删除么？',function(){
												userDatatables.deleteSelectRow(nRow);
											});		
										}
									}
								}
							]
						}
					}).render();
					
					function initDataTable(){
						var params = {};
						params.length = 10;
						params.start = 0;
						params.draw = 0;
						params.conditions = [];
						$.ajax({
							url: format_url("/safeMeeting/search"),
							contentType: "application/json",
							dataType: 'JSON',
							type: 'POST',
							data : JSON.stringify(params),
							success: function(result){
								userDatatables.addRows(result.data);
							}
							
						})
					};
					
					initDataTable();
					
					$('#userForm').validate({
						debug:true,
						rules: {
							meetingFlag: {
								required: true,
								maxlength: 10
							},
							meetingContent: {
								required: true,
								maxlength: 60,
							},
							checkState: {
								required: true,
							}
						},
				
						submitHandler: function (form) {
							var url = format_url("/safeMeeting");
							var str="";
							var obj = $("#userForm").serializeObject();
							var resultList= [];
							for(var key in obj){
								for(var i = 0; i<obj[key].length; i ++){
									if(resultList.length != obj[key].length){
										var result={};
										result[key] = obj[key][i];
										resultList.push(result);
									}else{
										resultList[i][key] = obj[key][i];
									}
								}
							}
						}
					});
					
					$("#btnSave").on("click", function(){
						$("#userForm").submit();
					});
					
				})
				
				
			});
		</script>
	</body>
</html>