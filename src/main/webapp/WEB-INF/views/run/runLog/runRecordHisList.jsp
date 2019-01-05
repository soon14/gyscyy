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
	<div class="col-xs-12 search-content">
				<div class="form-inline text-right" role="form">
				   <div class="clearfix">
				   	  <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
						<label class="" for="form-field-1">单位名称：</label>
					  		 <div id="hissearchunitIdDiv"  class="input-width text-left padding-zero"></div>
					   </div>
	                    <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
							<label class="" for="form-field-1">记录时间：</label>
							 <div class="form-group date-input padding-zero text-left">
                                <div id="hissearchStartfindTimeDiv"  style="border:none; padding:0px;"></div>
                            </div>
                            <label style="width: 2.6%;text-align: center">~</label>
                            <div class="form-group date-input padding-zero text-left">
                                <div id="hissearchEndfindTimeDiv"   style="border:none; padding:0px;"></div>
                            </div>
					   </div>
	                   <div class="form-group col-lg-3 col-md-3s col-sm-6 col-xs-12">
							<label class="" for="form-field-1">记录内容：</label>
	                            <input id="hisrecordContent" class="input-width  text-left" placeholder="请输入记录内容" type="text" >
	                   </div>
                   </div>
                  <div class="clearfix">
                  	   <div class="form-group col-lg-3 col-md-3s col-sm-6 col-xs-12">
							<label class="" for="form-field-1">记录类型：</label>
							 <div class="form-groupp adding-zero input-width  text-left">
					    	<select id="hisselprotectWayDiv" class="col-md-12" name="protectWay"></select>
					    	</div>					    					    				    
	                   </div>
                  </div>
                   <div class="form-group" style="float:right; margin-right:30px;">
						<button id="hisbtnSearch" class="btn btn-xs btn-primary">
							<i class="glyphicon glyphicon-search"></i>
							查询
						</button>
						<button id="hisbtnReset" class="btn btn-xs btn-primary">
							<i class="glyphicon glyphicon-repeat"></i>
							重置
						</button>								
					</div>
             </div>
           </div>
                <div class="row">
                	<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
        			 <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;"></h5>
						<table id="hisrunRecord-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>	
									<th>序号</th>								
	                                <th>单位名称</th>
	                                <th>记录类型</th>
	                                <th>记录时间</th>
	                                <th>记录内容</th>
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
								$('#tableTitle').html('<h5 class="table-title header smaller blue" >运行记事表</h5>');								
								//部门控件下拉树
								var hissearchunitId = new A.combotree({
								render: "#hissearchunitIdDiv",
								name: 'hissearchunitId',
								//返回数据待后台返回TODO
								datasource: ${runRecordTreeList},
								width:"210px",
								options: {
									treeId: 'hissearchunitId',
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
								var protectWayCombobox = new A.combobox({
									render: "#hisselprotectWayDiv",
									datasource:${recordTypeCombobox},
									//multiple为true时select可以多选
									multiple:false,
									//allowBlank为false表示不允许为空
									allowBlank: true,
									options:{
										"disable_search_threshold":10,
										"width":"100%"	
									}
									}).render();	
								var searchStartfindTime = new A.my97datepicker({
									id: 'hissearchStartfindTime',
									name:'hissearchStartfindTime',
									render:'#hissearchStartfindTimeDiv',
									options : {
											isShowWeek : false,
											skin : 'ext',
											maxDate : "#F{$dp.$D(\\'hissearchEndfindTime\\')}",
											minDate : "",
											maxDate: "",
											minDate: "",
											dateFmt: "yyyy-MM-dd HH:mm"
									}
								}).render();
								var searchEndfindTime = new A.my97datepicker({
									id: 'hissearchEndfindTime',
									name:'hissearchEndfindTime',
									render:'#hissearchEndfindTimeDiv',
									options : {
											isShowWeek : false,
											skin : 'ext',
											maxDate : "",
											minDate : "#F{$dp.$D(\\'hissearchStartfindTime\\')}",
											dateFmt: "yyyy-MM-dd HH:mm"
									}
								}).render();
								
					var hisrunRecordDatatables = new A.datatables({
						render: '#hisrunRecord-table',
						options: {
					        "ajax": {
					            "url": format_url("/runRecord/search"),
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
							checked:false,
							bInfo:false,
							paging:true,
							 order: [[ 4, "desc" ]],
							columns: [{data:"id", visible:false,orderable:false}, 
							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "unitName",width: "10%",orderable: true}, 
							          {data: "recordTypeName",width: "10%",orderable: true},
							          {data: "recordTime",width: "10%",orderable: true}, 
							          {data: "recordContent",width: "auto",orderable: true}],

						}
					}).render();
					$('#hisbtnSearch').on('click',function(){
						conditions=[];			
						if($('#hissearchStartfindTime').val()){
	    					conditions.push({
	    						field: 'a.C_RECORD_TIME',
	    						fieldType:'DATE',
	    						matchType:'GE',
	    						value:$('#hissearchStartfindTime').val()
	    					});
						}
						if($('#hissearchEndfindTime').val()){
	    					conditions.push({
	    						field: 'a.C_RECORD_TIME',
	    						fieldType:'DATE',
	    						matchType:'LE',
	    						value:$('#hissearchEndfindTime').val()
	    					});
						}
						if($('#hisselprotectWayDiv').val()){
	    					conditions.push({
	        					field: 'a.C_RECORD_TYPE',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#hisselprotectWayDiv').val()
	        				});
						}
						if(hissearchunitId.getValue()!=undefined&&hissearchunitId.getValue()+""!=""){
	    					conditions.push({
	        					field: 'a.C_UNIT_ID',
	        					fieldType:'INT',
	        					matchType:'EQ',
	        					value:hissearchunitId.getValue()
	        				});
						}
						if($('#hisrecordContent').val()){
	    					conditions.push({
	        					field: 'a.C_RECORD_CONTENT',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$('#hisrecordContent').val()
	        				});
						}
						hisrunRecordDatatables.draw();
					});
					$('#hisbtnReset').on('click',function(){
						hissearchunitId.setValue(undefined);
						$("#hisselprotectWayDiv").val("");
						$("#hisselprotectWayDiv").trigger("chosen:updated");
						$("#hissearchStartfindTime").val("");
						$("#hissearchEndfindTime").val("");
						$("#hisrecordContent").val("");
					});
				});
			});
        </script>
    </body>
</html>