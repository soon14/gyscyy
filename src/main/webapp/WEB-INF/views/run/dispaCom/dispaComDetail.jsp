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
		<div class="col-xs-12 search-content">
				<div class="form-inline text-right" role="form">
				<div class="clearfix" style="margin-left: -7%">
				  <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
						<label class="" for="form-field-1">电站名称：</label>
				  		 <div id="dissearchunitIdDiv" class="input-width  text-left padding-zero"></div>
				   </div>
                    <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
						<label class="" for="form-field-1">调度：</label>
						<div class="input-width padding-zero  text-left">
				    <select id="disselprotectWayDiv" class="col-md-12" name="dispath"></select>	
				    </div>				    					    				    
                   </div>
                    <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
						<label class="" for="form-field-1">时间：</label>
						<div class="form-group date-input padding-zero text-left">
							<div id="dissearchStartfindTimeDiv"
								style="border:none; padding:0px;"></div>
						</div>
						<label style="width: 2.6%;text-align: center">~</label>
						<div class="form-group date-input padding-zero text-left">
							<div id="dissearchEndfindTimeDiv" style="border:none; padding:0px;"></div>
						</div>
				   </div>
                 </div>
                 <div class="clearfix" style="margin-left: -7%">
                 <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
                            <label class="" for="">联系内容：</label>
                            <input id="contactContent" class="input-width   text-left" placeholder="请输入联系内容" type="text" >
                        </div> 
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="" for="form-field-1">受令人</label>：
                            <div class="padding-zero inputWidth  text-left">
<!--                               <select id="searchdutyChiefPersonDiv"  name="searchdutyChiefPersonDiv"></select> -->
                              <input id="searchdutyChiefPersonDiv" class="input-width  text-left" placeholder="请输入受令人" type="text" style="width: 95%">
                              </div>
                        </div>
                         <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="" for="form-field-1">发令人</label>：
                             <input id="dispatchPerson" class="input-width  text-left" placeholder="请输入发令人" type="text" style="width: 65%">
                        </div>
                   
					</div>
					<div class="clearfix">
					<div class="form-group  col-lg-3 col-md-3 col-sm-6 col-xs-12" style="float:right; ">
						<button id="disbtnSearch" class="btn btn-xs btn-primary">
							<i class="glyphicon glyphicon-search"></i>
							查询
						</button>
						<button id="disbtnReset" class="btn btn-xs btn-primary" style="margin-right: -25px">
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
                         <h5 class="table-title header smaller blue" >调度命令列表</h5>
						<table id="dispaCom-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
<!-- 									<th>序号</th>									 -->
	                                <th>电站名称</th>
	                                <th>时间</th>
	                                <th>调度</th>
	                                <th>发令人</th>
	                                <th>受令人</th>
	                                <th>联系内容</th>
<!-- 	                                <th>操作</th> -->
                                </tr>
                            </thead>
                        </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var listFormDialog;
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
				var RLId=${RLId};
				//部门控件下拉树
				var dissearchunitId = new A.combotree({
				render: "#dissearchunitIdDiv",
				name: 'dissearchunitId',
				//返回数据待后台返回TODO
				datasource: ${dispaComTreeList},
				width:"210px",
				options: {
					treeId: 'dissearchunitId',
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
				var disprotectWayCombobox = new A.combobox({
					render: "#disselprotectWayDiv",
					datasource:${dispaComCombobox},
					//multiple为true时select可以多选
					multiple:false,
					//allowBlank为false表示不允许为空
					allowBlank: true,
					options:{
						"disable_search_threshold":10,
						"width":"100%"	
					}
					}).render();	
				var dissearchStartfindTime = new A.my97datepicker({
					id: 'dissearchStartfindTime',
					name:'dissearchStartfindTime',
					render:'#dissearchStartfindTimeDiv',
					options : {
							isShowWeek : false,
							skin : 'ext',
							maxDate: "",
							minDate: "",
							dateFmt: "yyyy-MM-dd HH:mm"
					}
				}).render();
				var dissearchEndfindTime = new A.my97datepicker({
					id: 'dissearchEndfindTime',
					name:'dissearchEndfindTime',
					render:'#dissearchEndfindTimeDiv',
					options : {
							isShowWeek : false,
							skin : 'ext',
							maxDate: "",
							minDate: "#F{$dp.$D(\\'dissearchStartfindTime\\')}",
							dateFmt: "yyyy-MM-dd HH:mm"
					}
				}).render();
					var exportExcel;
					var dispaComDatatables = new A.datatables({
						render: '#dispaCom-table',
						options: {
					        "ajax": {
					            "url": format_url("/dispaCom/searchDetail/"+RLId),
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
							optWidth: 80,
							checked:true,
							bInfo:false,
							paging:false,
							 order: [[ 3, "desc" ]],
							columns: [{data:"id", visible:false,orderable:false},
// 							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
// 						                   var startIndex = meta.settings._iDisplayStart;  
// 						                   row.start=startIndex + meta.row;
// 						                   return startIndex + meta.row + 1;  
// 						               	} },
							          {data: "unitName",width: "0%",orderable: true}, 
							          {data: "time",width: "0%",orderable: true},
							          {data: "dispathName",width: "auto",orderable: true}, 
							          {data: "dispatchPerson",width: "auto",orderable: true}, 
							          {data: "dutyChiefPerson",width: "auto",orderable: true}, 
							          {data: "contactContent",width: "auto",orderable: true}],
							          fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
							        	 	 if(exportExcel){
												 exportExcels(format_url("/dispaCom/exportExcel?RLId="+RLId),JSON.stringify(conditions));
											 }
											 exportExcel="";
									  },
							          toolbars: [
// 							                     {
// 			        						label:"新增",
// 			        						icon:"glyphicon glyphicon-plus",
// 			        						className:"btn-success",
// 			        						events:{
// 			            						click:function(event){
// 			                						listFormDialog = new A.dialog({
// 			                    						width: 850,
// 			                    						height: 471,
// 			                    						title: "调度命令增加",
// 			                    						url:format_url('/dispaCom/getAdd'),
// 			                    						closed: function(){
// 			                    							dispaComDatatables.draw(false);
// 			                    						}	
// 			                    					}).render()
// 			            						}
// 			        						}
// 			        					}, {
// 											label:"删除",
// 											icon:"glyphicon glyphicon-trash",
// 											className:"btn-danger",
// 											events:{
// 												click: function(event){
// 													var data = dispaComDatatables.getSelectRowDatas();
// 													var ids = [];
// 													if(data.length && data.length>0){
// 														for(var i =0; i<data.length; i++){
// 															ids.push(data[i].id);
// 														}
// 													}
// 													if(ids.length < 1){
// 														alert('请选择要删除的数据');
// 														return;
// 													}
// 													var url = format_url('/dispaCom/bulkDelete/');
// 													A.confirm('您确认删除么？',function(){
// 														$.ajax({
// 															url : url,
// 															contentType : 'application/json',
// 															dataType : 'JSON',
// 															type : 'DELETE',
// 															data : JSON.stringify(ids),
// 															success: function(result){
// 																alert('删除成功');
// 																dispaComDatatables.draw(false);
// 															},
// 															error:function(v,n){
// 																alert('操作失败');
// 															}
// 														});
// 													});
// 												}
// 											}
// 										},
										{
											label:"导出",
											icon:"glyphicon glyphicon-download",
			        						className:"btn-primary",
											events:{
												click:function(event){
					    							$('#disbtnSearch').click();
			            							exportExcel="exportExcel";
					    						}
											}
										}]
// 										btns: [{
// 											id: "edit",
// 											label:"修改",
// 											icon: "fa fa-pencil-square-o bigger-130",
// 											className: "green",
// 											events:{
// 												click: function(event, nRow, nData){
// 													var id = nData.id;
// 													listFormDialog = new A.dialog({
// 														width: 850,
// 														height: 471,
// 														title: "调度命令修改",
// 														url:format_url('/dispaCom/getEdit/' + id),
// 														closed: function(){
// 															dispaComDatatables.draw(false);
// 														}
// 													}).render();
// 												}
// 											}
// 										}, {
// 											id:"delete",
// 											label:"删除",
// 											icon: "fa fa-trash-o bigger-130",
// 											className: "red del",
// 											events:{
// 												click: function(event, nRow, nData){
// 													var id = nData.id;
// 													var url =format_url('/dispaCom/'+ id);
// 													A.confirm('您确认删除么？',function(){
// 														$.ajax({
// 															url : url,
// 			        										contentType : 'application/json',
// 			        										dataType : 'JSON',
// 			        										type : 'DELETE',
// 			        										success: function(result){
// 			        											alert('删除成功');
// 			        											dispaComDatatables.draw(false);
// 			        										},
// 			        										error:function(v,n){
// 			        											alert('操作失败');
// 			        										}
// 														});
// 													});
// 												}
// 											}
// 									}]
						}
					}).render();
					$('#disbtnReset').on('click',function(){
						dissearchunitId.setValue(null);
						$("#dissearchStartfindTime").val('');
						$("#dissearchEndfindTime").val("");
						$("#disselprotectWayDiv").val("");
						$("#disselprotectWayDiv").trigger("chosen:updated");
						$('#contactContent').val("");
						$("#dispatchPerson").val("");
						$("#searchdutyChiefPersonDiv").val("");
					});
					$('#disbtnSearch').on('click',function(){
						conditions=[];			
						if($('#dissearchStartfindTime').val()){
	    					conditions.push({
	    						field: 'a.C_TIME',
	    						fieldType:'DATE',
	    						matchType:'GE',
	    						value:$('#dissearchStartfindTime').val()
	    					});
						}
						if($('#dissearchEndfindTime').val()){
	    					conditions.push({
	    						field: 'a.C_TIME',
	    						fieldType:'DATE',
	    						matchType:'LE',
	    						value:$('#dissearchEndfindTime').val()
	    					});
						}
						if($('#disselprotectWayDiv').val()){
	    					conditions.push({
	        					field: 'a.C_DISPATH',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#disselprotectWayDiv').val()
	        				});
						}
						if(dissearchunitId.getValue()!=undefined&&dissearchunitId.getValue()+""!=""){
	    					conditions.push({
	        					field: 'a.C_UNIT_ID',
	        					fieldType:'INT',
	        					matchType:'EQ',
	        					value:dissearchunitId.getValue()
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
						if($('#dispatchPerson').val()){
	    					conditions.push({
	        					field: 'a.C_DISPATCH_PERSON',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$('#dispatchPerson').val()
	        				});
						}
						if($('#searchdutyChiefPersonDiv').val()){
	    					conditions.push({
	        					field: 'a.C_DUTY_CHIEF_PERSON',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$('#searchdutyChiefPersonDiv').val()
	        				});
						}
						dispaComDatatables.draw();
					});
				});
			});
        </script>
    </body>
</html>