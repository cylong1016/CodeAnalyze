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

.charts-list {
	width: 100%;
	height: 100%;
}

.charts {
	height: 200px;
	width: 33%;
	float: left;
	margin-top: 20px;
}

#iter {
	width: 150px;
	margin-top: 15px;
	margin-left: 15px;
}

</style>

<div class="title">老师评分与编码规范相关性统计</div>

<select id="iter" class="form-control"></select>

<div class="charts-list"></div>

<script src="http://code.jquery.com/jquery-latest.js"></script>
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
				x : '18%',
				y : '26%',
				width : '65%',
				height : '60%'
			},
			tooltip : {
				formatter : '(x, y): ({c})'
			},
			xAxis : {
				name: '老师评分',
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
				var fun = "";
				var correlation = "";
				var option = options[i];
				if(reg.cffcA !== 0) {
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
					markLineOpt.data[0][1].coord = [100, -1];
					if(reg.cffcA > 0) {
						markLineOpt.data[0][0].coord = [0, reg.cffcA];
						correlation = "（正线性相关）";
					} else {
						markLineOpt.data[0][0].coord = [(0.0 - reg.cffcB) / reg.cffcA, 0];
						correlation = "（负线性相关）";
					}
				} else {
					correlation = "（非线性相关）";
				}
				var title = reg.toolName;
				option.title.text = title + correlation;
				option.yAxis.name = title;

				echarts.init($(".charts")[i]).setOption(option);
			});
		});
	}
	
	var getScatter = function(options) {
				
		$.getJSON(basePath + "/api/score/api/getScatter/" + currentIter, function(data) {
			$(".charts").each(function(i, chart) {
				var toolData = data[$(chart).attr("id")];
				options[i].yAxis.max = toolData[0][1];
				options[i].series.data = toolData;
				echarts.init($(".charts")[i]).setOption(options[i]);
			});
		});
	}
	
	var refreshCharts = function() {
		var names = ["checkstyle",
		             "pmd",
		             "Annotations",
                     "Block Checks",
                     "Class Design",
                     "Coding",
                     "Header",
                     "Imports",
                     "Javadoc Comments",
                     "Metrics",
                     "Miscellaneous",
                     "Modifiers",
                     "Naming Conventions",
                     "Regexp",
                     "Size Violations",
                     "WhiteSpaces"];
		var options = [];
		for(var i = 0; i < names.length; i++) {
			$('<div />', {
				class : "charts",
				id : names[i],
		    }).appendTo(".charts-list");
			options[i] = createOption();
		}
		getRegression(options);
		getScatter(options);
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
