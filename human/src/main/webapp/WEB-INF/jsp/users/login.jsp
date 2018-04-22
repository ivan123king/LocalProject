<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
</head>
<body>
	<form action="<%=request.getContextPath()%>/users/login.action" method="post">
		<label>用户名：</label><input type="text" name="name" placeholder="登录的用户名"/><br/>
		<label>密码：</label><input type="password" name="password" placeholder="输入密码"/><br/>
		<input type="submit" value="登录"/>
		<a href="<%=request.getContextPath()%>/navigation/register.action">注册</a>
	</form>
</body>
</html>