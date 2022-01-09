function validate() {
    const description = $('#descriptionInput').val();
    const catName = $('#catSelect').val();
    let alertMessage = "";
    if (catName[0] === "Выберите категории") {
        alertMessage = "Необходимо выбрать хотя бы одну категорию\n";
    }
    if (description === "") {
        alertMessage += "Заполните описание задачи";
    }
    if (alertMessage !== "") {
        alert(alertMessage);
        return false;
    }
    return true;
}
function getCategories() {
    $(document).ready(function () {
        $.ajax({
            type: 'GET',
            url: context + '/category',
            dataType: 'json'
        }).done(function (data) {
            for (let category of data) {
                $('#catSelect option:last').after(`<option value=${category.id}>${category.name}</option>`)
            }
        })
    });
}
function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}
function setDone() {
    let target = event.target;
    let id = target.id;
    $.ajax({
        type: 'GET',
        url: context + '/done' + '?id=' + id
    }).done(async function () {
        await sleep(500);
        drawTable();
    })
}
function getTasks() {
    return $.ajax({
        type: 'GET',
        url: context + '/show',
        dataType: 'json'
    })
}
function compare(a, b) {
    return a.created.localeCompare(b.created);
}
function drawTable() {
    window.onload = getTasks().done(function (data) {
        let checkBox = document.getElementById("allCheck");
        data.sort(compare);
        let tbody = document.getElementById("mainTableBody");
        while (tbody.firstChild) {
            tbody.removeChild(tbody.firstChild);
        }
        for(let task of data) {
            if (!checkBox.checked && task.done) {
                continue;
            }
            let tr = document.createElement("tr");
            let td1 = document.createElement("td");
            let td2 = document.createElement("td");
            let td3 = document.createElement("td");
            let td4 = document.createElement("td");
            let td5 = document.createElement("td");
            let td6 = document.createElement("td");
            let td7 = document.createElement("td");
            td1.innerText = task.id;
            td2.innerText = task.description;
            const date = new Date(task.created);
            const options = { weekday: 'short', year: 'numeric', month: 'short', day: 'numeric',
                hour: 'numeric', minute: 'numeric'};
            td3.innerText = date.toLocaleDateString("ru-RU", options);
            td4.innerText = task.user.name;
            let categoriesHTML = "";
            for (let i = 0; i < task.categories.length; i++) {
                categoriesHTML += '<p>' + task.categories[i].name + '</p>';
            }
            td5.innerHTML = categoriesHTML;
            td6.innerHTML = '<span class="badge rounded-pill bg-warning text-dark">В процессе</span>';
            if (task.done === true) {
                td6.innerHTML = '<span class="badge rounded-pill bg-success text-dark">Выполнена</span>';
            }
            let input = document.createElement("input");
            input.type = "checkbox";
            input.className = "form-check-input m-1 align-middle";
            input.id = task.id;
            input.onchange = setDone;
            let label = document.createElement("label");
            label.className = "form-check-label m-1 align-middle";
            label.htmlFor = input.id;
            label.innerText = "Выполнить задачу";
            if (task.done) {
                input.checked = true;
                label.innerText = "Возобновить задачу";
            }
            td7.append(input);
            td7.append(label);
            tr.append(td1);
            tr.append(td2);
            tr.append(td3);
            tr.append(td4);
            tr.append(td5);
            tr.append(td6);
            tr.append(td7);
            tbody.append(tr);
        }
    })
}
drawTable();
setInterval(drawTable, 30000);
getCategories();

