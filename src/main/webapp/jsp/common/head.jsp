<%--
  Created by cylong
  head 通用代码
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()
	+ "://"+request.getServerName()
	+ ":" + request.getServerPort()
	+ path
	+ "/";
%>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="<%=path%>/css/bootstrap.css" rel="stylesheet">
