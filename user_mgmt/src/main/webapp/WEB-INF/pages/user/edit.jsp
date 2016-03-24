<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>User Mgmt</title>
</head>

<body>
<form method="post">
    <div>
        <label for="userName">userName:</label>
        <input name="userName" value="${user.userName }">
        <span>${br.getFieldError("userName").getDefaultMessage()}</span>
    </div>

    <div>
        <label for="email">Email:</label>
        <input name="email" value="${user.email}">
        <span>${br.getFieldError("email").getDefaultMessage()}</span>
    </div>

    <div>
        <label for="age">age:</label>
        <input name="age" value="${user.age}">
        <span>${br.getFieldError("age").getDefaultMessage()}</span>
    </div>
    <button type="submit">Submit</button>
</form>

</body>
</html>