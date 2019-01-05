<%@page import="com.aptech.common.system.dictionary.web.SysDictionaryVO"%>
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
					两票管理
				</li>
				<li class="active">两票统计</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-xs-12 search-content padding-zero ">
				<div class="form-inline text-left" role="form">
					 <div class="clearfix">
						<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
							<label class="searchLabel" for="form-field-1">单位名称</label>：
							 <div id="searchunitDiv" class="inputWidth text-left padding-zero"></div>
						</div>
	 					<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
	 						<label class="searchLabel" for="form-field-1">统计方式</label>：
	 						<div class="form-groupp adding-zero inputWidth  text-left">
	 							<select id="DateType" name="DateType"></select>
	 						</div>
	                   </div>
	                   <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
							<label class="searchLabel" for="form-field-1">统计时间</label>：
						<div class="form-group dateInputOther padding-zero text-left">
							<div id="searchstatisticsDateDiv"  style="border:none; padding:0px;width: 250px;"></div>
						</div>
						</div>
						<div class="orm-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero btnSearchBottom"style="text-align:right;">
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
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<h5 class="table-title-withoutbtn header smaller blue " style="margin-bottom:0px;" id="table_title">
						<label id="table_titile">“两票”情况统计表</label>
						<div style="float:right; margin-right:40px;">
							<button id="btnimport" class="btn btn-xs btn-primary" style="margin-right: -40px">
								<i class="ace-icon glyphicon glyphicon-download"></i>
								导出
							</button>
						</div>
					</h5>
					<div id="displayTable"  style="width: 1710px; ">
					<div class="widget-main no-padding" id="defectStatistics-print-one" style="float:left;width:160px;  ">
					<table id="ticketStatisticsTableOne" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th rowspan="2" style="text-align:center;min-width:160px;">票据种类</th>
									<th colspan="1" style="text-align:center;border: none;">&nbsp;</th>
								</tr>
								<tr>
									<th style="text-align:center;max-width:1px;border-right: none;">&nbsp;</th>
								</tr>
							</thead>
					</table>
					</div>
					<div class="widget-main no-padding" id="defectStatistics-print" style="overflow-x:scroll; height:500px;width:1550px;float: left;">
						<table id="ticketStatisticsTable" class="table table-striped table-bordered table-hover" style="width:2600px;">
							<thead>
								<tr>
									<!-- <th rowspan="2" style="text-align:center;min-width:160px">票据种类</th> -->
									<th colspan="5" style="text-align:center">1月</th>
									<th colspan="5" style="text-align:center">2月</th>
									<th colspan="5" style="text-align:center">3月</th>
									<th colspan="5" style="text-align:center">4月</th>
									<th colspan="5" style="text-align:center">5月</th>
									<th colspan="5" style="text-align:center">6月</th>
									<th colspan="5" style="text-align:center">7月</th>
									<th colspan="5" style="text-align:center">8月</th>
									<th colspan="5" style="text-align:center">9月</th>
									<th colspan="5" style="text-align:center">10月</th>
									<th colspan="5" style="text-align:center">11月</th>
									<th colspan="5" style="text-align:center">12月</th>
									<th colspan="5" style="text-align:center">全年合计</th>
								</tr>
								<tr>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
								</tr>
							</thead>
						</table>
					</div>
					</div>
					<div class="widget-main no-padding" id="defectStatistics-print1" style="overflow-x:scroll; height:500px;display: none">
						<table id="ticketStatisticsMonthTable" class="table table-striped table-bordered table-hover" style="width:2600px;">
							<thead>
								<tr>
									<!-- <th rowspan="2" style="text-align:center;min-width:160px">票据种类</th> -->
									<th colspan="5" style="text-align:center">1日</th>
									<th colspan="5" style="text-align:center">2日</th>
									<th colspan="5" style="text-align:center">3日</th>
									<th colspan="5" style="text-align:center">4日</th>
									<th colspan="5" style="text-align:center">5日</th>
									<th colspan="5" style="text-align:center">6日</th>
									<th colspan="5" style="text-align:center">7日</th>
									<th colspan="5" style="text-align:center">8日</th>
									<th colspan="5" style="text-align:center">9日</th>
									<th colspan="5" style="text-align:center">10日</th>
									<th colspan="5" style="text-align:center">11日</th>
									<th colspan="5" style="text-align:center">12日</th>
									<th colspan="5" style="text-align:center">13日</th>
									<th colspan="5" style="text-align:center">14日</th>
									<th colspan="5" style="text-align:center">15日</th>
									<th colspan="5" style="text-align:center">16日</th>
									<th colspan="5" style="text-align:center">17日</th>
									<th colspan="5" style="text-align:center">18日</th>
									<th colspan="5" style="text-align:center">19日</th>
									<th colspan="5" style="text-align:center">20日</th>
									<th colspan="5" style="text-align:center">21日</th>
									<th colspan="5" style="text-align:center">22日</th>
									<th colspan="5" style="text-align:center">23日</th>
									<th colspan="5" style="text-align:center">24日</th>
									<th colspan="5" style="text-align:center">25日</th>
									<th colspan="5" style="text-align:center">26日</th>
									<th colspan="5" style="text-align:center">27日</th>
									<th colspan="5" style="text-align:center">28日</th>
									<th colspan="5" style="text-align:center">29日</th>
									<th colspan="5" style="text-align:center">30日</th>
									<th colspan="5" style="text-align:center">31日</th>
									<th colspan="5" style="text-align:center">全月合计</th>
								</tr>
								<tr>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">未鉴定</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率%</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
		<form id="downloadForm" class="hide" method="post" action="${ctx}/ticketStatistics/exprotExcel">
			<input type="hidden" name="name" id="downloadName">
			<input type="hidden" name="path" id="downloadPath">
		</form>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var yearTextLocal;
		    var searchstatisticsDate;
		    var dataTablesOne;
		    var dataTables;
		    var month;
		  	var url;
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					 
					//统计方式
					var DateType = new A.combobox({
							id:'DateType',
							name:'DateType',
							render : "#DateType",
							datasource : ${DateUtil},
							allowBlank: true,
							width:20,
							options : {
								"disable_search_threshold" : 10
							}
						}).render();
					//统计时间
					searchstatisticsDate = new A.my97datepicker({
						id: 'searchstatisticsDate',
						name:'searchstatisticsDate',
						render:'#searchstatisticsDateDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy"
						}
					}).render();
					
					//单位名称下拉树
					var searchunitId = new A.combotree({
						render: "#searchunitDiv",
						name: 'searchunitId',
						//返回数据待后台返回TODO
						datasource: ${unitNameIdTreeList},
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

					//发送ajax请求参数取得方法，返回选择值
					function getAjaxParam () {
						//统计时间
						var searchTime = $("#searchstatisticsDate").val();
						//单位名称
						var unitId = searchunitId.getValue();
						if (unitId == undefined) {
							unitId = ""
						}
						//统计方式
						var SearchDateType = DateType.getValue();
						var param = {};
						param.searchTime = searchTime;
						param.unitId = unitId;
						param.searchDateType = SearchDateType;
						return param;
					}
					var param = getAjaxParam ();
					
					//++++++++++
					dataTablesOne = new A.datatables({
						render: '#ticketStatisticsTableOne',
						options: {
							"ajax": {
								"url": format_url("/ticketStatistics/searchData"),
								"contentType": "application/json",
								"type": "POST",
								"dataType": "JSON",
								"data": function (d) {
									d.param = param;
									return JSON.stringify(d);
								}
							},
							multiple : true,
							checked:false,
							bInfo:false,
							paging:false,
							ordering:false,
							columns: [{data: "ticketTypeName",name:"ticketTypeName",width: "auto",orderable: false ,"sClass": "center"}
							]
						}
					}).render();
					
					//++++++++++++
					
					dataTables = new A.datatables({
						render: '#ticketStatisticsTable',
						options: {
							"ajax": {
								"url": format_url("/ticketStatistics/searchData"),
								"contentType": "application/json",
								"type": "POST",
								"dataType": "JSON",
								"data": function (d) {
									d.param = param;
									return JSON.stringify(d);
								}
							},
							multiple : true,
							checked:false,
							bInfo:false,
							paging:false,
							ordering:false,
							columns: [
							          /* {data: "ticketTypeName",name:"ticketTypeName",width: "auto",orderable: false ,"sClass": "center"
				               	}, */
									{data: "ticketCount_1",width: "auto",orderable: false,"sClass": "center",
				               		"render":function(data, type, row, meta){
				               			if(row.ticketTypeName=="合计"){
				               				return "<span >"+data+"</span>"
				               			}else {
			        	 				return "<a href='#' onclick='searchTicketCount_1("+row.ticketType+")'>"+data+"</a>"
				               			}
			        	 			}
				               	},
									{data: "identify_1",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchIdentify_1("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}
				               	},
									{data: "qualifiedCount_1",width: "auto",orderable: false,"sClass": "center",
				               		"render":function(data, type, row, meta){
				               			if(row.ticketTypeName=="合计"){
				               				return "<span >"+data+"</span>"
				               			}else {
			        	 				return "<a href='#' onclick='searchQualifiedCount_1("+row.ticketType+")'>"+data+"</a>"
				               			}
			        	 			}}, 
									{data: "unQualifiedCount_1",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchUnQualifiedCount_1("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "qualifiedRate_1",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_2",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCount_2("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_2",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentify_2("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_2",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCount_2("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_2",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCount_2("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_2",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_3",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCount_3("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_3",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentify_3("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_3",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchQualifiedCount_3("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}}, 
									{data: "unQualifiedCount_3",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchUnQualifiedCount_3("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedRate_3",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_4",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCount_4("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_4",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentify_4("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_4",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCount_4("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_4",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCount_4("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_4",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_5",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCount_5("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_5",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentify_5("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_5",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCount_5("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_5",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCount_5("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_5",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_6",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCount_6("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_6",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentify_6("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_6",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCount_6("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_6",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCount_6("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_6",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_7",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCount_7("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_7",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentify_7("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_7",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCount_7("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_7",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCount_7("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_7",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_8",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCount_8("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_8",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentify_8("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_8",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCount_8("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_8",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCount_8("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_8",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_9",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCount_9("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_9",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentify_9("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_9",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCount_9("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_9",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCount_9("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_9",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_10",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCount_10("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_10",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentify_10("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_10",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCount_10("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_10",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCount_10("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_10",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_11",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCount_11("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_11",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentify_11("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_11",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCount_11("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_11",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCount_11("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_11",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_12",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCount_12("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_12",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentify_12("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_12",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCount_12("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_12",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCount_12("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_12",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_13",width: "auto",orderable: false,"sClass": "center"},
									{data: "identify_13",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedCount_13",width: "auto",orderable: false,"sClass": "center"}, 
									{data: "unQualifiedCount_13",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedRate_13",width: "auto",orderable: false,"sClass": "center"},
							],
							
						}
					}).render();
					
					dataMonthTables = new A.datatables({
						render: '#ticketStatisticsMonthTable',
						options: {
							"ajax": {
								"url": format_url("/ticketStatistics/searchMonthData"),
								"contentType": "application/json",
								"type": "POST",
								"dataType": "JSON",
								"data": function (d) {
									d.param = param;
									return JSON.stringify(d);
								}
							},
							multiple : true,
							checked:false,
							bInfo:false,
							paging:false,
							ordering:false,
							columns: [
							         /*  {data: "ticketTypeName",width: "auto",orderable: false ,"sClass": "center"}, */
// 									{data: "ticketCount_0",width: "auto",orderable: false,"sClass": "center",
// 					               		"render":function(data, type, row, meta){
// 				        	 				return "<a href='#' onclick='searchTicketCountMon_1("+row.ticketType+")'>"+data+"</a>"
// 				        	 			}},
// 									{data: "identify_0",width: "auto",orderable: false,"sClass": "center",
// 						               		"render":function(data, type, row, meta){
// 					        	 				return "<a href='#' onclick='searchIdentifyMon_1("+row.ticketType+")'>"+data+"</a>"
// 					        	 			}},
// 									{data: "qualifiedCount_0",width: "auto",orderable: false,"sClass": "center",
// 							               		"render":function(data, type, row, meta){
// 						        	 				return "<a href='#' onclick='searchQualifiedCountMon_1("+row.ticketType+")'>"+data+"</a>"
// 						        	 			}}, 
// 									{data: "unQualifiedCount_0",width: "auto",orderable: false,"sClass": "center",
// 								               		"render":function(data, type, row, meta){
// 							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_1("+row.ticketType+")'>"+data+"</a>"
// 							        	 			}},
// 									{data: "qualifiedRate_0",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_1",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_1("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_1",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
						               				
						               				return "<a href='#' onclick='searchIdentifyMon_1("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 				
					        	 			}},
									{data: "qualifiedCount_1",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCountMon_1("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_1",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_1("+row.ticketType+")'>"+data+"</a>"
							        	 			}},
									{data: "qualifiedRate_1",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_2",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_2("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_2",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentifyMon_2("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_2",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCountMon_2("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_2",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_2("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_2",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_3",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_3("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_3",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchIdentifyMon_3("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "qualifiedCount_3",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchQualifiedCountMon_3("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}}, 
									{data: "unQualifiedCount_3",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_3("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "qualifiedRate_3",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_4",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_4("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_4",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentifyMon_4("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_4",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCountMon_4("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_4",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_4("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_4",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_5",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_5("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_5",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentifyMon_5("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_5",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCountMon_5("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_5",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_5("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_5",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_6",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_6("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_6",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentifyMon_6("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_6",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCountMon_6("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_6",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_6("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_6",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_7",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_7("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_7",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentifyMon_7("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_7",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCountMon_7("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_7",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_7("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_7",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_8",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_8("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_8",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentifyMon_8("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_8",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCountMon_8("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_8",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_8("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_8",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_9",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_9("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_9",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentifyMon_9("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_9",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCountMon_9("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_9",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_9("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_9",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_10",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_10("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_10",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentifyMon_10("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_10",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCountMon_10("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_10",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_10("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_10",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_11",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_11("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_11",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentifyMon_11("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_11",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCountMon_11("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_11",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_11("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_11",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_12",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_12("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_12",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentifyMon_12("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_12",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCountMon_12("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_12",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_12("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_12",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_13",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_13("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_13",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentifyMon_13("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_13",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCountMon_13("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_13",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_13("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_13",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_14",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_14("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_14",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentifyMon_14("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_14",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCountMon_14("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_14",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_14("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_14",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_15",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_15("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_15",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentifyMon_15("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_15",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCountMon_15("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_15",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_15("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_15",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_16",width: "auto",orderable: false,"sClass": "center",
										               		"render":function(data, type, row, meta){
										               			if(row.ticketTypeName=="合计"){
										               				return "<span >"+data+"</span>"
										               			}else {
									        	 				return "<a href='#' onclick='searchTicketCountMon_16("+row.ticketType+")'>"+data+"</a>"
										               			}
									        	 			}},
									{data: "identify_16",width: "auto",orderable: false,"sClass": "center",
											               		"render":function(data, type, row, meta){
											               			if(row.ticketTypeName=="合计"){
											               				return "<span >"+data+"</span>"
											               			}else {
										        	 				return "<a href='#' onclick='searchIdentifyMon_16("+row.ticketType+")'>"+data+"</a>"
											               			}
										        	 			}},
									{data: "qualifiedCount_16",width: "auto",orderable: false,"sClass": "center",
												               		"render":function(data, type, row, meta){
												               			if(row.ticketTypeName=="合计"){
												               				return "<span >"+data+"</span>"
												               			}else {
											        	 				return "<a href='#' onclick='searchQualifiedCountMon_16("+row.ticketType+")'>"+data+"</a>"
												               			}
											        	 			}}, 
									{data: "unQualifiedCount_16",width: "auto",orderable: false,"sClass": "center",
													               		"render":function(data, type, row, meta){
													               			if(row.ticketTypeName=="合计"){
													               				return "<span >"+data+"</span>"
													               			}else {
												        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_16("+row.ticketType+")'>"+data+"</a>"
													               			}
												        	 			}},
									{data: "qualifiedRate_16",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_17",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_17("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_17",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentifyMon_17("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_17",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCountMon_17("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_17",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_17("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_17",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_18",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_18("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_18",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentifyMon_18("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_18",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCountMon_18("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_18",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_18("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_18",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_19",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_19("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_19",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentifyMon_19("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_19",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCountMon_19("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_19",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_19("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_19",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_20",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_20("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_20",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentifyMon_20("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_20",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCountMon_20("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_20",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_20("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_20",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_21",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_21("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_21",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentifyMon_21("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_21",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCountMon_21("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_21",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_21("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_21",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_22",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_22("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_22",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentifyMon_22("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_22",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCountMon_22("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_22",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_22("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_22",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_23",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_23("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_23",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentifyMon_23("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_23",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCountMon_23("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_23",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_23("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_23",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_24",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_24("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_24",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentifyMon_24("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_24",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCountMon_24("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_24",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_24("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_24",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_25",width: "auto",orderable: false,"sClass": "center",
										               		"render":function(data, type, row, meta){
										               			if(row.ticketTypeName=="合计"){
										               				return "<span >"+data+"</span>"
										               			}else {
									        	 				return "<a href='#' onclick='searchTicketCountMon_25("+row.ticketType+")'>"+data+"</a>"
										               			}
									        	 			}},
									{data: "identify_25",width: "auto",orderable: false,"sClass": "center",
											               		"render":function(data, type, row, meta){
											               			if(row.ticketTypeName=="合计"){
											               				return "<span >"+data+"</span>"
											               			}else {
										        	 				return "<a href='#' onclick='searchIdentifyMon_25("+row.ticketType+")'>"+data+"</a>"
											               			}
										        	 			}},
									{data: "qualifiedCount_25",width: "auto",orderable: false,"sClass": "center",
												               		"render":function(data, type, row, meta){
												               			if(row.ticketTypeName=="合计"){
												               				return "<span >"+data+"</span>"
												               			}else {
											        	 				return "<a href='#' onclick='searchQualifiedCountMon_25("+row.ticketType+")'>"+data+"</a>"
												               			}
											        	 			}}, 
									{data: "unQualifiedCount_25",width: "auto",orderable: false,"sClass": "center",
													               		"render":function(data, type, row, meta){
													               			if(row.ticketTypeName=="合计"){
													               				return "<span >"+data+"</span>"
													               			}else {
												        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_25("+row.ticketType+")'>"+data+"</a>"
													               			}
												        	 			}},
									{data: "qualifiedRate_25",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_26",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_26("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_26",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentifyMon_26("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_26",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCountMon_26("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_26",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_26("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_26",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_27",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_27("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_27",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchIdentifyMon_27("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "qualifiedCount_27",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchQualifiedCountMon_27("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}}, 
									{data: "unQualifiedCount_27",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_27("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "qualifiedRate_27",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_28",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_28("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_28",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentifyMon_28("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_28",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCountMon_28("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_28",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_28("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_28",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_29",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_29("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_29",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentifyMon_29("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_29",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCountMon_29("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_29",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_29("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_29",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_30",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_30("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_30",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentifyMon_30("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_30",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCountMon_30("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_30",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_30("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_30",width: "auto",orderable: false,"sClass": "center"},
									
									{data: "ticketCount_31",width: "auto",orderable: false,"sClass": "center",
					               		"render":function(data, type, row, meta){
					               			if(row.ticketTypeName=="合计"){
					               				return "<span >"+data+"</span>"
					               			}else {
				        	 				return "<a href='#' onclick='searchTicketCountMon_31("+row.ticketType+")'>"+data+"</a>"
					               			}
				        	 			}},
									{data: "identify_31",width: "auto",orderable: false,"sClass": "center",
						               		"render":function(data, type, row, meta){
						               			if(row.ticketTypeName=="合计"){
						               				return "<span >"+data+"</span>"
						               			}else {
					        	 				return "<a href='#' onclick='searchIdentifyMon_31("+row.ticketType+")'>"+data+"</a>"
						               			}
					        	 			}},
									{data: "qualifiedCount_31",width: "auto",orderable: false,"sClass": "center",
							               		"render":function(data, type, row, meta){
							               			if(row.ticketTypeName=="合计"){
							               				return "<span >"+data+"</span>"
							               			}else {
						        	 				return "<a href='#' onclick='searchQualifiedCountMon_31("+row.ticketType+")'>"+data+"</a>"
							               			}
						        	 			}}, 
									{data: "unQualifiedCount_31",width: "auto",orderable: false,"sClass": "center",
								               		"render":function(data, type, row, meta){
								               			if(row.ticketTypeName=="合计"){
								               				return "<span >"+data+"</span>"
								               			}else {
							        	 				return "<a href='#' onclick='searchUnQualifiedCountMon_31("+row.ticketType+")'>"+data+"</a>"
								               			}
							        	 			}},
									{data: "qualifiedRate_31",width: "auto",orderable: false,"sClass": "center"},
									
									{data: "ticketCount_32",width: "auto",orderable: false,"sClass": "center"},
									{data: "identify_32",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedCount_32",width: "auto",orderable: false,"sClass": "center"}, 
									{data: "unQualifiedCount_32",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedRate_32",width: "auto",orderable: false,"sClass": "center"},
									
							],
						}
					}).render();
					
					
					
					function functicketTypeName(){
						var url = "${ctx}/ticketStatistics/createDataExcelFile";
						
					}
					//查询按钮
					$('#btnSearch').on('click',function(){
						//zzq
						var searchTime = $("#searchstatisticsDate").val();
						if(searchTime==""){
							 var date=new Date;
							 var year=date.getFullYear(); 
							 var month=date.getMonth()+1;
							 month =(month<10 ? "0"+month:month); 
							 var mydate = (year.toString()+"-"+month.toString());
							 searchTime=mydate;
						}
						var selectedDate = new Date(searchTime);
						var selectedMonth = selectedDate.getMonth()+1;
						selectedDate.setMonth(selectedMonth);
						selectedDate.setDate(0);
						var dayMany = selectedDate.getDate();
						//zzq
						//统计方式
						var SearchDateType = DateType.getValue();
						//统计时间
						var searchTime = $("#searchstatisticsDate").val();
						month = searchTime;
						//单位名称
						var unitId = searchunitId._displayNames;
						var yearText = "";
						var monthText = "";
						if (searchTime == "") {
							var date = new Date();
							var year = date.getFullYear();
							yearText = year + "年";
						} else {
							yearText = searchTime + "年";
							monthText = searchTime;
						}
						var unitText = "";
						if (unitId != "" && unitId != undefined) {
							unitText = unitId;
						}
						$("#table_titile").html(yearText + unitText + "“两票”情况统计表");
						param = getAjaxParam ();
						var SearchDateType = DateType.getValue();
						if(SearchDateType=="month"){
							$("#table_titile").html(monthText + unitText + "“两票”情况统计表");
							$("#defectStatistics-print1").show();
							$("#defectStatistics-print").hide();
							 if(dayMany==30){
								dataMonthTables._datatables.column( 140 ).visible( true );
								dataMonthTables._datatables.column( 141 ).visible( true );
								dataMonthTables._datatables.column( 142 ).visible( true );
								dataMonthTables._datatables.column( 143 ).visible( true );
								dataMonthTables._datatables.column( 144 ).visible( true );
								dataMonthTables._datatables.column( 145 ).visible( true );
								dataMonthTables._datatables.column( 146 ).visible( true );
								dataMonthTables._datatables.column( 147 ).visible( true );
								dataMonthTables._datatables.column( 148 ).visible( true );
								dataMonthTables._datatables.column( 149 ).visible( true );
								dataMonthTables._datatables.column( 150 ).visible( false );
								dataMonthTables._datatables.column( 151 ).visible( false );
								dataMonthTables._datatables.column( 152 ).visible( false );
								dataMonthTables._datatables.column( 153 ).visible( false );
								dataMonthTables._datatables.column( 154 ).visible( false );
								
								dataMonthTables.draw();
							}else if(dayMany==29){
								dataMonthTables._datatables.column( 140 ).visible( true );
								dataMonthTables._datatables.column( 141 ).visible( true );
								dataMonthTables._datatables.column( 142 ).visible( true );
								dataMonthTables._datatables.column( 143 ).visible( true );
								dataMonthTables._datatables.column( 144 ).visible( true );
								dataMonthTables._datatables.column( 145 ).visible( false );
								dataMonthTables._datatables.column( 146 ).visible( false );
								dataMonthTables._datatables.column( 147 ).visible( false );
								dataMonthTables._datatables.column( 148 ).visible( false );
								dataMonthTables._datatables.column( 149 ).visible( false );
								dataMonthTables._datatables.column( 150 ).visible( false );
								dataMonthTables._datatables.column( 151 ).visible( false );
								dataMonthTables._datatables.column( 152 ).visible( false );
								dataMonthTables._datatables.column( 153 ).visible( false );
								dataMonthTables._datatables.column( 154 ).visible( false );
								
								dataMonthTables.draw();
							}else if(dayMany==28){
								//29
								dataMonthTables._datatables.column( 140 ).visible( false );
								dataMonthTables._datatables.column( 141 ).visible( false );
								dataMonthTables._datatables.column( 142 ).visible( false );
								dataMonthTables._datatables.column( 143 ).visible( false );
								dataMonthTables._datatables.column( 144 ).visible( false );
								//30
								dataMonthTables._datatables.column( 145 ).visible( false );
								dataMonthTables._datatables.column( 146 ).visible( false );
								dataMonthTables._datatables.column( 147 ).visible( false );
								dataMonthTables._datatables.column( 148 ).visible( false );
								dataMonthTables._datatables.column( 149 ).visible( false );
								//31
								dataMonthTables._datatables.column( 150 ).visible( false );
								dataMonthTables._datatables.column( 151 ).visible( false );
								dataMonthTables._datatables.column( 152 ).visible( false );
								dataMonthTables._datatables.column( 153 ).visible( false );
								dataMonthTables._datatables.column( 154 ).visible( false );
								
								dataMonthTables.draw();
							}else{
								dataMonthTables._datatables.column( 140 ).visible( true );
								dataMonthTables._datatables.column( 141 ).visible( true );
								dataMonthTables._datatables.column( 142 ).visible( true );
								dataMonthTables._datatables.column( 143 ).visible( true );
								dataMonthTables._datatables.column( 144 ).visible( true );
								dataMonthTables._datatables.column( 145 ).visible( true );
								dataMonthTables._datatables.column( 146 ).visible( true );
								dataMonthTables._datatables.column( 147 ).visible( true );
								dataMonthTables._datatables.column( 148 ).visible( true );
								dataMonthTables._datatables.column( 149 ).visible( true );
								dataMonthTables._datatables.column( 150 ).visible( true );
								dataMonthTables._datatables.column( 151 ).visible( true );
								dataMonthTables._datatables.column( 152 ).visible( true );
								dataMonthTables._datatables.column( 153 ).visible( true );
								dataMonthTables._datatables.column( 154 ).visible( true );
								dataMonthTables.draw();
							}
						}
						if(SearchDateType=="year"||SearchDateType==""){
							$("#table_titile").html(yearText + unitText + "“两票”情况统计表");
							$("#defectStatistics-print1").hide();
							$("#defectStatistics-print").show();
							yearTextLocal=searchTime;
							dataTables.draw();
						}
						
					});
					//重置按钮
					$('#btnReset').on('click',function(){
						//单位名称下拉树
						searchunitId.setValue(undefined); 
						searchunitId._displayNames = "";
						//统计时间
						$("#searchstatisticsDate").val("");
						$("#DateType").val("");
						$("#DateType").trigger("chosen:updated");
						conditions=[];
					});
					//导出按钮
					$('#btnimport').on('click',function(){
						$("#btnSearch").click();
						var SearchDateType = DateType.getValue();
						if(SearchDateType=="year"||SearchDateType==""){
						var searchTime = $("#searchstatisticsDate").val();
						var yearText = "";
						if (searchTime == "") {
							var date = new Date();
							var year = date.getFullYear();
							yearText = year;
						}else {
							yearText = searchTime;
						}
						var param = getAjaxParam ();
						var url = "${ctx}/ticketStatistics/createDataExcelFile/"+yearText;
					 	$.ajax({
							type: 'POST',
							contentType : 'application/json',
							url: url,
							data : JSON.stringify(param),
							dataType : "JSON",
							cache: false,
							success: function(datas){
								if(datas.tempFileName.length >0) { 
									var excelName = $("#table_titile").html();
									$("#downloadName").val(excelName);
									$("#downloadPath").val(datas.tempFileName);
					 				$("#downloadForm").submit();
								}
							},
							error: function(datas){
							}
						}); 
						}
						if(SearchDateType=="month"){
							var searchTime = $("#searchstatisticsDate").val();
							if(searchTime==""){
								alert("统计时间未填写！");
								return;
							}
							var monthText = "";
							monthText = searchTime;
							var param = getAjaxParam ();
							var url = "${ctx}/ticketStatistics/exportExcel/"+monthText;
						 	$.ajax({
								type: 'POST',
								contentType : 'application/json',
								url: url,
								data : JSON.stringify(param),
								dataType : "JSON",
								cache: false,
								success: function(datas){
									alert(1);
									if(datas.tempFileName.length >0) { 
										var excelName = $("#table_titile").html();
										$("#downloadName").val(excelName);
										$("#downloadPath").val(datas.tempFileName);
						 				$("#downloadForm").submit();
									}
								},
								error: function(datas){
								}
							}); 
						}
					});
					
					function initPage() {
						//统计时间
						var searchTime = $("#searchstatisticsDate").val();
						month = searchTime;
						//单位名称
						var unitId =  searchunitId._displayNames;
						var yearText = "";
						if (searchTime == "") {
							var date = new Date();
							var year = date.getFullYear();
							yearText = year + "年";
							yearTextLocal=year;
						} else {
							yearText = searchTime + "年";
						}
						var unitText = "";
						if (unitId != "" && unitId != undefined) {
							unitText = unitId;
						} else {
							 unitText = "";
						}
						
						$("#table_titile").html(yearText + unitText + "“两票”情况统计表");
					}
					initPage();
					$("#DateType").on("change", function(event){
						var selectValue = $(this).val();
						if(selectValue=="year"){
							searchstatisticsDate = new A.my97datepicker({
	 							id: 'searchstatisticsDate',
	 							name:'searchstatisticsDate',
	 							render:'#searchstatisticsDateDiv',
	 							options : {
	 									isShowWeek : false,
	 									skin : 'ext',
	 									maxDate: "",
	 									minDate: "",
	 									dateFmt: "yyyy"
	 							}
	 						}).render();
						}else{
							searchstatisticsDate = new A.my97datepicker({
	 							id: 'searchstatisticsDate',
	 							name:'searchstatisticsDate',
	 							render:'#searchstatisticsDateDiv',
	 							options : {
	 									isShowWeek : false,
	 									skin : 'ext',
	 									maxDate: "",
	 									minDate: "",
	 									dateFmt: "yyyy-MM"
	 							}
	 						}).render();
						}
    				});
				});
			});
			
			function searchTicketCount_1(ticketType){
				var JanStart = "1-1";
				var JanEnd = "1-31";
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}
				else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentify_1(ticketType){
				var JanStart = "1-1";
				var JanEnd = "1-31";
				var identify = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCount_1(ticketType){
				var JanStart = "1-1";
				var JanEnd = "1-31";
				var qualifiedCount = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCount_1(ticketType){
				var JanStart = "1-1";
				var JanEnd = "1-31";
				var unQualifiedCount = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			
			function searchTicketCount_2(ticketType){
				var JanStart = "2-1";
				var JanEnd = "2-29";
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentify_2(ticketType){
				var JanStart = "2-1";
				var JanEnd = "2-29";
				var identify = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCount_2(ticketType){
				var JanStart = "2-1";
				var JanEnd = "2-29";
				var qualifiedCount = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCount_2(ticketType){
				var JanStart = "2-1";
				var JanEnd = "2-29";
				var unQualifiedCount = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			
			function searchTicketCount_3(ticketType){
				var JanStart = "3-1";
				var JanEnd = "3-31";
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentify_3(ticketType){
				var JanStart = "3-1";
				var JanEnd = "3-31";
				var identify = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCount_3(ticketType){
				var JanStart = "3-1";
				var JanEnd = "3-31";
				var qualifiedCount = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCount_3(ticketType){
				var JanStart = "3-1";
				var JanEnd = "3-31";
				var unQualifiedCount = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			
			function searchTicketCount_4(ticketType){
				var JanStart = "4-1";
				var JanEnd = "4-30";
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentify_4(ticketType){
				var JanStart = "4-1";
				var JanEnd = "4-30";
				var identify = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCount_4(ticketType){
				var JanStart = "4-1";
				var JanEnd = "4-30";
				var qualifiedCount = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCount_4(ticketType){
				var JanStart = "4-1";
				var JanEnd = "4-30";
				var unQualifiedCount = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			
			function searchTicketCount_5(ticketType){
				var JanStart = "5-1";
				var JanEnd = "5-31";
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentify_5(ticketType){
				var JanStart = "5-1";
				var JanEnd = "5-31";
				var identify = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCount_5(ticketType){
				var JanStart = "5-1";
				var JanEnd = "5-31";
				var qualifiedCount = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCount_5(ticketType){
				var JanStart = "5-1";
				var JanEnd = "5-31";
				var unQualifiedCount = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			
			function searchTicketCount_6(ticketType){
				var JanStart = "6-1";
				var JanEnd = "6-30";
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentify_6(ticketType){
				var JanStart = "6-1";
				var JanEnd = "6-30";
				var identify = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCount_6(ticketType){
				var JanStart = "6-1";
				var JanEnd = "6-30";
				var qualifiedCount = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCount_6(ticketType){
				var JanStart = "6-1";
				var JanEnd = "6-30";
				var unQualifiedCount = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			
			function searchTicketCount_7(ticketType){
				var JanStart = "7-1";
				var JanEnd = "7-31";
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentify_7(ticketType){
				var JanStart = "7-1";
				var JanEnd = "7-31";
				var identify = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCount_7(ticketType){
				var JanStart = "7-1";
				var JanEnd = "7-31";
				var qualifiedCount = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCount_7(ticketType){
				var JanStart = "7-1";
				var JanEnd = "7-31";
				var unQualifiedCount = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			
			function searchTicketCount_8(ticketType){
				var JanStart = "8-1";
				var JanEnd = "8-31";
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentify_8(ticketType){
				var JanStart = "8-1";
				var JanEnd = "8-31";
				var identify = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCount_8(ticketType){
				var JanStart = "8-1";
				var JanEnd = "8-31";
				var qualifiedCount = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCount_8(ticketType){
				var JanStart = "8-1";
				var JanEnd = "8-31";
				var unQualifiedCount = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			
			function searchTicketCount_9(ticketType){
				var JanStart = "9-1";
				var JanEnd = "9-30";
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentify_9(ticketType){
				var JanStart = "9-1";
				var JanEnd = "9-30";
				var identify = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCount_9(ticketType){
				var JanStart = "9-1";
				var JanEnd = "9-30";
				var qualifiedCount = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCount_9(ticketType){
				var JanStart = "9-1";
				var JanEnd = "9-30";
				var unQualifiedCount = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			
			function searchTicketCount_10(ticketType){
				var JanStart = "10-1";
				var JanEnd = "10-31";
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentify_10(ticketType){
				var JanStart = "10-1";
				var JanEnd = "10-31";
				var identify = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCount_10(ticketType){
				var JanStart = "10-1";
				var JanEnd = "10-31";
				var qualifiedCount = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCount_10(ticketType){
				var JanStart = "10-1";
				var JanEnd = "10-31";
				var unQualifiedCount = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			
			function searchTicketCount_11(ticketType){
				var JanStart = "11-1";
				var JanEnd = "11-30";
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentify_11(ticketType){
				var JanStart = "11-1";
				var JanEnd = "11-30";
				var identify = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCount_11(ticketType){
				var JanStart = "11-1";
				var JanEnd = "11-30";
				var qualifiedCount = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCount_11(ticketType){
				var JanStart = "11-1";
				var JanEnd = "11-30";
				var unQualifiedCount = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			
			function searchTicketCount_12(ticketType){
				var JanStart = "12-1";
				var JanEnd = "12-31";
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentify_12(ticketType){
				var JanStart = "12-1";
				var JanEnd = "12-31";
				var identify = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&identify="+identify;;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCount_12(ticketType){
				var JanStart = "12-1";
				var JanEnd = "12-31";
				var qualifiedCount = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCount_12(ticketType){
				var JanStart = "12-1";
				var JanEnd = "12-31";
				var unQualifiedCount = 1;
				if(ticketType==1){
					url="/workTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchJan?JanStart="+JanStart+"&JanEnd="+JanEnd+"&yearTextLocal="+yearTextLocal+"&ticketType="+ticketType+"&unQualifiedCount="+unQualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_1(ticketType){
				var JanStart = "1";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_1(ticketType){
				var JanStart = "1";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_1(ticketType){
				var JanStart = "1";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_1(ticketType){
				var JanStart = "1";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_2(ticketType){
				var JanStart = "2";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_2(ticketType){
				var JanStart = "2";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_2(ticketType){
				var JanStart = "2";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_2(ticketType){
				var JanStart = "2";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_3(ticketType){
				var JanStart = "3";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_3(ticketType){
				var JanStart = "3";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_3(ticketType){
				var JanStart = "3";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_3(ticketType){
				var JanStart = "3";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_4(ticketType){
				var JanStart = "4";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_4(ticketType){
				var JanStart = "4";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_4(ticketType){
				var JanStart = "4";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_4(ticketType){
				var JanStart = "4";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_5(ticketType){
				var JanStart = "5";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_5(ticketType){
				var JanStart = "5";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_5(ticketType){
				var JanStart = "5";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_5(ticketType){
				var JanStart = "5";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_6(ticketType){
				var JanStart = "6";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_6(ticketType){
				var JanStart = "6";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_6(ticketType){
				var JanStart = "6";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_6(ticketType){
				var JanStart = "6";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_7(ticketType){
				var JanStart = "7";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_7(ticketType){
				var JanStart = "7";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_7(ticketType){
				var JanStart = "7";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_7(ticketType){
				var JanStart = "7";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_8(ticketType){
				var JanStart = "8";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_8(ticketType){
				var JanStart = "8";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_8(ticketType){
				var JanStart = "8";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_8(ticketType){
				var JanStart = "8";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_9(ticketType){
				var JanStart = "9";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_9(ticketType){
				var JanStart = "9";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_9(ticketType){
				var JanStart = "9";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_9(ticketType){
				var JanStart = "9";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_10(ticketType){
				var JanStart = "10";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_10(ticketType){
				var JanStart = "10";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_10(ticketType){
				var JanStart = "10";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_10(ticketType){
				var JanStart = "10";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_11(ticketType){
				var JanStart = "11";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_11(ticketType){
				var JanStart = "11";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_11(ticketType){
				var JanStart = "11";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_11(ticketType){
				var JanStart = "11";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_12(ticketType){
				var JanStart = "12";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_12(ticketType){
				var JanStart = "12";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_12(ticketType){
				var JanStart = "12";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_12(ticketType){
				var JanStart = "12";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_13(ticketType){
				var JanStart = "13";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_13(ticketType){
				var JanStart = "13";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_13(ticketType){
				var JanStart = "13";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_13(ticketType){
				var JanStart = "13";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_14(ticketType){
				var JanStart = "14";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_14(ticketType){
				var JanStart = "14";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_14(ticketType){
				var JanStart = "14";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_14(ticketType){
				var JanStart = "14";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_15(ticketType){
				var JanStart = "3";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_15(ticketType){
				var JanStart = "3";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_15(ticketType){
				var JanStart = "3";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_15(ticketType){
				var JanStart = "3";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_16(ticketType){
				var JanStart = "4";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_16(ticketType){
				var JanStart = "4";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_16(ticketType){
				var JanStart = "4";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_16(ticketType){
				var JanStart = "4";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_17(ticketType){
				var JanStart = "17";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_17(ticketType){
				var JanStart = "17";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_17(ticketType){
				var JanStart = "17";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_17(ticketType){
				var JanStart = "17";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_18(ticketType){
				var JanStart = "18";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_18(ticketType){
				var JanStart = "18";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_18(ticketType){
				var JanStart = "18";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_18(ticketType){
				var JanStart = "18";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_19(ticketType){
				var JanStart = "19";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_19(ticketType){
				var JanStart = "19";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_19(ticketType){
				var JanStart = "19";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_19(ticketType){
				var JanStart = "19";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_20(ticketType){
				var JanStart = "20";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_20(ticketType){
				var JanStart = "20";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_20(ticketType){
				var JanStart = "20";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_20(ticketType){
				var JanStart = "20";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_21(ticketType){
				var JanStart = "21";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_21(ticketType){
				var JanStart = "21";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_21(ticketType){
				var JanStart = "21";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_21(ticketType){
				var JanStart = "21";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_22(ticketType){
				var JanStart = "22";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_22(ticketType){
				var JanStart = "22";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_22(ticketType){
				var JanStart = "22";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_22(ticketType){
				var JanStart = "22";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_23(ticketType){
				var JanStart = "23";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_23(ticketType){
				var JanStart = "23";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_23(ticketType){
				var JanStart = "23";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_23(ticketType){
				var JanStart = "23";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_24(ticketType){
				var JanStart = "24";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_24(ticketType){
				var JanStart = "24";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_24(ticketType){
				var JanStart = "24";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_24(ticketType){
				var JanStart = "24";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_25(ticketType){
				var JanStart = "25";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_25(ticketType){
				var JanStart = "25";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_25(ticketType){
				var JanStart = "25";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_25(ticketType){
				var JanStart = "25";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_26(ticketType){
				var JanStart = "26";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_26(ticketType){
				var JanStart = "26";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_26(ticketType){
				var JanStart = "26";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_26(ticketType){
				var JanStart = "26";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_27(ticketType){
				var JanStart = "27";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_27(ticketType){
				var JanStart = "27";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_27(ticketType){
				var JanStart = "27";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_27(ticketType){
				var JanStart = "27";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_31(ticketType){
				var JanStart = "31";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_31(ticketType){
				var JanStart = "31";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_31(ticketType){
				var JanStart = "31";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_31(ticketType){
				var JanStart = "31";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_28(ticketType){
				var JanStart = "28";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_28(ticketType){
				var JanStart = "28";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_28(ticketType){
				var JanStart = "28";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_28(ticketType){
				var JanStart = "28";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_29(ticketType){
				var JanStart = "29";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_29(ticketType){
				var JanStart = "29";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_29(ticketType){
				var JanStart = "29";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_29(ticketType){
				var JanStart = "29";
				var JanEnd = "30";
				var identify=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchTicketCountMon_30(ticketType){
				var JanStart = "30";
				var JanEnd = "30";
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchUnQualifiedCountMon_30(ticketType){
				var JanStart = "30";
				var JanEnd = "30";
				var unqualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&unqualifiedCount="+unqualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchQualifiedCountMon_30(ticketType){
				var JanStart = "30";
				var JanEnd = "30";
				var qualifiedCount=1;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&qualifiedCount="+qualifiedCount;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
			function searchIdentifyMon_30(ticketType){
				var JanStart = "30";
				var JanEnd = "30";
				var identify=30;
				if(ticketType==1){
					url="/workTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==2){
					url="/workTicketTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==12){
					url="/workTicketLine/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==13){
					url="/workTicketLineTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==6){
					url="/workTicketWindMechanical/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==8){
					url="/repairTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==14){
					url="/workHelpSafe/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==11){
					url="/operationTicket/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==3){
					url="/workTicketFire/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}else if(ticketType==4){
					url="/workTicketFireTwo/searchMon?JanStart="+JanStart+"&JanEnd="+JanEnd+"&month="+month+"&ticketType="+ticketType+"&identify="+identify;
				}
				workticketDialog = new A.dialog({
					title:"工作票查询",
					url:format_url(url),
					height:900,
					width:1200,
				}).render();
			}
        </script>
    </body>
</html>