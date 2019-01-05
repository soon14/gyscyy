<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
         <%@ page import="com.aptech.business.component.dictionary.OverhaulPlanStatusEnum"%>
         
<title></title>
<%@ include file="/WEB-INF/views/common/meta.jsp" %>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-12">
                <!-- 下面是人的列表 -->
                <form class="form-horizontal" role="form"  style="margin-right:100px;" id="addForm">
			<input id="id" value="${entity.id }" type="hidden" name="id"/>
			<input id="auditBtn" value="${entity.auditBtn }" type="hidden" name="auditBtn"/>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>审批意见
					</label>
					<div class="col-md-10">
					<textarea id="auditMsg" name="auditMsg" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="64"></textarea>
                	</div>
               </div>
               
               
             <!-- 下面是人的列表 -->
	                <div class="form-group " id="userlistdiv" >
	 					<label class="col-md-2 control-label no-padding-right">
	 					生技部审批人
					    </label>
							<div class="col-sm-10">
							<select multiple="multiple" size="5" name="userList" id="duallist" >
								<c:forEach items="${userList}" var="userList" varStatus="vs">
									<option value="${userList.loginName}">${userList.name}</option>
								</c:forEach>
								<!-- 
								<option value="option6" selected="selected">Option 6</option>
								 -->
							</select>
					         <div class="hr hr-16 hr-dotted"></div>
						</div>
				     </div>
	                <div class="form-group " id="userlistdiv2" >
	 					<label class="col-md-2 control-label no-padding-right">
	 					集控中心审批人
					    </label>
							<div class="col-sm-10">
							<select multiple="multiple" size="5" name="userList2" id="duallist" >
								<c:forEach items="${userList3}" var="userList" varStatus="vs">
									<option value="${userList.loginName}">${userList.name}</option>
								</c:forEach>
							</select>
							<div class="hr hr-16 hr-dotted"></div>
						</div>
				     </div>
	                <div class="form-group " id="userlistdiv3" >
	 					<label class="col-md-2 control-label no-padding-right">
	 					安监处审批人
					    </label>
							<div class="col-sm-10">
							<select multiple="multiple" size="5" name="userList3" id="duallist" >
								<c:forEach items="${userList2}" var="userList" varStatus="vs">
									<option value="${userList.loginName}">${userList.name}</option>
								</c:forEach>
							</select>
<!-- 							<div class="hr hr-16 hr-dotted"></div> -->
						</div>
				     </div>
			    </form>
        </div>
       	
    </div>
    <div style="margin-top: 15px;">
			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal" >
				<i class="ace-icon fa fa-times"></i>
				取消
			</button>
			
			<button id="btnSavePerson" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;" >
				<i class="ace-icon fa fa-floppy-o"></i>
				确定
			</button>
		</div>
</div>
<script type="text/javascript">
	var sureSubmitDatatables;
    jQuery(function ($) {
    	seajs.use(['duallistbox'], function(A){
    	
    			var demo1 = $('select[name="userList"]').bootstrapDualListbox({infoTextFiltered: '<span class="label label-purple label-lg">Filtered</span>'});
				var container1 = demo1.bootstrapDualListbox('getContainer');
				container1.find('.btn').addClass('btn-white btn-info btn-bold');
    			
				var demo2 = $('select[name="userList2"]').bootstrapDualListbox({infoTextFiltered: '<span class="label label-purple label-lg">Filtered</span>'});
				var container2 = demo2.bootstrapDualListbox('getContainer');
				container2.find('.btn').addClass('btn-white btn-info btn-bold');
    			
				var demo3 = $('select[name="userList3"]').bootstrapDualListbox({infoTextFiltered: '<span class="label label-purple label-lg">Filtered</span>'});
				var container3 = demo3.bootstrapDualListbox('getContainer');
				container3.find('.btn').addClass('btn-white btn-info btn-bold');
				
				$('#btnSavePerson').on('click',function(){
					var obj = $("#addForm").serializeObject();
				        
					    var listStr;
						var selectUser=$('[name="userList"]').val();
						if(selectUser==null||selectUser==""){
							alert('请选择审批人!');
							return;
						}
						
						listStr=selectUser.join(",");
						var selectUser2=$('[name="userList2"]').val();
						if(selectUser2==null||selectUser2==""){
							alert('请选择审批人!');
							return;
						}
						listStr +="&"+selectUser2.join(",");
						var selectUser3=$('[name="userList3"]').val();
						if(selectUser3==null||selectUser3==""){
							alert('请选择审批人!');
							return;
						}
						listStr +="&"+selectUser3.join(",");
						obj.userList=listStr;
						
						if($('#auditMsg').val()==null || $('#auditMsg').val()==""){
							alert('请填写审批意见!');
							return;
						}
					
					obj.taskId=$("#taskId").val();
					obj.procInstId=$("#procInstId").val();
					obj.auditMsg=$('#auditMsg').val();
					obj.id= $('#id').val();
					var url="overhaulPlan/auditPass";
					$.ajax({
						url :  format_url("/"+url),
						contentType : 'application/json',
						dataType : 'JSON',
						data : JSON.stringify(obj),
						cache: false,
						type : 'POST',
						success: function(result){
							if(result.result=="success"){
								alert('审批完成');	
								window.scrollTo(0,0);
								listFormDialog.close();
								$("#page-container").load(format_url('/todoTask/list/1/10'));
							}else{
								alert(result.result);
							}
						},
						error:function(v,n){
							alert('审批失败');
						}
					});
					
				});
    	});		
    });
    
</script>
