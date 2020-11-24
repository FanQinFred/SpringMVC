<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>文件上传</title>
</head>
<body>

<form action="http://localhost:8080/disk/upload" method="POST" enctype="multipart/form-data">
        <input type="text" name="name" id="pTitle" value="cnm">
        <input type="file" name="pFile" onChange="if(this.value)insertTitle(this.value);">
<%--    <input name="username" id="User Name"  required autofocus>--%>
<%--        <input type="file" name="file">--%>
        <button type="submit" value="submit">submit</button>
    </form>
    <script language="javascript">
        function insertTitle(tValue){
            var t1 = tValue.lastIndexOf("\\");
            var t2 = tValue.lastIndexOf(".");
            if(t1 >= 0 && t1 < t2 && t1 < tValue.length){
                document.getElementById("pTitle").value = tValue.substring(t1 + 1, t2);
            }
        }
    </script>
</body>
</html>


<%--&lt;%&ndash;--%>
<%--  Created by IntelliJ IDEA.--%>
<%--  User: surface--%>
<%--  Date: 2020/10/22--%>
<%--  Time: 22:16--%>
<%--  To change this template use File | Settings | File Templates.--%>
<%--&ndash;%&gt;--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<html lang="en">--%>

<%--<head>--%>
<%--    <meta charset="utf-8">--%>
<%--    <meta http-equiv="X-UA-Compatible" content="IE=edge">--%>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1">--%>
<%--    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->--%>
<%--    <meta name="description" content="">--%>
<%--    <meta name="author" content="">--%>
<%--    <link rel="icon" href="../../favicon.ico">--%>

<%--    <title>OAuth2.0-SSO Signin</title>--%>

<%--    <!-- Bootstrap core CSS -->--%>
<%--    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">--%>

<%--    <!-- Custom styles for this template -->--%>
<%--    <link href="signin.css" rel="stylesheet">--%>

<%--</head>--%>

<%--<body>--%>

<%--<div class="container">--%>

<%--    <form class="form-signin" method="post" action="http://localhost:8080/user/login">--%>
<%--        <h2 class="form-signin-heading">Please sign in</h2>--%>
<%--        <label for="User Name" class="sr-only">User Name</label>--%>
<%--        <input name="username" id="User Name" class="form-control" placeholder="Email address" required autofocus>--%>
<%--        <label for="inputPassword" class="sr-only">Password</label>--%>
<%--        <input name="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>--%>

<%--        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>--%>
<%--        <p><a class="btn btn-lg btn-success" href="http://localhost:8081/app01_register?LOCAL_SERVICE=http://localhost:8081/app01"--%>
<%--              role="button">Sign up today</a></p>--%>
<%--    </form>--%>

<%--</div> <!-- /container -->--%>

<%--</body>--%>

<%--</html>--%>


