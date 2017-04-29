<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/jsp/common/head.jsp"></jsp:include>
<link href="http://cdn.datatables.net/1.10.13/css/jquery.dataTables.min.css" rel="stylesheet">
<title>CodeAnalyze</title>
</head>
<body style="padding-top: 60px">
	<jsp:include page="/jsp/common/header.jsp"></jsp:include>
	<table id="overview" class="display table-hover" style="text-align: center">
		<colgroup span="1" style="background-color: white"></colgroup>
		<colgroup span="3" style="background-color: #F1F1F1"></colgroup>
		<colgroup span="3" style="background-color: #E6E6E6"></colgroup>
		<colgroup span="3" style="background-color: #F1F1F1"></colgroup>
		<thead>
			<tr>
				<th rowspan="2">组名</th>
				<th colspan="3">迭代一</th>
				<th colspan="3">迭代二</th>
				<th colspan="3">迭代三</th>
			</tr>
			<tr>
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
		</tfoot>
		<tbody id="tbody_content">
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
		</tbody>
	</table>

</body>
<jsp:include page="/jsp/common/footer.jsp"></jsp:include>

<script>
	document.getElementById('home').className = "active";

	var getAllGroupScore = function(dt) {
		$.getJSON("api/score/api/allGroupScore", function(data) {
			$.each(data, function(i, groupScore) {
				var groupId = groupScore.groupId;
				var groupName = groupScore.groupName;
				var checkDate = groupScore.checkDate;
				var checkstyleScore = groupScore.checkstyleScore;
				var pmdScore = groupScore.pmdScore;
				var teacherScore = groupScore.teacherScore;
				
				var row = [];
				row.push("<a href='<%=request.getContextPath()%>/api/score/api/" + groupId + "'>" + groupName + "</a>");

				$.each(checkDate, function(j) {
					var csItem = checkstyleScore[j];
					row.push(csItem);

					var psItem = pmdScore[j];
					row.push(psItem);

					var tsItem = teacherScore[j];
					row.push(tsItem);
				});
				
				dt.row.add(row).draw();
			});
		});
	}

	
	$(document).ready(
			function() {
				var dt = $('#overview').DataTable({
					"pagingType" : "full_numbers",
					stateSave : true,
					"info" : true,
					"language" : {
						"lengthMenu" : "每页 _MENU_ 条记录",
						"zeroRecords" : "没有找到记录",
						"info" : "第 _PAGE_ 页 ( 总共 _PAGES_ 页 )",
						"infoEmpty" : "无记录",
						"infoFiltered" : "(从 _MAX_ 条记录过滤)",
						"paginate" : {
							"first" : "首页",
							"last" : "尾页",
							"next" : "下一页",
							"previous" : "上一页"
						},
						"search" : "搜索："
					},
					"columnDefs": [{
						"defaultContent": "-",
						"targets": "_all"
					}]
				});

				getAllGroupScore(dt);
			});
</script>
<script src="http://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
</html>
