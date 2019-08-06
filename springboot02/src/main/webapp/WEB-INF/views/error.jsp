<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>异常页面</title>
<%
	String errorInfo = (String)request.getAttribute("reason");
	String url = (String)request.getAttribute("url"); 
%>
</head>
<body>
	错误URL： <%=url %>
	<br/>
	原因：<%=errorInfo %>
</body>
</html>