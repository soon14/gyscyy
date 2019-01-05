<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>	
		<title>500 Error Page</title>
</head>
<body>
	<div class="breadcrumbs ace-save-state" id="breadcrumbs">
		<ul class="breadcrumb">
			<li>
				<i class="ace-icon fa fa-home home-icon"></i>
				<a href="javascript:void(0);" onclick="firstPage()">首页</a>
			</li>

			<li>
				错误页面
			</li>
			<li class="active">Error 500</li>
		</ul><!-- /.breadcrumb -->
	</div>

	<div class="page-content">
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->

				<div class="error-container">
					<div class="well">
						<h1 class="grey lighter smaller">
							<span class="blue bigger-125">
								<i class="ace-icon fa fa-random"></i>
								500
							</span>
							系统出错啦,请联系系统管理员
						</h1>

						<hr />
						<h3 class="lighter smaller">
							岳能小伙伴们在努力
							<i class="ace-icon fa fa-wrench icon-animated-wrench bigger-125"></i>
						</h3>

						<div class="space"></div>

						<div>
							<h4 class="lighter smaller">Meanwhile, try one of the following:</h4>

							<ul class="list-unstyled spaced inline bigger-110 margin-15">
								<li>
									<i class="ace-icon fa fa-hand-o-right blue"></i>
									Read the faq
								</li>

								<li>
									<i class="ace-icon fa fa-hand-o-right blue"></i>
									Give us more info on how this specific error occurred!
								</li>
							</ul>
							<div class="row">
								<div class="col-md-12 ">
									<div class="widget-box collapsed">
										<div class="widget-header">
											<h5 class="widget-title">详细错误信息</h5>
											<div class="widget-toolbar">
												<a href="#" data-action="collapse">
													<i class="ace-icon fa fa-chevron-up"></i>
												</a>
											</div>
										</div>
										<div class="widget-body">
											<div class="widget-main">
												${errorMsg}
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>

						<hr />
						<div class="space"></div>

						<div class="center">
							<a href="javascript:history.back()" class="btn btn-grey">
								<i class="ace-icon fa fa-arrow-left"></i>
								返回
							</a>

							<a href="#" class="btn btn-primary">
								<i class="ace-icon glyphicon glyphicon-home"></i>
								首页
							</a>
						</div>
					</div>
				</div>

				<!-- PAGE CONTENT ENDS -->
			</div><!-- /.col -->
		</div><!-- /.row -->
	</div><!-- /.page-content -->
</body>
</html>