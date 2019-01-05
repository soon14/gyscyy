<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="page-content">
			<div style="text-align: center;font-size: 20px;color: #478fca;font-weight: lighter;margin-right:100px;">工  作  安  排 </div>
					<!-- div.dataTables_borderWrap -->
					<h5 class="table-title header smaller blue" ></h5>
					<div class="widget-main no-padding" style="margin-bottom: 20px;">
					       <table id="safeMeeting-table2" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>内容</th>
	                                <th>措施检查状态</th>
                                    <th>操作 </th>
                                </tr>
                            </thead>
                        </table>
					<!-- 	<table id="safeMeeting-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
        							<th>序号</th>
	                                <th>工作内容</th>
	                                <th>完成情况</th>
	                                <th>负责人</th>	                                
	                                <th>安排时间</th>	                                
                                    <th>操作 </th>
                                </tr>
                            </thead>
                        </table> -->
            </div>		
			<div style="text-align: center;font-size: 20px;color: #478fca;font-weight: lighter;margin-right:100px;margin-bottom: 0px;">安  全  交  底  内  容</div>
			<div class="row">
				<div class="col-xs-12">
					<div class="widget-main no-padding">
				<h5 class="table-title header smaller lighter blue">交代当日任务中作业环境、存在风险</h5>                        						
						<table id="safeMeeting-table1" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>内容</th>
	                                <th>措施检查状态</th>
                                    <th>操作 </th>
                                </tr>
                            </thead>
                        </table>
				<h5 class="table-title header smaller lighter blue">交代预防当日任务风险的安全作业事项</h5>  
				        <table id="safeMeeting-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
        							<th>序号</th>
	                                <th>工作内容</th>
	                                <th>完成情况</th>
	                                <th>负责人</th>	                                
	                                <th>安排时间</th>	                                
                                    <th>操作 </th>
                                </tr>
                            </thead>
                        </table>                      
                <!--        <table id="safeMeeting-table2" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>内容</th>
	                                <th>措施检查状态</th>
                                    <th> </th>
                                </tr>
                            </thead>
                        </table> -->
                    </div>
						<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addForm">				
					<input type="hidden" name="id" value="${rlId}">
						<input id="btmPersons" name="btmPersons"  type="hidden" value="${dataMap.btmPersonsIds}" class="col-md-12">
						<input id="atmPersons" name="atmPersons"  type="hidden" value="${dataMap.atmPersonsIds}" class="col-md-12">	
					<hr>
<!-- 					<div style="text-align: center;font-size: 20px;color: #478fca;font-weight: lighter;">班&nbsp&nbsp&nbsp&nbsp&nbsp前&nbsp&nbsp&nbsp&nbsp&nbsp会</div>				                     -->
<!--                    <div class="form-group" style="margin-right: 100px;"> -->
<!-- 				    <label class="col-md-2 control-label no-padding-right"> -->
<!-- 					    <span style="color:red;">*</span>班前会内容 -->
<!-- 				    </label> -->
<!--                      	<div class="col-md-10"> -->
<%-- 						<textarea placeholder="请输入班前会内容" id="btmContent" name="btmContent"  class="col-md-12" style="height:150px; resize:none;">${dataMap.btmContent}</textarea> --%>
<!-- 				    </div> -->
<!-- 				</div> -->
				 <div class="form-group" style="margin-right: 100px;">
				    <label class="col-md-2 control-label no-padding-right">
					  <span style="color:red;">*</span> 值班负责人
				    </label>
				    <div class="col-md-10">
				    	<select id="btmPersonsIdsDiv" class="col-md-12" name="btmPersonsIds"></select>									    
					</div>
				</div>
				 <hr>
<!-- 				 					<div style="text-align: center;font-size: 20px;color: #478fca;font-weight: lighter;">班&nbsp&nbsp&nbsp&nbsp&nbsp后&nbsp&nbsp&nbsp&nbsp&nbsp会</div>				                     -->
				 
<!--                       <div class="form-group" style="margin-right: 100px;"> -->
<!-- 				    <label class="col-md-2 control-label no-padding-right"> -->
<!-- 					 <span style="color:red;"></span>  班后会内容 -->
<!-- 				    </label> -->
<!--                      	<div class="col-md-10"> -->
<%-- 						<textarea placeholder="请输入班后会内容" id="atmContent"name="atmContent"  class="col-md-12" style="height:150px; resize:none;">${dataMap.atmContent}</textarea> --%>
<!-- 				    </div> -->
<!-- 				</div> -->
				 <div class="form-group" style="margin-right: 100px;">
				    <label class="col-md-2 control-label no-padding-right">
					 <span style="color:red;"></span>  值班人员
				    </label>
				    <div class="col-md-10">
				    	<select id="atmPersonsIdsDiv" class="col-md-12" name="atmPersonsIds"></select>									    
					</div>
				</div>
				
        	</div>
        	</form>	
        	<div style="margin-right:100px;">       		
        		<button id="saveFormBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
        			<i class="ace-icon fa fa-floppy-o"></i>
        			保存
        		</button>
        	</div>					                
            </div>
        </div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var listFormDialog;
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					var btmPersonsIds = $("#btmPersons").val().split(",");
					var atmPersonsIds = $("#atmPersons").val().split(",");					
					//combobox组件
					var btmPersonsIdsCombobox = new A.combobox({
						render: "#btmPersonsIdsDiv",
						datasource:${safeMeetingCombobox},
						multiple:true,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						},
						initValue:btmPersonsIds
					}).render();
					//combobox组件
					var atmPersonsIdsCombobox = new A.combobox({
						render: "#atmPersonsIdsDiv",
						datasource:${safeMeetingCombobox},
						multiple:true,						
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						},
						initValue:atmPersonsIds
					}).render();
					var safeMeetingDatatables = new A.datatables({
						render: '#safeMeeting-table',
						options: {
					        "ajax": {
					            "url": format_url("/safeMeeting/searchDataForWind"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
			        					field: 'C_MEETING_FLAG',
			        					fieldType:'INT',
			        					matchType:'EQ',
			        					value:6
			        				});
					            	conditions.push({
			        					field: 'C_RL_ID',
			        					fieldType:'INT',
			        					matchType:'EQ',
			        					value:${rlId}
			        				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							aLengthMenu: [5],
							columns: [{data:"id", visible:false,orderable:false}, 
							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "meetingContent",width: "auto",orderable: true}, 
							          {data: "checkStateName",width: "auto",orderable: true}, 
							          {data: "fZR",width: "auto",orderable: true},
							          {data: "workTime",width: "auto",orderable: true}],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							var rlId=${rlId};
                						listFormDialog = new A.dialog({
                    						width: 850,
                    						height: 371,
                    						title: "日例会增加",
                    						url:format_url('/safeMeeting/getAddForWind/'+rlId+'?flag='+6),
                    						closed: function(){
                    							safeMeetingDatatables.draw(false);
                    						}	
                    					}).render()
            						}
        						}
        					},{
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = safeMeetingDatatables.getSelectRowDatas();
										var ids = [];
										var meetingFlags = false;
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
												if(data[i].meetingFlag==3){
													meetingFlags=true;
												};
											}
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										if(meetingFlags){
											alert('存在固定内容不能删除');
											return;
										}
										var url = format_url('/safeMeeting/bulkDeleteForWind/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'POST',
												data : JSON.stringify(ids),
												success: function(result){
        											if(result.result=="success"){
        												alert('删除成功');	
        												safeMeetingDatatables.draw(false);
        											}else{
        												alert(result.errorMsg);
        											}
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
            							var rlId=${rlId};
		    							window.location.href=format_url("/safeMeeting/exportExcelForWind/"+rlId); 
		    						}
								}
							}],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										listFormDialog = new A.dialog({
											width: 850,
											height: 471,
											title: "日例会编辑",
											url:format_url("/safeMeeting/getEditRun/"+id+"?flag="+6+"&type=1"),
											closed: function(){
												safeMeetingDatatables.draw(false);
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
									if(data.meetingFlag==3){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/safeMeeting/delForWind/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											safeMeetingDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						}]
						}
					}).render();
					var conditions1=[];
					var safeMeetingDatatables1 = new A.datatables({
						render: '#safeMeeting-table1',
						options: {
					        "ajax": {
					            "url": format_url("/safeMeeting/searchDataForWind"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions1.push({
			        					field: 'C_MEETING_FLAG',
			        					fieldType:'INT',
			        					matchType:'EQ',
			        					value:5
			        				});
					            	conditions1.push({
			        					field: 'C_RL_ID',
			        					fieldType:'INT',
			        					matchType:'EQ',
			        					value:${rlId}
			        				});
					            	d.conditions = conditions1;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							aLengthMenu: [5],
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "meetingContent",width: "auto",orderable: true}, 
							          {data: "checkStateName",width: "auto",orderable: true}],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							var rlId=${rlId};
                						listFormDialog = new A.dialog({
                    						width: 850,
                    						height: 471,
                    						title: "日例会增加",
                    						url:format_url('/safeMeeting/getAddSafeFowWind/'+rlId+'?flag='+5),
                    						closed: function(){
                    							safeMeetingDatatables1.draw(false);
                    						}	
                    					}).render()
            						}
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = safeMeetingDatatables1.getSelectRowDatas();
										var ids = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
											}
										}
										if(ids.length == 0){
											alert('请选择要删除的数据');
											return;
										}
										var url = format_url('/safeMeeting/bulkDeleteForWind/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													safeMeetingDatatables1.draw(false);
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
								className: "green",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										listFormDialog = new A.dialog({
											width: 850,
											height: 471,
											title: "日例会编辑",
											url:format_url("/safeMeeting/getEdit/"+id+"/?flag=5"),
											closed: function(){
												safeMeetingDatatables1.draw(false);
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
										var url =format_url('/safeMeeting/delForWind/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											safeMeetingDatatables1.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						}]
						}
					}).render();
					var conditions2=[];
					var safeMeetingDatatables2 = new A.datatables({
						render: '#safeMeeting-table2',
						options: {
					        "ajax": {
					            "url": format_url("/safeMeeting/searchDataForWind"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions2.push({
			        					field: 'C_MEETING_FLAG',
			        					fieldType:'INT',
			        					matchType:'EQ',
			        					value:4
			        				});
					            	conditions2.push({
			        					field: 'C_RL_ID',
			        					fieldType:'INT',
			        					matchType:'EQ',
			        					value:${rlId}
			        				});
					            	d.conditions = conditions2;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							aLengthMenu: [5],
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "meetingContent",width: "auto",orderable: true}, 
							          {data: "checkStateName",width: "auto",orderable: true}],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							var rlId=${rlId};
                						listFormDialog = new A.dialog({
                    						width: 850,
                    						height: 471,
                    						title: "日例会增加",
                    						url:format_url('/safeMeeting/getAddSafeForWind/'+rlId+'?flag='+4),
                    						closed: function(){
                    							safeMeetingDatatables2.draw(false);
                    						}	
                    					}).render()
            						}
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = safeMeetingDatatables2.getSelectRowDatas();
										var ids = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
											}
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										var url = format_url('/safeMeeting/bulkDeleteForWind/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													safeMeetingDatatables2.draw(false);
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
								className: "green",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										listFormDialog = new A.dialog({
											width: 850,
											height: 471,
											title: "日例会编辑",
											url:format_url("/safeMeeting/getEdit/"+id+'?flag='+4),
											closed: function(){
												safeMeetingDatatables2.draw(false);
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
										var url =format_url('/safeMeeting/delForWind/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											safeMeetingDatatables2.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						}]
						}
					}).render();						
						
					$('#addForm').validate({
						rules:   {
// 							 btmContent:{required:true,maxlength:512},
							 btmPersonsIdsDiv:{required:true,maxlength:64}
							 },
						submitHandler: function (form) {
							var id=${rlId};
							var url = format_url("/runLog/meetingForWind/"+id);
							var obj = $("#addForm").serializeObject();
							var btmPersonsIds = btmPersonsIdsCombobox.getValue();
							if(btmPersonsIds){
								obj.btmPersonsIds = btmPersonsIds;
							}
							var atmPersonsIds = atmPersonsIdsCombobox.getValue();
							if(atmPersonsIds){
								obj.atmPersonsIds = atmPersonsIds;
							}
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('保存成功');
// 									A.loadPage({
// 										render : '#page-container',
// 										url : format_url("/runLog/indexForWind")
// 									});
								},
								error:function(v,n){
									alert('保存失败');
								}
							});
						}
					});
					//提交
					$("#saveFormBtn").on("click", function(){
						if($('#btmPersonsIdsDiv').val()==''){
							alert("请填写班前会参加人员！");
							return;
						}
						$("#addForm").submit();
					});
				});
			});
        </script>
    </body>
</html>