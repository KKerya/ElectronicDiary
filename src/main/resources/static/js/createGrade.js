document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("createGradeForm");
    const message = document.getElementById("message");

    form.addEventListener("submit", async function (e) {
        e.preventDefault();

        const data = {
            studentId: document.getElementById("student").value,
            subjectId: document.getElementById("subject").value,
            value: document.getElementById("value").value,
            date: document.getElementById("date").value
        };

        try {
            const response = await fetch("/api/grade/create", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            });

            if (!response.ok) {
                const err = await response.text();
                throw new Error(err || "Ошибка при создании оценки");
            }

            message.textContent = "Оценка успешно создана!";
            message.style.color = "green";
            form.reset();
        } catch (err) {
            console.error(err);
            message.textContent = "Ошибка: " + err.message;
            message.style.color = "red";
        }
    });
});
