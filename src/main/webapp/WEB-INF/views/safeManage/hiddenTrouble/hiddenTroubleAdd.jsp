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
					<label class="col-md-2 control-label no-padding-right">
					 <span style="color:red;">*</span>填写人
				    </label>
				    <div class="col-md-4">
<!-- 				    <select class="col-md-12 chosen-select" id="createUserId" name="createUserId"></select> -->
				         <input  class="col-md-12" id="createUserId" name="createUserId" placeholder=""  type="text" maxlength="64" readonly="readonly" value="${userName }">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>单位
				    </label>
				    <div class="col-md-4">
					    	<select id="unitId" name="unitId" ></select>
					</div>
					</div>
			   
			       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>年号
				    </label>
				    <div class="col-md-4">
<!-- 						  <textarea class="col-md-12" id="troubleName" name="troubleName"style="height:80px; resize:none;"  placeholder="" maxlength="128"></textarea> -->
						   
						<div id="yearNumDiv"></div>
                    </div>
                	<label class="col-md-2 control-label no-padding-right">
					<span style="color:red;">*</span>	排查时间
					</label>
					<div class="col-md-4">
					   <div id="checkDateDiv"></div>
                	</div>
               </div>
                   <div class="form-group">
				  <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>类别
					</label>
					<div class="col-md-4">
					   <select class="col-md-12 chosen-select" id="category" name="category"></select>	
                	</div>
                	<label class="col-md-2 control-label no-padding-right">
					<span style="color:red;">*</span>	整改时间
					</label>
					<div class="col-md-4">
					   <div id="rectificationTimeDiv"></div>
                	</div>
               </div>
                <div class="form-group">
                <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>排查人员
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="investigator" name="investigator"style="resize:none;"  placeholder="" type="text" maxlength="128">
                	</div>
                </div>
               <div class="form-group">
               <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>隐患描述
						</label>
						<div class="col-md-10">
					      <textarea class="col-md-12" id="troubleName" name="troubleName"style="height:80px; resize:none;"  placeholder="" maxlength="128"></textarea>
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
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					//单位
					 searchunitid = new A.combobox({
						render : "#unitId",
						datasource : ${unitList},
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
					
					//combobx组件
					var categoryCombobox = new A.combobox({
						render: "#category",
						//返回数据待后台返回TODO
						datasource:${categoryCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//日期组件
					var yearNumDatePicker = new A.my97datepicker({
						id: "yearNumId",
						name:"yearNum",
						render:"#yearNumDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy"
						}
					}).render();
					//日期组件 整改时间
					var rectificationTimePicker = new A.my97datepicker({
						id: "rectificationTimeId",
						name:"rectificationTime",
						render:"#rectificationTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					//日期组件
					var checkDateDatePicker = new A.my97datepicker({
						id: "checkDateId",
						name:"checkDate",
						render:"#checkDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					
					
					$('#addForm').validate({
						debug:true,
						rules:  {
							id:{      maxlength:20},
							troubleName:{  required:true ,     maxlength:128},
							unitId:{  required:true ,     maxlength:128},
							category:{  required:true ,     maxlength:64},
							yearNum:{   required:true ,   maxlength:64},
							checkDate:{  required:true ,     maxlength:64},
							rectificationTime:{  required:true ,     maxlength:64},
							investigator:{  required:true ,     maxlength:64}
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/hiddenTrouble/addEntity");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							
							obj.yearNum = $('#yearNumId').val()+"-01-01 00:00:00";
							obj.checkDate = $('#checkDateId').val()+" 00:00:00";
							//附件上传
							obj.fileId=JSON.stringify(uploaddropzone.getValue());
							obj.type=$("#type").val()
							
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
						
						var yearNumStr = yearNumDatePicker.getValue();
						var checkDateStr = checkDateDatePicker.getValue().split("-")[0];
						if(yearNumStr!=checkDateStr){
							alert("年号和时间不符");
							return;
						}
						$("#addForm").submit();
    				});
    				
				});
			});
        </script>
    </body>
</html>