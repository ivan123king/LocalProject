<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>更改博客</title>
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap.css"/>

    <script src="<%=request.getContextPath() %>/js/jquery-3.2.1.js"></script>
    <script src="<%=request.getContextPath() %>/js/bootstrap.js"></script>
	<script src="<%=request.getContextPath() %>/ckeditor/ckeditor.js"></script>
<%
	String blogContent = (String)request.getAttribute("blogContent");
	String title = (String)request.getAttribute("blogTitle");
	int blogId = (Integer)request.getAttribute("blogId");
%>
</head>
<body>
	<div id="writeblogDiv">
			<!-- 书写博客HTML代码 -->
			<form id="blog_form" action="<%=request.getContextPath() %>/blog/updateblog.action" method="post">
	        	<input style="display:none;" name="blogId" value="<%=blogId %>"/>
	        	<span>标题：</span><input type="text" name="title" value="<%=title%>"/>
	        	<br/>
	        	<textarea id="blog" name="content">
	        		<%=blogContent %>
	            </textarea>
	            <button onclick="commitBlog();">提交</button>
	        </form>
		</div>
</body>
<script>
	//ckeditor富文本编辑配置处
	var editor = null;
	window.onload = function(){
	    editor = CKEDITOR.replace('content',{height:400});
	}
	
	function commitBlog(){
		var blogForm = document.getElementById("blog_form");
		blogForm.submit();
	}
</script>
</html>