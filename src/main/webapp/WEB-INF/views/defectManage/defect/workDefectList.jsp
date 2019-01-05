<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html lang="zh">
<head>
	<meta charset="UTF-8">
	<%@ include file="/WEB-INF/views/common/meta.jsp" %>
</head>
<body>
		<div class="page-content">
			<div class="col-xs-12 search-content">
				<div class="form-inline" >
		           <div class="form-group" >
						<label class="" for="form-field-1">缺陷编码：</label>
				   </div>
				   <div class="form-group">
		                <input id="searchcode" class="form-control" placeholder="请输入缺陷编码" type="text" >
                   </div>
		           
		           <div class="form-group">
						<label class="" for="form-field-1">设备名称：</label>
				   </div>
				   <div class="form-group">
				  		<input id="searchequipName" class="form-control" placeholder="请输入设备名称" type="text" >
                   </div>
                   <div class="form-group" style="float:right; ">
                   		<button id="btnSearchQxd" class="btn btn-xs btn-primary">
							<i class="glyphicon glyphicon-search"></i>
							查询
						</button>
						<button id="btnResetQxd" class="btn btn-xs btn-primary">
							<i class="glyphicon glyphicon-repeat"></i>
							重置
			 			</button>	
                   </div>
				 </div>
            </div>
			<div class="row">
				<div class="col-xs-12">
					<div class="widget-main no-padding" id="defect-print">
                        <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom: 0px;">缺陷管理</h5>
						<table id="defect-table-tck" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>缺陷编码</th>
	                                <th>单位名称</th>
	                                <th>设备名称</th>
	                                <th>发现人</th>
	                                <th>发现时间</th>
	                                <th>缺陷类型</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div> 
		<div style="margin-right:100px;">
		        		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal" >
							<i class="ace-icon fa fa-times"></i>
							取消
						</button>
		    			<button id="qxdSaveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				确定
		    			</button>
		 </div>
		<script type="text/javascript">
		    var data=[];
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog'], function(A){
					var defectDatatables="";
					var conditions=[];
					 defectDatatables = new A.datatables({
						render: '#defect-table-tck',
						options: {
					        "ajax": {
					            "url": format_url("/defect/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : false,
							ordering: true,
							optWidth: 80,
 							aLengthMenu: [10],
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "code",width: "15%",orderable: true}, 
							          {data: "unitName",width: "15%",orderable: true}, 
							          {data: "equipName",width: "15%",orderable: true}, 
							          {data: "findUserName",width: "15%",orderable: true}, 
							          {data: "findTime",width: "15%",orderable: true}, 
							          {data: "typeName",width: "15%",orderable: true},],
							          fnInfoCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											//设置选中
											$("#defect-table-tck").find('td input[type=checkbox]').each(function(i){
												var row = $(this).closest('tr').get(0);
												var data=defectDatatables._datatables.row(row).data();
												for(var j=0;j<defectIds.length;j++){
													if(data.id==defectIds[j].id){
														defectDatatables._datatables.row(row).select();
													};
												}
											});
										} 
							
						}
					}).render();
					
					$("#qxdSaveBtn").on("click", function(){
						data = defectDatatables.getSelectRowDatas();
						if(data==undefined){
							alert("请选择一条记录!");
							return;
						}
						defectIds=[];
						defectIds.push(data);
 						$(".bootbox-close-button.close").trigger("click");
    				});
					
					
					$('#btnSearchQxd').on('click',function(){
						conditions=[];
						if($('#searchcode').val()){
	    					conditions.push({
	        					field: 'T.C_CODE',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					name:'searchcode',
	        					type:1,
	        					value:$('#searchcode').val()
	        				});
						}
						
						if($('#searchequipName').val()){
	    					conditions.push({
	        					field: 'T.C_EQUIP_NAME',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					name:searchequipName,
	        					type:3,
	        					value:$('#searchequipName').val()
	        				});
						}
						
						defectDatatables.draw();
					});
					$('#btnResetQxd').on('click',function(){
						conditions=[];
						$("#searchcode").val("");
						$("#searchequipName").val("");
						defectDatatables.draw();
					});
				});
			});
        </script>
    </body>
</html>