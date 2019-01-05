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
			   <input class="col-md-12" id="" name="createPeopleId" type="hidden" value="${entity.createPeopleId }">
				<div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>填写人
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="userName" readonly="readonly" name="userName" type="text" placeholder="" maxlength="20" value="${sysUserEntity.name }">
						<input class="col-md-12" id="userId" name="userId" type="hidden" placeholder="" maxlength="20" value="${sysUserEntity.id }">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>单位
					</label>
					<div class="col-md-4">
										<select id="unitId" name="unitId" ></select>
								</div>
               </div>	          
	           <div class="form-group">
<!-- 				    <label class="col-md-2 control-label no-padding-right"> -->
<!-- 						<span style="color:red;">*</span>目标责任书名称 -->
<!-- 				    </label> -->
<!-- 				    <div class="col-md-4"> -->
<!-- 						<input class="col-md-12" id="name" name="name" type="text" placeholder="" maxlength="64" value=""> -->
<!--                     </div> -->
                     <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>年号
				    </label>
				    <div class="col-md-4">
						<div id="targetDateDiv"></div>
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>日期
					</label>
					<div class="col-md-4">
					   <div id="signDateDiv"></div>
                	</div>
               </div>
              <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>名称
					</label>
					<div class="col-md-4">
								<input class="col-md-12" id="reportName" name="reportName" type="text" placeholder="" maxlength="64" value="${entity.reportName }">
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
					//单位
					 searchunitid = new A.combobox({
						render : "#unitId",
						datasource : ${unitList},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					 searchunitid.setValue(${entity.unitId});
					$('#addTargetForm').validate({
						debug:true,
						rules:  {
							targetDate:{required:true,maxlength:64},
							signDate:{ required:true, maxlength:64},
							unitId:{ required:true, maxlength:64},
							reportName:{ required:true , maxlength:64}
						},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/safeMeetings/saveEditPage");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addTargetForm").serializeObject();
							obj.yearNum = $('#targetDateId').val();
							obj.time = $('#signDateId').val();
							//附件上传
							obj.fileAddress=JSON.stringify(uploaddropzone.getValue());
							
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