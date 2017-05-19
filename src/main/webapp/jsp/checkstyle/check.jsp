<%--
  Created by IntelliJ IDEA.
  User: Floyd
  Date: 2017/3/28
  Time: 14:30
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
        <li role="presentation" class="active"><a
                href="<%=request.getContextPath()%>/api/checkstyle">Check</a></li>
        <li role="presentation"><a href="<%=request.getContextPath()%>/api/checkstyle/group">Group</a></li>
        <li role="presentation"><a href="<%=request.getContextPath()%>/api/checkstyle/config">Config</a></li>
        <li role="presentation"><a href="<%=request.getContextPath()%>/api/checkstyle/stats">Statistics</a></li>
    </ul>
    <div class="col-md-offset-2 col-sm-offset-2" id="content">
        <div id="check_info" class="panel panel-default panel-no-boder col-md-4 col-sm-4">
            <form class="form-horizontal" role="form" id="newCheckForm">
                <fieldset>
                    <legend>添加新的检查</legend>
                    <div class="form-group">
                        <label for="newCheckDate" class="col-md-2 col-sm-2 control-label">检查日期</label>
                        <div class="input-group date form_date col-sm-6 col-md-6" data-date="" data-date-format="dd MM yyyy" data-link-field="newCheckDate" data-link-format="yyyy-mm-dd">
                            <input class="form-control" type="text" value="" readonly>
                            <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                        </div>
                        <input type="hidden" id="newCheckDate" value="" /><br/>
                    </div>
                    <div class="form-group">
                        <label for="newCheckDescription" class="col-sm-2 col-md-2 control-label">检查描述</label>
                        <div class="input-group col-sm-6 col-md-6">
                            <input type="text" class="form-control" id="newCheckDescription" placeholder="添加描述">
                        </div><br/>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-md-offset-2 ">
                            <button type="button" class="btn btn-default" id="newCheckSubmit">提交</button>
                        </div><br/>
                    </div>
                </fieldset>
            </form><br />
            <div id="line_charts"></div>
        </div>
        <div class="col-md-1 col-sm-1"></div>
        <div id="detail" class="panel panel-info panel-no-padding col-md-4 col-sm-4">
            <div class="panel-heading">
                <h4>CheckStyle检查详细结果</h4>
            </div>
            <div class="panel-body">
                <ul class="nav nav-tabs" role="tablist" id="checklog_tab"></ul>
                <div class="tab-content" id="detail_content">

                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.2.0.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/echarts.common.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript">

    $(function () {
        var checkLog = [];
        var panel_width = ($(window).width() - $("#tab").width()) * 15/32;
        var panel_height = ($(window).height() - parseInt($('body').css('padding-top')) - $("header").height() );
        var line_charts_height = panel_height - $('#newCheckForm').height();
        var line_charts_width = panel_width * 7/8;

        $.ajax({
            url: "<%=request.getContextPath()%>/api/checkstyle/api/check",
            method: "GET"
        }).done(function (data) {
            try{
                checkLog = JSON.parse(data);
            } catch (err){
                console.log(err.message);
            }
            checkLog.sort(function (a, b) {
                return a.id - b.id;
            });
            console.log(checkLog);
            $('.form_date').datetimepicker({
                language:  'zh-CN',
                weekStart: 1,
                todayBtn:  1,
                autoclose: 1,
                todayHighlight: 1,
                startView: 2,
                minView: 2,
                forceParse: 0
            });

            $("#check_info").attr("style", "width:" + panel_width + "px;height:" + panel_height + "px;");
            $("#line_charts").attr("style", "width:" + line_charts_width + "px;height:" + line_charts_height + "px;")

            var line_charts = echarts.init(document.getElementById("line_charts"));
            drawLineChart(line_charts, checkLog);
            line_charts.on('click', function (params) {
                if (params.componentType === 'markPoint' || params.componentType === 'series') {
                    // 点击到了 markPoint 上
                    var index = params.dataIndex;
                    $($('#checklog_tab a')[index]).tab('show');
                }
            });

            $("#detail").attr("style", "width:" + panel_width + "px;height:" + panel_height + "px;");
            drawTabPanel(checkLog);
        });
        
        $('#newCheckForm').on('click', '#newCheckSubmit', function () {
            var date = $('.form_date').data('datetimepicker').getDate();
            var description = $('#newCheckDescription').val();
            $.ajax({
                url: "<%=request.getContextPath()%>/api/checkstyle/api/check",
                method: "POST",
                data: {
                    day: date.getDate(),
                    month: date.getMonth()+1,
                    year: date.getFullYear(),
                    description: description
                }
            }).done(function (data) {
                var line_charts = echarts.getInstanceByDom(document.getElementById("line_charts"));
                var option = line_charts.getOption();
                option.xAxis[0].data.push(data);
                if(option.series.length===1){
                    var future = [];
                    for(var i=0, len=option.series[0].data.length-1; i<len; ++i){
                        future.push('-');
                    }
                    future.push(option.series[0].data[option.series[0].data.length-1]);
                    future.push(0);
                    option.series[0].data.push('-');
                    option.series.push({
                        type: 'line',
                        data: future,
                        lineStyle:{
                        normal:{
                            type: 'dotted',
                        }
                        }
                    });
                } else if(option.series.length===2){
                    option.series[0].data.push('-');
                    option.series[1].data.push(0);
                }
                line_charts.setOption(option);
            });
        });

        function formatDate(date) {
            var day = date.getDate();
            var month = date.getMonth()+1;
            var year = date.getFullYear();
            return year+'-'+month+'-'+day;
        }

        function drawLineChart(obj, data) {
            var checkdate = [], checkgroup=[], check_future=[];
            $.each(data, function (index, value) {
                checkdate.push(formatDate(new Date(value.date)));
                if(value.groups.length===0){
                    check_future.push(0);
                }else {
                    checkgroup.push(value.groups.length + parseInt(Math.random()*10));
                }
            });
            check_future.unshift(checkgroup[checkgroup.length-1]);
            for(var i=0, len=data.length-check_future.length; i<len; ++i){
                check_future.unshift('-');
            }

            var option = {
                title: {
                    text: 'CheckStyle 每次检查组数',
                    subtext: '供参考'
                },
                tooltip: {
                    trigger: 'axis'
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
                    data: checkdate
                },
                yAxis: {
                    type: 'value',
                    axisLabel: {
                        formatter: '{value}'
                    }
                },
                series: [
                    {
                        name: 'check组数',
                        type: 'line',
                        data: checkgroup,
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
                        type: 'line',
                        data: check_future,
                        lineStyle:{
                            normal:{
                                type: 'dotted',
                            }
                        }
                    }
                ]
            };
            obj.setOption(option);
        }

        function drawTabPanel(data) {
            var tab_html = [], panel_html = [];
            $.each(data, function (index, value) {
                if(value.groups.length===0){
                    return;
                }
                if (index===0){
                    tab_html.push('<li role="presentation" class="active"><a href="#checklog_'+value.id+'" ar            ia-controls="checklog_'+value.id+'" role="tab" data-toggle="tab">'+value.description+'</a></li>');
                    panel_html.push('<div role="tabpanel" class="tab-pane active" id="checklog_'+value.id+'"><br />');
                } else {
                    tab_html.push('<li role="presentation"><a href="#checklog_'+value.id+'" aria-controls="checklog_'+value.id+'" role="tab" data-toggle="tab">'+value.description+'</a></li>');
                    panel_html.push('<div role="tabpanel" class="tab-pane" id="checklog_'+value.id+'"><br />');
                }
                panel_html.push('<table class="table table-bordered table-responsive table-striped check-table">');
                panel_html.push('<thead><tr><td>组名</td><td>Warn数量</td><td>Error数量</td></tr></thead>');
                panel_html.push('<tbody></tbody>');
                panel_html.push('</table></div>');
            });
            $('#checklog_tab').append(tab_html.join(''));
            $('#detail_content').append(panel_html.join(''));
            $.each($('.check-table'), function (index, obj) {
                $(obj).DataTable({
                    'searching': false,
                    'info': false,
                    'order': [[1, 'asc']],
                    'scrollY': panel_height/1.5,
                    'scrollCollapse':true,
                    'paging': false,
                    'data': data[index].groups,
                    'columns': [
                        {data: 'name'},
                        {data: 'warnCount'},
                        {data: 'errorCount'},
                    ]
                });
            });
        }
    })
</script>
