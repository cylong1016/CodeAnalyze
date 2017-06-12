<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<link href="http://cdn.datatables.net/1.10.13/css/jquery.dataTables.min.css" rel="stylesheet">
<title>CodeAnalyze</title>
<style type="text/css">
	table, th, td {
		text-align: center;
	}
	
	.table > thead > tr > th {
    	vertical-align: middle;
	}

	body {
		padding-top: 50px;
	}
	
	.content {
		margin-top: 10px;
	}
	
	colgroup.odd {
		background-color: #E6E6E6;
	}
	
	colgroup.even {
		background-color: #F1F1F1;
	}
	
	select.form-control {
		display: inline;
		width: inherit;
	}
	
	input[type="search"].form-control {
		display: inline;
		width: inherit;
	}
	
</style>

	<div class="content">
		<table id="overview" class="table table-hover">
			<colgroup class="odd" span="1"></colgroup>
			<colgroup class="even" span="3"></colgroup>
			<colgroup class="odd" span="3"></colgroup>
			<colgroup class="even" span="3"></colgroup>
			<thead>
				<tr>
					<th rowspan="2"></th>
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
					<th></th>
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
				<!-- getAllGroupScore -->
			</tbody>
		</table>
	</div>

<script>
	document.getElementById('home').className = "active";
	var basePath = '<%=request.getContextPath()%>';

	var getAllGroupScore = function(dt) {

		$.getJSON(basePath + "/api/score/api/allGroupScore", function(data) {
			$.each(data, function(i, groupScore) {
				var groupId = groupScore.groupId;
				var groupName = groupScore.groupName;
				var checkDate = groupScore.checkDate;
				var checkstyleScore = groupScore.checkstyleScore;
				var pmdScore = groupScore.pmdScore;
				var teacherScore = groupScore.teacherScore;
				
				var row = [];
				row.push()
				row.push("<a href='<%=request.getContextPath()%>/api/group/" + groupId + "'>" + groupName + "</a>");

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

	
	$(document).ready(function() {
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
		
		$("select").addClass("form-control");
		$("input[type=search]").addClass("form-control");

		getAllGroupScore(dt);
        dt.on( 'order.dt search.dt', function () {
            dt.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
                cell.innerHTML = i+1;
            } );
        } ).draw();
	});
</script>

<script src="http://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
</html>
