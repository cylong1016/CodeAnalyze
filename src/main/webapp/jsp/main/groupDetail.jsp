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
    <div class="col-md-1 col-sm-1">
        <button class="btn btn-default" onclick="history.go(-1);">返回</button>
    </div>
    <div class="col-md-offset-1 col-sm-offset-1" id="content" data-groupId="${groupId}">
        <%--<div class="col-md-1 col-sm-1"></div>--%>
        <div id="line_charts" class="panel panel-default panel-no-boder col-md-4 col-sm-4" style="display: inline-block" >

        </div>
        <div class="col-md-1 col-sm-1"></div>
        <div id="detail" class="panel panel-default panel-no-padding col-md-5 col-sm-5">
            <div class="panel-heading">
                <h4> 开源工具 检查详细结果</h4>
            </div>
            <div class="panel-body">
                <ul class="nav nav-tabs" role="tablist" id="detail_tab">
                    <li role="presentation" id="li_checkstyle" class="active"><a href="#checkstyle_panel" aria-controls="checkstyle_panel" role="tab" data-toggle="tab">CheckStyle</a></li>
                    <li role="presentation" id="li_pmd"><a href="#pmd_panel" aria-controls="pmd_panel" aria-controls="pmd_panel" role="tab" data-toggle="tab">PMD</a></li>
                    <!-- <li role="presentation" id="li_sq"><a href="#sq_panel" aria-controls="sq_panel" aria-controls="sq_panel" role="tab" data-toggle="tab">SonarQube</a></li> -->
                </ul>
                <div class="tab-content" id="detail_content">
                    <div role="tabpanel" class="tab-pane active" id="checkstyle_panel"><br />
                        <div class="panel-group" id="checkstyle" role="tablist" ></div>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="pmd_panel"><br />
                        <div class="panel-group" id="pmd" role="tablist"></div>
                    </div>
                    <!-- <div role="tabpanel" class="tab-pane" id="sq_panel"><br />
                        <div class="panel-group" id="sq" role="tablist"></div>
                    </div> -->
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
            var grades = [];
            try {
                grades = JSON.parse(data);
            } catch(err){
                console.log(err.message);
            }
            console.log(grades);

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
            url: basePath+"/api/checkstyle/api/result/"+groupId,
            method: "GET",
        }).done(
            function (data) {
                try{
                    data = JSON.parse(data);
                    data.sort(function (a, b) {
                        var keyA = new Date(a.checkDate),
                            keyB = new Date(b.checkDate);
                        if( keyA < keyB ) return -1;
                        if( keyA > keyB ) return 1;
                        return 0
                    });
                    console.log(data);
                    drawDetailPanel('checkstyle', $("#checkstyle"), data);
                }catch (err){
                    console.log(err.message);
                }
            }
        );

        $.ajax({
            url: basePath+"/api/pmd/api/result/"+groupId,
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
//
//        $.ajax({
//            url: basePath+"/api/snoarqube/api/result"+groupId,
//            method: "GET",
//        }).done(
//            function (data) {
//                try{
//                    data = JSON.parse(data);
//                    drawDetailPanel('snoarqube', $("#sq"), data);
//                }catch (err){
//                    console.log(err.message);
//                }
//            }
//        );

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
                    data: data.checkDate.reverse(),
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
                        data: data.teacherScore.reverse(),
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
                        data: data.checkstyleScore.reverse(),
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
                        data: data.pmdScore.reverse(),
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
                ]
            };
            obj.setOption(option);
        }

        function drawDetailPanel(panel_id, father_obj, data) {
//            panel_id = panel_id + '_list';
            var html = [];
            $.each(data, function (index, value) {
                html.push('<div class="panel panel-default">');
                html.push('<div class="panel-heading" role="tab" id="heading_'+index+'">');
                html.push('<h4 class="panel-title"> <a role="button" data-toggle="collapse" data-parent="#'+panel_id+'" href="#collapse_'+index+'" aria-expanded="true" aria-controls="collapse_'+index+'">');
                html.push( value.checkDate +'  第'+ (index+1) + '次检查');
                html.push('</a></h4></div>');
                // 判断 要展开哪次 检查详情
                if(index == 0 ){
                    html.push('<div id="collapse_'+index+'" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_'+index+'">');
                }else{
                    html.push('<div id="collapse_'+index+'" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading_'+index+'">');
                }
                html.push('<div class="panel-body">');
                html.push('<table id="'+index+'" class="table table-striped table-bordered datatable">');
                html.push('<thead><tr><td>Error类型</td><td>数量</td></tr></thead>');
                html.push('<tbody>');
                $.each(value.results, function (index2, value2) {
                    html.push('<tr>');
                    html.push('<td>' + value2.name + '</td>');
                    html.push('<td>' + value2.count + '</td>');
                    html.push('</tr>')
                });
                html.push('</tbody>');
                html.push('</table>');
                html.push('</div>');
                html.push('</div>');
                html.push('</div>');
            });
            father_obj.append(html.join(''));
            // 设置一次只显示一个
            father_obj.on('show.bs.collapse','.collapse', function() {
                father_obj.find('.collapse.in').collapse('hide');
            });
            $.each($('.datatable'), function (index, obj) {
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
