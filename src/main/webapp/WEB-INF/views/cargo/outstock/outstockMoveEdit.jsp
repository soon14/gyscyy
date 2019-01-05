<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
	<head>
    	<meta charset="UTF-8">
    	<%@ include file="/WEB-INF/views/common/meta.jsp" %> 
    </head>
	<body>
		<div class="breadcrumbs ace-save-state" id="breadcrumbs" style="margin-bottom:100px;">
    		<ul class="breadcrumb">
    			<li>
    				<i class="ace-icon fa fa-home home-icon"></i>
    				<a href="javascript:void(0);" onclick="firstPage()">首页</a>
    			</li>
    
    			<li>物资管理</li>
    			<li>出库管理</li>
    			<li class="active">出库单修改</li>
    		</ul><!-- /.breadcrumb -->
    		<div style="margin-right:100px;margin-top:10px;">
        		<button id="backBtnEdit" class="btn btn-xs btn-primary pull-right">
    				<i class="fa fa-reply"></i>
    				返回
    			</button>
        	</div>
    		<h5 class="table-title header smaller blue" style="margin-left:30px">出库单修改</h5>
    	</div>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
			     <input class="col-md-12" id="id" name="id" type="hidden" value="${ entity.id }">
			     <input class="col-md-12" id="wareHouseId" name="wareHouseId" type="hidden" value="${ entity.wareHouseId }">
			     <input class="col-md-12" id="unitId" name="unitId" type="hidden" value="${ entity.unitId }">
			   <div class="form-group">
				    <label class="col-md-1 control-label no-padding-right">
					    出库单号
				    </label>
				    <div class="col-md-2">
					    <input class="col-md-12" id="outstockNum" name="outstockNum" type="text" readonly="readonly" placeholder="" maxlength="64" value="${ entity.outstockNum }">
				    </div>	
				    <label class="col-md-1 control-label no-padding-right"><span style="color:red;">*</span>出库时间</label>
					 <div class="col-md-2">
						<div id="outstockTime" class="form-control" style="border:none; padding:0px;max-height:500px;display:inline-block;"></div>
	                 </div>
				    <label class="col-md-1 control-label no-padding-right">
					   <span style="color:red;">*</span>出库类型</label>
				    </label>
				    <div class="col-md-2">
					    <select class="col-md-12 chosen-select" id="outstockType" name="outstockType"></select>
				    </div>	
				    <label class="col-md-1 control-label no-padding-right">
					  <span style="color:red;">*</span>申请人</label>
				    </label>
				    <div class="col-md-2">
					    <div style="width:210px;display:inline-block">
							<div id="applicantEditDiv">
							</div>	
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-1 control-label no-padding-right"><span style="color:red;">*</span>所属仓库</label>
					<div class="col-md-2">
						<!-- <select class="col-md-12 chosen-select" id="wareHouseId" name="wareHouseId"></select> -->
						<input class="col-md-12" id="wareHouseName" name="wareHouseName"  type="text" readonly="readonly" value="${entity.wareHouseName}"></input>
					</div>
<!-- 					<label class="col-md-1 control-label no-padding-right"><span style="color:red;">*</span>场站名称</label> -->
<!-- 				    <div class="col-md-2"> -->
<%-- 						 <input class="col-md-12"  type="text" readonly="readonly" value="${entity.unitName}"></input> --%>
<!--                     </div> -->
			   </div>
			      <div class="form-group">
						<label class="col-md-1 control-label no-padding-right">备注</label>
						<div class="col-md-11">
							<textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128">${entity.remark}</textarea>
						</div>
	               </div>
				</form>
			<div class="page-content">
			     <div class="form-group  form-horizontal" style="margin-right:90px;">
				    <label class="col-md-1 control-label no-padding-right">附件</label>
					<div class="col-md-11" id="editAttachmentDiv"></div>
			     </div>
			     
			    <div class="row">
					<div class="col-xs-12">
						<!-- div.dataTables_borderWrap -->
						<div class="widget-main no-padding">
							<h5 class='table-title header smaller blue' >出库明细列表</h5>
							<form id="outMaterialEditForm" class="form-horizontal" role="form">
								<table id="materialEdit_table" class="table table-striped table-bordered table-hover" style="width:100%;">
									<thead>
										<tr>
											<th style="display:none;">主键</th>
											<!-- <th class="center sorting_disabled" style="width:50px;">
		        								<label class="pos-rel">
		        									<input type="checkbox" class="ace" />
		        									<span class="lbl"></span>
		        								</label>
		        							</th> -->
		        							<th>序号</th>
			                                <th>物资编码</th>
			                                <th>物资名称</th>
			                                <th>规格型号</th>
			                                <th>生产厂家</th>
			                                <th><span style="color:red;">*</span>数量</th>
			                                <th>计数单位</th>
			                                <th>物资属性</th>
	                                		<th>有效期</th>
	                                		<th>价格</th>
		                                    <th>操作</th>
		                                </tr>
		                            </thead>
		                        </table>
		                     </form>
	                    </div>
	                </div>
	            </div>
        	</div>
    		<div style="margin-right:100px;margin-top:30px;">
        		<button id="editBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
        			<i class="glyphicon glyphicon-floppy-disk"></i>
        			保存
        		</button>
        		<!-- <button id="submitOutstockEditBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
	    			<i class="ace-icon glyphicon glyphicon-floppy-saved"></i>
	    			提交审核
	    		</button> -->
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['datatables','combobox','uploaddropzone','my97datepicker','selectbox'], function(A){
        			var appData = ${entityJson};
        			//申请人选择带回
					var userEntity = ${sysUserEntity};
					var goodsAttribute= ${goodsAttribute};//物资属性  add  by  zhangxb
					var editManagerNamesDiv=new A.selectbox({
						id: 'applicantEditDivId',
						name:'applicant',
						title:'人员',
						url:'/sysUser/userSelect?singleSelect=1',
						render:'#applicantEditDiv',
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
					editManagerNamesDiv.setValueBack("#applicantEditDivId",appData.applicantName,appData.applicant);
					
					//出库时间
					var editOutstockTimeDatePicker = new A.my97datepicker({
						id: 'outstockTimeId',
						name: 'outstockTime',
						render:'#outstockTime',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: 'yyyy-MM-dd'
						}
					}).render();
					editOutstockTimeDatePicker.setValue(appData.showOutstockTime);
					//附件上传fileId传数组
					var uploaddropzone=new A.uploaddropzone({
						render : "#editAttachmentDiv",
						fileId:${entity.attachment},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					//combobx组件
					var outstockData = ${outstockTypeCombobox};
                	var editOutstockTypeCombobox = new A.combobox({
                		render: "#outstockType",
                		//返回数据待后台返回TODO
                		datasource:outstockData,
                		//multiple为true时select可以多选
                		multiple:false,
                		//allowBlank为false表示不允许为空
                		allowBlank: true,
                		options:{
                			"disable_search_threshold":10
                		}
                	}).render();
                    editOutstockTypeCombobox.setValue(appData.outstockType);
                  //combotree组件  单位  add  by  zhangxb 
// 					var unitNameIdCombotree = new A.combotree({
// 						render: "#organizationDiv",
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
// 					unitNameIdCombotree.setValue('${ entity.unitId }');//默认值当前单位,显示修改单位
					
// 					var goodsAreaValues=${goodsAreaValues};
					var goodsAllocationVal=[];
					
					var conditions=[];
					var materialResult = [];
// 					var materialGoodsAreaId=[];
// 					var materialGoodsPositionId=[];
					//出库单id
					var dataId = appData.id;
					var editOutstockMaterialDatatables = new A.datatables({
						render: '#materialEdit_table',
						options: {
					        serverSide: false,
					        multiple : true,
							ordering: true,
							checked:false,
							optWidth: 120,
							order:[["1",'asc']],
							"fnDrawCallback"    : function(){
								this.api().column(1).nodes().each(function(cell, i) {
								cell.innerHTML =  i + 1;
							});
						},
							columns: [
							        {data:"materialId", visible:false,orderable:false}, 
							         {data: null,width: "5%",orderable: true}, 
							         {data: "materialCode",width: "10%",orderable: true}, 
							         {data: "materialName",width: "10%",orderable: true}, 
							         {data: "materialModel",width: "15%",orderable: true}, 
							         {data: "materialManufacturer",width: "15%",orderable: true}, 
							         {data: "count",width: "10%",orderable: true,editType:"input",editHeight:"25px",editWidth:"95%"}, 
							         {data: "materialUnitName",width: "5%",orderable: true,editType:"input",editReadOnly:true},
							        /*  {data: "goodsAttribute",width: "8%" ,orderable: true, editType:"combobox" , cfg:{ datasource :goodsAttribute, allowBlank: true } }, */
							         {data: "goodsAttribute",width: "8%",orderable: false,editType:"input",editReadOnly:true },
							        {data: "goodsValidity",width: "8%",orderable: true,render:function(data){
							        	  if(data == null || data == ""){
							        		  return "";
							        	  }
							        	  var d = new Date(data);  
							        	  var dformat = [ d.getFullYear(), d.getMonth() + 1, d.getDate() ].join('-');  
							        	  return dformat;  
							        	 
							          }}, 
							         {data: "goodsPrice",width: "8%",orderable: true}
							        ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							var objMaterial = $('#outMaterialEditForm').serializeObject();
										var obgMaterialArr = Object.keys(objMaterial);
// 										console.log(objMaterial);
// 										if(objMaterial.goodsAttribute!=null){
// 											alert("不可以再添加数据");
// 											return;
// 										}
            							var materialIdArray = [];
            							if(materialResult){
	            							for(var i=0;i<materialResult.length;i++){
	        									materialIdArray.push(materialResult[i].id);
	        								}
            							}
            							var wareHouseChoose=appData.wareHouseId;
            							selectOutMaterial4EditDialog = new A.dialog({
                    						width: 1550,
                    						height: 900,
                    						title: "物资信息选择",
                    						url:format_url('/materialCategory/selectOutStockMaterialCategory4Edit?materialOutStockIdArray='+materialIdArray+'&wareHouseChoose='+wareHouseChoose),
                    						closed: function(){
                    							var result = selectOutMaterial4EditDialog.resule;
                    							if(result){
                    								for(var i=0;i<result.length;i++){
                    									materialResult.push(result[i]);
                    									editOutstockMaterialDatatables.addRow({"materialId":result[i].id, "materialCode":result[i].code, "materialName":result[i].name,
                    										"materialModel":result[i].model, "materialManufacturer":result[i].manufacturerName, "count":"","materialUnitName":result[i].unitName,"goodsAttribute":result[i].goodsAttribute,"goodsValidity":result[i].goodsValidity,"goodsPrice":result[i].goodsPrice,"showGoodsAttribute":result[i].showGoodsAttribute});
                    								}
                    							}
                    							editOutstockMaterialDatatables.draw(false);
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
										//出库物资明细id
										var id = nData.id;
										if(id==undefined){
											//循环已有物资明细id的数组
											for(var i=0;i<materialResult.length;i++){
												//取到删除的物资明细id
					                            var materialId = nData.materialId;
												//循环数组中的每一个物资明细id
					                            var materialResultId = materialResult[i].id;
												//如果两个id相等，在数组中去掉这个id
					                            if(materialId == materialResultId){
					                               materialResult.splice(i,1);
					                            }
					                         }
											editOutstockMaterialDatatables.deleteSelectRow(nRow);
											editOutstockMaterialDatatables.draw(false);
										}else{
											var url =format_url('/outstockDetail/'+id);
											A.confirm('您确认删除么？',function(){
												$.ajax({
													url : url,
	        										contentType : 'application/json',
	        										dataType : 'JSON',
	        										type : 'DELETE',
	        										success: function(result){
	        											alert('删除成功');
	        											//循环已有物资明细id的数组
	        											for(var i=0;i<materialResult.length;i++){
	        												//取到删除的物资明细id
	        					                            var materialId = nData.materialId;
	        												//循环数组中的每一个物资明细id
	        					                            var materialResultId = materialResult[i].id;
	        												//如果两个id相等，在数组中去掉这个id
	        					                            if(materialId == materialResultId){
	        					                               materialResult.splice(i,1);
	        					                            }
	        					                         }
	        											editOutstockMaterialDatatables.deleteSelectRow(nRow);
	        											editOutstockMaterialDatatables.draw(false);
	        										},
	        										error:function(v,n){
	        											alert('操作失败');
	        										}
												});
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
							url: format_url("/outstockMove/editData/list/"+dataId),
							contentType: "application/json",
							dataType: 'JSON',
							type: 'POST',
							data : JSON.stringify(params),
							success: function(result){
								if(result.data[0].id){
									editOutstockMaterialDatatables.addRows(result.data);
									var tableData = result.data; 
									if(tableData){
										for(var i=0;i<tableData.length;i++){
											materialResult.push({"id":tableData[i].materialId});
										}
									}
								}
							}
							
						});
					}; 
					initDataTable();
					
					$('#editForm').validate({
						debug:true,
						rules: {
							id:{maxlength:20},
							outstockNum:{maxlength:64},
							outstockTime:{required:true,maxlength:10},
							outstockType:{required:true,maxlength:20},
							applicant:{required:true,maxlength:64},
							attachment:{maxlength:128},
							remark:{maxlength:128}
						},
						submitHandler: function (form) {
							//添加按钮
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							obj.attachment=JSON.stringify(uploaddropzone.getValue());
							var objMaterial = $('#outMaterialEditForm').serializeObject();
							var objMaterialCount = objMaterial.count;
							var objGoodsAttribute = objMaterial.goodsAttribute;
								obj.materialIdArray = [];
// 								obj.materialGoodArea=[];
// 								obj.materialGoodsPosition=[];
							var propertyMap = new Map();
							var materialUnitName = objMaterial.materialUnitName;
							obj.materialUnitName = materialUnitName;
							if(objMaterialCount){
								obj.materialCountArray = objMaterialCount;
								obj.goodsAttribute = objGoodsAttribute;
								for(var i=0;i<materialResult.length;i++){
									obj.materialIdArray.push(materialResult[i].id);
// 									obj.materialGoodArea.push(materialGoodsAreaId[i]);
// 									obj.materialGoodsPosition.push(materialGoodsPositionId[i]);
									propertyMap[materialResult[i].id] = materialResult[i];
								}
								obj.propertyMap = propertyMap;
							}
							var materialSize = obj.materialIdArray;
							if(materialSize.length==0){
								alert("请选择出库物资！");
								return;
							}
							var url = format_url("/outstockMove/saveEditPage/"+materialSize);
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result=='success'){
										alert('修改成功');
										A.loadPage({
											render : '#page-container',
											url : format_url('/outstockMove/index')
										});
									}
									if(result.result=='exception'){
										if(!result.data){
											alert("库存数量不足！");
											return;
										}
									}
									if(result.result=='exceptionOne'){
										alert("不允许填写小数！");
										return;
								}
								},
								error:function(v,n){
									alert('操作失败');
								}
							});
						}
					});
					
					$('#outMaterialEditForm').validate({
						debug:true,
						rules:  {
						}
					})
					$("#editBtn").on("click", function(){
						var objMaterial = $('#outMaterialEditForm').serializeObject();
						var objMaterialCount = objMaterial.count;
						var countEdit = "${count}";
						var materialId = "${materialId}"
						if(materialId==materialResult[0].id){
							
							var intcount = parseInt(objMaterialCount);
							if(intcount>countEdit){
								alert("数量不能大于物资数量！");
								return;
							}
						}else{
							var result = selectOutMaterial4EditDialog.resule;
							var count = result[0].inventoryCount;
							var intcount = parseInt(count);
							var intMaterialCount = parseInt(objMaterialCount);
							if(intMaterialCount>intcount){
								alert("数量不能大于物资数量！");
								return;
							}
						}
    					$("#editForm").submit();
    				});
					 
					//由迁移修改页面返回列表页面
    				$("#backBtnEdit").on("click",function(){
    					$("#page-container").load(format_url('/outstockMove/index'));
    				});
					
				});
			});
        </script>
    </body>
</html>