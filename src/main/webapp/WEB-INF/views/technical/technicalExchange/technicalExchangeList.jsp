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
				<li class="active">技术监督</li>
				<li class="active">技术交流</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-lg-12 col-md-12 col-sm-12 search-content  padding-zero">
				<div class="form-inline text-left" role="form">
				<div class="clearfix">
	             	<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
	                    <label class="searchLabel" for="searchunitNameId">交流单位</label>：
	                    <div id="searchunitNameId" class="inputWidth text-left padding-zero"></div>
	                </div>
	                
	                <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12 padding-zero">
	                    <label class="searchLabel longSearchLabel" for="searchgroupId">交流申请人</label>：
	                    <div class="inputWidth padding-zero  text-left">
	                        <select id="searchguarderName" class="form-control chosen-select"></select>
	                    </div>
	                </div>
	                 <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12  padding-zero">
	                    <label class="searchLabel" for="form-field-1">交流时间</label>：
	                    <div class="form-group dateInputOther padding-zero text-left">
	                        <div id="searchFillTimeStartDiv" style="border:none; padding:0px;"></div>
	                    </div>
	                    <div class="toLabel" for="searchStartTimeRightDiv" >~</div>
	                    <div class="form-group dateInputOther padding-zero text-left">
	                        <div id="searchFillTimeEndDiv" style="border:none; padding:0px;"></div>
	                    </div>
	                </div>   
	            </div>
                   <div class="clearfix" >
						<div class="form-group col-lg-12 col-md-12 col-sm-6 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; ">
                            <button id="btnSearch" class="btn btn-xs btn-primary">
                                <i class="glyphicon glyphicon-search"></i>
                           		     查询
                            </button>
                            <button id="btnReset" class="btn btn-xs btn-primary" >
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
						<h5 class='table-title header smaller blue' >技术交流</h5>
						<table id="technicalExchange_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
	                                <th>交流时间</th>
	                                <th>交流申请人</th>
	                                <th>交流单位</th>
	                                <th>交流主题</th>
	                                <th>交流内容</th>
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
			var exportExcel;
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					var unitNameIdCombotree = new A.combotree({
						render: "#searchunitNameId",
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
					//工作负责人
					var searchguarderName = new A.combobox({
						render : "#searchguarderName",
						datasource : ${requestUsers},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					var searchFillTimeStartDiv = new A.my97datepicker({
						id: 'searchFillTimeStartDivId',
						name:'fillTime',
						render:'#searchFillTimeStartDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "#F{$dp.$D(\\'searchFillTimeEndDivId\\')}",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					
					var searchFillTimeEndDiv = new A.my97datepicker({
						id: 'searchFillTimeEndDivId',
						name:'fillTime',
						render:'#searchFillTimeEndDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'searchFillTimeStartDivId\\')}",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					var technicalExchangeDatatables = new A.datatables({
						render: '#technicalExchange_table',
						options: {
					        "ajax": {
					            "url": format_url("/technicalExchange/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.conditions = conditions;
					            	dd=d;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	}}, 
							          {data: "time",width: "auto",orderable: true}, 
							          {data: "userName",name:"userId",width: "auto",orderable: true}, 
							          {data: "unitName",name:"unitId",width: "auto",orderable: true}, 
							          {data: "name",width: "auto",orderable: true}, 
							          {data: "content",width: "auto",orderable: true}, 
							          ],
							          fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											 if(exportExcel){
												 exportExcels(format_url("/technicalExchange/exportExcel"),JSON.stringify(dd));
											 }
											 exportExcel="";
										 },
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							var unitId = ${unitId};
        								if(unitId=="112"||unitId=="113"||unitId=="114"||unitId=="115"||unitId=="116"||unitId=="117"||unitId=="138"||unitId=="141"||unitId=="144"){
        									listFormDialog = new A.dialog({
                        						width: '1000',
                        						height: '471',
                        						title: "技术交流增加",
                        						url:format_url('/technicalExchange/getAdd'),
                        						closed: function(){
                        							technicalExchangeDatatables.draw(false);
                        						}	
                        					}).render()
        								}else{
        									alert("只有风场、中心人员和生技部人员才可以新增！");
        								}
                						
            						}
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = technicalExchangeDatatables.getSelectRowDatas();
										var ids = [];
										var userIds = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
												userIds.push(data[i].userId);
											}
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										var loginUser = ${userId};
										var loginName ="${loginName}";
										for(var j=0;j<userIds.length;j++){
											if(userIds[j]!=loginUser&&loginName!="super"){
												alert('记录中包含不是当前登陆人的记录不能删除!');
												return;
											}
										}
										var url = format_url('/technicalExchange/bulkDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													technicalExchangeDatatables.draw(false);
												},
												error:function(v,n){
													alert('操作失败');
												}
											});
										});
									}
								}
							},{  
        						id:"dc",
        						label:"导出",
        						icon:"glyphicon glyphicon-download",
        						className:"btn-primary",
        						events:{
        							click:function(event){
            							$('#btnSearch').click();
            							exportExcel="exportExcel";
            						}
        						}
        					}],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								render: function(btnNode, data){
									var userId='${userId}';
									if(userId!=data.userId){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										listFormDialog = new A.dialog({
											width: '1000',
											height: '471',
											title: "技术交流编辑",
											url:format_url('/technicalExchange/getEdit/' + id),
											closed: function(){
												technicalExchangeDatatables.draw(false);
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
									var userId='${userId}';
									if(userId!=data.userId){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/technicalExchange/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											technicalExchangeDatatables.draw(false);
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
						    id:"440",
							label:"查看",
							icon: "fa fa-binoculars bigger-110",
							className: "blue",
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									summaryDialog = new A.dialog({
                						width: 880,
                						height:460 ,
                						title: "技术交流查看",
                						url:format_url("/technicalExchange/detail/"+ id),
                						closed: function(){
                							technicalExchangeDatatables.draw(false);
                						}	
                					}).render()
								}
							}
					}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						if(unitNameIdCombotree.getValue()!=null&&unitNameIdCombotree.getValue()!=""){
    						conditions.push({
            					field: 't.C_UNIT_ID',
            					fieldType:'LONG',
            					matchType:'EQ',
            					value:unitNameIdCombotree.getValue()
            				});
    					}
						if($("#searchguarderName").val()!=""){
	    					conditions.push({
	        					field: 't.C_USER_ID',
	        					fieldType:'LONG',
	        					matchType:'EQ',
	        					value:$('#searchguarderName').val()
	        				});
    					}
						if($("#searchFillTimeStartDivId").val()!=""){
	    					conditions.push({
	        					field: 't.C_TIME',
	        					fieldType:'DATE',
	        					matchType:'GE',
	        					value:$('#searchFillTimeStartDivId').val()
	        				});
    					}
						if($("#searchFillTimeEndDivId").val()!=""){
	    					conditions.push({
	        					field: 't.C_TIME',
	        					fieldType:'DATE',
	        					matchType:'LE',
	        					value:$('#searchFillTimeEndDivId').val()
	        				});
    					}
						technicalExchangeDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						unitNameIdCombotree.setValue();//
						$('#searchguarderName').val('');//
						$("#searchguarderName").trigger("chosen:updated");//
						$("#searchFillTimeStartDivId").val("");
						$("#searchFillTimeEndDivId").val("");
					});
				});
			});
        </script>
    </body>
</html>