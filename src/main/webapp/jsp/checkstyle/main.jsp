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
        <li role="presentation" class="active"><a href="<%=request.getContextPath()%>/api/checkstyle">Total</a></li>
        <li role="presentation"><a href="<%=request.getContextPath()%>/api/checkstyle/group">Group</a></li>
        <li role="presentation"><a href="<%=request.getContextPath()%>/api/checkstyle/check">Check</a>
        </li>
    </ul>
    <div class="col-md-offset-2 col-sm-offset-2" id="content">
        <h1>Total</h1>
    </div>
</div>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.2.0.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/echarts.common.min.js"></script>
<script type="text/javascript">
    $(function () {
        var basePath = "<%=request.getContextPath()%>";

//        $("#tab a").click(function (e) {
//            e.preventDefault();
//            $(this).tab('show')
//        });

//    $.ajax({
//        url: basePath + '/api/checkstyle/'
//    })
    })
</script>
