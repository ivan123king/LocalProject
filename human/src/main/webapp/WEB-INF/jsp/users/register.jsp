<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>注册页面</title>

</head>
<body>
	
	
	<div class="main">
	 	<div class="social-icons">
	 		注册
		</div>
		 <form action="<%=request.getContextPath()%>/users/register.action" method="post">
			<div class="lable">
                   	姓名：<input type="text" name="customerName" class="text" value="" placeholder="姓名"><br/>
                   	密码：<input type="password" name="password" class="text" value="" placeholder="密码"><br/>
                   	二次确认密码：<input type="password" class="text" value="" placeholder="确认密码"><br/>
                   	身份证号：<input type="text" name="ID" class="text" value="" placeholder="身份证号"><br/>
                   	地址：<input type="text" name="address" class="text" value="" placeholder="地址"><br/>
                   	出生年月：<input type="date" name="birthday" class="text" value="" placeholder="出生日期"><br/>
                   	年龄：<input type="number" name="age" class="text" value="" placeholder="年龄"><br/>
            </div>
            <div>
            	<input type="submit" value="提交"/>
            	<input type="reset" value="重置"/>
            </div>
                    
		</form>
	</div>
	 
</body>
</html>