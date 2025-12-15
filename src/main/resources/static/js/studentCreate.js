document.addEventListener('DOMContentLoaded', () => {
    const loginInput = document.getElementById('login');
    const groupSelect = document.getElementById('group');
    const createBtn = document.getElementById('createBtn');
    const messageDiv = document.getElementById('message');

    createBtn.addEventListener('click', () => {
        const login = loginInput.value;
        const groupId = groupSelect.value;

        if(!login || !groupId) {
            messageDiv.textContent = "Заполните все поля!";
            messageDiv.style.color = "red";
            return;
        }

        const formData = new URLSearchParams();
        formData.append("login", login);
        formData.append("groupId", groupId);

        fetch("/api/student/create", {
            method: "POST",
            body: formData
        })
        .then(res => res.text())
        .then(msg => {
            messageDiv.textContent = msg;
            messageDiv.style.color = "green";
            loginInput.value = "";
            groupSelect.value = "";
        })
        .catch(err => {
            console.error(err);
            messageDiv.textContent = "Ошибка при создании студента";
            messageDiv.style.color = "red";
        });
    });
});
