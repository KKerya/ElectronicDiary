document.getElementById("changePasswordForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const login = document.getElementById("login").value.trim();
    const newPassword = document.getElementById("newPassword").value.trim();

    if (!login || !newPassword) {
        alert("Заполните все поля");
        return;
    }

    fetch('/api/admin/change/password', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            login: login,
            newPassword: newPassword
        })
    })
    .then(response => {
        if (response.ok) {
            document.getElementById("passwordMessage").innerText =
                "Пароль успешно изменён";
        } else {
            document.getElementById("passwordMessage").innerText =
                "Ошибка при смене пароля";
        }
    })
    .catch(err => {
        console.error(err);
        document.getElementById("passwordMessage").innerText =
            "Произошла ошибка";
    });
});
