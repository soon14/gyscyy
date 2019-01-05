<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
				<div class="col-lg-12 col-md-12 col-sm-12 search-content">
				<div class="form-inline text-right" role="form">
	            <div class="clearfix" style="width: 95%">
	                <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                    <label class="" for="searchcode">工作票编号：</label>
	                    <input id="searchcode" class="input-width text-left" placeholder="请输入工作票编号" type="text">
	                </div>
	                <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                    <label class="" for="searchunitNameId">单位名称：</label>
	                    <div id="searchunitNameId" class="input-width text-left padding-zero"></div>
	                </div>
	                <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                    <label class="" for="searchgroupId">班组：</label>
	                    <div class="input-width padding-zero  text-left">
	                        <select id="searchgroupId" class="form-control chosen-select"></select>
	                    </div>
	                </div>
	                <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                    <label class="" for="searchguarderName">工作负责人：</label>
	                    <div class="padding-zero input-width  text-left">
						 <select id="searchguarderName"  ></select>
						 </div>
	                </div>
	
	            </div>
	            <div class="clearfix" style="width: 95%">
	                  <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                    <label class="" for="searchequipmentName">设备名称：</label>
	                    <div class="padding-zero input-width  text-left">
		                <div id="equipmentCodeWorkOneDiv" ></div>
						</div>
	                </div>
	                <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                    <label class="" for="searchworkStatus">状态：</label>
	                    <div class="input-width padding-zero  text-left">
	                        <select id="searchworkStatus" class="form-control chosen-select"></select>
	                    </div>
	                </div>
	                <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
	                    <label class="" for="searchcontent">工作内容：</label>
	                    <input id="searchcontent" class="input-width text-left contentInput" placeholder="请输入工作内容" type="text" ></input>
	                </div>
	            </div>
	            <div class="clearfix" style="width: 95%">	
	                <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12" >
	                  <label class="" for="searchallowperson">许可人：</label>
	                    <div class="padding-zero input-width  text-left">
	                   	<select id="searchallowperson" class="col-md-12" name="searchallowperson"></select>
						</div>
	                </div>
	                <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12" >
	                    <label class="" for="searchStartplandateEndDiv">许可时间：</label>
	                    <div class="form-group date-input padding-zero text-left">
	                        <div id="searchStartplandateEndDiv"></div>
	                    </div>
	                    <label style="width: 2.6%;text-align: center">~</label>
	                    <div class="form-group date-input padding-zero text-left">
	                        <div id="searchEndplandateEndDiv"></div>
	                    </div>
	                </div>
	                <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12" style="float:right; margin-right:30px;">
	                    <button id="btnSearchForWorkTicket" class="btn btn-xs btn-primary">
	                        <i class="glyphicon glyphicon-search"></i>
	                        查询
	                    </button>
	                    <button id="btnResetForWorkTicket" class="btn btn-xs btn-primary">
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
                       <h5 class="table-title header smaller blue" style="margin-bottom:0px!important;">工作票</h5>
						<table id="workTicket-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>工作票编号</th>
	                                <th>工作票类型</th>
	                                <th>单位名称</th>
	                                <th>班组</th>
	                                <th>工作负责人</th>
	                                <th>许可人</th>
	                                <th>工作内容</th>
	                                <th>设备名称</th>
	                                <th>许可时间</th>
	                                <th>状态</th>
	                                <th>典型票</th>
	                                <th>鉴定结果</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var userUnitRels = ${userUnitRels};
			//初始数据备份
			var processUserUnitRels = ${userUnitRels};
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker','selectbox'], function(A){
					var conditions=[];
					var workticketDialog;
					var workTicketDatatables ="";
					var equipIdJson = JSON.parse('${equipIdJson}');
					var equipIdArray = new Array();
					var equipIdString = "";
					for(i in equipIdJson){
						equipIdArray.push(equipIdJson[i].code);
					}
					if(equipIdArray.length>0){
						equipIdString = equipIdArray.join(",");
					}
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
					var selecttreeDh=new A.selectbox({
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
					}).render();
					
					
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
					
					
					var searchStartplandateEndDiv = new A.my97datepicker({
						id: 'searchStartplandateEndDivId',
						name:'changeAllowDate',
						width:"10",
						render:'#searchStartplandateEndDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
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
								dateFmt: "yyyy-MM-dd HH:mm:ss",
								minDate: "#F{$dp.$D(\\'searchStartplandateEndDivId\\')}",
						}
					}).render();
					
					
					 workTicketDatatables = new A.datatables({
						render: '#workTicket-table',
						options: {
					        "ajax": {
					            "url": format_url("/equiptree/workTicketListWithEquip"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
		            					field: 'C_EQUIP_CODE',
		            					fieldType:'STRING',
		            					matchType:'IN',
		            					value:equipIdString
		            				});
// 					            	conditions.push({
// 		            					field: 'C_STATUS',
// 		            					fieldType:'INT',
// 		            					matchType:'IN',
// 		            					value:1
// 		            				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							aLengthMenu: [20],
							optWidth: 280,
							columns: [
							          {data:"id", visible:false,orderable:false},
							          {data: "code",width: "190px",orderable: true}, 
							          {data: "typeName", name:"type",width: "120px",orderable: true}, 
							          {data: "unitName",width: "150px",orderable: true},
							          {data: "groupName",width: "100px",orderable: true}, 
							          {data: "guarderName",width: "120px",orderable: true}, 
							          {data: "changeAllowName",width: "100px",orderable: true},
							          {data: "content", width: "300px",orderable: true}, 
							          {data: "equipName",width: "150px",orderable: true}, 
							          {data: "changeAllowDate",width: "150px",orderable: true}, 
							          {data: "workStatusName",name:"workStatus",width: "150px",orderable: true}, 
							          {data: "isSetName",name:"isSet",width: "100px",orderable: true},
							          {data: "identifyName",name:"identify",width: "100px",orderable: true}]
					        }
					}).render();
					
					$('#btnSearchForWorkTicket').on('click',function(){
						conditions=[];
						if($("#searchcode").val()!=""){
	    					conditions.push({
	    						field: 't.C_CODE',
	    						fieldType:'STRING',
	    						matchType:'LIKE',
	    						value:$("#searchcode").val()
	    					});
						}
    					if(unitNameIdCombotree.getValue()!=null&&String(unitNameIdCombotree.getValue())!=""){
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
    					
    					if($("#equipmentWorkOneCode").val()!=null&&$("#equipmentWorkOneCode").val()!=""){
	    					conditions.push({
	        					field: 't.C_EQUIPMENT_NAME',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$('#equipmentWorkOneCode').val()
	        				});
    					}
    					
    					if($("#searchworkStatus").val()!=""){
	    					conditions.push({
	    						field: 't.C_WORK_STATUS',
	    						fieldType:'STRING',
	    						matchType:'LIKE',
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
	    						value:$("#searchStartplandateEndDivId").val()
	    					});
    					}
    					if($("#searchEndplandateEndDivId").val()!=""){
    						conditions.push({
	    						field: 't.C_CHANGE_ALLOW_DATE',
	    						fieldType:'DATE',
	    						matchType:'LE',
	    						value:$("#searchEndplandateEndDivId").val()
	    					});
    					}
    					
						workTicketDatatables.draw();
					});
					
					
					$('#btnResetForWorkTicket').on('click',function(){
						conditions=[];
						$("#searchcode").val("");
						unitNameIdCombotree.setValue(undefined);
						$("#searchgroupId").val("");
						$("#searchgroupId").trigger("chosen:updated");
						$('#searchguarderName').val('');
						$("#searchguarderName").trigger("chosen:updated");
						$('#searchcontent').val('');
						$('#equipmentWorkOneCode').val('');
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