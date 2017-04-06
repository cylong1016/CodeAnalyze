<%--
  Created by IntelliJ IDEA.
  User: floyd
  Date: 2017/3/22
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/checkstyle.css" type="text/css"/>

<div>
    <ul class="nav nav-pills nav-stacked left-chart-nav col-md-2 col-sm-2" id="tab" role="tablist">
        <li role="presentation"><a href="<%=request.getContextPath()%>/api/checkstyle">Total</a></li>
        <li role="presentation" class="active"><a href="<%=request.getContextPath()%>/api/checkstyle/group">Group</a></li>
        <li role="presentation"><a href="<%=request.getContextPath()%>/api/checkstyle/check">Check</a></li>
    </ul>
    <div class="col-md-offset-2 col-sm-offset-2" id="content">

    </div>
</div>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.2.0.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/echarts.common.min.js"></script>
<script type="text/javascript">
    $(function () {

        var basePath = "<%=request.getContextPath()%>";
        $.ajax({
            url: "<%=request.getContextPath()%>/api/checkstyle/api/group",
            method: "GET"
        }).done(function (data) {
            var querys = [];
            try {
                querys = JSON.parse(data);
            } catch (err) {
                console.log(err.message);
            }
            console.log(querys);

            drawGroup(querys);

        })

        function drawGroup(querys) {
            var count_row = 3;
            var row_num = 0;
            var width = ($(window).width() - $('#tab').width()) / 3.5;
            var height = ($(window).height() - parseInt($('body').css('padding-top')) - $('header').height()) / 1.6;
            $("#content").append('<div id="row_' + row_num + '">');
            $.each(querys, function (index,value) {
                if ( index % count_row == 0) {
                    row_num++;
                    $("#content").append('<div id="row_' + row_num + '">');
                }
                var html = '<div id="group_' + index + '" style="width:' + width + 'px;height:' + height + 'px;display: inline-block;margin: 20px;"></div>';
                $("#row_" + row_num).append(html);
                var group_chart = echarts.init(document.getElementById('group_' + index));
                var legend_data = [];
                var series_data = [];
                var check_log = [];
                var temp_index = 1;
                $.each(value.briefInfo, function (key,value) {
                    check_log.push(key);
                    legend_data.push(temp_index+"_Warn");
                    legend_data.push(temp_index+"_Error");
                    series_data.push({value:value[0], name:temp_index+"_Warn"});
                    series_data.push({value:value[1]+300, name:temp_index+"_Error"});
                    temp_index++;
                });
                group_chart.setOption({
                    title: {
                        text: "第" + value.id + "组",
                        link: basePath + '/api/checkstyle/group/' + value.id,
                        target: 'self',
                        x: 'left',
                        textStyle:{
                            fontSize:'22',
                        }
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
//                    legend: {
//                        orient: 'vertical',
//                        left: 'right',
//                        data: legend_data,
//                        formatter: function (name) {
//                            var attrs = name.split('_');
//                            var check_index = parseInt(attrs[0]);
//                            return check_log[check_index-1] + "_第" + check_index + "次_" + attrs[1];
//                        },
//                    },
                    series: [
                        {
                            name: '数量',
                            type: 'pie',
                            radius: '55%',
                            center: ['50%', '50%'],
                            data: series_data,
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
//                            roseType: 'angle'
                        }
                    ]
                });
                group_chart.on('click', function (params) {
                    console.log(params.name);
                })
            });
        }
    })
</script>

