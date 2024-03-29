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
				<li class="active">两票管理</li>
					<li class="active">工作票管理</li>
				<li class="active">电力线路第二种工作票</li>
			</ul>
		</div>
		<div class="page-content">
			<div class="col-lg-12 col-md-12 col-sm-12 search-content  padding-zero">
				<div class="form-inline text-left" role="form">
	            <div class="clearfix">
	                <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
	                    <label class="searchLabel" for="searchcode">编号</label>：
	                    <input id="searchcode" class="inputWidth text-left" placeholder="请输入编号" type="text">
	                </div>
	                <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
	                    <label class="searchLabel" for="searchunitNameId">单位名称</label>：
	                    <div id="searchunitNameId" class="inputWidth text-left padding-zero"></div>
	                </div>
	                <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12 padding-zero">
	                    <label class="searchLabel" for="searchgroupId">班组</label>：
	                    <div class="inputWidth padding-zero  text-left">
	                        <select id="searchgroupId" class="form-control chosen-select"></select>
	                    </div>
	                </div>
	                <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12 padding-zero">
	                    <label class="" for="searchguarderName">工作负责人</label>：
	                    <div class="padding-zero inputWidth  text-left">
						 <select id="searchguarderName"  ></select>
						 </div>
	                    
	                </div>
	
	            </div>
	            <div class="clearfix">
	                <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12 padding-zero">
	                    <label class="searchLabel" for="searchequipmentName">设备名称</label>：
	                    <div class="padding-zero inputWidth  text-left">
		                <div id="equipmentCodeWorkOneDiv" ></div>
						</div>
	                </div>
	                <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12 padding-zero">
	                    <label class="searchLabel" for="searchworkStatus">状态</label>：
	                    <div class="inputWidth padding-zero  text-left">
	                        <select id="searchworkStatus" class="form-control chosen-select"></select>
	                    </div>
	                </div>
	                <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero">
<!-- 	                    <label class="searchLabel" for="searchcontent">工作内容</label>： -->
           				<label class="" for="searchcontent">工作内容</label>：
	                    <input id="searchcontent" class="inputWidth text-left contentInput" placeholder="请输入工作内容" type="text"></input>
	                </div>
	            </div>
	            <div class="clearfix">	
	                <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12 padding-zero">
	                    <label class="searchLabel" for="searchallowperson">许可人</label>：
	                    <div class="padding-zero inputWidth  text-left">
	                   	<select id="searchallowperson" class="col-md-12" name="searchallowperson"></select>
						</div>
	                </div>
	                <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero ">
	                    <label class="searchLabel" for="form-field-1">许可时间</label>：
	                    <div class="form-group dateInputOther padding-zero text-left">
	                        <div id="searchStartplandateEndDiv" style="border:none; padding:0px;"></div>
	                    </div>
	                    <div class="toLabel" for="searchStartTimeRightDiv" >~</div>
	                    <div class="form-group dateInputOther padding-zero text-left">
	                        <div id="searchEndplandateEndDiv" style="border:none; padding:0px;"></div>
	                    </div>
	                </div>
	                <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; ">
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
                       <h5 class="table-title header smaller blue" >电力线路第二种工作票列表</h5>
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
	                                <th>许可时间</th>
	                                <th>状态</th>
	                                <!-- <th>典型票</th> -->
	                                <th>鉴定结果</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var workticketDialog;
			var workTicketDatatables ="";
			var userUnitRels = ${userUnitRels};
			//初始数据备份
			var processUserUnitRels = ${userUnitRels};
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker','selectbox','uploaddropzone'], function(A){
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
								maxDate: "#F{$dp.$D(\\'searchEndplandateEndDivId\\')}",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
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
								minDate: "#F{$dp.$D(\\'searchStartplandateEndDivId\\')}",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					
					var exportExcel="";
					 workTicketDatatables = new A.datatables({
						render: '#workTicket-table',
						options: {
					        "ajax": {
					            "url": format_url("/workTicketLineTwo/searchData"),
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
							optWidth: 280,
							columns: [
							          {data:"id", visible:false,orderable:false},
							          {data: "code",width: "9%",orderable: true}, 
							          {data: "unitName",width: "13%",orderable: true},
							          {data: "groupName",width: "11%",orderable: true}, 
							          {data: "guarderName",width: "8%",orderable: true}, 
							          {data: "content",width: "13%",orderable: true}, 
							          {data: "equipmentName",width: "10%",orderable: true,
							        	  render : function(data, type, row, meta) {
												if(data!=null && data.length>20){
													return "<div title='"+data+"'>"+data.substring(0,20)+"..."+"</div>";  
												}else{
													return data;  
												}
									  		}}, 
							          {data: "changeAllowName",width: "8%",orderable: true},
							          {data: "changeAllowDate",width: "8%",orderable: true}, 
							          {data: "workStatusName",name:"workStatus",width: "10%",orderable: true}, 
							          /* {data: "isSetName",name:"isSet",width: "5%",orderable: true}, */
							          {data: "identifyName",name:"identify",width: "6%",orderable: true}
							          ],
							          
										fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											 if(exportExcel){
												 exportExcels(format_url("/workTicketLineTwo/exportExcel"),JSON.stringify(conditions));
											 }
											 exportExcel="";
										 },
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
        							click: function(event, nRow, nData){
										//zzq修改开始
										$.ajax({
											url: format_url('/workTicket/addValidate'),
											contentType : 'application/json',
											dataType : 'JSON',
											type: 'POST',
											data : JSON.stringify(nData),
											success: function(result){
												if(result.result == 'success'){
													A.loadPage({
														render : '#page-container',
														url : format_url("/workTicketLineTwo/getAdd")
													});
            									} else{
            										alert(result.errorMsg);
            									}
											}
										});
										//zzq修改结束
//         								A.loadPage({
// 											render : '#page-container',
// 											url : format_url("/workTicketLineTwo/getAdd")
// 										});
									}
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = workTicketDatatables.getSelectRowDatas();
										var ids = [];
										var guarderIds = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
												guarderIds.push(data[i].guarderId);
											}
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										var loginUser = '${userEntity.id}';
										for(var j=0;j<guarderIds.length;j++){
											if(guarderIds[j]!=loginUser){
												alert('记录中包含不是当前登陆人的记录不能删除!');
												return;
											}
										}
										var url = format_url('/workTicketLineTwo/bulkDelete/'+ids);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													if(result.result == 'success'){
														alert('删除成功');
														workTicketDatatables.draw(false);
	            									}else{
	            										alert(result.errorMsg);
	            									} 
												},
												error:function(v,n){
													alert('操作失败');
												}
											});
										});
									}
								}
							}
        					,{  
        						id:"dc",
        						label:"导出",
        						icon:"glyphicon glyphicon-download",
        						className:"btn-primary",
        						events:{
        							click:function(event){
            							$('#btnSearch').click();
            							exportExcel="exportExcel";
            						}
        						}
        					}
							],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green",
								render: function(btnNode, data){
									if(data.workStatus!="1"&&data.workStatus!="7"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var workPerson = nData.guarderId;
										var loginUser = '${userEntity.id}';
			 							if(workPerson!=loginUser){
			 								alert("当前登陆人不是工作负责人不能修改!");
			 								return;
			 							}else{
										var id = nData.id;
										$.ajax({
											url: format_url('/workTicketLineTwo/updateValidate/'+id),
											contentType : 'application/json',
											dataType : 'JSON',
											type: 'POST',
											data : JSON.stringify(nData),
											success: function(result){
												if(result.result == 'success'){
													A.loadPage({
														render : '#page-container',
														url:format_url("/workTicketLineTwo/getEdit/"+ id),
													});
            									} else{
            										alert(result.errorMsg);
            									}
											}
										});
			 						}
									}
								}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								render: function(btnNode, data){
									if(data.workStatus!="1"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var workPerson = nData.guarderId;
										var loginUser = '${userEntity.id}';
										console.log(workPerson);
					                    console.log(loginUser);
			 							if(workPerson!=loginUser){
			 								alert("当前登陆人不是工作负责人不能删除!");
			 								return;
			 							}else{
										var id = nData.id;
										$.ajax({
											url: format_url('/workTicketLineTwo/deleteValidate/'+id),
											contentType : 'application/json',
											dataType : 'JSON',
											type: 'POST',
											data : JSON.stringify(nData),
											success: function(result){
												if(result.result == 'success'){
													var url =format_url('/workTicketLineTwo/'+ id);
													A.confirm('您确认删除么？',function(){
														$.ajax({
															url : url,
			        										contentType : 'application/json',
			        										dataType : 'JSON',
			        										type : 'DELETE',
			        										success: function(result){
			        											alert('删除成功');
			        											workTicketDatatables.draw(false);
			        										},
			        										error:function(v,n){
			        											alert('操作失败');
			        										}
														});
													});
            									} else{
            										alert(result.errorMsg);
            									}
											}
										});
			 						}
									}
								}
						}, {
								id:"436",
							 	label: "提交审核",
								icon: "fa fa-check-square-o bigger-130",
								className: " edit",
								render: function(btnNode, data){
									if(data.workStatus!="1"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var workPerson = nData.guarderId;
					                    var loginUser = '${userEntity.id}';
					                    console.log(workPerson);
					                    console.log(loginUser);
					                    
					                    if(workPerson!=loginUser){
					                       alert("当前登陆人不是工作负责人不能修改!");
					                       return;
					                    }else{
										var id = nData.id;
										$.ajax({
											url: format_url('/workTicketLineTwo/tijiaoValidate/'+id),
											contentType : 'application/json',
											dataType : 'JSON',
											type: 'POST',
											data : JSON.stringify(nData),
											success: function(result){
												if(result.result == 'success'){
													workticketDialog = new A.dialog({
														title:"提交确认",
														url:format_url("/workTicketLineTwo/sureSubmit?id="+ id),
														height:450,
														width:500
													}).render();
            									} else{
            										alert(result.errorMsg);
            									}
											}
										});
					                   }
									}
								}
						}, 
						/* {
								id:"437",
							 	label: "设为典型票",
								icon: "fa fa-file-text bigger-130",
								className: "edit",
								render: function(btnNode, data){
									if(data.workStatus!="21"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										$.ajax({
											url: format_url('/workTicketLineTwo/setValidate/'+id),
											contentType : 'application/json',
											dataType : 'JSON',
											type: 'POST',
											data : JSON.stringify(nData),
											success: function(result){
												if(result.result == 'success'){
            										alert('设置成功');
            										workTicketDatatables.draw(false);
            									} else {
            										alert(result.errorMsg);
            									}
											}
										})
									}
								}
								
						}, */ 
						{
							    id:"440",
								label:"查看",
								icon: "fa fa-binoculars bigger-110",
								className: "blue",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										A.loadPage({
											render : '#page-container',
											url:format_url("/workTicketLineTwo/detail/"+ id),
										});
									}
								}
						},
						 {
							id:"441",
						 	label: "鉴定",
							icon: "fa fa-gavel bigger-130",
							className: " edit",
							render: function(btnNode, data){
								if(data.workStatus!="21"&&data.workStatus!="7"){
									btnNode.hide();
								}
							},
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									$.ajax({
										url: format_url('/workTicketLineTwo/invalidValidate/'+id),
										contentType : 'application/json',
										dataType : 'JSON',
										type: 'POST',
										data : JSON.stringify(nData),
										success: function(result){
											if(result.result == 'success'){
												workticketDialog = new A.dialog({
													title:"鉴定",
													url:format_url("/workTicketLineTwo/invalid?id="+ id),
													height:350,
													width:600
												}).render();
        									} else{
        										alert(result.errorMsg);
        									}
										}
									});
								}
							}
					}, 
					{
						id: "print",
						label:"打印",
						icon: "glyphicon glyphicon-print",
						className: "blue ",
						events:{
							click: function(event, nRow, nData){
								birtPrint("workTicketLineTwo.rptdesign",nData.id);
							}
						}
					}
					,{//zzq修改第二次需求
						id: "printAqcs",
						label:"安措打印",
						icon: "glyphicon glyphicon-print",
						className: "blue ",
						events:{
							click: function(event, nRow, nData){
								var aqcsFlag=nData.safeFlag;
								if(aqcsFlag=="0"){
									//for (var int = 1; int <= nData.safeNum; int++) {
										birtPrintSafe("work_helpsafe.rptdesign",nData.id,1);
									//}
								}else{
									alert("您没有办理继电保护安全措施票!");
									return;
								}
							}
						}
					}
					]}
					}).render();
					
					
					
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
    							&&String(unitNameIdCombotree.getValue())+""!=""){
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
				
				var url =format_url("/workLineTwo/submit?workId="+id+"&selectUser="+selectUser);
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