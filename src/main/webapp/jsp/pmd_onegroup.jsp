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
	<h2>Project Problems Summary</h2>
	
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
				<!--第一个tab-->
					<div class="tab-pane  active" id="panel-298840">
						<select id="iterSelect" class="form-control commonSelect" style="width:130px;"> 
						</select>
						
						<select id="issueType" class="form-control commonSelect" style="width:130px;margin-left:20px"> 
							<option value="basic">basic</option> 
							<option value="naming">naming</option> 
							<option value="unusedcode">unusedcode</option> 
							<option value="codesize">codesize</option> 
							<option value="clone">clone</option> 
							<option value="coupling">coupling</option> 
						</select>
						<button class="btn btn-default" style="margin-top:10px;margin-left:20px">Submit</button>
						
						
						<form id="exportForm" action="api/pmd/export">
							<center>
							<h3>Problems found</h3>
							<span class="commonSpan">
								<a href="javascript:exportDetail()">Export Details</a>
								<input type="hidden" name="iter" id="iterHidden">
								<input type="hidden" name="type" id="typeHidden">
								<input type="hidden" name="groupName" id="nameHidden">
							</span>
							</center>
						</form>
						<table align="center" cellspacing="0" cellpadding="3"><tr>
						<th>#</th><th>File</th><th>Line</th><th>Problem</th></tr>
						<tr bgcolor="lightgrey"> 
						<td align="center">1</td>
						<td width="*%">E:\Documents\graduate\homework\StockEy\stockeyServlet\src\org\xeon\stockey\ui\stockui\StockListController.java</td>
						<td align="center" width="5%">115</td>
						<td width="*"><a href="https://pmd.github.io/pmd-5.5.0/pmd-java/rules/java/basic.html#ForLoopShouldBeWhileLoop">This for loop could be simplified to a while loop</a></td>
						</tr>
						<tr> 
						<td align="center">2</td>
						<td width="*%">E:\Documents\graduate\homework\StockEy\stockeyServlet\src\org\xeon\stockey\ui\stockui\StockMarketController.java</td>
						<td align="center" width="5%">86</td>
						<td width="*"><a href="https://pmd.github.io/pmd-5.5.0/pmd-java/rules/java/basic.html#ForLoopShouldBeWhileLoop">This for loop could be simplified to a while loop</a></td>
						</tr>
						<tr bgcolor="lightgrey"> 
						<td align="center">3</td>
						<td width="*%">E:\Documents\graduate\homework\StockEy\stockey\src\main\java\org\xeon\stockey\businessLogic\strategy\StrategyImpl.java</td>
						<td align="center" width="5%">82</td>
						<td width="*"><a href="https://pmd.github.io/pmd-5.5.0/pmd-java/rules/java/basic.html#ReturnFromFinallyBlock">Avoid returning from a finally block</a></td>
						</tr>
						<tr> 
						<td align="center">4</td>
						<td width="*%">E:\Documents\graduate\homework\StockEy\stockey\src\main\java\org\xeon\stockey\data\utility\NetworkHelper.java</td>
						<td align="center" width="5%">208</td>
						<td width="*"><a href="https://pmd.github.io/pmd-5.5.0/pmd-java/rules/java/basic.html#CollapsibleIfStatements">These nested if statements could be combined</a></td>
						</tr>
						<tr bgcolor="lightgrey"> 
						<td align="center">5</td>
						<td width="*%">E:\Documents\graduate\homework\StockEy\stockey\src\main\java\org\xeon\stockey\data\utility\NetworkHelper.java</td>
						<td align="center" width="5%">210</td>
						<td width="*"><a href="https://pmd.github.io/pmd-5.5.0/pmd-java/rules/java/basic.html#CollapsibleIfStatements">These nested if statements could be combined</a></td>
						</tr>
						<tr> 
						<td align="center">6</td>
						<td width="*%">E:\Documents\graduate\homework\StockEy\stockey\src\main\java\org\xeon\stockey\ui\stockui\selection\StockListController.java</td>
						<td align="center" width="5%">126</td>
						<td width="*"><a href="https://pmd.github.io/pmd-5.5.0/pmd-java/rules/java/basic.html#ForLoopShouldBeWhileLoop">This for loop could be simplified to a while loop</a></td>
						</tr>
						</table>
					</div>
					
					<!--第二个tab-->
					<div class="tab-pane" id="panel-912874">
					<div class="commonDiv" style="width:30%;height:500px;">
					<table class="pmdtable" style="width:250px;margin-top:20px;margin-left:12%;">
						
						<tbody>
							<tr>
								<td colspan=4><b>Current Problems</b></td>
							</tr>
							<tr>
								<td align="center"><div class="square" style="background:rgba(220,220,220,1)"></div></td>
								<td align="center">Coupling</td>
								<td align="center"><div class="square" style="background:rgba(210,105,30,1"></div></td>
								<td align="center">Basic</td>
							</tr>
							<tr>
								<td align="center"><div class="square" style="background:rgba(151,187,205,1)"></div></td>
								<td align="center">Naming</td>
								<td align="center"><div class="square" style="background:rgba(147,112,219,1)"></div></td>
								<td align="center">Unusedcode</td>
							</tr>
							<tr>
								<td align="center"><div class="square" style="background:rgba(218,165,32,1)"></div></td>
								<td align="center">Codesize</td>
								<td align="center"><div class="square" style="background:#999966"></div></td>
								<td align="center">Clone</td>
							</tr>
						</tbody>
						
					</table>
	
					<canvas id="pieChart" width="260" height="260" style="margin-top:10%"></canvas>
					
					</div>
					
					<div class="commonDiv" style="width:65%;height:500px;">
					<table class="pmdtable" style="width:600px;margin-top:20px">
					
						<tbody>
							<tr>
								<td colspan=2><b>Problem statistics</b></td>
							</tr>
							<tr>
								<td align="center"><div class="square" style="background:#dcdcdc"></div></td>
								<td>Iterator Ⅰ</td>
								<td align="center"><div class="square" style="background:#97bbcd"></div></td>
								<td>Iterator Ⅱ</td>
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
	
			<script  type="text/javascript">
				var getParameterByName = function (name, url) {
				    if (!url) url = window.location.href;
				    name = name.replace(/[\[\]]/g, "\\$&");
				    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
				        results = regex.exec(url);
				    if (!results) return null;
				    if (!results[2]) return '';
				    return decodeURIComponent(results[2].replace(/\+/g, " "));
				}
			
				
				var groupName = getParameterByName("groupName");
				var iter = getParameterByName("iter");
				for (var i=iter;i>0;i--)
				{
					var html='<option value='+i+'>'+'Iterator'+i+'</option>';
				 	$("#iterSelect").append(html);
				}
				
				var init=function(){
				}
			</script>
		<script type="text/javascript">
		
		function exportDetail(){
			 var iterValue=$("#iterSelect").val();
			 var issueType=$("#issueType").val();
			 $("#iterHidden").val(iterValue);
			 $("#typeHidden").val(issueType);
			 $("#nameHidden").val(groupName);
			 document.getElementById('exportForm').submit();
		};
		</script>
	
	<script src="js/projectPie.js"></script>
	<script src="js/projectBar.js"></script>
</div>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>