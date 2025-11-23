document.getElementById('gradesForm').addEventListener('submit', function(e){
    e.preventDefault();
    const subjectId = document.getElementById('subject').value;
    const groupSelect = document.getElementById('group');
    const groupId = groupSelect ? groupSelect.value : '';

    fetch(`/api/grades?subjectId=${subjectId}${groupId ? '&groupId=' + groupId : ''}`)
        .then(resp => resp.json())
        .then(data => {
            const container = document.getElementById('grades-container');
            if(data.length === 0){
                container.innerHTML = '<p>Оценок не найдено.</p>';
                return;
            }

            let html = '<table><thead><tr><th>Студент</th><th>Оценка</th><th>Учитель</th><th>Дата</th></tr></thead><tbody>';
            data.forEach(g => {
                html += `<tr>
                    <td>${g.student?.fullName ?? '—'}</td>
                    <td>${g.value}</td>
                    <td>${g.teacher?.fullName ?? '—'}</td>
                    <td>${g.date ? new Date(g.date).toLocaleString() : '—'}</td>
                </tr>`;
            });
            html += '</tbody></table>';
            container.innerHTML = html;
        })
        .catch(err => {
            console.error(err);
            document.getElementById('grades-container').innerHTML = '<p>Ошибка при загрузке оценок</p>';
        });
});