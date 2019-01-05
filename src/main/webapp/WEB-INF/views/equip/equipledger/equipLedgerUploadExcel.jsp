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
				<li>
					台账管理
				</li>
				<li class="active">设备导入</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<form id="uploadform"  action="${ctx}/equipLedger/importEquipledger" enctype="multipart/form-data" method="post">
			<div class="col-xs-12 search-content">
				<div class="form-inline" role="form">
				   <div class="form-group">
		                <input id="upload"  name="filename"  type="file" >
                   </div>
                   <div class="form-group" style="float:right; margin-right:30px;">
						<button id="upLoad" class="btn btn-xs btn-primary" type="submit">
							<i class="glyphicon glyphicon-repeat"></i>
							上传
						</button>								
					</div>
                </div>
            </div>
            </form>
        </div>
    </body>
</html>