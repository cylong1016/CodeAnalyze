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
        <li role="presentation" class="active"><a href="<%=request.getContextPath()%>/api/checkstyle/group">Group</a>
        </li>
        <li role="presentation"><a href="<%=request.getContextPath()%>/api/checkstyle/timeline">TimeLine</a></li>
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

//            drawGroup(querys);
        })

        function drawGroup(querys) {
            var count = 0;
            var count_row = 3;
            var row_num = 0;
            var width = ($(document.body).width() - $('#tab').width()) / 3.5;
            $("#content").append('<div id="row_' + row_num + '">');
            for (var i = 0; i < 20; i++) {
                if (count == count_row) {
                    row_num++;
                    $("#content").append('<div id="row_' + row_num + '">');
                    count = 0;
                }
                var html = '<div id="group_' + i + '" style="width:' + width + 'px;height:' + width + 'px;display: inline-block"></div>';
                $("#row_" + row_num).append(html);
                count++;
                var group_chart = echarts.init(document.getElementById('group_' + i));
                group_chart.setOption({
                    title: {
                        text: "第" + i + "组",
                        link: basePath + '/api/checkstyle/group/' + i,
                        target: 'self'
                    },
                    series: [
                        {
                            name: '访问来源',
                            type: 'pie',
                            radius: '55%',
                            data: [
                                {value: 235, name: '视频广告'},
                                {value: 274, name: '联盟广告'},
                                {value: 310, name: '邮件营销'},
                                {value: 335, name: '直接访问'},
                                {value: 400, name: '搜索引擎'}
                            ],
                            roseType: 'angle'
                        }
                    ]
                });
            }
        }

//    $.ajax({
//        url: basePath + '/api/checkstyle/'
//    })
    })
</script>

