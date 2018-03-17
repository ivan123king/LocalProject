<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>在customer中封装User信息</title>
</head>
<body>
	<form method="post" action="<%=request.getContextPath()%>/findUser.action">
		<input type="text" name="customerId" value="1024"/>
		<input type="text" name="user.userName" value="liuwei"/>
		<input type="submit" value="提交"/>
	</form>
</body>
</html>