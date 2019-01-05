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
					<li class="active">两票管理</li>
						<li class="active">工作票管理</li>
					<li class="active">二级动火工作票</li>
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
							票据信息
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
								<input type="hidden" id="uuidWorkTicket" name="uuidWorkTicket" value="${uuid}"/>
								<input type="hidden" id="remarkResponsibleNameChuan" name="remarkResponsibleName"/>
								<input type="hidden" id="remarkOtherChuan" name="remarkOther"/>
								
								<input type="hidden" id="saveOrSubmit" name="saveOrSubmit"/>
								<input type="hidden" id="selectUser" name="selectUser"/>
								
								<input type="hidden" id="typicalTicketId" name="typicalTicketId"   value="${typicalTicketId}"/>
				
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">编号</label>
									<div class="col-md-4">
											<input class="col-md-12" id="code" name="code" type="text" readonly="readonly" placeholder="" maxlength="64" value="">
								</div>
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>单位名称</label>
								<div class="col-md-4">
										<input class="col-md-12" id="unitNameId" type="hidden" readonly="readonly" name="unitNameId" value="${userEntity.unitId}"></input>
										<input class="col-md-12" id="unitName" type="text" readonly="readonly"  value="${userEntity.unitName}"></input>
								</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>班组</label>
									<div class="col-md-4">
										<select class="col-md-12 chosen-select" id="groupId" name="groupId"></select>
								</div>
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>动火工作负责人</label>
								<div class="col-md-4">
										<input class="col-md-12" id="guarderName" type="text" readonly="readonly" name="guarderName" value="${userEntity.name}"></input>
								</div>
							</div>
								
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>工作班成员</label>
								<div class="col-md-10">
									<textarea id="workClassMember" name="workClassMember" placeholder="名字要用逗号间隔如 1,2,3" style="height:80px; resize:none;" class="col-md-12" maxlength="128" onblur="gotoFindClassMember();"></textarea>
								</div>
							</div>
								
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>工作班人数（包括工作负责人）</label>
								<div class="col-md-4">
											<input class="col-md-12" id="workClassPeople" name="workClassPeople" type="text" readonly="readonly" maxlength="20" value="">
								</div>
								
							</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>工作任务</label>
									<div class="col-md-10">
										<textarea id="content" name="content" placeholder="" style="height:80px; resize:none;" class="col-md-12" maxlength="128"></textarea>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>动火地点</label>
									<div class="col-md-10">
										<textarea id="address" name="address" placeholder="" style="height:80px; resize:none;" class="col-md-12" maxlength="128"></textarea>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>设备编号</label>
									<div class="col-md-4">
										<div id="equipmentCodeWorkOneDiv">
										</div>
									</div>
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>设备名称</label>
									<div class="col-md-4">
												<input class="col-md-12" id="equipmentName" name="equipmentName" type="text" readonly="readonly" maxlength="64" >
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>计划动火开始时间</label>
									<div class="col-md-4">
										<div id="plandateStartDiv"></div>
								</div>
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>计划动火终了时间</label>
								<div class="col-md-4">
										<div id="plandateEndDiv"></div>
								</div>
							</div>
				
<!--     				</form> -->
    			<div class="widget-main no-padding">
                <h5 class="table-title header smaller lighter blue">应断开的断路器(大约125个汉字)</h5>
	 			<table id="workSafe-table" class="table table-striped table-bordered table-hover" style="width:100%;margin-top:54px;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>安全措施</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                </table>
				<h5 class="table-title header smaller lighter blue">应拉开的隔离开关(大约125个汉字)</h5>
	 			<table id="workSafe-table-Two" class="table table-striped table-bordered table-hover" style="width:100%;margin-top:54px;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>安全措施</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                </table>
	 			<h5 class="table-title header smaller lighter blue">应投切的相关电源（空气开关、熔断器、连接片）低压及二次回路(大约125个汉字)</h5>
	 				<table id="workSafe-table-Three" class="table table-striped table-bordered table-hover" style="width:100%;margin-top:54px;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>安全措施</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                	</table>
					</div>
					<h5 class="table-title header smaller lighter blue">应合上的接地刀闸（双重名称或编号）、装设的接地线（装设地点）、应设绝缘挡板(大约125个汉字)</h5>
					<table id="workSafe-table-Four" class="table table-striped table-bordered table-hover" style="width:100%;margin-top:54px;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>安全措施</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                	</table>
					<h5 class="table-title header smaller lighter blue">应设遮拦、应挂标示牌（位置）(大约125个汉字)</h5>
					<table id="workSafe-table-Five" class="table table-striped table-bordered table-hover" style="width:100%;margin-top:54px;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>安全措施</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                	</table>
					<h5 class="table-title header smaller lighter blue">其他安全措施和注意事项(大约125个汉字)</h5>
					<table id="workSafe-table-Six" class="table table-striped table-bordered table-hover" style="width:100%;margin-top:54px;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>安全措施</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                	</table>
                	
                	<h5 class="table-title header smaller lighter blue">动火作业单位应采取的安全措施(大约125个汉字)</h5>
		 			<table id="workSafe-table-Seven" class="table table-striped table-bordered table-hover" style="width:100%;margin-top:54px;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>安全措施</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
	                </table>
	                </form>					
		    		</div>
		    		<!-- 第一个div结束 -->
    			</div>
    		  </div>
    		</div>
    		
    		<div style="margin-right:100px;margin-top: 20px;">
    			<!-- <button class="btn btn-xs btn-danger pull-right" data-dismiss="modal" onclick="gotoQx();">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button> -->
    			<button id="saveBtnTicket" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    			<c:if test="${typicalTicketId==null||typicalTicketId==''}">
    			<button id="submitBtnTicket" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="glyphicon glyphicon-floppy-saved"></i>
    				保存并提交
    			</button>
    			</c:if>
    		</div>
		</div>
		<script type="text/javascript">
			var workSafeOneDialog;
			var userUnitRels = ${userUnitRels};
			//初始数据备份
			var processUserUnitRels = ${userUnitRels};
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','selectbox'], function(A){
					var uuid='${uuid}';
					
				
					var selecttreeFire=new A.selectbox({
						id: 'equipmentFireCode',
						name:'equipmentCode',
						title:'设备台账',
						url:'/equipLedger/selectEquipLedger?userUnitRels='+userUnitRels,
						render:'#equipmentCodeWorkOneDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data){
							var ids=[];
							var nameList='';
							var codeList='';
							
							 for(var i=0; i< data.length;i++){
								 if(ids.length>0){
									 if(!contains(ids,data[i].id)){
										ids.push(data[i].id);
									     codeList += data[i].code+',';
									     nameList += data[i].name+',';
										 
									 }
								}else{
								  ids.push(data[i].id);
								  codeList += data[i].code+',';
								  nameList += data[i].name+',';
									
								}
							 }
							  selecttreeFire.setValue(codeList,codeList);
								$("#equipmentName").val(nameList);
								
								$("#equipmentFireCode").attr("title",codeList);
								$("#equipmentName").attr("title",nameList);
							
						}
					}).render();
					
					//combobx组件
					var groupIdCombobox = new A.combobox({
						render: "#groupId",
						//返回数据待后台返回TODO
						datasource:${groupIdCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					

					
					//日期组件
					var plandateStartDatePicker = new A.my97datepicker({
						id: "plandateStartId",
						name:"plandateStart",
						render:"#plandateStartDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					//日期组件
					var plandateEndDatePicker = new A.my97datepicker({
						id: "plandateEndId",
						name:"plandateEnd",
						render:"#plandateEndDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
		
					$('#workTicketForm').validate({
						rules:  {
							unitNameId:{required:true,maxlength:64},
							groupId:{required:true,maxlength:20},
							guarderName:{required:true,maxlength:64},
							workClassMember:{required:true,maxlength:128},
							workClassPeople:{required:true,maxlength:20},
							equipmentCode:{required:true,maxlength:64},
							equipmentName:{required:true,maxlength:64},
							content:{required:true,maxlength:128},
							address:{required:true,maxlength:128},
							plandateStart:{required:true},
							plandateEnd:{required:true}
							},
						submitHandler: function (form) {
							//添加按钮
							if($("#workClassMember").val()==""){
								alert("工作班成员不能为空！");
								return;
							}
							if($("#content").val()==""){
								alert("工作任务不能为空！");
								return;
							}
							if($("#address").val()==""){
								alert("动火地点不能为空！");
								return;
							}
							if(plandateStartDatePicker.getValue()>plandateEndDatePicker.getValue()){
								alert("计划动火开始时间不能大于计划动火终了时间！");
								return;
							}

							var url = format_url("/workTicketFireTwo/add");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#workTicketForm").serializeObject();
							
							//sml添加开始
							debugger;
							var safecontent1=workSafeOneDatatables.getDatas()[0].signerContent;
							var safecontent2=workSafeTwoDatatables.getDatas()[0].signerContent;
							var safecontent3=workSafeThreeDatatables.getDatas()[0].signerContent;
							var safecontent4=workSafeFourDatatables.getDatas()[0].signerContent;
							var safecontent5=workSafeFiveDatatables.getDatas()[0].signerContent;
							var safecontent6=workSafeSixDatatables.getDatas()[0].signerContent;
							var safecontent7=workSafeSevenDatatables.getDatas()[0].signerContent;
						
// 							if(safecontent1=="无"|| safecontent2=="无"|| safecontent3=="无"|| safecontent4=="无"|| safecontent5=="无"||safecontent6=="无"||safecontent7=="无"){
// 								alert('安全措施信息填写不完整！');
// 								return;
// 							}//结束

							
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									//var workid=result.data.id;
									alert('添加成功');
									window.scrollTo(0,0);
									//这里是典型票点过来的保存，还是第一种票直接的保存，的判断跳转列表
									var typicalTicketId='${typicalTicketId}';
									if(typicalTicketId!=null&&typicalTicketId!=''){
										A.loadPage({
											render : '#page-container',
											url : format_url("/typicalTicket/index")
										});
									}else{
										A.loadPage({
											render : '#page-container',
											url : format_url("/workTicketFireTwo/index")
										});
									}
									
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					
					//下面是安全措施的列表 12
					var conditionsOne=[];
					var workSafeOneDatatables = new A.datatables({
						render: '#workSafe-table',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.length=2147483647;
					            	conditionsOne=[];
					            	conditionsOne.push({
		            					field: 'C_SAFE_TYPE',
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
// 							          {data: "orderSeq",width: "10%",orderable: true},
							          {orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "signerContent",width: "80%",orderable: true},
// 							          {data: "executeSituation",width: "20%",orderable: true}
							          ],
							toolbars: [/* {
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							var info =workSafeOneDatatables._datatables.page.info();
            							var  oneTotal=info.recordsTotal+1;
            								workSafeOneDialog = new A.dialog({
                        						width: 800,
                        						height: 300,
                        						title: "安全措施新增",
                        						url:format_url("/workSafe/getAdd?flag="+1+"&uuid="+uuid+"&total="+oneTotal),
                        						closed: function(){
                        							workSafeOneDatatables.draw(false);
                        						}	
                        					}).render();
            							}
        						}
        					} */],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										workSafeOneDialog = new A.dialog({
											width: 800,
											height: 270,
											title: "安全措施编辑",
											url:format_url("/workSafe/getEdit/"+ id+"/"+1),
											closed: function(){
												workSafeOneDatatables.draw(false);
											}
										}).render();
									}
								}
							}/* , {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/workSafe/'+ id);
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
						} */]
						}
					}).render();
					//下面是安全措施的列表 2
					var conditionsTwo=[];
					var workSafeTwoDatatables = new A.datatables({
						render: '#workSafe-table-Two',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.length=2147483647;
					            	conditionsTwo=[];
					            	conditionsTwo.push({
		            					field: 'C_SAFE_TYPE',
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
// 							          {data: "orderSeq",width: "10%",orderable: true},
							          {orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "signerContent",width: "80%",orderable: true},
// 							          {data: "executeSituation",width: "20%",orderable: true}
							          ],
							toolbars: [/* {
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							var info =workSafeTwoDatatables._datatables.page.info();
            							var  twoTotal=info.recordsTotal+1;
            								workSafeOneDialog = new A.dialog({
                        						width: 800,
                        						height: 300,
                        						title: "安全措施新增",
                        						url:format_url("/workSafe/getAdd?flag="+2+"&uuid="+uuid+"&total="+twoTotal ),
                        						closed: function(){
                        							workSafeTwoDatatables.draw(false);
                        						}	
                        					}).render();
            						}
        						}
        					} */],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										workSafeOneDialog = new A.dialog({
											width: 800,
											height: 270,
											title: "安全措施编辑",
											url:format_url("/workSafe/getEdit/"+ id+"/"+2),
											closed: function(){
												workSafeTwoDatatables.draw(false);
											}
										}).render();
									}
								}
							}/* , {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/workSafe/'+ id);
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
						} */]
						}
					}).render();
					//下面是安全措施的列表 3
					var conditionsThree=[];
					var workSafeThreeDatatables = new A.datatables({
						render: '#workSafe-table-Three',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.length=2147483647;
					            	conditionsThree=[];
					            	conditionsThree.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:3
		            				});
					            	conditionsThree.push({
		            					field: 'C_UUID_CODE',
		            					fieldType:'STRING',
		            					matchType:'EQ',
		            					value:uuid
		            				});
					            	d.conditions = conditionsThree;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							paging:false,
							bInfo:false,
							columns: [{data:"id", visible:false,orderable:false}, 
// 							          {data: "orderSeq",width: "10%",orderable: true},
							          {orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "signerContent",width: "80%",orderable: true},
// 							          {data: "executeSituation",width: "20%",orderable: true}
							          ],
							toolbars: [/* {
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            								var info =workSafeThreeDatatables._datatables.page.info();
            								var  threeTotal=info.recordsTotal+1;
            								workSafeOneDialog = new A.dialog({
                        						width: 800,
                        						height: 300,
                        						title: "安全措施新增",
                        						url:format_url("/workSafe/getAdd?flag="+3+"&uuid="+uuid+"&total="+threeTotal ),
                        						closed: function(){
                        							workSafeThreeDatatables.draw(false);
                        						}	
                        					}).render();
            						}
        						}
        					} */],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										workSafeOneDialog = new A.dialog({
											width: 800,
											height: 270,
											title: "安全措施编辑",
											url:format_url("/workSafe/getEdit/"+ id+"/"+3),
											closed: function(){
												workSafeThreeDatatables.draw(false);
											}
										}).render();
									}
								}
							}/* , {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/workSafe/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											workSafeThreeDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						} */]
						}
					}).render();
					//下面是安全措施的列表 4
					var conditionsFour=[];
					var workSafeFourDatatables = new A.datatables({
						render: '#workSafe-table-Four',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditionsFour=[];
					            	d.length=2147483647;
					            	conditionsFour.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:4
		            				});
					            	conditionsFour.push({
		            					field: 'C_UUID_CODE',
		            					fieldType:'STRING',
		            					matchType:'EQ',
		            					value:uuid
		            				});
					            	d.conditions = conditionsFour;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							paging:false,
							checked: false,
							bInfo:false,
							aLengthMenu: [5],
							columns: [{data:"id", visible:false,orderable:false}, 
// 							          {data: "orderSeq",width: "10%",orderable: true},
							          {orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "signerContent",width: "80%",orderable: true},
// 							          {data: "executeSituation",width: "20%",orderable: true}
							          ],
							toolbars: [/* {
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            								var info =workSafeFourDatatables._datatables.page.info();
            								var  fourTotal=info.recordsTotal+1;
            								workSafeOneDialog = new A.dialog({
                        						width: 800,
                        						height: 300,
                        						title: "安全措施新增",
                        						url:format_url("/workSafe/getAdd?flag="+4+"&uuid="+uuid+"&total="+fourTotal ),
                        						closed: function(){
                        							workSafeFourDatatables.draw(false);
                        						}	
                        					}).render();
            						}
        						}
        					} */],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										workSafeOneDialog = new A.dialog({
											width: 800,
											height: 270,
											title: "安全措施编辑",
											url:format_url("/workSafe/getEdit/"+ id+"/"+4),
											closed: function(){
												workSafeFourDatatables.draw(false);
											}
										}).render();
									}
								}
							}/* , {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/workSafe/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											workSafeFourDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						} */]
						}
					}).render();
					//下面是安全措施的列表 5
					var conditionsFive =[];
					var workSafeFiveDatatables = new A.datatables({
						render: '#workSafe-table-Five',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.length=2147483647;
					            	conditionsFive=[];
					            	conditionsFive.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:5
		            				});
					            	conditionsFive.push({
		            					field: 'C_UUID_CODE',
		            					fieldType:'STRING',
		            					matchType:'EQ',
		            					value:uuid
		            				});
					            	d.conditions = conditionsFive;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							paging:false,
							bInfo:false,
							columns: [{data:"id", visible:false,orderable:false}, 
// 							          {data: "orderSeq",width: "10%",orderable: true},
							          {orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "signerContent",width: "80%",orderable: true},
// 							          {data: "executeSituation",width: "20%",orderable: true}
							          ],
							toolbars: [/* {
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            								var info =workSafeFiveDatatables._datatables.page.info();
            								var  fiveTotal=info.recordsTotal+1;
            								workSafeOneDialog = new A.dialog({
                        						width: 800,
                        						height: 300,
                        						title: "安全措施新增",
                        						url:format_url("/workSafe/getAdd?flag="+5+"&uuid="+uuid+"&total="+fiveTotal ),
                        						closed: function(){
                        							workSafeFiveDatatables.draw(false);
                        						}	
                        					}).render();
            						}
        						}
        					} */],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										workSafeOneDialog = new A.dialog({
											width: 800,
											height: 270,
											title: "安全措施编辑",
											url:format_url("/workSafe/getEdit/"+ id+"/"+5),
											closed: function(){
												workSafeFiveDatatables.draw(false);
											}
										}).render();
									}
								}
							}/* , {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/workSafe/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											workSafeFiveDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						} */]
						}
					}).render();
					//下面是安全措施的列表 6
					var conditionsSix=[];
					var workSafeSixDatatables = new A.datatables({
						render: '#workSafe-table-Six',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditionsSix=[];
					            	d.length=2147483647;
					            	conditionsSix.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:6
		            				});
					            	conditionsSix.push({
		            					field: 'C_UUID_CODE',
		            					fieldType:'STRING',
		            					matchType:'EQ',
		            					value:uuid
		            				});
					            	d.conditions = conditionsSix;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							paging:false,
							bInfo:false,
							columns: [{data:"id", visible:false,orderable:false}, 
// 							          {data: "orderSeq",width: "10%",orderable: true},
							          {orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "signerContent",width: "80%",orderable: true},
// 							          {data: "executeSituation",width: "20%",orderable: true}
							          ],
							toolbars: [/* {
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            								var info =workSafeSixDatatables._datatables.page.info();
            								var  sixTotal=info.recordsTotal+1;
            								workSafeOneDialog = new A.dialog({
                        						width: 800,
                        						height: 300,
                        						title: "安全措施新增",
                        						url:format_url("/workSafe/getAdd?flag="+6+"&uuid="+uuid+"&total="+sixTotal ),
                        						closed: function(){
                        							workSafeSixDatatables.draw(false);
                        						}	
                        					}).render();
            						}
        						}
        					} */],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										workSafeOneDialog = new A.dialog({
											width: 800,
											height: 270,
											title: "安全措施编辑",
											url:format_url("/workSafe/getEdit/"+ id+"/"+6),
											closed: function(){
												workSafeSixDatatables.draw(false);
											}
										}).render();
									}
								}
							}/* , {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/workSafe/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											workSafeSixDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						} */]
						}
					}).render();
					//下面是安全措施的列表 1
					var workSafeSevenDatatables="";
					var conditionsSeven=[];
					workSafeSevenDatatables = new A.datatables({
						render: '#workSafe-table-Seven',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditionsSeven=[];
					            	d.length=2147483647;
					            	conditionsSeven.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:12
		            				});
					            	conditionsSeven.push({
		            					field: 'C_UUID_CODE',
		            					fieldType:'STRING',
		            					matchType:'EQ',
		            					value:uuid
		            				});
					            	d.conditions = conditionsSeven;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							optWidth: 80,
							aLengthMenu: [5],
							columns: [{data:"id", visible:false,orderable:false}, 
// 							          {data: "orderSeq",width: "10%",orderable: true},
							          {orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "signerContent",width: "80%",orderable: true}
// 							          {data: "executeSituation",width: "20%",orderable: true}
							          ],
							toolbars: [/* {
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							var info =workSafeSevenDatatables._datatables.page.info();
        								var  sevenTotal=info.recordsTotal+1;
            								workSafeOneDialog = new A.dialog({
                        						width: 800,
                        						height: 300,
                        						title: "安全措施新增",
                        						url:format_url("/workSafe/getAdd?flag="+12+"&uuid="+uuid+"&total="+sevenTotal),
                        						closed: function(){
                        							workSafeSevenDatatables.draw(false);
                        						}	
                        					}).render();
            							}
        						}
        					} */],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										workSafeOneDialog = new A.dialog({
											width: 800,
											height: 270,
											title: "安全措施编辑",
											url:format_url("/workSafe/getEdit/"+ id+"/"+12),
											closed: function(){
												workSafeSevenDatatables.draw(false);
											}
										}).render();
									}
								}
							}/* , {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/workSafe/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											workSafeSevenDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						} */]
						}
					}).render();
					
					//安全措施结束
					$("#saveBtnTicket").on("click", function(){
						
						//保存的时候把备注的字段赋值
						$("#remarkResponsibleNameChuan").val($("#remarkResponsibleName").val());
						$("#remarkOtherChuan").val($("#remarkOther").val());
						$("#selectUser").val("");
						$("#saveOrSubmit").val("");
						$("#workTicketForm").submit();
    				});
					//保存并提交
					$("#submitBtnTicket").on("click", function(){
				
						workticketDialog = new A.dialog({
							title:"提交确认",
							url:format_url("/workTicketFireTwo/sureSubmit"),
							height:450,
							width:500
						}).render();
						
						
    				});
					
					 
					$('#btnBackTicket').on('click',function(){
						var typicalTicketId='${typicalTicketId}';
						if(typicalTicketId!=null&&typicalTicketId!=''){
							A.loadPage({
								render : '#page-container',
								url : format_url("/typicalTicket/index")
							});
						}else{
							A.loadPage({
								render : '#page-container',
								url : format_url("/workTicketFireTwo/index?uuid="+uuid)
							});
						}
					});
					
				});
			});
			
			
			function gotoFindClassMember(){
				var classMember=$("#workClassMember").val();
				var count=0;
				var date = classMember.replace(/，/g,",").split(",");
				for(var i=0;i<date.length;i++){
					if(date[i]!=""){
						count=count+1;
					}
				}
				count=count+1;
				$("#workClassPeople").val(count);
			}
			function gotoQx(){
				var typicalTicketId='${typicalTicketId}';
				if(typicalTicketId!=null&&typicalTicketId!=''){
					A.loadPage({
						render : '#page-container',
						url : format_url("/typicalTicket/index")
					});
				}else{
					var uuid=$("#uuidWorkTicket").val();
					 $("#page-container").load(format_url("/workTicketFireTwo/index?uuid="+uuid));
				}
				 
			}
			
			
			function goBackToSubmitPerson(id,selectUser){

				//保存的时候把备注的字段赋值
				$("#remarkResponsibleNameChuan").val($("#remarkResponsibleName").val());
				$("#remarkOtherChuan").val($("#remarkOther").val());
				//给审批人  和提交标识 赋值
				$("#selectUser").val(selectUser);
				$("#saveOrSubmit").val("submit");

				$("#workTicketForm").submit();
			}
			
			
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