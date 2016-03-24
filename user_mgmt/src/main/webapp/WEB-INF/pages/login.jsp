<%--
  Created by IntelliJ IDEA.
  User: Jarvis
  Date: 3/24/16
  Time: 20:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head></head>
<body>
<h1>Struts 2 Hello World Example</h1>

<s:form action="Welcome">
    <s:textfield name="userName" label="Username"/>
    <s:password name="password" label="Password"/>
    <s:submit/>
</s:form>

</body>
</html>
