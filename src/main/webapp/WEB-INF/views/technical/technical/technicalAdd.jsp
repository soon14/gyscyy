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
					<li class="active">技术监督</li>
					<li class="active">计划</li>
					<li class="active">新增</li>
				</ul>
		</div>
		<div class="col-md-12" >
		<div class="page-content">
		<div class="tabbable" style="margin-top: 20px;">
		 		<ul class="nav nav-tabs" id="myTab">
		 			<li class="active">
			 			<a   data-toggle="tab" href="#workitem" aria-expanded="true">
							<i class="green ace-icon fa fa-home bigger-120"></i>
							基础信息
						</a>
		 			</li>
		 		</ul>
		 		<div style="float:right; margin-top:-35px;margin-right:55px;">
					<button id="btnBackTicket" class="btn btn-xs btn-primary">
						<i class="fa fa-reply"></i>
						返回
					</button>
				</div>	
				<div class="tab-content">
				<!-- 第一个div开始 -->
				<div id="workitem" class="tab-pane fade active in">
				<form class="form-horizontal" role="form" style="margin-right:100px;" id="workTicketForm">
								<input type="hidden" id="uuid" name="uuid" value="${uuid}"/>
								<input type="hidden" id="saveOrSubmit" name="saveOrSubmit"/>
								<input type="hidden" id="selectUser" name="selectUser"/>
				
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>单位名称</label>
									<div class="col-md-4">
										<div id="searchunitName"></div>
								    </div>
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>填报人</label>
									<div class="col-md-4">
									<input class="col-md-12" id="fillName" readonly="readonly" name="fillName" type="text" placeholder="" maxlength="20" value="${userEntity.name }">
										<input class="col-md-12" id="fillId" name="fillId" type="hidden" placeholder="" maxlength="20" value="${userEntity.id }">
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>时间</label>
									<div class="col-md-4">
										<div id="plandateStartDiv"></div>
								</div>
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>计划名称</label>
									<div class="col-md-4">
										<input type="text" id="planName" name="planName" maxlength="64" class="col-md-12"></input>
								</div>
							    </div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">备注</label>
									<div class="col-md-10">
										<textarea id="remark" name="remark" placeholder="" style="height:80px; resize:none;" class="col-md-12" maxlength="128"></textarea>
									</div>
								</div>
								
								<div class="form-group form-horizontal">
									<label class="col-md-2 control-label no-padding-right">附件</label>
									<div class="col-md-10" id="divfile">
									</div>
								</div>
					</form>		
    			<div class="widget-main no-padding">
				<h5 class="table-title header smaller lighter blue">定期工作</h5>
	 			<table id="workSafe-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>工作项目</th>
	                                <th>负责人</th>
	                                <th>计划缘由</th>
	                                <th>监督专业</th>
	                                <th>计划时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                </table>
				<h5 class="table-title header smaller lighter blue">技改工作</h5>
	 			<table id="workSafe-table-Two" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>工作项目</th>
	                                <th>负责人</th>
	                                <th>计划缘由</th>
	                                <th>监督专业</th>
	                                <th>计划时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                </table>
                </div>
				
		    	</div>
		    		<!-- 第一个div结束 -->
    		</div>
    	</div>
    </div>
    		<div style="margin-right:10px;margin-top: 20px;">
    			<button id="saveBtnTicket" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    			<button id="submitBtnTicket" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="glyphicon glyphicon-floppy-saved"></i>
    				保存并提交
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			var workSafeOneDialog;
			var workSafeTwoDialog;
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','selectbox','uploaddropzone'], function(A){
					
					var uuid='${uuid}';
					
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					
					var unitNameCombotree = new A.combotree({
						render: "#searchunitName",
						name: 'unitId',
						//返回数据待后台返回TODO
						datasource: ${unitNameIdTreeList},
						width:"210px",
						options: {
							treeId: 'unitNameId',
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
						}
					}).render();
					unitNameCombotree.setValue('${userEntity.unitId}');
					
					
					
					//日期组件
					var plandateStartDatePicker = new A.my97datepicker({
						id: "plandateStartId",
						name:"time",
						render:"#plandateStartDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy"
						}
					}).render();
					
					
					$('#workTicketForm').validate({
						rules:  {
							unitId:{required:true,maxlength:20},
							fillId:{required:true,maxlength:20},
							jdzyId:{required:true,maxlength:20},
							time:{required:true,maxlength:128},
							planName:{required:true,maxlength:128},
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/technical");
							var obj = $("#workTicketForm").serializeObject();
							    obj.fileid=JSON.stringify(uploaddropzone.getValue());
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('添加成功');
									window.scrollTo(0,0);
										A.loadPage({
											render : '#page-container',
											url : format_url("/technical/index")
										});
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					
					//安全措施 开始
					//下面是安全措施的列表 1
					var conditionsOne=[];
					var workSafeOneDatatables = new A.datatables({
						render: '#workSafe-table',
						options: {
					        "ajax": {
					            "url": format_url("/technicalWork/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.length=2147483647;
					            	conditionsOne=[];
					            	conditionsOne.push({
		            					field: 'C_WORK_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:1
		            				});
					            	conditionsOne.push({
		            					field: 'C_UUID_CODE',
		            					fieldType:'STRING',
		            					matchType:'EQ',
		            					value:uuid
		            				});
					            	d.conditions = conditionsOne;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							paging:false,
							bInfo:false,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "orderSqe",width: "auto",orderable: true},
							          {data: "content",width: "auto",orderable: true},
 							          {data: "picName",width: "auto",orderable: true},
 							          {data: "danger",width: "auto",orderable: true},
 							          {data: "jdzyName",width: "auto",orderable: true},
 							          {data: "time",width: "auto",orderable: true},
							          ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							var info =workSafeOneDatatables._datatables.page.info();
            							var  oneTotal=info.recordsTotal+1;
            								workSafeOneDialog = new A.dialog({
                        						width: 1000,
                        						height: 400,
                        						title: "定期工作新增",
                        						url:format_url("/technicalWork/getAdd?flag="+1+"&uuid="+uuid+"&total="+oneTotal),
                        						closed: function(){
                        							workSafeOneDatatables.draw(false);
                        						}	
                        					}).render();
            							}
        						}
        					}],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										workSafeOneDialog = new A.dialog({
											width: 1000,
											height: 400,
											title: "安全措施编辑",
											url:format_url("/technicalWork/getEdit/"+ id+"/"+1),
											closed: function(){
												workSafeOneDatatables.draw(false);
											}
										}).render();
									}
								}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/technicalWork/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											workSafeOneDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						}
						]
						}
					}).render();
					//下面是安全措施的列表 2
					var conditionsTwo=[];
					var workSafeTwoDatatables = new A.datatables({
						render: '#workSafe-table-Two',
						options: {
					        "ajax": {
					            "url": format_url("/technicalWork/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.length=2147483647;
					            	conditionsTwo=[];
					            	conditionsTwo.push({
		            					field: 'C_WORK_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:2
		            				});
					            	conditionsTwo.push({
		            					field: 'C_UUID_CODE',
		            					fieldType:'STRING',
		            					matchType:'EQ',
		            					value:uuid
		            				});
					            	d.conditions = conditionsTwo;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							paging:false,
							bInfo:false,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "orderSqe",width: "auto",orderable: true},
							          {data: "content",width: "auto",orderable: true},
 							          {data: "picName",width: "auto",orderable: true},
 							          {data: "danger",width: "auto",orderable: true},
 							         {data: "jdzyName",width: "auto",orderable: true},
 							         {data: "time",width: "auto",orderable: true},
							          ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							var info =workSafeTwoDatatables._datatables.page.info();
            							var  twoTotal=info.recordsTotal+1;
            								workSafeOneDialog = new A.dialog({
                        						width: 1000,
                        						height: 400,
                        						title: "安全措施新增",
                        						url:format_url("/technicalWork/getAdd?flag="+2+"&uuid="+uuid+"&total="+twoTotal ),
                        						closed: function(){
                        							workSafeTwoDatatables.draw(false);
                        						}	
                        					}).render();
            						}
        						}
        					}],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										workSafeOneDialog = new A.dialog({
											width: 1000,
											height: 400,
											title: "安全措施编辑",
											url:format_url("/technicalWork/getEdit/"+ id+"/"+2),
											closed: function(){
												workSafeTwoDatatables.draw(false);
											}
										}).render();
									}
								}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/technicalWork/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											workSafeTwoDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						}
						]
						}
					}).render();
					
					
					//安全措施结束
					$("#saveBtnTicket").on("click", function(){
						var unitId=unitNameCombotree.getValue();
						var jdzyId=$("#jdzyId").val();
						var time=$("#plandateStartId").val();
						$.ajax({
							url: format_url("/technical/saveValidate?unitId="+unitId+"&jdzyId="+jdzyId+"&time="+time+"&uuid="+uuid),
							contentType : 'application/json',
							dataType : 'JSON',
							type: 'POST',
							success: function(result){
								if(result.result == 'success'){
									$("#selectUser").val("");
									$("#saveOrSubmit").val("");
									$("#workTicketForm").submit();
								} else{
									alert(result.errorMsg);
								}
							}
						});
						
    				});
					//保存并提交
					$("#submitBtnTicket").on("click", function(){
						var unitId=unitNameCombotree.getValue();
						var jdzyId=$("#jdzyId").val();
						var time=$("#plandateStartId").val();
						$.ajax({
							url: format_url("/technical/saveValidate?unitId="+unitId+"&jdzyId="+jdzyId+"&time="+time+"&uuid="+uuid),
							contentType : 'application/json',
							dataType : 'JSON',
							type: 'POST',
							success: function(result){
								if(result.result == 'success'){
										workticketDialog = new A.dialog({
											title:"提交确认",
											url:format_url("/technical/sureSubmit"),
											height:400,
											width:500
										}).render();
								} else{
									alert(result.errorMsg);
								}
							}
						});
    				});
					$('#btnBackTicket').on('click',function(){
							A.loadPage({
								render : '#page-container',
								url : format_url("/technical/index?uuid="+uuid)
							});
					});
					
				});
			});
			function goBackToSubmitPerson(id,selectUser){//回调函数
				//给审批人  和提交标识 赋值
				$("#selectUser").val(selectUser);
				$("#saveOrSubmit").val("submit");
				$("#workTicketForm").submit();
			}
        </script>
    </body>
</html>