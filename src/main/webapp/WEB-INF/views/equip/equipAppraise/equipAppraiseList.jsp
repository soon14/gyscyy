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
				<li>设备管理</li>
				<li class="active">设备评价管理</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-xs-12 search-content">
				<div class="form-inline text-right" role="form">
	    			<div class="clearfix" style="width: 95%">
	                    <!-- <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
	                        <label class="" for="equipTopName">系统名称：</label>
	                        <input id="equipTopName" class="input-width text-left" placeholder="请输入系统名称" type="text">
	                    </div> -->
	                    <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                        <label class="" for="equipName">设备名称：</label>
	                        <input id="equipName" class="input-width text-left" placeholder="请输入设备名称" type="text">
	                    </div>
	                    <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                        <label class="" for="equipGradePre">原评：</label>
	                        <div class="padding-zero input-width  text-left" style="margin-left:12px">
	                            <select id="equipGradePre" class="form-control chosen-select" name="equipGradePre"></select>
	                        </div>
	                    </div>
	                    <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 pull-left" >
	                        <label class="" for="equipGradeNow">现评：</label>
	                        <div class="padding-zero input-width  text-left">
	                            <select id="equipGradeNow" class="form-control chosen-select" name="equipGradeNow"></select>
	                        </div>
	                    </div>
	                    <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                        <label class="" for="equipAppraisePerson">评价人：</label>
	                        <div class="padding-zero input-width  text-left">
	                       		 <select id="equipAppraisePerson" class="form-control chosen-select" name="equipAppraisePerson"></select>
	                        </div>
	                    </div>
	                 </div>
					<div class="form-inline" role="form">
						<div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12" style="width: 47%">
							<label class="" for="form-field-1"  style="width:72px">设备评价日期</label>：
							<div class="form-group date-input padding-zero text-left">
	                               <div id="startAppraiseDateDiv"  style="border:none; padding:0px;"></div>
	                           </div>
	                        <label style="width: 2.6%;text-align: center">~</label>
	                        <div class="form-group date-input padding-zero text-left">
	                               <div id="endAppraiseDateDiv"   style="border:none; padding:0px;"></div>
	                          </div>
	                   </div>
	                   <!-- <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                        <label class="" for="equipAppraisePerson">评价人：</label>
	                        <div class="padding-zero input-width  text-left">
	                       		 <select id="equipAppraisePerson" class="form-control chosen-select" name="equipAppraisePerson"></select>
	                        </div>
	                    </div> -->
	                   <div class="form-group" style="float:right; margin-right:14px;">
							<button id="btnSearch" class="btn btn-xs btn-primary">
								<i class="glyphicon glyphicon-search"></i>
								查询
							</button>
							<button id="btnReset" class="btn btn-xs btn-primary" style="margin-right: -27px">
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
						<h5 class='table-title header smaller blue' >设备评价</h5>
						<table id="equipAppraise_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
        							<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>设备编号</th>
	                                <th>设备名称</th>
	                                <th>原评</th>
	                                <th>现评</th>
	                                <th>评定或变动等级依据和原因</th>
	                                <th>现在存在主要缺陷及其影响</th>
	                                <th>设备评价日期</th>
	                                <th>设备评价人</th>
	                                <th>备注</th>
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
			var userUnitRels = ${userUnitRels};
			//初始数据备份
			var processUserUnitRels = ${userUnitRels};
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					//原评
					var equipGradePreCombobox = new A.combobox({
						render: "#equipGradePre",
						datasource:${equipGradePre},
						multiple:false,
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//现评
					var equipGradeNowCombobox = new A.combobox({
						render: "#equipGradeNow",
						datasource:${equipGradeNow},
						multiple:false,
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//开始时间
					var searchStartDate = new A.my97datepicker({
						id: 'startAppraiseDate',
						name:'equipAppraiseDate',
						render:'#startAppraiseDateDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "#F{$dp.$D(\\'endAppraiseDate\\')}",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					
					//终了时间
					var searchEndDate = new A.my97datepicker({
						id: 'endAppraiseDate',
						name:'equipAppraiseDate',
						render:'#endAppraiseDateDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'startAppraiseDate\\')}",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					
					//评价人
					var searchfindUserId = new A.combobox({
						render : "#equipAppraisePerson",
						datasource : ${searchuser},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					
					var equipAppraiseDatatables = new A.datatables({
						render: '#equipAppraise_table',
						options: {
					        "ajax": {
					            "url": format_url("/equipAppraise/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
			        					field: 'T.C_STATUS',
			        					fieldType:'INT',
			        					matchType:'EQ',
			        					value:'1'
			        				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							columns: [{data: "id", visible:false,orderable:false}, 
							          {data: "equipCode",width: "15%",orderable: false,
										render : function(data, type, row, meta) {
												if(data!=null && data.length>20){
													return "<div title='"+data+"'>"+data.substring(0,20)+"..."+"</div>";  
												}else{
													return data;  
												}
									  		}
							          }, 
							          {data: "equipName",width: "15%",orderable: false,
							        	  render : function(data, type, row, meta) {
												if(data!=null && data.length>20){
													return "<div title='"+data+"'>"+data.substring(0,20)+"..."+"</div>";  
												}else{
													return data;  
												}
									  		}
							          }, 
							          {name: "equipGradePre",data: "equipGradePreName",width: "5%",orderable: true}, 
							          {name: "equipGradeNow",data: "equipGradeNowName",width: "5%",orderable: true}, 
							          {data: "equipAppraiseReason",width: "15%",orderable: true}, 
							          {data: "equipAppraiseInfluence",width: "15%",orderable: true}, 
							          {data: "equipAppraiseDate",width: "8%",orderable: true}, 
							          {data: "equipAppraisePerson",width: "5%",orderable: true},
							          {data: "equipAppraiseComment",width: "13%",orderable: true,
							        	  render : function(data, type, row, meta) {
												if(data!=null && data.length>20){
													return "<div title='"+data+"'>"+data.substring(0,20)+"..."+"</div>";  
												}else{
													return data;  
												}
									  		}
							          }
							          ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							A.loadPage({
		    	    						render : '#page-container',
		    	    						url : format_url("/equipAppraise/getAdd")
		    	    					});
            						}
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = equipAppraiseDatatables.getSelectRowDatas();
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
										var url = format_url('/equipAppraise/deleteMore');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													equipAppraiseDatatables.draw(false);
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
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
            							A.loadPage({
		    	    						render : '#page-container',
		    	    						url : format_url('/equipAppraise/getEdit/' + id)
		    	    					});
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
										var url =format_url('/equipAppraise/deleteOnlyOne/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											equipAppraiseDatatables.draw(false);
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
									A.loadPage({
										render : '#page-container',
										url : format_url("/equipAppraise/getDetail/"+ id)
									});
								}
							}
						}]
						}
					}).render();
					
					$('#btnSearch').on('click',function(){
						conditions=[];
						if($('#equipTopName').val()){
	    					conditions.push({
	        					field: 'TREE.C_TOP_NAME',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$('#equipTopName').val()
	        				});
						}
						if($('#equipName').val()){
	    					conditions.push({
	        					field: 'C_EQUIP_NAME',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$('#equipName').val()
	        				});
						}
						if($('#equipGradePre').val()){
	    					conditions.push({
	        					field: 'C_EQUIP_GRADE_PRE',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#equipGradePre').val()
	        				});
						}
						if($('#equipGradeNow').val()){
	    					conditions.push({
	        					field: 'C_EQUIP_GRADE_NOW',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#equipGradeNow').val()
	        				});
						}
						if(searchStartDate.getValue()){
	    					conditions.push({
	    						field: 'C_EQUIP_APPRAISE_DATE',
	    						fieldType:'DATE',
	    						matchType:'GE',
	    						value:searchStartDate.getValue()
	    					});
						}
						if(searchEndDate.getValue()){
	    					conditions.push({
	    						field: 'C_EQUIP_APPRAISE_DATE',
	    						fieldType:'DATE',
	    						matchType:'LE',
	    						value:searchEndDate.getValue()
	    					});
						}
						if($('#equipAppraisePerson').val()){
	    					conditions.push({
	        					field: 'C_EQUIP_APPRAISE_PERSON',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#equipAppraisePerson').find("option:selected").text()
	        				});
						}
						
						equipAppraiseDatatables.draw();
					});
					
					$('#btnReset').on('click',function(){
						$("#equipTopName").val("");
						$("#equipName").val("");
						$("#equipGradePre").val("");
						$("#equipAppraisePerson").val("");
						$("#equipAppraisePerson").trigger("chosen:updated");
						$("#equipGradePre").trigger("chosen:updated");
						$("#equipGradeNow").val("");
						$("#equipGradeNow").trigger("chosen:updated");
						$("#startAppraiseDate").val("");
						$("#endAppraiseDate").val("");
					});
				});
			});
        </script>
    </body>
</html>