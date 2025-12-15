document.addEventListener('DOMContentLoaded', () => {
    const subjectSelect = document.getElementById('subject');
    const homeworkListDiv = document.getElementById('homeworkList');

    subjectSelect.addEventListener('change', () => {
        const subjectId = subjectSelect.value;

        if (!subjectId) {
            homeworkListDiv.innerHTML = "<p>Выберите предмет, чтобы увидеть домашние задания.</p>";
            return;
        }

        fetch(`/api/homework?subjectId=${subjectId}`)
            .then(res => res.json())
            .then(homeworks => {
                if(homeworks.length === 0) {
                    homeworkListDiv.innerHTML = "<p>Домашние задания не найдены.</p>";
                    return;
                }

                const html = homeworks.map(hw => `
                    <div class="homework-item">
                        <p><strong>Описание:</strong> ${hw.description}</p>
                        <p><strong>Создано:</strong> ${new Date(hw.createdAt).toLocaleString()}</p>
                        <p><strong>Дедлайн:</strong> ${new Date(hw.deadline).toLocaleString()}</p>
                    </div>
                `).join('');

                homeworkListDiv.innerHTML = html;
            })
            .catch(err => {
                console.error(err);
                homeworkListDiv.innerHTML = "<p>Ошибка при загрузке домашних заданий.</p>";
            });
    });
});
