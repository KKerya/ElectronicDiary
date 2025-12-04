const subjectSelect = document.getElementById("subjectSelect");
const lessonSelect = document.getElementById("lessonSelect");
const showBtn = document.getElementById("showStudentsBtn");
const studentsTable = document.getElementById("studentsTable");
const studentsTableBody = studentsTable.querySelector("tbody");

showBtn.addEventListener("click", () => {
    const lessonId = lessonSelect.value;
    const subjectId = subjectSelect.value;

    if (!lessonId || !subjectId) {
        alert("Выберите предмет и дату занятия!");
        return;
    }

    fetch(`/api/attendance/students/${lessonId}`)
        .then(res => {
            if (!res.ok) throw new Error("Ошибка запроса к серверу");
            return res.json();
        })
        .then(data => {
            studentsTableBody.innerHTML = "";

            data.forEach(student => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${student.fullName}</td>
                    <td class="status-cell">${student.status || ''}</td>
                    <td>
                        <button class="mark-btn" data-id="${student.id}" data-status="PRESENT">Присутствовал</button>
                        <button class="mark-btn" data-id="${student.id}" data-status="LATE">Опоздание</button>
                        <button class="mark-btn" data-id="${student.id}" data-status="EXCUSED_ABSENCE">Уважительная причина</button>
                        <button class="mark-btn" data-id="${student.id}" data-status="UNEXCUSED_ABSENCE">Неуважительная причина</button>
                    </td>
                `;
                studentsTableBody.appendChild(row);
            });

            studentsTable.style.display = "table";

            // Навешиваем обработчик на кнопки
            document.querySelectorAll(".mark-btn").forEach(btn => {
                btn.addEventListener("click", function() {
                    const studentId = this.dataset.id;
                    const status = this.dataset.status;

                    fetch("/api/attendance/create", {
                        method: "POST",
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify({ lessonId, studentId, status })
                    })
                    .then(res => res.json())
                    .then(attendance => {
                        this.closest("tr").querySelector(".status-cell").textContent = attendance.status;
                    })
                    .catch(err => console.error(err));
                });
            });
        })
        .catch(err => {
            console.error(err);
            alert("Не удалось загрузить студентов");
        });
});
