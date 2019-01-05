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
				<li class="active">查看检修日志</li>
			</ul>
		</div>
<div class="page-content">
		<div class="col-md-12" >
				</div>
					<div class="" style="margin-top: 20px;">
					
							<ul class="nav nav-tabs" id="myTab" >
							<c:forEach items="${list}" var="entity" varStatus="vs"> 
								<li class="<c:if  test="${vs.index==0 }">active</c:if>"><a data-toggle="tab" href="#${entity.id }" 
									aria-expanded="true"> <i
										class="green ace-icon fa fa-home bigger-120"></i> ${entity.unitName}
								</a></li>
								</c:forEach>
							</ul>
							
							<div style="float:right; margin-top:-35px;margin-right:55px;">
								<button id="btnBack"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" >
				        			<i class="fa fa-reply"></i>
				        			返回
				        		</button>
							</div>
							<div class=" col-md-12 tab-content" style="overflow-x:hidden;overflow-y: auto;">
								<c:forEach items="${list}" var="entity" varStatus="vs"> 
				
				<div id="${entity.id }" class="tab-pane fade <c:if  test="${vs.index==0 }">active in </c:if>" >
				<c:if  test="${vs.index==0 }">
				<input value="${entity.id }" <c:if  test="${vs.index==0 }">id="firstid" </c:if> type="hidden"/>
				
								</c:if>
								</div>
								</c:forEach>
							</div>
						</div>
         	</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					var firstid=parseInt($("#firstid").val());
					$.ajax({url : format_url("/overhaulLog/getDetailDay/"+firstid),
						contentType : 'application/x-www-form-urlencoded; charset=UTF-8;',
						success : function(result) {
							var divshow = $("#"+firstid);
							divshow.text("");// 清空数据
							divshow.append(result); // 添加Html内容，不能用Text 或 Val
						}
					});
					//附件上传
					 
						$('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
							var id = parseInt($(e.target).attr('href').substring(1));
							
							$("#"+id).siblings().text("");
							//加载列表
							$.ajax({url : format_url("/overhaulLog/getDetailDay/"+id),
								contentType : 'application/x-www-form-urlencoded; charset=UTF-8;',
								success : function(result) {
									var divshow = $("#"+id);
									divshow.text("");// 清空数据
									divshow.append(result); // 添加Html内容，不能用Text 或 Val
								}
							});
							
						});
					 
    				$("#btnBack").on('click',function(){
						A.loadPage({
							render : '#page-container',
							url : format_url("/overhaulLog/searchIndex")
						});
					});
					
				});
			});
			
        </script>
    </body>
</html>