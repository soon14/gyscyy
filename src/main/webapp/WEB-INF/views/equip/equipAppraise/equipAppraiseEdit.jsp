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
					设备管理
				</li>
				<li class="active">设备评价管理</li>
				<li class="active">修改</li>
			</ul><!-- /.breadcrumb -->
		</div>	
		<div class="col-md-12" >
			<h5 class='table-title header smaller blue' style="margin-bottom:0px;">基础信息</h5>
			<div class="tabbable" style="margin-top:3%;">
			 		<div style="float:right; margin-top:-35px;margin-right:25px;">
						<button id="btnBack" class="btn btn-xs btn-primary" type="button">
							<i class="fa fa-reply"></i>
							返回
						</button>
					</div>	
	    	</div>		
			<form class="form-horizontal" role="form"  style="margin-left:9%;width: 75%" id="editForm">
				<input id="equipAppraiseId" type="hidden"  name="id" value="${entity.id}"/>
				<div style="margin: 7% 0 0 -10%">
			       <div class="form-group">
					    <label class="col-md-2 control-label no-padding-right">
							设备编号
					    </label>
					    <div class="col-md-4">
							 <input class="col-md-12" id="equipCode" name="equipCode" type="text" placeholder="" maxlength="20" value=" ${entity.equipName}" readonly=readonly>
	                    </div>
	                    <label class="col-md-2 control-label no-padding-right">
							设备名称
					    </label>
					    <div class="col-md-4">
							<input class="col-md-12" id="equipName" name="equipName" type="text" placeholder="" maxlength="20" value="${entity.equipCode} " readonly=readonly>
	                    </div>
	               </div>
			       <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
							原评
						</label>
						<div class="col-md-4">
 							<input class="col-md-12" id="equipGradePre" name="equipGradePre" type="hidden" placeholder="" maxlength="20" value="${entity.equipGradeNow}" readonly=readonly> 
 							<input class="col-md-12" id="equipGradePre1" name="equipGradePre1" type="text" placeholder="" maxlength="20" value="${entity.equipGradeNowName}" readonly=readonly> 
<!--  		                   <select id="equipGradePre" class="" name="equipGradePre" ></select>  -->
	                	</div>
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>现评
						</label>
						<div class="col-md-4">
		                   <select id="equipGradeNow" class="" name="equipGradeNow"></select>
	                	</div>
	               </div>
	                <div class="form-group">
	      				 <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>评定或变动等级依据和原因
					    </label>
					    <div class="col-md-10">
							<textarea class="col-md-12" id="equipAppraiseReason" name="equipAppraiseReason"  maxlength="500" rows="5" >${entity.equipAppraiseReason}</textarea>
	                    </div>
	               </div>
			       <div class="form-group">
	      				 <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>现在存在主要缺陷及其影响
					    </label>
					    <div class="col-md-10">
							<textarea class="col-md-12" id="equipAppraiseInfluence" name="equipAppraiseInfluence"  maxlength="500" rows="5" >${entity.equipAppraiseComment}</textarea>
	                    </div>
	               </div>	               
			       <div class="form-group">
	      				 <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>备注
					    </label>
					    <div class="col-md-10">
							<textarea class="col-md-12" id="equipAppraiseComment" name="equipAppraiseComment"  maxlength="500" rows="5" >${entity.equipAppraiseInfluence} </textarea>
	                    </div>
	               </div>
			       <div class="form-group">
					     <label class="col-md-2 control-label no-padding-right">
										设备评价人
						</label>
						<div class="col-md-4">
		                   <input class="col-md-12" id="equipAppraisePerson" name="equipAppraisePerson" type="text" placeholder="" maxlength="64" value="${equipAppraisePerson}" readonly=readonly>
	                	</div>
			       		 <label class="col-md-2 control-label no-padding-right">
							设备评价日期
					    </label>
					    <div class="col-md-4">
							<input class="col-md-12" id="equipAppraiseDate" name="equipAppraiseDate" type="text" placeholder="" maxlength="" value="" readonly=readonly>
	                    </div>
	               </div>
	            </div>
			</form>
				
    		<div style="margin-right:100px;">
        		<!-- <button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
        			<i class="ace-icon fa fa-times"></i>
        			取消
        		</button> -->
        		<button id="editBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
        			<i class="ace-icon fa fa-floppy-o"></i>
        			保存
        		</button>
        	</div>
        </div>
		<script type="text/javascript">
			var userUnitRels = ${userUnitRels};
			//初始数据备份
			var processUserUnitRels = ${userUnitRels};
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','selectbox'], function(A){
					var entity = ${entityJson};
					//获得日期
					var myDate = new Date().Format("yyyy-MM-dd");
						$("#equipAppraiseDate").val(myDate);
					
					var equipGradeNowCombobox = new A.combobox({
						render: "#equipGradeNow",
						datasource:${equipGradeNow},
						multiple:false,
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();	
					
        			var appData = ${entityJson};
					$('#editForm').validate({
						debug:true,
						rules: {
							id:{
								required: true,
								maxlength:20
								},
							equipCode:{
									required: true
							},
							equipmentId:{
								required: true,
								maxlength:20
								},
							equipGradePre:{
								required: true,
								},
							equipAppraiseComment:{
								required: true,
								maxlength:500
								},
							equipAppraiseReason:{
								required: true,
								maxlength:500
								},
							equipAppraiseInfluence:{
								required: true,
								maxlength:500
								},
							equipGradeNow:{
								required: true,
								},
							equipAppraiseDate:{      
								maxlength:64
								},
							equipAppraisePerson:{      
								maxlength:64
								}
							},
						submitHandler: function (form) {
							//添加按钮
							var id=entity.id;
							var url = format_url("/equipAppraise/updateEquipAppraise");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('操作成功');
									A.loadPage({
										render : '#page-container',
										url : format_url("/equipAppraise/index")
									});
								},
								error:function(v,n){
									alert('操作失败');
								}
							});
						}
					});
					$("#editBtn").on("click", function(){
    					$("#editForm").submit();
    				});
					//返回
					$("#btnBack").on("click", function(e){
						window.scrollTo(0,0);
						$("#page-container").load(format_url('/equipAppraise/index'));
					});
				});
			});
        </script>
    </body>
</html>