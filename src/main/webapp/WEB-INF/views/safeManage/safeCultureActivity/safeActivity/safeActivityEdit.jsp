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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
				<input id="id" class="col-md-12" name="id"   readonly  type="hidden" value="${ entity.id}"/>
				<div class="form-group">
				<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>填写人
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="creatorName" readonly="readonly" name="creatorName" type="text" placeholder="" maxlength="20" value="${userEntity.name }">
						<input class="col-md-12" id="creatorId" name="creatorId" type="hidden" placeholder="" maxlength="20" value="${entity.creatorId }">
                    </div>
						<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>名称
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="name" name="name"style="resize:none;"  placeholder="" maxlength="128" type="text" value="${entity.name}">
                	</div>
<!--                 	<label class="col-md-2 control-label no-padding-right"> -->
<!-- 						<span style="color:red;">*</span>类别 -->
<!-- 				    </label> -->
<!-- 				    <div class="col-md-4"> -->
<!-- 					    	<select id="typeSelect" name="type" class="col-md-12 chosen-select"></select> -->
<!-- 					</div> -->
				</div>
			   
	           <div class="form-group">
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>年号
				    </label>
				    <div class="col-md-4">
						<div id="yearNumDiv"></div>
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>时间
					</label>
					<div class="col-md-4">
					   <div id="dateDiv"></div>
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
        		<button id="editBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
        			<i class="ace-icon fa fa-floppy-o"></i>
        			保存
        		</button>
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
        			var appData = ${entityJson};
					var id = $('#id').val();
					
					//相关资料 上传控件
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${entity.fileAddress},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
						options:{
							maxFiles:1
						},
					}).render();
					
					//日期组件 年号
					var yearNumPicker = new A.my97datepicker({
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
					yearNumPicker.setValue(appData.yearNum);
					
					//类别 下拉列表
					var typeCombobox = new A.combobox({
						render : "#typeSelect",
						datasource : ${safeActivityComboboxType},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					typeCombobox.setValue(appData.type);
					
					//日期组件 时间
					var datePicker = new A.my97datepicker({
						id: "dateId",
						name:"date",
						render:"#dateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					datePicker.setValue(appData.date);
					
					$('#editForm').validate({
						debug:true,
						rules: {id:{      maxlength:20},
							name:{required: true},
							yearNum:{required: true},
							type:{required: true},
							fileAddress:{required: true},
							date:{required: true},
							//fileName:{required:true},
							fileAddress:{required: true},
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/safeActivity/update/"+id);
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							obj.yearNum = $('#yearNumId').val();
							obj.date = $('#dateId').val();
							obj.fileAddress=JSON.stringify(uploaddropzone.getValue());
							obj.type=typeCombobox.getValue();
							obj.creatorId="${userEntity.id}";
							if(eval(uploaddropzone.getValue()).length==0){
								alert("请至少添加一个附件");
								return;
							}
							$("#editForm").attr("disabled",true);
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('操作成功');
									listFormDialog.close();
								},
								error:function(v,n){
									alert('操作失败');
								}
							});
						}
					});
					$("#editBtn").on("click", function(){
						var yearNumStr = yearNumPicker.getValue();
						var checkDateStr = datePicker.getValue().split("-")[0];
						if(yearNumStr!=checkDateStr){
						  alert("年号和时间不符");
						  return;
						}
    					$("#editForm").submit();
    				});
				});
			});
        </script>
    </body>
</html>