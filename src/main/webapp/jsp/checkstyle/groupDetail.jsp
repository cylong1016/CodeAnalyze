<%--
  Created by IntelliJ IDEA.
  User: Floyd
  Date: 2017/3/28
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/checkstyle.css" type="text/css"/>
<style>
    .panel-no-boder {
        border: 0;
    }
    .panel-no-padding {
        padding: 0;
    }
</style>
<div>
    <ul class="nav nav-pills nav-stacked left-chart-nav col-md-2 col-sm-2" id="tab">
        <li role="presentation"><a href="<%=request.getContextPath()%>/api/checkstyle">Total</a></li>
        <li role="presentation" class="active"><a href="<%=request.getContextPath()%>/api/checkstyle/group">Group</a>
        </li>
        <li role="presentation"><a href="<%=request.getContextPath()%>/api/checkstyle/timeline">TimeLine</a></li>
    </ul>
    <div class="col-md-offset-2 col-sm-offset-2" id="content" data-groupId="${groupId}">
        <%--<div class="col-md-1 col-sm-1"></div>--%>
        <div id="line_charts" class="panel panel-default panel-no-boder col-md-4 col-sm-4" style="display: inline-block" >

        </div>
        <div class="col-md-1 col-sm-1"></div>
        <div id="detail" class="panel panel-success panel-no-padding col-md-4 col-sm-4">
            <div class="panel-heading">
                <h4>CheckStyle检查详细结果</h4>
            </div>
            <div class="panel-body">
                <ul class="nav nav-tabs" role="tablist" id="detail_tab">
                    <li role="presentation" class="active"><a href="#warn" aria-controls="warn" role="tab" data-toggle="tab">Warn</a></li>
                    <li role="presentation"><a href="#error" aria-controls="error" role="tab" data-toggle="tab">Error</a></li>
                </ul>
                <div class="tab-content" id="detail_content">
                    <div role="tabpanel" class="tab-pane active" id="warn"></div>
                    <div role="tabpanel" class="tab-pane" id="error"></div>
                </div>
            </div>


        </div>
    </div>
</div>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.2.0.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/echarts.common.min.js"></script>
<script>
    $(function () {
        var basePath = '<%=request.getContextPath()%>';
        var groupId = $("#content").attr("data-groupId");
        var panel_width = ($(document.body).width() - $("#tab").width()) * 15/32;
        var panel_height = ($(window).height() - $("header").height() );
        console.log($(window).height());
        console.log($(window).clientHeight);
        console.log($(document.body).clientHeight);
        console.log($(document.body).height());

        $('#detail_tab a').click(function (e) {
            e.preventDefault()
            $(this).tab('show')
        });

        function drawLineCharts(obj, data) {
            option = {
                title: {
                    text: '第'+groupId+'小组',
                    subtext: '供参考'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data:['WARN','ERROR']
                },
                toolbox: {
                    show: true,
                    feature: {
                        dataZoom: {
                            yAxisIndex: 'none'
                        },
                        dataView: {readOnly: false},
                        magicType: {type: ['line', 'bar']},
                        restore: {},
                        saveAsImage: {}
                    }
                },
                xAxis:  {
                    type: 'category',
                    boundaryGap: false,
                    data: data.timeline
                },
                yAxis: {
                    type: 'value',
                    axisLabel: {
                        formatter: '{value}'
                    }
                },
                series: [
                    {
                        name:'WARN',
                        type:'line',
                        data:data.warnNum,
                        markPoint: {
                            data: [
                                {type: 'max', name: '最大值'},
                                {type: 'min', name: '最小值'}
                            ]
                        },
                        markLine: {
                            data: [
                                {type: 'average', name: '平均值'}
                            ]
                        }
                    },
                    {
                        name:'ERROR',
                        type:'line',
                        data:data.errorNum,
                        markPoint: {
                            data: [
                                {type: 'max', name: '最大值'},
                                {type: 'min', name: '最小值'}
                            ]
                        },
                        markLine: {
                            data: [
                                {type: 'average', name: '平均值'},
                            ]
                        }
                    }
                ]
            };
            obj.setOption(option);
        }

        function drawDetailPanel(obj, data) {
//            var html = [];
//            html.append('<div class="panel panel-success">');
//            html.append('<div class="panel-heading">');
//            html.append('<h4 class="panel-title" id="warn_panel_title">');

        }

        console.log(groupId);
        $.ajax({
            url: basePath+"/api/checkstyle/api/group/"+groupId,
            method: "GET",
        }).done(function (data) {
            var querys = []
            try {
                querys = JSON.parse(data);
            } catch(err){
                console.log(err.message);
            }
            console.log(querys);
            $("#line_charts").attr("style", "width:" + panel_width + "px;height:" + panel_height + "px;");
            var line_chrts = echarts.init(document.getElementById("line_charts"));
            drawLineCharts(line_chrts, querys);
            $("#detail").attr("style", "width:" + panel_width + "px;height:" + panel_height + "px;");
            drawDetailPanel($("#detail_content"), querys);
        });
    })
</script>
