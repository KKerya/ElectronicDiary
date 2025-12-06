document.getElementById("createBtn").addEventListener("click", () => {

    const groupId = document.getElementById("groupSelect").value;
    const subjectId = document.getElementById("subjectSelect").value;
    const teacherId = document.getElementById("teacherSelect").value;
    const startValue = document.getElementById("startTime").value;
    const wholeYear = document.getElementById("wholeYear").checked;

    if (!startValue) {
        alert("Укажите дату и время!");
        return;
    }

    const normalizedDate = startValue + ":00";

    const payload = {
        groupId: Number(groupId),
        subjectId: Number(subjectId),
        teacherId: Number(teacherId),
        startTime: normalizedDate,
        wholeYear: wholeYear
    };

    fetch("/api/lesson/create", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
    })
        .then(async res => {
            const text = await res.text();
            console.log("STATUS:", res.status);
            console.log("RESPONSE:", text);

            if (!res.ok) {
                document.getElementById("result").textContent = "Ошибка: " + text;
                return;
            }

            document.getElementById("result").textContent = "Уроки успешно созданы!";
        })
        .catch(err => {
            console.error("FETCH ERROR:", err);
            document.getElementById("result").textContent = "Ошибка запроса";
        });
});
