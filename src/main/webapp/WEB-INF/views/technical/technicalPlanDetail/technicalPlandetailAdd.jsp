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
				<form class="form-horizontal" role="form" style="margin-right:100px;" id="techniaclDetailAddForm">
						<input id="uuid" name="uuidCode" type="hidden" value="${uuid}"/>
						<input id="technicalWorkid" name="technicalWorkid" type="hidden" value="${technicalWorkId}"/>
						<input id="technicalId" name="technicalId" type="hidden" value="${technicalId}"/>
						<input id="orderSeqWork" name="orderSeqWork" type="hidden" value="${orderSqe}"/>
						<input id="detailoneTotal" name="orderSeq" type="hidden" value="${detailoneTotal}"/>
						<div class="form-group" >
							<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>项目名称</label>
							<div class="col-md-4">
							 <input class="col-md-12" id="planName" name="planName" type="text"  placeholder="" maxlength="128" value="">
							</div>
							<label class="col-md-2 control-label no-padding-right">定检周期</label>
							<div class="col-md-4">
								<select class="col-md-12 chosen-select" id="djzq" name="djzq"></select>
							</div>
						</div>
						<div class="form-group">
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>本年计划时间</label>
								<div class="col-md-4">
									<div id="bnDiv"></div>
								</div>
								<label class="col-md-2 control-label no-padding-right">实际完成时间</label>
								<div class="col-md-4">
									<div id="sjDiv"></div>
								</div>
						</div>
						<div class="form-group" >
							<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>完成状态</label>
							<div class="col-md-4">
								<select class="col-md-12 chosen-select" id="wcStatus" name="wcStatus"></select>
							</div>
						</div>
						
						<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">完成情况</label>
								<div class="col-md-10">
									<textarea id="wcqk" name="wcqk"  style="height:80px; resize:none;" class="col-md-12" maxlength="28"></textarea>
								</div>
						</div>
						<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">存在问题和风险</label>
								<div class="col-md-10">
									<textarea id="danger" name="danger"  style="height:80px; resize:none;" class="col-md-12" maxlength="28"></textarea>
								</div>
						</div>
						<div class="form-group form-horizontal">
									<label class="col-md-2 control-label no-padding-right">附件</label>
									<div class="col-md-10" id="divdetailfile">
									</div>
						</div>
								
			</form>
    		<div style="margin-right:100px;">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="saveWorkBtnSafe" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','selectbox','uploaddropzone'], function(A){
					
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divdetailfile",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					
					//日期组件
					var bnDatePicker = new A.my97datepicker({
						id: "bnDivId",
						name:"nowTime",
						render:"#bnDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM"
						}
					}).render();
					
					//日期组件
					var sjDatePicker = new A.my97datepicker({
						id: "sjDivId",
						name:"wcTime",
						render:"#sjDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM"
						}
					}).render();
					//完成状态
					var wcztCombobox = new A.combobox({
						render: '#wcStatus',
						datasource:${wcStatus},
						allowBlank:true,
						options:{
							"allow_single_deselect": true,
							"disable_search_threshold":10
						}
					}).render();
					
					//完成状态
					var wcztCombobox = new A.combobox({
						render: '#djzq',
						datasource:${djzqStatus},
						allowBlank:false,
						options:{
							"allow_single_deselect": true,
							"disable_search_threshold":10
						}
					}).render(); 
					
					
						$('#techniaclDetailAddForm').validate({
							rules:  {
								planName:{required:true,maxlength:128},
								nowTime:{required:true,maxlength:20},
								wcStatus:{required:true,maxlength:128}
								},
							submitHandler: function (form) {
								//添加按钮
								var url = format_url("/technicalPlandetail");
								var obj = $("#techniaclDetailAddForm").serializeObject();
								    obj.fileid=JSON.stringify(uploaddropzone.getValue());
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
					
						
					
					$("#saveWorkBtnSafe").on("click", function(){
						var djzqid=$("#djzq").val();
						if(djzqid=="1"){
							var wctime= $("#sjDivId").val();
							if(wctime==""){
								alert("定检周期选择了多年，实际完成时间必须填写!");
								return;
							}
						}
						$("#techniaclDetailAddForm").submit();
    				});
    				
				});
			});
        </script>
    </body>
</html>