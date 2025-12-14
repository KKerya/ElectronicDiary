document.getElementById("subjectForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const name = document.getElementById("subjectName").value.trim();
    if (!name) return alert("Введите название предмета");

    fetch('/api/subject/create', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name: name })
    })
    .then(response => {
        if (response.ok) {
            document.getElementById("subjectMessage").innerText = "Предмет успешно создан!";
            setTimeout(() => location.reload(), 1000);
        } else {
            document.getElementById("subjectMessage").innerText = "Ошибка при создании предмета";
        }
    })
    .catch(error => {
        console.error(error);
        document.getElementById("subjectMessage").innerText = "Произошла ошибка";
    });
});

document.getElementById("teacherForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const teacherId = parseInt(document.getElementById("teacherSelect").value);
    const subjectId = parseInt(document.getElementById("subjectSelect").value);

    if (!teacherId || !subjectId) {lf
        alert("Выберите учителя и предмет");
        return;
    }

    fetch('/api/subject/set', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ teacherId: teacherId, subjectId: subjectId })
    })
    .then(response => response.ok ? "Учитель успешно назначен на предмет!" : "Ошибка при назначении учителя")
    .then(message => document.getElementById("teacherMessage").innerText = message)
    .catch(error => {
        console.error(error);
        document.getElementById("teacherMessage").innerText = "Произошла ошибка";
    });
});

document.getElementById("removeForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const teacherId = parseInt(document.getElementById("teacherSelectRemove").value);
    const subjectId = parseInt(document.getElementById("subjectSelectRemove").value);

    if (!teacherId || !subjectId) {
        alert("Выберите учителя и предмет");
        return;
    }

    fetch('/api/subject/remove', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ teacherId, subjectId })
    })
    .then(response => response.ok ? "Предмет удален у учителя!" : "Ошибка при удалении")
    .then(message => {
        document.getElementById("removeMessage").innerText = message;
        setTimeout(() => location.reload(), 1000);
    })
    .catch(error => {
        console.error(error);
        document.getElementById("removeMessage").innerText = "Произошла ошибка";
    });
});
