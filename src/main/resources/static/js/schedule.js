const weekSelect = document.getElementById("weekSelect");
const scheduleContainer = document.getElementById("scheduleContainer");

const daysOrderLeft = ["MONDAY", "TUESDAY", "WEDNESDAY"];
const daysOrderRight = ["THURSDAY", "FRIDAY", "SATURDAY"];
const dayNamesRu = {
    MONDAY: "Понедельник",
    TUESDAY: "Вторник",
    WEDNESDAY: "Среда",
    THURSDAY: "Четверг",
    FRIDAY: "Пятница",
    SATURDAY: "Суббота"
};

function formatTime(isoString) {
    if (!isoString) return "";
    return isoString.substring(11, 16);
}

function createLessonNode(lesson) {
    const el = document.createElement("div");
    el.className = "lesson";
    const endTime = lesson.durationMinutes ? addMinutes(lesson.startTime, lesson.durationMinutes) : "";
    el.textContent = `${lesson.subjectName} (${formatTime(lesson.startTime)}${endTime ? " — " + endTime : ""})`;
    return el;
}

function addMinutes(iso, minutes) {
    const d = new Date(iso);
    d.setMinutes(d.getMinutes() + minutes);
    const hh = String(d.getHours()).padStart(2, "0");
    const mm = String(d.getMinutes()).padStart(2, "0");
    return `${hh}:${mm}`;
}

function createDayColumn(dayKey, lessons) {
    const container = document.createElement("div");
    container.className = "day-column";

    const h = document.createElement("h3");
    h.textContent = dayNamesRu[dayKey];
    container.appendChild(h);

    if (!lessons || lessons.length === 0) {
        const empty = document.createElement("div");
        empty.className = "lesson";
        empty.textContent = "Занятий нет";
        container.appendChild(empty);
        return container;
    }

    lessons.sort((a,b) => a.startTime.localeCompare(b.startTime));
    lessons.forEach(lesson => container.appendChild(createLessonNode(lesson)));
    return container;
}

function renderWeek(data) {
    scheduleContainer.innerHTML = "";

    const leftCol = document.createElement("div");
    const rightCol = document.createElement("div");
    leftCol.className = "days-column";
    rightCol.className = "days-column";

    daysOrderLeft.forEach(day => {
        const lessons = data.lessonsByDay[day] || [];
        leftCol.appendChild(createDayColumn(day, lessons));
    });
    daysOrderRight.forEach(day => {
        const lessons = data.lessonsByDay[day] || [];
        rightCol.appendChild(createDayColumn(day, lessons));
    });

    scheduleContainer.appendChild(leftCol);
    scheduleContainer.appendChild(rightCol);
}

function loadSchedule() {
    const weekNumber = weekSelect.value;
    if (!weekNumber) return;

    scheduleContainer.innerHTML = "Загрузка...";
    fetch(`/api/schedule/group/${groupId}/week/${weekNumber}`)
        .then(res => {
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            return res.json();
        })
        .then(data => renderWeek(data))
        .catch(err => {
            console.error(err);
            scheduleContainer.innerHTML = "Не удалось загрузить расписание";
        });
}

window.addEventListener("load", () => {
    if (!weekSelect.value && weekSelect.options.length > 0) weekSelect.selectedIndex = 0;
    loadSchedule();
});

weekSelect.addEventListener("change", loadSchedule);
