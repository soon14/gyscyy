<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="page-content" style="overflow-height:auto;max-height:750px;">
			<div class="col-xs-12 search-content">
					<div class="form-inline" role="form">
								<div class="form-group col-xs-4">
									<label class="" for="form-field-1">发文标题：</label>
									<input id="searchDispatchTitle" class="input-width text-left" placeholder="请输入发文标题" type="text" >
								</div>
								<div class="form-group col-xs-4" >
									<label class="" for="form-field-1">发文字号：</label>
									<input id="searchDispatchNumber" class="input-width text-left" placeholder="请输入发文字号" type="text" >
								</div>
						
								<div class="form-group col-lg-4 col-md-12 col-sm-12 padding-zero text-right btnSearchBottom">
									<button id="resetBtn" class="btn btn-xs btn-primary">
										<i class="glyphicon glyphicon-search"></i>
										查询
									</button>
									<button id="resetBtn" class="btn btn-xs btn-primary">
										<i class="glyphicon glyphicon-repeat"></i>
										重置
									</button>
								</div>
					</div>
			</div>
			<div class="form-group">
					<div class="widget-main no-padding">
						<h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">发文</h5>
						<table id="dispatch-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
										<label class="pos-rel">
											<input type="checkbox" class="ace" />
											<span class="lbl"></span>
										</label>
									</th>
									<th>发文标题</th>
									<th>发文字号</th>
									<th>发文类型</th>
									<th>拟稿人</th>
									<th>拟稿时间</th>
									<th>发文部门</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
		</div>
		<div style="margin-right:10px;margin-top:10px;">
				<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal" >
						<i class="ace-icon fa fa-times"></i>取消
				</button>
				<button id="selectBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i>	确定
				</button>
		</div>
		<div  class="form-group" style="height:20px;"></div>
		<script type="text/javascript">
		var data=[];
		var equipInfoArray = [];
			jQuery(function($) {
				var conditions=[];
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
				
					
					var dispatchDatatables = new A.datatables({
						render: '#dispatch-table',
						options: {
							"ajax": {
							"url": format_url("/trainManagement/selectDispatchData"),
							"contentType": "application/json",
							"type": "POST",
								"dataType": "JSON",
								"data": function (d) {
									 conditions.push({
										field: 'C_APPROVAL_STATUS',
										fieldType:'STRING',
										matchType:'EQ',
										value : '10'
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
							aLengthMenu: [10],
							columns: [
										{data:"id", visible:false, orderable:false}, 
										{data: "title",width: "15%",orderable: false,
											render : function(data, type, row, meta) {
												if(data!=null && data.length>20){
													return "<div title='"+data+"'>"+data.substring(0,20)+"..."+"</div>";  
												}else{
													return data;  
												}
										}},
										{data: "dispatchName",width: "15%",orderable: false}, 
										{data: "typeCN",width: "15%",orderable: false}, 
										{data: "drafterName",width: "15%",orderable: false},
										{data: "draftTime",width: "15%",orderable: false},
										{data: "departmentName",width: "15%",orderable: false}
							]
						}
					}).render();

					//确定按钮
					$("#selectBtn").on("click", function(){
						data = dispatchDatatables.getSelectRowDatas();
						var ids = [];
						if(data.length && data.length>0){
							for(var i =0; i<data.length; i++){
								ids.push(data[i].id);
								var map = {};
								map.equipid =data[i].id; 
								map.equipName = data[i].name;
								map.equipManageType = data[i].equipManageType;
								map.equipManageTypeName = data[i].equipManageTypeName;
								equipInfoArray.push(map);
							}
						}
						if(ids.length < 1){
							alert('请选择发文');
							data=[];
							return;
						}
 						$(".bootbox-close-button.close").trigger("click");
					});
					
					$('#searchBtn').on('click',function(){
						conditions=[];
						var searchDispatchTitle = $("#searchDispatchTitle").val();
						var searchDispatchNumber = $("#searchDispatchNumber").val();
						
						if(searchDispatchTitle){
							conditions.push({
								field: 'C_TITLE',
								fieldType:'STRING',
								matchType:'LIKE',
								value : searchDispatchTitle
							});
						}
						
						if(searchDispatchNumber){
							conditions.push({
								field: 'C_DISPATCH_NAME',
								fieldType:'STRING',
								matchType:'LIKE',
								value : searchDispatchNumber
							});
						}
						dispatchDatatables.draw(true);
					});
					
					$('#resetBtn').on('click',function(){
						$('#searchDispatchTitle').val('')
						$('#searchDispatchNumber').val('')
					});
				});
			});
		</script>
	</body>
</html>