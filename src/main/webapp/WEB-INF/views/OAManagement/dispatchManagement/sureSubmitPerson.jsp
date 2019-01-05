<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<title></title>
<%@ include file="/WEB-INF/views/common/meta.jsp" %>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-12">
                <!-- 下面是人的列表 -->
                <div class="form-group">

						<div class="col-sm-12">
							<select multiple="multiple" size="10" name="duallistbox_demo1[]" id="duallist" >
							<c:forEach items="${userList}" var="userList" varStatus="vs">
									<option value="${userList.loginName}">${userList.name}</option>
								</c:forEach>
								<!-- 
								<option value="option6" selected="selected">Option 6</option>
								 -->
							</select>
						</div>
			     </div>
			     <div class="form-group">
						<div class="col-md-1 no-padding-right; ">
						</div>
						<div class="col-md-4">
							1.右侧对话框显示的人员为选择的人
						</div>
						<div class="col-md-1 no-padding-right; ">
						</div>
						<div class="col-md-6">
							<div i>2.单击箭头为全选</div>
						</div>
					</div>
					 <div class="form-group">
						<div class="col-md-1 no-padding-right; ">
						</div>
						<div class="col-md-4">
							3.单击对左侧话框中的人名追加人员
						</div>
						<div class="col-md-1 no-padding-right; ">
						</div>
						<div class="col-md-6">
							<div >4.单击对右侧话框中的人名删除人员</div>
						</div>
					</div>
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
    		 	var demo1 = $('select[name="duallistbox_demo1[]"]').bootstrapDualListbox({infoTextFiltered: '<span class="label label-purple label-lg">Filtered</span>'});
				var container1 = demo1.bootstrapDualListbox('getContainer');
				container1.find('.btn').addClass('btn-white btn-info btn-bold');
			
				$('#btnSavePerson').on('click',function(){
					var selectUser=$('[name="duallistbox_demo1[]"]').val();
					if( selectUser == null|| selectUser==""){
						alert('请选择审批人!');
						return;
					}else{
						var selectOption = $('[name="duallistbox_demo1[]').find("option:selected");
						var selectUserName = "";
						for (var i=0;i< selectOption.length; i++) {
							selectUserName += selectOption[i].text;
							if (i != selectOption.length -1) {
								selectUserName += "&&";
							}
						}
						selectUser.push(selectUserName);
						submitUserDialog.close(selectUser);
					} 
					
				});
    	});		
    });
    
</script>
