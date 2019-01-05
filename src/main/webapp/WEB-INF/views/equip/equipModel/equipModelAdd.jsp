<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/views/common/meta.jsp"%>
</head>
<body>
	<div class="breadcrumbs ace-save-state" id="breadcrumbs">
		<ul class="breadcrumb">
			<li>
				<i class="ace-icon fa fa-home home-icon"></i>
				<a href="javascript:void(0);" onclick="firstPage()">首页</a>
			</li>
			<li>设备管理</li>
			<li>设备参数配置</li>
			<li>
				设备模版
			</li>
			<li class="active">新增</li>
		</ul><!-- /.breadcrumb -->
	</div>
	<div class="col-md-12" style="width: 100%;">
		<form class="form-horizontal" role="form"   id="equipModelform">
	           <input class="col-md-12" id="modelId" name="modelId" type="hidden"  maxlength="20" >
	           		<div style="margin:10px 0px 0 0;" >
			    			<button class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" id="button"  type="button">
			        			<i class="fa fa-reply"></i>
			        			返回
			        		</button>
					</div>
	           		<h5 class='table-title header smaller blue' style="margin-bottom:20px!important;">模版信息</h5>
	           		<div style="margin-right:200px;">
						<div class="form-group" >
							<label class="col-md-2 control-label no-padding-right">
								模版编号 </label>
							<div class="col-md-4">
								<input class="col-md-12" id="modelNumber" name="modelNumber"
									type="text" maxlength="64" value="${modelCode}" readonly="readonly">
							</div>
							<label class="col-md-2 control-label no-padding-right">
								<span style="color:red;">*</span>规格型号 </label>
							<div class="col-md-4">
								<input class="col-md-12" id="specificationModel"
									name="specificationModel" type="text"
									 maxlength="64"
									>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">
								<span style="color:red;">*</span>设备名称 </label>
							<div class="col-md-4">
								<input class="col-md-12" id="equipName" name="equipName"
									type="text" 
									maxlength="64" >
							</div>
							<label class="col-md-2 control-label no-padding-right">
								<span style="color:red;">*</span>制造商 </label>
							<div class="col-md-4">
								<input class="col-md-12" id="manuFacturer"
									name="manuFacturer" type="text"
									 maxlength="64"
									>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">
								<span style="color:red;">*</span>设备类型 </label>
							<div class="col-md-4">
								<select class="col-md-12 chosen-select" id="equipType"
									name="equipType"></select>
							</div>
						</div>
					</div>
         </form>
           <div class="row">
				<div class="col-xs-12">
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' style="margin-bottom:0px;">基础参数</h5>
			         	<form id="parameterForm">
							<table id="equipParameter-table" class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th style="display:none;">主键</th>
		                                <th>参数名称</th>
		                                <th>默认值</th>
		                                <th>操作</th>
	                                </tr>
	                            </thead>
	                        </table>
				        </form>
                    </div>
                </div>
            </div>
             <div class="row">
				<div class="col-xs-12">
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' style="margin-bottom:0px;">技术参数</h5>
						<form id="technologyForm"> 
							<table id="equipTechnologyParameter-table" class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th style="display:none;">主键</th>
		                                <th>参数名称</th>
		                                <th>默认值</th>
		                                <th>操作</th>
	                                </tr>
	                            </thead>
	                        </table>
				        </form>   
                    </div>
                </div>
            </div>

		<div class="form-group">
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-top:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
		 </div>
	</div>
	<script type="text/javascript">
	var equipParameterDatatables,equipTechnologyDatatables;
		jQuery(function($) {
			seajs.use([ 'datatables','combobox','dialog'], function(A){
 				//设备类型
	            var equipTypeCombobox = new A.combobox({
					render : "#equipType",
					datasource : ${equipType},
					multiple : false,
					allowBlank : true,
					options : {
						"disable_search_threshold" : 10
					}
				}).render(); 				

				$('#equipModelform').validate({
					debug:true,
					rules: {
						specificationModel: {
							required: true
						},
						equipName: {
							required: true,
							maxlength: 64
						},
						equipType: {
							required: true
						},
						manuFacturer: {
							required: true,
							maxlength: 64
						}
					},
					submitHandler: function (form){
						var params = {};
						var url = format_url("/equipModel/add");
						var obj = $("#equipModelform").serializeObject();
					    //设备参数
						var baseParameterList = $("#parameterForm").arraySerializeObject();
					    //技术参数
						var technologyParameterList = $("#technologyForm").arraySerializeObject();
						params.equipModelInfo = obj;
						params.baseParameter = baseParameterList;
						params.technologyParameter = technologyParameterList;
						$.ajax({
							url : url,
							contentType : 'application/json',
							dataType : 'JSON',
							data : JSON.stringify(params),
							cache: false,
							type : 'POST',
							success: function(result){
								$("#page-container").load(format_url('/equipModel/index/10'));
								if(result.data!=null){
									alert('添加成功');
									$("#page-container").load(format_url('/equipModel/index/10'));
								}else{
									alert(result.errorMsg);
								}
							},
							error:function(v,n){
								alert('添加失败');
							}
						});
					}
				});
				
				//基础参数
				equipParameterDatatables = new A.datatables({
					render: '#equipParameter-table',
					options: {
				        serverSide: false,
				        multiple : true,
				        checked:false,
						bInfo:false,
						paging:false,
						ordering:true,
						optWidth: 80,
						columns: [{data:"id", visible:false,orderable:false},
						          {data: "parameter",width: "auto",orderable: true,editType:"input"}, 
						          {data: "defaultValue",width: "auto",orderable: true,editType:"input"}
					   ],
					   toolbars: [{
							label:"新增",
							icon: "glyphicon glyphicon-plus",
							className:"btn-success",
							events:{
								click: function(event){
	 								equipParameterDatatables.addRow({"id":null, "parameter":"", "defaultValue":""});
								}
							}
					   }],
					   btns:[{
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								events:{
									"click": function(event, nRow, nData){
										var id = nData.id;
										A.confirm('您确认删除么？',function(){
											equipParameterDatatables.deleteSelectRow(nRow);
										});		
									}
								}
							}
						]
					}
				}).render();
				//技术参数
				equipTechnologyDatatables = new A.datatables({
					render: '#equipTechnologyParameter-table',
					options: {
				        serverSide: false,
				        multiple : true,
				        checked:false,
						bInfo:false,
						paging:false,
						ordering:true,
						optWidth: 80,
						columns: [{data:"id", visible:false,orderable:false},
						          {data: "parameter",width: "auto",orderable: true,editType:"input"},
						          {data: "defaultValue",width: "auto",orderable: true,editType:"input"}
						],
				 		toolbars: [{
								label:"新增",
								icon: "glyphicon glyphicon-plus",
								className:"btn-success",
								events:{
									click: function(event){
										equipTechnologyDatatables.addRow({"id":null, "parameter":"", "defaultValue":""});
									}
								}
					   		}
				 		],
				   		btns:[{
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								events:{
									"click": function(event, nRow, nData){
										var id = nData.id;
										A.confirm('您确认删除么？',function(){
											equipTechnologyDatatables.deleteSelectRow(nRow);
										});		
									}
								}
							}
						]
					}
				}).render();
		        //基础参数动态表单验证
				$('#parameterForm').validate({
					debug:true,
					rules:  {
						parameter:{required:true}
					}
				});
	 			//技术参数动态表单验证
				$('#technologyForm').validate({
					debug:true,
					rules:  {
						parameter:{required:true}
					}
				});
				//保存
				$("#saveBtn").on("click", function(){
					if(validform2().form() && validform1().form()){
						$("#equipModelform").submit();
						$("#parameterForm").submit();
						$("#technologyForm").submit();
					}
				});
				//返回
				$("#button").on("click", function(e){
					window.scrollTo(0,0);
					$("#page-container").load(format_url('/equipModel/index/10'));
				});
				
				function validform1(){
					 /*关键在此增加了一个return，返回的是一个validate对象，这个对象有一个form方法，返回的是是否通过验证*/
					 return $('#technologyForm').validate({
							debug:true,
							rules:  {
								parameter:{required:true,maxlength: 32},
								defaultValue:{maxlength: 32}
							}
						});
				}
				function validform2(){
					 /*关键在此增加了一个return，返回的是一个validate对象，这个对象有一个form方法，返回的是是否通过验证*/
					 return $('#parameterForm').validate({
							debug:true,
							rules:  {
								parameter:{required:true,maxlength: 32},
								defaultValue:{maxlength: 32}
							}
						});
				}
			});
		});
	</script>
</body>
</html>