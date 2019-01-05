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
	<div class="breadcrumbs ace-save-state" id="breadcrumbs">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="javascript:void(0);" onclick="firstPage()">首页</a>
				</li>
				<li class="active">缺陷管理</li>
				<li class="active">缺陷管理</li>
				<li class="active">查看</li>
			</ul><!-- /.breadcrumb -->
		</div>
	<div class="page-content">
		<div class="col-md-12" >
				</div>
					<div class="" style="margin-top: 20px;">
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
							<div style="float:right; margin-top:-35px;margin-right:55px;">
								<button id="resetBtn"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" >
				        			<i class="fa fa-reply"></i>
				        			返回
				        		</button>
							</div>
							<div class=" col-md-12 tab-content" style="overflow-x:hidden;overflow-y: auto;height: 680px">
								<div id="defectDetail" class="tab-pane fade active in" >
										<form id="approveForm" class="form-horizontal" role="form" style="margin-right:100px;">
											<div id="defectDetail-print">
						    				 <input  id="defectId" name="defectId" value="${defectEntity.id}" type="hidden"/>
						    				 <input  id="id" name="id" value="${defectEntity.id}" type="hidden"/>
						    				 <input  id="equipId" name="equipId" value="${defectEntity.equipId}" type="hidden"/>
						    				 <input  id="processStatus" name="processStatus" value="${defectEntity.processStatus}" type="hidden"/>
													<div class="form-group">
														<label class="col-md-2 control-label no-padding-right">缺陷编号</label>
														<div class="col-md-4">
																	<input class="col-md-12" id="code" name="code" type="text" placeholder="" maxlength="64" value="${defectEntity.code}" readonly="readonly">
														</div>
														<label class="col-md-2 control-label no-padding-right">缺陷单位名称</label>
														<div class="col-md-4">
														 	<input class="col-md-12" id="unitId" name="unitId" type="text" placeholder="" maxlength="64" value="${defectEntity.unitName}" readonly="readonly">
														</div>
													</div>
													<div class="form-group">
														<label class="col-md-2 control-label no-padding-right">设备编码</label>
															<div class="col-md-4">
																	<input class="col-md-12" id="equipNumber" name="equipNumber" type="text" placeholder="" maxlength="64" value="${defectEntity.equipId}" readonly="readonly">
														</div>
														<label class="col-md-2 control-label no-padding-right">设备名称</label>
															<div class="col-md-4">
																	<input class="col-md-12" id="equipName" name="equipName" type="text" placeholder="" maxlength="64" value="${defectEntity.equipName}" readonly="readonly">
														</div>
													</div>
														<div class="form-group">
														<label class="col-md-2 control-label no-padding-right">发现人</label>
														<div class="col-md-4">
																	<input class="col-md-12" id="findUserId" name="findUserId" type="text" placeholder="" maxlength="64" value="${defectEntity.findUserName}" readonly="readonly">
														</div>
															<label class="col-md-2 control-label no-padding-right">发现时间</label>
															<div class="col-md-4">
																<input class="col-md-12" id="findTime" name="findTime" type="text" placeholder="" maxlength="64" value="<fmt:formatDate value="${defectEntity.findTime}" type="both"/>" readonly="readonly">
														</div>
													</div>
<!-- 														<div class="form-group"> -->
<!-- 														<label class="col-md-2 control-label no-padding-right ">缺陷设备类型</label> -->
<!-- 															<div class="col-md-4"> -->
<%-- 															<input class="col-md-12" id="equipTypeName" name="equipTypeName" type="text" placeholder="" maxlength="64" value="${defectEntity.equipTypeName}" readonly="readonly"> --%>
<!-- 														</div> -->
<!-- 													</div> -->
														<div class="form-group">
														<label class="col-md-2 control-label no-padding-right ">设备类型</label>
															<div class="col-md-4">
															<input class="col-md-12" id="equipManageTypeName" name="equipManageTypeName" type="text" placeholder="" maxlength="64" value="${defectEntity.equipManageTypeName}" readonly="readonly">
														</div>
<!-- 														<label class="col-md-2 control-label no-padding-right">损失电量（万kwh）</label> -->
<!-- 															<div class="col-md-4"> -->
<%-- 															<input class="col-md-12" id="lossElectricity" name="lossElectricity" onkeyup="value=value.replace(/[\D]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[\D]/g,''))" readonly="readonly" type="text" placeholder="" maxlength="64" value="${defectEntity.lossElectricity }"  > --%>
<!-- 															</div> -->
													</div>
														<div class="form-group">
															<label class="col-md-2 control-label no-padding-right">设备缺陷描述</label>
															<div class="col-md-10">
																<textarea name="depict" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128" readonly="readonly">${defectEntity.depict}</textarea>
															</div>
														</div>
														<div class="form-group">
															<label class="col-md-2 control-label no-padding-right">备注</label>
															<div class="col-md-10">
																<textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128" readonly="readonly" >${defectEntity.remark}</textarea>
															</div>
														</div>
														<div class="form-group">
															<label class="col-md-2 control-label no-padding-right">处理意见</label>
															<div class="col-md-10">
															<textarea name="handlingSuggestion" placeholder="" readonly="readonly" style="height:100px; resize:none;" class="col-md-12" maxlength="128">${defectEntity.handlingSuggestion }</textarea>
														</div>
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
								<div id="workTicketDetail" class="tab-pane fade" >
									<div id="workTicket"></div>	
								</div>
								<div id="czbDetail" class="tab-pane fade ">
									<div id="czb"></div>		
								</div>
								<div id="sbydDetail" class="tab-pane fade ">
									<div id="sbyd"></div>		
								</div>
							</div>
						</div>
         	</div>
        <div class="col-md-12">
    		<div style="margin-right:100px;margin-top: 20px;"  >
        		<button   id="printBtn"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-print"></i>
		    				打印
		    	</button>
        	</div>
        </div>
		<script type="text/javascript">
			var detailDialog;
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					//附件上传
					var uploaddropzone=new A.uploaddropzone({
						render : "#divfile",
						fileId:${defectEntity.fileId},
						autoProcessQueue:false,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true,
					}).render();
					
					$("#equipNumber").attr("title",'${defectEntity.equipId}');
					$("#equipName").attr("title",'${defectEntity.equipName}');
					
					
					//全部按钮
					$("#resetBtn").on("click", function(e){
						window.scrollTo(0,0);
						 $("#page-container").load(format_url('/defect/index'));
    				});
					$("#printBtn").on("click", function(e){
// 						birtPrint("zjk_defect.rptdesign",${defectEntity.id});
						birtPrint("defectDetail.rptdesign",${defectEntity.id});
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
						if($(e.target).attr('href') == "#defectDetail"){
							//alert("自定义选择");
						}
						if($(e.target).attr('href') == "#workTicketDetail"){
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
						}
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