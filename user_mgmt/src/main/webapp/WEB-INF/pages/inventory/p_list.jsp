<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Product List</title>
</head>
<body>
<h2>Product List</h2>
<ul>
    <c:forEach items="${products }" var="product">
        <li>Description: ${product.description } &nbsp;&nbsp; Price: ${product.price }</li>
    </c:forEach>
</ul>

<a href="${pageContext.request.contextPath }/inventory/p_view">Add Product</a>
</body>
</html>