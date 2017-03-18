<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link href="css/pmdcss.css" rel="stylesheet">
	<link href="css/bootstrap.css" rel="stylesheet">
	<link href="css/bootstrap-responsive.css" rel="stylesheet">
	<link href="css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
	<script src="js/jquery-1.8.3.min.js"></script>
	<script src="js/table.js"></script>
	<script src="js/Chart-1.0.1-beta.4.js"></script>
	<script>
		$(function(){
			tableSort($('#dataTable'));
		})
	</script>
<title>All Group</title>
</head>
<body>
	<%@ include file="nav.jsp" %>
	<div class="container">
	<h2>Group Problems Summary</h2>
	
	<div class="tabbable" id="tabs-914413" style="margin-top:2%">
				<ul class="nav nav-tabs">
					<li class="active">
						 <a href="#panel-298840" data-toggle="tab">Data</a>
					</li>
					<li>
						 <a href="#panel-912874" data-toggle="tab">Chart</a>
					</li>
				</ul>
				
				<div class="tab-content">
					<div class="tab-pane  active" id="panel-298840">
						<select class="form-control" style="float:left;width:130px;margin-top:1%;margin-bottom:1%"> 
							<option value="1">迭代一</option> 
							<option value="2">迭代二</option> 
							<option value="3">迭代三</option> 
						</select>
						
					
						<table class="table table-striped table-bordered" id="dataTable">
						  <thead>
							<tr style="hover:  background-color: #0180FE;  cursor:hand; ">
							  <th datatype="int">rank</th>
							  <th datatype="text">Group</th>
							  <th datatype="int" class="current">basic</th>
							  <th datatype="int">naming</th>
							  <th datatype="int">unusedcode</th>
							  <th datatype="int">codesize</th>
							  <th datatype="int">clone</th>
							  <th datatype="int">coupling</th>
							</tr>
						  </thead>
						  <tbody>
							<tr>
							  <td>1</td>
							  <td><a href="onegroup.html">141250107</a></td>
							  <td>0</td>
							  <td>25</td>
							  <td>2</td>
							  <td>0</td>
							  <td>0</td>
							  <td>6</td>
							</tr>
							<tr>
							  <td>2</td>
							  <td><a href="onegroup.html">GitMining</a></td>
							  <td>82</td>
							  <td>663</td>
							  <td>103</td>
							  <td>64</td>
							  <td>0</td>
							  <td>1407</td>
							</tr>
							<tr>
							  <td>3</td>
							  <td><a href="onegroup.html">StockEy</a></td>
							  <td>6</td>
							  <td>614</td>
							  <td>272</td>
							  <td>108</td>
							  <td>3</td>
							  <td>2683</td>
							</tr>		
							<tr>
							  <td>4</td>
							  <td><a href="onegroup.html">StockWizard</a></td>
							  <td>4</td>
							  <td>645</td>
							  <td>38</td>
							  <td>125</td>
							  <td>0</td>
							  <td>1184
							  </td>
							</tr>
						  </tbody>
						</table>
					</div>
					
					<script src="js/groupBar.js"></script>
					<div class="tab-pane" id="panel-912874">
						<!--<canvas id="myChart" width="300" height="300"></canvas>-->
						
							<table class="pmdtable">
								<tbody>
									<tr>
										<td colspan=2><b>Median statistics</b></td>
									</tr>
									<tr>
										<td align="center"><div class="square" style="background:#dcdcdc"></div></td>
										<td>Iterator Ⅰ</td>
									</tr>
									<tr>
										<td align="center"><div class="square" style="background:#97bbcd"></div></td>
										<td>Iterator Ⅱ</td>
									</tr>
									<tr>
										<td align="center"><div class="square" style="background:#9370DB"></div></td>
										<td>Iterator Ⅲ</td>
									</tr>
								</tbody>
							</table>
							
							<canvas id="barChart" width="800" height="400"></canvas>
					</div>
				</div>
			</div>
	

	
</div>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>