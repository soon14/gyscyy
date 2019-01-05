<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-12">
                <!-- 下面是人的列表 -->
                <form class="form-horizontal" role="form"  id="finishform" style="margin-right: 8%">
                <div class="form-group">
	               <div>
		                 <div class="form-group" >
							<label class="col-md-2 control-label no-padding-right">
								<span style="color:red;" >*</span>实际开始时间
							</label>
							<div class="col-md-4">
								<div id="startDateDiv"></div>
		                	</div>
						    <label class="col-md-2 control-label no-padding-right">
								<span style="color:red;" >*</span>实际结束时间
						    </label>
						    <div class="col-md-4">
								<div id="endDateDiv"></div>
		                    </div>
		               	 </div>
<!-- 		                 <div class="form-group"> -->
<!-- 						    <label class="col-md-2 control-label no-padding-right"> -->
<!-- 								执行人 -->
<!-- 						    </label> -->
<!-- 						    <div class="col-md-4"> -->
<%-- 								<input class="col-md-12" id="executeUserName" name="executeUserName" type="text"  maxlength="20" readonly="readonly" value="${executePersonName}"> --%>
<%-- 								<input class="col-md-12" id="executeUserId" name="executeUserId" type="hidden"  maxlength="20" readonly="readonly" value="${executePerson}"> --%>
<!-- 		                    </div> -->
<!-- 						    <label class="col-md-2 control-label no-padding-right"></label> -->
<!-- 						    <div class="col-md-4"></div> -->
						   
<!-- 		               	 </div> -->
		               	<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">
								<span style="color:red;" >*</span>完成情况
							</label>
							<div class="col-md-9">
			                   <textarea class="col-md-12" id="finishInfo"  name="finishInfo" style="height:80px; resize:none;"  maxlength="300"></textarea>
		                	</div>
	               		</div>
		               	<div class="form-group" style="margin-right: 75px;">
						    <label class="col-md-2 control-label no-padding-right">
								附件
						    </label>
						    <div class="col-md-10">
								<div class="col-md-12" id="executefile" ></div>
		                    </div>
	               		</div>
	               </div>
		     </div>
		     </form>
        </div>
    </div>
    <div style="margin-top: 15px;">
			<button id="" class="btn btn-xs btn-danger pull-right" data-dismiss="modal" >
				<i class="ace-icon fa fa-times"></i>
				取消
			</button>
			
			<button id="addbtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;" >
				<i class="ace-icon fa fa-floppy-o"></i>
				确定
			</button>
		</div>
</div>
</body>
<script type="text/javascript">
executeUserName = "${executePersonName}";
executeUserId = "${executePerson}";
    jQuery(function ($) {
    	seajs.use(['my97datepicker','uploaddropzone'], function(A){	
    		if(executeUserName){
    			$("#executeUserName").val(executeUserName);
    		}
			//基础信息附件上传
			var executefileloaddropzone =new A.uploaddropzone({
				render : "#executefile",
				fileId:[],
				autoProcessQueue:true,//是否自动上传
				addRemoveLinks : true,//显示删除按钮
			}).render("Other");
    		//实际开始时间
			var startDateTimePicker = new A.my97datepicker({
				id: "startDate",
				name:"startDate",
				render:"#startDateDiv",
				options : {
						isShowWeek : false,
						skin : 'ext',
						maxDate: "#F{$dp.$D(\\'endDate\\')}",
						minDate: "",
						dateFmt: "yyyy-MM-dd HH:mm:ss"
				}
			}).render();
    		//实际结束时间
			var endDateTimeDatePicker = new A.my97datepicker({
				id: "endDate",
				name:"endDate",
				render:"#endDateDiv",
				options : {
						isShowWeek : false,
						skin : 'ext',
						maxDate: "",
						minDate: "#F{$dp.$D(\\'startDate\\')}",
						dateFmt: "yyyy-MM-dd HH:mm:ss"
				}
			}).render();
			$('#finishform').validate({
	 			debug:true,
	 			rules:  {
	 				startDate: {
	 					required: true
	 				},
	 				endDate: {
	 					required: true
	 				},
	 				finishInfo: {
	 					required: true
	 				}
	 			},
	 			submitHandler: function (form) {
	 				 finishinfo = $("#finishInfo").val();
	 				 finishStartDate = $("#startDate").val();
	 				 finishEndDate = $("#endDate").val();
	 				 finishFielId = JSON.stringify(executefileloaddropzone.getValue());
	 				 listFormDialog.close();
	 			}
	 		});
			
			//保存
			$("#addbtn").on("click", function(){
				$("#finishform").submit();
			});
			//返回
			$("#cancel").on("click", function(e){
				window.scrollTo(0,0);
				listFormDialog.close();
				$("#page-container").load(format_url("/todoTask/list/1/10"));
			});
    	});
    });
</script>