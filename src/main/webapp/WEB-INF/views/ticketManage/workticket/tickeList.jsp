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
				<div class="form-inline text-right" role="form">
	            <div class="clearfix">
	                <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                    <label class="" for="searchcode">编号：</label>
	                    <input id="searchcode" class="input-width text-left" placeholder="请输入编号" type="text">
	                </div>
	                <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                    <label class="" for="searchunitNameId">单位名称：</label>
	                    <div id="searchunitNameId" class="input-width text-left padding-zero"></div>
	                </div>
	                <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12">
	                    <label class="" for="searchgroupId">班组：</label>
	                    <div class="input-width padding-zero  text-left">
	                        <select id="searchgroupId" class="form-control chosen-select"></select>
	                    </div>
	                </div>
	                <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12">
	                    <label class="" for="searchguarderName">工作负责人：</label>
	                    <div class="padding-zero input-width  text-left">
						 <select id="searchguarderName"  ></select>
						 </div>
	                    
	                </div>
	
	            </div>
	            <div class="clearfix">
	                <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12">
	                    <label class="" for="searchequipmentName">设备名称：</label>
	                   <!--  <div class="padding-zero input-width  text-left">
		                <div id="equipmentCodeWorkOneDiv" ></div>
						</div> -->
	                    <input id="searchequipmentName" class="input-width text-left" placeholder="请输入设备名称" type="text">
	                </div>
	                <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12">
	                    <label class="" for="searchworkStatus">状态：</label>
	                    <div class="input-width padding-zero  text-left">
	                        <select id="searchworkStatus" class="form-control chosen-select"></select>
	                    </div>
	                </div>
	                <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
	                    <label class="" for="searchcontent">工作内容：</label>
	                    <input id="searchcontent" class="input-width text-left contentInput" placeholder="请输入工作内容" type="text"></input>
	                </div>
	            </div>
	            <div class="clearfix">	
	                <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12">
	                    <label class="" for="searchallowperson">许可人：</label>
	                    <div class="padding-zero input-width  text-left">
	                   	<select id="searchallowperson" class="col-md-12" name="searchallowperson"></select>
						</div>
						 
	                   <!--  <input id="searchallowperson" class="input-width text-left" placeholder="请输入许可人" type="text"> -->
	                </div>
	                <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12  ">
	                    <label class="" for="form-field-1">工作时间：</label>
	                    <div class="form-group date-input padding-zero text-left">
	                        <div id="searchStartplandateEndDiv" style="border:none; padding:0px;"></div>
	                    </div>
	                    <label style="width: 2.6%;text-align: center">~</label>
	                    <div class="form-group date-input padding-zero text-left">
	                        <div id="searchEndplandateEndDiv" style="border:none; padding:0px;"></div>
	                    </div>
	                </div>
	                <div class="form-group col-lg-2 col-md-2 col-sm-12 col-xs-12" style="float:right; ">
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
                        <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom: 0px;">工作票列表</h5>
						<table id="workTicket-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>工作票编号</th>
	                                <th>单位名称</th>
	                                <th>班组</th>
	                                <th>工作负责人</th>
	                                <th>工作内容</th>
	                                <th>设备名称</th>
	                             	 <th>许可人</th>
	                                <th>许可开始工作时间</th>
	                                <th>状态</th>
	                                <th>典型票</th>
	                                <th>鉴定结果</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
		<div style="margin-right:100px;margin-top: 20px;">
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
			var workticketDialog;
			var workTicketDatatables ="";
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					
					var unitNameIdCombotree = new A.combotree({
						render: "#searchunitNameId",
						name: 'unitNameId',
						//返回数据待后台返回TODO
						datasource: ${unitNameIdTreeList},
						width:"210px",
						options: {
							treeId: 'unitNameId',
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
					
					
					var groupIdCombobox = new A.combobox({
						render: "#searchgroupId",
						datasource:${groupIdCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"allow_single_deselect": true,
							"disable_search_threshold":10
						}
					}).render();
					
					//工作负责人
					var searchguarderName = new A.combobox({
						render : "#searchguarderName",
						datasource : ${requestUsers},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					//许可人
					var searchallowperson = new A.combobox({
						render : "#searchallowperson",
						datasource : ${requestUsers},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					
					//设备选择
					/* var selecttreeDh=new A.selectbox({
						id: 'equipmentWorkOneCode',
						name:'equipmentCode',
						title:'设备台账',
						url:'/equipLedger/selectEquipLedger',
						render:'#equipmentCodeWorkOneDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data){
							if(data&&data[0]){
								selecttreeDh.setValue(data[0].code,data[0].code);
								$("#equipmentWorkOneCode").val(data[0].name);
							};
						}
					}).render(); */
					
					//状态下拉框
					var ztTypeCombobox = new A.combobox({
						render: '#searchworkStatus',
						datasource:${statusTypes},
						allowBlank:true,
						options:{
							"allow_single_deselect": true,
							"disable_search_threshold":10
						}
					}).render();
					
					var searchStartplandateStartDiv = new A.my97datepicker({
						id: 'searchStartplandateStartDivId',
						name:'planDateEnd',
						render:'#searchStartplandateStartDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: ""
						}
					}).render();
					
					var searchEndplandateStartDiv = new A.my97datepicker({
						id: 'searchEndplandateStartDivId',
						name:'planDateEnd',
						render:'#searchEndplandateStartDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: ""
						}
					}).render();
					
					var searchStartplandateEndDiv = new A.my97datepicker({
						id: 'searchStartplandateEndDivId',
						name:'changeAllowDate',
						render:'#searchStartplandateEndDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: ""
						}
					}).render();
					
					var searchEndplandateEndDiv = new A.my97datepicker({
						id: 'searchEndplandateEndDivId',
						name:'changeAllowDate',
						render:'#searchEndplandateEndDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: ""
						}
					}).render();
					
					
					 workTicketDatatables = new A.datatables({
						render: '#workTicket-table',
						options: {
					        "ajax": {
					            "url": format_url("/operationTicket/workTicketList"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
		            					field: 'C_WORK_STATUS',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:21//电气第一种工作票
		            				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
							ordering: true,
							multiple : false,
							aLengthMenu: [10],
							optWidth: 280,
							columns: [
							          {data:"id", visible:false,orderable:false},
							          {data: "code",width: "10%",orderable: true}, 
							          {data: "unitName",width: "10%",orderable: true},
							          {data: "groupName",width: "10%",orderable: true}, 
							          {data: "guarderName",width: "10%",orderable: true}, 
							          {data: "content",width: "10%",orderable: true}, 
							          {data: "equipmentName",width: "8%",orderable: true}, 
							          {data: "changeAllowName",width: "8%",orderable: true},
							          {data: "changeAllowDate",width: "12%",orderable: true}, 
							          {data: "workStatusName",width: "5%",orderable: true}, 
							          {data: "isSetName",width: "5%",orderable: true},
							          {data: "identifyName",width: "7%",orderable: true}],
							
					}
					}).render();
					 $("#qxdSaveBtn").on("click", function(){
							data = workTicketDatatables.getSelectRowDatas();
							if(data==undefined){
								alert("请选择一条记录!");
								return;
							}
	 						$(".bootbox-close-button.close").trigger("click");
	    				});
					
					
						
						$('#btnSearch').on('click',function(){
							conditions=[];
							if($("#searchcode").val()!=""){
		    					conditions.push({
		    						field: 't.C_CODE',
		    						fieldType:'STRING',
		    						matchType:'LIKE',
		    						value:$("#searchcode").val()
		    					});
							}
	    					if(unitNameIdCombotree.getValue()!=null
	    							&&unitNameIdCombotree.getValue()!=""){
	    						conditions.push({
	            					field: 't.C_UNIT_NAME_ID',
	            					fieldType:'STRING',
	            					matchType:'EQ',
	            					value:unitNameIdCombotree.getValue()
	            				});
	    					}
	    					if($("#searchgroupId").val()!=""){
	    						conditions.push({
	            					field: 't.C_GROUP_ID',
	            					fieldType:'STRING',
	            					matchType:'EQ',
	            					value:$("#searchgroupId").val()
	            				});
	    					}
	    					
	    					
	    					if($("#searchguarderName").val()!=""){
		    					conditions.push({
		        					field: 't.C_GUARDER_ID',
		        					fieldType:'STRING',
		        					matchType:'EQ',
		        					value:$('#searchguarderName').val()
		        				});
	    					}
	    					if($("#searchcontent").val()!=""){
		    					conditions.push({
		    						field: 't.C_CONTENT',
		    						fieldType:'STRING',
		    						matchType:'LIKE',
		    						value:$('#searchcontent').val()
		    					});
	    					}
	    					
	    					if($("#searchequipmentName").val()!=""){
		    					conditions.push({
		    						field: 't.C_EQUIPMENT_NAME',
		    						fieldType:'STRING',
		    						matchType:'LIKE',
		    						value:$("#searchequipmentName").val()
		    					});
							}
	    					
	    					/* if($("#equipmentWorkOneCode").val()!=null&&$("#equipmentWorkOneCode").val()!=""){
		    					conditions.push({
		        					field: 't.C_EQUIPMENT_NAME',
		        					fieldType:'STRING',
		        					matchType:'LIKE',
		        					value:$('#equipmentWorkOneCode').val()
		        				});
	    					} */
	    					
	    					if($("#searchworkStatus").val()!=""){
		    					conditions.push({
		    						field: 't.C_WORK_STATUS',
		    						fieldType:'STRING',
		    						matchType:'EQ',
		    						value:$('#searchworkStatus').val()
		    					});
	    					}
	    					
	    					if($("#searchallowperson").val()!=""){
		    					conditions.push({
		        					field: 't.C_CHANGE_ALLOW_ID',
		        					fieldType:'STRING',
		        					matchType:'EQ',
		        					value:$('#searchallowperson').val()
		        				});
	    					}
	    					
	    					
	    					if($("#searchStartplandateEndDivId").val()!=""){
		    					conditions.push({
		    						field: 't.C_CHANGE_ALLOW_DATE',
		    						fieldType:'DATE',
		    						matchType:'GE',
		    						value:$("#searchStartplandateEndDivId").val()+" 00:00:00"
		    					});
	    					}
	    					if($("#searchEndplandateEndDivId").val()!=""){
		    					conditions.push({
		    						field: 't.C_CHANGE_ALLOW_DATE',
		    						fieldType:'DATE',
		    						matchType:'LE',
		    						value:$("#searchEndplandateEndDivId").val()+" 23:59:59"
		    					});
	    					}
	    					
							workTicketDatatables.draw();
						});
						
						
						$('#btnReset').on('click',function(){
							conditions=[];
							$("#searchcode").val("");
							unitNameIdCombotree.setValue();
							$("#searchgroupId").val("");
							$("#searchgroupId").trigger("chosen:updated");
							$('#searchguarderName').val('');
							$("#searchguarderName").trigger("chosen:updated");
							
							$('#searchcontent').val('');
							$('#searchequipmentName').val('');
							$('#searchworkStatus').val('');
							$("#searchworkStatus").trigger("chosen:updated");
							
							$('#searchallowperson').val('');
							$("#searchallowperson").trigger("chosen:updated");
							
							$("#searchStartplandateEndDivId").val("");
							$("#searchEndplandateEndDivId").val("");
							processUserUnitRels = [];
							//workTicketDatatables.draw(true);
						});
						
					});
				});
			
			function goBackToSubmitPerson(id,selectUser){//回调函数
				
				var url =format_url("/workElectric/submit?workId="+id+"&selectUser="+selectUser);
				$.ajax({
					contentType: "application/json",
					dataType:"JSON",
					url : url,
					success: function(result){
						if(result.result=="success"){
							alert('操作成功');
							workTicketDatatables.draw(false);
						}else{
							alert(result.errorMsg);
							workTicketDatatables.draw(false);
						}
					},
					error:function(v,n){
						alert('操作失败');
						workTicketDatatables.draw(false);
					}
				});
				
			}
        </script>
    </body>
</html>