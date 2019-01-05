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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addForm">
					<input id="id"  name="id"  value="${planEntity.id}" type="hidden"/>
					<input id="status"  name="status"  value="${planEntity.status}" type="hidden"/>
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
    		<div style="margin-right:100px;margin-top: 20px;" id="button">
    		<c:forEach items="${nodeList}" var="nodeList" varStatus="vs"> 
    			<c:if test="${nodeList.authFactorCode=='delete'}">
    			<button  id="delete"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-remove-circle"></i>
		    				删除
		    	</button>
		    	</c:if>
		    	<c:if test="${nodeList.authFactorCode=='penging'}">
    			<button  id="penging"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-ok-sign"></i>
		    				再提交
		    	</button>
		    	</c:if>
		    	<c:if test="${nodeList.authFactorCode=='reject'}">
    			<button  id="reject"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-remove-circle"></i>
		    				驳回
		    	</button>
		    	</c:if>
		    	<c:if test="${nodeList.authFactorCode=='overhaul'}">
    			<button id="overhaul" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="glyphicon glyphicon-ok-sign"></i>
    					            同意(检修主任)
    			</button>
    			</c:if>
		    	<c:if test="${nodeList.authFactorCode=='special'}">
    			<button id="special" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="glyphicon glyphicon-ok-sign"></i>
    					            同意(专工)
    			</button>
    			</c:if>
		    	<c:if test="${nodeList.authFactorCode=='biotech'}">
    			<button id="biotech" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="glyphicon glyphicon-ok-sign"></i>
    					            同意(生技部主任)
    			</button>
    			</c:if>
		    	<c:if test="${nodeList.authFactorCode=='plan'}">
    			<button id="plan" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="glyphicon glyphicon-ok-sign"></i>
    					            同意(计划主任)
    			</button>
    			</c:if>
		    	<c:if test="${nodeList.authFactorCode=='leader'}">
    			<button id="leader" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="glyphicon glyphicon-ok-sign"></i>
    					            同意(领导)
    			</button>
    			</c:if>
    		</c:forEach>
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
					$("#button button").on("click", function(e){
						if($(e.target).attr("id")=="delete"||$(e.target).attr("id")=="reject"||$(e.target).attr("id")=="leader"){
							var url=format_url("/plan/"+$(e.target).attr("id"));
							var obj = $("#addForm").serializeObject();
							obj.taskId=$("#taskId").val();
							obj.procInstId=$("#procInstId").val();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result=="success"){
										alert('审批成功');	
										listFormDialog.close();
										$("#page-container").load(format_url('/todoTask/list/1/10'));
									}else{
										alert(result.result);
									}
								},
								error:function(v,n){
									alert('审批失败');
								}
							});
							return;
						}
						listFormDialog = new A.dialog({
	    						width: 850,
	    						height: 601,
	    						title: "审批",
	    						url:format_url("/plan/getApprove/"+$("#taskId").val()+"/"+$(e.target).attr("id")),
	    						closed: function(){
	    						}	
	    					}).render(); 
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