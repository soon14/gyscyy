<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="breadcrumbs ace-save-state" id="breadcrumbs">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="javascript:void(0);" onclick="firstPage()">首页</a>
				</li>
				<li>
					检修管理
				</li>
				<li class="active">检修日志</li>
				<li class="active">工作任务填报</li>
			</ul>
		</div>
<div class="page-content" style="margin-left:30px;margin-right:30px;">
		<div style="float:right;margin-right:50px;">
			<button id="overhaulWorkTaskAddBtnBack"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style="    margin-top: 14px;">
       			<i class="fa fa-reply"></i>
       			返回
       		</button>
		</div>
		<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >基础信息</h5>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin:50px 200px 0 0;" id="overhaulWorkTaskForm">
			   <input id="overhaulArrangeId" name="overhaulArrangeId" type="hidden" value="${overhaulArrangeId}"/>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>完成状态
					</label>
					<div class="col-md-4">
						 <select id="finishStatusDiv"  name="finishStatus"></select>
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>设备名称
				    </label>
				    <div class="col-md-4">
						 <div id="equipIdDiv" ></div>
						 <input id="equipId" name="equipId" type="hidden" />
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>工作任务
					</label>
					<div class="col-md-10">
						 <textarea placeholder="请输入工作任务" id="workTask" name="workTask"  class="col-md-12" style="height:90px; resize:none;"></textarea>                   
                	</div>
               </div>
            </form>
    		<div style="margin-right:20px;">
    			<button id="overhaulWorkTaskAddSaveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
</div>
		<script type="text/javascript">
			var userUnitRels = ${userUnitRels};
			//初始数据备份
			var processUserUnitRels = ${userUnitRels};
			jQuery(function($) {
				seajs.use(['combobox','combotree','selectbox','my97datepicker'], function(A){
				 	//完成状态	
				 	var overhaulWorkTaskCombobox = new A.combobox({
						render : "#finishStatusDiv",
						datasource : ${overhaulFinishStatus},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					//设备名称
					var overhaulWorkTaskEquip=new A.selectbox({
						id: 'equipName',
						name:'equipName',
						title:'设备台账',
						url:'/equipLedger/selectEquipLedger',
						render:'#equipIdDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data){
							if(data&&data[0]){
								$("#equipName").val(data[0].name);
								$("#equipId").val(data[0].id);
							};
							
						}
					}).render();
				
					$('#overhaulWorkTaskForm').validate({
						debug:true,
						rules:  {
							finishStatus:{required:true},	
							equipId:{required:true},	
							workTask:{required:true,maxlength:512}
						},
						submitHandler: function (form) {
							$("#overhaulWorkTaskAddSaveBtn").attr("disabled",true);
							//添加按钮
							var url = format_url("/overhaulWorkTask/overhaulWorkTaskAdd");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#overhaulWorkTaskForm").serializeObject();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('添加成功');
									window.scrollTo(0,0);
									$("#page-container").load(format_url('/overhaulArrange/getAddMore/'+overhaulLogId+'/'+overhaulArrangeId+'/'+type));
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					//保存
					$("#overhaulWorkTaskAddSaveBtn").on("click", function(){
						$("#overhaulWorkTaskForm").submit();
    				});
					//返回
					$("#overhaulWorkTaskAddBtnBack").on("click", function(e){
						window.scrollTo(0,0);
						$("#page-container").load(format_url('/overhaulArrange/getAddMore/'+overhaulLogId+'/'+overhaulArrangeId+'/'+type));
					});
				});
			});
        </script>
    </body>
</html>