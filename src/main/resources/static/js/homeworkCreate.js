document.addEventListener('DOMContentLoaded', () => {
    const subjectSelect = document.getElementById('subject');
    const groupSelect = document.getElementById('group');
    const lessonSelect = document.getElementById('lesson');
    const form = document.getElementById('homeworkForm');
    const messageDiv = document.getElementById('message');

    function loadLessons() {
        const subjectId = subjectSelect.value;
        const groupId = groupSelect.value;
        if (!subjectId || !groupId) return;

        fetch(`/api/lesson/api/lessons?subjectId=${subjectId}&groupId=${groupId}`)
            .then(res => res.json())
            .then(lessons => {
                lessonSelect.innerHTML = '<option value="">-- Выберите занятие --</option>';
                lessons.forEach(lesson => {
                    const opt = document.createElement("option");
                    opt.value = lesson.id;
                    opt.textContent = new Date(lesson.startTime).toLocaleString();
                    lessonSelect.appendChild(opt);
                });
            })
            .catch(err => console.error('Ошибка при загрузке занятий:', err));
    }

    subjectSelect.addEventListener('change', loadLessons);
    groupSelect.addEventListener('change', loadLessons);

    form.addEventListener('submit', (e) => {
        e.preventDefault();

        const formData = new FormData(form);
        const data = new URLSearchParams(formData);

        fetch('/api/homework/create', {
            method: 'POST',
            body: data
        })
        .then(response => {
            if (response.ok) {
                messageDiv.style.color = 'green';
                messageDiv.textContent = 'Домашнее задание успешно создано!';
                form.reset();
                lessonSelect.innerHTML = '<option value="">-- Сначала выберите предмет и группу --</option>';
            } else {
                messageDiv.style.color = 'red';
                messageDiv.textContent = 'Ошибка при создании домашнего задания';
            }
        })
        .catch(err => {
            messageDiv.style.color = 'red';
            messageDiv.textContent = 'Ошибка при создании домашнего задания';
            console.error(err);
        });
    });
});
