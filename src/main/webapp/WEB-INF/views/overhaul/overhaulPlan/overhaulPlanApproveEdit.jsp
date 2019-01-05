<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>  
<%@ page import="com.aptech.business.component.dictionary.OverhaulPlanStatusEnum"%>
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>

	<div class="page-content" style="height:730px;overflow:auto;">
		<div class="widget-main no-padding">
				<form class="form-horizontal" role="form"   id="editFormAudit">
				   <input class="col-md-12" id="id1" name="id" type="hidden" value="${entity.id }">
				   <input id="auditBtn" value="${entity.auditBtn }" type="hidden" name="auditBtn"/>
	            </form>
	       </div>
	    <div class="col-xs-12">	
	    <h5 class="table-title header smaller lighter blue " style="margin-bottom:0px;">选择检修项目</h5>
		<div class="widget-main no-padding">
			<form id="overhaulProjectAudit"  onsubmit="return false;">
				<table id="overhaulProject-table1"  class="table table-striped table-bordered table-hover" style="width:100%;">
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
                       		<th><span style="color:red;">*</span>计划总投资(万元)</th>
                          	<th>操作</th>
						</tr>
					</thead>
				</table>
			</form>
		</div>
		</div>
		  <div class="col-xs-12">	
		  	<h5 class="table-title header smaller lighter blue " style="margin-bottom:0px;margin-top:40px;">选择审批人</h5>
		  			<div class="widget-main no-padding">
 					<div class="form-group " id="userlistdiv" style="margin-top:70px;">
							<select multiple="multiple" size="10" name="userList" id="duallist" >
								<c:forEach items="${userList}" var="userList" varStatus="vs">
									<option value="${userList.loginName}">${userList.name}</option>
								</c:forEach>
								<!-- 
								<option value="option6" selected="selected">Option 6</option>
								 -->
							</select>
							<div class="hr hr-16 hr-dotted"></div>
				     </div>
				     </div>
			</div>
			<div class="col-md-12">
    		<div style="margin-top: 20px;" id="" >
        		<button id="returnBtn" class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" >
        			<i class="glyphicon glyphicon-remove"></i>
        			关闭
        		</button>
        		<button  id="commitBtn"   title="" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-ok"></i>
		    		提交
		    	</button>
        	</div>
        </div>
</div>
        
		<script type="text/javascript">
		var listFormDialog;
		jQuery(function($) {
			seajs.use(['combobox','combotree','my97datepicker','selectbox','uploaddropzone','duallistbox'], function(A){
				var delProjectIds =[];
				var demo1 = $('select[name="userList"]').bootstrapDualListbox({infoTextFiltered: '<span class="label label-purple label-lg">Filtered</span>'});
				var container1 = demo1.bootstrapDualListbox('getContainer');
				container1.find('.btn').addClass('btn-white btn-info btn-bold');
				$('#editFormAudit').validate({
					debug:true,
					rules:  {},
					    submitHandler: function (form) {
					    var id = $("#id1").val();
						var url = format_url("/overhaulPlan/saveOverhaulPlan");
						var obj = $("#editFormAudit").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						var selectUser=$('[name="userList"]').val();
						obj.userList = selectUser.join(",");
						var projectobj = $("#overhaulProjectAudit").serializeObject();
						var resultList= [];
						for(var key in projectobj){
							var keylist = [];
							var tablelength = $("#overhaulProject-table1").find("tr").length;
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
						$.ajax({
							url : url,
							contentType : 'application/json',
							dataType : 'JSON',
							data : JSON.stringify(obj),
							cache: false,
							type : 'POST',
							success: function(result){
								if(result.result == "success"){
									 alert('提交成功');
									 listFormDialog.close();
										A.loadPage({
											render : '#page-container',
											url : format_url("/todoTask/list/1/10")
										});
								}else{
									alert(result.errorMsg);
								}
							
							},
							error:function(v,n){
								alert('提交失败');
							}
						});
						
					}
				});
				
				var conditions=[];
				overhaulProjectDatatables1 = new A.datatables({
					render: '#overhaulProject-table1',
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
					 {data: "projectName",width: "10%",orderable: false, editType:"input"}, 
					 {data: "projecDetail",width: "10%",orderable: false, editType:"input"}, 
					 {data: "projectReason",width: "18%",orderable: false, editType:"input"}, 
					 {data: "measure",width: "23%",orderable: false, editType:"input"}, 
					 {data: "startDate",width: "10%",orderable: false,editType:"my97datepicker", cfg:{
							name: 'startDateString',
							options : {
									isShowWeek : false,
									skin : 'ext',
									maxDate: "",
									minDate: "",
									dateFmt: 'yyyy-MM-dd'
							},
					 
						}},
					 {data: "endDate",width: "10%",orderable: false, editType:"my97datepicker", cfg:{
							name: 'endDateString',
							options : {
									isShowWeek : false,
									skin : 'ext',
									maxDate: "",
									minDate: "",
									dateFmt: 'yyyy-MM-dd'
							}
						}},
					 {data: "totalMoney",width: "6%",orderable: false, 
							render : function(data, type, row, meta) {
				                   return "<input name='totalMoney' type='text' value='"+data+"'/>"+"<input name='id' value='"+row.id+"' type='hidden'/>";  
				      }
				               	
					 }
					 ],
					toolbars: [{
						label:"新增",
						icon:"glyphicon glyphicon-plus",
						className:"btn-success",
						events:{
    						click:function(event){
    							overhaulProjectDatatables1.addRow({"id":0, "projectName":"", "projecDetail":"","projectReason":"", "measure":"", "startDate":null, "endDate":null, "totalMoney":""});
    						}
						}
					}],
					optWidth:50,
					btns:[
						{
							id:"delete",
							label:"删除",
							icon: "fa fa-trash-o bigger-130",
							className: "red del",
							render: function(btnNode, data){
								var id=data.id;
								if(id!=null && id!=0){
									btnNode.hide();
								}
							},
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									if(id!=null && id!=0){
										delProjectIds.push(id);
									}
									A.confirm('您确认删除么？',function(){
										overhaulProjectDatatables1.deleteSelectRow(nRow);
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
							overhaulProjectDatatables1.addRows(result.data);
						}
						
					})
				};
				initDataTable();
				
				function validform1(){
					 /*关键在此增加了一个return，返回的是一个validate对象，这个对象有一个form方法，返回的是是否通过验证*/
					 return $('#overhaulProjectAudit').validate({
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
				//全部按钮
				$("#commitBtn").on("click", function(e){
					var selectUser=$('[name="userList"]').val();
					if(selectUser==null || selectUser=="" ||selectUser==[]){
						alert("请选择审批人");
						return;
					}
					if(validform1().form()){
						var projectobj = $("#overhaulProjectAudit").serializeObject();
						for(var key in projectobj){
							$("#editFormAudit").submit();
 							return;
						}
						alert("检修项目不能为空");
					}
				});
				$("#returnBtn").on("click", function(e){
					listFormDialog.close();
				});
			});
		});
        </script>
    </body>
</html>