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
	<div class="breadcrumbs ace-save-state" id="breadcrumbs" style="margin-bottom:100px;">
				<ul class="breadcrumb">
					<li>
						<i class="ace-icon fa fa-home home-icon"></i>
						<a href="javascript:void(0);" onclick="firstPage()">首页</a>
					</li>
					<li class="active">缺陷管理</li>
					<li class="active">缺陷管理</li>
					<li class="active">修改</li>
				</ul><!-- /.breadcrumb -->
				<div style="margin-right:100px;margin-top:10px;">
        		<button id="button" class="btn btn-xs btn-primary pull-right">
    				<i class="fa fa-reply"></i>
    				返回
    			</button>
        		</div>
        		<h5 class="table-title header smaller blue" style="margin-left:30px;margin-right: 20px">基础信息</h5>
		</div>
	<div class="page-content">
		<div class="col-md-12" >
		       		 <form id="editForm" class="form-horizontal" role="form">
    				 <input type="hidden" id="id" name="id" value="${defectEntity.id}" />
<%--     				 <input type="hidden" id="createDate" name="createDate" value="${defectEntity.createDate}" /> --%>
							<div class="form-group">
								<label class="col-md-1 control-label no-padding-right">缺陷编号</label>
								<div class="col-md-4">
											<input class="col-md-12" id="code" name="code" type="text" placeholder="" maxlength="64" value="${defectEntity.code}" readonly="readonly">
								</div>
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>缺陷单位名称</label>
								 		<div class="col-md-4">
										<select class="col-md-12 chosen-select" id="unitId" name="unitId"></select>
								</div>
<!-- 										<input class="col-md-12" id="unitId" type="hidden" readonly="readonly" name="unitId" value="${defectEntity.unitId}"></input> -->
<!-- 										<input class="col-md-12" id="unitName" type="text" readonly="readonly"  value="${defectEntity.unitName}"></input> -->
							</div>
							<div class="form-group">
								<label class="col-md-1 control-label no-padding-right"><span style="color:red;">*</span>设备编码</label>
								<div class="col-md-4">
<%-- 									<input class="col-md-12" id="equipId" name="equipId"  value="${defectEntity.equipId}" type="hidden" style='position: absolute;top: 0;right: 0;width: 90%;'> --%>
									<div id="equipNumberDiv">
									</div>
								</div>	
								<label class="col-md-2 control-label no-padding-right">设备名称</label>
									<div class="col-md-4">
											<input class="col-md-12" id="equipName" name="equipName" type="text" 
											placeholder="" maxlength="64" value="${defectEntity.equipName}" readonly="readonly">
											<input class="col-md-12" id="equipType" name="equipType" 
												type="hidden" placeholder="" maxlength="128" value="" readonly="readonly" >
											<input class="col-md-12" id="equipTypeName" name="equipTypeName" 
												type="hidden" placeholder="" maxlength="128" value="" readonly="readonly" >
								</div>
							</div>
								<div class="form-group">
								<label class="col-md-1 control-label no-padding-right"><span style="color:red;">*</span>发现人</label>
								<div class="col-md-4">
										<select class="col-md-12 chosen-select" id="findUserId" name="findUserId"></select>
								</div>
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>发现时间</label>
									<div class="col-md-4">
										<div id="findTimeDiv"></div>
								</div>
							</div>
<!-- 								<div class="form-group"> -->
<!-- 								<label class="col-md-1 control-label no-padding-right"><span style="color:red;">*</span>缺陷设备类型</label> -->
<!-- 									<div class="col-md-4"> -->
<!-- 									<select class="col-md-12 chosen-select" id="equipType" name="equipType" style='position: absolute;z-index: -1;width: 60%;right:0'></select> -->
<!-- 								</div> -->
<!-- 							</div> -->
								<div class="form-group">
								<label class="col-md-1 control-label no-padding-right"><span style="color:red;">*</span>设备类型</label>
									<div class="col-md-4">
									<input class="col-md-12" id="equipManageType" name="equipManageType" type="hidden" placeholder="" maxlength="64" value="${defectEntity.equipManageType}" readonly="readonly">
									<input class="col-md-12" id="equipManageTypeName" name="equipManageTypeName" type="text" placeholder="" maxlength="64" value="${defectEntity.equipManageTypeName}" readonly="readonly">
								</div>
<!-- 								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>损失电量（万kwh）</label> -->
<!-- 									<div class="col-md-4"> -->
<%-- 									<input class="col-md-12" id="lossElectricity" name="lossElectricity" onkeyup="value=value.replace(/[\D]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[\D]/g,''))" type="text" placeholder="" maxlength="64" value="${defectEntity.lossElectricity }"  > --%>
<!-- 								</div> -->
							</div>
								<div class="form-group">
									<label class="col-md-1 control-label no-padding-right"><span style="color:red;">*</span>设备缺陷描述</label>
									<div class="col-md-10">
										<textarea name="depict" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128">${defectEntity.depict}</textarea>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-1 control-label no-padding-right">备注</label>
									<div class="col-md-10">
										<textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128">${defectEntity.remark}</textarea>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-1 control-label no-padding-right"><span style="color:red;">*</span>处理建议</label>
									<div class="col-md-10">
										<textarea name="handlingSuggestion" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128">${defectEntity.handlingSuggestion }</textarea>
									</div>
								</div>
				</form>
				<div class="form-group form-horizontal">
								<label class="col-md-1 control-label no-padding-right">附件</label>
									<div class="col-xs-10" id="divfile">
									
									</div>
							</div>
    		<div style="margin-right:100px;">
        		<button id="editBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
        			<i class="ace-icon fa fa-floppy-o"></i>
        			保存
        		</button>
<!--     		    <div id="upnext"> -->
<!--         		</div> -->
        	</div>
        </div>
      </div>
		<script type="text/javascript">
		var userUnitRels = ${userUnitRels};
		//初始数据备份
		var processUserUnitRels = ${userUnitRels};
		var id = ${id};
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','selectbox','upnextstrip','uploaddropzone'], function(A){
					var unitId = new A.combobox({
						render : "#unitId",
						datasource : ${unitNameIdTreeList},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					unitId.setValue(${defectEntity.unitId});
					//附件上传
					var uploaddropzone=new A.uploaddropzone({
						render : "#divfile",
						fileId:${defectEntity.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					var equipList=[];
					var nameList='';
					var codeList='';
					var equipManageTypeList='';
					var equipTypeList='';
					var equipTypeNameList='';
					var equipIds = [];
					var selecttreeDh = new A.selectbox({
						id: 'equipIdName',
						name:'equipId',
						title:'设备台账',
						url:'/equipLedger/selectEquipLedger?id='+id,
						render:'#equipNumberDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data){
							codeList = "";
							nameList = "";
							equipManageTypeList = "";
							equipTypeList = "";
							equipTypeNameList = "";
							idList = "";
							for(var i=0; i< data.length;i++){
									 equipIds.push(data[i].id);
									 codeList += data[i].code+',';
	 								 nameList += data[i].name+',';
	 								 equipManageTypeList += data[i].equipManageTypeName+',';
	 								 equipTypeList += data[i].equipType+',';
	 								 equipTypeNameList += data[i].equipTypeName+',';
	 								 idList += data[i].id+',';
							  }
							    selecttreeDh.setValue(codeList);
								$("#equipName").val(nameList);
								$("#equipManageTypeName").val(equipManageTypeList);
								$("#equipType").val(equipTypeList);
								$("#equipTypeName").val(equipTypeNameList);
								$("#equipNumberDiv").attr("title",codeList);
								$("#equipName").attr("title",nameList);
								$("#ids").val(idList);
						}
					}).render();
					selecttreeDh.setValue('${defectEntity.equipId}');
					//部门控件下拉树
					 searchunitid = new A.combobox({
						render : "#unitIdDiv",
						datasource : ${unitNameIdTreeList},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
// 					 searchunitid.setValue(${defectEntity.solveUnitId});
					//发现人
					var findUserId = new A.combobox({
						render : "#findUserId",
						datasource : ${searchuser},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					findUserId.setValue(${defectEntity.findUserId});
					//日期组件
					var findTimeDatePicker = new A.my97datepicker({
						id: "findTimeId",
						name: "findTime",
						render: "#findTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
				
					findTimeDatePicker.setValue('<fmt:formatDate value="${defectEntity.findTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />');
					var flag = true;
					unitId.change(function(event, value){
							if(flag){
								A.confirm('您确认改变单位名称么？(改变后现有设备编码和设备名称将会清空)',function(){
									selecttreeDh.setValue();
									$("#equipName").val(null);
								});
							}
							flag=true;
						});
					$('#editForm').validate({
						debug:true,
						rules:  {unitId:{required:true,maxlength:64},
							 equipId:{required:true},
							 findUserId:{required:true,maxlength:64},
							 findTime:{required:true,maxlength:64},
							 type:{required:true,maxlength:20},
							 solveUnitId:{required:true,maxlength:64},
							 depict:{required:true}
							 },
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/defect/update");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							obj.fileId=JSON.stringify(uploaddropzone.getValue());
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
										if(result.result=="success"){
											alert('修改成功');	
										 $("#page-container").load(format_url('/defect/index'));
										}else{
											alert(result.errorMsg);
										}
								},
								error:function(v,n){
									alert('修改失败');
								}
							});
						}
					});
					$("#editBtn").on("click", function(){
    					$("#editForm").submit();
    				});
					$("#button").on("click", function(e){
						window.scrollTo(0,0);
						 $("#page-container").load(format_url('/defect/index'));
    				});
					//上一条 下一条
// 					 new A.upnextstrip({
// 						render : "#upnext",
// 						url : format_url('/defect/getEdit/0'),
// 						data: '${dedit}',
// 						options : {
							
// 						}
// 					}).render();
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