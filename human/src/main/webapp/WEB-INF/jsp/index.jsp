<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<%
	String userId = session.getAttribute("userId").toString();
	String userName = session.getAttribute("username").toString();
%>
</head>
<body>
	<input type="hidden" value="<%=userId%>"/>
	欢迎用户<%=userName %>
	<br/>
	
	<a href="<%=request.getContextPath()%>/navigation/writeblog" target="_blank">写博客</a><br/>
	<a href="<%=request.getContextPath()%>/navigation/bloglist" target="_blank">博客列表</a><br/>
		
</body>
</html>