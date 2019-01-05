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
				<li class="active">运行管理</li>
				<li class="active">保护投退</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
		
		<div class="col-lg-12 col-md-12 col-sm-12 padding-zero search-content">
				<div class="form-inline text-left " role="form">
				 <div class="clearfix">
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="form-field-1">单位名称</label>：
                              <div id="searchunitIdDiv"  class="input-width text-left"></div>
                        </div>
                         <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="form-field-1">申请人</label>：
                              <div class="padding-zero inputWidth  text-left">
                               <select id="selapplyPersonIdDiv"  name="selapplyPersonIdDiv"></select>
                            </div>
                        </div>
                        <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero" >
                            <label class="searchLabel" for="form-field-1">申请时间</label>：
                              <div class="form-group dateInputOther padding-zero text-left" >
                            	<div id="searchStartfindTimeDiv"  style="border:none; padding:0px;"></div>
                            	</div>
                            <div class="toLabel" for="searchStartTimeRightDiv" >~</div>
		                    <div class="form-group dateInputOther padding-zero text-left">
		                        <div id="searchEndfindTimeDiv"></div>
		                    </div>
                        </div>
                        
                    </div>
				 <div class="clearfix">
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="form-field-1">申请方式</label>：
                            <div class="padding-zero inputWidth  text-left" style="width: 66%">
                              <select id="applyTypeDiv"  name="applyPersonId"></select>
                              </div>
                        </div>
                         <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="form-field-1">执行人</label>：
                              <div class="padding-zero inputWidth  text-left">
                             	<select id="executePersonDiv"  name="applyPersonId"></select>
                            </div>
                        </div>
                        <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero" >
                            <label class="searchLabel" for="form-field-1">执行结束时间</label>：
                              <div class="form-group dateInputOther padding-zero text-left" >
                            	<div id="searchStartexecuteEndtimeDiv"  style="border:none; padding:0px;"></div>
                            	</div>
                            <div class="toLabel" for="searchStartTimeRightDiv" >~</div>
		                    <div class="form-group dateInputOther padding-zero text-left">
		                        <div id="searchEndexecuteEndtimeDiv"></div>
		                    </div>
                        </div>
                        
                    </div>
				 <div class="clearfix">
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="form-field-1">设备名称</label>：
                           <div id="searchequipNameDiv" class="input-width  text-left padding-zero"></div>
                        </div>
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="form-field-1">保护变动方式</label>：
                           <div class="padding-zero inputWidth  text-left">
                           <select id="selprotectWayDiv"  name="protectWay"></select>
                           </div>
                        </div>
                         
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="form-field-1">状态</label>：
                           <div class="padding-zero inputWidth  text-left">
                           <select id="protectStatusDiv"  name="protectWay"></select>
                           </div>
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
                        <div id="tableTitle"></div>
						<table id="protect-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>单位名称</th>
	                                <th>设备名称 </th>
	                                <th>保护变动方式</th>
	                                <th>申请人</th>
	                                <th>申请时间</th>
	                                <th>申请方式</th>
	                                <th>执行人</th>
	                                <th>执行结束时间</th>
<!-- 	                                <th>保护变动原因</th>	                                	                                	                                 -->
	                                <th>状态</th>	                                
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
			var listFormDialog;
			var userUnitRels = ${userUnitRels};
			//初始数据备份
			var processUserUnitRels = ${userUnitRels};
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker','selectbox'], function(A){
					var conditions=[];
								$('#tableTitle').html('<h5 class="table-title header smaller blue" >保护投退</h5>');
								var searchequipName=new A.selectbox({
									id: 'searchequipName',
									name:'searchequipName',
									title:'设备台账',
									url:'/equipLedger/selectEquipLedger',
									render:'#searchequipNameDiv',
									width:'1100',//弹出窗体的宽度  可以不写这行
									hight:'780',//弹出窗体的高度  可以不写这行
									callback: function(data){
										if(data&&data[0]){
											$("#searchequipName").val(data[0].name);
										};
										
									}
								}).render();
								//combotree组件
								var searchunitId = new A.combotree({
									render: "#searchunitIdDiv",
									name: "searchunitId",
									//返回数据待后台返回TODO
									datasource: ${protectTreeList},
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
										}
									}
								}).render();
								var applyPersonIdCombobox = new A.combobox({
									render: "#selapplyPersonIdDiv",
									datasource:${protectCombobox},
									//multiple为true时select可以多选
									multiple:false,
									//allowBlank为false表示不允许为空
									allowBlank: true,
									width:"210px",
									options:{
										"disable_search_threshold":10
									}
									}).render();
								var protectWayCombobox = new A.combobox({
									render: "#selprotectWayDiv",
									datasource:${protectWayCombobox},
									//multiple为true时select可以多选
									multiple:false,
									//allowBlank为false表示不允许为空
									allowBlank: true,
									options:{
										"disable_search_threshold":10,
										"width":"100%"	
									}
									}).render()
								var executePersonCombobox = new A.combobox({
									render: "#executePersonDiv",
									datasource:${protectCombobox},
									multiple:false,
									allowBlank: true,
									width:"210px",
									options:{
										"disable_search_threshold":10
									}
									}).render();
								var applyTypeCombobox = new A.combobox({
									render: "#applyTypeDiv",
									datasource:${applyTypeCombobox},
									multiple:false,
									allowBlank: true,
									width:"210px",
									options:{
										"disable_search_threshold":10
									}
									}).render();
								var protectStatusCombobox = new A.combobox({
									render: "#protectStatusDiv",
									datasource:${protectStatusCombobox},
									multiple:false,
									allowBlank: true,
									options:{
										"disable_search_threshold":10,
										"width":"100%"	
									}
									}).render();
								var searchStartexecuteEndtime = new A.my97datepicker({
									id: 'searchStartexecuteEndtime',
									name:'searchStartexecuteEndtime',
									render:'#searchStartexecuteEndtimeDiv',
									options : {
											isShowWeek : false,
											skin : 'ext',
											maxDate: "#F{$dp.$D(\\'searchEndexecuteEndtime\\')}",
											minDate: "",
											dateFmt: "yyyy-MM-dd "
									}
								}).render();
								var searchEndexecuteEndtime = new A.my97datepicker({
									id: 'searchEndexecuteEndtime',
									name:'searchEndexecuteEndtime',
									render:'#searchEndexecuteEndtimeDiv',
									options : {
											isShowWeek : false,
											skin : 'ext',
											maxDate: "",
											minDate: "#F{$dp.$D(\\'searchStartexecuteEndtime\\')}",
											dateFmt: "yyyy-MM-dd "
									}
								}).render();
								var searchStartfindTime = new A.my97datepicker({
									id: 'searchStartfindTime',
									name:'searchStartfindTime',
									render:'#searchStartfindTimeDiv',
									options : {
											isShowWeek : false,
											skin : 'ext',
											maxDate: "#F{$dp.$D(\\'searchEndfindTime\\')}",
											minDate: "",
											dateFmt: "yyyy-MM-dd "
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
											dateFmt: "yyyy-MM-dd "
									}
								}).render();
								var exportExcel="";	
					var protectDatatables = new A.datatables({
						render: '#protect-table',
						options: {
					        "ajax": {
					            "url": format_url("/protect/search"),
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
							 order: [[ 0, "desc" ]],
							columns: [{data:"id", visible:false,orderable:true}, 
							          {name:"unitId",data: "unitName",width: "12%",orderable: true}, 
							          {data: "equipName",width: "10%",orderable: true,
							        	  render : function(data, type, row, meta) {
												if(data!=null && data.length>20){
													return "<div title='"+data+"'>"+data.substring(0,20)+"..."+"</div>";  
												}else{
													return data;  
												}
									  		}}, 
							          {data: "protectWayName",width: "8%",orderable: true,							        	  
							        	  "createdCell": function (td, cellData, rowData, row, col) {
								        	     if (rowData.protectWay=="quit") {
								        	       	$(td).attr('style','color:red');								        	       	
								        	     }
								        	}, 
							          }, 
							          {name:"applyPersonId",data: "applyPersonName",width: "10%",orderable: true}, 
							          {data: "applyTimeDay",name:"applyTime",width: "11%",orderable: true}, 
							          {data: "applyTypeName",name:"applyType",width: "10%",orderable: true,							        	  
							        	  "createdCell": function (td, cellData, rowData, row, col) {
								        	     if (rowData.applyType=="0") {
								        	       	$(td).attr('style','color:red');								        	       	
								        	     }
								        	}, 
							          }, 
							          {data: "executePersonName",width: "10%",orderable: true}, 
							          {data: "executeEndtimeDay",name:"executeEndtime",width: "10%",orderable: true},							           							          
// 							          {data: "protectReason",width: "14%",orderable: true},
							          {data: "processStatusName",name:"checkState",width: "10%",orderable: true}],
							    fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											 if(exportExcel){
												 exportExcels(format_url("/protect/exportExcel"),JSON.stringify(conditions));
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
											url : format_url("/protect/getAdd"),
										});
            						}
        						}
        					}, 
        					<p:purview code="protectDelAdmin">
        					{
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = protectDatatables.getSelectRowDatas();
										var ids = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
											}
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										var url = format_url('/protect/batchDelete/admin');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													if(result.result=="success"){
													alert('删除成功');
													protectDatatables.draw(false);
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
							},
							</p:purview>
							<p:purview code="protectDel">
        					{
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								render: function(btnNode, data){
									if(data.checkState!=1){
										btnNode.hide();
									}
								},
								events:{
									click: function(event){
										var data = protectDatatables.getSelectRowDatas();
										var ids = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
											}
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										var url = format_url('/protect/batchDelete/0');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													if(result.result=="success"){
													alert('删除成功');
													protectDatatables.draw(false);
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
							},
							</p:purview>
							{
								label:"导出",
								icon:"glyphicon glyphicon-download",
        						className:"btn-primary",
								events:{
		    						click:function(event){
		    							$('#btnSearch').click();
            							exportExcel="exportExcel";
// 		    							window.location.href=format_url("/protect/exportExcel"); 
		    						}
								}
							}],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green",
								render: function(btnNode, data){
									if(data.checkState!=1&&data.checkState!=0&&data.checkState!=8&&data.checkState!=9&&data.checkState!=12&&data.checkState!=14){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										if(nData.checkState!=1&&nData.checkState!=0&&nData.checkState!=8&&nData.checkState!=9&&nData.checkState!=12&&nData.checkState!=14){
											alert("该记录不是待提交或驳回状态，不能修改！");
											return;
										}
										var id = nData.id;
										A.loadPage({
											render : '#page-container',
											url : format_url("/protect/getEdit/" + id),
										});
									}
								}
							}, 
								<p:purview code="protectDelAdmin">
								{
								id:"delete1",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								render: function(btnNode, data){
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/protect/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){ 
        											alert('删除成功');
        											protectDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						},
						</p:purview>
						<p:purview code="protectDel">
						{
						id:"delete",
						label:"删除",
						icon: "fa fa-trash-o bigger-130",
						className: "red del",
						render: function(btnNode, data){
							if(data.checkState!=1){
								btnNode.hide();
							}
						},
						events:{
							click: function(event, nRow, nData){
								var id = nData.id;
								var url =format_url('/protect/'+ id);
								A.confirm('您确认删除么？',function(){
									$.ajax({
										url : url,
										contentType : 'application/json',
										dataType : 'JSON',
										type : 'DELETE',
										success: function(result){
											alert('删除成功');
											protectDatatables.draw(false);
										},
										error:function(v,n){
											alert('操作失败');
										}
									});
								});
							}
						}
				},
				</p:purview>
						{
								id: "detail",
								label:"查看",
								icon: "fa fa-binoculars",
								className: "blue ",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										A.loadPage({
											render : '#page-container',
											url : format_url("/protect/getDetail/" + id),
										});
									}
								}
							}, {
							id:"submit",
							label:"提交审批",
							icon: "fa fa-check-square-o bigger-130",
							className: " edit",
							render: function(btnNode, data){
								if(data.checkState!=1){
									btnNode.hide();
								}
							},
							events:{
								click: function(event, nRow, nData){
									debugger;
									var id = nData.id;
									if(nData.checkState!=1){
										alert("该记录不是待提交状态，不能提交！");
										return;
									}
									listFormDialog = new A.dialog({
										width: 850,
										height: 501,
										title: "保护投退审批人",
										url:format_url('/protect/submitPerson/'+id),
										closed: function(resule){
											var obj=new Object(); 
											var id = nData.id;
											var url =format_url('/protect/toCheck/'+ id);
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
				    												protectDatatables.draw(false);
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
					}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						if($('#applyTypeDiv').val()){
	    					conditions.push({
	        					field: 'a.C_APPLY_TYPE',
	        					fieldType:'INT',
	        					matchType:'EQ',
	        					value:$('#applyTypeDiv').val()
	        				});
						}
						if($('#executePersonDiv').val()){
	    					conditions.push({
	        					field: 'a.C_EXECUTE_PERSON_ID',
	        					fieldType:'INT',
	        					matchType:'EQ',
	        					value:$('#executePersonDiv').val()
	        				});
						}
						if($('#protectStatusDiv').val()){
	    					conditions.push({
	        					field: 'a.C_CHECK_STATE',
	        					fieldType:'INT',
	        					matchType:'EQ',
	        					value:$('#protectStatusDiv').val()
	        				});
						}
						if($('#searchStartexecuteEndtime').val()){
	    					conditions.push({
	    						field: 'a.C_EXECUTE_ENDTIME',
	    						fieldType:'DATE',
	    						matchType:'GE',
	    						value:$('#searchStartexecuteEndtime').val()
	    					});
						}
						if($('#searchEndexecuteEndtime').val()){
	    					conditions.push({
	    						field: 'a.C_EXECUTE_ENDTIME',
	    						fieldType:'DATE',
	    						matchType:'LE',
	    						value:$('#searchEndexecuteEndtime').val()
	    					});
						}
						if($('#searchStartfindTime').val()){
	    					conditions.push({
	    						field: 'a.C_APPLY_TIME',
	    						fieldType:'DATE',
	    						matchType:'GE',
	    						value:$('#searchStartfindTime').val()
	    					});
						}
						if($('#searchEndfindTime').val()){
	    					conditions.push({
	    						field: 'a.C_APPLY_TIME',
	    						fieldType:'DATE',
	    						matchType:'LE',
	    						value:$('#searchEndfindTime').val()
	    					});
						}
						if($('#selapplyPersonIdDiv').val()){
	    					conditions.push({
	        					field: 'a.C_APPLY_PERSON_ID',
	        					fieldType:'INT',
	        					matchType:'EQ',
	        					value:$('#selapplyPersonIdDiv').val()
	        				});
						}
						if($('#selprotectWayDiv').val()){
	    					conditions.push({
	        					field: 'a.C_PROTECT_WAY',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#selprotectWayDiv').val()
	        				});
						}
						if($('#searchequipName').val()){
	    					conditions.push({
	        					field: 'f.C_NAME',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#searchequipName').val()
	        				});
						}
						if($('#protectReason').val()){
	    					conditions.push({
	        					field: 'a.C_PROTECT_REASON',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$('#protectReason').val()
	        				});
						}
						if(searchunitId.getValue()!=undefined&&searchunitId.getValue()+""!=""){
	    					conditions.push({
	        					field: 'a.C_UNIT_ID',
	        					fieldType:'INT',
	        					matchType:'EQ',
	        					value:searchunitId.getValue()
	        				});
						}
						protectDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						searchunitId.setValue(undefined);
						$("#selapplyPersonIdDiv").val("");
						$("#selapplyPersonIdDiv").trigger("chosen:updated");
						$("#selprotectWayDiv").val("");
						$("#selprotectWayDiv").trigger("chosen:updated");
						$("#searchStartfindTime").val("");
						$("#searchEndfindTime").val("");
						$("#protectReason").val("");
						$("#searchequipName").val("");
						$("#applyTypeDiv").val("");
						$("#applyTypeDiv").trigger("chosen:updated");
						$("#executePersonDiv").val("");
						$("#executePersonDiv").trigger("chosen:updated");
						$("#protectStatusDiv").val("");
						$("#protectStatusDiv").trigger("chosen:updated");
						$("#searchStartexecuteEndtime").val("");
						$("#searchEndexecuteEndtime").val("");
						processUserUnitRels = [];
					});
				});
			});
        </script>
    </body>
</html>