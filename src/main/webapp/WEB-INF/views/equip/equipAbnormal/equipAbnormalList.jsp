<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
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
					设备管理
				</li>
				<li class="active">设备异动管理</li>
				<li class="active">设备异动申请</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content ">
            <div class="col-lg-12 col-md-12 col-sm-12  padding-zero search-content">
				<div class="form-inline text-right" role="form">
                    <div class="clearfix" style="width: 95%">
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
                            <label class="" for="equipAbnormalBill">设备异动单号：</label>
                            <input id="equipAbnormalBill" class="input-width text-left" placeholder="请输入设备异动单号" type="text">
                        </div>
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
                            <label class="" for="equipmentId">设备编号：</label>
                            <div id="equipAbnormalDiv" class="input-width  text-left padding-zero"></div>
                        </div>
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
                            <label class="" for="unitId">单位所属：</label>
                            <div id="unitId" class="input-width text-left padding-zero"></div>
                        </div>
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 pull-right" >
                            <label class="" for="applyUserId">申请人：</label>
                            <div class="padding-zero input-width  text-left">
                                <select id="applyUserId" class="form-control chosen-select"></select>
                            </div>
                        </div>
                    </div>
					<div class="clearfix" style="width: 95%">
                        <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
                            <label class="">计划开始时间：</label>
                            <div class="form-group date-input padding-zero text-left">
                                <div id="planBeginDateBeginDiv"  style="border:none; padding:0px;"></div>
                            </div>
                            <label style="width: 2.6%;text-align: center">~</label>
                            <div class="form-group date-input padding-zero text-left">
                                <div id="planBeginDateEndDiv"   style="border:none; padding:0px;"></div>
                            </div>
                        </div>
                        <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
                            <label class="">计划结束时间：</label>
                            <div class="form-group date-input padding-zero text-left">
                                <div id="planEndDateBeginDiv"  style="border:none; padding:0px;"></div>
                            </div>
                            <label style="width: 2.6%;text-align: center">~</label>
                            <div class="form-group date-input padding-zero text-left">
                                <div id="planEndDateEndDiv"   style="border:none; padding:0px;"></div>
                            </div>
                        </div>
                    </div>
                      <div class="clearfix" style="width: 95%">
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
                              <label  for="processStatus">审批状态：</label>
							<div class="padding-zero input-width  text-left">
                                 <select id="processStatus"  class="form-control chosen-select"></select>
                            </div>
                        </div>
                        <div class="form-group col-lg-7 col-md-7 col-sm-6 col-xs-12">
                             <label for="abnormalDesc">设备异动说明：</label>
                                <input id="abnormalDesc"  class="input-width padding-zero derect-input  text-left" placeholder="请输入设备异动说明" type="text">
                        </div>
                       
                    </div>
                     <div class="clearfix" >
						 <div class="form-group col-lg-2 col-md-2 col-sm-12 col-xs-12" style="float: right;">
                            <button id="btnSearch" class="btn btn-xs btn-primary" style="overflow: hidden;width: 48%;max-width: 54px">
                                <i class="glyphicon glyphicon-search"></i>
                               		 查询
                            </button>
                            <button id="btnReset" class="btn btn-xs btn-primary"style="overflow: hidden;width: 48%;max-width: 54px;margin-right: -13px" type="button" >
                                <i class="glyphicon glyphicon-repeat"></i>
                                	重置
                            </button>
                        </div>
				   </div>
                </div>
            </div>
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' >设备异动</h5>
						<table id="equipAbnormal-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>设备异动单号</th>
	                                <th>设备编号</th>
	                                <th>设备名称</th>
	                                <th>单位所属</th>
	                                <th>计划开始时间</th>
	                                <th>计划结束时间</th>
	                                <th>申请人</th>
	                                <th>设备异动说明</th>
	                                <th>审批状态</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
          </div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
		var processStatus;
		var answer = ${answer};
		var userUnitRels = ${userUnitRels};
		//初始数据备份
		var processUserUnitRels = ${userUnitRels};
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker','selectbox'], function(A){
					var conditions=[];
					//设备编号
					selectEquipLedger = new A.selectbox({
						id: 'equipmentCode',
						name:'equipmentCode',
						title:'设备台账',
						url:'/equipLedger/selectEquipLedger',
						render:'#equipAbnormalDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data,self){
							idList = "";
							nameList = "";
							codeList = "";
							for(var i=0; i< data.length;i++){
								codeList += data[i].code+',';
 								nameList += data[i].name+',';
 								idList += data[i].id+',';
						    }
							selectEquipLedger.setValue(codeList,codeList);
							if(data&&data[0]){
								$("#equipmentCode").val(codeList);
							};
						}
					}).render();
					//单位名称
					var unitNameTreeListCombobox = new A.combotree({
						render: "#unitId",
						name: 'unitId',
						datasource: ${unitNameIdTreeList},
						width:"230px",
						expand:false,
						multiple:false,
						options: {
							treeId: 'funCodeTree',
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
					//申请人
					var applyUserIdCombobox = new A.combobox({
						render: "#applyUserId",
						datasource:${applyUserId},
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//开始时间
					var searchBeginStartfindTime = new A.my97datepicker({
						id: 'planBeginDateBegin',
						name:'planBeginDate',
						render:'#planBeginDateBeginDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'planBeginDateEnd\\')}",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					//结束时间
					var searchBeginEndtfindTime = new A.my97datepicker({
						id: 'planBeginDateEnd',
						name:'planBeginDate',
						render:'#planBeginDateEndDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'planBeginDateBegin\\')}",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					//结束时间
					var searchEndStartfindTime = new A.my97datepicker({
						id: 'planEndDateBegin',
						name:'planEndDate',
						render:'#planEndDateBeginDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					//结束时间
					var searchEndEndfindTime = new A.my97datepicker({
						id: 'planEndDateEnd',
						name:'planEndDate',
						render:'#planEndDateEndDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'planEndDateBegin\\')}",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					//状态
					var processStatusNameCombobox = new A.combobox({
						render: "#processStatus",
						datasource:${processStatus},
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					var equipAbnormalDatatables = new A.datatables({
						render: '#equipAbnormal-table',
						options: {
					        "ajax": {
					            "url": format_url("/equipAbnormal/list"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	if(d.search.value){
					            		d.conditions=JSON.parse(d.search.value);
					            		if(d.conditions){
						            		for(var index in d.conditions){
							            			$("#"+d.conditions[index].name).val(d.conditions[index].value);
							            			$("#"+d.conditions[index].name).trigger("chosen:updated");
						            		}
					            		}
					            	}
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 100,
							bStateSave: true,
							searching: true,
							iCookieDuration:cookieTime,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "equipAbnormalBill",width: "200px",orderable: true}, 
							          {data: "equipmentId",width: "150px",orderable: false,
							        	  render : function(data, type, row, meta) {
												if(data!=null && data.length>20){
													return "<div title='"+data+"'>"+data.substring(0,20)+"..."+"</div>";  
												}else{
													return data;  
												}
									  	  }
							          }, 
							          {data: "equipName",width: "200px",orderable: false,
							        	  render : function(data, type, row, meta) {
												if(data!=null && data.length>20){
													return "<div title='"+data+"'>"+data.substring(0,20)+"..."+"</div>";  
												}else{
													return data;  
												}
									  	  }
							          }, 
							          {data: "unitName",width: "100px",orderable: true},
							          {data: "planBeginDate",width: "110px",orderable: true,"sClass": "center"}, 
							          {data: "planEndDate",width: "110px",orderable: true,"sClass": "center"}, 
							          {data: "applyUserName",width: "50px",orderable: true}, 
							          {data: "abnormalDesc",width: "200px",orderable: false,
							        	  render : function(data, type, row, meta) {
												if(data!=null && data.length>20){
													return "<div title='"+data+"'>"+data.substring(0,20)+"..."+"</div>";  
												}else{
													return data;  
												}
									  	  }
							          }, 
							          {data: "processStatusName",name:"processStatus",width: "200px",orderable: true}],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
									click:function(event){
										if(answer!=false){
											A.loadPage({
			    	    						render : '#page-container',
			    	    						url : format_url("/equipAbnormal/getAdd")
			    	    					});	
										}else{
											alert("只有检修、集控中心专工及以上人员可以申请！");
											return;
										}
										
		    						}
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = equipAbnormalDatatables.getSelectRowDatas();
										var ids = [];
										var processStatus = new Array();
										var checkids =[];
										var userIds = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
												userIds.push(data[i].applyUserId);
												processStatus.push(data[i].processStatus);
												if(data[i].processStatus!=1){
													checkids.push(data[i].id);
												}
											}
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										var applyUserId = ${userId};
										var applyLoginName = "${userLoginName}";
										for(var j=0;j<userIds.length;j++){
											if(userIds[j]!=applyUserId&&applyLoginName!="super"){
												alert('记录中包含不是当前登陆人的记录不能删除!');
												return;
											}
										}
										var url = format_url('/equipAbnormal/deleteMore');
										A.confirm('您确认删除么？',function(){
											for(var index in processStatus){
												if(processStatus[index]!=1){
													alert("只有状态为待提交状态的才可以删除");
													return;
												}
											}
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'POST',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													equipAbnormalDatatables.draw(false);
												},
												error:function(v,n){
													alert('操作失败');
												}
											});
										});
									}
								}
							}],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								render: function(btnNode, data){
									var applyUserId = ${userId};
									var applyLoginName = "${userLoginName}";
									if(data.applyUserId!=applyUserId && applyLoginName!='super'){
										btnNode.hide();
									}
									if(data.processStatus!="1"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										A.loadPage({
		    	    						render : '#page-container',
		    	    						url:format_url('/equipAbnormal/getEdit/' + id),
		    	    					});
									}
								}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								render: function(btnNode, data){
									var applyUserId = ${userId};
									var applyLoginName = "${userLoginName}";
									if(data.applyUserId!=applyUserId && applyLoginName!='super'){
										btnNode.hide();
									}
									if(data.processStatus!="1"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/equipAbnormal/deleteEquipAbnormal/'+ id);
										A.confirm('您确认删除么？',function(){
											//如果不是待提交状态不可以删除
											if(nData.processStatus!=1){
												alert("不是待提交状态不可以删除！");
												return;
											}
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'POST',
        										success: function(result){
        											console.log(result);
        											alert('删除成功');
        											equipAbnormalDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						},
						{
							id:"abnormalSubmit",
							label:"提交",
							icon: "fa fa-check-square-o bigger-130",
							className: "edit",
							render: function(btnNode, data){
								var applyUserId = ${userId};
								var applyLoginName = "${userLoginName}";
								if(data.applyUserId!=applyUserId && applyLoginName!='super'){
									btnNode.hide();
								}
								if(data.processStatus!="1"){
									btnNode.hide();
								}
							},
							events:{
								click: function(event, nRow, nData){
									if(!(nData.processStatus==1)){
										alert("该记录不是待提交状态，不能提交！");
										return;
									}
									processStatus = nData.processStatus;
									listFormDialog = new A.dialog({
										width: 850,
										height: 531,
										title: "提交审核",
										url:format_url('/equipAbnormal/sureSubmitPerson/'+processStatus),
										closed: function(resule){
											var obj=new Object(); 
											var id = nData.id;
											var status = nData.status;
											var url =format_url('/equipAbnormal/submit/'+ id);
											if(listFormDialog.resule){
												  obj.userList=listFormDialog.resule.join(",");
														$.ajax({
															url : url,
				    										contentType : 'application/json',
				    										dataType : 'JSON',
				    										type : 'POST',
				    										data:JSON.stringify(obj),
				    										success: function(result){
				    											if(result.result=="success"){
				    												alert("提交成功");
				    												equipAbnormalDatatables.draw(false);
				    											}else{
				    												alert(result.errorMsg);
				    											}
				    										},
				    										error:function(v,n){
				    											alert('提交失败');
				    										}
													});
											}
										}
									}).render();
								}
							}
					},{
						id: "detail",
						label:"查看",
						icon: "fa fa-binoculars bigger-110",
						className: "blue ",
						events:{
							click: function(event, nRow, nData){
								var id = nData.id;
								A.loadPage({
									render : '#page-container',
									url : format_url('/equipAbnormal/getDetail/' + id)
								});
							}
						}
					}]
						}
					}).render();
 		 		$('#btnSearch').on('click',function(){
						conditions=[];
						if($("#equipAbnormalBill").val()){
	    					conditions.push({
	        					field: 'C_EQUIP_ABNORMAL_BILL',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					name:"equipAbnormalBill",
	        					value:$("#equipAbnormalBill").val()
	        				});
						}
						if(unitNameTreeListCombobox.getValue()!=null&&unitNameTreeListCombobox.getValue()+""!=""){
							conditions.push({
	        					field: 'T.C_UNIT_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:"unitId",
	        					value:unitNameTreeListCombobox.getValue()
	        				});
						}
						if($("#equipmentCode").val()){
	    					conditions.push({
	        					field: 'C_EQUIPMENT_ID',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					name:"equipmentId",
	        					value:$("#equipmentCode").val()
	        				});
						}
						if($("#applyUserId").val()){
	    					conditions.push({
	        					field: 'C_APPLY_USER_ID',
	        					fieldType:'INT',
	        					matchType:'EQ',
	        					name:"applyUserId",
	        					value:$("#applyUserId").val()
	        				});
						 }
						if($("#planBeginDateBegin").val()){
	    					conditions.push({
	        					field: 'C_PLAN_BEGIN_DATE',
	        					fieldType:'DATE',
	        					matchType:'GE',
	        					name:"planBeginDateBeginDiv",
	        					value:$("#planBeginDateBegin").val()
	        				});
						 }
						if($("#planBeginDateEnd").val()){
	    					conditions.push({
	        					field: 'C_PLAN_BEGIN_DATE',
	        					fieldType:'DATE',
	        					matchType:'LE',
	        					name:"planBeginDateEndDiv",
	        					value:$("#planBeginDateEnd").val()
	        				});
						 }
						if($("#planEndDateBegin").val()){
	    					conditions.push({
	        					field: 'C_PLAN_END_DATE',
	        					fieldType:'DATE',
	        					matchType:'GE',
	        					name:"planEndDateBeginDiv",
	        					value:$("#planEndDateBegin").val()
	        				});
						 }
						if($("#planEndDateEnd").val()){
	    					conditions.push({
	        					field: 'C_PLAN_END_DATE',
	        					fieldType:'DATE',
	        					matchType:'LE',
	        					name:"planEndDateEndDiv",
	        					value:$("#planEndDateEnd").val()
	        				});
						 }
						if($("#processStatus").val()){
	    					conditions.push({
	        					field: 'C_PROCESS_STATUS',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:"processStatus",
	        					value:$("#processStatus").val()
	        				});
						 }
						if($("#abnormalDesc").val()){
	    					conditions.push({
	        					field: 'C_ABNORMAL_DESC',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					name:"abnormalDesc",
	        					value:$("#abnormalDesc").val()
	        				});
						 }
						equipAbnormalDatatables._datatables.search(JSON.stringify(conditions)).draw();
					}); 
					$('#btnReset').on('click',function(){
						$("#equipAbnormalBill").val("");
						$("#equipmentCode").val("");
						userUnitRels = [];
						unitNameTreeListCombobox.setValue(null);
						$("#applyUserId").val("");
						$("#applyUserId").trigger("chosen:updated");
						$("#planBeginDateBegin").val("");
						$("#planBeginDateEnd").val("");
						$("#planEndDateBegin").val("")
						$("#planEndDateEnd").val("");
						$("#processStatus").val("");
						$("#processStatus").trigger("chosen:updated");
						$("#abnormalDesc").val("");
						processUserUnitRels = [];
					});
				});
			});
        </script>
    </body>
</html>