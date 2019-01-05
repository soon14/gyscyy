<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addForm">
				<div class="form-group">
				    <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>
						所属部门
				    </label>
				    <div class="col-md-4">
				    	<select id="contractXdname" name="contractXdname" class="col-md-12 chosen-select" ></select>
                    </div>
                    <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>
						供应商
					</label>
					<div class="col-md-4">
					   <select class="col-md-12 chosen-select" id="remark" name="remark"></select>
	                   <!-- <input class="col-md-12" id="remark" name="remark" type="text" placeholder="" maxlength="64" value=""> -->
                	</div>
               </div>
		       <div class="form-group">
                    <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>
						合同编号
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="contractCode" name="contractCode" type="text" placeholder="" maxlength="64" value="">
                	</div>
                	<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>
						已签合同名称
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="contractName" name="contractName" type="text" placeholder="" maxlength="64" value="">
                    </div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>
						合同金额（元）
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="contractMoney" name="contractMoney" type="text" placeholder="" maxlength="255" value="">
                    </div>
                    <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>
						签约时间
					</label>
					<div class="col-md-4">
					   <div id="qdrqDiv"></div>
                	</div>
               </div>
		       <div class="form-group">
                    <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>
						采购方式
				    </label>
				    <div class="col-md-4">
				    	<select class="col-md-12 chosen-select" id="zbfs" name="zbfs"></select>
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>合同履行开始时间
					</label>
					<div class="col-md-4">
	                   <div id="contractStartDateDiv"></div>
                	</div>
               </div>
		       <div class="form-group">
		       <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>
						合同年限
				    </label>
				    <div class="col-md-4">
				    	<select class="col-md-12 chosen-select" id="contractLife" name="contractLife"></select>
                    </div>
               <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>合同履行结束时间
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="contractEndDate" name="contractEndDate" readonly="readonly" type="text" placeholder="" >
                	</div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						附件上传
					</label>
					<div class="col-md-10" id="attachmentDiv">
					</div>
               </div>
            </form>
    		<div style="margin-right:100px;">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				var sysUserEntity = ${sysUserEntity};
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					
					//所属部门
					var contractXdnameCombobox = new A.combobox({
						render: "#contractXdname",
						datasource:${ssbmList},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					//履行合同开始时间
					var startDateDiv = new A.my97datepicker({
						id: 'contractStartDateId',
						name:'contractStartDate',
						render:'#contractStartDateDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					//采购方式
					var ztTypeCombobox = new A.combobox({
						render: '#zbfs',
						datasource:${purchaseModeIdList},
						allowBlank:true,
						options:{
							"allow_single_deselect": true,
							"disable_search_threshold":10
						}
					}).render();
					//合同年限
					var contractLifeCombobox = new A.combobox({
						render: '#contractLife',
						datasource:${contractLife},
						allowBlank:true,
						options:{
							"allow_single_deselect": true,
							"disable_search_threshold":10
						}
					}).render();
					contractLifeCombobox.change(function(){
						startDateDiv.setValue(null);
						var date = new Date(startDateDiv.getValue());
						$("#contractEndDate").val();
						if(date == "Invalid Date"){
							alert("请先选择合同履行开始时间！");
							$("#contractLife").val();
							contractLifeCombobox.clearValue();
						}else{
							var date = new Date(startDateDiv.getValue());
							var year =parseInt(contractLifeCombobox.getValue()) ;
							var y = date.getFullYear()+year;  
						    var m = date.getMonth()+1 ; 
							date.setDate(date.getDate()-1);
							var d = date.getDate();
						    m = m < 10 ? ('0' + m) : m;  
						    d = d < 10 ? ('0' + d) : d;  
						    var dateStr = y + '-' + m + '-' + d;
							$("#contractEndDate").val(dateStr);
						}
						
					});
					//供应商
					var gysCombobox = new A.combobox({
						render: '#remark',
						datasource:${supplierIdList},
						allowBlank:true,
						options:{
							"allow_single_deselect": true,
							"disable_search_threshold":10
						}
					}).render();
					
					
					//日期组件
					var qdrqPicker = new A.my97datepicker({
						id: "qdrqId",
						name:"qdrq",
						render:"#qdrqDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					
					
					
					var uploadattachment=new A.uploaddropzone({
						render : "#attachmentDiv",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					
					$('#addForm').validate({
						debug:true,
						rules:  {
							contractXdname:{required:true,maxlength:64},
							remark:{required:true,maxlength:64},
							contractCode:{required:true,maxlength:64},
							contractName:{required:true,maxlength:64},
							contractMoney:{required:true,maxlength:64,number:true},
							qdrq:{required:true,maxlength:64},
							zbfs:{required:true,maxlength:64},
							contractLife:{required:true,maxlength:64},
							contractStartDate:{required:true,maxlength:20},
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/contractManage");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							obj.attachment=JSON.stringify(uploadattachment.getValue());
							obj.createUserId = sysUserEntity.id;
							obj.updateUserId = sysUserEntity.id;
							obj.createDate = getFormatDate();
							obj.updateDate = getFormatDate();
							obj.status=1;
							obj.isnotReject=0;
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('添加成功');
									listFormDialog.close();
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					$("#saveBtn").on("click", function(){
						var date = new Date(startDateDiv.getValue());
						var year =parseInt(contractLifeCombobox.getValue()) ;
						var y = date.getFullYear()+year;  
					    var m = date.getMonth() + 1;  
					    m = m < 10 ? ('0' + m) : m;  
					    var d = date.getDate();  
					    d = d < 10 ? ('0' + d) : d;  
					    var dateStr = y + '-' + m + '-' + d;
					    var endDate = $("#contractEndDate").val();
					    var begin=new Date(dateStr.replace(/-/g,"/"));
					    begin.setDate(begin.getDate()-1)
					    var end=new Date($("#contractEndDate").val().replace(/-/g,"/"));
					    if(begin-end!=0){
					    	alert("开始时间、年限和结束时间不符！");
					    	contractLifeCombobox.clearValue();
					    	return;
					    }
						$("#addForm").submit();
    				});
    				
				});
			});
			function getFormatDate(){    
			    var nowDate = new Date();     
			    var year = nowDate.getFullYear();    
			    var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1) : nowDate.getMonth() + 1;    
			    var date = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();    
			    var hour = nowDate.getHours()< 10 ? "0" + nowDate.getHours() : nowDate.getHours();    
			    var minute = nowDate.getMinutes()< 10 ? "0" + nowDate.getMinutes() : nowDate.getMinutes();    
			    var second = nowDate.getSeconds()< 10 ? "0" + nowDate.getSeconds() : nowDate.getSeconds();    
			    return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;    
			} 
        </script>
    </body>
</html>