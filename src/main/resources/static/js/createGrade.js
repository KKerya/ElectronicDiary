const subjectSelect = document.getElementById("subjectSelect");
const groupSelect = document.getElementById("groupSelect");
const lessonSelect = document.getElementById("lessonSelect");
const loadStudentsBtn = document.getElementById("loadStudentsBtn");
const studentsTable = document.getElementById("studentsTable");
const studentsTableBody = studentsTable.querySelector("tbody");

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
        });
}

subjectSelect.addEventListener("change", loadLessons);
groupSelect.addEventListener("change", loadLessons);

loadStudentsBtn.addEventListener("click", () => {
    const lessonId = lessonSelect.value;
    if (!lessonId) {
        alert("Выберите занятие!");
        return;
    }

    fetch(`/api/student?lessonId=${lessonId}`)
        .then(res => res.json())
        .then(students => {
            studentsTableBody.innerHTML = "";
            students.forEach(student => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${student.fullName}</td>
                    <td>
                        <select class="grade-select">
                            <option value="">--</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                        </select>
                    </td>
                    <td><button class="save-btn" data-id="${student.id}">Сохранить</button></td>
                `;
                studentsTableBody.appendChild(row);
            });
            studentsTable.style.display = "table";

            document.querySelectorAll(".save-btn").forEach(btn => {
                btn.addEventListener("click", function() {
                    const studentId = this.dataset.id;
                    const value = this.closest("tr").querySelector(".grade-select").value;
                    if (!value) return alert("Выберите оценку!");

                    fetch("/api/grade/create", {
                        method: "POST",
                        headers: {"Content-Type": "application/json"},
                        body: JSON.stringify({
                            studentId: studentId,
                            value: parseInt(value),
                            subjectId: parseInt(subjectSelect.value),
                            date: new Date().toISOString().split('T')[0]
                        })
                    }).then(res => res.json())
                      .then(data => alert(`Оценка сохранена: ${value}`))
                      .catch(err => console.error(err));
                });
            });
        });
});
