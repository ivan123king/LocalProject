<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑博客</title>
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap.css"/>
    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/blog/fileinput.css"/>

    <script src="<%=request.getContextPath() %>/js/jquery-3.2.1.js"></script>
    <script src="<%=request.getContextPath() %>/js/bootstrap.js"></script>
    <script src="<%=request.getContextPath() %>/js/blog/fileinput.js"></script>
    <script src="<%=request.getContextPath() %>/js/blog/zh.js"></script>
	<script src="<%=request.getContextPath() %>/ckeditor/ckeditor.js"></script>
</head>
<body>
		<input type="radio" name="blog" value="upload" onclick="chooseWay(this)" checked/>上传Word文档<br/>
		<input type="radio" name="blog" value="write" onclick="chooseWay(this)"/>书写博客
		<!-- 导入Docx变成博客HTML代码 -->
		<div id="uploadWordDiv">
		    <form action="<%=request.getContextPath()+"/blog/upload/uploadword.action"%>" method="post" enctype="multipart/form-data">
		        <input id="file_input" type="file" name="file" data-show-preview="false"/>
		    </form>
		</div>
		
		<div id="writeblogDiv">
			<!-- 书写博客HTML代码 -->
			<form id="blog_form" action="<%=request.getContextPath() %>/blog/writeblog.action" method="post">
	        	<textarea id="blog" name="content">
	            </textarea>
	            <button onclick="commitBlog();">提交</button>
	        </form>
		</div>
		
			
            
</body>
<script>
	//上传文件空间配置处
	$("#file_input").fileinput({
	    uploadUrl: "<%=request.getContextPath()%>/blog/upload/uploadword.action", // server upload action
	    uploadAsync: false,
	    showBrowse: true,
	    language:'zh',
	    browseOnZoneClick: false
	});

	//ckeditor富文本编辑配置处
	var editor = null;
	window.onload = function(){
	    editor = CKEDITOR.replace('content',{height:400});
	}
	
	function commitBlog(){
		var blogForm = document.getElementById("blog_form");
		blogForm.submit();
	}
	
	function chooseWay(obj){
		var way = obj.value;
		var uploadDiv = $("#uploadWordDiv");
		var writeDiv = $("#writeblogDiv");
		if(way=="upload"){
			uploadDiv.show();
			writeDiv.hide();
		}else{
			uploadDiv.hide();
			writeDiv.show();
		}
	}
	
	$(function(){
		var uploadDiv = $("#uploadWordDiv");
		var writeDiv = $("#writeblogDiv");
		uploadDiv.show();
		writeDiv.hide();
	});
	
</script>
</html>