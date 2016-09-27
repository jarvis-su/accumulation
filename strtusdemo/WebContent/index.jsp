<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sd" uri="/struts-dojo-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Basic Struts 2 Application - Welcome</title>
<s:head theme="xhtml" />
<sd:head parseContent="true"/>
</head>
<body>
<h1>Welcome To Struts 2!</h1>
<form action="/strtusdemo/basicstruts2/hello.action" method="post">
<input name="messageStore.message" type="text" /> <input
	name="messageStore.age" type="text" /> 
	<sd:datetimepicker name="messageStore.birthday" label="Format (yyyy-MM-dd)" displayFormat="yyyy-MM-dd"/>
	<input type="submit" value="ok"></form>
</body>
</html>