<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html lang="zh">
	<head>
    	<meta charset="UTF-8">
    	<%@ include file="/WEB-INF/views/common/meta.jsp" %> 
    </head>
	<body>
	<div class="page-content">
		<div class="col-md-12" >
					<div class="" >
							<ul class="nav nav-tabs" id="myTab" >
								<li class="active"><a data-toggle="tab" href="#defectDetail" 
									aria-expanded="true"> <i
										class="green ace-icon fa fa-home bigger-120"></i> 缺陷信息
								</a></li>
<!-- 								<li class=""><a data-toggle="tab" href="#workTicketDetail"  -->
<!-- 									aria-expanded="false"> <i -->
<!-- 										class="green ace-icon fa fa-home bigger-120"></i> 工作票 -->
<!-- 								</a></li> -->
<!-- 								<li class=""><a data-toggle="tab" href="#czbDetail"  -->
<!-- 									aria-expanded="false"> <i -->
<!-- 										class="green ace-icon fa fa-home bigger-120"></i> 操作票 -->
<!-- 								</a></li> -->
<!-- 								<li class=""><a data-toggle="tab" href="#sbydDetail"  -->
<!-- 									aria-expanded="false"> <i -->
<!-- 										class="green ace-icon fa fa-home bigger-120"></i> 设备异动 -->
<!-- 								</a></li> -->
							</ul>
							<div class=" col-md-12 tab-content" style="overflow-x:hidden;overflow-y: auto;height: 600px">
									  <form id="approveForm" class="form-horizontal" role="form" style="margin-right:100px;">
					    				 <input  id="defectId" name="defectId" value="${defectEntity.id}" type="hidden"/>
					    				 <input  id="id" name="id" value="${defectEntity.id}" type="hidden"/>
					    				 <input  id="equipId" name="equipId" value="${defectEntity.equipId}" type="hidden"/>
					    				 <input  id="processStatus" name="processStatus" value="${defectEntity.processStatus}" type="hidden"/>
												<div class="form-group">
													<label class="col-md-2 control-label no-padding-right">缺陷编码</label>
													<div class="col-md-4">
																<input class="col-md-12" id="code" name="code" type="text" placeholder="" maxlength="64" value="${defectEntity.code}" readonly="readonly">
													</div>
													<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>缺陷单位名称</label>
													<div class="col-md-4">
													 	<input class="col-md-12" id="unitName"  name="unitName"  type="text" placeholder="" maxlength="64" value="${defectEntity.unitName}" readonly="readonly">
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>设备编码</label>
														<div class="col-md-4">
																<input class="col-md-12" id="equipNumber" name="equipNumber" type="text" placeholder="" maxlength="64" value="${defectEntity.equipId}"readonly="readonly">
													</div>
													<label class="col-md-2 control-label no-padding-right">设备名称</label>
														<div class="col-md-4">
																<input class="col-md-12" id="equipName" name="equipName" type="text" placeholder="" maxlength="64" value="${defectEntity.equipName}" readonly="readonly">
													</div>
												</div>
													<div class="form-group">
													<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>发现人</label>
													<div class="col-md-4">
																<input class="col-md-12" id="findUserName" name="findUserName" type="text" placeholder="" maxlength="64" value="${defectEntity.findUserName}" readonly="readonly">
													</div>
														<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>发现时间</label>
														<div class="col-md-4">
															<input class="col-md-12" id="findTime" name="findTime" type="text" placeholder="" maxlength="64" value="<fmt:formatDate value="${defectEntity.findTime}" type="both"/>" readonly="readonly">
													</div>
												</div>
														<div class="form-group">
														<label class="col-md-2 control-label no-padding-right ">设备类型</label>
															<div class="col-md-4">
															<input class="col-md-12" id="equipManageTypeName" name="equipManageTypeName" type="text" placeholder="" maxlength="64" value="${defectEntity.equipManageTypeName}" readonly="readonly">
														</div>
<!-- 														<label class="col-md-2 control-label no-padding-right">损失电量</label> -->
<!-- 															<div class="col-md-4"> -->
<%-- 															<input class="col-md-12" id="lossElectricity" name="lossElectricity" onkeyup="value=value.replace(/[\D]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[\D]/g,''))" readonly="readonly" type="text" placeholder="" maxlength="64" value="${defectEntity.lossElectricity }"  > --%>
								</div>
													<div class="form-group">
														<label class="col-md-2 control-label no-padding-right">设备缺陷描述</label>
														<div class="col-md-10">
															<textarea name="depict" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128" readonly="readonly">${defectEntity.depict}</textarea>
														</div>
													</div>
													<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						备注
					</label>
					<div class="col-md-10">
					   <textarea class="col-md-12" id="remark" name="remark"style="height:80px; resize:none;" readonly="true" placeholder="" maxlength="128">${defectEntity.remark}</textarea>
                	</div>
				</div>
<!-- 													<div class="form-group"> -->
<!-- 														<label class="col-md-2 control-label no-padding-right">备注</label> -->
<!-- 														<div class="col-md-10"> -->
<%-- 															<textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128" readonly="readonly" >${defectEntity.remark}</textarea> --%>
<!-- 														</div> -->
<!-- 													</div> -->
													<div class="form-group">
															<label class="col-md-2 control-label no-padding-right">处理意见</label>
															<div class="col-md-10">
															<textarea name="handlingSuggestion" placeholder="" readonly="readonly" style="height:100px; resize:none;" class="col-md-12" maxlength="128">${defectEntity.handlingSuggestion }</textarea>
														</div>
													</div>
															<div class="form-group form-horizontal">
																<label class="col-md-2 control-label no-padding-right">附件</label>
																<div class="col-xs-10" id="divfile">
																	
																</div>
															</div>
									</form>
														<div class="form-group">
														<div id="appraisal"></div>
														<div id="solve"></div>
														<div id="check"></div>	
														</div>
												  </div>
<!-- 												<div id="workTicketDetail" class="tab-pane fade" >
<!-- 													<div id="workTicket"></div>	 -->
<!-- 												</div> -->
<!-- 												<div id="czbDetail" class="tab-pane fade "> -->
<!-- 													<div id="czb"></div>		 -->
<!-- 												</div> -->
<!-- 												<div id="sbydDetail" class="tab-pane fade "> -->
<!-- 													<div id="sbyd"></div>		 -->
<!-- 												</div> --> 
											</div>
										</div>
									<div class="form-group">
									<div id="appraisal"></div>
<!-- 									<div id="solve"></div> -->
									<div id="check"></div>
									</div>
								</div>
						<!-- 		<div id="workTicketDetail" class="tab-pane fade" >
									<div id="workTicket"></div>	
								</div>
								<div id="czbDetail" class="tab-pane fade ">
									<div id="czb"></div>		
								</div>
								<div id="sbydDetail" class="tab-pane fade ">
									<div id="sbyd"></div>		
								</div> -->
							</div>
        <div class="col-md-12">
    		<div style="margin-right:100px;margin-top: 20px;" id="button" >
    		<c:forEach items="${nodeList}" var="nodeList" varStatus="vs"> 
    			<c:if test="${nodeList.authFactorCode=='appraisal/getAddOverhaul'}">
        		<button  id="appraisal/getAddOverhaul"  title="集控中心鉴定"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-ok-sign"></i>
		    				集控中心鉴定
		    	</button>
<!-- 		    	<div  class="tab-pane fade active in" style="margin-top: 20px;"> -->
<!-- 	        		<button id="printOne" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;"> -->
<!-- 	        			<i class="glyphicon glyphicon-print"></i> -->
<!-- 	        			打印 -->
	        		</button>
<!-- 	        </div> -->
		    	</c:if>
		    	<c:if test="${nodeList.authFactorCode=='appraisal/getAddMonitor'}">
		    	<button  id="appraisal/getAddMonitor"  title="检修中心鉴定"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-ok-sign"></i>
		    				检修中心鉴定
		    	</button>
<!-- 		    	<button id="printOne" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;"> -->
<!-- 	        			<i class="glyphicon glyphicon-print"></i> -->
<!-- 	        			打印 -->
<!-- 	        		</button> -->
		    	</c:if>
		    	<c:if test="${nodeList.authFactorCode=='xqBtn'}">
		    	<button  id="defect/getXQ"  title="消缺"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-ok-sign"></i>
		    				消缺
		    	</button>
		    	</c:if>
		    	<c:if test="${nodeList.authFactorCode=='gqBtn'}">
		    	<button  id="defect/getGQ"  title="挂起"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-ok-sign"></i>
		    				挂起
		    	</button>
		    	</c:if>
		    	<c:if test="${nodeList.authFactorCode=='appraisal/getAddBiotech'}">
		    	<button  id="appraisal/getAddBiotech "  title="生技部审批"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-ok-sign"></i>
		    				生技部审批
		    	</button>
<!-- 		    	<button id="printOne" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;"> -->
<!-- 	        			<i class="glyphicon glyphicon-print"></i> -->
<!-- 	        			打印 -->
<!-- 	        		</button> -->
		    	</c:if>
		    	<c:if test="${nodeList.authFactorCode=='appraisal/getAddEngineer'}">
		    	<button  id="appraisal/getAddEngineer"  title="总工、生产分管领导鉴定"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-ok-sign"></i>
		    				总工、生产分管领导鉴定
		    	</button>
		    	</c:if>
		    	<c:if test="${nodeList.authFactorCode=='solve/getAdd'}">
		    	<button  id="solve/getAdd"  title="执行人进行消缺"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-ok-sign"></i>
		    				执行人进行消缺
		    	</button>
		    	</c:if>
		    	<c:if test="${nodeList.authFactorCode=='solve/getAddMonitor'}">
		    	<button  id="solve/getAddMonitor"  title="检修主任审批"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-ok-sign"></i>
		    				检修主任审批
		    	</button>
		    	</c:if>
		    	<c:if test="${nodeList.authFactorCode=='solve/getAddBiotech'}">
		    	<button  id="solve/getAddBiotech"  title="解除挂起"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-ok-sign"></i>
		    				解除挂起
		    	</button>
		    	</c:if>
		    	<c:if test="${nodeList.authFactorCode=='solve/getAddEngineer'}">
		    	<button  id="solve/getAddEngineer"  title="总工审批、生产分管领导审批"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-ok-sign"></i>
		    				总工审批、生产分管领导审批
		    	</button>
		    	</c:if>
		    	<c:if test="${nodeList.authFactorCode=='check/getAdd'}">
		    	<button  id="check/getAdd"  title="验收人验收"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-ok-sign"></i>
		    				验收人验收
		    	</button>
<!-- 		    	<button id="printOne" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;"> -->
<!-- 	        			<i class="glyphicon glyphicon-print"></i> -->
<!-- 	        			打印 -->
<!-- 	        		</button> -->
		    	</c:if>
    		</c:forEach>
        	</div>
        </div>
		<script type="text/javascript">
			var listFormDialog;
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					//附件上传
					var uploaddropzone=new A.uploaddropzone({
						render : "#divfile",
						fileId:${defectEntity.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render();
					//全部按钮
					$("#button button").on("click", function(e){
						var defectId=$("#defectId").val(); 
						var obj = $("#approveForm").serializeObject();
							obj.taskId=$("#taskId").val();
							obj.procInstId=$("#procInstId").val();
							obj.defectId=defectId;
						var url=$(e.target).attr("id");
						if(url=="appraisal/getAddOverhaul"||url=="solve/getAdd"||url=="check/getAdd"){
							//弹出框
							A.loadPage({
								render : '#page-container',
								url : format_url("/"+url),
								data:{taskId:$("#taskId").val(),
									procInstId:$("#procInstId").val(),
									id:$("#defectId").val(),
									procDefId:$("#procDefId").val(),
									processStatus:$("#processStatus").val(),
									}
							});
						}else if(url=="getXQ"||url=="getGQ"){
							listFormDialog = new A.dialog({
								width: 850,
								height: 481,
								title: $(e.target).attr("title"),
								url:format_url("/"+url),
								data:{taskId:$("#taskId").val(),
									procInstId:$("#procInstId").val(),
									procDefId:$("#procDefId").val(),
									id:$("#defectId").val(),
									processStatus:$("#processStatus").val(),
									},
								closed: function(){
									
								}
							}).render();
							//弹出框
// 							A.loadPage({
// 								render : '#page-container',
// 								url : format_url("/defect/"+url),
// 								data:{taskId:$("#taskId").val(),
// 									procInstId:$("#procInstId").val(),
// 									id:$("#defectId").val(),
// 									procDefId:$("#procDefId").val(),
// 									processStatus:$("#processStatus").val(),
// 									}
// 							});
						} else{
								listFormDialog = new A.dialog({
									width: 850,
									height: 481,
									title: $(e.target).attr("title"),
									url:format_url("/"+url),
									data:{taskId:$("#taskId").val(),
										procInstId:$("#procInstId").val(),
										procDefId:$("#procDefId").val(),
										id:$("#defectId").val(),
										processStatus:$("#processStatus").val(),
										},
									closed: function(){
										
									}
								}).render();
							}
    				});
					var id = $('#id').val();
					//加载列表
					$.ajax({url : format_url("/appraisal/index"),
						contentType : 'application/x-www-form-urlencoded; charset=UTF-8;',
						data : {"id":id},
						success : function(result) {
							var divshow = $("#appraisal");
							divshow.text("");// 清空数据
							divshow.append(result); // 添加Html内容，不能用Text 或 Val
						}
					});
// 					$.ajax({url : format_url("/solve/index"),
// 						contentType : 'application/x-www-form-urlencoded; charset=UTF-8;',
// 						data : {"id":id},
// 						success : function(result) {
// 							var divshow = $("#solve");
// 							divshow.text("");// 清空数据
// 							divshow.append(result); // 添加Html内容，不能用Text 或 Val
// 						}
// 					});
					$.ajax({url : format_url("/check/index"),
						contentType : 'application/x-www-form-urlencoded; charset=UTF-8;',
						data : {"id":id},
						success : function(result) {
							var divshow = $("#check");
							divshow.text("");// 清空数据
							divshow.append(result); // 添加Html内容，不能用Text 或 Val
						}
					});
					$('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
						//加载列表
						$.ajax({url : format_url("/defect/workTicket"),
							contentType : 'application/x-www-form-urlencoded; charset=UTF-8;',
							data : {"id":$('#id').val()},
							success : function(result) {
								var workdivshow = $("#workTicket");
								workdivshow.text("");// 清空数据
								var czbdivshow = $("#czb");
								czbdivshow.text("");// 清空数据
								var sbyddivshow = $("#sbyd");
								sbyddivshow.text("");// 清空数据
								workdivshow.append(result); // 添加Html内容，不能用Text 或 Val
							}
						});
						if($(e.target).attr('href') == "#czbDetail"){
							//加载列表
							$.ajax({url : format_url("/defect/operationTicket"),
								contentType : 'application/x-www-form-urlencoded; charset=UTF-8;',
								data : {"id":$('#id').val()},
								success : function(result) {
									var workdivshow = $("#workTicket");
									workdivshow.text("");// 清空数据
									var czbdivshow = $("#czb");
									czbdivshow.text("");// 清空数据
									var sbyddivshow = $("#sbyd");
									sbyddivshow.text("");// 清空数据
									czbdivshow.append(result); // 添加Html内容，不能用Text 或 Val
								}
							});;
						} 
						var id = ${defectEntity.id};
						$("#printOne").on("click", function(e){
							
							birtPrint("zjk_defect.rptdesign",id);
	    				});
						if($(e.target).attr('href') == "#sbydDetail"){
							//加载列表
							$.ajax({url : format_url("/defect/sbyd"),
								contentType : 'application/x-www-form-urlencoded; charset=UTF-8;',
								data : {"id":$('#id').val()},
								success : function(result) {
									var workdivshow = $("#workTicket");
									workdivshow.text("");// 清空数据
									var czbdivshow = $("#czb");
									czbdivshow.text("");// 清空数据
									var sbyddivshow = $("#sbyd");
									sbyddivshow.text("");// 清空数据
									sbyddivshow.append(result); // 添加Html内容，不能用Text 或 Val
								}
							});
						} 
					});
				});
			});
        </script>
    </body>
</html>