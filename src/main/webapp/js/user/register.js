function togglePassword(id, btn) {
    const input = document.getElementById(id);
    if (input.type === "password") {
        input.type = "text";
        btn.textContent = "👁";
    } else {
        input.type = "password";
        btn.textContent = "🙈";
    }
}

document.getElementById("register-form").addEventListener("submit", function (event) {
    const pass = document.getElementById("password").value;
    const confirm = document.getElementById("confirmPassword").value;

    if (pass !== confirm) {
        event.preventDefault();
        alert("Пароли не совпадают!");
    }
});