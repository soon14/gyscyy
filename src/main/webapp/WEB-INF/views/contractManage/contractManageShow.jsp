<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh">
	<head>
    	<meta charset="UTF-8">
    	<%@ include file="/WEB-INF/views/common/meta.jsp" %> 
    </head>
	<body>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>
						所属部门
				    </label>
				    <div class="col-md-4">
				    	<input class="col-md-12" id="contractXdname" name="contractXdname" readonly="readonly" type="text" placeholder="" value="${ entity.contractXdname }">
                    </div>
                    <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>
						供应商
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="remark" name="remark" type="text"  readonly="readonly" placeholder="" maxlength="64" value="${ entity.remarkName }">
                	</div>
               </div>
		       <div class="form-group">
                    <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>
						合同编号
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="contractCode" name="contractCode" type="text"  readonly="readonly" placeholder="" maxlength="64" value="${ entity.contractCode }">
                	</div>
                	<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>
						已签合同名称
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="contractName" name="contractName" type="text"  readonly="readonly" placeholder="" maxlength="64" value="${ entity.contractName }">
                    </div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>
						合同金额（元）
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="contractMoney" name="contractMoney" type="text"  readonly="readonly" placeholder="" maxlength="64" value="${ entity.contractMoney }">
                    </div>
                    <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>
						签约时间
					</label>
					<div class="col-md-4">
					   <input class="col-md-12" id="qdrq" type="text"
									readonly="readonly" maxlength="64"
									value='<fmt:formatDate  pattern="yyyy-MM-dd" value="${entity.qdrq}" type="date"/>'>
                	</div>
               </div>
		       <div class="form-group">
                    <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>
						采购方式
				    </label>
				    <div class="col-md-4">
				    	<input class="col-md-12" id="zbfs" name="zbfs" type="text"  readonly="readonly" placeholder="" maxlength="64" value="${ entity.zbfsName }">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>合同履行开始时间
					</label>
					<div class="col-md-4">
					<input class="col-md-12" id="contractStartDate" name="contractStartDate" readonly="readonly" type="text" placeholder="" value='<fmt:formatDate  pattern="yyyy-MM-dd" value="${entity.contractStartDate}" type="date"/>'>
                	</div>
               </div>
               <div class="form-group">
		       <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>
						合同年限
				    </label>
				    <div class="col-md-4">
				    	<input class="col-md-12" id="contractLife" name="contractLife" readonly="readonly" type="text" placeholder="" value="${ entity.contractLife}年">
                    </div>
               <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>合同履行结束时间
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="contractEndDate" name="contractEndDate" readonly="readonly" type="text" placeholder="" value="${contractEndDateStr}" >
                	</div>
               </div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    附件上传
				    </label>
				    <div class="col-md-10" id="attachmentDiv"  >
					</div>
               </div>
			</form>
    		<div style="margin-right:100px;">
        		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
        			<i class="ace-icon fa fa-times"></i>
        			取消
        		</button>
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				var sysUserEntity = ${sysUserEntity};
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					
					
        			var appData = ${entityJson};
					//附件
					var uploadattachment=new A.uploaddropzone({
						render : "#attachmentDiv",
						fileId:${entity.attachment},
						autoProcessQueue:false,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render();
					
				});
			});
			function getFormatDate(){    
			    var nowDate = new Date();     
			    var year = nowDate.getFullYear();    
			    var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1) : nowDate.getMonth() + 1;    
			    var date = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();    
			    var hour = nowDate.getHours()< 10 ? "0" + nowDate.getHours() : nowDate.getHours();    
			    var minute = nowDate.getMinutes()< 10 ? "0" + nowDate.getMinutes() : nowDate.getMinutes();    
			    var second = nowDate.getSeconds()< 10 ? "0" + nowDate.getSeconds() : nowDate.getSeconds();    
			    return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;    
			} 
        </script>
    </body>
</html>