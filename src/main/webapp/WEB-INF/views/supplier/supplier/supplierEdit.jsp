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
					<a href="#">首页</a>
				</li>
				<li>供应商管理</li>
				<li class="active">供应商管理</li>
				<li class="active">编辑</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="col-md-12" >
			<div style="margin-top:10px;">
        		<button id="returnBtn" class="btn btn-xs btn-primary pull-right">
    				<i class="fa fa-reply"></i>
    				返回
    			</button>
        	</div>
			<h5 class="table-title-withoutbtn header smaller blue" >基础信息</h5>
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addForm">
			<input id="id" name="id" type="hidden" value="${ entity.id }">
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>供应商名称
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="supplierName" name="supplierName" type="text" placeholder="" maxlength="20" value="${ entity.supplierName }">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>供应商类型
				    </label>
				    <div class="col-md-4">
						<select class="col-md-12 chosen-select" id="supplierType" name="supplierType"></select>
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						公司电话
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="companyTel" name="companyTel" type="text" placeholder="" maxlength="20" value="${ entity.companyTel }">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						公司传真
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="companyFax" name="companyFax" type="text" placeholder="" maxlength="64" value="${ entity.companyFax }">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						公司网址
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="companyWebsite" name="companyWebsite" type="text" placeholder="" maxlength="64" value="${ entity.companyWebsite }">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						公司邮箱
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="companyEmail" name="companyEmail" type="text" placeholder="" maxlength="64" value="${ entity.companyEmail }">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						地址
					</label>
					<div class="col-md-10">
	                   <input class="col-md-12" id="address" name="address" type="text" placeholder="" maxlength="64" value="${ entity.address }">
                	</div>
               </div>
            </form>
            <div class="row">
			    <div class="col-xs-12" >	
				    <h5 class="table-title header smaller blue "  >联系人</h5>
					<div class="widget-main no-padding">
						<form id="supplierContactForm" >
							<table id="supplierContact_table"  class="table table-striped table-bordered table-hover" style="width:100%;">
								<thead>
									<tr>
										<th style="display:none;">主键</th>
										<th>序号</th>
			                          	<th><span style="color:red;">*</span>姓名</th>
		                                <th><span style="color:red;">*</span>性别</th>
		                                <th><span style="color:red;">*</span>部门</th>
		                                <th><span style="color:red;">*</span>职务</th>
		                                <th><span style="color:red;">*</span>负责业务</th>
		                                <th><span style="color:red;">*</span>电话</th>
		                                <th><span style="color:red;">*</span>手机</th>
		                                <th><span style="color:red;">*</span>邮箱</th>
	                                    <th>操作</th>
									</tr>
								</thead>
							</table>
						</form>
					</div>
				</div>
			</div>
    		<div style="margin-right:100px;margin-top:100px">
    			<button id="cancelBtn" class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					var entity = ${entityJson};
					//combobx组件
					var supplierType=${supplierType};
					var supplierTypeCombobox = new A.combobox({
						render: "#supplierType",
						//返回数据待后台返回TODO
						datasource:supplierType,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					supplierTypeCombobox.setValue(entity.supplierType);
					$('#addForm').validate({
						debug:true,
						rules:{
								supplierName:{required:true,maxlength:20},
								supplierType:{required:true,maxlength:20},
								companyTel:{maxlength:20},
								companyFax:{maxlength:64},
								companyWebsite:{maxlength:64},
								companyEmail:{maxlength:64},
								address:{maxlength:64}
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/supplier/saveEditPage");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							var contactObj = $("#supplierContactForm").serializeObject();
							
							//验证公司邮箱格式
							if(obj.companyEmail){
								var patrn = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
								if(!patrn.exec(obj.companyEmail)){
									alert("公司邮箱地址格式不正确！");
									return;
								}
							}
							//验证公司网址格式
							if(obj.companyWebsite){
								var patrn = /http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/
								if(!patrn.exec(obj.companyWebsite)){
									alert("公司网址格式不正确！应以'http://www.'开头！");
									return;
								}
							}
							//验证公司电话格式
							if(obj.companyTel){
// 								var patrn = /^[0][1-9]{2,3}-[0-9]{5,10}$/;
								var patrn =  /^0\d{2,3}-?\d{7,8}$/;
								if(!patrn.exec(obj.companyTel)){
									alert("公司电话格式不正确！应以区号开头！");
									return;
								}
							}
							
							obj.contactName = contactObj.name;
							obj.contactSex = contactObj.sex;
							obj.contactDepartment = contactObj.department;
							obj.contactDuty = contactObj.duty;
							obj.contactBusiness = contactObj.business;
							obj.contactTelephone = contactObj.telephone;
							obj.contactMobilePhone = contactObj.mobilePhone;
							obj.contactEmail = contactObj.email;
							
							for (i=0;i<obj.contactName.length ;i++ ){
								//验证联系人电话格式
								if(obj.contactTelephone[i].length>1){
									var patrn =  /^0\d{2,3}-?\d{7,8}$/;
									if(!patrn.exec(obj.contactTelephone[i])){
										alert("联系人电话格式不正确！应以区号开头！");
										return;
									}
								}else{
									var patrn =  /^0\d{2,3}-?\d{7,8}$/;
									if(!patrn.exec(obj.contactTelephone)){
										alert("联系人电话格式不正确！应以区号开头！");
										return;
									}
								}
								//验证联系人手机格式
								if(obj.contactMobilePhone[i].length>1){
									var patrn = /^1(3|4|5|7|8)\d{9}$/;
									if(!patrn.exec(obj.contactMobilePhone[i])){
										alert("联系人手机号格式不正确！");
										return;
									}
									
								}else{
									var patrn = /^1(3|4|5|7|8)\d{9}$/;
									if(!patrn.exec(obj.contactMobilePhone)){
										alert("联系人手机号格式不正确！");
										return;
									}
								}
								//验证联系人邮箱格式
								if(obj.contactEmail[i].length>1){
									var patrn = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
									if(!patrn.exec(obj.contactEmail[i])){
										alert("联系人邮箱地址格式不正确！");
										return;
									}
								}else{
									var patrn = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
									if(!patrn.exec(obj.contactEmail)){
										alert("联系人邮箱地址格式不正确！");
										return;
									}
								}
							}
							
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result=="exception"){
										alert('修改失败！供应商名称不可重复！');
										return;
									}else{
										alert('修改成功');
										$("#page-container").load(format_url('/supplier/index/'));
										
									}
								},
								error:function(v,n){
									alert('修改失败');
								}
							});
						}
					});
					$('#returnBtn').on('click',function(){
						 $("#page-container").load(format_url('/supplier/index/'));
					});
					
					initDataTable();
					function validform(){
						 /*关键在此增加了一个return，返回的是一个validate对象，这个对象有一个form方法，返回的是是否通过验证*/
						 return $('#supplierContactForm').validate({
								debug:true,
								rules:{
									name:{required:true,maxlength:20},
									sex:{required:true,maxlength:20},
									department:{required:true,maxlength:64},
									duty:{required:true,maxlength:64},
									business:{required:true,maxlength:64},
									telephone:{maxlength:20},
									mobilePhone:{required:true,maxlength:11},
									email:{required:true,maxlength:64},
									}
							});
						}
					//关键词内容填写模块
					var conditions=[];
					supplierContactFormDatatables = new A.datatables({
						render: '#supplierContact_table',
						options: {
							    serverSide: false,
						        multiple : true,
						        checked:false,
								bInfo:false,
								paging:false,
								ordering:true,
								optWidth: 120,
								order:[["1",'asc']],
							    "fnDrawCallback"    : function(){
								this.api().column(1).nodes().each(function(cell, i) {
								cell.innerHTML =  i + 1;
							});
						},
							columns: [
							 		  {data:"id", visible:false,orderable:false}, 
							 		  {data: null,width: "3%",orderable: false},
							          {data: "name",width: "10%",orderable: false, editType:"input",
										 	render : function(data, type, row, meta) {
										        return "<input name='name' type='text' style='width:100%;' value='"+data+"'/>";  
										      }}, 
							          {data: "sex",width: "10%",orderable: false,editType:"combobox",cfg:{ datasource : ${sexType}, allowBlank: false }}, 
							          {data: "department",width: "12%",orderable: false, editType:"input",
											 	render : function(data, type, row, meta) {
											        return "<input name='department' type='text' style='width:100%;' value='"+data+"'/>";  
											      }}, 
							          {data: "duty",width: "12%",orderable: false, editType:"input",
											 	render : function(data, type, row, meta) {
											        return "<input name='duty' type='text' style='width:100%;' value='"+data+"'/>";  
											      }}, 
							          {data: "business",width: "12%",orderable: false, editType:"input",
											 	render : function(data, type, row, meta) {
											        return "<input name='business' type='text' style='width:100%;' value='"+data+"'/>";  
											      }}, 
							          {data: "telephone",width: "12%",orderable: false, editType:"input",
											 	render : function(data, type, row, meta) {
											        return "<input name='telephone' type='text' style='width:100%;' value='"+data+"'/>";  
											      }}, 
							          {data: "mobilePhone",width: "12%",orderable: false, editType:"input",
											 	render : function(data, type, row, meta) {
											        return "<input name='mobilePhone' type='text' style='width:100%;' value='"+data+"'/>";  
											      }}, 
							          {data: "email",width: "12%",orderable: false, editType:"input",
											 	render : function(data, type, row, meta) {
											        return "<input name='email' type='text' style='width:100%;' value='"+data+"'/>"+"<input name='id' value='"+row.id+"' type='hidden'/>";  
											      }}
							 ],
							optWidth:30,
							toolbars: [{
       						label:"新增",
       						icon:"glyphicon glyphicon-plus",
       						className:"btn-success",
       						events:{
           						click:function(event){
           							supplierContactFormDatatables.addRow({"id":null, "name":"", "sex":"", "department":"", "duty":"", 
           								"business":"", "telephone":"", "mobilePhone":"", "email":""});
           						}
       						}
       					}],
							btns:[
								{
									id:"delete",
									label:"删除",
									icon: "fa fa-trash-o bigger-130",
									className: "red del",
									events:{
										"click": function(event, nRow, nData){
											var id = nData.id;
											A.confirm('您确认删除么？',function(){
												supplierContactFormDatatables.deleteSelectRow(nRow);
											});		
										}
									}
								}
							]
						}
					}).render();
					function initDataTable(){
						var params = {};
						params.length = 100;
						params.start = 0;
						params.draw = 0;
						var conditions=[];
						conditions.push({
        					field: 'supplierId',
        					fieldType:'LONG',
        					matchType:'EQ',
        					value:$("#id").val()
        				});
						params.conditions = conditions;
						$.ajax({
							url: format_url("/supplierContact/search"),
							contentType: "application/json",
							dataType: 'JSON',
							type: 'POST',
							data : JSON.stringify(params),
							success: function(result){
								supplierContactFormDatatables.addRows(result.data);
							}
							
						})
					};
					
					//保存按钮
					$("#saveBtn").on("click", function(){
						if(validform().form()){
							var projectobj = $("#supplierContactForm").serializeObject();
							var flag = false;
							for(var key in projectobj){
								flag = true;
	 							break;
							}
							if(flag){
	 							$("#addForm").submit();
							}else{
								alert("请填写联系人信息");
							}
						}
						
   				});
					//取消按钮
					$("#cancelBtn").on("click", function(){
						$("#page-container").load(format_url('/supplier/index/'));
   				});
				});
			});
        </script>
    </body>
</html>