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
				<li class="active">典型操作票</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-lg-12 col-md-12 col-sm-12 search-content padding-zero ">
				<div class="form-inline  text-left" role="form">
					<div class="clearfix">
			           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero " >
							<label class="searchLabel">典型票名称</label>：
			                <input id="searchname" class="input-width text-left" placeholder="请输入名称" type="text" >
	                   </div>
			           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" >
							<label class="searchLabel" for="form-field-1">典型票类型</label>：
							 <div class="padding-zero input-width  text-left">
							 <select id="searchtype" class="col-md-8" name="searchtype"></select>
							 </div>
	                   </div>
			           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
							<label class="searchLabel" for="form-field-1">单位名称</label>：
							 <div id="searchunitIdDiv" class="input-width text-left padding-zero"></div>
	                   </div>
			           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
							<label class="searchLabel" for="form-field-1">设置人</label>：
							 <div class="padding-zero input-width  text-left">
	                   		 <select id="searchcreatePersonId" class="col-md-12" name="searchcreatePersonId"></select>
	                   		 </div>
	                   </div>
	               </div>
	               <div class="clearfix">
	              		 <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
	                            <label class="searchLabel" for="searchprocessStatus">状态</label>：
	                            <div class="input-width padding-zero  text-left">
	                                <select id="searchapproveStatus"  name="searchapproveStatus"></select>
	                            </div>
	                          </div>
	                          <div class="form-group col-lg-7 col-md-7 col-sm-6 col-xs-12 padding-zero">
		                      </div>
							 <div class="form-group col-lg-2 col-md-2 col-sm-2 col-xs-12 padding-zero btnSearchBottom" style="text-align:right;  ">
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
						<h5 class='table-title header smaller blue' >典型操作票</h5>
						<table id="typicalTicket_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>典型票名称</th>
	                                <th>典型票类型</th>
	                                <th>单位名称</th>
	                                <th>设置人</th>
	                                <th>设置时间</th>
	                                <th>状态</th>
<!-- 	                                <th>鉴定结果</th> -->
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
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					var searchapproveStatus = new A.combobox({
						render : "#searchapproveStatus",
						datasource : ${typicalTicketApproveStatus},
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
					//监护人
					var searchcreatePersonId = new A.combobox({
						render : "#searchcreatePersonId",
						datasource : ${searchuser},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					//票类型
					var searchtypeId = new A.combobox({
						render : "#searchtype",
						datasource : ${typicalTicketType},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					var exportExcel="";
					var typicalTicketDatatables = new A.datatables({
						render: '#typicalTicket_table',
						options: {
					        "ajax": {
					            "url": format_url("/typicalTicket/search"),
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
					            	}else{
					            		d.conditions=[];
					            	}
					            	d.conditions.push({
		        						field: 'C_STATUS',
		        						fieldType:'STRING',
		        						matchType:'LIKE',
		        						value:1
		        					});
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 200,
							//aLengthMenu: [2],
							bStateSave: true,
							searching: true,
							iCookieDuration:cookieTime,
							columns: [{data:"id", visible:false,orderable:false},
							          {data: "name",width: "16%",orderable: true},
							          {data: "typeName",name:"type",width: "16%",orderable: true},
							          {data: "unitName",width: "16%",orderable: true},
							          {data: "createPersonName",width: "15%",orderable: true},
							          {data: "createDate",width: "16%",orderable: true, "sClass": "center"},
							          {data: "approveStatusName",name:"approveStatus",width: "13%",orderable: true},
// 							          {data: "identifyName",name:"identify",width: "6%",orderable: true}
							          ],
						    fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											 if(exportExcel){
												 exportExcels(format_url("/typicalTicket/exportExcel"),JSON.stringify(conditions));
											 }
											 exportExcel="";
							},
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							listFormDialog = new A.dialog({
											width:850 ,
											height:200 ,
											title: "典型票新增",
											url:format_url('/typicalTicket/getAdd'),
											closed: function(){
												typicalTicketDatatables.draw(false);
											}
										}).render();
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
									if(!(data.approveStatus==0||data.approveStatus==1||data.approveStatus==6)){
										btnNode.hide();
									}
									if(data.istypical==1){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var workPerson = nData.createPersonId;
										var loginUser = '${userEntity.id}';
										
			 							if(workPerson!=loginUser){
			 								alert("当前登陆人不是设置人不能修改!");
			 								return;
			 							}else{
// 										var id = nData.id;
// 										listFormDialog = new A.dialog({
// 											width:850 ,
// 											height:200 ,
// 											title: "修改",
// 											url:format_url('/typicalTicket/getEdit/' + id),
// 											closed: function(){
// 												typicalTicketDatatables.draw(false);
// 											}
// 										}).render();

			 								var id = nData.id;
											var url="";
											//每一个票的修改页面
											if(nData.type==1){
												url=format_url('/workTicket/getEdit/'+ nData.workticket_id,{"typicalTicketId":id});
											}
											if(nData.type==2){
												url=format_url('/workTicketTwo/getEdit/'+ nData.workticket_id,{"typicalTicketId":id});
											}
											if(nData.type==3){
												if(nData.istypical==1){//设置的典型操作票
// 													url=format_url('/operationTicket/getDetailDx/'+ nData.workticket_id,{"typicalTicketId":id});
													url=format_url('/operationTicket/getDetailDx/'+ nData.workticket_id+"/"+id);
												}else{
// 													url=format_url('/operationTicket/getEditDx/'+ nData.workticket_id,{"typicalTicketId":id});
													url=format_url('/operationTicket/getEditDx/'+ nData.workticket_id+"/"+id);
												}
											}
											if(nData.type==4){	
												url=format_url('/interventionTicket/getEdit/'+ nData.workticket_id,{"typicalTicketId":id});
											}
											A.loadPage({
												render : '#page-container',
												url : url
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
									if(data.approveStatus!="1"&&data.approveStatus!="0"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var workPerson = nData.createPersonId;
										var loginUser = '${userEntity.id}';
										
			 							if(workPerson!=loginUser){
			 								alert("当前登陆人不是设置人不能删除!");
			 								return;
			 							}else{
										var id = nData.id;
										var url =format_url('/typicalTicket/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											if(result.result=="success"){
        												alert('删除成功');	
														typicalTicketDatatables.draw(false);
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
						},{
							id: "detail",
							label:"查看",
							icon: "fa fa-binoculars bigger-110",
							className: "blue",
							
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									A.loadPage({
										render : '#page-container',
// 										url:format_url('/operationTicket/getDetailDx/'+ nData.workticket_id,{"typicalTicketId":id})
										url:format_url('/operationTicket/getDetailDx/'+ nData.workticket_id+"/"+id)
									});
									/* listFormDialog = new A.dialog({
										width:850 ,
										height:181 ,
										title: "查看",
										url:format_url('/typicalTicket/getDetail/' + id),
										closed: function(){
											typicalTicketDatatables.draw(false);
											
										}
									}).render(); */
								}
							}
						}, {
							id:"submit",
							label:"提交",
							icon: "fa fa-check-square-o bigger-130",
							className: "edit",
							render: function(btnNode, data){
								if(!(data.approveStatus==0||data.approveStatus==1 ||data.approveStatus==6)){
									btnNode.hide();
								}
								
								if(data.istypical==1){
									btnNode.hide();
								}
								
								if(!(data.flag=='1'||data.flag=='2')){
									btnNode.hide();
								}
							},
							events:{
								click: function(event, nRow, nData){
									var workPerson = nData.createPersonId;
									var loginUser = '${userEntity.id}';
									
		 							if(workPerson!=loginUser){
		 								alert("当前登陆人不是设置人不能提交!");
		 								return;
		 							}else{
									if(!(nData.approveStatus==0||nData.approveStatus==1||nData.approveStatus==6)){
										alert("该记录不是待提交或驳回状态，不能提交！");
										return;
									}
									
									if(nData.flag=='2'){
										listFormDialog = new A.dialog({
											width: 850,
											height: 481,
											title: "典型票修改提交人",
											url:format_url('/typicalTicket/sureSubmitPerson'),
											closed: function(resule){
												var obj=new Object(); 
												var id = nData.id;
												var url =format_url('/typicalTicket/editSubmit/'+ id);
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
					    												typicalTicketDatatables.draw(false);
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
										
									}else{
										
									listFormDialog = new A.dialog({
										width: 850,
										height: 481,
										title: "典型票新增提交人",
										url:format_url('/typicalTicket/sureSubmitPerson'),
										closed: function(resule){
											var obj=new Object(); 
											var id = nData.id;
											var url =format_url('/typicalTicket/submit/'+ id);
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
				    												typicalTicketDatatables.draw(false);
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
							}
							}
						/* ,{
								id: "isSet",
								label:"设置",
								icon: "fa fa-cog bigger-130",
								className: "edit",
 								render: function(btnNode, data){
 									if(data.istypical==1){
											btnNode.hide();
									  }else{
										  if(!(data.approveStatus=="1")){
											  btnNode.hide();
		 									}
									  }
 								},
								events:{
									click: function(event, nRow, nData){
										var workPerson = nData.createPersonId;
										var loginUser = '${userEntity.id}';
										
			 							if(workPerson!=loginUser){
			 								alert("当前登陆人不是设置人不能设置!");
			 								return;
			 							}else{
										var id = nData.id;
										var url="";
										//每一个票的修改页面
										if(nData.type==1){
											url=format_url('/workTicket/getEdit/'+ nData.workticket_id,{"typicalTicketId":id});
										}
										if(nData.type==2){
											url=format_url('/workTicketTwo/getEdit/'+ nData.workticket_id,{"typicalTicketId":id});
										}
										if(nData.type==3){
											if(nData.istypical==1){//设置的典型操作票
												url=format_url('/operationTicket/getDetailDx/'+ nData.workticket_id,{"typicalTicketId":id});
											}else{
												url=format_url('/operationTicket/getEditDx/'+ nData.workticket_id,{"typicalTicketId":id});
											}
										}
										if(nData.type==4){	
											url=format_url('/interventionTicket/getEdit/'+ nData.workticket_id,{"typicalTicketId":id});
										}
										A.loadPage({
											render : '#page-container',
											url : url
										});
									}
								}
								}
							} */
						]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						if($('#searchname').val()){
        					conditions.push({
        						field: 'C_NAME',
        						fieldType:'STRING',
        						matchType:'LIKE',
        						name:'searchname',
	        					type:1,
        						value:$('#searchname').val()
        					});
						}

						if($('#searchtype').val()){
        					conditions.push({
        						field: 'C_TYPE',
        						fieldType:'STRING',
        						matchType:'EQ',
        						name:'searchtype',
	        					type:1,
        						value:$('#searchtype').val()
        					});
						}
						if(searchunitId.getValue()!=null&&searchunitId.getValue()+""!=""){
	    					conditions.push({
	        					field: 'C_UNIT_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:searchunitId,
	        					type:2,
	        					value:searchunitId.getValue()
	        				});
						}
						if($('#searchcreatePersonId').val()){
        					conditions.push({
        						field: 'C_CREATE_PERSON_Id',
        						fieldType:'STRING',
        						matchType:'EQ',
        						name:'searchcreatePersonId',
	        					type:1,
        						value:$('#searchcreatePersonId').val()
        					});
						}

						if($('#searchapproveStatus').val()){
        					conditions.push({
        						field: 'C_APPROVE_STATUS',
        						fieldType:'STRING',
        						matchType:'EQ',
        						name:'searchapproveStatus',
	        					type:1,
        						value:$('#searchapproveStatus').val()
        					});
						}
						typicalTicketDatatables._datatables.search(JSON.stringify(conditions)).draw();
					});
					$('#btnReset').on('click',function(){
						$('#searchname').val('')
						searchunitId.setValue(null);
						$("#searchtype").val("");
						$("#searchtype").trigger("chosen:updated");
						$("#searchcreatePersonId").val("");
						$("#searchcreatePersonId").trigger("chosen:updated");
						$("#searchapproveStatus").val("");
						$("#searchapproveStatus").trigger("chosen:updated");
					});
				});
			});
        </script>
    </body>
</html>