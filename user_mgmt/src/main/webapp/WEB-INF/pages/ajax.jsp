<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Spring 4 MVC - Ajax</title>
</head>
<body>

<button>load all products by ajax</button>

<script type="text/javascript" src="<c:url value='/js/zepto.js'/>"></script>
<script type="text/javascript">
    $(function () {
        $('button').click(function () {
            $.get('/SpringMVC/ajax/products', function (data) {
                $.each(data, function (index, item) {
                    $('body').append("<p>" + item.description + "&nbsp;&nbsp;" + item.price + "</p>");
                });
            });
        });
    });
</script>
</body>
</html>