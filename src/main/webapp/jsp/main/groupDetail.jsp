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
    <%--<ul class="nav nav-pills nav-stacked left-chart-nav col-md-2 col-sm-2" id="tab">--%>
        <%--<li role="presentation"><a href="<%=request.getContextPath()%>/api/checkstyle">Check</a></li>--%>
        <%--<li role="presentation" class="active"><a href="<%=request.getContextPath()%>/api/checkstyle/group">Group</a></li>--%>
        <%--<li role="presentation"><a href="<%=request.getContextPath()%>/api/checkstyle/config">Config</a></li>--%>
        <%--<li role="presentation"><a href="<%=request.getContextPath()%>/api/checkstyle/stats">Statistics</a></li>--%>
    <%--</ul>--%>
    <div class="col-md-offset-1 col-sm-offset-1" id="content" data-groupId="${groupId}">
        <%--<div class="col-md-1 col-sm-1"></div>--%>
        <div id="line_charts" class="panel panel-default panel-no-boder col-md-4 col-sm-4" style="display: inline-block" >

        </div>
        <div class="col-md-1 col-sm-1"></div>
        <div id="detail" class="panel panel-success panel-no-padding col-md-5 col-sm-5">
            <div class="panel-heading">
                <h4> 开源工具 检查详细结果</h4>
            </div>
            <div class="panel-body">
                <ul class="nav nav-tabs" role="tablist" id="detail_tab">
                    <li role="presentation" id="li_checkstyle"><a href="#checkstyle" aria-controls="checkstyle" role="tab" data-toggle="tab">CheckStyle</a></li>
                    <li role="presentation" id="li_pmd"><a href="#pmd" aria-controls="pmd" role="tab" data-toggle="tab">PMD</a></li>
                    <li role="presentation" id="li_sq"><a href="#sq" aria-controls="sq" role="tab" data-toggle="tab">SonarQube</a></li>
                </ul>
                <div class="tab-content" id="detail_content">
                    <div role="tabpanel" class="tab-pane" id="checkstyle"><br /></div>
                    <div role="tabpanel" class="tab-pane" id="pmd"><br /></div>
                    <div role="tabpanel" class="tab-pane" id="sq"><br /></div>
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
        var panel_width = $(window).width() * 13/32;
        var panel_height = ($(window).height() - parseInt($('body').css('padding-top')) - $("header").height());

        $.ajax({
            url: basePath+"/api/score/api/"+groupId,
            method: "GET",
        }).done(
            function (data) {
            var grades = []
            try {
                grades = JSON.parse(data);
            } catch(err){
                console.log(err.message);
            }
            console.log(grades);
            var test = [3,14,1]
            var min = Math.min.apply(Math, test)
            var max = Math.max.apply(Math,test)
            console.log(min,max)

            $("#line_charts").attr("style", "width:" + panel_width + "px;height:" + panel_height + "px;");
            var line_charts = echarts.init(document.getElementById("line_charts"));
            drawLineCharts(line_charts, grades);
//            line_chrts.on('click', function (params) {
//                if (params.componentType === 'markPoint' || params.componentType === 'series') {
//                    console.log(params);
//                    // 点击到了 markPoint 上
//                    var date = params.name;
//                    var type = params.seriesName;
//                    if(type==='WARN'){
//                        $('#detail_tab a[href="#warn"]').tab('show');
//                        $('#collapse_'+date).collapse('show');
//                    }else if(type==='ERROR'){
//                        $('#detail_tab a[href="#error"]').tab('show');
//                    }
//                }
//            });
        });

        $.ajax({
            url: basePath+"/api/checkstyle/api/checkstyleResult"+groupId,
            method: "GET",
        }).done(
            function (data) {
                try{
                    data = JSON.parse(data);
                    drawDetailPanel('checkstyle', $("#checkstyle"), data);
                }catch (err){
                    console.log(err.message);
                }
            }
        );

        $.ajax({
            url: basePath+"/api/pmd/api/checkstyleResult"+groupId,
            method: "GET",
        }).done(
            function (data) {
                try{
                    data = JSON.parse(data);
                    drawDetailPanel('pmd', $("#pmd"), data);
                }catch (err){
                    console.log(err.message);
                }
            }
        );

        $.ajax({
            url: basePath+"/api/snoarqube/api/checkstyleResult"+groupId,
            method: "GET",
        }).done(
            function (data) {
                try{
                    data = JSON.parse(data);
                    drawDetailPanel('snoarqube', $("#sq"), data);
                }catch (err){
                    console.log(err.message);
                }
            }
        );

        $('#detail_tab a').click(function (e) {
            e.preventDefault()
            $(this).tab('show')
        });

        function drawLineCharts(obj, data) {
            var line_name = ['Teacher', 'CheckStyle', '\n', 'PMD', 'SonarQube'];
            var minScores = [];
            minScores.push(Math.min.apply(Math, data.teacherScore));
            minScores.push(Math.min.apply(Math, data.checkstyleScore));
            minScores.push(Math.min.apply(Math, data.pmdScore));
            minScores.push(Math.min.apply(Math, data.sqScore));
            var minScore = Math.min.apply(Math, minScores);
            var option = {
                title: {
                    text: '第'+data.groupId+'小组分数变化图',
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
                        dataView: {readOnly: false},
                        magicType: {type: ['line', 'bar']},
                        restore: {},
                        saveAsImage: {}
                    }
                },
                xAxis:  {
                    type: 'category',
                    boundaryGap: false,
                    data: data.checkDate,
                    axisLine:{
                        lineStyle: {
                            width: 2
                        }
                    }
                },
                yAxis:[ {
                    type: 'value',
                    axisLabel: {
                        formatter: '{value}'
                    },
                    min: minScore,
                    axisLine:{
                        lineStyle: {
                            width: 2
                        }
                    }
                } ],
                series: [
                    {
                        name: line_name[0],
                        type: 'line',
                        data: data.teacherScore,
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
                        data: data.checkstyleScore,
                        markPoint: {
                            data: [
//                                {type: 'max', name: '最大值'},
//                                {type: 'min', name: '最小值'}
                            ]
                        },
                        markLine: {
                            data: [
                                {type: 'average', name: '平均值'},
                            ]
                        }
                    },
                    {
                        name: line_name[3],
                        type: 'line',
                        data: data.pmdScore,
                        markPoint: {
                            data: [
//                                {type: 'max', name: '最大值'},
//                                {type: 'min', name: '最小值'}
                            ]
                        },
                        markLine: {
                            data: [
                                {type: 'average', name: '平均值'},
                            ]
                        }
                    },{
                        name: line_name[4],
                        type: 'line',
                        data: data.sqScore,
//                        yAxisIndex: 1,
                        markPoint: {
                            data: [
//                                {type: 'max', name: '最大值'},
//                                {type: 'min', name: '最小值'}
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

        function drawDetailPanel(panel_id, father_obj, data) {
            var html = [];
            html.push('<div class="panel-group" id="warn_detail" role="tablist" aria-multiselectable="true">');
            $.each(detail_warn, function (key, value) {
                html_warn.push('<div class="panel panel-default">');
                html_warn.push('<div class="panel-heading" role="tab" id="heading_'+key+'">');
                html_warn.push('<h4 class="panel-title"> <a role="button" data-toggle="collapse" data-parent="#warn_detail" href="#collapse_'+key+'" aria-expanded="true" aria-controls="collapse_'+key+'">');
                html_warn.push(key+'  第'+ ($.inArray(key, detail_timeline)+1) + '次检查');
                html_warn.push('</a></h4></div>');
                // 判断 要展开哪次 检查详情
                <%--if($.inArray(key, detail_timeline)== ${check}){--%>
                    <%--html_warn.push('<div id="collapse_'+key+'" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_'+key+'">');--%>
                <%--}else{--%>
                    <%--html_warn.push('<div id="collapse_'+key+'" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading_'+key+'">');--%>
                <%--}--%>
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
