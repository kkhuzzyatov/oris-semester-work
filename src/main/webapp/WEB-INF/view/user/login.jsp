<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Вход</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/login.css">
</head>
<body>
    <form class="login-form" id="login-form" method="post" action="${pageContext.request.contextPath}/user?action=login">
        <h2>Вход</h2>

        <label for="email">Email</label>
        <input type="email" id="email" name="email" placeholder="Введите email" required>

        <label for="password">Пароль</label>
        <div class="password-container">
            <input type="password" id="password" name="password" placeholder="Введите пароль" required>
            <button type="button" class="toggle-password" onclick="togglePassword()">👁</button>
        </div>

        <button type="submit" class="submit-btn">Войти</button>

        <div class="extra-links">
            <a href="${pageContext.request.contextPath}/user?action=register">Регистрация</a>
        </div>
    </form>

    <script src="${pageContext.request.contextPath}/js/user/login.js"></script>
</body>
</html>
