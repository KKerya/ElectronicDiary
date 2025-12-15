document.addEventListener('DOMContentLoaded', () => {
    const subjectSelect = document.getElementById('subjectSelect');
    const loadBtn = document.getElementById('loadAttendanceBtn');
    const tableBody = document.querySelector('#attendanceTable tbody');
    const messageDiv = document.getElementById('message');

    loadBtn.addEventListener('click', () => {
        const subjectId = subjectSelect.value;
        tableBody.innerHTML = '';
        messageDiv.textContent = '';

        if (!subjectId) {
            messageDiv.textContent = "Пожалуйста, выберите предмет!";
            return;
        }

        fetch(`/api/attendance/my-attendance/subject/${subjectId}`)
            .then(response => response.json())
            .then(data => {
                if (data.length === 0) {
                    messageDiv.textContent = "Нет данных по посещаемости для выбранного предмета.";
                    return;
                }

                data.forEach(a => {
                    const row = document.createElement('tr');
                    row.innerHTML = `<td>${a.date}</td><td>${a.status}</td>`;
                    tableBody.appendChild(row);
                });
            })
            .catch(err => {
                console.error('Ошибка загрузки посещений:', err);
                messageDiv.textContent = "Произошла ошибка при загрузке посещаемости.";
            });
    });
});
