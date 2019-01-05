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
		<div class="breadcrumbs ace-save-state" id="breadcrumbs">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="javascript:void(0);" onclick="firstPage()">首页</a>
				</li>
				<li>
					缺陷管理
				</li>
				<li class="active">缺陷管理</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content ">
			<div class="col-lg-12 col-md-12 col-sm-12 padding-zero search-content">
				<div class="form-inline text-left" role="form">
                    <div class="clearfix">
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="searchunitIdDiv">缺陷单位名称</label>：
<!--                             <div id="searchunitIdDiv" class="inputWidth text-left padding-zero"></div> -->
                            <div class="padding-zero inputWidth  text-left">
                                <select id="searchunitIdDiv" class="" name="unitId"></select>
                            </div>
                        </div>
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="searchequipIdDiv">设备名称</label>：
                            <div id="searchequipNameDiv" class="inputWidth  text-left padding-zero"></div>
                        </div>
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" >
                            <label class="searchLabel" for="searchappraisalUserId">鉴定人</label>：
                            <div class="padding-zero inputWidth  text-left">
                                <select id="searchappraisalUserId" class="" name="searchtype"></select>
                            </div>
                        </div>
                         <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" >
                            <label class="searchLabel longSearchLabel" for="searchappraisalUserId">消缺负责人</label>：
                            <div class="padding-zero inputWidth  text-left">
                                <select id="searchsolveUserId" class="" name="searchtype"></select>
                            </div>
                        </div>
                    </div>
					<div class="clearfix">
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" style="margin-top: 7px">缺陷类型</label>：
                            <div class="form-group  inputWidth padding-zero  text-left">
                                <select id="searchtype"  name="searchtype"></select>
                            </div>
                        </div>
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="searchfindUserId">发现人</label>：
                            <div class="form-groupp adding-zero inputWidth  text-left">
                                <select id="searchfindUserId" class="" name="searchtype"></select>
                            </div>
                        </div>
                        <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel">发现时间</label>：
                            <div class="form-group dateInputOther padding-zero text-left">
                                <div id="searchStartfindTimeDiv"  style="border:none; padding:0px;"></div>
                            </div>
                            <div class="toLabel" for="searchStartTimeRightDiv" >~</div>
                            <div class="form-group dateInputOther padding-zero text-left">
                                <div id="searchEndfindTimeDiv"   style="border:none; padding:0px;"></div>
                            </div>
                        </div>
                    </div>
                    <div class="clearfix">
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="searchprocessStatus">状态</label>：
                            <div class="form-group  inputWidth padding-zero  text-left">
                                <select id="searchprocessStatus"  name="searchprocessStatus"></select>
                            </div>
                        </div>
<!--                         <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero"> -->
<!--                             <label class="searchLabel longSearchLabel" for="searchprocessStatus">缺陷设备类型</label>： -->
<!--                             <div class="form-group  inputWidth padding-zero  text-left"> -->
<!--                                 <select id="searchequipTypes"  name="searchequipTypes"></select> -->
<!--                             </div> -->
<!--                         </div> -->
                        <div class="form-group col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="searchdepict">缺陷描述</label>：
                            <input id="searchdepict" class="form-group  inputWidth padding-zero  text-left" placeholder="请输入缺陷描述" type="text">
                        </div>
                        <div class="form-group col-lg-2 col-md-2 col-sm-12 col-xs-12 padding-zero btnSearchBottom"style="text-align:right;" >
                            <button id="btnSearch" class="btn btn-xs btn-primary">
                                <i class="glyphicon glyphicon-search"></i>
                                查询
                            </button>
                            <button id="btnReset" class="btn btn-xs btn-primary" style="margin-right: -150%">
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
					<div class="widget-main no-padding" id="defect-print">
                        <h5 class="table-title header smaller blue" >缺陷管理</h5>
						<table id="defect-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
        							<th>序号</th>
<!-- 	                                <th>缺陷编码</th> -->
	                                <th>缺陷单位名称</th>
	                                <th>设备名称</th>
	                                <th>发现人</th>
	                                <th>发现时间</th>
	                                <th>消缺时间</th>
	                                <th>缺陷类型</th>
	                                <th>缺陷描述</th>
	                                <th>鉴定人</th>
	                                <th>消缺负责人</th>
	                                <th>状态</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div> 
<object id="LODOP_OB"
	classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width="0"
	height="0">

	<embed id="LODOP_EM" type="application/x-print-lodop" width="0"
		height="0" pluginspage="install_lodop32.exe"></embed>

</object>


		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var listFormDialog;
			var userUnitRels = ${userUnitRels};
			//初始数据备份
			var processUserUnitRels = ${userUnitRels};
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker','duallistbox','selectbox'], function(A){
					var demo1 = $('select[name="duallistbox_demo1[]"]').bootstrapDualListbox({infoTextFiltered: '<span class="label label-purple label-lg">Filtered</span>'});
					var container1 = demo1.bootstrapDualListbox('getContainer');
					container1.find('.btn').addClass('btn-white btn-info btn-bold');
					var dedit;
					var conditions=[];
					var equipList=[];
					var nameList='';
					var codeList='';
					var equipManageTypeList='';
					var equipTypeList='';
					var equipTypeNameList='';
					var idList='';
					var ids = $("#ids").val();
					var equipIds = [];
					var searchequipName=new A.selectbox({
						id: 'searchequipName',
						name:'searchequipName',
						title:'设备台账',
						url:'/equipLedger/selectEquipLedger',
						render:'#searchequipNameDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data){
// 							if(data&&data[0]){
// 								$("#searchequipName").val(data[0].name);
// 							};
							for(var i=0; i< data.length;i++){
								if(equipIds.length>0){
									 if(!contains(equipIds,data[i].id)){
										 equipIds.push(data[i].id);
										 codeList += data[i].code+',';
		 								 nameList += data[i].name+',';
		 								 equipManageTypeList += data[i].equipManageTypeName+',';
		 								 equipTypeList += data[i].equipType+',';
		 								 equipTypeNameList += data[i].equipTypeName+',';
									 }
								}else{
									equipIds.push(data[i].id);
									 codeList += data[i].code+',';
	 								 nameList += data[i].name+',';
	 								 equipManageTypeList += data[i].equipManageTypeName+',';
	 								 equipTypeList += data[i].equipType+',';
	 								 equipTypeNameList += data[i].equipTypeName+',';
								}
							    }
							searchequipName.setValue(nameList,nameList);
						}
					}).render();
					//部门控件下拉树
// 						var searchunitId = new A.combotree({
// 						render: "#searchunitIdDiv",
// 						name: 'searchunitId',
// 						//返回数据待后台返回TODO
// 						datasource: ${unitNameIdTreeList},
// 						width:"110px",
// 						options: {
// 							treeId: 'unitId',
// 							data: {
// 								key: {
// 									name: "name"
// 								},
// 								simpleData: {
// 									enable: true,
// 									idKey: "id",
// 									pIdKey: "parentId"
// 								}
// 							}
// 						}
// 					}).render();
						//鉴定人
						var searchunitId = new A.combobox({
							render : "#searchunitIdDiv",
							datasource : ${unitNameIdTreeList},
							allowBlank: true,
							options : {
								"disable_search_threshold" : 10
							}
						}).render();
					var searchStartfindTime = new A.my97datepicker({
						id: 'searchStartfindTime',
						name:'searchStartfindTime',
						render:'#searchStartfindTimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					var searchEndfindTime = new A.my97datepicker({
						id: 'searchEndfindTime',
						name:'searchEndfindTime',
						render:'#searchEndfindTimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'searchStartfindTime\\')}",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					//鉴定人
					var searchappraisalUserId = new A.combobox({
						render : "#searchappraisalUserId",
						datasource : ${searchuser},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					var searchsolveUserId = new A.combobox({
						render : "#searchsolveUserId",
						datasource : ${searchuser},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					//发现人
					var searchfindUserId = new A.combobox({
						render : "#searchfindUserId",
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
					var searchequipTypes = new A.combobox({
						render : "#searchequipTypes",
						datasource : ${equipType},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					var searchtype = new A.combobox({
						render : "#searchtype",
						datasource : ${searchtype},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					var exportExcel="";
					var defectDatatables = new A.datatables({
						render: '#defect-table',
						options: {
					        "ajax": {
					            "url": format_url("/defect/search"),
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
					            	//上一条、下一条代码
					            	dedit=d;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 100,
							bStateSave: true,
							searching: true,
							//aLengthMenu: [5],
							//iCookieDuration:'${cookieTime}',
							iCookieDuration:cookieTime,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
// 							          {data: "code",width: "100px",orderable: true}, 
							          {data: "unitName",width: "10%",orderable: true,
										render : function(data, type, row, meta) {
											if(row.defectTime!=null){
												var defectTime =new Date(row.defectTime).getTime();
												var findTime = new Date(row.findTime).getTime();
												var time = defectTime-findTime;
												var day = parseInt(time/(24*3600*1000));
			                                  if(day>7){
			                                      return "<span style='color:red'>"+data+"</span>";
			                                      }return data;
			                                   }
											return data;
											}	
											}, 
							          {data: "equipName",width: "10%",orderable: true,
												render : function(data, type, row, meta) {
													if(row.defectTime!=null){
														var defectTime =new Date(row.defectTime).getTime();
														var findTime = new Date(row.findTime).getTime();
														var time = defectTime-findTime;
														var day = parseInt(time/(24*3600*1000));
					                                  if(day>7){
					                                      return "<span style='color:red'>"+data+"</span>";
					                                      }return data;
					                                   }
													if(data!=null && data.length>20){
														return "<div title='"+data+"'>"+data.substring(0,20)+"..."+"</div>";  
													}else{
														return data;  
													}
													}}, 
							          {data: "findUserName",width: "10%",orderable: true,
														render : function(data, type, row, meta) {
															if(row.defectTime!=null){
																var defectTime =new Date(row.defectTime).getTime();
																var findTime = new Date(row.findTime).getTime();
																var time = defectTime-findTime;
																var day = parseInt(time/(24*3600*1000));
							                                  if(day>7){
							                                      return "<span style='color:red'>"+data+"</span>";
							                                      }return data;
							                                   }
															return data;
															}}, 
							          {data: "findTime",width: "10%",orderable: true, "sClass": "center",
																render : function(data, type, row, meta) {
																	if(row.defectTime!=null){
																		var defectTime =new Date(row.defectTime).getTime();
																		var findTime = new Date(row.findTime).getTime();
																		var time = defectTime-findTime;
																		var day = parseInt(time/(24*3600*1000));
									                                  if(day>7){
									                                      return "<span style='color:red'>"+data+"</span>";
									                                      }return data;
									                                   }
																	return data;
																	}}, 
							          {data: "defectTime",width: "10%",orderable: true, "sClass": "center",
																		render : function(data, type, row, meta) {
																			if(row.defectTime!=null){
																				var defectTime =new Date(row.defectTime).getTime();
																				var findTime = new Date(row.findTime).getTime();
																				var time = defectTime-findTime;
																				var day = parseInt(time/(24*3600*1000));
											                                  if(day>7){
											                                      return "<span style='color:red'>"+data+"</span>";
											                                      }return data;
											                                   }
																			return data;
																			}}, 
							          {data: "typeName",width: "10%",orderable: true,
																				render : function(data, type, row, meta) {
																					if(row.defectTime!=null){
																						var defectTime =new Date(row.defectTime).getTime();
																						var findTime = new Date(row.findTime).getTime();
																						var time = defectTime-findTime;
																						var day = parseInt(time/(24*3600*1000));
													                                  if(day>7){
													                                      return "<span style='color:red'>"+data+"</span>";
													                                      }return data;
													                                   }
																					return data;
																					}},
							          {data: "depict",width: "10%",orderable: true,
							        	  render : function(data, type, row, meta) {
							        		  if(data!=null &&  data.length>20){
					                                 return "<div title='"+data+"'>"+data.substring(0,20)+"..."+"</div>";  
							        		  }else if(row.defectTime!=null){
							        			  var defectTime =new Date(row.defectTime).getTime();
													var findTime = new Date(row.findTime).getTime();
													var time = defectTime-findTime;
													var day = parseInt(time/(24*3600*1000));
				                                  if(day>7){
				                                      return "<span style='color:red'>"+data+"</span>";
				                                      }return data;
							        		  }else{
					                                 return data;  
							        		  }
				                   		  } 
							          }, 
							          {data: "appraisalUserName",name:"appraisalUserId",width: "9%",orderable: true,
											render : function(data, type, row, meta) {
												if(row.defectTime!=null){
													var defectTime =new Date(row.defectTime).getTime();
													var findTime = new Date(row.findTime).getTime();
													var time = defectTime-findTime;
													var day = parseInt(time/(24*3600*1000));
				                                  if(day>7){
				                                      return "<span style='color:red'>"+data+"</span>";
				                                      }return data;
				                                   }
												return data;
												}}, 
							          {data: "solveUserName",name:"solveUserId",width: "9%",orderable: true,
													render : function(data, type, row, meta) {
														if(row.defectTime!=null){
															var defectTime =new Date(row.defectTime).getTime();
															var findTime = new Date(row.findTime).getTime();
															var time = defectTime-findTime;
															var day = parseInt(time/(24*3600*1000));
						                                  if(day>7){
						                                      return "<span style='color:red'>"+data+"</span>";
						                                      }return data;
						                                   }
														return data;
														}}, 
							          {data: "processStatusName",name:"processStatus",width: "19%",orderable: true,
															render : function(data, type, row, meta) {
																if(row.defectTime!=null){
																	var defectTime =new Date(row.defectTime).getTime();
																	var findTime = new Date(row.findTime).getTime();
																	var time = defectTime-findTime;
																	var day = parseInt(time/(24*3600*1000));
								                                  if(day>7){
								                                      return "<span style='color:red'>"+data+"</span>";
								                                      }return data;
								                                   }
																return data;
																}}
							],
							fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
								 if(exportExcel){
									 exportExcels(format_url("/defect/exportExcel"),JSON.stringify(conditions));
								 }
								 exportExcel="";
							 },
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							A.loadPage({
											render : '#page-container',
											url : format_url('/defect/getAdd')
										});
            						}
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = defectDatatables.getSelectRowDatas();
										var ids = [];
										var userIds = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
												userIds.push(data[i].findUserId);
											}
										}
										var loginUser = '${sysUserEntity.id}';
										var loginName = '${sysUserEntity.loginName}'
										for(var j=0;j<userIds.length;j++){
											if(userIds[j]!=loginUser&&loginName!="super"){
												alert('记录中包含不是当前登陆人的记录不能删除!');
												return;
											}
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										var url = format_url('/defect/bulkDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'POST',
												data : JSON.stringify(ids),
												success: function(result){
        											if(result.result=="success"){
        												alert('删除成功');	
        												defectDatatables.draw(false);
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
									var userId = ${sysUserEntity.id}
									var loginName = '${sysUserEntity.loginName}'
									if(data.processStatus!="1"&&data.processStatus!="0"){
										btnNode.hide();
									}
									if(data.findUserId!=userId&&loginName!="super"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										//上一条、下一条代码
										dedit.length=1;
										dedit.start=nData.start;
										$.ajax({url : format_url('/defect/getEdit/' + id),
											contentType : 'application/json',
    										type : 'POST',
											data : JSON.stringify(dedit),
											success : function(result) {
												var divshow = $("#page-container");
												divshow.text("");// 清空数据
												divshow.append(result); // 添加Html内容，不能用Text 或 Val
											}
										});
									}
								}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								render: function(btnNode, data){
									var userId = ${sysUserEntity.id}
									var loginName = '${sysUserEntity.loginName}'
									if(data.processStatus!="1"&&data.processStatus!="0"){
										btnNode.hide();
									}
									if(data.findUserId!=userId&&loginName!="super"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/defect/delete/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'POST',
        										success: function(result){
        											if(result.result=="success"){
        												alert('删除成功');	
        												defectDatatables.draw(false);
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
						}, {
							id:"submit",
							label:"提交",
							icon: "fa fa-check-square-o bigger-130",
							className: "edit",
							render: function(btnNode, data){
								var userId = ${sysUserEntity.id}
								var loginName = '${sysUserEntity.loginName}'
								if(!(data.processStatus==0||data.processStatus==1)){
									btnNode.hide();
								}
								if(data.findUserId!=userId){
									btnNode.hide();
								}
							},
							events:{
								click: function(event, nRow, nData){
									if(!(nData.processStatus==0||nData.processStatus==1)){
										alert("该记录不是待提交或驳回状态，不能提交！");
										return;
									}
									listFormDialog = new A.dialog({
										width: 850,
										height: 481,
										title: "缺陷管理提交人",
										url:format_url('/defect/sureSubmitPerson'),
										closed: function(resule){
											var obj=new Object(); 
											var id = nData.id;
											var url =format_url('/defect/submit/'+ id);
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
				    												defectDatatables.draw(false);
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
						id: "detail",
						label:"查看",
						icon: "fa fa-binoculars bigger-110",
						className: "blue ",
						events:{
							click: function(event, nRow, nData){
								var id = nData.id;
								A.loadPage({
									render : '#page-container',
									url : format_url('/defect/getDetail/' + id)
								});
							}
						}
					}
					,{
						id: "print",
						label:"打印",
						icon: "glyphicon glyphicon-print",
						className: "blue ",
						render: function(btnNode, data){
							if((data.processStatus==0||data.processStatus==1)){
								btnNode.hide();
							}
						},
						events:{
							click: function(event, nRow, nData){
								birtPrint("zjk_defect.rptdesign",nData.id);
							}
						}
// 						events:{
// 							click: function(event, nRow, nData){
// 								birtPrint("zjk_defect.rptdesign",nData.id);
// 							}
// 						}
					}
					]
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
						
						if($('#searchunitIdDiv').val()){
	    					conditions.push({
	        					field: 'T.C_UNIT_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:searchunitId,
	        					type:2,
	        					value:$('#searchunitIdDiv').val()
	        				});
						}
						if($('#searchequipName').val()){
	    					conditions.push({
	        					field: 'T.C_EQUIP_NAME',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:'searchequipName',
	        					type:1,
	        					value:$('#searchequipName').val()
	        				});
						}
						if($('#searchfindUserId').val()){
	    					conditions.push({
	        					field: 'T.C_FIND_USER_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:'searchfindUserId',
	        					type:1,
	        					value:$('#searchfindUserId').val()
	        				});
						}
						if($('#searchappraisalUserId').val()){
	    					conditions.push({
	        					field: 'T.C_APPRAISAL_USER_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:'searchappraisalUserId',
	        					type:1,
	        					value:$('#searchappraisalUserId').val()
	        				});
						}
						if($('#searchsolveUserId').val()){
	    					conditions.push({
	        					field: 'T.C_SOLVE_USER_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:'searchsolveUserId',
	        					type:1,
	        					value:$('#searchsolveUserId').val()
	        				});
						}
						if($('#searchStartfindTime').val()){
	    					conditions.push({
	    						field: 'T.C_FIND_TIME',
	    						fieldType:'DATE',
	    						matchType:'GE',
	    						name:'searchStartfindTime',
	        					type:1,
	    						value:$('#searchStartfindTime').val()
	    					});
						}
						if($('#searchEndfindTime').val()){
	    					conditions.push({
	    						field: 'T.C_FIND_TIME',
	    						fieldType:'DATE',
	    						matchType:'LE',
	    						name:'searchEndfindTime',
	        					type:1,
	    						value:$('#searchEndfindTime').val()
	    					});
						}
						if($('#searchtype').val()){
	    					conditions.push({
	        					field: 'T.C_TYPE',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:'searchtype',
	        					type:1,
	        					value:$('#searchtype').val()
	        				});
						}
						if($('#searchdepict').val()){
	    					conditions.push({
	        					field: 'T.C_DEPICT',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					name:'searchdepict',
	        					type:1,
	        					value:$('#searchdepict').val()
	        				});
						}
						if($('#searchprocessStatus').val()){
	    					conditions.push({
	        					field: 'T.C_PROCESS_STATUS',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:'searchprocessStatus',
	        					type:1,
	        					value:$('#searchprocessStatus').val()
	        				});
						}
						if($('#searchequipTypes').val()){
	    					conditions.push({
	        					field: 'T.C_EQUIP_TYPE',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:'searchequipTypes',
	        					type:1,
	        					value:$('#searchequipTypes').val()
	        				});
						}
						defectDatatables._datatables.search(JSON.stringify(conditions)).draw();
					});
					$('#btnReset').on('click',function(){
						$("#searchcode").val('');
						$("#searchunitIdDiv").val("");
						$("#searchunitIdDiv").trigger("chosen:updated");
						$("#searchequipName").val("");
						$("#searchfindUserId").val("");
						$("#searchfindUserId").trigger("chosen:updated");
						$("#searchappraisalUserId").val("");
						$("#searchappraisalUserId").trigger("chosen:updated");
						$("#searchsolveUserId").val("");
						$("#searchsolveUserId").trigger("chosen:updated");
						$("#searchtype").val("");
						$("#searchtype").trigger("chosen:updated");
						$("#searchprocessStatus").val("");
						$("#searchprocessStatus").trigger("chosen:updated");
						$("#searchequipTypes").val("");
						$("#searchequipTypes").trigger("chosen:updated");
						$("#searchStartfindTime").val("");
						$("#searchEndfindTime").val("");
						$("#searchdepict").val("");
						processUserUnitRels = [];
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
</html>