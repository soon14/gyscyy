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
			<div class="col-lg-12 col-md-12 col-sm-12 search-content">
				<div class="form-inline text-right" role="form">
                    <div class="clearfix">								                      
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
						<label class="" for="form-field-1">单位名称：</label>
	                    <div id="dissearchunitIdDiv" class="input-width text-left"></div>
                   </div>
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
						<label class="" for="form-field-1">调度：</label>
                            <div class="form-group  input-width padding-zero  text-left">						
				    	<select id="seldispathDiv" class="col-md-12" name="dispath"></select>					    
                   </div>
                   </div>  
                   <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
						<label class="" for="form-field-1">时间：</label>
		             <div class="form-group date-input padding-zero text-left">					
                    	<div id="searchStarttimeDiv"  style="border:none; padding:0px;"></div>
                     
                     </div>   <label style="width:2.6%;text-align:center">~</label>                      
              <div class="form-group date-input padding-zero text-left">                     
                        <div id="searchEndtimeDiv"   style="border:none; padding:0px;"></div>
                  </div>
                   </div>                 
                   
                   </div>
					<div class="clearfix">                    
                   <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
						<label class="" for="form-field-1">受令人：</label>
                    <div class="form-group  input-width padding-zero  text-left">												
				    <input id="dutyChiefPersonDiv" name="dutyChiefPerson" class="input-width text-left" placeholder="请输入受令人" type="text" >				    					    				    
                   </div>
                   </div>  	
                   <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
                            <label class="" for="searchdepict">发令人：</label>
                            <input id="searchdispatchPerson" class="input-width text-left" placeholder="请输入发令人" type="text" >
                        </div> 				    
                   <div class="form-group col-lg-4 col-md-4 col-sm-6 col-xs-12">
                            <label class="" for="searchdepict">联系内容：</label>
                            <input id="contactContent" class="input-width text-left" placeholder="请输入联系内容" type="text" >
                        </div> 
                   <div class="form-group col-lg-2 col-md-2 col-sm-6 col-xs-12" style="text-align: center">                                                                                            
                   <div class="form-group" style="float:right; ">
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
						<h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">调度命令列表</h5>
						<table id="dispaCom-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>时间</th>
	                                <th>单位名称</th>
	                                <th>调度</th>
	                                <th>发令人</th>
	                                <th>受令人</th>
	                                <th>联系内容</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
         <div style="margin-right:100px;margin-top:10px;">
		        		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal" >
							<i class="ace-icon fa fa-times"></i>
							取消
						</button>
		    			<button id="selectBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				确定
		    			</button>
		    		</div>
		    		<div  class="form-group" style="height:20px;"></div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var listFormDialog,data=[];
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
					var searchunitId = new A.combotree({
						render: "#dissearchunitIdDiv",
						name: "searchunitId",
						//返回数据待后台返回TODO
						datasource: ${dispaComTreeList},
						width:"210px",
						options: {
							treeId: 'searchunitId',
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
					
// 					var dutyChiefIdCombobox = new A.combobox({
// 						render: "#dutyChiefPersonDiv",
// 						datasource:${dispaComCombobox},
// 						//multiple为true时select可以多选
// 						multiple:false,
// 						//allowBlank为false表示不允许为空
// 						allowBlank: true,
// 						options:{
// 							"disable_search_threshold":10,
// 							"width":"100%"	
// 						}
// 					}).render();
// 					var dispathCombobox = new A.combobox({
// 						render: "#seldispathDiv",
// 						datasource:${dispatchCombobox},
// 						//multiple为true时select可以多选
// 						multiple:false,
// 						//allowBlank为false表示不允许为空
// 						allowBlank: true,
// 						options:{
// 							"disable_search_threshold":10,
// 							"width":"100%"	
// 						}
// 					}).render();
					//请将下行表格标题标签移到table上一行
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
							 aLengthMenu: [10],
							columns: [{data:"id", visible:false,orderable:true}, 
							          {data: "time",width: "15%",orderable: true}, 
							          {data: "unitName",width: "15%",orderable: true}, 
							          {data: "dispathName",width: "8%",orderable: true}, 
							          {data: "dispatchPerson",width: "20%",orderable: true}, 
							          {data: "dutyChiefPerson",width: "7%",orderable: true}, 
							          {data: "contactContent",width: "30%",orderable: true}],
						}
					}).render();
					$("#selectBtn").on("click", function(){
						data = dispaComDatatables.getSelectRowDatas();
						var ids = [];
						if(data.length && data.length>0){
							for(var i =0; i<data.length; i++){
								ids.push(data[i].id);
							}
						}
						if(ids.length < 1){
							alert('请选择调度命令');
							data=[];
							return;
						}
						if(ids.length > 1){
							alert('只能选择一个调度命令');
							data=[];
							return;
						}
 						$(".bootbox-close-button.close").trigger("click");
    				});
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
						if($('#dutyChiefPersonDiv').val()){
	    					conditions.push({
	        					field: 'a.dutyChiefPerson',
	        					fieldType:'INT',
	        					matchType:'EQ',
	        					value:$('#dutyChiefPersonDiv').val()
	        				});
						}
						if(searchunitId.getValue()!=undefined&&searchunitId.getValue()+""!=""){
	    					conditions.push({
	        					field: 'a.C_UNIT_ID',
	        					fieldType:'INT',
	        					matchType:'EQ',
	        					value:searchunitId.getValue()
	        				});
						}
						if($('#searchdispatchPerson').val()){
	    					conditions.push({
	        					field: 'a.C_DISPATCH_PERSON',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$('#searchdispatchPerson').val()
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
						searchunitId.setValue(undefined);
						$("#seldispathDiv").val("");
						$("#seldispathDiv").trigger("chosen:updated");
						$("#dutyChiefPersonDiv").val("");
						$("#searchStartfindTime").val("");
						$("#searchEndfindTime").val("");
						$("#searchdispatchPerson").val("");		
						$("#contactContent").val("");						
					});
				});
			});
        </script>
    </body>
</html>