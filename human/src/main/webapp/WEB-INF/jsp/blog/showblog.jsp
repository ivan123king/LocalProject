<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>博客展示</title>
<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath() %>/css/blog/Styles/SyntaxHighlighter.css" />
<script class="javascript"
	src="<%=request.getContextPath() %>/js/blog/Scripts/jquery.js"></script>
<script class="javascript"
	src="<%=request.getContextPath() %>/js/blog/Scripts/shCore.js"></script>
<script>
/* $(document)
.ready(
		function() {}); */
		
		$(function(){
			 $("#blogContent pre")
				.each(
					function() {
						var $this = $(this);
						var attr = $this.children("code")
								.attr("class");
						attr = attr.split("-")[1];//此时这个就是哪种代码比如java,python,perl,javascript等
						//这个是代码内容
						var content = $this
								.children("code").text();
						var $textarea = " <textarea cols=\"50\" rows=\"15\" name=\"code\" class="+attr+" >"
								+ content + "</textarea>	";

						$this.replaceWith($textarea);
					});

				$(".article_content pre")
				.each(
					function() {
						var $this = $(this);
						if ($this.attr("class").indexOf(
								"brush:") != -1) {
							var lang = $this.attr("class")
									.split(';')[0]
									.split(':')[1];
							$this.attr('name', 'code');
							$this.attr('class', lang);
						}
					});
				dp.SyntaxHighlighter.HighlightAll('code');
		});

</script>
</head>
<body>
	<label id="blogId" style="display:none;"><%=request.getAttribute("blogId") %></label>
	<h2 id="blogTitle">
		<%=request.getAttribute("blogTitle") %>
	</h2>
	<div id="blogContent" class="article_content"
		style="width: 99%; text-align: left">
		<!-- 下面这个是CSDN的代码那个高亮显示的东西 -->
		<%=request.getAttribute("blogContent") %>
	</div>
</body>
<script>
	function updateBlog(){
		var blogId = $("#blogId").text();
		var blogTitle = $("#blogTitle").text();
		var blogContent = $("blogContent").text();
		$.ajax({  
	         type : "post",  
	          url : "<%=request.getContextPath() %>/blog/updateblog",  
	          data : {"blogId":blogId,"blogTitle":blogTitle,"blogContent":blogContent},
	          async : true,  
	          success : function(data){  
	          }  
	     }); 
	}
</script>
</html>