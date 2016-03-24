<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Upload Multiple File</title>
</head>
<body>

<form method="POST" action="uploadMultipleFile" enctype="multipart/form-data">
    File1 to upload: <input type="file" name="file"><br/>
    Name1: <input type="text" name="name"><br/> <br/>
    File2 to upload: <input type="file" name="file"><br/>
    Name2: <input type="text" name="name"><br/> <br/>
    <input type="submit" value="Upload"> Press here to upload the file!
</form>

</body>
</html>