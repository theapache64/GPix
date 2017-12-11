<%--
  Created by IntelliJ IDEA.
  User: theapache64
  Date: 11/12/17
  Time: 2:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%=request.getParameter("title")%>
    </title>
    <%@include file="common_headers.jsp" %>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-6">
            <h3><%=request.getParameter("title")%>
            </h3>
            <p><%=request.getParameter("message")%>
            </p>
        </div>
    </div>
</div>
</body>
</html>
