<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="widget-box widget-color-green2" style="min-height: 600px;"  id="menuEdit">
	<div class="widget-header">
	    <h5 class="widget-title lighter smaller">
	        编辑
	    </h5>
	</div>

	<div class="widget-body">
		<div id="editBody" class="widget-main padding-8">
			<form id="unitForm" class="form-horizontal" role="form" style="margin-right:100px;">
				<input placeholder="" id="id"  name="id" type="hidden" class="col-md-12" value="${unit.id}">
				<input placeholder="" id="status"  name="status" type="hidden" class="col-md-12" value="${unit.status}">					
				<div class="form-group">
					<div class="col-md-4">
						<input placeholder="" id="parentId"  name="parentId" type="hidden" class="col-md-12" value="${unit.parentId}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>编码</label>
					<div class="col-md-4">
						<input placeholder="" id="code"  name="code" type="text" value="${unit.code}" class="col-md-12">
					</div>
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>名称</label>
					<div class="col-md-4">
						<input placeholder="" id="name"  name="name" type="text" class="col-md-12" value="${unit.name}">
					</div>									
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">序号</label>
					<div class="col-md-4">
						<input placeholder="" id="order"  name="order" type="text" class="col-md-12" value="${unit.order}">
					</div>		
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>层级</label>
					<div class="col-md-4">
						<select id="levelDiv" class="col-md-12" name="level"></select>
					</div>													
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">机构类型</label>
					<div class="col-md-4">
						<select id="organizationDiv" class="col-md-12" name="organization"></select>
					</div>		
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">备注</label>
					<div class="col-md-10">
						<textarea id="remark" name="remark"  style="width:100%; height:150px;resize:none;">${unit.remark}</textarea>
					</div>
				</div>
				<div class="form-group">
					<div  class="col-sm-offset-10 col-sm-1">
						<button id="btnSave" type="submit" class="btn btn-sm btn-success" style="float:right;">
							<i class="ace-icon fa fa-floppy-o"></i>
							保存
						</button>
					</div>
				</div>	
			</form>
        </div>
    </div>
</div>
<script type="text/javascript">
seajs.use(['combobox','combotree'], function(A){
	//组织机构层级
	var unitLevelCombobox = new A.combobox({
		render: "#levelDiv",
		datasource:${unitLevel},
		options:{
			"disable_search_threshold":10
		}
	}).render();
	unitLevelCombobox.setValue(${unit.level});
	//组织机构类型
	var organizationCombobox = new A.combobox({
		render: "#organizationDiv",
		datasource:${organization},
		options:{
			"disable_search_threshold":10
		}
	}).render();
	organizationCombobox.setValue(${unit.organization});
	
	

	$('#unitForm').validate({
		errorElement: 'div',
		errorClass: 'help-block',
		focusInvalid: false,
		rules: {
			name: {
				required: true,
				maxlength: 30
			},
			code: {
				required: true,
				maxlength: 30
			},
			/* type:{
				required:true
			}, */
			/* order:{
				digits:true
			}, */
			level: {
				required: true,
			}
		},

		submitHandler: function (form) {
			var url =format_url("/unit/" + $("#id").val());
			var obj = $("#unitForm").serializeObject();
			if(obj.code){
				var patrn =  /^[A-Za-z0-9.(){};@_-]+$/;
				if(!patrn.exec(obj.code)){
					alert("编码禁止输入汉字！");
					return;
				}
			}
			$.ajax({
				url : url,
				contentType : 'application/json',
				dataType : 'JSON',
				data : JSON.stringify(obj),
				cache: false,
				type : 'POST',
				success: function(result){
					alert('修改成功');
					unitTree._dropDownTree.reAsyncChildNodes(null, "refresh");
				},
				error:function(v,n){
					alert('修改失败');
				}
			});
		}
	});
});
</script>