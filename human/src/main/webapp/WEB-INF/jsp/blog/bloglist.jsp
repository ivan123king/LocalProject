<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<%=request.getContextPath()%>/js/jquery-3.2.1.js"></script>
<title>博客列表</title>
<%
	String json = (String) request.getAttribute("blogList");
%>
<style>
	.noneDisplay{
		display:none;
	}
	table{
		width:100%;
	}
	table thead{
		text-align:center;
		color:#321eee;
		font-size:25px;
	}
	table,tr,td{
		border:1px solid black;
		margin:0;
		padding:0;
		text-align:center;
	}
</style>
</head>
<body>
	<h5 id="totalSize"></h5>
	<span style="color:red;">博客列表： </span><br/>
	<table id="blogListTable">
		<thead>
			<tr>
				<td>标题</td>
				<td>作者</td>
				<td>发布时间</td>
				<td colspan="2">操作</td>
			</tr>
		</thead>
		<tbody></tbody>
	</table>
</body>

<script>
	$(function() {
		var blogList = <%=json%> ;
		var tbody = $("#blogListTable tbody");
		$("#totalSize").text("博客总数： "+blogList.totalSize);
		$.each(blogList.content, function(index, blog) {
			var tbodyStr = "<tr id='blog_"+blog.blog_id+"'>"+ 
				"<td>"+blog.title+"</td>"+
				"<td>"+blog.user_name+"</td>"+
				"<td>"+blog.blog_date+"</td>"+
				"<td><buttion onclick=\"updateBlog("+blog.blog_id+")\">更新</button></td>"+
				"<td><buttion onclick='deleteBlog("+blog.blog_id+")'>删除</button></td>"+
				"<td class='noneDisplay'>"+blog.user_id+"</td>"+
				"<td class='noneDisplay'>"+blog.blog_id+"</td>"+
			"</tr>";
			tbody.append(tbodyStr);
		});
	});
	
	$.extend({
	    PostSubmitForm: function (url, args) {
	        var body = $(document.body),
	            form = $("<form method='post' style='display:none'></form>"),
	            input;
	        form.attr({ "action": url });
	        $.each(args, function (key, value) {
	            input = $("<input type='hidden'>");
	            input.attr({ "name": key });
	            input.val(value);
	            form.append(input);
	        });

	        //IE低版本和火狐下
	        form.appendTo(document.body);
	        form.submit();
	        document.body.removeChild(form[0]);
	    }
	});
	
	function updateBlog(blogId){
		var postData = JSON.stringify({"blogId":blogId}); 
		var url = "<%=request.getContextPath() %>/navigation/updateblog";
		$.PostSubmitForm(url,{"blogId":blogId});
	}
	
	function deleteBlog(blogId){
		var postData = JSON.stringify({"blogId":blogId}); 

		$.ajax({  
	         type : "get",  
	          url : "<%=request.getContextPath()%>/blog/deleteBlog/"+blogId,  
	          dataType:"json",
	          contentType:'application/json;charset=UTF-8',
	          async : true,  
	          success : function(data){  
	        	  if(data=="success"){
	        		  alert("delete success blogId:"+blogId);
	        		  $("#blog_"+blogId).hide();
	        	  }else{
	        		  alert("error when del	ete blog,blogId is "+blogId+" contact the admin。");
	        	  }
	          },
	          error:function(data){
	        	  alert("error when delete blog:"+data);
	        	  console.info(data);
	          }
				
	     }); 
	}
	
</script>

</html>