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
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.dataTables.min.css" type="text/css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/dataTables.bootstrap.min.css" type="text/css"/>

<style>
    .panel-no-boder {
        border: 0;
    }
    .panel-no-padding {
        padding: 0;
    }
</style>
<div class="content">
    <ul class="nav nav-pills nav-stacked left-chart-nav col-md-2 col-sm-2" id="tab">
        <li role="presentation"><a href="<%=request.getContextPath()%>/api/checkstyle">Check</a></li>
        <li role="presentation" class="active"><a href="<%=request.getContextPath()%>/api/checkstyle/group">Group</a></li>
        <li role="presentation"><a href="<%=request.getContextPath()%>/api/checkstyle/config">Config</a></li>
        <li role="presentation"><a href="<%=request.getContextPath()%>/api/checkstyle/stats">Statistics</a></li>
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
                    <li role="presentation" id="li_warn"><a href="#warn" aria-controls="warn" role="tab" data-toggle="tab">Warn</a></li>
                    <li role="presentation" id="li_error"><a href="#error" aria-controls="error" role="tab" data-toggle="tab">Error</a></li>
                </ul>
                <div class="tab-content" id="detail_content">
                    <div role="tabpanel" class="tab-pane" id="warn"><br /></div>
                    <div role="tabpanel" class="tab-pane" id="error"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.2.0.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/echarts.common.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataTables.bootstrap.min.js"></script>
<script>
    $(function () {
        var basePath = '<%=request.getContextPath()%>';
        var groupId = $("#content").attr("data-groupId");
        var panel_width = ($(window).width() - $("#tab").width()) * 15/32;
        var panel_height = ($(window).height() - parseInt($('body').css('padding-top')) - $("header").height() );
        var detail_info = [];
        var detail_timeline = [];
        var detail_warn = {};
        var detail_error = {};
        var grades = [];
        $('#'+'${type}').addClass('active');
        $('#li_'+'${type}').addClass('active');
        $.ajax({
            url: basePath+"/api/checkstyle/api/group/"+groupId,
            method: "GET",
        }).done(function (data) {
            try {
                detail_info = JSON.parse(data);
            } catch(err){
                console.log(err.message);
            }
            console.log(detail_info);
            $.each(detail_info.checkstyleResults, function (key, value) {
                detail_timeline.push(key);
                detail_warn[key] = {};
                detail_error[key] = {};
                grades.push(value.grade);
                // 统计warn的subtype，以及每一个subtype的个数
                $.each(value.warn, function (index, value2) {
                    // 如果sub_type不存在
                    if ( $.inArray(value2.sub_type, Object.keys(detail_warn[key])) === -1){
                        detail_warn[key][value2.sub_type] = 0;
                    }
                    detail_warn[key][value2.sub_type] += 1;
                });
                $.each(value.error, function (index, value2) {
                    // 如果sub_type不存在
                    if ( $.inArray(value2.sub_type, Object.keys(detail_error)) === -1){
                        detail_error[key][value2.sub_type] = 0;
                    }
                    detail_error[key][value2.sub_type] += 1;
                });
            });
            console.log(grades);
            $("#line_charts").attr("style", "width:" + panel_width + "px;height:" + panel_height + "px;");
            var line_chrts = echarts.init(document.getElementById("line_charts"));
            drawLineCharts(line_chrts);
            $("#detail").attr("style", "width:" + panel_width + "px;height:" + panel_height + "px;");
            drawDetailPanel($("#detail_content"));

            line_chrts.on('click', function (params) {
                if (params.componentType === 'markPoint' || params.componentType === 'series') {
                    console.log(params);
                    // 点击到了 markPoint 上
                    var date = params.name;
                    var type = params.seriesName;
                    if(type==='WARN'){
                        $('#detail_tab a[href="#warn"]').tab('show');
                        $('#collapse_'+date).collapse('show');
                    }else if(type==='ERROR'){
                        $('#detail_tab a[href="#error"]').tab('show');
                    }
                }
            });
        });

        $('#detail_tab a').click(function (e) {
            e.preventDefault()
            $(this).tab('show')
        });
        function drawLineCharts(obj) {
            var warn_num = [], error_num = [];
            var line_name = ['Warn', 'Error', 'Grade']
            $.each(detail_warn, function (key, value) {
                var count = 0;
                $.each(value, function (key2, val2) {
                    count += val2;
                });
                warn_num.push(count);
            });
            $.each(detail_error, function (key, value) {
                var count = Math.random()*120;
                $.each(value, function (key2, val2) {
                    count += val2;
                });
                error_num.push(count);
            });
            var option = {
                title: {
                    text: '第'+detail_info.id+'小组',
                    subtext: '供参考'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: line_name
                },
                toolbox: {
                    show: true,
                    feature: {
//                        dataZoom: {
//                            yAxisIndex: 'none'
//                        },
                        dataView: {readOnly: false},
                        magicType: {type: ['line', 'bar']},
                        restore: {},
                        saveAsImage: {}
                    }
                },
                xAxis:  {
                    type: 'category',
                    boundaryGap: false,
                    data: detail_timeline
                },
                yAxis:[ {
                    type: 'value',
                    axisLabel: {
                        formatter: '{value}'
                    }
                },{
                    type:'value',
                    axisLabel: {
                        formatter: '{value}'
                    }
                }],
                series: [
                    {
                        name: line_name[0],
                        type: 'line',
                        data: warn_num,
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
                        name: line_name[1],
                        type: 'line',
                        data: error_num,
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
                    },
                    {
                        name: line_name[2],
                        type: 'line',
                        data: grades,
                        yAxisIndex: 1,
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
                    },
                ]
            };
            obj.setOption(option);
        }

        function drawDetailPanel(obj) {
            var html_warn = [], html_error = [];

            html_warn.push('<div class="panel-group" id="warn_detail" role="tablist" aria-multiselectable="true">');
            $.each(detail_warn, function (key, value) {
                html_warn.push('<div class="panel panel-default">');
                html_warn.push('<div class="panel-heading" role="tab" id="heading_'+key+'">');
                html_warn.push('<h4 class="panel-title"> <a role="button" data-toggle="collapse" data-parent="#warn_detail" href="#collapse_'+key+'" aria-expanded="true" aria-controls="collapse_'+key+'">');
                html_warn.push(key+'  第'+ ($.inArray(key, detail_timeline)+1) + '次检查');
                html_warn.push('</a></h4></div>');
                // 判断 要展开哪次 检查详情
                if($.inArray(key, detail_timeline)== ${check}){
                    html_warn.push('<div id="collapse_'+key+'" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_'+key+'">');
                }else{
                    html_warn.push('<div id="collapse_'+key+'" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading_'+key+'">');
                }
                html_warn.push('<div class="panel-body">');
                html_warn.push('<table id="'+key+'" class="table table-striped table-bordered datatable">');
                html_warn.push('<thead><tr><td>Warn类型</td><td>数量</td></tr></thead>');
                html_warn.push('<tbody>');
                $.each(value, function (key2, val2) {
                    html_warn.push('<tr>');
                    html_warn.push('<td>' + key2 + '</td>');
                    html_warn.push('<td>' + val2 + '</td>');
                    html_warn.push('</tr>')
                });
                html_warn.push('</tbody>');
                html_warn.push('</table>');
                html_warn.push('</div>');
                html_warn.push('</div>');
                html_warn.push('</div>');
            });
            html_warn.push('</div>');
            $('#warn').append(html_warn.join(''));
            // 设置一次只显示一个
            $('#warn_detail').on('show.bs.collapse','.collapse', function() {
                $('#warn_detail').find('.collapse.in').collapse('hide');
            });
            $.each($('.datatable'), function (index,obj) {
                $(obj).DataTable({
                    'searching': false,
                    'info': false,
                    'order': [[1, 'desc']],
                    'scrollY': panel_height/2.4,
                    'scrollCollapse':true,
                    'paging': false,
                });
            });

        }
    })
</script>
