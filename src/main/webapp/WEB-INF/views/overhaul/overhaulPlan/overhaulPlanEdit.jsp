<%@page import="com.aptech.business.component.dictionary.OverhaulPlanStatusEnum"%>
<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>  
<%@ page import="com.aptech.business.component.dictionary.PowerStatusEnum"%>
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
					<li class="active">
					检修管理</li>
					<li class="active">
					检修计划</li>
					<li class="active">修改</li>
				</ul>
		</div>
	<div class="page-content">
			   <div style="float:right; margin-right:50px;">
					<button id="btnBack"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style="    margin-top: 14px;">
	        			<i class="fa fa-reply"></i>
	        			返回
	        		</button>
				</div>
		
		<h5 class='table-title-withoutbtn header smaller blue' style="margin-right:50px;margin-left:30px;" >基础信息</h5>
	
		<div class="widget-main no-padding" style="margin-right:50px;margin-left:30px;">
				<form class="form-horizontal" role="form"   id="editForm">
				   <input class="col-md-12" id="dutyUserName" name="dutyUserName" type="hidden" value="${entity.dutyUserName }">
				   <input class="col-md-12" id="approveStatus" name="approveStatus" type="hidden" value="${entity.approveStatus }">
				   <input class="col-md-12" id="unitName" name="unitName" type="hidden" value="${entity.unitName }">
				   <input class="col-md-12" id="userList" name="userList" type="hidden" value="${entity.userList }">
				   <input class="col-md-12" id="id" name="id" type="hidden" value="${entity.id }">
<%-- 				    <input class="col-md-12" id="planNumber" name="planNumber"  type="hidden" value="${entity.planNumber}"> --%>
				   
				    <div class="col-md-12" style="margin-top:20px">
			       <div class="form-group">
						<label class="col-md-1 control-label no-padding-right">
							<span style="color:red;">*</span>检修计划编号
						</label>
						<div class="col-md-4">
						   <input class="col-md-12" id="planNumber" name="planNumber" readOnly type="text" value="${entity.planNumber}">
						
	                	</div>
					     <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>申报日期
					    </label>
					    <div class="col-md-4">
							<div id="planYearDiv"></div>
	                    </div>
	               </div>
		           <div class="form-group">
		            <label class="col-md-1 control-label no-padding-right">
							<span style="color:red;">*</span>单位名称
					    </label>
						 <div class="col-md-4">
						    <select class="col-md-12 chosen-select" id="unitId" name="unitId"></select>
					    </div>

						<label class="col-md-2 control-label no-padding-right">
					      修改项目
						</label>
						   <div class="col-md-4">
		                       <input class="col-md-12" id="updateProject" name="updateProject"  type="text" value="${entity.updateProject}" placeholder="" >
		                   </div>
	               </div>
	    		  <div class="form-group">
						<label class="col-md-1 control-label no-padding-right">
							检修计划名称
						</label>
						<div class="col-md-4">
						<c:set var="planNameId "  value="${entity.planNameId}"/>
						<select class="col-md-12 chosen-select"  id="planNameId" name="planNameId">
						<c:if test="${entity.planNameId=='0'}">
								<option value="0"  selected="selected">无</option>
								<option value="1"  >有年度计划</option>
						</c:if>
						<c:if test="${entity.planNameId=='1'}">
								<option value="0"  >无</option>
								<option value="1"  selected="selected" >有年度计划</option>
						</c:if>
						</select>
						</div>
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>计划填报人
					    </label>
					    <div class="col-md-4">
							<select class="col-md-12 chosen-select" id="dutyUserId" name="dutyUserId"></select>
	                    </div>
	               </div>
	               <div class="form-group">
						<label class="col-md-1 control-label no-padding-right">
							签报标题
						</label>
						<div class="col-md-4">
<!-- 							<div id="dispatchTitleDiv" name="dispatchId"></div> -->
							<input id="dispatchId"  name="dispatchId" class="col-md-12" type="text" value="${entity.dispatchId}">
						</div>
<!-- 						<label class="col-md-2 control-label no-padding-right"> -->
<!-- 							发文字号 -->
<!-- 					    </label> -->
<!-- 					    <div class="col-md-4"> -->
<!-- 							<input class="col-md-12" id="dispatchNumber" name="dispatchNumber" type="text"  value="" readonly> -->
<!-- 							<input class="col-md-12" id="dispatchTitle" name="dispatchTitle" type="hidden"  value="" > -->
<!-- 	                    </div> -->
	               </div>	
		           <div class="form-group">
						<label class="col-md-1 control-label no-padding-right">
							备注
						</label>
						<div class="col-md-10">
							<textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12">${entity.remark }</textarea>
						</div>
	               </div>
			       <div class="form-group form-horizontal">
						<label class="col-md-1 control-label no-padding-right">
							附件
						</label>
						<div class="col-xs-10" id="divfile">
						</div>
	               </div>
	               </div>
	            </form>
	       </div>
	   <div class="row" style="margin-right:70px;margin-left:30px;">
	    <div class="col-xs-12">	
	    <h5 class="table-title header smaller blue ">检修项目</h5>
		<div class="widget-main no-padding">
			<form id="overhaulProject">
				<table id="overhaulProject-table"  class="table table-striped table-bordered table-hover" style="width:100%;">
					<thead>
						<tr>
							<th style="display:none;">主键</th>
							<th>序号</th>
							<th><span style="color:red;">*</span>项目名称</th>
                       		<th><span style="color:red;">*</span>项目明细</th>
                       		<th>列入原因</th>
                       		<th>方案措施</th>
                       		<th><span style="color:red;">*</span>开工时间</th>
                       		<th><span style="color:red;">*</span>完成时间</th>
                       		<th><span style="color:red;">*</span>计划总投资(元)</th>
                          	<th>操作</th>
						</tr>
					</thead>
				</table>
			</form>
			</div>
		</div>
		</div>
		
		<div class="row" style="margin-right:70px;margin-left:30px;">
		    <div class="col-xs-12" >	
		    <h5 class="table-title header smaller blue">停送电管理</h5>
			<div class="widget-main no-padding">
				 <table id="power_table" class="table table-striped table-bordered table-hover" style="width:100%;">
					<thead>
						<tr>
							<th style="display:none;">主键</th>
							  <th>序号</th>
							  <th>申请编号</th>
                              <th>申请单位</th>
                              <th>停送电联系人</th>
                              <th>工作任务</th>
                              <th>是否停送电</th>
                              <th>申请停电时间</th>
	                      	  <th>操作</th>
						</tr>
					</thead>
				</table>
			</div>
		 </div>
		 </div>
		 
    		<div style="margin-right:50px;">
<!--     			<button id="cancelBtn"  class="btn btn-xs btn-danger pull-right" style="margin-top:50px;" data-dismiss="modal"> -->
<!--     				<i class="ace-icon glyphicon glyphicon-remove"></i> -->
<!--     				取消 -->
<!--     			</button> -->
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;margin-top:50px;">
    				<i class="ace-icon glyphicon glyphicon-floppy-disk"></i>
    				保存
    			</button>
    			
    			<button id="submitBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;margin-top:50px;">
    				<i class="ace-icon glyphicon glyphicon-ok"></i>
    				提交审批
    			</button>
    		</div>
</div>
		<script type="text/javascript">
			var listFormDialog;
			var powerDatatables ;
			var overhaulPlanId=$('#id').val();
			var userUnitRels = ${userUnitRels};
			//初始数据备份
			var processUserUnitRels = ${userUnitRels};
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','selectbox','uploaddropzone'], function(A){
					var delProjectIds =[];
					var tempOverhaulPlanId=$('#id').val();
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${entity.attachementIds},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					
					var selectDispatchTitle = new A.selectbox({
						id: 'selectDispatchTitle',
						name: 'dispatchId',
						title:'选择发文',
						url:'/overhaulPlan/goSelectDispatchPage',
						render:'#dispatchTitleDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data,self){
							if(data){
								$("#dispatchNumber").val (data.dispatchName);
								$("#dispatchTitle").val (data.title);
								self.setValue(data.title,data.id);
							};
						}
					}).render();
					selectDispatchTitle.setValue('${entity.dispatchTitle}',${entity.dispatchId});
					$("#dispatchNumber").val('${entity.dispatchNumber}');
					$("#dispatchTitle").val ('${entity.dispatchTitle}');
// 					//检修项目
// 					var planNamelist = new A.combobox({
// 						render : "#planNameId",
// 						datasource : ${planList},
// 						allowBlank: true,
// 						options : {
// 							"disable_search_threshold" : 10
// 						},
// 						callback: function(data){
							
// 						}
// 					}).render();
// 					planNamelist.setValue('${entity.planNameId}');
					
					var unitIdCombobox = new A.combobox({
                		render: "#unitId",
                		//返回数据待后台返回TODO
                		datasource:${unitList},
                		//multiple为true时select可以多选
                		multiple:false,
                		//allowBlank为false表示不允许为空
                		allowBlank: true,
                		options:{
                			"disable_search_threshold":10
                		}
                	}).render();
                    unitIdCombobox.setValue('${entity.unitId}');
                    
					//combobx组件
					/* var unitNameCombobox = new A.combotree({
						render: "#unitIdDiv",
						name: 'unitId',
						//返回数据待后台返回TODO
						datasource: ${unitNameIdTreeList},
						width:"210px",
						options: {
							treeId: 'unitId',
							data: {
								key: {
									name: "name"
								},
								simpleData: {
									enable: true,
									idKey: "id",
									pIdKey: "parentId"
								}
							}
						},
						callback: function(data){
							if(data&&data[0]){
								$("#unitName").val(data[0].name);
							};
							
						}
					}).render();
					unitNameCombobox.setValue(${entity.unitId});
					$("#unitName").val('${entity.unitName}'); */
					
					
					var status = '${entity.approveStatus}';
					if(status == '<%=OverhaulPlanStatusEnum.REJECT.getCode()%>'){
						$("#submitBtn").hide();
					}
					//检修负责人
					var dutyUserId = new A.combobox({
						render : "#dutyUserId",
						datasource : ${dutyUser},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						},
						callback: function(data){
							if(data&&data[0]){
								$("#dutyUserName").val(data[0].Name);
							};
							
						}
					}).render();
					dutyUserId.setValue(${entity.dutyUserId});
					$('#dutyUserName').val('${entity.dutyUserName}');

					//日期组件
					var planYearDatePicker = new A.my97datepicker({
						id: "planYear",
						render:"#planYearDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "${date}",
								dateFmt: "yyyy"
						}
					}).render();
					
					planYearDatePicker.setValue('${entity.planYearString}');
					$('#planYear').val('${entity.planYearString}');
					
					$('#editForm').validate({
						debug:true,
						rules:  {
							unitId:{required:true, maxlength:32},
							planYear:{required:true,maxlength:32},
							planNameId:{maxlength:64},
							dutyUserId:{ required:true},
							unitId:{required:true}},
						    submitHandler: function (form) {
						    var id = $("#id").val();
							var url = format_url("/overhaulPlan/update/"+id);
							var obj = $("#editForm").serializeObject();
							obj.planYear = $('#planYear').val()+" 00:00:00";
							obj.attachementIds=JSON.stringify(uploaddropzone.getValue());
							var projectobj = $("#overhaulProject").serializeObject();
							
							
							
							var resultList= [];
							for(var key in projectobj){
								var keylist = [];
								var tablelength = $("#overhaulProject-table").find("tr").length;
								if(tablelength == 2){
									
									keylist.push(projectobj[key]);
								}else{
									keylist=projectobj[key];
								}
								for(var i = 0; i<keylist.length; i ++){
									if(resultList.length != keylist.length){
										var result={};
										result[key] = keylist[i];
										resultList.push(result);
									}
									else{
										resultList[i][key] = keylist[i];
									}
								}
							}
							obj.list = resultList;
							obj.delIds  = delProjectIds;
							console.log(obj);
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result == "success"){
									  tempOverhaulPlanId = overhaulPlanId = result.id;
										 alert('保存成功');
										 $("#page-container").load(format_url('/overhaulPlan/index'));
									}else{
										alert(result.errorMsg);
									}
								
								},
								error:function(v,n){
									alert('保存失败');
								}
							});
							
						}
					});
					
					$("#saveBtn").on("click", function(){
<%-- 						$('#approveStatus').val("<%=PowerStatusEnum.PENDING.getCode() %>"); --%>
						if(validform().form()){
							var projectobj = $("#overhaulProject").serializeObject();
							var flag = false;
							for(var key in projectobj){
								flag = true;
	 							break;
							}
							if(flag){
								if(checkTime(projectobj)&& checkMoney(projectobj)){
									$("#editForm").submit();
								}
							}else{
								alert("检修项目不能为空");
							}
						}
						
    				});
					function checkTime(projectobj){
						var planYear = $("#planYear").val();
						var start =projectobj.startDateString;
						var end = projectobj.endDateString;
						var plantime = new Date(planYear.replace("-", "/").replace("-", "/"));
						
						if((typeof start=='string')&&start.constructor==String){
							var starttime =new Date(start.replace("-", "/").replace("-", "/"));
							var endtime =new Date(end.replace("-", "/").replace("-", "/"));
							
							if(starttime<plantime){
								alert("请填写正确的检修项目开工时间和完工时间");
								return false;
							}
							if(starttime>endtime){
								alert("请填写正确的检修项目开工时间和完工时间");
								return false;
							}
							
						}else{
							for(var k in start){
								var starttime =new Date(start[k].replace("-", "/").replace("-", "/"));
								var endtime =new Date(end[k].replace("-", "/").replace("-", "/"));
								
								if(starttime<plantime){
									alert("请填写正确的检修项目开工时间和完工时间");
									return false;
								}
								if(starttime>endtime){
									alert("请填写正确的检修项目开工时间和完工时间");
									return false;
								}
							}
						}
						return true;
					}
					function checkMoney(projectobj){
						var totalMoney =projectobj.totalMoney;
						var reg= /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
						if((typeof totalMoney=='string')&&totalMoney.constructor==String){
							if(!reg.test(totalMoney) || totalMoney==0){ 
								alert("请填写正确的计划总金额");
							    return false;
							 }
						}else{
							for(var k in totalMoney){
								if(!reg.test(totalMoney[k])|| totalMoney[k]==0){ 
									alert("请填写正确的计划总金额");
								    return false;
								}
							}
						}
						return true;
					}
					$("#submitBtn").on("click", function(){
						$('#approveStatus').val("<%=PowerStatusEnum.OVERHAUL.getCode() %>");
						if(validform().form()){
							var projectobj = $("#overhaulProject").serializeObject();
							var flag = false;
							for(var key in projectobj){
								flag = true;
	 							break;
							}
							if(flag){
								if(checkTime(projectobj)&& checkMoney(projectobj)){
									listFormDialog = new A.dialog({
										width: 850, 
										height: 481,
										title: "审批人",
										url:format_url('/power/sureSubmitPerson'),
										closed: function(resule){
											var obj=new Object(); 
											if(listFormDialog.resule){
												  var userList=listFormDialog.resule.join(",");
												  $('#userList').val(userList);
												  $("#editForm").submit();
											}
										}
									}).render();
		 							return;
								}
							}else{
								alert("检修项目不能为空");
							}
							
						}
						
						
						
    				});
					
					
					$("#btnBack").on("click", function(){
						 $("#page-container").load(format_url('/overhaulPlan/index'));
    				});
    				
					
					var conditions=[];
					overhaulProjectDatatables = new A.datatables({
						render: '#overhaulProject-table',
						options: {
							    serverSide: false,
						        multiple : true,
						        checked:false,
								bInfo:false,
								paging:false,
								ordering:true,
								optWidth: 120,
								order:[["1",'asc']],
							    "fnDrawCallback"    : function(){
									this.api().column(1).nodes().each(function(cell, i) {
									cell.innerHTML =  i + 1;
								});
							},
							columns: [
							 {data:"id",editType:"input", visible:false,orderable:false},
					         {data:null,orderable: true,"width":"3%"},
							 {data: "projectName",width: "11%",orderable: false, editType:"input"}, 
							 {data: "projecDetail",width: "11%",orderable: false, editType:"input"}, 
							 {data: "projectReason",width: "24%",orderable: false, editType:"input"}, 
							 {data: "measure",width: "24%",orderable: false, editType:"input"}, 
							 {data: "startDate",width: "8%",orderable: false,editType:"my97datepicker", cfg:{
									name: 'startDateString',
									options : {
											isShowWeek : false,
											skin : 'ext',
											maxDate: "",
											minDate: "${monthdate}",
											dateFmt: 'yyyy-MM-dd'
									},
							 
								}},
							 {data: "endDate",width: "8%",orderable: false, editType:"my97datepicker", cfg:{
									name: 'endDateString',
									options : {
											isShowWeek : false,
											skin : 'ext',
											maxDate: "",
											minDate: "${monthdate}",
											dateFmt: 'yyyy-MM-dd'
									}
								}},
							 {data: "totalMoney",width: "8%",orderable: false, 
									render : function(data, type, row, meta) {
						                   return "<input name='totalMoney' type='text' value='"+data+"'/>"+"<input name='id' value='"+row.id+"' type='hidden'/>";  
						      }
						               	
							 }
							 ],
							optWidth:50,
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							overhaulProjectDatatables.addRow({"id":0, "projectName":"", "projecDetail":"","projectReason":"", "measure":"", "startDate":null, "endDate":null, "totalMoney":""});
            							$('input[name="totalMoney"]').bind("input propertychange", function() { 
            								if($(this).val().length>10){
            									$(this).val($(this).val().substr(0,10));
            								}
            							}); 
            						}
        						}
        					}],
							btns:[
								{
									id:"delete",
									label:"删除",
									icon: "fa fa-trash-o bigger-130",
									className: "red del",
									events:{
										click: function(event, nRow, nData){
											var id = nData.id;
											if(id!=null && id!=0){
												delProjectIds.push(id);
											}
											A.confirm('您确认删除么？',function(){
												overhaulProjectDatatables.deleteSelectRow(nRow);
											});		
										}
									}
								}
							]
						}
					}).render();
					function initDataTable(){
						var params = {};
						params.length = 10;
						params.start = 0;
						params.draw = 0;
						params.conditions = [];
						params.overhualPlanId = ${entity.id};
						$.ajax({
							url: format_url("/overhaulProject/search"),
							contentType: "application/json",
							dataType: 'JSON',
							type: 'POST',
							data : JSON.stringify(params),
							success: function(result){
								overhaulProjectDatatables.addRows(result.data);
    							$('input[name="totalMoney"]').bind("input propertychange", function() { 
    								if($(this).val().length>10){
    									$(this).val($(this).val().substr(0,10));
    								}
    							}); 
							}
							
						})
					};
					initDataTable();
					
					
					//停送电管理
					 powerDatatables = new A.datatables({
							render: '#power_table',
							options: {
						        "ajax": {
						            "url": format_url("/power/search"),
						            "contentType": "application/json",
						            "type": "POST",
						            "dataType": "JSON",
						            "async":false, 
						            "data": function (d) {
						            	if(tempOverhaulPlanId){
						            		conditions.push({
												field: 'C_OVERHAUL_PLAN_ID',
						    					fieldType:'INT',
						    					matchType:'EQ',
						    					value:tempOverhaulPlanId
						    				});
						            	}else{
						            		conditions.push({
						     					field: 'C_OVERHAUL_PLAN_ID',
						     					fieldType:'INT',
						     					matchType:'EQ',
						     					value:-1
						     				});
						            	}
						            	d.conditions = conditions;
						                return JSON.stringify(d);
						              }
						        },
						        multiple : true,
								ordering: true,
								checked: false,
								optWidth: 80,
								columns: [{data:"id", visible:false,orderable:false},
								          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
							                   var startIndex = meta.settings._iDisplayStart;  
							                   row.start=startIndex + meta.row;
							                   return startIndex + meta.row + 1;  
							               	} },
								          {data: "requestNumber",visible:false,width: "120px",orderable: false},
								          {data: "unitName",width: "auto",orderable: true}, 
								          {data: "requestUserName",width: "auto",orderable: true},
								          {data: "powerDec",width: "auto",orderable: true},
								          {data: "isPowerText",width: "auto",name:"isPower",orderable: true},
								          {data: "requestDateString",width: "auto",name:"requestDate",orderable: true},
								          ],
								toolbars: [   
								    
								     {
		        						label:"新增",
		        						icon:"glyphicon glyphicon-plus",
		        						className:"btn-success",
										events:{
			    							click: function(event, nRow, nData){
			    								listFormDialog = new A.dialog({
			                						width: 850,
			                						height: 870,
			                						title: "停送电新增",
			                						url:format_url('/power/getAdd'),
			                						closed: function(){
			                							powerDatatables.draw(false);
			                						}	
			                					}).render()

										   }
			    						}
	        						}
	        						/* ,{
									label:"删除",
									icon:"glyphicon glyphicon-trash",
									className:"btn-danger",
									events:{
										click: function(event){
											var data = powerDatatables.getSelectRowDatas();
											var ids = [];
											var userIds = [];
											if(data.length && data.length>0){
												for(var i =0; i<data.length; i++){
													
													ids.push(data[i].id);
													userIds.push(data[i].requestUserId);
												}
											}
											if(ids.length < 1){
												alert('请选择要删除的数据');
												return;
											}
											if(unids.length > 0){
												alert('只有待提交和已取消的记录可以删除');
												return;
											}
											
//											var loginUser = ${sysUserId};
//											var loginName =${loginName};
//											for(var j=0;j<userIds.length;j++){
//												if(userIds[j]!=loginUser&&loginName!="super"){
//													alert('记录中包含不是当前登陆人的记录不能删除!');
//													return;
//												}
//											}
											
											
											var url = format_url('/power/bulkDelete/');
											A.confirm('您确认删除么？',function(){
												$.ajax({
													url : url,
													contentType : 'application/json',
													dataType : 'JSON',
													type : 'DELETE',
													data : JSON.stringify(ids),
													success: function(result){
														alert('删除成功');
														powerDatatables.draw(false);
													},
													error:function(v,n){
														alert('操作失败');
													}
												});
											});
										}
									}
								} */
	        						],
								btns: [{
									id: "edit",
									label:"修改",
									icon: "fa fa-pencil-square-o bigger-130",
									className: "green edit",
									render: function(btnNode, data){
										
//										var userId =${sysUserId};
//										var loginName =${loginName};
//										if(data.requestUserId!=userId && loginName!='super'){
//											btnNode.hide();
//										}
										
									},
									events:{
										click: function(event, nRow, nData){
											var id = nData.id;
											listFormDialog = new A.dialog({
		                						width: 850,
		                						height: 870,
		                						title: "停送电修改",
		                						url:format_url('/power/getEdit/' + id),
		                						closed: function(){
		                							powerDatatables.draw(false);
		                						}	
		                					}).render()
										}
									}
								}, {
									id:"delete",
									label:"删除",
									icon: "fa fa-trash-o bigger-130",
									className: "red del",
									render: function(btnNode, data){
										
//										var userId =${sysUserId};
//										var loginName =${loginName};
//										if(data.requestUserId!=userId && loginName!='super'){
//											btnNode.hide();
//										}
									},
									events:{
										click: function(event, nRow, nData){
											var id = nData.id;
											var url =format_url('/power/'+ id);
											A.confirm('您确认删除么？',function(){
												$.ajax({
													url : url,
	        										contentType : 'application/json',
	        										dataType : 'JSON',
	        										type : 'DELETE',
	        										success: function(result){
	        											alert('删除成功');
	        											powerDatatables.draw(false);
	        										},
	        										error:function(v,n){
	        											alert('操作失败');
	        										}
												});
											});
										}
									}
							},{
									id: "detail",
									label:"查看",
									icon: "fa fa-binoculars bigger-130",
									className: "blue ",
									events:{
										click: function(event, nRow, nData){
											var id = nData.id;
											listFormDialog = new A.dialog({
		                						width: 850,
		                						height: 870,
		                						title: "停送电查看",
		                						url:format_url('/power/getDetail/' + id),
		                						closed: function(){
		                							powerDatatables.draw(false);
		                						}	
		                					}).render()
// 											A.loadPage({
// 												render : '#page-container',
// 												url : format_url('/power/getDetail/' + id)
// 											});
										}
									}
								}
						]
							}
						}).render();
					
					
					function validform(){
						 /*关键在此增加了一个return，返回的是一个validate对象，这个对象有一个form方法，返回的是是否通过验证*/
						 return $('#overhaulProject').validate({
								debug:true,
								rules: {
										projectName: {
											required: true,
											maxlength: 30,
										},
										projecDetail: {
											required: true,
											maxlength: 30,
										},
										projectReason: {
											maxlength: 60,
										},
										measure:{
											maxlength: 60,
										},
										startDateString: {
											required: true,
										},
										endDateString:{
											required: true
										},
										totalMoney:{
											required: true,
											maxlength: 10,
											number:true
										}
								}
							});
						}

				});
			});
		    
        </script>
    </body>
</html>