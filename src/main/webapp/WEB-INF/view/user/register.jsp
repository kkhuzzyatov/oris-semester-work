<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Регистрация</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/register.css">
</head>
<body>
    <form class="registration-form" id="register-form" method="post" action="${pageContext.request.contextPath}/user?action=register">
        <h2>Регистрация</h2>

        <label for="name">Имя</label>
        <input type="text" id="name" name="name" placeholder="Введите имя" required>

        <label for="email">Email</label>
        <input type="email" id="email" name="email" placeholder="Введите email" required>

        <label for="phone">Телефон</label>
        <input type="tel" id="phone" name="phone" placeholder="Введите телефон" required>

        <label for="password">Пароль</label>
        <div class="password-container">
            <input type="password" id="password" name="password" placeholder="Введите пароль" required>
            <button type="button" class="toggle-password" onclick="togglePassword('password', this)">🙈</button>
        </div>

        <label for="confirmPassword">Подтверждение пароля</label>
        <div class="password-container">
            <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Повторите пароль" required>
            <button type="button" class="toggle-password" onclick="togglePassword('confirmPassword', this)">🙈</button>
        </div>

        <button type="submit" class="submit-btn">Зарегистрироваться</button>
    </form>

    <script src="${pageContext.request.contextPath}/js/user/register.js"></script>
</body>
</html>