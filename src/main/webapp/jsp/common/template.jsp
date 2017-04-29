<%--
  Created by IntelliJ IDEA.
  User: floyd
  Date: 2017/3/22
  Time: 15:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.css" type="text/css">
    <title>CodeAnalyze</title>
    <style>
        body {padding-top: 60px;}
    </style>
</head>
<body>
    <jsp:include page="header.jsp"></jsp:include>
    <jsp:include page="${partial}"></jsp:include>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
