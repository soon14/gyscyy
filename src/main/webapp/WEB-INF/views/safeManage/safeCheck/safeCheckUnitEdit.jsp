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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addTargetForm">
			   <input id="id" class="col-md-12" name="id"   readonly  type="hidden" value="${ entity.id }"/>
<%-- 			   <input class="col-md-12" id="" name="createPeopleId" type="hidden" value="${entity.createPeopleId }"> --%>
	           <div class="form-group">
	           <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>填写人
					</label>
					<div class="col-md-4">
						<input class="col-md-12" id="userName" readonly="readonly" name="userName" type="text" placeholder="" maxlength="20" value="${sysUserEntity.name }">
						<input class="col-md-12" id="createPeopleId" name="createPeopleId" type="hidden" placeholder="" maxlength="20" value="${sysUserEntity.id }">
                	</div>
                     <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>单位
				    </label>
				    <div class="col-md-4">
						<select id="unitIdSelect" name="unitId" class="col-md-12 chosen-select"></select>
                    </div>
                    
               </div>
		       <div class="form-group">
				   
					 <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>年号
				    </label>
				    <div class="col-md-4">
						<div id="targetDateDiv"></div>
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>检查时间
					</label>
					<div class="col-md-4">
					   <div id="signDateDiv"></div>
                	</div>
               </div>
              <div class="form-group">
                   <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>类别
				    </label>
				    <div class="col-md-4">
					    	<select id="categorySelect" name="category" class="col-md-12 chosen-select"></select>
					</div>
					<label class="col-md-2 control-label no-padding-right">
					<span style="color:red;">*</span>名称
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="checkName" name="checkName"style="resize:none;"  placeholder="" type="text" maxlength="128" value="${ entity.checkName}">
                	</div>
                	
               </div>
               <div class="form-group">
               <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>整改时间
					</label>
					<div class="col-md-4">
					   <div id="rectificationTimeDiv"></div>
                	</div>
                	<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>检查人员
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="landWaiter" name="landWaiter"style="resize:none;"  placeholder="" type="text" maxlength="128" value="${ entity.landWaiter}">
                	</div>
               </div>
               <div class="form-group">
				<label class="col-md-2 control-label no-padding-right">
				<span style="color:red;">*</span>附件
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
					var appData = ${entityJson};
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${entity.fileAddress},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
						options : {
							maxFiles:1,
						}
					}).render();
					
					//日期组件 年号
					var targetDateDatePicker = new A.my97datepicker({
						id: "targetDateId",
						name:"targetDate",
						render:"#targetDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy"
						}
					}).render();
					targetDateDatePicker.setValue(appData.yearNum);
					//日期组件 检查时间
					var signDateDatePicker = new A.my97datepicker({
						id: "signDateId",
						name:"signDate",
						render:"#signDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					signDateDatePicker.setValue(appData.exportDate);
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
					rectificationTimePicker.setValue(appData.rectificationTime);
					//单位
					 searchunitid = new A.combobox({
						render : "#unitIdSelect",
						datasource : ${unitList},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					searchunitid.setValue(appData.unitId);
					//类别
					 categoryCombobox = new A.combobox({
						render : "#categorySelect",
						datasource : ${safeCheckTypeEnumCombobox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					 categoryCombobox.setValue(appData.category);
					 
						
					var id = $('#id').val();
					$('#addTargetForm').validate({
						debug:true,
						rules:  {
							id:{      maxlength:20},
							name:{required:true,maxlength:64},
							targetDate:{ required:true,maxlength:64},
							signDate:{ required:true, maxlength:64},
							category:{ required:true , maxlength:64},
							fileId:{      maxlength:128},
							checkName:{      maxlength:128},
							rectificationTime:{required:true ,maxlength:128},
							landWaiter:{required:true ,maxlength:128}
						},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/safeCheck/saveEditPage/2");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addTargetForm").serializeObject();
							obj.yearNum = $('#targetDateId').val();
							obj.checkDate = $('#signDateId').val();
							//附件上传
							obj.fileAddress=JSON.stringify(uploaddropzone.getValue());
							obj.type=2
							
							if(eval(uploaddropzone.getValue()).length==0){
								alert("请至少添加一个附件");
								return;
							}
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('修改成功');
									listFormDialog.close();
								},
								error:function(v,n){
									alert('修改失败');
								}
							});
						}
					});
					$("#saveBtn").on("click", function(){
						var yearNumStr = targetDateDatePicker.getValue();
						var checkDateStr = signDateDatePicker.getValue().split("-")[0];
						if(yearNumStr!=checkDateStr){
							alert("年号和时间不符");
							return;
						}
						$("#addTargetForm").submit();
    				});
    				
				});
			});
        </script>
    </body>
</html>