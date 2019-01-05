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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="overhaulArrangeForm">
			   <input id="overhaulLogId" name="overhaulLogId" type="hidden"/>
			   <input id="processStatus" name="processStatus" value="1" type="hidden"/>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>负责人
					</label>
					<div class="col-md-4">
						 <select id="dutyPersonId"  name="dutyPersonId"></select>
                	</div>
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>完成状态
					</label>
					<div class="col-md-4">
						 <select id="finishStatus"  name="finishStatus"></select>
                	</div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
					    工作人员
					</label>
					<div class="col-md-4">
						 <select id="askPersonIdDiv"  name="askPersonId"></select>
                	</div>
                	<label class="col-md-2 control-label no-padding-right">
					<span style="color:red;">*</span>时间
					</label>
					<div class="col-md-4">
						<div id="workDateDiv"></div>
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>工作地点
				    </label>
				    <div class="col-md-10">
						 <textarea id="workPlace" class="col-md-12" style="height:100px; resize:none;" name="workPlace" type="text" placeholder="请输入工作地点" maxlength="512" value=""></textarea>
                    </div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>工作内容
				    </label>
				    <div class="col-md-10">
						 <textarea id="workProject" class="col-md-12" style="height:100px; resize:none;" name="workProject" type="text" placeholder="请输入工作内容" maxlength="512" value=""></textarea>
                    </div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						当日工作安排
				    </label>
				    <div class="col-md-10">
						 <textarea id="workArrange" class="col-md-12" style="height:100px; resize:none;" name="workArrange" type="text" placeholder="请输入当日工人安排" maxlength="512" value=""></textarea>
                    </div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						当日遗留问题
				    </label>
				    <div class="col-md-10">
						 <textarea id="other" class="col-md-12" style="height:100px; resize:none;" name="other" type="text" placeholder="请输入当日遗留问题" maxlength="512" value=""></textarea>
                    </div>
               </div>
<!--                <div class="form-group"> -->
<!-- 					<label class="col-md-2 control-label no-padding-right"  style="text-align:right;"> -->
<!-- 						请假事由 -->
<!--                     </label> -->
<!-- 					<div class="col-md-10"> -->
<!--                    		 <textarea id="other" class="col-md-12" style="height:100px; resize:none;" name="other" type="text" placeholder="请输入请假事由" maxlength="512" value=""></textarea> -->
<!--                     </div> -->
<!-- 	           </div> -->
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						备注
				    </label>
				    <div class="col-md-10">
						 <textarea id="remark" class="col-md-12" style="height:100px; resize:none;" name="remark" type="text" placeholder="请输入备注" maxlength="512" value=""></textarea>
                    </div>
               </div>
            </form>
    		<div style="margin:0 0 100px 0;">
    			<button class="btn btn-xs btn-danger pull-right" id="qx">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="overhaulArrangeSaveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;" type="button">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','selectbox','my97datepicker'], function(A){
					
					//日期组件
					var planYearDatePicker = new A.my97datepicker({
						id: "workDate",
						render:"#workDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "${yeardate}",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					
					planYearDatePicker.setValue('${yeardate}');
					$('#workDate').val('${yeardate}');
					
					//检修负责人
					dutyUsersId = new A.combobox({
						render : "#dutyPersonId",
						datasource : ${dutyUsers},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					//检修负责人
					askUsersIdCombox = new A.combobox({
						render : "#askPersonIdDiv",
						datasource : ${dutyUsers},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					//完成状态
					var overhaulArrangeCombobox = new A.combobox({
						render: "#finishStatus",
						datasource:${overhaulArrangeFinishStatus},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						width:"210px",
						options:{
							"disable_search_threshold":10
						}
					}).render();
					$('#overhaulArrangeForm').validate({
						debug:true,
						rules:  {
							dutyPersonId:{required:true},
							askPersonId:{maxlength:64},
							finishStatus:{required:true},
							workPlace:{required:true,maxlength:512},	
							workProject:{required:true,maxlength:512},	
							workDate:{required:true,maxlength:64},	
							workArrange:{maxlength:512},	
							other:{maxlength:512},	
							remark:{maxlength:512}
							
						},
						submitHandler: function (form) {
							if(overhaulLogId){
								$("#overhaulLogId").val(overhaulLogId);
							}
							//添加按钮
							var url = format_url("/overhaulArrange");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#overhaulArrangeForm").serializeObject();
							obj.workDate = $('#workDate').val();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('添加成功');
									overhaulArrangeDatatables.draw();
									listFormDialog.close();
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					$("#overhaulArrangeSaveBtn").on("click", function(){
						$("#overhaulArrangeForm").submit();
    				});
					$("#qx").on("click", function(){
							listFormDialog.close();	
    				});
				});
			});
        </script>
    </body>
</html>