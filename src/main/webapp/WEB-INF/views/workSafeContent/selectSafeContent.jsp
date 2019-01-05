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
		   <div class="col-lg-12 col-md-12 col-sm-12 padding-zero search-content">
				<div class="form-inline text-left " role="form">
				 <div class="clearfix">
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" >
                            <label class="searchLabel" for="searchName">安措内容</label>：
                             <input id="searchName" class="input-width text-left"  placeholder="请输入安措内容" type="text"></input>
                        </div>
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="unitId" >单位名称</label>：
						    <div class="padding-zero inputWidth  text-left">
								<select id="searchunitIdDiv" class="form-control chosen-select"></select>
		                    </div>
                        </div>
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                        </div>
                         <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero btnSearchBottom"style="text-align:right;" >
                            <button id="btnSearch" class="btn btn-xs btn-primary" >
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
						<h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;" >安全措施列表</h5>
						<table id="workSafeContent_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
	                                <th>安措内容</th>
	                                <th>时间</th>
	                                <th>填写人</th>
	                                <th>单位名称</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        
        <div style="margin-right:100px;margin-top:20px;">
       		<button id="cancel" class="btn btn-xs btn-danger pull-right" data-dismiss="modal" >
				<i  class="ace-icon fa fa-times"></i>
				取消
			</button>
   			<button id="selectBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
   				<i class="ace-icon fa fa-floppy-o"></i>
   				确定
   			</button>
   		</div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var listFormDialog;
			var resule=[];
			var safeInfoArray = [];
			var userUnitRels1=[];
			
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					
					//单位名称
					var unitIdCombobox = new A.combobox({
						render: "#searchunitIdDiv",
						//返回数据待后台返回TODO
						datasource:${unitList},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
			
					var existDataId = ${existDataId};
					var workSafeContentDatatables = new A.datatables({
						render: '#workSafeContent_table',
						options: {
					        "ajax": {
					            "url": format_url("/workSafeContent/searchList?existDataId="+existDataId),
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
							searching: true,
							bStateSave: true,
							optWidth: 80,
							columns: [
							          {data:"id", visible:false,orderable:false}, 
							          {orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "content",width: "0%",orderable: true}, 
							          {data: "createDateString",name:"createDate",width: "0%",orderable: true},
							          {data: "userName",name:"createUserId",width: "0%",orderable: true},
							          {data: "unitName",name:"unitId",width: "0%",orderable: true}
							          ],
							          
							      	fnInfoCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
										//设置选中
										$("#workSafeContent_table").find('td input[type=checkbox]').each(function(i){
											var row = $(this).closest('tr').get(0);
											var data=workSafeContentDatatables._datatables.row(row).data();
											for(var j=0;j<userUnitRels1.length;j++){
												if(data.id==userUnitRels1[j].id){
													workSafeContentDatatables._datatables.row(row).select();
												};
											}
										});
									}
						}
					}).render();
					
					//设置选中事件
					$(workSafeContentDatatables._config.render).on('click', 'td input[type=checkbox]' , function(i){
						var row = $(this).closest('tr').get(0);
						var checked = $(this).is(":checked");
						var data=workSafeContentDatatables._datatables.row(row).data();
						if(checked) {
							userUnitRels1.push(data.id);
						}else{
							userUnitRels1.remove(data.id);
						} 
					});
					
					
					//设置全选事件
					$( '#workSafeContent_table > thead > tr > th input[type=checkbox], #workSafeContent_table_wrapper input[type=checkbox]').eq(0).on('click', function(){
							var th_checked = this.checked;
							$('#workSafeContent_table').find('tbody > tr').each(function(){
								var flag=true;
								var row = this;
								var data=workSafeContentDatatables._datatables.row(row).data();
								if(th_checked){
									for (var int = 0; int < userUnitRels1.length; int++) {
										if(userUnitRels1[int]==data.id){
											flag=false;
										}
									}
									if(flag){
										userUnitRels1.push(data.id);
									}
								}else{
									for (var int = 0; int < userUnitRels1.length; int++) {
										if(userUnitRels1[int]==data.id){
											flag=false;
										}
									}
									if(!flag){
										userUnitRels1.remove(data.id);
									}
								}  
							});
						});
				
					
					$("#selectBtn").on("click", function(){
						resule = workSafeContentDatatables.getSelectRowDatas();
						var ids = [];
						debugger;
						if(resule.length && resule.length>0){
							for(var i =0; i<resule.length; i++){
								ids.push(resule[i].id);
								var map = {};
								map.id =resule[i].id; 
								map.content = resule[i].content;
								map.unitId = resule[i].unitId;
								safeInfoArray.push(map);
							}
						}
						if(ids.length < 1){
							alert('请选择安全措施');
							resule=[];
							return;
						}
						workSafeOneDialog.close(resule);
    				});
					
					$('#btnSearch').on('click',function(){
						conditions=[];
				
						if($("#searchName").val()!=""){
							conditions.push({
								field: 'a.C_CONTENT',
								fieldType:'STRING',
								matchType:'LIKE',
								value:$("#searchName").val().trim()
							});
						}
						
						if($("#searchunitIdDiv").val()!=""){
	    					conditions.push({
	        					field: 'a.C_UNIT_ID',
	        					fieldType:'INT',
	        					matchType:'EQ',
	        					value:$('#searchunitIdDiv').val()
	        				});
    					}
						workSafeContentDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$("#searchName").val('');
						$("#searchunitIdDiv").val('');
						$("#searchunitIdDiv").trigger("chosen:updated");
					});
				});
			});
        </script>
    </body>
</html>