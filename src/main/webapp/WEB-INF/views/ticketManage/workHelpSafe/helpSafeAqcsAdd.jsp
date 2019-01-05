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
		<form class="form-horizontal" role="form" style="margin-right:100px;" id="helpSafeAqcsAddForm">
		        				<input type="hidden"  name="uuidCode" value="${uuid}" />
		        				<input id="workId" name="workticketId" type="hidden" value="${workId}"/>
								<input  id="orderSeq" name="orderSeq" type="hidden"   maxlength="20" value="${total}">
								<div class="form-group">
									
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>执行情况</label>
									<div class="col-md-4">
											<select id="executeSituation" name="executeSituation"  class="form-control chosen-select" style="width:200px;"></select>
									</div>
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>恢复情况</label>
									<div class="col-md-4">
											<select id="hfSituation" name="hfSituation"  class="form-control chosen-select" style="width:200px;"></select>
									</div>
<!-- 									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>执行时间</label> -->
<!-- 									<div class="col-md-4"> -->
<!-- 										<div id="aqcsStarttimeDiv"></div> -->
<!-- 									</div> -->
								</div>
<!-- 								<div class="form-group"> -->
									
<!-- 									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>恢复情况</label> -->
<!-- 									<div class="col-md-4"> -->
<!-- 											<select id="hfSituation" name="hfSituation"  class="form-control chosen-select" style="width:200px;"></select> -->
<!-- 									</div> -->
<!-- 									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>恢复时间</label> -->
<!-- 									<div class="col-md-4"> -->
<!-- 										<div id="aqcsEndtimeDiv"></div> -->
<!-- 									</div> -->
<!-- 								</div> -->
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>操作项目</label>
									<div class="col-md-10">
										<textarea id="signerContent" name="signerContent"  style="height:80px; resize:none;" class="col-md-12" maxlength="128"></textarea>
									</div>
								</div>
				</form>
    		<div style="margin-right:100px;">
        		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
        			<i class="ace-icon fa fa-times"></i>
        			取消
        		</button>
        		<button id="addBtnSafe" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
        			<i class="ace-icon fa fa-floppy-o"></i>
        			保存
        		</button>
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					//日期组件
					var aqcsStarttimeDatePicker = new A.my97datepicker({
						id: "approveStarttimeId",
						name:"executeDate",
						render:"#aqcsStarttimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					//日期组件
					var aqcsEndtimeDatePicker = new A.my97datepicker({
						id: "approveEndtimeId",
						name:"hfDate",
						render:"#aqcsEndtimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					
					//状态下拉框
					var zxqkCombobox = new A.combobox({
						render: '#executeSituation',
						datasource:${executeSituation},
						allowBlank:true,
						options:{
							"disable_search_threshold":10,
							"placeholder_text_single" : "请选择"
						}
					}).render();
					//状态下拉框
					var zxqkhfCombobox = new A.combobox({
						render: '#hfSituation',
						datasource:${hfSituation},
						allowBlank:true,
						options:{
							"disable_search_threshold":10,
							"placeholder_text_single" : "请选择"
						}
					}).render();
					
						var flag=${flag};
						$('#helpSafeAqcsAddForm').validate({
							debug:true,
							rules: {
								executeSituation:{required:true,maxlength:60},
// 								executeDate:{required:true},
// 								hfDate:{required:true},
								hfSituation:{required:true,maxlength:60},
								signerContent:{required:true,maxlength:128}
								},
							submitHandler: function (form) {
								//添加按钮
								var url = format_url("/workSafe");
								//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
								var obj = $("#helpSafeAqcsAddForm").serializeObject();
								obj.safeType=${flag};
								$.ajax({
									url : url,
									contentType : 'application/json',
									dataType : 'JSON',
									data : JSON.stringify(obj),
									cache: false,
									type : 'POST',
									success: function(result){
										alert('操作成功');
										workSafeOneDialog.close();
									},
									error:function(v,n){
										alert('操作失败');
									}
								});
							}
						});
					
					
					$("#addBtnSafe").on("click", function(){
    					$("#helpSafeAqcsAddForm").submit();
    				});
				});
			});
        </script>
    </body>
</html>