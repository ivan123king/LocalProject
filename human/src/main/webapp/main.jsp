<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
</head>
<body>
	<a href="<%=request.getContextPath()%>/navigation/login">登录</a><br/>
	<a href="<%=request.getContextPath()%>/navigation/register">注册</a><br/>
	<a href="<%=request.getContextPath()%>/navigation/writeblog">写博客</a><br/>
	<a href="<%=request.getContextPath()%>/navigation/bloglist">博客列表</a><br/>
	<a href="<%=request.getContextPath()%>/navigation/updateblog">修改博客</a><br/>
	<a></a>
</body>
</html>