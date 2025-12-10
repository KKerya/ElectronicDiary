document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('makeTeacherForm');
    const resultMessage = document.getElementById('resultMessage');

    form.addEventListener('submit', (e) => {
        e.preventDefault();

        const login = document.getElementById('login').value.trim();
        const subjectsSelect = document.getElementById('subjects');
        const selectedSubjects = Array.from(subjectsSelect.selectedOptions).map(opt => parseInt(opt.value));

        if (!login || selectedSubjects.length === 0) {
            resultMessage.textContent = "Введите логин и выберите хотя бы один предмет.";
            resultMessage.style.color = "red";
            return;
        }

        fetch('/api/admin/makeTeacher', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                login: login,
                subjectIds: selectedSubjects
            })
        })
        .then(response => response.text())
        .then(msg => {
            resultMessage.textContent = msg;
            resultMessage.style.color = "green";
            form.reset();
        })
        .catch(err => {
            console.error(err);
            resultMessage.textContent = "Произошла ошибка при назначении учителя.";
            resultMessage.style.color = "red";
        });
    });
});
