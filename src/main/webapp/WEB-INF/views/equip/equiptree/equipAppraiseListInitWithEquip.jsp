<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
            <div class="col-lg-12 col-md-12 col-sm-12 search-content">
				<div class="form-inline text-right" role="form">
                    <div class="clearfix" style="width: 95%">
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
	                        <label class="" for="equipAppraisePerson">设备评价人：</label>
	                        <div class="padding-zero input-width  text-left">
	                       		 <select id="equipAppraisePerson" class="form-control chosen-select" name="equipAppraisePerson"></select>
	                        </div>
	                    </div>
	                 </div>
	                 <div class="clearfix" style="width: 95%">
					<div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
							<label class="" for="form-field-1"  style="width:72px">设备评价日期</label>：
							<div class="form-group date-input padding-zero text-left">
	                               <div id="startAppraiseDate"  style="border:none; padding:0px;"></div>
	                           </div>
	                        <label style="width: 2.6%;text-align: center">~</label>
	                        <div class="form-group date-input padding-zero text-left">
	                               <div id="endAppraiseDate"   style="border:none; padding:0px;"></div>
	                          </div>
	                   </div>
                        </div>
                        <div class="clearfix">
                        <div class="form-group col-lg-2 col-md-2 col-sm-12 col-xs-12" style="float: right;">
                            <button id="btnSearchForEquipAppraise" class="btn btn-xs btn-primary" style="overflow: hidden;width: 48%;max-width: 54px">
                                <i class="glyphicon glyphicon-search"></i>
                               		 查询
                            </button>
                            <button id="btnResetEquipAppraise" class="btn btn-xs btn-primary"style="overflow: hidden;width: 48%;max-width: 54px" type="button">
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
						<h5 class='table-title header smaller blue' style="margin-bottom:0px!important;">设备评价</h5>
						<table id="equipAppraise_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
<!-- 									<th class="center sorting_disabled" style="width:50px;"> -->
<!--         								<label class="pos-rel"> -->
<!--         									<input type="checkbox" class="ace" /> -->
<!--         									<span class="lbl"></span> -->
<!--         								</label> -->
<!--         							</th> -->
	                                <th>设备编号</th>
	                                <th>设备名称</th>
	                                <th>设备评级</th>
	                                <th>评定或变动等级依据和原因</th>
	                                <th>现在存在主要缺陷及其影响</th>
	                                <th>设备评价日期</th>
	                                <th>设备评价人</th>
	                                <th>备注</th>
<!--                                     <th>操作</th> -->
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					var equipIdJson = JSON.parse('${equipIdJson}');
					var equipIdArray = [];
					var equipIdString = "";
					for(i in equipIdJson){
						equipIdArray.push(equipIdJson[i].code);
					}
					if(equipIdArray.length>0){
						equipIdString = equipIdArray.join(",");
					}
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
						id: 'startAppraiseDateId',
						name:'equipAppraiseDate',
						render:'#startAppraiseDate',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					
					//终了时间
					var searchEndDate = new A.my97datepicker({
						id: 'endAppraiseDateId',
						name:'equipAppraiseDate',
						render:'#endAppraiseDate',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
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
					            "url": format_url("/equiptree/equipAppraiseListInitWithEquip"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
		            					field: 'R.C_EQUIP_CODE',
		            					fieldType:'STRING',
		            					matchType:'IN',
		            					value : equipIdString
		            				});
					            	conditions.push({
										field: 'T.C_STATUS',
			        					fieldType:'INT',
			        					matchType:'EQ',
			        					value:1
			        				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					            }
					            	
					              },multiple : true,
									ordering: true,
									checked:false,
									optWidth: 80,
									columns: [{data:"id", visible:false,orderable:false}, 
									          {data: "equipLedgerCode",width: "11%",orderable: true}, 
									          {data: "equipLedgerName",width: "12%",orderable: true}, 
									          {name:"equipGradeNow",data: "equipGradeNowName",width: "5%",orderable: true}, 
									          {data: "equipAppraiseReason",width: "15%",orderable: true}, 
									          {data: "equipAppraiseInfluence",width: "15%",orderable: true}, 
									          {data: "equipAppraiseDate",width: "8%",orderable: true}, 
									          {data: "equipAppraisePerson",width: "8%",orderable: true},
									          {data: "equipAppraiseComment",width: "15%",orderable: true}],
					        },
					        
					}).render();
					$('#btnSearchForEquipAppraise').on('click',function(){
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
	        					field: 'B.C_NAME',
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
						equipAppraiseDatatables._datatables.search(JSON.stringify(conditions)).draw();
					}); 
					$('#btnResetEquipAppraise').on('click',function(){
						$("#equipTopName").val("");
						$("#equipName").val("");
						$("#equipGradePre").val("");
						$("#equipAppraisePerson").val("");
						$("#equipAppraisePerson").trigger("chosen:updated");
						$("#equipGradePre").trigger("chosen:updated");
						$("#equipGradeNow").val("");
						$("#equipGradeNow").trigger("chosen:updated");
						$("#startAppraiseDateId").val("");
						$("#endAppraiseDateId").val("");
					});
				});
			});
        </script>
    </body>
</html>