<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
	<div class="page-content">
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addWholeForm">
		       	<input id="id"  name="id"  value="${planWholeEntity.id}" type="hidden" />
		       	<input id="num"  name="num"  value="${planWholeEntity.num}" type="hidden"/>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>项目名称
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="projectName" name="projectName"  readonly="readonly"
						type="text" placeholder="" maxlength="128" value="${planWholeEntity.projectName}">
                    </div>
					<label class="col-md-2 control-label no-padding-right">
						计划开工时间
					</label>
					<div class="col-md-4">
						<input class="col-md-12" id="stratTime" name="stratTime"  readonly="readonly"
	                   type="text" placeholder="" maxlength="64" value="${planWholeEntity.stratTime}">
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						计划完成时间
				    </label>
				    <div class="col-md-4">
				    <input class="col-md-12" id="endTime" name="endTime"  readonly="readonly"
	                   type="text" placeholder="" maxlength="64" value="${planWholeEntity.endTime}">
                    </div>
					<label class="col-md-2 control-label no-padding-right">
						计划总投资（万元）
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="planTotal" name="planTotal"  readonly="readonly"
	                   type="text" placeholder="" maxlength="64" value="${planWholeEntity.planTotal}">
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						备注
				    </label>
				    <div class="col-md-10">
						<textarea class="col-md-12" id="remark" name="remark"  readonly="readonly"
						style="height:100px; resize:none;"    placeholder="" maxlength="128">${planWholeEntity.remark}</textarea>
                    </div>
               </div>
            </form>
            <form id="downloadForm" name="downloadForm" action="" hidden="hidden">
		 		</form>
               <div class="form-group">
					<div id="planDetail">
					
					</div>
				</div>
		</div>
	</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					var id = ${planWholeEntity.id};
					//加载列表
					$.ajax({url : format_url("/planDetail/indexDetail"),
						contentType : 'application/x-www-form-urlencoded; charset=UTF-8;',
						data : {"id":id},
						success : function(result) {
							var divshow = $("#planDetail");
							divshow.text("");// 清空数据
							divshow.append(result); // 添加Html内容，不能用Text 或 Val
						}
					});
				});
			});
        </script>
    </body>
</html>