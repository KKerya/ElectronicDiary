const subjectSelect = document.getElementById("subjectSelect");
const groupSelect = document.getElementById("groupSelect");
const loadBtn = document.getElementById("loadBtn");

const avgSpan = document.getElementById("average");
const totalSpan = document.getElementById("totalCount");
const tableBody = document.querySelector("#distributionTable tbody");

loadBtn.addEventListener("click", () => {
    const subjectId = subjectSelect.value;
    const groupId = groupSelect.value;

    if (!subjectId || !groupId) {
        alert("Выберите предмет и группу");
        return;
    }

    fetch(`/api/grade/teacher/average?subjectId=${subjectId}&groupId=${groupId}`)
        .then(res => res.json())
        .then(avg => avgSpan.textContent = avg.toFixed(2))
        .catch(err => console.error(err));

    fetch(`/api/grade/subject/count?subjectId=${subjectId}&groupId=${groupId}`)
        .then(res => res.json())
        .then(count => totalSpan.textContent = count)
        .catch(err => console.error(err));

    fetch(`/api/grade/subject/distribution?subjectId=${subjectId}&groupId=${groupId}`)
        .then(res => res.json())
        .then(data => {
            tableBody.innerHTML = "";
            data.forEach(d => {
                const row = document.createElement("tr");
                row.innerHTML = `<td>${d.value}</td><td>${d.count}</td>`;
                tableBody.appendChild(row);
            });
        })
        .catch(err => console.error(err));
});
