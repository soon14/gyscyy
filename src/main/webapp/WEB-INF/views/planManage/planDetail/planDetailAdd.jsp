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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addDetailForm">
				<input id="num"  name="num"  value="${num}" type="hidden" />
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>项目名称
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="projectName" name="projectName" type="text" placeholder="" maxlength="128" value="">
                    </div>
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>具体项目明细
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="projectDetail" name="projectDetail" type="text" placeholder="" maxlength="128" value="">
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>方案、措施
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="step" name="step" type="text" placeholder="" maxlength="128" value="">
                    </div>
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>计划开工时间
					</label>
					<div class="col-md-4">
						<div id="stratTimeDetailDiv"></div>
	                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>计划完成时间
				    </label>
				    <div class="col-md-4">
					    <div id="endTimeDetailDiv"></div>
	                    </div>
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>计划总投资（万元）
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="planTotal" name="planTotal" type="text" placeholder="" maxlength="20" value="">
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>完成状态
				    </label>
				    <div class="col-md-4">
				     	<select class="col-md-12 chosen-select" id="finish" name="finish"></select>
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						备注
					</label>
					<div class="col-md-10">
	                   <textarea class="col-md-12" id="remark" name="remark" 
	                   	 style="height:100px; resize:none;"  	 placeholder="" maxlength="128" ></textarea>
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						附件
				    </label>
					    <div class="col-xs-10" id="divfile">
						</div>
               </div>
            </form>
    		<div style="margin-right:100px;">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="saveDetailBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					var finishType = new A.combobox({
						render : "#finish",
						datasource : ${finishType},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					//日期组件
					var timeDatePicker = new A.my97datepicker({
						id: "stratTimeId",
						name:"stratTime",
						render:"#stratTimeDetailDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM"
						}
					}).render();
					//日期组件
					var timeDatePicker = new A.my97datepicker({
						id: "endTimeId",
						name:"endTime",
						render:"#endTimeDetailDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM"
						}
					}).render();
					$('#addDetailForm').validate({
						debug:true,
						rules:  {
							projectName:{   required:true,   maxlength:128},
							projectDetail:{ required:true,     maxlength:128},
							step:{ required:true,     maxlength:128},
							stratTime:{ required:true,     maxlength:20},
							endTime:{   required:true,   maxlength:20},
							planTotal:{ required:true,     maxlength:20,min:0,minNumber:$("#planTotal").val()},
							finish:{   required:true,   maxlength:20},
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/planDetail/add");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addDetailForm").serializeObject();
							obj.fileId=JSON.stringify(uploaddropzone.getValue());
							obj.planWholeId='${planWholeId}';
							obj.uuidWhole=$("#uuidWhole").val();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result=="success"){
										alert('保存成功');
										listFormDialog.close();
									}else{
										alert(result.errorMsg);
									}
								},
								error:function(v,n){
									alert('保存失败');
								}
							});
						}
					});
					$("#saveDetailBtn").on("click", function(){
						$("#addDetailForm").submit();
    				});
					 jQuery.validator.addMethod("minNumber",function(value, element){
				            var returnVal = true;
				            inputZ=value;
				            var ArrMen= inputZ.split(".");    //截取字符串
				            if(ArrMen.length==2){
				                if(ArrMen[1].length>2){    //判断小数点后面的字符串长度
				                    returnVal = false;
				                    return false;
				                }
				            }
				            return returnVal;
				        },"小数点后最多为两位");         //验证错误信息

				});
			});
        </script>
    </body>
</html>