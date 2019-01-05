<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
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
					计划经营管理
				</li>
				<li class="active">设备物资采购计划</li>
				<li class="active">年度采购</li>
				<li class="active">年度实际采购信息</li>
			</ul>
		</div>
		<div class="page-content" style="margin-left:30px;margin-right:30px;">
		<div style="float:right;margin-right:50px;">
			<button id="btnBack"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style=" margin-top: 14px;">
       			<i class="fa fa-reply"></i>
       			返回
       		</button>
		</div>
		<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >计划采购信息</h5>
		<div class="col-md-12" style="margin-top: 30px">
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:200px;" id="editForm">
			  <input class="col-md-12" id="id" name="id" type="hidden" placeholder="" maxlength="20" value="${ entity.id }">
<!-- 			   <div class="form-group"> -->
<!-- 			   		<label class="col-md-2 control-label no-padding-right"> -->
<!-- 						<span style="color:red;">*</span>计划编号 -->
<!-- 				    </label> -->
<!-- 				    <div class="col-md-4"> -->
<%-- 						<input class="col-md-12" id="code" name="code" type="text" readonly="readonly" placeholder="" maxlength="64" value="${ entity.code}"> --%>
<!--                     </div> -->
<!--                </div> -->
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>项目采购名称
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="name" name="name" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.name }">
					</div>
					 <label class="col-md-2 control-label no-padding-right">
					    采购计划名称
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="projectName" name="projectName" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.projectName }">
				    </div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 设备类别
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="type" name="type" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.typeName }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					  <span style="color:red;">*</span>  规格型号
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="specification" name="specification" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.specification }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 数量
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="amount" name="amount" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.amount }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>预算单价
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="budgetPrice" name="budgetPrice" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.budgetPrice }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 预算总价
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="totalPrice" name="totalPrice" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.totalPrice }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 预计采购时间
				    </label>
				    <div class="col-md-4">
				        <input class="col-md-12" id="buyTime" name="buyTime" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.buyTimeString }">
					</div>
				</div>
				
			   <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						采购人员
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="createUserId" name="createUserId" type="text" readonly="readonly" placeholder="" maxlength="64" value="${userName }" >
                	</div>
                	<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>计数单位
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="unit" name="unit" type="text" placeholder="" maxlength="64" readOnly="readOnly" value="${entity.unitName}">
                    </div>
    		     </div>
    		     <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>责任处室
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="dutyUnit" name="dutyUnit" type="text" placeholder="" maxlength="64" readOnly="readOnly" value="${unitEntity.name}">
                	</div>
                	<label class="col-md-2 control-label no-padding-right">
						公司名称
				    </label>
				    <div class="col-md-4">
				     <input class="col-md-12" id="companyName" name="companyName" readonly="readonly" type="text"  placeholder="" maxlength="64" value="${organizationEntity.name }" >
                    </div>
               </div>
<!-- 			      <div class="form-group"> -->
<!-- 				    <label class="col-md-2 control-label no-padding-right"> -->
<!-- 					    备注 -->
<!-- 				    </label> -->
<!-- 				    <div class="col-md-10"> -->
<%-- 					    <textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12"  readonly="readonly" maxlength="128">${ entity.remark }</textarea> --%>
<!-- 				    </div> -->
<!-- 			     </div> -->
<!-- 			    <div class="form-group"> -->
<!-- 				    <label class="col-md-2 control-label no-padding-right"> -->
<!-- 						附件 -->
<!-- 				    </label> -->
<!-- 						<div class="col-md-10" id="divfile"> -->
<!--                        </div> -->
<!--                </div> -->
				</form>
        		</div>
        		</div>
                <div id="jhgl" style="text-align: left;">
					<div class="col-xs-12" style="margin-top:0px;">	
						<div class="widget-main no-padding">
							<h5 class="table-title header smaller blue">实际采购信息</h5>
							<table id="yearPurchaseReal_table" class="table table-striped table-bordered table-hover" style="width:100%; color: black;">
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
										<th>设备名称</th>
										<th>设备类型</th>
										<th>规格型号</th>
										<th>实际采购时间</th>
										<th>数量</th>
										<th>实际单价(万元)</th>
										<th>实际总价(万元)</th>
										<th>操作</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
                </div>	
		<script type="text/javascript">
		var exportExcel = "";
			jQuery(function($) {
				seajs.use(['combobox','dialog', 'confirm','my97datepicker','datatables'], function(A){
					var yearPurchaseId = ${yearPurchaseId};
					var conditions=[];
        			//附件上传
// 					var uploaddropzone =new A.uploaddropzone({
// 						render : "#divfile",
// 						fileId:${entity.fileId},
// 						autoProcessQueue:true,//是否自动上传
// 						addRemoveLinks : false,//显示删除按钮
// 						chargeUp:true
// 					}).render();
        	
					 var yearPurchaseRealDatatables = new A.datatables({
									render: '#yearPurchaseReal_table',
									options: {
										"ajax": {
								            "url": format_url("/yearPurchaseReal/search"),
								            "contentType": "application/json",
								            "type": "POST",
								            "dataType": "JSON",
								            "data": function (d) {
								            	conditions.push({
							     					field: 'C_YEAR_PURCHASE_ID',
							     					fieldType:'STRING',
							     					matchType:'EQ',
							     					value:yearPurchaseId
							     				});
								            	d.conditions = conditions;
								                return JSON.stringify(d);
								              }
								        },
								        multiple : true,
										ordering: true,
										optWidth: 80,
									
									columns: [
											  {data:"id", visible:false,orderable: false},
											  {orderable: false,"width":"5%", "sClass": "center",render : function(data, type, row, meta) {
								                   var startIndex = meta.settings._iDisplayStart;  
								                   row.start=startIndex + meta.row;
								                   return startIndex + meta.row + 1;  
								               } },
									          {data: "name",width: "20%",orderable: true},
									          {data: "typeName",name:"type",width: "10%",orderable: true}, 
									          {data: "specification",width: "10%",orderable: true}, 
									          {data: "realBuyTimeStr",name:"realBuyTime",width: "10%",orderable: true},
									          {data: "amount",width: "10%",orderable: true},
									          {data: "budgetPrice",width: "15%",orderable: true}, 
									          {data: "totalPrice",width: "15%",orderable: true}
									          ],
									fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											 if(exportExcel){
												 exportExcels(format_url("/yearPurchaseReal/exportExcel"),JSON.stringify(conditions));
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
														url : format_url('/yearPurchaseReal/getAdd/'+yearPurchaseId)
													});
			            						}
			        						}
			        					}, {
											label:"删除",
											icon:"glyphicon glyphicon-trash",
											className:"btn-danger",
											events:{
												click: function(event){
													var data = yearPurchaseRealDatatables.getSelectRowDatas();
													var ids = [];
													var userIds = [];
													if(data.length && data.length>0){
														for(var i =0; i<data.length; i++){
															ids.push(data[i].id);
															userIds.push(data[i].createUserId);
														}
													}
													if(ids.length < 1){
														alert('请选择要删除的数据');
														return;
													}
													
													var url = format_url('/yearPurchaseReal/bulkDelete/');
													A.confirm('您确认删除么？',function(){
														$.ajax({
															url : url,
															contentType : 'application/json',
															dataType : 'JSON',
															type : 'DELETE',
															data : JSON.stringify(ids),
															success: function(result){
																alert('删除成功');
																yearPurchaseRealDatatables.draw(false);
															},
															error:function(v,n){
																alert('操作失败');
															}
														});
													});
												}
											}
										},{
			        						label:"批量导入",
			        						icon:"glyphicon glyphicon-upload",
			        						className:"btn-primary",
			        						events:{
			            						click:function(event){
			            							workSafeOneDialog = new A.dialog({
			                    						width: 800,
			                    						height: 300,
			                    						title: "批量导入",
			                    						url:format_url("/yearPurchaseReal/getBatchAdd/"+yearPurchaseId),
			                    						closed: function(){
			                    						}	
			                    					}).render();
			            						}
			        						}
			        					},{  
			        						id:"dc",
			        						label:"导出",
			        						icon:"glyphicon glyphicon-download",
			        						className:"btn-primary",
			        						events:{
			        							click:function(event){
			        								yearPurchaseRealDatatables.draw();
			            							exportExcel="exportExcel";
			            						}
			        						}
			        					},{  
			        						id:"dc",
			        						label:"导出模板",
			        						icon:"glyphicon glyphicon-download",
			        						className:"btn-primary",
			        						events:{
			        							click:function(event){
			    								 	exportExcels(format_url("/yearPurchaseReal/exportExcelModel"));
			            						}
			        						}
			        					}], 
										btns: [{
											id: "edit",
											label:"修改",
											icon: "fa fa-pencil-square-o bigger-130",
											className: "green edit",
											events:{
												click: function(event, nRow, nData){
													var id = nData.id;
													A.loadPage({
														render : '#page-container',
														url:format_url('/yearPurchaseReal/getEdit/'+ id)
													});
												}
											}
										},{
											id:"delete",
											label:"删除",
											icon: "fa fa-trash-o bigger-130",
											className: "red del",
											events:{
												click: function(event, nRow, nData){
													var id = nData.id;
													var url =format_url('/yearPurchaseReal/'+ id);
													A.confirm('您确认删除么？',function(){
														$.ajax({
															url : url,
			        										contentType : 'application/json',
			        										dataType : 'JSON',
			        										type : 'DELETE',
			        										success: function(result){
			        											alert('删除成功');
			        											yearPurchaseRealDatatables.draw(false);
			        										},
			        										error:function(v,n){
			        											alert('操作失败');
			        										}
														});
													});
												}
											}
									},{
										id: "detail",
										label:"查看",
										icon: "fa fa-binoculars bigger-130",
										className: "blue ",
										events:{
											click: function(event, nRow, nData){
												var id = nData.id;
												A.loadPage({
													render : '#page-container',
													url : format_url("/yearPurchaseReal/getDetail/"+ id)
												});
											}
										}
									}]
									}
								}).render();
        			
					$("#btnBack").on("click", function(){
						A.loadPage({
							render : '#page-container',
							url : format_url("/yearPurchase/list/"+'${organizationEntity.id}'+'/2')
						});
    				});
				});
			});
        </script>
    </body>
</html>