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
			<div class="col-lg-12 col-md-12 col-sm-12 search-content">
				<div class="form-inline  text-right" role="form">
					<div class="clearfix">
			           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
							<label class="" for="form-field-1">典型票名称：</label>
			                <input id="searchname" class="input-width text-left" placeholder="请输入名称" type="text" >
	                   </div>
			           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
							<label class="" for="form-field-1">单位名称：</label>
							 <div id="searchunitIdDiv" class="input-width text-left padding-zero"></div>
	                   </div>
			           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
							<label class="" for="form-field-1">设置人：</label>
							 <div class="padding-zero input-width  text-left">
	                   		 	<select id="searchcreatePersonId" class="col-md-12" name="searchcreatePersonId"></select>
	                   		</div>
	                   </div>
	                   <div class="form-group" style="float:right; margin-right:30px;">
							<button id="btnSearch" class="btn btn-xs btn-primary">
								<i class="glyphicon glyphicon-search"></i>
								查询
							</button>
							<button id="btnReset" class="btn btn-xs btn-primary">
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
						<h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">典型票</h5>
						<table id="typicalTicket_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>典型票名称</th>
	                                <th>典型票类型</th>
	                                <th>单位名称</th>
	                                <th>设置人</th>
	                                <th>设置时间</th>
	                                <th>状态</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
		<div class="col-md-12">
			<div style="margin-right:100px;margin-top: 20px;">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="savedxpBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
    	</div>	
		<script type="text/javascript">
			var data;
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					var searchapproveStatus = new A.combobox({
						render : "#searchapproveStatus",
						datasource : ${typicalTicketApproveStatus},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					//部门控件下拉树
					var searchunitId = new A.combotree({
					render: "#searchunitIdDiv",
					name: 'searchunitId',
					//返回数据待后台返回TODO
					datasource: ${unitNameIdTreeList},
					width:"210px",
					options: {
						treeId: 'searchunitId',
						data: {
							key: {
								name: "name"
							},
							simpleData: {
								enable: true,
								idKey: "id",
								pIdKey: "parentId"
							}
						},
					}
					}).render();
					//监护人
					var searchcreatePersonId = new A.combobox({
						render : "#searchcreatePersonId",
						datasource : ${searchuser},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					var typicalTicketDatatables = new A.datatables({
						render: '#typicalTicket_table',
						options: {
					        "ajax": {
					            "url": format_url("/typicalTicket/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
		        						field: 'C_STATUS',
		        						fieldType:'STRING',
		        						matchType:'EQ',
		        						value:1
		        					});
					            	conditions.push({
		        						field: 'C_TYPE',
		        						fieldType:'STRING',
		        						matchType:'EQ',
		        						value:${type}
		        					});
					            	conditions.push({
		        						field: 'C_APPROVE_STATUS',
		        						fieldType:'STRING',
		        						matchType:'EQ',
		        						value:6
		        					});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : false,
							ordering: true,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false},
							          {data: "name",width: "20%",orderable: true},
							          {data: "typeName",width: "15%",orderable: true},
							          {data: "unitName",width: "15%",orderable: true},
							          {data: "createPersonName",width: "10%",orderable: true},
							          {data: "createDate",width: "15%",orderable: true},
							          {data: "approveStatusName",width: "10%",orderable: true}],
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						if($('#searchname').val()){
        					conditions.push({
        						field: 'C_NAME',
        						fieldType:'STRING',
        						matchType:'LIKE',
        						value:$('#searchname').val()
        					});
						}

						if($('#searchtype').val()){
        					conditions.push({
        						field: 'C_TYPE',
        						fieldType:'STRING',
        						matchType:'LIKE',
        						value:$('#searchtype').val()
        					});
						}
						if(searchunitId.getValue()!=null&&searchunitId.getValue()+""!=""){
	    					conditions.push({
	        					field: 'C_UNIT_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:searchunitId,
	        					type:2,
	        					value:searchunitId.getValue()
	        				});
						}
						if($('#searchcreatePersonId').val()){
        					conditions.push({
        						field: 'C_CREATE_PERSON_Id',
        						fieldType:'STRING',
        						matchType:'LIKE',
        						value:$('#searchcreatePersonId').val()
        					});
						}

						if($('#searchapproveStatus').val()){
        					conditions.push({
        						field: 'C_APPROVE_STATUS',
        						fieldType:'STRING',
        						matchType:'LIKE',
        						value:$('#searchapproveStatus').val()
        					});
						}

						typicalTicketDatatables.draw();
					});
					 $("#savedxpBtn").on("click", function(){
							data = typicalTicketDatatables.getSelectRowDatas();
							if(data==undefined){
								alert("请选择一条记录!");
								return;
							}
							listFormDialog.close(data);
	    				});
					$('#btnReset').on('click',function(){
						$('#searchname').val('')
						searchunitId.setValue(null);
						$("#searchtype").val("");
						$("#searchtype").trigger("chosen:updated");
						$("#searchcreatePersonId").val("");
						$("#searchcreatePersonId").trigger("chosen:updated");
						$("#searchapproveStatus").val("");
						$("#searchapproveStatus").trigger("chosen:updated");
					});
				});
			});
        </script>
    </body>
</html>