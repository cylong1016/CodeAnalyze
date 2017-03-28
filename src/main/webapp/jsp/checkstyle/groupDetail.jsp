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

<div>
    <ul class="nav nav-pills nav-stacked left-chart-nav col-md-2 col-sm-2" id="tab">
        <li role="presentation"><a href="<%=request.getContextPath()%>/api/checkstyle">Total</a></li>
        <li role="presentation" class="active"><a href="<%=request.getContextPath()%>/api/checkstyle/group">Group</a>
        </li>
        <li role="presentation"><a href="<%=request.getContextPath()%>/api/checkstyle/timeline">TimeLine</a></li>
    </ul>
    <div class="col-md-offset-2 col-sm-offset-2" id="content">

    </div>
</div>

