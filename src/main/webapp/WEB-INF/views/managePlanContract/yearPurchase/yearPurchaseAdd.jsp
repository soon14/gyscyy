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
				<li class="active">新增</li>
			</ul>
		</div>
		<div class="page-content" style="margin-left:30px;margin-right:30px;">
		<div style="float:right;margin-right:50px;">
			<button id="btnBack"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style=" margin-top: 14px;">
       			<i class="fa fa-reply"></i>
       			返回
       		</button>
		</div>
		<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >基础信息</h5>
		<div class="col-md-12" style="margin-top: 30px">
			<form class="form-horizontal" role="form"  style="margin-right:200px;" id="addForm">
			 <input class="col-md-12" id="companyUnit" name="companyUnit" type="hidden"  value="${organizationEntity.id }" >
<!-- 			   <div class="form-group"> -->
<!-- 			   		<label class="col-md-2 control-label no-padding-right"> -->
<!-- 						<span style="color:red;">*</span>计划编号 -->
<!-- 				    </label> -->
<!-- 				    <div class="col-md-4"> -->
<!-- 						<input class="col-md-12" id="code" name="code" type="text" placeholder="" maxlength="64" value=""> -->
<!--                     </div> -->
<!--                </div> -->
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>项目采购名称
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="name" name="name" type="text" placeholder="" maxlength="64" value="">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						采购计划名称
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="projectName" name="projectName" type="text" placeholder="" maxlength="64" value="">
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
						<span style="color:red;">*</span>规格型号
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="specification" name="specification" type="text" placeholder="" maxlength="64" value="">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>数量
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="amount" name="amount" type="text" placeholder="0" maxlength="64" value="" 
	                   onkeyup="checkInt(this);" onpaste="checkInt(this);" oncut="checkInt(this);" ondrop="checkInt(this);" onchange="checkInt(this);">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>预算单价(万元)
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="budgetPrice" name="budgetPrice" type="text" placeholder="0" maxlength="64" value="" 
						onkeyup="checkP(this);" onpaste="checkP(this);" oncut="checkP(this);" ondrop="checkP(this);" onchange="checkP(this);">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>预算总价(万元)
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="totalPrice" name="totalPrice" type="text" placeholder="0" maxlength="64" value=""  readonly="readonly"   onkeyup="value=value.replace(/[\D]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[\D]/g,''))">
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
						采购人员
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="createUserId" name="createUserId" type="text" readonly="readonly" placeholder="" maxlength="64" value="${userName }" >
                	</div>
                	<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>计数单位
				    </label>
				    <div class="col-md-4">
						<select id="unit" name="unit" class="form-control chosen-select" style="width: 150px;"></select>
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>责任处室
					</label>
					<div class="col-md-4">
	                   <select id="dutyUnit" name="dutyUnit" class="form-control chosen-select" style="width: 150px;"></select>
                	</div>
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
						<textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128"></textarea>
					</div>
               </div>
		    	 <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						附件
				    </label>
						<div class="col-md-10" id="divfile">
                       </div>
               </div>
            </form>
    		<div style="margin-right:100px;">
    		
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					
					//项目类别
					var statusCombobox = new A.combobox({
						render: "#type",
						datasource:${typeCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					//日期组件
					var buyTimeDatePicker = new A.my97datepicker({
						id: "buyTimeId",
						name:"buyTime",
						render:"#buyTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM"
						}
					}).render();
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
					
					//责任处室
					var dutySource = ${unitList};
					var dutyunitCombobox = new A.combobox({
						render: "#dutyUnit",
						//返回数据待后台返回
						datasource:dutySource,
						multiple:false,
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					
					$('#addForm').validate({
						debug:true,
						rules:  {
							id:{      maxlength:20},
// 							code:{ required:true ,     maxlength:64},
							name:{ required:true ,     maxlength:64},
							type:{  required:true ,    maxlength:64},
							specification:{ required:true ,     maxlength:64},
							amount:{ digits:true,required:true ,     maxlength:64},
							budgetPrice:{number:true, required:true ,     maxlength:64},
							totalPrice:{number:true,  required:true ,    maxlength:64},
							buyTime:{ required:true ,     maxlength:64},
							projectName:{      maxlength:64},
							remark:{      maxlength:128},
							dutyUnit:{ required:true ,  maxlength:64},
							companyUnit:{      maxlength:64},
							unit:{  required:true ,     maxlength:128}
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/yearPurchase/addSave");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							
							obj.buyTime=$("#buyTimeId").val()+"-01 00:00:00";
							//附件上传
							obj.fileId=JSON.stringify(uploaddropzone.getValue());
							
							obj.ifEndStatus = 0;
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('添加成功');
									A.loadPage({
										render : '#page-container',
										url : format_url("/yearPurchase/list/"+'/2')
									});
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					$("#saveBtn").on("click", function(){
						$("#addForm").submit();
    				});
					
					$("#btnBack").on("click", function(){
						A.loadPage({
							render : '#page-container',
							url : format_url("/yearPurchase/list/"+'/2')
						});
    				});
				});
			});
			
			//预算总价计算
			function checkInt(o){ 
				theV=isNaN(parseInt(o.value))?0:parseInt(o.value); 
				if(theV!=o.value){o.value=theV;} 
				totalPrice.value=amount.value*budgetPrice.value; 
				} 
			function checkP(o){ 
				theV=isNaN(parseFloat(o.value))?0:parseFloat(o.value); 
				theV=parseInt(theV*100)/100; 
				if(theV!=o.value){ 
				theV=(theV*100).toString(); 
				theV=theV.substring(0,theV.length-2)+"."+theV.substring(theV.length-2,theV.length) 
				o.value=theV; 
				} 
				totalPrice.value=amount.value*budgetPrice.value; 
				} 
        </script>
    </body>
</html>