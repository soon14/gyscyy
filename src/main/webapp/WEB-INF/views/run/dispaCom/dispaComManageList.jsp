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
				<li class="active">运行管理</li>
				<li class="active">调度命令</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-xs-12 search-content">
				<div class="form-inline " role="form">
                    <div class="clearfix">								                      
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
						<label class="searchLabel" for="form-field-1">电站名称</label>：
						<div class="padding-zero inputWidth  text-left">
	                      <select id="dissearchunitIdDiv" class="inputWidth text-left padding-zero" ></select>
<!-- 	                      <div id="dissearchunitIdDiv" class="inputWidth text-left padding-zero"></div> -->
   						</div>
	                    
                   </div>
                        
                   <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
						<label class="searchLabel" for="form-field-1">时间</label>：
		             <div class="form-group date-input padding-zero text-left">					
                    	<div id="searchStarttimeDiv"  style="border:none; padding:0px;"></div>
                     
                     </div>   <label style="width:2.6%;text-align:center">~</label>                      
              <div class="form-group date-input padding-zero text-left">                     
                        <div id="searchEndtimeDiv"   style="border:none; padding:0px;"></div>
                  </div>
                   </div>                 
                    <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
						<label class="searchLabel" for="form-field-1">调度</label>：
                        	<div class="form-group  input-width padding-zero  text-left">
                        <select id="seldispathDiv" class="form-control chosen-select" name="dispath" ></select>						
                   </div>
                   </div> 
                   </div>
					<div class="clearfix">                    
                   <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12">
						<label class="searchLabel" for="form-field-1">受令人</label>：
						<input id="dutyChiefPerson" class="inputWidth text-left" placeholder="请输入受令人" type="text" >
                   </div>  	
                   <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12">
                            <label class="searchLabel" for="searchdepict">发令人</label>：
                            <input id="dispatchPerson" class="inputWidth text-left" placeholder="请输入发令人" type="text" >
                        </div> 	
                        <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
                            <label class="" for="searchdepict">联系内容</label>：
                            <input id="contactContent" class="input-width padding-zero derect-input  text-left" placeholder="请输入联系内容" type="text" style="width: 667px">
                        </div> 			    
					</div>
					<div class="clearfix">        
                   <div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12" style="text-align: center">                                                                                            
                   <div class="form-group" style="float:right; margin-right:-25px;">
						<button id="btnSearch" class="btn btn-xs btn-primary">
							<i class="glyphicon glyphicon-search"></i>
							查询
						</button>
						<button id="btnReset" class="btn btn-xs btn-primary">
							<i class="glyphicon glyphicon-repeat"></i>
							重置
						</button>								
					</div>
					</div>           
</div>					
                </div>
            </div>
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						
						<table id="dispaCom-table" class="table table-striped table-bordered table-hover" style="width:100%;">
						<h5 class='table-title header smaller blue' >调度命令列表</h5>
						
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
        							 <th>电站名称</th>
	                                <th>时间</th>
	                                <th>调度</th>
	                                <th>发令人</th>
	                                <th>受令人</th>
	                                <th>联系内容</th>
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
			var listFormDialog;
			var exportExcel = "";
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					var searchStartfindTime = new A.my97datepicker({
						id: 'searchStartfindTime',
						name:'searchStartfindTime',
						render:'#searchStarttimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					var searchEndfindTime = new A.my97datepicker({
						id: 'searchEndfindTime',
						name:'searchEndfindTime',
						render:'#searchEndtimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
// 					debugger;
// 					var searchunitId = new A.combotree({
// 						render: "#dissearchunitIdDiv",
// 						name: "unitId",
// 						//返回数据待后台返回TODO
// 						datasource: ${dispaComTreeList},
// 						width:"210px",
// 						options: {
// 							treeId: 'searchunitId',
// 							data: {
// 								key: {
// 									name: "name"
// 								},
// 								simpleData: {
// 									enable: true,
// 									idKey: "id",
// 									pIdKey: "parentId"
// 								}
// 							}
// 						}
// 					}).render();
					//单位
					 searchunitid = new A.combobox({
						render : "#dissearchunitIdDiv",
						datasource : ${dispaComTreeList},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					var dispathCombobox = new A.combobox({
						render: "#seldispathDiv",
						datasource:${dispatchCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10,
							"width":"100%"	
						}
					}).render();
					var dutyChiefPersonCombobox = new A.combobox({
						render: "#searchdutyChiefPersonDiv",
						datasource:${searchPerson},
						multiple:false,
						allowBlank: true,
						width:"210px",
						options:{
							"disable_search_threshold":10
						}
						}).render();
					var dispaComDatatables = new A.datatables({
						render: '#dispaCom-table',
						options: {
					        "ajax": {
					            "url": format_url("/dispaCom/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 280,
							 order: [[ 0, "desc" ]],
							columns: [{data:"id", visible:false,orderable:true}, 
							          {data: "unitName",width: "15%",orderable: true}, 
							          {data: "time",width: "15%",orderable: true}, 
							          {data: "dispathName",width: "8%",orderable: true}, 
							          {data: "dispatchPerson",width: "20%",orderable: true}, 
							          {data: "dutyChiefPerson",width: "7%",orderable: true}, 
							          {data: "contactContent",width: "30%",orderable: true}],
							          fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											 if(exportExcel){
												 exportExcels(format_url("/dispaCom/exportExcelCom"),JSON.stringify(conditions));
											 }
											 exportExcel="";
										 },
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
        								var url = format_url("/dispaCom/isAdd");
        								var flag = false;
        								if('${SysUserEntity.loginName}'!="admin"&&'${SysUserEntity.loginName}'!="super"){
	            							$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												success: function(result){
													if(JSON.stringify(result.result)=="true"){
														listFormDialog = new A.dialog({
				                    						width: 850,
				                    						height: 471,
				                    						title: "调度命令增加",
				                    						url:format_url('/dispaCom/getAdd'),
				                    						closed: function(){
				                    							dispaComDatatables.draw(false);
				                    						}	
				                    					}).render()
													}else{
														alert('您无权操作！');
														return;
													}
												},
												error:function(v,n){
													alert('操作失败');
												}
											});
        								}else{
        									listFormDialog = new A.dialog({
	                    						width: 850,
	                    						height: 471,
	                    						title: "调度命令增加",
	                    						url:format_url('/dispaCom/getAdd'),
	                    						closed: function(){
	                    							dispaComDatatables.draw(false);
	                    						}	
	                    					}).render()
        								}
            						}
        						}
        					}, 
        					{
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								render: function(btnNode, data){
									var loginName = '${SysUserEntity.loginName}'
									if(loginName!="super"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event){
										var data = dispaComDatatables.getSelectRowDatas();
										var ids = [];
										//当前运行日志时间
										var currentDutyDate = new Date('${currentDutyDate}').getTime();
										//调度命令时间
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												var dispaComDate = new Date(data[i].timeString).getTime();
												ids.push(data[i].id);
												if('${SysUserEntity.loginName}'!='super'){
												if('${SysUserEntity.id}'!=data[i].createUserId ||'${currentDutyPersonId}'!=data[i].createUserId || dispaComDate < currentDutyDate){
													alert('你无权删除该数据！');
													return;
												}
												}
											}
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										var url = format_url('/dispaCom/bulkDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													dispaComDatatables.draw(false);
												},
												error:function(v,n){
													alert('操作失败');
												}
											});
										});
									}
								}
							},{
								label:"导出",
								icon:"glyphicon glyphicon-download",
        						className:"btn-primary",
								events:{
		    						click:function(event){
		    							$('#btnSearch').click();
            							exportExcel="exportExcel";
// 		    							window.location.href=format_url("/dispaCom/exportExcelCom"); 
		    						}
								}
							}],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green",
								render: function(btnNode, data){
									//当前运行日志时间
									var currentDutyDate = new Date('${currentDutyDate}').getTime();
									//调度命令时间
									var dispaComDate = new Date(data.timeString).getTime();
									//如果调度命令不是当前用户创建||当前用户不是当前运行日志的负责人||调度命令时间小于当前运行日志时间
									if('${SysUserEntity.id}'!=data.createUserId ||'${currentDutyPersonId}'!=data.createUserId || dispaComDate < currentDutyDate){
 										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										listFormDialog = new A.dialog({
											width: 850,
											height: 471,
											title: "调度命令修改",
											url:format_url('/dispaCom/getEdit/' + id),
											closed: function(){
												dispaComDatatables.draw(false);
											}
										}).render();
									}
								}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								render: function(btnNode, data){
									//当前运行日志时间
									var currentDutyDate = new Date('${currentDutyDate}').getTime();
									//调度命令时间
									var dispaComDate = new Date(data.timeString).getTime();
									//只有超级管理员能删除
									if('${SysUserEntity.loginName}'!="super"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/dispaCom/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											dispaComDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						},{
							id:"detail",
							label:"查看",
							icon: "fa fa-binoculars",
							className: "blue ",
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									listFormDialog = new A.dialog({
										width: 850,
										height: 471,
										title: "调度命令查看",
										url:format_url("/dispaCom/dispaComdetail/" + id),
										closed: function(){
											dispaComDatatables.draw(false);
										}
									}).render();
								}
							}
					}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];			
						if($('#searchStartfindTime').val()){
	    					conditions.push({
	    						field: 'a.C_TIME',
	    						fieldType:'DATE',
	    						matchType:'GE',
	    						value:$('#searchStartfindTime').val()
	    					});
						}
						if($('#searchEndfindTime').val()){
	    					conditions.push({
	    						field: 'a.C_TIME',
	    						fieldType:'DATE',
	    						matchType:'LE',
	    						value:$('#searchEndfindTime').val()
	    					});
						}
						if($('#seldispathDiv').val()){
	    					conditions.push({
	        					field: 'a.C_DISPATH',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#seldispathDiv').val()
	        				});
						}
						if($('#dutyChiefPerson').val()){
	    					conditions.push({
	        					field: 'a.C_DUTY_CHIEF_PERSON',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$('#dutyChiefPerson').val()
	        				});
						}
						if($('#dissearchunitIdDiv').val()){
	    					conditions.push({
	        					field: 'a.C_UNIT_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#dissearchunitIdDiv').val()
	        				});
						}
// 						if(searchunitId.getValue()!=null&&searchunitId.getValue()+""!=""){
// 	    					conditions.push({
// 	        					field: 'a.C_UNIT_ID',
// 	        					fieldType:'INT',
// 	        					matchType:'EQ',
// 	        					value:searchunitId.getValue()
// 	        				});
// 						}
						if($('#dispatchPerson').val()){
	    					conditions.push({
	        					field: 'a.C_DISPATCH_PERSON',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$('#dispatchPerson').val()
	        				});
						}
						if($('#contactContent').val()){
	    					conditions.push({
	        					field: 'a.C__CONTACT_CONTENT',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$('#contactContent').val()
	        				});
						}
						dispaComDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$("#dissearchunitIdDiv").val("");
						$("#dissearchunitIdDiv").trigger("chosen:updated");
						$("#searchdutyChiefPersonDiv").val("");
						$("#searchdutyChiefPersonDiv").trigger("chosen:updated");
						$("#dutyChiefPerson").val("");
						$("#searchStartfindTime").val("");
						$("#searchEndfindTime").val("");
						$("#dutyChiefPerson").val("");
						$("#contactContent").val("");						
						$("#dispatchPerson").val("");						
					});
				});
			});
        </script>
    </body>
</html>