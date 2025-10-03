<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>register</title>
</head>
<body>
    <form method="post" id="register-form">
        <label>
            <input type="text" name="name" placeholder="Имя" required>
        </label>
        <label>
            <input type="email" name="email" placeholder="Email" required>
        </label>
        <label>
            <input type="text" name="phone" placeholder="Телефон" required>
        </label>
        <label>
            <input type="password" name="password" placeholder="Пароль" required>
        </label>
        <label>
            <input type="submit" value="Зарегистрироваться">
        </label>
    </form>
</body>
</html>
