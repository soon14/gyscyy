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
				<form class="form-horizontal" role="form" style="margin-right:100px;" id="overhaulLogDetailForm">
				<input id="overhaulArrangeId" name="overhaulArrangeId" value="${recordId}" type="hidden"/>
<%-- 				<input id="overhaulArrangeId" name="overhaulArrangeId" value="${overhaulLogId}" type="hidden"/> --%>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
					  外协单位
                    </label>
					<div class="col-md-4">
				    	<input id="unitName" name="unitName" class="col-md-12" type="text" value="${overhaulLogDetailEntities.unitName}"/>
				    </div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>检修负责人
                    </label>
					<div class="col-md-4">
				    	<select id="dutyPersonId" name="dutyPerson" class="col-md-12 chosen-select" name="chargeId" data-placeholder="请选择负责人"></select>
				    </div>
                </div>
                <div class="form-group">
                <label class="col-md-2 control-label no-padding-right">
					<span style="color:red;">*</span>开始时间
                    </label>
					<div class="col-md-4">
                    	<div id="logDateDiv"></div>
                    </div>
                   <label class="col-md-2 control-label no-padding-right">
					<span style="color:red;">*</span>结束时间
                    </label>
					<div class="col-md-4">
                    	<div id="endDatetimeDiv"></div>
                    </div>
                </div>
			    <div class="form-group">
				   <label class="col-md-2 control-label no-padding-right">
					  损失电量(万kWh)
                    </label>
                    <div class="col-md-4">
                        <input id="afterMeet" class="col-md-12" style="resize:none;" name="afterMeet" type="number" placeholder="请输入损失电量" maxlength="128" value="${overhaulLogDetailEntities.afterMeet}">
                    </div>
                </div>
			    <div class="form-group">
             		<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>工作班成员
                    </label>
                    <div class="col-md-10">
	                    <textarea id="teamMemberNames" class="col-md-12" style="height:150px; resize:none;" name="teamMember" type="text" placeholder="请输入工作班成员" maxlength="128" value="">${overhaulLogDetailEntities.teamMember}</textarea>
                    </div>
                </div>
		        <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>工作完成情况
                    </label>
					<div class="col-md-10">
	                    <textarea id="workFinishInfo" class="col-md-12" style="height:150px; resize:none;" name="workFinishInfo" type="text" placeholder="请输入工作完成情况" maxlength="128" value="">${overhaulLogDetailEntities.workFinishInfo}</textarea>
                    </div>
                </div>
		        <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>遗留问题和应急措施
                    </label>
					<div class="col-md-10">
	                    <textarea id="problemMeasures" class="col-md-12" style="height:150px; resize:none;" name="problemMeasures" type="text" placeholder="请输入遗留问题和应急措施" maxlength="128" value="">${overhaulLogDetailEntities.problemMeasures}</textarea>
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
					
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId : [],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					//日志日期
					var dateDatePicker = new A.my97datepicker({
						id: "logDate",
						name:"logDate",
						render:"#logDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH"
						}
					}).render();
					dateDatePicker.setValue('${yeardate}');
					
					//结束时间
					var enddateDatePicker = new A.my97datepicker({
						id: "endDate",
						name:"endDate",
						render:"#endDatetimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH"
						}
					}).render();
					enddateDatePicker.setValue('${enddate}');
					
					//检修负责人
					var dutyUsersId = new A.combobox({
						render : "#dutyPersonId",
						datasource : ${dutyUsers},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					dutyUsersId.setValue(${userEntity.id});
					
					$('#overhaulLogDetailForm').validate({
						debug:true,
						rules:  {
							id:{      maxlength:20},
							overhaulArrangeId:{      maxlength:20},
							unitId:{      maxlength:512},
							teamMember:{  required:true,    maxlength:512},
							workFinishInfo:{ required:true,     maxlength:512},
							problemMeasures:{  required:true,    maxlength:512},
							beforeMeet:{      maxlength:512},
							afterMeet:{      maxlength:512},
							attchmentIds:{      maxlength:512},
							dutyPerson:{required:true,      maxlength:20},
							logDate:{  required:true,    maxlength:20},
							status:{      maxlength:64},
							endDate:{   required:true,    maxlength:64}
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/overhaulLogDetail");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#overhaulLogDetailForm").serializeObject();
							obj.fileId=JSON.stringify(uploaddropzone.getValue());
							obj.logDate=$('#logDate').val()+" 00:00:00";
							obj.endDate=$('#endDate').val()+" 00:00:00";
							
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
						$("#overhaulLogDetailForm").submit();
    				});
    				
				});
			});
        </script>
    </body>
</html>