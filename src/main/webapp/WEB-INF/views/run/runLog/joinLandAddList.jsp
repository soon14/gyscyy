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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addRWListForm">
			    	<input type="hidden" id="rlId" name="rlId" value="${rlId}" />
			    	<input type="hidden" id="unitId" name="unitId" value="${unitId}" />			    	
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding" id="defectStatistics-print">
                        <h5 class="table-title-withoutbtn header smaller blue lighter" style="margin-bottom:0px;">设备清单</h5>
						<table id="defectStatistics-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>							
								<tr>
									<th style="display:none;">主键</th>
	                                <th>设备类别</th>
	                                <th>设备名称</th>
	                                <th>接地线（刀闸）状态</th>
	                                <th>接地线（刀闸）编号</th>	                                
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
            </form>
        </div>
        <div style="margin-right:100px;">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="saveRWListBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var listFormDialog;
			var formdatas="";
			var rowflag="";
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					var rlId=${rlId};
					var unitId=${unitId};
					var closedPosition="";
					var depositGroup="";
					var flag="";
					var defectStatisticsDatatables = new A.datatables({
						render: '#defectStatistics-table',
						options: {
					        "ajax": {
					            "url": format_url("/joinLand/addSearch/"+unitId),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {					            	
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							checked:false,
							bInfo:false,
							paging:false,
							ordering:true,
							columns: [{data:"unitId", visible:false,orderable:false}, 
								      {data: "equipTypeName",width: "auto",orderable: true							        	   
								      },
							          {data: "name",width: "auto",orderable: false}
								      ,
							          {width: "20%",orderable: false,
										"render": function(data, type, row){
											flag++;
											var html='<select  name="state" id="'+row.id+'_'+row.equipType+'_closedName,"><option value="1">已分</><option value="2">已合</></>';
											closedPosition+=row.id+",";
											depositGroup+=row.equipType+",";
											return html ;
							          	}
							          },
							          {width: "20%",orderable: false,
											"render": function(data, type, row){
												var html='<input type="text"  name="code" id="'+row.id+'_'+row.equipType+'_closedPosition,"></>';
								        	  return html ;
								          	}
								          }
							          
							 ],
							 fnInfoCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
								 
							 }
						}
					}).render();
					
					$('#addRWListForm').validate({
						debug:true,
						rules:  {},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/joinLand/insertList");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addRWListForm").serializeObject();							
							obj.closedPosition=closedPosition;
							obj.depositGroup=depositGroup;
							if(flag==1){
								var _stateArray = new Array();
								_stateArray[0]=$("select[name='state']").val();    
							//	var _TheArray = new Array($("select[name='state']").val());
								obj.state=_stateArray;
								var _codeArray = new Array();
								_codeArray[0]=$("input[name='code']").val();    
								obj.code=_codeArray;
							}

						//	obj.state = $("select[name='state']").val().split(",");						
						//	obj.code = $("input[name='code']").val().split(",");
						//	obj.rowspanNum=formdatas;
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('添加成功');
									listFormDialog.close();
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					$("#saveRWListBtn").on("click", function(){
						$("#addRWListForm").submit();
    				});
				});
			});
			function asd(id,name) {
				if(rowflag==""){
					formdatas+=id;
				}
				if(rowflag!=""){
					if(rowflag!=name){
						formdatas+=id;
					}
				}
				rowflag=name;
			}
        </script>
    </body>
</html>