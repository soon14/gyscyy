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
				<li class="active">零星采购</li>
				<li class="active">修改</li>
			</ul>
		</div>
		<div class="page-content" style="margin-left:30px;margin-right:30px;">
		<div style="float:right;margin-right:50px;">
			<button id="btnBack2"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style=" margin-top: 14px;">
       			<i class="fa fa-reply"></i>
       			返回
       		</button>
		</div>
		<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >基础信息</h5>
		<div class="col-md-12" style="margin-top: 30px">
			<form class="form-horizontal" role="form"  style="margin-right:200px;" id="editgoodsForm">
			  <input class="col-md-12" id="id" name="id" type="hidden" placeholder="" maxlength="20" value="${ entity.id }">
			  <input class="col-md-12" id="ifEndStatus" name="ifEndStatus" type="hidden" placeholder="" maxlength="20" value="${ entity.ifEndStatus }">
			   <input class="col-md-12" id="companyUnit" name="companyUnit" type="hidden"  value="${entity.companyUnit }" >
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 项目采购名称
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="name" name="name" type="text" placeholder="" maxlength="64" value="${ entity.name }">
					</div>
						<label class="col-md-2 control-label no-padding-right">
						采购人员
					</label>
					<div class="col-md-4">
					 <input class="col-md-12" id="createUserId" name="createUserId" type="hidden" readonly="readonly" placeholder="" maxlength="64" value="${entity.createUserId }" >
	                   <input class="col-md-12" id="userName" name="userName" type="text" readonly="readonly" placeholder="" maxlength="64" value="${userName }" >
                	</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>设备类别
				    </label>
				    <div class="col-md-4">
					    <select class="col-md-12 chosen-select" id="type" name="type"></select>
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>物资采购计划类别
				    </label>
				    <div class="col-md-4">
					    <select class="col-md-12 chosen-select" id="planType" name="planType"></select>
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>型号规格
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="specification" name="specification" type="text" placeholder="" maxlength="64" value="${ entity.specification }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 数量
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="amount" name="amount" type="text" placeholder="" maxlength="64" value="${ entity.amount }"
					     onkeyup="checkInt(this);" onpaste="checkInt(this);" oncut="checkInt(this);" ondrop="checkInt(this);" onchange="checkInt(this);">
					</div>
				</div>
			   <div class="form-group">
			      	<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>计数单位
					</label>
					<div class="col-md-4">
	                  <select id="unit" name="unit" class="form-control chosen-select" style="width: 150px;"></select>
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>预计单价(万元)
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="budgePrice" name="budgePrice" type="text" placeholder="" maxlength="64" value="${ entity.budgePrice }" 
					      onkeyup="checkP(this);" onpaste="checkP(this);" oncut="checkP(this);" ondrop="checkP(this);" onchange="checkP(this);" >
				    </div>	
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>预计总价(万元)
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="totalPrice" name="totalPrice" type="text" placeholder="" maxlength="64" value="${ entity.totalPrice }"  readonly="readonly" onkeyup="value=value.replace(/[\D]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[\D]/g,''))">
					</div>
					 <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>预计采购时间
				    </label>
				    <div class="col-md-4">
					    <div id="buyTimeDiv"></div>
				    </div>	
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 采购计划名称
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="projectName" name="projectName" type="text" placeholder="" maxlength="64" value="${ entity.projectName }">
					</div>
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>计划处室
					</label>
					<div class="col-md-4">
	                   <select id="dutyUnit" name="dutyUnit" class="form-control chosen-select" style="width: 150px;"></select>
                	</div>
				</div>
				<div class="form-group">
<!-- 			  		<label class="col-md-2 control-label no-padding-right"> -->
<!-- 						<span style="color:red;">*</span>计划编号 -->
<!-- 				    </label> -->
<!-- 				    <div class="col-md-4"> -->
<%-- 						<input class="col-md-12" id="code" name="code" type="text" placeholder="" maxlength="64" value="${ entity.code}"> --%>
<!--                     </div> -->
                	<label class="col-md-2 control-label no-padding-right">
						公司名称
				    </label>
				    <div class="col-md-4">
				     <input class="col-md-12" id="companyName" name="companyName" readonly="readonly" type="text"  placeholder="" maxlength="64" value="${organizationEntity.name }" >
                    </div>
               </div>
			      <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    备注
				    </label>
				    <div class="col-md-10">
					    <textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128">${ entity.remark }</textarea>
				    </div>
			     </div>
				</div>
				
                <div  id="jhgl" style="text-align: left;color: #478fca;font-weight: lighter;">
<!--                     <button  id="jhgl" class="btn btn-xs btn-success  " type="button" > -->
<!-- 						计划关联 -->
<!-- 					</button> -->
                </div>	
              	<hr> 
			</form>
    		<div style="margin-right:100px;">
<!--         		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal"> -->
<!--         			<i class="ace-icon fa fa-times"></i> -->
<!--         			取消 -->
<!--         		</button> -->
        		<button id="editGoodsBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
        			<i class="ace-icon fa fa-floppy-o"></i>
        			保存
        		</button>
        	</div>
        </div>
		<script type="text/javascript">
		
		  var goodsResult = [];
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
        			var appData = ${entityJson};
					//combobx组件
                	var typeCombobox = new A.combobox({
                		render: "#type",
                		//返回数据待后台返回TODO
                		datasource:${typeCombobox},
                		//multiple为true时select可以多选
                		multiple:false,
                		//allowBlank为false表示不允许为空
                		allowBlank: true,
                		options:{
                			"disable_search_threshold":10
                		}
                	}).render();
                	typeCombobox.setValue(appData.type);
					//combobx组件
                	var planTypeCombobox = new A.combobox({
                		render: "#planType",
                		//返回数据待后台返回TODO
                		datasource:${planCombobox},
                		//multiple为true时select可以多选
                		multiple:false,
                		//allowBlank为false表示不允许为空
                		allowBlank: true,
                		options:{
                			"disable_search_threshold":10
                		}
                	}).render();
                	planTypeCombobox.setValue(appData.planType);
                	
                	//责任处室
					var dutyCombobox = new A.combobox({
						render: "#dutyUnit",
						//返回数据待后台返回
						datasource:${unitList},
						multiple:false,
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					dutyCombobox.setValue(appData.dutyUnit);
                	
					//日期组件
					var buyTimeDatePicker = new A.my97datepicker({
						id: "buyTimeId",
						name: "buyTime",
						render: "#buyTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM"
						}
					}).render();
					buyTimeDatePicker.setValue(appData.buyTimeString);
					
					//计数单位下拉
					var unitSource = ${unitCombobox};
					var unitCombobox = new A.combobox({
						render: "#unit",
						//返回数据待后台返回
						datasource:unitSource,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					unitCombobox.setValue(appData.unit);
					
					var id = $('#id').val();
				
					//年度内计划关联年度采购
					$("#planType").on("change", function(){
						if($("#planType").val()==1){
						
							$("#jhgl")
							.append(' <div class="col-xs-12" style="margin-top:0px;">'	
							     +'  <div class="widget-main no-padding">'
							     +' <form id="goodsRelation-table-form" class="form-horizontal" >'
							     +'		        <h5 class="table-title header smaller blue" style="margin-bottom:0px;">计划关联</h5>	'
							     +' <table id="goodsRelation_table" class="table table-striped table-bordered table-hover" style="width:100%;margin-bottom:30px; color: black;">'
							     +' 	<thead>'
							     +' 			<tr>'
							     +'                 <th>序号</th>'
							     +'                 <th>项目名称</th>'
							     +'                 <th>设备名称</th>'
							     +'                 <th>设备类别</th>'
							     +'                 <th>型号规格</th>'
							     +'                 <th>数量</th>'
							     +'                 <th>计数单位</th>'
							     +'                 <th>预算单价(万元)</th>'
							     +'                 <th>预算总价(万元)</th>'
							     +'                 <th>操作</th>'
							     +' 			</tr>'
							     +' 		</thead>'
							     +' 	</table>'
							     +' </form>'
							     +' </div>'
							     +' </div>');

// 								var goodsRelationDatatables;
// 								var selectGoodsDialog;
								var conditions=[];
							
								 goodsRelationDatatables = new A.datatables({
									render: '#goodsRelation_table',
									options: {
										    serverSide: false,
									        multiple : true,
									        checked:false,
											bInfo:false,
											paging:false,
											ordering:true,
											optWidth: 120,
// 											order:[["1",'asc']],
									
									columns: [
									    	  {data:"id", visible:false,orderable: true}, 
									          {data: "projectName",width: "18%",orderable: true},
									          {data: "equipName",width: "18%",orderable: false},
									          {data: "equipType",width: "10%",orderable: true}, 
									          {data: "specification",width: "10%",orderable: true}, 
									          {data: "amount",width: "10%",orderable: true},
									          {data: "unit",width: "10%",orderable: true},
									          {data: "budgePrice",width: "10%",orderable: true}, 
									          {data: "totalPrice",width: "10%",orderable: true}
									         
									          ],
										toolbars: [{
					        						label:"计划关联",
					        						icon:"glyphicon glyphicon-plus",
					        						className:"btn-success",
					        						events:{
					            						click:function(event){
					            							var dataIdArray = [];
					            							if(goodsResult){
						            							for(var i=0;i<goodsResult.length;i++){
						            								dataIdArray.push(goodsResult[i].id);
						        								}
					            							}
					            							
					            						selectGoodsDialogEdit= new A.dialog({
					            							 goodsInfoArray:true,
					                    						width: 1100,
					                    						height: 800,
					                    						title: "年度采购",
					                    						url:format_url("/yearPurchase/selectYearPurchaseEdit?dataIdArray="+dataIdArray),
																closed: function(){
																	debugger;
																	var result = selectGoodsDialogEdit.resule;
// 					                    							var goodsInfo = selectGoodsDialogEdit.goodsInfoArray;
// 					                    							var result = goodsInfoArray;
					                    							if(result){
					                    								for(var i=0;i<result.length;i++){
					                    									goodsResult.push(result[i]);
					                    									goodsRelationDatatables.addRow({"id":result[i].id, "projectName":result[i].projectName, "equipName":result[i].name,"equipType":result[i].typeName, "specification":result[i].specification, "amount":result[i].amount,"unit":result[i].unitName,"budgePrice":result[i].budgetPrice, "totalPrice":result[i].totalPrice});
					                    								}
					                    							}
					                    							goodsRelationDatatables.draw(false);
					                    						}
					                    					}).render()
					            						}
					        						}
					        					}], 
										btns: [{
											id:"delete",
											label:"删除",
											icon: "fa fa-trash-o bigger-130",
											className: "red del",
											events:{
												"click": function(event, nRow, nData){
													var id = nData.id;
													if(id==undefined){
														goodsRelationDatatables.deleteSelectRow(nRow);
// 														goodsRelationDatatables.draw(false);
													}else{
														
													A.confirm('您确认删除么？',function(){
														$.ajax({
															url: format_url("/goodsRelation/delete/"+id),
															contentType: "application/json",
															dataType: 'JSON',
															type: 'POST',
															success: function(result){
																alert('删除成功');
																goodsRelationDatatables.deleteSelectRow(nRow);
// 																goodsRelationDatatables.draw(false);
															},
															error:function(v,n){
																alert('删除失败');
															}
														})
													});		
														
													}
												}
											}
									}]
									}
								}).render();

								 function initDataTable(){
										var params = {};
										params.length = 10;
										params.start = 0;
										params.draw = 0;
										params.conditions = [];
										
										$.ajax({
								            "url": format_url("/goodsRelation/editData/list/"+id),
								        	contentType: "application/json",
											dataType: 'JSON',
											type: 'POST',
								            data : JSON.stringify(params),
								            success: function(result){
								            	if(result.data[0].id){
								            		goodsRelationDatatables.addRows(result.data);
													var tableData = result.data; 
													if(tableData){
														for(var i=0;i<tableData.length;i++){
															goodsResult.push({"id":tableData[i].id});
														}
													}
												}
// 												goodsRelationDatatables.addRows(result.data);
												
											}
										});
									}; 
								initDataTable();  

						}else{
							$("#jhgl").text("");
						}
    				});
// 						$("#planType").change();
						init();
						function init(){
							if(${entity.planType}==1){
								
								$("#jhgl")
								.append(' <div class="col-xs-12" style="margin-top:0px;">'	
								     +'  <div class="widget-main no-padding">'
								     +' <form id="goodsRelation-table-form" class="form-horizontal" >'
								     +'		        <h5 class="table-title header smaller blue" style="margin-bottom:0px;">计划关联</h5>	'
								     +' <table id="goodsRelation_table" class="table table-striped table-bordered table-hover" style="width:100%;margin-bottom:30px; color: black;">'
								     +' 	<thead>'
								     +' 			<tr>'
								     +'                 <th>序号</th>'
								     +'                 <th>项目名称</th>'
								     +'                 <th>设备名称</th>'
								     +'                 <th>设备类别</th>'
								     +'                 <th>型号规格</th>'
								     +'                 <th>数量</th>'
								     +'                 <th>计数单位</th>'
								     +'                 <th>预算单价(万元)</th>'
								     +'                 <th>预算总价(万元)</th>'
								     +'                 <th>操作</th>'
								     +' 			</tr>'
								     +' 		</thead>'
								     +' 	</table>'
								     +' </form>'
								     +' </div>'
								     +' </div>');

//	 								var goodsRelationDatatables;
//	 								var selectGoodsDialog;
									var conditions=[];
								
									 goodsRelationDatatables = new A.datatables({
										render: '#goodsRelation_table',
										options: {
											    serverSide: false,
										        multiple : true,
										        checked:false,
												bInfo:false,
												paging:false,
												ordering:true,
												optWidth: 120,
//	 											order:[["1",'asc']],
										
										columns: [
										    	  {data:"id", visible:false,orderable: true}, 
										          {data: "projectName",width: "18%",orderable: true},
										          {data: "equipName",width: "18%",orderable: false},
										          {data: "equipType",width: "10%",orderable: true}, 
										          {data: "specification",width: "10%",orderable: true}, 
										          {data: "amount",width: "10%",orderable: true},
										          {data: "unit",width: "10%",orderable: true},
										          {data: "budgePrice",width: "10%",orderable: true}, 
										          {data: "totalPrice",width: "10%",orderable: true}
										         
										          ],
											toolbars: [{
						        						label:"计划关联",
						        						icon:"glyphicon glyphicon-plus",
						        						className:"btn-success",
						        						events:{
						            						click:function(event){
						            							var dataIdArray = goodsRelationDatatables.getDatasId();
						            						debugger;
						            						 selectGoodsDialog= new A.dialog({
						            							 goodsInfoArray:true,
						                    						width: 1100,
						                    						height: 800,
						                    						title: "年度物资",
						                    						url:format_url("/yearPurchase/selectYearPurchase?dataIdArray="+dataIdArray),
																	closed: function(){
																		var result = selectGoodsDialog.resule;
						                    							var goodsInfo = selectGoodsDialog.goodsInfoArray;
//	 					                    							var result = goodsInfoArray;
						                    							if(result){
						                    								for(var i=0;i<result.length;i++){
						                    									goodsResult.push(result[i]);
						                    									goodsRelationDatatables.addRow({"id":result[i].id, "projectName":result[i].projectName, "equipName":result[i].name,"equipType":result[i].typeName, "specification":result[i].specification, "amount":result[i].amount,"unit":result[i].unitName,"budgePrice":result[i].budgetPrice, "totalPrice":result[i].totalPrice});
						                    								}
						                    							}
						                    							goodsRelationDatatables.draw(false);
						                    						}
						                    					}).render()
						            						}
						        						}
						        					}], 
											btns: [{
												id:"delete",
												label:"删除",
												icon: "fa fa-trash-o bigger-130",
												className: "red del",
												events:{
													"click": function(event, nRow, nData){
														var id = nData.id;
														A.confirm('您确认删除么？',function(){
															$.ajax({
																url: format_url("/goodsRelation/delete/"+id),
																contentType: "application/json",
																dataType: 'JSON',
																type: 'POST',
																success: function(result){
																	alert('删除成功');
																	goodsRelationDatatables.deleteSelectRow(nRow);
																},
																error:function(v,n){
																	alert('删除失败');
																}
															})
														});		
													}
												}
										}]
										}
									}).render();
									
									    function initDataTable(){
						                    var params = {};
						                    params.length = 10;
						                    params.start = 0;
						                    params.draw = 0;
						                    params.conditions = [];
						                    
						                    $.ajax({
						                            "url": format_url("/goodsRelation/editData/list/"+id),
						                          contentType: "application/json",
						                      dataType: 'JSON',
						                      type: 'POST',
						                            data : JSON.stringify(params),
						                            success: function(result){
						                        goodsRelationDatatables.addRows(result.data);
						                        
						                      }
						                    });
						                  }; 
									
									initDataTable();  

							}else{
								$("#jhgl").text("");
							}
						}
						$('#editgoodsForm').validate({
							debug:true,
							rules: {
								id:{      maxlength:20},
// 								code:{ required:true ,     maxlength:64},
								name:{ required:true ,     maxlength:64},
								type:{ required:true ,     maxlength:64},
								planType:{ required:true ,     maxlength:64},
								specification:{ required:true ,     maxlength:64},
								amount:{ digits:true,required:true ,     maxlength:64},
								unit:{ required:true ,     maxlength:20},
								budgePrice:{ number:true,required:true ,     maxlength:64},
								totalPrice:{number:true, required:true ,     maxlength:64},
								buyTime:{  required:true ,    maxlength:64},
								projectName:{required:true ,      maxlength:64},
								dutyUnit:{ required:true , maxlength:64},
								companyUnit:{  maxlength:64},
								remark:{      maxlength:128}
							},
							submitHandler: function (form) {
								//添加按钮
								var url = format_url("/goodsPurchase/saveEditPage/"+id);
								//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
								var obj = $("#editgoodsForm").serializeObject();
								
								obj.buyTime=$("#buyTimeId").val()+"-01 00:00:00";
								
								var	goodsPurchase = [];
								
								var spareList = $("#goodsRelation-table-form").serializeObject();  
							
								for(var i=0;i<goodsResult.length;i++){
									var spareObj = {};
									spareObj.id = goodsResult[i].id;
									spareObj.projectName = goodsResult[i].projectName;
									spareObj.equipName = goodsResult[i].name;
									spareObj.equipType = goodsResult[i].typeName;
									spareObj.specification = goodsResult[i].specification;
									spareObj.amount = goodsResult[i].amount;
									spareObj.unit = goodsResult[i].unitName;
									spareObj.budgePrice = goodsResult[i].budgetPrice;
									spareObj.totalPrice = goodsResult[i].totalPrice;
									goodsPurchase.push(spareObj);
								}
								obj.goodsList = goodsPurchase;
// 								obj.yearPurchaseId=goodsPurchase[0].yearPurchaseId;
								
								$.ajax({
									url : url,
									contentType : 'application/json',
									dataType : 'JSON',
									data : JSON.stringify(obj),
									cache: false,
									type : 'POST',
									success: function(result){
										alert('操作成功');
										A.loadPage({
											render : '#page-container',
											url : format_url("/yearPurchase/list/"+1)
										});
									},
									error:function(v,n){
										alert('操作失败');
									}
								});
							}
						});
						$("#editGoodsBtn").on("click", function(){
	    					
	    					$("#editgoodsForm").submit();
	    				});
						$("#btnBack2").on("click", function(){
							A.loadPage({
								render : '#page-container',
								url : format_url("/yearPurchase/list/"+1)
							});
	    				});
					
				});
			});
				
			
			//预算总价计算
			function checkInt(o){ 
				theV=isNaN(parseInt(o.value))?0:parseInt(o.value); 
				if(theV!=o.value){o.value=theV;} 
				totalPrice.value=amount.value*budgePrice.value; 
				} 
			function checkP(o){ 
				theV=isNaN(parseFloat(o.value))?0:parseFloat(o.value); 
				theV=parseInt(theV*100)/100; 
				if(theV!=o.value){ 
				theV=(theV*100).toString(); 
				theV=theV.substring(0,theV.length-2)+"."+theV.substring(theV.length-2,theV.length) 
				o.value=theV; 
				} 
				totalPrice.value=amount.value*budgePrice.value; 
				} 
        </script>
    </body>
</html>