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
			<form class="form-horizontal" role="form"  id="editForm" style="margin-right:90px;">
			  <input class="col-md-12" id="id" name="id" type="hidden" placeholder="" maxlength="20" value="${ entity.id }">
			  <div class="col-md-12" >
			  
<!-- 			   <div class="form-group"> -->
<!-- 				    <label class="col-md-2 control-label no-padding-right"> -->
<!-- 					    <span style="color:red;"></span>申请编号 -->
<!-- 				    </label> -->
<!-- 				    <div class="col-md-4"> -->
<%-- 					    <input class="col-md-12" id="requestNumber" name="requestNumber" type="text" readOnly placeholder="" maxlength="64" value="${ entity.requestNumber }" readOnly> --%>
<!-- 					</div> -->
<!-- 				</div> -->
				
			  <%--  <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;"></span>设备编号
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="equipNumber" name="equipNumber" type="text" readOnly placeholder="" maxlength="64" value="${ entity.equipNumber }" readOnly>

				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;"></span>设备名称
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="equipName" name="equipName" type="text" placeholder="" maxlength="64" readOnly value="${ entity.equipName }">
					</div>
				</div> --%>
				
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					  申请单位
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="unitName" name="unitName" type="text" placeholder="" maxlength="64" readOnly value="${ entity.unitName }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    申请停电时间
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="requestDateString" name="requestDateString" type="text" placeholder="" maxlength="64" readOnly value="${ entity.requestDateString }">
					</div>
				</div>
				
			    <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    停送电联系人
				    </label>
				    <div class="col-md-4">
						 <input class="col-md-12" id="requestUserName" name="requestUserName" type="text" placeholder="" maxlength="64" readOnly value="${ entity.requestUserName }">
						
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;"></span>是否停送电
				    </label>
				    <div class="col-md-4">
                    	<input class="col-md-12" id="isPowerText" name="isPowerText" type="text" placeholder="" maxlength="64" readOnly value="${ entity.isPowerText }">
					</div>
				</div>
				<div class="form-group">
	                <label class="col-md-2 control-label no-padding-right">
							涉及电压等级
					    </label>
					    <div class="col-md-4" style="padding-top:7px">
						    <c:forEach items="${levelList}" var="list" varStatus="vs">
								<label class=" inline  col-md-2  no-padding-right" style="width: 80%;">
									<input id="powerLevel${list.code}" name="powerLevel" type="checkbox"  value="${list.code}"/>${list.name}
								</label>
							</c:forEach>
						</div>
						<label class="col-md-2 control-label no-padding-right">
						 停送电方式
					    </label>
					    <div class="col-md-4">
					    	<input class="col-md-12" id="powerStatusText" name="powerStatusText" type="text" placeholder="" maxlength="64" readOnly value="${ entity.powerStatusText }">
	                    </div>
			       </div>
				
			      <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    工作任务
				    </label>
				    <div class="col-md-10">
					    <textarea name="powerDec" placeholder="" style="height:100px; resize:none;" class="col-md-12" readOnly maxlength="1024">${ entity.powerDec }</textarea>
				    </div>
			     </div>
    		    <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
						  设备所在地点及名称
						</label>
						<div class="col-md-10">
							<textarea name="equipLocalName" id="equipLocalName"placeholder="请输入设备所在地点及名称"  readOnly style="height:100px; resize:none;" class="col-md-12" maxlength="1024">${ entity.equipLocalName }</textarea>
						</div>
	               </div>
		           <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
							停电范围
						</label>
						<div class="col-md-10">
							<textarea name="powerRange" id="powerRange"placeholder="请输入停电范围" readOnly style="height:100px; resize:none;" class="col-md-12" maxlength="1024">${ entity.powerRange }</textarea>
						</div>
	               </div>
			      <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    要求断开的开关刀闸及应作安全措施
				    </label>
				    <div class="col-md-10">
					    <textarea name="remark" placeholder="" readOnly style="height:100px; resize:none;" class="col-md-12" maxlength="128">${ entity.remark }</textarea>
				    </div>
			     </div>
			     
<!-- 			   <div class="form-group"> -->
<!-- 					<label class="col-md-2 control-label no-padding-right"> -->
<!-- 						附件 -->
<!-- 					</label> -->
<!-- 					<div class="col-md-10" id="divfile"> -->
<!-- <!-- 						<div class="col-xs-12"  style="min-height:100px; background-color: #f5f5f5;padding:5px;border: 1px solid #D5D5D5"> --> 
<!-- <!-- 						</div> --> 
<!-- 					</div> -->
<!-- 			   </div> -->
<!-- 	<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >执行情况</h5> -->
			</div>
        </form>	
        	<div style="margin-right:50px;">
    			<button id="cancelBtn"  class="btn btn-xs btn-danger pull-right" style="margin-top:50px;" data-dismiss="modal">
    				<i class="ace-icon glyphicon glyphicon-remove"></i>
    				取消
    			</button>
        	</div>
       
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['uploaddropzone'], function(A){
// 				 var uploaddropzone =new A.uploaddropzone({
// 						render : "#divfile",
// 						fileId:${entity.attchmentIds},
// 						maxFilesize:1,
// 						autoProcessQueue:false,//是否自动上传
// 						addRemoveLinks : false,//显示删除按钮
// 						chargeUp:true 
// 					}).render();

                   //电压等级
					var type_value =[]; 
					$("input[name='powerLevel']:checked").each(function(){
							type_value.push($(this).val());
						});
						$("#powerLevel").val(type_value);
						
						//控制回显多选框开始
						var onecheck='${entity.powerLevel}';
						var  powerCheck =onecheck.split(",");
						
						$("input[name='powerLevel']").each(function(){
							debugger;
							if(contains(powerCheck,$(this).val())){
								 $("#powerLevel"+$(this).val()).attr("checked",'true');
							}
						});
				 
// 				 var uploaddropzone1 =new A.uploaddropzone({
// 						render : "#resultdivfile",
// 						fileId:${entity.attchmentResultIds},
// 						maxFilesize:1,
// 						autoProcessQueue:true,//是否自动上传
// 						addRemoveLinks : false,//显示删除按钮
// 						chargeUp:true 
// 					}).render('Other');
				 
				 
				 
// 				 var attchmentResultIds = '${entity.attchmentResultIds}';
// 				 if(attchmentResultIds!=null && attchmentResultIds!=""){
// 					 var attchmentResultIdsJson = $.parseJSON('${entity.attchmentResultIds}');
// 					 var html = "";
// 					  $.each(attchmentResultIdsJson, function(i, item) {
// 						  html +=" <div class='col-md-3'><a href='"+ format_url(item.url) +"'>"+item.name+"</a></div>"
// 					  });
// 					$("#resultdivfile").html(html);
// 				 }
				
				});
			});
			//判断值是否在数组里的方法zzq
			function contains(arr, obj) {
				  var i = arr.length;
				  while (i--) {
				    if (arr[i] === obj) {
				      return true;
				    }
				  }
				  return false;
			}
        </script>
    </body>
</html>