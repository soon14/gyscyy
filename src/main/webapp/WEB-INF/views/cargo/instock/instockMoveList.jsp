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
				<li>物资管理</li>
				<li class="active">入库管理</li>
			</ul>
		</div>
		<div class="page-content">
			<div class=" col-lg-12 col-md-12 col-sm-12  padding-zero search-content">
				<div class="form-inline text-left" role="form">
					<div class="clearfix">
						<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
							<div  class="clearfix groupDiv">
								<label class="searchLabel" for="searchInstockNum">入库单号</label>：
				                <input id="searchInstockNum" class="inputWidth text-left" placeholder="请输入入库单号" type="text" >
		           		 	</div>
		           		 	<div  class="clearfix  groupRightDiv">
				           		 <label class="searchLabel" for="searchInstockType">入库类型</label>：
								 <div class="inputWidth padding-zero  text-left">
			                	<select id="searchInstockType" ></select>
								</div>
							</div>
						</div>
			        	<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
							<label class="searchLabel" for="searchStartInstockTimeDiv">入库时间</label>：
							<div class="form-group dateInput padding-zero text-left">
			                    <div id="searchStartInstockTimeDiv" style="border:none; padding:0px;"></div>
			                </div>
							<div class="toLabel" for="searchStartTimeRightDiv" >~</div>
							<div class="form-group dateInput padding-zero text-left">
	                            <div id="searchEndInstockTimeDiv" style="border:none; padding:0px;"></div>
						    </div>
                   		 </div>
		        </div>
		        <div class="clearfix">
						<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
							<div  class="clearfix groupDiv">
								<label class="searchLabel" for="searchApproveStatus">审核状态</label>：
								<div class="inputWidth padding-zero  text-left">
					                <select id="searchApproveStatus" ></select>
								</div>
							</div>
							<div  class="clearfix  groupRightDiv">
							<label class="searchLabel" for="searchStationName">场站名称</label>：
		                	<div id="searchStationNameDiv" class="inputWidth text-left padding-zero"></div>
							</div>
		                </div>
						<div class="form-group  col-lg-3 col-md-3 col-sm-3 padding-zero" style="height: 31px;">
						<label class="searchLabel" for="searchApplicant" >申请人</label>：
						<div  class='inputWidth'>
							<div id="searchApplicantDiv">
							</div>	
						</div>
                   </div>
	              <div  class="form-group col-lg-3 col-md-3 col-sm-3 padding-zero text-right btnSearchBottom">
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
						<h5 class='table-title header smaller blue' >入库单列表</h5>
						<table id="instock_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>入库单号</th>
	                                <th>所属仓库</th>
	                                <th class='onlyLeft'>入库时间</th>
	                                <th>入库类型</th>
	                                <th>场站名称</th>
	                                <th>申请人</th>
	                                <th>审核状态</th>
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
			var submitUserDialog;
			var data=[];
			jQuery(function($) {
				seajs.use(['datatables','confirm','combobox','selectbox','my97datepicker','combotree'], function(A){
					var conditions=[];
					var startDatePicker = new A.my97datepicker({
						id: 'searchStartTime',
						name: 'instockTime',
						render:'#searchStartInstockTimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: 'yyyy-MM-dd'
						}
					}).render();
					var endDatePicker = new A.my97datepicker({
						id: 'searchEndTime',
						name: 'instockTime',
						render:'#searchEndInstockTimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'searchStartTime\\')}",
								dateFmt: 'yyyy-MM-dd'
						}
					}).render();
					//入库类型
					var instockTypeData = ${instockTypeCombobox};
					var instockTypeCombobox = new A.combobox({
						render: "#searchInstockType",
						datasource:instockTypeData,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//审核状态
					var approveStatusData = ${approveStatusCombobox};
					var approveStatusCombobox = new A.combobox({
						render: "#searchApproveStatus",
						datasource:approveStatusData,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//申请人选择带回
					var flowManagerNamesDiv=new A.selectbox({
						id: 'searchApplicantDivId',
						name:'searchApplicantNames',
						title:'人员',
						url:'/sysUser/userSelect?singleSelect=1',
						render:'#searchApplicantDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data,self){
							var names =[];
							var ids=[];
							for(var i =0; i<data.length; i++){
								names.push(data[i].name);
								ids.push(data[i].id);
							}
							self.setValue(names,ids);
						}
					}).render();
					var userName = ${sysUserName};
					var userId = ${sysUserId};
					
					//单位下拉树
					var unitNameTreeData = ${unitNameIdTreeList};
					var searchunitId = new A.combotree({
						render: "#searchStationNameDiv",
						name: 'searchunitId',
						//返回数据待后台返回
						datasource: unitNameTreeData,
						width:"110px",
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
							}
						}
					}).render();
					
					var instockDatatables = new A.datatables({
						render: '#instock_table',
						options: {
					        "ajax": {
					            "url": format_url("/instockMove/data/list"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	//d.conditions = conditions;
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
							optWidth: 120,
							bStateSave: true,
							searching: true,
							iCookieDuration:cookieTime,
							columns: [
							         {data:"id", visible:false,orderable:false}, 
							         {data: "instockNum",width: "15%",orderable: false}, 
							         {data: "wareHouseName",width: "10%",orderable: false},
							         {data: "instockTime",width: "10%",orderable: false,sClass:'center'}, 
							         {data: "instockTypeName",width: "15%",orderable: false}, 
							         {data: "unitName",width: "15%",orderable: false}, 
							         {data: "applicantName",width: "15%",orderable: false}, 
							         {data: "approveStatusName",width: "15%",orderable: false}
							        ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
										A.loadPage({
											render : '#page-container',
											url : format_url('/instockMove/getAdd')
										});
            						}
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = instockDatatables.getSelectRowDatas();
										var ids = [];
										var approveStatusArray = [];
										var applicant = [];
										
										var isDuty = ${isDuty};
										var isSystemManage = ${isSystemManage};
										
										
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
												applicant.push(data[i].applicant);
												approveStatusArray.push(data[i].approveStatus);
											}
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										
										function contains(arr, obj) {  
										    var i = arr.length;  
										    while (i--) {  
										        if (arr[i] === obj) {  
										            return true;  
										        }  
										    }  
										    return false;  
										}  
										
										if(isDuty!=true&&isSystemManage!=true&&!contains(applicant, userId)){
											alert('没有批量删除权限！');
											return;
										}
										
										//判断该数据的审核状态，为0时可删除
										for(var i=0;i<approveStatusArray.length;i++){
											if(approveStatusArray[i]!="0"){
												alert("有数据在流程中不可删除！");
												return;
											}
										}
										var url = format_url('/instockMove/multipleDel');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													instockDatatables.draw(false);
												},
												error:function(v,n){
													alert('操作失败');
												}
											});
										});
									}
								}
							}, {
								label:"导出",
								icon: "glyphicon glyphicon-download",
								className: "btn-primary",
								events:{
									click: function(event){
										$('#btnSearch').click();
										var searchInstockNum = $('#searchInstockNum').val();
										var searchStartTime = $('#searchStartTime').val();
										var searchEndTime = $('#searchEndTime').val();
										var instockType = instockTypeCombobox.getValue();
										var approveStatus = approveStatusCombobox.getValue();
										var searchUnitId= searchunitId.getValue();
										var searchApplicantDivId = $("#searchApplicantDivId").next('input').val();
										window.location.href=format_url("/instockMove/exportExcel?instockNum="+searchInstockNum
												+"&startTime="+searchStartTime+"&endTime="+searchEndTime+"&instockType="+instockType
												+"&approveStatus="+approveStatus+"&unitId="+searchUnitId+"&applicant="+searchApplicantDivId); 
									}
								}
							}/* , {
								label:"打印",
								icon: "glyphicon glyphicon-print",
								className: "btn-primary",
								events:{
									click: function(event){
										var data = instockDatatables.getSelectRowDatas();
										$.ajax({
											url: format_url(),
											contentType : 'application/json',
											dataType : 'JSON',
											type: 'POST',
											data : JSON.stringify(data),
											success: function(result){
												if(result.result == 'success'){
													alert("操作成功");
													instockDatatables.draw(false);
												}else if (result.result == 'error') {
													alert(result.errorMsg);
												}
											}
										})
									}
								}
							} */],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								render: function(btnNode, data){
									var isDuty = ${isDuty};
									var isSystemManage = ${isSystemManage};
									var applicantId = data.applicant;
									var status = data.approveStatus;
									var sysUserId = ${sysUserId};
									//非库管和系统管理员
									//审核状态为非未提交（0）、驳回（1）、取消流程（5）不能修改
									if(status!=0&&status!=1){
										btnNode.hide();
									}else{
				                        if(applicantId!=sysUserId){
				                          btnNode.hide();
				                        }
									}
									//如果审核通过状态，超级管理员除外
									if(status==4){
										btnNode.hide();
									}
			                      },
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var approveId = nData.applicant;
										A.loadPage({
											render : '#page-container',
											url : format_url('/instockMove/getEdit/' + id)
										});
								}
								}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								render: function(btnNode, data){
									var isDuty = ${isDuty};
									var isSystemManage = ${isSystemManage};
									var applicantId = data.applicant;
									var status = data.approveStatus;
									var sysUserId = ${sysUserId};
									//非库管和系统管理员
									//审核状态为非未提交（0）不能删除
									var sysUserId = ${sysUserId};
									//非库管和系统管理员
									//审核状态为非未提交（0）、驳回（1）、取消流程（5）不能修改
									
									if(status!=0){
										btnNode.hide();
									}else{
				                        if(applicantId!=sysUserId){
				                          btnNode.hide();
				                        }
									}
									
			                      },
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/instockMove/singleDelete/'+ id);
										//判断该数据的审核状态，为0时可删除
										var approveStatus = nData.approveStatus;
										if(approveStatus!="0"){
											alert("非待提交数据不可删除！");
											return;
										}
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											instockDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						}, {
						 	label: "查看",
							icon: "fa fa-binoculars bigger-130",
							className: "blue search",
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									A.loadPage({
										render : '#page-container',
										url : format_url('/instockMove/showPage/' + id)
									});
								}
							}
						},{
							id:"submit",
							label:"提交审核",
							icon: "fa fa-check-square-o bigger-130",
							className: "blue key",
							render: function(btnNode, data){
								var isDuty = ${isDuty};
								var isSystemManage = ${isSystemManage};
								var applicantId = data.applicant;
								var status = data.approveStatus;
								var sysUserId = ${sysUserId};
								//非库管和系统管理员
								//审核状态为非未提交（0）不能提交
								if(status!=0||sysUserId!=applicantId){
									btnNode.hide();
								}
								
		                      },
							events:{
								click: function(event, nRow, nData){
									if(!(nData.approveStatus==0||nData.approveStatus==1)){
										alert("该记录不是待提交或驳回状态，不能提交！");
										return;
									}
									submitUserDialog = new A.dialog({
										width: 850,
										height: 481,
										title: "入库管理提交人",
										url:format_url('/instockMove/sureSubmitPerson'),
										closed: function(resule){
											var obj=new Object(); 
											var id = nData.id;
											var url =format_url('/instockMove/submit/'+ id);
											if(submitUserDialog.resule){
												  obj.userList=submitUserDialog.resule.join(",");
												  $.ajax({
															url : url,
				    										contentType : 'application/json',
				    										dataType : 'JSON',
				    										type : 'POST',
				    										data:JSON.stringify(obj),
				    										success: function(result){
				    											if(result.result=="success"){
				    												alert("提交成功");
				    												instockDatatables.draw(false);
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
					},{
						id: "print",
						label:"打印",
						icon: "glyphicon glyphicon-print",
						className: "blue ",
						events:{
							click: function(event, nRow, nData){
								console.log(nData.id);
								birtPrint("instock.rptdesign",nData.id);
							}
						}
					}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						//入库单号
						if($("#searchInstockNum").val()){
    						conditions.push({
    							field: 'instockNum',
    							fieldType:'STRING',
    							matchType:'LIKE',
								name:'instockNum',
								type:"1",
    							value:$("#searchInstockNum").val()
    						});
						}
						//入库时间开始
						if($("#searchStartTime").val()){
        					conditions.push({
        						field: 'instockTime',
        						fieldType:'DATE',
        						matchType:'GE',
        						value:$("#searchStartTime").val()
        					});
						}
						//入库时间结束
						if($("#searchEndTime").val()){
        					conditions.push({
        						field: 'instockTime',
        						fieldType:'DATE',
        						matchType:'LE',
        						value:$("#searchEndTime").val()
        					});
						}
						//入库类型
						if($("#searchInstockType").val()){
    						conditions.push({
    							field: 'instockType',
    							fieldType:'STRING',
    							matchType:'EQ',
    							name:'instockType',
								type:"1",
    							value:$("#searchInstockType").val()
    						});
						}
						//申请人
						if($("#searchApplicantDivId").next('input').val()){
    						conditions.push({
    							field: 'applicant',
    							fieldType:'STRING',
    							matchType:'EQ',
    							name:'applicant',
    							type:"1",
    							value:$("#searchApplicantDivId").next('input').val()
    						});
						}
						//审核状态
						if($("#searchApproveStatus").val()){
    						conditions.push({
    							field: 'approveStatus',
    							fieldType:'STRING',
    							matchType:'EQ',
    							name:'approveStatus',
    							type:'1',
    							value:$("#searchApproveStatus").val()
    						});
						}
						//风场名称
						if(searchunitId.getValue()!=null&&searchunitId.getValue()+""!=""){
	    					conditions.push({
	        					field: 'a.C_unit_Id',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:searchunitId,
	        					type:2,
	        					value:searchunitId.getValue()
	        				});
						}
						
						instockDatatables._datatables.search(JSON.stringify(conditions)).draw();
						//instockDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$('#searchInstockNum').val('');
						$('#searchStartTime').val('');
						$('#searchEndTime').val('');
						//申请人清空
						flowManagerNamesDiv.setValue(null);
						searchunitId.setValue(null);
						//下拉框清空
						instockTypeCombobox.setValue("99");
						approveStatusCombobox.setValue("99");
					});
				});
			});
        </script>
    </body>
</html>