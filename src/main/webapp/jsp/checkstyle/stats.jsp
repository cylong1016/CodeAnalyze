<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/14
  Time: 1:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/checkstyle.css" type="text/css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.dataTables.min.css" type="text/css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/dataTables.bootstrap.min.css" type="text/css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-datetimepicker.min.css" type="text/css"/>
<style>
    .panel-no-padding {
        padding: 0;
    }
    .panel-no-boder {
        border: 0;
    }
</style>
<div>
    <ul class="nav nav-pills nav-stacked left-chart-nav col-md-2 col-sm-2" id="tab" role="tablist">
        <li role="presentation"><a href="<%=request.getContextPath()%>/api/checkstyle">Check</a></li>
        <li role="presentation"><a href="<%=request.getContextPath()%>/api/checkstyle/group">Group</a></li>
        <li role="presentation"><a href="<%=request.getContextPath()%>/api/checkstyle/config">Config</a></li>
        <li role="presentation" class="active"><a href="<%=request.getContextPath()%>/api/checkstyle/stats">Statistics</a></li>
    </ul>
    <div class="col-md-offset-2 col-sm-offset-2" id="content">
        <br/>
        <div id="charts" class="col-md-offset-1 col-sm-offset-1" style="width: 600px; height: 400px;">
            
        </div>
    </div>
</div>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.2.0.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/echarts.common.min.js"></script>
<script type="text/javascript">
    $(function () {
        var basePath = "<%=request.getContextPath()%>";
        $.ajax({
            url: basePath+'/api/checkstyle/api/stat/All'
        }).done(function (data) {
            var querys = []
            try{
                querys = JSON.parse(data);
                console.log(querys);
            }catch(e) {
                console.log(e.message)
            }
        });

        var dataAll = [
            [
                [10.0, 8.04],
                [8.0, 6.95],
                [13.0, 7.58],
                [9.0, 8.81],
                [11.0, 8.33],
                [14.0, 9.96],
                [6.0, 7.24],
                [4.0, 4.26],
                [12.0, 10.84],
                [7.0, 4.82],
                [5.0, 5.68]
            ],
            [
                [10.0, 9.14],
                [8.0, 8.14],
                [13.0, 8.74],
                [9.0, 8.77],
                [11.0, 9.26],
                [14.0, 8.10],
                [6.0, 6.13],
                [4.0, 3.10],
                [12.0, 9.13],
                [7.0, 7.26],
                [5.0, 4.74]
            ],
            [
                [10.0, 7.46],
                [8.0, 6.77],
                [13.0, 12.74],
                [9.0, 7.11],
                [11.0, 7.81],
                [14.0, 8.84],
                [6.0, 6.08],
                [4.0, 5.39],
                [12.0, 8.15],
                [7.0, 6.42],
                [5.0, 5.73]
            ],
            [
                [8.0, 6.58],
                [8.0, 5.76],
                [8.0, 7.71],
                [8.0, 8.84],
                [8.0, 8.47],
                [8.0, 7.04],
                [8.0, 5.25],
                [19.0, 12.50],
                [8.0, 5.56],
                [8.0, 7.91],
                [8.0, 6.89]
            ]
        ];

        var markLineOpt = {
            animation: false,
            label: {
                normal: {
                    formatter: 'y = 0.5 * x + 3',
                    textStyle: {
                        align: 'right'
                    }
                }
            },
            lineStyle: {
                normal: {
                    type: 'solid'
                }
            },
            tooltip: {
                formatter: 'y = 0.5 * x + 3'
            },
            data: [[{
                coord: [0, 3],
                symbol: 'none'
            }, {
                coord: [20, 13],
                symbol: 'none'
            }]]
        };

        var option = {
            title: {
                text: '四次检查 得分与编码规范相关性统计',
                x: 'center',
                y: 0
            },
            grid: [
                {x: '7%', y: '7%', width: '38%', height: '38%'},
                {x2: '7%', y: '7%', width: '38%', height: '38%'},
                {x: '7%', y2: '7%', width: '38%', height: '38%'},
                {x2: '7%', y2: '7%', width: '38%', height: '38%'}
            ],
            tooltip: {
                formatter: 'Group {a}: ({c})'
            },
            xAxis: [
                {gridIndex: 0, min: 0, max: 20},
                {gridIndex: 1, min: 0, max: 20},
                {gridIndex: 2, min: 0, max: 20},
                {gridIndex: 3, min: 0, max: 20}
            ],
            yAxis: [
                {gridIndex: 0, min: 0, max: 15},
                {gridIndex: 1, min: 0, max: 15},
                {gridIndex: 2, min: 0, max: 15},
                {gridIndex: 3, min: 0, max: 15}
            ],
            series: [
                {
                    name: 'I',
                    type: 'scatter',
                    xAxisIndex: 0,
                    yAxisIndex: 0,
                    data: dataAll[0],
                    markLine: markLineOpt
                },
                {
                    name: 'II',
                    type: 'scatter',
                    xAxisIndex: 1,
                    yAxisIndex: 1,
                    data: dataAll[1],
                    markLine: markLineOpt
                },
                {
                    name: 'III',
                    type: 'scatter',
                    xAxisIndex: 2,
                    yAxisIndex: 2,
                    data: dataAll[2],
                    markLine: markLineOpt
                },
                {
                    name: 'IV',
                    type: 'scatter',
                    xAxisIndex: 3,
                    yAxisIndex: 3,
                    data: dataAll[3],
                    markLine: markLineOpt
                }
            ]
        };
        var charts = echarts.init(document.getElementById('charts'));
        charts.setOption(option);
    })
</script>