document.addEventListener('DOMContentLoaded', () => {
    const btn = document.getElementById('getAverageBtn');
    btn.addEventListener('click', () => {
        const subjectSelect = document.getElementById('subjectSelect');
        const subjectId = subjectSelect.value;
        if (!subjectId) {
            alert('Выберите предмет!');
            return;
        }

        fetch(`/api/grade/average?subjectId=${subjectId}`)
            .then(response => response.json())
            .then(avg => {
                document.getElementById('average').textContent = `Средняя оценка: ${avg.toFixed(2)}`;
            })
            .catch(err => {
                console.error(err);
                alert('Ошибка при получении данных');
            });

        fetch(`/api/attendance/my-attendance/subject/${subjectId}`)
                    .then(res => res.json())
                    .then(attendances => {
                        document.getElementById('attendance').style.display = 'block';

                        const total = attendances.length;
                        const present = attendances.filter(a => a.status === 'Присутствовал').length;
                        const late = attendances.filter(a => a.status === 'Опоздание').length;
                        const excused = attendances.filter(a => a.status === 'Отсутствовал по уважительной причине').length;
                        const unexcused = attendances.filter(a => a.status === 'Отсутствовал по неуважительной причине').length;

                        document.getElementById('totalClasses').textContent = total;
                        document.getElementById('presentCount').textContent = present;
                        document.getElementById('lateCount').textContent = late;
                        document.getElementById('excusedAbsenceCount').textContent = excused;
                        document.getElementById('unexcusedAbsenceCount').textContent = unexcused;
                    })
                    .catch(err => console.error(err));
    });
});
