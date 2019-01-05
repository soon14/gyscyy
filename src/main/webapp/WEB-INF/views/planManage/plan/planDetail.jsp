<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
			<div class="breadcrumbs ace-save-state" id="breadcrumbs" style="margin-bottom:100px;">
				<ul class="breadcrumb">
					<li>
						<i class="ace-icon fa fa-home home-icon"></i>
						<a href="javascript:void(0);" onclick="firstPage()">首页</a>
					</li>
					<li class="active">计划管理</li>
					<li class="active">计划管理</li>
					<li class="active">查看</li>
				</ul><!-- /.breadcrumb -->
				<div style="margin-right:55px;margin-top:10px;">
        		<button id="button" class="btn btn-xs btn-primary pull-right">
    				<i class="fa fa-reply"></i>
    				返回
    			</button>
        		</div>
        		<h5 class="table-title header smaller blue" style="margin-left:30px">基础信息</h5>
		</div>
	<div class="page-content">
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addForm">
					<input id="id"  name="id"  value="${planEntity.id}" type="hidden"/>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>单位名称
				    </label>
				    <div class="col-md-4">
					<input id="unitName" name="unitName" value="${planEntity.unitName}" 
								class="col-md-12" type="text" readonly="readonly"/>
                    </div>
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>填报人
					</label>
					<div class="col-md-4">
						<input id="userName" name="userName" value="${planEntity.userName}" 
								class="col-md-12" type="text" readonly="readonly"/>
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>计划周期
				    </label>
				    <div class="col-md-4">
				    <input id="cycleName" name="cycleName" value="${planEntity.cycleName}" 
								class="col-md-12" type="text" readonly="readonly"/>
                    </div>
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>计划类型
					</label>
					<div class="col-md-4">
					  <input id="typeName" name="typeName" value="${planEntity.typeName}" 
								class="col-md-12" type="text" readonly="readonly"/>
                	</div>
               </div>
		       <div class="form-group">
				    <label id="startTime" class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>计划时间
				    </label>
				    <div class="col-md-4">
				     <input id="time" name="time" value="${planEntity.time}" 
								class="col-md-12" type="text" readonly="readonly"/>
                    </div>
                    <div id="endTime" >
	                 </div>
               </div>
	                 <div class="form-group">
	                  <label  class="col-md-2 control-label no-padding-right">
						总投资合计（万元）
				    </label>
				    <div class="col-md-4">
				    	<input id="planSum" name="planSum" value="${planEntity.planSum}" 
				    		class="col-md-12" type="text" readonly="readonly"/>
                    </div>
                    </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						备注
					</label>
					<div class="col-md-10">
	                   <textarea class="col-md-12" id="remark" name="remark"   readonly="readonly"
	                   style="height:100px; resize:none;"  placeholder="" maxlength="128" >${planEntity.remark}</textarea>
	                   
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						附件
				    </label>
				   <div class="col-xs-10" id="divfile">
					</div>
               </div>
            </form>
              	<div class="form-group">
					<div id="planWhole">
					
					</div>
				</div>
		</div>
	</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					var id = $('#id').val();
					//加载列表
					$.ajax({url : format_url("/planWhole/indexDetail"),
						contentType : 'application/x-www-form-urlencoded; charset=UTF-8;',
						data : {"id":id},
						success : function(result) {
							var divshow = $("#planWhole");
							divshow.text("");// 清空数据
							divshow.append(result); // 添加Html内容，不能用Text 或 Val
						}
					});
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:'${planEntity.fileId}'==""?[]:${planEntity.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
						chargeUp:true,
					}).render();
					
					$("#button").on("click", function(e){
						window.scrollTo(0,0);
						 $("#page-container").load(format_url('/plan/index'));
    				});
					if('${planEntity.cycle}'!=0){
						$("#endTime").append(
						  '<label class="col-md-2 control-label no-padding-right">'+
						  '<span style="color:red;">*</span>计划结束时间 </label>'+
						  '<div class="col-md-4"> <input id="timeEnd" name="timeEnd" '+
						  ' value="${planEntity.timeEnd}" '+
						  'class="col-md-12" type="text" readonly="readonly"/>'
						);
						$("#startTime").text("");
						$("#startTime").append("<span style='color:red;'>*</span>计划开始时间");
					}
				});
			});
        </script>
    </body>
</html>