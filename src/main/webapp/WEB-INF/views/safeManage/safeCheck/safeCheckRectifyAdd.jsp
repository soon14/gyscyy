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
			   <input id="id" class="col-md-12" name="id"   readonly  type="hidden"/>
<%-- 			   <input class="col-md-12" id="" name="createPeopleId" type="hidden" value="${sysUserEntity.id }"> --%>
	           <div class="form-group">
	             	<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>填写人
					</label>
					<div class="col-md-4">
						<input class="col-md-12" id="userName" readonly="readonly" name="userName" type="text" placeholder="" maxlength="20" value="${userEntity.name }">
						<input class="col-md-12" id="createPeopleId" name="createPeopleId" type="hidden" placeholder="" maxlength="20" value="${userEntity.id }">
                	</div>
                     <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>单位
				    </label>
				    <div class="col-md-4">
						<select id="unitId" name="unitId" class="col-md-12 chosen-select"></select>
                    </div>
               </div>
		       <div class="form-group">
<!-- 					 <label class="col-md-2 control-label no-padding-right"> -->
<!-- 						<span style="color:red;">*</span>年号 -->
<!-- 				    </label> -->
<!-- 				    <div class="col-md-4"> -->
<!-- 						<div id="targetDateDiv"></div> -->
<!--                     </div> -->
					<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>名称
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="checkName" name="checkName"style="resize:none;"  placeholder="" type="text" maxlength="128">
                	</div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>发现时间
					</label>
					<div class="col-md-4">
					   <div id="signDateDiv"></div>
                	</div>
               </div>
              <div class="form-group">
             	 <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>整改情况
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="correctionRectify" name="correctionRectify"style="resize:none;"  placeholder="" type="text" maxlength="128">
                	</div>
                   <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>整改时间
					</label>
					<div class="col-md-4">
					   <div id="correctionDateDiv"></div>
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
						<span style="color:red;">*</span>检查人员
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="landWaiter" name="landWaiter"style="resize:none;"  placeholder="" type="text" maxlength="128">
                	</div>
               </div>
                <div class="form-group">
                <label class="col-md-2 control-label no-padding-right">
						备注
				    </label>
				    <div class="col-md-10">
						<textarea placeholder="请输入备注" name="remark" class="col-md-12" style="height:90px; resize:none;"></textarea>
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
					
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:[],
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
					//日期组件 检查时间
					var correctionDatePicker = new A.my97datepicker({
						id: "correctionDateId",
						name:"correctionDate",
						render:"#correctionDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					//类别
					 categoryCombobox = new A.combobox({
						render : "#categorySelect",
						datasource : ${safeCheckTypeEnumCombobox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					//类别
					 unitCombobox = new A.combobox({
						render : "#unitId",
						datasource : ${unitList},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					
					
					
					$('#addTargetForm').validate({
						debug:true,
						rules:  {
							id:{      maxlength:20},
							unitId:{ required:true,     maxlength:20},
							name:{required:true, maxlength:64},
							targetDate:{ required:true,maxlength:64},
							signDate:{ required:true, maxlength:64},
							category:{ required:true , maxlength:64},
							fileId:{      maxlength:128},
							createPeopleId:{ required:true ,     maxlength:64},
							checkName:{ required:true,     maxlength:128},
							correctionRectify:{ required:true,     maxlength:128},
							correctionDate:{ required:true,     maxlength:128},
							landWaiter:{required:true ,maxlength:128}
						},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/safeCheck/add/3");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addTargetForm").serializeObject();
							obj.yearNum = $('#targetDateId').val();
							obj.checkDate = $('#signDateId').val();
							//附件上传
							obj.fileAddress=JSON.stringify(uploaddropzone.getValue());
							obj.type=3
							
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
// 						var yearNumStr = targetDateDatePicker.getValue();
// 						var checkDateStr = signDateDatePicker.getValue().split("-")[0];
// 						if(yearNumStr!=checkDateStr){
// 							alert("年号和时间不符");
// 							return;
// 						}
						$("#addTargetForm").submit();
    				});
    				
				});
			});
        </script>
    </body>
</html>