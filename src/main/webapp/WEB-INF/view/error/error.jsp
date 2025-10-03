<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ошибка ${errorCode}</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/error/error.css">
</head>
<body>
<div class="error-container">
    <h1>Ошибка ${errorCode}</h1>
    <p class="message">${errorMessage}</p>
    <a href="<%= request.getContextPath() %>">На главную</a>
</div>
</body>
</html>

