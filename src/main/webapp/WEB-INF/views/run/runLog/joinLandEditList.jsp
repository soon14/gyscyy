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
		<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editRWListForm">
					<input type="hidden" id="rlId" name="rlId" value="${rlId}" />
			    	<input type="hidden" id="unitId" name="unitId" value="${unitId}" />			
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding" id="defectStatistics-print">
                        <h5 class="table-title-withoutbtn header smaller blue lighter" style="margin-bottom:0px;">设备清单</h5>
						<table id="runWayEditDatatables" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>							
								<tr>
									<th style="display:none;">主键</th>
	                                <th>设备类别</th>
	                                <th>设备名称</th>
 									<th>接地线（刀闸）状态</th>
	                                <th>接地线（刀闸）编号</th>	                                  </tr>
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
    			<button id="editRWListBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
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
					var unitId=${unitId};
					var rlId=${rlId};
					var closedPosition="";
					var depositGroup="";
					var jlId="";
					var flag="";
					var runWayEditDatatables = new A.datatables({
						render: '#runWayEditDatatables',
						options: {
					        "ajax": {
					            "url": format_url("/joinLand/editSearch/"+unitId+'?rlId='+rlId),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
			        					field: 'C_UNIT_ID',
			        					fieldType:'INT',
			        					matchType:'EQ',
			        					value:${unitId}
			        				});
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
								      {data: "deviceTypeName",width: "auto",orderable: true,	
								"createdCell": function (td, cellData, rowData, row, col) { 
					        	     if (rowData.tdHide=="show" ) {
					        	       	$(td).attr('rowspan',rowData.rowspanNum);
					        	       	$(td).attr('align','center');
					        	       	var height=rowData.rowspanNum*32;
					        	       	$(td).attr('style','height:'+height+'px;line-height:'+height+'px');
					        	     }else{
					        	    	 $(td).hide();
					        	     }
					        	}, 
								      },
							          {data: "deviceName",width: "auto",orderable: false}
								      ,
								      {width: "20%",orderable: false,
											"render": function(data, type, row){
												flag++;
												jlId+=row.id+",";
												closedPosition+=row.deviceId+",";
												depositGroup+=row.deviceType+",";												
												var starthtml='<select  name="state" id="'+row.id+'_'+row.deviceType+'_closedName,">';
												var html='';
												var selecthtml='';
												if(row.closedName==1){
													selecthtml='<option value="1" selected>已分</><option value="2">已合</>';
												}
												if(row.closedName==2){
													selecthtml='<option value="1" >已分</><option value="2" selected>已合</>';
												}
																						
											//	$(".selector").val(row.closedName); 
											html=starthtml+selecthtml;
												return html ;
								          	}
								          },
								          {width: "20%",orderable: false,
												"render": function(data, type, row){
													var html='<input type="text"  name="code" id="'+row.id+'_'+row.equipType+'_closedPosition," value="'+row.closedPosition+'"></>';
									        	  return html ;
									          	}
									          }						          
							 ],
							 fnInfoCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
								 
							 }
						}
					}).render();
					$('#editRWListForm').validate({
						debug:true,
						rules:  {},
						submitHandler: function (form) {
						     
						  /*   var trList = $("#runWayEditDatatables  tbody").children("tr");
						     for (var i=0;i<trList.length;i++) {
						         var tdArr = trList.eq(i).find("td");
						         var getVal = tdArr.eq(2).find("input[type='radio']:checked").val();
						         alert(getVal);
						     }*/						     
							//添加按钮
							var url = format_url("/joinLand/editList");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editRWListForm").serializeObject();
							obj.closedPosition=closedPosition;
							obj.depositGroup=depositGroup;
							obj.closedName=jlId;
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
						//	obj.code=new Array[obj.code];
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('保存成功');
									listFormDialog.close();

								},
								error:function(v,n){
									alert('保存失败');
								}
							});
						}
					});
					$("#editRWListBtn").on("click", function(){						
						 $("#editRWListForm").submit();
    				});
				});
			});
		/*	function asd(id,name) {
				if(rowflag==""){
					id+=',';
					formdatas+=id;
				}
				if(rowflag!=""){
					if(rowflag!=name){
						id+=',';						
						formdatas+=id;
					}
				}
				rowflag=name;
			}*/

        </script>
    </body>
</html>