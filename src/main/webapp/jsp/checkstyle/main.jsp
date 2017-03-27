<%--
  Created by IntelliJ IDEA.
  User: floyd
  Date: 2017/3/22
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../../css/bootstrap.css" type="text/css">
    <link rel="stylesheet" href="../../css/checkstyle.css" type="text/css"/>
</head>
<body>
<div>
    <ul class="nav nav-pills nav-stacked left-chart-nav col-md-2 col-sm-2" id="tab" role="tablist">
        <li role="presentation" class="active"><a href="#total" aria-controls="total" role="tab"
                                                  data-toggle="tab">Total</a></li>
        <li role="presentation"><a href="#group" aria-controls="group" role="tab" data-toggle="tab">Group</a></li>
        <li role="presentation"><a href="#timeline" aria-controls="timeline" role="tab" data-toggle="tab">TimeLine</a>
        </li>
    </ul>
    <div class="tab-content col-md-offset-2 col-sm-offset-2">
        <div role="tabpanel" class="tab-pane active" id="total">
            <h1>Total</h1>
        </div>
        <div role="tabpanel" class="tab-pane" id="group">
            <h1>Group</h1>
            <%--<div id="group_1" style="width: 600px;height:400px;"></div>--%>
        </div>
        <div role="tabpanel" class="tab-pane" id="timeline"><h1>TimeLine</h1></div>

    </div>
    </div>
</body>

<script type="text/javascript" src="../../js/jquery-3.2.0.min.js"></script>
<script type="text/javascript" src="../../js/bootstrap.min.js"></script>
<script type="text/javascript" src="../../js/echarts.common.min.js"></script>
<script type="text/javascript">
    $(function () {

        var basePath = "<%=request.getContextPath()%>";

        $("#tab a").click(function (e) {
            e.preventDefault();
            $(this).tab('show')
        });

        drawGroup();

        function drawGroup() {
            var count = 0;
            var count_row = 3;
            var row_num = 0;
            var width = ($(document.body).width() - $('#tab').width()) / 4;
            $("#group").append('<div id="row_' + row_num + '">');
            for (var i = 0; i < 20; i++) {
                if (count == count_row) {
                    row_num++;
                    $("#group").append('<div id="row_' + row_num + '">');
                    count = 0;
                }
                var html = '<div id="group_' + i + '" style="width:' + width + 'px;height:' + width + 'px;display: inline-block"></div>';
                $("#row_" + row_num).append(html);
                count++;
                var group_chart = echarts.init(document.getElementById('group_' + i));
                group_chart.setOption({
                    title: {
                        text: "第" + i + "组"
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
                            ]
//                        roseType: 'angle'
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

</html>
