document.getElementById("lessonForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const groupId = document.getElementById("groupSelect").value;
    const subjectId = document.getElementById("subjectSelect").value;
    const teacherId = document.getElementById("teacherSelect").value;
    const startValue = document.getElementById("startTime").value;
    const wholeYear = document.getElementById("wholeYear").checked;

    const result = document.getElementById("result");
    result.textContent = "";
    result.style.color = "black";

    if (!groupId || !subjectId || !teacherId) {
        result.textContent = "Заполните все поля";
        result.style.color = "red";
        return;
    }

    if (!startValue) {
        result.textContent = "Укажите дату и время";
        result.style.color = "red";
        return;
    }

    let finalStartTime = startValue;

    if (wholeYear) {
        const selectedDate = new Date(startValue);
        const selectedDay = selectedDate.getDay() === 0 ? 7 : selectedDate.getDay();
        const hours = selectedDate.getHours();
        const minutes = selectedDate.getMinutes();

        const year = selectedDate.getFullYear();
        const sept1 = new Date(year, 8, 1);
        const sept1Day = sept1.getDay() === 0 ? 7 : sept1.getDay();

        let diff = selectedDay - sept1Day;
        if (diff < 0) diff += 7;

        const firstLessonDate = new Date(sept1);
        firstLessonDate.setDate(sept1.getDate() + diff);
        firstLessonDate.setHours(hours, minutes, 0, 0);

        const yyyy = firstLessonDate.getFullYear();
        const mm = String(firstLessonDate.getMonth() + 1).padStart(2, "0");
        const dd = String(firstLessonDate.getDate()).padStart(2, "0");
        const hh = String(firstLessonDate.getHours()).padStart(2, "0");
        const min = String(firstLessonDate.getMinutes()).padStart(2, "0");

        finalStartTime = `${yyyy}-${mm}-${dd}T${hh}:${min}`;
    }

    const payload = {
        groupId: Number(groupId),
        subjectId: Number(subjectId),
        teacherId: Number(teacherId),
        startTime: finalStartTime + ":00",
        wholeYear: wholeYear
    };

    fetch("/api/lesson/create", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
    })
        .then(async res => {
            const text = await res.text();

            if (!res.ok) {
                result.textContent = "Ошибка: " + text;
                result.style.color = "red";
                return;
            }

            result.textContent = "Уроки успешно созданы!";
            result.style.color = "green";
        })
        .catch(err => {
            console.error(err);
            result.textContent = "Ошибка запроса к серверу";
            result.style.color = "red";
        });
});
