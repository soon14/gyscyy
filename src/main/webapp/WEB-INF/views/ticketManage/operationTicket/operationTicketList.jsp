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
				<li>
					两票管理
				</li>
				<li class="active">
					操作票管理
				</li>
				<li class="active">操作票</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content ">
			<div class="col-lg-12 col-md-12 col-sm-12 search-content  padding-zero">
				<div class="form-inline  text-left" role="form">
					 <div class="clearfix">
				           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
								<label class="searchLabel">操作票编号</label>：
				                <input id="searchcode" class="input-width text-left" placeholder="请输入操作票编号" type="text" >
		                   </div>
				           <div class="form-group form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
								<label class="searchLabel" for="form-field-1">单位名称</label>：
								 <div id="searchunitIdDiv" class="input-width text-left padding-zero"></div>
		                   </div>
				           <div class="form-group form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
								<label class="searchLabel" for="form-field-1">操作人</label>：
								 <div class="padding-zero input-width  text-left">
		                   		 <select id="searchoperateId" class="col-md-12" name="searchoperateId"></select>
		                   		 </div>
		                   </div>
				           <div class="form-group form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
								<label class="searchLabel" for="form-field-1">监护人</label>：
								 <div class="padding-zero input-width  text-left">
		                   		 <select id="searchguardianId" class="col-md-12" name="searchguardianId"></select>
		                   		 </div>
		                   </div>
	                    </div>
						<div class="clearfix">
					           <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
									<label class="searchLabel" for="form-field-1">开始时间</label>：
									<div class="form-group date-input padding-zero text-left">
		                                <div id="searchStartstartDateDiv"  style="border:none; padding:0px;"></div>
		                            </div>
			                        <label style="width: 2.6%;text-align: center">~</label>
			                        <div class="form-group date-input padding-zero text-left">
		                                <div id="searchEndstartDateDiv"   style="border:none; padding:0px;"></div>
		                            </div>
			                   </div>
					           <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
									<label class="searchLabel" for="form-field-1">结束时间</label>：
									<div class="form-group date-input padding-zero text-left">
		                                <div id="searchStartendDateDiv"  style="border:none; padding:0px;"></div>
		                            </div>
			                        <label style="width: 2.6%;text-align: center">~</label>
			                        <div class="form-group date-input padding-zero text-left">
		                                <div id="searchEndendDateDiv"   style="border:none; padding:0px;"></div>
		                            </div>
			                   </div>
	                    </div>
						<!-- <div class="clearfix">
					           <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
									<label class="searchLabel" for="form-field-1">操作开始时间</label>：
									<div class="form-group date-input padding-zero text-left">
		                                <div id="searchStartstartDateDiv"  style="border:none; padding:0px;"></div>
		                            </div>
			                        <label style="width: 2.6%;text-align: center">~</label>
			                        <div class="form-group date-input padding-zero text-left">
		                                <div id="searchEndstartDateDiv"   style="border:none; padding:0px;"></div>
		                            </div>
			                   </div>
					           <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
									<label class="searchLabel" for="form-field-1">操作终了时间</label>：
									<div class="form-group date-input padding-zero text-left">
		                                <div id="searchStartendDateDiv"  style="border:none; padding:0px;"></div>
		                            </div>
			                        <label style="width: 2.6%;text-align: center">~</label>
			                        <div class="form-group date-input padding-zero text-left">
		                                <div id="searchEndendDateDiv"   style="border:none; padding:0px;"></div>
		                            </div>
			                   </div>
	                    </div> -->
						 <div class="clearfix">
		                     <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                            <label class="searchLabel" for="searchprocessStatus">状态</label>：
	                            <div class="input-width padding-zero  text-left">
	                                <select id="searchprocessStatus"  name="searchprocessStatus"></select>
	                            </div>
	                          </div>
								 <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12" style="width: 49%;">
		                            <label class="searchLabel" for="searchdepict">操作任务</label>：
		                            <input id="searchworkText" class="input-width padding-zero derect-input  text-left" placeholder="请输入操作任务" type="text">
		                        </div>
                       		  <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; ">
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
						<h5 class='table-title header smaller blue' >操作票</h5>
						<table id="operationTicket_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>操作票编号</th>
	                                <th>单位名称</th>
	                                <th>操作人</th>
	                                <th>监护人</th>
	                                <th>操作任务</th>
	                                <th>工作票编码</th>
	                                <th>开始时间</th>
	                                <th>结束时间</th>
	                                <th>状态</th>
	                                <th>典型票</th>
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
			var listFormDialog,operationTicketDatatables;
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					//操作人
					var searchfindUserId = new A.combobox({
						render : "#searchoperateId",
						datasource : ${searchuser},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					//监护人
					var searchfindUserId = new A.combobox({
						render : "#searchguardianId",
						datasource : ${searchuser},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					var searchprocessStatus = new A.combobox({
						render : "#searchprocessStatus",
						datasource : ${searchprocessStatus},
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
					//开始时间
					 var searchStartstartDate = new A.my97datepicker({
						id: 'searchStartstartDate',
						name:'searchStartstartDate',
						render:'#searchStartstartDateDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "#F{$dp.$D(\\'searchEndstartDate\\')}",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					var searchEndstartDate = new A.my97datepicker({
						id: 'searchEndstartDate',
						name:'searchEndstartDate',
						render:'#searchEndstartDateDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'searchStartstartDate\\')}",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render(); 
					var searchStartendDate = new A.my97datepicker({
						id: 'searchStartendDate',
						name:'searchStartendDate',
						render:'#searchStartendDateDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "#F{$dp.$D(\\'searchEndendDate\\')}",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					//终了时间
					 var searchEndendDate = new A.my97datepicker({
						id: 'searchEndendDate',
						name:'searchEndendDate',
						render:'#searchEndendDateDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'searchStartendDate\\')}",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render(); 
					var exportExcel="";
					 operationTicketDatatables = new A.datatables({
						render: '#operationTicket_table',
						options: {
					        "ajax": {
					            "url": format_url("/operationTicket/searchData"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	if(d.search.value){
					            	d.conditions = JSON.parse(d.search.value);
					            	if(d.conditions){
						            	for(var index in d.conditions){
					            			if(d.conditions[index].type==1){
					            			$("#"+d.conditions[index].name).val(d.conditions[index].value);
					            			$("#"+d.conditions[index].name).trigger("chosen:updated");
					            			}
					            			if(d.conditions[index].type==2){
					            				searchunitId.setValue(d.conditions[index].value);
						            		}
					            		}
					            	}
					            	}
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 100,
							bStateSave: true,
							searching: true,
							//aLengthMenu: [2],
							//iCookieDuration:'${cookieTime}',
							iCookieDuration:cookieTime,
							columns: [{data:"id", visible:false,orderable:false},
							          {data: "code",width: "8%",orderable: true}, 
							          {data: "unitName",width: "9%",orderable: true},
							          {data: "operateName",width: "9%",orderable: true},
							          {data: "guardianName",width: "10%",orderable: true},
							          {data: "workText",width: "15%",orderable: true}, 
							          {data: "workticketCode",width: "10%",orderable: true},
							          {data: "startDate",width: "8%",orderable: true}, 
							          {data: "endDate",width: "8%",orderable: true}, 
							          {data: "statusName",name:"status",width: "8%",orderable: true},
							          {data: "isSetName",name:"isSet",width: "5%",orderable: true},
							          {data: "identifyName",name:"identify",width: "5%",orderable: true},
							          ],
						    fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											 if(exportExcel){
												 exportExcels(format_url("/operationTicket/exportExcel"),JSON.stringify(conditions));
											 }
											 exportExcel="";
							},
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event, nRow, nData){
            							//zzq修改开始
// 										$.ajax({
// 											url: format_url('/operationTicket/addValidate'),
// 											contentType : 'application/json',
// 											dataType : 'JSON',
// 											type: 'POST',
// 											data : JSON.stringify(nData),
// 											success: function(result){
// 												if(result.result == 'success'){
// 													A.loadPage({
// 														render : '#page-container',
// 														url : format_url('/operationTicket/getAdd')
// 													});
//             									} else{
//             										alert(result.errorMsg);
//             									}
// 											}
// 										});
										//zzq修改结束
            							A.loadPage({
											render : '#page-container',
											url : format_url('/operationTicket/getAdd')
										});
            						}
        						}
        					},{
								label:"导出",
								icon:"glyphicon glyphicon-download",
								className:"btn-primary",
								events:{
		    						click:function(event){
		    							$('#btnSearch').click();
            							exportExcel="exportExcel";
		    						}
								}
							}],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								render: function(btnNode, data){
									if(data.status!="1"&&data.status!="0"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var workPerson = nData.operateId;
										var loginUser = '${userEntity.id}';
										
			 							if(workPerson!=loginUser){
			 								alert("当前登陆人不是操作人不能修改!");
			 								return;
			 							}else{
										var id = nData.id;
										A.loadPage({
											render : '#page-container',
											url : format_url('/operationTicket/getEdit/' + id)
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
									if(data.status!="1"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var workPerson = nData.operateId;
										var loginUser = '${userEntity.id}';
			 							if(workPerson!=loginUser){
			 								alert("当前登陆人不是操作人不能删除!");
			 								return;
			 							}else{
										var id = nData.id;
										var url =format_url('/operationTicket/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											if(result.result=="success"){
        												alert('删除成功');	
        												operationTicketDatatables.draw(false);
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
						}, {
							id:"submit",
							label:"提交",
							icon: "fa fa-check-square-o bigger-130",
							className: " edit",
							render: function(btnNode, data){
								if(!(data.status==1)){
									btnNode.hide();
								}
							},
							events:{
								click: function(event, nRow, nData){
									var workPerson = nData.operateId;
									var loginUser = '${userEntity.id}';
		 							if(workPerson!=loginUser){
		 								alert("当前登陆人不是操作人不能提交!");
		 								return;
		 							}else{
									if(!(nData.status==0||nData.status==1)){
										alert("该记录不是待提交或驳回状态，不能提交！");
										return;
									}
									listFormDialog = new A.dialog({
										width: 850,
										height: 450,
										title: "操作票提交人",
										url:format_url('/operationTicket/sureSubmitPerson'),
										closed: function(resule){
											var obj=new Object(); 
											var id = nData.id;
											var url =format_url('/operationTicket/submit/'+ id);
											if(listFormDialog.resule){
												  obj.userList=listFormDialog.resule.join(",");
														$.ajax({
															url : url,
				    										contentType : 'application/json',
				    										dataType : 'JSON',
				    										type : 'POST',
				    										data:JSON.stringify(obj),
				    										success: function(result){
				    											if(result.result=="success"){
				    												alert("提交成功");
				    												operationTicketDatatables.draw(false);
				    											}else{
				    												alert(result.errorMsg);
				    											}
				    										},
				    										error:function(v,n){
				    											alert('提交失败');
				    										}
													});
											}
										}
									}).render();
		 						}
								}
							}
					},{
						id:"isSet",
					 	label: "设为典型票",
						icon: "fa fa-file-text bigger-130",
						className: "edit",
						render: function(btnNode, data){
							if(data.status!="9"){
								btnNode.hide();
							}
							if(data.isSet=="1"){
								btnNode.hide();
							}
						},
						events:{
							click: function(event, nRow, nData){
								/* var workPerson = nData.operateId;
								var loginUser = '${userEntity.id}';
	 							if(workPerson!=loginUser){
	 								alert("当前登陆人不是操作人不能设为典型票!");
	 								return;
	 							}else{ */
	 							
								var data = ${setuser};
								var setUserIds = [];
								if(data.length && data.length>0){
									for(var i =0; i<data.length; i++){
										setUserIds.push(data[i].code);
									}
								}
								var loginUser = '${userEntity.id}';
								for(var j=0;j<setUserIds.length;j++){

								if(!contains(setUserIds,loginUser)){
									alert('当前登录人不是生产技处人员不能设为典型票!');
									return;
								 }
							 } 
	 							
								listFormDialog = new A.dialog({
									width:850 ,
									height:181 ,
									title: "典型票设置",
									data:{type:3},
									url:format_url('/operationTicket/getIsSet'),
									closed: function(){
										if(listFormDialog.resule){
											$.ajax({
												url: format_url('/operationTicket/setIsSet/'+nData.id),
												contentType : 'application/json',
												dataType : 'JSON',
												type: 'POST',
												data:JSON.stringify(listFormDialog.resule),
												success: function(result){
													if(result.result == 'success'){
	            										alert('设置成功');
	            										operationTicketDatatables.draw(false);
	            									} else {
	            										alert(result.errorMsg);
	            									}
												}
											});
										}
									}
								}).render();
	 							}
							//}
						}
					},{
						id: "detail",
						label:"查看",
						icon: "fa fa-binoculars bigger-110",
						className: "blue ",
						events:{
							click: function(event, nRow, nData){
								var id = nData.id;
								A.loadPage({
									render : '#page-container',
									url : format_url('/operationTicket/getDetail/' + id)
								});
							}
						}
					}, /* {
						id:"jd",
					 	label: "鉴定",
						icon: "fa fa-gavel bigger-130",
						className: " edit",
						render: function(btnNode, data){
							if(data.status!="9"){
								btnNode.hide();
							}
						},
						events:{
							click: function(event, nRow, nData){
								var id = nData.id;
// 								$.ajax({
// 									url: format_url('/workTicket/invalidValidate/'+id),
// 									contentType : 'application/json',
// 									dataType : 'JSON',
// 									type: 'POST',
// 									data : JSON.stringify(nData),
// 									success: function(result){
// 										if(result.result == 'success'){
											listFormDialog = new A.dialog({
												title:"鉴定",
												url:format_url("/operationTicket/invalid?id="+ id),
												height:350,
												width:600
											}).render();
//     									} else{
//     										alert(result.errorMsg);
//     									}
// 									}
// 								});
							}
						}
				},  */
				{
						id: "print",
						label:"打印",
						icon: "glyphicon glyphicon-print",
						className: "blue ",
						events:{
							click: function(event, nRow, nData){
								birtPrint("operate_ticket.rptdesign",nData.id);
							}
						}
					}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
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
						if(searchunitId.getValue()!=null&&searchunitId.getValue()+""!=""){
	    					conditions.push({
	        					field: 'T.C_UNIT_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:searchunitId,
	        					type:2,
	        					value:searchunitId.getValue()
	        				});
						}
						if($('#searchoperateId').val()){
	    					conditions.push({
	        					field: 'T.C_OPERATE_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:'searchoperateId',
	        					type:1,
	        					value:$('#searchoperateId').val()
	        				});
						}
						if($('#searchguardianId').val()){
	    					conditions.push({
	        					field: 'T.C_GUARDIAN_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:'searchguardianId',
	        					type:1,
	        					value:$('#searchguardianId').val()
	        				});
						}
						 if($('#searchStartstartDate').val()){
	    					conditions.push({
	    						field: 'T.C_START_DATE',
	    						fieldType:'DATE',
	    						matchType:'GE',
	    						name:'searchStartstartDate',
	        					type:1,
	    						value:$('#searchStartstartDate').val()
	    					});
						}
						if($('#searchEndstartDate').val()){
	    					conditions.push({
	    						field: 'T.C_START_DATE',
	    						fieldType:'DATE',
	    						matchType:'LE',
	    						name:'searchEndstartDate',
	        					type:1,
	    						value:$('#searchEndstartDate').val()
	    					});
						} 
						if($('#searchStartendDate').val()){
	    					conditions.push({
	    						field: 'T.C_END_DATE',
	    						fieldType:'DATE',
	    						matchType:'GE',
	    						name:'searchStartendDate',
	        					type:1,
	    						value:$('#searchStartendDate').val()
	    					});
						} 
						 if($('#searchEndendDate').val()){
	    					conditions.push({
	    						field: 'T.C_END_DATE',
	    						fieldType:'DATE',
	    						matchType:'LE',
	    						name:'searchEndendDate',
	        					type:1,
	    						value:$('#searchEndendDate').val()
	    					});
						} 
						if($('#searchworkText').val()){
	    					conditions.push({
	        					field: 'T.C_WORK_TEXT',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					name:'searchworkText',
	        					type:1,
	        					value:$('#searchworkText').val()
	        				});
						}
						if($('#searchprocessStatus').val()){
	    					conditions.push({
	        					field: 'T.C_STATUS',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:'searchprocessStatus',
	        					type:1,
	        					value:$('#searchprocessStatus').val()
	        				});
						}
						operationTicketDatatables._datatables.search(JSON.stringify(conditions)).draw();
					});
					$('#btnReset').on('click',function(){
						$("#searchcode").val('');
						searchunitId.setValue(null);
						$("#searchoperateId").val("");
						$("#searchoperateId").trigger("chosen:updated");
						$("#searchguardianId").val("");
						$("#searchguardianId").trigger("chosen:updated");
						$("#searchprocessStatus").val("");
						$("#searchprocessStatus").trigger("chosen:updated");
					 	$("#searchStartstartDate").val("");
						$("#searchEndstartDate").val("");
						$("#searchStartendDate").val(""); 
						$("#searchEndendDate").val(""); 

						$("#searchworkText").val('');
					});
				});
			});
			
			//判断值是否在数组里的方法zzq
			function contains(arr, obj) {
				  var i = arr.length;
				  while (i--) {
				    if (arr[i] === obj) {
				      return true;
				    }
				  }
				  return false;
			}
        </script>
    </body>
</html>