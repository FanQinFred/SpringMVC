<html>
<head>
    <title>View</title>
</head>
<body>
<h1>hello world</h1>
<%
    String view2= (String)request.getAttribute("view2");
    String view= (String)request.getAttribute("view");
%>
This is view.jsp
<label style="color: red;">${view}</label>
<%=view%>

<label style="color: red;">${view2}</label>
<%=view2%>
</body>
</html>
