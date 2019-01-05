<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<title>选人树</title>
<%@ include file="/WEB-INF/views/common/meta.jsp" %>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-12">
                <div class="col-sm-4" >
                    <div class="widget-box widget-color-blue2" style="min-height: 550px;">
                        <div class="widget-header">
                            <h5 class="widget-title lighter smaller">组织</h5>
                        </div>
                        <div class="widget-body" style="height:568px; overflow:auto">
                            <div class="widget-main padding-8">
                                <ul  class="ztree" id="uintTree"></ul>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 下面是人的列表 -->
                <div id="userSelectList" class="col-sm-8"  >
                	<div class="page-content" >
			 			<div class="col-xs-12 search-content" style="pad;padding-right: 0;padding-bottom:5px;">
			 				<div class="form-inline" role="form">
								<div class="form-group col-xs-4">
									<label class="" for="searchName">用户名称：</label>
									<input id="searchName" class="form-control" placeholder="请输入用户名" type="text"style="width: 55%;" >
								</div>
								<div class="form-group col-xs-4" >
									<label class="" for="searchLoginName">登录名称：</label>
									<input id="searchLoginName" class="form-control" placeholder="请输入登录名" type="text" style="width:55%;" >
								</div>
								<div class="form-group  col-xs-4">
									<label class="" for="sysDutiesId">职务：</label>
									<div  style="width: 70%;display: inline-block;">
										<select class="chosen-select" id="sysDutiesId" name="sysDutiesId"></select>
									</div>
								</div>
								<div class="form-group col-lg-12 col-md-12 col-sm-12 padding-zero text-right btnSearchBottom">
									<button id="searchUserBtn" class="btn btn-xs btn-primary">
										<i class="glyphicon glyphicon-search"></i>
										查询
									</button>
									<button id="resetUserBtn" class="btn btn-xs btn-primary">
										<i class="glyphicon glyphicon-repeat"></i>
										重置
									</button>								
								</div>
							</div>
			 			</div>
				 		<div class="row" style="margin: 0px;">
				 			<div class="col-xs-12">
								<div class="widget-main no-padding">
								  <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">人员列表</h5>
									<table id="userselected-table" class="table table-striped table-bordered table-hover" style="width:100%;">
										<thead>
											<tr>
												<th style="display:none;">主键</th>
												<th class="center sorting_disabled">
													<label class="pos-rel">
														<input type="checkbox" class="ace" />
														<span class="lbl"></span>
													</label>
												</th>
												<th>用户名称</th>
												<th>登录名称</th>
												<th>角色</th>
												<th>职务</th>
												<th>组织机构</th>
												<th>状态</th>
											</tr>
										</thead>
									</table>
								</div>
       	<div style="margin-top: 30px;">
			<button class="btn btn-xs btn-danger pull-right"  id='cancleBtn'>
				<i class="ace-icon fa fa-times"></i>
				取消
			</button>
			
			<button id="btnSaveSelect" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;" >
				<i class="ace-icon fa fa-floppy-o"></i>
				确定
			</button>
		</div>
							</div>
						</div>
					</div>
                
                </div>
        </div>
    </div>
</div>
<script src="${ctx}/static/js/thirdparty/tree.min.js"></script>
<script src="${ctx}/static/js/thirdparty/bootbox.js"></script>
<script type="text/javascript">
	var unitTree,data=[];
    jQuery(function ($) {
    	var unitId="";
    	seajs.use(['tree','confirm','combobox','combotree','datatables', 'dialog'], function(A){
    		unitTree = new A.tree({
				render: '#uintTree',
				options: {
					treeId: 'uintTree',
					async:{
						enable: true,
						url: format_url("/sysUnit/getUnit"),
						type:"post",
						dataType:"json"
					},
					data: {
						key: {
							name: "name"
						},
						simpleData: {
							enable: true,
							idKey: "id",
							pIdKey: "parentId"
						}
					},
					callback: {
						onAsyncSuccess: function(event, treeId, treeNode, msg){
							var currentTree = this.getZTreeObj(treeId);
							var nodes = currentTree.getNodes();
							currentTree.expandNode(nodes[0],true,true,true);
						},
						onClick: function(event, treeId, treeNode){
							unitId = treeNode.id;
							if(unitId>-1){
								userDatatables.draw();
							}
						},
					},
					view: {
						showLine: true
					}
				}
			}).render();
    		
    		//职务
			var sysDutiesId = new A.combobox({
				render : "#sysDutiesId",
				datasource : ${sysDuties},
				allowBlank: true,
				options : {
					"disable_search_threshold" : 10
				}
			}).render();
    		
			//人员的列表
    		var conditions=[];
			userDatatables = new A.datatables({
				render: '#userselected-table',
				options: {
			        "ajax": {
			            "url": format_url("/sysUser/search"),
			            "contentType": "application/json",
			            "type": "POST",
			            "dataType": "JSON",
			            "data": function (d) {
			            	if($('#searchName').val()||$('#searchLoginName').val()||$('#sysDutiesId').val()){
				            	if(unitId&&unitId>-1){
									conditions.push({
										field:'a.C_UNIT_ID',
										fieldType:'STRING',
										matchType:'EQ',
										value:unitId
									}); 
								}
			            		
			            	}else{
			            		if(unitId&&unitId>-1){
				            		conditions=[];
									conditions.push({
										field:'a.C_UNIT_ID',
										fieldType:'STRING',
										matchType:'EQ',
										value:unitId
									}); 
								}
			            	}
			            	d.conditions = conditions;
			                return JSON.stringify(d);
			              }
			        },
			        multiple : true,
					ordering:true,
					autoWidth:false,
					aLengthMenu: [10],
					columns: [
						{data:"id", "width":"4%","visible": false, orderable: false},   
						{ data:"name", "width":"12%", "height":"30px", orderable: true},
						{ data:"loginName", "width":"16%"},
						{ name:"roleIds",data:"roleName", "width":"16%",
							render : function(data, type, row, meta) {
				        		  if(data!=null &&  data.length>7){
		                                 return "<div title='"+data+"'>"+data.substring(0,7)+"..."+"</div>";  
				        		  }else{
		                                 return data;  
				        		  }
	                   		  } },
						{ name:"dutyIds",data:"dutyName", "width":"16%",
	                   			render : function(data, type, row, meta) {
					        		  if(data!=null &&  data.length>7){
			                                 return "<div title='"+data+"'>"+data.substring(0,7)+"..."+"</div>";  
					        		  }else{
			                                 return data;  
					        		  }
		                   		  } },
						{ name:"unitId",data:"unitName", "width":"24%"},
						{ data:"statusName", name:"status", "width":"12%"}
					],
					optWidth:120,
					
				}
			}).render();
			
			//下面是人员的查询
			$('#searchUserBtn').on('click',function(){
				conditions=[];
				if($('#searchName').val()){
					conditions.push({
						field:'a.C_NAME',
						fieldType:'STRING',
						matchType:'LIKE',
						value:$('#searchName').val()
					});
				}
				if($('#searchLoginName').val()){
					conditions.push({
						'field':'C_LOGIN_NAME',
						'fieldType':'STRING',
						'matchType':'LIKE',
						'value':$('#searchLoginName').val()
					});
				}
				if($('#sysDutiesId').val()){
					conditions.push({
						'field':'c.C_DUTIES_ID',
						'fieldType':'STRING',
						'matchType':'IN',
						'value':$('#sysDutiesId').val()
					});
				}
				userDatatables.draw();
			});
			$('#resetUserBtn').on('click',function(){
				$('#searchName').val('');
				$('#searchLoginName').val('');
				//$('#sysDutiesId').val('');
				//$('#sysDutiesId').trigger("chosen:updated");
				sysDutiesId.setValue('99999999999');
			});
			$('#cancleBtn').on('click',function(){
				$('.widget-body').getNiceScroll().hide();
				$(".bootbox-close-button.close").trigger("click");
			})
			$('#btnSaveSelect').on('click',function(){
				$('.widget-body').getNiceScroll().hide();
				data = userDatatables.getSelectRowDatas();
				var ids = [];
				if(data.length && data.length>0){
					for(var i =0; i<data.length; i++){
						ids.push(data[i].id);
					}
				}
				if(ids.length < 1){
					alert('请选择人员！');
					data=[];
					return;
				}
				var singleSelect = ${singleSelect};
				if(singleSelect=="1"){
					if(ids.length > 1){
						alert('只能选择一人！');
						data=[];
						return;
					}
				}
				$(".bootbox-close-button.close").trigger("click");
			});
			
    	});
    });
    

</script>
<style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
.ztree li span.button.remove {margin-left:2px;margin-right: 2px;background-position: -110px -64px;vertical-align: top;}
</style>