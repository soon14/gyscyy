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
				<li class="active">安全管理</li>
				<li class="active">事故管理</li>
			</ul>
		</div>
		<div class="page-content">
            <div class="col-lg-12 col-md-12 col-sm-12 search-content  padding-zero">
				<div class="form-inline text-left" role="form">
	             <div class="clearfix">
	             	 <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12 padding-zero">
	                    <label class="searchLabel" for="sjmc">名称</label>：
	                    <input id="name" class="inputWidth text-left contentInput" placeholder="请输入名称" type="text"></input>
	                </div>
	                <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12 padding-zero">
	                    <label class="searchLabel" for="searchgroupId">类别</label>：
	                    <div class="inputWidth padding-zero  text-left">
	                        <select id="sglb" class="form-control chosen-select"></select>
	                    </div>
	                </div>
	             	<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
	                    <label class="searchLabel" for="summaryUnitNameId">单位</label>：
	                     <div class="padding-zero inputWidth  text-left">
	                      <select id="summaryUnitNameId" class="inputWidth text-left padding-zero" ></select>
<!-- 	                    <div id="searchunitIdDiv" class="inputWidth text-left padding-zero"></div> -->
   						</div>
<!-- 	                    <div id="summaryUnitNameId" class="inputWidth text-left padding-zero"></div> -->
	                </div>
	                  <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; ">
	                    <button id="btnSgglSearch" class="btn btn-xs btn-primary">
	                        <i class="glyphicon glyphicon-search"></i>
	                        查询
	                    </button>
	                    <button id="btnReset" class="btn btn-xs btn-primary">
	                        <i class="glyphicon glyphicon-repeat"></i>
	                        重置
	                    </button>	
	                </div>
	            </div>
<!-- 	            <div class="clearfix"> -->
<!-- 	                <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12  padding-zero"> -->
<!-- 	                </div> -->
<!-- 	                <div class="form-group col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero"> -->
<!-- 	                </div> -->
<!-- 	                <div class="form-group col-lg-2 col-md-2 col-sm-2 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; "> -->
<!-- 	                    <button id="btnSgglSearch" class="btn btn-xs btn-primary"> -->
<!-- 	                        <i class="glyphicon glyphicon-search"></i> -->
<!-- 	                        查询 -->
<!-- 	                    </button> -->
<!-- 	                    <button id="btnReset" class="btn btn-xs btn-primary"> -->
<!-- 	                        <i class="glyphicon glyphicon-repeat"></i> -->
<!-- 	                        重置 -->
<!-- 	                    </button>	 -->
<!-- 	                </div> -->
<!-- 	            </div> -->
       		</div>
       		</div>
       		
			<div class="row">
				<div class="col-xs-12">
					<div class="widget-main no-padding">
                       <h5 class="table-title header smaller blue" >事故管理</h5>
						<table id="accident-table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
	                                <th>名称</th>
	                                <th>单位</th>
	                                <th>类别</th>
	                                <th>时间</th>
	                                <th>填写人</th>
<!-- 	                                <th>调查报告</th> -->
                                    <th>操作</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
		<script type="text/javascript">
			var summaryDialog;
			var accidentDatatables ="";
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker','selectbox'], function(A){
					var conditions=[];
					
// 					var accidentComboxtree = new A.combotree({
// 						render: "#summaryUnitNameId",
// 						name: 'unitId',
// 						//返回数据待后台返回TODO
// 						datasource: ${unitNameIdTreeList},
// 						width:"210px",
// 						options: {
// 							treeId: 'unitNameId',
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
						//单位
					 searchunitid = new A.combobox({
						render : "#summaryUnitNameId",
						datasource : ${unitList},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					
					//事故管理
					var searchguarderName = new A.combobox({
						render : "#sglb",
						datasource : ${sgglType},
						allowBlank: true,
						options : {
							"allow_single_deselect": true,
							"disable_search_threshold":10
						}
					}).render();
					
					
					var exportExcel="";
					 accidentDatatables = new A.datatables({
						render: '#accident-table',
						options: {
					        "ajax": {
					            "url": format_url("/accident/dataList"),
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
							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "name",name:"unitId",width: "20%",orderable: true},
							          {data: "unitName",name:"unitId",width: "18%",orderable: true}, 
							          {data: "sglbName",name:"sglb",width: "18%",orderable: true}, 
							          {data: "time",width: "18%",orderable: true}, 
							          {data: "userName",name:"createUserId",width: "17%",orderable: true}
// 							          {data: "dcbg",width: "25%",orderable: true},
									 ],
									 fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
										 if(exportExcel){
											 exportExcels(format_url("/accident/exportExcel"),JSON.stringify(conditions));
										 }
										 exportExcel="";
									 },
							toolbars: [
							
							           {
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
        							click: function(event, nRow, nData){
										summaryDialog = new A.dialog({
                    						width: 880,
                    						height:460 ,
                    						title: "事故管理新增",
                    						url:format_url('/accident/getAdd'),
                    						closed: function(){
                    							accidentDatatables.draw(false);
                    						}	
                    					}).render()
									}
        						}
        					}
							 , 
							
        					{
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = accidentDatatables.getSelectRowDatas();
										var ids = [];
										var userIds = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
												userIds.push(data[i].createUserId);
											}
										}
										
										var loginUser = '${userEntity.id}';
					                    var loginName = '${userEntity.loginName}';
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
										var url = format_url('/accident/bulkDelete/'+ids);
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
														accidentDatatables.draw(false);
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
							{  
        						id:"dc",
        						label:"导出",
        						icon:"glyphicon glyphicon-download",
        						className:"btn-primary",
        						events:{
        							click:function(event){
            							$('#btnSgglSearch').click();
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
									var loginUser = '${userEntity.id}';
				                    var loginName = '${userEntity.loginName}'
									if(data.createUserId!=loginUser&&loginName!="super"){
										btnNode.hide();
									} 
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										$.ajax({
											url: format_url('/accident/updateValidate/'+id),
											contentType : 'application/json',
											dataType : 'JSON',
											type: 'POST',
											data : JSON.stringify(nData),
											success: function(result){
												if(result.result == 'success'){
													summaryDialog = new A.dialog({
			                    						width: 880,
			                    						height:460 ,
			                    						title: "事故管理修改",
			                    						url:format_url("/accident/getEdit/"+ id),
			                    						closed: function(){
			                    							accidentDatatables.draw(false);
			                    						}	
			                    					}).render()
            									} else{
            										alert(result.errorMsg);
            									}
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
									var loginUser = '${userEntity.id}';
				                    var loginName = '${userEntity.loginName}';
									if(data.createUserId!=loginUser&&loginName!="super"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										$.ajax({
											url: format_url('/accident/deleteValidate/'+id),
											contentType : 'application/json',
											dataType : 'JSON',
											type: 'POST',
											data : JSON.stringify(nData),
											success: function(result){
												if(result.result == 'success'){
													var url =format_url('/accident/'+ id);
													A.confirm('您确认删除么？',function(){
														$.ajax({
															url : url,
			        										contentType : 'application/json',
			        										dataType : 'JSON',
			        										type : 'DELETE',
			        										success: function(result){
			        											alert('删除成功');
			        											accidentDatatables.draw(false);
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
						}, 
						{
							    id:"440",
								label:"查看",
								icon: "fa fa-binoculars bigger-110",
								className: "blue",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										summaryDialog = new A.dialog({
                    						width: 880,
                    						height:460 ,
                    						title: "事故管理查看",
                    						url:format_url("/accident/detail/"+ id),
                    						closed: function(){
                    							accidentDatatables.draw(false);
                    						}	
                    					}).render()
									}
								}
						}
					]}
					}).render();
					
					
					
					$('#btnSgglSearch').on('click',function(){
						conditions=[];
// 						if((accidentComboxtree.getValue())!=null&&String((accidentComboxtree.getValue()))!=""){
//     						conditions.push({
//             					field: 'a.C_UNIT_ID',
//             					fieldType:'STRING',
//             					matchType:'EQ',
//             					value:String(accidentComboxtree.getValue())
//             				});
//     					}
						if($('#summaryUnitNameId').val()){
	    					conditions.push({
	        					field: 'a.C_UNIT_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#searchunitIdDiv').val()
	        				});
						}
    					if($("#sglb").val()!=null&&($("#sglb").val())!=""){
	    					conditions.push({
	        					field: 'a.C_SGLB',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#sglb').val()
	        				});
    					}
    					if($("#name").val()!=null&&($("#name").val())!=""){
	    					conditions.push({
	    						field: 'a.C_NAME',
	    						fieldType:'STRING',
	    						matchType:'LIKE',
	    						value:$('#name').val()
	    					});
    					}
    					
						accidentDatatables.draw();
					});
					
					$('#btnReset').on('click',function(){
// 						accidentComboxtree.setValue();//
						$('#sglb').val('');//
						$("#sglb").trigger("chosen:updated");//
						$('#name').val('');//
						$("#summaryUnitNameId").val('');
						$("#summaryUnitNameId").trigger("chosen:updated");
					});
					
				});
			});
			
        </script>
    </body>
</html>