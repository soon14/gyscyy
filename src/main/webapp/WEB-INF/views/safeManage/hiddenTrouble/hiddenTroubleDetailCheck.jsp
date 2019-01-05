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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
			<input class="col-md-12" id="id" name="id" type="hidden" placeholder="" maxlength="20" value="${ entity.id }">
			   <input id="type" class="col-md-12" name="type" value="${entity.type}"  type="hidden"/>
		        <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					单位
					</label>
					<div class="col-md-4">
					<input class="col-md-12" id="unitId" name="unitId" type="text"  readonly="readonly" maxlength="64" required="required" value="${ sysUnitEntity.name }">
					</div>
				      <label class="col-md-2 control-label no-padding-right">
					  填写人
				    </label>
				    <div class="col-md-4">
				         <input  class="col-md-12" id="createUserId" name="createUserId" placeholder=""  type="text" maxlength="64" readonly="readonly" value="${userName }">
                    </div>	
				</div>
			   <div class="form-group">
<!-- 			       <label class="col-md-2 control-label no-padding-right"> -->
<!-- 					    年号 -->
<!-- 				    </label> -->
<!-- 				    <div class="col-md-4"> -->
<%-- 					    <input class="col-md-12" id="yearNum" name="yearNum" type="text"  readonly="readonly" maxlength="64" value="${ entity.yearNumString }"> --%>
<!-- 					</div> -->
					 <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>排查人员
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="investigator" name="investigator"style="resize:none;"  placeholder="" type="text" maxlength="128" value="${entity.investigator}" readonly="readonly">
                	</div>
				  	<label class="col-md-2 control-label no-padding-right">
					     排查时间
				    </label>
				    <div class="col-md-4">
				     <input class="col-md-12" id="checkDate" name="checkDate" type="text"  readonly="readonly" maxlength="64" value="${ entity.checkDateString }">
				    </div>
				
				</div>
				<div class="form-group">
					
				    <label class="col-md-2 control-label no-padding-right">
						整改情况
				    </label>
				    <div class="col-md-4">
				     <input  class="col-md-12" id="editContent" name="editContent" placeholder=""  type="text" maxlength="64" readonly="readonly" value="${entity.editContent }">
                    </div>
                   <label class="col-md-2 control-label no-padding-right">
						整改时间
					</label>
					 <div class="col-md-4">
					  <input class="col-md-12" id="editDate" name="editDate" type="text"  readonly="readonly" maxlength="64" value="${ entity.editDateString }">
                    </div>
               </div>
			    <div class="form-group">
                   <label class="col-md-2 control-label no-padding-right">
					隐患描述
				    </label>
				   <div class="col-md-10">
				    <textarea class="col-md-12" id="troubleName" name="troubleName" readonly="readonly" style="height:80px; resize:none;"  placeholder="" maxlength="128">${ entity.troubleName }</textarea>
<%-- 				     <input  class="col-md-12" id="troubleName" name="troubleName" placeholder=""  type="text" maxlength="64" readonly="readonly" value="${ entity.troubleName }"> --%>
                    </div>
                </div>
				<div class="form-group">
                <label class="col-md-2 control-label no-padding-right">
					 备注
				    </label>
				    <div class="col-md-10">
						  <textarea class="col-md-12" id="remark" name="remark"style="height:80px; resize:none;" readonly="readonly"  placeholder="" maxlength="128">${entity.remark }</textarea>
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
        		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
        			<i class="ace-icon fa fa-times"></i>
        			取消
        		</button>
<!--         		<button id="editBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;"> -->
<!--         			<i class="ace-icon fa fa-floppy-o"></i> -->
<!--         			保存 -->
<!--         		</button> -->
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
        			

        			//附件上传
						var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:'${entity.fileId}'==""?[]:${entity.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render();
        			
					
				
// 					$("#editBtn").on("click", function(){
//     					$("#editForm").submit();
//     				});
				});
			});
        </script>
    </body>
</html>