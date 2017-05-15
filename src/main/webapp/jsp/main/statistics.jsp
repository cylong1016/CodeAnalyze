<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<style type="text/css">

.title {
	height: 50px;
	line-height: 50px;
	font-family: "微软雅黑", "黑体", sans-serif;
	font-size: 25px;
	background-color: #E6E6E6;
	text-align: center;
	font-weight: bold;
}

.charts {
	height: 500px;
	width: 50%;
	float: left;
	margin-top: 10px;
}

#iter {
	width: 150px;
	margin-top: 15px;
	margin-left: 15px;
}

</style>

<div class="title">老师给分与编码规范相关性统计</div>

<select id="iter" class="form-control"></select>

<div class="charts"></div>
<div class="charts"></div>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/echarts.common.min.js"></script>

<script>
	document.getElementById('sta').className = "active";
	var basePath = '<%=request.getContextPath()%>';
	var currentIter = 1;
	
	var getIter = function() {
 		$.getJSON(basePath + "/api/pmdAna/getIter", function(data) {
 			var code = data.code;
			if(code == "0") {
				var currentIter = data.data;
				for (var i = 1; i <= currentIter; i++) {
					var option = $('<option />', {
						value : i,
						text : 'Iterator ' + i,
				    }).appendTo("#iter");
				}
			}
 		});
 	}
	
	var createOption = function() {
	
		var markLineOpt = {
			label : {
				normal : {
					formatter : '',
					textStyle : {
						align : 'right'
					}
				}
			},
			lineStyle : {
				normal : {
					type : 'solid'
				}
			},
			tooltip : {
				formatter : ''
			},
			data : [ [ {
				coord : null,
				symbol : 'none'
			}, {
				coord : null,
				symbol : 'none'
			} ] ]
		};
		
		var option = {
			animation : false,
			title : {
				text : '',
				x : 'center',
				y : 0
			},
			grid : {
				x : '10%',
				y : '15%',
				width : '80%',
				height : '75%'
			},
			tooltip : {
				formatter : '(x, y): ({c})'
			},
			xAxis : {
				name: '老师给分',
				gridIndex : 0,
				min : 0,
				max : 100
			},
			yAxis : {
				name: '',
				gridIndex : 0,
				min : 0,
				max : 100
			},
			series : {
				name : '',
				type : 'scatter',
				xAxisIndex : 0,
				yAxisIndex : 0,
				data : null,
				markLine : markLineOpt
			}
		};
		
		return option;
	}

	var getRegression = function(options) {
		$.getJSON(basePath + "/api/score/api/getRegression/" + currentIter, function(data) {
			$.each(data, function(i, reg) {
				var fun;
				var option = options[i];
				if(reg.cffcA == 1 && reg.cffcB == 0) {
					fun = "y = x";
				} else if(reg.cffcA == 1 && reg.cffcB != 0) {
					fun = "y = x + " + reg.cffcB;
				} else if(reg.cffcA != 1 && reg.cffcB == 0) {
					fun = "y = " + reg.cffcA + " * x";
				} else {
					fun = "y = " + reg.cffcA + " * x + " + reg.cffcB;
				}
				var markLineOpt = option.series.markLine;
				markLineOpt.tooltip.formatter = fun;
				markLineOpt.label.normal.formatter = fun;
				markLineOpt.data[0][0].coord = [0, reg.cffcB];
				markLineOpt.data[0][1].coord = [100, 100 * reg.cffcA + reg.cffcB];
				var title = reg.toolName.toUpperCase() + " 给分";
				option.title.text = title;
				option.yAxis.name = title;

				echarts.init($(".charts")[i]).setOption(option);
			});
		});
	}
	
	var getAllGroupScore = function(options) {
		var pmdData = new Array();
		var checkstyleData = new Array();
				
		$.getJSON(basePath + "/api/score/api/allGroupScore", function(data) {
			$.each(data, function(i, groupScore) {
				var groupId = groupScore.groupId;
				var groupName = groupScore.groupName;
				var checkDate = groupScore.checkDate;
				var checkstyleScore = groupScore.checkstyleScore;
				var pmdScore = groupScore.pmdScore;
				var teacherScore = groupScore.teacherScore;
				
				var index = currentIter - 1;
				pmdData[i] = [pmdScore[index], teacherScore[index]];
				checkstyleData[i] = [checkstyleScore[index], teacherScore[index]];
			});
			
			options[0].series.data = pmdData;
			echarts.init($(".charts")[0]).setOption(options[0]);
			options[1].series.data = checkstyleData;
			echarts.init($(".charts")[1]).setOption(options[1]);
			
		});
	}
	
	var refreshCharts = function() {
		var options = [createOption(), createOption()];
		getRegression(options);
		getAllGroupScore(options);
	}
	
	$(document).ready(function() {
		getIter();
		refreshCharts();
	});
	
	$("#iter").change(function() {
		currentIter = $("#iter").val();
		refreshCharts();
	});
</script>
