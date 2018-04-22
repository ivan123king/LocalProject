<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>博客</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/blog/base.css"/>
<%
	String html = request.getAttribute("html").toString();
%>
</head>
<body>
	<div contenteditable="true">
		<%=html %>
	</div>
</body>
</html>