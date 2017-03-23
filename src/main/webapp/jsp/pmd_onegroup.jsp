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
	
<title>One Group</title>
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
						<button id="getType" class="btn btn-default" style="margin-top:10px;margin-left:20px">Submit</button>
						
						
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
						<table align="center" cellspacing="0" cellpadding="3" style="width:100%;" class="table-striped table-bordered">
						<thead>
							<tr>
							<th>#</th><th>File</th><th>Line</th><th>Problem</th>
							</tr>
						</thead>
						<tbody id="tbody_content">
						</tbody>
						</table>
					</div>
					
					<!--第二个tab-->
					<div class="tab-pane" id="panel-912874">
					<div class="commonDiv" style="width:30%;height:500px;">
					<center><b>Current Issues</b></center>
					<table class="pmdtable" style="width:250px;margin-top:20px;margin-left:12%;">
						
						<tbody>
							<tr>
								<td height=40 align="center"><div class="square" style="background:rgba(220,220,220,1)"></div></td>
								<td align="center">Coupling</td>
								<td align="center"><div class="square" style="background:rgba(210,105,30,1"></div></td>
								<td align="center">Basic</td>
							</tr>
							<tr>
								<td height=40 align="center"><div class="square" style="background:rgba(151,187,205,1)"></div></td>
								<td align="center">Naming</td>
								<td align="center"><div class="square" style="background:rgba(147,112,219,1)"></div></td>
								<td align="center">Unusedcode</td>
							</tr>
							<tr>
								<td height=40 align="center"><div class="square" style="background:rgba(218,165,32,1)"></div></td>
								<td align="center">Codesize</td>
								<td align="center"><div class="square" style="background:#999966"></div></td>
								<td align="center">Clone</td>
							</tr>
						</tbody>
						
					</table>
	
					<canvas id="pieChart" width="260" height="260" style="margin-top:10%"></canvas>
					
					</div>
					
					<div class="commonDiv" style="width:65%;height:500px;">
					<center><b>Problem Statistics</b></center>
					<table style="width:600px;margin-top:20px">
					
						<tbody>
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
		
		<script type="text/javascript">
			var init=function(iter,type,groupName){
				var param=new Array(3);
				param.push({name:"iter",value:iter});
				param.push({name:"type",value:type});
				param.push({name:"groupName",value:groupName});
				$.getJSON("api/pmd/getOneGroup",param,function(data){
					$("#tbody_content").empty();
					 var code = data.code;
					 if(code=="0"){
						 var arr = data.data;
						 var html;
						 $.each(arr,function(index){
							 var filePath=arr[index].filePath;
							 var line=arr[index].line;
							 var problem=arr[index].problem;
							 var link=arr[index].link;
							 html=html+'<tr>'+ 
								'<td>'+(index+1)+'</font></td>'+
								'<td width="*%"><font size="3">'+filePath+'</font></td>'+
								'<td width="5%"><font size="3">'+line+'</font></td>'+
								'<td width="*%"><font size="3">'+'<a href="'+link+'">'+problem+'</a></font></td>'+
								'</tr>';
						 });
						 $("#tbody_content").append(html);
					 }
				});
			}
			
			var getPie=function(){
				var param=new Array(1);
				param.push({name:"groupName",value:groupName});
		 		$.getJSON("api/pmd/getCurrent",param,function(data){
		 			var code = data.code;
		 			var fillColorArr=new Array('rgba(210,105,30,1)','rgba(151,187,205,1','rgba(147,112,219,1)',
		 					'rgba(218,165,32,1)','#999966','rgba(220,220,220,1)');
		 			if(code=="0"){
	 				var measure=data.data;
	 				var pieArr=new Array(6);
	 				var dataArr=new Array(6);
	 				var labelArr=new Array("Basic","Naming","Unusedcode","Codesize","Clone","Coupling");
	 				dataArr[0]=measure.basic;
	 				dataArr[1]=measure.naming;
	 				dataArr[2]=measure.unusedcode;
	 				dataArr[3]=measure.codesize;
	 				dataArr[4]=measure.clone;
	 				dataArr[5]=measure.coupling;
					for(var i=0;i<6;i++){
						pieArr[i]={
								label: labelArr[i],
								value : dataArr[i],
								color : fillColorArr[i]
							};
					}
					var chartPie = null;
					
					var ctx = document.getElementById("pieChart").getContext("2d");
					chartPie = new Chart(ctx).Pie(pieArr, {segmentShowStroke : false, showTooltips : 1});
		 				}
		 		});
		 	}
			
			 $(function(){
				 init(iter,"basic",groupName);
				 getPie();
			 });
			 
			 $("#getType").click(function(){
				 var iterValue=$("#iterSelect").val();
				 var issueType=$("#issueType").val();
				 init(iterValue,issueType,groupName);
			 });
		</script>
	
	<script src="js/projectBar.js"></script>
</div>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>