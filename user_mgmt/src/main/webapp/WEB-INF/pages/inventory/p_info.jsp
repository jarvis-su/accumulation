<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Add Product</title>
</head>
<body>
<h3>Product Information</h3>
<form:form modelAttribute="product" action="${pageContext.request.contextPath }/inventory/p_add">
    description: <form:input type="text" path="description" value="${product.description }"/><form:errors
        path="description"/><br/><br/>
    price: <form:input type="text" path="price" value="${product.price }"/><form:errors path="price"/><br/>
    <input type="submit"/>
</form:form>
</body>
</html>