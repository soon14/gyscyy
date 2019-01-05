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
					计划管理
				</li>
				<li class="active">投资计划</li>
				<li class="active">投资计划新增</li>
			</ul>
		</div>
		<div class="page-content" style="margin-left:30px;margin-right:30px;">
		<div style="float:right;margin-right:10px;">
			<button id="btnBack"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style="margin-top: 14px;">
       			<i class="fa fa-reply"></i>
       			返回
       		</button>
		</div>
		<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >基础信息</h5>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:200px;margin-top:30px;" id="addForm">
		       <div class="form-group">
					
				    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>项目名称
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="name" name="name" type="text" placeholder="" maxlength="64" value="">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>项目类别
					</label>
					<div class="col-md-4">
					   <select class="col-md-12 chosen-select" id="category" name="category"></select>	
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>年份
				    </label>
				    <div class="col-md-4">
						<div id="yearNumDiv"></div>
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>股权结构(%)
					</label>
					<div class="col-md-4">
					<input class="col-md-12" id="stockStruct" name="stockStruct" onkeyup="value=value.replace(/[\D]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[\D]/g,''))" type="text" placeholder="" maxlength="64" value="">
<!-- 	                   <input class="col-md-12" id="stockStruct" name="stockStruct" type="text" placeholder="请输入股权结构百分比形式，如：50%" maxlength="64" value=""  > -->
                	</div>
               </div>
		       <div class="form-group">
					
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>建设规模
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="buildSize" name="buildSize" type="text" placeholder="" maxlength="64" value="">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>建设规模量纲
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="buildSizeUnit" name="buildSizeUnit" type="text" placeholder="" maxlength="64" value="">
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>建设阶段
				    </label>
				    <div class="col-md-4">
						<select class="col-md-12 chosen-select" id="buildStage" name="buildStage"></select>
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						填写人
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="createUserId" name="createUserId" type="text" placeholder="" readonly="readonly" maxlength="64" value="${userName }">
                	</div>
               </div>
	           <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>年度完成主要工程节点
					</label>
					<div class="col-md-10">
						<textarea name="content" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="512"></textarea>
					</div>
               </div>
            
<!--                <div style="text-align: left;color: #478fca;font-weight: lighter;">计划投资额(万元)</div>	 -->
<!-- 			<hr>               -->
               <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>小计
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="totalMoney" name="totalMoney" type="text" placeholder="" maxlength="64" value=""  onkeyup="value=value.replace(/[^\d]/g,'') " ng-pattern="/[^a-zA-Z]/" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[\D]/g,''))">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>自有金额
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="ownMoney" name="ownMoney" type="text" placeholder="" maxlength="64" value=""  onkeyup="value=value.replace(/[^\d]/g,'') " ng-pattern="/[^a-zA-Z]/" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[\D]/g,''))">
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>贷款
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="loanMoney" name="loanMoney" type="text" placeholder="" maxlength="64" value=""  onkeyup="value=value.replace(/[^\d]/g,'') " ng-pattern="/[^a-zA-Z]/" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[\D]/g,''))">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						其他
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="other" name="other" type="text" placeholder="" maxlength="64" value="">
                	</div>
               </div>
	           <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						备注
					</label>
					<div class="col-md-10">
						<textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128"></textarea>
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
    			<button id="qxBtn" class="btn btn-xs btn-danger pull-right" data-dismiss="modal" >
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
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
					}).render();
					
					
					//combobx组件
					var categoryCombobox = new A.combobox({
						render: "#category",
						//返回数据待后台返回TODO
						datasource:${categoryCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//日期组件
					var yearNumDatePicker = new A.my97datepicker({
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
					//combobx组件
					var buildStageCombobox = new A.combobox({
						render: "#buildStage",
						//返回数据待后台返回TODO
						datasource:${stageCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					$('#addForm').validate({
						debug:true,
						rules:  {
							id:{      maxlength:20},
							name:{ required:true ,     maxlength:64},
							category:{ required:true ,     maxlength:64},
							yearNum:{ required:true ,     maxlength:64},
							stockStruct:{digits:true, required:true ,     maxlength:64},
							buildSize:{ required:true ,     maxlength:64},
							buildSizeUnit:{ required:true ,     maxlength:64},
							buildStage:{ required:true ,     maxlength:64},
							content:{  required:true ,    maxlength:512},
							totalMoney:{digits:true, required:true ,      maxlength:64},
							ownMoney:{ digits:true,required:true ,     maxlength:64},
							loanMoney:{digits:true, required:true ,     maxlength:64},
							other:{      maxlength:64},
							remark:{      maxlength:128}
						},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/investmentPlan");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							
							obj.yearNum = $('#yearNumId').val()+"-01-01 00:00:00";
							//附件上传
							obj.fileId=JSON.stringify(uploaddropzone.getValue());
							
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									A.loadPage({
										render : '#page-container',
										url : format_url("/investmentPlan/index")
									});
									alert('添加成功');
									
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					$("#saveBtn").on("click", function(){
						$("#addForm").submit();
    				});
					
					//返回
					$("#btnBack").on("click", function(e){
						window.scrollTo(0,0);
						$("#page-container").load(format_url('/investmentPlan/index'));
					});
					//取消
					$("#qxBtn").on("click", function(e){
						window.scrollTo(0,0);
						$("#page-container").load(format_url('/investmentPlan/index'));
					});
    				
				});
			});
			
			
			//正整数
			function isPInt(str) {
			    var g = /^(?:100|[1-9][0-9]?|0)$/;
			    return g.test(str);
			}

        </script>
    </body>
</html>