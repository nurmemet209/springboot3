<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 1/22/2017
  Time: 5:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" enctype="multipart/form-data" action="/handleRegister">


    <div><label>用户名：</label><input name="username"></div>

    <div><label>密码：</label><input name="password"></div>

    <div> <label>工程文件1：</label><input type="file" name="materyal"></div>
    <div> <label>工程文件2：</label><input type="file" name="materyal"></div>
    <div> <label>工程文件3：</label><input type="file" name="materyal"></div>

    <div> <input type="submit" value="提交"></div>
</form>
</body>
</html>
