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
				<li>
					标准管理
				</li>
				<li class="active">制度管理</li>
				<li class="active">查看</li>
			</ul>
		</div>
		<div class="page-content" style="margin-left:30px;margin-right:30px;">
		<div style="float:right;margin-right:50px;">
			<button id="btnBack"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style="    margin-top: 14px;">
       			<i class="fa fa-reply"></i>
       			返回
       		</button>
		</div>
	<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >基础信息</h5>
		<div class="col-md-12" style="margin-left: -55px" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;margin-top: 30px" id="addForm">
			  <input class="col-md-12" id="id" name="id" type="hidden" placeholder="" maxlength="20" value="">
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						编码
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="code" name="code" readonly="readonly" type="text" placeholder="" maxlength="64" value="${sysManagementEntity.code }">
                    </div>
                      <label class="col-md-2 control-label no-padding-right">
						单位名称
				    </label>
				    <div class="col-md-4">
				    <input class="col-md-12" id="unitId" name="unitId" readonly="readonly" type="text" placeholder="" maxlength="64" value="${sysUnitEntity.name}">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						制度名称
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="sysName" name="sysName" readonly="readonly" type="text" placeholder="" maxlength="20" value="${sysManagementEntity.sysName }">
                	</div>
                	<label class="col-md-2 control-label no-padding-right">
						填报人
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="applyUserId" name="applyUserId" readonly="readonly" type="text" placeholder="" maxlength="20" value="${sysUserEntity.name}">
                	</div>
				    
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						实施日期
				    </label>
				    <div class="col-md-4">
					<input class="col-md-12" id="materialDate" name="materialDate" type="text" placeholder="" maxlength="64" value="<fmt:formatDate value="${sysManagementEntity.materialDate}" type="both"/>" readonly="readonly">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						年号
				    </label>
				    <div class="col-md-4">
				     <input class="col-md-12" id="yearNum" name="yearNum" readonly="readonly" type="text" placeholder="" maxlength="20" value="<fmt:formatDate pattern="yyyy"  value="${sysManagementEntity.yearNum}" type="both"/>">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						类别
					</label>
					<div class="col-md-4">
	               <input class="col-md-12" id="type" name="type" readonly="readonly" type="text" placeholder="" maxlength="20" value="${sysManagementEntity.typeName}">
                	</div>
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>归口单位</label>
					<div class="col-md-4">
						<input class="col-md-12"name="centralizedUnitId" readonly="readonly" type="text" value="${sysManagementEntity.centralizedUnitName}">
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
		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${sysManagementEntity.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render();
					$("#btnBack").on("click", function(){
						A.loadPage({
							render : '#page-container',
							url : format_url("/sysManagement/index")
						});
    				});
    				
				});
			});
        </script>
    </body>
</html>