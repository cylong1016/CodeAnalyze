<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="../common/head.jsp"></jsp:include>
	<link href="http://cdn.datatables.net/1.10.13/css/jquery.dataTables.min.css" rel="stylesheet">
	<title>CodeAnalyze</title>
</head>
<body style="padding-top:60px">
	<jsp:include page="../common/header.jsp"></jsp:include>
	<table id="overview" class="table table-hover" >
		<thead>
			<tr>
				<th>Group</th>
				<th>CheckStyle</th>
				<th>PMD</th>
				<th>Teacher</th>
				<th>CheckStyle</th>
				<th>PMD</th>
				<th>Teacher</th>
				<th>CheckStyle</th>
				<th>PMD</th>
				<th>Teacher</th>
			</tr>
		</thead>
		<tfoot>
			<tr>
				<th></th>
				<th>CheckStyle</th>
				<th>PMD</th>
				<th>Teacher</th>
				<th>CheckStyle</th>
				<th>PMD</th>
				<th>Teacher</th>
				<th>CheckStyle</th>
				<th>PMD</th>
				<th>Teacher</th>
			</tr>
		</tfoot>
		<tbody>
			<tr>
				<td>Group 1</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
			</tr>
			<tr>
				<td>Group 2</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
			</tr>
			<tr>
				<td>Group 3</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
			</tr>
			<tr>
				<td>Group 3</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
			</tr>
			<tr>
				<td>Group 3</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
			</tr>
			<tr>
				<td>Group 3</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
			</tr>
			<tr>
				<td>Group 3</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
			</tr>
			<tr>
				<td>Group 3</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
			</tr>
			<tr>
				<td>Group 3</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
			</tr>
			<tr>
				<td>Group 3</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
			</tr>
			<tr>
				<td>Group 3</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
			</tr>
			<tr>
				<td>Group 3</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
				<td>10</td>
			</tr>
		</tbody>
	</table>

</body>
<jsp:include page="../common/footer.jsp"></jsp:include>
<script src="http://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
<script>
	document.getElementById('home').className = "active";
	$(document).ready(function(){
		$('#overview').DataTable();
	});
</script>
</html>
