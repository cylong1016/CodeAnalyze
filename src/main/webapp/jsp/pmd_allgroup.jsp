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
					 <form id="select_query">
						<select id="iter" name="iter" class="form-control commonSelect" style="width:130px"> 
						</select>
					</form>
					
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
						  <tbody id="tbody_content">
							
						  </tbody>
						</table>
					</div>
					
					
					<div class="tab-pane" id="panel-912874">
						<!--<canvas id="myChart" width="300" height="300"></canvas>-->
						
							<table style="float:left;width:200px;margin-top:10%;">
								<tbody  id="squareBody">
									<tr>
										<td colspan=2><b>Average statistics</b></td>
									</tr>
								</tbody>
							</table>
							
							<canvas id="barChart" width="800" height="400"></canvas>
					</div>
				</div>
			</div>
	<input type="hidden" id="iterHidden">
</div>

	<script type="text/javascript">
	 	var getIter=function(){
	 		$.getJSON("api/pmd/getIter",function(data){
	 			var colorArr=new Array('#dcdcdc','#97bbcd','#9370DB');
	 			var code = data.code;
 				if(code=="0"){
 					var iter=data.data;
 					 $("#iterHidden").val(iter);
 					for (var i=iter;i>0;i--)
 					{
 						var html='<option value='+i+'>'+'Iterator'+i+'</option>';
 					 	$("#iter").append(html);
 					}
 					for(var i=1;i<iter+1;i++){
 						var color=colorArr[i-1];
 					 	var tr='<tr><td height=40 align=center><div class="square" style="background:'+color+'"></div></td>'+
 					 	'<td>Iterator'+i+'</td></tr>';
 						$("#squareBody").append(tr);
 					}
 				}
	 		});
	 	}
	 	
	 	
	 	var getBar=function(){
	 		$.getJSON("api/pmd/getAve",function(data){
	 			var code = data.code;
	 			var labelArr=new Array("Basic","Clone","Codesize","Coupling","Naming","Unusedcode");
	 			var fillColorArr=new Array('rgba(220,220,220,0.5)','rgba(151,187,205,0.5)','rgba(147,112,219,0.5)');
 				if(code=="0"){
 				var dataArr=data.data;
	 			var barArr=new Array(dataArr.length);
					for(var i=0;i<dataArr.length;i++){
						barArr[i]={
								barItemName:labelArr[i],
								fillColor : fillColorArr[i],
								strokeColor : fillColorArr[i],
								data : dataArr[i]
							};
					}
					var barData = {
							labels :labelArr,
							datasets : barArr
						};
					setBar(barData);
	 				}
	 		});
	 	}
	 	
	 	var getAll=function(iter){
	 		$("#tbody_content").empty();
	 		 var param = $("#select_query").serializeArray();
	 		 param.push({name:"iter",value:iter});
	 		$.getJSON("api/pmd/getAll",param,function(data){
	 			var code = data.code;
 				if(code=="0"){
 					var arr=data.data;
 					var html;
 					 $.each(arr,function(index){
 						var groupName=arr[index].groupName;
 						var basic=arr[index].basic;
 						var naming=arr[index].naming;
 						var unusedcode=arr[index].unusedcode;
 						var codesize=arr[index].codesize;
 						var clone=arr[index].clone;
 						var coupling=arr[index].coupling;
 						html=html+'<tr>'+
							  '<td>'+(index+1)+'</td>'+
							  '<td><a href="jsp/pmd_onegroup.jsp?groupName='+groupName+'&iter='+$("#iterHidden").val()+'">'+groupName+'</a></td>'+
							  '<td>'+basic+'</td>'+
							  '<td>'+naming+'</td>'+
							  '<td>'+unusedcode+'</td>'+
							  '<td>'+codesize+'</td>'+
							  '<td>'+clone+'</td>'+
							  '<td>'+coupling+'</td>'+
							  '</tr>';
 					 });
 						$("#tbody_content").append(html);
 				}
	 		});
	 	}
	 	
	 	 $(function(){
	 		getIter();
	 		getAll(0);
	 		getBar();
	 	 });
	 </script>
	 <script src="js/groupBar.js"></script>
	 
	 <script>
	 $("#iter").change(function(){
		 var iterValue=$("#iter").val();
		 getAll(iterValue);;
		});
	 </script>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>