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
				<li>用户操作日志</li>
				<li>用户操作日志</li>
			</ul>
		</div>
		<div class="page-content">
			<div class="col-lg-12 col-md-12 col-sm-12 search-content  padding-zero">
				<div class="form-inline text-left" role="form">
				<div class="clearfix">
				 <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
	                   <label class="searchLabel " for="form-field-1">操作人</label>：
	                    <div class="padding-zero inputWidth  text-left">
                                <select id="searchOperateUser" class="" name="searchtype"></select>
                            </div>
	                </div>
	                <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12  padding-zero">
	                    <label class="searchLabel " for="form-field-1">操作时间</label>：
	                    <div class="form-group dateInputOther padding-zero text-left">
	                        <div id="searchCreateDateStartDiv" style="border:none; padding:0px;"></div>
	                    </div>
	                    <div class="toLabel" for="searchStartTimeRightDiv" >~</div>
	                    <div class="form-group dateInputOther padding-zero text-left">
	                        <div id="searchCreateDateEndDiv" style="border:none; padding:0px;"></div>
	                    </div>
	                </div>
	                <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" >
                            <label class="searchLabel" for="searchappraisalUserId">操作模块</label>：
                            <div class="padding-zero inputWidth  text-left">
                                <select id="searchOperateUserModule" class="" name="searchtype"></select>
                            </div>
                        </div>
	            </div>
                   <div class="clearfix" >
                   
                       <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" >
                            <label class="searchLabel" for="searchappraisalUserId">操作类型</label>：
                            <div class="padding-zero inputWidth  text-left">
                                <select id="searchOperateType" class="" ></select>
                            </div>
                        </div>
						<div class="form-group col-lg-9 col-md-9 col-sm-6 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; ">
                            <button id="btnSearchAccident" class="btn btn-xs btn-primary">
                                <i class="glyphicon glyphicon-search"></i>
                           		     查询
                            </button>
                            <button id="btnResetAccident" class="btn btn-xs btn-primary" >
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
                       <h5 class="table-title-withoutbtn header smaller blue " style='margin-bottom:0px;' >用户操作日志</h5>
						<table id="accidentMeasuresPlan_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
	                                <th>操作时间</th>
	                                <th>操作人</th>
	                                <th>操作模块</th>
	                                <th>操作类型</th>
<!-- 	                                <th>操作内容</th> -->
	                                <th>IP地址</th>
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
			var exportExcelAccident="";
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					var searchOperateUser = new A.combobox({
						render : "#searchOperateUser",
						datasource : ${searchuser},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					var searchOperateUserModule = new A.combobox({
						render : "#searchOperateUserModule",
						datasource : ${operateUserEnumCombobox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					//操作类型
					var searchOperatetype = new A.combobox({
						render : "#searchOperateType",
						datasource : ${operateTypeCombobox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					
					var searchCreateDateStart = new A.my97datepicker({
						id: 'searchCreateDateStart',
						name:'searchCreateDateStart',
						render:'#searchCreateDateStartDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					var searchCreateDateEnd = new A.my97datepicker({
						id: 'searchCreateDateEnd',
						name:'searchCreateDateEnd',
						render:'#searchCreateDateEndDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'searchCreateDateStart\\')}",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					var accidentMeasuresPlanDatatables = new A.datatables({
						render: '#accidentMeasuresPlan_table',
						options: {
					        "ajax": {
					            "url": format_url("/operateLog/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.conditions = conditions;
					            	dd=d;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "createDate",width: "auto",orderable: true}, 
							          {data: "userName",width: "auto",orderable: true}, 
							          {data: "moduleName",width: "auto",orderable: true}, 
// 							          {data: "operateUserName",name:"operateUser",width: "auto",orderable: true}, 
							          {data: "operateDesc",name:"planDate",width: "auto",orderable: true}, 
							          {data: "ipAddress",name:"unitId",width: "auto",orderable: true}, 
							          ],
							toolbars: [],
						}
					}).render();
					$('#btnSearchAccident').on('click',function(){
						conditions=[];
						if($('#searchOperateUser').val()){
	    					conditions.push({
	        					field: 'C_USER_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#searchOperateUser').val()
	        				});
						}
						if($('#searchOperateUserModule').val()){
	    					conditions.push({
	        					field: 'C_MODULE_NAME',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#searchOperateUserModule').val()
	        				});
						}
						if($('#searchOperateType').val()){
	    					conditions.push({
	        					field: 'C_OPERATE_TYPE',
	        					fieldType:'INT',
	        					matchType:'EQ',
	        					value:$('#searchOperateType').val()
	        				});
						}
						
						if($('#searchCreateDateStart').val()){
	    					conditions.push({
	    						field: 'C_CREATE_DATE',
	    						fieldType:'DATE',
	    						matchType:'GE',
	    						value:$('#searchCreateDateStart').val()
	    					});
						}
						if($('#searchCreateDateEnd').val()){
	    					conditions.push({
	    						field: 'C_CREATE_DATE',
	    						fieldType:'DATE',
	    						matchType:'LE',
	    						value:$('#searchCreateDateEnd').val()
	    					});
						}
						accidentMeasuresPlanDatatables.draw();
					});
					$('#btnResetAccident').on('click',function(){
						$("#searchOperateUser").val("");
						$("#searchOperateUser").trigger("chosen:updated");
						$("#searchOperateUserModule").val("");
						$("#searchOperateUserModule").trigger("chosen:updated");
						$("#searchOperateType").val("");
						$("#searchOperateType").trigger("chosen:updated");
						$("#searchCreateDateStart").val("");
						$("#searchCreateDateEnd").val("");
					});
				});
			});
        </script>
    </body>
</html>